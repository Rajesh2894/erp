<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<script>
$(document).ready(function(e){
	$('.datepicker').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
	});
	
	$('.firstdatepicker').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		  beforeShowDay: function (date) {

		        if (date.getDate() == 1) {
		            return [true, ''];
		        }
		        return [false, ''];
		       }

	});
	
	
	$('.lastdatepicker').datepicker({
				dateFormat: 'dd/mm/yy',	
				changeMonth: true,
				changeYear: true,
			    beforeShowDay: function(date)
			    {
			        // getDate() returns the day [ 0 to 31 ]
			        if (date.getDate() ==
			            getLastDayOfYearAndMonth(date.getFullYear(), date.getMonth()))
			        {
			            return [true, ''];
			        }

			        return [false, ''];
			    }
			});
	
	prepareExceptionalGapDateTag();
	prepareExceptionalGapFirstDateTag();
	prepareExceptionalGapLastDateTag();
$('#selectall').click(function(event) { 
    if(this.checked) { 
        $('.checkall').each(function() { 
            this.checked = true;
        });
    }else{
        $('.checkall').each(function() { 
            this.checked = false;  
          
        });         
    }
});

$('.checkall').on('click',function(){
    if($('.checkall:checked').length == $('.checkall').length){
        $('#selectall').prop('checked',true);
    }else{
        $('#selectall').prop('checked',false);
    }
    
});
});

function getLastDayOfYearAndMonth(year, month)
{
    return(new Date((new Date(year, month + 1, 1)) - 1)).getDate();
}


function serachWaterBillData(obj)
{
	var formName	=	findClosestElementId(obj,'form');
	var theForm	=	'#'+formName;
	var url		=	'WaterExceptionalGap.html?serachWaterBillData';
	$(theForm).attr('action',url);
	$(theForm).submit();
}

function saveData(element)
{
	return saveOrUpdateForm(element,"Water Exceptional gap Entry done Successfully!", 'WaterExceptionalGap.html', 'saveform');
}
function resetgapMaster(obj){
	var formName	=	findClosestElementId(obj,'form');
	var theForm	=	'#'+formName;
	var url		=	'WaterExceptionalGap.html?resetPage';
	var requestData = {
	}
	var returnData =__doAjaxRequest(url,'POST',requestData, false,'html');
	$("#result").html(returnData);
}

function prepareExceptionalGapDateTag() {
	var dateFields = $('.datepicker');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}
function prepareExceptionalGapFirstDateTag() {
	var dateFields = $('.firstdatepicker');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}

function prepareExceptionalGapLastDateTag() {
	var dateFields = $('.lastdatepicker');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}


function populate(ele,id){
	var rows=$('#hiddenRowSize').val();
	if (ele.checked) {
		var value=$('#'+id+0).val();
		for(var i=1;i<rows ;i++)
		{
			$('#'+id+i).val(value);
		} 
	}
	else{
		for(var i=1;i<rows ;i++)
		{
			$('#'+id+i).val('');
		} 
	}
}

function findBackPage(obj)
{
	var formName	=	findClosestElementId(obj,'form');
	
	var theForm	=	'#'+formName;
	
	var url		=	'WaterExceptionalGap.html?backPage';
	
	var requestData = {
		
	}
	var returnData =__doAjaxRequest(url,'POST',requestData, false,'html');
	$("#result").html(returnData);
}

</script>

