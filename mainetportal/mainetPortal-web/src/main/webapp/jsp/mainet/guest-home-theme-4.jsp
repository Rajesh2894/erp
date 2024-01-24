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
<link href="assets/css/style-blue-theme-4.css" rel="stylesheet" type="text/css"/>

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

${command.setThemeMap()}
${command.getAboutUs()}

<div class="invisibeHead"></div>
<div id="main">
<%-- Defect #158433 --%>
<c:if test="${command.themeMap['ANNOUNCEMENTS'] ne 'I'}">
	<div class="container scroll-messages-container">
		<div class="scroll-messages col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<%-- Latest News Marquee --%>
			<div class="message-container">
				<div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
					<span class="message-title">
						<spring:message code="portal.Latest.News" text="Latest News" />
					</span>
				</div>
				<div class="marquee-outer marquee2 col-xs-12 col-sm-9 col-md-10 col-lg-10">
					<ul class="list-aggregate">
						<marquee direction="left" onmouseover="stop()" onmouseout="start()" scrollamount="4">
						 <c:forEach items="${command.getAllhtml('Latest News')}" var="latestnews"><li class="fat-l"> ${latestnews}</li> </c:forEach>
						</marquee>
					</ul>
				</div>
			</div>
			<%-- Latest News Marquee ends --%>
		</div>
	</div>
