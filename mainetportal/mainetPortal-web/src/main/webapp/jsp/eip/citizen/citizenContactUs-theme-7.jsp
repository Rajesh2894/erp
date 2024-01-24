<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<script src="js/eip/citizen/citizenContactUs.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>

<script>
    $( document ).ready(function() {
        console.log( "document loaded" );
        
    });
 
   
    </script>
    
<script type="text/javascript">




function contactUsAction(selectObject){
	
	
	
	var value = selectObject.value; 
	if(value==='Administrator'){
	    document.getElementById("Administrator").style.display = "inline";
	    document.getElementById("operator").style.display = "none";
	    
	   } 
	if(value==='operator'){
		document.getElementById("operator").style.display = "inline";
		document.getElementById("Administrator").style.display = "none";
	}
	
}
</script>

<script type="text/javascript">
        function codeAddress() {
            //alert('ok');
            var myParam = location.search.split('id=')[1]
        	//alert("hello"+myParam);
            if(myParam == 1){
            	//document.getElementById("Administrator").style.display = "none";
            	document.getElementById("operator").style.display = "none";
            	 
            	 $('#eipUserContactUs-contactUs').val("Administrator").change();
            }
			if(myParam == 2){
				document.getElementById("Administrator").style.display = "none";
				$('#eipUserContactUs-contactUs').val("operator").change();
	        	//document.getElementById("operator").style.display = "none";
            }
        	
        }
        window.onload = codeAddress;
</script>


 <ol class="breadcrumb">
	<li class="breadcrumb-item"><a href="CitizenHome.html"><i class="fa fa-home"></i> <spring:message code="menu.home"/></a></li>
	<li class="breadcrumb-item active"><spring:message code="ContactUs" text="Contact Us"/></li>
</ol>
 
<div class="container-fluid dashboard-page">
  <div class="col-sm-12" id="nischay">
 			<h2><spring:message code="eip.citizen.footer.contactUs" /></h2>
		<div class="widget-content padding dashboard-page">
			<%--<div class="alert alert-info padding-10 text-center" role="alert">
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
					<p>
						<spring:message code="eip.admin.contactUs.msg2" />
						<br>
				</c:if>

			</p> 
			</div>--%>
			<ul id="demo2" class="nav nav-tabs">
				<li class="active"><a href="#demo2-home" title="Contact"
					data-toggle="tab"><strong class="fa fa-phone-square"></strong> <spring:message
							code="eip.citizen.footer.contactUs" /></a></li>
				<li class=""><a href="#demo2-profile" title="contactUs"
					data-toggle="tab"><strong class="fa fa-info-circle"></strong> <spring:message
							code="eip.admin.contactUs.ulbdet" /></a></li>
			</ul>
			<div class="tab-content tab-boxed contact-us-tab-content">
				<div class="tab-pane fade active in" id="demo2-home">
						<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
								<address>
									<c:choose>
										<c:when test="${not empty  command.contactUsListByFlag}">
											<c:forEach items="${command.contactUsListByFlag}" var="list1"
												varStatus="var">
		
												<div class="thumbnail" id="${list1.designationEn}">
													<c:choose>
														<c:when
															test="${userSession.getCurrent().getLanguageId() == 1}">
		
															<p class="contact-name">
																<strong> ${list1.contactNameEn}</strong>
															</p>
															<address class="responsive">
																<p class="designation">${list1.designationEn}</p>
																<p>
																	<spring:message code="eip.admin.contactUs.phoneNo" />
																	: ${list1.telephoneNo1En}
																</p>
																<p>
																	<spring:message code="feedback.Mobile" />
																	:${list1.telephoneNo2En}
																</p>
																<p>
																<spring:message code="Email" text="Email"/> : <a href="mailto:${list1.email1}" class="responsive">${list1.email1}</a>
															   </p>
															</address>
														</c:when>
		
														<c:otherwise>
														
															<p class="contact-name">
																<strong>${list1.contactNameReg}</strong>
															</p>
															<address class="responsive">
																<p class="designation">${list1.designationReg}</p>
																<p>
																	<spring:message code="eip.admin.contactUs.phoneNo" />
																	:${list1.telephoneNo1En}
																</p>
																<p>
																	<spring:message code="feedback.Mobile" />
																	:${list1.telephoneNo2En}
																</p>
																<%-- <p>
																	<spring:message code="feedback.Email" />
																	<a href="${list1.email1}"></a>
																</p> --%>
																<p>
																<spring:message code="Email" text="Email"/> : <a href="${list1.email1}" class="responsive">${list1.email1}</a>
																</p>
																<!-- <a href="mailto:muzaffarpur.ulb@gmail.com"
																	class="table-responsive">muzaffarpur.ulb@gmail.com</a> -->
															</address>
														</c:otherwise>
													</c:choose>
												</div>
	
											</c:forEach>
										</c:when>
										<c:otherwise>
											<spring:message code="eip.admin.contactUs.nocontactmsg" />
										</c:otherwise>
									</c:choose>
								</address>
							</div>
				</div>
				<div class="tab-pane fade table-responsive" id="demo2-profile">
					<table class="table table-hover table-striped table-bordered">
						<thead>
							<tr>
								<th class="text-center"><spring:message code="SerialNo" text="Serial No."/></th>
								<th class="text-center"><spring:message code="eip.admin.contactUs.ulbdet" /></th>
								<th class="text-center"><spring:message code="eip.admin.contactUs.contactdet" /></th>
								<th class="text-center"><spring:message code="eip.admin.contactUs.oficeno" /></th>
								<th class="text-center"><spring:message code="citizen.editProfile.emailId" /></th>
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
											<span>
												<spring:message code="eip.admin.contactUs.phoneNo" />
												: ${list.telephoneNo1En}
											</span>
											<span>
												<spring:message code="feedback.Mobile" />
												: ${list.telephoneNo2En}
											</span>
										</td>
										<td>
											<span>${list.email1}</span>
										</td>
									</c:if>
									<c:if test="${userSession.getCurrent().getLanguageId() == 2}">
										<td>${lk.count}</td>
										<td>${list.departmentReg}</td>
										<td>${list.contactNameReg},${list.designationReg},${list.address2Reg}</td>
										<td>
											<span>
												<spring:message code="eip.admin.contactUs.phoneNo" />
												:  ${list.telephoneNo1En}
											</span>
											<span>
												<spring:message code="feedback.Mobile" />
												:  ${list.telephoneNo2En}
											</span>
										</td>
										<td>
											<span>${list.email1}</span>
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