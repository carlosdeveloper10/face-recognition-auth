package com.cmsoft.fr.module.recognition.image;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ImageUtil {

	private ImageUtil() {
		//It's private to avoid an instance creation.
	}

	public static boolean isBase64ImageStructureOk(String imageBase64) {
		try {
			// If it's possible to get the base64 image, then the image structure is OK
			getBase64ImageExtension(imageBase64);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String getBase64ImageExtension(String imageBase64) {
		if (imageBase64 == null)
			throw new NullPointerException("The base64 image is null.");

		Pattern patternForHeader = Pattern.compile("^(data:image\\/(jpeg|png|jpg));base64");
		Matcher coincidenceForHeader = patternForHeader.matcher(imageBase64);
		if (!coincidenceForHeader.find()) {
			throw new IllegalArgumentException("The argument is not a image");
		}
		return coincidenceForHeader.group(2);
	}

	public static String getBase64ImageBody(String imageBase64) {
		if (imageBase64 == null)
			throw new NullPointerException("The base64 image is null.");
		
		if(!isBase64ImageStructureOk(imageBase64)) 
			throw new IllegalArgumentException("The argument is not a image");
		
		return imageBase64.split(",")[1];
	}
}
