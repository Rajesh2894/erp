<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/assetRegistrationSendBack.js"></script>
<div class="pagediv">
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="${userSession.moduleDeptCode == 'AST' ? 'asset.information.management':'asset.information.ITmanagement'}"
					text="Asset Registration" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message
							code="asset.information.help" /></span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form id="astReg" action="AssetRegistration.html" method="post"
				class="form-horizontal">
				<%-- <jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div> --%>
				<div class="compalint-error-div">
					<div class="mand-label clearfix">
						<span><spring:message code="asset.information.fieldwith" /><i
							class="text-red-1">*</i> <spring:message
								code="asset.information.ismendatory" /></span>
					</div>

					<ul class="nav nav-tabs asset-registration" id="assetParentTab">

					
						<li id="info-tab" class="active"><a data-toggle="tab" href="#astInfo" data-content-param="showAstInfoPage" data-loaded="true"><spring:message
									code="asset.tab.assetIformation" /></a></li>

						
						<li id="class-tab"><a data-toggle="tab" href="#astClass" data-content-param="showAstClsPage" data-loaded="false"><spring:message
									code="asset.tab.location.detail" /></a></li>

						
						<li id="purchase-tab"><a data-toggle="tab" href="#astPurch" data-content-param="showAstPurchPage" data-loaded="false"><spring:message
									code="asset.tab.acquisition.detail" /></a></li>
									
						<li id="realEstate-tab"><a data-toggle="tab" href="#astRealEstate" data-content-param="showAstRealEstate" data-loaded="false"><spring:message
									code="asset.tab.real.estate.detail" /></a></li>			
					
						<li id="service-tab"><a data-toggle="tab" href="#astSer" data-content-param="showAstSerPage" data-loaded="false"><spring:message
									code="asset.tab.serviceInformation" /></a></li>

						
						<li id="insurance-tab"><a data-toggle="tab" href="#astInsu" data-content-param="showAstInsuPage" data-loaded="false"><spring:message
									code="asset.tab.insuranceDetails" /></a></li>

						
						<li id="leasing-comp"><a data-toggle="tab" href="#astLease" data-content-param="showAstLeasePage" data-loaded="false"><spring:message
									code="asset.tab.leasingCompany" /></a></li>

						
						<li id="depre-tab"><a data-toggle="tab" href="#astCod" data-content-param="showAstDepreChartPage" data-loaded="false"><spring:message
									code="asset.tab.chartOfDepriciation" /></a></li>

						
						<li id="linear-tab"><a data-toggle="tab" href="#astLine" data-content-param="showAstLinePage" data-loaded="false"><spring:message
									code="asset.tab.lenearAsset" /></a></li>

						<li id="doc-tab"><a data-toggle="tab" href="#astDoc" data-content-param="showAstDocDet" data-loaded="false"><spring:message
									code="asset.tab.documentsDetails" /></a></li>

					</ul>
					<div class="tab-content">

						<div id="astInfo" class="tab-pane fade in active">
							<jsp:include page="/jsp/asset/assetInformation.jsp"/>
						</div>

						<div id="astClass" class="tab-pane fade">
						</div>
						
						<div id="astPurch" class="tab-pane fade">
						</div>
						
						<div id="astRealEstate" class="tab-pane fade">
						</div>
						
						<div id="astSer" class="tab-pane fade">
						</div>
						
						<div id="astInsu" class="tab-pane fade">
						</div>
						
						<div id="astLease" class="tab-pane fade">
						</div>
						
						<div id="astCod" class="tab-pane fade">
						</div>

						<div id="astLine" class="tab-pane fade">
						</div>
						
						<div id="astDoc" class="tab-pane fade">
						</div>
						
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
</div>