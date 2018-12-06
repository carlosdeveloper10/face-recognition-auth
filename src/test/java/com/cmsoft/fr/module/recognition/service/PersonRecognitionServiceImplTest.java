package com.cmsoft.fr.module.recognition.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.Request;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.cmsoft.fr.module.base.service.DtoFactory;
import com.cmsoft.fr.module.base.service.DtoFactoryImpl;
import com.cmsoft.fr.module.recognition.image.ImageUtil;

@RunWith(MockitoJUnitRunner.class)
public class PersonRecognitionServiceImplTest {

	@Mock
	AmazonRekognition awsRekognitionMock;

	DtoFactory dtoFactory;

	PersonRecognitionServiceImpl recognitionService;

	// ----- TDD for RecognitionDto recognize(FaceRecognitionRequestDto dto)
	@Before
	public void setup() {
		dtoFactory = new DtoFactoryImpl();
		recognitionService = new PersonRecognitionServiceImpl(awsRekognitionMock, dtoFactory);
	}

	@Test
	public void whenBase64ImageIsNullThenThrowNullPointerException() {
		FaceRecognitionRequestDto recognitionRequestDto = null;
		assertThatThrownBy(() -> {
			recognitionService.recognize(recognitionRequestDto);
		}).isExactlyInstanceOf(NullPointerException.class).hasMessage("The requestDto is null.");
	}

	@Test
	public void whenSomeAttributeRequestDtoAreMissingThenThrowIlegalArgumentException() {
		FaceRecognitionRequestDto recognitionRequestDto = new FaceRecognitionRequestDto();

		// error for null image
		assertThatThrownBy(() -> {
			recognitionService.recognize(recognitionRequestDto);
		}).isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Check request attributes, one or more mandatory attributes are illegal.");

		// error by bad image strcutre
		recognitionRequestDto.setBase64Image("BAD_BASE_64_IMAGE");
		assertThatThrownBy(() -> {
			recognitionService.recognize(recognitionRequestDto);
		}).isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Check request attributes, one or more mandatory attributes are illegal.");

		// confidence error---

		recognitionRequestDto.setBase64Image("data:image/png;base64,GOOD_BASE_64_IMAGE");
		// By null
		assertThatThrownBy(() -> {
			recognitionService.recognize(recognitionRequestDto);
		}).isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Check request attributes, one or more mandatory attributes are illegal.");

		// By less than zero
		recognitionRequestDto.setMinimumConfidence(-0.1F);
		assertThatThrownBy(() -> {
			recognitionService.recognize(recognitionRequestDto);
		}).isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Check request attributes, one or more mandatory attributes are illegal.");

		// By greater than 100
		recognitionRequestDto.setMinimumConfidence(100.01F);
		assertThatThrownBy(() -> {
			recognitionService.recognize(recognitionRequestDto);
		}).isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Check request attributes, one or more mandatory attributes are illegal.");
	}

	@Test
	public void ifBase64ImageHasZeroFacesThenReturnFalseDto() throws FileNotFoundException {

		// Arrange
		FaceRecognitionRequestDto requestDto = new FaceRecognitionRequestDto();
		requestDto.setBase64Image(base64ImageForTest((byte) 1));
		requestDto.setMinimumConfidence(99.55f);

		byte[] decodedString = Base64.getDecoder().decode(base64ImageForTest((byte) 2));
		ByteBuffer imageBytes = ByteBuffer.wrap(decodedString);
		DetectFacesRequest detectFacesRequest = new DetectFacesRequest().withImage(new Image().withBytes(imageBytes))
				.withAttributes(Attribute.ALL);

		List<FaceDetail> faceDetails = new ArrayList<>();
		DetectFacesResult rekognitionResult = new DetectFacesResult();
		rekognitionResult.setFaceDetails(faceDetails);

		when(awsRekognitionMock.detectFaces(detectFacesRequest)).thenReturn(rekognitionResult);

		RecognitionDto expectedDto = new RecognitionDto();
		expectedDto.setMatchedPerson(null);
		expectedDto.setRecognition(PersonMatchingStatus.ZERO_FACES_ON_INBOUND_IMAGE);
		expectedDto.setConfidence(0.0f);
		expectedDto.setMinimumRequestedConfidence(requestDto.getMinimumConfidence());
		expectedDto.setPlusConfidence(0.0f);

		// Act
		RecognitionDto returnedDto = recognitionService.recognize(requestDto);

		// Assert
		assertThat(returnedDto).isEqualToComparingFieldByFieldRecursively(expectedDto);
	}

	@Test
	public void ifBase64ImageHasManyFacesThenReturnFalseDto() throws FileNotFoundException {

		// Arrange
		FaceRecognitionRequestDto requestDto = new FaceRecognitionRequestDto();
		requestDto.setBase64Image(base64ImageForTest((byte) 1));
		requestDto.setMinimumConfidence(99.55f);

		byte[] decodedString = Base64.getDecoder().decode(base64ImageForTest((byte) 2));
		ByteBuffer imageBytes = ByteBuffer.wrap(decodedString);
		DetectFacesRequest detectFacesRequest = new DetectFacesRequest().withImage(new Image().withBytes(imageBytes))
				.withAttributes(Attribute.ALL);

		List<FaceDetail> faceDetails = new ArrayList<>();
		faceDetails.add(new FaceDetail());
		faceDetails.add(new FaceDetail());
		DetectFacesResult rekognitionResult = new DetectFacesResult();
		rekognitionResult.setFaceDetails(faceDetails);

		when(awsRekognitionMock.detectFaces(detectFacesRequest)).thenReturn(rekognitionResult);

		RecognitionDto expectedDto = new RecognitionDto();
		expectedDto.setMatchedPerson(null);
		expectedDto.setRecognition(PersonMatchingStatus.MANY_FACES_ON_INBOUND_IMAGE);
		expectedDto.setConfidence(0.0f);
		expectedDto.setMinimumRequestedConfidence(requestDto.getMinimumConfidence());
		expectedDto.setPlusConfidence(0.0f);

		// Act
		RecognitionDto returnedDto = recognitionService.recognize(requestDto);

		// Assert
		assertThat(returnedDto).isEqualToComparingFieldByFieldRecursively(expectedDto);

	}

	public String base64ImageForTest(byte whichOne) throws FileNotFoundException {

		//The base6a image come from files to avoid ERROR COMPILATION too long string 
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("image-for-test/base64-image-zero-faces.txt").getFile());
		Scanner fileIn = new Scanner(file);
		String base6aImage = "";
		
	
		if (whichOne == 1 && fileIn.hasNextLine()) {
			base6aImage = fileIn.nextLine();
		} else if (whichOne == 2 && fileIn.hasNextLine()) {
			base6aImage = ImageUtil.getBase64ImageBody(fileIn.nextLine());
		}
		return base6aImage;
	}
}
