/*
 * @(#)FnmUtils.java 1.0 2011-6-16下午06:18:09
 *
 */
package com.xsyi.core.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xsyi.core.config.SystemConfigUtils;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 故障报警系统Fnm工具类</dd>
 * <p>
 * <dt><b>errorDescription:</b></dt>
 * <dd>
 * <p>
 * 1.先在errorcode区域定义故障代码
 * <p>
 * 2.调用相关系统的方法XXXSend(故障代码)，见main函数</dd>
 * </dl>
 * 
 * @author tanks
 * @version 1.0, 2011-6-16
 * @since draco
 * 
 */
public class FnmUtils {

	private static Log _logger = LogFactory.getLog(FnmUtils.class);
	
	public static String DRACO_SYS_NO = "28"; // 新报表系统在报警系统中使用的系统号

	private FnmUtils() {
	}

	public enum SubSystem {
		DRACO_REPORT_NO("05");// 新报表系统中的系统编号
		SubSystem(String value) {
			this.value = value;
		}

		private String value;

		public String getValue() {
			return value;
		}
	}

	public enum FnmLevel {
		NOTIFY('N'), // 通知级别报警
		WARN('W'), // 警告级别报警
		ERROR('E'), // 一般错误级别报警
		FAIL('F'); // 致命错误级别报警
		FnmLevel(char value) {
			this.value = value;
		}

		private char value;

		public char getValue() {
			return value;
		}
	}

	public enum FnmErrorCodes {

		// XXX_YYY_ZZZ(故障级别，故障代码ErrorCode，当用“xxx.yyy.zzz”到资源文件中取不到值时发送的错误信息)
		/** 身份验证主服务器连接失败 */
		JOB_PATCH_EXECUTOR_ERROR(FnmLevel.ERROR,1001,"报表系统AppServer任务执行失败");

		// #################################################error code Begin
		// ##########################################################//
		// 报警名称(错误级别，错误代码，错误描述资源文件中messageCode，资源文件中找不到时默认错误描述)
		FnmErrorCodes(FnmLevel fnmLevel, Integer errorCode,String defaultDescMsg) {
			this.fnmLevel = fnmLevel;
			this.errorCode = errorCode;
			this.defaultDescMsg = defaultDescMsg;
		}
		
		// 报警名称(错误级别，错误代码，错误描述资源文件中messageCode，资源文件中找不到时默认错误描述)
		FnmErrorCodes(FnmLevel fnmLevel, Integer errorCode,String defaultDescMsg,Object[] params) {
			this.fnmLevel = fnmLevel;
			this.errorCode = errorCode;
			this.defaultDescMsg = defaultDescMsg;
			this.params = params;
		}

		public FnmErrorCodes addParams(Object... params) {
			this.params = params;
			return this;
		}
		private Integer errorCode;
		private FnmLevel fnmLevel;
		private String defaultDescMsg;
		private Object[] params = null;

		public FnmLevel getLevel() {
			return fnmLevel;
		}

		public Integer getErrorCode() {
			return errorCode;
		}

		public String getDefaultDescMsg() {
			return defaultDescMsg;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return StringUtils.isNotBlank(defaultDescMsg) ? defaultDescMsg + (params!=null ? params[0]:StringUtils.EMPTY):StringUtils.EMPTY;
		}
	}
	
	/**
	 * 向报警系统发报警--返回发送结果信息
	 */
	public static int sendFmt(FnmErrorCodes errorcode) {
		return sendFmt(SubSystem.DRACO_REPORT_NO,errorcode);
	}

	/**
	 * 向报警系统发报警--返回发送结果信息
	 */
	private static int sendFmt(SubSystem subSystem, FnmErrorCodes errorcode) {
		_logger.info("send error to fnm: level=" + errorcode.getLevel()
				+ ",errorcode=" + errorcode.getErrorCode() + ",errormsg="
				+ errorcode.getDefaultDescMsg() );
		String errdesc = errorcode.toString();
		if (StringUtils.isBlank(errdesc) || errdesc.length() < 10) {
			errdesc = StringUtils.leftPad(errdesc, 10, "0");
		}
		if (StringUtils.isNotBlank(errdesc) && errdesc.length() > 100) {
			errdesc = StringUtils.substring(errdesc, 0, 100) + "...";
		}
		return snd_fnm(errorcode.getLevel().getValue(), DRACO_SYS_NO,
				subSystem.getValue(), errorcode.getErrorCode().toString(),
				errdesc);
	}

