<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>



<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility" />

<!-- <link href="https://fonts.googleapis.com/css?family=Alegreya+Sans" rel="stylesheet"> -->
<link rel="stylesheet" href="assets/libs/bootstrap/css/bootstrap.min.css">
<link href="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.min.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js" ></script>
<script src="assets/js/jquery.marquee.min.js"></script>
<link href="assets/css/style-blue-theme-5.css" rel="stylesheet" type="text/css"/>
<style>

	

@keyframes spinner {
  to {transform: rotate(360deg);}
}
 
.spinner45:before {
  content: '';
  box-sizing: border-box;
  position: absolute;
  top: 50%;
  left: 50%;
  width: 60px;
  height: 60px;
  margin-top: -10px;
  margin-left: -10px;
  border-radius: 50%;
  border: 8px solid #ccc;
  border-top-color: #d74701;
  animation: spinner .6s linear infinite;
  z-index:100000;
}
#appDate{
display: block;
    width: 100%;
    height: 34px;
    padding: 6px 12px;
    font-size: 14px;
    line-height: 1.42857143;
    color: #555;
    background-color: #fff;
    background-image: none;
    border: 1px solid #ccc;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgb(0 0 0 / 8%);
    box-shadow: inset 0 1px 1px rgb(0 0 0 / 8%);
    -webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
    -o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
    transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
}
}
</style>
<script>
	
		  
	$(function() {
		var curHeight = $('.about1').height();
		var curHeight2 = $('.about2').height();
		if (curHeight >= 117 || curHeight2 >= 117) {
			$('.read-more').css('display', 'block');
		} else
			$('.read-more').css('display', 'none');
	});
	function showPropertyTaxCalculator(obj) {
		var data = {};
		var response = __doAjaxRequest(
				'PropertyTaxCalculator.html?viewApplication', 'POST', data,
				false);
		$(formDivName).html(response);
	}

	$(function() {
		$('.drag1 .col-lg-12').find('.col-lg-6').removeClass('col-lg-6')
				.addClass('col-lg-4');
		$('.drag1 .col-lg-12 .dark-blue').removeClass('height-390').addClass(
				'height-200');
		$('.drag1 .col-lg-12 .list-number').removeClass('col-sm-12');
		$('.drag1 .col-lg-12 .dark-blue .helpline').removeClass('help-line')
				.addClass('help-line1');
	});
	
	
</script>
<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}">
	<script>
		$(function() {
			$('#myCarousel').css('margin-top', '0px');
		});
	</script>
</c:if>

<!-- JS for MDDA Pie Chart starts -->
<script>
$(document).ready(function(){
	
var options = {
	animationEnabled: true,
	 title:{
		text: "<spring:message code="theme5.portal.MDDAServiceWiseAppCount"/>",
		fontSize: 25,
		fontFamily: "arial",
		fontColor: "#0f4f73"
	}, 
	legend:{
		horizontalAlign: "right",
		verticalAlign: "center",
		fontSize: 12,
        fontFamily: "arial",
        fontColor: "#000000",
        fontWeight: "normal"
	},
	data: [{
		type: "pie",
		showInLegend: true,
		toolTipContent: "<b>{name}</b>: ${y} {count}",
		indexLabel: "{count}",
		legendText: "{name}",
		indexLabelPlacement: "inside",
		indexLabelFontColor: "#ffffff",
	    indexLabelFontSize: 15,
		dataPoints: [
			   
		]
	}]
};
for(var i=1;i<=($('#sizeMDDA').attr("value"))-1;i++){
	options.data[0].dataPoints.push({y:$('#service'+i).attr("value"), count:$('#service'+i).attr("value") ,name:$('#service'+i).attr("name")});	
}
$("#chartContainerService").CanvasJSChart(options);


var option = {
		animationEnabled: true,
		 title:{
			text: "<spring:message code="theme5.portal.MDDABPMS"/>",
			fontSize: 25,
			fontFamily: "arial",
			fontColor: "#0f4f73"
		}, 
		legend:{
			horizontalAlign: "right",
			verticalAlign: "center",
			fontSize: 12,
	        fontFamily: "arial",
	        fontColor: "#000000",
	        fontWeight: "normal"
		},
		data: [{
			type: "doughnut",
			showInLegend: true,
			toolTipContent: "<b>{name} </b>: ${y} {y}",
			indexLabel: "{count}: {y}",
			legendText: "{name}",
			indexLabelPlacement: "outside",
			dataPoints: [
			   
			]
		}]
	};
	for(var i=1;i<=($('#sizeMDDAstatus').attr("value"))-1;i++){

		if($('#status'+i).attr("name")=='ISSUED'){
		 option.data[0].dataPoints.push({y:$('#status'+i).attr("value"), count:$('#status'+i).attr("name") ,name:$('#status'+i).attr("name"),color:"green"});	
		 }
		  else if($('#status'+i).attr("name")=='REJECTED'){
			 option.data[0].dataPoints.push({y:$('#status'+i).attr("value"), count:$('#status'+i).attr("name") ,name:$('#status'+i).attr("name"),color:"red"});	
			 }
		  else if($('#status'+i).attr("name")=='APPROVED'){
			 option.data[0].dataPoints.push({y:$('#status'+i).attr("value"), count:$('#status'+i).attr("name") ,name:$('#status'+i).attr("name"),color:"yellow"});	
			 }
		   else{
		      option.data[0].dataPoints.push({y:$('#status'+i).attr("value"), count:$('#status'+i).attr("name") ,name:$('#status'+i).attr("name")});	
		 }	
	}
	$("#chartContainerStatus").CanvasJSChart(option);

});

</script>
<!-- JS for MDDA Pie Chart ends -->

${command.setThemeMap()}
${command.getAboutUs()}

<div class="invisibeHead"></div>
<div id="main">

<!-- Website Name starts -->
<div class="container">
	<div class="website-name-section">
		<c:if test="${userSession.languageId eq 1}">
			<span class="website-name">
				<%-- <h1>${userSession.organisation.ONlsOrgname}</h1>
				<h3>${userSession.organisation.ONlsOrgnameMar}</h3> --%>
			</span>
		</c:if>
		<c:if test="${userSession.languageId eq 2}">
			<span class="website-name">
				<%-- <h1>${userSession.organisation.ONlsOrgnameMar}</h1>
				<h3>${userSession.organisation.ONlsOrgname}</h3> --%>
			</span>
		</c:if>
	</div>
</div>
<!-- Website Name ends -->

<%-- Defect #164740 --%>
<%-- Splash Screen starts --%>
<div class="splash-screen-section">
	<c:forEach items="${command.getAllhtml('Splash Screen')}" var="splashScreen"> ${splashScreen} </c:forEach>
</div>
<%-- Splash Screen ends --%>

<!-- Slider Start -->
	<c:if test="${command.themeMap['SLIDER_IMG'] ne 'I'}">
		<div class="container-fluid slider-main" id="container-fluid1">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="sld">		
			<div class="row">
				<div id="myCarousel" class="carousel slide carousel-fade"
					data-ride="carousel">
					<c:set var="maxfilecount"
						value="${userSession.getSlidingImgLookUps().size()}" />						
					<!-- Indicators -->
					<ol class="carousel-indicators hidden-xs">
						<c:if test="${maxfilecount gt 0 }">
							<c:forEach begin="0" end="${maxfilecount-1}" varStatus="index">
								<c:choose>
									<c:when test="${index.index eq 0}">
										<li data-target="#myCarousel" data-slide-to="${index.index}"
											class="active"></li>
									</c:when>
									<c:otherwise>
										<li data-target="#myCarousel" data-slide-to="${index.index}"></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>

					</ol>

					<!-- Wrapper for slides -->
					<div class="carousel-inner">

						<c:if test="${userSession.getSlidingImgLookUps().size() gt 0}">
							
							<c:forEach items="${userSession.slidingImgLookUps}" var="slide"
								varStatus="status">

								<c:set var="data" value="${fn:split(slide,'*')}" />
								<c:set var="image" value="${data[0]}" />
								<c:choose>
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
										<c:set var="caption" value="${data[1]}" />
									</c:when>
									<c:otherwise>
										<c:set var="caption" value="${data[2]}" />
									</c:otherwise>
								</c:choose>
								<c:if test="${status.index eq 0 }">
									<div class="item active">
										<img src="./${image}" alt="${caption}" />
										<c:if test="${not empty caption}">
											<div class="container hidden-xs">
												<div class="carousel-caption animate fadeInLeft">
													<h1>${caption}</h1>
												</div>
											</div>
										</c:if>
									</div>
								</c:if>
								<c:if test="${status.index ne 0 }">
									<div class="item active">
										<img src="./${image}" alt="${caption}" />
										<c:if test="${not empty caption}">
											<div class="container hidden-xs">
												<div class="carousel-caption animate fadeInLeft">
													<h1>${caption}</h1>
												</div>
											</div>
										</c:if>
									</div>
								</c:if>
							</c:forEach>
						</c:if>

					</div>

					<!-- Left and right controls -->
					<a class="left carousel-control hidden-xs" href="#myCarousel"
						data-slide="prev"><span
						class="glyphicon glyphicon-chevron-left"></span><span
						class="sr-only">Previous</span></a> <a
						class="right carousel-control hidden-xs" href="#myCarousel"
						data-slide="next"><span
						class="glyphicon glyphicon-chevron-right"></span><span
						class="sr-only">Next</span></a>
				</div>
			</div>
			</div>			
					
						
			<%-- <c:if test="${command.themeMap['NEWS'] ne 'I'}">
			<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
				 <div class="panel-group sidemenu" id="accordion" role="tablist" aria-multiselectable="true">
					  <div class="panel panel-primary">
						<div class="panel-heading" role="tab" id="newsupdates">
						  <h4 class="panel-title">
							<a role="button" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
							  <spring:message code="portal.Latest.News" text="Latest News" />
							</a>
						  </h4>
						</div>
						<div id="collapseTwo" class="panel-collapse" role="tabpanel" aria-labelledby="newsupdates">
						  <div class="list-group scrolllistbox" style="height:410px"> 
						  <c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status">			  
						  <span class="list-group-item">
						  <c:set var="links" value="${fn:split(lookUp.attach,',')}" />
						  <c:forEach items="${links}" var="download" varStatus="count">
						  <c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
						  <c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
						  <c:set var="link"	value="${stringUtility.getStringAfterChars(download)}" />
						  <c:choose>
						  <c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
						  <c:choose>
						  <c:when test="${isDMS}">
						  <a href="javascript:void(0);" 
						     onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
						  </c:when>
						  <c:otherwise>
						  <apptags:filedownload filename="EIP"
												filePath="${download}"									
												actionUrl="CitizenHome.html?Download"
												eipFileName="${lookUp.announceDescEng}"></apptags:filedownload>
						  </c:otherwise>
						  </c:choose>
						  <br/><span class="date"><i class="fa fa-calendar" aria-hidden="true"></i>
						  <fmt:formatDate value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
						  </c:when>
						  <c:otherwise>
						  <c:choose>
					      <c:when test="${isDMS}">			  
						  <a href="javascript:void(0);" 
						     onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
						  </c:when>
						  <c:otherwise>
						  <apptags:filedownload filename="EIP"
												filePath="${download}"
												actionUrl="CitizenHome.html?Download"
												eipFileName="${lookUp.announceDescReg}"></apptags:filedownload>
						  </c:otherwise>
						  </c:choose>
						  <br/><span class="date"><i class="fa fa-calendar" aria-hidden="true"></i>
						  <fmt:formatDate value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
						  </c:otherwise>
						  </c:choose>
						  </c:forEach>
						  </span> 
						  </c:forEach>
						  </div>
						</div>
					  </div>
					</div>
				</div>	
			</c:if> --%>
			<div class="clearfix"></div>
		</div>
	</c:if>
<!-- Slider End -->
<div class="clear"></div>

<%-- <!-- New Helpline Number -->
	<c:if test="${command.themeMap['HELPLINE_NO'] ne 'I'}">
	<i id="helpline"></i>
	<div class="helpline-main">
		<div class="helpline-text"><spring:message code="theme5.portal.contact" text="Contact" /></div>
		<div class="helpline-inner-container">
			<div class="helpline-list">
				<div class="list-number">
					<spring:message code="theme5.portal.address.name" text="Address" />
				</div>
				<div class="list-text">
					<spring:message code="theme5.portal.address" text="Saatvik Tower, 777, Kaulagarh Road, Rajender Nagar, Dehradun-248001" />
				</div>
			</div>
			<div class="helpline-list">
				<div class="list-number ">
					<spring:message code="theme5.portal.phone" text="Phone" />
				</div>
				<div class="list-text">
					<spring:message code="theme5.portal.phone.num" text="0135- 2750984" />
				</div>
			</div>
			<div class="helpline-list">
				<div class="list-number">
					<spring:message code="theme5.portal.email" text="Email" />
				</div>
				<div class="list-text">
					<spring:message code="theme5.portal.email.address" text="smartcityddn@gmail.com" />
				</div>
			</div>
			<div class="helpline-list">
				<div class="list-number">
					<spring:message code="theme5.portal.cin" text="CIN (Corporate Identification Number)" />
				</div>
				<div class="list-text">
					<spring:message code="theme5.portal.cin.num" text="U45309UR2017SGC008127" />
				</div>
			</div>
			<div class="helpline-list">
				<div class="list-number">
					<spring:message code="theme5.portal.gstn" text="GSTN" />
				</div>
				<div class="list-text">
					<spring:message code="theme5.portal.gstn.num" text="05AAGCD3672G1ZR" />
				</div>
			</div>
		</div>
	</div>
	</c:if>
