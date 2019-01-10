package org.cmsoft.fra.test;

import java.io.File;

public final class UtilTest {

	private UtilTest() {
		//
	}

	public static File getFilesFromResource(String filePath) {
		ClassLoader classLoader = UtilTest.class.getClassLoader();
		return new File(classLoader.getResource(filePath).getFile());
	}
}
