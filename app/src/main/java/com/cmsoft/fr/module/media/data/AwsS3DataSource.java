package com.cmsoft.fr.module.media.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.Stream;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.cmsoft.fr.module.recognition.image.ImageUtil;

public class AwsS3DataSource implements MediaDataSource {

	public AmazonS3 s3;
	
	public AwsS3DataSource(AmazonS3 s3) {
		this.s3 = s3;
	}
	
	@Override
	public void save(String base64object, String objectName,  String destination) {
		byte[] bI = java.util.Base64.getDecoder().decode(ImageUtil.getBase64ImageBody(base64object));
		InputStream objectForSaving = new ByteArrayInputStream(bI);
		ObjectMetadata objectMetadata = createMetada(base64object);
		
		s3.putObject(destination, objectName, objectForSaving, objectMetadata);
	}
	
	private ObjectMetadata createMetada(String base64object) {
		ObjectMetadata metada = new ObjectMetadata();
		byte[] bI = java.util.Base64.getDecoder().decode(ImageUtil.getBase64ImageBody(base64object));
		metada.setContentType("image/" + ImageUtil.getBase64ImageExtension(base64object));
		metada.setContentLength(bI.length);
		
		return metada;
	}

}
