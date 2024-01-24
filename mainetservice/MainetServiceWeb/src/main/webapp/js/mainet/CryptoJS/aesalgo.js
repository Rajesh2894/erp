/**
 * 
 */
function encryptData(data, encryptionKey) {
	// Convert the encryptionKey to a WordArray
	const key = CryptoJS.enc.Utf8.parse(encryptionKey);

	// Encrypt using AES (ECB mode with PKCS5Padding)
	const encryptedData = CryptoJS.AES.encrypt(data, key, {
		mode : CryptoJS.mode.ECB,
		padding : CryptoJS.pad.Pkcs7,
	});
	const encryptedStringWithPadding = CryptoJS.enc.Base64
			.stringify(encryptedData.ciphertext);
	return encryptedStringWithPadding;
}