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
<script type="text/javascript" src="js/additionalServices/echallanPayment.js"></script>
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
<script>
function getActionForDefination(challanId, mode) {
	var divName = '.content-page';
    var requestData = {
	"saveMode" : mode,
	"challanId" : challanId
    };
    var ajaxResponse = doAjaxLoading('EChallanPayment.html?ViewForm', requestData,
    	    'html', divName);
        $(divName).removeClass('ajaxloader');
        $(divName).html(ajaxResponse);
        prepareTags();
}
</script>
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
					<spring:message code="EChallan.challanUlbCounter" text="Challan ULB Counter" />
				</h2>
			</div>
			<div class="widget-content padding">
				<form:form action="EChallanPayment.html" method="POST" class="form-horizontal" id="eChallanPaymentSummary" commandName="command" >
					
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div class="error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display: none;">
							<button type="button" class="close" onclick="closeOutErrBox()"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<span id="errorId"></span>
						</div>
						
					<h4><spring:message code="EChallan.searchByRaidNo" text="Search By Raid No." /></h4>
					
					<div class="form-group">
					
						<label for="text-1" class="col-sm-2 control-label ">
							<spring:message code="EChallan.raidNumber" text="Raid Number" />
						</label>
						<div class="col-sm-3">
							<spring:message code="EChallan.enter.raidNo" text="Enter Raid No." var="enterRaidNo" />
							<form:input type="text" class="form-control hasNumber" maxLength="10" name="raidNo" id="raidNo" 
							path="challanMasterDto.raidNo" value="" placeholder="${enterRaidNo}" />
						</div>
						
						<span class="text-info col-sm-2 padding-top-10 text-center">
							<spring:message code="EChallan.or" text="OR"/></span>
						
						<label for="text-1" class="col-sm-2 control-label ">
							<spring:message code="EChallan.offenderMobNo" text="Mobile Number" />
						</label>
						<div class="col-sm-3">
							<spring:message code="EChallan.enter.offenderMobNo" text="Enter Offender Mobile Number" var="enterOffenderMobNo" />
							<form:input type="text" class="form-control hasNumber" name="offenderMobNo" id="offenderMobNo" 
										path="challanMasterDto.offenderMobNo" maxlength="10" minlength="10"
										value="" placeholder="${enterOffenderMobNo}" />
						</div>
						
					</div>

					<div class="text-center">
						<button type="button" onclick="searchPaymentDetails(this);" 
								class="btn btn-success" title="<spring:message code="EChallan.search" text="Search" />" >
								<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
								<spring:message code="EChallan.search" text="Search" />
						</button>
						
						<button type="button" onclick="resetForm(this);" 
								class="btn btn-warning" title="<spring:message code="EChallan.reset" text="Reset" />" >
								<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
								<spring:message code="EChallan.reset" text="Reset" />
						</button>
						
					</div>
					
					<h4><spring:message code="EChallan.paymentDetails" text="Payment Details" /></h4>
          			<div class="table-responsive">
			          <table class="table table-bordered margin-bottom-10" id="datatables">
			            <thead>
			              <tr>
			              <th class="text-center"><spring:message code="EChallan.srno" text="Sr No." /></th>
			                <th class="text-center"><spring:message code="EChallan.raidNumber" text="Raid No." /></th>
			                <th class="text-center"><spring:message code="EChallan.date" text="Date" /></th>
			                <th class="text-center"><spring:message code="EChallan.offenderName" text="Offender Name" /></th>
			                <th class="text-center"><spring:message code="EChallan.violationPurpose" text="Violation Purpose" /></th>
			                <th class="text-center"><spring:message code="EChallan.mobNo" text="Mobile Number" /></th>
			                <th class="text-center"><spring:message code="EChallan.action" text="Action" /></th>
			              </tr>
			            </thead>
			            <tbody>
			            	<c:forEach items="${command.challanMasterDtoList}" var="data" varStatus="loop">
			              <tr>
			              	<td class="text-center"><c:out value="${loop.index + 1}" ></c:out></td>
			                <td class="text-center">${data.raidNo}</td>
			                <td class="text-center">${data.datechallan}</td>
			                <td class="text-center">${data.offenderName}</td>
			                <td class="text-center">${data.challanDesc}</td>
			                <td class="text-center">${data.offenderMobNo}</td>
			                <td class="text-center">
				                <c:choose>
				                	<c:when test="${data.status eq 'Y'}">
				                		<%-- <spring:message code="" text="Payment Already Done" /> --%>
				                		<button type="button" class="btn btn-blue-3 btn-sm margin-right-5" 
					                  		  title="<spring:message code="EChallan.view" text="View" />"
					                  		  onclick="getActionForDefination(${data.challanId},'V');">
					                  		  <i class="fa fa-eye"></i>
					                  </button>
				                	</c:when>
				                	<c:otherwise>
				                		<button type="button" class="btn btn-warning btn-sm margin-right-5" 
					                  		  title="<spring:message code="EChallan.edit" text="Edit" />"
					                  		  onclick="getActionForDefination(${data.challanId},'E');">
					                  		  <i class="fa fa-pencil" aria-hidden="true"></i>
					                  </button>
				                	</c:otherwise>
				                </c:choose>
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