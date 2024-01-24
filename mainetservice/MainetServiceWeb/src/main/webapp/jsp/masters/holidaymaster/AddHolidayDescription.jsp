<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<div class="widget ">
	<div class="widget-header">
		<h2><spring:message code="holidaymaster.changeholidaydescription"/></h2>
	</div>
	<div class="widget-content padding">
		<form:form action="HolidayMaster.html" method="post"
			class="form-horizontal" name="holidaymaster"
			id="AddHolidayDescription">
			<div
				class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDivdiscription"></div>
			<div class="form-group">
				<div class="col-sm-12 ">
					<form:input path="holidayMasterEntity.hoDescription"
						class="form-control" id="hoDescription" maxlength="100" />
				</div>
				<div class="col-sm-5">
					<div class="row">

						<form:input path="holidayMasterEntity.hoDate" type="hidden"
							id="hoDate" />
					</div>
				</div>

				<div class="text-center clear padding-10">
					<button type="button" class="button-input btn btn-success"
						name="button-1509602301770" value="Save" style="" id=""
						onclick="saveHolidayDescription();"><spring:message code="holidaymaster.savechanges"/></button>
					<c:if test="${command.mode eq 'A'}">
						<button type="button" class="button-input btn btn-danger" name=""
							value="" style="" onclick="editHolidayDescription();"><spring:message code="holidaymaster.remove"/></button>
					</c:if>
				</div>
		</form:form>
	</div>
</div>
