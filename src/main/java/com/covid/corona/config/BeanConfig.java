package com.covid.corona.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;

import com.covid.corona.util.JmsSender;


@Configuration
public class BeanConfig {
	@Value("${com.covid.corona.brokerurl}")
	private String brokerUrl;
	@Value("${com.covid.corona.queueName}")
	private String queueName;
	@Value("${com.covid.corona.retryDelayInSeconds}")
	private int retryDelayInSeconds;
	
	@Bean
	public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }
	@Bean("jmsSender")
	public JmsSender jmsSender() {
		JmsSender jmsSender = new JmsSender();
		jmsSender.setConnFactoryBeanName("activeMQconnectionFactory");
		jmsSender.setRetryDelayInSeconds(retryDelayInSeconds);
		jmsSender.setJmsTemplate(jmsTemplate());
		return jmsSender;
	}
	@Bean("activeMQconnectionFactory")
	public ActiveMQConnectionFactory activeMQconnectionFactory() {
		ActiveMQConnectionFactory amq = new ActiveMQConnectionFactory();
		amq.setBrokerURL(brokerUrl);
		amq.setTrustAllPackages(true);
		return amq;
	}
	@Bean("jmsTemplate")
	public JmsTemplate jmsTemplate() {
		JmsTemplate jms = new JmsTemplate();
		jms.setConnectionFactory(activeMQconnectionFactory());
		jms.setDefaultDestinationName(queueName);
		return jms;
	}

}
