package com.abm.mainet.common.master.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.jws.WebService;

import com.abm.mainet.common.dto.PaymentDetailForMPOSDto;
import com.abm.mainet.common.dto.ReversalPaymentForMPOSDto;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author cherupelli.srikanth
 *@since 22 Jan 2020
 */

@WebService
public interface ICommonEncryptionAndDecryption {

	String commonEncrypt(Object obj);
	
	Object commonDecryption(String encrypt);
	
	String commonCheckSum(String input);
	
	String hashKeyForMpos(String requestBody) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;
	
	String convertDtoToString(PaymentDetailForMPOSDto dto) throws JsonProcessingException;

	String decryptions(String encrypt);
	
	String convertDtoToStringForReversePayment(ReversalPaymentForMPOSDto dto) throws JsonProcessingException;
}
