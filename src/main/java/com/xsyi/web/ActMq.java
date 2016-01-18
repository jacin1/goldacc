package com.xsyi.web;

import com.xsyi.common.SpringProducerTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 易晓双
 * activemq 测试请求入口
 */
@Controller
public class ActMq {

    @Autowired
    private SpringProducerTest springProducer;

    private final Logger log= LoggerFactory.getLogger(ActMq.class);

    /**
     * 推送消息:deom1-简单测试消息推送
     * @param request
     * @param response
     */
    @RequestMapping("/sendMq")
    public void sendMq(HttpServletRequest request,HttpServletResponse response){
        log.info("start send");

        String content=request.getParameter("content") == null  ? "11撒旦法" : request.getParameter("content");
        log.info("content="+content);
        springProducer.send(content);

    }

}
