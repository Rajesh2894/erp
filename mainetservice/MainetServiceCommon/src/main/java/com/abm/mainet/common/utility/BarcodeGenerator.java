package com.abm.mainet.common.utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

import javax.imageio.ImageIO;

import com.abm.mainet.common.exception.FrameworkException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * This class uses zxing API to generate the barcodes in various formats
 * @author Vardan.Savarde
 *
 */
public class BarcodeGenerator {
	private Writer barcodeWriter = new MultiFormatWriter();
	private BarcodeType codeType;
	private int width;
	private int height;
	/**
	 * Currently supported formats. BarcodeGenerator by default supports QR code.
	 *
	 */
	public enum BarcodeType {
		QR(BarcodeFormat.QR_CODE), CODE_39(BarcodeFormat.CODE_39), CODE_93(
				BarcodeFormat.CODE_93), CODE_128(BarcodeFormat.CODE_128);
		private BarcodeFormat format;
		private BarcodeType(BarcodeFormat barcodeFormat) {
			format = barcodeFormat;
		}
		
		private BarcodeFormat getFormat() {
			return format;
		}
	}
	/**
	 * Defaults to QR code format. Image size width=100, height=100
	 */
	public BarcodeGenerator() {
		codeType = BarcodeType.QR;
		width = 100;
		height = 100;
	}
	
