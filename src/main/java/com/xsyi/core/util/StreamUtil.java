package com.xsyi.core.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


/**
 * @author Hing<xingguang.ren@pactera.com>
 * @since 2014年4月19日
 */
public class StreamUtil {
    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);


    public static String readStream(HttpServletRequest request) throws Exception {
        InputStream is = null;
        try {
            is = request.getInputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            byte[] reqBytes = null;
            while ((len = is.read(bytes)) != -1) {
                reqBytes = ArrayUtils.addAll(reqBytes, ArrayUtils.subarray(bytes, 0, len));
            }
            is.close();

            // 解压报文
            reqBytes = GzipUtil.decode(reqBytes);

            String receive = new String(reqBytes, "UTF-8");
            logger.info("receive:{}", receive);

            return receive;
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != is) {
                is.close();
            }
        }
    }


    public static void writeStream(HttpServletResponse response, Object object) throws Exception {
        String jsonstr = JSON.toJSONString(object, true);
        writeStream(response, jsonstr);
    }


    public static void writeStream(HttpServletResponse response, String jsonstr) throws Exception {
        response.setContentType("application/json; charset=".concat("utf-8"));
        logger.info("response:{}", jsonstr);

        //报文压缩
        if (true) {
            response.setHeader("Content-Encoding", "gzip");
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                // 压缩报文, 并写入流
                os.write(GzipUtil.encode(jsonstr.getBytes("utf-8")));
                os.close();
                return;
            } catch (Exception e) {
                throw e;
            } finally {
                if (os != null) {
                    os.close();
                }
            }
        } else {
            PrintWriter pw = null;
            try {
                pw = response.getWriter();
                pw.write(jsonstr);
                pw.close();
                return;
            } catch (Exception e) {
                throw e;
            } finally {
                if (null != pw) {
                    pw.close();
                }
            }
        }

    }
}
