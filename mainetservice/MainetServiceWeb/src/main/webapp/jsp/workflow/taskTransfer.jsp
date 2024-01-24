<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%><script type="text/javascript">

</script>
<div class="content animated slideInDown">
   <div class="widget">
	<div class="widget-header">
        <h2><spring:message code=""  text="Task Transfer"/></h2>
        <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
	  </div>
	 <div class="widget-content padding">
	     <div class="mand-label clearfix">
		<span>Field with <i class="text-red-1">*</i> is mandatory</span>
		 </div>
		 <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<ul><li><label id="errorId"></label></li></ul>
			</div>
  <form:form action="" method="POST" name="taskTransferForm" class="form-horizontal" modelAttribute="command">
  		<div class="form-group">
             <label  class="col-sm-2 control-label"><spring:message code="" text="Application Number"/></label>
             <div class="col-sm-4"><form:input id="appno" path="applicationId" type="text" class="form-control hasNumber" readonly="true"></form:input>
             </div>
            <label class="col-sm-2 control-label"><spring:message code="" text="Department Name"/></label>
            <div class="col-sm-4"><form:input type="select" path="" class="form-control" readonly="true"></form:input>
           </div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label"><spring:message code="" text="Service Name"/></label>
            <div class="col-sm-4"><form:input class="form-control" path="serviceName" readonly="true" ></form:input>
            </div>
			<label class="col-sm-2 control-label"><spring:message code="" text="Event Name"/></label>
            <div class="col-sm-4"><form:input class="form-control"  path="eventName" readonly="true"></form:input>
            </div>
		</div>
	   <div class="form-group">
			<label  class="col-sm-2 control-label"><spring:message code="" text="Assigned to"/></label>
            <div class="col-sm-4"><form:select  class="form-control" path="" >
            <form:option value="option-0" >Select</form:option>
                </form:select>
            </div>
		</div>
		<div class=" form-group">
			<label class="col-sm-2 control-label required-control"><spring:message code="" text="Transfer To"/></label>
            <div class="col-sm-4">
				<form:select class="form-control mandColorClass chosen-select-no-results" path="">
					<form:option value="option-0" >Select</form:option>
					<c:forEach items="${command.transferEmpList}" var="objArray">
					<form:option value="${objArray[3]}" label="${objArray[0]} ${objArray[2]}"></form:option>
					</c:forEach>
					
                </form:select>
            </div>
			<label class="control-label col-sm-2 required-control"><spring:message code="" text="Reason for Transfer"/></label>
	              <div class="col-sm-4 "><form:textarea maxlength="1000" path="" class="form-control mandColorClass"></form:textarea>
	              </div>
		</div>
      <div class="text-center">
			 <input type="submit"class="btn btn-success" value="<spring:message code="saveBtn" />" /> 
			  <input type="button" class="btn btn-danger" onclick="window.location.href='AdminHome.html'" value="Back"> 
		</div>
         </form:form>
	          </div>
      </div>
</div>
