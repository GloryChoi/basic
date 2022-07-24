package com.basic.api.member.web;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

/**
 * @author CYK
 * @description 회원관련 API
 */
@Controller
public class MemberController {

	@Autowired
	MemberService memberService;

	/**
	 * @param bpa0001vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 회원 약관조회
	 */
	@RequestMapping(value = "/api/bppay/getAgreeList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getAgreeList(@RequestBody @Valid BPA0001VO bpa0001vo, BindingResult bindingResult) {
		// TODO getAgreeList
        JSONObject result= new JSONObject();

        result = memberService.getAgreeList(bpa0001vo);

        return result;
    }

	/**
	 * @param bpa0002vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 회원 아이디 사용가능 여부 확인
	 */
	@RequestMapping(value = "/api/bppay/checkUserId", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkUserId(@RequestBody @Valid BPA0002VO bpa0002vo, BindingResult bindingResult) {
		// TODO checkUserId
        JSONObject result= new JSONObject();

        result = memberService.checkUserId(bpa0002vo);

        return result;
    }

	/**
	 * @param bpa0003vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 회원가입
	 */
	@RequestMapping(value = "/api/bppay/joinMember", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject joinMember(@RequestBody @Valid BPA0003VO bpa0003vo, BindingResult bindingResult) {
		// TODO joinMember
        JSONObject result= new JSONObject();

        result = memberService.joinMember(bpa0003vo);

        return result;
    }

	/**
	 * @param bpa0004vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 회원인증 요청
	 */
	@RequestMapping(value = "/api/bppay/authMember", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject authMember(@RequestBody @Valid BPA0004VO bpa0004vo, BindingResult bindingResult) {
		// TODO authMember
        JSONObject result= new JSONObject();

        result = memberService.authMember(bpa0004vo);

        return result;
    }

	/**
	 * @param bpa0005vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 비밀번호 설정
	 */
	@RequestMapping(value = "/api/bppay/setPasswd", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject setPasswd(@RequestBody @Valid BPA0005VO bpa0005vo, BindingResult bindingResult) {
		// TODO setPasswd
        JSONObject result= new JSONObject();

        result = memberService.setPasswd(bpa0005vo);

        return result;
    }

	/**
	 * @param bpa0006vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 회원정보 조회
	 */
	@RequestMapping(value = "/api/bppay/getMemeberInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getMemeberInfo(@RequestBody @Valid BPA0006VO bpa0006vo, BindingResult bindingResult) {
		// TODO getMemeberInfo
        JSONObject result= new JSONObject();

        result = memberService.getMemeberInfo(bpa0006vo);

        return result;
    }

	/**
	 * @param bpa0007vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 회원정보 변경
	 */
	@RequestMapping(value = "/api/bppay/updateMemberInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateMemberInfo(@RequestBody @Valid BPA0007VO bpa0007vo, BindingResult bindingResult) {
		// TODO updateMemberInfo
        JSONObject result= new JSONObject();

        result = memberService.updateMemberInfo(bpa0007vo);

        return result;
    }

	/**
	 * @param bpa0008vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 회원 탈퇴
	 */
	@RequestMapping(value = "/api/bppay/withdrawMember", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject withdrawMember(@RequestBody @Valid BPA0008VO bpa0008vo, BindingResult bindingResult) {
		// TODO withdrawMember
        JSONObject result= new JSONObject();

        result = memberService.withdrawMember(bpa0008vo);

        return result;
    }

	/**
	 * @param bpa0009vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 아이디 찾기
	 */
	@RequestMapping(value = "/api/bppay/findId", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findId(@RequestBody @Valid BPA0009VO bpa0009vo, BindingResult bindingResult) {
		// TODO findId
        JSONObject result= new JSONObject();

        result = memberService.findId(bpa0009vo);

        return result;
    }

	/**
	 * @param bpa0010vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 비밀번호 찾기
	 */
	@RequestMapping(value = "/api/bppay/findPasswd", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findPasswd(@RequestBody @Valid BPA0010VO bpa0010vo, BindingResult bindingResult) {
		// TODO findPasswd
        JSONObject result= new JSONObject();

        result = memberService.findPasswd(bpa0010vo);

        return result;
    }

	/**
	 * @param bpa0011vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 회원토큰 생성
	 */
	@RequestMapping(value = "/api/bppay/getToken", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getToken(@RequestBody @Valid BPA0011VO bpa0011vo, BindingResult bindingResult) {
		// TODO getToken
        JSONObject result= new JSONObject();

        result = memberService.getToken(bpa0011vo);

        return result;
    }

	/**
	 * @param bpa0012vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 회원토큰 인증
	 */
	@RequestMapping(value = "/api/bppay/authToken", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject authToken(@RequestBody @Valid BPA0012VO bpa0012vo, BindingResult bindingResult) {
		// TODO authToken
        JSONObject result= new JSONObject();

        result = memberService.authToken(bpa0012vo);

        return result;
    }

	/**
	 * @param bpa0013vo
	 * @param bindingResult
	 * @return JSONObject
	 * @throws Exception
	 * @description 회원토큰 만료처리
	 */
	@RequestMapping(value = "/api/bppay/expireToken", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject expireToken(@RequestBody @Valid BPA0013VO bpa0013vo, BindingResult bindingResult) {
		// TODO expireToken
        JSONObject result= new JSONObject();

        result = memberService.expireToken(bpa0013vo);

        return result;
    }
}
