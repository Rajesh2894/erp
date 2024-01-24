<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility" />
<jsp:useBean id="marathiConvert" class="com.abm.mainet.common.util.Utility"></jsp:useBean>
<jsp:useBean id="test" class="com.abm.mainet.common.util.Utility"/>
<%@ page import="java.util.Date" %>
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss" var="myDate"/> 

    


<!-- <link href="https://fonts.googleapis.com/css?family=Alegreya+Sans" rel="stylesheet"> -->
<link rel="stylesheet" href="assets/libs/bootstrap/css/bootstrap.min.css">
<link href="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.min.css" rel="stylesheet" type="text/css" />
<script	src="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<link href="assets/css/style-blue-theme-6.css" rel="stylesheet" type="text/css" />
<script>

$(document).ready(function() {
	$('.popular-box #news-carousel').owlCarousel({
		loop : true,
		margin : 10,
		animateOut : 'slideOutDown',
		animateIn : 'flipInX',
		 autoPlay:true,
		    autoPlayTimeout:4000,
		    autoplayHoverPause:true,

		items : 1,
		nav : true,
		responsive : {
			0 : {
				items : 1
			},
			600 : {
				items : 1
			},
			1000 : {
				items : 1
			}
		}
	})
});

$(document).ready(function(){
	//Owl Caraousel-----------------------------------------------

	$( "#photo-carosel .owl-next").html('<img alt="Image Next" src="https://d1ycj7j4cqq4r8.cloudfront.net/bbb447994b253bea1bb81b002e3413b2.svg" />');
		$( "#photo-carosel .owl-prev").html('<img alt="Image Prev" src="https://d1ycj7j4cqq4r8.cloudfront.net/20bd4ea61b53e89f4d8c6531d59f19ab.svg" />');

	$('#photo-carosel .owl-carousel').owlCarousel({
		autoPlay: 3000, //Set AutoPlay to 3 seconds
		items : 4,
		margin: 20,
		stagePadding: 50,
		nav: true,
		navText:["<div class='nav-btn prev-slide'></div>","<div class='nav-btn next-slide'></div>"]
		/* responsive: {
			0: {
				items: 1
			},
			600: {
				items: 3
			},
			1000: {
				items: 4
			}
		} */
	});
   
	$(".press-release").click(function(){
		   
	    var imageId = $(this).data('id');
	    var imageList=imageId.split(",");
	    $('div#press-modal-body-div > div').remove();	
	    $('div#press-modal-body-div > img').remove();
		for(i=0;i<imageList.length;i++){
			if(i==0){
				$("<div>", {
				    'class': "item active press-release-item"
				}).appendTo('.press-modal-body');					
			}
			else{
				$("<div>", {
				    'class': "item press-release-item"
				}).appendTo('.press-modal-body');
			}

			var img = $('<img alt="Image" class="img-responsive" />').attr({
	            'id': 'myImage'+i,
	            'src': './'+imageList[i]
	        }).appendTo('.press-release-item');
	    }
	    
     $('#pressreleasediv').modal('show');
   });
});


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
<c:if
	test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}">
	<script>
		$(function() {
			$('#myCarousel').css('margin-top', '0px');
		});
	</script>
</c:if>

${command.setThemeMap()} ${command.getAboutUs()}

