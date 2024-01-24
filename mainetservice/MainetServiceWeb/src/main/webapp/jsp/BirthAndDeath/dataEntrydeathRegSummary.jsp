<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/birthAndDeath/dataEntryForDeathReg.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<script>

	$(document).ready(function() {
	    var end = new Date();
	    end.setFullYear(2016);
	    $("#drDod").datepicker({
	        dateFormat : 'dd/mm/yy',
	        changeMonth : true,
	         changeYear: true,
	        yearRange: "-200:+200",
	        maxDate : new Date(end.getFullYear(), 11, 31)
	    });
	});


</script>

<html>


<div class="pagediv">

	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="dataentry.deathRegForm" text="Death Registration Data Entry Form" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form id="frmDeathRegistrationCorrection"
					action="dataEntryForDeathReg.html" method="POST"
					class="form-horizontal" name="deathRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="${deathRegDTO.drId}"
						cssClass="hasNumber form-control" id="drId" />

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<h4>
						<spring:message code="dataentry.deathReg" text="Death Registration" />
					</h4>
					<div class="form-group" style="display:none">
						<apptags:input labelCode="BirthRegDto.brCertNo"
							path="tbDeathregDTO.drCertNo" isMandatory="false"
							cssClass="hasNumClass form-control" maxlegnth="12">
						</apptags:input>
					</div>
						<div class="form-group" align="center" style="display:none"><spring:message code="birth.death.or" text="OR" /></div>
						<div class="form-group padding_top_10 text-red-1" align="center"><spring:message code="birth.data.available" text="" /></div>
					<div class="form-group">
					<apptags:input labelCode="BirthRegDto.year"
						path="tbDeathregDTO.year" cssClass="hasNumber form-control"
						maxlegnth="4">
					</apptags:input>
						
					<apptags:input labelCode="TbDeathregDTO.drRegno" maxlegnth="20"
						path="tbDeathregDTO.drRegno" cssClass="hasNumClass form-control">
					</apptags:input>
				 </div>
				
				<div class="form-group" align="center"><spring:message code="birth.death.or" text="OR" /></div>
					<div class="form-group">
					
					<label class="col-sm-2 control-label">
						<spring:message code="TbDeathregDTO.drDod" text="Date of Death" />
					</label>
					<div class="col-sm-4">
					    <div class="input-group">
					        <form:input path="tbDeathregDTO.drDod"
					            cssClass="form-control mandColorClass" id="drDod"
					           readonly="true" />
					        <div class="input-group-addon">
					            <i class="fa fa-calendar"></i>
					        </div>
					    </div>
					</div>
				
					<apptags:input labelCode="TbDeathregDTO.drDeceasedname"
						path="tbDeathregDTO.drDeceasedname" 
						cssClass="hasNameClass form-control">
					</apptags:input>
				 </div>
			<div class="form-group" align="center"><spring:message code="birth.death.or" text="OR" /></div>
					<div class="form-group">
						<label class="control-label col-sm-2 "
							for="Census"> <spring:message
								code="TbDeathregDTO.cpdDeathcauseId" text="Death Cause" />
						</label>
						<c:set var="baseLookupCode" value="DCA" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="tbDeathregDTO.cpdDeathcauseId" cssClass="form-control"
							 hasId="true"
							selectOptionLabelCode="selectdropdown" />

						<label class="control-label col-sm-2 "
							for="Census"> <spring:message code="TbDeathregDTO.drSex"
								text="Gender" />
						</label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="tbDeathregDTO.drSex" cssClass="form-control"
							 hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>


					<div class="text-center" align="center">
						<button type="button" align="center" class="btn btn-blue-3" title="Search"
							onclick="SearchDeathData(this)">
							<spring:message code="BirthRegDto.search"/>
						</button>
						<button type="button" align="center" class="btn btn-warning btn-yellow-2"
							title="Submit"
							onclick="window.location.href ='dataEntryForDeathReg.html'">
							<spring:message code="BirthRegDto.reset"/>
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
					
						<!-- grid search -->
					
					 <div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="deathregDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="BirthRegDto.srNo" text="Sr.No" /></th>
									<th width="15%" align="center"><spring:message
											code="TbDeathregDTO.drDod" text="Deceased Date" /></th>
									<th width="12%" align="center"><spring:message
											code="TbDeathregDTO.drRegno" text="Registration Number" /></th>
									<th width="12%" align="center">
										<spring:message code="TbDeathregDTO.drRegdate" text="Registration Date" />
									</th>
									<th width="15%" align="center"><spring:message
											code="TbDeathregDTO.drDeceasedname" text="Deceased Name" /></th>
									<th width="10%" align="center"><spring:message
											code="TbDeathregDTO.drSex" text="Sex" /></th>
									<th width="10%" align="center">
										<spring:message code="TbDeathregDTO.cpdRegUnit" text="Reg. Unit" />
									</th>
									<th width="10%" align="center"><spring:message
											code="TbDeathregDTO.form.action" text="Action" /></th>
								</tr>
							</thead>
							
						</table>
					</div>
			</form:form>

			</div>

		</div>

	</div>
</div>


</html>








 
 
 