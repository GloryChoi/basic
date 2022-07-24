package com.basic.api.member.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import com.basic.common.model.ParamMap;


@Mapper("memberMapper")
public interface MemberMapper {

	List<EgovMap> selectRpTbPayAgreeList(ParamMap paramMap);

	EgovMap selectRpTbUser(ParamMap paramMap);

	void insertRpTbUser(ParamMap paramMap);

	void insertRpTbUserDevice(ParamMap paramMap);

	void insertRpTbUserAgree(ParamMap paramMap);

	void updateRpTbUserPasswdErrCnt(ParamMap paramMap);

	int updateRpTbUserPasswd(ParamMap paramMap);

	int updateRpTbUserInfo(ParamMap paramMap);

	int updateRpTbUserDevice(ParamMap paramMap);

	int updateRpTbUserWithdraw(ParamMap paramMap);

	void deleteRpTbUserDevice(ParamMap paramMap);

	List<EgovMap> selectRpTbUserIdList(ParamMap paramMap);

	int updateRpTbUserNotiYn(ParamMap paramMap);

	EgovMap selectRpTbUserToken(ParamMap paramMap);

	int insertRpTbUserToken(ParamMap paramMap);

	int updateRpTbUserToken(ParamMap paramMap);

	int updateRpTbUserTokenDup(ParamMap paramMap);

}
