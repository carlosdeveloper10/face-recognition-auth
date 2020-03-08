package com.cmsoft.fr.module.media.data;

import java.util.stream.Stream;

public interface MediaDataSource {

	void save(String base64object, String objectName,  String destination);
}
