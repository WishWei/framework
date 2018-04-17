package com.wish.service.mq;

import com.wish.model.vo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wish on 2018/2/27.
 */
public class MessageConsumer implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    private Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    @Override
    public void onMessage(Message message) {
        Employee employee = (Employee) jackson2JsonMessageConverter.fromMessage(message);
        logger.info("received message:{}" , employee.getFirstName());
    }
}
