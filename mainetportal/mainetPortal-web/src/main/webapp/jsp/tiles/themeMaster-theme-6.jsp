
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/validation.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="theme.label" text="Theme Master" />
			</h2>
			<apptags:helpDoc url="ThemeMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<%-- <div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div> --%>

			<div class="theme-master-main">
				<form:form action="ThemeMaster.html" class="form-horizontal" name="frmThemeMaster" id="frmThemeMaster" method="post">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<table id="main-table" class="table text-left table-striped table-bordered colOneAsc">
						<thead>
							<tr>
								<th><spring:message code="theme.items" text="ITEMS"/></th>
								<%-- <th><spring:message code="theme.pos" text="POSITION"/></th> --%>
								<th><spring:message code="theme.status" text="STATUS"/></th>
							</tr>
						</thead>
						<tbody>
							<%-- <tr>
								<td><form:hidden path=""/><spring:message code="theme.label.menu" text="MENU"/></td>
								<td><select class="form-control valid" id="select-01"
									name="select-01"><option value="0">Select</option>
										<option value="T"><spring:message code="theme.pos.top" text="TOP"/></option>
										<option value="L"><spring:message code="theme.pos.left" text="LEFT"/></option></select></td>
								<td><input type="checkbox" class="" id="text-checkbox"
									name="text-checkbox"></td>
							</tr>
							<tr>
								<td><spring:message code="theme.label.logo" text="LOGO"/></td>
								<td><select class="form-control valid" id="select-01"
									name="select-01"><option value="0">Select</option>
										<option value="L"><spring:message code="theme.pos.left" text="LEFT"/></option>
										<option value="R"><spring:message code="theme.pos.right" text="RIGHT"/></option></select></td>
								<td><input type="checkbox" class="" id="text-checkbox" name="text-checkbox"></td>
							</tr> --%>
							<tr>
								<td>
									<spring:message code="theme.label.footer" text="FOOTER"/>
									<form:hidden path="ThemeMasterDTOList[0].section" value="FOOTER" id=""/>
									<form:hidden path="ThemeMasterDTOList[0].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[0].status" value="A" aria-label="Footer"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme.lbl.slider" text="SLIDER IMAGES"/>
									<form:hidden path="ThemeMasterDTOList[1].section" value="SLIDER_IMG" id=""/>
									<form:hidden path="ThemeMasterDTOList[1].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[1].status" value="A" aria-label="Slider Images"/></td>
							</tr>
							<%-- <tr>
								<td>
									<spring:message code="CommitteeMembers" text="COMMITTEE MEMBERS"/>
									<form:hidden path="ThemeMasterDTOList[3].section" value="COMMITTEE_MEMBERS" id=""/>
									<form:hidden path="ThemeMasterDTOList[3].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[3].status" value="A" aria-label="Committee Members"/></td>
							</tr> --%>
							<tr>
								<td>
									<spring:message code="PhotoGallery" text="PHOTO GALLERY"/>
									<form:hidden path="ThemeMasterDTOList[7].section" value="PHOTO_GALLERY" id=""/>
									<form:hidden path="ThemeMasterDTOList[7].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[7].status" value="A" aria-label="Photo Gallery"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="VideoGallery" text="VIDEO GALLERY"/>
									<form:hidden path="ThemeMasterDTOList[8].section" value="VIDEO_GALLERY" id=""/>
									<form:hidden path="ThemeMasterDTOList[8].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[8].status" value="A" aria-label="Video Gallery"/></td>
							</tr>
							<tr>
								<td>
									
									<spring:message code="theme6.portal.disaster.alert" text="Disaster Alert" />
									<form:hidden path="ThemeMasterDTOList[13].section" value="DISASTER-ALERT" id=""/>
									<form:hidden path="ThemeMasterDTOList[13].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[13].status" value="A" aria-label="Disaster Alert"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.live" text="LIVE" />
									<form:hidden path="ThemeMasterDTOList[14].section" value="LIVE" id=""/>
									<form:hidden path="ThemeMasterDTOList[14].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[14].status" value="A" aria-label="LIVE"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.civic.message" text="Civic Message" />
									<form:hidden path="ThemeMasterDTOList[15].section" value="CIVIC-MESSAGE" id=""/>
									<form:hidden path="ThemeMasterDTOList[15].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[15].status" value="A" aria-label="Civic Message"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.corona.alert" text="Corona Alert" />
									<form:hidden path="ThemeMasterDTOList[16].section" value="CORONA-ALERT" id=""/>
									<form:hidden path="ThemeMasterDTOList[16].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[16].status" value="A" aria-label="Corona Alert"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.org.dropdown" text="Organization Dropdown" />
									<form:hidden path="ThemeMasterDTOList[17].section" value="ORGANIZATION_DROPDOWN" id=""/>
									<form:hidden path="ThemeMasterDTOList[17].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[17].status" value="A" aria-label="ORGANIZATION DROPDOWN"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.contact.us" text="Contact Us" />
									<form:hidden path="ThemeMasterDTOList[18].section" value="CONTACT_US" id=""/>
									<form:hidden path="ThemeMasterDTOList[18].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[18].status" value="A" aria-label="Contact Us"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.helpline.numbers" text="Helpline Numbers"/>
									<form:hidden path="ThemeMasterDTOList[19].section" value="HELPLINE_NUMBERS" id=""/>
									<form:hidden path="ThemeMasterDTOList[19].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[19].status" value="A" aria-label="Helpline Numbers"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.important.notices" text="Important Notices" />
									<form:hidden path="ThemeMasterDTOList[20].section" value="IMPORTANT_NOTICES" id=""/>
									<form:hidden path="ThemeMasterDTOList[20].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[20].status" value="A" aria-label="Important Notices"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.press.release" text="Press Release" />
									<form:hidden path="ThemeMasterDTOList[21].section" value="PRESS_RELEASE" id=""/>
									<form:hidden path="ThemeMasterDTOList[21].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[21].status" value="A" aria-label="Press Release"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.external.links" text="External Links" />
									<form:hidden path="ThemeMasterDTOList[22].section" value="EXTERNAL_LINKS" id=""/>
									<form:hidden path="ThemeMasterDTOList[22].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[22].status" value="A" aria-label="External Links"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.eservices" text="E-Services" />
									<form:hidden path="ThemeMasterDTOList[23].section" value="E_SERVICES" id=""/>
									<form:hidden path="ThemeMasterDTOList[23].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[23].status" value="A" aria-label="E-Services"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.on.going.projects" text="On-Going Projects" />
									<form:hidden path="ThemeMasterDTOList[24].section" value="ON_GOING_PROJECTS" id=""/>
									<form:hidden path="ThemeMasterDTOList[24].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[24].status" value="A" aria-label="On-Going Projects"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.quotations" text="Quotations" />
									<form:hidden path="ThemeMasterDTOList[25].section" value="QUOTATIONS" id=""/>
									<form:hidden path="ThemeMasterDTOList[25].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[25].status" value="A" aria-label="Quotations"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.kdmc.mobile.apps" text="KDMC Mobile Apps" />
									<form:hidden path="ThemeMasterDTOList[26].section" value="MOBILE_APPS" id=""/>
									<form:hidden path="ThemeMasterDTOList[26].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[26].status" value="A" aria-label="KDMC Mobile Apps"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.mayor" text="Mayor" />
									<form:hidden path="ThemeMasterDTOList[27].section" value="MAYOR" id=""/>
									<form:hidden path="ThemeMasterDTOList[27].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[27].status" value="A" aria-label="Mayor"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.commissioner" text="Commissioner" />
									<form:hidden path="ThemeMasterDTOList[28].section" value="COMMISSIONER" id=""/>
									<form:hidden path="ThemeMasterDTOList[28].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[28].status" value="A" aria-label="Commissioner"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="CitizenLogin" text="Citizen Login"/>
									<form:hidden path="ThemeMasterDTOList[29].section" value="CITIZEN_LOGIN" id=""/>
									<form:hidden path="ThemeMasterDTOList[29].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[29].status" value="A" aria-label="Citizen Login"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="DepartmentEmployeeLogin" text="Department Employee Login"/>
									<form:hidden path="ThemeMasterDTOList[30].section" value="DEPARTMENT_EMPLOYEE_LOGIN" id=""/>
									<form:hidden path="ThemeMasterDTOList[30].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[30].status" value="A" aria-label="Department Employee Login"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.subscribe.newsletter" text="Subscribe To Newsletter"/>
									<form:hidden path="ThemeMasterDTOList[31].section" value="SUBSCRIBE_NEWSLETTER" id=""/>
									<form:hidden path="ThemeMasterDTOList[31].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[31].status" value="A" aria-label="Subscribe To Newsletter"/></td>
							</tr>
							<%-- <tr>
								<td>
									<spring:message code="eip.citizen.footer.feedback" text="Feedback" />
									<form:hidden path="ThemeMasterDTOList[27].section" value="FEEDBACK" id=""/>
									<form:hidden path="ThemeMasterDTOList[27].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[27].status" value="A" aria-label="Feedback"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme6.portal.opinion.poll" text="Opinion Poll"/>
									<form:hidden path="ThemeMasterDTOList[28].section" value="OPINION_POLL" id=""/>
									<form:hidden path="ThemeMasterDTOList[28].themeId" id=""/>
								</td>
								<td><form:checkbox path="ThemeMasterDTOList[28].status" value="A" aria-label="Opinion Poll"/></td>
							</tr> --%>
						</tbody>
					</table>
					
					<div class="text-center margin-top-10">
						<apptags:submitButton entityLabelCode="bt.save" actionParam="saveform" cssClass="btn btn-success" successUrl="ThemeMaster.html"></apptags:submitButton>
						<apptags:resetButton buttonLabel="bt.clear"/>
						<apptags:backButton url="CitizenHome.html" buttonLabel="bt.back"/>
					</div>
				</form:form>
				</div>
			</div>
		</div>
	</div>
 