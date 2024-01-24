<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/ckeditor/ckeditor.js"></script>
<script src="assets/libs/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" src="js/cfc/scrutiny.js"></script>
<script type="text/javascript">


$(document)
.ready(
    function() 
    {
    	 $('#String1').ckeditor({skin : 'bootstrapck'});
    });
</script>


<form:form action="" cssClass="form-horizontal">
	<div class="form-group" style="padding: 0rem 0.5rem">
	<form:hidden path="slLabelId" cssClass="form-control mandColorClass" id="slLabelId" />
	<form:hidden path="levels" cssClass="form-control mandColorClass" id="levels" />

		<div style="width: 150%;">
			<form:textarea id="String1" path="" />
		</div>

		<div class="text-center padding-top-10"> 
			<input type="button" class="btn btn-success" onclick="submitRemark(this)"
				value="<spring:message code="scrutiny.submit" />" />
		 <input type="button" class="btn btn-danger"
				onclick="closePopup()" value="<spring:message code="back.msg" />">

		</div>
	</div>

</form:form>