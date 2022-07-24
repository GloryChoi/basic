package com.basic.common.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 *
 * @ClassName   : RestUtils.java
 * @Description : RestUtils Class
 * @Modification Information
 * @
 * @ 수정일                       수정자                      수정내용
 * @ -----------   -----------   -------------------------------
 * @ 2020. 7. 16.  cyk       2020. 7. 16. 최초생성
 *
 * @author cyk
 * @since 2020. 7. 16.
 * @version 1.0
 * @see
 *
 *  Copyright (C) by cyk All right reserved.
 */

public class RestUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestUtils.class);

    private static String URL;

    public RestUtils(String hostUrl) {
        URL = hostUrl;
    }

    static String urlEncodeUTF8(Map<String, Object> map) {

        if(map == null) return null;

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (sb.length() == 0) {
                sb.append("?");
            }else if(sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                entry.getKey().toString(),
                entry.getValue().toString()
            ));
        }
        return sb.toString();
    }

    public ResponseEntity<?> requestFORM(RestTemplate restTemplate, String request, Map<String, String> headers, Map<String, Object> parameters, final HttpMethod method) throws Exception {
        final String reqUrl = URL.concat(request);
        requestHandler(reqUrl, parameters);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<String>(urlEncodeUTF8(parameters), httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(reqUrl, method, entity, String.class);

        return responeHandler(response);
    }

    public ResponseEntity<?> requestMAP(RestTemplate restTemplate, String request, Map<String, String> headers, MultiValueMap<String, String> parameters, final HttpMethod method) throws Exception {
        String reqUrl = URL.concat(request);
        //requestHandler(reqUrl, parameters);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = null;
        if(HttpMethod.POST == method) {
        	entity = new HttpEntity<>(parameters, httpHeaders);
        } else {
        	entity = new HttpEntity<>(httpHeaders);
        	reqUrl = UriComponentsBuilder.fromHttpUrl(reqUrl).queryParams(parameters).toUriString();
        }

        ResponseEntity<String> response = restTemplate.exchange(reqUrl, method, entity, String.class);

        return responeHandler(response);
    }

    public ResponseEntity<?> requestJSON(RestTemplate restTemplate, String request, Map<String, String> headers, Map<String, Object> parameters, final HttpMethod method) throws Exception {
        final String reqUrl = URL.concat(request);
        requestHandler(reqUrl, parameters);

        Gson gson = new GsonBuilder().create();
        String param = gson.toJson(parameters);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(param, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(reqUrl, method, entity, String.class);

        return responeHandler(response);
    }

    private void requestHandler(String reqUrl, Map<String, Object> parameters) {
        LOGGER.info("request reqUrl : {}", reqUrl);
        LOGGER.info("request parameters : {}", urlEncodeUTF8(parameters));
    }

    private ResponseEntity<?> responeHandler(ResponseEntity<?> response) {

        LOGGER.info(StringUtils.nvl(response.getBody()));

//        switch (response.getStatusCode()) {
//        case OK :
//            break;
//        case UNAUTHORIZED :
//            throw new HttpClientErrorException(response.getStatusCode(),"");
//        case NOT_ACCEPTABLE :
//            throw new HttpClientErrorException(response.getStatusCode(),"");
//        default:
//            throw new HttpClientErrorException(response.getStatusCode(),"");
//        }

        return response;
    }
}