<div class="invisibeHead"></div>
<div class="row">
	<!-- Header-3 starts -->
	<div class="header-3">
		<div class="scroll-to-div-nav col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<ul>
				<li class="smooth-scroll">
					<a href="#imp-notices-sect">
						<span class="sdn-text"><spring:message code="theme6.portal.important.notices" text="Important Notices" /></span>
					</a>
				</li>
				<li class="smooth-scroll">
					<a href="#press-sect">
						<span class="sdn-text"><spring:message code="theme6.portal.press.release" text="Press Releases" /></span>
					</a>
				</li>
				<li class="smooth-scroll">
					<a href="#ext-links-sect">
						<span class="sdn-text"><spring:message code="theme6.portal.external.links" text="External Links" /></span>
					</a>
				</li>
				<li class="smooth-scroll">
					<a href="#quot-sect">
						<span class="sdn-text"><spring:message code="theme6.portal.quotations" text="Quotations" /></span>
					</a>
				</li>
				<li class="smooth-scroll">
					<a href="#media-sect">
						<span class="sdn-text"><spring:message code="theme6.portal.media.gallery" text="Media Gallery" /></span>
					</a>
				</li>
				<%-- <li class="smooth-scroll">
					<a href="#cont-us-sect">
						<span class="sdn-text"><spring:message code="theme6.portal.contact.us" text="Contact Us" /></span>
					</a>
				</li> --%>
			</ul>
			
		</div>
	</div>
	<!-- Header-3 ends -->

	<div class="scroll-messages col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<%-- <c:if test="${command.themeMap['DISASTER_ALERT'] eq 'A' && command.themeMap['LIVE'] eq 'A'}">
			<!-- Disaster ALERT Marquee -->
			<div class="disaster-alert-container col-xs-12 col-sm-10 col-md-11 col-lg-11">
				<div class="da-title">
					<span class="message-title"><spring:message code="theme6.portal.disaster.alert" text="Disaster Alert" /></span>
				</div>
				<div class="marquee da-message">
					<c:forEach items="${command.getAllhtml('DISASTER ALERT')}" var="disasterMsg"> ${disasterMsg} </c:forEach>
				</div>
			</div>
			<!-- Disaster ALERT Marquee ends -->
			<!-- Live Section starts -->
			<div class="live-section col-xs-12 col-sm-2 col-md-1 col-lg-1">
				<a href="#">
					<span class="ls-text"><spring:message code="theme6.portal.live" text="LIVE" /></span>
					<i class="fa fa-circle" aria-hidden="true"></i>
				</a>
			</div>
			<!-- Live Section ends -->
		</c:if> --%>
		<c:if test="${command.themeMap['DISASTER-ALERT'] ne 'I'}">
			<!-- Disaster ALERT Marquee -->
			<div class="message-container disaster-alert-container">
				<div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
					<span class="message-title"><spring:message code="theme6.portal.disaster.alert" text="Disaster Alert" /></span>
				</div>
				<div class="marquee-outer col-xs-12 col-sm-9 col-md-10 col-lg-10">
					<div class="marquee">
						<c:forEach items="${command.getAllhtml('DISASTER-ALERT')}" var="disasterMsg"> ${disasterMsg} </c:forEach>
					</div>
				</div>
			</div>	
		</c:if>
		<%-- <c:if test="${command.themeMap['DISASTER_ALERT'] ne 'A' && command.themeMap['LIVE'] eq 'A'}">
			<!-- Live Section starts -->
			<div class="live-section col-xs-12 col-sm-1 col-md-1 col-lg-1">
				<a href="#">
					<span class="ls-text"><spring:message code="theme6.portal.live" text="LIVE" /></span>
					<i class="fa fa-circle" aria-hidden="true"></i>
				</a>
			</div>
			<!-- Live Section ends -->
		</c:if> --%>
		
		<c:if test="${command.themeMap['CIVIC-MESSAGE'] ne 'I'}">
			<!-- CIVIC MESSAGE Marquee -->
			<div class="message-container">
				<div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
					<span class="message-title">
						<spring:message code="theme6.portal.civic.message" text="Civic Message" />
					</span>
				</div>
				<div class="marquee-outer col-xs-12 col-sm-9 col-md-10 col-lg-10">
					<div class="marquee">
						<c:forEach items="${command.getAllhtml('CIVIC-MESSAGE')}" var="civicMsg"> ${civicMsg} </c:forEach>
					</div>
				</div>
			</div>
			<!-- CIVIC MESSAGE Marquee ends -->
		</c:if>
		
		<c:if test="${command.themeMap['LIVE'] ne 'I'}">
			<!-- Live Events Marquee -->
			<div class="live-events-container col-xs-12 col-sm-10 col-md-11 col-lg-11">
				<div class="le-title">
					<span class="message-title"><spring:message code="theme6.portal.live.events" text="Live Events" /></span>
				</div>
				<div class="marquee-outer le-message">
					<div class="marquee">
						<c:forEach items="${command.getAllhtml('LIVE EVENTS')}" var="liveEvents"> ${liveEvents} </c:forEach>
					</div>
				</div>
			</div>
			<!-- Live Events Marquee ends -->
			<!-- Live Button starts -->
			<div class="live-section col-xs-12 col-sm-2 col-md-1 col-lg-1">
				<a href="SectionInformation.html?editForm&rowId=&page=Live Event Details">
					<span class="ls-text"><spring:message code="theme6.portal.live" text="LIVE" /></span>
					<i class="fa fa-circle" aria-hidden="true"></i>
				</a>
			</div>
			<!-- Live Button ends -->
		</c:if>
	</div>
	
	<!-- Column 1 starts -->
	<div class="column-1 col-xs-12 col-sm-3 col-md-3 col-lg-3 col-sm-push-9 col-md-push-9 col-lg-push-9">
		 <div class="section-container citizen-services panel">
			<div class="mkpay">
				<div class="panel-heading">
					<h3><div class="image">
						<!-- <i class="fa fa-credit-card" aria-hidden="true"></i> -->
						<spring:message code="QuickPayment" text="Quick Payment" />
						<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
					</h3>
				</div>
			</div>
			<div class="panel-body">	
				<div id="mkpay">
					<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<div class="hidden-lg hidden-md hidden-sm"
							style="margin-top: 10px;"></div>
						<div class="content-tab">
							<div class="warning-div error-div alert alert-danger alert-dismissible" id="error-msg" style="display: none;"></div>
							<form class="form">
								<div class="form-group">
									<div class="col-sm-12"><label for="billtype"><spring:message code="BillType"
											text="Bill Type" /></label> <select class="form-control"
										name="bill-type" id="billtype" onchange="getPropDetails();">
										<option value=""> <spring:message code="selectdropdown" text="Select" /></option>
										<option value="PropertyBillPayment.html"><spring:message code="PropertyBill" text="Property Bill" /></option>
										<option value="WaterBillPayment.html"><spring:message code="WaterBill" text="Water Bill" /></option>
									</select>
									</div> 
									<div class="col-sm-12 propertyNo"><spring:message code="eg" var="placeholder4" />
									<label for="billNumber"> <spring:message
											code="ConsumerNumber" text="Consumer Number" /></label> <input
										type="text" class="form-control" name="referral"
										id="billNumber" placeholder="${placeholder4}">
									</div> 
								</div>
								<div class=" col-sm-12 text-right">
									<br />
									<button type="button" onclick="searchBillPay()"
										class="btn btn-danger margin-top-10 margin-bottom-10">
										<spring:message code="eip.page.process" text="Proceed" />
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		 <div class="section-container citizen-services panel">
			<div class="mkpay">
				<div class="panel-heading">
					<h3><div class="image">
						<!-- <i class="fa fa-credit-card" aria-hidden="true"></i> -->
						<spring:message code="receipt.Download" text="Receipt Download" />
						<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
					</h3>
				</div>
			</div>
			<div class="panel-body">	
				<div id="mkpay">
					<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<div class="hidden-lg hidden-md hidden-sm"
							style="margin-top: 10px;"></div>
						<div class="content-tab">
							<div class="warning-div error-div alert alert-danger alert-dismissible" id="receipt-error-msg" style="display: none;"></div>
							<form class="form">
								<div class="form-group">
									<div class="col-sm-12"><label for="billtype"><spring:message code="receipt.type"
											text="Receipt Type" /></label> <select class="form-control"
										name="bill-type" id="receiptType">
										<option value=""> <spring:message code="selectdropdown" text="Select" /></option>
										<option value="PropertyBillPayment.html"><spring:message code="PropertyBill" text="Property Bill" /></option>
										<option value="receiptApplication.html"><spring:message code="citizen.dashboard.applNo" text="Application No" /></option>
										<option value="WaterBillPayment.html"><spring:message code="WaterBill" text="Water Bill" /></option>
									</select>
									</div> 
								</div>
								<div class=" col-sm-12 text-right">
									<br />
									<button type="button" onclick="searchReceiptDetail()"
										class="btn btn-danger margin-top-10 margin-bottom-10">
										<spring:message code="eip.page.process" text="Proceed" />
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<c:if test="${command.themeMap['E_SERVICES'] ne 'I'}">
			<!-- E-Services starts -->
			<div class="section-container eserv-main panel">
				<div class="panel-heading">
					<h3><spring:message code="theme6.portal.eservices" text="E-Services" /></h3>
				</div>
				<div class="panel-body">
					<c:if test="${not empty command.themeMap['CITIZEN_SERVICES'] && command.themeMap['CITIZEN_SERVICES'] ne 'I'}">
						<c:set var="citizenService"
							value="${command.getAllDepartmentAndServices()}" />
						<c:if test="${citizenService.size()>0}">
							<div id="CitizenService">
								<div id="accordion">
									<%-- <div class="panel Services-tab">
										<a data-toggle="collapse" data-parent="#accordion" href="#billPayment">
											<span class="eserv-icon"><i class="fa fa-inr" aria-hidden="true"></i></span>
											<span class="eserv-text"><spring:message code="theme6.portal.bill.payment" text="Bill Payment" /></span>
										</a>
										<div id="billPayment" class="collapse eserv-list">
											<ul id="l1">
												<li>
													<a href="javascript:void(0);" onclick="getCitizenLoginForm('N')">
													<a href="https://www.kdmc.gov.in/RtsPortal/BillPayment.html" target="_blank">
														<i class="fa fa-chevron-right"></i>
														<spring:message code="theme6.portal.pay.property.and.water.bill" text="Pay Property And Water Bill" />
													</a>
												</li>
												<li>
													<a data-toggle="collapse" data-parent="#l1" href="#billDetails">
														<i class="fa fa-chevron-right"></i>
														<spring:message code="theme6.portal.payment.details" text="Payment Details" />
													</a>
													<ul id="billDetails" class="collapse">
														<li>
															<a href="javascript:void(0);" onclick="getCitizenLoginForm('N')">
															<a href="https://www.kdmc.gov.in/RtsPortal/BillPrint.html" target="_blank"> 
																<i class="fa fa-chevron-right"></i>
																<spring:message code="Property" text="Property" />
															</a>
														</li>
														<li>
															<a href="javascript:void(0);" onclick="getCitizenLoginForm('N')">
															<a href="https://www.kdmc.gov.in/RtsPortal/WaterBillPrintHome.html" target="_blank">
																<i class="fa fa-chevron-right"></i>
																<spring:message code="Water" text="Water" />
															</a>
														</li>
													</ul>
												</li>
											</ul>
										</div>
									</div>
									<div class="panel Services-tab">
										<a href="javascript:void(0);"
														onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
											<span class="eserv-icon"><span class="home-icon es-icon"></span></span>
											<span class="eserv-text"><spring:message code="theme6.portal.assessment.department" text="Assessment Department" /></span>
										</a>
										<div id="assessmentDept" class="collapse eserv-list">
											<ul>
												<li>
													<a href="javascript:void(0);" onclick="getCitizenLoginForm('N')">
													<a href="<spring:message code="theme6.portal.e.services.redirect.link" text="Redirected to portal" />" target="_blank">
														<i class="fa fa-chevron-right"></i>
														<spring:message code="theme6.portal.rts.and.citizen.services.login" text="RTS & Citizen Services Login" />
													</a>
												</li>
											</ul>
										</div>
									</div>
									<div class="panel Services-tab">
										<a href="javascript:void(0);"
														onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
											<span class="eserv-icon"><i class="fa fa-tint" aria-hidden="true"></i></span>
											<span class="eserv-text"><spring:message code="theme6.portal.water.department" text="Water Department" /></span>
										</a>
										<div id="waterDept" class="collapse eserv-list">
											<ul>
												<li>
													<a href="javascript:void(0);" onclick="getCitizenLoginForm('N')" >
													<a href="<spring:message code="theme6.portal.e.services.redirect.link" text="Redirected to portal" />" target="_blank">
														<i class="fa fa-chevron-right"></i>
														<spring:message code="theme6.portal.rts.and.citizen.services.login" text="RTS & Citizen Services Login" />
													</a>
												</li>
											</ul>
										</div>
									</div>
									<div class="panel Services-tab">
										<a href="javascript:void(0);"
														onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
											<span class="eserv-icon"><i class="fa fa-credit-card" aria-hidden="true"></i></span>
											<span class="eserv-text"><spring:message code="theme6.portal.market.department" text="Market Department" /></span>
										</a>
										<div id="marketDept" class="collapse eserv-list">
											<ul>
												<li>
													<a href="javascript:void(0);" onclick="getCitizenLoginForm('N')" >
													<a href="<spring:message code="theme6.portal.e.services.redirect.link" text="Redirected to portal" />" target="_blank">
														<i class="fa fa-chevron-right"></i>
														<spring:message code="theme6.portal.rts.and.citizen.services.login" text="RTS & Citizen Services Login" />
													</a>
												</li>
											</ul>
										</div>
									</div>
									<div class="panel Services-tab">
										<a href="javascript:void(0);"
														onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
											<span class="eserv-icon"><span class="health-department-icon es-icon"></span></span>
											<span class="eserv-text"><spring:message code="theme6.portal.health.department" text="Health Department" /></span>
										</a>
										<div id="healthDept" class="collapse eserv-list">
											<ul>
												<li>
													<a href="javascript:void(0);" onclick="getCitizenLoginForm('N')">
													<a href="<spring:message code="theme6.portal.e.services.redirect.link" text="Redirected to portal" />" target="_blank">
														<i class="fa fa-chevron-right"></i>
														<spring:message code="theme6.portal.rts.and.citizen.services.login" text="RTS & Citizen Services Login" />
													</a>
												</li>
											</ul>
										</div>
									</div>
									<div class="panel Services-tab">
										<a data-toggle="collapse" data-parent="#accordion" href="#onlineComplaints">
											<span class="eserv-icon"><span class="online-complaints-icon es-icon"></span></span>
											<span class="eserv-text"><spring:message code="theme6.portal.online.complaints" text="Online Complaints" /></span>
										</a>
										<div id="onlineComplaints" class="collapse eserv-list">
											<ul>
												<li>
													<a href="javascript:void(0);" onclick="getCitizenLoginForm('N')">
													<a href="<spring:message code="theme6.portal.online.complaints.redirect.link" text="Redirected to portal (Online Complaint)" />" target="_blank">
														<i class="fa fa-chevron-right"></i>
														<spring:message code="theme6.portal.rts.and.citizen.services.login" text="RTS & Citizen Services Login" />
													</a>
												</li>
											</ul>
										</div>
									</div>
									<div class="panel Services-tab">
										<a href="javascript:void(0);"
														onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
											<span class="eserv-icon"><span class="marriage-department-icon es-icon"></span></span>
											<span class="eserv-text"><spring:message code="theme6.portal.marriage.department" text="Marriage Department" /></span>
										</a>
										<div id="marriageDept" class="collapse eserv-list">
											<ul>
												<li>
													<a href="javascript:void(0);" onclick="getCitizenLoginForm('N')">
													<a href="<spring:message code="theme6.portal.e.services.redirect.link" text="Redirected to portal" />" target="_blank">
														<i class="fa fa-chevron-right"></i>
														<spring:message code="theme6.portal.rts.and.citizen.services.login" text="RTS & Citizen Services Login" />
													</a>
												</li>
											</ul>
										</div>
									</div> --%>
									<c:forEach items="${command.getAllDepartmentAndServices()}"
										var="dept" varStatus="index">
										<div class="panel Services-tab">
											<a data-toggle="collapse" data-parent="#accordion" href="#${dept.departmentCode}">
												<span class="eserv-icon"><i class="fa fa-list_${dept.departmentCode}"></i></span>
												<span class="eserv-text">${dept.departmentName}</span>
											</a>
											<div id="${dept.departmentCode}" class="collapse eserv-list">
												<ul>
													<c:forEach items="${dept.childDTO}" var="serv"
													varStatus="index">
													<li><a href="javascript:void(0);"
														onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
														<i class="fa fa-chevron-right"></i>  ${serv.serviceName}</a></li>
													</c:forEach>
													<%-- Code to display non service link in Compl service Start --%>
													<c:if test="${dept.departmentCode eq 'CFC'}">
														<li><a href="<spring:message code="grievance.cs_complaint_vps_link" text="grievance.html" />" 
														class="extlink internal"><i class="fa fa-chevron-right"></i>
														 <spring:message code="grievance.cs_complaint_vps" text="File Complaint" /></a></li>
													     
													     <li><a href="<spring:message code="grievance.cs_complaint_status_link" text="grievance.html?complaintRegistrationStatus" />" 
														class="extlink internal"><i class="fa fa-chevron-right"></i>
														 <spring:message code="grievance.cs_complaint_status" text="Complaint Status" /></a></li>
													</c:if>
													<%-- Code to display non service link in Compl service End --%>
												</ul>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</c:if>
					</c:if>
				</div>
			</div>
			<!-- E-Services ends -->
		</c:if>
		
		<c:if test="${command.themeMap['ON_GOING_PROJECTS'] ne 'I'}">
			<!-- On-Going Projects starts -->
			<div class="section-container ogp-main panel" id="quot-sect">
				<div class="panel-heading">
					<h3><spring:message code="theme6.portal.on.going.projects" text="On-Going Projects" /></h3>
				</div>
				<div class="panel-body">
					<c:if test="${command.highlighted eq true}">
						<c:set var="serial_count" scope="page" value="0" />
						<ul>
						<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
							<fmt:formatDate value="${lookUp.issueDate}" pattern="yyyy-MM-dd HH:mm:ss" var="iDate"/> 
							<fmt:formatDate value="${lookUp.validityDate}" pattern="yyyy-MM-dd HH:mm:ss" var="vDate"/>
							<fmt:formatDate value="${lookUp.updatedDate}" pattern="yyyy-MM-dd HH:mm:ss" var="uDate"/> 
							<c:if test="${lookUp.isUsefullLink eq 'P' && serial_count < 7 &&  iDate le myDate &&  myDate le vDate}">
								<li>
									<%-- <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
									<span class="serialno-new">${serial_count+1}</span>
									<c:set var="serial_count" scope="page"
										value="${serial_count+1}" />
									</div> --%>
									<c:set var="serial_count" scope="page" value="${serial_count+1}" />
										<c:set var="link"
											value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
										<c:choose>
											<c:when test="${isDMS}">
												<c:choose>
													<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
														<a href="javascript:void(0);"
															onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.noticeSubEn}</a>
													</c:when>
													<c:otherwise>
														<a href="javascript:void(0);"
															onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.noticeSubReg}</a>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
															${lookUp.noticeSubEn}
															<c:if test="${lookUp.isHighlighted eq 'Y'}">
																<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
															</c:if>
														</div>
														<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
															<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
															<c:set var="exlink" value="${lookUp.link}" />
															<c:forEach items="${links}" var="downloadL" varStatus="count">
																<c:set var="lnk1" value="./cache/${downloadL}" /> 
																<c:set var="link" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
                                          								<c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
                                                                
                                                                   <c:if test="${lookUp.linkType eq 'R' }">
							                                             <apptags:filedownload filename="${link}" 
							                                                filePath="${path}" showIcon="true" docImage="true"
							                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
																   </c:if>
															</c:forEach>
															<c:if test="${not empty exlink && lookUp.linkType ne 'R'}">
																<a href="${exlink}" target="_blank" class=""><span class="external_link_icon"></span></a>
															</c:if>
														</div>
													</c:when>
													<c:otherwise>
														<div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
															${lookUp.noticeSubReg}
															<c:if test="${lookUp.isHighlighted eq 'Y'}">
																<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
															</c:if>
														</div>
														<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
															<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
															<c:set var="exlink" value="${lookUp.link}" />
															<c:forEach items="${links}" var="downloadL" varStatus="count">
																<c:set var="lnk1" value="./cache/${downloadL}" /> 
																<c:set var="link" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
                                          								<c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
                                                                   <c:if test="${lookUp.linkType eq 'R' }">
							                                             <apptags:filedownload filename="${link}"
							                                                filePath="${path}" showIcon="true" docImage="true" 
							                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
																   </c:if>
															</c:forEach>
															<c:if test="${not empty exlink && lookUp.linkType ne 'R'}">
																<a href="${exlink}" target="_blank" class=""><span class="external_link_icon"></span></a>
															</c:if>
														</div>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
										<input type="hidden" name="downloadLink"
											value="${lookUp.profileImgPath}">

								</li>
							</c:if>
						</c:forEach>
						</ul>
					</c:if>
				</div>
				<div class="panel-btn">
					<a href="CitizenHome.html?onGoingProjects" class="vm-btn" title="View More">
						<spring:message code="portal.link.viewmore" text="VIEW MORE" />
					</a>
				</div>
			</div>
			<!-- On-Going Projects ends -->
		</c:if>
		
		<c:if test="${command.themeMap['QUOTATIONS'] ne 'I'}">
			<!-- Quotations starts -->
			<div class="section-container quotations-main panel" id="quot-sect">
				<div class="panel-heading">
					<h3><spring:message code="theme6.portal.quotations" text="Quotations" /></h3>
				</div>
				<div class="panel-body">
					<c:if test="${command.highlighted eq true}">
						<c:set var="serial_count" scope="page" value="0" />
						<ul>
						<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
							<fmt:formatDate value="${lookUp.issueDate}" pattern="yyyy-MM-dd HH:mm:ss" var="iDate"/> 
							<fmt:formatDate value="${lookUp.validityDate}" pattern="yyyy-MM-dd HH:mm:ss" var="vDate"/> 
							<fmt:formatDate value="${lookUp.updatedDate}" pattern="yyyy-MM-dd HH:mm:ss" var="uDate"/>
							<c:if test="${lookUp.isUsefullLink eq 'Q' && serial_count < 7 &&  iDate le myDate &&  myDate le vDate}">
								<li>
									<%-- <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
									<span class="serialno-new">${serial_count+1}</span>
									<c:set var="serial_count" scope="page"
										value="${serial_count+1}" />
									</div> --%>
									<c:set var="serial_count" scope="page" value="${serial_count+1}" />
										<c:set var="link"
											value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
										<c:choose>
											<c:when test="${isDMS}">
												<c:choose>
													<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
														<a href="javascript:void(0);"
															onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.noticeSubEn}</a>
													</c:when>
													<c:otherwise>
														<a href="javascript:void(0);"
															onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.noticeSubReg}</a>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
															${lookUp.noticeSubEn}
															<c:if test="${lookUp.isHighlighted eq 'Y'}">
																<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
															</c:if>
														</div>
														<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
															<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
															<c:set var="exlink" value="${lookUp.link}" />
															<c:forEach items="${links}" var="downloadL" varStatus="count">
													
																<c:set var="lnk1" value="./cache/${downloadL}" /> 
																<c:set var="link" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
                                          								<c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
                                                                       <c:if test="${lookUp.linkType eq 'R' }">
							                                             <apptags:filedownload filename="${link}"
							                                                filePath="${path}" showIcon="true" docImage="true" 
							                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
																       </c:if>
															</c:forEach>
															<c:if test="${not empty exlink && lookUp.linkType ne 'R'}">
																<a href="${exlink}" target="_blank" class=""><span class="external_link_icon"></span></a>
															</c:if>
														</div>
													</c:when>
													<c:otherwise>
														<div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
															${lookUp.noticeSubReg}
															<c:if test="${lookUp.isHighlighted eq 'Y'}">
																<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
															</c:if>
														</div>
														<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
															<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
															<c:set var="exlink" value="${lookUp.link}" />
															<c:forEach items="${links}" var="downloadL" varStatus="count">
																<c:set var="lnk1" value="./cache/${downloadL}" /> 
																<c:set var="link" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
                                          								<c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
                                                                       <c:if test="${lookUp.linkType eq 'R' }">
							                                             <apptags:filedownload filename="${link}" 
							                                                filePath="${path}" showIcon="true" docImage="true"
							                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
																       </c:if>
															</c:forEach>
															<c:if test="${not empty exlink && lookUp.linkType ne 'R'}">
																<a href="${exlink}" target="_blank" class=""><span class="external_link_icon"></span></a>
															</c:if>
														</div>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
										<input type="hidden" name="downloadLink"
											value="${lookUp.profileImgPath}">

								</li>
							</c:if>
						</c:forEach>
						</ul>
					</c:if>
				</div>
				<div class="panel-btn">
					<a href="CitizenHome.html?getQuotationNotices" class="vm-btn" title="View More">
						<spring:message code="portal.link.viewmore" text="VIEW MORE" />
					</a>
				</div>
			</div>
			<!-- Quotations ends -->
		</c:if>
		
		<c:if test="${command.themeMap['MOBILE_APPS'] ne 'I'}">
			<!-- KDMC Mobile Apps starts -->
			<div class="section-container mob-apps-main panel">
				<div class="panel-heading">
					<h3><spring:message code="theme6.portal.kdmc.mobile.apps" text="KDMC Mobile Apps" /></h3>
				</div>
				<div class="panel-body">
					<c:forEach items="${command.getAllhtml('MOBILE APPS')}" var="mobileApps"> ${mobileApps} </c:forEach>
				</div>
			</div>
			<!-- KDMC Mobile Apps ends -->
		</c:if>
	</div>
	<!-- Column 1 ends -->
	
	<!-- Column 2 starts -->
	<div class="column-2 col-xs-12 col-sm-3 col-md-3 col-lg-3 col-sm-pull-3 col-md-pull-3 col-lg-pull-3">
		<c:if test="${command.themeMap['COMMITTEE_MEMBERS'] ne 'I'}">
			<!-- Member Profiles starts -->
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
			<ul class="member-profiles">
				<c:if test="${command.themeMap['MAYOR'] ne 'I'}">
					<c:if test="${not empty sessionScope.mayorProfile}">
						<li>
							<div class="mp-section">
								<div class="member-profile-image">
									<img src="${sessionScope.mayorprofileImage}"
										alt="${sessionScope.mayorName}"
										title="${sessionScope.mayorName}" class="img-responsive" width="100">
								</div>
								<div class="member-profile-info">
								<c:if test="${not empty  sessionScope.mayorName }">
									<p class="member-name">${sessionScope.mayorName}</p>
								</c:if>
								<c:if test="${not empty sessionScope.mayorProfile }">
									<p class="member-designation">${sessionScope.mayorProfile}</p>
								</c:if>
								<c:if test="${not empty sessionScope.SummaryEng }">
									<p class="member-phone-no">
										<c:choose>
											<c:when test="${userSession.languageId eq 1}">
												<a href="tel:${sessionScope.SummaryEng}">${sessionScope.SummaryEng}</a>
											</c:when>
											<c:otherwise>
												<a href="tel:${marathiConvert.convertToRegional('marathi',sessionScope.SummaryEng)}">
													${marathiConvert.convertToRegional("marathi",sessionScope.SummaryEng)}
												</a>
											</c:otherwise>
										</c:choose>
									</p>
								</c:if>	
									<%-- <p class="member-doj">
										${fn:substring(sessionScope.mayorDtOfJoin, 0,11)}
									</p> --%>
								</div>
								<div class="read-more-link">
									 <a href="CitizenHome.html?OfficerProfile&profileType=Mayor">
										<spring:message code="ReadMore" text="Read More" />
									</a>
								</div>
							</div>
						</li>
					</c:if>
				</c:if>
				
				<c:if test="${command.themeMap['COMMISSIONER'] ne 'I'}">
					<c:if test="${not empty sessionScope.deputyMayorProfile}">
						<li>
							<div class="mp-section">
								<div class="member-profile-image">
									<img src="${sessionScope.deputyMayorProfileImage}"
										alt="${sessionScope.deputyMayorName}"
										title="${sessionScope.deputyMayorName}"
										class="img-responsive" width="100">
								</div>
								<div class="member-profile-info">
								<c:if test="${not empty  sessionScope.deputyMayorName }">
									<p class="member-name">${sessionScope.deputyMayorName}</p>
								</c:if>	
								<c:if test="${not empty  sessionScope.deputyMayorProfile }">
									<p class="member-designation">${sessionScope.deputyMayorProfile}</p>
								</c:if>
								<c:if test="${not empty  sessionScope.deputyMayorSummaryEng }">	
									<p class="member-phone-no">
										<c:choose>
											<c:when test="${userSession.languageId eq 1}">
												<a href="tel:${sessionScope.deputyMayorSummaryEng}">${sessionScope.deputyMayorSummaryEng}</a>
											</c:when>
											<c:otherwise>
												<a href="tel:${marathiConvert.convertToRegional('marathi',deputyMayorSummaryEng)}">
													${marathiConvert.convertToRegional("marathi",deputyMayorSummaryEng)}
												</a>
											</c:otherwise>
										</c:choose>
									</p>
								</c:if>	
									<%-- <p class="member-doj">
										${fn:substring(sessionScope.deputyMayorDtOfJoin, 0,11)}
									</p> --%>
								</div>
								<div class="read-more-link">
									<a href="CitizenHome.html?OfficerProfile&profileType=Commissioner">
										<spring:message code="ReadMore" text="Read More" />
									</a>
								</div>
							</div>
						</li>
					</c:if>
				</c:if>
				
				<%-- <c:if test="${not empty sessionScope.commissionerProfile}">
					<li>
						<div class="mp-section">
							<div class="member-profile-image">
								<img src="${sessionScope.commissionerProfileImage}"
									alt="${sessionScope.commissionerName}"
									title="${sessionScope.commissionerName}"
									class="img-responsive">
							</div>
							<div class="member-profile-info">
								<p class="member-name">${sessionScope.commissionerName}</p>
								<p class="member-designation">${sessionScope.commissionerProfile}</p>
								<p class="member-phone-no">
									<a href="tel:${sessionScope.commissionerSummaryEng}">${sessionScope.commissionerSummaryEng}</a>
								</p>
								<p class="member-doj">
									${fn:substring(sessionScope.commissionerDtOfJoin, 0,11)}
								</p>
							</div>
						</div>
					</li>
				</c:if>
				
				<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()!='Y'}">
					<c:if test="${not empty sessionScope.deputycommissionerProfile}">
						<li>
							<div class="mp-section">
								<div class="member-profile-image">
									<img src="${sessionScope.deputycommissionerProfileImage}"
										alt="${sessionScope.deputyCommissionerName}"
										title="${sessionScope.deputyCommissionerName}"
										class="img-responsive">
								</div>
								<div class="member-profile-info">
									<p class="member-name">${sessionScope.deputyCommissionerName}</p>
									<p class="member-designation">${sessionScope.deputycommissionerProfile}</p>
									<p class="member-phone-no">
										<a href="tel:${sessionScope.deputyMayorSummaryEng}">${sessionScope.deputyMayorSummaryEng}</a>
									</p>
									<p class="member-doj">
										${fn:substring(sessionScope.deputycommissionerDtOfJoin, 0,11)}
									</p>
								</div>
							</div>
						</li>
					</c:if>
				</c:if> --%>
					
				<%-- <c:if test="${empty sessionScope.mayorProfile}">
					<li>
						<div class="mp-section">
							<div class="member-profile-image">
								<img src="assets/img/Hon'ble-Minister.jpg"
									alt="Hon'ble-Minister" title="Hon'ble-Minister"
									class="img-responsive">
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${empty sessionScope.deputyMayorProfile}">
					<li>
						<div class="mp-section">
							<div class="member-profile-image">
								<img src="assets/img/Hon'ble-Minister.jpg"
									alt="Hon'ble-Minister" title="Hon'ble-Minister"
									class="img-responsive">
							</div>
						</div>
					</li>
				</c:if> --%>
				<%-- <c:if test="${empty sessionScope.commissionerProfile}">
					<li>
						<div class="mp-section">
							<div class="member-profile-image">
								<img src="assets/img/Hon'ble-Minister.jpg"
									alt="Hon'ble-Minister" title="Hon'ble-Minister"
									class="img-responsive">
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()!='Y'}">
					<c:if test="${empty sessionScope.deputycommissionerProfile}">
						<li>
							<div class="mp-section">
								<div class="member-profile-image">
									<img src="assets/img/Hon'ble-Minister.jpg"
										alt="Hon'ble-Minister" title="Hon'ble-Minister"
										class="img-responsive">
								</div>
							</div>
						</li>
					</c:if>
				</c:if> --%>
			</ul>
			<!-- Member Profiles ends -->
		</c:if>
		
		<c:if test="${command.themeMap['ORGANIZATION_DROPDOWN'] ne 'I'}">
			<!-- Dropdown Selection starts -->
			<div class="dropdown-selection-section">
				<div class="form-group">
					<form:form name="selectMunicipalForm" id="selectMunicipalForm"
						action="" class="list" method="get">
						<div class="input-group">
							<%-- <label class="hidden" for="selectedOrg">Select Department</label> --%>
							<select name="orgId" id="selectedOrg"
								data-content="" class="form-control chosen-select-no-results">
								<%-- <option value="-1" selected style="display: none;"><spring:message code="Select" text="Select" /></option> --%>
								<c:forEach items="${command.userSession.organisationsList}"
									var="orglist">
									<optgroup label="${orglist.key}">
										<c:forEach items="${orglist.value}" var="org">
										<c:if test ="${ org.orgShortNm eq 'SKDCL'}">
											<c:if test="${userSession.languageId eq 1}">
												<option value="${org.orgid}">${org.ONlsOrgname}</option>
											</c:if>
											<c:if test="${userSession.languageId eq 2}">
												<option value="${org.orgid}">${org.ONlsOrgnameMar}</option>
											</c:if>
											</c:if>
										</c:forEach>
									</optgroup>
									
								</c:forEach>
							</select>
							<div class="input-group-btn">
								<button type="button" id="submitMunci" class="btn input-group-text">
									<spring:message code="theme6.portal.go" text="GO" />
								</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
			<!-- Dropdown Selection ends -->
		</c:if>
		
		<c:if test="${command.themeMap['CONTACT_US'] ne 'I'}">
			<!-- Contact Us starts -->
			<div class="section-container cont-us-main panel" id="cont-us-sect">
				<div class="panel-heading">
					<h3><spring:message code="theme6.portal.contact.us" text="Contact Us" /></h3>
				</div>
				<div class="panel-body">
					<ul>
						<c:set var="serial_count" value="0" scope="page" />
						<c:forEach items="${command.organisationContactList }" var="contact">
							<c:if test="${serial_count <= 5}">
								<c:set var="serial_count" value="${serial_count+1}"  />
								<c:if test ="${contact.flag eq 'P' }">
									<li>
										<c:if test="${ contact.designationEn ne 'P.S. To Minister'}">
											<p class="cont-name">
												<c:choose>
													<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
														<strong>${contact.contactNameEn}</strong>
														${contact.designationEn}
													</c:when>
													<c:otherwise>
														<strong>${contact.contactNameReg}</strong>
														${contact.designationReg}
													</c:otherwise>
												</c:choose>
											</p>
										 <c:if test="${not empty contact.telephoneNo1En }">
											<p class="cont-ph-number">
												<span class="cont-icon">
													<span class="phone_icon"></span>
												</span>
												<c:choose>
													<c:when test="${userSession.languageId eq 1}">
														<span class="cont-detail"><a href="tel:${contact.telephoneNo1En}">${contact.telephoneNo1En}</a></span>
													</c:when>
													<c:otherwise>
														<span class="cont-detail">
															<a href="tel:${marathiConvert.convertToRegional('marathi',contact.telephoneNo1En)}">
																${marathiConvert.convertToRegional("marathi",contact.telephoneNo1En)}
															</a>
														</span>
													</c:otherwise>
												</c:choose>
											</p>	
										</c:if>
											<%-- <p class="cont-mob-number">
												<span class="cont-icon">
													<i class="fa fa-mobile" aria-hidden="true"></i>
												</span>
												<span class="cont-detail"><a href="tel:${contact.telephoneNo2En}">${contact.telephoneNo2En}</a></span>
											</p> --%>	
										 <c:if test="${not empty contact.email1 }">
											<p class="cont-email">
												<span class="cont-icon">
													<span class="message_icon"></span>
												</span>
												<span class="cont-detail">
													<a href="mailto:${contact.email1}" title="${contact.email1}">
														${contact.email1}
													</a>
												</span>
											</p>
										</c:if>
										</c:if>
									</li>
								</c:if>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				<div class="panel-btn">
					<a href="CitizenHome.html?contactUs" class="vm-btn" title="View More">
						<spring:message code="portal.link.viewmore" text="VIEW MORE" />
					</a>
				</div>
			</div>
			<!-- Contact Us ends -->
		</c:if>
		
		<c:if test="${command.themeMap['HELPLINE_NUMBERS'] ne 'I'}">
			<!-- Helpline Numbers starts -->
			<div class="section-container helpline-main-section panel">
				<div class="panel-heading">
					<h3><spring:message code="theme6.portal.helpline.numbers" text="Helpline Numbers"/></h3>
				</div>
				<div class="panel-body">
					<ul>
						<li>
							<div class="helpline-icon-container">
								<span class="helpline-icon">
									<span class="phone_icon"></span>
								</span>
							</div>
							<div class="helpline-num">
								<span class="helpline-info"><spring:message code="theme6.portal.helpline.label1" text="Kalyan"/></span>
								<span class="helpline-detail">
									<a href="tel:<spring:message code="theme6.portal.helpline.num1" text="0251-2211373"/>"><spring:message code="theme6.portal.helpline.num1" text="0251-2211373"/></a>
								</span>
							</div>
						</li>
						<li>
							<div class="helpline-icon-container">
								<span class="helpline-icon">
									<span class="phone_icon"></span>
								</span>
							</div>
							<div class="helpline-num">
								<span class="helpline-info"><spring:message code="theme6.portal.helpline.label2" text="Dombivli"/></span>
								<span class="helpline-detail">
									<a href="tel:<spring:message code="theme6.portal.helpline.num2" text="0251-2443800"/>"><spring:message code="theme6.portal.helpline.num2" text="0251-2443800"/></a>
								</span>
							</div>
						</li>
						<li>
							<div class="helpline-icon-container">
								<span class="helpline-icon">
									<span class="phone_icon"></span>
								</span>
							</div>
							<div class="helpline-num">
								<span class="helpline-info"><spring:message code="theme6.portal.helpline.label3" text="Enquiry/Complaint Registration On Cleaning Of Septic Tank/Sewer"/></span>
								<span class="helpline-detail">
									<a href="tel:<spring:message code="theme6.portal.helpline.num3" text="14420"/>"><spring:message code="theme6.portal.helpline.num3" text="14420"/></a>
								</span>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<!-- Helpline Numbers ends -->
		</c:if>
	</div>
	<!-- Column 2 ends -->
	
	<!-- Column 3 starts -->
	<div class="column-3 col-xs-12 col-sm-6 col-md-6 col-lg-6 col-sm-pull-3 col-md-pull-3 col-lg-pull-3">
		<c:if test="${command.themeMap['CORONA-ALERT'] ne 'I'}">
			<div class="scroll-messages corona-container col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<!-- CORONA ALERT Marquee -->
				<div class="message-container">
					<div class="corona-title-container">
						<span class="message-title">
							<spring:message code="theme6.portal.corona.alert" text="Corona Alert" />
						</span>
					</div>
					<div class="list-wrpaaer marquee2 col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<ul class="list-aggregate" id="marquee-horizontal">
							 <c:forEach items="${command.getAllhtml('CORONA-ALERT')}" var="coronaMsg"><li class="fat-l"> ${coronaMsg}</li> </c:forEach>
						</ul>
					</div>
				</div>
				<!-- CORONA ALERT Marquee ends -->
			</div>
		</c:if>
		
		<c:if test="${command.themeMap['SLIDER_IMG'] ne 'I'}">
			<!-- Carousel Slider starts -->
			<div id="myCarousel" class="slider-main carousel slide" data-ride="carousel">
				<c:set var="maxfilecount" value="${userSession.getSlidingImgLookUps().size()}" />
				<!-- Indicators -->
				<ol class="carousel-indicators">
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
				<div id="carouselButtons" class="carouselButtons-slider">
				    <button id="playButton" type="button" class="btn btn-default btn-xs hide">
				        <span class="glyphicon glyphicon-play"></span>
				     </button>
				    <button id="pauseButton" type="button" class="btn btn-default btn-xs">
				        <span class="glyphicon glyphicon-pause"></span>
				    </button>
				</div>
				<!-- Wrapper for slides -->
				<div class="carousel-inner">
					<c:if test="${userSession.getSlidingImgLookUps().size() gt 0}">
						<c:forEach items="${userSession.slidingImgLookUps}" var="slide"
							varStatus="status">

							<c:set var="data" value="${fn:split(slide,'*')}" />
							<c:set var="image" value="${data[0]}" />
							<c:choose>
								<c:when
									test="${userSession.getCurrent().getLanguageId() eq 1}">
									<c:set var="caption" value="${data[1]}" />
								</c:when>
								<c:otherwise>
									<c:set var="caption" value="${data[2]}" />
								</c:otherwise>
							</c:choose>
							<c:if test="${status.index eq 0 }">
								<div class="item active">
									<img src="./${image}" alt="${caption}" width="400" />
									<%-- <c:if test="${not empty caption}">
										<div class="container hidden-xs">
											<div class="carousel-caption animate fadeInLeft">
												<h1>${caption}</h1>
											</div>
										</div>
									</c:if> --%>
								</div>
							</c:if>
							<c:if test="${status.index ne 0 }">
								<div class="item active">
									<img src="./${image}" alt="${caption}" width="400" />
									<%-- <c:if test="${not empty caption}">
										<div class="container hidden-xs">
											<div class="carousel-caption animate fadeInLeft">
												<h1>${caption}</h1>
											</div>
										</div>
									</c:if> --%>
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
			<!-- Carousel Slider ends -->
		</c:if>
		<!-- CITIZEN SERVICES STARTS -->
		<%-- <div class="section-container citizen-services panel">
			<div class="mkpay">
				<div class="panel-heading">
					<h3><div class="image">
						<i class="fa fa-credit-card" aria-hidden="true"></i>
						<spring:message code="QuickPayment" text="Quick Payment" />
					</h3>
				</div>
			</div>
			<div class="panel-body">	
				<div id="mkpay">
					<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<div class="hidden-lg hidden-md hidden-sm"
							style="margin-top: 10px;"></div>
						<div class="content-tab">
							<form class="form">
								<div class="form-group">
									<div class="col-sm-6"><label for="billtype"><spring:message code="BillType"
											text="Bill Type" /></label> <select class="form-control"
										name="bill-type" id="billtype" onchange="getPropDetails();">
										<option> <spring:message code="selectdropdown" text="Select" /></option>
										<option value="PropertyBillPayment.html"><spring:message code="PropertyBill" text="Property Bill" /></option>
										<option value="WaterBillPayment.html"><spring:message code="WaterBill" text="Water Bill" /></option>
									</select>
									</div> 
									<div class="col-sm-6 propertyNo"><spring:message code="eg" var="placeholder4" />
									<label for="billNumber"> <spring:message
											code="ConsumerNumber" text="Consumer Number" /></label> <input
										type="text" class="form-control" name="referral"
										id="billNumber" placeholder="${placeholder4}">
									</div> 
								</div>
								<div class="text-center">
									<br />
									<button type="button" onclick="searchBillPay()"
										class="btn btn-success margin-top-10 margin-bottom-10" style="width: 150px">
										<spring:message code="eip.page.process" text="Proceed" />
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div> --%>
		<!-- CITIZEN SERVICES ENDS -->
		<c:if test="${command.themeMap['IMPORTANT_NOTICES'] ne 'I'}">
			<!-- Important Notices starts -->
			<div class="section-container imp-notices-main panel" id="imp-notices-sect">
				<div class="panel-heading">
					<h3><spring:message code="theme6.portal.important.notices" text="Important Notices" /></h3>
					<a href="CitizenHome.html?impNotices" class="vm-btn" title="View More">
						<spring:message code="portal.link.viewmore" text="VIEW MORE" />
					</a>
					<%-- <div class="section-icon">
						<i class="fa fa-bullhorn" aria-hidden="true"></i>
					</div> --%>
				</div>
				<div class="panel-body">
					<c:if test="${command.highlighted eq true}">
						<c:set var="serial_count" scope="page" value="0" />
						<ul>
						<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
							<fmt:formatDate value="${lookUp.issueDate}" pattern="yyyy-MM-dd HH:mm:ss" var="iDate"/> 
							<fmt:formatDate value="${lookUp.validityDate}" pattern="yyyy-MM-dd HH:mm:ss" var="vDate"/>
							<fmt:formatDate value="${lookUp.updatedDate}" var="updatedDate" type="date" pattern="dd-MM-yyyy" />
							<%-- <c:if test="${serial_count < 7 && lookUp.isUsefullLink ne 'T' && lookUp.isUsefullLink ne 'Q' && lookUp.isUsefullLink ne 'P' &&  iDate le myDate &&  myDate le vDate}"> --%>
								<c:if test="${serial_count < 7 && lookUp.isUsefullLink eq 'I'  &&  iDate le myDate &&  myDate le vDate}">
								<li>
									<%-- <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
									<span class="serialno-new">${serial_count+1}</span>
									<c:set var="serial_count" scope="page"
										value="${serial_count+1}" />
									</div> --%>
									<c:set var="serial_count" scope="page" value="${serial_count+1}" />
										<c:set var="link"
											value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
										<c:choose>
											<c:when test="${isDMS}">
												<c:choose>
													<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
														<a href="javascript:void(0);"
															onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.noticeSubEn}</a>
													</c:when>
													<c:otherwise>
														<a href="javascript:void(0);"
															onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.noticeSubReg}</a>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
															${lookUp.noticeSubEn}
															<c:if test="${lookUp.isHighlighted eq 'Y'}">
																<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
															</c:if>
															<h7 style="display: block; text-align: left; font-size: 10px;"><spring:message code="important.notice.updated.date" text="Last Updated Date" />&nbsp; ${updatedDate}</h7>
														</div>
														<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
															<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
															<c:set var="exlink" value="${lookUp.link}" />
															<c:forEach items="${links}" var="downloadL" varStatus="count">
																<c:set var="lnk1" value="./cache/${downloadL}" /> 
																<c:set var="link" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
						                                        <c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
																 <c:if test="${lookUp.linkType eq 'R' }">
																<apptags:filedownload filename="${link}" 
					                                                filePath="${path}" showIcon="true" docImage="true"
					                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
															     </c:if>
															     <c:set value="${test.fileSize(path, link)}" var="fileSize"></c:set>
						                                         <strong style="display: block; text-align: left; font-size: 7px;">${link}<br>${fileSize}</strong>
															</c:forEach>
															<c:if test="${not empty exlink && lookUp.linkType ne 'R'}">
																<a href="${exlink}" target="_blank" class=""><span class="external_link_icon"></span></a>
															</c:if>
														</div>
													</c:when>
													<c:otherwise>
														<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
															${lookUp.noticeSubReg}
															<c:if test="${lookUp.isHighlighted eq 'Y'}">
																<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
															</c:if>
															<h7 style="display: block; text-align: left; font-size: 10px;"><spring:message code="important.notice.updated.date" text="Last Updated Date" />&nbsp; ${updatedDate}</h7>
														</div>
														<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
															<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
															<c:set var="exlink" value="${lookUp.link}" />
															<c:forEach items="${links}" var="downloadL" varStatus="count">
																<c:set var="lnk1" value="./cache/${downloadL}" /> 
																<c:set var="link" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
																<c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
                                         						 <c:if test="${lookUp.linkType eq 'R' }">
                                         						<apptags:filedownload filename="${link}" 
					                                                filePath="${path}" showIcon="true" docImage="true"
					                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
															    </c:if>
															</c:forEach>
															<c:if test="${not empty exlink && lookUp.linkType ne 'R'}">
																<a href="${exlink}" target="_blank" class=""><span class="external_link_icon"></span></a>
															</c:if>
															<c:set value="${test.fileSize(path, link)}" var="fileSize"></c:set>
						                                    <strong style="display: block; text-align: left; font-size: 7px;">${link}<br>${fileSize}</strong>
														</div>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
										<input type="hidden" name="downloadLink"
											value="${lookUp.profileImgPath}">
								</li>
							</c:if>
						</c:forEach>
						</ul>
					</c:if>
				</div>
			</div>
			<!-- Important Notices ends -->
		</c:if>
		
		<c:if test="${command.themeMap['PRESS_RELEASE'] ne 'I'}">
			<!-- Press Releases starts -->
			<div class="section-container press-release-main panel" id="press-sect">
				<div class="panel-heading">
					<h3><spring:message code="theme6.portal.press.release" text="Recent Announcement" /></h3>
					<a href="CitizenHome.html?newsAndEvent" class="vm-btn" title="View More">
						<spring:message code="portal.link.viewmore" text="VIEW MORE" />
					</a>
					<%-- <div class="section-icon">
						<i class="fa fa-newspaper-o" aria-hidden="true"></i>
					</div> --%>
				</div>
				<div class="panel-body">
					<%-- <c:if test="${command.highlightedAnnouncement eq true}"> --%>
						<c:set var="serial_count" scope="page" value="0" />
						<ul>
						<li style="background:#FFF;">
								<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12 padding-left-0">
									<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10 text-bold">
										<spring:message code="theme6.portal.announcemen.details" text="Announcement Details" />
									</div>
									<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 text-bold">
										<spring:message code="theme6.portal.documents" text="Documents" />
									</div>
								</div>
								<span class="date col-lg-3 col-md-3 col-sm-12 col-xs-12 padding-right-0 text-right text-bold">
									<spring:message code="theme6.portal.creation.date" text="Creation Date" />
								</span>
						</li>
							<c:forEach items="${command.eipAnnouncement}" var="lookUp"
								varStatus="status">
								<fmt:formatDate value="${lookUp.newsDate}" pattern="yyyy-MM-dd HH:mm:ss" var="iDate"/> 
								<fmt:formatDate value="${lookUp.validityDate}" pattern="yyyy-MM-dd HH:mm:ss" var="vDate"/>
								<fmt:formatDate value="${lookUp.updatedDate}" var="updatedDate" type="date" pattern="dd-MM-yyyy" />
								<c:if test="${serial_count < 7 && iDate le myDate &&  myDate le vDate}">
		
								<%-- <c:if test="${lookUp.isHighlighted eq 'Y' && serial_count < 7}"> --%>
									<li>
										<c:set var="serial_count" scope="page" value="${serial_count+1}" />
										<c:choose>
											<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
												<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12 padding-left-0">
													<c:choose>
														<c:when test="${isDMS}">
															<a href="javascript:void(0);"
																onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
														</c:when>
														<c:otherwise>
					 										<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
					 											${lookUp.announceDescEng}
					 											<c:if test="${lookUp.isHighlightedFlag eq 'Y'}">
																	<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
																</c:if>
																<h7 style="display: block; text-align: left; font-size: 10px;"><spring:message code="important.notice.updated.date" text="Last Updated Date" />&nbsp; ${updatedDate}</h7>
															</div>
															<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
																<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
																<c:forEach items="${links}" var="download" varStatus="count">
																	<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}" />
                                         								<c:set var="path" value="${stringUtility.getStringBeforeChar('/',download)}" />
                                        
						                                             <apptags:filedownload filename="${link}" 
						                                                filePath="${path}" showIcon="true" docImage="true"
						                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
																	
																</c:forEach>
																<c:if test="${(not empty lookUp.linkType) and (lookUp.linkType eq 'E' || lookUp.linkType eq 'L') }">
																	<a href="${lookUp.link }" target="_blank" class="extlink external"><span class="external_link_icon"></span></a>
														        </c:if>
														        <c:set value="${test.fileSize(path, link)}" var="fileSize"></c:set>
						                                        <strong style="display: block; text-align: left ; font-size: 7px;">${link}<br>${fileSize}</strong>
					 										</div>
														</c:otherwise>
													</c:choose>
												</div>
												<span class="date col-lg-3 col-md-3 col-sm-12 col-xs-12 padding-right-0 text-right">
													<i class="fa fa-calendar" aria-hidden="true"></i>
														<fmt:formatDate value="${lookUp.newsDate}" pattern="dd-MM-yyyy" />
												</span>
												<div class="col-lg-1 col-md-1 col-sm-12 col-xs-12 modal-btn-container">
													<c:set var="flagCnt" value="0" />
													<c:set var="links" value="${fn:split(lookUp.attachImage,',')}" />
													<c:forEach items="${links}" var="download" varStatus="count">
														<c:if test="${not empty download && fn:trim(download) ne ''}">
															<c:set var="flagCnt" value="1" />
														</c:if>
													</c:forEach>
													<c:if test="${not empty lookUp.attachImage && flagCnt eq 1}">
														<a class="press-release" data-toggle="modal" data-id="${lookUp.attachImage}"  ><span class="image_icon"></span></a>
													</c:if>
												</div>
											</c:when>
											<c:otherwise>
												<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12 padding-left-0">
													<c:choose>
														<c:when test="${isDMS}">
															<a href="javascript:void(0);"
																onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
														</c:when>
														<c:otherwise>
															<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
																${lookUp.announceDescReg}
					 											<c:if test="${lookUp.isHighlighted eq 'Y'}">
																	<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
																</c:if>
																<h7 style="display: block; text-align: left; font-size: 10px;"><spring:message code="important.notice.updated.date" text="Last Updated Date" />&nbsp; ${updatedDate}</h7>
															</div>
															<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
																<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
																<c:forEach items="${links}" var="download" varStatus="count">
																	<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}" />
                                         								<c:set var="path" value="${stringUtility.getStringBeforeChar('/',download)}" />
                                        
						                                             <apptags:filedownload filename="${link}" 
						                                                filePath="${path}" showIcon="true" docImage="true"
						                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
																	
																</c:forEach>
																<c:if test="${(not empty lookUp.linkType) and (lookUp.linkType eq 'E' || lookUp.linkType eq 'L') }">
																	<a href="${lookUp.link }"><span class="external_link_icon"></span></a>
														        </c:if>
														        <c:set value="${test.fileSize(path, link)}" var="fileSize"></c:set>
						                                        <strong style="display: block; text-align: left ; font-size: 7px;">${link}<br>${fileSize}</strong>
					 										</div>
														</c:otherwise>
													</c:choose>
												</div>
												<span class="date col-lg-3 col-md-3 col-sm-12 col-xs-12 padding-right-0 text-right">
													<i class="fa fa-calendar" aria-hidden="true"></i>
													<fmt:formatDate value="${lookUp.newsDate}" var="newsDate" pattern="dd-MM-yyyy" />
													${marathiConvert.convertToRegional("marathi",newsDate)}
												</span>
												<div class="col-lg-1 col-md-1 col-sm-12 col-xs-12 modal-btn-container">
													<c:set var="flagCnt" value="0" />
													<c:set var="links" value="${fn:split(lookUp.attachImage,',')}" />
													<c:forEach items="${links}" var="download" varStatus="count">
														<c:if test="${not empty download && fn:trim(download) ne ''}">
															<c:set var="flagCnt" value="1" />
														</c:if>
													</c:forEach>
																	
													<c:if test="${not empty lookUp.attachImage && flagCnt eq 1}">
														<a class="press-release" data-toggle="modal" data-id="${lookUp.attachImage}"  ><span class="image_icon"></span></a>
													</c:if>
												</div>
											</c:otherwise>
										</c:choose>
											
									</li>
								</c:if>
								
							</c:forEach>
						</ul>
					<%-- </c:if> --%>
				</div>
			</div>
			<!-- Press Releases ends -->
			<!-- Modal for Press Release Events starts -->
			<!-- <div id="pressreleasediv" class="modal fade press-modal press-release-modal-class" role="dialog">
				<div class="modal-dialog modal-lg">
					Modal content
					<div class="modal-content">
						<button type="button" class="close" data-dismiss="modal">
							<i class="fa fa-times" aria-hidden="true"></i>
						</button>
						<div class="modal-body ">
							<div id="myCarousel-1" class="carousel slide carousel-fade" data-ride="carousel">
								Wrapper for slides
								<div class="carousel-inner press-modal-body" id="press-modal-body-div">
	
								</div>
		
								Left and right controls
								<a class="left carousel-control hidden-xs" href="#myCarousel-1"	data-slide="prev">
									<i class="fa fa-angle-left" aria-hidden="true"></i>
									<span class="sr-only">Previous</span>
								</a>
								<a class="right carousel-control hidden-xs" href="#myCarousel-1" data-slide="next">
									<i class="fa fa-angle-right" aria-hidden="true"></i>
									<span class="sr-only">Next</span>
								</a>
							</div>
							
						</div>
					</div>
	
				</div>
			</div> -->
			<!-- Modal for Press Release Events ends -->
		</c:if>
		
		<c:if test="${command.themeMap['EXTERNAL_LINKS'] ne 'I'}">
			<!-- External Links starts -->
			<div class="section-container tenders-main panel" id="ext-links-sect">
				<div class="panel-heading">
					<h3><spring:message code="theme6.portal.external.links" text="External Links" /></h3>
					<a href="CitizenHome.html?tenderNotices" class="vm-btn" title="View More">
						<spring:message code="portal.link.viewmore" text="VIEW MORE" />
					</a>
					<%-- <div class="section-icon">
						<i class="fa fa-gavel" aria-hidden="true"></i>
					</div> --%>
				</div>
				<div class="panel-body">
					<c:if test="${command.highlighted eq true}">
						<c:set var="serial_count" scope="page" value="0" />
						<ul>
						<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
						<fmt:formatDate value="${lookUp.issueDate}" pattern="yyyy-MM-dd HH:mm:ss" var="iDate"/> 
						<fmt:formatDate value="${lookUp.validityDate}" pattern="yyyy-MM-dd HH:mm:ss" var="vDate"/> 
							<c:if test="${lookUp.isUsefullLink eq 'T' && serial_count < 7 &&  iDate le myDate &&  myDate le vDate}">
								<li>
									<%-- <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
									<span class="serialno-new">${serial_count+1}</span>
									<c:set var="serial_count" scope="page"
										value="${serial_count+1}" />
									</div> --%>
									<c:set var="serial_count" scope="page" value="${serial_count+1}" />
										<c:set var="link"
											value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
										<c:choose>
											<c:when test="${isDMS}">
												<c:choose>
													<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
													
														<a href="javascript:void(0);"
															onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.noticeSubEn}</a>
													</c:when>
													<c:otherwise>
														<a href="javascript:void(0);"
															onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.noticeSubReg}</a>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when
														test="${userSession.getCurrent().getLanguageId() eq 1}">
														<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
															${lookUp.noticeSubEn}
															<c:if test="${lookUp.isHighlighted eq 'Y'}">
																<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
															</c:if>
														</div>
														<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
															<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
															<c:set var="exlink" value="${lookUp.link}" />
																													
															<c:forEach items="${links}" var="downloadL" varStatus="count">
													
																<c:set var="lnk1" value="./cache/${downloadL}" /> 
																<c:set var="link" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
                                          								<c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
                                                                       <c:if test="${lookUp.linkType eq 'R' }">
							                                             <apptags:filedownload filename="${link}"
							                                                filePath="${path}" showIcon="true" docImage="true" 
							                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
																       </c:if>
															</c:forEach>
															<c:if test="${not empty exlink && lookUp.linkType ne 'R'}">
																<a href="${exlink}" target="_blank" class=""><span class="external_link_icon"></span></a>
															</c:if>
														</div>
													</c:when>
													<c:otherwise>
														<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
															${lookUp.noticeSubReg}
															<c:if test="${lookUp.isHighlighted eq 'Y'}">
																<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
															</c:if>
														</div>
														<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
															<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
															<c:set var="exlink" value="${lookUp.link}" />
															
															<c:forEach items="${links}" var="downloadL" varStatus="count">
													
																<c:set var="lnk1" value="./cache/${downloadL}" /> 
																<c:set var="link" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
                                          								<c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
                                                                       <c:if test="${lookUp.linkType eq 'R' }">
							                                             <apptags:filedownload filename="${link}"
							                                                filePath="${path}" showIcon="true" docImage="true" 
							                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
																       </c:if>
															</c:forEach>
															<c:if test="${not empty exlink && lookUp.linkType ne 'R'}">
																<a href="${exlink}" target="_blank" class=""><span class="external_link_icon"></span></a>
															</c:if>
														</div>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
										<input type="hidden" name="downloadLink"
											value="${lookUp.profileImgPath}">

								</li>
							</c:if>
						</c:forEach>
						</ul>
					</c:if>
				</div>
			</div>
			<!-- External Links ends -->
		</c:if>
		
		<c:if test="${command.themeMap['PHOTO_GALLERY'] eq 'A' or command.themeMap['VIDEO_GALLERY'] eq 'A' or (command.themeMap['PHOTO_GALLERY'] ne 'I' && command.themeMap['VIDEO_GALLERY'] ne 'I') }">
			<!-- Media Gallery starts -->
			<div class="section-container media-gallery-main panel" id="media-sect">
				<div class="panel-heading">
					<h3><spring:message code="theme6.portal.media.gallery" text="Media Gallery" /></h3>
					<%-- <div class="section-icon">
						<i class="fa fa-film" aria-hidden="true"></i>
					</div> --%>
				</div>
				<div class="panel-body">
					<c:if test="${command.themeMap['PHOTO_GALLERY'] ne 'I'}">
						<!-- Photo Gallery section starts -->
						<div class="mg-section-1">
							<div class="mg-heading">
								<h3><spring:message code="theme6.portal.photo.gallery" text="Photo Gallery" /></h3>
								<a href="SectionInformation.html?editForm&rowId=&page=Photo Gallery Album" class="vm-btn" title="View More">
									<spring:message code="portal.link.viewmore" text="VIEW MORE" />
								</a>
							</div>
							<div id="p-image-container" class="col-xs-9 col-sm-9 col-md-9 col-lg-9 pg-img-container"></div>
							<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
								<ul id="p-slider" class="pg-slider">
									<c:set var="albums" value="${command.getAllhtmlPhotoWithDownloadedLoc('GALLERY SECTION OF PHOTO GALLERY')}" /> 
									<c:forEach items="${albums}" var="album" varStatus="count">
										<c:forEach items="${album}" var="singleAbbum" varStatus="count">
											<li>
												<a href="./${singleAbbum}"><img alt="Photo Gallery Image" src="./${singleAbbum}" width="200"></a>
											</li>
										</c:forEach>
									</c:forEach>
									
								<%-- <c:set var="detailsList" value="${command.getAllhtmlPhoto('GALLERY SECTION OF PHOTO GALLERY')}" /> 
									<c:forEach items="${detailsList}" var="details" varStatus="count">
									    <c:set var="links" value="${fn:split(details,',')}" /> 
									    <c:forEach items="${links}" var="downloadL" varStatus="count">
											<c:set var="lnk1" value="./${downloadL}" /> 
											<c:set var="lastName" value="${fn:split(lnk1, '/')}" />
											<c:set var="fname" value="${lastName[fn:length(lastName)-1]}" />
											
											<li>
												<apptags:filedownload filename="${fname}"
		                                                filePath="${lnk1}" showFancyBox="true"
		                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
											</li>
										</c:forEach>
									</c:forEach> --%>
								</ul>
							</div>
						</div>
						<!-- Photo Gallery section ends -->
					</c:if>
					
					<c:if test="${command.themeMap['VIDEO_GALLERY'] ne 'I'}">
						<!-- Video Gallery section starts -->
						<div class="mg-section-2">
							<div class="mg-heading">
								<h3><spring:message code="theme6.portal.video.gallery" text="Video Gallery" /></h3>
								<a href="Content.html?links&page=Video Gallery" class="vm-btn" title="View More">
									<spring:message code="portal.link.viewmore" text="VIEW MORE" />
								</a>
							</div>
							<ul id="rcbrand-v-slider">
								<c:set var="detailsList" value="${command.getAllhtmlVideo('GALLERY SECTION OF VIDEO GALLERY')}" /> 
								<c:forEach items="${detailsList}" var="details" varStatus="count">
								
								<c:set var="links" value="${fn:split(details,',')}" /> 
									<c:forEach items="${links}" var="downloadL" varStatus="count">
										<c:set var="lnk1" value="./${downloadL}" /> 
										<c:set var="lastName" value="${fn:split(lnk1, '/')}" />
										<c:set var="fname" value="${lastName[fn:length(lastName)-1]}" />
										<li>
										<apptags:filedownload filename="${fname}"
                                                filePath="${lnk1}"
                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
										</li>
									</c:forEach>
								</c:forEach>
							</ul>
						</div>
						<!-- Video Gallery section ends -->
					</c:if>
				</div>
			</div>
			<!-- Media Gallery ends -->
		</c:if>
	</div>
	<!-- Column 3 ends -->
	
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
	$(document).ready(
			function() {
				$("div.bhoechie-tab-menu>div.list-group>a").click(
						function(e) {
							e.preventDefault();
							$(this).siblings('a.active').removeClass("active");
							$(this).addClass("active");
							var index = $(this).index();
							$("div.bhoechie-tab>div.bhoechie-tab-content")
									.removeClass("active");
							$("div.bhoechie-tab>div.bhoechie-tab-content").eq(
									index).addClass("active");
						});

				$(".header-inner").addClass('hide');

			});
</script>

<%-- <c:if
	test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">
	<script>
		$(document).ready(
				function() {
					var space = $('.minister-details').offset().top;
					if (space >= 200) {
						$('.org-select').addClass('org-select-slider').css(
								'float', 'right');
					} else {
						$('.org-select').addClass('org-select-minister');
					}
				});
	</script>
</c:if>

<c:if
	test="${(not empty command.themeMap['SLIDER_IMG']) && (command.themeMap['SLIDER_IMG'] ne 'A')}">
	<script>
		$(function() {
			$('.about-bg').css('margin-top', '65px');
		});
	</script>
</c:if> --%>
