package com.abm.mainet.common.master.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import org.slf4j.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.lucene.analysis.path.ReversePathHierarchyTokenizer;
import org.bouncycastle.jce.provider.JDKMessageDigest.SHA256;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.PaymentDetailForMPOSDto;
import com.abm.mainet.common.dto.ReversalPaymentForMPOSDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.EncryptionAndDecryption;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cherupelli.srikanth
 * @since 22 Jan 2020
 */

@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.common.master.service.ICommonEncryptionAndDecryption")
@Api(value = "/commonEncryptAndDecrypt")
@Path("/commonEncryptAndDecrypt")
@Service
public class CommonEncryptionAndDecryptionImpl implements ICommonEncryptionAndDecryption {
	
	static Logger logger = org.slf4j.LoggerFactory.getLogger(EncryptionAndDecryption.class);

	@Override
	@Consumes({"application/json", "text/plain"})
	@POST
	@ApiOperation(value = "commonEncrypt", notes = "Encypt the data", response = Object.class)
	@Path("/commonEncrypt")
	public String commonEncrypt(@RequestBody Object obj) {
		try {
			byte[] objectToByteArray = ObjectToByteArray(obj);
			String encodedString = java.util.Base64.getEncoder().encodeToString(objectToByteArray);
			return EncryptionAndDecryption.encrypt(encodedString);
		} catch (Exception exception) {
			throw new FrameworkException("Error while encrypting: ", exception);
		}
	}

	@Override
	@Consumes({"application/json", "text/plain"})
	@POST
	@ApiOperation(value = "commonDecrypt", notes = "Decrypt the data", response = Object.class)
	@Path("/commonDecrypt")
	public Object commonDecryption(@RequestBody String encrypt) {
		try {
			String decrypt = EncryptionAndDecryption.decrypt(encrypt);
			byte[] decode = java.util.Base64.getDecoder().decode(decrypt);
			ByteArrayInputStream in = new ByteArrayInputStream(decode);
			ObjectInputStream is = new ObjectInputStream(in);
			Object object = is.readObject();
			return object;
		} catch (Exception exception) {
			throw new FrameworkException("Error while encrypting: ", exception);
		}

	}
	
	@Override
	@Consumes({"application/json", "text/plain"})
	@POST
	@ApiOperation(value = "decrypts", notes = "Decrypt the data", response = Object.class)
	@Path("/decrypts")
	public String decryptions(@RequestBody String encrypt) {
		try {
			String decrypt = EncryptionAndDecryption.decrypt(encrypt);
			logger.info(decrypt);
			return decrypt;
		} catch (Exception exception) {
			throw new FrameworkException("Error while dcrypting: ", exception);
		}

	}

	private byte[] ObjectToByteArray(Object obj) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		oos.flush();
		byte[] data = bos.toByteArray();
		return data;
	}
	
	@Override
	@Consumes({"application/json", "text/plain"})
	@POST
	@ApiOperation(value = "commonCheckSum", notes = "CheckSum the data", response = Object.class)
	@Path("/commonCheckSum")
	public String commonCheckSum(String input) {
		try {

			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	@Override
	@Consumes({"application/json", "text/plain"})
	@POST
	@Path("/hashKeyForMpos")
	public String hashKeyForMpos(String requestBody) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		 // Hashing algorithm
		 String hmacAlgo = "HmacSHA256";//
		 // Secret key is different for different environments
		 //String secretKey = ApplicationSession.getInstance().getMessage("mpos.pscl.pinelab.key");
		 String secretKey = "bQJwXNhFQxyWFSw8pAcsMiBlkabUlGyX";
		 javax.crypto.spec.SecretKeySpec key = new 
		javax.crypto.spec.SecretKeySpec((secretKey).getBytes("UTF-8"), hmacAlgo);
		 javax.crypto.Mac mac = javax.crypto.Mac.getInstance(hmacAlgo);
		 mac.init(key);
		 byte[] hashInBytes = mac.doFinal(requestBody.getBytes("UTF-8"));
		String digest = java.util.Base64.getUrlEncoder().encodeToString(hashInBytes);
		 return digest;
	}
	
	@Override
	@Consumes({"application/json", "text/plain"})
	@POST
	@ApiOperation(value = "convertDtoToString", notes = "Encypt the data", response = Object.class)
	@Path("/convertDtoToString")
	public String convertDtoToString(PaymentDetailForMPOSDto dto) throws JsonProcessingException {
		  String writeValueAsString = new ObjectMapper().writeValueAsString(dto);
		  return writeValueAsString;
	}
	
	@Override
	@Consumes({"application/json", "text/plain"})
	@POST
	@ApiOperation(value = "convertDtoToStringForReversePayment", notes = "Encypt the data", response = Object.class)
	@Path("/convertDtoToString")
	public String convertDtoToStringForReversePayment(ReversalPaymentForMPOSDto dto) throws JsonProcessingException {
		  String writeValueAsString = new ObjectMapper().writeValueAsString(dto);
		  return writeValueAsString;
	}
	
	
	public static void main(String [] args) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException {
		CommonEncryptionAndDecryptionImpl impl=new CommonEncryptionAndDecryptionImpl();
		System.out.println(impl.hashKeyForMpos("billIdentifier=N23534654564"));
		System.out.println(EncryptionAndDecryption.calculateRFC2104HMAC("billIdentifier=N23534654564", "bQJwXNhFQxyWFSw8pAcsMiBlkabUlGyX"));
		String requestBody = "billIdentifier=N23534654564"; 
	     String hmacAlgo = "HmacSHA256"; 
		 String secretKey = "bQJwXNhFQxyWFSw8pAcsMiBlkabUlGyX"; 
		 javax.crypto.spec.SecretKeySpec key = new javax.crypto.spec.SecretKeySpec((secretKey).getBytes("UTF-8"), hmacAlgo);
		 javax.crypto.Mac mac = javax.crypto.Mac.getInstance(hmacAlgo); mac.init(key);
		 byte[] hashInBytes = mac.doFinal(requestBody.getBytes("UTF-8")); 
		 String digest = java.util.Base64.getUrlEncoder().encodeToString(hashInBytes);
		 System.out.println(digest);
	
	}
}
