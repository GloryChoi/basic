package com.basic.api.member.service;

import org.json.simple.JSONObject;

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

public interface MemberService {

	JSONObject getAgreeList(BPA0001VO bpa0001vo);

	JSONObject checkUserId(BPA0002VO bpa0002vo);

	JSONObject joinMember(BPA0003VO bpa0003vo);

	JSONObject authMember(BPA0004VO bpa0004vo);

	JSONObject setPasswd(BPA0005VO bpa0005vo);

	JSONObject getMemeberInfo(BPA0006VO bpa0006vo);

	JSONObject updateMemberInfo(BPA0007VO bpa0007vo);

	JSONObject withdrawMember(BPA0008VO bpa0008vo);

	JSONObject findId(BPA0009VO bpa0009vo);

	JSONObject findPasswd(BPA0010VO bpa0010vo);

	JSONObject getToken(BPA0011VO bpa0011vo);

	JSONObject authToken(BPA0012VO bpa0012vo);

	JSONObject expireToken(BPA0013VO bpa0013vo);

}