<div id="result">
 <apptags:breadcrumb></apptags:breadcrumb>

    <!-- ============================================================== --> 
    <!-- Start Content here --> 
    <!-- ============================================================== -->
    <div class="content"> 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="water.exception.gap.tool" text="Exceptional Gap Tool"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"><span><spring:message code="water.fieldwith" /> <i
						class="text-red-1">*</i> <spring:message code="water.ismandtry" /></span></div>
          <form:form action="WaterExceptionalGap.html" method="POST" class="form-horizontal" id="exceptionalGapTool">
          	<jsp:include page="/jsp/tiles/validationerror.jsp" />
          	<h4 class="margin-top-0"><spring:message code="water.exception.con.search.criteria" text="Connection Search Criteria"/></h4>
			 <c:if test="${command.addEdit eq 'A'}">	
				
			<div id="addNew">	
				
         	<div class="form-group margin-bottom-0">
				<apptags:lookupFieldSet cssClass="form-control required-control" baseLookupCode="WWZ" hasId="true"
					pathPrefix="waterDTO.codDwzid" isMandatory="true"
					hasLookupAlphaNumericSort="true"
					hasSubLookupAlphaNumericSort="true"  showAll="true"/>
           </div>
        	<div class="form-group margin-bottom-0">
				<apptags:lookupFieldSet cssClass="form-control required-control" baseLookupCode="TRF" hasId="true"
					pathPrefix="waterDTO.trmGroup" isMandatory="true"
					hasLookupAlphaNumericSort="true"
					hasSubLookupAlphaNumericSort="true" showAll="true"/>
            </div>
         <div class="form-group">
          <label class="col-sm-2 control-label "><spring:message code="water.ConnectionSize"/></label>
          <div >
			   <c:set var="baseLookupCode" value="CSZ" />
                    <apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="waterDTO.csCcnsize" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="true"
								selectOptionLabelCode="eip.select" isMandatory="true"/>
		
		
         </div>
          <label class="col-sm-2 control-label "><spring:message code="water.BillingFrequency"/> </label>
           <div >
			   <c:set var="baseLookupCode" value="BSC" />
                    <apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="billingFrequency" cssClass="form-control required-control"
								hasChildLookup="false" hasId="true" showAll="true"
								selectOptionLabelCode="eip.select" isMandatory="true"/> 
			</div> 
        </div>
        </div>
        </c:if>
         <c:if test="${command.addEdit eq 'E'}">	
        <div id="editOld">	
        
        <c:if test="${command.meterTypeDesc eq 'MTR'}">	
        <div class="form-group">
          <label class="col-sm-2 control-label"><spring:message code="water.met.status"/></label>
          <div >
			   <c:set var="baseLookupCode" value="MST" />
                    <apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="editGap.cpdMtrstatus" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="true"
								selectOptionLabelCode="eip.select" isMandatory="true"/>
		
		
         </div>
         <label class="control-label col-sm-2"><spring:message code="water.meterReading.GapCode"/></label>
          <div>
          <c:set var="baseLookupCode" value="GAP" />
                    <apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="editGap.cpdGap" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="true"
								selectOptionLabelCode="eip.select" isMandatory="true"/>
          </div>
        </div>
        </c:if>
         <div class="form-group">
              <label class="col-sm-2  control-label"><spring:message code="water.billPrintingSearch.FromDate"/></label>
              <div class="col-sm-4">
              <div class="input-group">
                  <form:input path="editGap.mgapFrom" type="text" id="fromDate" class="datepicker form-control"></form:input>  
                  <label class="input-group-addon" for="fromDate"><i class="fa fa-calendar"></i></label>
                </div>
		 </div> 
              
              <label class="col-sm-2 control-label"><spring:message code="water.disconnect.to"/></label>
              <div class="col-sm-4">
              <div class="input-group">
                  <form:input path="editGap.mgapTo" type="text" id="toDate" class="datepicker form-control"  ></form:input>  
                  <label class="input-group-addon" for="toDate"><i class="fa fa-calendar"></i></label>
                </div>
                
		
        </div> 
            </div>
        </div>
        </c:if>
        
         <div class="text-center">
              <button type="button" class="btn btn-info" onclick="return serachWaterBillData(this);"><spring:message code="water.search"/></button>
              <button type="button" class="btn btn-danger" onclick="return findBackPage(this)"><spring:message code="water.btn.back"/></button>
              <button type="button" class="btn btn-warning" onclick="resetgapMaster(this)"><spring:message code="water.btn.reset"/></button>
          </div>
           <input type="hidden" id="hiddenRowSize" value="${fn:length(command.gapDto)}"/>
         <c:if test="${not empty command.gapDto}">
            <div class="table-responsive max-height-300  padding-top-10">
              <table class="table table-bordered table-condensed">
                <tr>
                  <th width="20"><spring:message code="water.nodues.srno"/></th>
                  <th width="100"><spring:message code="water.ConnectionNo"/></th>
                  <th width="150"><spring:message code="water.consumerName"/></th>
                 <!--  <th>Address</th> -->
                  <c:if test="${command.meterTypeDesc eq 'MTR'}">	
                  <th width="120"><spring:message code="water.met.status"/><label class="checkbox-inline"><input type="checkbox"  id="mtrStatus" onclick="populate(this,'mtrStatus');"/><spring:message code="" text="Apply to all"/></label></th>
                  <th width="120"><spring:message code="water.meterReading.GapCode"/> <label class="checkbox-inline"><input type="checkbox"  id="gapCode" onclick="populate(this,'gapCode');"/><spring:message code="" text="Apply to all"/></label></th>
                  </c:if>
                  <th width="100"><spring:message code="WaterDisconnectionModel.disconnectFromDate"/><label class="checkbox-inline"><input type="checkbox"  id="fromDate" onclick="populate(this,'fromDate');"/><spring:message code="" text="Apply to all"/></label></th>
                  <th width="100"><spring:message code="WaterDisconnectionModel.disconnectToDate"/><label class="checkbox-inline"><input type="checkbox"  id="toDate" onclick="populate(this,'toDate');"/><spring:message code="" text="Apply to all"/></label></th>
                  <th width="20"><spring:message code="water.Active"/><label class="checkbox-inline"><input type="checkbox"  id="selectall" /><spring:message code="" text="All"/></label></th>
                </tr>
                  <c:forEach items="${command.gapDto}" var="data"  varStatus="status"> 
                <tr>
                  <td>${status.index+1}</td>
                  <td>${data.ccnNumber}</td>
                  <td>${data.csName}</td>
                  <%-- <td><form:input path=""  class="form-control"/></td> --%>
                  <form:hidden path="gapDto[${status.index}].csIdn" value="${data.csIdn}"/>
                  
                   <c:if test="${command.meterTypeDesc eq 'MTR'}">	
                  <td>
                 <c:set var="baseLookupCode" value="MST" />
          		<form:select
					path="gapDto[${status.index}].cpdMtrstatus" class="form-control" id="mtrStatus${status.index}">
					<form:option value=""><spring:message code="water.select"/></form:option>
						<c:forEach items="${command.getLevelData(baseLookupCode)}"
							var="lookUp">
							<form:option value="${lookUp.lookUpId}">${lookUp.lookUpCode}</form:option>
						</c:forEach>
				</form:select>
                    </td>
                  <td>
                   <c:set var="baseLookupCode1" value="GAP" />
	         		<form:select
						path="gapDto[${status.index}].cpdGap" class="form-control" id="gapCode${status.index}">
						<form:option value="">select</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode1)}"
								var="lookUp1">
								<form:option value="${lookUp1.lookUpId}">${lookUp1.lookUpCode}</form:option>
							</c:forEach>
					</form:select>
                    </td>
                     <td><form:input path="gapDto[${status.index}].mgapFrom" type="text" id="fromDate${status.index}" class="datepicker form-control "  ></form:input> </td>
                  <td><form:input path="gapDto[${status.index}].mgapTo" type="text" id="toDate${status.index}" class="datepicker form-control "  ></form:input> </td>
                    </c:if>
                     <c:if test="${command.meterTypeDesc ne 'MTR'}">	
                     
                     <td><form:input path="gapDto[${status.index}].mgapFrom" type="text" id="fromDate${status.index}" class="firstdatepicker form-control "  ></form:input> </td>
                  <td><form:input path="gapDto[${status.index}].mgapTo" type="text" id="toDate${status.index}" class="lastdatepicker form-control "  ></form:input> </td> 
                     
                     </c:if> 
                 
                  <td>
                  
              
                  <label class="checkbox margin-left-20">
                  <form:checkbox path="gapDto[${status.index}].mgapActive" value="Y" cssClass="checkall" id="${status.index}"/>
                  </label>
                  </td>
                </tr>
                </c:forEach>
              </table>
            </div>
            
             <div class="form-group padding-top-10">
			   <label class="control-label col-sm-2 required-control"><spring:message code="water.meter.cutOffReason"/></label>
              <div class="col-sm-8">
                <form:textarea path="reason"  class="form-control"></form:textarea>
              </div>
        </div>
            
             <div class="text-center padding-top-10">
              <button type="button" class="btn btn-success btn-submit" onclick="return saveData(this);"><spring:message code="water.btn.submit"/></button>
         	 <button type="button" class="btn btn-danger" onclick="window.location.href='AdminHome.html'"><spring:message code="water.btn.cancel"/></button>
            </div>
            </c:if>
          </form:form>
        </div>
      </div>
      </div>
      </div>
      <!-- End of info box --> 
    