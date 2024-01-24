
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
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>

			<form:form action="ThemeMaster.html" class="form-horizontal" name="frmThemeMaster" id="frmThemeMaster" method="post">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				
				<div class="theme-master">
					<table id="main-table" class="table text-left table-striped table-bordered margin-bottom-10">
						<thead>
							<tr>
								<th><spring:message code="theme.items" text="ITEMS"/></th>
								<%-- <th><spring:message code="theme.pos" text="POSITION"/></th> --%>
								<th><spring:message code="theme.status" text="STATUS"/></th>
							</tr>
						</thead>
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
						<tbody>
							<tr>
								<td><spring:message code="theme.label.footer" text="FOOTER"/><form:hidden path="ThemeMasterDTOList[0].section" value="FOOTER" id=""/><form:hidden path="ThemeMasterDTOList[0].themeId" id=""/></td>
								<td><form:checkbox path="ThemeMasterDTOList[0].status" value="A" aria-label="Footer"/></td>
							</tr>
							<tr>
								<td><spring:message code="theme.lbl.slider" text="SLIDER IMAGES"/><form:hidden path="ThemeMasterDTOList[1].section" value="SLIDER_IMG" id=""/><form:hidden path="ThemeMasterDTOList[1].themeId" id=""/></td>
								<td><form:checkbox path="ThemeMasterDTOList[1].status" value="A" aria-label="Slider Images"/></td>
							</tr>
							<tr>
								<td><spring:message code="PublicNotice" text="PUBLIC NOTICE"/><form:hidden path="ThemeMasterDTOList[2].section" value="PUBLIC_NOTICE" id=""/><form:hidden path="ThemeMasterDTOList[2].themeId" id=""/></td>
								<td> <form:checkbox path="ThemeMasterDTOList[2].status" value="A" aria-label="Public Notices"/></td>
							</tr>
							<tr>
								<td><spring:message code="CommitteeMembers" text="COMMITTEE MEMBERS"/><form:hidden path="ThemeMasterDTOList[3].section" value="COMMITTEE_MEMBERS" id=""/><form:hidden path="ThemeMasterDTOList[3].themeId" id=""/></td>
								<td> <form:checkbox path="ThemeMasterDTOList[3].status" value="A" aria-label="Committee Members"/></td>
							</tr>
							<tr>
								<td><spring:message code="theme.lbl.keyContacts" text="KEY CONTACTS"/><form:hidden path="ThemeMasterDTOList[4].section" value="KEY_CONTACTS" id=""/><form:hidden path="ThemeMasterDTOList[4].themeId" id=""/></td>
								<td> <form:checkbox path="ThemeMasterDTOList[4].status" value="A" aria-label="Key Contacts"/></td>
							</tr>
							<tr>
								<td><spring:message code="theme.lbl.helplineNo" text="HELPLINE NUMBERS"/><form:hidden path="ThemeMasterDTOList[5].section" value="HELPLINE_NO" id=""/><form:hidden path="ThemeMasterDTOList[5].themeId" id=""/></td>
								<td> <form:checkbox path="ThemeMasterDTOList[5].status" value="A" aria-label="Helpline Numbers"/></td>
							</tr>
							<tr>
								<td><spring:message code="News" text="NEWS"/><form:hidden path="ThemeMasterDTOList[6].section" value="NEWS" id=""/><form:hidden path="ThemeMasterDTOList[6].themeId" id=""/></td>
								<td> <form:checkbox path="ThemeMasterDTOList[6].status" value="A" aria-label="News"/></td>
							</tr>
							<tr>
								<td><spring:message code="PhotoGallery" text="PHOTO GALLERY"/><form:hidden path="ThemeMasterDTOList[7].section" value="PHOTO_GALLERY" id=""/><form:hidden path="ThemeMasterDTOList[7].themeId" id=""/></td>
								<td> <form:checkbox path="ThemeMasterDTOList[7].status" value="A" aria-label="Photo Gallery"/></td>
							</tr>
							<tr>
								<td><spring:message code="VideoGallery" text="VIDEO GALLERY"/><form:hidden path="ThemeMasterDTOList[8].section" value="VIDEO_GALLERY" id=""/><form:hidden path="ThemeMasterDTOList[8].themeId" id=""/></td>
								<td> <form:checkbox path="ThemeMasterDTOList[8].status" value="A" aria-label="Video Gallery"/></td>
							</tr>
							<tr>
								<td><spring:message code="theme.lbl.schemes" text="ABOUT US"/><form:hidden path="ThemeMasterDTOList[9].section" value="SCHEMES" id=""/><form:hidden path="ThemeMasterDTOList[9].themeId" id=""/></td>
								<td> <form:checkbox path="ThemeMasterDTOList[9].status" value="A" aria-label="ABOUT US"/></td>
							</tr>
							<tr>
								<td><spring:message code="theme.lbl.links" text="IMPORTANT LINKS"/><form:hidden path="ThemeMasterDTOList[10].section" value="IMP_LINKS" id=""/><form:hidden path="ThemeMasterDTOList[10].themeId" id=""/></td>
								<td> <form:checkbox path="ThemeMasterDTOList[10].status" value="A" aria-label="Important Links"/></td>
							</tr>
							<tr>
								<td><spring:message code="theme.citizenServices" text="CITIZEN SERVICES"/><form:hidden path="ThemeMasterDTOList[11].section" value="CITIZEN_SERVICES" id=""/><form:hidden path="ThemeMasterDTOList[11].themeId" id=""/></td>
								<td> <form:checkbox path="ThemeMasterDTOList[11].status" value="A" aria-label="Citizen Services"/></td>
							</tr>
							<tr>
								<td><spring:message code="theme.externalServices" text="DEPARTMENT SERVICES"/><form:hidden path="ThemeMasterDTOList[12].section" value="EXTERNAL_SERVICES" id=""/><form:hidden path="ThemeMasterDTOList[11].themeId" id=""/></td>
								<td> <form:checkbox path="ThemeMasterDTOList[12].status" value="A" aria-label="DEPARTMENT Services"/></td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme5.portal.civic.message" text="Civic Message" />
									<form:hidden path="ThemeMasterDTOList[13].section" value="CIVIC_MESSAGE" id=""/>
									<form:hidden path="ThemeMasterDTOList[13].themeId" id=""/>
								</td>
								<td>
									<form:checkbox path="ThemeMasterDTOList[13].status" value="A" aria-label="Civic Message"/>
								</td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme5.portal.budget.finance" text="Budget & Finance" />
									<form:hidden path="ThemeMasterDTOList[14].section" value="BUDGET_FINANCE" id=""/>
									<form:hidden path="ThemeMasterDTOList[14].themeId" id=""/>
								</td>
								<td>
									<form:checkbox path="ThemeMasterDTOList[14].status" value="A" aria-label="Budget & Finance"/>
								</td>
							</tr>
							<tr>
								<td>
									<spring:message code="theme5.portal.quick.links" text="Quick Links" />
									<form:hidden path="ThemeMasterDTOList[15].section" value="QUICK_LINKS" id=""/>
									<form:hidden path="ThemeMasterDTOList[15].themeId" id=""/>
								</td>
								<td>
									<form:checkbox path="ThemeMasterDTOList[15].status" value="A" aria-label="Quick Links"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<div class="text-center">
					<apptags:submitButton entityLabelCode="bt.save" actionParam="saveform" cssClass="btn btn-success" successUrl="ThemeMaster.html"></apptags:submitButton>
					<apptags:resetButton buttonLabel="bt.clear"/>
					<apptags:backButton url="CitizenHome.html" buttonLabel="bt.back"/>
				</div>
			</form:form>
		</div>
	</div>
</div>
 