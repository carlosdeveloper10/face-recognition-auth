package com.cmsoft.fr.module.recognition.service;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.cmsoft.fr.module.base.service.DtoFactory;
import com.cmsoft.fr.module.recognition.image.ImageUtil;

@Service
public class PersonRecognitionServiceImpl implements RecognitionService {

	private AmazonRekognition awsRekognition;

	private DtoFactory dtoFactory;

	@Autowired
	public PersonRecognitionServiceImpl(AmazonRekognition awsRekognition, DtoFactory dtoFactory) {
		this.awsRekognition = awsRekognition;
		this.dtoFactory = dtoFactory;
	}

	@Override
	public RecognitionDto recognize(FaceRecognitionRequestDto requestDto) {
		if (requestDto == null)
			throw new NullPointerException("The requestDto is null.");

		checkRequestMandatoryAttributes(requestDto);

		RecognitionDto recognitionDto = (RecognitionDto) dtoFactory.create("RECOGNITION");
		List<FaceDetail> faceDetails = this.getFaceDetails(requestDto.getBase64Image());

		if (faceDetails.isEmpty()) {
			recognitionDto.setMatchedPerson(null);
			recognitionDto.setRecognition(PersonMatchingStatus.ZERO_FACES_ON_INBOUND_IMAGE);
			recognitionDto.setConfidence(0.0f);
			recognitionDto.setMinimumRequestedConfidence(requestDto.getMinimumConfidence());
			recognitionDto.setPlusConfidence(0.0f);
		} else if (faceDetails.size() > 1) {
			recognitionDto.setMatchedPerson(null);
			recognitionDto.setRecognition(PersonMatchingStatus.MANY_FACES_ON_INBOUND_IMAGE);
			recognitionDto.setConfidence(0.0f);
			recognitionDto.setMinimumRequestedConfidence(requestDto.getMinimumConfidence());
			recognitionDto.setPlusConfidence(0.0f);
		}

		return recognitionDto;
	}

	private List<FaceDetail> getFaceDetails(String base64Image) {
		String base64ImageBody = ImageUtil.getBase64ImageBody(base64Image);
		byte[] arrayByteImageBody = Base64.getDecoder().decode(base64ImageBody);
		ByteBuffer imageBytes = ByteBuffer.wrap(arrayByteImageBody);
		DetectFacesRequest detectFacesRequest = new DetectFacesRequest().withImage(new Image().withBytes(imageBytes))
				.withAttributes(Attribute.ALL);

		DetectFacesResult result = awsRekognition.detectFaces(detectFacesRequest);
		return result.getFaceDetails();
	}

	private void checkRequestMandatoryAttributes(FaceRecognitionRequestDto requestDto) {
		if (requestDto.getBase64Image() == null || !ImageUtil.isBase64ImageStructureOk(requestDto.getBase64Image())
				|| requestDto.getMinimumConfidence() == null || requestDto.getMinimumConfidence() < 0
				|| requestDto.getMinimumConfidence() > 100)
			throw new IllegalArgumentException(
					"Check request attributes, one or more mandatory attributes are illegal.");
	}
}
