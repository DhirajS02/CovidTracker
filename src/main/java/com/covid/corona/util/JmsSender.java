package com.covid.corona.util;

import java.io.Serializable;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.covid.corona.exception.JmsSenderException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class JmsSender implements ApplicationContextAware {
	private JmsTemplate jmsTemplate;
	private int retryDelayInSeconds;
	private String connFactoryBeanName;
	private ApplicationContext applicationContext;

	// private static final int LOOP_COUNT = 5;
	public void sendToQueue(Serializable msg) throws JmsSenderException {
		doInitialSend(msg);
	}

	private void doInitialSend(final Serializable objectMsg) throws JmsSenderException {
		MessageCreator mc = new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage(objectMsg);
				return message;
			}
		};
		sendMessage(mc);
	}

	private void sendMessage(MessageCreator mc) {
		try {
			this.jmsTemplate.send(mc);
		} catch (Throwable t) {
			for (int i = 0; i < 5; i++) {
				try {
					this.jmsTemplate.setConnectionFactory(
							(ConnectionFactory) this.applicationContext.getBean(this.connFactoryBeanName));
					this.jmsTemplate.send(mc);
					return;
				} catch (Throwable t2) {
				}
			}

			log.error("Message is not sent due to fail to get connection factory.", t);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
