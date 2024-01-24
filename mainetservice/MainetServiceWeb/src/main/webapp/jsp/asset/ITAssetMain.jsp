<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetInformation.js"></script>
<script type="text/javascript" src="js/asset/assetPurchaserInfromation.js"></script> 

<!-- T#101107 This page assetInformation.jsp is used by two modules 
named as 1) Asset Module(AST) 
         2) ITAsset Module(IAST)
          if(Asset Module){
          assetFlag = true;
         }else{
         assetFlag =false;
         }
         <c:choose>
            <c:when test="${assetFlag}">
               Asset Module code
			</c:when>
			<c:otherwise>
              ITAsset Module code
			</c:otherwise>
		</c:choose>
                -->
                
                
<apptags:breadcrumb></apptags:breadcrumb>                

<div class="widget-content padding itAssetMain" id="contentInformation">
<form:form action="AssetRegistration.html" id="assetDocumentId"
		method="post" class="form-horizontal">
	
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<!-- <div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDiv"></div> -->
		<c:if test = "${userSession.moduleDeptCode == 'AST' }">
		<form:hidden path="modeType" id="modeType" />
		<form:hidden path="saveMode" id="saveMode" />
		</c:if>
		<%-- <form:hidden path="astDetailsDTO.assetInformationDTO.assetId"
			id="assetId" /> --%>


		<input type="hidden" id="moduleDeptUrl"
			value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
		<c:set var="assetFlag"
			value="${userSession.moduleDeptCode == 'AST' ? true : false}" />

		<c:choose>
			<c:when test="${assetFlag}">			
				<input type="hidden" id="atype" value="AST" />
			</c:when>
			<c:otherwise>
				<input type="hidden" id="atype" value="IAST" />
			</c:otherwise>
		</c:choose>

		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<%-- <div class="panel panel-default"> --%>
				<%-- <div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
								code="asset.information.assettype" /></a>
					</h4>
					<!----------------------------------------------- Asset information start ---------------------------------- -->
				</div> --%>


				<%-- <div id="Applicant" class="panel-collapse collapse in">
					<div class="panel-body"> --%>
						<jsp:include page="/jsp/asset/assetPurchaserInformation.jsp" />
						<%-- <jsp:include page="/jsp/asset/assetPurchaserInformation.jsp" />	 --%>					
					<%-- </div> --%>
					<%-- <div class="panel-body">
						<jsp:include page="/jsp/asset/assetInformation.jsp" />
					</div>
					<div class="panel-body">
						<jsp:include page="/jsp/asset/assetServiceInformation.jsp" />
					</div>
				    <div class="panel-body">
						<jsp:include page="/jsp/asset/assetDocumentDetails.jsp" />
					</div> --%>
				<%-- </div>
			</div> --%>

			
		</div>

	</form:form>
</div>
<script>
$("#SaveAstMain").click(function(){
	debugger
	saveAssetInformation(this);
	});
</script>