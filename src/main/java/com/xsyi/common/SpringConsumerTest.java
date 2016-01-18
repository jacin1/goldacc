package com.xsyi.common;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author 易晓双
 */
public class SpringConsumerTest {

    private final Logger log= LoggerFactory.getLogger(SpringConsumerTest.class);

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void receive(){
        while(true){
            //接收消息
            TextMessage txtMsg= (TextMessage) jmsTemplate.receive();
            if(null != txtMsg){
                try {
                    log.info("收到的消息为:"+txtMsg.getText()+"");
                } catch (JMSException e) {

                }

            }

        }
    }

}
