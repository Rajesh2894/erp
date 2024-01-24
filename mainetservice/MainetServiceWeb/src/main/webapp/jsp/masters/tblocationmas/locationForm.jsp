<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%	response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/masters/tblocationmas/locationMaster.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<div class="widget">
      <div class="widget-header">
      <h2><spring:message code="master.locMaster" /></h2>
      <apptags:helpDoc url="LocationMas.html"></apptags:helpDoc>
      </div>
        <div class="widget-content padding">
        <div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
		  <form:form method="post" action="LocationMas.html" id="locmasForm" class="form-horizontal">
		    <form:hidden path="tbLocationMas.hiddeValue" id="hiddeValue"/>
		    <form:hidden path="tbLocationMas.locId" id="locId"/>
		    <form:hidden path="locationNameList" id="locNameList"/>
            <form:hidden path="locationAreaList" id="locAreaList"/>
             
	 		<div class="warning-div alert alert-danger alert-dismissible hide" id="errorDivTaxMas">
				<button type="button" class="close" aria-label="Close" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button>
				<ul><li><i class='fa fa-exclamation-circle'></i>&nbsp;<form:errors path="*"/></li></ul>
			</div>
          <div class="panel-group accordion-toggle" id="accordion_single_collapse">
          	<div class="panel panel-default" id="disableInput">
          	<div class="panel-heading">
          	<h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#collapseone"><spring:message code="master.locMaster.details" /></a></h4></div>
          	<div id="collapseone" class="panel-collapse collapse in">
 	      	 <div class="panel-body">
             <div class="form-group">
             <label class="control-label col-sm-2 required-control" for="locationNameId"><spring:message code="locationMas.locationName" /></label>
             <div class="col-sm-4"><form:input path="tbLocationMas.locNameEng" type="text" id="locationNameId" class="form-control mandColorClass" maxlength="500"/></div>
            <label class="control-label col-sm-2 required-control" for="locationNameRegId"><spring:message code="locationMas.locationNameReg" /></label>
            <div class="col-sm-4"><form:input path="tbLocationMas.locNameReg" type="text" id="locationNameRegId" class="form-control mandColorClass" maxlength="500"/></div>
            </div>
             
            <div class="form-group">
	              <label class="control-label col-sm-2 required-control" for="locationAreaId"><spring:message code="locationMas.locationArea" /></label>
	              <div class="col-sm-4"><form:input path="tbLocationMas.locArea" type="text" id="locationAreaId" class="form-control mandColorClass" maxlength="200"/></div>
	              <label class="control-label col-sm-2 required-control" for="locationAreaRegId"><spring:message code="locationMas.locationAreaReg" /></label>
	              <div class="col-sm-4"><form:input path="tbLocationMas.locAreaReg" type="text" id="locationAreaRegId" class="form-control mandColorClass" maxlength="200"/></div>
            </div>
            <div class="form-group">
	              <label class="control-label col-sm-2 required-control" for="locAddressId"><spring:message code="locationMas.address" /></label>
	              <div class="col-sm-4"><form:textarea path="tbLocationMas.locAddress" type="text" id="locAddressId" class="form-control mandColorClass" maxlength="1000"/></div>
	              <label class="control-label col-sm-2 required-control" for="locAddressRegId"><spring:message code="locationMas.addressReg" /></label>
	              <div class="col-sm-4"><form:textarea path="tbLocationMas.locAddressReg" type="text" id="locAddressRegId" class="form-control mandColorClass" maxlength="1000"/></div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-2" for="pincodeId"><spring:message code="locationMas.pincode" /></label>
              <div class="col-sm-4"><form:input path="tbLocationMas.pincode" type="text" class="form-control hasNumber mandColorClass" maxlength="6" id="pincodeId"/></div>
              <label class="control-label col-sm-2" for="Landmark"><spring:message code="locationMas.landmark" /></label>
              <div class="col-sm-4"><form:input path="tbLocationMas.Landmark" type="text" class="form-control"  id="Landmark"/></div>
            </div>
            
           <div class="form-group">
           <label class="control-label col-sm-2 required-control"><spring:message code="locationMas.isDeptLoc" /></label>
           <div class="col-sm-4">
			<form:select path="tbLocationMas.deptLoc"  name="" class="form-control mandColorClass" id="isDeptLoc">
	             <form:option value="0"><spring:message code="locationMas.select"/></form:option>
	             <form:option value="N"><spring:message code="locationMas.no" /></form:option>
	             <form:option value="Y"><spring:message code="locationMas.yes" /></form:option>
	             </form:select></div>
	       
	       	<label class="col-sm-2 control-label required-control"><spring:message
						code='master.serviceActive' text="Service Active"/></label>
			 <div class="col-sm-4">
			 <c:choose>
			 	<c:when test="${command.modeType eq 'C'}">
			 		<form:select path="tbLocationMas.locActive"  name="" class="form-control mandColorClass" id="locActive" disabled="true">
	             		<form:option value="0"><spring:message code="locationMas.select"/></form:option>
	             		<form:option value="Y" selected="selected"><spring:message code="locationMas.active"/></form:option>
	             		<form:option value="N"><spring:message code="locationMas.inactive"/></form:option>
	        		</form:select>
			 	</c:when>
			 	<c:otherwise>
			 		<form:select path="tbLocationMas.locActive"  name="" class="form-control mandColorClass" id="locActive" onchange="checkIfEmployeeExists()">
	             		<form:option value="0"><spring:message code="locationMas.select"/></form:option>
	             		<form:option value="Y"><spring:message code="locationMas.active"/></form:option>
	             		<form:option value="N"><spring:message code="locationMas.inactive"/></form:option>
	        		</form:select>
			 	</c:otherwise>
			 </c:choose>
			
	        </div>
            </div>
            
            <div class="form-group">
            	<label class="control-label col-sm-2" for="GISNoId"><spring:message code="locationMas.GISNumber" /></label>
           		<div class="col-sm-4"><form:input path="tbLocationMas.GISNo" id="GISNoId" maxlength="15" type="text" class="form-control" /></div>
           		
           		<label class="control-label col-sm-2 required-control"><spring:message code="locationMas.locCategory" text="Location Category"/></label>
           		<c:set var="LCTlookUp" value="LCT" />
           				<apptags:lookupField items="${command.getLevelData(LCTlookUp)}"
						path="tbLocationMas.locCategory"
						cssClass="form-control chosen-select-no-results"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true"
						disabled="${command.modeType eq 'V'}" />
           		
           		
            </div>
            
            <div class="form-group">
            	<label class="control-label col-sm-2" for="lacCodeId"><spring:message code="" text="Location Code" /></label>
           		<div class="col-sm-4">
           			<form:input path="tbLocationMas.locCode" id="lacCodeId" type="text" maxlength="5"
           			 class="form-control" disabled="${command.modeType eq 'V'}"/>
      			 </div>
           		
            </div>
            
            <form:hidden path="filePath" value="${command.filePath}" id="hiddenlocPath"/>
            
             <div class="form-group">
             <c:if test="${command.modeType ne 'V' }">	
             <div id="uploadDoc">
            	<label class="control-label col-sm-2"><spring:message code="location.uploadImage" /></label>
           		<div class="col-sm-4"><apptags:formField fieldType="7" labelCode=""
								hasId="true" fieldPath=""
								isMandatory="false" showFileNameHTMLId="true"
								fileSize="BND_COMMOM_MAX_SIZE"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
								currentCount="0" /></div></div>
							</c:if>	
					<c:if test="${command.modeType ne 'C'}">
 				<div id="viewDoc">	
 					<label class="control-label col-sm-2"><spring:message code="location.viewUploadedDoc" /></label>
					<div id="uploadPreview" class="col-sm-4">
					<ul></ul>
				</div>
				</div>
           		</c:if>
            </div>
            <form:hidden path=""  id="locChnage"/>
              <div class="form-group">
            	<label class="control-label col-sm-2" for="latitudeId"><spring:message code="locationMas.latitude" /></label>
           		<div class="col-sm-4"><form:input path="tbLocationMas.latitude" id="latitudeId" type="text" class="form-control" disabled="${command.modeType eq 'V'}"/></div>
      
            	<label class="control-label col-sm-2" for="longitudeId"><spring:message code="locationMas.longitude" /></label>
           		<div class="col-sm-4"><form:input path="tbLocationMas.longitude" id="longitudeId" type="text" class="form-control" disabled="${command.modeType eq 'V'}"/></div>
            </div>
            
             <div class="form-group">
              <label class="control-label col-sm-2"><spring:message code="locationMas.areaMapping" /></label>
              <div class="col-sm-10">
              	 <label class="checkbox-inline"><form:checkbox path="tbLocationMas.locElectrolWZMappingDto[0].electoralChkBox"  class="mandColorClass" id="electoralChkBxId"/><spring:message code="locationMas.electoralWardZone" /> </label>
              	 <label class="checkbox-inline"><form:checkbox path="tbLocationMas.locRevenueWZMappingDto[0].revenueChkBox"   class="mandColorClass" id="revenueChkBxId"/><spring:message code="locationMas.revenueWardZone" /> </label>
              	 <label class="checkbox-inline"><form:checkbox path="tbLocationMas.locOperationWZMappingDto[0].opertionalChkBox"  class="mandColorClass" id="operationalChkBxId" /><spring:message code="locationMas.operationalWardZone" /></label>
              </div>
            </div>
            </div>
            </div>
            </div>
            
              <div class="panel panel-default electoral-box">
                <div class="panel-heading">
                  <h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#collapsetwo"><spring:message code="locationMas.electoralWardZone" /></a> </h4>
                </div>
                <div id="collapsetwo" class="panel-collapse collapse in">
                  <div class="panel-body">
                     <div class="form-group"> <apptags:lookupFieldSet baseLookupCode="EWZ" hasId="true" pathPrefix="tbLocationMas.locElectrolWZMappingDto[0].codIdElecLevel"
						isMandatory="true" hasLookupAlphaNumericSort="true" hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control"  showAll="false" /></div>
                  </div>
                </div>
              </div>
              <div class="panel panel-default revenue-box">
                <div class="panel-heading">
                  <h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#collapsethree"><spring:message code="locationMas.revenueWardZone" /></a> </h4>
                </div>
                <div id="collapsethree" class="panel-collapse collapse in">
                  <div class="panel-body">
 					<div class="form-group">
                     <label class="control-label col-sm-2" for="codIdRevLevel1"><spring:message code="location.fieldMapping" /></label>
 	                  <div class="col-sm-4">
						<form:select path="tbLocationMas.locRevenueWZMappingDto[0].codIdRevLevel1" id="codIdRevLevel1" class="form-control mandColorClass">
						   <form:option value="0"><spring:message code="master.selectDropDwn" text="Select" /></form:option>
						   <form:options items="${command.mapRenewalList}" /> 
							</form:select>
					   	</div>
							  </div>	   		
                  </div>
                </div>
              </div>
              <div class="panel panel-default operational-box">
                <div class="panel-heading">
                  <h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#collapsefour"><spring:message code="locationMas.operationalWardZone" /></a> </h4>
                </div>
                <div id="collapsefour" class="panel-collapse collapse in">
                  <div class="panel-body">
                  	<div class="form-group">
                    	<label class="col-sm-2 control-label required-control" for="dpDeptId"><spring:message code="employee.department" /></label>
                         <div class="col-sm-4">
                        	 <c:if test="${command.modeType eq 'C'}">
		                        	<form:select path="tbLocationMas.locOperationWZMappingDto[0].dpDeptId" id="dpDeptId" class="form-control mandColorClass" onchange="addDepartment(this)">
										<form:option value="0"> <spring:message code="workflow.form.select.department" /></form:option>
										<c:forEach items="${command.deptList}" var="departmentData">
											<option value="${departmentData.dpDeptid}" id="${departmentData.dpDeptcode}" >${departmentData.dpDeptdesc}</option>						
										</c:forEach>
							       </form:select> 
					       </c:if>
					    <c:if test="${command.modeType eq 'E' || command.modeType eq 'V'}"> 
						       <form:select path="tbLocationMas.locOperationWZMappingDto[0].dpDeptId" id="dpDeptId" class="form-control mandColorClass" onchange="addDepartmentEdit(this)">
									<form:option value="0"> <spring:message code="workflow.form.select.department" /></form:option>
									<c:forEach items="${command.deptList}" var="departmentData">
										<form:option value="${departmentData.dpDeptid}" id="${departmentData.dpDeptcode}" >${departmentData.dpDeptdesc}</form:option>						
									</c:forEach>
						       </form:select> 
						        
					        </c:if> 
					   </div> 
                    </div>
                    <div class="form-group"><div id="areaMappingId"></div> </div>
                  </div>
                </div>
              </div>
            </div>
            
       <div class="text-center padding-top-10">
		 <c:if test="${command.modeType eq 'C' || command.modeType eq 'E'}"> 
        <input type="submit" class="btn  btn-success" id="save" name="save" value="<spring:message code="master.save"/>" onclick="return saveData(this);"  />
         </c:if> 
        <c:if test="${command.modeType eq 'C'}">
        <button type="Reset" class="btn btn-warning" id="resetLocation" onclick="resetLocationForm(this);" ><spring:message code="bt.clear"/></button></c:if>
        <input type="button" id="backBtn" class="btn btn-danger" onclick="back()" value="<spring:message code="bt.backBtn"/>" />	
		</div> 	
           </form:form>
        </div>
      </div>
	