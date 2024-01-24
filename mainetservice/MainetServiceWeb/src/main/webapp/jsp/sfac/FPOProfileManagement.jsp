<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>



<div class="pagediv">
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.fpo.pm.name"
					text="FPO Profile Management" /> &nbsp; <strong>(${command.dto.fpoName })</strong>
			</h2>
			<apptags:helpDoc url="FPOProfileManagementForm.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form id="astReg" action="FPOProfileManagementForm.html" method="post"
				class="form-horizontal">
				<%-- <jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div> --%>
					<form:hidden path="fpmId" id="fpmId" />
					
				<div class="compalint-error-div">
					<div class="mand-label clearfix">
						<span><spring:message code="asset.information.fieldwith" /><i
							class="text-red-1">*</i> <spring:message
								code="asset.information.ismendatory" /></span>
					</div>

					<ul class="nav nav-tabs " id="fpoPMParentTab">
					
						<li id="fin-tab"  class="active" ><a data-toggle="tab" href="#finInfo"  ><spring:message
									code="sfac.fpo.pm.finInfo" /></a></li>

						<%-- <li id="info-tab" class="active"><a data-toggle="tab" href="#astInfo" onclick="navigateTab('info-tab','showAstInfoPage', this); return false;"><spring:message
									code="asset.tab.assetIformation" /></a></li> --%>
						<li id="license-tab" class="disabled"><a  class="disabled" data-toggle="tab" href="#" ><spring:message
									code="sfac.fpo.pm.licenseEntry" /></a></li>

						<%-- <li id="class-tab" class="disabled"><a data-toggle="tab" href="#astClass" onclick="navigateTab('class-tab','saveAstInfoPage', this); return false;"><spring:message
									code="asset.tab.assetClassification" /></a></li> --%>
						
						<li id="credit-tab"  class="disabled"><a  class="disabled"   href="#creditInfo"  ><spring:message
									code="sfac.fpo.pm.creditInfo" /></a></li>
                    
					
						<li id="equity-tab" class="disabled"><a class="disabled"  href="#equityInfo" ><spring:message
									code="sfac.fpo.pm.equityInfo" /></a></li>

						<%-- <li id="leasing-comp" class="disabled"><a href="#astLease" onclick="navigateTab('leasing-comp','saveAstInsuPage', this); return false;"><spring:message
									code="asset.tab.leasingCompany" /></a></li> --%>
													
						<li id="farmerSummary-tab" class="disabled" ><a class="disabled"  href="#farmerSummaryInfo" ><spring:message
									code="sfac.fpo.pm.farmerSummary" /></a></li>
				

						<%-- <li id="depre-tab" class="disabled"><a data-toggle="tab" href="#astCod" onclick="navigateTab('depre-tab','saveAstLeasePage', this); return false;"><spring:message
									code="asset.tab.chartOfDepriciation" /></a></li> --%>
						
						<li id="mgmt-tab" class="disabled"><a class="disabled"  href="#mgmtInfo" ><spring:message
									code="sfac.fpo.pm.mgmtCost" /></a></li>
                     
						<li id="creditGrant-tab" class="disabled"><a class="disabled"  href="#creditGrantInfo" ><spring:message
									code="sfac.fpo.pm.creditGrant" /></a></li>
									
						<li id="storage-tab" class="disabled"><a class="disabled"  href="#storageInfo" ><spring:message
									code="sfac.fpo.pm.storage" /></a></li>	
						
						<li id="custom-tab" class="disabled"><a class="disabled"  href="#customInfo" ><spring:message
									code="sfac.fpo.pm.customHiring" /></a></li>	
						
						<li id="pns-tab" class="disabled"><a class="disabled"  href="#pnsInfo" ><spring:message
									code="sfac.fpo.pm.productionAndSale" /></a></li>	
						
						<li id="subsidies-tab" class="disabled"><a class="disabled"  href="#subsidiesInfo" ><spring:message
									code="sfac.fpo.pm.subsidies" /></a></li>
						
					<%-- 	<li id="preharvesh-tab" class="disabled"><a class="disabled"  href="#preharveshInfo" ><spring:message
									code="sfac.fpo.pm.preHarvesh" /></a></li> --%>	
						
						<li id="postharvest-tab" class="disabled"><a class="disabled"  href="#postharvestInfo" ><spring:message
									code="sfac.fpo.pm.postHarvest" /></a></li>	
						
						<li id="transport-tab" class="disabled"><a class="disabled"  href="#transportInfo" ><spring:message
									code="sfac.fpo.pm.transportVehicle" /></a></li>	
						
						<li id="ml-tab" class="disabled"><a class="disabled"  href="#mlInfo" ><spring:message
									code="sfac.fpo.pm.marketLinkage" /></a></li>	
						
						<li id="bp-tab" class="disabled"><a class="disabled"  href="#bpInfo" ><spring:message
									code="sfac.fpo.pm.businessPlan" /></a></li>		
									
						<li id="abs-tab" class="disabled"><a class="disabled"  href="#absInfo" ><spring:message
									code="sfac.fpo.pm.auditedBalanceSheet" /></a></li>	
						<li id="dpr-tab" class="disabled"><a class="disabled"  href="#dprInfo" ><spring:message
									code="sfac.fpo.pm.dpr" /></a></li>													
																
					<li id="traning-tab" class="disabled"><a class="disabled"  href="#traningInfo" ><spring:message
									code="sfac.fpo.pm.trainingDet" /></a></li>
									
						

					</ul>
					<div class="tab-content">

						<div id="finInfo" class="tab-pane fade in active">
							 <jsp:include page="/jsp/sfac/FinancialInfo.jsp"></jsp:include> 
						</div>
						<div id="licenseInfo" class="tab-pane fade ">
							<jsp:include page="/jsp/sfac/LicenseInfo.jsp"/>
						</div>

						<div id="creditInfo" class="tab-pane fade ">
							 <jsp:include page="/jsp/sfac/CreditInfo.jsp"></jsp:include> 
						</div>
						
						
						
						<div id="equityInfo" class="tab-pane fade">
						 <jsp:include page="/jsp/sfac/EquityInfo.jsp"></jsp:include> 
						</div>
						
						<div id="farmerSummaryInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/fpoProfileFarmerSummaryInfo.jsp"></jsp:include>
						</div>
						
						<div id="mgmtInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/ManagementCostInfo.jsp"></jsp:include>
						</div>
						
						<div id="creditGrantInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/CreditGrandInfo.jsp"></jsp:include>
						</div>
						
						
						<div id="storageInfo" class="tab-pane fade">
						 <jsp:include page="/jsp/sfac/StorageInfo.jsp"></jsp:include> 
						</div>
						
						<div id="customInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/CustomHiringInfo.jsp"></jsp:include>
						</div>
						
						<div id="pnsInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/ProductAndSaleInfo.jsp"></jsp:include>
						</div>
						
						<div id="subsidiesInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/subsidiesInfo.jsp"></jsp:include>
						</div>
						
						<%-- <div id="preharveshInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/preHarveshInfo.jsp"></jsp:include>
						</div> --%>
						
						<div id="postharvestInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/postHarvestInfo.jsp"></jsp:include>
						</div>
						
						<div id="transportInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/TransportVehicleInfo.jsp"></jsp:include>
						</div>
						
						<div id="mlInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/MarketLinkageInfo.jsp"></jsp:include>
						</div>
						
						<div id="bpInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/BusinessPlanInfo.jsp"></jsp:include>
						</div>
						
						<div id="absInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/AuditedBalanceSheetInfo.jsp"></jsp:include>
						</div>
						<div id="dprInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/dprInfo.jsp"></jsp:include>
						</div>
							<div id="traningInfo" class="tab-pane fade">
							<jsp:include page="/jsp/sfac/FPOProfileTrainingInfo.jsp"></jsp:include>
						</div>

					
						
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
</div>

