package com.tekntime.mfa.web.controller;

import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekntime.mfa.service.BarCodeGeneratorService;

@RestController
@RequestMapping("/barcodes")
public class BarCodeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BarCodeGeneratorService service;

	@GetMapping(value = "/barbecue/ean13/{barcode}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> barbecueBarcode(@PathVariable("barcode") String barcode) throws Exception {
        return ResponseEntity.ok(service.generateBarcodeImage(barcode));
    }
	
	@GetMapping(value = "/barbecue/qrcode/{barcode}")
    public ResponseEntity<BufferedImage> qrBarcode(@PathVariable("barcode") String barcode) throws Exception {
        return ResponseEntity.ok(service.generateQRCodeImage(barcode));
    }
}
