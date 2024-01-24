<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/mainet/themes/humanity/jquery.ui.all.css" />
<script src="js/mainet/ui/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/mainet/jquery.jqGrid.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/masters/tbcomparammas/comparamMasList.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" id="content">
      <div class="widget">
        <div class="widget-header">        
        <h2><spring:message code="prefix.master.breadcrum" text="Prefix Master"/></h2>
        <apptags:helpDoc url="ComparamMaster.html" helpDocRefURL="ComparamMaster.html"></apptags:helpDoc>
        </div>        
        <div id="comparamMasDiv" class="widget-content padding">
        	<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDivComparamMas"></div>	
		<form class="form-horizontal">			
			<input type="hidden" value="${isDefault}" id="isDefault"/>
			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message code="master.department" text="Department"/></label>
				<div class="col-sm-4">
				<select id="tbComparamDet_cpmModuleName" name="cpmModuleName" class="form-control chosen-select-no-results" onchange="hideErrorDiv()">
					<option value=""><spring:message code="prefix.master.selDept" text="Select Department"/></option>
					<c:forEach items="${departmentList}" var="departmentData">
					<c:if test="${userSession.languageId eq 1}">
					<option value="${departmentData.dpDeptcode}">${departmentData.dpDeptdesc}</option>
					</c:if>
					<c:if test="${userSession.languageId eq 2}">
					<option value="${departmentData.dpDeptcode}">${departmentData.dpNameMar}</option>
					</c:if>	
					</c:forEach>
				</select>
				</div>
				<label class="col-sm-2 control-label"><spring:message code="master.prefixname" text="Prefix Name"/></label>
		        <div class="col-sm-4">
		        <input type="text" name="cpmPrefix" id="tbComparamMas_cpmPrefix" class="form-control mandClassColor" 
		        	style="text-transform: uppercase;" maxlength="4"/> <!-- onkeyup="getData();hideErrorDiv();" onchange="getData();hideErrorDiv();" -->
		        </div>             		
			</div>			
			<div class="text-center padding-bottom-20">
				<input type="BUTTON" id="search" value="<spring:message code="search.data"/>" class="btn btn-blue-2" onclick="searchPrefixData()"/>
				<input type="BUTTON" id="reset" value="<spring:message code="reset.msg"/>" class="btn btn-warning" onclick="resetPrefixForm()"/>
				<input type="BUTTON" id="" value="<spring:message code="" text="Print"/>" class="btn btn-blue-2" onclick="printPrefixData()"/>
				<c:if test="${isDefault eq 'Y'}">
					<input type="BUTTON" id="createData" value="<spring:message code="master.addButton"/>" class="btn btn-success btn-submit" onclick="createPrefix()"/>
				</c:if>			
			</div>
			<table id="grid" class="padding-bottom-20"></table>
			<div id="pagered"></div>
		</form>
		</div>
		
	</div>
</div>

	
	
	