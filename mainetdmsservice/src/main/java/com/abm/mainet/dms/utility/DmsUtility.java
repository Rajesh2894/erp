package com.abm.mainet.dms.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DmsUtility {

	public static byte[] convertStreamtoByte(InputStream stream) {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];

		try {
			while ((nRead = stream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			buffer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buffer.toByteArray();
	}
	
	

}