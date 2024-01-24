<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<apptags:breadcrumb></apptags:breadcrumb>
<c:if test="${not command.hasValidationErrors()}">
</c:if>

<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
%>
		<!--  <script type="text/javascript">
		
		 $( document ).ready(function() {
			
		var deptcode = $('#deptCode').val();
		
		var serviceURl = $('#hideService').val();
		
		$("#serviceUrl").find('option:gt(0)').remove();
			
			 if(deptcode != '0'){
				
				var postdata = 'deptcode=' + deptcode;
				
				 var json = __doAjaxRequest('HelpDoc.html?serviceList',
						'POST', postdata, false, 'json'); 
			    
				var  optionsAsString='';
				for(var i = 0; i < json.length; i++) {
				    optionsAsString += "<option value='" + json[i].lookUpCode + "'>" + json[i].lookUpCode + "</option>";
				}
				$("#serviceUrl").append( optionsAsString );
				
				$('#serviceUrl option[value="' + serviceURl + '"]').prop('selected', true); 
			} 
			
		}); 
		</script>  -->
		
		<div class="content" >
		<div class="widget">
	    <div class="widget-header">
	   <!--  <div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide">Help</span></i></a>
				</div> -->
	    <h2><strong><spring:message code="eip.admin.helpDocs.breadcrumb" /></strong></h2>
	   <apptags:helpDoc url="CommonHelpDocs.html"></apptags:helpDoc>
	    </div>
	     
		<div class="widget-content padding ">
		<div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
		<form:form  id="myForm" class="form-horizontal" >
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div class="form-group">
		<label class="col-sm-2 control-label required-control"><spring:message code="eip.admin.helpDocs.formname" text="Form Name" /></label>
		<div class="col-sm-4">
		<form:select path="entity.moduleName"  cssClass=" form-control mandClassColor chosen-select-no-results" id="deptCode">
		<form:option value="0" > <spring:message code="eip.admin.helpDocs.selformname" text="Select Form Name" /></form:option> 
		<c:forEach items="${command.nodes}" var="look">
		<form:option value="${look.value}" label="${look.key}"/>
		</c:forEach>
		</form:select>
		</div>
		</div>
					
					
		<div class="text-center padding-10">
				<a href="javascript:void(0);"  onclick="findAll(this)"class="btn btn-blue-3"><i class="fa fa-search"></i> <spring:message code="eip.admin.auth.search" /></a>
				<a href="javascript:void(0);"  onclick="openForm('HelpDoc.html')"class="btn btn-success"><i class="fa fa-plus-circle"></i> <spring:message code='master.addButton' text="Add"/></a>
				<spring:url var="cancelButtonURL" value="CommonHelpDocs.html" />
				<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message code="reset.msg" text="Reset"/></a>
		</div>   
		</form:form>
		<div class="table-responsive" id="quickLinkGrid">
		
		<apptags:jQgrid id="helpDocs"
						url="CommonHelpDocs.html?List_Of_Services" mtype="POST"
						gridid="gridCommonHelpDocs" 
						deleteURL="CommonHelpDocs.html"
						editurl="HelpDoc.html"
						colHeader="eip.admin.helpDocs.gridTitle.uploadFileNameEng,eip.admin.helpDocs.gridTitle.uploadFileNameReg"
						colModel="[
								{name : 'fileNameEng',index : 'fileNameEng', editable : true,sortable : false,search : false, align : 'center' },
								{name : 'fileNameReg',index : 'fileNameReg', editable : true,sortable : false,search : false, align : 'center' }
					             ]"
						height="400" caption="eip.admin.helpDocs.gridTitle.uploadDocs" isChildGrid="false"
						hasViewDet="false" 
						hasEdit="true" 
						hasDelete="true"
						loadonce="true" 
						sortCol="rowId" 
						showrow="true" />
		</div> 
		</div>
		</div>
		</div>