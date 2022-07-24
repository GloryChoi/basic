package com.basic.api.member.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.basic.api.common.vo.ApiCommonVO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BPA0013VO extends ApiCommonVO{

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
	@Size(max=40, message="validate.size")
	private String userNo = "";	//회원번호

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
	@Size(max=80, message="validate.size")
	private String userToken = "";	//회원토큰

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
	@Size(max=2, message="validate.size")
	@Pattern(regexp="^(PC|MO|AP)$", message="validate.pattern")
	private String deviceTypeCd = "";	//접속기기구분

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
	@Size(max=50, message="validate.size")
	private String ipAddr = "";	//접속IP주소

	@Size(max=300, message="validate.size")
	private String mobileUuid = "";	//기기UUID

	@Override
	public String toString() {
		return "BPA0013VO [userNo=" + userNo + ", userToken=" + userToken + ", deviceTypeCd=" + deviceTypeCd
				+ ", ipAddr=" + ipAddr + ", mobileUuid=" + mobileUuid
				+ ", reqSysId=" + getReqSysId() + ", reqSysCode=" + getReqSysCode() + ", reqDate=" + getReqDate() + ", signature=" + getSignature() + "]";
	}

}
