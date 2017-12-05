package com.activemq.ve.consumer;

import java.io.IOException;

import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.activemq.ve.config.ActiveMQConfig;
import com.activemq.ve.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OrderConsumer {

	private static Logger log = LoggerFactory.getLogger(OrderConsumer.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	@JmsListener(destination = ActiveMQConfig.BATCH2VE)
	public void receiveMessage(@Payload String order, @Headers MessageHeaders headers, Message message, Session session)
			throws JsonProcessingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		Order readValue = mapper.readerFor(Order.class).readValue(order);
		readValue.setStatus("RECEIVED");

		log.info("received <" + readValue + ">");

		log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
		log.info("###### Message Details #####");
		log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
		log.info("headers: " + headers);
		log.info("message: " + message);
		log.info("session: " + session);
		log.info("- - - - - - - - - - - - - - - - - - - - - - - -");

		readValue.setStatus("SENT BACK");
		jmsTemplate.convertAndSend(ActiveMQConfig.VE2BATCH, mapper.writeValueAsString(readValue));
	}
}
