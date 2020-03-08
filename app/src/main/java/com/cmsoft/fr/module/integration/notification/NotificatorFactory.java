package com.cmsoft.fr.module.integration.notification;

import com.amazonaws.services.sns.AmazonSNSClientBuilder;

public final class NotificatorFactory {

	public Notificator create(TypeNotificator type) {
		if (type == TypeNotificator.AWS_SNS_SMS) {
			return new SmsNotificator(AmazonSNSClientBuilder.defaultClient());
		}

		return null;
	}

}
