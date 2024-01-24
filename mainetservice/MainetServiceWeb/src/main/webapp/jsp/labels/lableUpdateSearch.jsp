<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="js/labels/lableUpdate.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content" >
	<div class="widget">
	     <div class="widget-header">  <h2><strong><spring:message code="label.update" /></strong></h2></div>
	    <%--  <apptags:helpDoc url="LableUpdateSearch.html"></apptags:helpDoc> --%>
			<div class="widget-content padding ">
				<form:form class =" form-horizontal">	
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message code="label.sel.prop.file" text="Select Property File"/></label>
					<div class="col-sm-6">				
				<form:select path="selectBoxValue" onchange="showKyeValueList(this)" id="selectedPropertiesFile"
								cssClass="chosen-select-no-results form-control">
							<form:option value="0" ><spring:message code="label.sel.file"/></form:option> 
							<form:options items="${command.fileList}" />
						</form:select>
						</div>
						</div>
						</form:form>
						<div class="clear padding_10"></div>
					<div class="form-group table-responsive">	
					<apptags:jQgrid id="labelUpdate"
		url="LableUpdateSearch.html?LABLE_LIST" mtype="post"
		gridid="gridLableUpdateSearch"
		colHeader="label.update.key,label.update.value,label.update.edit"
		colModel="[	
		                                        {name : 'name',index : 'name',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'value',index : 'value',editable : false,sortable : true,search : true,align : 'center',width :'200' },
												{name : 'editTemplate',index : 'editTemplate',editable : false,sortable : true,search : false,align : 'center',width :'30' }
												
				  ]"
		sortCol="rowId" isChildGrid="false" hasActive="false" 
		 viewAjaxRequest="false" hasDelete="false" showrow="true"
		height="300" caption="label.update" loadonce="true" /> 
		</div>
				</div>
			</div>
		</div>
 
