
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- <script type="text/javascript" src="js/mainet/jquery.js"></script>-->
<script src="assets/libs/lightbox/jquery.fancybox.js"></script> <!-- LightBox --> 

<!-- <script type="text/javascript" src="js/commanremark/commanremark.js"></script> -->
<script type="text/javascript">
$(document).ready(function() {
    
	 $("#Submit").click(function () {
		 var serviceId = $("#woAllocation").val();
		 var checkedNum = $('.case:checked').length;
		 var plumberId = $("#plumId").val();
		 var woc = $("#WOChiddden").val();
		 
		 if (!checkedNum) {
			 var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select a Remark.</li></ul></div>';
				$("#serviceRuleDefMas").html(errMsg);
				$("#serviceRuleDefMas").show();
				return false;
		 }
			
			/* if(serviceId == '') {
				serviceId = -1;
				var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select a In  the name of.</li></ul></div>';
				$("#serviceRuleDefMas").html(errMsg);
				$("#serviceRuleDefMas").show();
				return false;
			} */
			
			/* if(woc=='' && plumberId=='') {
				var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select Plumber</li></ul></div>';
				$("#serviceRuleDefMas").html(errMsg);
				$("#serviceRuleDefMas").show();
				return false;
			} */
			
			
			$('.error-div').hide();
			
			
			var url = "LandOrder.html?create";
			var data = $("#workOrderForm").serialize();
			$.ajax({
				url : url,
				data : data,
				type: 'POST',
				 success : function(response) {						
					/* $(".widget").html(response); */
					var record = data.substring(data.search("&woApplicationId=")+17).split("&")[0];
					console.log(record);
					showConfirmBox("Work order successfully generated for application number "+record);
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				} 
			});	

	 });
    // add multiple select / deselect functionality
    $("#selectall").click(function () {
          $('.case').attr('checked', this.checked);
    });
    
$(".case").click(function(){
        
    	var alertms=getLocalMessage('tp.app.alMessg');
    	if($(".case").length == 0)	
    	{	
    		alert(alertms);
    	}
    	else
    		{
    		
    		if($(".case").length == $(".case:checked").length) {
                $("#selectall").attr("checked", "checked");
            } else {
                $("#selectall").removeAttr("checked");
            }
    		}
    	});
});

function closeErrBox() {
	$('.error-div').hide();
}

function showConfirmBox(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + successMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceed()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function proceed() {
	window.location.href = "AdminHome.html";
}
</script>

</head>
 <ol class="breadcrumb">
      <li><a href="../index.html"><i class="fa fa-home"></i></a></li>
      <li>Work Order View</li>
    </ol>
    <!-- ============================================================== --> 
    <!-- Start Content here --> 
    <!-- ============================================================== -->
    <div class="content"> 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2>Work Order View</h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        
        <div class="widget-content padding">
        <div class="mand-label clearfix">
				<span>Field with  <i class="text-red-1">*</i>  is mandatory
				</span>
			</div>
		 <div class="error-div alert alert-danger alert-dismissible"
				style="display: none;" id="serviceRuleDefMas"></div>
			
          <form:form action="LandOrder.html?create" modelAttribute="tbWorkOrder" name="tbWorkOrder" method="POST" class="form-horizontal" id="workOrderForm">
          	<form:hidden path="woApplicationId"/>
          	<form:hidden path="woServiceId"/>
          	<form:hidden path="woDeptId"/>
          	<form:hidden path="PlumId"/>
          		<form:hidden path="taskId"/>
            <div class="form-group">
             
              <label class="col-sm-2 control-label"><spring:message code="ApplicationForm.departmentName"/></label>
              <div class="col-sm-4">
               <input name="deptName" type="text" class="form-control" value="${deptName}" readonly="readonly">
              </div>
           <label class="col-sm-2 control-label"><spring:message code="onl.loi.serviceName"/></label>
                <div class="col-sm-4">
                    <input name="serviceName" type="text" class="form-control" value="${serviceName}" readonly="readonly">
                </div>
            </div> 
            <div class="form-group">
             
              <label class="col-sm-2 control-label"><spring:message code="chn.applicationNo"/> </label>
              <div class="col-sm-4">
               <input name="woApplicationId" type="text" class="form-control" value="${applicationId}" readonly="readonly">
              </div>
              <label class="col-sm-2 control-label"><spring:message code="chn.name"/> </label>
                <div class="col-sm-4">
                    <input name="woServiceId" type="text" class="form-control" value="${ApplicantFullName}" readonly="readonly">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="trutirejection.applicationdate"/> </label>
                <div class="col-sm-4">
                <div class="input-group">
                  <input id="datepicker2" name="woApplicationDateS" type="text" class="form-control" value="${ApplicarionDate}" readonly="readonly"/>
                  <label for="datepicker2" class="input-group-addon"><i class="fa fa-calendar"></i></label>
                </div>
              </div>
              
              <label class="col-sm-2 control-label"><spring:message code="tbOrganisation.userMobiNo"/></label>
                <div class="col-sm-4">
                    <input name="mobileNo" type="text" class="form-control" value="${mobileNo}" readonly="readonly">
                </div>
						
            </div>
		
             <h4>  Remarks According To  Deparment And Service</h4>
             <div class="mand-label clearfix">
				<span><i class="fa fa-check" ></i> Select At Least One Check-Box
				</span>
			</div>
            <div class="table-responsive">
              <table class="table table-bordered table-condensed">
                <tr>
                  <th width="100">Sr. No.</th>
                  <th>Remarks</th>
                  <th width="50"><label class="padding-left-20 margin-bottom-10"><input type="checkbox" name="All" id="selectall" class="pull-left">All</label></th>
                </tr>
					<c:forEach var="singleDoc" items="${apprejMasList}" varStatus="count">
						<tr>
						        
						         <td>${count.count}</td> 
								<td><textarea name="" cols="" rows=""  class="form-control" readonly="readonly">${singleDoc.artRemarks}</textarea></td>
								<td align="center"><form:checkbox  id="${count.index}" path ="workOrderRemarklist[${count.index}].isSelected"  class="case"  name="case"   value="${singleDoc.artId}" /></td>
								
							
						</tr>
						</c:forEach>
               
              </table>
            </div>
            <div class="text-center margin-top-10">
            	 <input type="button" value="<spring:message code="bt.save"/>"  class="btn btn-success" id="Submit">
                <input type="button"
						onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger  hidden-print" value="Back">
            </div>
          </form:form>
        </div>
      </div>
      </div>
      
</html>