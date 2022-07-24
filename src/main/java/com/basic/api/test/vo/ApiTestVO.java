package com.basic.api.test.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ApiTestVO {

    @NotNull(message="validate.required.value")
    @NotEmpty(message="validate.required.value")
    @Size(max=7, message="validate.size")
    private String reqSysId;	//인터페이스ID

    @NotNull(message="validate.required.value")
    @NotEmpty(message="validate.required.value")
    @Size(max=3, message="validate.size")
    @Pattern(regexp="^(BPF|BPA|BPI|BPM|INI|WPY|TMN|MER)$", message="validate.pattern")
    private String reqSysCode;	//요청시스템코드

    @NotNull(message="validate.required.value")
    @NotEmpty(message="validate.required.value")
    @Size(max=14, message="validate.size")
    @Pattern(regexp="\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}\\d{2}", message="validate.pattern")
    private String reqDate;		//요청일시

    @NotNull(message="validate.required.value")
    @NotEmpty(message="validate.required.value")
    @Size(max=512, message="validate.size")
    private String signature;	//서명값

	@Override
	public String toString() {
		return "ApiTestVO [reqSysId=" + reqSysId + ", reqSysCode=" + reqSysCode + ", reqDate=" + reqDate
				+ ", signature=" + signature + "]";
	}
}
