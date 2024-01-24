<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript">
function getStatus2(event,obj)
{
	var keycode = (event.keyCode ? event.keyCode : event.which);
	if(keycode == '13'){

		findAll(obj);	

	}
}

function getEmployeeDetails(rowId){
	
	var url	="AgencyAuthorizationForm.html?edit";
	var divName	=	formDivName;
	var requestData = {"rowId" : rowId};
	var ajaxResponse = doAjaxLoading(url, requestData, 'html', divName);
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	$(divName).show();
}

  function openUpdateForm(formName,actionParam) {	
	
	var url	="AgencyAuthorizationForm.html?save";	
	
	//var rowId = url.split("=")[1];		
	
	//var URL='AgencyAuthorization.html?isEditable';	
	
	//var requestData = {"rowId" : rowId};
	
	/* var result = __doAjaxRequest(URL,'POST',requestData,false,'json');
	
	if(result == 1) {
		
		$(errMsgDiv).html("Record is inserted through Data Entry hence you won't be able to Authorize this record.");		
		$(errMsgDiv).show();
		showModalBox(errMsgDiv);
	}else if(result == 2){
		$(errMsgDiv).html("Not Unique Record in Agency Master against EmpId.So Please Contact to Administrator.");		
		$(errMsgDiv).show();
		showModalBox(errMsgDiv);
	}else if(result == 3){
		$(errMsgDiv).html("Some Problem Occured During Retrieve Agency Type.So Please Contact to Administrator.");		
		$(errMsgDiv).show();
		showModalBox(errMsgDiv);
	}else { */
		
		var requestData = __serializeForm(theForm);
		var ajaxResponse = doAjaxLoading(url, requestData, 'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		
		prepareTags();
		
		$(divName).show();
	/* }  */
} 

</script>
<ol class="breadcrumb">
<li><a href="AdminHome.html"><spring:message code="menu.home"/></a></li>
<li><a href="javascript:void(0);"><spring:message code="eip.authentication"/></a></li>
<li><a href="javascript:void(0);"><spring:message code="eip.transactions"/></a></li>
<li class="active"><spring:message code="eip.agencyauthorization"/></li>
</ol>

<div class="content"> 
	<div class="widget">


<%-- <apptags:helpDoc url="AgencyAuthorization.html"></apptags:helpDoc> --%>

<div class="widget-header">
	<h2><spring:message code="eip.agencyauthorization"/></h2></div>

<div id="content" class="widget-content padding">
           <%-- <div class="mand-label">
				<span><spring:message code="MandatoryMsg" /></span>
			</div> --%>
    <form:form action="AgencyAuthorization.html" method="POST" class="form-horizontal">
		  <jsp:include page="/jsp/tiles/validationerror.jsp"/>
			  
			  <div class="form-group">
			  		 <div >
						<label class="col-sm-2 control-label required-control" ><spring:message	code="eip.agency.reg.type" text="Type of Agency " /> :</label>
				     </div>
				     <div class="col-sm-4">
						<form:select cssClass="mandClassColor form-control"  path="entity.emplType">
							<form:option value="0">Select Agency Type</form:option>
							<c:forEach items="${command.lookUps}" var="lookup">
								<form:option value="${lookup.lookUpId}">${lookup.lookUpDesc}</form:option>
							</c:forEach>
						</form:select> 
					</div>
					 					 
					<div >
				 		<label class="col-sm-2 control-label required-control" ><spring:message code="eip.agency.auth.name"/> :</label>
							
				    </div>
				    <div class="col-sm-4">
							<form:input  cssClass="maxLength30 form-control"   path="agencyName" onkeypress="getStatus2(event,this)"  />
					</div>			
							
				</div>
				<br/>
			<div class="form-group">
			
					<div>
						<label class="col-sm-2 control-label required-control"><spring:message code="eip.agency.auth.sortBy" text="Sort By"/> :</label>
					</div>
					<div class="col-sm-4">
					<form:select path="statusIS" cssClass="mandClassColor form-control">
					<option value="0" ><spring:message code="" text="Select Option" /> :</option>
					<form:option value="P" ><spring:message code="eip.agency.auth.pending" text="Pending"/></form:option>
					<form:option value="A"><spring:message code="eip.agency.auth.appr" text=""/></form:option>
					<form:option value="R"><spring:message code="eip.agency.auth.rejc" text="Rejected"/></form:option>
					<form:option value="H"><spring:message code="eip.agency.auth.hold" text="Hold"/></form:option>
					</form:select>
					</div>
				</div>
			
			 <div class="text-center padding-10">
				<a href="javascript:void(0);" class="btn btn-success" onclick="findAll(this)" ><spring:message code ="eip.search"/></a>
			 </div>
          </form:form>
           <div class="form-group">
             <apptags:jQgrid id="agencyAuthorization"
							 url="AgencyAuthorization.html?AGENCY_LIST" 
							 mtype="post"
							 gridid="gridAgencyAuthorizationForm"
							 colHeader="eip.agency.auth.name,eip.agency.reg.mobNo,eip.agency.Status,lgl.view"
							 colModel="[
											{name : 'empname',index : 'empname',editable : false,sortable : true,search : false,align : 'center',width :'80'},
											{name : 'empmobno',index : 'empmobno',editable : false,sortable : true,search : false,align : 'center',width :'90'},
											{name : 'authStatus',index : 'authStatus',editable : false,sortable : true,search : false,align : 'center',width :'70'},
											{name : 'selectTemplet',index : 'selectTemplet',width :'50',align : 'center'}
				 						]"
							sortCol="rowId"
							isChildGrid="false"
							hasActive="false"
							hasViewDet="false"
							hasDelete="false"
							height="500" 
							caption="eip.agencyGridCaption" 
							loadonce="true" 
							hasEdit="false"/>
						
			</div>
			</div>
		</div>
	</div>

		 		