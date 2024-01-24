package com.abm.mainet.common.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author Ritesh.Patil Used for text encryption decryption
 *
 */
public class EncryptionAndDecryption {

    static Logger logger = Logger.getLogger(EncryptionAndDecryption.class);

    public static void main(final String[] args) throws IllegalBlockSizeException {
        final String name = "Ritesh.Patil";
        EncryptionAndDecryption.encrypt(name);
    }

	private static byte[] key = "KNOWLEDGEABMWARE".getBytes();

    public static String encrypt(final String strToEncrypt) throws IllegalBlockSizeException {
        try {
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            final String encryptedString = Base64.encodeBase64URLSafeString(cipher.doFinal(strToEncrypt.getBytes()));
            return encryptedString;
        } catch (final IllegalBlockSizeException e) {
            throw e;
        } catch (final Exception e) {
            throw new FrameworkException(e);
        }

    }

    public static String decrypt(final String strToDecrypt) throws IllegalBlockSizeException {
        try {

            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
            return decryptedString;
        } catch (final IllegalBlockSizeException e) {
            throw e;
        } catch (final Exception e) {
            throw new FrameworkException(e);
        }
    }

    
    public static String encrypt(String input, String key)
	{
    	byte[] crypted = null;
  	  try{
  	    SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
  	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
  	      cipher.init(Cipher.ENCRYPT_MODE, skey);
  	      crypted = cipher.doFinal(input.getBytes());
  	    }catch(Exception e){
  	    	logger.error("Unable to encrypt");
  	    }
  	    return new String(Base64.encodeBase64(crypted));
	}
    public static String decrypt(String input, String key){
    	 byte[] output = null;
 	    try{
 	      SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
 	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
 	      cipher.init(Cipher.DECRYPT_MODE, skey);
 	      output = cipher.doFinal(Base64.decodeBase64(input));
 	    }catch(Exception e){
 	    	logger.error("Unable to decrypt "+ e);
 	    }
 	    return new String(output);
 	}
 	
    public static String checkSum(String input) throws NoSuchAlgorithmException
	{

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes());
 
        byte byteData[] = md.digest();
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	return  hexString.toString();
	}
    
    public static String generateChecksumForBillCloud(String input) { 
    	 byte bytes[] = input.getBytes(Charset.forName("UTF-8")); 
    	 Checksum generateCheckSum = new Adler32(); 
    	 generateCheckSum.update(bytes,0,bytes.length); 
    	 long checksum = generateCheckSum.getValue(); 
    	 return checksum+""; 
    	}
    

}
