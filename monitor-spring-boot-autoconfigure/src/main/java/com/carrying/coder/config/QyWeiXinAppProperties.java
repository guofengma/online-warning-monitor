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

    public static String PROTOCOL = "https";

    public static String HOST="qyapi.weixin.qq.com";

    public static String SEND_PATH = "/cgi-bin/message/send?access_token={access_token}";
    public static String TOKEN_PATH = "/cgi-bin/gettoken?corpid={corpID}&corpsecret={SECRECT}";

    private String corpID="ww9e2138f2d49466af";

    private int agentId=1000026;

    private String secret="r28SEqj_dsrRbwF_jSx2FEkhnLVUSs8kpPY1UB1OFZU";

    private String touser;

    private String proxy;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info( "######Read config from properties...." );
    }
}
