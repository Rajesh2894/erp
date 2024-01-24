<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <script>
function openCitizenGuideLines(data) {
    var myWindow = window.open("", "MsgWindow3", "scrollbars=1,width=800,height=600");
    var result = __doAjaxRequest("CitizenContactUs.html?showPage", 'GET', data, false);
    myWindow.document.write(result);
}

</script>

<div id="left_menu">

	<ul id="nav">
		<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
			<li class="folder" id="${masters.entitle.etId}"><a
				href="${masters.entitle.action}">${masters.entitle.nameEng}</a>
				<ul>
					<c:forEach items="${menuRoleEntitlement.childList}" var="data">
						<c:if test="${masters.entitle.etId eq  data.parentId}">
							<li class="folder" id="${data.entitle.etId}"><a
								href="${data.entitle.action}">${data.entitle.nameEng}</a>
								<ul>
									<c:forEach items="${menuRoleEntitlement.childList}" var="data1">
										<c:if test="${data.entitle.etId eq  data1.parentId}">
											<li class="folder" id="${data1.entitle.etId}"><a
												href="${data1.entitle.action}">${data1.entitle.nameEng}</a>
												<ul>
													<c:forEach items="${menuRoleEntitlement.childList}"
														var="data2">
														<c:if test="${data1.entitle.etId eq  data2.parentId}">
															<li class="folder" id="${data2.entitle.etId}"><a
																href="${data2.entitle.action}">${data2.entitle.nameEng}</a>
																<ul>
																	<c:forEach items="${menuRoleEntitlement.childList}"
																		var="data3">
																		<c:if test="${data2.entitle.etId eq  data3.parentId}">
																			<li class="folder" id="${data3.entitle.etId}"><a
																				href="${data3.entitle.action}">${data3.entitle.nameEng}</a></li>
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