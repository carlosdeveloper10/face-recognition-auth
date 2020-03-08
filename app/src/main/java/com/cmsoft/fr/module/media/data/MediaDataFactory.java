package com.cmsoft.fr.module.media.data;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public final class MediaDataFactory {

	public MediaDataSource create(TypeMediaData type) {
		if (type == TypeMediaData.AWS_S3_BASIC_BUCKET) {
			return new AwsS3DataSource(AmazonS3ClientBuilder.defaultClient());
		}

		return null;
	}

}
