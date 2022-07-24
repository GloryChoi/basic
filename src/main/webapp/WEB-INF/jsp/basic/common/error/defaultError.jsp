<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Error</title>
	<%@include file="/WEB-INF/jsp/basic/common/include/commonHeader.jspf" %>
	<%@include file="/WEB-INF/jsp/basic/common/include/declare.jspf" %>

	<script type="text/javascript">
        $(document).ready(function() {
        	var resultCode = '${resultCode}';
        	var resultMsg = '${resultMsg}';
        	var returnUrl = '${returnUrl}';

        	if(resultCode != ''){
        		$('#body').text(resultCode + ' : ' + resultMsg);
        	}

        	if(resultCode != "" && returnUrl != ""){
        		//리턴URL로 전송  submit 필요
            	$("#frmReturn").attr('action', returnUrl);
             	$("#frmReturn").submit();
        	}
        });
    </script>
</head>

<body id="body">
    	일시적인 오류가 발생 하였습니다. 지속적으로 발생시 고객센터에 문의 바랍니다.
    <form id="frmReturn">
    	<input type="hidden" id="resultCode" name="resultCode" value="${resultCode}"></input>
    	<input type="hidden" id="resultMsg" name="resultMsg" value="${resultMsg}"></input>
    </form>
</body>
</html>
