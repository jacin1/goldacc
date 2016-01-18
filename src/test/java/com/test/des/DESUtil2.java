package com.test.des;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.thoughtworks.xstream.core.util.Base64Encoder;

/**
 * 
 * @author vlinux
 *
 */
public class DESUtil2 {

	private static String strDefaultKey = "national";

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[] hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB) 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出 @ <a href="mailto:leo841001@163.com">LiGuoQing</a>
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 默认构造方法，使用默认密钥
	 * 
	 * @throws Exception
	 */
	public DESUtil2() throws Exception {
		this(strDefaultKey);
	}

	/**
	 * 指定密钥构造方法
	 * 
	 * @param strKey
	 *            指定的密钥
	 * @throws Exception
	 */
	public DESUtil2(String strKey) throws Exception {
		Key key = getKey(strKey.getBytes());

		// NoPadding
		// PKCS5Padding
		encryptCipher = Cipher.getInstance("DES/ECB/NoPadding", "SunJCE");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES/ECB/NoPadding", "SunJCE");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 加密字符串
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public String encrypt(String strIn, String encode) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes(encode)));
	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 解密字符串
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public String decrypt(String strIn, String encode) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)), encode);
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	/**
	 * DES加密
	 * @param key
	 * @param str
	 * @param encode
	 * @return
	 */
	public static String encrypt(String key, String str, String encode) {
		try {
			byte[] strBytes = str.getBytes(encode);
			byte[] newStrBytes = new byte[strBytes.length];
			for( int i=0; i<newStrBytes.length; i++ ) {
				newStrBytes[i] = i<strBytes.length?strBytes[i]:32;
			}//for
			return new DESUtil2(key).encrypt(new String(newStrBytes,encode), encode);
		} catch (Exception e) {
			//throw new MPayException(MPayException.MPAY_DES_ERROR,"加密失败",e);
			return "加密失败";
		}//try
	}
	
    public static byte[] desCrypto(byte[] datasource, String password) {            
        try{
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(password.getBytes("utf-8"));
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        //Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        //用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
        //现在，获取数据并加密
        //正式执行加密操作
        return cipher.doFinal(datasource);
        }catch(Throwable e){
                e.printStackTrace();
        }
        return null;
}
	
	private static byte[] bitXOR(final byte[] src1, final byte[] src2) {
		if (src1 == null) {
			throw new RuntimeException("src1 is null");
		}
		if (src2 == null) {
			throw new RuntimeException("src2 is null");
		}
		if (src1.length != src2.length) {
			throw new RuntimeException(
					"the length of src1 is not equal the length of src2");
		}
		byte[] bb = new byte[src1.length];
		for (int i = 0; i < src1.length; i++) {
			bb[i] = (byte) ((src1[i] ^ src2[i]) & 0xFF);
		}
		return bb;
	}
	
	private static String sbitXOR( String src1, String src2) {
		String tmps=src1;
		if (src1 == null) {
			throw new RuntimeException("src1 is null");
		}
		if (src2 == null) {
			throw new RuntimeException("src2 is null");
		}
		if (src1.length() != src2.length()) {
			throw new RuntimeException(
					"the length of src1 is not equal the length of src2");
		}
		byte tmpByt[]=tmps.getBytes();
		byte data1[]=src1.getBytes();
		byte data2[]=src2.getBytes();
		for(int i=0;i<src1.length();i++){
			tmpByt[i]=(byte) (data1[i]^data2[i]);
		}
		
		return new String(tmpByt);
	}
	
	/**
	 * DES解密
	 * @param key
	 * @param str
	 * @param encode
	 * @return
	 */
	public static String decrypt(String key, String str, String encode) {
		try {
			return new DESUtil2(key).decrypt(str, encode).replaceAll("\\s*$", "");
		} catch (Exception e) {
			//throw new MPayException(MPayException.MPAY_DES_ERROR,"解密失败",e);
			return "解密失败";
		}//try
	}
	
	public static String encrypt2(String sSrc, String sKey) throws Exception {  
        if (sKey == null) {  
            throw new IllegalArgumentException("Argument sKey is null.");  
        }  
        if (sKey.length() != 16) {  
            throw new IllegalArgumentException(  
                    "Argument sKey'length is not 16.");  
        }  
        byte[] raw = sKey.getBytes("UTF-8");  
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
   
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);  
   
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));  
        String tempStr = parseByte2HexStr(encrypted);  
   
        Base64Encoder encoder = new Base64Encoder();  
        return encoder.encode(tempStr.getBytes("UTF-8"));  
    } 
	
	/** 
     * 将二进制转换成16进制 
     *  
     * @param buf 
     * @return 
     */  
    public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    }
    
  //转化字符串为十六进制编码  
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}
	
	public static void test1(){
		
		Integer[] Integers ={0x5c,0xb6,0x36,0x3b,0xbd,0xf4,0xf0,0x95};
		Integer[] Integers2 ={0x45,0xe7,0x92,0x37,0xdf,0x25,0x0e,0xca};
		Integer[] tmp=new Integer[Integers.length];
		StringBuffer sBuffer=new StringBuffer();
		for(int i=0;i<Integers.length;i++){
			tmp[i] = (Integers[i]^Integers2[i]);
			System.out.println(Integer.toBinaryString(tmp[i]));
			sBuffer.append(Integer.toBinaryString(tmp[i]));
		}
		System.out.println(sBuffer.toString());
		
	}

	
	
	public static void main(String[] args) throws Exception {
//		String str = DESUtil2.encrypt("}7$#3@+1~/B\\[D&...","intf", "UTF-8");
//		System.out.println("密文=" + str);
//		System.out.println("明文=#" + DESUtil2.decrypt("}7$#3@+1~/B\\[D&...",str, "UTF-8")+"#");
//		System.out.println(DESUtil2.decrypt("}7$#3@+1~/B\\[D&...","54c5b564c51868ba", "UTF-8"));//f54c505c18c74894  54c5b564c51868ba
//		
//		System.out.println( DESUtil2.decrypt("}7$#3@+1", "d40d315388d74f2d", "GBK") );
		
		String key = "1234567890ABCDEF";
		String message = "1951A40C62D1FE5F";
		
		String s2="5cb6363bbdf4f095";
//		System.out.println(DESUtil2.toHexString(s2));
		test1();
	}

}
