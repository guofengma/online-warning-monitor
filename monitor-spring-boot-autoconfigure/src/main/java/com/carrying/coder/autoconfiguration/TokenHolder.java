package com.carrying.coder.autoconfiguration;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.carrying.coder.config.QyWeiXinAppProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author Liu Hailin
 * @create 2017-12-13 下午2:04
 **/
@Configuration
@ConditionalOnProperty(name = "qy.weixin.enable",havingValue = "true")
@EnableConfigurationProperties(QyWeiXinAppProperties.class)
@Slf4j
public class TokenHolder implements InitializingBean {

    @Autowired
    private QyWeiXinAppProperties properties;

    @Autowired
    private RestTemplate template;

    @Getter
    private volatile String token;

    private AtomicInteger expire = new AtomicInteger( 0 );

    private volatile boolean expired = false;

    private Object monitor = new Object();

    private ExecutorService tokenThreadPool = Executors.newFixedThreadPool( 2 );



    @Override
    public void afterPropertiesSet() throws Exception {

        tokenThreadPool.execute( new FatchTokenAndRefresh( template ) );
        tokenThreadPool.execute( new MonitorExpried() );

    }

    class MonitorExpried implements Runnable {

        @Override
        public void run() {

            while (true) {
                synchronized (monitor) {

                    while (StringUtils.isEmpty( token ) || expired) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    while (expire.getAndDecrement() > 0) {
                        try {
                            Thread.sleep( 1000 );
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    expired = true;
                    log.warn( "########token is Expired!" );
                    monitor.notifyAll();
                }
            }

        }
    }

    class FatchTokenAndRefresh implements Runnable {

        private RestTemplate template;

        public FatchTokenAndRefresh(RestTemplate template) {
            this.template = template;
        }

        @Override
        public void run() {

            while (true) {
                synchronized (monitor) {
                    while (!StringUtils.isEmpty( token ) && !expired) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    Map<String, Object> result = template.getForObject(
                        "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={corpID}&corpsecret={SECRECT}",
                        Map.class, properties.getCorpID(), properties.getSecret() );
                    log.warn( "########token Result:{}", result );
                    if (result != null) {
                        token = result.get( "access_token" ).toString();
                        expire.addAndGet( Integer.parseInt( result.get( "expires_in" ).toString() ) );
                        expired = false;
                        monitor.notifyAll();
                    }

                }
            }

        }
    }

}
