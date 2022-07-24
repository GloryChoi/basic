package com.basic.common.rest;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.basic.common.utils.StringUtils;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : RestTemplate.java
 * @Description : 클래스 설명을 기술합니다.
 * @author BPOSFT
 * @since 2020. 12. 04.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 12. 04.     cyk         최초 생성
 * </pre>
 */

@Component
public class RestTemplateHttp {
	protected Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public RestTemplate restTemplate() throws Exception{
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(60000); //읽기시간 초과
        factory.setConnectTimeout(60000); //연결시간 초과

        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100) //connection pool
                .setMaxConnPerRoute(5)
                .build();

        factory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        return restTemplate;
    }

    @Bean
    public RestTemplate getHttpsTemplate() throws Exception{
        HttpComponentsClientHttpRequestFactory httpClientFactory = new HttpComponentsClientHttpRequestFactory();
        httpClientFactory.setReadTimeout(60000); //읽기시간 초과
        httpClientFactory.setConnectTimeout(60000); //연결시간 초과
        RestTemplate restTemplate = null;

        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
                @Override
                public boolean verify(String hostname,SSLSession session) {
                    return true;
                }
            });
            httpClientFactory.setHttpClient(HttpClients.custom()
                    .setMaxConnTotal(100)
                    .setMaxConnPerRoute(5)
                    .setSSLSocketFactory(sslsf).build());

        } catch (Exception e) {
            logger.error("RestTemplate getHttpsTemplate() Exception : {}", e.getMessage());
            logger.error("StackTrace : {}", StringUtils.getError(e));
        }

        restTemplate = new RestTemplate(httpClientFactory);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        return restTemplate;
    }
}