	public static int snd_fnm(char fault_level, String sys_id,
			String sub_sys_id, String err_id, String err_desc) {
		if (sys_id.length() != 2 || sub_sys_id.length() != 2
				|| err_id.length() > 8 || err_id.length() < 4
				|| err_desc.length() > 256 || err_desc.length() < 10) {
			_logger.info("====>fnm calling param error");
			return -1;
		}
		StringBuffer sBuf = new StringBuffer("0");
		sBuf.append("|").append(fault_level);
		sBuf.append("|").append(sys_id);
		sBuf.append("|").append(sub_sys_id);
		sBuf.append("|").append(err_id);
		sBuf.append("|").append(err_desc).append("|");

		int ret = -1;
		boolean mainfailed = false;
		// ret = SendData( "10.98.9.42" , 8108 , sBuf ) ;
		try {
			String ip = SystemConfigUtils.getSysconfigProperty("fnm.server.main.ip");
			int port = NumberUtils.toInt(SystemConfigUtils.getSysconfigProperty("fnm.server.main.port"));
			_logger.info("send to main fnm server:ip=" + ip + ",port=" + port);
			_logger.info("send data:" + sBuf.toString());
			ret = SendData(ip, port, sBuf);
		} catch (Exception e) {
			_logger.error("error while sending fnm main server", e);
			mainfailed = true;
		}
		if (mainfailed || ret < 0) {
			try {
				String ip = SystemConfigUtils.getSysconfigProperty("fnm.server.bak.ip");
				int port = NumberUtils.toInt(SystemConfigUtils.getSysconfigProperty("fnm.server.bak.port"));
				_logger.info("send to bak fnm server:ip=" + ip + ",port="
						+ port);
				_logger.info("send data:" + sBuf.toString());
				ret = SendData(ip, port, sBuf);
			} catch (Exception e) {
				_logger.error("error while sending fnm bak server", e);
			}
		}

		if (ret < 0) {
			ret = -1;
		}

		return 0;
	}

	static int SendData(String fnm_ip, int fnm_port, StringBuffer sBuf) {
		Socket sid = null;
		DataOutputStream out = null;
		DataInputStream in = null;
		try {
			sid = new Socket(fnm_ip, fnm_port);
			out = new DataOutputStream(sid.getOutputStream());
			in = new DataInputStream(sid.getInputStream());
			sid.setSoTimeout(10000);

			byte bData[], bSendData[];
			bSendData = sBuf.toString().getBytes();

			bData = new byte[4];
			append(bData, 0, "" + bSendData.length, 4, 'r');
			out.write(bData);
			out.write(bSendData);

			bData = new byte[4];
			in.read(bData, 0, 4);
			String sData = new String(bData);
			if (!sData.equals("0002")) {
				_logger.info("fnm in down");
				out.close();
				in.close();
				sid.close();
				return -1;
			}

			bData = new byte[2];
			in.read(bData, 0, 2);

			out.close();
			in.close();
			sid.close();

			sData = new String(bData);
			if (!sData.equals("00")) {
				_logger.info("fnm in down");
				return -1;
			}
		} catch (IOException e) {
			_logger.error("sendtoho err" + e + "]");
			try {
				sid.close();
				in.close();
				out.close();
			} catch (IOException sube) {
				_logger.error("io err" + sube + "]");
				return -1;
			} catch (Exception ee) {
				_logger.error("other err" + ee + "]");
				return -1;
			}
			// can not open read or write port
			return -5;
		} catch (Exception e) {
			_logger.error("other err" + e + "]");
			try {
				sid.close();
				in.close();
				out.close();
			} catch (IOException sube) {
				_logger.error("io err" + sube + "]");
				return -10;
			} catch (Exception ee) {
				_logger.error("other err" + ee + "]");
				return -1;
			}
			return -15;
		}

		return 0;
	}

	static void append(byte bData[], int Start, String s, int len, char mode) {
		byte bTmp[] = s.getBytes();
		int l = bTmp.length;
		byte bb = (byte) '0';
		if (mode == 'l') {
			bb = (byte) ' ';
		}
		int i;
		for (i = 0; i < len; i++) bData[Start + i] = bb;
		if (l > len) l = len;
		if (mode == 'l') {
			for (i = 0; i < l; i++)
				bData[Start + i] = bTmp[i];
		} else {
			for (i = 0; i < l; i++)
				bData[Start + len - l + i] = bTmp[i];
		}
	}

}