</c:if>


	<c:if test="${command.themeMap['SLIDER_IMG'] ne 'I'}">
		<div class="container" id="container-fluid1">			
			<div class="row r1">
			
			<%-- NNA Online Services starts --%>
			<%-- <c:if test="${command.themeMap['PUBLIC_NOTICE'] ne 'I'}">
			<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 nna-online-serv-section">
				<div class="widget section-main">
					<div class="widget-header">
						<h1><spring:message code="theme4.portal.online.services" text="Online Services" /></h1>
					</div>
					<div class="widget-content element-animate">
						<div class="announcement scrolllistbox magazine-section">
							<c:if test="${command.highlighted eq true}">
							 <c:set var="serial_count" scope="page" value="0"/>
								<c:forEach items="${command.publicNotices}" var="lookUp"
									varStatus="lk">
									  
									<c:if test="${lookUp.isHighlighted eq 'Y' && empty lookUp.isUsefullLink}">
										<div class="public-notice lst">
											<c:set var="link" value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
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
														<c:when test="${not empty lookUp.profileImgPath}">
															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																	<c:set var="exlink" value="${lookUp.link}" />																			 
																	<apptags:filedownload filename="EIP"
																		filePath="${lookUp.profileImgPath}"
																		actionUrl="CitizenPublicNotices.html?Download"
																		eipFileName="${lookUp.detailEn}"></apptags:filedownload>
																		
																	<c:forEach items="${links}" var="downloadL" varStatus="count">
																	 <c:set var="lnk1" value="./cache/${downloadL}" />
																		<c:if test="${count.index eq 0 }">
																			<c:if test="${lnk1 ne './cache/'}">
																				<a href="${lnk1}" target="_blank" class="">
																				${lookUp.detailEn}<i class="fa fa-download"></i></a>
																			</c:if>
																			<c:if test="${lnk1 eq './cache/'}">
																				${lookUp.detailEn}
																			</c:if>
																		</c:if>
																		<c:if test="${count.index ne 0 }">
																		<a href="${lnk1}" target="_blank" class=""><i class="fa fa-download"></i></a>
																		</c:if>
																	</c:forEach>
																</c:when>
																<c:otherwise>
																	<apptags:filedownload filename="EIP"
																		filePath="${lookUp.profileImgPath}"
																		actionUrl="CitizenPublicNotices.html?Download"
																		eipFileName="${lookUp.detailReg}"></apptags:filedownload>
																		
																	<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																	<c:set var="exlink" value="${lookUp.link}" />
																	
																	<c:forEach items="${links}" var="downloadL" varStatus="count">
																	 <c:set var="lnk1" value="./cache/${downloadL}" />
																		<c:if test="${count.index eq 0 }">
																			<c:if test="${lnk1 ne './cache/'}">
																				<a href="${lnk1}" target="_blank" class="">${lookUp.detailReg}<i class="fa fa-download"></i></a>
																			</c:if>
																			<c:if test="${lnk1 eq './cache/'}">
																				${lookUp.detailReg}
																			</c:if>
																		</c:if>
																		<c:if test="${count.index ne 0 }">
																		<a href="${lnk1}" target="_blank" class=""><i class="fa fa-download"></i></a>
																		</c:if>
																	</c:forEach>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:if test="${lookUp.imagePath ne ' '}">																	
															<c:set var="links" value="${fn:split(lookUp.imagePath,',')}" />
																<c:set var="lnk1" value="./cache/${downloadL}" />																	
																<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<c:if test="${not empty lookUp.detailEn}">
																	<a href="${lookUp.link}" target="_self">
																	<div class="displayimage">
																		<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailEn}">
																		</c:forEach>
																		</div>${lookUp.detailEn}
																	 </a>
																	</c:if>
																	<c:if test="${empty lookUp.detailEn}">
																	<div class="displayimage">
																	<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																		<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailEn}">
																	 </c:forEach>
																	</div>
																	</c:if>
																</c:if>
																<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
																	<c:if test="${not empty lookUp.detailReg}">
																	<a href="${lookUp.link}" target="_self">
																	<div class="displayimage">
																		<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailReg}">
																		</c:forEach>
																		</div>${lookUp.detailReg}
																	</a>
																	</c:if>
																	<c:if test="${empty lookUp.detailReg}">
																	<div class="displayimage">
																		<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailReg}">
																		</c:forEach>
																	</div>
																	</c:if>
																</c:if>
															</c:if>
														</c:otherwise>
													</c:choose>															
												</c:otherwise>
											</c:choose>
											<p>
												<fmt:formatDate type="date" value="${lookUp.issueDate}"
													pattern="dd/MM/yyyy" var="issueDate" />
												${issueDate}
											</p>
											<input type="hidden" name="downloadLink"
												value="${lookUp.profileImgPath}">

												<c:choose>
											<c:when test="${isDMS}">
												<a href="jadocIdvascript:void(0);"
													onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.docName}</a>
											</c:when>
											<c:otherwise>
												<apptags:filedownload filename="EIP"
													filePath="${lookUp.profileImgPath}"
													actionUrl="CitizenPublicNotices.html?Download"
													eipFileName="${stringUtility.getStringAfterChars(lookUp.profileImgPath)}"></apptags:filedownload>
											</c:otherwise>
										</c:choose>
											
										</div>
									</c:if>
								</c:forEach>
							</c:if>
											
						</div>
						<div class="view-more-main">
							<a href="CitizenHome.html?usefullLink" class="view-more-btn" title="View More">
								<span><spring:message code="portal.link.viewmore" text="VIEW MORE"/></span>
							</a>
						</div>
					</div>
				</div>
			</div>
			</c:if> --%>
			<%-- NNA Online Services ends --%>
			
			<%-- Slider Start --%>
			<div class="col-lg-9 col-md-9 col-sm-8 col-xs-12 slider-main" id="sld">
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
			<%-- Slider End --%>
			
			<%-- Nidaan tiles starts --%>
			<c:if test="${not empty command.themeMap['IMP_LINKS'] && command.themeMap['IMP_LINKS'] ne 'I'}">
				<div class="nidaan-main element-animate">
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
							<div class="section-header">
								<h1><spring:message code="theme4.portal.online.services" text="Online Services" /></h1>
							</div>
							<div id="exservices">
								<%-- <a href="<spring:message code="dashboard.url" text="CitizenHome.html"/>" target="_blank"
									data-target="internal">
									<div class="dcard">
										<div class="image">
											<i class="fa fa-dashboard fa-3x" aria-hidden="true"></i>
										</div>
										<div class="txt">
											<spring:message code="portal.dashboard" text="Dashboard" />
										</div>
									</div>
								</a> --%>
								<div class="dcard">
									<a href="#" onclick="showPropertyTaxCalculator(this)"
									data-target="internal">
										<div class="image">
											<i class="fa fa-building" aria-hidden="true"></i>
										</div>
										<div class="txt">
											<spring:message code="PropertyTax" text="Property Tax" />
											<spring:message code="Calculator" text="Calculator" />
										</div>
									</a>
								</div>
								<%-- <div class="dcard mkcmp">
									<div class="image">
										<i class="fa fa-comments fa-3x" aria-hidden="true"></i>
									</div>
									<div class="txt">
										<spring:message code="MakeaComplaint" text="Make a Complaint" />
									</div>
								</div> --%>
								<%-- <div class="dcard">
									<a href="<spring:message code="theme4.portal.print.water.supply.bill.url" text="https://nnaligarh.in/birtPA/frameset?__report=RP_WaterBillReport_ASCL.rptdesign"/>" target="_blank">
										<div class="image">
											<i class="fa fa-tint fa-3x" aria-hidden="true"></i>
										</div>
										<div class="txt">
											<spring:message code="theme4.portal.print.water.supply.bill" text="Print Your Water Supply Bill" />
										</div>
									</a>
								</div> --%>
								<%-- <div class="dcard ckstat">
									<div class="image">
										<i class="fa fa-check fa-3x" aria-hidden="true"></i>
									</div>
									<div class="txt">
										<spring:message code="CheckStatus" text="Check Status" />
									</div>
								</div> --%>
								<div class="dcard mkpay">
									<div class="image">
										<i class="fa fa-credit-card" aria-hidden="true"></i>
									</div>
									<div class="txt">
										<spring:message code="MakePayment" text="Make Payment & Check Payment Status" />
									</div>
								</div>
								<%-- <div class="dcard mk">
									<div class="image">
										<i class="fa fa-print fa-3x" aria-hidden="true"></i>
									</div>
									<div class="txt">
										<a target=_blank
										href="http://nnaligarh.in/birtPA/frameset?__report=RP_PropertyBillReport_ASCL.rptdesign
										"><spring:message code="Property.Tax.Bill.printing" text="Print Your Property Tax Bill" /></a>
									</div>
								</div> --%>
								<div class="dcard mk">
									<div class="image">
										<i class="fa fa-print" aria-hidden="true"></i>
									</div>
									<div class="txt">
										<spring:message code="Property.Tax.Bill.printing" text="Print Your Bill" />
									</div>
								</div>
								<div class="dcard reprint">
									<div class="image">
										<i class="fa fa-building-o" aria-hidden="true"></i>
									</div>
									<div class="txt">
										<spring:message code="theme4.portal.receipt.reprint.property.details" text="Receipt Reprint" />
									</div>
									
								</div>
									<div class="dcard certificate">
										<div class="image">
											<i class="fa fa-print" aria-hidden="true"></i>
										</div>
										<div class="txt">
											<spring:message code="theme4.portal.print.certificate.bnd.details" text="Print Your Birth & Death Certificate" />
										</div>
									</div>
								</div>
						</div>
					</div>
					<div class="nidaan-contents" style="display: none">
						<!--  Make complaint -->
						<div id="mkcmp" style="display: none">
							<div class="col-sm-6 col-md-8 col-lg-8 col-xs-12">
								<div class="content-tab">
									<h3>
										<spring:message code="eip.nidan1100" text="Nidaan 1100" />
									</h3>
									<p>
										<spring:message code="eip.nidan.desc" text="" />
										&nbsp;
									</p>
									<p class="yellow">
										<spring:message code="eip.comp.time" text="" />
									</p>
								</div>
							</div>
							<div class="col-sm-6 col-md-4 col-lg-4 col-xs-12">
								<div class="hidden-lg hidden-md hidden-sm"
									style="margin-top: 20px;"></div>
								<div class="content-tab">
									<h3>
										<spring:message code="eip.reg.care"
											text="Grievance Registration" />
									</h3>
									<ul class="banner-new">
										<li><a href="javascript:void(0);" data-toggle="modal"
											data-target="#phonemodal"><i class="fa fa-phone"></i>&nbsp;
												<span><spring:message code="eip.tel"
														text="Registration On Phone" /></span></a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<!--  Make complaint -->
						<!-- Check Status -->
						<div id="ckstat" style="display: none">
							<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
								<div class="content-tab">
									<h3>
										<spring:message code="CheckStatus" text="Check Status" />
									</h3>
									<p>
										<spring:message code="eip.checkstatus.desc" text="" />
									</p>
									<ul class="banner-new col-sm-6">
										<li><a href="CitizenHome.html?applicationStatus"><i
												class="fa fa-file"></i>&nbsp; <span><spring:message
														code="eip.app.status" text="Applicant Status" /></span></a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<!-- Check Status -->
						<!-- Make payment -->
						<div id="mkpay" style="display: none">
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
								<div class="hidden-lg hidden-md hidden-sm"
									style="margin-top: 20px;"></div>
								<div class="content-tab">
									<form class="form">
										<div class="form-group">
											<label for="billtype"><spring:message code="BillType"
													text="Bill Type" /></label> <select class="form-control"
												name="bill-type" id="billtype">
												<option value="prop" ><spring:message code="PropertyBill" text="Property Bill" /></option>
												<option value="WaterBillPayment.html"><spring:message code="WaterBill" text="Water Bill" /></option>
											</select>
										</div>
										<%--  <div class="form-group">
											<spring:message code="eg" var="placeholder4" />
											<label for="billNumber"> <spring:message
													code="ConsumerNumber" text="Consumer Number" /></label> <input
												type="text" class="form-control" name="referral"
												id="billNumber" placeholder="${placeholder4}">
										</div>  --%>
										<div class="text-center">
											<br />
											<button type="button" onclick="searchBillPay()"
												class="btn btn-success" style="width: 150px">
												<spring:message code="eip.page.process" text="Proceed" />
											</button>
										</div>
										
									</form>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<!-- Make payment -->
						
						<div id="reprint" style="display: none">
							<div class="col-sm-7 col-md-7 col-lg-7 col-xs-7" align="right">
								<div class="hidden-lg hidden-md hidden-sm"
									style="margin-top: 20px;"></div>
								<div class="content-tab" align="right">
								
								<form:form><div class="form-group">
								<div class="col-sm-4">
								<label class="control-label col-sm-8 required-control">
								<spring:message code="theme4.portal.view.type"
													text="view Type" /></label>
								</div> 
								<div class="col-sm-8">
								<form:select path="" class="form-control mandColorClass" id="viewtype" >
								
								<form:option value="ViewWaterDetails.html" code="WT">
									<spring:message
										code="" text="Water" />
								</form:option>
							</form:select>
							</div>
							</div>
							
							
							
							<div class="text-center">
											<br /><br /><br />
											<button type="button" onclick="searchViewDetails()"
												class="btn btn-success" style="width: 150px">
												<spring:message code="eip.page.process" text="Proceed" />
											</button>
								</div>
							</form:form>
							
							
									<%-- <form class="form">
										<div class="form-group" >
											<label for="viewtype"><spring:message code=""
													text="view Type" /></label> <select class="form-control"
												name="view-type" id="viewtype">
												<option value="ViewPropertyDetail.html" ><spring:message code="" text="View Property" /></option>
												<option value="ViewWaterDetails.html"><spring:message code="" text="View Water" /></option>
											</select>
										</div>
										<div class="text-center">
											<br />
											<button type="button" onclick="searchViewDetails()"
												class="btn btn-success" style="width: 150px">
												<spring:message code="eip.page.process" text="Proceed" />
											</button>
										</div>
										
									</form> --%>
								</div>
							</div>
							
						</div>
						
						
							<div id="certificate" style="display: none">
							<div class="col-sm-7 col-md-7 col-lg-7 col-xs-7" align="right">
								<div class="hidden-lg hidden-md hidden-sm"
									style="margin-top: 20px;"></div>
								<div class="content-tab" align="right">
								
								<form:form><div class="form-group">
								<div class="col-sm-4">
								<label class="control-label col-sm-8 required-control">
								<spring:message code="theme4.portal.view.type"
													text="view Type" /></label>
								</div> 
								<div class="col-sm-8">
								<form:select path="" class="form-control mandColorClass" id="birthviewtype" >
								
								<form:option value="BCD" >
									<spring:message
										code="theme4.printBr.certificate" text="Print Birth Certificate" />
								</form:option>
								<form:option value="DCD" code="">
									<spring:message
										code="theme4.printDe.certificate" text="Print Death Certificate" />
								</form:option>
							</form:select>
							</div>
							</div>
							
							
							<div class="text-center">
											<br /><br /><br />
											<button type="button" onclick="birthViewDetails()"
												class="btn btn-success" style="width: 150px">
												<spring:message code="eip.page.process" text="Proceed" />
											</button>
								</div>
							</form:form>
							
							
								</div>
							</div>
							
						</div>
						
						
						
						
						
						
						
						
						<div class="clearfix"></div>
						<div id="mk" style="display: none">
							<div class="col-sm-7 col-md-7 col-lg-7 col-xs-7" align="right">
								<div class="hidden-lg hidden-md hidden-sm"
									style="margin-top: 20px;"></div>
								<div class="content-tab" align="right">
								
								<form:form><div class="form-group">
								<div class="col-sm-4">
								<label class="control-label col-sm-8 required-control">
								<spring:message code=""
													text="Bill Type" /></label>
								</div> 
								<div class="col-sm-8">
								<form:select path="" class="form-control mandColorClass" id="printtype" >
								
								<form:option value="" code="WT">
									<spring:message code="" text="Water" />
								</form:option>
							</form:select>
							</div>
							</div>
							<div class="text-center">
											<br /><br /><br />
											<button type="button" onclick="printDetails()"
												class="btn btn-success" style="width: 150px">
												<spring:message code="eip.page.process" text="Proceed" />
											</button>
								</div>
							</form:form>
								</div>
							</div>
							
						</div>
						<div class="clearfix"></div>
						
					</div>
				</div>
			</c:if>
			<%-- Nidaan tiles ends --%>
			</div>			
		</div>
	</c:if>
