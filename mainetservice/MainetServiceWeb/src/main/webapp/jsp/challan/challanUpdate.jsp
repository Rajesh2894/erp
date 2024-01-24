<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript">
$( document ).ready(function(){
	
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0"
	});
});
var cvs1 = getLocalMessage('Challan Upadted Successfully');

function saveForm(element,successurl)
{
	var result= saveOrUpdateForm(element,cvs1,successurl,'saveform');
	prepareChallanDateTag();
	return result;
}

function prepareChallanDateTag() {
	var dateFields = $('.lessthancurrdate');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}
</script>

		<%-- <ol class="breadcrumb">
			<li><a href="AdminHome.html"><spring:message code="cfc.home" text="Home"/></a></li>
			<li><a href="javascript:void(0);"><spring:message code="cfc.module" text="CFC"/></a></li>
			<li><a href="javascript:void(0);"><spring:message code="cfc.transaction" text="Transactions"/></a></li>
			<li class="active"><spring:message code="cfc.challan.verification" text="Challan Varification"/></li>
		</ol> --%>
     <apptags:breadcrumb></apptags:breadcrumb>

<div class="content"> 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="cfc.challan.verification" text="Challan Varification"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding">
        <form:form action="ChallanUpdate.html" method="post" class="form-horizontal">
						<jsp:include page="/jsp/tiles/validationerror.jsp"/>
						
						<c:if test="${command.pageUrlFlag ne 'C' }">
					<h4 class="margin-top-0"><spring:message code="chn.searchCriteria" text="Search Criteria"/></h4>
				<div class="form-group">
	                <label class="col-sm-2 control-label"><spring:message text="Challan No" code="chn.challanNumber"/></label>
					<div class="col-sm-4">
						<form:input path="challanNo" cssClass="form-control hasNumber"  maxlength="30"/>
					</div>
					<label class="col-sm-2 control-label"><spring:message text="Application No" code="chn.applicationNo"/></label>
					<div class="col-sm-4">
						<form:input path="applicationNo" cssClass="form-control hasNumber" maxlength="16"/>
					</div>
				</div>
				
		<div class="text-center padding-bottom-10">
		 <button type="submit" class="btn btn-info" onclick="findAll(this)"><i class="fa fa-search"></i> Search</button>
          <button type="button" class="btn btn-warning" onclick="window.location.href='ChallanUpdate.html'">Reset</button>				
						
		</div>
		</c:if>
				<c:if test="${not empty command.entity}">
				<h4 class="margin-top-0"><spring:message code="" text="Challan Details"/></h4>
				<div class="form-group">
                <label class="col-sm-2 control-label">
                	<spring:message code="chn.challanNumber" text="Challan Number"/>
                </label> 
                <div class="col-sm-4">
               		<span class="form-control disabled">${command.entity.challanNo}</span>
				</div>
				
				<c:set var="date" value="${command.entity.challanDate}" />
				<label class="col-sm-2 control-label">
					<spring:message code="chn.challanGeneratedDate" text="Challan Date"/>
				</label>
				<div class="col-sm-4">
					<span class="form-control disabled"><fmt:formatDate pattern="dd/MM/yyyy" value="${date}" /></span>
				</div>
			</div>
				
			<div class="form-group">
			<c:set var="date1" value="${command.entity.challanValiDate}" />
                <label class="col-sm-2 control-label">
               		<spring:message code="chn.chnValidDate" text="Challan valid Date"/>
                </label>
				<div class="col-sm-4">	
				<div class="input-group">
<span class="form-control disabled"><fmt:formatDate pattern="dd/MM/yyyy" value="${date1}" /></span>	 
     <label class="input-group-addon"><i class="fa fa-calendar"></i></label>
	    </div>
               		
				</div>

				<label class="col-sm-2 control-label">
					<spring:message code="chn.amount" text="Challan Amount"/>
				</label>  
				<div class="col-sm-4">	
					<fmt:formatNumber type="number" value="${command.entity.challanAmount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amt"/>
					<span class="form-control disabled" id="amount"><c:out value="${amt }"></c:out></span>  
				</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">
						<spring:message code="chn.applicationNo" text="Application number"/>
					</label> 
					<div class="col-sm-4">
               			<span class="form-control disabled">${command.entity.apmApplicationId}</span>
					</div>
					<label class="col-sm-2 control-label required-control">
						<spring:message code="chn.transactionId" text="transaction Id"/>
					</label> 
					<div class="col-sm-4">
		               	<form:input path="entity.bankTransId" cssClass="numericAlpha form-control" maxlength="30"/>
					</div>
				</div>
				
			
				<div class="form-group">
					<label class="col-sm-2 control-label required-control">
						<spring:message code="chn.challanTransDate" text="Challan Transaction Date"/>
					</label> 
					<div class="col-sm-4">
					<div class="input-group">
            <apptags:dateField fieldclass="lessthancurrdate" datePath="entity.challanRcvdDate" cssClass="form-control mandColorClass"></apptags:dateField>
     <label class="input-group-addon"><i class="fa fa-calendar"></i></label>
	    </div>
					</div> 
				</div>
				
				<div class="text-center">
				
				<c:choose>
					<c:when test="${command.immeadiateService}">
					
						<input type="submit" class="btn btn-success btn-submit" onclick="return saveForm(this,'${command.successUrl}');" value="<spring:message code="" text="Submit"/>" />					
					</c:when>
					<c:otherwise>
					<c:if test="${command.pageUrlFlag ne 'C' }">
						<input type="submit" class="btn btn-success btn-submit" onclick="return saveForm(this,'ChallanUpdate.html');" value="<spring:message code="" text="Submit"/>" />					
					</c:if>
					<c:if test="${command.pageUrlFlag eq 'C' }">
					<input type="submit" class="btn btn-success btn-submit" onclick="return saveForm(this,'AdminHome.html');" value="<spring:message code="" text="Submit"/>" />
					</c:if>
					
					</c:otherwise>
				</c:choose>
					<%-- <c:if test="${command.pageUrlFlag ne 'C' }">
					<apptags:backButton url="ChallanUpdate.html"></apptags:backButton>
					</c:if>
					<c:if test="${command.pageUrlFlag eq 'C' }"> --%>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
					<%-- </c:if> --%>
				</div>
						
						
						</c:if>
						
					</form:form>
			
				</div>
			</div>
		</div>
