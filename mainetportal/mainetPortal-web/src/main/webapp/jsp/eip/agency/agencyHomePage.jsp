
<div id="onlineServices" title="D2K Application" style="display: none" class="leanmodal"></div>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="js/mainet/dashboard/tabs.js"></script>
<script src="js/mainet/dashboard/highcharts.js"></script>
<script src="js/mainet/dashboard/moment.min.js"></script>
<script src="js/mainet/dashboard/fullcalendar.min.js"></script>
<script src="js/mainet/dashboard/gcal.js"></script>
<script src="js/mainet/dashboard/dashboard.js"></script>
<link rel="stylesheet" type="text/css" href="css/mainet/dashboard/fullcalendar.css"/>
<script src="js/eip/agency/agencyResetPasswordProcess.js"></script>

<script>
var setp2Form = 'verifyOTPForm';
function getALoginForm() {
	var msg = getLocalMessage("citizen.login.resetPass.success.msg");

	var childPoppup = '.popup-dialog';

	$(childPoppup).html("<h3>"+msg+"</h3>");
	$(childPoppup).show();
	showModalBox(childPoppup);
}

function getAgencyResetPassStepII() {

	
		var formData = $(document.getElementById(setpIFormName)).serialize();
		var response = __doAjaxRequest(agencyResetPassword+getOTPform, 'post', formData, false, 'html');
		openPopupWithResponse(response);
		
	
}
</script>

<style >
	.afterDocUpload{
			border-style: solid;
			border-color:windowframe;
			border-width: 2px;
			text-align: center;
			
	}

</style>

<c:choose>
	<c:when test="${userSession.getEmployee().getAuthStatus() eq '' or userSession.getEmployee().getAuthStatus() eq 'P' or userSession.getEmployee().getAuthStatus() eq 'D' 
			and userSession.getEmployee().getIsUploaded() eq 'Y'}">
		<h2><spring:message code="eip.citizen.home.msg" text="Welcome to bihar nagar seva!"></spring:message></h2>

	<br><br><br>
	<div class="afterDocUpload">
		<h3><spring:message code="eip.agency.upload.msg.onUpload.head" /></h3><br>
			<h3><spring:message code="eip.agency.upload.msg.onUpload" /> </h3>
	
	</div>

	</c:when>
	<c:otherwise>
		
		 <div class="form-div">


  <c:set var="now" value="<%=new java.util.Date()%>" />
  
      <ul class="breadcrumbs">
        <li><i class="fa fa-home"></i></li>
        <li><spring:message code="agency.link.name"></spring:message></li>
        <li class="active"><spring:message code="agency.dashboard.heading"></spring:message></li>
      </ul>
      
      
      
      <div id="content" class="dashboard clear">
        <div class="user clear">
        <div class="edit"><a href="CitizenHome.html?EditUserProfile" id="editUserProfile" target="_self"><i class="fa fa-pencil"></i><spring:message code="agency.dashboard.userAccount.edit" text="Edit"></spring:message></a> | <a href="javascript:void(0);" onclick="getAgencyResetPassStepII();" ><i class="fa fa-key"></i> <spring:message code="eip.citizen.login.ResetPassword" text="Reset Password"/></a></div>
          <ul>
            <li><img alt="profile_pic" src="css/images/profile_pic.png"></li>
            <li>
              <ul>
                <li><span>${userSession.employee.empname}&nbsp;${userSession.employee.empMName}&nbsp;${userSession.employee.empLName}</span></li>
                <li>
                    <fmt:formatDate value="${now}" type="both" dateStyle="medium" timeStyle="medium"/>
                </li>
              </ul>
            </li>
           </ul>
        </div>





  
   <div class="pull-left" >
      <div class="grid" id="applicationChart">
      <ul><li>
           <h4 id="statusHeading"><i class="fa fa-pie-chart"></i> <spring:message code="agency.dashboard.services.heading"></spring:message></h4>
		   <div id="container"></div>
          
          
      </li></ul>
     </div>
     <div class="grid" id="paymentChart">
        <ul><li>
            <h4 id="payHeading"><i class="fa fa-pie-chart"></i><spring:message code="agency.dashboard.payments.heading"/></h4>
		    <div id="container1"></div>
        </li></ul>
     </div>
     
     
  </div>
  
<div class="pull-right">

    <div class="grid">
            <ul>
              <li>
                <div id='calendar'></div>
              </li>
            </ul>
         </div>
