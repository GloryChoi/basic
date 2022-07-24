package com.basic.api.board.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.basic.api.common.vo.ApiCommonVO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BPA2004VO extends ApiCommonVO{

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=3, message="validate.size")
	@Pattern(regexp="^(QNA)$", message="validate.pattern")
	private String boardGubun;	//게시판구분코드 QNA : 문의게시판

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=5, message="validate.size")
	private String boardKind;	//게시판분류코드

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=14, message="validate.size")
	private String regDate;	//등록일시

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=1000, message="validate.size")
	private String boardTitle;	//제목

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=5000, message="validate.size")
	private String boardContent;	//내용

	@Size(max=20, message="validate.size")
	private String userNo = "";	//회원번호

	@Size(max=64, message="validate.size")
	private String regNm = "";	//등록자명

	@Size(max=200, message="validate.size")
	private String regEmail = "";	//등록자이메일

	@Size(max=64, message="validate.size")
	private String regTelNo = "";	//등록자연락처

	@Size(max=128, message="validate.size")
	private String passwd = "";	//게시글비밀번호

	@Override
	public String toString() {
		return "BPA2004VO [boardGubun=" + boardGubun + ", boardKind=" + boardKind + ", regDate=" + regDate
				+ ", boardTitle=" + boardTitle + ", boardContent=" + boardContent + ", userNo=" + userNo + ", regNm="
				+ regNm + ", regEmail=" + regEmail + ", regTelNo=" + regTelNo + ", passwd=" + passwd
				+ ", reqSysId=" + getReqSysId() + ", reqSysCode=" + getReqSysCode() + ", reqDate=" + getReqDate() + ", signature=" + getSignature() + "]";
	}
}
