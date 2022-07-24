package com.basic.api.member.vo;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.basic.api.common.vo.ApiCommonVO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BPA0003VO extends ApiCommonVO{

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=50, message="validate.size")
	private String userId;			//회원아이디

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=128, message="validate.size")
	private String userCi;			//회원Ci

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=216, message="validate.size")
	private String userNm;			//회원명

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=44, message="validate.size")
	private String passwd;			//회원비밀번호

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=8, message="validate.size")
	private String birthDt;			//생년월일

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=1, message="validate.size")
	@Pattern(regexp="^(M|F)$", message="validate.pattern")
	private String sexCd;			//성별

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=1, message="validate.size")
	@Pattern(regexp="^(Y|N)$", message="validate.pattern")
	private String domesticYn;		//내국인여부

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=256, message="validate.size")
	private String email = "";			//이메일주소

    @NotNull(message="validate.required.value")
    @NotEmpty(message="validate.required.value")
    @Size(max=3, message="validate.size")
    @Pattern(regexp="^(SKT|LGT|KTF|SKR|LGR|KTR)$", message="validate.pattern")
	private String userMobileCd;	//휴대폰통신사

    @NotNull(message="validate.required.value")
    @NotEmpty(message="validate.required.value")
    @Size(max=24, message="validate.size")
	private String userMobile;		//휴대폰번호

    @NotNull(message="validate.required.value")
    @NotEmpty(message="validate.required.value")
    @Size(max=1, message="validate.size")
	private String joinMethodCd;	//회원가입방법

    @Size(max=500, message="validate.size")
	private String joinMethodDet = "";	//회원가입방법상세

    @Size(max=100, message="validate.size")
	private String joinSnsKey = "";		//SNS회원참조키

    @Size(max=300, message="validate.size")
	private String mobileUuid = "";			//모바일기기고유번호

    @Size(max=3, message="validate.size")
    @Pattern(regexp="^(|AND|IOS)$", message="validate.pattern")
	private String mobileOsCd = "";			//모바일기기타입

    @NotNull(message="validate.required.value")
    @NotEmpty(message="validate.required.value")
    @Size(max=11, message="validate.size")
	private List<AgreeVO> agreeList;//약관동의목록

	@Override
	public String toString() {
		return "BPA0003VO [userId=" + userId + ", userCi=" + userCi + ", userNm=" + userNm + ", passwd=" + passwd
				+ ", birthDt=" + birthDt + ", sexCd=" + sexCd + ", domesticYn=" + domesticYn + ", email=" + email
				+ ", userMobileCd=" + userMobileCd + ", userMobile=" + userMobile + ", joinMethodCd=" + joinMethodCd
				+ ", joinMethodDet=" + joinMethodDet + ", joinSnsKey=" + joinSnsKey + ", mobileUuid=" + mobileUuid
				+ ", mobileOsCd=" + mobileOsCd + ", agreeList=" + agreeList
				+ ", reqSysId=" + getReqSysId() + ", reqSysCode=" + getReqSysCode() + ", reqDate=" + getReqDate() + ", signature=" + getSignature() + "]";
	}

}
