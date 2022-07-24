package com.basic.api.member.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.basic.api.common.vo.ApiCommonVO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BPA0002VO extends ApiCommonVO{

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=50, message="validate.size")
	private String userId;	//회원아이디

	@Override
	public String toString() {
		return "BPA0002VO [userId=" + userId
				+ ", reqSysId=" + getReqSysId() + ", reqSysCode=" + getReqSysCode() + ", reqDate=" + getReqDate() + ", signature=" + getSignature() + "]";
	}


}
