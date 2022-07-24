package com.basic.api.test.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import com.basic.common.model.ParamMap;


@Mapper("ApiTestMapper")
public interface ApiTestMapper {

	List<EgovMap> selectList(ParamMap paramMap);

}