<!-- New Helpline Number ends -->
<div class="clear"></div> --%>
	
			<%-- <c:if test="${command.themeMap['SLIDER_IMG'] eq 'I'}">
			<c:if test="${command.themeMap['NEWS'] ne 'I'}">			
			<div class="col-lg-12 col-md-12 col-sm-12" style="margin-top:283px" id="lnsh">
				 <div class="panel-group sidemenu" id="accordion" role="tablist" aria-multiselectable="true">
					  <div class="panel panel-primary">
						<div class="panel-heading" role="tab" id="newsupdates">
						  <h4 class="panel-title">
							<a role="button" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
							  <spring:message code="portal.Latest.News" text="Latest News" />
							</a>
						  </h4>
						</div>
						<div id="collapseTwo" class="panel-collapse" role="tabpanel" aria-labelledby="newsupdates">
						  <ul class="list-group scrolllistbox" style="height:410px"> 
						  <c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status">			  
						  <li class="list-group-item">
						  <c:set var="links" value="${fn:split(lookUp.attach,',')}" />
						  <c:forEach items="${links}" var="download" varStatus="count">
						  <c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
						  <c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
						  <c:set var="link"	value="${stringUtility.getStringAfterChars(download)}" />
						  <c:choose>
						  <c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
						  <c:choose>
						  <c:when test="${isDMS}">
						  <a href="javascript:void(0);" 
						     onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
						  </c:when>
						  <c:otherwise>
						  <apptags:filedownload filename="EIP"
												filePath="${download}"									
												actionUrl="CitizenHome.html?Download"
												eipFileName="${lookUp.announceDescEng}"></apptags:filedownload>
						  </c:otherwise>
						  </c:choose>
						  <br/><span class="date"><i class="fa fa-calendar" aria-hidden="true"></i>
						  <fmt:formatDate value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
						  </c:when>
						  <c:otherwise>
						  <c:choose>
					      <c:when test="${isDMS}">			  
						  <a href="javascript:void(0);" 
						     onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
						  </c:when>
						  <c:otherwise>
						  <apptags:filedownload filename="EIP"
												filePath="${download}"
												actionUrl="CitizenHome.html?Download"
												eipFileName="${lookUp.announceDescReg}"></apptags:filedownload>
						  </c:otherwise>
						  </c:choose>
						  <br/><span class="date"><i class="fa fa-calendar" aria-hidden="true"></i>
						  <fmt:formatDate value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
						  </c:otherwise>
						  </c:choose>
						  </c:forEach>
						  </li> 
						  </c:forEach>
						  </ul>
						</div>
					  </div>
					</div>
				</div>	
			</c:if></c:if> --%>
			
			
	<%-- ${command.setContactList()} --%>
	<%-- select Organization Box in case of slider/Minister not active start --%>
	<%-- <c:if test="${command.themeMap['COMMITTEE_MEMBERS'] ne 'A'}">
		<!--SELECT ORGNAIZATION START -->
		<c:if
			test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">
			<div
				class="about-bg container-fluid clear  minister-details padding-bottom_20 margin-top-90">
				<div class="minister-org ">

					<div
						class="col-sm-12 col-md-4 col-lg-3 col-xs-12 col-md-offset-8 col-lg-offset-9  org-select hidden-xs">
						<div class="content-tab">
							<div class="form-group">
								<h3>
									<spring:message code="ULB.head" text="Select ULB" />
								</h3>
								<form:form name="selectMunicipalForm" id="selectMunicipalForm"
									action="" class="list" method="get">
									<div class="row">
										<label class="hidden" for="selectedOrg">Select
											Department</label> <select name="orgId" id="selectedOrg"
											data-content="" class="form-control chosen-select-no-results">
											<option value="-1" selected style="display: none;"><spring:message code="Select" text="Select" /></option>
											<c:forEach items="${command.userSession.organisationsList}"
												var="orglist">
												<optgroup label="${orglist.key}">
													<c:forEach items="${orglist.value}" var="org">
														<c:if test="${userSession.languageId eq 1}">
															<option value="${org.orgid}">${org.ONlsOrgname}</option>
														</c:if>
														<c:if test="${userSession.languageId eq 2}">
															<option value="${org.orgid}">${org.ONlsOrgnameMar}</option>
														</c:if>
													</c:forEach>
												</optgroup>
											</c:forEach>
										</select>
										<button type="button" id="submitMunci" class="btn btn-success">
											<spring:message code="change" text="Change" />
										</button>
									</div>
								</form:form>
							</div>
						</div>
					</div>

				</div>
			</div>
		</c:if>
	</c:if> --%>
	<%-- select Organization Box in case of slider/Minister not active end --%>
	<%--SELECT ORGNAIZATION END --%>
	
	
	<%-- <!-- E-Services, Department Services & Public Notice Tabs starts -->
	<div class="container">
		<div class="scroll-tabs element-animate">
			<ul class="nav nav-pills">
				<li class="active smooth-scroll">
					<a href="#e-serv">
						<span class="st-icon"><i class="fa fa-desktop" aria-hidden="true"></i></span>
						<span class="st-label"><spring:message code="theme5.portal.e.services" text="E-Services" /></span>
					</a>
				</li>
				<c:if test="${not empty command.themeMap['EXTERNAL_SERVICES'] && command.themeMap['EXTERNAL_SERVICES'] ne 'I'}">
					<li class="smooth-scroll">
						<a href="#depser">
							<span class="st-icon"><i class="fa fa-users" aria-hidden="true"></i></span>
							<span class="st-label"><spring:message code="theme5.portal.department.services" text="Department Services" /></span>
						</a>
					</li>
				</c:if>
				<li class="smooth-scroll">
					<a href="#PublicNotice">
						<span class="st-icon"><i class="fa fa-bullhorn" aria-hidden="true"></i></span>
						<span class="st-label"><spring:message code="theme5.portal.public.notice" text="Public Notice" /></span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<!-- E-Services, Department Services & Public Notice Tabs ends -->
	<div class="clear"></div> --%>
	
	<div class="bg-blue">
		<div class="container">
			<div class="scroll-messages col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<c:if test="${command.themeMap['CIVIC_MESSAGE'] ne 'I'}">
					<%-- CIVIC MESSAGE Marquee --%>
					<div class="message-container">
						<div class="col-xs-6 col-sm-3 col-md-2 col-lg-2">
							<span class="message-title">
								<spring:message code="theme5.portal.civic.message" text="Civic Message" />
							</span>
						</div>
						<div class="marquee col-xs-6 col-sm-9 col-md-10 col-lg-10">
							<c:forEach items="${command.getAllhtml('CIVIC MESSAGE')}" var="civicMsg"> ${civicMsg} </c:forEach>
						</div>
					</div>
					<%-- CIVIC MESSAGE Marquee ends --%>
				</c:if>
			</div>
		</div>
	
		<%-- MDDA Dashboard starts --%>
		<div class="container">
			<div id="Mdda" class="mdda-graphs mdda-section">
			<div class="owl-carousel owl-theme">
			<c:if test="${not empty command.map && not empty command.map.get('sizeOfMDDAService') && not empty command.mddastatus.get('sizeOfMDDAStatus')}">
				<input type="hidden" name="size" value="${command.map.get('sizeOfMDDAService')}" id="sizeMDDA">
				<input type="hidden" name="size" value="${command.mddastatus.get('sizeOfMDDAStatus')}" id="sizeMDDAstatus">				
				
				<c:forEach items="${command.map}" var="entry" varStatus="noOfService">
				<input type="hidden" name="${entry.key}" value="${entry.value}" id="service${noOfService.count}">
				</c:forEach>
				
				
				<c:forEach items="${command.mddastatus}" var="entry1" varStatus="noOfstatus">
				<input type="hidden" name="${entry1.key}" value="${entry1.value}" id="status${noOfstatus.count}">
				</c:forEach>
				
				
				<%-- <div id="chartContainerService" class="col-xs-12 col-sm-6 col-md-6 col-lg-6 chart-container"></div>
				<div id="chartContainerStatus" class="col-xs-12 col-sm-6 col-md-6 col-lg-6 chart-container"></div> --%>
				
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="thumbnail-container">					
						<div class="carousel slide" data-ride="carousel">
							<div class="carousel-inner"  data-toggle="modal" data-target="#m1">
								<div class="item active">
									<img alt="mdda-logo" src="./images/DSCL-images/mdda-logo.png" class="img-responsive">
								</div>
								<div class="item">
									<span class="thumbnail-item">
										<span class="mdda-label"><spring:message code="theme5.portal.mdda1" text="Total Application Count (MDDA)"/></span>
										<span class="mdda-count"><c:out value="${command.totalMDDA.get('first_total')}"></c:out></span>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="thumbnail-container">		
						<div class="carousel slide" data-ride="carousel">
							<div class="carousel-inner" data-toggle="modal" data-target="#m2">
								<div class="item active">
									<img alt="mdda-logo" src="./images/DSCL-images/mdda-logo.png" class="img-responsive">
								</div>
								<div class="item">
									<span class="thumbnail-item">
										<span class="mdda-label"><spring:message code="theme5.portal.mdda2" text="Total BPMS Services Count (MDDA)"/></span>
										<span class="mdda-count"><c:out value="${command.totalMDDA.get('second_total')}"></c:out></span>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				</c:if>
				
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="thumbnail-container">
						<div class="spin-loader spinLoader"></div>
						<div class="carousel slide" data-ride="carousel">
							<div class="carousel-inner margin-top-10" data-toggle="modal" data-target="#m3" onclick="getTrafficUpdate()">
								<div>
									<img alt="Dehradun_Traffice_Police" src="./images/DSCL-images/Dehradun_Traffice_Police.jpg" class="img-responsive">
								</div>
								<div>
									<span class="thumbnail-item">
										<p class="mdda-label text-center"><spring:message code="trafic.update.heading" text="Traffic Update"/></p>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="thumbnail-container">		
						<div class="carousel slide" data-ride="carousel">
							<div class="carousel-inner margin-top-10" data-toggle="modal" data-target="#m4">
								<div>
									<img alt="AQI" src="./images/DSCL-images/AQI.png" class="img-responsive">
								</div>
								<div>
									<span class="thumbnail-item">
										<p class="mdda-label text-center"><spring:message code="sensor.heading" text="Current Environment Detials"/></p>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="thumbnail-container">		
						<div class="carousel slide" data-ride="carousel">
							<div class="carousel-inner margin-top-10" data-toggle="modal" data-target="#m5">
								<div>
									<img alt="e-challan" src="./images/DSCL-images/e-challan.png" class="img-responsive">
								</div>
								<div>
									<span class="thumbnail-item">
										<p class="mdda-label text-center"><spring:message code="echallan.heading" text="E Challan"/></p>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				 <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="thumbnail-container">		
						<div class="carousel slide" data-ride="carousel">
							<div class="carousel-inner margin-top-10" data-toggle="modal" data-target="#m6">
								<div>
									<img alt="view-updcl-bill" src="./images/DSCL-images/view-updcl-bill.png" class="img-responsive">
								</div>
								<div>
									<span class="thumbnail-item">
										<p class="mdda-label text-center"><spring:message code="bill.heading" text="Electricity BIll"/></p>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div> 
				<%-- <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="thumbnail-container">
						<div class="spin-loader spinLoaderITMS"></div>
						<div class="carousel slide" data-ride="carousel">
							<div class="carousel-inner margin-top-10" data-toggle="modal" data-target="#m7" onclick="getItmsUpdate()">
								<div>
									<img alt="Dehradun_Traffice_Police" src="./images/DSCL-images/Dehradun_Traffice_Police.jpg" class="img-responsive">
								</div>
								<div>
									<span class="thumbnail-item">
										<p class="mdda-label text-center"><spring:message code="itms.name" text="ITMS"/></p>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div> --%>
				
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="thumbnail-container">
						<div class="spin-loader spinLoaderITMS"></div>
						<div class="carousel slide" data-ride="carousel">
							<div class="carousel-inner margin-top-10" data-toggle="modal" data-target="#m8">
								<div>
									<img alt="pay_property_tax" src="./images/DSCL-images/pay_property_tax.jpg" class="img-responsive">
								</div>
								<div>
									<span class="thumbnail-item">
										<p class="mdda-label text-center"><spring:message code="pay.propertytax.name" text="Pay Property Tax"/></p>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="thumbnail-container">
						<!-- <div class="spin-loader spinLoader"></div> -->
						<div class="carousel slide" data-ride="carousel">
							<div class="carousel-inner margin-top-10" data-toggle="modal" data-target="#m9">
							 <div>
									<img alt="jalsansthan" src="./images/DSCL-images/jalsansthan.jpg" class="img-responsive">
								</div>
								<div>
									<span class="thumbnail-item">
										<p class="mdda-label text-center"><spring:message code="jal.sansthan.bill.heading" text="Jal Sansthan Bill View"/></p>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				</div>
				
			<!-- Modal 1 -->
				<!-- we will remove while the api is up -->
				<c:if test="${not empty command.map && not empty command.map.get('sizeOfMDDAService') && not empty command.mddastatus.get('sizeOfMDDAStatus')}">
				<div class="modal fade" id="m1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				   <div class="modal-dialog" role="document">
				      <div class="modal-content">
				         <div class="modal-header">
				            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				            <h3 class="modal-title">
				            	<spring:message code="theme5.portal.MDDAServiceWiseAppCount"/>
				            	<span class="margin-left-5">${command.totalMDDA.get('minYear')} - ${command.totalMDDA.get('maxYear')}</span>
				            </h3>
				         </div>
				         <div class="modal-body">
				            <div id="chartContainerService" class="chart-container"></div>
				         </div>
				      </div>
				   </div>
				</div>
				
				<!-- Modal 2 -->
				<div class="modal fade" id="m2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				   <div class="modal-dialog" role="document">
				      <div class="modal-content">
				         <div class="modal-header">
				            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				            <h3 class="modal-title">
				            	<spring:message code="theme5.portal.MDDABPMS"/>
				            	<span class="margin-left-5">${command.totalMDDA.get('minYear')} - ${command.totalMDDA.get('maxYear')}</span>
				            </h3>
				         </div>
				         <div class="modal-body">
				            <div id="chartContainerStatus" class="chart-container"></div>
				         </div>
				      </div>
				   </div>
				</div>
				</c:if>
				<div class="modal fade" id="m3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				   <div class="modal-dialog" role="document">
				      <div class="modal-content">
				         <div class="modal-header">
				            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				            <h3 class="modal-title">
				            	<spring:message code="trafic.update.heading" text="Traffic Update"/>
				            </h3>
				         </div>
				         <div class="modal-body">
			            	<table class="table table-bordered">
			            		<thead>
			            			<tr>
			            				<th colspan="2" class="text-center"><spring:message code="trafic.update.range" text="Range"/></th>
			            			</tr>
			            		</thead>
			            		<tbody>
				            		<tr>
				            			<td><spring:message code="trafic.update.low.key" text="Low"/></td>
				            			<td class="text-green"><spring:message code="trafic.update.low.range" text="less than 40"/></td>
				            		</tr>
				            		<tr>
				            			<td><spring:message code="trafic.update.medium.key" text="Medium"/></td>
				            			<td class="text-amber"><spring:message code="trafic.update.medium.range" text="40 to 75"/></td>
				            		</tr>
				            		<tr>
				            			<td><spring:message code="trafic.update.high.key" text="High"/></td>
				            			<td class="text-red"><spring:message code="trafic.update.high.range" text="greater than 75"/></td>
				            		</tr>
			            		</tbody>
			            	</table>
				            
			               <div id ="traficUpdateEmptyTabDiv">
                           		<div id="loadereTraficUpdate" class="spinner45" align="center" style="display: none;"><spring:message code="bill.loading.data" text="Loading Data...." /> </div>
								<div class="table-responsive">
									<table class="table table-bordered table-striped" id="traficUpdateTable">
										<thead>
											<tr>
												<th><spring:message code="trafic.update.srNo " text="srNo"/></th>							
												<th><spring:message code="trafic.update.junction.name" text="Junction"/></th>
												<th><spring:message code="trafic.update.congestion" text="Congestion"/></th>
												<th><spring:message code="trafic.update.status" text="Status"/></th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
				      </div>
				   </div>
				</div>
				</div>
				
				<div class="modal fade" id="m4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				   <div class="modal-dialog modal-lg" role="document">
				      <div class="modal-content">
				         <div class="modal-header">
				            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				            <h3 class="modal-title">
				            	<spring:message code="sensor.heading"  text="Current Environment Detials"/><span class="d-middle" id="sensorName"></span>
				            
				            </h3>
				         </div>
				         <div class="modal-body">
	                         <div class="form-group">
	                         	<label class="col-sm-4 control-label required-control" ><spring:message code="sensor.select.location" text="Select Location"/> </label>
								<div class="col-sm-8">
									<select name="sensorId" id="selectSensor" data-content=""
										class="form-control chosen-select-no-results"
										 onchange="getEventSensorData(this)">
										<option value="-1" selected style="display: none;"><spring:message
												code="Select" text="Select" /></option>
										<option value="DDNAAQMS0001">Survey Chowk</option>
											<option value="DDNAAQMS0002">Dilaraam Chowk</option>
											<option value="DDNAAQMS0003">Kishan Nagar Chowk</option>
											<option value="DDNAAQMS0004">Mussorie Diversion</option>
											<option value="DDNAAQMS0005">Clock Tower</option>
											<option value="DDNAAQMS0006">Asharodi</option>
											<option value="DDNAAQMS0007">ONGC Chowk</option>
											<option value="DDNAAQMS0008">F.R.I Gate</option>
											<option value="DDNAAQMS0009">Mohkampur</option>
											<option value="DDNAAQMS0010">Balliwala Chowk</option>
											<option value="DDNAAQMS0011">JN ENV BalluPur Chowk</option>
											<option value="DDNAAQMS0012">MS ENV SP Traffic Office</option>
											<option value="DDNAAQMS0013">JN ENV ISBT Chowk</option>
											<option value="DDNAAQMS0014">JN ENV Kargi Chowk</option>
											<option value="DDNAAQMS0015">MS ENV Kaulagarh Crossing </option>
											<option value="DDNAAQMS0016">JN ENV Laal Pul Chowk</option>
											<option value="DDNAAQMS0017">JN ENV Lansdowne  Chowk</option>
											<option value="DDNAAQMS0018">JN ENV Fountain Chowk</option>
											<option value="DDNAAQMS0019">JN ENV Niranjanpur Sabji Mandi Chowk</option>
											<option value="DDNAAQMS0020">JN ENV Race course chowk</option>
											<option value="DDNAAQMS0021">MS ENV Vidhan Sabha</option>
											<option value="DDNAAQMS0022">JN ENV Saint Jude Chowk 1 </option>
											<option value="DDNAAQMS0023">JN ENV Shimla Bypass Chowk</option>
											<option value="DDNAAQMS0024">JN ENV Tehsil Chowk</option>
											<option value="DDNAAQMS0025">JN ENV Buddha Park (Chowk)</option>
											<option value="DDNAAQMS0026">JN ENV Nanys bakery chowk</option>
											<option value="DDNAAQMS0027">MS ENV Rispana ROB 1</option>
											<option value="DDNAAQMS0028">MS ENV Rispana ROB 2</option>
											<option value="DDNAAQMS0029">JN ENV Kargi Chowk 2</option>
											<option value="DDNAAQMS0030">MS ENV Asharodi 2</option>
											<option value="DDNAAQMS0031">MS ENV Mohhabewala 1</option>
											<option value="DDNAAQMS0032">MS ENV Mohhabewala 2</option>
											<option value="DDNAAQMS0033">JN ENV FRI Gate 2</option>
											<option value="DDNAAQMS0034">MS ENV Near Ballupur Flyover</option>
											<option value="DDNAAQMS0035">MS ENV Mohkampur 2</option>
											<option value="DDNAAQMS0036">MS ENV Shastri Nagar</option>
											<option value="DDNAAQMS0037">MS ENV Clock Tower 2</option>
											<option value="DDNAAQMS0038">MS ENV Pacific Mall</option>
											<option value="DDNAAQMS0039">MS ENV Niepvd</option>
											<option value="DDNAAQMS0040">MS ENV President bodyguard house</option>
											<option value="DDNAAQMS0041">MS ENV Village bagryal</option>
											<option value="DDNAAQMS0042">JN ENV Shastradhara Crossing </option>
											<option value="DDNAAQMS0043">MS ENV  cross road</option>
											<option value="DDNAAQMS0044">MS ENV Maharanapratap chowk</option>
											<option value="DDNAAQMS0045">MS ENV Gurudwara nanaksar</option>
											<option value="DDNAAQMS0046">MS ENV Balayogi asramam</option>
											<option value="DDNAAQMS0047">MS ENV Gyani institute</option>
											<option value="DDNAAQMS0048">MS ENV Canal road</option>
											<option value="DDNAAQMS0050">MS ENV Lakhad mandi chowk</option>
										 <c:set var="sensorLookupList" value ="${command.getLevelData('SNS')}"/>	
										<%-- <c:if test="${not empty sensorLookupList }">			
										<c:forEach items="${sensorLookupList}" var="lookupList">
											<c:if test="${userSession.languageId eq 1}">
												<option value="${lookupList.lookUpCode}">${lookupList.lookUpDesc}</option>
											</c:if>
											<c:if test="${userSession.languageId eq 2}">
												<option value="${lookupList.lookUpCode}">${lookupList.descLangSecond}</option>
											</c:if>
										</c:forEach>
										</c:if>  --%>
									</select>
								</div>
                             </div> 
	                             
                             <div id ="sensorEmptyTabDiv">
	                          
								<%-- <div class="form-group">
									<label class="col-sm-4 control-label" ><spring:message
											code="sensor.date" text="Date" /> </label>
									<div class="col-sm-8">
										<span  id="dateId" ></span>
									</div>
								</div> --%>
								<div id="loader" class="spinner45" align="center" style="display: none;"><spring:message code="bill.loading.data" text="Loading Data...." /> </div>
								 
								 <div class="no-data-found hide" id="nodatafound">
								 	<i class="fa fa-meh-o" aria-hidden="true"></i>
								 	<h3>Oops! No Data Found for Selected Location.</h3>
								 	<p>Please refresh the page and try again</p>
								 </div>
								 <div class="text-center env-sensor-box hide">
								 	<h2 class="text-center sub-heading-env bg-blue">Air Quality Index (Date and Time  : <span id="aqidatetime"></span>)</h2>								  
								          <div class="col-sm-4 col-md-4 col-6 col-lg-3 margin-bottom-15">
									           <div class="env-box">
									           		
								            		<div class="aqi-heading"><i class="fa fa-mixcloud" aria-hidden="true"></i><b id="noReading">0</b><span>ug/m3</span></div>
								            		<div>NO2
								            		<div style="border-radius:10px; background:#FFF;" class="aqi-meter">
								            			<span class="" id="noColor"></span>
								            		</div>
								            	</div>
									           </div>								            
								          </div>
								          <div class="col-sm-4 col-md-4 col-6 col-lg-3 margin-bottom-15">
								          		 <div class="env-box">
									           		<div class="aqi-heading"><i class="fa fa-circle-o" aria-hidden="true"></i><b id="o3Reading">51</b><span>ug/m3</span></div>
								            		<div>O3
								            			<div style="border-radius:10px; background:#FFF;" class="aqi-meter">
								            			<span class="" id="o3Color"></span>
								            		</div>
								            		</div>
									           </div>	
								          </div>
								          <div class="col-sm-4 col-md-4 col-6 col-lg-3 margin-bottom-15">
								            <div class="env-box">
									           	<div class="aqi-heading"><i class="fa fa-tree" aria-hidden="true"></i><b id="pmReading">1</b> <span>ppb</span></div>
								            		<div>PM10
								            			<div style="border-radius:10px; background:#FFF;" class="aqi-meter">
								            				<span class="" id="pmColor"></span>
								            			</div>
								            		</div>
									           </div>	
								          </div>
								          <div class="col-sm-4 col-md-4 col-6 col-lg-3 margin-bottom-15">
								             <div class="env-box">
									           	<div class="aqi-heading"><i class="fa fa-compass" aria-hidden="true"></i><b id="soReading">22</b> <span>ppb</span></div>
									           		<div>SO2
								            			<div style="border-radius:10px; background:#FFF;" class="aqi-meter">
								            				<span class="" id="soColor"></span>
								            			</div>
								            		</div>
									           </div>
								          </div>
								          <div class="col-sm-4 col-md-4 col-6 col-lg-3 margin-bottom-15">
								            	<div class="env-box">
									           		<div class="aqi-heading"><i class="fa fa-circle-o-notch" aria-hidden="true"></i><b id="coReading">389</b><span>ppb</span></div>
									           		<div>CO
								            			<div style="border-radius:10px; background:#FFF;" class="aqi-meter">
								            				<span class="" id="coColor"></span>
								            			</div>
								            		</div>
									           </div>
								          </div>
								          <div class="col-sm-4 col-md-4 col-6 col-lg-3 margin-bottom-15">
								            <div class="env-box">
								            	<div class="aqi-heading"><i class="fa fa-cloud" aria-hidden="true"></i><b id="cReading">6</b><span>ppb</span></div>
									           		<div>CO2
								            			<div style="border-radius:10px; background:#FFF;" class="aqi-meter">
								            				<span class="" id="cColor"></span>
								            			</div>
								            		</div>
									           </div>
								          </div>
								           <div class="col-sm-4 col-md-4 col-6 col-lg-3 margin-bottom-15">
								            <div class="env-box">
								            	<div class="aqi-heading"><i class="fa fa-tachometer" aria-hidden="true"></i><b id="aqiReading">6</b><span>ppb</span></div>
									           		<div>AQI
								            			<div style="border-radius:10px; background:#FFF;" class="aqi-meter">
								            				<span class="" id="aqiColor"></span>
								            			</div>
								            		</div>
									           </div>
								          </div>
								          
								       <div style="clear:both"></div>
								       
								       
									<%-- <table class="table table-striped table-bordered"
										id="eventSensorTable">
										<thead>
											<tr>
												<th width="5%" align="center"><spring:message
														code="trafic.update.srNo" text="Sr.No" /></th>
												<th width="15%" align="center"><spring:message
														code="event.sensor.list" text="Item List" /></th>
												<th width="20%" align="center"><spring:message
														code="event.details" text="Details" /></th>
											</tr>
										</thead>
									</table> --%>
									<div id="aqi-index">
									<table class="text-center">
										<tr>
											<td class="aqi-bg-green">Good</td>
											<td class="aqi-bg-yellow">Satisfactory</td>
											<td class="aqi-bg-ddyellow">Moderate</td>
											<td class="aqi-bg-pink">Poor</td>
											<td class="aqi-bg-megenta">Very Poor</td>
											<td class="aqi-bg-red">Severe</td>
										</tr>
									</table>
									<div class="text-left">Note: The above data is received from field devices and it may vary depending on the situation at the ground.</div>
								</div> 
								
								</div>
								  
								
							</div>		
							
				         </div>
				      </div>
				   </div>
				</div>
				
				<div class="modal fade" id="m5" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					
				   <div class="modal-dialog modal-lg" role="document">
				      <div class="modal-content">
				         <div class="modal-header">
				            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				            <h3 class="modal-title">
				            	<spring:message code="echallan.heading"  text="E Challan"/><span class="d-middle" id="sensorName"></span>
				            
				            </h3>
				         </div>
				         <div class="modal-body">
                         <div class="form-group">
                         	<label class="col-sm-4 control-label required-control" ><spring:message code="enter.challan.number" text="Enter Challan Number"/> </label>
							<div class="col-sm-4">
								<div class="input-group">
									<input type="text" class="form-control" id="echallanNo" />
									<span class="input-group-btn">
										<button  type="button" class="btn btn-blue-2" onClick="getChallanData()"><i class="fa fa-search"></i> <spring:message code="eip.search"/></button>
									</span>
								</div>
							</div>
							<div class="col-sm-2">
								<button type="button" class="btn btn-primary clearTableBtn"><spring:message code="btn.clear.search" text="Clear Search"/></button>
							</div>
                         </div> 
                         <div id ="challanEmptyTabDiv" style="display:block !important;">
                         	
                       		<div id="loadereChallan" class="spinner45" align="center" style="display: none;"><spring:message code="bill.loading.data" text="Loading Data...." /> </div>
							<div class="table-responsive">
								<table class="table table-striped table-bordered" id="echallanTable">
									<thead>
										<tr>
											<th align="center"><spring:message
													code="trafic.update.srNo" text="Sr.No" /></th>
											<th align="center"><spring:message
													code="echallan.challanNo" text="challanNo" /></th>
											<th align="center"><spring:message
													code="echallan.vehicleNo" text="vehicleNo" /></th>
											<th align="center"><spring:message
													code="echallan.violationType" text="violationType" /></th>
											<th align="center"><spring:message
													code="echallan.timeStamp" text="timeStamp" /></th>
											<th align="center"><spring:message
													code="echallan.challanTimeStamp" text="challanTimeStamp" /></th>
											<th align="center"><spring:message
													code="echallan.paymentStatus" text="paymentStatus" /></th>
											<th align="center"><spring:message
													code="echallan.speed" text="speed" /></th>
											<th align="center"><spring:message
													code="echallan.lane" text="lane" /></th>
											<th align="center"><spring:message
													code="echallan.description" text="description" /></th>
											<th align="center"><spring:message
													code="echallan.location" text="location" /></th>
											<th align="center"><spring:message
													code="echallan.amount" text="amount" /></th>
										</tr>
									</thead>
								</table>
							</div>
						 </div>
								
			         </div>
			      </div>
			   </div>
			</div>

			<div class="modal fade" id="m6" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel">
				<div class="modal-dialog modal-lg" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h3 class="modal-title">
								<spring:message code="bill.heading" text="Bill Detials" />
								<span class="d-middle" id="sensorName"></span>

							</h3>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<div class="row">
									<label class="col-sm-4 control-label required-control"><spring:message
											code="bill.accNo" text="Account No" /> </label>
									<div class="col-sm-8">
										<input type="text" class="form-control" id="accountNo" />
									</div>
								</div>
							</div>
							<%-- <div class="form-group">
								<div class="row">
									<label class="col-sm-4 control-label required-control"><spring:message
											code="bill.date" text="Bill Month" /> </label>
									<div class="col-sm-8">
										
										<input id="appDate" name="" class="" placeholder="mm/yyyy" type="month" value=""  maxlength="10" autocomplete="off">
										
									
									</div>
								</div>
							</div> --%>
							
							<div class="text-right">
								<button id="getDsclBillDetDataId" onclick="getDsclBillDetData()"
									class="btn btn-success">
									<spring:message code="bill.search" text="Search Bill" />
								</button>
							</div>
							<div id="eleBillTabelDiv">
								

								<div id="eleBillTabelLoder" class="spinner45 hide" align="center">
									<spring:message code="bill.loading.data" text="Loading Data...." /> </div>
								 <div class="no-data-found hide" id="nodatafound">
								 	<i class="fa fa-meh-o" aria-hidden="true"></i>
								 	<h3 id="upclErrorMsg"></h3>
								 	<p id="upclErrorSubMsg"></p>
								 </div>
								<div class="table-responsive clear hide" id="eleBill">
									<table class="table table-bordered" id="eleBillTabel">
										<thead>
											<tr>
												<td colspan="4">UPCL Bill Data</td>
											</tr>
										</thead>
										<tr>
											<th align="center" width="20%"><spring:message code="bill.serviceNo" text="Service No" /></th>
											<td id="serviceNo" width="30%"></td>
											<th align="center" width="20%"><spring:message code="bill.accNo" text="Account No" /></th>
											<td id="accNo" width="30%"></td>
										</tr>
										<tr>
											<th align="center"><spring:message code="bill.customerName" text="Customer Name" /></th>
											<td id="customerName"></td>
											<th align="center"><spring:message code="bill.categoryCode" text="Category Code" /></th>
											<td id="categoryCode"></td>
										</tr>
										<tr>
											<th align="center"><spring:message code="bill.billDate" text="Bill Date" /></th>
											<td id="billDate"></td>
											<th align="center"><spring:message code="bill.billDueDate" text="Bill Due Date" /></th>
											<td id="billDueDate"></td>
										</tr>
										<tr>
											<th align="center"><spring:message code="bill.billAmt " text="Current Bill Amount" /></th>
											<td id="billAmt"></td>
											<th align="center"><spring:message code="bill.dueAmt" text="Due Amount" /></th>
											<td id="dueAmt"></td>
										</tr>
										<tr>
											<%-- <th align="center"><spring:message code="bill.totalpayAmt " text="Total Payable Amount" /></th>
											<td id="totalpayAmt"></td> --%>
											<th align="center"><spring:message code="bill.billDownload " text="Download Bill" /></th>
											<td id="dwnBill"></td>
										</tr>
										<tr>
											<td style="text-align: center !important" colspan="4">
												<button id="payBillButton" class="btn btn-danger" name="paybill" onclick="payUpclbill();">
													<spring:message code="bill.payBill" text="Pay Bill" />
												</button>
											</td>
										</tr>
									</table> 

								</div>

							</div>

						</div>
					</div>
				</div>
			</div> 
			
			<div class="modal fade" id="m7" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				   <div class="modal-dialog" role="document">
				      <div class="modal-content">
				         <div class="modal-header">
				            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				            <h3 class="modal-title">
				            	<spring:message code="itms.name" text="ITMS"/>
				            </h3>
				         </div>
				         <div class="modal-body">				            
			               <div id ="ITMSTabDiv">
                           		<div id="ITMSUpdate" class="spinner45" align="center" style="display: none;"><spring:message code="bill.loading.data" text="Loading Data...." /></div>
								<div class="table-responsive">
									<table class="table table-bordered table-striped" id="itmsUpdateTable">
										<thead>
											<tr>
												<th><spring:message code="itms.update.sr" text="srNo"/></th>							
												<th><spring:message code="itms.update.accident" text="accidentType"/></th>
												<th><spring:message code="itms.update.weather" text="weatherType"/></th>
												<th><spring:message code="itms.update.roadtype" text="roadType"/></th>
											    <th><spring:message code="itms.update.area" text="areaType"/></th>
											    <th><spring:message code="itms.update.roadFeature" text="roadFeature"/></th>
											</tr>
										</thead>
										<tr><td></td>
										<!-- <td>No data available</td></tr> -->
									</table>
								</div>
							</div>
				      </div>
				   </div>
				</div>
			</div>
			
			<div class="modal fade" id="m8" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				   <div class="modal-dialog" role="document">
				      <div class="modal-content">
				         <div class="modal-header">
				            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				            <h3 class="modal-title">
				            	<spring:message code="pay.propertytax.name" text="Pay Property Tax"/>
				            </h3>
				         </div>
				         <div class="modal-body">	
				         <div id="propertyBillDataLoader" class="spinner45" align="center" style="display: none;"><spring:message code="bill.loading.data" text="Loading Data...." /></div>			            
			               <div id ="propertytaxabDiv">
                           		<div id="propertytaxUpdate" class="spinner45" align="center" style="display: none;"><spring:message code="bill.loading.data" text="Loading Data...." /></div>
								<%--  Form Started  --%>
								<div class="form-group">
									<div class="row">
										<label class="col-sm-4 control-label required-control"><spring:message
												code="" text="Property No" /> </label>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="propertyNo" />
										</div>
									</div>
								</div>
								<div class="text-right">
								<button id="getDsclBillDetDataId" onclick="getDsclpropertyBillData()"
									class="btn btn-success">
									<spring:message code="bill.search" text="Search Bill" />
								</button>
							</div>
								<%--  Form End  --%>
								<div id="errorPropertyData" class="hide alert alert-danger margin-top-10"></div>
				               <div id="propertyresdata" class="margin-top-10 hide">
				               		
				               		<table class="table table-bordered" id="waterBillDataTable">
				               			<tr>
				               				<th colspan = "2">Payer Name</th>
				               				<th>Mobile No</th>
				               			</tr>
				               			<tr>
				               				<td id="payerName" colspan = "2"></td>
				               				<td id="payerMobileno"></td>
				               			</tr>
				               			<tr>
				               				<th colspan="3">Payer Address</th>
				               			</tr>
				               			<tr>
				               				<td colspan="3" id="payerAddress"></td>
				               			</tr>
				               			<tr>
				               				<th>Payer E-Mail</th>
				               				<th>Status</th>
				               				<th>Total Amount</th>
				               			</tr>
				               			<tr>
				               				<td id="payermail"></td>
				               				<td id="payerstatus"></td>
				               				<td id="payerTotalamount"></td>
				               			</tr>
				               		</table>
				               		<div class="text-center">
				               			<button class="btn btn-danger" id="paypropertybill" value="Pay Bill" onclick="payDsclPropertyTax()">Pay Bill</button>
				               		</div>
				               </div>
				           
							</div>
				      </div>
				   </div>
				</div>
			</div>
			 <%-- <div class="text-center clear">
					<a href="https://mddaonline.org.in/mdda/public" target="_blank" class="view-more-btn">
						<spring:message code="portal.link.viewmore" text="View More" />
					</a>
				</div> --%>
				
				
				<div class="modal fade" id="m9" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				   <div class="modal-dialog" role="document">
				      <div class="modal-content">
				         <div class="modal-header">
				            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				            <h3 class="modal-title">
				            	<spring:message code="jal.sansthan.bill.heading" text="Jal Sansthan Bill View"/>
				            </h3>
				         </div>
				         <div class="modal-body">
				         <div id="waterBillDataLoader" class="spinner45" align="center" style="display: none;"><spring:message code="bill.loading.data" text="Loading Data...." /></div>
			            	<%--  Form Started  --%>
								<div class="form-group">
									<div class="row">
										<label class="col-sm-4 control-label required-control"><spring:message
												code="" text="Consumer No" /> </label>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="consumerNo" />
										</div>
									</div>
								</div>
								<div class="text-right">
								<button id="getDsclBillDetDataId" onclick="getDsclwaterData()"
									class="btn btn-success">
									<spring:message code="bill.search" text="Search Bill" />
								</button>
							</div>
								<%--  Form End  --%>
								<div id="errorWaterData" class="hide">
									 <div class="no-data-found hide" id="nodatafound">
											 	<i class="fa fa-meh-o" aria-hidden="true"></i>
											 	<h3 id="waterErrorMsg"></h3>
											 	<p id="waterErrorSubMsg"></p>
											 </div>
								</div>
					               <div id="waterresdata" class="margin-top-10 hide">
					               		
					               		<table class="table table-bordered" id="waterBillDataTable">
					               			<tr>
					               				<th>ConsumerName</th>
					               				<th>Consumer Code</th>
					               			</tr>
					               			<tr>
					               				<td id="consumerName"></td>
					               				<td id="consumerCode"></td>
					               			</tr>
					               			<tr>
					               				<th colspan="2">Consumer Address</th>
					               			</tr>
					               			<tr>
					               				<td colspan="2" id="conAddress"></td>
					               			</tr>
					               			<tr>
					               				<th>Bill No</th>
					               				<th>Bill Amount</th>
					               			</tr>
					               			<tr>
					               				<td id="waterBillNo"></td>
					               				<td id="waterBillAmount"></td>
					               			</tr>
					               			<tr>
					               				<th>Payment Due Date</th>
					               				<td id="waterPaymentDueDate"></td>
					               			</tr>
					               			<tr>
											<td align="center" colspan="4">
												<button class="btn btn-danger hide" id="waterBillPayBtn" value="Pay Bill" onclick="payWaterBill()">
													<spring:message code="bill.payBill" text="Pay Bill" />
												</button>
											</td>
										</tr>
					               		</table>
					               		
					               </div>
				      </div>
				   </div>
				</div>
				</div>
			</div>
		</div>
		
				
		
	    <%-- MDDA Dashboard ends --%>
    </div>
	<div class="clear"></div>
	
	<%-- On Going Projects starts --%>
	<div class="on-going-projects-section">
		<div class="container">
			<h3 class="sub-heading"><spring:message code="theme5.portal.projects" text="Projects" /></h3>
			<h1><spring:message code="theme5.portal.on.going.projects" text="On Going Projects" /></h1>
			<div class="element-animate" id="ogp">
				<c:forEach items="${command.getAllhtml('On Going Projects')}" var="onGoingProjects"> ${onGoingProjects} </c:forEach>
			</div>
		</div>
	</div>
	<%-- On Going Projects ends --%>
	
	<!-- Departmental based Services
	Code added by ABM2144 on 20-5-2019 -->
 	<c:if test="${not empty command.themeMap['EXTERNAL_SERVICES'] && command.themeMap['EXTERNAL_SERVICES'] ne 'I'}">
	 	<div class="depser" id="depser">
	 		<div class="container">
				<h3 class="text-center sub-heading"><spring:message code="Services" text="Services" /></h3>
				<h1 class="text-center " data-animation-effect="fadeInDown" data-effect-delay="600">
					<spring:message code="depServices" text="Department Services" /></h1>
			
				<div class="padding-0 element-animate">
					<ul>
						<%-- <li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>' target="_blank">
									<i class="fa fa-home icon" aria-hidden="true"></i>
									<span class="dept-serv-name">
										<span class="d-middle"><spring:message code="depServices1" text="Services" /></span>
									</span>
								</a>
							</div>
						</li> --%>
						<%-- <li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="fa fa-tint icon" aria-hidden="true"></i>
									<span class="dept-serv-name">
										<span class="d-middle"><spring:message code="depServices2" text="Services" /></span>
									</span>
								</a>
							</div>
						</li> --%>				
						<%-- <li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="fa fa-desktop icon" aria-hidden="true"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices3" text="Services" /></span>
									</span>
								</a>
							</div>
						</li> --%>		
						<%-- <li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="fa fa-cogs icon" aria-hidden="true"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices4" text="Services" /></span>
									</span>
								</a>
							</div>
						</li> --%>		
						<%-- <li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="dashboard.url"/>'  target="_blank">
								<a href='<spring:message code="theme5.portal.dashboard.url"/>'>
									<i class="icofont-dashboard-web icon"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices5" text="Services" /></span>
									</span>
								</a>
							</div>
						</li> --%>
						<%-- <li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="fa fa-inr icon" aria-hidden="true"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices6" text="Services" /></span>
									</span>
								</a>
							</div>
						</li> --%>		
						<%-- <li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="fa fa-building-o icon" aria-hidden="true"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices7" text="Services" /></span>
									</span>
								</a>
							</div>
						</li> --%>		
						<li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="icofont-document-folder icon"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices8" text="Services" /></span>
									</span>
								</a>
							</div>
						</li>		
						<li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="icofont-bank-alt icon"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices9" text="Services" /></span>
									</span>
								</a>
							</div>
						</li>	
						<li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="icofont-gift icon"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices10" text="Services" /></span>
									</span>
								</a>
							</div>
						</li>
						
						<!-- start Added by ABM2144 for SUDA all 18 Modules task dated 19.07.2019 -->
						
						<li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="icofont-search-stock icon"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices11" text="Services" /></span>
									</span>
								</a>
							</div>
						</li>
						<li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="icofont-recycling-man icon"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices12" text="Services" /></span>
									</span>
								</a>
							</div>
						</li>
						<li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="icofont-bag-alt icon"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices13" text="Services" /></span>
									</span>
								</a>			
							</div>
						</li>
						<%-- <li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="fa fa-user-secret icon" aria-hidden="true"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices14" text="Services" /></span>
									</span>
								</a>
							</div>
						</li>
						<li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="fa fa-road icon" aria-hidden="true"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices15" text="Services" /></span>
									</span>
								</a>
							</div>
						</li>
						<li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="fa fa-bullhorn icon" aria-hidden="true"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices16" text="Services" /></span>
									</span>
								</a>
							</div>
						</li>
						<li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="fa fa-send icon" aria-hidden="true"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices17" text="Services" /></span>
									</span>
								</a>
							</div>
						</li> --%>
						<li class=" col-lg-4 col-md-6 col-sm-6 col-xs-6">
							<div class="thumbnail">
								<a href='<spring:message code="service.admin.home.url"/>'  target="_blank">
									<i class="icofont-court icon"></i>
									<span class="dept-serv-name">
										
										<span class="d-middle"><spring:message code="depServices18" text="Services" /></span>
									</span>
								</a>
							</div>
						</li>
						
						<!-- ended Added by ABM2144 for SUDA all 18 Modules task dated 19.07.2019 -->
					</ul>
					
				</div>
			</div>
		</div>
	</c:if>
	<!-- Departmental based Services
	End Code by ABM2144 -->
	
	<!-- Citizen Services Start -->
	<%-- <c:if test="${not empty command.themeMap['CITIZEN_SERVICES'] && command.themeMap['CITIZEN_SERVICES'] ne 'I'}"> --%>
		<c:set var="citizenService"
			value="${command.getAllDepartmentAndServices()}" />
		<c:if test="${citizenService.size()>0}">
			<div class="service-div citser" id="CitizenService">
				<div class="container">
					<h3 class="sub-heading"><spring:message code="Services" text="Services" /></h3>
					<h1><spring:message code="eip.citizencervices" text="Citizen Services" /></h1>
					<div>
						<c:forEach items="${command.getAllDepartmentAndServices()}"
							var="dept" varStatus="index">
							<c:forEach items="${dept.childDTO}" var="serv" varStatus="index">
								<div class="col-lg-4 col-md-6 col-sm-6 col-xs-6 Services-tab  item element-animate">
									<div class="folded-corner service_tab_AS">
										<a href="javascript:void(0);" onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
											<i class="icofont_${serv.serviceCode} icon"></i>
											<span class="cit-serv-name">${serv.serviceName}</span>
										</a>
								     </div>
								</div>	
							</c:forEach>
							<%-- <div class="col-lg-4 col-md-6 col-sm-6 col-xs-6 Services-tab  item element-animate">
								<div class="folded-corner service_tab_AS">
									<div class="text">																						
										<h3><i class="fa fa-list_${dept.departmentCode} fa-icon-image"></i>${dept.departmentName}</h3><div class="clearfix"></div>
									</div>
									<div class="text-contents">
										<ul>
											<c:forEach items="${dept.childDTO}" var="serv"
												varStatus="index">
												<li><a href="javascript:void(0);"
													onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
														<i class="fa fa-chevron-right"></i>  ${serv.serviceName}</a></li>

											</c:forEach>
										</ul>
									</div>
								</div>
							</div> --%>
						</c:forEach>
						<div class="col-lg-4 col-md-6 col-sm-6 col-xs-6 Services-tab item element-animate">
							<div class="folded-corner service_tab_AS">
								<a href='<spring:message code="apuni.sarkar.url" text="https://eservices.uk.gov.in/#/apuni-sarkar" />'>
									<i class="icofont-globe icon"></i>
									<span class="cit-serv-name"><spring:message code="apuni.sarkar.heading" text="Apuni Sarkar" /></span>
								</a>
						     </div>
						</div>
					</div>
						
				</div>
			</div>
		</c:if>
	<%-- </c:if> --%>
	<!-- Citizen Services End -->
	
	<!--MINISTER START  -->
	<c:if test="${command.themeMap['COMMITTEE_MEMBERS'] ne 'I'}">
		<div class="minister-section">
		<div class="container">
		<div
			class="about-bg clear minister-details" id="container-fluid">
			<div class="minister-org">
				<!--SELECT ORGNAIZATION START -->
				<%-- <c:if
					test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">
					<div
						class="col-sm-12 col-md-3 col-lg-3 col-xs-12 org-select hidden-xs">
						<div class="content-tab">
							<div class="form-group">
								<h3>
									<spring:message code="ULB.head" text="Select ULB" />
								</h3>
								<form:form name="selectMunicipalForm" id="selectMunicipalForm"
									action="" class="list" method="get">
									<div class="row">
										<label class="hidden" for="selectedOrg">Select
											Department</label> <select name="orgId" id="selectedOrg"
											data-content="" class="form-control chosen-select-no-results">
											<option value="-1" selected style="display: none;"><spring:message code="Select" text="Select" /></option>
											<c:forEach items="${command.userSession.organisationsList}"
												var="orglist">
												<optgroup label="${orglist.key}">
													<c:forEach items="${orglist.value}" var="org">
														<c:if test="${userSession.languageId eq 1}">
															<option value="${org.orgid}">${org.ONlsOrgname}</option>
														</c:if>
														<c:if test="${userSession.languageId eq 2}">
															<option value="${org.orgid}">${org.ONlsOrgnameMar}</option>
														</c:if>
													</c:forEach>
												</optgroup>

											</c:forEach>
										</select>
										<button type="button" id="submitMunci" class="btn btn-success">
											<spring:message code="change" text="Change" />
										</button>
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</c:if> --%>

				<!--SELECT ORGNAIZATION END -->
				<c:set var="count" value="0" scope="page" />
				<c:if test="${not empty sessionScope.mayorProfile}">
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:if>
				<c:if test="${not empty sessionScope.deputyMayorProfile}">
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:if>
				<c:if test="${not empty sessionScope.commissionerProfile}">
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:if>
				<c:if test="${not empty sessionScope.deputycommissionerProfile}">
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:if>				
				<div class="minister-div">
					<%-- <h1 class="text-center " data-animation-effect="fadeInDown" data-effect-delay="600">
						<spring:message code="theme5.portal.our.honours" text="Our Honours" /></h1> --%>
					
					<div class="row">
						<div class="minister-ul">

							<c:if test="${not empty sessionScope.mayorProfile}">
								<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 element-animate">

									<div class="content-img">
										<img src="${sessionScope.mayorprofileImage}"
											alt="${sessionScope.mayorName}"
											title="${sessionScope.mayorName}" class="img-responsive">
									</div>
									<div class="minister-info">
										<h3>${sessionScope.mayorName}</h3>
										<p class="designation">${sessionScope.mayorProfile}</p>
										<p class="minister-phone">${sessionScope.SummaryEng}</p>
									</div>
								</div>
							</c:if>
							
							<c:if test="${not empty sessionScope.deputyMayorProfile}">
								<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 element-animate">

									<div class="content-img">
										<img src="${sessionScope.deputyMayorProfileImage}"
											alt="${sessionScope.deputyMayorName}"
											title="${sessionScope.deputyMayorName}"
											class="img-responsive">
									</div>
									<div class="minister-info">
										<h3>${sessionScope.deputyMayorName}</h3>
										<p class="designation">${sessionScope.deputyMayorProfile}</p>
										<p class="minister-phone">${sessionScope.deputyMayorSummaryEng}</p>
									</div>
								</div>
							</c:if>

							<c:if test="${not empty sessionScope.commissionerProfile}">
								<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 element-animate">

									<div class="content-img">
										<img src="${sessionScope.commissionerProfileImage}"
											alt="${sessionScope.commissionerName}"
											title="${sessionScope.commissionerName}"
											class="img-responsive">
									</div>
									<div class="minister-info">
										<h3>${sessionScope.commissionerName}</h3>
										<p class="designation">${sessionScope.commissionerProfile}</p>
										<p class="minister-phone">${sessionScope.commissionerSummaryEng}</p>
					
									</div>
								</div>
							</c:if>

							<c:if test="${not empty sessionScope.deputycommissionerProfile}">

							<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 element-animate">
								<div class="content-img">
									<img src="${sessionScope.deputycommissionerProfileImage}"
										alt="${sessionScope.deputyCommissionerName}"
										title="${sessionScope.deputyCommissionerName}"
										class="img-responsive">
								</div>
								<div class="minister-info">
									<h3>${sessionScope.deputyCommissionerName}</h3>
									<p class="designation">${sessionScope.deputycommissionerProfile}</p>
									<p class="minister-phone">${sessionScope.deputycommissionerSummaryEng}</p>
								</div>
							</div>
							</c:if>
							
							<c:if test="${empty sessionScope.mayorProfile}">
								<div class=" col-xs-6 col-sm-3 col-md-3 col-lg-3 element-animate">
									<div class="content-img">
										<img src="assets/img/Hon'ble-Minister.jpg"
											alt="Hon'ble-Minister" title="Hon'ble-Minister"
											class="img-responsive">
									</div>
									<div class="minister-info">
										<p class="designation"></p>
									</div>
								</div>
							</c:if>
							<c:if test="${empty sessionScope.deputyMayorProfile}">
								<div class=" col-xs-6 col-sm-3 col-md-3 col-lg-3 element-animate">
									<div class="content-img">
										<img src="assets/img/Hon'ble-Minister.jpg"
											alt="Hon'ble-Minister" title="Hon'ble-Minister"
											class="img-responsive">
									</div>
									<div class="minister-info">
										<p class="designation"></p>
									</div>
								</div>
							</c:if>
							<c:if test="${empty sessionScope.commissionerProfile}">
								<div class=" col-xs-6 col-sm-3 col-md-3 col-lg-3 element-animate">
									<div class="content-img">
										<img src="assets/img/Hon'ble-Minister.jpg"
											alt="Hon'ble-Minister" title="Hon'ble-Minister"
											class="img-responsive">
									</div>
									<div class="minister-info">
										<p class="designation"></p>
									</div>
								</div>
							</c:if>

							<c:if test="${empty sessionScope.deputycommissionerProfile}">
								<div class=" col-xs-6 col-sm-3 col-md-3 col-lg-3 element-animate">
									<div class="content-img">
										<img src="assets/img/Hon'ble-Minister.jpg"
											alt="Hon'ble-Minister" title="Hon'ble-Minister"
											class="img-responsive">
									</div>
									<div class="minister-info">
										<p class="designation"></p>
									</div>
								</div>
							</c:if>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		</div>
		</div>
	</c:if>
	<!--MINISTER END  -->
	<div class="clear"></div>
	
	<!-- Media Gallery starts -->
	<c:if test="${command.themeMap['PHOTO_GALLERY'] ne 'I'}">
		<div class="media-gallery-section">
			<div class="container">
				<div class="photo-boxx" id="photo-box">
					<h3 class="sub-heading"><spring:message code="quick.header.gallery" text="Gallery" /></h3>
					<h1 data-animation-effect="fadeInDown" data-effect-delay="600">
						<spring:message code="portal.Media.Gallery" text="Media Gallery" />
					</h1>
					
					<div class="media-gallery-main">
						<ul class="nav nav-pills">
							<li class="active">
								<a data-toggle="tab" href="#mg1" class="internal"><spring:message code="PhotoGallery" text="Photo Gallery" /></a>
							</li>
							<li>
								<a data-toggle="tab" href="#mg2" class="internal"><spring:message code="portal.Media.Gallery" text="Media Gallery" /></a>
							</li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane fade in active element-animate" id="mg1">
								<div class="row">
									<div class="pg-section">
										<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
											<a href="./images/DSCL-images/pg-img-1.jpg" class="fancybox" data-fancybox-group="images">
												<img src="./images/DSCL-images/pg-img-1.jpg" alt="pg-img-1" class="img-responsive img-thumbnail" />
											</a>
										</div>
										<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
											<a href="./images/DSCL-images/pg-img-2.jpg" class="fancybox" data-fancybox-group="images">
												<img src="./images/DSCL-images/pg-img-2.jpg" alt="pg-img-2" class="img-responsive img-thumbnail" />
											</a>
										</div>
										<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
											<a href="./images/DSCL-images/pg-img-3.jpg" class="fancybox" data-fancybox-group="images">
												<img src="./images/DSCL-images/pg-img-3.jpg" alt="pg-img-3" class="img-responsive img-thumbnail" />
											</a>
										</div>
										<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
											<a href="./images/DSCL-images/pg-img-4.jpg" class="fancybox" data-fancybox-group="images">
												<img src="./images/DSCL-images/pg-img-4.jpg" alt="pg-img-4" class="img-responsive img-thumbnail" />
											</a>
										</div>
									</div>
									<div class="pg-section-button">
										<a href="./SectionInformation.html?editForm&rowId=7&page=" type="button" class="btn" title="<spring:message code="portal.link.viewmore" text="View More" />">
											<spring:message code="portal.link.viewmore" text="View More" />
										</a>
									</div>
								</div>
							</div>
							<div class="tab-pane fade element-animate" id="mg2">
								<div class="row">
									<%-- <div class="col-xs-6 col-sm-6 col-md-3 col-lg-3">
										<div class="mg-tile">
											<a href="./SectionInformation.html?editForm&rowId=7&page=" title="<spring:message code="PhotoGallery" text="Photo Gallery"/>">
												<span class="mg-image">
													<img src="images/DSCL-images/dscl_photo.png" class="img-responsive" alt="Photo Gallery">
												</span>
												<span class="mg-text-container">
													<span class="mg-text"><spring:message code="PhotoGallery" text="Photo Gallery" /></span>
												</span>
											</a>
										</div>
									</div> --%>
								 	<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
									 	<div class="mg-tile">
										 	<a href="./SectionInformation.html?editForm&rowId=10&page=" title="<spring:message code="VideoGallery" text="Video Gallery"/>">
										 		<span class="mg-image">	
										 			<img src="images/DSCL-images/dscl_video.png" class="img-responsive" alt="Video Gallery">
										 		</span>
										 		<span class="mg-text-container">
										 			<span class="mg-text"><spring:message code="VideoGallery" text="Video Gallery" /></span>
										 		</span>
										 	</a>
									 	</div>
								 	</div>
								 	<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
									 	<div class="mg-tile">
									 		<a href="./SectionInformation.html?editForm&rowId=11&page=" title="<spring:message code="theme5.portal.news.clips" text="News Clips"/>">
									 			<span class="mg-image">	
									 				<img src="images/DSCL-images/dscl-news-clips.png" class="img-responsive" alt="Video Gallery">
									 			</span>
									 			<span class="mg-text-container">
									 				<span class="mg-text"><spring:message code="theme5.portal.news.clips" text="News Clips" /></span>
									 			</span>
									 		</a>
									 	</div>
								 	</div>
								 	<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
									 	<div class="mg-tile">
									 		<a onclick="openPopup('CitizenFeedBack.html?publishFeedBack')" href='#' title="Feedback">
									 			<span class="mg-image">	
									 				<img src="images/DSCL-images/dscl_feedback.png" class="text-center img-responsive" alt="Smart Citizen Corner">
									 			</span>
									 			<span class="mg-text-container">
									 				<span class="mg-text"><spring:message code="theme5.portal.smart.citizen.corner" text="Smart Citizen Corner" /></span>
									 			</span>
									 		</a>
									 	</div>
								 	</div>
							 	</div>
							</div>
						</div>
					</div>
		   			
				</div>
			</div>
		</div>
	</c:if>
	<!-- Media Gallery ends -->
	
	<!-- E-Services Links  -->
	<div class="eservices-section">
		<div class="container">
			<div class="int-ext-links" id="e-serv">
				<h3 class="sub-heading"><spring:message code="Services" text="Services" /></h3>
				<h1><spring:message code="theme5.portal.e.services" text="E-Services" /></h1>
				<div class="eserv-content">
					<div class="element-animate int-ext-container">
						<c:forEach items="${command.getAllhtml('ESERVICES')}" var="eService"> ${eService} </c:forEach>
					</div>
					<div class="int-ext-buttons">
						<button type="button" class="btn expand-btn-more"><spring:message code="portal.link.viewmore" text="View More" /></button>
						<button type="button" class="btn expand-btn-less"><spring:message code="theme5.portal.view.less" text="View Less" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
    <!-- E-Services Links Ends  -->
    
    <div class=" drag1" id="PublicNotice">
    	<div class="container">
    		<div class="row">
				<div class="clear">
					<c:if
						test="${empty command.themeMap['PUBLIC_NOTICE'] || command.themeMap['PUBLIC_NOTICE'] eq 'A'}">
						<c:set var="class2" value="col-lg-6 col-md-6 col-sm-6" />
						<c:set var="class3" value="col-lg-4 col-md-6 col-sm-6" />
						<c:set var="class4" value="col-lg-4 col-md-12 col-sm-12" />
						<c:set var="class5" value="col-lg-4 col-md-4 col-sm-6" />
					</c:if>
	
					<c:if test="${command.themeMap['PUBLIC_NOTICE'] eq 'I'}">
						<c:set var="class2" value="col-lg-12" />
					</c:if>
					<c:if
						test="${empty command.themeMap['HELPLINE_NO'] || command.themeMap['HELPLINE_NO'] eq 'A'}">
						<c:set var="class1" value="col-lg-6 col-md-6 col-sm-6" />
					</c:if>
	
					<c:if test="${command.themeMap['HELPLINE_NO'] eq 'I'}">
						<c:set var="class1" value="col-lg-12" />
					</c:if>
					
					<%-- Quick Links starts --%>
					<c:if test="${command.themeMap['QUICK_LINKS'] ne 'I'}">
						<%-- <div class="${class1}"> --%>
						<div class="${class5} element-animate quick-links">
							<div class="widget">
								<div class="widget-header">
									<h2><spring:message code="theme5.portal.quick.links" text="Quick Links" /></h2>
									<i class="icofont-external-link"></i>
								</div>
								<div class="widget-content announcement scrolllistbox magazine-section public-notice">
									<c:if test="${command.highlighted eq true}">
										<c:set var="serial_count" scope="page" value="0"/>
										<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
											<c:if test="${lookUp.isUsefullLink ne 'Y' && empty lookUp.isHighlighted && lookUp.isHighlighted ne 'Y'}">
												<div class="public-notice">
													<div class="col-md-12 col-sm-12 col-lg-12 col-xs-12">
														<c:choose>
															<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																${lookUp.noticeSubEn}
															</c:when>
															<c:otherwise>
																${lookUp.noticeSubReg}
															</c:otherwise>
														</c:choose>
														<c:set var="exlink" value="${lookUp.link}" />
														<c:if test="${not empty exlink}">
															<a href="${exlink}" target="_blank" class="">&nbsp;<i class="fa fa-external-link" style="color:red"></i>&nbsp;</a>
														</c:if>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</c:if>
								</div>
								<div class="drag-vm-section">
									<a href="CitizenHome.html?quickLinks" class="drag-vm" title="View More">
										<spring:message code="portal.link.viewmore" text="VIEW MORE" />
									</a>
								</div>
							</div>
						</div>
					</c:if>
					<%-- Quick Links ends --%>
					
					
					<%-- Important Links starts --%>
					<c:if test="${command.themeMap['IMP_LINKS'] ne 'I'}">
						<%-- <div class="${class1}"> --%>
						<div class="${class5} element-animate imp-links">
							<div class="widget">
								<div class="widget-header">
									<h2><spring:message code="theme5.portal.imp.links" text="Important Links" /></h2>
									<i class="icofont-link-alt"></i>
								</div>
								<div class="widget-content announcement scrolllistbox magazine-section public-notice">
									
									<c:if test="${command.highlighted eq true}">
									 <c:set var="serial_count" scope="page" value="0"/>
										<c:forEach items="${command.publicNotices}" var="lookUp"
											varStatus="lk">
											  
											<c:if test="${lookUp.isUsefullLink eq 'Y' && empty lookUp.isHighlighted && lookUp.isHighlighted ne 'Y'}">
												<div class="public-notice">
													<%-- <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
														<span class="serialno-new">${serial_count+1}</span>
														<c:set var="serial_count" scope="page" value="${serial_count+1}"/>
													</div> --%>
	
													<div class="col-md-11 col-sm-11 col-lg-11 col-xs-11">
														<c:set var="link"
															value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
														<c:choose>
																<c:when test="${isDMS}">
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<a href="javascript:void(0);"
																				onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.detailEn}</a>
																		</c:when>
																		<c:otherwise>
																			<a href="javascript:void(0);"
																				onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.detailReg}</a>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			${lookUp.detailEn}
																			<%-- <c:set var="imagelinks" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:forEach items="${imagelinks}" var="downloadL" varStatus="vcount">
																				<c:if test="${vcount.count eq 1}">
																					<apptags:filedownload filename="EIP"
																						filePath="${downloadL}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName="${lookUp.detailEn}"></apptags:filedownload>
																				</c:if>
																				<c:if test="${vcount.count ne 1}">
																					<apptags:filedownload filename="EIP"
																						filePath="${downloadL}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName=""></apptags:filedownload>
																				</c:if>
																			</c:forEach> --%>
																		</c:when>
																		<c:otherwise>
																			<%-- <c:set var="imagelinks" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:forEach items="${imagelinks}" var="downloadL" varStatus="vcount">
																				<c:if test="${vcount.count eq 1}">
																					<apptags:filedownload filename="EIP"
																						filePath="${downloadL}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName="${lookUp.detailReg}"></apptags:filedownload>
																				</c:if>
																				<c:if test="${vcount.count ne 1}">
																				<apptags:filedownload filename="EIP"
																						filePath="${downloadL}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName=""></apptags:filedownload>
																				</c:if>
																			</c:forEach> --%>
																			${lookUp.detailReg}
																		</c:otherwise>
																	</c:choose>
																	<c:if test="${not empty lookUp.profileImgPath}">								
																		<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																		<c:forEach items="${links}" var="download" varStatus="count">
																			<a href="./cache/${download}" target="_blank" class="">&nbsp;<i class="fa fa-download"></i>&nbsp;</a>
																		</c:forEach>
																	</c:if>
																</c:otherwise>
															</c:choose>
															<c:if test="${lookUp.imagePath ne '' && lookUp.imagePath ne ' '}">																	
																<c:set var="ilinks" value="${fn:split(lookUp.imagePath,',')}" />
																<c:forEach items="${ilinks}" var="imagelnk" varStatus="vcount">
																	<a href="${imagelnk}" target="_blank" class=""><i class="fa fa-picture-o" aria-hidden="true"></i></a>
																</c:forEach>
															</c:if>
														<%-- <p>
															<fmt:formatDate type="date" value="${lookUp.issueDate}"
																pattern="dd/MM/yyyy" var="issueDate" />
															${issueDate}
														</p> --%>
														<input type="hidden" name="downloadLink"
															value="${lookUp.profileImgPath}">
	
															<%-- <c:choose>
													<c:when test="${isDMS}">
														<a href="jadocIdvascript:void(0);"
															onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.docName}</a>
													</c:when>
													<c:otherwise>
														<apptags:filedownload filename="EIP"
															filePath="${lookUp.profileImgPath}"
															actionUrl="CitizenPublicNotices.html?Download"
															eipFileName="${lookUp.docName}"></apptags:filedownload>
													</c:otherwise>
												</c:choose> --%>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</c:if>
								</div>
								<div class="drag-vm-section">
									<a href="CitizenHome.html?importantLinks" class="drag-vm" title="View More">
										<spring:message code="portal.link.viewmore" text="VIEW MORE" />
									</a>
								</div>
							</div>
						</div>
					</c:if>
					<%-- Important Links ends --%>
					
					<%-- Public Notice --%>
					<c:if test="${command.themeMap['PUBLIC_NOTICE'] ne 'I'}">
	
						<div class="${class5} element-animate public-notices">
							<div class="widget">
								<div class="widget-header">
									<h2><spring:message code="PublicNotice" text="Public Notice" /></h2>
									<i class="icofont-unique-idea"></i>
								</div>
								<div
									class="widget-content announcement scrolllistbox magazine-section public-notice">
									<c:if test="${command.highlighted eq true}">
									 <c:set var="serial_count" scope="page" value="0"/>
										<c:forEach items="${command.publicNotices}" var="lookUp"
											varStatus="lk">
											  
											<c:if test="${lookUp.isHighlighted eq 'Y' && empty lookUp.isUsefullLink && lookUp.isUsefullLink ne 'Y'}">
												<div class="public-notice">
													<%-- <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
														<span class="serialno-new">${serial_count+1}</span>
														<c:set var="serial_count" scope="page" value="${serial_count+1}"/>
													</div> --%>
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
														<c:set var="link"
															value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
														<c:choose>
																<c:when test="${isDMS}">
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<a href="javascript:void(0);"
																				onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.detailEn}</a>
																		</c:when>
																		<c:otherwise>
																			<a href="javascript:void(0);"
																				onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.detailReg}</a>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			${lookUp.detailEn}
																			<%-- <c:set var="imagelinks" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:forEach items="${imagelinks}" var="downloadL" varStatus="vcount">
																				<c:if test="${vcount.count eq 1}">
																					<apptags:filedownload filename="EIP"
																						filePath="${downloadL}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName="${lookUp.detailEn}"></apptags:filedownload>
																				</c:if>
																				<c:if test="${vcount.count ne 1}">
																					<apptags:filedownload filename="EIP"
																						filePath="${downloadL}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName=""></apptags:filedownload>
																				</c:if>
																			</c:forEach> --%>
																		</c:when>
																		<c:otherwise>
																		     ${lookUp.detailReg}
																			<%-- <c:set var="imagelinks" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:forEach items="${imagelinks}" var="downloadL" varStatus="vcount">
																				<c:if test="${vcount.count eq 1}">
																					<apptags:filedownload filename="EIP"
																						filePath="${downloadL}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName="${lookUp.detailReg}"></apptags:filedownload>
																				</c:if>
																				<c:if test="${vcount.count ne 1}">
																				<apptags:filedownload filename="EIP"
																						filePath="${downloadL}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName=""></apptags:filedownload>
																				</c:if>
																			</c:forEach> --%>
																		</c:otherwise>
																	</c:choose>
																	<c:if test="${not empty lookUp.profileImgPath}">								
																		<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																		<c:forEach items="${links}" var="download" varStatus="count">
																			<a href="./cache/${download}" target="_blank" class="">&nbsp;<i class="fa fa-download"></i>&nbsp;</a>
																		</c:forEach>
																	</c:if>
																</c:otherwise>
															</c:choose>
														<p>
															<fmt:formatDate type="date" value="${lookUp.issueDate}"
																pattern="dd/MM/yyyy" var="issueDate" />
															${issueDate}
														</p>
														
														<c:if test="${lookUp.imagePath ne '' && lookUp.imagePath ne ' '}">																	
															<c:set var="ilinks" value="${fn:split(lookUp.imagePath,',')}" />
															<c:forEach items="${ilinks}" var="imagelnk" varStatus="vcount">
																<a href="${imagelnk}" target="_blank" class=""><i class="fa fa-picture-o" aria-hidden="true"></i></a>
															</c:forEach>
														</c:if>
														
														<input type="hidden" name="downloadLink"
															value="${lookUp.profileImgPath}">
	
															<%-- <c:choose>
													<c:when test="${isDMS}">
														<a href="jadocIdvascript:void(0);"
															onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.docName}</a>
													</c:when>
													<c:otherwise>
														<apptags:filedownload filename="EIP"
															filePath="${lookUp.profileImgPath}"
															actionUrl="CitizenPublicNotices.html?Download"
															eipFileName="${lookUp.docName}"></apptags:filedownload>
													</c:otherwise>
												</c:choose> --%>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</c:if>
	
								</div>
								<div class="drag-vm-section">
									<a href="CitizenHome.html?publicNotices" class="drag-vm" title="View More">
										<spring:message code="portal.link.viewmore" text="VIEW MORE"/>
									</a>
								</div>
							</div>
						</div>
					</c:if>
					<%-- Public Notice ends --%>
					
					<%-- Budget / Finance starts --%>
					<c:if test="${command.themeMap['BUDGET_FINANCE'] ne 'I'}">
						<div class="${class5} element-animate budget-finance">
							<div class="widget">
								<div class="widget-header">
									<h2><spring:message code="theme5.portal.budget.finance" text="Budget & Finance" /></h2>
									<i class="icofont-rupee"></i>
								</div>
								<div class="widget-content announcement scrolllistbox magazine-section public-notice">
									<c:if test="${command.usefull eq true}">
									 <c:set var="serial_count" scope="page" value="0"/>
										<c:forEach items="${command.publicNotices}" var="lookUp"
											varStatus="lk">
											  
											<c:if test="${lookUp.isUsefullLink eq 'Y' && lookUp.isHighlighted eq 'Y'}">
												<div class="public-notice">
													<div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
														<span class="serialno-new">${serial_count+1}</span>
														<c:set var="serial_count" scope="page" value="${serial_count+1}"/>
													</div>
	
													<div class="col-md-11 col-sm-11 col-lg-11 col-xs-11">
														<c:set var="link"
															value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
														<c:choose>
																<c:when test="${isDMS}">
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<a href="javascript:void(0);"
																				onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.detailEn}</a>
																		</c:when>
																		<c:otherwise>
																			<a href="javascript:void(0);"
																				onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.detailReg}</a>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			
																			<c:set var="imagelinks" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:forEach items="${imagelinks}" var="downloadL" varStatus="vcount">
																				<c:if test="${vcount.count eq 1}">
																					<apptags:filedownload filename="EIP"
																						filePath="${downloadL}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName="${lookUp.detailEn}"></apptags:filedownload>
																				</c:if>
																				<c:if test="${vcount.count ne 1}">
																					<apptags:filedownload filename="EIP"
																						filePath="${downloadL}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName=""></apptags:filedownload>
																				</c:if>
																			</c:forEach>
																		</c:when>
																		<c:otherwise>
																			<c:set var="imagelinks" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:forEach items="${imagelinks}" var="downloadL" varStatus="vcount">
																				<c:if test="${vcount.count eq 1}">
																					<apptags:filedownload filename="EIP"
																						filePath="${downloadL}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName="${lookUp.detailReg}"></apptags:filedownload>
																				</c:if>
																				<c:if test="${vcount.count ne 1}">
																				<apptags:filedownload filename="EIP"
																						filePath="${downloadL}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName=""></apptags:filedownload>
																				</c:if>
																			</c:forEach>
																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
														<%-- <p>
														
														<c:if test="${lookUp.imagePath ne '' && lookUp.imagePath ne ' '}">																	
															<c:set var="ilinks" value="${fn:split(lookUp.imagePath,',')}" />
															<c:forEach items="${ilinks}" var="imagelnk" varStatus="vcount">
																<a href="${imagelnk}" target="_blank" class=""><i class="fa fa-picture-o" aria-hidden="true"></i></a>
															</c:forEach>
														</c:if>
														
															<fmt:formatDate type="date" value="${lookUp.issueDate}"
																pattern="dd/MM/yyyy" var="issueDate" />
															${issueDate}
														</p> --%>
														<input type="hidden" name="downloadLink"
															value="${lookUp.profileImgPath}">
	
															<%-- <c:choose>
													<c:when test="${isDMS}">
														<a href="jadocIdvascript:void(0);"
															onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.docName}</a>
													</c:when>
													<c:otherwise>
														<apptags:filedownload filename="EIP"
															filePath="${lookUp.profileImgPath}"
															actionUrl="CitizenPublicNotices.html?Download"
															eipFileName="${lookUp.docName}"></apptags:filedownload>
													</c:otherwise>
												</c:choose> --%>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</c:if>				
								</div>
								<div class="drag-vm-section">
									<a href="CitizenHome.html?budgetFinance" class="drag-vm" title="View More">
										<spring:message code="portal.link.viewmore" text="VIEW MORE"/>
									</a>
								</div>
							</div>
						</div>
					</c:if>
					<%-- Budget / Finance ends --%>
					
					<%-- Public Notice starts --%>
					<%-- <c:if test="${command.themeMap['NEWS'] ne 'I'}">
						
						<div class="${class1} press-release-main">
						<div class="${class1} element-animate">
							<div class="widget">
								<div class="widget-header">
									<h2>
										<!-- <i class="fa fa-newspaper-o"></i> -->
										<i class="fa fa-bullhorn" aria-hidden="true"></i>
										<spring:message code="theme5.portal.public.notice" text="Public Notice" />	
										<a href="CitizenHome.html?newsAndEvent" class="pull-right" title="View More"><spring:message code="portal.link.viewmore" text="VIEW MORE"/></a>
									</h2>
								</div>	
								<div
									class="widget-content announcement scrolllistbox magazine-section public-notice">
									<c:if test="${command.highlighted eq true}">
									 <c:set var="serial_count" scope="page" value="0"/>
										<c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status">
											  
											<c:if test="${lookUp.isHighlighted eq 'Y'}">
												<div class="public-notice">
													<div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
														<span class="serialno-new">${serial_count+1}</span>
														<c:set var="serial_count" scope="page" value="${serial_count+1}"/>
													</div>
	
													<div class="col-md-11 col-sm-11 col-lg-11 col-xs-11">	
														<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
														<c:forEach items="${links}" var="download" varStatus="count">
														<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
														<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
														<c:set var="link"	value="${stringUtility.getStringAfterChars(download)}" />
														<c:choose>
														<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
														<div class="col-lg-12 col-md-8 col-sm-12 col-xs-12 padding-0">
															<c:choose>
															<c:when test="${isDMS}">
																
															<a href="javascript:void(0);" 
															   onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
															</c:when>
															<c:otherwise>
															<apptags:filedownload filename="EIP"
																			filePath="${download}"									
															actionUrl="CitizenHome.html?Download"
															eipFileName="${lookUp.announceDescEng}"></apptags:filedownload>
															</c:otherwise>
															</c:choose>
														</div>
														<!-- <br/> -->
														<span class="date col-lg-12 col-md-4 col-sm-12 col-xs-12 padding-0"><i class="fa fa-calendar" aria-hidden="true"></i>
														<fmt:formatDate value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
														</c:when>
														<c:otherwise>
														<div class="col-lg-12 col-md-8 col-sm-12 col-xs-12 padding-0">
															<c:choose>
															<c:when test="${isDMS}">			  
															<a href="javascript:void(0);" 
															   onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
															</c:when>
															<c:otherwise>
															<apptags:filedownload filename="EIP"
																			filePath="${download}"
															actionUrl="CitizenHome.html?Download"
															eipFileName="${lookUp.announceDescReg}"></apptags:filedownload>
															</c:otherwise>
															</c:choose>
														</div>
														<!-- <br/> -->
														<span class="date col-lg-12 col-md-4 col-sm-12 col-xs-12 padding-0"><i class="fa fa-calendar" aria-hidden="true"></i>
														<fmt:formatDate value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
														</c:otherwise>
														</c:choose>
														</c:forEach>
												  	</div>
												</div>
											</c:if>
										</c:forEach>
									</c:if>
								</div>		
							</div>
						</div>	
					</c:if> --%>
					<%-- Public Notice ends --%>
					
					<%-- Key Contacts --%>
					<c:if test="${command.themeMap['KEY_CONTACTS'] ne 'I'}"><i  id="keyContact"></i>
						<%-- <div class="${class2} help-no"> --%>
						<div class="${class5} element-animate key-contacts">
							<div class="widget">
								<div class="widget-header">
									<h2><spring:message code="theme5.portal.citizen.support" text="For Citizen Support" /></h2>
									<i class="icofont-address-book"></i>
								</div>
								<div class="widget-content  magazine-section scrolllistbox" >
								
									<c:forEach items="${command.organisationContactList }" var="contact">
										<c:if test="${ contact.designationEn ne 'P.S. To Minister'}">
											<div class="kCbox col-lg-12 col-md-12 col-sm-12 col-xs-12">
												<div class="cu-title">
													<c:choose>
														<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																<strong class="cu-name">${contact.contactNameEn}</strong><br>
																<span class="cu-designation">${contact.designationEn}</span><br>
														</c:when>
														<c:otherwise>
																<strong class="cu-name">${contact.contactNameReg}</strong><br>
																<span class="cu-designation">${contact.designationReg}</span><br>	
														</c:otherwise>	
													</c:choose>
												</div>
												<div class="cu-info">
													<ul>
														<li>
															<abbr title="Phone"></abbr><i class="icofont-phone cu-icon"></i>
															<span class="cu-info-text">${contact.telephoneNo1En}</span>
														</li>
														<li>
															<abbr title="Phone"></abbr><i class="icofont-iphone cu-icon"></i>
															<span class="cu-info-text">${contact.telephoneNo2En}</span>
														</li>
														<li>
															<a href="mailto:${contact.email1}" title="${contact.email1}">
												  				<i class="icofont-email cu-icon"></i>
												  				<span class="cu-info-text">${contact.email1}</span>
												  			</a>
														</li>
													</ul>
												</div>
											</div>
										</c:if>
									</c:forEach>							
									
								</div>
							</div>
						</div>
					</c:if>
					<%-- Key Contact End --%>
					
					<%-- Social Media Links starts --%>
					<c:if test="${command.themeMap['SOCIAL_MEDIA_LINKS'] ne 'I'}">
						<div class="${class5} element-animate social-media-links">
							<div class="widget">
								<div class="widget-header">
									<h2><spring:message code="theme5.portal.social.media.links" text="Social Media Links" /></h2>
									<i class="icofont-comment"></i>
								</div>
								<div class="widget-content magazine-section scrolllistbox" >
									<%--  Start of code by ABM2144 for Social Media Section on 21-05-2019 --%>
						            <c:if test="${command.userSession.socialMediaMap.size() gt 0}">
					                     <c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
					                        <div class="public-notice">
					                        	<a href="${media.value}" target="new_${ count.count}">
						                           <i class="fa fa-${media.key}"> <span class="hidden">${media.key}</span></i>
						                           <span>Like Us on ${media.key}</span>
					                           	</a>
					                        </div>
					                     </c:forEach>
						            </c:if>
						            <%--  End of code by ABM2144 for Social Media Section on 21-05-2019 --%>
								</div>
							</div>
						</div>
					</c:if>
					<%-- Social Media Links ends --%>
						
				</div>
			</div>
		</div>
	</div>
    
	<%-- WELCOME CONTENT START --%>
	<c:if test="${not empty command.themeMap['SCHEMES'] && command.themeMap['SCHEMES'] ne 'I'}">
		<c:if test="${not empty command.aboutUsDescFirstPara }">
			<div class="welcome-content">
				<div class="container">
					<h1>
						<spring:message code="theme5.portal.welcome.message" text="Welcome To Dehradun Smart City Limited" />
					</h1>
					<div class="element-animate">
						<p class="welcome-message about1">${command.aboutUsDescFirstPara}</p>
			
						<p class="welcome-message about2">${command.aboutUsDescSecondPara}</p>
						<div class="text-center">
							<a href="CitizenAboutUs.html" class="read-more-btn">
								<spring:message code="ReadMore" text="read more ...." />
							</a>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</c:if>
	<%-- WELCOME CONTENT ENDS --%>

	<%-- <hr/>
	<!-- Get Mobile App Section -->
	<c:forEach items="${command.getAllhtml('GETMOBILEAPP')}" var="getMobileApp"> ${getMobileApp} </c:forEach>
	<!-- Get Mobile App Section ends --> --%>

	<%-- <div class="container-fluid" id="exservices-main">
	<div class="row">
		<div class="col-lg-12">
		<div id="exservices" class="text-center">
		<a href="<spring:message code="dashboard.url"/>" target="_blank" data-target="internal">
		<div class="dcard">
			<div class="image"><i class="fa fa-dashboard fa-3x" aria-hidden="true"></i></div>
			<div class="txt"><spring:message code="portal.dashboard" text="Dashboard" /></div>
		</div></a>
		<a href="#" onclick="showPropertyTaxCalculator(this)" data-target="internal">
		<div class="dcard">
			<div class="image"><i class="fa fa-building fa-3x" aria-hidden="true"></i></div>
			<div class="txt"><spring:message code="PropertyTax" text="Property Tax" /><br><spring:message code="Calculator" text="Calculator" /></div>
		</div></a>
		<div class="dcard mkcmp" >
			<div class="image"><i class="fa fa-comments fa-3x" aria-hidden="true"></i></div>
			<div class="txt"><spring:message code="MakeaComplaint" text="Make a Complaint" /></div>
		</div>
		<div class="dcard ckstat">
			<div class="image"><i class="fa fa-check fa-3x" aria-hidden="true"></i></div>
			<div class="txt"><spring:message code="CheckStatus" text="Check Status" /></div>
		</div>
		<div class="dcard mkpay">
			<div class="image"><i class="fa fa-credit-card fa-3x" aria-hidden="true"></i></div>
			<div class="txt"><spring:message code="MakePayment" text="Make Payment" /></div>
		</div>
		<a href="javascript:void(0)" data-target="internal">
		<div class="dcard">
			<div class="image"><i class="fa fa-mobile fa-3x" aria-hidden="true"></i></div>
			<div class="txt"><spring:message code="GetMobileApp" text="Get MobileApp" /></div>
		</div></a>		
	</div>
