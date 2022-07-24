package com.basic.api.member.service.impl;

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

import com.basic.api.common.service.ApiCommonService;
import com.basic.api.member.service.MemberService;
import com.basic.api.member.vo.BPA0001VO;
import com.basic.api.member.vo.BPA0002VO;
import com.basic.api.member.vo.BPA0003VO;
import com.basic.api.member.vo.BPA0004VO;
import com.basic.api.member.vo.BPA0005VO;
import com.basic.api.member.vo.BPA0006VO;
import com.basic.api.member.vo.BPA0007VO;
import com.basic.api.member.vo.BPA0008VO;
import com.basic.api.member.vo.BPA0009VO;
import com.basic.api.member.vo.BPA0010VO;
import com.basic.api.member.vo.BPA0011VO;
import com.basic.api.member.vo.BPA0012VO;
import com.basic.api.member.vo.BPA0013VO;
import com.basic.common.AppPropertiesLoader;
import com.basic.common.model.ParamMap;
import com.basic.common.utils.DateUtils;
import com.basic.common.utils.MessageUtils;
import com.basic.common.utils.NtnoCrtnUtils;
import com.basic.common.utils.StringUtils;
import com.basic.common.utils.crypto.AES256;
import com.basic.common.utils.crypto.SHA256;


@Service
public class MemberServiceImpl implements MemberService{

	@Resource(name="memberMapper")
	MemberMapper memberMapper;

	@Autowired
	ApiCommonService commonService;

	@Autowired
    AppPropertiesLoader appPropertiesLoader;

	@Autowired
	MessageUtils messageUtils;

	@Autowired
	NtnoCrtnUtils ntnoCrtnUtils;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getAgreeList(BPA0001VO bpa0001vo) {
		// TODO getAgreeList 약관목록 조회
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");
		result.put("agreeCnt", "");
		result.put("agreeList", "");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0001vo.getReqSysCode());

			SHA256 sha256 = new SHA256();

			String sig = sha256.doCryption("BPA0001" + bpa0001vo.getReqSysCode() + bpa0001vo.getReqDate() + hashKey);

