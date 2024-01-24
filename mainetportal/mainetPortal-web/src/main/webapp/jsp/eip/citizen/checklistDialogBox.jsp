<%@page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<script src="js/eip/citizen/guest-home.js"></script>
<script>
$(document).ready(function() {
	if($('#checkListId').val() == 0){
		var redirectUrl = $('#serviceURLId').val()
		getLogin(redirectUrl);
	}
});
</script>

       <div class="modal-header">
         <h4 class="modal-title"><i class="fa fa-file-text-o margin-5"></i> &nbsp; Required Document For ${command.serviceName}</h4>
        </div>
        <div class="modal-body">
          
         <input type="hidden" value ="${command.serviceURL}" id ="serviceURLId"/>
         <input type="hidden" value ="${command.checkList.size()}" id ="checkListId"/>
        
          <table class="table table-bordered">
													<tbody>
														<tr>
															<th><spring:message code="water.serialNo"
																	text="Sr No" /></th>
															<th><spring:message code="water.docName"
																text="Document Name" /></th>
															<th><spring:message code="water.status"
																	text="Status" /></th>
															
														</tr>
<c:if test="${command.checkList.size() > 0}">
														<c:forEach items="${command.checkList}" var="lookUp"
															varStatus="lk">

															<tr>
																<td>${lookUp.documentSerialNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>${lookUp.doc_DESC_ENGL}</td>
																	</c:when>
																	<c:otherwise>
																		<td>${lookUp.doc_DESC_Mar}</td>
																	</c:otherwise>
																</c:choose>
																<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td><spring:message code="water.doc.mand" /></td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td><spring:message code="water.doc.opt" /></td>
																</c:if>
																
															</tr>
														</c:forEach>
														</c:if>
														<c:if test="${command.checkList.size()== 0}">
														<tr>
														<td colspan="3" class="text-center">No Document Required</td>
														</tr>
														</c:if>
													</tbody>
												</table>
        </div>
        <div class="modal-footer text-center">
        <c:if test="${command.serviceCode eq 'ESR' }">
          <button type="button" class="btn btn-blue-2" data-dismiss="modal" onclick="checkBookingAvailability();"><spring:message code="checkBooking"/></button></c:if>
          <button type="button" class="btn btn-blue-2" data-dismiss="modal" onclick="getLogin('${command.serviceURL}')">Apply</button>
          <button type="button" class="btn btn-warning" data-dismiss="modal" onclick="closeBox();">Close</button>
        </div>

<script>
 function closeBox(){
	 $.fancybox.close();
	 }
</script>
    