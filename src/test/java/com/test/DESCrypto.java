package  com.test;
 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;
 

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
 

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
 
public class DESCrypto {
 
    Key key ;
 
    public DESCrypto() {
 
    }
 
    public DESCrypto(String str) {
       setKey(str); // 生成密匙
    }
 
    public Key getKey() {
       return key ;
    }
 
    public void setKey(Key key) {
       this . key = key;
    }
 
    /**
      * 根据参数生成 KEY
      */
    public void setKey(String strKey) {
       try {
           KeyGenerator _generator = KeyGenerator.getInstance ( "DES" );
           _generator.init( new SecureRandom(strKey.getBytes()));
           this . key = _generator.generateKey();
           _generator = null ;
       } catch (Exception e) {
           throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
       }
    }
 
    /**
      * 加密 String 明文输入 ,String 密文输出
      */
    public String encryptStr(String strMing) {
       byte [] byteMi = null ;
       byte [] byteMing = null ;
       String strMi = "" ;
       BASE64Encoder base64en = new BASE64Encoder();
       try {
           byteMing = strMing.getBytes( "UTF8" );
           byteMi = this .encryptByte(byteMing);
           strMi = base64en.encode(byteMi);
       } catch (Exception e) {
           throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
       } finally {
           base64en = null ;
           byteMing = null ;
           byteMi = null ;
       }
       return strMi;
    }
 
    /**
      * 解密 以 String 密文输入 ,String 明文输出
      *
      * @param strMi
      * @return
      */
    public String decryptStr(String strMi) {
       BASE64Decoder base64De = new BASE64Decoder();
       byte [] byteMing = null ;
       byte [] byteMi = null ;
       String strMing = "" ;
       try {
           byteMi = base64De.decodeBuffer(strMi);
           byteMing = this .decryptByte(byteMi);
           strMing = new String(byteMing, "UTF-8" );
       } catch (Exception e) {
           throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
       } finally {
           base64De = null ;
           byteMing = null ;
           byteMi = null ;
       }
       return strMing;
    }
 
    /**
      * 加密以 byte[] 明文输入 ,byte[] 密文输出
      *
      * @param byteS
      * @return
      */
    private byte [] encryptByte( byte [] byteS) {
       byte [] byteFina = null ;
       Cipher cipher;
       try {
           cipher = Cipher.getInstance ( "DES" );
           cipher.init(Cipher. ENCRYPT_MODE , key );
           byteFina = cipher.doFinal(byteS);
       } catch (Exception e) {
           throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
       } finally {
           cipher = null ;
       }
       return byteFina;
    }
 
    /**
      * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
      *
      * @param byteD
      * @return
      */
    private byte [] decryptByte( byte [] byteD) {
       Cipher cipher;
       byte [] byteFina = null ;
       try {
           cipher = Cipher.getInstance ( "DES" );
           cipher.init(Cipher. DECRYPT_MODE , key );
           byteFina = cipher.doFinal(byteD);
       } catch (Exception e) {
           throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
       } finally {
           cipher = null ;
       }
       return byteFina;
    }
 
    /**
      * 文件 file 进行加密并保存目标文件 destFile 中
      *
      * @param file
      *             要加密的文件 如 c:/test/srcFile.txt
      * @param destFile
      *             加密后存放的文件名 如 c:/ 加密后文件 .txt
      */
    public void encryptFile(String file, String destFile) throws Exception {
       Cipher cipher = Cipher.getInstance ( "DES" );
       // cipher.init(Cipher.ENCRYPT_MODE, getKey());
       cipher.init(Cipher. ENCRYPT_MODE , this . key );
       InputStream is = new FileInputStream(file);
       OutputStream out = new FileOutputStream(destFile);
       CipherInputStream cis = new CipherInputStream(is, cipher);
       byte [] buffer = new byte [1024];
       int r;
       while ((r = cis.read(buffer)) > 0) {
           out.write(buffer, 0, r);
       }
       cis.close();
       is.close();
       out.close();
    }
 
    /**
      * 文件采用 DES 算法解密文件
      *
      * @param file
      *             已加密的文件 如 c:/ 加密后文件 .txt *
      * @param destFile
      *             解密后存放的文件名 如 c:/ test/ 解密后文件 .txt
      */
    public void decryptFile(String file, String dest) throws Exception {
       Cipher cipher = Cipher.getInstance ( "DES" );
       cipher.init(Cipher. DECRYPT_MODE , this . key );
       InputStream is = new FileInputStream(file);
       OutputStream out = new FileOutputStream(dest);
       CipherOutputStream cos = new CipherOutputStream(out, cipher);
       byte [] buffer = new byte [1024];
       int r;
       while ((r = is.read(buffer)) >= 0) {
           cos.write(buffer, 0, r);
       }
       cos.close();
       out.close();
       is.close();
    }
    
    /**
     * 使用异或进行简单的密码加密
     * @return <code>String[]</code> 加密后字符串
     * @author Administrator
     * @since 1.0 2005/11/28
     */

    public static String setEncrypt(String str){
        String sn="ziyu"; //密钥
        int[] snNum=new int[str.length()];
        String result="";
        String temp="";

        for(int i=0,j=0;i<str.length();i++,j++){
            if(j==sn.length())
                j=0;
            snNum[i]=str.charAt(i)^sn.charAt(j);
        }

        for(int k=0;k<str.length();k++){

            if(snNum[k]<10){
                temp="00"+snNum[k];
            }else{
                if(snNum[k]<100){
                    temp="0"+snNum[k];
                }
            }
            result+=temp;
        }
        return result;
    }
    
    public static byte[] byteXOR(byte[] parameter1, byte[] parameter2)
    {
        if(parameter1.length != parameter2.length)
        {
            System.out.println("不能进行模二加！");
            return null;
        }

        byte[] ByteXOR = new byte[parameter1.length];
        byte temp3;
        
        for(int i = 0; i < parameter1.length; i++)
        {
            byte temp1 = parameter1[i];
            byte temp2 = parameter2[i];
            temp3 = (byte)(temp1 ^ temp2);
            ByteXOR[i] = temp3;
        }
        
        return ByteXOR;
    }
 
    public static void main(String[] args) throws Exception {
//       DESCrypto des = new DESCrypto( "1234567890ABCDEF" );
//       String str1 = "1951A40C62D1FE5F" ;
//       // DES 加密字符串
//       String tmp="0A394D63C627B16B";
//       String str2 = des.encryptStr(str1);
//       // DES 解密字符串
//       String deStr = des.decryptStr(str2);
////       String tmpDes= des.decryptStr(tmp);
//       des.decryptByte(tmp.getBytes());
//       System. out .println( " 加密前： " + str1);
//       System. out .println( " 加密后： " + str2);
//       System. out .println( " 解密后： " + deStr);
    	String s3="1951A40C62D1FE5F";
    	byte[] tmpb=s3.getBytes();
    	String s1="5cb6363bbdf4f095";
    	String s2="45e79237df250eca";
    	byte[] tmps1=DESCrypto.byteXOR(s1.getBytes(), s2.getBytes()); 
    	System.out.println(new String(tmps1));
    }
} 