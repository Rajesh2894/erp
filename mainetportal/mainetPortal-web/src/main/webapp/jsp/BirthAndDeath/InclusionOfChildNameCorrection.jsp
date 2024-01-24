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
<script type="text/javascript" src="js/birthAndDeath/InclusionOfChildName.js"></script>

<script>
$(document).ready(function() {
    var end = new Date();
    end.setFullYear(2016);
    $("#brDob").datepicker({
        dateFormat : 'dd/mm/yy',
        changeMonth : true,
         changeYear: true,
        yearRange: "-200:+200",
        maxDate : new Date(end.getFullYear(), 11, 31)
    });
});
</script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="Inclusion Of Child Name and Birth Certificate" code="BirthRegDto.inclAndBrCert"/>
				</h2>
				<apptags:helpDoc url="InclusionOfChildName.html"></apptags:helpDoc>			
				
			</div>
			<div class="widget-content padding">
			<form:form id="frmInclusionOfChildName"
				action="InclusionOfChildName.html" method="POST"
				class="form-horizontal" name="InclusionOfChildNameId">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display:none;"></div>
				<%-- <h4>
					<spring:message code="BirthRegDto.ublCountScrn" text="ULB Counter Screen" />
				</h4> --%>
				<div class="form-group padding_top_10 text-red-1" align="center"><spring:message code="birth.data.available" text="" /></div>
				<div class="form-group" style="display:none">
					<apptags:input labelCode="BirthRegDto.brCertNo"
						path="birthRegDto.brManualCertNo" cssClass="hasNumClass form-control" 
						maxlegnth="12">
					</apptags:input>
				</div>
				<div class="form-group" align="center" style="display:none"><spring:message code="birth.death.or" text="OR" /></div>
				<%-- <div class="form-group">
					<apptags:input labelCode="BirthRegDto.applnId"
						path="birthRegDto.applicationId"
						cssClass="hasNumClass form-control" maxlegnth="20">
					</apptags:input>
				</div>
				<div class="form-group" align="center">OR</div> --%>
				<div class="form-group">
					<apptags:input labelCode="BirthRegDto.year" path="birthRegDto.year"
						cssClass="hasNumber form-control" maxlegnth="4">
					</apptags:input>
					<apptags:input labelCode="BirthRegDto.brRegNo"
						path="birthRegDto.brRegNo" cssClass="hasNumClass form-control"
						maxlegnth="20">
					</apptags:input>
				</div>
				<div class="form-group" align="center"><spring:message code="birth.death.or" text="OR" /></div>
					
				<div class="form-group">
						<label class="col-sm-2 control-label required-control">
						<spring:message code="BirthRegistrationDTO.brDob" text="Date of Birth" />
					</label>
                    <div class="col-sm-4">
                        <div class="input-group">
                            <form:input path="birthRegDto.brDob" 
                                cssClass="form-control mandColorClass" id="brDob"
                                readonly="" />
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                        </div>
                    </div>
                   
				<%-- <apptags:date fieldclass="datepicker" labelCode="BirthRegistrationDTO.brDob"
							datePath="birthRegDto.brDob" isMandatory="true"
							cssClass="custDate mandColorClass date">
						</apptags:date> --%>
				</div>
				
				<div class="form-group" align="center"><spring:message code="birth.death.or" text="OR" /></div>
					<div class="form-group">
						<apptags:input labelCode="ParentDetailDTO.pdFathername"
							path="birthRegDto.parentDetailDTO.pdFathername"
							cssClass="hasNameClass form-control" maxlegnth="100">
						</apptags:input>
						<apptags:input labelCode="ParentDetailDTO.pdMothername"
							path="birthRegDto.parentDetailDTO.pdMothername"
							cssClass="hasNameClass form-control" maxlegnth="100">
						</apptags:input>
					</div>

					<div class="text-center">
					<button type="button" class="btn btn-blue-3" title="Search"
						onclick="searchBirthData(this)">
						<i class="fa padding-left-4" aria-hidden="true"></i>
						<spring:message code="BirthRegDto.search"/>
					</button>
					<button type="button" class="btn btn-warning btn-yellow-2"
						title="Reset"
						onclick="window.location.href ='InclusionOfChildName.html'">
						<i class="fa padding-left-4" aria-hidden="true"></i>
						<spring:message code="BirthRegDto.reset"/>
					</button>
					<apptags:backButton url="CitizenHome.html"></apptags:backButton>
				</div>
					
					 <div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="ChildNameCorrDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="BirthRegDto.srNo" text="Sr.No" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegDto.brDob" text="Birth Date" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegDto.brRegNo" text="Registration Number" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegistrationDTO.brRegDate" text="Registration Date" /></th>
									<th width="13%" align="center"><spring:message
											code="ParentDetailDTO.pdFathername" text="Father Name(in English)" /></th>
									<th width="12%" align="center"><spring:message
											code="ParentDetailDTO.pdMothername" text="Mother Name(in English)" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegDto.brSex" text="Sex" /></th>
									<th width="10%" align="center">
										<spring:message code="TbDeathregDTO.cpdRegUnit" text="Registration Unit" />
									</th> 
									
									<th width="10%" align="center">
										<spring:message code="BirthRegistrationDTO.hiId" text="Hospital Name" />
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


