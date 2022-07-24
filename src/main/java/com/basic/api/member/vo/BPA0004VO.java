package com.basic.api.member.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.basic.api.common.vo.ApiCommonVO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BPA0004VO extends ApiCommonVO{

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=1, message="validate.size")
	@Pattern(regexp="^(1|2)$", message="validate.pattern")
	private String jobCd;	//요청업무구분 1:로그인비밀번호, 2:캐시결제비밀번호

    @Size(max=20, message="validate.size")
	private String userNo = "";	//회원번호

    @Size(max=50, message="validate.size")
	private String userId = "";	//회원아이디

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=44, message="validate.size")
	private String userPasswd;	//비밀번호

	@Override
	public String toString() {
		return "BPA0004VO [jobCd=" + jobCd + ", userNo=" + userNo + ", userId=" + userId + ", userPasswd=" + userPasswd
				+ ", reqSysId=" + getReqSysId() + ", reqSysCode=" + getReqSysCode() + ", reqDate=" + getReqDate() + ", signature=" + getSignature() + "]";
	}

}
