<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/care/grievance-status.js"></script>
<style>
.additional-btn{display:none;}
#status .content{padding: 0px !important;}
</style>

<%-- <apptags:breadcrumb></apptags:breadcrumb> --%>
 <ol class="breadcrumb">
      <li  class="breadcrumb-item"><a href="CitizenHome.html"><strong class="fa fa-home"></strong><spring:message code="home" text="Home"/>  </a></li>
      <li class="breadcrumb-item active"><spring:message code="" text="Chief Minister Ward Office"/></li>
</ol>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="text-center">
				<spring:message code="care.ward.heading" text="Mukhyamantri Ward Kaaryalaya " />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			<form:form action="" method="get" class="form-horizontal">
				<%-- <h4>
					<spring:message code="app.status.head" text="Enter your Application Number to know the Application Status" />
				</h4> --%>
				<div class="form-group margin-top-10" >
					<div class=" col-sm-8">
					<div class="cm-div">
					<label class="col-sm-6 control-label required-control" for="searchString">
						<spring:message code="app.status.head"	text="Enter your Application Number to know the Application Status" />
					</label>
					<div class="col-sm-5">					
					<%-- <spring:message code="app.no.lbl" text="Please enter Application No." var="placeholderSerachString"/>
						<input type="text" class="form-control hasNumber" name="searchString" id="searchString"  maxlength="19"
						value="" required="required" placeholder="${placeholderSerachString}">
					</div> --%>
					
						<spring:message code="care.placeholder.serachString"
							text="lease enter Token No. Or Mobile No."
							var="placeholderSerachString" />
						<input type="text" class="form-control " name="searchString"
							id="searchString" maxlength="" value="" required="required"
							placeholder="${placeholderSerachString}">
							</div>
					
					<div class="col-sm-1">			
					<button type="button" id="submitPreLogin" name="submitPreLogin"
						class="btn btn-success" onclick="displayComplaintStatus(this)">
						<spring:message code="care.submit" />
					</button>
					</div>
					
					</div>
					<div class="col-sm-12">
					<div class="panel panel-default about-ward" style="border: none;margin: 10px 0px 10px 35px;">
					 <div id="status">
					 
					   </div>
					   </div>
					</div>
					</div>
					<div class="col-sm-4 text-center">
					
					<img src="assets/img/cm-logo.png"></img>
					</div>
					
					
				</div>
				
				<div class="panel panel-default about-ward" style="background: white !important;">
					  <div class="panel-body" style="background: white !important;">
					  <h2 class="text-greenWard"><spring:message code="care.sm.ward.office.heading"/></h2>
					  <p class="text-para"><spring:message code="care.sm.ward.office"/></p></div>
				</div>
					<div class="row">
					<div class="embed-responsive embed-responsive-16by9 col-sm-8">
 					 <iframe class="embed-responsive-item" id="frame1" src="<spring:message code="care.dashboard.report.url"></spring:message>RP_CARE.rptdesign&__toolbar=false&__showtitle=false" ></iframe>
					</div>
					
					<div class="col-sm-4">
					<div class="panel panel-default about-ward" style="margin: 0px 0px 20px 5px;">
					  <div class="panel-body text-center">
					  <h2 class="text-greenWard"><spring:message code="care.login.text" text="For Departmental use click on below link "/></h2>
					  <a  id="" href="<spring:message code="service.admin.home.url"/>"	target="_blank" class="btn btn-success text-center"><i class="fa fa-sign-in" aria-hidden="true"></i>
						<spring:message code="eip.admin.login.FormHeader.IPRD"  text="LOGIN"/>
					</a>
					</div>
					
					</div>
					</div>
					</div>
					
			</form:form>
		</div>
	</div>
	
	
	
	
</div>
<!-- End Content here -->