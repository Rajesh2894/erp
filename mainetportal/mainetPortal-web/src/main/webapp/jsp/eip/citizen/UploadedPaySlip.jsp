<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>



<c:if test="${not empty command.uploadedPath}">
	<div class="form-group">

		<div class="col-sm-4">
			<label class="control-label">Download : </label>  <apptags:filedownload
					filename="${command.attFname}"
					filePath="${command.uploadedPath}"
					actionUrl="PaySlipGeneration.html?Download"></apptags:filedownload>
		</div>
	</div>
</c:if>
