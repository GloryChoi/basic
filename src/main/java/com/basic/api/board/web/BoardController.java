package com.basic.api.board.web;

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

import com.basic.api.board.service.BoardService;
import com.basic.api.board.vo.BPA2002VO;
import com.basic.api.board.vo.BPA2003VO;
import com.basic.api.board.vo.BPA2004VO;
import com.basic.common.AppPropertiesLoader;
import com.basic.common.utils.MessageUtils;

/**
 * @author CYK
 * @description 공통 API
 */
@Controller
public class BoardController {

    @Autowired
    MessageUtils messageUtils;

    @Autowired
    AppPropertiesLoader appPropertiesLoader;

    @Autowired
    BoardService boardService;

    protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @param bpa2002vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 게시물 목록 조회
	 */
	@RequestMapping(value = "/api/cm/getBoardList")
    @ResponseBody
    public JSONObject getBoardList(@RequestBody @Valid BPA2002VO bpa2002vo, BindingResult bindingResult) {
		// TODO getBoardList
        JSONObject result= new JSONObject();

        result = boardService.getBoardList(bpa2002vo);

        return result;
    }

	/**
	 * @param bpa2003vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 게시물 상세 조회
	 */
	@RequestMapping(value = "/api/cm/getBoardDetail")
    @ResponseBody
    public JSONObject getBoardDetail(@RequestBody @Valid BPA2003VO bpa2003vo, BindingResult bindingResult) {
		// TODO getBoardDetail
        JSONObject result= new JSONObject();

        result = boardService.getBoardDetail(bpa2003vo);

        return result;
    }

	/**
	 * @param bpa2004vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 게시물 등록
	 */
	@RequestMapping(value = "/api/cm/setBoard")
    @ResponseBody
    public JSONObject setBoard(@RequestBody @Valid BPA2004VO bpa2004vo, BindingResult bindingResult) {
		// TODO setBoard
        JSONObject result= new JSONObject();

        result = boardService.setBoard(bpa2004vo);

        return result;
    }
}
