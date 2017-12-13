package com.carrying.coder.autoconfiguration;

import java.util.Map;

import com.carrying.coder.config.QyWeiXinAppProperties;
import com.carrying.coder.message.IMessageSender;
import com.carrying.coder.message.Message;
import com.carrying.coder.message.TxtContent;
import com.carrying.coder.message.TxtMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author Liu Hailin
 * @create 2017-12-13 下午1:34
 **/
@Configuration
@AutoConfigureAfter(TokenHolder.class)
@EnableConfigurationProperties(QyWeiXinAppProperties.class)
@Slf4j
public class MessageSenderAutoConfiguration {

    ObjectMapper objectMapper = new ObjectMapper(  );

    @Autowired
    private QyWeiXinAppProperties properties;

    @Autowired
    private TokenHolder tokenHolder;

    @Bean
    public IMessageSender createMsgSender(RestTemplate restTemplate) {

        return new IMessageSender() {

            @Override
            public boolean send(Message msg) {

                return false;
            }

            @Override
            public boolean send(TxtMessage txtMessage) {

                TxtContent content = new TxtContent( txtMessage.getTxtMsg() );
                content.setAgentid( properties.getAgentId() );

                if(StringUtils.isEmpty( properties.getTouser() )){
                    content.setTouser( "@all" );
                }else{
                    content.setTouser( properties.getTouser() );
                }

                content.setMsgtype( "text" );
                content.setSafe( 0 );

                try {
                    log.info( "#####send message content{}",objectMapper.writeValueAsString( content ) );
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                HttpEntity<TxtContent> entity = new HttpEntity<>( content );
                ResponseEntity<Map> result = restTemplate.postForEntity(
                    "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={access_token}", entity,
                    Map.class, tokenHolder.getToken() );

                log.info( "#####send message Result{}",result );

                return true;
            }
        };
    }

}
