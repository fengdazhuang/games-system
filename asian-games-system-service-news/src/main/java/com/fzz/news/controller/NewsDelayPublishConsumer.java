package com.fzz.news.controller;

import com.fzz.api.config.RabbitmqDelayConfig;
import com.fzz.news.service.NewsService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NewsDelayPublishConsumer {

    @Autowired
    private NewsService newsService;

    @RabbitListener(queues = {RabbitmqDelayConfig.QUEUE_DELAY})
    public void watchArticlePublishDelay(Message message) throws Exception {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();

        if(routingKey.equalsIgnoreCase("delay.publish.news")){
            String newsIdStr = new String(message.getBody());
            Long newsId = Long.valueOf(newsIdStr);
            System.out.println("时间："+new Date()+"--文章发布");
            newsService.updateDelayedArticle(newsId);
        }

    }
}
