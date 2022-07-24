package com.basic.api.member.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.basic.api.common.vo.ApiCommonVO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BPA0010VO extends ApiCommonVO{

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=216, message="validate.size")
	private String userNm;			//회원명

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=256, message="validate.size")
	private String email = "";			//이메일주소

    @NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=50, message="validate.size")
	private String userId;			//회원아이디

	@Override
	public String toString() {
		return "BPA0010VO [userNm=" + userNm + ", email=" + email + ", userId=" + userId
				+ ", reqSysId=" + getReqSysId() + ", reqSysCode=" + getReqSysCode() + ", reqDate=" + getReqDate() + ", signature=" + getSignature() + "]";
	}

}
