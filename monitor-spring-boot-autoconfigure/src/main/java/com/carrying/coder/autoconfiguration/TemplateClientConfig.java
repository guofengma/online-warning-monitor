package com.carrying.coder.autoconfiguration;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Liu Hailin
 * @create 2017-11-30 下午5:18
 **/
@Configuration
public class TemplateClientConfig {


    @Bean
    public RestTemplate createRestTemplate()
        throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = SSLContexts.custom()
            .loadTrustMaterial( null, acceptingTrustStrategy )
            .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory( sslContext );

        CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLSocketFactory( csf )
            .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient( httpClient );
        RestTemplate restTemplate = new RestTemplate( requestFactory );
        return restTemplate;
    }
}
