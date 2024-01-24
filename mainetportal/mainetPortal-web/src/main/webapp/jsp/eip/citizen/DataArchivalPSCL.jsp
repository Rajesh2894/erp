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
                                          <spring:message  text="Important Links"/>
                                       </a>
                                       <a href="#" class="list-group-item text-center">
                                          <spring:message  text="Tender Notice"/>
                                       </a>
                                       <a href="#"	class="list-group-item text-center">
                                          <spring:message  text="News and Updates"/>
                                       </a>
                                       <c:forEach items="${sectionList}" var="section">
                                          <a href="#" class="list-group-item text-center">
                                          ${section}</a>
                                       </c:forEach>
                                    </div>
                                 </div>
                            
                          <%-- --------------------------------------------Important Link----------------------------------------------- --%>
                                 <div class="col-lg-10 col-md-10 col-sm-8 col-xs-12 bhoechie-tab">
                                  <div class="bhoechie-tab-content active">
                                       <%-- <h3>
                                          <spring:message code="" text="Highlightes"/>
                                       </h3> --%>
                                       <table class="table table-bordered dataTableNormal">
                                          <thead>
                                             <tr>
                                                <th>
                                                   <spring:message code="rti.srno" text="SR NO"/>
                                                </th>
                                                <th>
                                                   <spring:message code="rti.link.name" text="Link Name"/>
                                                </th>
                                             </tr>
                                          </thead>
                                          <tbody>
                                             <c:set var="count" value="0" scope="page"/>
                                            <%-- <c:if test="${command.highlighted eq true}">  --%>
                                                <c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
                                                   <c:if test="${lookUp.isUsefullLink ne 'Y'}">
                                                      <c:set var="count" value="${count + 1}" scope="page"/>
                                                      <tr>
                                                         <td>${lk.count}</td>
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
																				eipFileName="${lookUp.noticeSubEn}"></apptags:filedownload> --%>
																			<td>	
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="./cache/${downloadL}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">
																						${lookUp.noticeSubEn}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubEn}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																			</c:forEach>
																			</td>
																			<td>${lookUp.link}</td>
																		</c:when>
																		<c:otherwise>
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubReg}"></apptags:filedownload> --%>
																				
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<td>
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="./cache/${downloadL}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.noticeSubReg}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubReg}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																			</c:forEach>
																			</td>
																			<td>${lnk1}</td>
																		</c:otherwise>
																	</c:choose>
																</c:when>
                                                            <c:otherwise>
																	<c:if test="${not empty lookUp.imagePath}">																	
																	<c:set var="links" value="${fn:split(lookUp.imagePath,',')}" />
																		<c:set var="lnk1" value="./cache/${downloadL}" />																	
																		<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>
																		
																			<c:if test="${not empty lookUp.noticeSubEn}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			 		<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																			 	</c:forEach>
																			 	</div>${lookUp.noticeSubEn}
																			 </a>
																			</c:if>
																			<c:if test="${empty lookUp.noticeSubEn}">
																			<div class="displayimage">
																			<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			 	<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																			 </c:forEach>
																			</div>
																			</c:if>
																		</td>	
																		</c:if>
																		<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
																		<td>
																		
																			<c:if test="${not empty lookUp.noticeSubReg}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																				</c:forEach>
																				</div>${lookUp.noticeSubReg}
																			</a>
																			</c:if>
																			<c:if test="${empty lookUp.noticeSubReg}">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																				</c:forEach>
																			</div>
																			</c:if>
																			</td>
																			
																		</c:if>
																	</c:if>
																</c:otherwise>
                                                         </c:choose>
                                                        
                                                      </tr>
                                                   </c:if>
                                                </c:forEach>
                                            <%--  </c:if> --%>
                                          </tbody>
                                       </table>
                                    </div>														
                                     <%-- --------------------------------------Announcement / Press Release Start ----------------------------------- --%>		
                                    <div class="bhoechie-tab-content">
										<div class="press-release-content">
											<table class="table table-bordered table-striped dataTableNormal">
												<thead>
													<tr>
														<th><spring:message code="theme3.portal.details.info" text="Details / Information"/></th>
														<th class="pr-date"><spring:message code="theme3.portal.date" text="Date"/></th>							
														<th><spring:message code="theme3.portal.images" text="Images"/></th>
														<th><spring:message code="theme3.portal.download" text="Download"/></th>
														<th><spring:message code="theme6.portal.internal.external.links" text="Internal / External Links"/></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="counter">
														<tr>
															<td>
																<c:choose>
																	<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<c:choose>
																			<c:when
																				test="${(not empty lookUp.linkType) and (lookUp.linkType eq 'E' || lookUp.linkType eq 'L') }">
																				${lookUp.announceDescEng}
																			</c:when>
																			<c:otherwise>
																				${lookUp.announceDescEng}
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${(not empty lookUp.linkType) and (lookUp.linkType eq 'E' || lookUp.linkType eq 'L') }">
																				${lookUp.announceDescReg}
																			</c:when>
																			<c:otherwise>
																				${lookUp.announceDescReg}
																			</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>
															</td>
															<td class="pr-date text-center">
																<span class="date">
																	<fmt:formatDate type="date" value="${lookUp.newsDate}" dateStyle="SHORT" pattern="dd-MM-yyyy"/>
																</span>
															</td>
															<td  class="text-center">
															<c:set var="flagCnt" value="0" />
															<c:set var="links" value="${fn:split(lookUp.attachImage,',')}" />
															<c:forEach items="${links}" var="download" varStatus="count">
																<c:if test="${not empty download && fn:trim(download) ne ''}">
																	
																	<c:set var="flagCnt" value="1" />
																</c:if>
															</c:forEach>
																			
															<c:if test="${not empty lookUp.attachImage && flagCnt eq 1}">
																<a class="press-release" data-toggle="modal" data-id="${lookUp.attachImage}"  ><i class="fa fa-picture-o " ></i></a>
															</c:if>	
															</td>
															<td class="text-center">
															<c:if test="${not empty lookUp.attach}">
															<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
															<c:forEach items="${links}" var="download" varStatus="count">
																<a href="./cache/${download}" target="_blank" class="">&nbsp;<i class="fa fa-download" ></i>&nbsp;</a>
															</c:forEach>	
															</c:if>
															</td>
															<td>
																<c:choose>
																	<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<c:choose>
																			<c:when
																				test="${(not empty lookUp.linkType) and (lookUp.linkType eq 'E' || lookUp.linkType eq 'L') }">
																				<a href="${lookUp.link }"><span class="external_link_icon"></span></a>
																			</c:when>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${(not empty lookUp.linkType) and (lookUp.linkType eq 'E' || lookUp.linkType eq 'L') }">
																				<a href="${lookUp.link }"><span class="external_link_icon"></span></a>
																			</c:when>
																		</c:choose>
																	</c:otherwise>
																</c:choose>
															</td>				
														</tr>
													</c:forEach>
												</tbody>
											</table>
											
											<!-- Modal for Press Release Events starts -->
											<div id="pressreleasediv" class="modal fade press-modal press-release-modal-class" role="dialog">
												<div class="modal-dialog modal-lg">
													<!-- Modal content-->
													<div class="modal-content">
														<button type="button" class="close" data-dismiss="modal">
															<i class="fa fa-times" aria-hidden="true"></i>
														</button>
														<div class="modal-body ">
															<div id="myCarousel-1" class="carousel slide carousel-fade" data-ride="carousel">
																<!-- Wrapper for slides -->
																<div class="carousel-inner press-modal-body" id="press-modal-body-div">
							
																</div>
										
																<!-- Left and right controls -->
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
											</div>
											<!-- Modal for Press Release Events ends -->
										</div>
                                    </div> 
                                    <%-- --------------------------------------------Announcement / Press Release End-------------------------------- --%>
                              		 <%-- --------------------------------------------Schemes----------------------------------------------- --%>														
                                    <div class="bhoechie-tab-content">
                                      <%--  <h3>
                                          <spring:message code="" text="Schemes"/>
                                       </h3> --%>
                                       <table class="table table-bordered dataTableNormal">
                                          <thead>
                                             <tr>
                                                <th>
                                                   <spring:message code="rti.srno" text="SR NO"/>
                                                </th>
                                                <th>
                                                   <spring:message code="eip.details" text="details"/>
                                                </th>
                                             </tr>
                                          </thead>
                                          <tbody>
                                             <c:set var="schemeCount" value="0" scope="page"/>
                                             <c:if test="${command.scheme eq true}">
                                                <c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
                                                   <c:if test="${lookUp.isUsefullLink eq 'Y' }">
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
																				eipFileName="${lookUp.noticeSubEn}"></apptags:filedownload> --%>
																			<td>	
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="./cache/${downloadL}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">
																						${lookUp.noticeSubEn}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubEn}
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
																				eipFileName="${lookUp.noticeSubReg}"></apptags:filedownload> --%>
																				
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<td>
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="./cache/${downloadL}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.noticeSubReg}&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubReg}
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
																		
                                                                    <c:if test="${not empty lookUp.noticeSubEn}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																				
																			 		<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																			 	</c:forEach>
																			 	</div>${lookUp.noticeSubEn}
																			 </a>
																			</c:if>
																			<c:if test="${empty lookUp.noticeSubEn}">
																			<div class="displayimage">
																			<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			 	<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																			 </c:forEach>
																			</div>
																			</c:if>
																			
																		</td>	
																		</c:if>
																		<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
																		<td>
																			
                                                                      <c:if test="${not empty lookUp.noticeSubReg}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																				</c:forEach>
																				</div>${lookUp.noticeSubReg}
																			</a>
																			</c:if>
																			<c:if test="${empty lookUp.noticeSubReg}">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																				</c:forEach>
																			</div>
																			</c:if>
																			</td>
																		
																		</c:if>
																	</c:if>
																</c:otherwise>
                                                         </c:choose>
                                                      </tr>
                                                   </c:if>
                                                </c:forEach>
                                             </c:if>
                                          </tbody>
                                       </table>
                                    </div>
                                    <%-- --------------------------------------------Schemes End----------------------------------------------- --%>	
                                    
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
$(window).load(function(){$("select.form-control").attr("aria-label","select");});
</script>