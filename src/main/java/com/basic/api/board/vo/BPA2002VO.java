package com.basic.api.board.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.basic.api.common.vo.ApiCommonVO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BPA2002VO extends ApiCommonVO{

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=3, message="validate.size")
//	@Pattern(regexp="^(FAQ|QNA|COM)$", message="validate.pattern")
	private String boardGubun;	//게시판구분코드 FAQ : FAQ, QNA : 문의게시판, COM : 공지사항

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=5, message="validate.size")
//	@Pattern(regexp="^(FAQ|QNA|COM)$", message="validate.pattern")
	private String boardKind;	//게시판분류코드

    @Size(max=8, message="validate.size")
	private String startDt = "";	//게시물등록시작일

    @Size(max=8, message="validate.size")
	private String endDt = "";	//게시물등록종료일

    @NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=10, message="validate.size")
    @Pattern(regexp="^(Y|N)$", message="validate.pattern")
	private String dateApplyYn;	//게시기간적용여부

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=10, message="validate.size")
	private String pageNo;	//페이지번호

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=10, message="validate.size")
	private String pageListCnt;	//페이지당 목록 건수

    @Size(max=20, message="validate.size")
	private String userNo = "";	//회원번호

    @Size(max=1, message="validate.size")
	private String answerYn = "";	//답변여부

    @Size(max=50, message="validate.size")
	private String searchTerm = "";	//검색어

	@Override
	public String toString() {
		return "BPA2002VO [boardGubun=" + boardGubun + ", boardKind=" + boardKind + ", startDt=" + startDt + ", endDt="
				+ endDt + ", dateApplyYn=" + dateApplyYn + ", pageNo=" + pageNo + ", pageListCnt=" + pageListCnt
				+ ", userNo=" + userNo + ", answerYn=" + answerYn + ", searchTerm=" + searchTerm
				+ ", reqSysId=" + getReqSysId() + ", reqSysCode=" + getReqSysCode() + ", reqDate=" + getReqDate() + ", signature=" + getSignature() + "]";
	}
}