</div></div>
<div class="nidaan-contents" style="display:none">
	<!--  Make complaint -->	
		<div id="mkcmp" style="display:none">
		<div class="col-sm-6 col-md-8 col-lg-8 col-xs-12">
			<div class="content-tab">
				<h3><spring:message code="eip.nidan1100" text="Nidaan 1100" /></h3>
				<p><spring:message code="eip.nidan.desc" text="" />&nbsp;</p>
				<p class="yellow"><spring:message code="eip.comp.time" text="" /></p>
			</div>
		</div>		
		<div class="col-sm-6 col-md-4 col-lg-4 col-xs-12">
		<div class="hidden-lg hidden-md hidden-sm" style="margin-top:20px;"></div>
			<div class="content-tab">
				<h3><spring:message code="eip.reg.care" text="Grievance Registration" /></h3>
				<ul class="banner-new">
					<li><a href="javascript:void(0);" data-toggle="modal"
						data-target="#phonemodal"><i class="fa fa-phone"></i>&nbsp;
							<span><spring:message code="eip.tel" text="Registration On Phone" /></span></a></li>
				</ul>
			</div>
		</div></div>
	<div class="clearfix"></div>
	<!--  Make complaint -->
	<!-- Check Status -->	
	<div id="ckstat" style="display:none">
		<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="content-tab">
				<h3><spring:message code="CheckStatus" text="Check Status" /></h3>
				<p><spring:message code="eip.checkstatus.desc" text="" /></p>
				<ul class="banner-new col-sm-6">
					<li><a
						href="CitizenHome.html?applicationStatus"><i
							class="fa fa-file"></i>&nbsp;
							<span><spring:message code="eip.app.status" text="Applicant Status" /></span></a></li>
				</ul>
			</div>
		</div></div>
		<div class="clearfix"></div>
	<!-- Check Status -->
	<!-- Make payment -->
	<div id="mkpay" style="display:none">
		<div class="col-sm-7 col-md-7 col-lg-7 col-xs-12">
							<div class="content-tab">
								<h3><spring:message code="MakePayment" text="Make Payment" /></h3>
								<p><spring:message code="eip.online.process" text="" /></p>
								<ol class="payment">
									<li><spring:message code="eip.ref.no" text="" /></li>
									<li><spring:message code="eip.auth.det" text="" /></li>
									<li><spring:message code="eip.confirm.pay" text="" /></li>
									<li><spring:message code="eip.onl.tran.conf" text="" /></li>
							</ol>
							</div>
						</div>
						<div class="col-sm-5 col-md-5 col-lg-5 col-xs-12">
						<div class="hidden-lg hidden-md hidden-sm" style="margin-top:20px;"></div>
							<div class="content-tab">
								<form class="form">
									<div class="form-group">
										<label for="billtype"><spring:message code="BillType"
												text="Bill Type" /></label> <select class="form-control"
											name="bill-type" id="billtype">
											<option> <spring:message code="selectdropdown" text="Select" /></option>
											<option value="PropertyBillPayment.html"><spring:message code="PropertyBill" text="Property Bill" /></option>
											<option value="WaterBillPayment.html"><spring:message code="WaterBill" text="Water Bill" /></option>
										</select>
									</div>
									<div class="form-group">
										<spring:message code="eg" var="placeholder4" />
										<label for="billNumber"> <spring:message
												code="ConsumerNumber" text="Consumer Number" /></label> <input
											type="text" class="form-control" name="referral"
											id="billNumber" placeholder="${placeholder4}">
									</div>					
									<div class="text-center"><br/><button type="button" onclick="searchBillPay()"
										class="btn btn-success" style="width:150px">
										<spring:message code="eip.page.process" text="Proceed" />
									</button></div>
								</form>
							</div>
						</div>
	</div>
		<div class="clearfix"></div>
	<!-- Make payment -->
