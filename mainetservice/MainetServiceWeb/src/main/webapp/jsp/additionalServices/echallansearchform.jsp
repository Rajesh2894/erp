<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/additionalServices/eChallanSearchForm.js"></script>
<style>
.thumbnail img {
		height: 150px;
		width: 150px;
	}
  .myTable .btn-primary{
    width: 50%;
  }
  /* .widget .widget-header h2{
    background: #02386d;
    color: #FFF;
  } */
 /*  .widget{
    border: 2px solid #02386d;
  } */
  .form-horizontal h4{
    border-left: 3px solid #ed502e;
  }
  .widget table tr th {
    background: #157eb8;
    font-size: 0.95em;
    color: #fff;
    padding: 1rem 0rem;
  }
</style>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
	
		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
				class="fa fa-question-circle fa-lg"></i> 
				<span class="hide">
					<spring:message code="EChallan.help" text="Help" />
				</span>
			</a>
		</div>
		
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="EChallan.challanEntry" text="E-Challan Summary" />
				</h2>
			</div>
			<div class="widget-content padding">
				<form:form action="EChallanEntry.html" method="POST" class="form-horizontal" id="eChallanSummaryForm" commandName="command" >
					
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div class="error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display: none;">
							<button type="button" class="close" onclick="closeOutErrBox()"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span id="errorId"></span>
						</div>
						
					<h4><spring:message code="EChallan.challanEntryDetails" text="Challan Entry and Details" /></h4>
					<div class="form-group">
						<label for="text-1" class="col-sm-2 control-label ">
							<spring:message code="EChallan.challanNo" text="Challan No." />
						</label>
						<div class="col-sm-4 margin-top-5">
							<spring:message code="EChallan.enter.challanNo" text="Enter Challan No." var="enterChallanNo" />
							<form:input type="text" class="form-control hasNumber" name="challanNo" id="challanNo" maxlength="10" minlength="10"
							path="challanMasterDto.challanNo" value="" placeholder="${enterChallanNo}" />
						</div>
						
						<%-- <label for="text-1" class="col-sm-2 control-label ">
							<spring:message code="EChallan.raidNumber" text="Raid Number" />
						</label>
						<div class="col-sm-4 margin-top-5">
							<spring:message code="EChallan.enter.raidNo" text="Enter Raid No." var="enterRaidNo" />
							<form:input type="text" class="form-control hasNumber" maxLength="10" name="raidNo" id="raidNo" 
								path="challanMasterDto.raidNo" value="" placeholder="${enterRaidNo}" />
						</div> --%>
						
					</div>
					<div class="form-group">
						
						<apptags:date fieldclass="datepicker"
								labelCode="EChallan.fromDate" readonly="true"
								datePath="challanMasterDto.challanFromDate">
						</apptags:date>
						
						<apptags:date fieldclass="datepicker"
								labelCode="EChallan.toDate" readonly="true"
								datePath="challanMasterDto.challanToDate">
						</apptags:date>
						
					</div>
					
					<div class="form-group">
						<label for="text-1" class="col-sm-2 control-label ">
							<spring:message code="EChallan.mobNo" text="Mobile Number" />
						</label>
						<div class="col-sm-4">
							<spring:message code="EChallan.enter.offenderMobNo" text="Enter Offender Mobile Number" var="enterOffenderMobNo" />
							<form:input type="text" class="form-control hasNumber" name="offenderMobNo" id="offenderMobNo" 
										path="challanMasterDto.offenderMobNo" maxlength="10" minlength="10"
										value="" placeholder="${enterOffenderMobNo}" />
						</div>
						
						<label for="text-1"
								class="col-sm-2 control-label"> <spring:message
									code="EChallan.offenderName" text="Offender Name" />
						</label>
						<div class="col-sm-4">
							<spring:message code="EChallan.enter.offenderName" text="Enter the Offender Name" var="enterOffenderName" />
							<form:input type="text" class="form-control hasCharacter"
									    id="offenderName" path="challanMasterDto.offenderName" 
									    placeholder="${enterOffenderName}" />
						</div>
					</div>

					<div class="text-center">
						<button type="button" onclick="searchChallanData('EChallanEntry.html','searchChallanDetails');" 
								class="btn btn-success" title="<spring:message code="EChallan.search" text="Search" />" >
								<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
								<spring:message code="EChallan.search" text="Search" />
						</button>
						
						<button type="button" onclick="resetForm(this);" 
								class="btn btn-warning" title="<spring:message code="EChallan.reset" text="Reset" />" >
								<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
								<spring:message code="EChallan.reset" text="Reset" />
						</button>
						<button type="submit" onclick="addNewPage('EChallanEntry.html','AddForm');"
								class="btn btn-blue-2" title="<spring:message code="EChallan.add" text="Add" />" >
								<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
								<spring:message code="EChallan.add" text="Add" />
						</button>
					</div>

          <h4><spring:message code="EChallan.challanDetails" text="Challan Details" /></h4>
          		<div class="table-responsive">
			          <table class="table table-bordered margin-bottom-10" id="datatables">
			            <thead>
			              <tr>
			              <th class="text-center"><spring:message code="EChallan.srno" text="Sr No." /></th>
			                <th class="text-center"><spring:message code="EChallan.challanNo" text="Challan No." /></th>
			                <th class="text-center"><spring:message code="EChallan.challanDate" text="Challan Date" /></th>
			                <th class="text-center"><spring:message code="EChallan.offenderName" text="Offender Name" /></th>
			                <th class="text-center" style="width:200px;"><spring:message code="EChallan.violationPurpose" text="Violation Purpose" /></th>
			                <th class="text-center"><spring:message code="EChallan.mobNo" text="Mobile Number" /></th>
			                <th class="text-center"><spring:message code="EChallan.action" text="Action" /></th>
			              </tr>
			            </thead>
			            <tbody>
			            	<c:forEach items="${command.challanMasterDtoList}" var="data" varStatus="loop">
			              <tr>
			              	<td class="text-center"><c:out value="${loop.index + 1}" ></c:out></td>
			                <td class="text-center">${data.challanNo}</td>
			                <td class="text-center">${data.datechallan}</td>
			                <td class="text-center">${data.offenderName}</td>
			                <td class="text-center">${data.challanDesc}</td>
			                <td class="text-center">${data.offenderMobNo}</td>
			                <td class="text-center">
			                  <button type="button" class="btn btn-blue-3 btn-sm margin-right-5" 
			                  		  title="<spring:message code="EChallan.view" text="View" />"
			                  		  onclick="getActionForDefination(${data.challanId},'V');">
			                  		  <i class="fa fa-eye"></i>
			                  </button>
			                  <button onclick="printContent(${data.challanId});"
									class="btn btn-danger btn-sm btn-sm" title="<spring:message code="EChallan.print" text="Print" />">
									<i class="fa fa-print" aria-hidden="true"></i>
									
							</button>
			                </td>
			              </tr>
			              <c:set var="e" value="${e + 1}" scope="page" />							
						  </c:forEach>
			            </tbody>
			          </table>
			       </div>
				</form:form>
			</div>
		</div>					
	</div>	
</div>