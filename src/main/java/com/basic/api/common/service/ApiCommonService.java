package com.basic.api.common.service;

import org.json.simple.JSONObject;

import com.basic.api.common.vo.BPA2001VO;



public interface ApiCommonService {

	JSONObject getCodeData(BPA2001VO bpa2001vo);
}
