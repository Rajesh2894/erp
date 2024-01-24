<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>


  <ol class="breadcrumb">
      <li><a href="CitizenHome.html"><strong class="fa fa-home"></strong></strong><spring:message code="home" text="Home"/></a></li>
      <li><spring:message code="eip.citizen.aboutUs.title"/></li>
    </ol>
 <div id="content" class="content"> 
      <div class="row">
        <div class="col-md-12 portlets">
          <div class="widget">
            <div class="widget-header ">
              <h2><spring:message code="eip.citizen.aboutUs.title"/></h2>
              <%-- <div class="additional-btn"><a href="#" class="widget-toggle"><i class="icon-down-open-2"></i></a></div> --%>
             </div>
             
                <div class="widget-content padding">
              		<p>${command.aboutUsDescFirstPara}</p><br>
					<p>${command.aboutUsDescSecondPara}</p>
            	</div>
            	</div>
          
          </div>
        </div>
      </div>		

