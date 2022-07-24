package com.basic.api.common.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BPA2001VO extends ApiCommonVO{

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=5, message="validate.size")
	@Pattern(regexp="^(U3009|U3010|U3011)$", message="validate.pattern")
	private String codeId;

    @Size(max=20, message="validate.size")
	private String codeCd;

	@Override
	public String toString() {
		return "BPA2001VO [codeId=" + codeId + ", codeCd=" + codeCd
				+ ", reqSysId=" + getReqSysId() + ", reqSysCode=" + getReqSysCode() + ", reqDate=" + getReqDate() + ", signature=" + getSignature() + "]";
	}
}
