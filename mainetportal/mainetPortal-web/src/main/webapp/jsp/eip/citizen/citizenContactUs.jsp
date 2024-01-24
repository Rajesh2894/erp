<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%  response.setContentType("text/html; charset=utf-8");%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>

<script src="js/eip/citizen/citizenContactUs.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script type="text/javascript">
$(function(){
    $('input[type="text"]').change(function(){
        this.value = $.trim(this.value);
    });
    $('textarea[type="text"]').change(function(){
        this.value = $.trim(this.value);
    });
});


$( document ).ready(function()
		{
			
			jQuery('.hasMobileNoExt').keyup(function () 
			{ 
			    this.value = this.value.replace(/[^0-9 +]/g, '');
			    $(this).attr('maxlength','6');
			});
		});



$(function() {
	$("#frmCitizenContactUs").validate();
});
</script>
<ol class="breadcrumb" id="CitizenService">
   <li class="breadcrumb-item">
      <a href="CitizenHome.html">
         <i class="fa fa-home"></i> 
         <spring:message code="menu.home"/>
      </a>
   </li>
   <li class="breadcrumb-item active">
      <spring:message code="ContactUs" text="Contact Us"/>
   </li>
</ol>
<div class="container-fluid  dashboard-page citizen-contact-us">
   <div class="col-sm-12" id="nischay">
      <h2>
         <spring:message code="eip.citizen.footer.contactUs" />
      </h2>
      <div class="widget-content padding dashboard-page">
          <div class="alert alert-warning padding-10 text-center" role="alert">
            <p>
               <c:if test="${userSession.getCurrent().getLanguageId() == 1}">
                  <spring:message code="eip.admin.contactUs.msg1" />
                  <b>
                  ${userSession.getCurrent().getOrganisation().getONlsOrgname()}</b>
                  <spring:message code="eip.admin.contactUs.msg2" />
               </c:if>
               <c:if test="${userSession.getCurrent().getLanguageId() == 2}">
                  <b>${userSession.getCurrent().getOrganisation().getONlsOrgnameMar()}</b>
                  <spring:message code="eip.admin.contactUs.msg1" />
            	<p><spring:message code="eip.admin.contactUs.msg2" /><br></p>
            	
            </c:if>
            <p style="color:#982525;"><spring:message code="eip.working.hours.msg" text="Working hours:- 9:45 AM to 5:45 PM (Monday - Friday)" /><br></p>
         </div> 
         <ul id="demo2" class="nav nav-tabs">
            <li class="active">
               <a href="#demo2-home" title="Contact"
                  data-toggle="tab">
                  <strong class="fa fa-phone-square"></strong> 
                  <spring:message
                     code="eip.citizen.footer.contactUs" />
               </a>
            </li>
            <li class="">
               <a href="#demo2-profile" title="contactUs"
                  data-toggle="tab">
                  <strong class="fa fa-info-circle"></strong> 
                  <spring:message text="test"
                     code="eip.admin.contactUs.ulbdetails" />
               </a>
            </li>
         </ul>
         <div class="tab-content tab-boxed">
            <div class="tab-pane active in" id="demo2-home">
               <div class="row">
                  <div class="col-sm-6">
                     <div class="error-div"></div>
                     <div class="error1-div"></div>
                     <jsp:include page="/jsp/tiles/validationerror.jsp" />
                     <form:form method="post" action="CitizenContactUs.html"
                        name="frmCitizenContactUs" id="frmCitizenContactUs"
                        class="form-horizontal">
                        <div class="form-group">
                           <label class="col-xs-12 col-sm-3 control-label required-control" for="phoneno">
                              <spring:message code="MobileNo" text="Mobile No"/>
                           </label>
                           
                           <div class="col-xs-3 col-sm-2 col-md-2 col-lg-2 ">
                           <c:set var="MobExtFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.contact.mobile.ext.msg') }"></c:set>
						   <form:input path="eipUserContactUs.moblieExtension" maxlength="6" aria-label="Mobile Extension"
							id="Ext" cssClass="form-control hasMobileNoExt " placeholder="+91" autocomplete="off" data-rule-required="true" data-msg-required="${MobExtFieldLevelMsg}"/>
							</div>
							<div class="col-xs-9 col-sm-6 col-md-6 col-lg-6 ">
							<c:set var="MobNoFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.contact.mobile.no.msg') }"></c:set>
							<form:input path="eipUserContactUs.phoneNo" maxlength="12" aria-label="Mobile number"
							class="input2 hasMobileNo12 form-control" placeholder="999999999999" autocomplete="off" data-rule-required="true" data-msg-required="${MobNoFieldLevelMsg}"/>
							
                           </div>
                        </div>                 
                        <div class="form-group">
                           <label class="col-sm-3 control-label required-control" for="Fname">
                              <spring:message code="FirstName" text="First Name"/>
                           </label>
                           <div class="col-sm-8">
                           	  <c:set var="FNameFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.contact.fname.msg') }"></c:set>
                              <form:input type="text"
                                 class="input2 hasSpecialChara form-control" id="Fname" name="" path="eipUserContactUs.firstName" maxlength="200" autocomplete="off" data-rule-required="true" data-msg-required="${FNameFieldLevelMsg}"></form:input>
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-sm-3 control-label required-control" for="Lname">
                              <spring:message code="LastName" text="Last Name"/>
                           </label>
                           <div class="col-sm-8">
                           	  <c:set var="LNameFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.contact.lname.msg') }"></c:set>
                              <form:input type="text"
                                 class="input2 hasSpecialChara form-control" id="Lname" name=""
                                 path="eipUserContactUs.lastName" maxlength="200" autocomplete="off" data-rule-required="true" data-msg-required="${LNameFieldLevelMsg}"></form:input>
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-sm-3 control-label required-control" for="emailId">
                              <spring:message code="Email" text="Email"/>
                           </label>
                           <div class="col-sm-8">
                           	  <c:set var="EmailFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.contact.email.msg') }"></c:set>
                              <form:input type="text" class="input2 form-control"
                                 id="emailId" name="" path="eipUserContactUs.emailId" maxlength="200" autocomplete="off" data-rule-required="true" data-msg-required="${EmailFieldLevelMsg}"></form:input>
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-sm-3 control-label" for="DescQuery">
                              <spring:message code="Description" text="Description"/><span class="mand">*</span>
                           </label>
                           <div class="col-sm-8">
                           	  <c:set var="DescriptionFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.contact.description.msg') }"></c:set>
                              <form:textarea type="text" class="input2 form-control" id="DescQuery"
                                 name="" path="eipUserContactUs.descQuery" maxlength="1000" autocomplete="off" data-rule-required="true" data-msg-required="${DescriptionFieldLevelMsg}" 
                                 onkeyup="countCharacter(this,1000,'DescriptionCount')"  onfocus="countCharacter(this,1000,'DescriptionCount')"></form:textarea>
                           <div>
							 <spring:message code="charcter.remain" text="characters remaining " /><span id="DescriptionCount">1000</span>
						    </div>
                           </div>
                        </div>
                        
                        <div class="form-group" >
							<div class="col-xs-6 col-sm-5 col-md-5 " id="captchaLDiv">
								<c:set var="rand"><%=java.lang.Math.round(java.lang.Math.random() * 10000)%></c:set>
								<img id="cimg" src="CitizenContactUs.html?captcha&id=${rand}"  alt="captcha value <%=request.getDateHeader("captcha3")%>"/>
								<a href="#" onclick="doRefreshLoginCaptcha()"><i class="fa fa-refresh"><span class="hide">Refresh</span></i></a>
							</div>
							<div class="col-xs-6 col-sm-6 ">
								<label for="captchaSessionLoginValue" class="hide">captchaP</label>
								<c:set var="CaptchaFieldLevelMsg" value="${command.getAppSession().getMessage('eip.citizen.contact.captcha.msg') }"></c:set>
								<form:input path="captchaSessionLoginValue"
								cssClass="form-control hasNumber" placeholder='${captchaP}'
								autocomplete="off" maxlength="4" data-rule-required="true" data-msg-required="${CaptchaFieldLevelMsg}" />
							</div>
					</div>
					
