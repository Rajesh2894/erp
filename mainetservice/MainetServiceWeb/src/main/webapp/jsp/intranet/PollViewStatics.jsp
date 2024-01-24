<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script> 
<script type="text/javascript" src="js/intranet/pollCreation.js"></script>  

<div class="pagediv">
<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="intranet.pollVwStat" text="Poll Statics" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span></a>
				</div>
			</div>
			<div class="widget-content padding">

				<form:form id="pollViewStaticsForm" action="PollViewStatics.html" method="POST" class="form-horizontal" name="pollRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					
					<%-- <h4>
						<spring:message code="intranet.pollVwStat" text="Poll Statics" />
					</h4> --%>

					<fieldset class="fieldRound">
						<div class="overflow">
							<div class="table-responsive">
								<table class="table table-hover table-bordered table-striped">
									<thead>
                                    <c:set var="initialvalue" value="0" scope="page"/> 
                                    <c:set var="initialvaluecount" value="0" scope="page"/> 
                                    <c:set var="varCount" value="0" scope="page"/> 
                                    
										<tr>
											<th width="5%"><label class="tbold"><spring:message code="intranet.srNo" text="Sr No." /></label></th>
											<th width="10%"><label class="tbold"><spring:message code="intranet.ans" text="Answer" /></label></th>
											<th width="10%"><label class="tbold"><spring:message code="intranet.cnt" text="Count" /></label></th>
										</tr>
									</thead>
																
									<c:forEach items="${command.intranetPollViewDTOListObj}" var="intranetPollViewDTOListObj" varStatus="lk">
									
									<tbody>
										<c:if test="${initialvalue ne intranetPollViewDTOListObj[0]}">
										<c:set var="varCount" value="${varCount + 1}" scope="page"/>	
											<tr>
												<td colspan="3"> ${varCount} -- ${intranetPollViewDTOListObj[0]} </td>
											</tr>
											<c:set var="initialvalue" value="${intranetPollViewDTOListObj[0]}" /> 
											<c:if test="${initialvalue ne intranetPollViewDTOListObj[0]}">
											<tr>
												<td colspan="3"> ${intranetPollViewDTOListObj[0]} </td>
											</tr>
											<c:set var="lk.count" value="0" scope="page"/> 
											</c:if>	
										</c:if>
										<tr>
										    <td></td> <%-- ${lk.count} --%>
										    <td>${intranetPollViewDTOListObj[1]}</td>
											<td>${intranetPollViewDTOListObj[2]}</td>
										</tr>
									</tbody>
									</c:forEach> 
								</table>
							</div>
						</div>
					</fieldset>

				    <br>
					<div class="text-center">	
						<input type="button" onclick="window.location.href='AdminHome.html'" class="btn btn-danger hidden-print" value="Back">
					</div>	
				</form:form>
				
				</div>
		</div>
	</div>
</div> 

<!-- ashish test -->


