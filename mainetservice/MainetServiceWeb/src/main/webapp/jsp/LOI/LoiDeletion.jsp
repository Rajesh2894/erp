<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.fancybox').fancybox();
	});
</script> 
<script type="text/javascript">
    $(document).ready(function() {

    $("#FeesDetails").fancybox({
            'titlePosition'     : 'inside',
            'transitionIn'      : 'none',
            'transitionOut'     : 'none'
        });
     
    });
  
    function searchLOIDeletionData(obj)
    {
    	var formName	=	findClosestElementId(obj,'form');
    	
    	var theForm	=	'#'+formName;
    	
    	var url		=	'LoiDeletion.html?searchLOIRecordsDelete';
    	
    	var requestData = {
    			"applicationId":$("#applicationId").val(),
    	}
    	var returnData =__doAjaxRequest(url,'POST',requestData, false,'html');
    	$("#loiDeletion").html(returnData);
    }
    
    function resetLoiDeletionForm(element){
    	$("#loiDeletionTool").submit();
    }
    function saveData(element)
    {
    	return saveOrUpdateForm(element," Saved", 'LoiDeletion.html', 'saveform');
    }
    
    </script>
 <div id="loiDeletion"> 
 <apptags:breadcrumb></apptags:breadcrumb>
    
    <!-- Start Content here --> 
    <!-- ============================================================== -->
    <div class="content"> 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="master.loi.deletion.tool" text="LOI Deletion Tool"/></h2>
          <apptags:helpDoc url="LoiDeletion.html"></apptags:helpDoc>
        </div>
        <div class="widget-content padding">
        <div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
          <form:form action="LoiDeletion.html" method="POST" class="form-horizontal" id="loiDeletionTool">
            <jsp:include page="/jsp/tiles/validationerror.jsp"/>
            <div class="form-group">
              <label class="control-label col-sm-2 required-control"><spring:message code="master.loi.deletion.appno" text="Application No."/></label>
              <div class="col-sm-4">
                <form:input path="searchData.applicationId" value="" class="form-control hasNumber" id="applicationId"/>
              </div>
              <div class="col-sm-6">
                <button type="button" class="btn btn-info" onclick="return searchLOIDeletionData(this);"><i class="fa fa-search"></i><spring:message code="search.data" text="Search"/></button>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-2"><spring:message code="master.loi.deletion.loino" text="LOI Number"/></label>
              <div class="col-sm-4">
                <form:input path="" value="${command.searchData.loiMasData.loiNo}" class="form-control"  readonly="true"/>
              </div>
              <label class="control-label col-sm-2"><spring:message code="master.loi.loidate" text="LOI Date"/></label>
              <div class="col-sm-4">
              <fmt:formatDate pattern="dd/MM/yyyy" value="${command.searchData.loiMasData.loiDate}" var="date"/>
                 <form:input path="" value="${date}" class="form-control" readonly="true"/>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-2"><spring:message code="master.loi.deletion.consumername" text="Consumer Name"/></label>
              <div class="col-sm-4">
                 <form:input path=""  value="${command.searchData.applicantName}" class="form-control" readonly="true"/>
              </div>
              <label class="control-label col-sm-2"><spring:message code="master.loi.deletion.address" text="Address"/> </label>
              <div class="col-sm-4">
                <form:textarea path="address"  class="form-control" readonly="true"></form:textarea>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-2"><spring:message code="master.loi.deletion.servicename" text="Service Name"/> </label>
              <div class="col-sm-4">
                 <form:input path="" value="${command.searchData.serviceName}"  class="form-control"  readonly="true"/>
              </div>
              <div class="col-sm-6"><a class="btn btn-default" id="FeesDetails" href="#Fees"><i class="fa fa-inr"></i><spring:message code="master.loi.deletion.fees.details" text="Fees Details"/></a></div>
              <div class="hide">
                <div id="Fees">
                  <div class="widget margin-bottom-0">
                    <div class="widget-content">
                      <table class="table table-bordered table-condensed">
                        <tr>
                          <th><spring:message code="master.loi.deletion.feescharge" text="Fee / Charge Name"/></th>
                          <th><spring:message code="master.loi.amt" text="Amount"/></th>
                        </tr>
                        <c:forEach items="${command.searchData.chargeDesc}" var="chargesData">
                        <tr>
                          <td>${chargesData.key}</td>
                          <td>
                          <fmt:formatNumber type="number" value="${chargesData.value}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amt"/>
                          ${amt}</td>
                        </tr>
                       </c:forEach>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-2 required-control"><spring:message code="master.loi.deletion.delremark" text="Deletion Remark"/></label>
              <div class="col-sm-10">
              <form:textarea path="loiDelRemark"  class="form-control" ></form:textarea>
              </div>
            </div>
            <div class="text-center padding-top-10">
              <button type="button" class="btn btn-success btn-submit" onclick="return saveData(this);"><spring:message code="submit.msg" text="Submit"/></button>
              <button type="button" class="btn btn-warning" onclick="resetLoiDeletionForm(this);"><spring:message code="reset.msg" text="Reset"/></button>
               <button type="button" class="btn btn-danger" onclick="window.location.href='AdminHome.html'"><spring:message code="master.cancel" text="Cancel"/></button>
            </div>
          </form:form>
        </div>
        </div>
      </div>
      </div>