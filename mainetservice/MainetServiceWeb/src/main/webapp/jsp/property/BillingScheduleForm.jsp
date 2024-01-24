<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="js/property/billingSchedule.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<div id="reloadDiv">
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="bill.schedule" text="Billing Schedule"/></h2>
			 <div class="additional-btn"><a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
		</div>
	<div class="widget-content padding">
	   <div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory</span></div>
	<form:form method="post" action="BillingSchedule.html" name="BillingSchedule" id="BillingSchedule" class="form-horizontal" modelAttribute="command">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible hide" id="errordiv">
				<button type="button" class="close" aria-label="Close" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button>
				<ul><li><i class='fa fa-exclamation-circle'></i>&nbsp;<form:errors path="*"/></li></ul>
			</div>
		<form:hidden path="billScheduleDto.asBillScheid"/>
		<div class="form-group">
		<label class="col-sm-2 control-label required-control"><spring:message code="bill.finYear" text ="Financial Year"/></label>
		<div class="col-sm-4">
	<c:choose>
	  <c:when test="${command.modeType eq 'C'}">
        <form:select path="billScheduleDto.tbFinancialyears" id="financialYear" multiple="multiple" class="form-control mandColorClass">
		  <c:forEach items="${command.financialYearMap}" var="yearMap">
		  <form:option value="${yearMap.key}" label="${yearMap.value}"></form:option>
		  </c:forEach>
		 </form:select>
	 </c:when>
	 <c:otherwise>
		 <form:input path="billScheduleDto.finYearView" type="text" class="form-control mandColorClass" disabled="true" id="financialYear"/>		 
		 </c:otherwise>
  </c:choose>
		</div>
		<label class="col-sm-2 control-label required-control"><spring:message code="bill.frequency" text="Billing Frequency"/></label> 
		<apptags:lookupField items="${command.getLevelData('BSC')}" path="billScheduleDto.asBillFrequency" 
		  cssClass="form-control changeParameterClass" hasChildLookup="false" hasId="true" selectOptionLabelCode="Select"
		   showAll="false" isMandatory="true" disabled="${command.modeType eq 'V' ? true : false}" changeHandler="setToMonthData(this);"/>

		</div>
	<div id="SchedulewiseDueDate">
	 <c:if test="${not empty command.getBillSchDtoList()}">
	        <h4><spring:message code="bill.SchedulewiseDueDate" text="Schedule wise Due Date"/></h4>
             <table id="main-tableQ" class="table text-left table-striped table-bordered">
                <tbody>
                  <tr>
                    <th><spring:message code="bill.srNo" text="Sr. No."/></th>
                    <th><spring:message code="bill.ScheduleFrom" text="Schedule From"/></th>
                    <th><spring:message code="bill.ScheduleTo" text="Schedule To"/></th>
                    <th><spring:message code="bill.CalculateFrom" text="Calculate From"/></th>
                    <th><spring:message code="bill.NoOfDay" text="No Of Day"/></th>
                  </tr>
                  <c:forEach items="${command.getBillSchDtoList()}" var="billSchDto" varStatus="status">
                  <tr class="dueDateDetail">
                    <td>${status.count}</td>
                    <td>${billSchDto.billFromMonth}</td>
                    <td>${billSchDto.billToMonth}</td>
                    <td>		
           <%--         <apptags:lookupField items="${command.getLevelData('BDC')}" path="billSchDtoList[${status.count-1}].calculateFrom" 
		  				cssClass="form-control chosen-select-no-results" hasChildLookup="false" hasId="true" selectOptionLabelCode="Select"
		   				showAll="false" isMandatory="true"/>  --%>
		   				<c:set var="baseLookupCode" value="BDC" /> 
					<form:select path="billSchDtoList[${status.count-1}].calculateFrom" class="form-control"
																id="calculateFrom${status.count-1}" disabled="${command.modeType eq 'V' ? true : false}"  >
						<form:option value="0"><spring:message code="bill.Select" text="Select"/></form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
											<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach> 
								</form:select>
		   			</td>
                    <td>
					<form:input cssClass="form-control hasNumber" id="noOfDay${status.count-1}" maxlength="3" path="billSchDtoList[${status.count-1}].noOfDay"  disabled="${command.modeType eq 'V' ? true : false}" ></form:input>
                    </td>
                  </tr>
                  </c:forEach>
                </tbody>
              </table> 
	</c:if>
	</div>
	
		  <div class="text-center padding-top-10">
		 <c:if test="${command.modeType eq 'C' || command.modeType eq 'E'}"> 
        <input type="button" class="btn  btn-success" id="save" name="save" value="<spring:message code="master.save"/>" onclick="saveData(this);"  />
         </c:if> 
        <c:if test="${command.modeType eq 'C'}">
        <input type="button" value="<spring:message code="bt.clear"/>" onclick="resetBillSchedule()" class="btn btn-warning"></input>
	     </c:if>
  <input type="button" id="backBtn" class="btn btn-danger" onclick=" back();" value="<spring:message code="bt.backBtn"/>" />	
	</div> 
</form:form>
	</div>
	</div>
	</div>
	</div>
