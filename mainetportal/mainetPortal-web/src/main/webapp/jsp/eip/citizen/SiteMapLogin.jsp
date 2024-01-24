<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<meta charset="utf-8">
<script>
	function showConfirm(form) {

		var childDivName = '.child-popup-dialog';
		var message = ' ';
		var msg1 = getLocalMessage("tp.red1");
		var msg2 = getLocalMessage("tp.red2");
		var btnMsg = getLocalMessage("bt.ok");

		message += '<div class="sucess">';
		message += '<h3>' + msg1 + ' </br>' + msg2 + '</h3>';
		message += '<div class="btn_fld">';
		message += '<input type="button" class="css_btn" value=' + btnMsg
				+ ' onclick=OpenNewTab("' + form + '"); />';
		message += '</div>';
		message += '</div>';

		$(childDivName).html(message);
		showModalBox(childDivName);
	}

	function OpenNewTab(url) {
		var win = window.open(url, '_blank');
		win.focus();
		closeFancyOnLinkClick('.child-popup-dialog');

	}
</script>

<ol class="breadcrumb margin-top-45">
	<li><a href="CitizenHome.html"><i class="fa fa-home"></i></a></li>
	<li class="active"><spring:message code="eip.sitemap" /></li>
</ol>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="eip.sitemap" />
			</h2>
		</div>
		<div class="widget-content padding">



			<div class="sitemap">
				<div class="row">
					
					
						
								<!-- <ul class="nav nav-pills" id="tabmenuhover"> -->
								<ul class="nav nav-pills">
					<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
					<div class="col-sm-6 col-md-3">
					<div class="categTree">
						<li class="dropdown" id="${masters.entitle.smfid}">
							<a class="dropdown-toggle" href="${masters.entitle.smfaction}" title="${masters.entitle.smfname}" onclick="dodajAktywne(this)">${masters.entitle.smfname}</a>
								<ul class="tree">
								<c:forEach items="${menuRoleEntitlement.childList}" var="data"  varStatus="count">
							
								   <c:if test="${masters.entitle.smfid eq  data.parentId}">
										 <c:set var="action0" value="${data.entitle.smfaction}" />
										
										<li id="${data.entitle.smfid}">
										<a href=<c:choose><c:when test="${fn:containsIgnoreCase(action0 , '.html')}">"javascript:openRelatedForm('${action0}','this');"</c:when><c:otherwise>${action0}</c:otherwise></c:choose> >${data.entitle.smfname} </a> 
											<ul class="tree">
												<c:forEach items="${menuRoleEntitlement.childList}"
													var="data1">
													
													<c:if test="${data.entitle.smfid eq  data1.parentId}">
														
														
														
												<c:set var="action1" value="${data1.entitle.smfaction}" />
												
												
												
												<li id="${data1.entitle.smfid}"><a href=<c:choose><c:when test="${fn:containsIgnoreCase(action1 , '.html')}">"javascript:openRelatedForm('${action1}');"</c:when><c:otherwise>${action1}</c:otherwise></c:choose>>${data1.entitle.smfname}</a>
															<ul class="tree">
																<c:forEach items="${menuRoleEntitlement.childList}"
																	var="data2">
																	
																	<c:if test="${data1.entitle.smfid eq  data2.parentId}">
																	  <c:set var="action2" value="${data2.entitle.smfaction}" />
																		<li id="${data2.entitle.smfid}"><a
																			href=<c:choose><c:when test="${fn:containsIgnoreCase(action2 , '.html')}">"javascript:openRelatedForm('${action2}');"</c:when><c:otherwise>${action2}</c:otherwise></c:choose>>${data2.entitle.smfname}</a>
																			<ul>
																				<c:forEach items="${menuRoleEntitlement.childList}"
																					var="data3">
																					<c:if
																						test="${data2.entitle.smfid eq  data3.parentId}">
																						<c:set var="action3"
																							value="${data3.entitle.smfaction}" />
																						<li id="${data3.entitle.smfid}"><a
																							<c:choose><c:when test="${fn:containsIgnoreCase(action3 , '.html')}">"javascript:openRelatedForm('${action3}');"</c:when><c:otherwise>${action3}</c:otherwise></c:choose>>${data3.entitle.smfname}</a></li>
																					</c:if>
																				</c:forEach>
																			</ul></li>
																	</c:if>
																</c:forEach>
															</ul></li>
													</c:if>
												</c:forEach>
											</ul></li>
									</c:if>
								</c:forEach>
							</ul>
							
							</li>
						</div>	
						</div>
					</c:forEach>
					
					<div class="col-md-2">
						<div class="categTree">
							<a title=" Main Menu" href="CitizenHome.html"><spring:message
									code="" text="Main Menu" /></a>
							<ul class="tree">
								<li><a href="CitizenAboutUs.html"><spring:message
											code="top.aboutus" /></a></li>
								<li><a href="CitizenPublicNotices.html"><spring:message
											code="top.notices" /></a></li>
								<li><a href="CitizenFAQ.html?getFAQ"><spring:message
											code="top.faq" /></a></li>
								<li><a href="CitizenContactUs.html"><spring:message
											code="eip.citizen.footer.contactUs" /></a></li>
								<li><a href="CitizenFeedBack.html"><spring:message
											code="eip.citizen.footer.feedback" /></a></li>
							<%-- 	<li><a href="javascript:void(0);"
									onclick="openInformation('name=policy')"><spring:message
											code="eip.citizen.footer.privacyPolicy" /></a></li>
								<li><a href="javascript:void(0);"
									onclick="openInformation('name=terms')"><spring:message
											code="eip.citizen.footer.termsCondition" /></a></li>
								<li><a href="javascript:void(0);"
									onclick="openInformation('name=refund')"><spring:message
											code="eip.citizen.footer.refundCancellation" /></a></li> --%>
							</ul>
						</div>
					</div>
					
						
					</ul>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>