<div class="clear"></div>

<!-- New Helpline Number -->
	<c:if test="${command.themeMap['HELPLINE_NO'] ne 'I'}">
	<i id="helpline"></i>
	<div class="helpline-main">
		<div class="helpline-text"><spring:message code="theme4.portal.contact" text="Contact" /></div>
		<div class="helpline-inner-container">
			<div class="helpline help-line">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="helpline-list">
						<span class="list-number">
							<spring:message code="theme4.portal.address.name" text="Address" />
						</span>
						<span class="list-text">
							<spring:message code="theme4.portal.address" text="Sewa Bhawan, Near Ghanta Ghar, Aligarh" />
						</span>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="helpline-list">
						<span class="list-number">
							<spring:message code="theme4.portal.phone" text="Phone" />
						</span>
						<span class="list-text">
							<spring:message code="theme4.portal.phone.num" text="0571-2405520" />
						</span>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="helpline-list">
						<span class="list-number">
							<spring:message code="theme4.portal.toll.free" text="Toll Free" />
						</span>
						<span class="list-text">
							<spring:message code="theme4.portal.toll.free.num" text="18002747047" />
						</span>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="helpline-list">
						<span class="list-number">
							<spring:message code="theme4.portal.email" text="Email" />
						</span>
						<span class="list-text">
							<spring:message code="theme4.portal.email.address" text="nnaligarh@gmail.com" />
						</span>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="helpline-list">
						<span class="list-number">
							<spring:message code="theme4.portal.fax" text="Fax" />
						</span>
						<span class="list-text">
							<spring:message code="theme4.portal.fax.num" text="0571-2403796" />
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	</c:if>
<!-- New Helpline Number ends -->
<div class="clear"></div>

