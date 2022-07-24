package com.basic.api.member.vo;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.basic.api.common.vo.ApiCommonVO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BPA0007VO extends ApiCommonVO{

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=1, message="validate.size")
	@Pattern(regexp="^(1|2|3|4|5|6|7)$", message="validate.pattern")
	private String jobCd;	//요청업무구분 1:휴대폰번호변경, 2:이메일주소변경, 3:현금영수증 설정 정보 변경, 4:기기정보등록/변경, 5:결제알림설정, 6:로그인알림설정, 7:서비스알림설정

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=20, message="validate.size")
	private String userNo;	//회원번호

    @Size(max=3, message="validate.size")
    @Pattern(regexp="^(|SKT|LGT|KTF|SKR|LGR|KTR)$", message="validate.pattern")
	private String userMobileCd = "";	//휴대폰통신사

    @Size(max=24, message="validate.size")
	private String userMobile = "";		//휴대폰번호

    @Size(max=256, message="validate.size")
	private String email = "";			//이메일주소

    @Size(max=1, message="validate.size")
    @Pattern(regexp="^(|Y|N)$", message="validate.pattern")
	private String cashReceiptAutoYn = "";			//현금영수증자동발행여부

    @Size(max=1, message="validate.size")
    @Pattern(regexp="^(|1|2)$", message="validate.pattern")
	private String cashReceiptCd = "";			//현금영수증구분 1:소득공제, 2:지출증빙

    @Size(max=1, message="validate.size")
    @Pattern(regexp="^(|H|C|B)$", message="validate.pattern")
	private String cashReceiptNoCd = "";			//현금영수증발급번호구분 H:휴대폰번호, C:카드번호, B:사업자번호

    @Size(max=44, message="validate.size")
	private String cashReceiptNo = "";			//현금영수증발급번호

    @Size(max=300, message="validate.size")
	private String mobileUuid = "";			//모바일기기고유번호

    @Size(max=3, message="validate.size")
    @Pattern(regexp="^(|AND|IOS)$", message="validate.pattern")
	private String mobileOsCd = "";			//모바일기기타입

    @Size(max=1, message="validate.size")
    @Pattern(regexp="^(|Y|N)$", message="validate.pattern")
	private String notiYn = "";			//알림여부

	@Override
	public String toString() {
		return "BPA0007VO [jobCd=" + jobCd + ", userNo=" + userNo + ", userMobileCd=" + userMobileCd + ", userMobile="
				+ userMobile + ", email=" + email + ", cashReceiptAutoYn=" + cashReceiptAutoYn + ", cashReceiptCd="
				+ cashReceiptCd + ", cashReceiptNoCd=" + cashReceiptNoCd + ", cashReceiptNo=" + cashReceiptNo
				+ ", mobileUuid=" + mobileUuid + ", mobileOsCd=" + mobileOsCd + ", notiYn=" + notiYn
				+ ", reqSysId=" + getReqSysId() + ", reqSysCode=" + getReqSysCode() + ", reqDate=" + getReqDate() + ", signature=" + getSignature() + "]";
	}

}
