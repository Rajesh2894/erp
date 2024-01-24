<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<script type="text/javascript" src="js/menu/existEntitlement.js"></script>
<div id="tree1">
	
	 <ul>
		<c:forEach var="masters" items="${command.listSystems}">
			<li class="folder" id="${masters.entitle.smfid}"><a href='#'
				target="${masters.entitle.smfid}/0">${masters.entitle.smfname}</a>
				<ul>
					<c:forEach items="${command.listModules}" var="data">
						<c:if test="${masters.entitle.smfid eq  data.parentId}">
							<li class="folder" id="${data.entitle.smfid}"><a href='#'
								target="${data.entitle.smfid}/${data.parentId}">${data.entitle.smfname}</a>
								<ul>
									<c:forEach items="${command.listModules}" var="data1">
										<c:if test="${data.entitle.smfid eq  data1.parentId}">
											<li class="folder" id="${data1.entitle.smfid}"><a
												href='#' target="${data1.entitle.smfid}/${data1.parentId}">${data1.entitle.smfname}</a>
												<ul>
													<c:forEach items="${command.listModules}" var="data2">
														<c:if test="${data1.entitle.smfid eq  data2.parentId}">
															<li class="folder" id="${data2.entitle.smfid}"><a
																href='#'
																target="${data2.entitle.smfid}/${data2.parentId}">${data2.entitle.smfname}</a>
																<ul>
																	<c:forEach items="${command.listModules}" var="data3">
																		<c:if test="${data2.entitle.smfid eq  data3.parentId}">
																			<li class="folder" id="${data3.entitle.smfid}"><a
																				href='#'
																				target="${data3.entitle.smfid}/${data3.parentId}">${data3.entitle.nameEng}</a></li>
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
				</ul></li>
		</c:forEach>


	</ul>
 
<%--  <ul>
        
		<c:forEach var="masters" items="${command.listSystems}">
			<li class="folder" id="${masters.entitle.etId}"><a href='#'
				target="${masters.entitle.etId}/0">${masters.entitle.nameEng}</a>
			    <c:set value="${masters.entitle.etId}" var="masterId" scope="request"></c:set>
				<jsp:include page="existSubPage.jsp"></jsp:include>
			</li>
		</c:forEach>


	</ul>  --%>

























	<%--  <ul>
					       <c:forEach var="masters" items="${command.listSystems}">
					      <li class="folder" id="${masters.mntId}"><a href='#' target="${masters.mntId}/0">${masters.menuname}</a>
						      <ul> 
						     <c:forEach  items="${command.listModules}" var="data"> 
						         <c:if test="${masters.mntId eq  data.parenId}">
							         <li class="folder" id="${data.mntId}"><a href='#' target="${data.mntId}/${data.parenId}">${data.menuname}</a>
							            <ul>
							            <c:forEach  items="${command.listModules}" var="data1"> 
							               <c:if test="${data.mntId eq  data1.parenId}">
							                 <li class="folder" id="${data1.mntId}"><a href='#' target="${data1.mntId}/${data1.parenId}">${data1.menuname}</a>
							                   <ul>
							                   <c:forEach  items="${command.listModules}" var="data2"> 
							                          <c:if test="${data1.mntId eq  data2.parenId}">
							                              <li class="folder" id="${data2.mntId}"><a href='#' target="${data2.mntId}/${data2.parenId}">${data2.menuname}</a>
							                              <ul>
							                                 <c:forEach  items="${command.listModules}" var="data3"> 
							                                    <c:if test="${data2.mntId eq  data3.parenId}"> 
							                                        <li class="folder" id="${data3.mntId}"><a href='#' target="${data3.mntId}/${data3.parenId}">${data3.menuname}</a></li>
							                                      </c:if> 
							                                  </c:forEach>
							                              </ul>
							                              
							                              </li>
							                         </c:if>
							                    </c:forEach>
							                   </ul>
							                   </li>
							                </c:if>
							            </c:forEach>
							           </ul>
							         </li>
							     </c:if> 
							 </c:forEach></ul>
						</li>
					  </c:forEach>
					    
					    
</ul> --%>
</div>

<%-- <ul>
						<c:forEach var="masters" items="${command.entitlements}">
							<li class="folder" id="${masters.etId}"><a href='#' target="${masters.etId}/0"><c:choose><c:when test="${langType == 1}">${masters.nameEng}</c:when><c:otherwise>${masters.nameReg}</c:otherwise></c:choose></a>
								<ul>
									<c:forEach var="subMaster"
										items="${masters.menuHierarchicalList}">
										<li id="${subMaster.etId}" class="folder"><a target="${subMaster.etId}/${subMaster.entitlement.etId}"
											href='##'><c:choose><c:when test="${langType == 1}">${subMaster.nameEng}</c:when><c:otherwise>${subMaster.nameReg}</c:otherwise></c:choose></a>
											<ul>
												<c:forEach var="subMaster1"
													items="${subMaster.menuHierarchicalList}">
													<li id="${subMaster1.etId}"><a href='###' target="${subMaster1.etId}/${subMaster1.entitlement.etId}"><c:choose><c:when test="${langType == 1}">${subMaster1.nameEng}</c:when><c:otherwise>${subMaster1.nameReg}</c:otherwise></c:choose></a>
														<ul>
															<c:forEach var="subMaster2"
																items="${subMaster1.menuHierarchicalList}">
																<li id="${subMaster2.etId}"><a href='####'
																	target="${subMaster2.etId}/${subMaster2.entitlement.etId}"><c:choose><c:when test="${langType == 1}">${subMaster2.nameEng}</c:when><c:otherwise>${subMaster2.nameReg}</c:otherwise></c:choose></a>
																	<ul>
																		<c:forEach var="subMaster3"
																			items="${subMaster2.menuHierarchicalList}">
																			<li id="${subMaster3.etId}"><a href='#####'
																				target="${subMaster3.etId}/${subMaster3.entitlement.etId}"><c:choose><c:when test="${langType == 1}">${subMaster3.nameEng}</c:when><c:otherwise>${subMaster3.nameReg}</c:otherwise></c:choose></a></li>
																		</c:forEach>
																	</ul></li>
															</c:forEach>
														</ul></li>
												</c:forEach>
											</ul></li>
									</c:forEach>
								</ul></li>
						</c:forEach>
					</ul> --%>