</div>	
</div> --%>

	<%-- <!--  Complaint Start Here   -->
	<hr/>
	<div class="container-fluid complaint" id="nidaan">
		<div class="row">
			<div
				class="col-lg-12 col-md-12 col-sm-12 col-xs-12 bhoechie-tab-container">
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 bhoechie-tab-menu">
					<div class="list-group">
						<a href="#" class="list-group-item active"> <i
							class="fa fa-pencil-square-o fa-3x" aria-hidden="true"></i>&nbsp;
						<spring:message code="MakeaComplaint" text="Make a Complaint" />
						</a> <a href="#" class="list-group-item"> <i
							class="fa fa-check-square-o fa-3x" aria-hidden="true"></i>&nbsp;
						<spring:message code="CheckStatus" text="Check Status" />
						</a> <a href="#" class="list-group-item"> <i
							class="fa fa-credit-card fa-3x" aria-hidden="true"></i>&nbsp;
						<spring:message code="MakePayment" text="Make Payment" />
						</a>
					</div>
				</div>
				<div class="col-lg-9 col-md-9 col-sm-12 col-xs-12 bhoechie-tab">
					<!-- flight section -->
					<div class="bhoechie-tab-content active tab-pane">
						<div class="col-sm-7 col-md-7 col-lg-7 col-xs-12">
							<div class="content-tab">
								<h3>
									<spring:message code="eip.nidan1100" text="Nidaan 1100" />
								</h3>
								<p>
									<spring:message code="eip.nidan.desc" text="" />
									&nbsp
									<a href='<spring:message code="care.dashboard.report.url"></spring:message>RP_CARE.rptdesign'
								target="_blank" class="dashboard-blink"><spring:message
									code="eip.care.dashboard" text="DASHBOARD" /></a>
								</p>
								<p class="yellow">
									<spring:message code="eip.comp.time" text="" />
								</p>
							</div>
						</div>

						<div class="col-sm-5 col-md-5 col-lg-5 col-xs-12">
							<div class="content-tab">
								<h3>
									<spring:message code="eip.reg.care"
										text="Grievance Registration" />
								</h3>
								<ul class="banner-new">
									<li><a href="javascript:void(0);" data-toggle="modal"
										data-target="#phonemodal"><img src="assets/img/phone.png"
											alt="Registration On Phone" class="hidden-xs hidden-sm">
											<span><spring:message code="eip.tel"
													text="Registration On Phone" /></span></a></li>


													
							<li><a href="grievance.html"><img
									src="assets/img/internet.png" alt="Registration On Internet" class="hidden-xs hidden-sm"> <span><spring:message
											code="eip.ie" text="Registration On Internet" /></span></a></li>
								</ul>

							</div>
						</div>
					</div>
					<!-- train section -->
					<div class="bhoechie-tab-content tab-pane">
						<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
							<div class="content-tab">
								<h3>
									<spring:message code="CheckStatus" text="Check Status" />
								</h3>
								<p>
									<spring:message code="eip.checkstatus.desc" text="" />
								</p>
								<ul class="banner-new">
									<li class="col-sm-6"><a
								href="grievance.html?grievanceStatus"><img
									src="assets/img/check_status.png" alt="Complaint Status" class="hidden-xs"> <span><spring:message
											code="eip.comp.status" text="Complaint Status" /></span></a></li>
									<li class="col-sm-6"><a
										href="CitizenHome.html?applicationStatus"><img
											src="assets/img/check_status.png" alt="Applicant Status"
											class="hidden-xs"> <span><spring:message
													code="eip.app.status" text="Applicant Status" /></span></a></li>
								</ul>
							</div>
						</div>
					</div>

					<!-- hotel search -->
					<div class="bhoechie-tab-content tab-pane">
						<div class="col-sm-7 col-md-7 col-lg-7 col-xs-12">
							<div class="content-tab">
								<h3>
									<spring:message code="MakePayment" text="Make Payment" />
								</h3>
								<p>
									<spring:message code="eip.online.process" text="" />
								</p>

								<ol class="payment">

									<li><spring:message code="eip.ref.no" text="" /></li>
									<li><spring:message code="eip.auth.det" text="" /></li>
									<li><spring:message code="eip.confirm.pay" text="" /></li>
									<li><spring:message code="eip.onl.tran.conf" text="" /></li>
								</ol>

							</div>
						</div>

						<div class="col-sm-5 col-md-5 col-lg-5 col-xs-12">

							<div class="content-tab">

								<form class="form">
									<div class="form-group">
										<label for="bill-type"><spring:message code="BillType"
												text="Bill Type" /></label> <select class="form-control"
											name="bill-type" id="billtype">
											<option> <spring:message code="selectdropdown" text="Select" /></option>
											<option value="PropertyBillPayment.html"><spring:message code="PropertyBill" text="Property Bill" /></option>
											<option value="WaterBillPayment.html"><spring:message code="WaterBill" text="Water Bill" /></option>
										</select>
									</div>
									<div class="form-group">
										<spring:message code="eg" var="placeholder4" />
										<label for="referral"> <spring:message
												code="ConsumerNumber" text="Consumer Number" /></label> <input
											type="text" class="form-control" name="referral"
											id="billNumber" placeholder="${placeholder4}">
									</div>
									<!-- <div class="form-group">
								<label for="amount"><spring:message code="AmountRs" text="Amount in Rs." /></label> <input type="text"
									class="form-control" name="amount" id="amount"
									placeholder="Amount in Rupees">
							</div> -->									
									<div class="text-center"><br/><button type="button" onclick="searchBillPay()"
										class="btn btn-success" style="width:150px">
										<spring:message code="eip.page.process" text="Proceed" />
									</button></div>
								</form>


							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--  Complaint End Here   --> --%>

