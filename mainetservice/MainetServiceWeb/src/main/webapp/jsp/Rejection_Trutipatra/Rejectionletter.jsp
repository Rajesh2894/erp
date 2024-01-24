<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script src="js/siteinspection/siteinspection.js"></script>
<script src="js/cfc/scrutiny.js"></script>
<!-- <script type="text/javascript" src="js/mainet/jquery.js"></script> -->
<script type="text/javascript" src="js/commanremark/commanremark.js"></script>
<script type="text/javascript">
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

	function submitForm(obj){

	var checkedNum = $('.case:checked').length;
	if(${firstattempt == 'firstattempt'}){
	 if (!checkedNum) {
		 var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select a Remark.</li></ul></div>';
			$("#rejectionRuleDefMas").html(errMsg);
			$("#rejectionRuleDefMas").show();
			return false;
	 }
	}
	requestData=$("#tbRejectionMst").serialize();
	
    var response = __doAjaxRequest("RejectionMst.html?create", 'POST',requestData, false,'html');
	
	$(".content").html(response);
	
}
</script>
 <c:if test="${Turtidocument == 'Turtidocument'}">
    <ol class="breadcrumb">
      <li><a href="../index.html"><i class="fa fa-home"></i></a></li>
      <li> <spring:message code="trutirejection.Turti"/></li>
    </ol>
  </c:if>  
   <c:if test="${Turtidocument != 'Turtidocument'}"> 
    <ol class="breadcrumb">
      <li><a href="../index.html"><i class="fa fa-home"></i></a></li>
      <li><spring:message code="trutirejection.Rejection"/></li>
    </ol>
   </c:if> 
    <!-- ============================================================== --> 
    <!-- Start Content here --> 
    <!-- ============================================================== -->
    <div class="content"> 
      <!-- Start info box -->
      <div class="widget">
       <c:if test="${Turtidocument == 'Turtidocument'}">
        <div class="widget-header">
          <h2><spring:message code="trutirejection.Turti"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
       </c:if> 
       <c:if test="${Turtidocument != 'Turtidocument'}">
       <div class="widget-header">
          <h2><spring:message code="trutirejection.Rejection"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        </c:if>
         <%-- <c:if test="${Secondattempt == 'Secondattempt'}">
         <div class=" text-center error-div alert alert-danger alert-dismissible">
         <h4> <spring:message code="trutirejection.Reprintmessage"/> </h4>
         </div>
     
         </c:if>      --%>
        <div class="widget-content padding">
        	 <div class="error-div alert alert-danger alert-dismissible"
				style="display: none;" id="rejectionRuleDefMas"></div>
          <form:form action="RejectionMst.html?create"  modelAttribute="tbRejectionMst" method="POST" class="form-horizontal" id="tbRejectionMst"> 
            <h4 class="margin-top-0"> <spring:message code="trutirejection.ApplicationDetails"/></h4>
            <div class="form-group">
              <label class="col-sm-2 control-label"> <spring:message code="trutirejection.ServiceName"/> :</label>
              <div class="col-sm-4">
               <!--  <input name="" type="text" class="form-control"> -->
                <form:input path="rejServicename" cssClass="form-control" readonly="true"/>
              </div>
              <form:hidden path="rejServiceId" cssClass="form-control"/>
              <form:hidden path="labelId" cssClass="form-control"/>
              <form:hidden path="rejType" cssClass="form-control"/>
              <form:hidden path="level" cssClass="form-control"/>
              <form:hidden path="lableValue" cssClass="form-control"/>
             <%--  <form:hidden path="rejServiceId" cssClass="form-control"/> --%>
               
              <label class="col-sm-2 control-label"><spring:message code="siteinspection.ApplicationNo"/>  :</label>
              <div class="col-sm-4">
                <!-- <input name="" type="text" class="form-control"> -->
                 <form:input path="rejApplicationId" cssClass="form-control" readonly="true"/>
              </div>
            </div>
  <c:if test="${firstattempt == 'firstattempt'}">     
            <div class="form-group">
            <label class="col-sm-2 control-label"> <spring:message code="trutirejection.Remarks"/>:</label>
              <div class="col-sm-10">
              
              <form:textarea path="rejRemarks" cssClass="form-control"/>
           
              </div>
			</div>
            <h4><spring:message code="trutirejection.DetailsRemarks"/></h4>
            <div class="table-responsive">
              <table class="table table-bordered table-striped">
					  <tr>
					     <th><label class="padding-left-20 margin-bottom-10"><input type="checkbox" name="All" id="selectall" class="pull-left">All</label></th>
					    <th><spring:message code="trutirejection.Remarks"/></th>
					     
					  </tr>
					  <c:forEach var="singleDoc" items="${list}" varStatus="count">
						<tr>
						        <td align="center"><form:checkbox  id="${count.index}" path ="rejectionlist[${count.index}].isSelected"  class="case"  name="case"   value="${singleDoc.artId}" /></td> 
								<td>${singleDoc.artRemarks}</td>
								
							
						</tr>
						</c:forEach>
					 
          </table>
</div>

<c:if test="${Turtidocument == 'Turtidocument'}">
<h4 class="margin-top-10">Document </h4>
<fieldset class="fieldRound">
								<div class="overflow">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="tp.serialNo" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="tp.docName" text="Document Name" /></label></th>
												     <th><label class="tbold">Download</label></th>
												     <th><label class="tbold">Remark</label></th>
												</tr>

												<c:forEach items="${documentList}" var="lookUp"
													varStatus="lk">
													<tr>
														<td><label>${lookUp.clmSrNo}</label></td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td><label>${lookUp.clmDescEngl}</label></td>
															</c:when>
															<c:otherwise>
																<td><label>${lookUp.clmDesc}</label></td>
															</c:otherwise>
														</c:choose>
														  <td>
														  <c:set var="links" value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
														 
										
							
							
		                                                <div class="fa fa-download">
								                         <apptags:filedownload filename="${lookUp.attFname}" filePath="${links}" actionUrl="NewWaterConnectionForm.html?Download"></apptags:filedownload>
								                        </div>
					                                     
			
															
														</td>
														<td><label> </label><input  type="text" name="documentList[${lk.index}].clmRemark"></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
	</c:if>		
     </c:if> 
     
            <div class="text-center padding-top-10">
              <c:if test="${Secondattempt == 'Secondattempt'}">
              <form:button type="button" onclick="submitForm(this);" class="btn btn-success btn-submit"><spring:message code="trutirejection.print"/></form:button>
              </c:if>
               <c:if test="${firstattempt == 'firstattempt'}"> 
              <form:button type="button" onclick="submitForm(this);"  class="btn btn-success btn-submit"><spring:message code="trutirejection.Reprint"/></form:button>
              </c:if>
            <!--   <button type="submit" class="btn btn-info">Preview</button> -->
              <input type="button"
						onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${rejApplicationId}','${lableID}','${ServiceId}')"
						class="btn btn-danger" value="Back" id="Submit">
             <!--  <button type="button" class="btn btn-danger">Reset</button> -->
            </div>
          </form:form>
        </div>
      </div>
      <!-- End of info box --> 
      
      <!--Wedgets End Here--> 
      <!-- ============================================================== --> 
      <!-- End content here --> 
      <!-- ============================================================== --> 
      
      <!-- End right content --> 
    </div>
    
   
    <!-- Footer End --> 
    <!--Scroll To Top--> 
    <a class="tothetop" href="javascript:;"><i class="fa fa-angle-up"></i></a> 
    
