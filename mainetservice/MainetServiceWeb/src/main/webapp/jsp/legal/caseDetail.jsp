<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script>
	function printdiv(printpage) {
		var headstr = "<html><head><title></title><link href='assets/css/style-responsive.css' rel='stylesheet' type='text/css' /></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
</script>
<style>
.sectionSeperator {
	border-bottom: 1px solid #123456;
	border-top: 1px solid #123456;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong>
						<spring:message code="lgl.casedet"  text="Case Details" /></strong>
			</h2>

		</div>
			
		<div class="widget-content padding">
			<div class="col-sm-10 col-sm-offset-1" id="case">
				<table class="table table-bordered">
					<tr>
						<td class="text-center" colspan="2"><span class="text-bold"><spring:message code="lgl.case.organization.name" text="Organization Name" />
								 &nbsp;&nbsp;</span><span class="text-bold">:</span>&nbsp;&nbsp;
					<c:if test="${userSession.languageId eq 1}">
 							${userSession.organisation.ONlsOrgname}
					</c:if>
					<c:if test="${userSession.languageId ne 1}">
 							${userSession.organisation.ONlsOrgnameMar}
					</c:if></td>
					</tr>
					<tr>
						<th colspan="2" class="text-center"><spring:message code="lgl.casedet" text="Case Details" /></th>
					</tr>
					<tr>
						<td><div class="col-sm-4 col-xs-6 text-bold"><spring:message code="lgl.caseno" text="Case Number" />&nbsp;&nbsp;</div><span
							class="text-bold">:</span>&nbsp;&nbsp;${command.caseEntryDTO.cseSuitNo}</td>
						<td><div class="col-sm-5 col-xs-6 text-bold"><spring:message code="caseEntryDTO.cseDate" text="Case registration date" />
								&nbsp;&nbsp;</div><span class="text-bold">:&nbsp;&nbsp;</span>&nbsp;&nbsp;${command.caseEntryDTO.cseDate}</td>
					</tr>
					<tr>
						<td colspan="2" class="text-center"><span class="text-bold"><spring:message code="caseEntryDTO.cseName" text="Case name" />
								&nbsp;&nbsp;</span><span class="text-bold">:&nbsp;&nbsp;</span>&nbsp;&nbsp;${command.caseEntryDTO.cseName}</td>
					</tr>
					<tr>
						<th class="text-center"><spring:message code="caseEntryDTO.tbLglCasePddetails.csedName" text="Plaintiff/Petitioner name" /></th>
						<th class="text-center"><spring:message code="defender.csedName" text="Defender/Respondent name" /></th>
					</tr>
                         	<tr>
								<td class="text-center">
								<table style="margin:auto;" ><c:forEach items="${command.caseEntryDetailDTO}" var="data"
							    varStatus="loop">
							    <c:if test="${data.csedFlag eq 'P'}">
								<tr><td  style="border:none !important;text-align:center !important;"> ${data.csedName}</td></tr>
								</c:if>
								</c:forEach>
								</table>
								
								</td>
								<td class="text-center" >
								<table style="margin:auto;"><c:forEach items="${command.caseEntryDetailDTO}" var="data" 
							    varStatus="loop">
							    <c:if test="${data.csedFlag eq 'D'}">
								<tr><td style="border:none !important; text-align:center !important;">${data.csedName}</td></tr>
								</c:if>
								</c:forEach>
								</table>
								</td>
							 </tr>
					     <tr>
						<td colspan="2"><div class="col-sm-2 col-xs-6 text-bold"><spring:message code="lgl.matter.case" text="Matter of case" />
							&nbsp;&nbsp;</div> <span class="text-bold">:&nbsp;&nbsp;</span>${command.caseEntryDTO.cseMatdet1}</td>
					</tr>
					<%-- <tr>
						<td colspan="2"><div class="col-sm-2 col-xs-6 text-bold">Section
								Applied&nbsp;&nbsp;</div> <span class="text-bold">:&nbsp;&nbsp;</span>${command.caseEntryDTO.cseSectAppl}</td>
					</tr> --%>
					<tr>
						<c:set var="caseStatusId"
							value="${command.caseEntryDTO.cseCaseStatusId}" />
						<c:set var="lookUp"
							value="${command.getNonHierarchicalLookUpObject(caseStatusId)}" />
						<td colspan="2"><div class="col-sm-2  col-xs-6 text-bold"><spring:message code="caseEntryDTO.cseCaseStatusId" text=" Case Status" />
								&nbsp;&nbsp;</div> <span class="text-bold">:&nbsp;&nbsp;</span>${lookUp.lookUpDesc}</td>
					</tr>
					<tr>
						<td><div class="col-sm-4  col-xs-6 text-bold"><spring:message code="lgl.courtnm" text="Court name" />
								&nbsp;&nbsp;</div> <span class="text-bold">&nbsp;&nbsp:&nbsp;&nbsp;</span>${command.caseEntryDTO.crtName}</td>
						<td><div class="col-sm-4 col-xs-6 text-bold"><spring:message code="caseReferenceDTO.tbLglCaseReference.caseAdName" text="Advocate name" />
								&nbsp;&nbsp;</div> <span class="text-bold">&nbsp;&nbsp:&nbsp;&nbsp;</span>${command.advocateMasterDTO.advFirstNm}
							${command.advocateMasterDTO.advLastNm}</td>
					</tr>
					  	<!-- #D76897 -->
						<%-- <td><div class="col-sm-4 col-xs-6 text-bold">Judge
								name:</div> 
								<div class="col-sm-6 col-xs-6 ">
								<table><c:forEach items="${command.judgeNameList}" var="data" 
							    varStatus="loop">
								<tr><td class="text-center" style="border:none !important;">${data.judgeFName}  ${data.judgeLName}</td></tr>
								</c:forEach>
								</table></div>
						</td> --%>
						<!-- D#76896 -->
						<!-- D#81141 -->
						<c:set var = "oicLength"  value = "${fn:length(command.caseEntryDTO.tbLglCaseOICdetails)-1}"/>
						<td><div class="col-sm-4 col-xs-6 text-bold"><spring:message code="caseEntryDTO.tbLglCaseOICdetails.oicName" text="OIC name" />
								&nbsp;&nbsp;</div> <span class="text-bold">&nbsp;&nbsp:&nbsp;&nbsp;</span>${command.caseEntryDTO.tbLglCaseOICdetails[oicLength].oicName}</td>
						<td><div class="col-sm-4 col-xs-6 text-bold"><spring:message code="caseEntryDTO.tbLglCaseOICdetails.oicDesg" text="OIC Designation" />
								&nbsp;&nbsp;</div> <span class="text-bold">&nbsp;&nbsp:&nbsp;&nbsp;</span>${command.caseEntryDTO.tbLglCaseOICdetails[oicLength].oicDesg}</td>
					</tr>
					<tr>
						<th colspan="2" class="text-center"><spring:message code="lgl.case.other.details" text="Other Details" /></th>
					</tr>
					<tr>
						<td><div class="col-sm-5 col-xs-6 text-bold"><spring:message code="lgl.case.advocate.mobile.no" text="Advocate Mobile no" />
								&nbsp;&nbsp;</div> <span class="text-bold">:&nbsp;&nbsp;</span>${command.advocateMasterDTO.advMobile}
						</td>
						<td><div class="col-sm-5 col-xs-6 text-bold"><spring:message code="lgl.case.advocate.email.id" text="Advocate Email Id" />
								&nbsp;&nbsp;</div> <span class="text-bold">:&nbsp;&nbsp;</span>${command.advocateMasterDTO.advEmail}
						</td>
					</tr>
					<tr>
					  <c:set var = "oicLength"  value = "${fn:length(command.caseEntryDTO.tbLglCaseOICdetails)-1}"/>
						<td><div class="col-sm-5 col-xs-6  text-bold"><spring:message code="caseEntryDTO.tbLglCaseOICdetails.oicPhoneNo" text="OIC mobile no" />
								&nbsp;&nbsp;</div> <span class="text-bold">:&nbsp;&nbsp;</span>${command.caseEntryDTO.tbLglCaseOICdetails[oicLength].oicPhoneNo}
						</td>
						<td><div class="col-sm-5 col-xs-6 text-bold"><spring:message code="lgl.case.oic.emial.id" text="OIC email Id" />
								&nbsp;&nbsp;</div> <span class="text-bold">:&nbsp;&nbsp;</span>${command.caseEntryDTO.tbLglCaseOICdetails[oicLength].oicEmailId}
						</td>
					</tr>
				</table>

			</div>
			<div class="clear"></div>
			<div class="text-center hidden-print padding-20">
				<button onclick="printdiv('case')"
					class="btn btn-primary hidden-print">
					<i class="fa fa-print"></i>
					<spring:message code="lgl.prnt" text="Print" />
				</button>
				<apptags:backButton url="CaseEntry.html"></apptags:backButton>
			</div>

		</div>

	</div>

</div>