			if(!sig.equals(bpa0001vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			ParamMap paramMap = new ParamMap();
			paramMap.put("agreeLocCd", bpa0001vo.getAgreeLocCd());	//약관노출위치

			//약관목록 조회
			List<EgovMap> payAgreelist = memberMapper.selectRpTbPayAgreeList(paramMap);

			for(int i = 0; i < payAgreelist.size(); i++) {
				payAgreelist.get(i).put("agreeNm", StringUtils.URLEncode(payAgreelist.get(i).get("agreeNm")));
				payAgreelist.get(i).put("agreeTxt", StringUtils.URLEncode(payAgreelist.get(i).get("agreeTxt")));
			}

			result.put("resultCode", messageUtils.getCode("success"));
			result.put("resultMsg", messageUtils.getMsg("success"));
			result.put("agreeCnt", payAgreelist.size());
			result.put("agreeList", payAgreelist);
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing getAgreeList() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject checkUserId(BPA0002VO bpa0002vo) {
		// TODO checkUserId 아이디 사용 가능 여부 체크
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0002vo.getReqSysCode());

			SHA256 sha256 = new SHA256();

			String sig = sha256.doCryption("BPA0002" + bpa0002vo.getReqSysCode() + bpa0002vo.getReqDate() + hashKey);

			if(!sig.equals(bpa0002vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			ParamMap paramMap = new ParamMap();
			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
			paramMap.put("userId", bpa0002vo.getUserId());	//회원아이디

			//회원정보 조회
			EgovMap user = memberMapper.selectRpTbUser(paramMap);

			if(user != null) {
				result.put("resultCode", messageUtils.getCode("in.validate.check.id"));
				result.put("resultMsg", messageUtils.getMsg("in.validate.check.id"));
			}else {
				result.put("resultCode", messageUtils.getCode("success"));
				result.put("resultMsg", messageUtils.getMsg("success"));
			}
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing checkUserId() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject joinMember(BPA0003VO bpa0003vo) {
		// TODO joinMember 회원가입
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");
		result.put("userNo", "");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0003vo.getReqSysCode());

			SHA256 sha256 = new SHA256();

			String sig = sha256.doCryption("BPA0003" + bpa0003vo.getReqSysCode() + bpa0003vo.getReqDate() + bpa0003vo.getUserId() + hashKey);

			if(!sig.equals(bpa0003vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			AES256 aes256 = new AES256(hashKey);

			ParamMap paramMap = new ParamMap();
			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴

			if("exception".equals(aes256.decrypt(bpa0003vo.getUserCi()))) {
				result.put("resultCode", messageUtils.getCode("validate.decrypt"));
				result.put("resultMsg", messageUtils.getMsg("validate.decrypt"));

				return result;
			}else {
				paramMap.put("userCi", aes256.decrypt(bpa0003vo.getUserCi()));	//회원Ci
			}

			//회원정보 조회
			EgovMap user = memberMapper.selectRpTbUser(paramMap);

			if(user != null) {
				result.put("resultCode", messageUtils.getCode("in.validate.already.member"));
				result.put("resultMsg", messageUtils.getMsg("in.validate.already.member"));

				return result;
			}

			String userNm = aes256.decrypt(bpa0003vo.getUserNm());

			paramMap.clear();
			paramMap.put("userNo", ntnoCrtnUtils.createUserNo(bpa0003vo.getJoinMethodCd()));          /* 회원번호 */
			paramMap.put("userId", bpa0003vo.getUserId());          /* 회원아이디 */
			paramMap.put("userCi", aes256.decrypt(bpa0003vo.getUserCi()));          /* 회원Ci */
			paramMap.put("encUserNm", aes256.encrypt(userNm));          /* 회원명(50) */
			paramMap.put("encPasswd", sha256.doCryption(aes256.decrypt(bpa0003vo.getPasswd())));          /* 암호화_로그인비밀번호(20) */ //해시
			paramMap.put("birthDt", bpa0003vo.getBirthDt());          /* 생년월일 */
			paramMap.put("sexCd", bpa0003vo.getSexCd());          /* 성별 M:남자, F:여자 */
			paramMap.put("domesticYn", bpa0003vo.getDomesticYn());          /* 내국인여부 Y:내국인, N:외국인 */
			paramMap.put("encEmail", bpa0003vo.getEmail());          /* 암호화_이메일주소(100) */
			paramMap.put("joinMethodCd", bpa0003vo.getJoinMethodCd());          /* 회원가입방법 'U3003' G:일반가입, S:SNS가입 */
			paramMap.put("joinMethodDet", StringUtils.URLDecode(bpa0003vo.getJoinMethodDet()));          /* 회원가입방법상세 */
			paramMap.put("joinSnsKey", bpa0003vo.getJoinSnsKey());          /* SNS회원참조키 */
			paramMap.put("userMobileCd", bpa0003vo.getUserMobileCd());          /* 휴대폰통신사 'U3004' */
			paramMap.put("encUserMobile", bpa0003vo.getUserMobile());          /* 암호화_휴대폰번호('-'생략) */
			paramMap.put("userStatusCd", "0");          /* 회원상태 'U3002' 0:정상, 9:탈퇴 */

			//회원정보 저장
			memberMapper.insertRpTbUser(paramMap);

			paramMap.clear();
			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
			paramMap.put("userId", bpa0003vo.getUserId());	//회원아이디

			//회원정보 조회
			user = memberMapper.selectRpTbUser(paramMap);

			String userNo = user.get("userNo").toString();

			//모바일UUID가 있으면 저장
			if(!"".equals(bpa0003vo.getMobileUuid())) {
				paramMap.clear();
				paramMap.put("userNo", userNo);	//회원번호
				paramMap.put("mobileUuid", bpa0003vo.getMobileUuid());	//기기UUID
				paramMap.put("mobileOsCd", bpa0003vo.getMobileOsCd());	//기기OS타입
				paramMap.put("statusCd", "A");	//상태 A:정상, D:중지, T:임시

				//회원 기기정보 저장
				memberMapper.insertRpTbUserDevice(paramMap);
			}

			for(int i = 0; i < bpa0003vo.getAgreeList().size(); i++) {
				paramMap.clear();
				paramMap.put("userNo", userNo);	//회원번호
				paramMap.put("agreeVerNo", bpa0003vo.getAgreeList().get(i).getAgreeVerNo());	//약관버전번호
				paramMap.put("agreeNo", bpa0003vo.getAgreeList().get(i).getAgreeNo());	//약관번호
				paramMap.put("agreeYn", bpa0003vo.getAgreeList().get(i).getAgreeYn());	//동의여부

				//회원 약관동의 정보 저장
				memberMapper.insertRpTbUserAgree(paramMap);
			}

			result.put("resultCode", messageUtils.getCode("success"));
			result.put("resultMsg", messageUtils.getMsg("success"));
			result.put("userNo", userNo);
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing joinMember() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject authMember(BPA0004VO bpa0004vo) {
		// TODO authMember 회원인증
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");
		result.put("passwdErrCnt", "");
		result.put("userNo", "");
		result.put("userStatus", "");
		result.put("passwdChgDate", "");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0004vo.getReqSysCode());

			SHA256 sha256 = new SHA256();

			String sig = sha256.doCryption("BPA0004" + bpa0004vo.getReqSysCode() + bpa0004vo.getReqDate() + hashKey);

			if(!sig.equals(bpa0004vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			//회원번호, 회원아이디 둘다 없을 경우 필수값 오류
			if("".equals(StringUtils.nvl(bpa0004vo.getUserNo(), "")) && "".equals(StringUtils.nvl(bpa0004vo.getUserId(), ""))) {
				result.put("resultCode", messageUtils.getCode("validate.required.value"));
				result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

				return result;
			}

			AES256 aes256 = new AES256(hashKey);

			ParamMap paramMap = new ParamMap();
			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
			paramMap.put("userNo", bpa0004vo.getUserNo());
			paramMap.put("userId", bpa0004vo.getUserId());
			paramMap.put("jobCd", bpa0004vo.getJobCd());	//1:로그인비밀번호, 2:캐시결제비밀번호

			//aes256 복호화 오류시
			if("exception".equals(aes256.decrypt(bpa0004vo.getUserPasswd()))) {
				result.put("resultCode", messageUtils.getCode("validate.decrypt"));
				result.put("resultMsg", messageUtils.getMsg("validate.decrypt"));

				return result;
			}else {
				paramMap.put("userPasswd", sha256.doCryption(aes256.decrypt(bpa0004vo.getUserPasswd())));
			}

			//회원정보 조회
			EgovMap user = memberMapper.selectRpTbUser(paramMap);

			if(user != null) {
				paramMap.put("passwdErrCnt", "0");

				//회원인증이 성공이면 비밀번호 오류 횟수 0으로 업데이트
				memberMapper.updateRpTbUserPasswdErrCnt(paramMap); //비밀번호 오류 횟수 변경

				result.put("resultCode", messageUtils.getCode("success"));
				result.put("resultMsg", messageUtils.getMsg("success"));
				result.put("passwdErrCnt", "0");
				result.put("userNo", user.get("userNo"));
				result.put("userStatus", user.get("userStatusCd"));
				result.put("passwdChgDate", user.get("chgPasswdDate"));
			}else {
				paramMap.put("passwdErrCnt", "1");

				//회원인증이 실패면 비밀번호 오류 횟수 +1
				memberMapper.updateRpTbUserPasswdErrCnt(paramMap); //비밀번호 오류 횟수 변경

				paramMap.put("userPasswd", "");

				//해당 회원이 있는지 체크
				user = memberMapper.selectRpTbUser(paramMap); //회원번호나 아이디로 있는지 체크

				//회원이 있으면 비밀번호 오류 횟수 값 전달
				if(user != null) {
					if("1".equals(bpa0004vo.getJobCd())){
						result.put("passwdErrCnt", user.get("passwdErrCnt"));
					}else {
						result.put("passwdErrCnt", user.get("appPasswdErrCnt"));
					}
				}else {
					result.put("passwdErrCnt", "0");
				}

				result.put("resultCode", messageUtils.getCode("in.validate.auth.member"));
				result.put("resultMsg", messageUtils.getMsg("in.validate.auth.member"));
			}
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing authMember() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject setPasswd(BPA0005VO bpa0005vo) {
		// TODO setPasswd 비밀번호 설정
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0005vo.getReqSysCode());

			SHA256 sha256 = new SHA256();

			String sig = sha256.doCryption("BPA0005" + bpa0005vo.getReqSysCode() + bpa0005vo.getReqDate() + hashKey);

			if(!sig.equals(bpa0005vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			AES256 aes256 = new AES256(hashKey);
			String jobCd = bpa0005vo.getJobCd();
			String encPasswd = "";

			ParamMap paramMap = new ParamMap();
			paramMap.put("userNo", bpa0005vo.getUserNo());
			paramMap.put("jobCd", jobCd);	//1:로그인비밀번호, 2:캐시결제비밀번호, 3:임시비밀번호

			if("exception".equals(aes256.decrypt(bpa0005vo.getUserPasswd()))) {
				result.put("resultCode", messageUtils.getCode("validate.decrypt"));
				result.put("resultMsg", messageUtils.getMsg("validate.decrypt"));

				return result;
			}else {
				encPasswd = sha256.doCryption(aes256.decrypt(bpa0005vo.getUserPasswd()));
			}

			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
			paramMap.put("userNo", bpa0005vo.getUserNo());	//회원번호

	        //회원정보 조회
	  		EgovMap user = memberMapper.selectRpTbUser(paramMap);

	  		String encNowPasswd = StringUtils.nvl(user.get("encPasswd"));
	  		String encBfPasswd = StringUtils.nvl(user.get("encBfPasswd"));
	  		String encPcashPasswd = StringUtils.nvl(user.get("encPcashPasswd"));
	  		String encBfPcashPasswd = StringUtils.nvl(user.get("encBfPcashPasswd"));
	  		String userStatusCd = StringUtils.nvl(user.get("userStatusCd"));

	  		//현재 비밀번호와 이전 비밀번호 사용 체크
	  		switch (jobCd) {
			case "1":
				if(encPasswd.equals(encNowPasswd) || encPasswd.equals(encBfPasswd)) {
		  			result.put("resultCode", messageUtils.getCode("in.member.check.passwd"));
					result.put("resultMsg", messageUtils.getMsg("in.member.check.passwd"));

					return result;
		  		}
				break;
			case "2":
				if(encPasswd.equals(encPcashPasswd) || encPasswd.equals(encBfPcashPasswd)) {
		  			result.put("resultCode", messageUtils.getCode("in.member.check.passwd"));
					result.put("resultMsg", messageUtils.getMsg("in.member.check.passwd"));

					return result;
		  		}
				break;
			}

	  		paramMap.put("userPasswd", encPasswd); //DB암호화
	  		paramMap.put("userStatusCd", userStatusCd);

			int updateResult = memberMapper.updateRpTbUserPasswd(paramMap);

			if(updateResult > 0) {
				result.put("resultCode", messageUtils.getCode("success"));
				result.put("resultMsg", messageUtils.getMsg("success"));
			}else {
				result.put("resultCode", messageUtils.getCode("in.member.unregistered"));
				result.put("resultMsg", messageUtils.getMsg("in.member.unregistered"));
			}
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing setPasswd() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getMemeberInfo(BPA0006VO bpa0006vo) {
		// TODO getMemeberInfo 회원정보 조회
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");
		result.put("userNo", "");
		result.put("userStatusCd", "");
		result.put("userNm", ""); //DB암호화 복호화
		result.put("userId", "");
		result.put("userMobile", ""); //DB암호화 복호화
		result.put("email", ""); //DB암호화 복호화
		result.put("pcashPinYn", "");
		result.put("pcashJoinYn", "");
		result.put("payJoinYn", "");
		result.put("cashReceiptAutoYn", "");
		result.put("cashReceiptCd", "");
		result.put("cashReceiptNoCd", "");
		result.put("cashReceiptNo", "");
		result.put("notiYn", "");
		result.put("passNotiYn", "");
		result.put("loginNotiYn", "");
		result.put("svcNotiYn", "");
		result.put("mobileUuid", "");
		result.put("passwdErrCnt", "");
		result.put("appPasswdErrCnt", "");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0006vo.getReqSysCode());

			SHA256 sha256 = new SHA256();

			String sig = sha256.doCryption("BPA0006" + bpa0006vo.getReqSysCode() + bpa0006vo.getReqDate() + hashKey);

			if(!sig.equals(bpa0006vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			String userCi = StringUtils.nvl(bpa0006vo.getUserCi(), "");
			String userNo = StringUtils.nvl(bpa0006vo.getUserNo(), "");

			AES256 aes256 = new AES256(hashKey);

			userCi = aes256.decrypt(bpa0006vo.getUserCi());

			//회원CI, 회원번호 둘다 없을 경우 필수값 오류
			if("".equals(userCi) && "".equals(userNo)) {
				result.put("resultCode", messageUtils.getCode("validate.required.value"));
				result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

				return result;
			}

			if(!"".equals(userCi) && "exception".equals(userCi)) {
				result.put("resultCode", messageUtils.getCode("validate.decrypt"));
				result.put("resultMsg", messageUtils.getMsg("validate.decrypt"));

				return result;
			}

			ParamMap paramMap = new ParamMap();
			paramMap.put("userCi", userCi);
			paramMap.put("userNo", userNo);
			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴

			EgovMap user = memberMapper.selectRpTbUser(paramMap);

			if(user != null) {
				result.put("resultCode", messageUtils.getCode("success"));
				result.put("resultMsg", messageUtils.getMsg("success"));
				result.put("userNo", user.get("userNo"));
				result.put("userStatusCd", user.get("userStatusCd"));
				result.put("userNm", aes256.encrypt(user.get("encUserNm").toString())); //DB암호화 복호화
				result.put("userId", user.get("userId"));
				result.put("userMobile", aes256.encrypt(user.get("encUserMobile").toString())); //DB암호화 복호화
				result.put("email", aes256.encrypt(user.get("encEmail").toString())); //DB암호화 복호화
				result.put("pcashPinYn", user.get("pcashPasswdYn"));
				result.put("pcashJoinYn", user.get("pcashJoinYn"));
				result.put("payJoinYn", user.get("payJoinYn"));
				result.put("cashReceiptAutoYn", user.get("bpaperAutoYn"));
				result.put("cashReceiptCd", user.get("bpaperType"));
				result.put("cashReceiptNoCd", user.get("bpaperNoType"));
				result.put("cashReceiptNo", aes256.encrypt(user.get("encBpaperNo").toString()));
				result.put("passNotiYn", user.get("passNotiYn"));
				result.put("loginNotiYn", user.get("loginNotiYn"));
				result.put("svcNotiYn", user.get("svcNotiYn"));
				result.put("mobileUuid", user.get("mobileUuid"));
				result.put("passwdErrCnt", user.get("passwdErrCnt"));
				result.put("appPasswdErrCnt", user.get("appPasswdErrCnt"));
			}else {
				result.put("resultCode", messageUtils.getCode("in.member.unregistered"));
				result.put("resultMsg", messageUtils.getMsg("in.member.unregistered"));

			}
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing getMemeberInfo() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public JSONObject updateMemberInfo(BPA0007VO bpa0007vo) {
		// TODO updateMemberInfo 회원정보 변경
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0007vo.getReqSysCode());

			SHA256 sha256 = new SHA256();

			String sig = sha256.doCryption("BPA0007" + bpa0007vo.getReqSysCode() + bpa0007vo.getReqDate() + hashKey);

			if(!sig.equals(bpa0007vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			AES256 aes256 = new AES256(hashKey);

			String userNo = bpa0007vo.getUserNo();
			String jobCdNm = "";

			ParamMap paramMap = new ParamMap();
			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
			paramMap.put("userNo", userNo);	//회원번호

	        //회원정보 조회
	  		EgovMap user = memberMapper.selectRpTbUser(paramMap);

	  		String mobileUuid = StringUtils.nvl(user.get("mobileUuid"));

	  		if(user != null) {
	  			paramMap.clear();

				paramMap.put("userNo", userNo);
				paramMap.put("jobCd", bpa0007vo.getJobCd());

				switch(bpa0007vo.getJobCd()) {
					case "1" ://휴대폰번호 변경
						jobCdNm = "휴대폰번호";
						paramMap.put("userMobileCd", bpa0007vo.getUserMobileCd());

						if("".equals(bpa0007vo.getUserMobileCd()) || "".equals(bpa0007vo.getUserMobile()) || "".equals(aes256.decrypt(bpa0007vo.getUserMobile()))) {
							result.put("resultCode", messageUtils.getCode("validate.required.value"));
							result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

							return result;
						}else if("exception".equals(aes256.decrypt(bpa0007vo.getUserMobile()))){
							result.put("resultCode", messageUtils.getCode("validate.decrypt"));
							result.put("resultMsg", messageUtils.getMsg("validate.decrypt"));

							return result;
						}else {
							paramMap.put("userMobile", bpa0007vo.getUserMobile());

							memberMapper.updateRpTbUserInfo(paramMap);
						}

						break;
					case "2" ://이메일주소 변경
						jobCdNm = "이메일주소";
						if("".equals(bpa0007vo.getEmail()) || "".equals(aes256.decrypt(bpa0007vo.getEmail()))) {
							result.put("resultCode", messageUtils.getCode("validate.required.value"));
							result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

							return result;
						}else if("exception".equals(aes256.decrypt(bpa0007vo.getEmail()))){
							result.put("resultCode", messageUtils.getCode("validate.decrypt"));
							result.put("resultMsg", messageUtils.getMsg("validate.decrypt"));

							return result;
						}else {
							paramMap.put("email", bpa0007vo.getEmail()); //DB암호화

							memberMapper.updateRpTbUserInfo(paramMap);
						}

						break;
					case "3" ://현금영수증 설정 정보 변경
						jobCdNm = "현금영수증 설정 정보";
						if("".equals(bpa0007vo.getCashReceiptAutoYn())) {
							result.put("resultCode", messageUtils.getCode("validate.required.value"));
							result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

							return result;
						}else if("exception".equals(aes256.decrypt(bpa0007vo.getCashReceiptNo()))){
							result.put("resultCode", messageUtils.getCode("validate.decrypt"));
							result.put("resultMsg", messageUtils.getMsg("validate.decrypt"));

							return result;
						}else {
							if("Y".equals(bpa0007vo.getCashReceiptAutoYn())) {//현금영수증자동발행여부가 Y 이면 현금영수증 정보 필수
								String cashReceiptNo = aes256.decrypt(bpa0007vo.getCashReceiptNo());

								 if("".equals(bpa0007vo.getCashReceiptCd()) || "".equals(bpa0007vo.getCashReceiptNoCd()) || "".equals(cashReceiptNo)){
									result.put("resultCode", messageUtils.getCode("validate.required.value"));
									result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

									return result;
								 }

								 if(!StringUtils.checkNumber(cashReceiptNo)) {//숫자로 되있는지 체크
									result.put("resultCode", messageUtils.getCode("validate.pattern"));
									result.put("resultMsg", messageUtils.getMsg("validate.pattern", "[현금영수증발행번호]"));

									return result;
								 }

								 if(cashReceiptNo.length() < 10){//10자 이상인지 체크
									result.put("resultCode", messageUtils.getCode("validate.size"));
									result.put("resultMsg", messageUtils.getMsg("validate.size", "[현금영수증발행번호]"));

									return result;
								 }
							}
							paramMap.put("cashReceiptYn", bpa0007vo.getCashReceiptAutoYn());
							paramMap.put("cashReceiptCd", bpa0007vo.getCashReceiptCd());	//현금영수증구분 1:소득공제, 2:지출증빙
							paramMap.put("cashReceiptNoCd", bpa0007vo.getCashReceiptNoCd()); //현금영수증발급번호구분 H:휴대폰번호, C:카드번호, B:사업자번호
							paramMap.put("cashReceiptNo", bpa0007vo.getCashReceiptNo()); //DB암호화

							memberMapper.updateRpTbUserInfo(paramMap);
						}

						break;
					case "4" ://기기정보변경
						jobCdNm = "기기정보";
						if("".equals(bpa0007vo.getMobileUuid()) || "".equals(bpa0007vo.getMobileOsCd())) {
							result.put("resultCode", messageUtils.getCode("validate.required.value"));
							result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

							return result;
						}else {
							if(!mobileUuid.equals(bpa0007vo.getMobileUuid())) {
								paramMap.put("mobileUuid", bpa0007vo.getMobileUuid());
								paramMap.put("mobileOsCd", bpa0007vo.getMobileOsCd());
								paramMap.put("statusCd", "A");	//상태 A:정상, D:중지, T:임시

								memberMapper.updateRpTbUserDevice(paramMap);

								mobileUuid = bpa0007vo.getMobileUuid();
							}else {
								mobileUuid = "";//등록된 UUID가 같으면 PUSH 안보내기 위해 설정
							}
						}

						break;
					case "5" ://결제알림설정
						jobCdNm = "결제알림설정";
						if("".equals(bpa0007vo.getNotiYn())) {
							result.put("resultCode", messageUtils.getCode("validate.required.value"));
							result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

							return result;
						}else {
							paramMap.put("passNotiYn", bpa0007vo.getNotiYn());

							memberMapper.updateRpTbUserNotiYn(paramMap);
						}

						break;
					case "6" ://로그인알림여부변경
						jobCdNm = "로그인알림설정";
						if("".equals(bpa0007vo.getNotiYn())) {
							result.put("resultCode", messageUtils.getCode("validate.required.value"));
							result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

							return result;
						}else {
							paramMap.put("loginNotiYn", bpa0007vo.getNotiYn());

							memberMapper.updateRpTbUserNotiYn(paramMap);
						}

						break;
					case "7" ://서비스알림여부변경
						jobCdNm = "서비스알림설정";
						if("".equals(bpa0007vo.getNotiYn())) {
							result.put("resultCode", messageUtils.getCode("validate.required.value"));
							result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

							return result;
						}else {
							paramMap.put("svcNotiYn", bpa0007vo.getNotiYn());

							memberMapper.updateRpTbUserNotiYn(paramMap);
						}

						break;
				}

				result.put("resultCode", messageUtils.getCode("success"));
				result.put("resultMsg", messageUtils.getMsg("success"));
			}else {
				result.put("resultCode", messageUtils.getCode("in.member.unregistered"));
				result.put("resultMsg", messageUtils.getMsg("in.member.unregistered"));
			}
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing updateMemberInfo() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public JSONObject withdrawMember(BPA0008VO bpa0008vo) {
		// TODO withdrawMember 회원탈퇴
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0008vo.getReqSysCode());

			SHA256 sha256 = new SHA256();
			AES256 aes256 = new AES256(hashKey);

			String sig = sha256.doCryption("BPA0008" + bpa0008vo.getReqSysCode() + bpa0008vo.getReqDate() + hashKey);

			if(!sig.equals(bpa0008vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			ParamMap paramMap = new ParamMap();
			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
			paramMap.put("userNo", bpa0008vo.getUserNo());

			//회원정보 조회
			EgovMap user = memberMapper.selectRpTbUser(paramMap);

			String mobileUuid = StringUtils.nvl(user.get("mobileUuid"));

			if(user != null) {
				//회원탈퇴
				paramMap.put("userId", DateUtils.getMillisecTime() + ":" + user.get("userId").toString().substring(0, user.get("userId").toString().length() - 3) + "***");//yyyymmddhhmmssfff + ":" + ID 뒤 3자리 *
				paramMap.put("userCi", DateUtils.getMillisecTime() + ":" + user.get("userCi").toString().substring(0, user.get("userCi").toString().length() - 3) + "***");//yyyymmddhhmmssfff + ":" + CI 뒤 3자리 *
				paramMap.put("encUserNm", "탈퇴자"); //DB암호화
				paramMap.put("encEmail", StringUtils.maskEmad(aes256.decrypt(user.get("encEmail").toString()))); //DB암호화
				paramMap.put("encUserMobile", StringUtils.maskMbphNo(aes256.decrypt(user.get("encUserMobile").toString()))); //DB암호화

				String encBpaperNo = StringUtils.nvl(user.get("encBpaperNo"));

				if(!"".equals(encBpaperNo)) {
					encBpaperNo = aes256.decrypt(encBpaperNo);
					encBpaperNo	= encBpaperNo.substring(0, encBpaperNo.length() - (encBpaperNo.length() >= 3 ? 3 : 0)) + "***";
					encBpaperNo = aes256.encrypt(encBpaperNo);
				}

				paramMap.put("encBpaperNo", encBpaperNo); //DB암호화
				paramMap.put("withdrawCd", bpa0008vo.getWithdrawCd());//10:앱에 대한 신뢰도		20:자주 이용하지 않음		30:시스템 오류/장애/불편		90:기타
				paramMap.put("withdrawDet", StringUtils.URLDecode(bpa0008vo.getWithdrawDet()));

				memberMapper.updateRpTbUserWithdraw(paramMap);//회원탈퇴 처리

				memberMapper.deleteRpTbUserDevice(paramMap);//회원기기정보 삭제

				result.put("resultCode", messageUtils.getCode("success"));
				result.put("resultMsg", messageUtils.getMsg("success"));
			}else {
				result.put("resultCode", messageUtils.getCode("in.member.unregistered"));
				result.put("resultMsg", messageUtils.getMsg("in.member.unregistered"));
			}
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing withdrawMember() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject findId(BPA0009VO bpa0009vo) {
		// TODO findId 아이디찾기
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");
		result.put("userList", "");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0009vo.getReqSysCode());

			SHA256 sha256 = new SHA256();

			String sig = sha256.doCryption("BPA0009" + bpa0009vo.getReqSysCode() + bpa0009vo.getReqDate() + hashKey);

			if(!sig.equals(bpa0009vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			String encUserNm = bpa0009vo.getUserNm();
			String encEmail = bpa0009vo.getEmail();

			ParamMap paramMap = new ParamMap();
			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
			paramMap.put("encUserNm", encUserNm);
			paramMap.put("encEmail", encEmail);

			//회원정보 조회
			List<EgovMap> userList = memberMapper.selectRpTbUserIdList(paramMap);

			if(userList.size() > 0) {
				result.put("resultCode", messageUtils.getCode("success"));
				result.put("resultMsg", messageUtils.getMsg("success"));
				result.put("userList", userList);
			}else {
				paramMap.clear();
				paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
				paramMap.put("encUserNm", encUserNm);
				//회원정보가 없을 때 회원명으로만 다시 조회
				userList = memberMapper.selectRpTbUserIdList(paramMap);

				if(userList.size() > 0) {//회원명만 일치하는게 있을 시 2304
					result.put("resultCode", messageUtils.getCode("in.member.not.equal.email"));
					result.put("resultMsg", messageUtils.getMsg("in.member.not.equal.email"));
				}else {
					result.put("resultCode", messageUtils.getCode("in.member.unregistered"));
					result.put("resultMsg", messageUtils.getMsg("in.member.unregistered"));
				}
			}
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing findId() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject findPasswd(BPA0010VO bpa0010vo) {
		// TODO findPasswd 비밀번호 찾기
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");
		result.put("userNo", "");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0010vo.getReqSysCode());

			SHA256 sha256 = new SHA256();

			String sig = sha256.doCryption("BPA0010" + bpa0010vo.getReqSysCode() + bpa0010vo.getReqDate() + hashKey);

			if(!sig.equals(bpa0010vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			String encUserNm = bpa0010vo.getUserNm();
			String encEmail = bpa0010vo.getEmail();

			ParamMap paramMap = new ParamMap();
			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
			paramMap.put("encUserNm", encUserNm);
			paramMap.put("encEmail", encEmail);
			paramMap.put("userId", bpa0010vo.getUserId());

			//회원정보 조회
			EgovMap user = memberMapper.selectRpTbUser(paramMap);

			if(user != null) {
				result.put("resultCode", messageUtils.getCode("success"));
				result.put("resultMsg", messageUtils.getMsg("success"));
				result.put("userNo", user.get("userNo"));
			}else {
				paramMap.clear();
				paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
				paramMap.put("encUserNm", encUserNm);
				paramMap.put("userId", bpa0010vo.getUserId());
				//회원 정보가 없을 때 회원명 및 아이디로만 다시 조회
				user = memberMapper.selectRpTbUser(paramMap);

				if(user != null) {//일치하는게 있을 시 2304
					result.put("resultCode", messageUtils.getCode("in.member.not.equal.email"));
					result.put("resultMsg", messageUtils.getMsg("in.member.not.equal.email"));
				}else {
					result.put("resultCode", messageUtils.getCode("in.member.unregistered"));
					result.put("resultMsg", messageUtils.getMsg("in.member.unregistered"));
				}
			}
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing findPasswd() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getToken(BPA0011VO bpa0011vo) {
		// TODO getToken 회원토큰 생성
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");
		result.put("userToken", "");
		result.put("expireDate", "");
		result.put("activeTokenYn", "N");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0011vo.getReqSysCode());

			SHA256 sha256 = new SHA256();

			String sig = sha256.doCryption("BPA0011" + bpa0011vo.getReqSysCode() + bpa0011vo.getReqDate() + bpa0011vo.getUserNo() + hashKey);

			if(!sig.equals(bpa0011vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			if("AP".equals(bpa0011vo.getDeviceTypeCd())) {
				if("".equals(bpa0011vo.getMobileUuid())) {
					result.put("resultCode", messageUtils.getCode("validate.required.value"));
					result.put("resultMsg", messageUtils.getMsg("validate.required.value"));

					return result;
				}
			}

			ParamMap paramMap = new ParamMap();
			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
			paramMap.put("userNo", bpa0011vo.getUserNo());

			//회원정보 조회
			EgovMap user = memberMapper.selectRpTbUser(paramMap);

			if(user != null) {
				String token = sha256.doCryption(ntnoCrtnUtils.occrSessionGuid());

				paramMap.put("tokenStatusCd", "A");	//토큰상태(A:정상,D:삭제)
				EgovMap activeToken = memberMapper.selectRpTbUserToken(paramMap);

				if(activeToken != null) {//활성화된 토큰이 있으면
					paramMap.put("tokenStatusCd", "D");	//토큰상태(A:정상,D:삭제)
					paramMap.put("userToken", activeToken.get("userToken").toString());

					memberMapper.updateRpTbUserToken(paramMap);

					result.put("activeTokenYn", "Y");

					paramMap.put("tokenStatusCd", "A");	//토큰상태(A:정상,D:삭제)
					paramMap.put("userToken", token);
					paramMap.put("deviceTypeCd", bpa0011vo.getDeviceTypeCd());
					paramMap.put("ipAddr", bpa0011vo.getIpAddr());
					paramMap.put("mobileUuid", bpa0011vo.getMobileUuid());
					paramMap.put("expireDate", bpa0011vo.getExpireDate());

					memberMapper.insertRpTbUserToken(paramMap);

//					paramMap.put("deviceTypeCd", bpa0011vo.getDeviceTypeCd());
//					paramMap.put("ipAddr", bpa0011vo.getIpAddr());
//					paramMap.put("mobileUuid", bpa0011vo.getMobileUuid());
//
//					EgovMap userToken = memberMapper.selectRpTbUserToken(paramMap);
//
//					if(userToken != null) {//활성화된 토큰과 같은 접속정보인게 있으면 만료시간 연장 후 리턴
//						String now = DateUtils.getSecTime();
//						String expireDate = activeToken.get("expireDate").toString();
//
//						Long diffMin = DateUtils.getMinutesBetween(now, expireDate);
//
//						if(diffMin > 0) {//토큰이 만료가 아니면 만료일시 연장 후 리턴
//							if(diffMin < 10) {
//								paramMap.put("userToken", userToken.get("userToken").toString());
//
//								memberMapper.updateRpTbUserToken(paramMap);
//							}
//						}else {//활성상태의 토큰이 만료일시가 지났으면 새로 발급
//							paramMap.put("tokenStatusCd", "D");	//토큰상태(A:정상,D:삭제)
//							paramMap.put("userToken", userToken.get("userToken").toString());
//
//							memberMapper.updateRpTbUserToken(paramMap);
//
//							paramMap.put("tokenStatusCd", "A");	//토큰상태(A:정상,D:삭제)
//							paramMap.put("userToken", token);
//							paramMap.put("expireDate", bpa0011vo.getExpireDate());
//							memberMapper.insertRpTbUserToken(paramMap);
//						}
//					}else {//활성화된 토큰이 접속정보가 다르면 활성화된 토큰 상태 변경 후 새로 발급
//						paramMap.put("tokenStatusCd", "D");	//토큰상태(A:정상,D:삭제)
//						paramMap.put("userToken", activeToken.get("userToken").toString());
//
//						memberMapper.updateRpTbUserToken(paramMap);
//
//						result.put("activeTokenYn", "Y");
//
//						paramMap.put("tokenStatusCd", "A");	//토큰상태(A:정상,D:삭제)
//						paramMap.put("userToken", token);
//						paramMap.put("expireDate", bpa0011vo.getExpireDate());
//						memberMapper.insertRpTbUserToken(paramMap);
//					}
				}else {//활성화된 토큰이 없으면 토큰 생성
					paramMap.put("userToken", token);
					paramMap.put("deviceTypeCd", bpa0011vo.getDeviceTypeCd());
					paramMap.put("ipAddr", bpa0011vo.getIpAddr());
					paramMap.put("mobileUuid", bpa0011vo.getMobileUuid());
					paramMap.put("expireDate", bpa0011vo.getExpireDate());

					memberMapper.insertRpTbUserToken(paramMap);
				}

//				EgovMap userToken = memberMapper.selectRpTbUserToken(paramMap);

				result.put("resultCode", messageUtils.getCode("success"));
				result.put("resultMsg", messageUtils.getMsg("success"));
				result.put("userToken", token);
				result.put("expireDate", bpa0011vo.getExpireDate());
//				result.put("userToken", userToken.get("userToken"));
//				result.put("expireDate", userToken.get("expireDate"));
			}else {
				result.put("resultCode", messageUtils.getCode("in.member.unregistered"));
				result.put("resultMsg", messageUtils.getMsg("in.member.unregistered"));
			}
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing getToken() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject authToken(BPA0012VO bpa0012vo) {
		// TODO authToken 회원토큰 인증
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");
		result.put("expireDate", "");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0012vo.getReqSysCode());

			SHA256 sha256 = new SHA256();

			String sig = sha256.doCryption("BPA0012" + bpa0012vo.getReqSysCode() + bpa0012vo.getReqDate() + bpa0012vo.getUserNo() + hashKey);

			if(!sig.equals(bpa0012vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			ParamMap paramMap = new ParamMap();
			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
			paramMap.put("userNo", bpa0012vo.getUserNo());

			//회원정보 조회
			EgovMap user = memberMapper.selectRpTbUser(paramMap);

			if(user != null) {
				paramMap.put("userToken", bpa0012vo.getUserToken());
				paramMap.put("tokenStatusCd", "A");	//토큰상태(A:정상,D:삭제)
//				paramMap.put("deviceTypeCd", bpa0012vo.getDeviceTypeCd());
//				paramMap.put("ipAddr", bpa0012vo.getIpAddr());
//				paramMap.put("mobileUuid", bpa0012vo.getMobileUuid());

				EgovMap activeToken = memberMapper.selectRpTbUserToken(paramMap);

				if(activeToken != null) {
					String now = DateUtils.getSecTime();
					String expireDate = activeToken.get("expireDate").toString();

					Long diffMin = DateUtils.getMinutesBetween(now, expireDate);

					if(diffMin > 0) {//토큰이 만료가 아니면 만료일시 연장 후 리턴
						if(diffMin < 10) {//만료일시가 10분보다 작으면 10분 연장
							memberMapper.updateRpTbUserToken(paramMap);
						}

						//중복 토큰이 발생 할 경우 가 있어, 중복된 토큰을 삭제 하는 업데이트
						memberMapper.updateRpTbUserTokenDup(paramMap);

						activeToken = memberMapper.selectRpTbUserToken(paramMap);

						result.put("resultCode", messageUtils.getCode("success"));
						result.put("resultMsg", messageUtils.getMsg("success"));
						result.put("expireDate", activeToken.get("expireDate"));
					}else {
						result.put("resultCode", messageUtils.getCode("in.member.auth.token.expire"));
						result.put("resultMsg", messageUtils.getMsg("in.member.auth.token.expire"));
					}
				}else {
					result.put("resultCode", messageUtils.getCode("in.member.auth.token.error"));
					result.put("resultMsg", messageUtils.getMsg("in.member.auth.token.error"));
				}
			}else {
				result.put("resultCode", messageUtils.getCode("in.member.unregistered"));
				result.put("resultMsg", messageUtils.getMsg("in.member.unregistered"));
			}
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing authToken() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject expireToken(BPA0013VO bpa0013vo) {
		// TODO expireToken 회원토큰 만료 처리
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
		result.put("resultMsg", "");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
			String hashKey = appPropertiesLoader.getValue(bpa0013vo.getReqSysCode());

			SHA256 sha256 = new SHA256();

			String sig = sha256.doCryption("BPA0013" + bpa0013vo.getReqSysCode() + bpa0013vo.getReqDate() + hashKey);

			if(!sig.equals(bpa0013vo.getSignature())) {
				result.put("resultCode", messageUtils.getCode("validate.signature"));
				result.put("resultMsg", messageUtils.getMsg("validate.signature"));

				return result;
			}

			ParamMap paramMap = new ParamMap();
			paramMap.put("userStatusCd", "0");	//회원상태 0:정상, 9:탈퇴
			paramMap.put("userNo", bpa0013vo.getUserNo());

			//회원정보 조회
			EgovMap user = memberMapper.selectRpTbUser(paramMap);

			if(user != null) {
				paramMap.put("userToken", bpa0013vo.getUserToken());
				paramMap.put("tokenStatusCd", "D");	//토큰상태(A:정상,D:삭제)
				memberMapper.updateRpTbUserToken(paramMap);

				result.put("resultCode", messageUtils.getCode("success"));
				result.put("resultMsg", messageUtils.getMsg("success"));
			}else {
				result.put("resultCode", messageUtils.getCode("in.member.unregistered"));
				result.put("resultMsg", messageUtils.getMsg("in.member.unregistered"));
			}
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing expireToken() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

		return result;
	}

}
