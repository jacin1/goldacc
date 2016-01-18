package com.test.activemq;

import com.xsyi.common.SpringConsumerTest;
import com.xsyi.common.SpringProducerTest;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * @author 易晓双
 */
public class JmsActiveMqTest {

    public static void main(String args[]){

        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring.xml");

        SpringProducerTest producerTest= (SpringProducerTest) applicationContext.getBean("springProducer");
        producerTest.send("1111");

        SpringConsumerTest consumer = (SpringConsumerTest) applicationContext.getBean("springConsumer");
        consumer.receive();
    }

}
