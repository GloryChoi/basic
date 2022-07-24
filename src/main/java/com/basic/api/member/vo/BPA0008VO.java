package com.basic.api.member.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.basic.api.common.vo.ApiCommonVO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BPA0008VO extends ApiCommonVO{

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=20, message="validate.size")
	private String userNo;	//회원번호

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=2, message="validate.size")
	private String withdrawCd;	//회원탈퇴사유코드

    @Size(max=500, message="validate.size")
	private String withdrawDet;	//회원탈퇴사유상세

	@Override
	public String toString() {
		return "BPA0008VO [userNo=" + userNo + ", withdrawCd=" + withdrawCd + ", withdrawDet=" + withdrawDet
				+ ", reqSysId=" + getReqSysId() + ", reqSysCode=" + getReqSysCode() + ", reqDate=" + getReqDate() + ", signature=" + getSignature() + "]";
	}

}
