<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
 <script type="text/javascript">
$(document).ready(function(e){
$('.lessthancurrdate').datepicker({
	dateFormat: 'dd/mm/yy',	
	changeMonth: true,
	changeYear: true,
	maxDate: '-0d',
	yearRange: "-100:-0"
});
prepareChallanDateTag();

if($("#dataSize").val()>0)
	{
	var radioValue = $("input[name='conType']:checked").val();
	if(radioValue==='S')
		{
		 $('#selectall').prop('checked',true);
	 $('.checkall').each(function() { 
         this.checked = true;
     });
	}
	}

var radioValue = $("input[name='conType']:checked").val();
if(radioValue==='S')
	{
	$('#connNumber').show();
	$('#multiSearch').hide();
	}
else
	{
	$('#connNumber').hide();
	$('#ccnNumber').val('');
	$('#multiSearch').show();
	}
	
$(".conSelect").click(function() {
	var radioValueSel = $("input[name='conType']:checked").val();
	if(radioValueSel==='S')
	{
	$('#connNumber').show();
	$('#multiSearch').hide();
	}
else
	{
	$('#connNumber').hide();
	$('#ccnNumber').val('');
	$('#multiSearch').show();
	}
});



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

function serachWaterBillPrintData(obj)
{
	showloader(true);
	var formName	=	findClosestElementId(obj,'form');
	
	var theForm	=	'#'+formName;
	
	var url		=	'WaterBillPrinting.html?serachWaterBillPrintData';
		
	$(theForm).attr('action',url);
	
	$(theForm).submit();
}

function saveData(element)
{
	return saveOrUpdateForm(element,"Water Bill printed successfully!", 'WaterBillPrinting.html', 'saveform');
}
function resetbillPrint(element){
	$("#waterBillPrintingForm").submit();
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
function generateBill(element){	
	showloader(true);
	setTimeout(function(){startGenBill(element)},2);
}

function startGenBill(element) {
var idArray=[];
$('.checkall').each(function() { 
	 if(this.checked) {
    var id = $(this).attr('id');
    idArray.push(id);
	 }
});

if(idArray.length>0){
	 var idsData=idArray.join(", ");
	var formData ={"idarray":idsData,
	}
	var returnData =__doAjaxRequest('WaterBillPrinting.html?generateBillAndPrint','post',formData,false);
	$(".content").html(returnData);
}
else{
	showloader(false);
	showErrormsgboxTitle("water.bill.print.select.record")
	return false;
}
}

 </script>
  <!-- Start right content -->
   <apptags:breadcrumb></apptags:breadcrumb>
    <!-- ============================================================== --> 
    <!-- Start Content here --> 
    <!-- ============================================================== -->
    <div class="content"> 
      <!-- Start info box -->
      <div class="widget ">
        <div class="widget-header">
          <h2><spring:message code="water.bill.print" text="Bill Printing"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"><span><spring:message code="water.fieldwith" /> <i
						class="text-red-1">*</i> <spring:message code="water.ismandtry" /></span></div>
          <form:form action="WaterBillPrinting.html" method="POST" class="form-horizontal" id="waterBillPrintingForm">
           	<jsp:include page="/jsp/tiles/validationerror.jsp" />
            <div class="form-group">
              <label class="col-sm-2 control-label"><spring:message code="water.connectiontype"/><span class="mand">*</span></label>
            <div class="col-sm-4">  <label class="radio-inline"><form:radiobutton path="conType"  value="S" id="single" class="conSelect"/><spring:message code="water.meterReading.Single"/> </label>
              <label class="radio-inline"><form:radiobutton path="conType"  value="M" id="Multi" class="conSelect"/> <spring:message code="water.meterReading.Multiple"/></label></div>
	           <div id='connNumber'><label class="control-label col-sm-2 required-control"><spring:message code="water.ConnectionNo"/></label>
	          <div class="col-sm-4"><form:input path="entity.csCcn" class="form-control" id="ccnNumber"></form:input></div>
	          </div>
            </div>
            
            <div id='multiSearch'>
             <div class="panel-group accordion-toggle margin-bottom-0" id="accordion_single_collapse">
            <div class="panel panel-default">
		  	<div class="panel-heading"><h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#Additional_Owners"><spring:message code="water.meterReading.Multiple"/></a></h4></div>
		 	<div id="Additional_Owners" class="panel-collapse collapse in">
			<div class="panel-body">
            
            
             <div class="form-group">
              <div class="col-sm-12"><label class="checkbox-inline"><form:checkbox path="meterType"  value="MTR"/> <spring:message code="water.meter"/></label></div>
            </div>
            <div class="form-group">
              <label class="col-sm-2 required-control control-label"><spring:message code="water.billPrintingSearch.FromDate"/></label>
              <div class="col-sm-4">
              <div class="input-group">
                  <form:input path="entity.csFromdt" type="text" id="fromDate" class="lessthancurrdate form-control"></form:input>  
                  <label class="input-group-addon" for="fromDate"><i class="fa fa-calendar"></i></label>
                </div>
		 </div> 
              
              <label class="col-sm-2 required-control"><spring:message code="water.disconnect.to"/></label>
              <div class="col-sm-4">
              <div class="input-group">
                  <form:input path="entity.csTodt" type="text" id="toDate" class="lessthancurrdate form-control"  ></form:input>  
                  <label class="input-group-addon" for="toDate"><i class="fa fa-calendar"></i></label>
                </div>
                
		
        </div> 
              
            </div>
           <div class="form-group margin-bottom-0">
			<apptags:lookupFieldSet cssClass="form-control required-control" baseLookupCode="WWZ" hasId="true"
				pathPrefix="entity.codDwzid" isMandatory="true"
				hasLookupAlphaNumericSort="true"
				hasSubLookupAlphaNumericSort="true"  showAll="true"/>
           </div>
           </div>
           </div>
           </div>
           </div>
           </div>
           
           <form:hidden path="" value="${fn:length(command.billMas)}" id="dataSize"/>
           
           
           
           <div class="text-center padding-bottom-10">
           <button type="submit" class="btn btn-info" onclick="return serachWaterBillPrintData(this);"><i class="fa fa-search"></i> <spring:message code="water.search"/></button>
          <button type="button" class="btn btn-warning" onclick="resetbillPrint(this)"><spring:message code="water.btn.reset"/></button>
        </div>
              <c:if test="${not empty command.billMas}">
            <h4><spring:message code="water.connectiondetails"/></h4>
             <div class="table-responsive max-height-300">
              <table class="table table-bordered table-condensed table-striped">
	              	<tr>
		               <th width="120"><label class="checkbox-inline"><input type="checkbox" id="selectall"/><spring:message code="water.selectall" text="Select All"/></label></th>
					   <th width="100"><spring:message code="water.nodues.srno"/></th>
					   <th><spring:message code="water.ConnectionNo"/></th>
					   <th><spring:message code="water.nodues.consumername"/></th>
					   <th><spring:message code="water.meterReadingViewDetails.BillNo."/></th>
	              	</tr>
	              <c:forEach items="${command.billMas}" var="data"  varStatus="status"> 
	              	<tr>
	              	 <%-- <td><label class="checkbox margin-left-20"><form:checkbox path="entityList[${status.index}].pcFlg" value="Y" cssClass="checkall" id="${data.bmIdno}"/></label></td> --%>
		              <td><label class="checkbox margin-left-20"><form:checkbox path="" value="Y" cssClass="checkall" id="${data.bmIdno}"/></label></td>
		              <td><c:out value="${status.index+1}"></c:out></td>
		              <td><c:out value="${data.waterMas.csCcn}"></c:out></td>
		              <td><c:out value="${data.waterMas.csName} ${data.waterMas.csMname} ${data.waterMas.csLname}"></c:out></td>
		              <td><c:out value="${data.bmNo}"></c:out></td>
	              	</tr>
             	 </c:forEach>
         		</table>
         	</div>
            
            <div class="text-center margin-top-10">
             <button type="button" class="btn btn-success" onclick="return generateBill(this);"><spring:message code="water.print.bill"/></button>
            	 <button type="button" class="btn btn-danger" onclick="window.location.href='AdminHome.html'"><spring:message code="water.btn.back"/></button>
            </div>
            </c:if>
            
            
          </form:form>
        </div>
      </div>
      <!-- End of info box --> 
      </div>
      