<%-- Dashboard/Property Tax/Mobile App Start
	<section class="dash">
		<div class="">
			<div class="row mbr-justify-content-center">
				<div class="col-lg-4 col-md-4 col-sm-4 dash-bg">
					<a href="<spring:message code="dashboard.url"/>" target="_blank">
						<div class="wrap">
							<div class="ico-wrap">
								<img src="assets/img/dashboard-icon1.png" height="67" width="67">
							</div>
							<div class="text-wrap vcenter">
								<h2
									class="mbr-fonts-style mbr-bold mbr-section-title3 display-5">
									<spring:message code="portal.dashboard" text="Dashboard" />
								</h2><br/>
							</div>
						</div>
					</a>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-4 tax-bg">
					<a href="#" onclick="showPropertyTaxCalculator(this)">
						<div class="wrap">
							<div class="ico-wrap">
								<img src="assets/img/property-tax-icon1.png" height="67"
									width="67">
							</div>
							<div class="text-wrap vcenter">
								<h2
									class="mbr-fonts-style mbr-bold mbr-section-title3 display-5">
									<spring:message code="PropertyTaxCalculator"
										text="Property Tax Calculator" />
								</h2><br>
							</div>
						</div>
					</a>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-4 mob-bg">
					<a href="#">
						<div class="wrap">
							<div class="ico-wrap">
								<img src="assets/img/android-67.png" height="67" />
							</div>
							<div class="text-wrap vcenter">
								<h2
									class="mbr-fonts-style mbr-bold mbr-section-title3 display-5">
									<spring:message code="GetMobileApp" text="Get Mobile App" />
								</h2><br/>
							</div>
						</div>
					</a>
				</div>
				<div class="col-lg-6 mbr-col-md-10"></div>
			</div>
		</div>

	</section>
	
	Dashboard/Property Tax/Mobile App End --%>
	
	<!-- modal for care registration on phone start -->
		<div class="modal fade" id="phonemodal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<spring:message code="eip.tel" text="Registration On Phone" />
					</h4>
				</div>
				<div class="modal-body">
					<p>
						<spring:message code="eip.grievance.msg"
							text="Citizens can Dial 1100 to Register the Grievance. ." />
					</p>
				</div>

			</div>
		</div>
	</div>
	<!-- modal for care registration on phone end -->

