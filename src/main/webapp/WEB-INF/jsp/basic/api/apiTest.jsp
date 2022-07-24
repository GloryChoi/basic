<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>API 테스트 페이지</title>
	<%@include file="/WEB-INF/jsp/basic/common/include/commonHeader.jspf" %>
	<%@include file="/WEB-INF/jsp/basic/common/include/declare.jspf" %>

	<link rel="stylesheet" href="/css/testPage.css">

	<script type="text/javascript">
    	var apiUrl = '${apiUrl}';

        $(document).ready(function() {
        });

        api_datas = [
        	{
        		"API_NM" : "AES256 암호화",
        		"url" : "/api/encryptAes256",
        		"popYn" : "N",
        		"field_IDs" : ["key", "value"],
        		"field_NMs"	: ["*약속된키", "*암호화할 값"],
        		"field_VLs" : ["ABCDEFGHIJKLMNOPQRSTUVWXYZ123456", "1234"]
        	},
        	{
        		"API_NM" : "AES256 복호화",
        		"url" : "/api/decryptAes256",
        		"popYn" : "N",
        		"field_IDs" : ["key", "value"],
        		"field_NMs"	: ["*약속된키", "*복호화할 값"],
        		"field_VLs" : ["ABCDEFGHIJKLMNOPQRSTUVWXYZ123456", ""]
        	},
        	{
        		"API_NM" : "SHA256 암호화",
        		"url" : "/api/encryptSha256",
        		"popYn" : "N",
        		"field_IDs" : ["reqSysId", "reqSysCode","reqDate","hashKey"],
        		"field_NMs"	: ["*인터페이스ID", "*시스템식별코드", "*현재날짜", "*해시키"],
        		"field_VLs" : ["API001", "CYK", "20220519", "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456"]
        	},
        	{
        		"API_NM" : "validTest",
        		"url" : "/api/validTest",
        		"popYn" : "Y",
        		"field_IDs" : ["reqSysId", "reqSysCode","reqDate","signature"],
        		"field_NMs"	: ["*인터페이스ID", "*시스템식별코드", "*현재날짜", "*시그니처키"],
        		"field_VLs" : ["API001", "CYK", "20220519", "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456"]
        	}
        ];
    </script>
</head>
<body onload="init();">
	<div class="top">
		API 테스트 페이지 &nbsp;&nbsp;&nbsp;&nbsp;<select id="sel_apilist" onChange="javascript:doSelChange();"></select>
	</div>
	<header>
		<span id="sp_header"></span>
	</header>
	<form method="POST" id="frmMain" name="frmMain">
		<div class="wrap">
			<div id="dv_contents"></div>
			<div class="flex-last">
<!-- 				<input type="hidden" id="params"/> -->
				<input type="button" id="btnCall" name="btnCall" class="button" value="API 호출" onclick="javascript:doCall();" />
			</div>

			<div>
				<label>결과</label>
			</div>
			<div>
				<label id="result" class="result"></label>
			</div>
		</div>
	</form>
</body>
	<script type="text/javascript">
		function init()
	    {
	    	for(var i = 0; i < api_datas.length; i++) {
	    		tmp_opt = $("<option value='" + i + "'>" + api_datas[i]["API_NM"] + "</option>");
	    		$("#sel_apilist").append(tmp_opt);
	    	}

	    	$( "#sel_apilist" ).trigger( "change" );
	    }

		function doCall()
	    {
	    	var sResult = "";
	    	var idx = $("#sel_apilist option:selected").val();
	    	var popYn = api_datas[idx]["popYn"];

	    	for(var i = 0; i < api_datas[idx]["field_IDs"].length; i++) {
	    		sResult += (api_datas[idx]["field_IDs"][i] + "=" + $("#" + api_datas[idx]["field_IDs"][i]).val() + "&");
	    	}

	    	sResult = sResult.substring(0, sResult.length-1);

	    	$("#params").val(sResult);
	    	var frm = document.frmMain;

	    	if(popYn == "Y"){
	    		var result = window.open("", "result", "width=450, height=800, top=100, scrollbars, resizable, tollbar=no")
		    	frm.action = api_datas[idx]["url"];
	    		frm.target = "result";
		    	frm.submit();
	    	}else{
	    		var data = new FormData(frm);

	    		$.ajax({
	                url: api_datas[idx]["url"],
	                type:"POST",
	                data : sResult,
	                dataType : 'json',
	                success:function(data) {
						$("#result").text(JSON.stringify(data));
	                },
	                error:function(er) {
	                    alert("er : " + er) ;
	                }
	            });
	    	}

	    }

	    function doSelChange()
	    {
	    	var sTmp = "";

	    	var idx = $("#sel_apilist option:selected").val();
	    	var obj_data = api_datas[idx];

	    	$("#sp_header").text("API 호출 : " + obj_data["API_NM"]);

	    	for(var i = 0; i < obj_data["field_IDs"].length; i++)
	    	{
	    		sTmp += "<div class='flex-wrap'>";
	    		sTmp += "<div class='flex-left'>" + obj_data["field_NMs"][i] + "</div>";
	    		sTmp += "<div class='flex-right'><input type='text' name='" + obj_data["field_IDs"][i] + "' id='" + obj_data["field_IDs"][i] + "' size='90%' value='" + obj_data["field_VLs"][i] + "'/></div>";
	    		sTmp += "</div>";
	    	}

	    	$("#dv_contents").html(sTmp);
	    }
	</script>
</html>