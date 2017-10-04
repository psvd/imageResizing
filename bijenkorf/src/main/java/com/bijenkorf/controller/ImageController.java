package com.bijenkorf.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.util.IOUtils;
import com.bijenkorf.service.ImageResizingService;

@RestController
public class ImageController {

	private ImageResizingService imageService;	

	@Autowired
	public ImageController(ImageResizingService imageService) {
		this.imageService = imageService;
	}

	@RequestMapping(value = "/image/show/{predefinedTypeName}/{dummySeoName}/?reference={reference}", method=RequestMethod.GET)
	public @ResponseBody byte[] showImage(@RequestParam("predefinedTypeName") String predefinedTypeName, 
			@RequestParam("dummySeoName") String dummySeoName, @RequestParam("reference") String reference) {
		byte[] image = null;
		
		try {
			imageService.getImage(predefinedTypeName, dummySeoName, reference);
			image = IOUtils.toByteArray(null); 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return image;
	}

	@RequestMapping(value = "/image/flush/{predefinedTypeName}/?reference={reference}", method = RequestMethod.DELETE)
	public void flushImage(@RequestParam("predefinedTypeName") String predefinedTypeName, @RequestParam("reference") String reference) {

		imageService.deleteImage(predefinedTypeName, reference);
	}
}