</div>
		
		
<div class="full clear">
          <ul>
            <li>
              <h4><a href="javascript:void(0);" title="Status of Service Application" class="active" id="stsOfSeviceApp"><i class="fa fa-table"></i> <spring:message code="citizen.services.heading"></spring:message></a><span style="padding:0px 10px;">|</span><a href="javascript:void(0);" title="Status of Payments" id="stsOfPayments"><i class="fa fa-credit-card"></i> <spring:message code="citizen.payments.heading"/></a></h4>
              <!-- Service status -->
              <div id="svcStatusDiv">
                <div class="container">
                  <ul id="tabs">
                    <li class="active" id="Inprocess" title='<spring:message code="agency.dashboard.application.draft.toolkit" />'><spring:message code="agency.dashboard.application.draft"></spring:message></li>
                    <li  id="Completed" title='<spring:message code="agency.dashboard.application.completed.toolkit" />'><spring:message code="agency.dashboard.application.completed"></spring:message></li>
                    <li  id="Rejected" title='<spring:message code="agency.dashboard.application.rejected.toolkit" />'><spring:message code="agency.dashboard.application.rejected"></spring:message></li>
                     <li  id="Progress" title='<spring:message code="agency.dashboard.application.inprocess" />'><spring:message code="agency.dashboard.application.inprocess"></spring:message></li> 
                  </ul>
                  <div class="overflow_auto">
                    <ul id="tab">
                      <li class="active" id="In">
                        <apptags:jQgrid id="applicationForms"
		                              url="ResubmissionOfApplication.html?RESUBMISSION_DRAFT_LIST" mtype="post"
		                              gridid="gridApplicationComp"
		                               colHeader="agency.dashboard.draftId,agency.dashboard.serviceName,agency.dashboard.applicationDate,grid.viewDet"
		                             colModel="[	
	
	                                            {name : 'draftId',index : 'draftId',editable : false,sortable : true,search : true,align : 'center',width :'100'},
												{name : 'serviceName',index : 'serviceName',editable : false,sortable : true,search : false,align : 'center',width :'150' },
												{name : 'applicationDate',index : 'applicationDate',editable : false,sortable : true,search : false,align : 'center',width :'100',formatter: 'date', formatoptions: { newformat: 'd/m/Y'}},
												{name : 'selectedApplicationView',index : 'selectedApplicationView',align : 'center',sortable : false,search : false,width :'50'}
										  ]"
								sortCol="rowId" isChildGrid="false" hasActive="false" 
								hasViewDet="false" viewAjaxRequest="false" hasDelete="false" showrow="true"
								height="150" caption="eip.draftGridLabel" loadonce="true" /> 
                      </li>
                      <li id="Co">
                         <apptags:jQgrid id="completedForm"
									url="ResubmissionOfApplication.html?RESUBMISSION_APPLICANT_LIST" mtype="post"
									gridid="gridApplicationCompleted"
									colHeader="agency.dashboard.repayment.applicationid,agency.dashboard.serviceName,agency.dashboard.applicationDate,grid.viewDet"
									colModel="[	
									                                        {name : 'applicationNo',index : 'applicationId',editable : false,sortable : true,search : true,align : 'center',width :'115'},
																			{name : 'serviceName',index : 'serviceName',editable : false,sortable : true,search : false,align : 'center',width :'273' },
																			{name : 'applicationDate',index : 'applicationDate',editable : false,sortable : true,search : false,align : 'center',width :'150',formatter: 'date', formatoptions: { newformat: 'd/m/Y'}},
																			{name : 'selectedApplicationView',index : 'selectedApplicationView',align : 'center',sortable : false,width :'100',search : false}
											  ]"
									sortCol="rowId" isChildGrid="false" hasActive="false" 
									hasViewDet="false" viewAjaxRequest="false" hasDelete="false" showrow="true" 
									height="150" caption="agency.dashboard.applicantionDetailsView" loadonce="true" postData="Y" /> 
                      </li>
                      <li id="Re">
                        <apptags:jQgrid id="applicationForm"
									url="ResubmissionOfApplication.html?RESUBMISSION_APPLICANT_LIST" mtype="post"
									gridid="gridApplicationCompDetails"
									colHeader="agency.dashboard.repayment.applicationid,agency.dashboard.serviceName,agency.dashboard.applicationDate,grid.viewDet"
									colModel="[
									                                        {name : 'applicationNo',index : 'applicationNo',editable : false,sortable : true,search : true,align : 'center',width :'115'},
																			{name : 'serviceName',index : 'serviceName',editable : false,sortable : true,search : false,align : 'center',width :'273' },
																			{name : 'applicationDate',index : 'applicationDate',editable : false,sortable : true,search : false,align : 'center',width :'150',formatter: 'date', formatoptions: { newformat: 'd/m/Y'}},
																			{name : 'selectedApplicationView',index : 'selectedApplicationView',align : 'center',sortable : false,search : false, width :'100'}
											  ]"
									sortCol="rowId" isChildGrid="false" hasActive="false" 
									hasViewDet="false" viewAjaxRequest="false" hasDelete="false" showrow="true" 
								height="150" caption="agency.dashboard.applicantionDetailsView" loadonce="true" postData="N" />
                      </li>
                       <li id="Ap">
                        			
						<apptags:jQgrid id="pedingApplicationForm"
						             url="ResubmissionOfApplication.html?GET_APPLICATION_INPROCESS_LIST" mtype="post"
						            gridid="gridApplicationStatusInProcess"
						           colHeader="agency.dashboard.repayment.applicationid,agency.dashboard.serviceName,agency.dashboard.applicationDate,grid.viewDet"
						          colModel="[
													{name : 'apmId',index : 'id', editable : false,sortable : true,search : true, align : 'center',width :'135',key:true },
													{name : 'apmServiceName',index : 'apmServiceName', editable : false,sortable : true,search : false, align : 'center',width :'250' },
													{name : 'applicationDate',index : 'applicationDate',editable : false,sortable : true,search : false,align : 'center',width :'150',formatter: 'date', formatoptions: { newformat: 'd/m/Y'}},
													{name : 'selectedApplicationView',index : 'selectedApplicationView',align : 'center',sortable : false,search : false, width :'100'}
									       ]"
						               height="150" caption="agency.dashboard.applicantionDetailsView" isChildGrid="false"
						                  hasActive="false" hasViewDet="false" hasDelete="false"
						             loadonce="true" sortCol="rowId" showrow="true" />		
						
                      </li>
                      
                    </ul>
                  </div>
                </div>
              </div>
              
              
               <div id="payStatusDiv">
                <div class="container">
                  <ul id="tabs">
                    <li  id="TobePaid" class="active" title='<spring:message code="agency.dashboard.application.pending.toolkit" />'><spring:message code="agency.dashboard.application.tobepaid"></spring:message></li>
                    <li  id="OnlineTransfail" title='Bill Pending'><spring:message code="agency.dashboard.application.online.trans"></spring:message></li>
                   </ul>
                  <div class="overflow_auto">
                    <ul id="tab">
                      <li class="active" id="To">
     					<apptags:jQgrid id="pedingPaymentForm"
						             url="RePayment.html?PENDING_LOI_LIST" mtype="post"
						            gridid="gridPendingLOIForm"
						           colHeader="agency.dashboard.repayment.applicationid,agency.dashboard.loino,agency.dashboard.serviceName"
						          colModel="[
						                             
													{name : 'applicationNo',index : 'applicationNo', editable : false,sortable : true,search : true, align : 'center',width :'140' },
													{name : 'loiNo',index : 'loiNo', editable : false,sortable : true,search : true, align : 'center',width :'130' },
													{name : 'serviceName',index : 'serviceName', editable : false,sortable : true,search : false, align : 'center',width :'285' }
									       ]"
						               height="150" caption="agency.dashboard.applicantionDetailsView" isChildGrid="false"
						                  hasActive="false" hasViewDet="true" hasDelete="false"
						             loadonce="true" sortCol="rowId" showrow="true" />	
                      </li>
                      
                    <li id="On">
     					<apptags:jQgrid id="pendingBill"
						             url="RePayment.html?FAIL_CANCEL_LIST" mtype="post"
						            gridid="gridRePaymentForm"
						           colHeader="agency.dashboard.repayment.applicationid,agency.dashboard.serviceName,eip.stp.tranStatus"
						          colModel="[
													{name : 'id',index : 'id', editable : false,sortable : true,search : true, align : 'center',width :'130' },
													{name : 'serviceName',index : 'serviceName', editable : false,sortable : true,search : false, align : 'center',width :'275' },
										
													{name : 'payStatus',index : 'recvStatus', editable : false,sortable : true,search : false, align : 'center' ,width :'150'}
									       ]"
						               height="150" caption="agency.dashboard.repayment" isChildGrid="false"
						                  hasActive="false" hasViewDet="true" hasDelete="false"
						             loadonce="true" sortCol="rowId" showrow="true" />	
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              
            </li>
          </ul>
          
        </div>
      </div>
    </div>
		
	</c:otherwise>
</c:choose>