<%--                         <div class="form-group">
                           <label class="col-sm-3 control-label" for="eipUserContactUs.attPath">
                              <spring:message code="rti.UploadFile" />
                           </label>
                           <div class="col-sm-4">
                              <c:set var="errmsg">
                                 <spring:message code="file.upload.size" />
                              </c:set>
                              <apptags:formField fieldType="7" labelCode="" hasId="true"
                                 folderName="0" maxFileCount="CHECK_LIST_MAX_COUNT"
                                 fieldPath="eipUserContactUs.attPath"
                                 fileSize="BND_COMMOM_MAX_SIZE"
                                 validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
                                 isMandatory="false" showFileNameHTMLId="true"
                                 currentCount="0" />
                           </div>
                        </div> --%>
                        <div class="text-center">
                           <input type="submit" value="<spring:message code="Save" text="Save"/>" onClick="return saveContactForm(this);" class="btn btn-success">
                           <input type="button" class="btn btn-warning" value="<spring:message code="rstBtn"/>" onclick="resetForm1(this)" >
                        </div>
                     </form:form>
                  </div>
                  <div class="hidden-lg hidden-sm hidden-md"><br/></div>
                  <div class="col-sm-6">
                     <address>
                        <c:choose>
                           <c:when test="${not empty  command.contactUsListByFlag}">
										<table class="table table-hover table-striped table-bordered">
											<thead>
												<tr>
													<th><spring:message code="citizen.contactus.key.contact" text="Key Contact" />
													</th>

												</tr>
											</thead>
											<tbody>
												<c:forEach items="${command.contactUsListByFlag}"
													var="list1" varStatus="var">
													<tr>
														<td>
														   <c:choose>
                                    <c:when
                                       test="${userSession.getCurrent().getLanguageId() == 1}">
                                       <p>
                                          <strong> ${list1.contactNameEn}</strong>
                                       </p>
                                       <p>
                                       <spring:message code="eip.admin.contactUs.departmentName" text="Department Name" />
                                       : ${list1.designationEn}
                                       </p>
									 <c:if test="${not empty list1.telephoneNo1En }">
									  <p>
                                          <spring:message code="eip.admin.contactUs.phoneNo" />
                                          : ${list1.telephoneNo1En}
                                       </p>
									</c:if>
									<c:if test="${not empty list1.telephoneNo2En }">
                                       <p>
                                          <spring:message code="feedback.Mobile" />
                                          :${list1.telephoneNo2En}
                                       </p>
									</c:if>  
									<c:if test="${not empty list1.email1 }">
                                       <a href="mailto:${list1.email1}" class="responsive">${list1.email1}</a>
									 </c:if>
                                    </c:when>
                                    <c:otherwise>
                                       <strong>${list1.contactNameReg}</strong>
                                       <div class="responsive">
                                          <p>${list1.designationReg}</p>
                                     <c:if test="${not empty list1.telephoneNo1En }">     
										  <p>
                                             <spring:message code="eip.admin.contactUs.phoneNo" />
                                             :${list1.telephoneNo1En}
                                          </p>
									</c:if>
									<c:if test="${not empty list1.telephoneNo2En }">
                                          <p>
                                             <spring:message code="feedback.Mobile" />
                                             :${list1.telephoneNo2En}
                                          </p>
									</c:if>
									<c:if test="${not empty list1.email1 }">
                                          <a href="${list1.email1}" class="responsive">${list1.email1}</a>
									</c:if> 
                                          <hr>
                                       </div>
                                    </c:otherwise>
                                 </c:choose>
														</td>
													</tr>



												</c:forEach>
												</td>
											</tbody>
										</table>
									</c:when>
                           <c:otherwise>
                              <spring:message code="eip.admin.contactUs.nocontactmsg" />
                           </c:otherwise>
                        </c:choose>
                     </address>
                  </div>
               </div>
            </div>
            <div class="tab-pane" id="demo2-profile">
            	<div class="table-responsive">
	               <table class="table table-hover table-striped table-bordered">
	                  <thead>
	                     <tr>
	                        <th>
	                           <spring:message code="SerialNo" text="Serial No."/>
	                        </th>
	                        <th>
	                           <spring:message code="eip.admin.contactUs.ulbdet" />
	                        </th>
	                        <th>
	                           <spring:message code="eip.admin.contactUs.contactdet" />
	                        </th>
	                        <th>
	                           <spring:message code="eip.admin.contactUs.oficeno" />
	                        </th>
	                        <th>
	                           <spring:message code="citizen.editProfile.emailId" />
	                        </th>
	                     </tr>
	                  </thead>
	                  <tbody>
	                     <c:forEach items="${command.contactUsList}" var="list"
	                        varStatus="lk">
	                        <tr>
	                           <c:if test="${userSession.getCurrent().getLanguageId() == 1}">
	                              <td>${lk.count}</td>
	                              <td>${list.departmentEn}</td>
	                              <td>${list.contactNameEn},${list.designationEn},${list.address2En}</td>
	                              <td>
	                                 <c:if test="${not empty list.telephoneNo1En }">
	                                 
	                                  <spring:message code="eip.admin.contactUs.phoneNo" />
	                                    : ${list.telephoneNo1En}
	                                
	                                 </c:if> 
	                                  <c:if test="${not empty list.telephoneNo2En }">
	                                 
	                                    <spring:message code="feedback.Mobile" />
	                                    : ${list.telephoneNo2En}
	                                
	                                 </c:if>
	                              </td>
	                              <td>
	                                 ${list.email1}
	                              </td>
	                           </c:if>
	                           <c:if test="${userSession.getCurrent().getLanguageId() == 2}">
	                              <td>${lk.count}</td>
	                              <td>${list.departmentReg}</td>
	                              <td>${list.contactNameReg},${list.designationReg},${list.address2Reg}</td>
	                              <td>
	                              	<c:if test="${not empty list.telephoneNo1En }">
	                                 
	                                    <spring:message code="eip.admin.contactUs.phoneNo" />
	                                    :  ${list.telephoneNo1En}
	                                
	                                 </c:if> 
	                                 <c:if test="${not empty list.telephoneNo2En }">
	                                 
	                                    <spring:message code="feedback.Mobile" />
	                                    :  ${list.telephoneNo2En}
	                                
	                                 </c:if> 
	                              </td>
	                              <td>
	                                 ${list.email1}
	                              </td>
	                           </c:if>
	                        </tr>
	                     </c:forEach>
	                  </tbody>
	               </table>
               </div>
            </div>
         </div>
      </div>
   </div>
</div>