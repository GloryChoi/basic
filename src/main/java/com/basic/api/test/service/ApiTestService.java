package com.basic.api.test.service;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import com.basic.common.model.ParamMap;



public interface ApiTestService {

	List<EgovMap> selectList(ParamMap paramMap);
}
