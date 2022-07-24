package com.basic.api.board.service;

import org.json.simple.JSONObject;

import com.basic.api.board.vo.BPA2002VO;
import com.basic.api.board.vo.BPA2003VO;
import com.basic.api.board.vo.BPA2004VO;

public interface BoardService {

	JSONObject getBoardList(BPA2002VO bpa2002vo);

	JSONObject getBoardDetail(BPA2003VO bpa2003vo);

	JSONObject setBoard(BPA2004VO bpa2004vo);

}
