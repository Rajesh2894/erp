<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%  response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<ol class="breadcrumb"  id="CitizenService">
   <li>
      <a href="CitizenHome.html">
         <i class="fa fa-home"></i> 
         <spring:message code="menu.home" />
      </a>
   </li>
   <li class="active">
      <spring:message
         code="fast.search.result.header" />
   </li>
</ol>
<div class="content"><br/>
   <!-- Start info box -->
   <div class="widget">
      <div class="widget-header">
         <h2>
            <spring:message code="fast.search.result" />
         </h2>
      </div>
      <div class="widget-content padding sitemap">
         <ul class="search-list searchResult">
            <c:if test="${not empty command.propList}">
               <c:forEach items="${command.propList}" var="list1">
                  <c:set var="parts" value="${fn:split(list1.name,',')}"></c:set>
                  <c:choose>
                     <c:when test="${parts[1] eq 'OK'}">
                        <li>
                           <a href="${parts[2]}">
                              <spring:message
                                 code="${parts[0]}" text="Please click" />
                           </a>
                        </li>
                     </c:when>
                     <c:otherwise>
                        <li>
                           <a href="javascript:void(0);" onclick="${parts[1]}">
                              <spring:message
                                 code="${parts[0]}" text="Please click" />
                           </a>
                        </li>
                     </c:otherwise>
                  </c:choose>
               </c:forEach>
            </c:if>
            <c:if test="${not empty command.details}">
               <c:forEach items="${command.details}" var="list1">
                  <li><a href="#" onclick="openRelatedForm('${list1.smfaction}','this');">${list1.smfname}</a></li>
               </c:forEach>
            </c:if>
            <c:if test="${not empty command.linksMasterList}">
               <c:forEach items="${command.linksMasterList}" var="list1">
                  <li>
                     <c:choose>
                        <c:when test="${empty list1.subLinkMasters}">
                           <c:choose>
                              <c:when test="${fn:startsWith(list1.linkPath,'http')}">
                                 <c:set value="#" var="hrefValue" />
                                 <c:set value="showConfirm('${list1.linkPath}');" var="onClickValue" />
                              </c:when>
                              <c:otherwise>
                                 <c:set value="${list1.linkPath}" var="hrefValue" />
                                 <c:set value="" var="onClickValue" />
                              </c:otherwise>
                           </c:choose>
                           <a href="${hrefValue}" onclick="${onClickValue}">
                              <c:choose>
                                 <c:when test="${userSession.getCurrent().getLanguageId() == 1}">${list1.linkTitleEg}</c:when>
                                 <c:otherwise>${list1.linkTitleReg}</c:otherwise>
                              </c:choose>
                           </a>
                        </c:when>
                        <c:otherwise>
                           <c:choose>
                              <c:when test="${userSession.getCurrent().getLanguageId() == 1}">${list1.linkTitleEg}</c:when>
                              <c:otherwise>${list1.linkTitleReg}</c:otherwise>
                           </c:choose>
                           <c:forEach items="${list1.subLinkMasters}" var="sublink">
                              <ul class="tree">
                                 <li>
                                    <c:if test="${sublink.hasSubLink eq 'N'}">
                                       <a href="SectionInformation.html?editForm&rowId=${sublink.id}&page=">
                                          <c:choose>
                                             <c:when test="${userSession.getCurrent().getLanguageId() == 1}">${sublink.subLinkNameEn}</c:when>
                                             <c:otherwise>${sublink.subLinkNameRg}</c:otherwise>
                                          </c:choose>
                                       </a>
                                    </c:if>
                                 </li>
                              </ul>
                           </c:forEach>
                        </c:otherwise>
                     </c:choose>
                  </li>
               </c:forEach>
            </c:if>
            <%-- <c:if test="${not empty command.subLinkMasterList}">
               <c:forEach items="${command.subLinkMasterList}" var="list1">
                  <li>
                     <c:if test="${list1.hasSubLink eq 'N'}">
                        <a href="SectionInformation.html?editForm&rowId=${list1.id}&page=">
                           <c:choose>
                              <c:when test="${userSession.getCurrent().getLanguageId() == 1}">${list1.subLinkNameEn}</c:when>
                              <c:otherwise>${list1.subLinkNameRg}</c:otherwise>
                           </c:choose>
                        </a>
                     </c:if>
                  </li>
               </c:forEach>
            </c:if>
            <c:if test="${not empty command.subLinkFieldDetailsCKeditorList}">
               <c:forEach items="${command.subLinkFieldDetailsCKeditorList}" var="list1">
                  <li>
                     <c:if test="${list1.hasSubLink eq 'N'}">
                        <a href="SectionInformation.html?editForm&rowId=${list1.id}&page=">
                           <c:choose>
                              <c:when test="${userSession.getCurrent().getLanguageId() == 1}">${list1.subLinkNameEn}</c:when>
                              <c:otherwise>${list1.subLinkNameRg}</c:otherwise>
                           </c:choose>
                        </a>
                     </c:if>
                  </li>
               </c:forEach>
            </c:if> --%>
             <c:if test="${not empty command.subLinkMasterList || not empty command.subLinkFieldDetailsCKeditorList}">
           		<c:if test="${not empty command.finalSubLinkMasterList}">
	               <c:forEach items="${command.finalSubLinkMasterList}" var="list1">
	                  <li>
	                     <c:if test="${list1.hasSubLink eq 'N'}">
	                        <a href="SectionInformation.html?editForm&rowId=${list1.id}&page=">
	                           <c:choose>
	                              <c:when test="${userSession.getCurrent().getLanguageId() == 1}">${list1.subLinkNameEn}</c:when>
	                              <c:otherwise>${list1.subLinkNameRg}</c:otherwise>
	                           </c:choose>
	                        </a>
	                     </c:if>
	                  </li>
	               </c:forEach>
	            </c:if>
             </c:if>
            <c:if test="${empty command.propList && empty command.details && empty command.linksMasterList && empty command.subLinkMasterList && empty command.subLinkFieldDetailsCKeditorList}">
               <div class="alert alert-danger text-center padding-10" role="alert">
                  <h3>
                     <spring:message code="search.no.result"></spring:message>
                  </h3>
               </div>
            </c:if>
         </ul>
      </div>
   </div>
</div>