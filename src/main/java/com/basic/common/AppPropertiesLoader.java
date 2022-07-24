package com.basic.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.basic.common.cnst.Const;

/**
 *
 * @ClassName   : PropertiesUtils.java
 * @Description : PropertiesUtils Class
 * @Modification Information
 * @
 * @ 수정일                       수정자                      수정내용
 * @ -----------   -----------   -------------------------------
 * @ 2020. 7. 10.  cyk       2020. 7. 10. 최초생성
 *
 * @author cyk
 * @since 2020. 7. 10.
 * @version 1.0
 * @see
 *
 *  Copyright (C) by cyk All right reserved.
 */
@Configuration
public class AppPropertiesLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppPropertiesLoader.class);

    private Properties prop;

    public AppPropertiesLoader() {
        prop = new Properties();
        try{
            String profile = System.getProperty("spring.profiles.active");
            LOGGER.info("[LOAD] app properties profile : {}", profile);
            if(profile == null) {
                profile = Const.ENV_DEV;
            }
            String path = "/" + profile + "/app.properties";
            ClassPathResource resource = new ClassPathResource(path);
            InputStream inputStream = resource.getInputStream();
            prop.load(inputStream);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getValue(String key){
        return prop.getProperty(key);
    }

}
