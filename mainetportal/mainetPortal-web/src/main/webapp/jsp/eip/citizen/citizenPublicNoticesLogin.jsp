<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>

<ol class="breadcrumb">
	<li><a href="CitizenHome.html"><strong class="fa fa-home"></strong><span class="hide">Home</span></a></li>
	<li class="active"><spring:message code="eip.citizen.pbNotice.title"/></li>
</ol>
     
<div class="content">
	<div class="widget">
       <div class="widget-header">
         <h2><spring:message code="eip.citizen.pbNotice.title" /></h2>
       </div>
   <div class="widget-content padding">
		<div class="table-responsive">
			<table class="table table-striped table-bordered">
				<tr>
					<th><spring:message code="eip.citizen.pbNotice.srNo" /></th>
					<th><spring:message code="eip.citizen.pbNotice.issueDate" /></th>
					<th><spring:message code="eip.citizen.pbNotice.deptName" /></th>
					<th><spring:message code="eip.citizen.pbNotice.deptDesc" /></th>
					<th><spring:message code="eip.citizen.pbNotice.doc" /></th>
				</tr>

				<c:forEach items="${command.publicNotices}" var="lookUp"
					varStatus="lk">

					<tr>
						<td class="arial">${lk.count}</td>
						<td class="arial"><fmt:formatDate type="date"
								value="${lookUp.issueDate}" pattern="dd/MM/yyyy" var="issueDate" />
							${issueDate}</td>
						<td><c:choose>
								<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
											${lookUp.department.dpDeptdesc}
										</c:when>
								<c:otherwise>					
											${lookUp.department.dpNameMar}
										</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
											${lookUp.detailEn}
										</c:when>
								<c:otherwise>					
											${lookUp.detailReg}
										</c:otherwise>
							</c:choose></td>

						<td><input type="hidden" name="downloadLink"
							value="${lookUp.profileImgPath}"> <c:set var="filename"
								value="${stringUtility.getStringBeforeChar('/',lookUp.profileImgPath)}" />
							<c:set var="link"
								value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
							
								<apptags:filedownload filename="EIP" filePath="${link}"
									actionUrl="CitizenPublicNotices.html?Download"
									eipFileName="${stringUtility.getStringAfterChars(lookUp.profileImgPath)}"></apptags:filedownload>
							</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>
</div>
