package com.cmsoft.fr.module.recognition.service;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.cmsoft.fr.module.base.service.DtoFactory;
import com.cmsoft.fr.module.integration.notification.Notificator;
import com.cmsoft.fr.module.integration.notification.NotificatorFactory;
import com.cmsoft.fr.module.integration.notification.TypeNotificator;
import com.cmsoft.fr.module.person.data.dao.PersonDao;
import com.cmsoft.fr.module.person.data.entity.PersonEntity;
import com.cmsoft.fr.module.person.service.PersonDto;
import com.cmsoft.fr.module.recognition.image.ImageUtil;

@Service
public class PersonRecognitionServiceImpl implements RecognitionService {

	private AmazonRekognition awsRekognition;

	private DtoFactory dtoFactory;

	private PersonDao personDao;

	@Autowired
	public PersonRecognitionServiceImpl(AmazonRekognition awsRekognition, PersonDao personDao, DtoFactory dtoFactory) {
		this.awsRekognition = awsRekognition;
		this.dtoFactory = dtoFactory;
		this.personDao = personDao;
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

			return recognitionDto;
		} else if (faceDetails.size() > 1) {
			recognitionDto.setMatchedPerson(null);
			recognitionDto.setRecognition(PersonMatchingStatus.MANY_FACES_ON_INBOUND_IMAGE);
			recognitionDto.setConfidence(0.0f);
			recognitionDto.setMinimumRequestedConfidence(requestDto.getMinimumConfidence());
			recognitionDto.setPlusConfidence(0.0f);

			return recognitionDto;
		}

		return searchFaceByFace(requestDto);
	}

	private RecognitionDto searchFaceByFace(FaceRecognitionRequestDto requestDto) {
		List<PersonEntity> people = (List<PersonEntity>) personDao.findAll();

		// Config target photo
		String targetBase64photo = ImageUtil.getBase64ImageBody(requestDto.getBase64Image());
		byte[] decodeByteTarget = Base64.getDecoder().decode(targetBase64photo);
		ByteBuffer targetImageBytes = ByteBuffer.wrap(decodeByteTarget);
		Image target = new Image().withBytes(targetImageBytes);

		String sourceBase64photo;
		byte[] decodedByteSource;
		ByteBuffer sourceImageBytes;
		Image source;

		RecognitionDto recognitionDto = (RecognitionDto) dtoFactory.create("RECOGNITION");
		recognitionDto.setMatchedPerson(null);
		recognitionDto.setRecognition(PersonMatchingStatus.FACIAL_FEATURES_NOT_FOUND);
		recognitionDto.setConfidence(0.0f);
		recognitionDto.setMinimumRequestedConfidence(requestDto.getMinimumConfidence());
		recognitionDto.setPlusConfidence(0.0f);

		for (PersonEntity person : people) {

			CompareFacesRequest compareRequest = new CompareFacesRequest()
					.withSourceImage(new Image()
							.withS3Object(new S3Object().withName(person.getPhotoName()).withBucket("fra-photos")))
					.withTargetImage(target).withSimilarityThreshold(requestDto.getMinimumConfidence());

			CompareFacesResult compareFacesResult = awsRekognition.compareFaces(compareRequest);
			List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();

			// First one found by matching, first retrieved.
			if (!faceDetails.isEmpty()) {
				CompareFacesMatch matchedFace = faceDetails.get(0);

				recognitionDto.setMatchedPerson((PersonDto) dtoFactory.create(person));
				recognitionDto.setRecognition(PersonMatchingStatus.FACIAL_FEATURES_FOUND);
				recognitionDto.setConfidence(matchedFace.getSimilarity());
				recognitionDto.setMinimumRequestedConfidence(requestDto.getMinimumConfidence());
				recognitionDto.setPlusConfidence(matchedFace.getSimilarity() - requestDto.getMinimumConfidence());

				this.notifyLogginOn(person);
				return recognitionDto;
			}
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
	
	private void notifyLogginOn(PersonEntity foundPerson) {

		NotificatorFactory notifcatorFactory = new NotificatorFactory();
		Notificator notificator = notifcatorFactory.create(TypeNotificator.AWS_SNS_SMS);
		
		try {
		notificator.notify(foundPerson.getPhoneNumber(),
				"Hi " + foundPerson.getUsername() + ", you have logged on by face-recognition on -piece of cake family-.");
		}catch (Exception e) {
			// TODO: handle exception
		}

	}
}
