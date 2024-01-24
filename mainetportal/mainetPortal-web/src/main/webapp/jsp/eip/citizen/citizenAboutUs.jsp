<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<ol class="breadcrumb" id="CitizenService">
   <li  class="breadcrumb-item">
      <a href="CitizenHome.html">
         <strong class="fa fa-home"></strong>
         <spring:message code="home" text="Home"/>
      </a>
   </li>
   <li class="breadcrumb-item active">
      <spring:message code="eip.citizen.aboutUs.title"/>
   </li>
</ol>

<div class="container-fluid dashboard-page">
   <div class="col-sm-12" id="nischay">
      <h2>
         <spring:message code="top.aboutus"/>
      </h2>	
      <div class="widget-content padding form-horizontal">
         <div class="panel-group accordion-toggle" id="accordion_single_collapse">
            <div class="panel panel-default">
               <%-- <div class="panel-heading">
                  <div class="additional-btn"><a href="#" class="widget-toggle"><i class="icon-down-open-2"><span class="hide">help</span></i></a></div>
               </div> --%>
               <div class="panel-collapse collapse in">
                  <div class="panel-body">
                     <p>${command.aboutUsDescFirstPara}</p>
                     <p>${command.aboutUsDescSecondPara}</p>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
</div>
<hr/>