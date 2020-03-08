package com.cmsoft.fr.module.integration.notification;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

public class SmsNotificator implements Notificator {

	private AmazonSNS snsClient;

	public SmsNotificator(AmazonSNS snsClient) {
		this.snsClient = snsClient;
	}

	@Override
	public void notify(String destination, String message) {
		snsClient.publish(new PublishRequest().withMessage(message).withPhoneNumber(destination));
	}

}
