package com.basic.api.board.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.basic.api.board.service.BoardService;
import com.basic.api.board.vo.BPA2002VO;
import com.basic.api.board.vo.BPA2003VO;
import com.basic.api.board.vo.BPA2004VO;
import com.basic.api.common.service.ApiCommonService;
import com.basic.common.model.ParamMap;
import com.basic.common.AppPropertiesLoader;
import com.basic.common.utils.DateUtils;
import com.basic.common.utils.MessageUtils;
import com.basic.common.utils.StringUtils;
import com.basic.common.utils.crypto.AES256;
import com.basic.common.utils.crypto.SHA256;


@Service
public class BoardServiceImpl implements BoardService{

	@Resource(name="boardMapper")
	BoardMapper boardMapper;

	@Autowired
	ApiCommonService apiCommonService;

	@Autowired
    AppPropertiesLoader appPropertiesLoader;

    @Autowired
    MessageUtils messageUtils;

    protected Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getBoardList(BPA2002VO bpa2002vo) {
		// TODO getBoardList 게시물 목록 조회
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
        result.put("resultMsg", "");
        result.put("totalCnt", "");
        result.put("boardList", "");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
	        String hashKey = appPropertiesLoader.getValue(bpa2002vo.getReqSysCode());

	        SHA256 sha256 = new SHA256();

	        String sig = sha256.doCryption("BPA2002" + bpa2002vo.getReqSysCode() + bpa2002vo.getReqDate() + hashKey);

	        if(!sig.equals(bpa2002vo.getSignature())) {
	            result.put("resultCode", messageUtils.getCode("validate.signature"));
	            result.put("resultMsg", messageUtils.getMsg("validate.signature"));

	            return result;
	        }

	        ParamMap paramMap = new ParamMap();

	        if("QNA".equals(bpa2002vo.getBoardGubun())) {//QNA 일땐 회원번호와 답변여부 필수
	        	if("".equals(bpa2002vo.getUserNo()) || "".equals(bpa2002vo.getAnswerYn())) {
	        		result.put("resultCode", messageUtils.getCode("validate.required.value"));
		            result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

		            return result;
	        	}else {
	        		paramMap.put("userNo", bpa2002vo.getUserNo());
	    	        paramMap.put("answerYn", "A".equals(bpa2002vo.getAnswerYn()) ? "" : bpa2002vo.getAnswerYn());//전체 :A, 답변Y : Y, 답변N : N
	        	}
	        }

	        paramMap.put("boardGubun", bpa2002vo.getBoardGubun());
	        paramMap.put("startDt", bpa2002vo.getStartDt());
	        paramMap.put("endDt", bpa2002vo.getEndDt());
	        paramMap.put("pageNo", bpa2002vo.getPageNo());
	        paramMap.put("pageListCnt", bpa2002vo.getPageListCnt());
	        paramMap.put("dateApplyYn", bpa2002vo.getDateApplyYn());
	        paramMap.put("boardKind", bpa2002vo.getBoardKind());
	        paramMap.put("searchTerm", StringUtils.URLDecode(bpa2002vo.getSearchTerm()));

	        //게시판 목록 조회
	        List<EgovMap> boardlist = boardMapper.selectRpTbBoardList(paramMap);

	        if(boardlist.size() > 0) {
	            result.put("resultCode", messageUtils.getCode("success"));
	            result.put("resultMsg", messageUtils.getMsg("success"));

	            result.put("totalCnt", boardlist.get(0).get("totalCnt"));

	            for(int i = 0; i < boardlist.size(); i++) {
	            	boardlist.get(i).remove("totalCnt");
	            	boardlist.get(i).put("boardTitle", StringUtils.URLEncode(boardlist.get(i).get("boardTitle")));
	            	boardlist.get(i).put("boardContent", StringUtils.URLEncode(boardlist.get(i).get("boardContent")));
	            	boardlist.get(i).put("answerContent", StringUtils.URLEncode(boardlist.get(i).get("answerContent")));
	            }

	            result.put("boardList", boardlist);
	        }else {
	            result.put("resultCode", messageUtils.getCode("success.nodata"));
	            result.put("resultMsg", messageUtils.getMsg("success.nodata"));
	        }
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing getBoardList() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

        return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getBoardDetail(BPA2003VO bpa2003vo) {
		// TODO getBoardDetail 게시물 상세 조회
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
        result.put("resultMsg", "");
        result.put("regDate", "");
        result.put("boardTitle", "");
        result.put("boardContent", "");
        result.put("boardKind", "");
        result.put("boardKindNm", "");
        result.put("answerYn", "");
        result.put("answerContent", "");
        result.put("answerRegDate", "");
        result.put("regNm", "");
        result.put("regEmail", "");
        result.put("regTelNo", "");
        result.put("bannerFileNm", "");
        result.put("detFileNm", "");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
	        String hashKey = appPropertiesLoader.getValue(bpa2003vo.getReqSysCode());

	        SHA256 sha256 = new SHA256();

	        String sig = sha256.doCryption("BPA2003" + bpa2003vo.getReqSysCode() + bpa2003vo.getReqDate() + hashKey);

	        if(!sig.equals(bpa2003vo.getSignature())) {
	            result.put("resultCode", messageUtils.getCode("validate.signature"));
	            result.put("resultMsg", messageUtils.getMsg("validate.signature"));

	            return result;
	        }

	        ParamMap paramMap = new ParamMap();
	        paramMap.put("seqNo", bpa2003vo.getSeqNo());

	        //게시물 상세 조회
	        EgovMap boardDetail = boardMapper.selectRpTbBoardDetail(paramMap);

	        if(boardDetail.size() > 0) {
	        	AES256 aes256 = new AES256(hashKey);

	            result.put("resultCode", messageUtils.getCode("success"));
	            result.put("resultMsg", messageUtils.getMsg("success"));
	            result.put("regDate", boardDetail.get("regDate"));
	            result.put("boardTitle", StringUtils.URLEncode(boardDetail.get("boardTitle")));
	            result.put("boardContent", StringUtils.URLEncode(boardDetail.get("boardContent")));
	            result.put("boardKind", boardDetail.get("boardKind"));
	            result.put("boardKindNm", StringUtils.URLEncode(boardDetail.get("boardKindNm")));
	            result.put("answerYn", boardDetail.get("answerYn"));
	            result.put("answerContent", StringUtils.URLEncode(boardDetail.get("answerContent")));
	            result.put("answerRegDate", boardDetail.get("answerRegDate"));
	            result.put("regNm", aes256.encrypt(StringUtils.nvl(boardDetail.get("regNm"),"")));
	            result.put("regEmail", aes256.encrypt(StringUtils.nvl(boardDetail.get("regEmail"), "")));
	            result.put("regTelNo", aes256.encrypt(StringUtils.nvl(boardDetail.get("regTelNo"), "")));
	            result.put("bannerFileNm", StringUtils.URLEncode(boardDetail.get("bannerFileNm")));
	            result.put("detFileNm", StringUtils.URLEncode(boardDetail.get("detFileNm")));
	        }else {
	            result.put("resultCode", messageUtils.getCode("success.nodata"));
	            result.put("resultMsg", messageUtils.getMsg("success.nodata"));
	        }
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing getBoardDetail() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

        return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject setBoard(BPA2004VO bpa2004vo) {
		// TODO setBoard 게시물 등록
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
        result.put("resultMsg", "");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String mobileUuid = "";
			String userId = "";
			String userNm = "";
			String svcNotiYn = "";
			String boardTitle = StringUtils.URLDecode(bpa2004vo.getBoardTitle());
	        String hashKey = appPropertiesLoader.getValue(bpa2004vo.getReqSysCode());

	        SHA256 sha256 = new SHA256();

	        String sig = sha256.doCryption("BPA2004" + bpa2004vo.getReqSysCode() + bpa2004vo.getReqDate() + hashKey);

	        if(!sig.equals(bpa2004vo.getSignature())) {
	            result.put("resultCode", messageUtils.getCode("validate.signature"));
	            result.put("resultMsg", messageUtils.getMsg("validate.signature"));

	            return result;
	        }

	        AES256 aes256 = new AES256(hashKey);

	        String userNo = bpa2004vo.getUserNo();
	        String regNm = bpa2004vo.getRegNm();
	        String regEmail = bpa2004vo.getRegEmail();
	        String regTelNo = bpa2004vo.getRegTelNo();
	        String passwd = bpa2004vo.getPasswd();

	        ParamMap paramMap = new ParamMap();

	        paramMap.put("boardGubun", bpa2004vo.getBoardGubun());
	        paramMap.put("boardKind", bpa2004vo.getBoardKind());
	        paramMap.put("regDate", bpa2004vo.getRegDate());
	        paramMap.put("boardTitle", boardTitle);
	        paramMap.put("boardContent", StringUtils.URLDecode(bpa2004vo.getBoardContent()));
	        paramMap.put("userNo", userNo);

	        if("".equals(userNo)) {//회원번호가 없을 시 등록자명 등록자이메일 등록자연락처 게시글비밀번호 필수 체크
	        	if("".equals(regNm) || "".equals(regEmail) || "".equals(regTelNo) || "".equals(passwd)) {
	        		result.put("resultCode", messageUtils.getCode("validate.required.value"));
		            result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

		            return result;
	        	}else {
	        		paramMap.put("encRegNm", aes256.decrypt(regNm));
			        paramMap.put("encRegEmail", aes256.decrypt(regEmail));
			        paramMap.put("encRegTelNo", aes256.decrypt(regTelNo));
			        paramMap.put("encPasswd", aes256.decrypt(passwd));
	        	}
	        }else {
	        	paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴

//		        //회원정보 조회
//		  		EgovMap user = memberMapper.selectRpTbUser(paramMap);
//
//		  		if(user != null) {
//		  			String encUserNm = StringUtils.nvl(user.get("encUserNm"));
//		        	paramMap.put("encRegNm", encUserNm);
//			        paramMap.put("encRegEmail", StringUtils.nvl(user.get("encEmail")));
//			        paramMap.put("encRegTelNo", StringUtils.nvl(user.get("encUserMobile")));
//			        paramMap.put("encPasswd", "");
//			        mobileUuid = StringUtils.nvl(user.get("mobileUuid"));
//			        userNm = aes256.decrypt(encUserNm);
//			        userId = StringUtils.nvl(user.get("userId"));
//			        svcNotiYn = StringUtils.nvl(user.get("svcNotiYn"));
//		        }else {
//		        	result.put("resultCode", messageUtils.getCode("in.member.unregistered"));
//		            result.put("resultMsg", messageUtils.getMsg("in.member.unregistered"));
//
//		            return result;
//		        }
	        }

	        String regDate = DateUtils.getPushDate();

	        //게시물 등록
	        boardMapper.insertRpTbBoard(paramMap);

            result.put("resultCode", messageUtils.getCode("success"));
            result.put("resultMsg", messageUtils.getMsg("success"));
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing setBoard() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

        return result;
	}

}