	/**
	 * Defaults to QR code format. Image size is based on arguments
	 */
	public BarcodeGenerator(int width, int height) {
		if(width <= 0 || height <= 0) {
			throw new FrameworkException("Width or height cannot be zero");
		}
		codeType = BarcodeType.QR;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Creates the generator as per the input
	 * @param codeType The barcode format
	 * @param width width of the image
	 * @param height height of the image
	 */
	public BarcodeGenerator(BarcodeType codeType, int width, int height) {
		if(codeType == null || width <= 0 || height <= 0) {
			throw new FrameworkException("BarcodeType cannot be null and Width or height cannot be zero");
		}
		this.codeType = codeType;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Returns the Buffered image as per the content passed in argument
	 * @param barcodeContent barcode content
	 * @return Image
	 * @throws Exception if any exception occurs while generating barcode
	 */
	public BufferedImage generateBarcode(String barcodeContent) throws Exception {
		if(barcodeContent == null || barcodeContent.trim().equals("")) {
			throw new FrameworkException("barcodeContent cannot be null or empty");
		}
		BitMatrix bitMatrix = this.barcodeWriter.encode(barcodeContent, this.codeType.getFormat(), this.width, this.height);
		BufferedImage bufferImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
		return bufferImage;
	}
	
	/**
	 * Returns the Buffered image as per the content passed in argument
	 * @param barcodeContent barcode content
	 * @return Image
	 * @throws Exception if any exception occurs while generating barcode
	 */
	public BufferedImage generateBarcodeWithVisibleContent(String barcodeContent) throws Exception {
		
		if(barcodeContent == null || barcodeContent.trim().equals("")) {
			throw new FrameworkException("barcodeContent cannot be null or empty");
		}
		/*
		 * Temporary image for getting text dimensions
		 */
    	BufferedImage bufferImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB); // aux implementation
    	Graphics2D g2d = bufferImage.createGraphics();
    	Font font = new Font("Times", Font.PLAIN, 12);
    	g2d.setFont(font);
    	FontMetrics fm = g2d.getFontMetrics();
    	int textWidth = fm.stringWidth(barcodeContent);
    	int textHeight = fm.getHeight();
    	g2d.dispose();
    	
    	if(this.width < textWidth) {
    		this.width = textWidth + 20;
    	}
    	
    	/*
    	 * Create containing image 
    	 */
    	bufferImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
    	g2d = bufferImage.createGraphics();
    	g2d.fillRect(0, 0, this.width, this.height);
    	g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    	g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    	g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    	g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    	g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    	g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    	fm = g2d.getFontMetrics();
    	g2d.setColor(Color.BLACK);
    	g2d.drawString(barcodeContent, Math.round(Math.floor((this.width-textWidth)/2))+8, (this.height-fm.getAscent())+8);
    	g2d.dispose();

    	//Generate the BitMatrix to generate the barcode
    	Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
    	hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    	BitMatrix bitMatrix = this.barcodeWriter.encode(barcodeContent, this.codeType.getFormat(), this.width, (this.height-textHeight-(2*fm.getAscent()))+10, hintMap);

    	// Write the barcode to containing image
    	int matrixWidth = bitMatrix.getWidth();
    	int matrixHeight = bitMatrix.getHeight();

    	Graphics2D graphics = (Graphics2D) bufferImage.getGraphics();
    	graphics.setColor(Color.BLACK);
    	for (int i = 0; i < matrixWidth; i++) {
    	    for (int j = 0; j < matrixHeight; j++) {
    	        if (bitMatrix.get(i, j)) {
    	            graphics.fillRect(i, j+fm.getAscent(), 1, 1);
    	        }
    	    }
    	}
    	
		return bufferImage;
	}
	
	/**
	 * Writes the barcode image as per the content passed in argument at file path passed in argument
	 * @param barcodeContent barcode content
	 * @param filePath image will be written to this
	 * @return false if image is not generated
	 * @throws Exception if any exception occurs while generating barcode
	 */
	public boolean generateBarcode(String barcodeContent, String filePath) throws Exception {
		return ImageIO.write(generateBarcode(barcodeContent), "png", new File(filePath));
	}
	
	/**
	 * Writes the barcode image as per the content passed in argument at file path passed in argument
	 * @param barcodeContent barcode content
	 * @param filePath image will be written to this
	 * @return false if image is not generated
	 * @throws Exception if any exception occurs while generating barcode
	 */
	public boolean generateBarcodeWithVisibleContent(String barcodeContent, String filePath) throws Exception {
		return ImageIO.write(generateBarcodeWithVisibleContent(barcodeContent), "png", new File(filePath));
	}
	/**
	 * Writes the barcode image as per the content passed in argument to the output stream
	 * @param barcodeContent barcode content
	 * @param outStream image will be written to this
	 * @return false if image is not generated
	 * @throws Exception if any exception occurs while generating barcode
	 */
	public boolean generateBarcode(String barcodeContent, OutputStream outStream) throws Exception {
		return ImageIO.write(generateBarcode(barcodeContent), "png", outStream);
	}
	
	/**
	 * Writes the barcode image as per the content passed in argument to the byte array
	 * @param barcodeContent barcode content
	 * @return null if image is not generated else byte[]
	 * @throws Exception if any exception occurs while generating barcode
	 */
	public byte[] getBarcodeInByteArray(String barcodeContent) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( generateBarcode(barcodeContent), "png", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
	
	/**
	 * Writes the barcode image as per the content passed in argument to the output stream
	 * @param barcodeContent barcode content
	 * @param outStream image will be written to this
	 * @return false if image is not generated
	 * @throws Exception if any exception occurs while generating barcode
	 */
	public boolean generateBarcodeWithVisibleContent(String barcodeContent, OutputStream outStream) throws Exception {
		return ImageIO.write(generateBarcodeWithVisibleContent(barcodeContent), "png", outStream);
	}
	
	public final static void main(String[] args) {
		// (ImageIO.getWriterFormatNames() returns a list of supported formats)
		String imageFormat = "png"; // could be "gif", "tiff", "jpeg"
		
		try {
			//BarcodeGenerator brGen = new BarcodeGenerator();
			//ImageIO.write(brGen.generateBarcode("hellow how are you",true),imageFormat, new File("E:/"+generateRandoTitle(new Random(), 9)+".png"));
			/*ImageIO.write(brGen.generateBarcode("hellow how are you"),imageFormat, new File("E:/\"+generateRandoTitle(new Random(), 9)+\".png"));
			brGen.generateBarcode("hellow how are you", "E:/\"+generateRandoTitle(new Random(), 9)+\".png");
			FileOutputStream outStream = new FileOutputStream("E:/\"+generateRandoTitle(new Random(), 9)+\".png");
			brGen.generateBarcode("hellow how are you", outStream);
			outStream.close();*/
			BarcodeGenerator brGen = new BarcodeGenerator(BarcodeType.QR,400,100);
			ImageIO.write(brGen.generateBarcodeWithVisibleContent("hellow how are you aljkdf ajfk asj fdasfj a fja fas fjafk jsafjklj faj fsaj fjsad ajf ksaljfd dasfj asfj adj fdas"),imageFormat, new File("E:/"+generateRandomTitle(new Random(), 9)+".png"));
			/*brGen.generateBarcode("hellow how are you", "E:/\"+generateRandoTitle(new Random(), 9)+\".png");
			outStream = new FileOutputStream("E:/\"+generateRandoTitle(new Random(), 9)+\".png");
			brGen.generateBarcode("hellow how are you", outStream);
			outStream.close();*/
			Arrays.stream(ImageIO.getWriterFormatNames()).forEach(wr -> {System.out.println(wr);});
			System.out.println("================================");
			Arrays.stream(ImageIO.getWriterFileSuffixes()).forEach(wr -> {System.out.println(wr);});
			System.out.println("================================");
			Arrays.stream(ImageIO.getWriterMIMETypes()).forEach(wr -> {System.out.println(wr);});
			System.out.println("================================");
		}catch(Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	//Used in only the test main method to generate random file names
	private static String generateRandomTitle(Random random, int length) {
        return random.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
	
}
