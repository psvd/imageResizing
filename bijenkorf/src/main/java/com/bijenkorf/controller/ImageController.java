package com.bijenkorf.controller;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bijenkorf.domain.Image;
import com.bijenkorf.domain.PredefinedImageType;
import com.bijenkorf.enumerator.ApplicationMessageKey;
import com.bijenkorf.exception.CustomImageException;
import com.bijenkorf.service.ImageResizingService;


@RestController
public class ImageController {

	private ImageResizingService imageService;	

	@Autowired
	public ImageController(ImageResizingService imageService) {
		this.imageService = imageService;
	}

	@RequestMapping(value = "/image/show/{predefinedTypeName}/{dummySeoName}", method=RequestMethod.GET)
	@ResponseBody
	public void showImage(HttpServletResponse response, @PathVariable(value = "predefinedTypeName") String predefinedTypeName, 
			@PathVariable(value="dummySeoName", required=false) String dummySeoName, 
			@RequestParam(value = "reference", required= true) String reference) throws CustomImageException {		
		try {
			imageService.downloadImage(predefinedTypeName, reference);
			response.setHeader("Content-Disposition", "attachment; filename=" + reference);

		} catch (CustomImageException e) {
			throw new CustomImageException(ApplicationMessageKey.NOT_FOUND);			
		}	
	}
	
	@RequestMapping(value = "/image/flush/{predefinedTypeName}", method = RequestMethod.GET)
	public void flushImage(@PathVariable("predefinedTypeName") String predefinedTypeName, 
			@RequestParam(value = "reference", required = true) String reference) {

		imageService.deleteImage(predefinedTypeName, reference);
	}

	@RequestMapping(value = "/image/imageResizing/{reference}", method = RequestMethod.GET)
	public Image uploadImage(@PathVariable String reference) {
		Image image = null;
		try {			
			image = imageService.uploadImage(reference);
		} catch (CustomImageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return image;
	}
}