<!-- City GIS starts -->
	<div class="city-gis-main">
		<div class="city-gis-text"><spring:message code="theme4.portal.city.gis" text="City GIS" /></div>
		<div class="city-gis-inner-container">
			<a href="http://gis.nnaligarh.in/" target="_blank">
				<img src="../../images/ASCL-images/ascl_gis_logo.png" alt="Aligarh GIS" title="City GIS Portal" class="img-responsive"/>
			</a>
		</div>
	</div>
<!-- City GIS ends -->
<div class="clear"></div>

			<c:if test="${command.themeMap['SLIDER_IMG'] eq 'I'}">
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
			</c:if></c:if>
			
			
	${command.setContactList()}
	<!-- select Organization Box in case of slider/Minister not active start -->
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
	<!-- select Organization Box in case of slider/Minister not active end -->
	<!--SELECT ORGNAIZATION END -->
	
	<!-- WELCOME CONTENT START -->
	<c:if test="${not empty command.themeMap['SCHEMES'] && command.themeMap['SCHEMES'] ne 'I'}">
		<c:if test="${not empty command.aboutUsDescFirstPara }">
			<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 welcome-content" id="about11">
				<div>
					<h1>
						<spring:message code="theme4.portal.welcome.message" text="Welcome To Aligarh Smart City Limited" />
					</h1>
					<p class="welcome-message">${command.aboutUsDescFirstPara}</p>
		
					<p class="welcome-message">${command.aboutUsDescSecondPara}</p>
					<div class="text-center margin-top-30">
						<a href="CitizenAboutUs.html" class="read-more-btn">
							<spring:message code="ReadMore" text="read more ...." />
						</a>
					</div>
				</div>
			</div>
		</c:if>
	</c:if>
	<!-- WELCOME CONTENT ENDS -->
	<div class="clear"></div>
	
	<%-- <!-- Scrolling Messages -->
	<div class="scroll-messages-main">
		<!-- Latest News Marquee starts -->
		<div class="scr-msg">
			<div class="col-lg-2 col-md-3 col-sm-4 col-xs-12">
				<div class="scr-msg-heading">
					<h3><spring:message code="theme4.portal.latest.news" text="Latest News" /></h3>
				</div>
			</div>
			<div class="marquee col-lg-10 col-md-9 col-sm-8 col-xs-12">	
				<div class="marquee-text">
					<a href="./SectionInformation.html?editForm&rowId=8&page=">
						<spring:message code="theme4.portal.latest.news.text" text="E-Newsletters" />
					</a>
					<a onclick="searchPropertyBillPay()">
					<a class="mkpay">
						<spring:message code="theme4.portal.view.pay.property" text="View & Pay Your  Bill Online Through nnaligarh.in" />
						<img src="./assets/img/flashing-new.png" class="flash-new"/>
					</a>
					<a href="./SectionInformation.html?editForm&rowId=91&page=">
						<spring:message code="theme4.portal.rfp" text="Request For Proposal (RFP)" />
					</a>
					<c:forEach items="${command.getAllhtml('LATEST NEWS')}" var="latestNewsMsg"> ${latestNewsMsg} </c:forEach>
				</div>
			</div>
		</div>
		<!-- Latest News Marquee ends -->
		
		<!-- Notice Board / News Marquee starts -->
		<div class="scr-msg">
			<div class="col-lg-2 col-md-3 col-sm-4 col-xs-12">
				<div class="scr-msg-heading">
					<h3><spring:message code="theme4.portal.notice.board.news" text="Notice Board / News" /></h3>
				</div>
			</div>
			<div class="marquee col-lg-10 col-md-9 col-sm-8 col-xs-12">	
				<div class="marquee-text">	
					<c:forEach items="${command.getAllhtml('NOTICE BOARD')}" var="noticeBoardMsg"> ${noticeBoardMsg} </c:forEach>
				</div>
			</div>
		</div>
		<!-- Notice Board / News Marquee ends -->
	</div>
	<!-- Scrolling Messages ends -->
	<div class="clear"></div> --%>
	
	<%-- <div class="text-center corona-div-main">
		<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
			<div class="cont-text">
				<i class="fa fa-phone" aria-hidden="true"></i>
				<span><spring:message code="theme4.portal.telemedicine" text="Telemedicine:" /></span>
				<span><spring:message code="theme4.portal.telemedicine.no" text="9105053427 (WhatsApp-Video Call)" /></span>
			</div>
		</div>
		<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<a href="./SectionInformation.html?editForm&rowId=18&page=" class="flash-text1">
				<spring:message code="theme4.portal.corona.covid.19" text="Corona (Covid-19)"/>
				<img src="./assets/img/flashing-new.png" class="flash-new"/>
			</a>
		</div>
		<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="cont-text">
				<i class="fa fa-phone" aria-hidden="true"></i>
				<span><spring:message code="theme4.portal.essential.services" text="Essential Services:" /></span>
				<span><spring:message code="theme4.portal.essential.services.no" text="7009183954" /></span>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div> --%>
	
	<div class="container">
		<div class="row r2">
			<%-- Latest News & Updates starts --%>
			<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 lat-news-updates">
				<div class="widget section-main">
					<div class="widget-header">
						<h1>
							<spring:message code="theme4.portal.latest.news.updates" text="Latest News & Updates" />
						</h1>
					</div>
					<div class="widget-content element-animate">
						<ul>
							<marquee direction="up" onmouseover="stop()" onmouseout="start()" scrollamount="4">
								<c:forEach items="${command.getAllhtml('Home Page Latest News And Updates')}" var="homePageLatestNewsAndUpdates"><li> ${homePageLatestNewsAndUpdates} </li></c:forEach>
							</marquee>
						</ul>
					</div>
				</div>
			</div>
			<%-- Latest News & Updates ends --%>
		
			<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
			<%-- MINISTER START --%>
			<c:if test="${command.themeMap['COMMITTEE_MEMBERS'] ne 'I'}">
				<div
					class="about-bg clear minister-details">
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
							<div class="minister-ul">
								
								<c:if test="${not empty sessionScope.mayorProfile}">
									<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 element-animate">
										<div class="col-xs-12 col-sm-7 col-md-7 col-lg-7 col-sm-push-5 col-md-push-5 col-lg-push-5">
											<div class="content-img ">
												<img src="${sessionScope.mayorprofileImage}"
													alt="${sessionScope.mayorName}"
													title="${sessionScope.mayorName}" class="img-responsive">
											</div>
										</div>
										<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5 col-sm-pull-7 col-md-pull-7 col-lg-pull-7">
											<div class="minister-info">
												<h3>${sessionScope.mayorName}</h3>
												<p class="designation">${sessionScope.mayorProfile}</p>
												<p class="minister-phone"><i class="fa fa-volume-control-phone" aria-hidden="true"></i>${sessionScope.SummaryEng}</p>
											</div>
										</div>
									</div>
								</c:if>
									
								<c:if test="${not empty sessionScope.deputyMayorProfile}">
									<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 element-animate">
										<div class="col-xs-12 col-sm-7 col-md-7 col-lg-7 col-sm-push-5 col-md-push-5 col-lg-push-5">
											<div class="content-img">
												<img src="${sessionScope.deputyMayorProfileImage}"
													alt="${sessionScope.deputyMayorName}"
													title="${sessionScope.deputyMayorName}"
													class="img-responsive">
											</div>
										</div>
										<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5 col-sm-pull-7 col-md-pull-7 col-lg-pull-7">
											<div class="minister-info">
												<h3>${sessionScope.deputyMayorName}</h3>
												<p class="designation">${sessionScope.deputyMayorProfile}</p>
												<p class="minister-phone"><i class="fa fa-volume-control-phone" aria-hidden="true"></i> ${sessionScope.deputyMayorSummaryEng}</p>
											</div>
										</div>
									</div>
								</c:if>
		
								<c:if test="${not empty sessionScope.commissionerProfile}">
									<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 element-animate">
										<div class="col-xs-12 col-sm-7 col-md-7 col-lg-7 col-sm-push-5 col-md-push-5 col-lg-push-5">
											<div class="content-img">
												<img src="${sessionScope.commissionerProfileImage}"
													alt="${sessionScope.commissionerName}"
													title="${sessionScope.commissionerName}"
													class="img-responsive">
											</div>
										</div>
										<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5 col-sm-pull-7 col-md-pull-7 col-lg-pull-7">
											<div class="minister-info">
												<h3>${sessionScope.commissionerName}</h3>
												<p class="designation">${sessionScope.commissionerProfile}</p>
												<p class="minister-phone"><i class="fa fa-volume-control-phone" aria-hidden="true"></i>${sessionScope.commissionerSummaryEng}</p>
											</div>
										</div>
									</div>
								</c:if>
		
								<c:if test="${not empty sessionScope.deputycommissionerProfile}">
	
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 element-animate">
									<div class="col-xs-12 col-sm-7 col-md-7 col-lg-7 col-sm-push-5 col-md-push-5 col-lg-push-5">
										<div class="content-img">
											<img src="${sessionScope.deputycommissionerProfileImage}"
												alt="${sessionScope.deputyCommissionerName}"
												title="${sessionScope.deputyCommissionerName}"
												class="img-responsive">
										</div>
									</div>
									<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5 col-sm-pull-7 col-md-pull-7 col-lg-pull-7">
										<div class="minister-info">
											<h3>${sessionScope.deputyCommissionerName}</h3>
											<p class="designation">${sessionScope.deputycommissionerProfile}</p>
											<p class="minister-phone"><i class="fa fa-volume-control-phone" aria-hidden="true"></i> ${sessionScope.deputycommissionerSummaryEng}</p>
										</div>
									</div>
								</div>
								</c:if>
									
									<c:if test="${empty sessionScope.mayorProfile}">
										<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 element-animate">
											<div class="content-img">
												<img src="assets/img/Hon'ble-Minister.jpg"
													alt="Hon'ble-Minister" title="Hon'ble-Minister"
													class="img-responsive">
											</div><br/>
										</div>
									</c:if>
									<c:if test="${empty sessionScope.deputyMayorProfile}">
										<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 element-animate">
											<div class="content-img">
												<img src="assets/img/Hon'ble-Minister.jpg"
													alt="Hon'ble-Minister" title="Hon'ble-Minister"
													class="img-responsive">
											</div><br/>
										</div>
									</c:if>
									<c:if test="${empty sessionScope.commissionerProfile}">
										<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 element-animate">
											<div class="content-img">
												<img src="assets/img/Hon'ble-Minister.jpg"
													alt="Hon'ble-Minister" title="Hon'ble-Minister"
													class="img-responsive">
											</div><br/>
										</div>
									</c:if>
		
									<c:if test="${empty sessionScope.deputycommissionerProfile}">
										<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 element-animate">
											<div class="content-img">
												<img src="assets/img/Hon'ble-Minister.jpg"
													alt="Hon'ble-Minister" title="Hon'ble-Minister"
													class="img-responsive">
											</div><br/>
										</div>
									</c:if>
								</div>
							<%-- </nav>
							</div> --%>
						</div>
					</div>
				</div>
			</c:if>
			<%-- MINISTER END --%>
		
			<%-- <!-- E-Services Links  -->
			<c:forEach items="${command.getAllhtml('ESERVICES')}" var="eService"> ${eService} </c:forEach>
		    <!-- E-Services Links Ends  -->
		    
		    <!-- Temporary Kiosk Link starts  -->
		    <div class="text-center">
		    	<a href="CitizenHome.html?kiosk" title=""><spring:message code="" text="KIOSK LINK"/></a>
		    </div>
		    <!-- Temporary Kiosk Link ends  --> --%>
			
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
    
			<!-- Down Arrow Start -->
			<%-- <div class="mbr-arrow hidden-sm-down" aria-hidden="true">
				<a> <i class="fa fa-angle-down" aria-hidden="true"></i>
					<span><spring:message code="theme3.portal.scroll" text="Scroll"/></span>
				</a>
			</div> --%>
			<!-- Down Arrow End -->
			</div>
		</div>
			
		<div class="row r3">
			<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
				<%-- Announcements starts --%>
				<c:if test="${command.themeMap['NEWS'] ne 'I'}">
					<div class="announcements-section">
						<div class="widget section-main">
							<div class="widget-header">
								<h1>
									<spring:message code="theme4.portal.announcements" text="Announcements" />
								</h1>
							</div>
							<div class="widget-content element-animate">
								<ul>
								<marquee direction="up" onmouseover="stop()" onmouseout="start()" scrollamount="4">
								<c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status">	
								  <li>		  
								  <p>
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
										  <c:choose>
										      <c:when test="${not empty download}">			  
												  <apptags:filedownload filename="EIP"
													filePath="${download}"									
													actionUrl="CitizenHome.html?Download"
													eipFileName="${lookUp.announceDescEng}"></apptags:filedownload>
											  </c:when>
											  <c:otherwise>
											  	   ${lookUp.announceDescEng}
											  </c:otherwise>
										  </c:choose>
										  <c:if test="${not empty lookUp.link}">
											  <a href="${lookUp.link}" target="_blank"><i class="fa fa-external-link" aria-hidden="true"></i></a>
										  </c:if>
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
									  	  <c:choose>
										      <c:when test="${not empty download}">			  
												  <apptags:filedownload filename="EIP"
													filePath="${download}"
													actionUrl="CitizenHome.html?Download"
													eipFileName="${lookUp.announceDescReg}"></apptags:filedownload>
											  </c:when>
											  <c:otherwise>
											  	   ${lookUp.announceDescEng}
											  </c:otherwise>
										  </c:choose>
										  <c:if test="${not empty lookUp.link}">
											  <a href="${lookUp.link}" target="_blank"><i class="fa fa-external-link" aria-hidden="true"></i></a>
										  </c:if>
									  </c:otherwise>
									  </c:choose>
									  <br/><span class="date"><i class="fa fa-calendar" aria-hidden="true"></i>
									  <fmt:formatDate value="${lookUp.lmodDate}" pattern="dd-MM-yyyy" /></span>
									  </c:otherwise>
									  </c:choose>
									  </c:forEach>
								  </p>
								  </li> 
								 </c:forEach>
								 </marquee>
								 </ul>
							</div>
						</div>
					</div>	
				</c:if>
				<%-- Announcements ends --%>
				
				<%-- Media Gallery starts --%>
				<c:if test="${command.themeMap['PHOTO_GALLERY'] ne 'I'}">
					<div class="photo-boxx">
						<div id="photo-box">
							<div class="widget section-main">
								<div class="widget-header">
									<h1><spring:message code="PhotoGallery" text="Photo Gallery" /></h1>
								</div>
								<div class="widget-content photo-carousel-main element-animate" id="photo-carousel">
									<%-- Defect #162690 --%>
									<c:forEach items="${command.getAllhtml('Home Page Photo Gallery')}" var="homePagePhotoGallery"> ${homePagePhotoGallery} </c:forEach>
								</div>
								<div class="widget-btn-div">
									<a href="SectionInformation.html?editForm&rowId=&page=Photo Gallery Album"
										title="<spring:message code="" text="Photo Gallery"/>">
										<spring:message code="theme4.portal.more.photo.galleries" text="More Photo Galleries"/>
									</a>
								</div>
							</div>
						</div>
						<div class="vid-gal-section">
							<div class="widget section-main">
								<div class="widget-header">
									<h1><spring:message code="VideoGallery" text="Video Gallery"/></h1>
								</div>
								<div class="widget-content element-animate" id="">
									<c:forEach items="${command.getAllhtml('Home Page Video Gallery')}" var="homePageVideoGallery"> ${homePageVideoGallery} </c:forEach>
								</div>
								<div class="widget-btn-div">
									<a href="SectionInformation.html?editForm&rowId=&page=Video Gallery Album"
										title="<spring:message code="" text="More Video Galleries"/>">
										<spring:message code="theme4.portal.more.video.galleries" text="More Video Galleries"/>
									</a>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<%-- Media Gallery ends --%>
			</div>
			
			<!-- Citizen Services Start -->
			<c:if test="${not empty command.themeMap['CITIZEN_SERVICES'] && command.themeMap['CITIZEN_SERVICES'] ne 'I'}">
				<c:set var="citizenService"
					value="${command.getAllDepartmentAndServices()}" />
				<c:if test="${citizenService.size()>0}">
					<div class="col-xs-12 col-sm-9 col-md-9 col-lg-9 service-div citser" id="CitizenService">
						<h1>
							<spring:message code="theme4.portal.services" text="Aligarh Municipal Corporation Services"/>
						</h1>
						<div class="row">
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<c:forEach items="${command.getAllDepartmentAndServices()}"
									var="dept" varStatus="index">
									<c:set var="even" value="${(index.index)%2}"></c:set>
									<c:if test="${even eq '0'}">
										<div class="Services-tab item">
											<div class="folded-corner service_tab_AS">
												<div class="text">																						
													<h3>
														<i class="fa fa-list_${dept.departmentCode}"></i>
														<span class="ocs-label">${dept.departmentName}</span>
													</h3>
													<div class="clearfix"></div>
												</div>
												<div class="text-contents">
													<ul>
														<c:forEach items="${dept.childDTO}" var="serv"
															varStatus="index">
															<li><a href="javascript:void(0);"
																onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
																	 ${serv.serviceName}</a></li>
			
														</c:forEach>
													</ul>
												</div>	
											</div>
										</div>
									</c:if>
								</c:forEach>
							</div>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<c:forEach items="${command.getAllDepartmentAndServices()}"
									var="dept" varStatus="index">
									<c:set var="even" value="${(index.index)%2}"></c:set>
									<c:if test="${even ne '0'}">
										<div class="Services-tab item">
											<div class="folded-corner service_tab_AS">
												<div class="text">																						
													<h3>
														<i class="fa fa-list_${dept.departmentCode}"></i>
														<span class="ocs-label">${dept.departmentName}</span>
													</h3>
													<div class="clearfix"></div>
												</div>
												<div class="text-contents">
													<ul>
														<c:forEach items="${dept.childDTO}" var="serv"
															varStatus="index">
															<li><a href="javascript:void(0);"
																onclick="getCheckList('${serv.serviceCode}','${serv.serviceurl}','${serv.serviceName}')">
																	 ${serv.serviceName}</a></li>
			
														</c:forEach>
													</ul>
												</div>	
											</div>
										</div>
									</c:if>
								</c:forEach>
							</div>
						</div>
						<div class="row ck-serv-div">
							<c:forEach items="${command.getAllhtml('ASCL Services')}" var="asclServices"> ${asclServices} </c:forEach>
						</div>
					</div>
				</c:if>
			</c:if>
			<!-- Citizen Services End --> 
		</div>
		
		<div class="row r4">
			<div class="col-xs-12 col-sm-9 col-md-9 col-lg-9 col-sm-offset-3 col-md-offset-3 col-lg-offset-3">
				<div class="row">
					<%-- Mobile App starts --%>
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 mob-app-section" id="mobAppSection">
						<c:forEach items="${command.getAllhtml('Home Page Mobile App')}" var="homePageMobileApp"> ${homePageMobileApp} </c:forEach>
					</div>
					<%-- Mobile App ends --%>
					
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
						<div class="drag1">
							<%-- Information Desk starts --%>
							<c:if test="${command.themeMap['PUBLIC_NOTICE'] ne 'I'}">
								<div class="widget section-main">
									<div class="widget-header">
										<h1><spring:message code="theme4.portal.information.desk" text="Information Desk" /></h1>
									</div>
									<div class="widget-content element-animate">
										<div class="announcement scrolllistbox magazine-section">
											<c:if test="${command.usefull eq true}">
											 <c:set var="serial_count" scope="page" value="0"/>
												<c:forEach items="${command.publicNotices}" var="lookUp"
													varStatus="lk">
													  
													<c:if test="${lookUp.isUsefullLink eq 'Y' && empty lookUp.isHighlighted}">
														<div class="public-notice lst">
															<c:set var="link" value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
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
																		<c:when test="${not empty lookUp.profileImgPath}">
																			<c:choose>
																				<c:when
																					test="${userSession.getCurrent().getLanguageId() eq 1}">
																					<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																					<c:set var="exlink" value="${lookUp.link}" />																			 
																					<%-- <apptags:filedownload filename="EIP"
																						filePath="${lookUp.profileImgPath}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName="${lookUp.detailEn}"></apptags:filedownload> --%>
																						
																					<c:forEach items="${links}" var="downloadL" varStatus="count">
																					 <c:set var="lnk1" value="./cache/${downloadL}" />
																						<c:if test="${count.index eq 0 }">
																							<c:if test="${lnk1 ne './cache/'}">
																								<a href="${lnk1}" target="_blank" class="">
																								${lookUp.detailEn}<i class="fa fa-download"></i></a>
																							</c:if>
																							<c:if test="${lnk1 eq './cache/'}">
																								${lookUp.detailEn}
																							</c:if>
																						</c:if>
																						<c:if test="${count.index ne 0 }">
																						<a href="${lnk1}" target="_blank" class=""><i class="fa fa-download"></i></a>
																						</c:if>
																					</c:forEach>
																				</c:when>
																				<c:otherwise>
																					<%-- <apptags:filedownload filename="EIP"
																						filePath="${lookUp.profileImgPath}"
																						actionUrl="CitizenPublicNotices.html?Download"
																						eipFileName="${lookUp.detailReg}"></apptags:filedownload> --%>
																						
																					<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																					<c:set var="exlink" value="${lookUp.link}" />
																					
																					<c:forEach items="${links}" var="downloadL" varStatus="count">
																					 <c:set var="lnk1" value="./cache/${downloadL}" />
																						<c:if test="${count.index eq 0 }">
																							<c:if test="${lnk1 ne './cache/'}">
																								<a href="${lnk1}" target="_blank" class="">${lookUp.detailReg}<i class="fa fa-download"></i></a>
																							</c:if>
																							<c:if test="${lnk1 eq './cache/'}">
																								${lookUp.detailReg}
																							</c:if>
																						</c:if>
																						<c:if test="${count.index ne 0 }">
																						<a href="${lnk1}" target="_blank" class=""><i class="fa fa-download"></i></a>
																						</c:if>
																					</c:forEach>
																				</c:otherwise>
																			</c:choose>
																		</c:when>
																		<c:otherwise>
																			<c:if test="${lookUp.imagePath ne ' '}">																	
																			<c:set var="links" value="${fn:split(lookUp.imagePath,',')}" />
																				<c:set var="lnk1" value="./cache/${downloadL}" />																	
																				<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
																					<c:if test="${not empty lookUp.detailEn}">
																					<a href="${lookUp.link}" target="_self">
																					<div class="displayimage">
																						<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					 		<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailEn}">
																					 	</c:forEach>
																					 	</div>${lookUp.detailEn}
																					 </a>
																					</c:if>
																					<c:if test="${empty lookUp.detailEn}">
																					<div class="displayimage">
																					<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					 	<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailEn}">
																					 </c:forEach>
																					</div>
																					</c:if>
																				</c:if>
																				<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
																					<c:if test="${not empty lookUp.detailReg}">
																					<a href="${lookUp.link}" target="_self">
																					<div class="displayimage">
																						<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																							<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailReg}">
																						</c:forEach>
																						</div>${lookUp.detailReg}
																					</a>
																					</c:if>
																					<c:if test="${empty lookUp.detailReg}">
																					<div class="displayimage">
																						<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																							<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.detailReg}">
																						</c:forEach>
																					</div>
																					</c:if>
																				</c:if>
																			</c:if>
																		</c:otherwise>
																	</c:choose>															
																</c:otherwise>
															</c:choose>
															<p>
																<fmt:formatDate type="date" value="${lookUp.issueDate}"
																	pattern="dd/MM/yyyy" var="issueDate" />
																${issueDate}
															</p>
															<input type="hidden" name="downloadLink"
																value="${lookUp.profileImgPath}">
			
															<%-- 	<c:choose>
															<c:when test="${isDMS}">
																<a href="jadocIdvascript:void(0);"
																	onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.docName}</a>
															</c:when>
															<c:otherwise>
																<apptags:filedownload filename="EIP"
																	filePath="${lookUp.profileImgPath}"
																	actionUrl="CitizenPublicNotices.html?Download"
																	eipFileName="${stringUtility.getStringAfterChars(lookUp.profileImgPath)}"></apptags:filedownload>
															</c:otherwise>
														</c:choose> --%>
															
														</div>
													</c:if>
												</c:forEach>
											</c:if>
														
										</div>
										<div class="view-more-main">
											<a href="CitizenHome.html?schemes" class="view-more-btn" title="View More">
												<span><spring:message code="portal.link.viewmore" text="VIEW MORE"/></span>
											</a>
										</div>
									</div>
								</div>
							</c:if>
							<%-- Information Desk ends --%>
						</div>
					</div>
				</div>
			</div>

		</div>
			
	</div>
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
