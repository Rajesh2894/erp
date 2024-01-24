<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>


<apptags:breadcrumb></apptags:breadcrumb>
    
<div class="content">
<div class="widget">
<div class="widget-header"><h2><strong><spring:message code="pg.merchntgrid" /></strong></h2></div>
<apptags:helpDoc url="MerchantDetail.html"></apptags:helpDoc>
	
		
			<div class="widget-content padding ">
				<form:form action="MerchantDetail.html"  class="form"
					name="frmMerchantMaster" id="frmMerchantMaster">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="text-right padding-bottom-10">
						<span class="otherlink"> <a href="javascript:void(0);" class="btn btn-success" onclick="openForm('MerchantEntryForm.html')"><i class="fa fa-plus-circle"></i> <spring:message code="eip.add" /></a>
						</span>
					</div>
				</form:form>
                 
				<div class="table-responsive" id="merchantEntryFormGrid">
					<apptags:jQgrid id="merchantEntryForm"
						url="MerchantDetail.html?MERCHANT_MASTER_LIST" mtype="post"
						gridid="gridMerchantEntryForm"
						colHeader="pg.bankname,pg.merchid,pg.merchName,pg.merchshort"
						colModel="[
						    {name : 'bankName',index : 'bankName',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'mmmrcntid',index : 'mmmrcntid',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'mmmrcntname',index : 'mmmrcntname',editable : false,sortable : false,search : false,  align : 'center'},
							{name : 'mmmrcntcode',index : 'mmmrcntcode',editable : false,sortable : false,search : false,  align : 'center'}
					  		]"
						height="200" 
						caption="pg.merchntgrid.list" 
						isChildGrid="false"
						hasActive="true"
						hasEdit="true"
                        showInDialog="false"  
						hasViewDet="false" 
						hasDelete="true"
						loadonce="false" 
						sortCol="rowId" 
						showrow="true" />
				</div>

			</div>
		</div>

	</div>
