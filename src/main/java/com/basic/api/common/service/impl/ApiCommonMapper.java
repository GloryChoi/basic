package com.basic.api.common.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import com.basic.common.model.ParamMap;


@Mapper("ApiCommonMapper")
public interface ApiCommonMapper {

	List<EgovMap> selectRpTbCodeDataList(ParamMap paramMap);

}
