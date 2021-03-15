package com.tekntime.mfa.service;

import java.awt.image.BufferedImage;

import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Component
public class BarCodeGeneratorService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	public  BufferedImage generateBarcodeImage(String barcodeText) {
	    EAN13Bean barcodeGenerator = new EAN13Bean();
	    BitmapCanvasProvider canvas = 
	      new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);
	    LOGGER.info("generating ....");
	    barcodeGenerator.generateBarcode(canvas, barcodeText);
	    return canvas.getBufferedImage();
	}
	

	
	public  BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
	    QRCodeWriter barcodeWriter = new QRCodeWriter();
	    BitMatrix bitMatrix = 
	      barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
	    LOGGER.info("generating QR code ....");
	    return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

}
