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
<script src="js/rnl/master/tenantAddForm.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div  class="content animated slideInDown">
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="Tenant Master" text="Tenant Master"></spring:message></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"><span><spring:message code="master.field.message"/> <i class="text-red-1">*</i> <spring:message code="master.field.mandatory.message"/></span></div>
          <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			 <form:form method="post" action="TenantMaster.html" class="form-horizontal" name="tenantForm" id="tenantForm" >
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="tenantMaster.hiddeValue" id="hiddeValue"/>
				<div class="panel-group accordion-toggle" id="accordion_single_collapse">
                     <div class="panel panel-default">
		                <div class="panel-heading">
		                  <h4 class="panel-title"> <a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#estate"><spring:message code="rnl.tenant.info" text="Tenant Information"></spring:message>  </a> </h4>
		                </div>
                          <div id="estate" class="panel-collapse collapse in">
                                  <div class="panel-body">
				
				                          <div class="form-group">
											      <label for="code" class="control-label col-sm-2"><spring:message code='estate.label.code'/></label>
											      <div class="col-sm-4">
											        <form:input  type="text" class="form-control" path="tenantMaster.code" id="code" disabled="true" />
											      </div>
							                      <label class="control-label col-sm-2 required-control" for="type"><spring:message code="rnl.tenant.type" text="Tenant Type"></spring:message></label>
							                        <c:set var="baseLookupCode" value="TNT"/>
													<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" 
												   					 path="tenantMaster.type" cssClass="form-control"	 																			
																	 selectOptionLabelCode="Select" hasId="true" isMandatory="true"/>
                     
                                           </div>
                                            <div class="hidebox individual group">
							                      <div class="form-group">
							                        <label class="control-label col-sm-2 required-control" for="title"><spring:message code="rnl.tenant.title" text="Title"></spring:message></label>
							                          <c:set var="baseLookupCode" value="TTL"/>
													  <apptags:lookupField items="${command.getLevelData(baseLookupCode)}" path="tenantMaster.title" cssClass="form-control" selectOptionLabelCode="Select" hasId="true" isMandatory="true"/>
							                        <label for="fName" class="control-label col-sm-2 required-control"><spring:message code="rnl.master.fname" text="First Name"></spring:message></label>
							                        <div class="col-sm-4">
							                          <form:input  type="text" class="form-control" path="tenantMaster.fName" maxlength="500" id="fName" />
							                        </div>
                                            </div>    
                                          <div class="form-group">
					                        <label class="control-label col-sm-2" for="mName"><spring:message code="rnl.master.mname" text="Middle Name"></spring:message></label>
					                        <div class="col-sm-4">
					                          <form:input  type="text" class="form-control" path="tenantMaster.mName" maxlength="500" id="mName" />
					                        </div>
					                        <label class="control-label col-sm-2 required-control" for="lNmae"><spring:message code="rnl.master.lname" text="Last Name"></spring:message></label>
					                        <div class="col-sm-4">
					                          <form:input  type="text" class="form-control" path="tenantMaster.lName" maxlength="500" id="lNmae" />
					                        </div>
					                      </div>
					                      </div>
					                      <div class="hidebox organization">
					                      <div class="form-group">
					                        <label class="control-label col-sm-2 required-control" for="tntOrgName"><spring:message code="rnl.master.name" text="Name"></spring:message></label>
					                        <div class="col-sm-10">
					                          <form:input  type="text" class="form-control" path="tenantMaster.tntOrgName" maxlength="1000" id="lNmae" />
					                        </div>
					                      </div>
					                    </div>
					                    <div class="form-group">
					                      <label class="control-label col-sm-2" for="address1"><spring:message code="" text=""></spring:message><spring:message code="rnl.master.address" text="Address 1"></spring:message><span class="mand">*</span></label>
					                      <div class="col-sm-10">
					                         <form:input  type="text" class="form-control mandColorClass" path="tenantMaster.address1" maxlength="500" id="address1"></form:input>
					                        <small><spring:message code="rnl.master.street.address" text="Street address, P.O. box, Company name, c/o"></spring:message></small> </div>
					                    </div>
					                    <div class="form-group">
					                      <label class="control-label col-sm-2" for="address2"><spring:message code="rnl.master.addres2" text="Address 2"></spring:message><span class="mand">*</span></label>
					                      <div class="col-sm-10">
					                          <form:input  type="text" class="form-control mandColorClass" path="tenantMaster.address2" maxlength="500" id="address2"></form:input>
					                        <small><spring:message code="rnl.master.apartment" text="Apartment, suite, unit, building, floor, etc."></spring:message></small> </div>
					                    </div>
                    
					                     <div class="form-group">
					                      <label class="control-label col-sm-2 required-control" for="pinCode"><spring:message code="rnl.master.pincode" text="Pincode"></spring:message></label>
					                      <div class="col-sm-4">
					                        <form:input  type="text" class="form-control mandColorClass hasNumber" path="tenantMaster.pinCode" maxlength="6" id="pinCode" />
					                      </div>
					                    </div>
					                    <div class="form-group">
					                      <label class="control-label col-sm-2 required-control" for="emailId"> <spring:message code="rnl.master.email" text="Email"></spring:message></label>
					                      <div class="col-sm-4">
					                        <form:input  type="text" class="form-control mandColorClass" path="tenantMaster.emailId" maxlength="50" id="emailId" />
					                      </div>
					                      <label class="control-label col-sm-2 required-control" for="mobileNumber"><spring:message code="rnl.master.mobileno" text="Mobile No."></spring:message></label>
					                      <div class="col-sm-4">
					                        <form:input  type="text" class="form-control mandColorClass" path="tenantMaster.mobileNumber" maxlength="14" id="mobileNumber" />
					                      </div>
					                    </div>
					                    <div class="form-group">
					                      <label class="control-label col-sm-2" for="phoneNumber"><spring:message code="rnl.master.telephone" text="Telephone"></spring:message></label>
					                      <div class="col-sm-4">
					                         <form:input  type="text" class="form-control mandColorClass" path="tenantMaster.phoneNumber" maxlength="30" id="phoneNumber" />
					                      </div>
					                      <label class="control-label col-sm-2" for="faxNumber"><spring:message code="rnl.master.faxno" text="Fax No"></spring:message></label>
					                      <div class="col-sm-4">
					                         <form:input  type="text" class="form-control mandColorClass" path="tenantMaster.faxNumber" maxlength="30" id="faxNumber" />
					                      </div>
					                    </div>
					                    <div class="form-group">
					                      <label class="control-label col-sm-2" for="aadharNumber"><spring:message code="rnl.master.aadharno" text="Aadhaar Number"></spring:message></label>
					                      <div class="col-sm-4">
					                        <form:input  type="text" class="form-control mandColorClass hasNumber" path="tenantMaster.aadharNumber" maxlength="12" id="aadharNumber" />
					                      </div>
					                      <label class="control-label col-sm-2" for="panNumber"><spring:message code="rnl.master.panno" text="PAN Number"></spring:message></label>
					                      <div class="col-sm-4">
					                        <form:input  type="text" class="form-control mandColorClass" path="tenantMaster.panNumber" maxlength="10" id="panNumber" />
					                      </div>
					                    </div>
					                    <c:set var="d" value="0" scope="page" />
				                        <div class="hidebox group">
						                      <div class="table-responsive margin-top-10" id="childProperty">
						                        <table class="table table-bordered table-striped" id="customFields">
						                          <tr>
						                            <th width="100"><spring:message code="rnl.tenant.title" text="Title"></spring:message><span class="mand">*</span></th>
						                            <th><spring:message code="rnl.master.fname" text="First Name"></spring:message><span class="mand">*</span></th>
						                            <th><spring:message code="rnl.master.mname" text="Middle Name"></spring:message></th>
						                            <th><spring:message code="rnl.master.lname" text="Last Name"></spring:message><span class="mand">*</span></th>
						                            <th width="130"><spring:message code="rnl.master.mobileno" text="Mobile No"></spring:message><span class="mand">*</span></th>
						                            <th width="130"><spring:message code="rnl.master.email" text="Email"></spring:message>Email<span class="mand">*</span></th>
						                            <th><spring:message code="rnl.master.aadharno" text="Aadhaar Number"></spring:message></th>
						                            <th><spring:message code="rnl.master.panno" text="PAN Number"></spring:message></th>
						                            <th width="50"><spring:message code="rnl.master.addremove" text="Add/Remove"></spring:message></th>
						                          </tr>
						                        
			
														
													    <c:if test="${command.modeType eq 'V' || command.modeType eq 'E'}">
													   
													      <c:forEach items="${command.tenantMaster.tenantOwnerMasters}" var="list">
													          <tr class="appendableClass">
															       <td> <form:input type="hidden" id="ownerId_${d}"  path="tenantMaster.tenantOwnerMasters[${d}].tntOwnerId" value="${list.tntOwnerId}"/>
																  <c:set var="baseLookupCode" value="TTL" />
																	<form:select path="tenantMaster.tenantOwnerMasters[${d}].title" class="form-control" id="title_${d}">
																		<form:option value="0"><spring:message code="select" text="select"/></form:option>
																		<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
																			<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select>
																	<script>
																		   $(document).ready(function(){
																			   $('#title_'+'${d}').val('${list.title}').attr("selected", "selected");
																		   });
																	 </script>
																</td>
																<td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].fName" class="form-control" id="fName_${d}"  value="${list.fName}" maxlength="500"/></td>
												                <td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].mName" class="form-control" id="mName_${d}"  value="${list.mName}" maxlength="500"/></td>
												                <td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].lName" class="form-control" id="lName_${d}"  value="${list.lName}" maxlength="500"/></td>
												                <td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].mobileNumber" class="form-control" id="mobileNumber_${d}"  value="${list.mobileNumber}" maxlength="14"/></td>
												                <td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].emailId" class="form-control" id="emailId_${d}"  value="${list.emailId}" maxlength="50"/></td>
												                 <td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].aadharNumber" class="form-control hasNumber" id="aadharNumber_${d}"  value="${list.aadharNumber}" maxlength="12"/></td>
												                 <td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].panNumber" class="form-control" id="panNumber_${d}"  value="${list.panNumber}" maxlength="10"/></td>
																<td><a href="javascript:void(0);" data-toggle="tooltip"
																	data-placement="top" id="addCF" class="addCF btn btn-success btn-sm"
																	data-original-title="Add"><i class="fa fa-plus-circle"></i></a>
																	<a href="javascript:void(0);" data-toggle="tooltip"
																	data-placement="top" id="remCF" class="remCF btn btn-danger btn-sm"
																	data-original-title="Delete"><i class="fa fa-trash"></i></a>
															   </td>
															 </tr>
															 <c:set var="d" value="${d+1}" scope="page" />
															 </c:forEach>
													     
													     
													   </c:if> 
													    <c:if test="${command.modeType eq 'C'}"> 
													        <tr class="appendableClass">
													          <td> <form:input type="hidden" id="ownerId_${d}" value="" path="tenantMaster.tenantOwnerMasters[${d}].tntOwnerId" />
																  <c:set var="baseLookupCode" value="TTL" />
																	<form:select path="tenantMaster.tenantOwnerMasters[${d}].title" class="form-control" id="title_${d}">
																		<form:option value="0"><spring:message code="select" text="select"/></form:option>
																		<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
																			<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select>
																</td>
																<td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].fName" class="form-control" id="fName_${d}" maxlength="500"/></td>
												                <td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].mName" class="form-control" id="mName_${d}" maxlength="500"/></td>
												                <td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].lName" class="form-control" id="lName_${d}" maxlength="500"/></td>
												                <td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].mobileNumber" class="form-control" id="mobileNumber_${d}" maxlength="14"/></td>
												                <td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].emailId" class="form-control" id="emailId_${d}" maxlength="50"/></td>
												                 <td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].aadharNumber" class="form-control hasNumber" id="aadharNumber_${d}" maxlength="12"/></td>
												                 <td><form:input  path="tenantMaster.tenantOwnerMasters[${d}].panNumber" class="form-control" id="panNumber_${d}" maxlength="10"/></td>
																<td><a href="javascript:void(0);" data-toggle="tooltip"
																	data-placement="top"  class="addCF btn btn-success btn-sm"
																	data-original-title="Add"><i class="fa fa-plus-circle"></i></a>
																	<a href="javascript:void(0);" data-toggle="tooltip"
																	data-placement="top"  class="remCF btn btn-danger btn-sm"
																	data-original-title="Delete"><i class="fa fa-trash"></i></a>
															   </td>
															 </tr>  
													    </c:if> 
									              </table>
						                      </div>
				                            </div>
				                           
				                  </div>
				                    
				          </div>
				     </div>       
				     <div class="panel panel-default">
									                <div class="panel-heading">
									                  <h4 class="panel-title"><a data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#Applicant"> <spring:message code="rl.property.label.Upload.Attachment" text="Upload Attachment"></spring:message> <small class="text-blue-2">(Upload File upto 5MB and only .pdf or .doc)</small> </a> </h4>
									                </div>
									                <div id="Applicant" class="panel-collapse collapse">
									                  <div class="panel-body">
									                    <div class="table-responsive margin-top-10">
									                      <table class="table table-hover table-bordered table-striped">
														                <tbody>
														                  <tr>
														                    <th><spring:message code='estate.table.column.srno'/></th>
														                    <th><spring:message code='estate.table.column.doc'/></th>
														                    <th><spring:message code='estate.table.column.header'/></th>
														                  </tr>
														                  <tr>
														                    <td>1</td>
														                    <td><spring:message code='estate.table.upload.image'/></td>
														                    <td>
														                       <c:if test="${command.modeType eq 'V'}">
														                           <c:forEach items="${command.documentList}" var="lookUp" varStatus="lk">
														                               <c:if test="${lookUp.serialNo eq 0}">
																				          <apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" actionUrl="TenantMaster.html?Download"/>
																				       </c:if>
																			      </c:forEach>
														                       </c:if>
														                       <c:if test="${command.modeType ne 'V'}">
														                             <apptags:formField
																							fieldType="7" fieldPath="tenantMaster.imagesPath" labelCode="" currentCount="0" 
																							showFileNameHTMLId="true"  fileSize="COMMOM_MAX_SIZE" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT" folderName="0"
																							validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" /> 
																				</c:if>			
														                    </td>
														                  </tr>
														                  <tr>
														                    <td>2</td>
														                    <td><spring:message code='estate.table.upload.terms'/></td>
														                    <td>  
														                    <c:if test="${command.modeType eq 'V'}">
														                           <c:forEach items="${command.documentList}" var="lookUp" varStatus="lk">
														                               <c:if test="${lookUp.serialNo eq 1}">
																				          <apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" actionUrl="TenantMaster.html?Download"/>
																			            </c:if>
																			      </c:forEach>
														                       </c:if>
														                       <c:if test="${command.modeType ne 'V'}">
														                       <apptags:formField fieldType="7" labelCode="" hasId="true" fieldPath="tenantMaster.docsPath"
																					isMandatory="false" showFileNameHTMLId="true" fileSize="COMMOM_MAX_SIZE"
																							maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT" validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																												folderName="1"	currentCount="1" /></c:if></td>
																					     
														                  </tr>
														                </tbody>
                                                               </table>
									                    
									                    </div>
									                    </div>
				                             </div>
				                           </div>          
			   </div>	
			             <div class="text-center padding-top-10">
                            <c:if test="${command.modeType ne 'V'}"><button type="button" class="btn btn-success btn-submit"
								    id="submitTenant"><spring:message code="bt.save"/></button></c:if>
                            <c:if test="${command.modeType eq 'C'}"><button type="Reset" class="btn btn-warning" id="resetTenant" ><spring:message code="bt.clear"/></button></c:if>
						    <input type="button" id="backBtn" class="btn btn-danger" onclick="back()" value="<spring:message code="bt.backBtn"/>" />
						  </div> 
					 <form:hidden path="removeChildIds"/>	  
			</form:form>	
			
			
			
			
      
        </div>
      </div>
 </div>    