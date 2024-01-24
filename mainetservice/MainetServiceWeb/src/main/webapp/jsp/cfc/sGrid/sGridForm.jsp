<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<script type="text/javascript">

	function doScrutiny() {

		var appId=8116040100029;
		
		viewApplAndScrutiny(appId,'');
		}

</script>
<ol class="breadcrumb">
	   	  <li><a href="AdminHome.html"><spring:message code="menu.home"/></a></li>
	      <li><a href="javascript:void(0);"><spring:message code="cfc.cfcHeader"/></a></li>
	      <li><a href="javascript:void(0);"><spring:message code="audit.transactions"/></a></li>
	      <li><spring:message code="cfc.sGridHeader" text="Scrutiny Grid"/></li>
</ol>
 <%-- <apptags:helpDoc url="ScrutinyGridForm.html"/> --%>
 
  <div class="content"> 
          <div class="widget">
            <div class="widget-header">
              <h2><spring:message code="cfc.sGridHeader"  text="Scrutiny Grid"/></h2>
              <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
            </div>
            <div class="widget-content padding">
	<form:form action="ScrutinyGridForm.html" class="form-horizontal" >
		 	<%-- 	<jsp:include page="/jsp/tiles/validationerror.jsp"/> --%>
				 <div class="form-group">
                    <label class="col-sm-2 control-label">
                    	<spring:message code="cfc.applId" text="Application Id"/> :</label>
					<div class="col-sm-4">
						<form:input path="entity.applicationId" cssClass="hasNumber form-control"/>
					</div>
					<label class="col-sm-2 control-label"><spring:message code="cfc.srvcName" text="Service Name"/> :</label>
					<div class="col-sm-4">
						<form:select path="entity.smServiceName" cssClass="form-control">
							<form:option value="0">Select Service</form:option>
							<c:forEach items="${command.onlineServiceList}" var="ser">
								<form:option value="${ser.lookUpDesc}">${ser.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
						<%-- <apptags:lookupField items="${command.onlineServiceList}" path="entity.serviceid"></apptags:lookupField> --%>
					</div>
				</div>
				<div class="form-group">
                    <label class="col-sm-2 control-label">
                    	<spring:message code="cfc.appFrDt" text="Application From Date"/> :</label>
						<div class="col-sm-4">
							<apptags:dateField fieldclass="datepicker" datePath="entity.applicationFromDate" cssClass="form-control"/>
						</div>
					<label class="col-sm-2 control-label">
						<spring:message code="cfc.appToDt" text="Application To Date"/> :</label>
						<div class="col-sm-4">
							<apptags:dateField fieldclass="datepicker" datePath="entity.applicationToDate" cssClass="form-control"/>
						</div>
				</div>
		 			<div class="text-center">
						<input type="button" class="btn btn-success" onclick="doScrutiny()" value="<spring:message  code='tp.Search'/>">
<%-- 						<input type="button" class="btn btn-success" onclick="findAll(this)" value="<spring:message  code='tp.Search'/>"> --%>
					</div>
			</form:form>
			<%--
			<apptags:jQgrid id="scrutinyGridForm"
							url="ScrutinyGridForm.html?SEARCH_RESULTS" mtype="post"
							gridid="gridScrutinyGridForm"
							colHeader="cfc.assignDt,cfc.applId,cfc.applDate,cfc.applName,cfc.srvcName,cfc.action"
							colModel="[	{name : 'amAssignedDate',index : 'amAssignedDate',align : 'center',formatter  : dateTemplate,search:false, width:100},
										{name : 'applicationId',index : 'applicationId',align : 'center', width:145},
										{name : 'apmApplicationDate',index : 'apmApplicationDate',align : 'center',formatter  : dateTemplate, search:false, width:100},
										{name : 'applicantsName',index : 'applicantsName',align : 'center', width:190},
										{name : 'smServiceName',index : 'smServiceName',align : 'center', width:180},
										{name : 'actionTemplet',index : 'viewAppSelectTemplet',align : 'center', search:false, width:150}]"
							sortCol="rowId"
							isChildGrid="false"
							hasActive="false"
							hasViewDet="false"
							hasDelete="false"
							height="200" 
							caption="cfc.sGridHeader" 
							loadonce="true" 
							showrow="true"/>
				<!-- <div id="piechart_3d" style="width:100%; height: 300px;text-align: center;"></div> -->
		</div>
		</div> --%>
		
		</div>
		</div>
		</div>
		
