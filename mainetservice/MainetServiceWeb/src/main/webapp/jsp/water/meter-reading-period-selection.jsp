<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript">
	
$(document).ready(function() {
	
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
	  /*   
	    $('#cancelCycle').click(function(event) { 
	            $('.checkall').each(function() { 
	                this.checked = false;  
	            });         
	    });
	    
	     */
	  
});
	</script>
<div class="content"> 
  <!-- Start info box -->
  <div class="widget margin-bottom-0">
    <div class="widget-header">
      <h2><spring:message code="water.meterreading.select.bill.period" /></h2>
    </div>
    <div class="widget-content padding">
    <form:form id="meterPeriod">
    <div class="alert alert-danger alert-dismissible error-div" role="alert"></div>
    <form:hidden path="" id="selectedvalues"/>
    <div class="table-responsive">
     <table class="table table-bordered table-condensed">
       <tbody>
        <tr>
			<th><label class="checkbox-inline"><!-- <input type="checkbox" id="selectall" class="pull-left"/> --><spring:message code="water.meter.select.cycle" text="Select Cycle"/></label></th>
			<th><spring:message code="water.meter.select.period" text="From Month - To Month"/></th>
		</tr>
		<c:forEach items="${command.monthDto}" var="data"  varStatus="status">
			<tr>
				<td>
				<label class="checkbox-inline"><form:checkbox path="monthDto[${status.index}].valueCheck" value="Y" cssClass="checkall" id="${status.index}" disabled="${data.cssProperty}"/></label>
				</td>
				<td>
				<%-- <c:out value="${data.from}"></c:out> - <c:out value="${data.to}"></c:out> --%>
				<strong class="form-control"><c:out value="${data.monthDesc}"></c:out></strong>
				<%-- <form:input path="entityList[${command.index}].month[${status.index}].monthDesc" cssClass="form-control"/> --%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
    
    </div>
    </form:form>
    
    <div class="text-center padding-top-10">
          <button type="button" class="btn btn-success"  onclick="calDaysAndConsumption(this);"><spring:message code="water.meter.ok" text="Ok"/></button>
           <button type="button" class="btn btn btn-danger" id="cancelCycle" onclick="clearCalculation(this),closeBillCycleForm()"><spring:message code="water.btn.cancel" text="C
           ancel"/></button>
     </div>
    
  </div>
  <!-- End of info box --> 
</div>
</div>
<!-- End Content here --> 
