package com.basic.api.common.web;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.basic.api.common.service.ApiCommonService;
import com.basic.api.common.vo.BPA2001VO;
import com.basic.common.AppPropertiesLoader;
import com.basic.common.utils.MessageUtils;

/**
 * @author cyk
 * @description 공통
 */
@Controller
public class ApiCommonController {

    @Autowired
    MessageUtils messageUtils;

    @Autowired
    AppPropertiesLoader appPropertiesLoader;

    @Autowired
    ApiCommonService apiCommonService;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
	 * @param bpa2001vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 코드조회
	 */
	@RequestMapping(value = "/api/cm/getCodeData")
    @ResponseBody
    public JSONObject getCodeData(@RequestBody @Valid BPA2001VO bpa2001vo, BindingResult bindingResult) {
		// TODO getCodeData
        JSONObject result= new JSONObject();

        result = apiCommonService.getCodeData(bpa2001vo);

        return result;
    }
}
