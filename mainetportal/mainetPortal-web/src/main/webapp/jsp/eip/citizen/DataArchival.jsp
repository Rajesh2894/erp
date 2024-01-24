<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
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
   						$("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active");
   						$($.fn.dataTable.tables(true)).DataTable().columns.adjust().responsive.recalc();
   					});
   			
   		}); 
   
   
	  
   
</script>
<style>
.displayimagef,.displayimage{float: left;height:90px;overflow:hidden}
.displayimagef img{height: 90px;width: 100%;}
.displayimage img{height: 90px;width: 100px;margin-right: 10px;}
#collapseTwo .scrolllistbox1 .displayimage img{height: 50px;width: 80px;margin-right: 10px;}
#collapseTwo .scrolllistbox1 .displayimage {height:50px;overflow:hidden}
</style>
<ol class="breadcrumb" id="CitizenService">
   <li class="breadcrumb-item">
      <a href="CitizenHome.html">
         <i class="fa fa-home"></i> 
         <spring:message code="home"/>
      </a>
   </li>
   <li class="breadcrumb-item active">
      <spring:message code="lbl.archiveData" text="Archive Data"/>
   </li>
</ol>
<div class="container-fluid dashboard-page" id="datarch">
   <div class="col-sm-12" id="nischay">
   		<h2>
         <spring:message code="lbl.archiveData" text="Archive Data"/>
      </h2>   
      <div class="widget">
         <div class="widget-content padding">
            <div class="col-sm-12">
               <form action="" method="get" class="form-horizontal" novalidate>
                  <div class="panel-group accordion-toggle"
                     id="accordion_single_collapse">
                     <div class="panel panel-default">
                        <div class="panel-heading margin-bottom-20">
                           <%-- <h4 class="panel-title">
                              <spring:message code="lbl.data.archival" text="Data Archival"/>
                           </h4> --%>
                        </div>
                        <div id="accordion1" class="panel-collapse collapse in">
                           <div class="panel-body">
                              <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 bhoechie-tab-container">
                                 <c:set var="sectionList" value="${command.getArchiveSection()}" />
                                 <div
                                    class="col-lg-2 col-md-2 col-sm-4 col-xs-12 bhoechie-tab-menu">
                                    <div class="list-group ">
                                       <a href="#" class="list-group-item text-center active">
                                          <spring:message code="portal.Scheme" text="Schemes"/>
                                       </a>
                                       <a href="#" class="list-group-item text-center">
                                          <spring:message code="eip.highlightes" text="Highlightes"/>
                                       </a>
                                       <a href="#"	class="list-group-item text-center">
                                          <spring:message code="eip.announcements" text="Announcements"/>
                                       </a>
                                       <c:forEach items="${sectionList}" var="section">
                                          <a href="#" class="list-group-item text-center">
                                          ${section.lookUpCode}</a>
                                       </c:forEach>
                                    </div>
                                 </div>
                                 <div class="col-lg-10 col-md-10 col-sm-8 col-xs-12 bhoechie-tab">
                                    <%-- --------------------------------------------Schemes----------------------------------------------- --%>														
                                    <div class="bhoechie-tab-content active">
                                      <%--  <h3>
                                          <spring:message code="" text="Schemes"/>
                                       </h3> --%>
                                       <table class="table table-bordered dataTableClass">
                                          <thead>
                                             <tr>
                                                <th>
                                                   <spring:message code="rti.srno" text="SR NO"/>
                                                </th>
                                                <th>
                                                   <spring:message code="portal.Scheme" text="SCHEMES"/>
                                                </th>
                                                <th>
                                                   <spring:message code="portal.archivaldate" text="ARCHIVAL DATE"/>
                                                </th>
                                             </tr>
                                          </thead>
                                          <tbody>
                                             <c:set var="schemeCount" value="0" scope="page"/>
                                             <c:if test="${command.scheme eq true}">
                                                <c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
                                                   <c:if test="${lookUp.isHighlighted ne 'Y' && lookUp.isUsefullLink ne 'Y'}">
                                                      <c:set var="schemeCount" value="${schemeCount + 1}" scope="page"/>
                                                      <tr>
                                                         <td>${schemeCount}</td>
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
																			<td>	
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="./cache/${downloadL}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">
																						${lookUp.detailEn}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.detailEn}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																			</c:forEach>
																			</td>
																		</c:when>
																		<c:otherwise>
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.detailReg}"></apptags:filedownload> --%>
																				
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<td>
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="./cache/${downloadL}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.detailReg}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.detailReg}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																			</c:forEach>
																			</td>
																		</c:otherwise>
																	</c:choose>
																</c:when>
                                                            <c:otherwise>
																	<c:if test="${not empty lookUp.imagePath}">																	
																	<c:set var="links" value="${fn:split(lookUp.imagePath,',')}" />
																		<c:set var="lnk1" value="./cache/${downloadL}" />																	
																		<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>
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
																		</td>	
																		</c:if>
																		<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
																		<td>
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
																			</td>
																		</c:if>
																	</c:if>
																</c:otherwise>
                                                         </c:choose>
                                                         <td>${lookUp.validityDate}</td>
                                                      </tr>
                                                   </c:if>
                                                </c:forEach>
                                             </c:if>
                                          </tbody>
                                       </table>
                                    </div>
                                    <%-- --------------------------------------------Schemes End----------------------------------------------- --%>														
                                    <%-- --------------------------------------------Highlightes----------------------------------------------- --%>														
                                    <div class="bhoechie-tab-content">
                                       <%-- <h3>
                                          <spring:message code="" text="Highlightes"/>
                                       </h3> --%>
                                       <table class="table table-bordered dataTableClass">
                                          <thead>
                                             <tr>
                                                <th>
                                                   <spring:message code="rti.srno" text="SR NO"/>
                                                </th>
                                                <th>
                                                   <spring:message code="eip.highlightes" text="HIGHLIGHTES"/>
                                                </th>
                                                <th>
                                                   <spring:message code="portal.archivaldate" text="ARCHIVAL DATE"/>
                                                </th>
                                             </tr>
                                          </thead>
                                          <tbody>
                                             <c:set var="count" value="0" scope="page"/>
                                             <c:set var="hgcount" value="0" scope="page"/>
                                             <c:if test="${command.highlighted eq true}">
                                                <c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
                                                   <c:if test="${lookUp.isHighlighted eq 'Y'}">
                                                      <c:set var="count" value="${count + 1}" scope="page"/>
                                                       <c:set var="hgcount" value="${hgcount + 1}" scope="page"/>
                                                      <tr>
                                                         <td>${hgcount}</td>
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
																			<td>	
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="./cache/${downloadL}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">
																						${lookUp.detailEn}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.detailEn}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																			</c:forEach>
																			</td>
																		</c:when>
																		<c:otherwise>
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.detailReg}"></apptags:filedownload> --%>
																				
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<td>
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="./cache/${downloadL}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.detailReg}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.detailReg}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																			</c:forEach>
																			</td>
																		</c:otherwise>
																	</c:choose>
																</c:when>
                                                            <c:otherwise>
																	<c:if test="${not empty lookUp.imagePath}">																	
																	<c:set var="links" value="${fn:split(lookUp.imagePath,',')}" />
																		<c:set var="lnk1" value="./cache/${downloadL}" />																	
																		<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>
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
																		</td>	
																		</c:if>
																		<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
																		<td>
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
																			</td>
																		</c:if>
																	</c:if>
																</c:otherwise>
                                                         </c:choose>
                                                         <td>${lookUp.validityDate}</td>
                                                      </tr>
                                                   </c:if>
                                                </c:forEach>
                                             </c:if>
                                          </tbody>
                                       </table>
                                    </div>
                                    <%-- --------------------------------------Highlightes End--------------------------------- --%>	
                                    <%-- --------------------------------------Announcement Start ----------------------------------- --%>		
                                    <div class="bhoechie-tab-content">
                                       <%-- <h3>
                                          <spring:message code="" text="Announcements"/>
                                       </h3> --%>
                                       <table class="table table-bordered dataTableClass">
                                          <thead>
                                             <tr>
                                                <th>
                                                   <spring:message code="rti.srno" text="SR NO"/>
                                                </th>
                                                <th>
                                                   <spring:message code="eip.announcements" text="ANNOUNCEMENT"/>
                                                </th>
                                                <th>
                                                   <spring:message code="portal.archivaldate" text="ARCHIVAL DATE"/>
                                                </th>
                                             </tr>
                                          </thead>
                                          <tbody>
                                             <c:if test="${command.getEipAnnouncement().size() gt 0 }">
                                             	
                                                <c:forEach items="${command.getEipAnnouncement()}" var="lookUp" varStatus="lk">
                                                   <c:set var="count" value="${count + 1}" scope="page"/>
                                                   <c:set var="images" value="${fn:split(lookUp.attachImage,',')}" />
                                                   <c:set var="links" value="${fn:split(lookUp.attach,',')}" />	
                                                   <tr>
                                                      <td>${lk.count}</td>
                                                      <c:choose>
                                                         <c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
                                                            <td>
                                                               <c:choose>
															 <c:when test="${lookUp.linkType eq 'L'}">
															 <a href="${lookUp.link}" target="">
															 <c:if test="${not empty images}"><div class="displayimage">															 
															<c:forEach items="${images[0]}" var="downloadL" varStatus="icount"> 
														 		<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.announceDescEng}">
														 	</c:forEach>
														 	</div></c:if>
															 ${lookUp.announceDescEng}
															 <%-- <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted"> --%>
															 </a>
															 </c:when>
															 <c:when test="${lookUp.linkType eq 'E'}">			                     
															 <a href="${lookUp.link}" target="_blank">
															 <c:if test="${not empty images}"><div class="displayimage">
															<c:forEach items="${images[0]}" var="downloadL" varStatus="icount">
														 		<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.announceDescEng}">
														 	</c:forEach>
														 	</div></c:if>
															 ${lookUp.announceDescEng}
															 <%-- <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted"> --%>
															 </a>
															 </c:when>
															 <c:otherwise>
															 <c:forEach items="${links}" var="download" varStatus="icount">
															 <c:set var="lnk1" value="./cache/${download}" />
														 		<c:if test="${icount.index eq 0 }">
														 		<a href="${lnk1}" target="_blank">
														 		<c:if test="${not empty images}"><div class="displayimage">
															 	<c:forEach items="${images[0]}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive eip-sml-slide" alt="${lookUp.announceDescEng}">
														 	 	</c:forEach>
														 	 	</div></c:if>${lookUp.announceDescEng}</a>
														 		</c:if> 
																<c:if test="${lnk1 ne './cache/'}">
																	<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>																		
																</c:if>
																
															 <%-- <apptags:filedownload filename="EIP"
																filePath="${download}"
																actionUrl="CitizenHome.html?Download"
																eipFileName="${lookUp.announceDescEng}"></apptags:filedownload> --%>
															 </c:forEach>
															 <%-- <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted"> --%>
															 </c:otherwise>
															</c:choose> 
                                                            </td>
                                                         </c:when>
                                                         <c:otherwise>
                                                            <td>
                                                               <c:choose>
															 <c:when test="${lookUp.linkType eq 'L'}">			                     
															 <a href="${lookUp.link}" target="">
															 <c:if test="${not empty images}"><div class="displayimage">
															<c:forEach items="${images[0]}" var="downloadL" varStatus="icount"> 
														 		<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.announceDescReg}">
														 	</c:forEach>
														 	</div></c:if>
															 ${lookUp.announceDescReg}
															 <%-- <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted"> --%>
															 </a>
															 </c:when>
															 <c:when test="${lookUp.linkType eq 'E'}">			                     
															 <a href="${lookUp.link}" target="_blank">
															 <c:if test="${not empty images}"><div class="displayimage">
															<c:forEach items="${images[0]}" var="downloadL" varStatus="icount"> 
														 		<img src="${downloadL}" class="img-responsive sml-slide" alt="${lookUp.announceDescReg}">
														 	</c:forEach>
														 	</div></c:if>
															 ${lookUp.announceDescReg}
															 <%-- <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted"> --%>
															 </a>
															 </c:when>
															 <c:otherwise>
															 <c:forEach items="${links}" var="download" varStatus="icount">															 
															 <c:set var="lnk1" value="./cache/${download}" />
														 		<c:if test="${icount.index eq 0 }">
														 		<a href="${lnk1}" target="_blank">
														 		<c:if test="${not empty images}"><div class="displayimage">
															 	<c:forEach items="${images[0]}" var="downloadL" varStatus="count"> 
														 		<img src="${downloadL}" class="img-responsive eip-sml-slide" alt="${lookUp.announceDescReg}">
														 	 	</c:forEach>
														 	 	</div></c:if>
														 		${lookUp.announceDescReg} <%-- <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted"> --%></a>
														 		</c:if>
																<c:if test="${lnk1 ne './cache/'}">
																	<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>																		
																</c:if>																
															 <%-- <apptags:filedownload filename="EIP"
																filePath="${download}"
																actionUrl="CitizenHome.html?Download"
																eipFileName="${lookUp.announceDescReg}"></apptags:filedownload> --%>
															 </c:forEach>
															<%-- <input type="hidden" value="${lookUp.isHighlighted}" class="Highlighted"> --%>
															 </c:otherwise>
															</c:choose> 
                                                            </td>
                                                         </c:otherwise>
                                                      </c:choose>
                                                      <td>${lookUp.validityDate}</td>
                                                   </tr>
                                                </c:forEach>
                                             </c:if>
                                          </tbody>
                                       </table>
                                    </div> 
                                    <%-- --------------------------------------------Announcement End-------------------------------- --%>		
                                    <c:forEach items="${sectionList}" var="section">
                                       <div class="bhoechie-tab-content">
                                          <c:set var="lookUpList" value="${command.getSectionInformation(section.lookUpId)}" />
                                          <%-- <h3>${section.lookUpCode}</h3> --%>
                                          <table class="table table-bordered dataTableClass">
                                             <c:forEach items="${lookUpList}" var="lookUps"
                                                varStatus="lk">
                                                <c:if test="${lk.index eq 0}">
                                                   <thead>
                                                      <tr>
                                                         <c:forEach items="${lookUps}" var="lookUp" varStatus="count">
                                                            <c:if
                                                               test="${not empty lookUp.lookUpCode && lookUp.lookUpType ne MainetConstants.TEXT_AREA_HTML}">
                                                               <th width="20%">${lookUp.lookUpCode}</th>
                                                            </c:if>
                                                         </c:forEach>
                                                      </tr>
                                                   </thead>
                                                </c:if>
                                                <tr>
                                                   <c:forEach items="${lookUps}" var="lookUp">
                                                   	  <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.VALIDITY_DATE_CODE}">
                                                         <td>${lookUp.lookUpDesc}</td>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.TEXT_AREA_HTML}">
                                                         <td>${lookUp.lookUpDesc}</td>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.TEXT_FIELD}">
                                                         <td style="word-break: break-all;">${lookUp.lookUpDesc}</td>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.TEXT_AREA}">
                                                         <td style="word-break: break-all;">${lookUp.lookUpDesc}</td>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.DATE_PICKER}">
                                                         <td>
                                                            ${lookUp.lookUpDesc}
                                                         </td>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.PROFILE_IMG}">
                                                         <c:choose>
                                                            <c:when test="${not empty lookUp.lookUpDesc}">
                                                               <td>
                                                                  <c:set var="imgs"
                                                                     value="${fn:split(lookUp.lookUpDesc, '|')}" />
                                                                  <c:forEach items="${imgs}" var="img">
                                                                     <a class="fancybox" href="${img}"
                                                                        data-fancybox-group="images"> <img
                                                                        src="${img}" alt="${img}" class="img-thumbnail"
                                                                        width="300px"></a>
                                                                  </c:forEach>
                                                               </td>
                                                            </c:when>
                                                            <c:otherwise>
                                                               <td>
                                                                  <div align="center">
                                                                  </div>
                                                               </td>
                                                            </c:otherwise>
                                                         </c:choose>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.VIDEO}">
                                                         <c:choose>
                                                            <c:when test="${not empty lookUp.lookUpDesc}">
                                                               <td>
                                                                  <c:set var="videos"
                                                                     value="${fn:split(lookUp.lookUpDesc, '|')}" />
                                                                  <c:forEach items="${videos}" var="video">
                                                                     <a class="fancybox" href="${video}"
                                                                        data-fancybox>
                                                                        <video alt="Videos" width="100%" controls>
                                                                           <source src="${video}"
                                                                              type='video/mp4; codecs="avc1.42E01E, mp4a.40.2"'>
                                                                        </video>
                                                                     </a>
                                                                  </c:forEach>
                                                               </td>
                                                            </c:when>
                                                            <c:otherwise>
                                                               <td>
                                                                  <div align="center">
                                                                  </div>
                                                               </td>
                                                            </c:otherwise>
                                                         </c:choose>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.ATTACHMENT_FIELD}">
                                                         <c:set var="links"
                                                            value="${fn:split(lookUp.lookUpDesc, ',')}" />
                                                         <td>
                                                            <c:forEach items="${links}" var="download"
                                                               varStatus="status">
                                                               <c:set var="link"
                                                                  value="${stringUtility.getStringAfterChar('/',download)}" />
                                                               <c:set var="path"
                                                                  value="${stringUtility.getStringBeforeChar('/',download)}" />
                                                               <c:if test="${not empty lookUp.lookUpDesc}">
                                                                  <apptags:filedownload filename="${link}"
                                                                     filePath="${path}"
                                                                     showIcon="true"
                                                                     actionUrl="SectionInformation.html?Download"></apptags:filedownload>
                                                               </c:if>
                                                               <c:if test="${empty lookUp.lookUpDesc}">
                                                               </c:if>
                                                            </c:forEach>
                                                         </td>
                                                      </c:if>
                                                   </c:forEach>
                                                </tr>
                                             </c:forEach>
                                          </table>
                                       </div>
                                    </c:forEach>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
               </form>
            </div>
            <div class="clearfix"></div>
         </div>
      </div>
   </div>
</div>
<script>
$(window).on('load', function(){$("select.form-control").attr("aria-label","select");});
</script>