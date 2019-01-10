package com.cmsoft.fr.module.recognition.image;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

public class ImageUtilTest {

	// ------- TDD for isBase64StructureOk(String imageBase64)
	@Test
	public void ifImageHasIncorrectOrUnknownOrNullFormatThenReturnFalse() {
		assertThat(ImageUtil.isBase64ImageStructureOk("data:image/bimp;base64,iVBORw0KGgoAAAANSUhEUgAAAHkAAAB5C")).isFalse();
		assertThat(ImageUtil.isBase64ImageStructureOk("data:image/mp4;base64,iVBORw0KGgoAAAANSUhEUgAAAHkAAAB5C")).isFalse();
		assertThat(ImageUtil.isBase64ImageStructureOk(null)).isFalse();
	}
	
	@Test
	public void ifImageHasCorrectFormatThenReturnTrue() {
		assertThat(ImageUtil.isBase64ImageStructureOk("data:image/png;base64,retrewtrewtrewtretrewtretret")).isTrue();
		assertThat(ImageUtil.isBase64ImageStructureOk("data:image/jpg;base64,rewtretgretretretre")).isTrue();
		assertThat(ImageUtil.isBase64ImageStructureOk("data:image/jpeg;base64,iVBORw0KGgoAAAANSUhEUgAAAHkAAAB5C")).isTrue();
	}

	// ------- TDD for getBase64ImageExtension(String imageBase64)
	@Test
	public void whenImageIsNullThenThrownNullPointerException_1() {
		assertThatThrownBy(() -> {
			ImageUtil.getBase64ImageExtension(null);
		}).isExactlyInstanceOf(NullPointerException.class).hasMessage("The base64 image is null.");
	}

	@Test
	public void whenBase64ImageDoesNotHaveExtensionReturnNullIlegalArgumentException() {
		assertThatThrownBy(() -> {
			ImageUtil.getBase64ImageExtension("data:video/mp4;base64,hdkasjdfajgdfkugewuirakjdafgkfdgjdf");
		}).isExactlyInstanceOf(IllegalArgumentException.class).hasMessage("The argument is not a image");
	}

	@Test
	public void whenBase64ImageHasExtensionThenReturnTheExtension() {
		String expectedExtension[] = { "png", "jpg", "jpeg" };

		String jpeg = ImageUtil.getBase64ImageExtension("data:image/jpeg;base64,hdkasjdfajgdfkugewuirakjdafgkfdgjdf");
		String png = ImageUtil.getBase64ImageExtension("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHkAAAB5C");
		String jpg = ImageUtil.getBase64ImageExtension("data:image/jpg;base64,TESTINADO;ESTA;MONDA");

		assertThat(png).isEqualTo(expectedExtension[0]);
		assertThat(jpg).isEqualTo(expectedExtension[1]);
		assertThat(jpeg).isEqualTo(expectedExtension[2]);
	}
	
	// ------ TDD for getBase64ImageBody(String imageBase64)
	@Test
	public void whenImageIsNullThenThrownNullPointerException_2() {
		assertThatThrownBy(() -> {
			ImageUtil.getBase64ImageBody(null);
		}).isExactlyInstanceOf(NullPointerException.class).hasMessage("The base64 image is null.");
	}
	
	@Test
	public void whenBase64ImageDoesNotHaveCorrectStructureThrowIllegalArguemtException() {
		assertThatThrownBy(() -> {
			ImageUtil.getBase64ImageBody("data:video/mp4;base64,HAS_BAD_STURCTURE");
		}).isExactlyInstanceOf(IllegalArgumentException.class).hasMessage("The argument is not a image");
	}
	
	@Test
	public void whenBase64ImageDoesHavsCorrectStructureThrowIllegalArguemtException() {
		String base64ImageBody = ImageUtil.getBase64ImageBody("data:image/jpeg;base64,BASE64_IMAGE_STRUTURE_IS_OK_AND_THIS_IS_THE_BODY");
		assertThat(base64ImageBody).isEqualTo("BASE64_IMAGE_STRUTURE_IS_OK_AND_THIS_IS_THE_BODY");
	}

}
