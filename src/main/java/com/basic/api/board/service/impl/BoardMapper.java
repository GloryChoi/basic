package com.basic.api.board.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import com.basic.common.model.ParamMap;


@Mapper("boardMapper")
public interface BoardMapper {

	List<EgovMap> selectRpTbBoardList(ParamMap paramMap);

	EgovMap selectRpTbBoardDetail(ParamMap paramMap);

	void insertRpTbBoard(ParamMap paramMap);

}