<!-- 	<div class="common-bg">
		<div class="container-fluid" id="keyContact">
			<div class="row"> -->
				<c:if
					test="${empty command.themeMap['NEWS'] || command.themeMap['NEWS'] eq 'A'}">
					<c:set var="class3" value="col-lg-6 col-md-6 col-sm-6" />
				</c:if>


				<c:if test="${command.themeMap['SLIDER_IMG'] eq 'A'}">
					<c:set var="class3" value="col-lg-12" />
				</c:if>
				<c:if test="${command.themeMap['NEWS'] eq 'I'}">
					<c:set var="class3" value="col-lg-12" />
				</c:if>
				<c:if
					test="${empty command.themeMap['KEY_CONTACTS'] || command.themeMap['KEY_CONTACTS'] eq 'A'}">
					<c:set var="class" value="col-lg-6 col-md-6 col-sm-6" />
				</c:if>


				<c:if test="${command.themeMap['KEY_CONTACTS'] eq 'I'}">
					<c:set var="class" value="col-lg-12" />
				</c:if>
				<%-- <c:if test="${command.themeMap['KEY_CONTACTS'] ne 'I'}">
<!-- Key Contact Strat -->
					<div class="${class3}">

						<div class="key-contact contact">
							<div class="row">
								<div class="col-md-3">
									<div class="contact-info">
										<img src="assets/img/mail-contact.png" alt="image" width="64"
											height="64" />
										<h2>
											<spring:message code="KeyContacts" text="Key Contacts" />
										</h2>
										<h4><spring:message code="KeyContacts.tagline" text="Get in touch with us !" /></h4>
									</div>
								</div>
								<div class="col-md-9">
									<div id="list-main">
										<ul id="list">
											<c:forEach items="${command.organisationContactList }"
												var="contact">
												<c:if test="${ contact.designationEn ne 'P.S. To Minister'}">
													<li style="display: block;"><c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<h3>${contact.contactNameEn}
																	<small> ${contact.designationEn}</small>
																</h3>
															</c:when>
															<c:otherwise>
																<h3>${contact.contactNameReg}
																	<small> ${contact.designationReg}</small>
																</h3>
															</c:otherwise>
														</c:choose>
														<table width="100%" class="table-respns">
															<thead>
																<tr>
																	<th class="hidden-lg  hidden-md hidden-sm">Phone</th>
																	<th class="hidden-lg  hidden-md hidden-sm">Mobile</th>
																	<th class="hidden-lg  hidden-md hidden-sm">Email</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td class="hidden-xs" width="8%" align="center"><i
																		class="fa fa-phone" aria-hidden="true"><span
																			class="hide">Phone</span></i></td>
																	<td width="25%"><span class="minister-phone">
																			${contact.telephoneNo1En}</span></td>
																	<td class="hidden-xs" width="8%" align="center"><i
																		class="fa fa-mobile" aria-hidden="true"><span
																			class="hide">Mobile</span></i></td>
																	<td width="20%"><span class="minister-phone">${contact.telephoneNo2En}</span></td>
																	<td class="hidden-xs" width="8%" align="center"><i
																		class="fa fa-envelope" aria-hidden="true"><span
																			class="hide">Email</span></i></td>
																	<td width="36%"><span class="minister-phone"><a
																			href="mailto:${contact.email1}"
																			title="${contact.email1}">${contact.email1}</a></span></td>
																</tr>
															</tbody>
														</table></li>
												</c:if>
											</c:forEach>
										</ul>
										<c:forEach items="${command.organisationContactList }" var="contact">
										<c:if test="${ contact.designationEn ne 'P.S. To Minister'}">
										<div class="kCbox col-lg-4 col-md-6 col-sm-3 col-xs-6">
										<address>
										<c:choose>
										<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
												<strong>${contact.contactNameEn}</strong><br>
												${contact.designationEn}<br>
										</c:when>
										<c:otherwise>
												<strong>${contact.contactNameReg}</strong><br>
												${contact.designationReg}<br>	
										</c:otherwise>	
										</c:choose>	
												<abbr title="Phone"></abbr><i class="fa fa-phone" aria-hidden="true"></i> ${contact.telephoneNo1En}<br>
												<abbr title="Phone"></abbr><i class="fa fa-mobile" aria-hidden="true"></i> ${contact.telephoneNo2En}<br>
											  <a href="mailto:${contact.email1}" title="${contact.email1}">
											  <i class="fa fa-envelope" aria-hidden="true"></i> ${contact.email1}</a>
											</address>
											</div>
										</c:if>
									</c:forEach>		
										</div>
								</div>
							</div>
						</div>
					</div>
				</c:if> --%>
				<!--Key Contacts -->


				<!--  News and Events  -->
				<%-- <c:if test="${command.themeMap['SLIDER_IMG'] eq 'I'}">
				<c:if test="${command.themeMap['NEWS'] ne 'I'}">
					<div class="${class}" id="news">
						<div class=" news-department">
							<div class="container-fluid clear">
								<div class="row">
									<div class="col-md-3 col-lg-3">
										<div class="contact-info">
											<img src="assets/img/latest-news.png" alt="image"
												style="height: 64px; width: 64px;">
											<h2 data-animation-effect="fadeInDown"
												data-effect-delay="600">
												<spring:message code="portal.Latest.News" text="Latest News" />
											</h2>
											<h4><spring:message code="portal.News.tagline" text="Top Stories !!!" /></h4>
										</div>
									</div>
									<div class="col-md-9 col-lg-9 white" style="height: 600px;">
										<ul
											class="news-holder-departments clearfix announcement scrolllistbox">

											<c:forEach items="${command.eipAnnouncement}" var="lookUp"
												varStatus="status">

												<li class="wow fadeInLeft animated animated"
													data-wow-offset="10" data-wow-duration="1.5s"
													style="visibility: visible; -webkit-animation-duration: 1.5s; -moz-animation-duration: 1.5s; animation-duration: 1.5s;">

													<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
													<c:forEach items="${links}" var="download"
														varStatus="count">
														<c:set var="idappender"
															value="<%=java.util.UUID.randomUUID()%>" />
														<c:set var="idappender"
															value="${fn:replace(idappender,'-','')}" />
														<c:set var="link"
															value="${stringUtility.getStringAfterChars(download)}" />

														<div class="news-description">

															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<div class="col-md-5 col-sm-5 col-lg-5 col-xs-5">
																		<!-- <div class="view overlay hm-white-slight z-depth-1-half"> -->

																		<c:if test="${ lookUp.attachImage ne ' '}">
																			<img src="${lookUp.attachImage }" class="img-fluid"
																				alt="Minor sample post image">
																		</c:if>
																		<c:if test="${ lookUp.attachImage eq ' '}">
																			<img src="\images\news.svg" class="img-fluid"
																				alt="Minor sample post image">
																		</c:if>
																		<!-- </div> -->
																	</div>

																	<div class="col-md-7 col-sm-7 col-lg-7 col-xs-7 ">

																		<c:choose>
																			<c:when test="${isDMS}">
																				<h3>
																					<a href="javascript:void(0);"
																						onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
																				</h3>
																			</c:when>
																			<c:otherwise>

																				<h3>
																					<apptags:filedownload filename="EIP"
																						filePath="${download}"
																						actionUrl="CitizenHome.html?Download"
																						eipFileName="${lookUp.announceDescEng}"></apptags:filedownload>
																				</h3>

																			</c:otherwise>
																		</c:choose>
																		<span class="date"><i class="fa fa-calendar"
																			aria-hidden="true"></i> <fmt:formatDate
																				value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>

																	</div>
																</c:when>
																<c:otherwise>
																	<div class="col-md-5 col-sm-5 col-lg-5 col-xs-5">
																		<!--  <div class="view overlay hm-white-slight z-depth-1-half"> -->
																		<c:if test="${ lookUp.attachImage ne ' '}">
																			<img src="${lookUp.attachImage }" class="img-fluid"
																				alt="Minor sample post image">
																		</c:if>
																		<c:if test="${ lookUp.attachImage eq ' '}">
																			<img src="\images\news.svg" class="img-fluid"
																				alt="Minor sample post image">
																		</c:if>
																		<!-- </div> -->
																	</div>
																	<div class="col-md-7 col-sm-7 col-lg-7 col-xs-7 ">

																		<c:choose>
																			<c:when test="${isDMS}">
																				<h3>
																					<a href="javascript:void(0);"
																						onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
																				</h3>
																			</c:when>
																			<c:otherwise>


																				<h3>
																					<apptags:filedownload filename="EIP"
																						filePath="${download}"
																						actionUrl="CitizenHome.html?Download"
																						eipFileName="${lookUp.announceDescReg}"></apptags:filedownload>
																				</h3>

																			</c:otherwise>
																		</c:choose>
																		<span class="date"><i class="fa fa-calendar"
																			aria-hidden="true"></i> <fmt:formatDate
																				value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
																	</div>
																</c:otherwise>
															</c:choose>

														</div>

													</c:forEach>

												</li>

											</c:forEach>



										</ul>

									</div>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				</c:if> --%>
				<!--  News and Events  -->

		<!--Public Notice START  -->

		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content"></div>
			</div>

		</div>
			
		<%-- <c:if test="${command.themeMap['HELPLINE_NO'] ne 'I'}">
				<i id="helpline"></i><hr/>			
				<div class="helpline-main">
				<div class="col-md-12 col-lg-12 help-no depser text-center">							
							<h2><spring:message code="eip.helplineNo" text="Helpline Numbers" /></h2>
						</div>
						<div class="drag1">
						<div class="dark-blue height-3900">
							<div class="helpline help-line">
								<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
									<div class="helpline-list col-xs-12">
										<span class="list-number  col-sm-12"><spring:message
												code="eip.citizen.center" text="Citizen's Call center" /></span>
										<span class="list-text"><spring:message
												code="" text="155300" /></span>
		
									</div>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
									<div class="helpline-list col-xs-12">
										<span class="list-number  col-sm-12"><spring:message
												code="eip.child.helpline" text="Child Helpline" /></span> <span
											class="list-text "><spring:message code="1098"
												text="1098" /></span>
									</div>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
									<div class="helpline-list col-xs-12">
										<span class="list-number  col-sm-12"><spring:message
												code="eip.women.helpline" text="Women Helpline" /></span> <span
											class="list-text "><spring:message code=""
												text="1091" /></span>
									</div>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
									<div class="helpline-list col-xs-12">
										<span class="list-number  col-sm-12"><spring:message
												code="eip.airport" text="Airport" /></span> <span
											class="list-text "><spring:message code=""
												text="2418201" /></span>
									</div>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
									<div class="helpline-list col-xs-12">
										<span class="list-number  col-sm-12"><spring:message
												code="eip.railway" text="Railway" /></span> <span
											class="list-text "><spring:message code=""
												text="139/2528131" /></span>
									</div>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
									<div class="helpline-list col-xs-12">
										<span class="list-number  col-sm-12"><spring:message
												code="eip.ambulance" text="Ambulance" /> </span> <span
											class="list-text "><spring:message code=""
												text="102, 108" /></span>
									</div>
								</div>
		
							</div>
				</div><div class="clearfix"></div></div></div>
			</c:if> --%>

	<!-- SCHEME AND BANNER LINK START -->
	 <%-- <c:if
		test="${not empty command.themeMap['SCHEMES'] && command.themeMap['SCHEMES'] ne 'I'}">

		<div class="container-fluid facts" id="schemes">
			<c:if test="${command.scheme eq true}">

				<div class="row">
					<div class="col-md-12 col-lg-12">
						<h2 class="text-center" data-animation-effect="fadeInDown"
							data-effect-delay="600">
							<spring:message code="portal.Scheme" text="Schemes" />
						</h2>
					</div>
					<div class="container-fluid">
						<div class="clear" id="schemes-new">
							<div class="owl-carousel owl-theme">
								<c:set var="schemeCount" value="0" scope="page" />
								<c:forEach items="${command.publicNotices}" var="lookUp"
									varStatus="lk">
									<c:if
										test="${lookUp.isHighlighted ne 'Y' && lookUp.isUsefullLink ne 'Y'}">
										<c:set var="schemeCount" value="${schemeCount + 1}"
											scope="page" />

										<c:choose>
											<c:when test="${not empty lookUp.profileImgPath }">
												<c:set var="link"
													value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
												<div class="column">
													<div class="post-module">
														<c:if test="${lookUp.imagePath ne ' ' }">
															<c:set var="search" value="\\" />
															<c:set var="replace" value="\\\\" />
															<c:set var="path"
																value="${fn:replace(link,search,replace)}" />
															<c:if test="${empty lookUp.detailEn}">
																<div class="thumbnail">
																	<a href="javascript:void(0);"
																		onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')"
																		><img src="${lookUp.imagePath }"
																		class="img-responsive" alt="${lookUp.imagePath }"></a>
																</div>
															</c:if>
															<c:if test="${not empty lookUp.detailEn}">
																<div class="thumbnail">
																	<a href="javascript:void(0);"
																		onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')"
																		><img src="${lookUp.imagePath }"
																		class="img-responsive" alt="${lookUp.detailEn }"></a>
																</div>
															</c:if>
														</c:if>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<div class="post-content">
																	<h1 class="title">
																		<apptags:filedownload filename="EIP"
																			filePath="${lookUp.profileImgPath}"
																			actionUrl="CitizenPublicNotices.html?Download"
																			eipFileName="${lookUp.detailEn}"></apptags:filedownload>
																	</h1>
																	<h2 class="sub_title">
																		<apptags:filedownload filename="EIP"
																			filePath="${lookUp.profileImgPath}"
																			actionUrl="CitizenPublicNotices.html?Download"
																			eipFileName="${lookUp.noticeSubReg}"></apptags:filedownload>
																	</h2>

																</div>
															</c:when>
															<c:otherwise>
																<div class="post-content">
																	<h1 class="title">
																		<apptags:filedownload filename="EIP"
																			filePath="${lookUp.profileImgPath}"
																			actionUrl="CitizenPublicNotices.html?Download"
																			eipFileName="${lookUp.detailReg}"></apptags:filedownload>
																	</h1>
																	<h2 class="sub_title">
																		<apptags:filedownload filename="EIP"
																			filePath="${lookUp.profileImgPath}"
																			actionUrl="CitizenPublicNotices.html?Download"
																			eipFileName="${lookUp.noticeSubReg}"></apptags:filedownload>

																	</h2>
																</div>
															</c:otherwise>
														</c:choose>
													</div>
												</div>

											</c:when>
											<c:otherwise>

												<div class="column">
													<div class="post-module">
														<c:if test="${lookUp.imagePath ne ' '}">
															<c:if test="${not empty lookUp.detailEn}">
																<div class="thumbnail">
																	<a class="title" title="${lookUp.detailEn}"
																		href="${lookUp.link}" target="new"><img
																		src="${lookUp.imagePath }" class="img-responsive"
																		alt="${lookUp.detailEn }"></a>
																</div>
															</c:if>
															<c:if test="${empty lookUp.detailEn}">
																<div class="thumbnail">
																	<a class="title" title="${lookUp.detailEn}"
																		href="${lookUp.link}" target="new"><img
																		src="${lookUp.imagePath }" class="img-responsive"
																		alt="${lookUp.imagePath }"></a>
																</div>
															</c:if>
														</c:if>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<div class="post-content">
																	<h1 class="title">
																		<a class="" title="${lookUp.detailEn}"
																			href="${lookUp.link}" target="new">${lookUp.detailEn}</a>
																	</h1>
																	<h2 class="sub_title">
																		<a class="" title="${lookUp.noticeSubReg}"
																			href="${lookUp.link}" target="new">${lookUp.noticeSubReg}</a>
																	</h2>
																</div>
															</c:when>
															<c:otherwise>
																<div class="post-content">
																	<h1 class="title">
																		<a class="" title="${lookUp.detailReg}"
																			href="${lookUp.link}" target="new">${lookUp.detailReg}</a>
																	</h1>
																	<h2 class="sub_title">
																		<a class="" title="${lookUp.noticeSubReg}"
																			href="${lookUp.link}" target="new">${lookUp.noticeSubReg}</a>
																	</h2>
																</div>

															</c:otherwise>
														</c:choose>
													</div>
												</div>

											</c:otherwise>
										</c:choose>

									</c:if>
								</c:forEach>

							</div>
							<c:if test="${schemeCount gt 8}">
								<div class="news-bttn-area text-center">
									<a href="CitizenHome.html?schemes" class="bttn"
										title="View More"><spring:message
											code="portal.link.viewmore" text="VIEW MORE" /></a>
								</div>
							</c:if>
						</div>
					</div>
				</div>


			</c:if>
		</div>
	</c:if>  --%>

	<!-- SCHEME AND BANNER LINK END -->

	<%-- <c:if
		test="${not empty command.themeMap['IMP_LINKS'] && command.themeMap['IMP_LINKS'] ne 'I'}">
		<div class="container-fluid important-bg" id="importantLink">
			<div class="container">
				<div class="col-md-12 col-lg-12">
					<h2 class="text-center" data-animation-effect="fadeInDown"
						data-effect-delay="600">
						<spring:message code="ImportantLinks" text="Important Links" />
					</h2>
				</div>
				<div class="col-sm-12 col-lg-12 col-lg-12 col-xs-12 text-center">
					<ul>



						<c:forEach items="${command.publicNotices}" var="lookUp"
							varStatus="lk">

							<c:if test="${lookUp.isUsefullLink eq 'Y'}">
								<c:choose>
									<c:when test="${not empty lookUp.profileImgPath }">


										<c:set var="link"
											value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
										<li><c:if test="${lookUp.imagePath ne ' ' }">
												<c:set var="search" value="\\" />
												<c:set var="replace" value="\\\\" />
												<c:set var="path" value="${fn:replace(link,search,replace)}" />
												<c:if test="${empty lookUp.detailEn}">
													<a href="javascript:void(0);"
														onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')"
														><img src="${lookUp.imagePath }"
														class="img-responsive" alt="${lookUp.imagePath }"></a>
												</c:if>
												<c:if test="${not empty lookUp.detailEn}">
													<div class="banner-img">
														<a href="javascript:void(0);"
															onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')"
															><img src="${lookUp.imagePath }"
															class="img-responsive" alt="${lookUp.detailEn }"></a>
													</div>
												</c:if>
											</c:if> <c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() eq 1}">
													<c:if test="${lookUp.imagePath eq' ' }">
														<p class="padding-18">
													</c:if>
													<apptags:filedownload filename="EIP" filePath="${lookUp.profileImgPath}"
														actionUrl="CitizenPublicNotices.html?Download"
														eipFileName="${lookUp.detailEn}"></apptags:filedownload>
													<c:if test="${lookUp.imagePath eq ' '}">
														</p>
													</c:if>
												</c:when>
												<c:otherwise>
													<c:if test="${lookUp.imagePath eq' ' }">
														<p class="padding-18">
													</c:if>
													<apptags:filedownload filename="EIP" filePath="${lookUp.profileImgPath}"
														actionUrl="CitizenPublicNotices.html?Download"
														eipFileName="${lookUp.detailReg}"></apptags:filedownload>
													<c:if test="${lookUp.imagePath eq ' '}">
														</p>
													</c:if>
												</c:otherwise>
											</c:choose></li>

									</c:when>
									<c:otherwise>


										<li><c:if test="${lookUp.imagePath ne ' '}">
												<c:if test="${not empty lookUp.detailEn}">
													<div class="banner-img">
														<a class="title" title="${lookUp.detailEn}"
															href="${lookUp.link}" target="_blank"><img
															src="${lookUp.imagePath }" class="img-responsive"
															alt="${lookUp.detailEn}"></a>
													</div>
												</c:if>
												<c:if test="${empty lookUp.detailEn}">
													<a class="title" title="${lookUp.detailEn}"
														href="${lookUp.link}" target="_blank"><img
														src="${lookUp.imagePath }" class="img-responsive"
														alt="${lookUp.imagePath }"></a>
												</c:if>
											</c:if> <c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() eq 1}">
													<c:if test="${lookUp.imagePath eq ' '}">
														<p class="padding-18">
													</c:if>
													<a class="title" title="${lookUp.detailEn}"
														href="${lookUp.link}" target="_blank">${lookUp.detailEn}</a>
													<c:if test="${lookUp.imagePath eq ' ' }">
														</p>
													</c:if>
												</c:when>
												<c:otherwise>
													<c:if test="${lookUp.imagePath eq ' '}">
														<p class="padding-18">
													</c:if>
													<a class="title" title="${lookUp.detailReg}"
														href="${lookUp.link}" target="_blank">${lookUp.detailReg}</a>
													<c:if test="${lookUp.imagePath eq ' ' }">
														</p>
													</c:if>
												</c:otherwise>
											</c:choose></li>

									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>


					</ul>
				</div>
			</div>
		</div>
	</c:if> --%>
	<!--  IMPORTANT LINK-->

	<!--<c:if test="${command.themeMap['EXTERNAL_SERVICES'] ne 'I'}">
		<div id="footer">
			<div class="container-fluid">

				<div class="row">

					<div class="col-md-12 col-xs-12">
						<h2>
							<spring:message code="external.services" text="External Services" />
						</h2>
						<div class="row">
							<div class="container-fluid ">
								<div class="col-sm-2 col-xs-12">
									<h3>
										<spring:message code="ex.certificates" text="Certificates" />
									</h3>

									<ul class="nav nav-list narrow">

										<li><a
											href="https://edistrict.cgstate.gov.in/PACE/instructionPageHome.do?serviceId=1&OWASP_CSRFTOKEN=ET80-2MFL-5TQK-IR1S-8DYU-D423-QPPH-REMZ"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.birth.certificate" text="Birth Certificate" /></a></li>
										<li><a
											href="https://edistrict.cgstate.gov.in/PACE/instructionPageHome.do?serviceId=2&OWASP_CSRFTOKEN=ET80-2MFL-5TQK-IR1S-8DYU-D423-QPPH-REMZ"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.death.certificate" text="Death Certificate" /></a></li>
										<li><a
											href="https://edistrict.cgstate.gov.in/PACE/instructionPageHome.do?serviceId=7&OWASP_CSRFTOKEN=ET80-2MFL-5TQK-IR1S-8DYU-D423-QPPH-REMZ"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.residence.certificate"
													text="Residence Certificate" /></a></li>
										<li><a
											href="https://edistrict.cgstate.gov.in/PACE/instructionPageHome.do?serviceId=3&OWASP_CSRFTOKEN=ET80-2MFL-5TQK-IR1S-8DYU-D423-QPPH-REMZ"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.marriage.registration"
													text="Marriage Registration" /> </a></li>
										<li><a
											href="https://edistrict.cgstate.gov.in/PACE/instructionPageHome.do?serviceId=7&OWASP_CSRFTOKEN=ET80-2MFL-5TQK-IR1S-8DYU-D423-QPPH-REMZ"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.caste.certificate" text="Caste Certificate" /> </a></li>
									</ul>

								</div>
								<div class="col-sm-2 col-xs-12">
									<h3>
										<spring:message code="ex.bills" text="Bills" />
									</h3>

									<ul class="nav nav-list narrow">


										<li><a
											href="https://portal2.bsnl.in/myportal/cfa.do?gclid=EAIaIQobChMIn_qSxqHF2QIVwQ0rCh3zPgMcEAAYASAAEgLzVfD_BwE"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.pay.bill" text="Pay Telephone Bill Online" /></a></li>
										<li><a
											href="http://www.cspdcl.co.in/cseb/(S(jwufbn0pgajoqjirwogy22xc))/billPayment.aspx"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.electricity.bill" text="Electricity Bill Payment" />
										</a></li>
										<li><a
											href="http://www.nagarnigamprojects.in/tax/admin/old-tax-pay/create"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.online.property"
													text="Online Property Tax Payment" /> </a></li>

									</ul>
								</div>
								<div class="col-sm-2 col-xs-12">
									<h3>
										<spring:message code="ex.election" text="Election" />
									</h3>

									<ul class="nav nav-list narrow">

										<li><a href=" http://election.cg.nic.in/elesrch/"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.search.name"
													text="Search Your Name In Voters List" /> </a></li>
										<li><a
											href="http://election.cg.nic.in/voterlist/Default.aspx"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.voter.list" text="Voters List" /> </a></li>
										<li><a
											href="http://election.cg.nic.in/voterlist/Default.aspx"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.list.polling" text="List of Polling Stations" />
										</a></li>
									</ul>

								</div>
								<div class="col-sm-2 col-xs-12">
									<h3>
										<spring:message code="ex.magisterial" text="Magisterial" />
									</h3>

									<ul class="nav nav-list narrow">

										<li><a
											href="http://districtcourt-raipur.cg.nic.in/dcraipur/"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.district.court" text="District Court Raipur" /> </a></li>
										<li><a href=" http://highcourt.cg.gov.in/" target="_new"><i
												class="fa fa-angle-double-right"></i> <spring:message
													code="ex.high.court" text="High Court of Chhattisgarh" />
										</a></li>
									</ul>

								</div>

								<div class="col-sm-2 col-xs-12">
									<h3>
										<spring:message code="ex.revenue" text="Revenue" />
									</h3>

									<ul class="nav nav-list narrow">

										<li><a href="https://revenue.cg.nic.in/revcase/"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.ecourt" text="Ecourt " /> </a></li>
										<li><a href="http://cg.nic.in/bhunaksha/" target="_new"><i
												class="fa fa-angle-double-right"></i> <spring:message
													code="ex.bhunaksha" text="Bhu-Naksha" /></a></li>
										<li><a href="https://bhuiyan.cg.nic.in/" target="_new"><i
												class="fa fa-angle-double-right"></i> <spring:message
													code="ex.bhuiyan" text="Bhuiyan" /> </a></li>
										<li><a
											href="http://cg.nic.in/raipur/misal/UserSearch.aspx"
											target="_new"><i class="fa fa-angle-double-right"></i> <spring:message
													code="ex.missal" text="Missal/Chakbandi" /></a></li>
									</ul>

								</div>

								<div class="col-sm-2 col-xs-12">
									<h3>
										<spring:message code="ex.social.security"
											text="Social Security" />
									</h3>

									<ul class="nav nav-list narrow">

										<li><a href=" http://sw.cg.gov.in/" target="_new"><i
												class="fa fa-angle-double-right"></i> <spring:message
													code="ex.social.welfare" text="Social Welfare" /> </a></li>

									</ul>

								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>-->

