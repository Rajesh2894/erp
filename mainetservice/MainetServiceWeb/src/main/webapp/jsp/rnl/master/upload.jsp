<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/rnl/master/rl-file-upload.js"></script>

<%
	response.setContentType("text/html; charset=utf-8");
%>

			<form method="POST" id="frmLetter" action="EstateMaster.html?upload" enctype="multipart/form-data">

				<fieldset class="formSize">
					<legend><spring:message code="Letter Utility" text="Letter Utility"></spring:message></legend>
					<label class="formLabel"><spring:message code="rnl.master.letter.type" text="Letter Type"></spring:message></label>
					<label class="formLabel"><spring:message code="rnl.upload.file" text="File"/></label>
					<div class="formField">
						<input type="file" name="file" id="file" />
					</div>
				</fieldset>
				<div style="text-align: center; clear: both; padding-top: 10px;">
						<input type="submit" class="button" id="letterSubmit" value="Save">
						<button type="button" id="letterCancel" class="button"><spring:message code="rnl.master.cancele" text="Cancel"></spring:message></button>
			   </div>
                 
			</form>
	