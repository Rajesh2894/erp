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

<script src="js/menu/existEntitlement.js"></script>
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
 
</div>