<!--AUBOUT US START  -->

	<%-- <c:if test="${not empty command.aboutUsDescFirstPara }">
		<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 about11"
			id="about11">
			<div class="about-content">
				<h1>
					<spring:message code="eip.citizen.aboutUs.title" />
				</h1>
				<p class="about about1">${command.aboutUsDescFirstPara}</p>

				<h1>
					<spring:message code="eip.citizen.aboutUs.vision" text="Vision" />
				</h1>
				<p class="about about2">${command.aboutUsDescSecondPara}</p>
				<a href="CitizenAboutUs.html" class="read-more pull-right"><spring:message
						code="ReadMore" text="read more ...." /> </a>

			</div>
		</div>
	</c:if> --%>
	<!--AUBOUT US END  -->
	<%-- <div class="clear"></div>	
<section class="subscribe-area pt-15">
	<div class="container">
		<div class="row">

			<div class="col-md-4">
				<div class="subscribe-text mb-15">
					<span><spring:message code="eip.newsletter.msg" text="JOIN OUR NEWSLETTER" /></span>
					<h2>
						<spring:message code="eip.newsletter" text="Suscribe for Newsletter" />
					</h2>
				</div>
			</div>
			<div class="col-md-8">
				<div class="subscribe-wrapper subscribe2-wrapper mb-15">
				<div class="error-div alert alert-danger alert-dismissable"
							role="alert" style="display: none"></div>
				<div class="message alert" role="alert" style="display: none"></div>
					<div class="subscribe-form">
						<form class="form">
							<spring:message code="newsletter.placeholder" var="placeholder3" />							
							<input type="email" name="email" id="subscribeemail" autocomplete="off" aria-label="${placeholder3}"
								placeholder="${placeholder3}">
								

							<button onclick="subscribeNewsLetter(this)" type="button">
								<spring:message code="portal.subscribe" text="Subscribe" />
								<i class="fa fa-long-arrow-right"></i>
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>

	</div>
