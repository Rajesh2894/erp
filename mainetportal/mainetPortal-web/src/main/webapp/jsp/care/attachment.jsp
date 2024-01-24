<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/care/attachment.js"></script>


	<c:set var="count" value="${param.myVar}" scope="page" />
	<!--  <div class="table-responsive" > -->
	<form:form name="attachmentForm23" id="attachmentForm23235" action="grievance.html">
	<table class="table table-bordered table-striped" id="customFields">
		<tbody class="appendableClass">
			<tr>
				<th width="10%">Sr. No.</th>
				<th width="25%">Document Name</th>
				<th width="30%">File Type<span class="mand">*</span></th>
				<th width="25%">Upload</th>
				<th width="10%">Action</th>
			</tr>
		</tbody>
	</table>
	<!--  </div>   -->
	
</form:form>


