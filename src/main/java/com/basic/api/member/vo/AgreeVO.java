package com.basic.api.member.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AgreeVO {

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=5, message="validate.size")
	private String agreeNo;		//약관번호

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=5, message="validate.size")
	private String agreeVerNo;	//약관버전번호

	@NotNull(message="validate.required.value")
	@NotEmpty(message="validate.required.value")
    @Size(max=1, message="validate.size")
	private String agreeYn;		//동의여부구분

	@Override
	public String toString() {
		return "AgreeVO [agreeNo=" + agreeNo + ", agreeVerNo=" + agreeVerNo + ", agreeYn=" + agreeYn + "]";
	}
}