</section> --%>
</div>
<script src="js/eip/citizen/guest-home.js"></script>

<script>
	$(document)
			.ready(
					function() {

						var show_per_page = 5;
						/* var number_of_items = $('#list').children('li').size(); */
						var number_of_items = $('#list').children('li').length;
						var number_of_pages = Math.ceil(number_of_items
								/ show_per_page);

						$('#list-main')
								.append(
										'<ul class="pagination"></ul><input id=current_page type=hidden><input id=show_per_page type=hidden>');
						$('#current_page').val(0);
						$('#show_per_page').val(show_per_page);

						var navigation_html = '';
						var current_link = 0;
						while (number_of_pages > current_link) {
							navigation_html += '<li><a class="page" onclick="go_to_page('
									+ current_link
									+ ')" longdesc="'
									+ current_link
									+ '">'
									+ (current_link + 1)
									+ '</a></li>';
							current_link++;
						}
						navigation_html += '';

						$('.pagination').html(navigation_html);
						$('.pagination li a.page:first').addClass('active');

						$('#list').children().css('display', 'none');
						$('#list').children().slice(0, show_per_page).css(
								'display', 'block');

					});

	function go_to_page(page_num) {

		var show_per_page = parseInt($('#show_per_page').val(), 0);
		start_from = page_num * show_per_page;
		end_on = start_from + show_per_page;
		$('#list').children().css('display', 'none').slice(start_from, end_on)
				.css('display', 'block');

		$('ul li a.active').removeClass('active');
		$('.pagination li a.page[longdesc=' + page_num + ']')
				.addClass('active');
		$('#current_page').val(page_num);
	}

	$(function() {
		$('ul.scrolllistbox-new').marquee();
	});
</script>
<script>
$(document).ready(function() {
    $("div.bhoechie-tab-menu>div.list-group>a").click(function(e) {
        e.preventDefault();
        $(this).siblings('a.active').removeClass("active");
        $(this).addClass("active");
        var index = $(this).index();
        $("div.bhoechie-tab>div.bhoechie-tab-content").removeClass("active");
        $("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active");
    });
    
	  $(".header-inner").addClass('hide');
	
});
</script>
<c:if
	test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">
	<script>
$(document).ready(function() {
    $('#sensorEmptyTabDiv').hide(); 
	$('#challanEmptyTabDiv').hide();

		var space=$('.minister-details').offset().top;
		if(space>=200){
		 $('.org-select').addClass('org-select-slider').css('float','right');
			/* $('.minister-div').removeClass('col-lg-9').addClass('col-lg-12');
			$('.minister-div').removeClass('col-md-8').addClass('col-md-12'); */
			
		}else{
			$('.org-select').addClass('org-select-minister');
			/* $('.minister-div').removeClass('col-lg-12').addClass('col-lg-9');
			$('.minister-div').removeClass('col-md-12').addClass('col-md-8'); */
		}
});


	

</script>
</c:if>

<c:if test="${(not empty command.themeMap['SLIDER_IMG']) && (command.themeMap['SLIDER_IMG'] ne 'A')}">  
 <script>
$(function(){
	$('.about-bg').css('margin-top','65px');	
});
</script>
</c:if>
