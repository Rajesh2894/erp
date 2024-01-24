<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>
 <link rel="stylesheet" type="text/css" href= "./css/mainet/section-info.css" /> 
     <ol class="breadcrumb">
	  <li><a href="index.html"><strong class="fa fa-home"></strong><span class="hide">Home</span></a></li>
      <li class="active"><spring:message code="eip.citizen.pbNotice.title"/></li>
     </ol>
		<div id="content" class="content">
				<%-- <h2><spring:message code="eip.citizen.pbNotice.title"/></h2> --%>
				
				<div class="row">
				<div class="col-md-12 portlets">
        		<div class="widget">
          			<div class="widget-header ">
          			
          	          <h2> <spring:message code="eip.citizen.pbNotice.title"/></h2>
          			
          			 <div class="additional-btn"> <a href="#" class="widget-toggle"><i class="icon-down-open-2"></i></a> </div>
          		    </div>
				 <div class="widget-content">
            		<div class="table-responsive">
              		
                		<table data-sortable class="table table-hover">
						   <thead>
							<tr>
								<td><spring:message code="eip.citizen.pbNotice.srNo"/></td>
								<td><spring:message code="eip.citizen.pbNotice.issueDate"/></td>
								<td><spring:message code="eip.citizen.pbNotice.deptName"/></td>
								<td><spring:message code="eip.citizen.pbNotice.deptDesc"/></td>
								<td><spring:message code="eip.citizen.pbNotice.doc"/></td>
							</tr>
						
						    </thead> 
						     
							<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
							
							<tr>
								<td>${lk.count}</td>
								<td>
									<fmt:formatDate type="date" value="${lookUp.issueDate}" pattern="dd/MM/yyyy" var="issueDate" />
								${issueDate}</td>
								<td>
									<c:choose>
										<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
											${lookUp.department.dpDeptdesc}
										</c:when>	
										<c:otherwise>					
											${lookUp.department.dpNameMar}
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
											${lookUp.detailEn}
										</c:when>	
										<c:otherwise>					
											${lookUp.detailReg}
										</c:otherwise>
									</c:choose>
								</td>
								 
								<td>	
								<input type="hidden" name="downloadLink" value="${lookUp.profileImgPath}">	
								  <c:set var="filename" value="${stringUtility.getStringBeforeChar('/',lookUp.profileImgPath)}"/>
		                  	 <c:set var="link"
							value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
						
                               <apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${stringUtility.getStringAfterChars(lookUp.profileImgPath)}"></apptags:filedownload>
	                      
						</td>
							</tr>
							</c:forEach>
						</table>
						
						</div>
					</div>
				</div>
			</div>
			</div>
			</div>
			
