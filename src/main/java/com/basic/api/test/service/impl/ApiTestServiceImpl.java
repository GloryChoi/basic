package com.basic.api.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basic.api.test.service.ApiTestService;
import com.basic.common.AppPropertiesLoader;
import com.basic.common.model.ParamMap;
import com.basic.common.utils.MessageUtils;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Service
public class ApiTestServiceImpl implements ApiTestService{

	@Resource(name="ApiTestMapper")
	ApiTestMapper apiTestMapper;

	@Autowired
    AppPropertiesLoader appPropertiesLoader;

    @Autowired
    MessageUtils messageUtils;

    protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<EgovMap> selectList(ParamMap paramMap) {
		// TODO
		List<EgovMap> list = apiTestMapper.selectList(paramMap);

		return list;
	}

}
