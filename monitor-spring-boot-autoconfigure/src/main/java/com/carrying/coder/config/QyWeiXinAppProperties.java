package com.carrying.coder.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Liu Hailin
 * @create 2017-12-13 下午12:31
 **/
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "qy.weixin")
public class QyWeiXinAppProperties implements InitializingBean{

    private String corpID;

    private int agentId;

    private String secret;

    private String touser;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info( "######Read config from properties...." );
    }
}
