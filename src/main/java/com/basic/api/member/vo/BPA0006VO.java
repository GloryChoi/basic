package com.basic.api.member.vo;

import javax.validation.constraints.Size;

import com.basic.api.common.vo.ApiCommonVO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BPA0006VO extends ApiCommonVO{

    @Size(max=128, message="validate.size")
	private String userCi = "";			//회원Ci

    @Size(max=20, message="validate.size")
	private String userNo = "";	//회원번호

	@Override
	public String toString() {
		return "BPA0006VO [userCi=" + userCi + ", userNo=" + userNo
				+ ", reqSysId=" + getReqSysId() + ", reqSysCode=" + getReqSysCode() + ", reqDate=" + getReqDate() + ", signature=" + getSignature() + "]";
	}

}
