<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/viewAsset.js"></script>
<script type="text/javascript">
	//D#34059
	$(document)
			.ready(
					function() {
						//check command.modeType  = D is (Draft than hit only )
						if ('${command.modeType}' == 'D'
								&& '${command.modeType}' != undefined) {
							var urlParam = '${command.astDetailsDTO.assetInformationDTO.urlParam}';
							var hitHref = $(
									'[data-content-param =' + urlParam + ']')
									.attr('href');
							$('#assetViewParentTab a[href=' + hitHref + ']')
									.click();
						}
					});
</script>
 <c:set var="assetFlag"	value="${userSession.moduleDeptCode == 'AST' ? true : false}" />

			<c:if test="${userSession.moduleDeptCode != 'AST' && userSession.moduleDeptCode != 'IAST'}">
				<c:set var="assetFlag"	value="${command.astDetailsDTO.assetInformationDTO.deptCode == 'AST' ? true : false}" />
			</c:if> 
<div class="pagediv" id="viewAssetPage">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message
						code="${assetFlag ? 'asset.information.management':'asset.information.ITmanagement'}"
						text="Asset Registration" />
				</h2>
				<apptags:helpDoc url="AssetRegistration.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="astReg" action="AssetRegistration.html" method="post"
					class="form-horizontal">
					<div class="compalint-error-div">
						<div class="mand-label clearfix">
							<span><spring:message code="asset.information.fieldwith" /><i
								class="text-red-1">*</i> <spring:message
									code="asset.information.ismendatory" /></span>
						</div>

						<ul class="nav nav-tabs asset-view" id="assetViewParentTab">

							<li id="info-tab" class="active"><a data-toggle="tab"
								href="#astInfo" data-content-param="showAstInfoPage"
								data-loaded="true"><spring:message
										code="asset.tab.assetIformation" /></a></li>
							<c:choose>

								<c:when test="${userSession.moduleDeptCode == 'AST'}">
									<li id="class-tab"><a data-toggle="tab" href="#astClass"
										data-content-param="showAstClsPage" data-loaded="false"><spring:message
												code="asset.tab.location.detail" /></a></li>
									<li id="purchase-tab"><a data-toggle="tab"
										href="#astPurch" data-content-param="showAstPurchPage"
										data-loaded="false"><spring:message
												code="asset.tab.acquisition.detail" /></a></li>
								</c:when>
								<c:otherwise>
									<li id="purchase-tab"><a data-toggle="tab"
										href="#astPurch" data-content-param="showAstPurchPage"
										data-loaded="false"><spring:message
												code="asset.tab.purchase.details" /></a></li>
								</c:otherwise>
							</c:choose>

							<c:if test="${userSession.moduleDeptCode == 'AST' }">
								<li id="realEstate-tab"><a data-toggle="tab"
									href="#astRealEstate" data-content-param="showAstRealEstate"
									data-loaded="false"><spring:message
											code="asset.tab.real.estate.detail" /></a></li>
							</c:if>
							<li id="service-tab"><a data-toggle="tab" href="#astSer"
								data-content-param="showAstSerPage" data-loaded="false"><spring:message
										code="asset.tab.serviceInformation" /></a></li>
							<!-- D#34059 -->
							<c:choose>
								<c:when test="${command.modeType eq 'D'}">
									<li id="insurance-tab"><a data-toggle="tab"
										href="#astInsu" data-content-param="showAstInsuPage"
										data-loaded="false"><spring:message
												code="asset.tab.insuranceDetails" /></a></li>
								</c:when>
								<c:otherwise>
									<li id="insurance-tab"><a data-toggle="tab"
										href="#astInsu" data-content-param="showAstInsuPageDataTable"
										data-loaded="false"><spring:message
												code="asset.tab.insuranceDetails" /></a></li>
								</c:otherwise>
							</c:choose>
							<c:if test="${userSession.moduleDeptCode == 'AST' }">
								<li id="leasing-comp"><a data-toggle="tab" href="#astLease"
									data-content-param="showAstLeasePage" data-loaded="false"><spring:message
											code="asset.tab.leasingCompany" /></a></li>
							</c:if>
							<c:choose>

								<c:when test="${userSession.moduleDeptCode == 'AST'}">
									<li id="depre-tab"><a data-toggle="tab" href="#astCod"
										data-content-param="showAstDepreChartPage" data-loaded="false"><spring:message
												code="asset.tab.chartOfDepriciation" /></a></li>
								</c:when>
								<c:otherwise>
									<li id="depre-tab"><a data-toggle="tab" href="#astCod"
										data-content-param="showAstDepreChartPage" data-loaded="false"><spring:message
												code="asset.tab.depriciation" /></a></li>
								</c:otherwise>
							</c:choose>

							<c:if test="${userSession.moduleDeptCode == 'AST' }">
								<li id="linear-tab"><a data-toggle="tab" href="#astLine"
									data-content-param="showAstLinePage" data-loaded="false"><spring:message
											code="asset.tab.lenearAsset" /></a></li>
							</c:if>
							<li id="doc-tab"><a data-toggle="tab" href="#astDoc"
								data-content-param="showAstDocDet" data-loaded="false"><spring:message
										code="asset.tab.documentsDetails" /></a></li>
						</ul>
						<div class="tab-content">

							<div id="astInfo" class="tab-pane fade in active">
								<jsp:include page="/jsp/asset/assetInformation.jsp" />
							</div>

							<div id="astClass" class="tab-pane fade"></div>

							<div id="astPurch" class="tab-pane fade"></div>

							<div id="astRealEstate" class="tab-pane fade"></div>

							<div id="astSer" class="tab-pane fade"></div>

							<div id="astInsu" class="tab-pane fade"></div>

							<div id="astLease" class="tab-pane fade"></div>

							<div id="astCod" class="tab-pane fade"></div>

							<div id="astLine" class="tab-pane fade"></div>

							<div id="astDoc" class="tab-pane fade"></div>

						</div>
						<%-- <form:form action="AssetRegistration.html" id="informationTabForm" method="post"> --%>
						<!-- It gets enable only when approval status is pending -->
						<c:if
							test='${(command.astDetailsDTO.assetInformationDTO.appovalStatus != null) && (command.astDetailsDTO.assetInformationDTO.appovalStatus == "P") && (command.approvalViewFlag == "A")}'>
							<div class="panel-group accordion-toggle"
								id="accordion_single_collapse">
								<apptags:CheckerAction hideSendback="true" hideForward="true"></apptags:CheckerAction>
							</div>
							<div class="text-center widget-content padding">
								<button type="button" id="save"
									class="btn btn-success btn-submit"
									onclick="saveAssetInfoApprovalData(this);">
									<spring:message code="asset.transfer.save" text="Save" />
								</button>
								<button type="button" class="button-input btn btn-danger"
									name="button-Cancel" value="Cancel"
									onclick="window.location.href='AdminHome.html'"
									id="button-Cancel">
									<spring:message code="asset.information.back" text="Back" />
								</button>
							</div>
						</c:if>
						<%-- 	</form:form> --%>

					</div>
				</form:form>

			</div>

		</div>
	</div>
</div>