<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/rnl/master/rl-file-upload.js"></script>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<script src="js/rnl/master/estateAddForm.js"></script>
 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="master.estate.form.name"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"><span><spring:message code="master.field.message"/> <i class="text-red-1">*</i> <spring:message code="master.field.mandatory.message"/></span></div>
          <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
					<form:errors path="*" cssClass="errorblock" element="div" />
				</ul>
			</div>
			 <form:form method="post" action="Location.html" class="form-horizontal" name="locationForm" id="locationForm" >
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
				       <div class="form-group">
				              <label for="regNo" class="control-label col-sm-2"><spring:message code='estate.label.regno'/></label>
				              <div class="input-group">
				                   <form:input path="estateMaster.regDate" type="text" class="form-control"  id="regDate"/>
				                   <label class="input-group-addon" for=regDate><i class="fa fa-calendar"></i></label>
				                </div>
				              <label for="regDate" class="control-label col-sm-2"><spring:message code='estate.label.regDate'/></label>
				              <div class="col-sm-4">
				                <div class="input-group">
				                   <form:input path="estateMaster.regDate" type="text" class="form-control"  id="regDate"/>
				                   <label class="input-group-addon" for=regDate><i class="fa fa-calendar"></i></label>
				                </div>
				              </div>
                       </div>		
                       <div class="form-group">
		                     <label class="control-label col-sm-2 required-control" for="OccupancyType"><spring:message code='rl.property.label.Occupancy'/></label>
		                      <c:set var="baseLookupCode" value="ROC"/>
						      <apptags:lookupField items="${command.getLevelData(baseLookupCode)}" path="estatePropMaster.occupancy" cssClass="form-control chosen-select-no-results" selectOptionLabelCode="Select" hasId="true" isMandatory="true"/>
		                      <div id="Operational" style="display:none">
                                     <label class="col-sm-2 control-label required-control" for="Department"><spring:message code="master.complaint.department" text="Department"></spring:message></label>
					                  <div class="col-sm-4"> 
						                    <form:select id="locId" class="form-control required-control" path="estateMaster.locId" >
												<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
											</form:select>
									   </div>	
				              </div>
				        </div>
			 </form:form>			
			
		</div>	
		</div>