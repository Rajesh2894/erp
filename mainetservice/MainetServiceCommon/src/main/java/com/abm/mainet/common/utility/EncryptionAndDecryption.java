package com.abm.mainet.common.utility;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author Ritesh.Patil Used for text encryption decryption
 *
 */
public class EncryptionAndDecryption {

    static Logger logger = org.slf4j.LoggerFactory.getLogger(EncryptionAndDecryption.class);
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public static void main(final String[] args) throws IllegalBlockSizeException {
        final String name = "Ritesh.Patil";
        EncryptionAndDecryption.encrypt(name);
    }

    private static byte[] key = "KNOWLEDGEABMWARE".getBytes();

    public static String encrypt(final String strToEncrypt) throws IllegalBlockSizeException {
        try {
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key, MainetConstants.AES);
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
            final SecretKeySpec secretKey = new SecretKeySpec(key, MainetConstants.AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
            return decryptedString;
        } catch (final IllegalBlockSizeException e) {
            throw e;
        } catch (final Exception e) {
            throw new FrameworkException(e);
        }
    }

    public static String encrypt(String input, String key) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), MainetConstants.AES);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            logger.error("Unable to encrypt");
        }
        return new String(Base64.encodeBase64(crypted));
    }

    public static String decrypt(String input, String key) {

        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), MainetConstants.AES);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decodeBase64(input));
        } catch (Exception e) {
            logger.error("Unable to decrypt" + e);
        }
        return new String(output);
    }

    public static String checkSum(String input) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance(MainetConstants.SHA_256);
        md.update(input.getBytes());

        byte byteData[] = md.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String encode(String input) throws UnsupportedEncodingException {
        byte[] bytesEncoded = null;
        String encodedString = null;
        bytesEncoded = Base64.encodeBase64(input.getBytes("UTF8"));
        encodedString = new String(bytesEncoded, "UTF8");
        return encodedString;
    }

    public static String decode(String encodedString) throws UnsupportedEncodingException {
        byte[] valueDecoded = Base64.decodeBase64(encodedString.getBytes("UTF8"));
        String decodedString = new String(valueDecoded, "UTF8");
        return decodedString;
    }

    public static String generateChecksumForBillCloud(String input) {
        byte bytes[] = input.getBytes(Charset.forName("UTF-8"));
        Checksum generateCheckSum = new Adler32();
        generateCheckSum.update(bytes, 0, bytes.length);
        long checksum = generateCheckSum.getValue();
        return checksum + "";
    }
    
   public static String HmacSHA256ForChecksumBillDesk(String message,String secret)  {
    	MessageDigest md = null;
    		try {

    			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
    			 SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
    			 sha256_HMAC.init(secret_key);


    			byte raw[] = sha256_HMAC.doFinal(message.getBytes());

    			StringBuffer ls_sb=new StringBuffer();
    			for(int i=0;i<raw.length;i++)
    				ls_sb.append(char2hex(raw[i]));
    				return ls_sb.toString(); //step 6
    		}catch(Exception e){
    			e.printStackTrace();
    			return null;
    		}
    	}
    public static String char2hex(byte x)

    {
        char arr[]={
                      '0','1','2','3',
                      '4','5','6','7',
                      '8','9','A','B',
                      'C','D','E','F'
                    };

        char c[] = {arr[(x & 0xF0)>>4],arr[x & 0x0F]};
        return (new String(c));
      }
    
    public static String calculateRFC2104HMAC(String data, String secret)
    	    throws java.security.SignatureException
    	    {
    	        String result;
    	        try {

    	            
    	            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), HMAC_SHA256_ALGORITHM);

    	           
    	            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
    	            mac.init(signingKey);

    	            
    	            byte[] rawHmac = mac.doFinal(data.getBytes());

    	            
    	            result = DatatypeConverter.printHexBinary(rawHmac).toLowerCase();

    	        } catch (Exception e) {
    	            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
    	        }
    	        return result;
    	    }  

}