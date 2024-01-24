<%@page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<link href="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css" />
<script src="js/eip/citizen/landingPage.js"></script>
<script src="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>


<div class="department-logo hidden-md hidden-sm hidden-xs">

	<div class="logo-text fadeInLeft animated animated">
          <c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
            <c:set var="parts" value="${fn:split(logo, '*')}" />
            <c:if test="${parts[1] eq '1'}"><a href="CitizenHome.html"><img src="${parts[0]}" alt="<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if><c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if>"></a> </c:if>
          </c:forEach>
          <h1><c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if><c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if></h1>
        </div>
</div>
			
			 
 <%-- <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">  
<div class="ulbselection" style="display:none;">
<form:form name="selectMunicipalForm" id="selectMunicipalForm" action="" class="list" method="get">
	<div class="form-group">
		<label for="selectedOrg"> 
			<spring:message code="eip.landingpage.selectdistrict.msg" text="Select District" />
		</label>   
		<div id="mainselection">
			<select name="orgId" id="selectedOrg" data-content="" class="form-control chosen-select-no-results">
				<option value="-1"><spring:message code="Select" text="Select" /></option>
				<c:forEach items="${command.organisationsList}" var="org">
					<c:if test="${userSession.languageId eq 1}"><option value="${org.orgid}">${org.ONlsOrgname}</option></c:if>
					<c:if test="${userSession.languageId eq 2}"><option value="${org.orgid}">${org.ONlsOrgnameMar}</option></c:if>
				</c:forEach>
			</select>
			</div>
	</div>
	<div class="text-center">
		<input type="button" class="btn btn-primary btn-block" value="<spring:message code="bt.save" text="save"/>" id="submitMunci" />
	</div>
</form:form>
</div>
 </c:if> --%>
 
<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
<div class="carousel-inner" role="listbox">
<c:set var="maxfilecount" value="${userSession.getSlidingImgLookUps().size()}" />
<c:if test="${userSession.getSlidingImgLookUps().size() gt 0}">
<c:forEach items="${userSession.slidingImgLookUps}" var="slide" varStatus="status">
<c:set var="data" value="${fn:split(slide,'*')}"/>
<c:set var="image" value="${data[0]}"/>
<c:if test="${status.index eq 0 }"><div class="item active"><img src="./${image}" alt="Image Sliding"/></div></c:if>
<c:if test="${status.index ne 0 }"><div class="item"><img src="./${image}" alt="Image Sliding"/></div></c:if>
</c:forEach>
</c:if>
</div> 
<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev"><span class="fa fa-angle-left" aria-hidden="true"></span> <span class="sr-only">Previous</span></a> 
<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next"><span class="fa fa-angle-right" aria-hidden="true"></span> <span class="sr-only">Next</span></a>
</div>
<!-- Start info box -->
${command.setThemeMap()}
<c:if test="${command.themeMap['COMMITTEE_MEMBERS'] ne 'I'}">
 <div class="container clear padding-top-20">
  <div class="row">
  <div class="col-lg-12">
   <div class="widget">
<div class="widget-header"><h2><spring:message code="CommitteeMembers" text="Committee Members"/></h2></div>
<div class="widget-content padding">
<div class="row active-with-click">
        
        <div class="col-sm-4">
        	<% if (session.getAttribute("mayorProfile") != "") { String mayorImage = session.getAttribute("mayorprofileImage") .toString(); %>
        	<form:form name="mayor" action="SectionInformation.html?ViewCommitee" method="post" id="mayorid">
				<input type="hidden" name="prefixvalue" value="MYR">
			</form:form>
            <article class="material-card Red">
                <h2>
                    <span><%	if (session.getAttribute("mayorName") != null) {	%>
						  <%=session.getAttribute("mayorName")%></span>
                    <strong>
                        <i class="fa fa-certificate"></i>
                        <%=session.getAttribute("mayorProfile")%> <% } %>
                    </strong>
                </h2>
                <div class="mc-content">
                    <div class="img-container">
                       <a id="mayorinfo"><img src="<%=mayorImage%>" alt="<%=mayorImage%>" title="<%=session.getAttribute("mayorName")%>" class="img-responsive" /></a>
                    </div>
                    <div class="mc-description">
                     <%	if (session.getAttribute("SummaryEng") != null) {	%>   
                        <%=session.getAttribute("SummaryEng")%>
                     <% } %>   
                    </div>
                    
                </div>
                <a class="mc-btn-action"><i class="fa fa-comment-o"></i></a>
                
                <div class="mc-footer">     
                <%	if (session.getAttribute("EmailId") != null && session.getAttribute("EmailId") != "" ) {	%>                 
                    <a href="mailto:<%=session.getAttribute("EmailId")%>"><i class="fa fa-envelope-o"></i> <%=session.getAttribute("EmailId")%></a>
                <% } %>
                </div>
               
            </article>
            <% } %>
        </div>
        
        
        
        <div class="col-sm-4">
        <% if (session.getAttribute("deputyMayorProfileImage") != "") { String deputyImage = session.getAttribute("deputyMayorProfileImage").toString();	%>
           <form:form name="dymayor" id="dymayorid" action="SectionInformation.html?ViewCommitee" method="post">
				<input type="hidden" name="prefixvalue" value="DMYR">
			</form:form>
			 <article class="material-card Indigo">
                <h2><span><% if (session.getAttribute("deputyMayorName") != null) { %><%=session.getAttribute("deputyMayorName")%>
				<%	}	%></span>
                    <strong><i class="fa fa-certificate"></i><%=session.getAttribute("deputyMayorProfile")%></strong></h2>
                <div class="mc-content">
                    <div class="img-container">
                        <a id="deputyMayorinfo"><img src="<%=deputyImage%>" alt="<%=deputyImage%>" title="<%=session.getAttribute("deputyMayorName")%>" class="img-responsive"/></a>
                    </div>
                    <div class="mc-description">
                       <% if (session.getAttribute("deputyMayorSummaryEng") != null) { %>
							<%=session.getAttribute("deputyMayorSummaryEng")%>
						<% } %> 
                    </div>
                </div>
                <a class="mc-btn-action"><i class="fa fa-comment-o"></i></a>
                <div class="mc-footer"><%	if (session.getAttribute("deputyMayorEmailId") != "") { %>
                    <a href="mailto:<%=session.getAttribute("deputyMayorEmailId")%>"><i class="fa fa-envelope-o"></i> <%=session.getAttribute("deputyMayorEmailId")%></a>
                    <%	}	%>
                </div>
            </article>
            <% }	%>
        </div>
        <div class="col-sm-4">
        <% if (session.getAttribute("commissionerName") != null) { String commissionarImage = session.getAttribute("commissionerProfileImage").toString();	%>
            <form:form name="com" id="commid" action="SectionInformation.html?ViewCommitee" method="post">
				<input type="hidden" name="prefixvalue" value="COM">
			</form:form>
			<article class="material-card Teal">
                <h2><span><% if (session.getAttribute("commissionerName") != null) { %>
				<%=session.getAttribute("commissionerName")%><% } %></span><strong>
                        <i class="fa fa-certificate"></i>
                       <% if (session.getAttribute("commissionerProfile") != null) { %> <%=session.getAttribute("commissionerProfile")%> <% } %>
                    </strong>
                </h2>
                <div class="mc-content">
                    <div class="img-container"><a id="commisionarinfo"><img src="<%=commissionarImage%>" alt="<%=commissionarImage%>" title="<%=session.getAttribute("commissionerName")%>" class="img-responsive"/></a></div>
                    <div class="mc-description">
						<% if (session.getAttribute("commissionerSummaryEng") != null) { %>
							<%=session.getAttribute("commissionerSummaryEng")%>
						<% } %> 
                    </div>
                </div>
                <a class="mc-btn-action"><i class="fa fa-comment-o"></i></a>
                <div class="mc-footer">
                <% if (session.getAttribute("commissionerEmailId") != null && session.getAttribute("commissionerEmailId")!="") { %>                    
               		<a href="mailto:<%=session.getAttribute("commissionerEmailId")%>"><i class="fa fa-envelope-o"></i> <%=session.getAttribute("commissionerEmailId")%></a>
                <% } %>
                </div>
            </article>
            <% } %>
        </div>
        </div>
    </div>
</div>
        </div>
    </div>
</div>
 

</c:if>


 
<div class="container clear drag">
<div class="row">
<c:if test="${command.themeMap['PUBLIC_NOTICE'] eq 'A' and command.themeMap['NEWS'] eq 'A'}">
<c:set var="class" value="col-lg-6"/>
</c:if>

<c:if test="${command.themeMap['PUBLIC_NOTICE'] eq 'I' or command.themeMap['NEWS'] eq 'I'}">
<c:set var="class" value="col-lg-12"/>
</c:if>
<c:if test="${command.themeMap['PUBLIC_NOTICE'] ne 'I'}">

		<div class="${class}">
		<div class="widget">
		<div class="widget-header"><h2><i class="fa fa-newspaper-o"></i> <spring:message code="PublicNotice" text="Public Notice"/></h2><div class="additional-btn">
        	<div class="control pause auto-scrolling-toggle"> <span class="left"></span><span class="right"></span> </div>
        </div></div>
		<div class="widget-content announcement height-295 scrolllistbox magazine-section public-notice">
 					<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">							
					<div class="public-notice">
 						<div class="col-md-1 col-sm-1 col-lg-1 col-xs-1"><span class="serialno-new">${lk.count}</span></div>
 														
						 <div class="col-md-11 col-sm-11 col-lg-11 col-xs-11">
				         <c:set var="link" value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />
						 	<a href="javascript:void(0)">
 						   <c:choose>
						     <c:when test="${isDMS}">
						       <c:choose>
								<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
									<a href="javascript:void(0);" onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.detailEn}</a> 
								</c:when>	
								<c:otherwise>					
							 <a href="javascript:void(0);" onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.detailReg}</a>
								</c:otherwise>
							</c:choose>
						    </c:when>
						   <c:otherwise>
						   <c:choose>
								<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
									<apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailEn}"></apptags:filedownload>
								</c:when>	
								<c:otherwise>					
									<apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailReg}"></apptags:filedownload>
								</c:otherwise>
							</c:choose>
						  </c:otherwise>	
						</c:choose>
						</a>
 									<p><fmt:formatDate type="date" value="${lookUp.issueDate}" pattern="dd/MM/yyyy" var="issueDate" />	${issueDate}</p>	
									<input type="hidden" name="downloadLink" value="${lookUp.profileImgPath}">	
			                   
			                   <c:choose>
				                   <c:when test="${isDMS}">
				                      <a href="jadocIdvascript:void(0);" onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.docName}</a>
				                   </c:when>
				                   <c:otherwise>
										<apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" 
										eipFileName="${stringUtility.getStringAfterChars(lookUp.profileImgPath)}"></apptags:filedownload>
				                   </c:otherwise>		
			                   </c:choose>
						</div>
					</div>
					</c:forEach>
 				
			</div>
	</div>
</div>
</c:if>

<c:if test="${command.themeMap['NEWS'] ne 'I'}">
	<div class="${class}">
	<div class="widget">
	<div class="widget-header"><h2><i class="fa fa-newspaper-o"></i> <spring:message code="News" text="News"/></h2></div>
	<div class="widget-content announcement magazine-section">
		<div class="col-lg-12 col-md-12 scrolllistbox height-295">
		<c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status">
		<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
		<c:forEach items="${links}" var="download" varStatus="count">
			<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
								<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
								<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}" />
								<div class="single-news">
								<div class="row">
								<c:choose>
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
										<div class="col-md-3 col-sm-3 col-lg-3 col-xs-3">
										 <div class="view overlay hm-white-slight z-depth-1-half">
										
										 <c:if test="${ lookUp.attachImage ne ' '}">
										 <img src="${lookUp.attachImage }" class="img-fluid" alt="Minor sample post image">
										 </c:if>
										 <c:if test="${ lookUp.attachImage eq ' '}">
										 <img src="\images\news.svg" class="img-fluid" alt="Minor sample post image">
										 </c:if>
 										</div>
										</div>
										
										<div class="col-md-9 col-sm-9 col-lg-9 col-xs-9 news-image">
										
										<c:choose>
						                    <c:when test="${isDMS}">
						                    <a href="javascript:void(0);" onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a>
 						                    </c:when>
										   <c:otherwise>
										     <apptags:filedownload filename="EIP" filePath="${download}" actionUrl="CitizenHome.html?Download" eipFileName="${lookUp.announceDescEng}" ></apptags:filedownload>
										    <p><a href="javascript:void(0);" class="download"><i class="fa fa-file-pdf-o" aria-hidden="true"></i> Download PDF</a></p>
										</c:otherwise>
										</c:choose> 
										<p><strong><fmt:formatDate type = "date" value="${lookUp.lmodDate}"  dateStyle="FULL"/></strong></p>
										
										
										</div>
									</c:when>
									<c:otherwise>
									<div class="col-md-3 col-sm-3 col-lg-3 col-xs-3">
										 <div class="view overlay hm-white-slight z-depth-1-half">
										 <c:if test="${ lookUp.attachImage ne ' '}">
										 <img src="${lookUp.attachImage }" class="img-fluid" alt="Minor sample post image">
										 </c:if>
										 <c:if test="${ lookUp.attachImage eq ' '}">
										 <img src="\images\news.svg" class="img-fluid" alt="Minor sample post image">
										 </c:if>
 										</div>
										</div>
  										<div class="col-md-9 col-sm-9 col-lg-9 col-xs-9 news-image">
										<p><strong><fmt:formatDate type = "date" value="${lookUp.lmodDate}"  dateStyle="FULL"/></strong></p>
										<c:choose>
						                    <c:when test="${isDMS}">						                    
						                      <a href="javascript:void(0);" onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
						                    </c:when>
										     <c:otherwise>
										       <apptags:filedownload filename="EIP" filePath="${download}" actionUrl="CitizenHome.html?Download" eipFileName="${lookUp.announceDescReg}"></apptags:filedownload>
										   		<p><a href="javascript:void(0);" class="download"><i class="fa fa-file-pdf-o" aria-hidden="true"></i> Download PDF</a></p>
										     
										     </c:otherwise>
										     </c:choose>
										</div>
									</c:otherwise>
								</c:choose>
								</div>
								</div>
							</c:forEach>
						</c:forEach>
					</div>							
				</div>
       		</div>
       		</div>  
       		</c:if>
</div>
</div>


<div class="col-lg-12 complaint">
            <div class="widget">
              
              <div class="widget-content">
                 
				 <div class="accordion">
				 
				 <div class="container">
	<div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 bhoechie-tab-container">
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 bhoechie-tab-menu">
              <div class="list-group">
                <a href="#" class="list-group-item text-center active" >
               Make a Complaint
                </a>
                <a href="#" class="list-group-item text-center" >
                 Check Status
                </a>
                <a href="#" class="list-group-item text-center" >
                 Make Payment
                </a>
                <a href="#" class="list-group-item text-center" >
                 Get the App
                </a>
               
              </div>
            </div>
            <div class="col-lg-10 col-md-10 col-sm-10 col-xs-10 bhoechie-tab">
                <!-- flight section -->
                <div class="bhoechie-tab-content active">
					  <div class="card col-sm-6">
    <div class="card-header" id="headingOne">
      <h5>Nidaan 1100</h5>
    </div>

    <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
      <div class="card-body">
	  Online Grievance Redressal System Web based software, aimed at providing facilities to all the citizens of Chhattisgarh, basically water supply, road lighting and sanitation facilities to the citizens!
      <br><br><p class="clearfix"><b class="text-red-1">The time to file a complaint is from 08:00 to 06:00</b></p>

	  
	  </div>
    </div>
  </div>
  <div class="card col-sm-6">
    <div class="card-header" id="headingTwo">
      <h5>Grievance Registration </h5>
    </div>
    <div id="collapseTwo" class="collapse show" aria-labelledby="headingTwo" data-parent="#accordion">
      <div class="card-body">
	  <ul class="free-services">
	  <li><a  onclick="myFunction()" title="Via Telephone"  ><img src="assets/img/telephone.png" class="img-responsive" alt="Via Telephone" title="Via Telephone" ><b>Telephone</b> </a></li>
	  <li ><a  href="grievance.html" title="Via Internet" ><img src="assets/img/internet-icon.png" class="img-responsive" alt="Via Internet" title="Via Internet" ><b>Internet</b> </a></li>
	  </ul>
	  
	    <div class="col-sm-8">
  
    <div  id="telephone-view" style="display:none;">
      <p>Citizens can Dial 1100 number to Register the Grievance. </p> 
 
    </div>

	  
	  </div>
    </div>
	</div>
	</div>
    </div>
                <!-- train section -->
                <div class="bhoechie-tab-content" id="tab2">
                      <div class="card col-sm-6">
    <div class="card-header" id="headingTwo">
      <h5>Check Status </h5>
    </div>
    <div id="collapseTwo" class="collapse show" aria-labelledby="headingTwo" data-parent="#accordion">
      <div class="card-body">
	  <ul class="free-services ">
	  	  <li class=""><a  href="grievance.html?grievanceStatus" title="Complaint Status" > <img src="assets/img/know-your-status.png" alt="Complaint Status" title="Complaint Status" class="img-responsive"><b>Complaint Status</b> </a></li>
	    <li class=""><a  href="#" title="Applicant Status" > <img src="assets/img/know-your-status.png" alt="Applicant Status" title="Applicant Status" class="img-responsive"><b>Applicant Status</b> </a></li>
	  </ul>
	  
	   
    </div>
	</div>
	</div>
	
	    
                </div>
    
                <!-- hotel search -->
                <div class="bhoechie-tab-content">
                           <div class="card col-sm-12">
    <div class="card-header" id="headingTwo">
      <h5>Make a Payment for:</h5>
    </div>
    <div id="collapseTwo" class="collapse show paymet" aria-labelledby="headingTwo" data-parent="#accordion">
      <div class="card-body">
	<div class="form-group">
       
			  <label for="select-1493186249513" class="col-sm-2 control-label">Select Bill Type </label>
              <div class="col-sm-4">
                <select type="select" class="form-control" name="select-1493186249513" id="select-1493186249513" path=" ">
                  <option value="option-1" selected="true">Property Bill</option>
                  <option value="option-2">Water Bill</option>
                
                </select>
              </div>
              
              
              <label for="text-1493186216894" class="col-sm-2 control-label">Refrence No.</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="text-1493186216894" id="text-1493186216894" path=" ">
              </div>
            </div>
            
            	<div class="form-group">
       
                <label for="text-1493186216894" class="col-sm-2 control-label">Amount</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="text-1493186216894" id="text-1493186216894" path=" " disabled>
              </div>
            </div>
            
            <div class="margin-top-120 text-center clear">
            <button class="btn btn-blue-2">Pay</button>
            
            </div>
    </div>
	</div>
	</div>
                </div>
                <div class="bhoechie-tab-content">
                   
                </div>
             
            </div>
        </div>
  </div>
</div>
				 
				 
				 
				 

   </div>
	</div>
   </div>
   </div>


<%-- <section class="blue">	
<div class="container padding-bottom-20">	
	 <div class="row">
		
		
		
	<div class="col-sm-12 member">		
		<h2><strong><spring:message code="CitizenServices" text="Citizen Services"/></strong></h2>	
		<div class="row services-list">
			<div class="col-sm-4 padding-right-md-0">
			    <ul class="tabs">
					<c:forEach items="${command.getAllDepartment()}" var="dept" varStatus="index">
						<c:choose>
							<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
								<li class="tab-link">${dept.dpDeptdesc}</li>
							</c:when>
							<c:otherwise>
								<li class="tab-link">${dept.dpNameMar}</li>
							</c:otherwise>
						</c:choose>				
					</c:forEach>
				</ul>
			</div>
	<div class="col-sm-8 service-content height-340 padding-left-md-0">
	<c:forEach items="${command.getAllDepartment()}" var="dept" varStatus="index"> 
		<div class="tab-content tab_content_ser">
		 <c:forEach items="${command.getAllServicewithUrl()}" var="ServicewithUrl"> 
				  <c:if test="${dept.dpDeptid eq  ServicewithUrl.departmentid  }">
			       <c:choose>
						<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
					    	<a href="javascript:void(0);" onclick="getCitizenLoginFormredirectLogin('N','${ServicewithUrl.serviceurl}')"> ${ServicewithUrl.serviceName}</a>
					    </c:when>
						<c:otherwise>
					    	<a href="javascript:void(0);" onclick="getCitizenLoginFormredirectLogin('N','${ServicewithUrl.serviceurl}')"> ${ServicewithUrl.serviceNamemar}</a>
		                </c:otherwise>
                   </c:choose> 
                </c:if>								
			</c:forEach> 							
		</div>
	 </c:forEach>

    </div>
 
  </div>
  
 </div>
 </div>
	</div>	
	</section> --%>


<!--  <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Open Modal</button>

  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      Modal content
      <div class="modal-content">
        <div class="modal-header">
        <div class="modal-head">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Modal Header</h4>
          </div>
          
          <div class="modal-head2">
          
          <h4 class="modal-title"><i class="fa fa-file-text-o margin-5"></i> &nbsp; Requied Document</h4>
          </div>
        </div>
        <div class="modal-body">
          
          
          <table class="table table-bordered">
          <tr><th>Sr.No.</th> <th>Proof of Identity (Any -1)</th></tr>
          <tr><td>1</td><td> Pan Card</td></tr>
           <tr><td>2</td><td> Passport</td></tr>
            <tr><td>3</td><td>Semi government ID card</td></tr>
            <tr><th>Sr.No.</th> <th>Proof of Address (Any -1)</th></tr>
          <tr><td>1</td><td> Pan Card</td></tr>
           <tr><td>2</td><td> Passport</td></tr>
            <tr><td>3</td><td>Semi government ID card</td></tr>
          
          </table>
           <table class="table table-bordered">
          <tr><th>Sr.No.</th> <th>Proof of Identity (Any -1)</th></tr>
          <tr><td>1</td><td> Pan Card</td></tr>
           <tr><td>2</td><td> Passport</td></tr>
            <tr><td>3</td><td>Semi government ID card</td></tr>
            <tr><th>Sr.No.</th> <th>Proof of Address (Any -1)</th></tr>
          <tr><td>1</td><td> Pan Card</td></tr>
           <tr><td>2</td><td> Passport</td></tr>
            <tr><td>3</td><td>Semi government ID card</td></tr>
          
          </table>
        </div>
        <div class="modal-footer text-center">
        <button type="button" class="btn btn-blue-2" data-dismiss="modal">Apply</button>
          <button type="button" class="btn btn-warning" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div> -->
  



<div class="container clear drag">
  <div class="row">
  
  <c:if test="${command.themeMap['PHOTO_GALLERY'] eq 'A' and command.themeMap['VIDEO_GALLERY'] eq 'A'}">
<c:set var="class" value="col-lg-6"/>
</c:if>

<c:if test="${command.themeMap['PHOTO_GALLERY'] eq 'I' or command.themeMap['VIDEO_GALLERY'] eq 'I'}">
<c:set var="class" value="col-lg-12"/>
</c:if>
<c:if test="${command.themeMap['PHOTO_GALLERY'] ne 'I'}">
  <div class="${class}">
	  <div class="widget">
			<div class="widget-header">
			<h2><i class="fa fa-picture-o" aria-hidden="true"></i> <spring:message code="PhotoGallery" text="Photo Gallery"/></h2>
			</div>
			<div class="widget-content">
			<div id="service-carousel2" class="owl-carousel">
			<c:forEach items="${command.getAllphotos('photo gallery')}" var="dept">
			<div class="item"><a class="fancybox" rel="gallery" href="./${dept.imagePath}"><img src="${dept.imagePath}" class="img-thumbnail"  alt="Loading please wait"/></a></div>
			</c:forEach>
	 		</div>	
			</div>
		</div>
	</div>
</c:if>
	
<c:if test="${command.themeMap['VIDEO_GALLERY'] ne 'I'}">
		<div class="${class}">
		 <div class="widget">
			<div class="widget-header">
			<h2><i class="fa fa-video-camera" aria-hidden="true"></i> <spring:message code="VideoGallery" text="Video Gallery"/></h2>
			</div>
			<div class="widget-content">
			<div id="video-gallery" class="owl-carousel">
	 		<c:forEach items="${command.getAllvideos('video gallery')}" var="dept2">
			<div class="item"><video alt="Videos" width="100%" controls><source src="./${dept2.imagePath}" type='video/mp4; codecs="avc1.42E01E, mp4a.40.2"'></video></div>
			</c:forEach>
		</div>
		</div>
		
		
	  </div>
	</div>
</c:if>

</div>
</div>

<c:if test="${command.themeMap['RTI'] ne 'I'}">
	
	<section class="white">
			<div class="container member">
			<h2 class="text-center"><spring:message code="RighttoInformation" text="Right to Information"/></h2>
			<div class="row">
				<div class="col-sm-12">
					<ul class="list-row">
						<c:forEach items="${command.gethomepagelink('Right to Information')}" var="lookUps" varStatus="lk">
						 	 <li> 
								<c:forEach items="${lookUps}" var="lookUp">
									<c:if test="${lookUp.lookUpType==MainetConstants.TEXT_FIELD }">
										 <c:set var="URLvalue" value="${lookUp.lookUpDesc}" />
									</c:if>
									<c:if test="${lookUp.lookUpType==MainetConstants.ATTACHMENT_FIELD }">
										<c:set var="links" value="${fn:split(lookUp.lookUpDesc, ',')}" />
										<c:forEach items="${links}" var="download" varStatus="status">
												<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}" />
												<c:set var="path" value="${stringUtility.getStringBeforeChar('/',download)}" />
												<c:if test="${not empty lookUp.lookUpDesc}">
													<a href="javascript:void(0);" onClick="downloadFile('${lookUp.descLangSecond}','SectionInformation.html?Download')" > ${URLvalue}</a>   
												</c:if>
												<c:if test="${empty lookUp.lookUpDesc}">
												</c:if>
											</c:forEach>
									</c:if>
								</c:forEach>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			</div>
	</section>
</c:if>





<div id="footer">
<div class="container">
	<div class="row">
		<div class="col-md-4">			
              <ul class="nav nav-tabs nav-justified">
                <li class="active"> <a href="#popular10" data-toggle="tab" class="text-center"><i class="fa fa-facebook"></i> <spring:message code="Facebook" text="Facebook"/></a> </li>
                <li> <a href="#recent10" data-toggle="tab" class="text-center"><i class="fa fa-twitter"></i> <spring:message code="Twitter" text="Twitter"/></a> </li>
              </ul>
              <div class="tab-content p-none">
                <div id="popular10" class="tab-pane bg-info text-center active">
                	<iframe src="<spring:message code="facebook.url" text="facebookurl"/>" width="100%" height="320" style="border:none;overflow:hidden" scrolling="no" allowTransparency="true"></iframe>
                  </div>
                <div id="recent10" class="tab-pane bg-info text-center">
					<div style="overflow-y:scroll; height:325px;">
						<a class="twitter-timeline" href="<spring:message code="twitter.url" text="twitterurl"/>">Tweets</a> 
						<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script> 
					</div>
				</div>
              </div>
		</div>
		<div class="col-md-8">
		<h2><spring:message code="cms.useful.link" text="Useful Links"/></h2>
		<div class="row">
			<div class="col-sm-4">
				<ul class="nav nav-list narrow">
					<li><a href="http://india.gov.in" target="_new"><i class="fa fa-angle-double-right"></i> India Portal</a></li>
					<li><a href="http://supremecourtofindia.nic.in" target="_new"><i class="fa fa-angle-double-right"></i> Supreme Court of India</a></li>
                    <li><a href="http://presidentofindia.nic.in" target="_new"><i class="fa fa-angle-double-right"></i> President of India</a></li>
                    <li><a href="http://parliamentofindia.gov.in/" target="_new"><i class="fa fa-angle-double-right"></i> Parliament of India</a></li>
                    <li><a href="http://www.ddinews.com" target="_new"><i class="fa fa-angle-double-right"></i> DD News</a></li>
                    <li><a href="http://pmindia.nic.in" target="_new"><i class="fa fa-angle-double-right"></i> Prime Minister's Office</a></li>
                    <li><a href="http://passport.nic.in/" target="_new"><i class="fa fa-angle-double-right"></i> Passport/Visa</a></li>
                    <li><a href="http://www.eci.gov.in/" target="_new"><i class="fa fa-angle-double-right"></i> Election Commission</a></li>
                    <li><a href="http://www.healthy-india.org" target="_new"><i class="fa fa-angle-double-right"></i> Healthy India</a></li>
                    <li><a href="http://energy.bih.nic.in/" target="_new"><i class="fa fa-angle-double-right"></i> Energy</a></li>
				</ul>
			</div>
			<div class="col-sm-4">
				<ul class="nav nav-list narrow">					
                    <li><a href="https://www.biharcommercialtax.gov.in/" target="_new"><i class="fa fa-angle-double-right"></i> Commercial Taxes</a></li>
                    <li><a href="http://cooperative.bih.nic.in/" target="_new"><i class="fa fa-angle-double-right"></i> Co-operative</a></li>
                    <li><a href="http://disastermgmt.bih.nic.in/" target="_new"><i class="fa fa-angle-double-right"></i> Disaster Management</a></li>
                    <li><a href="http://educationbihar.gov.in/" target="_new"><i class="fa fa-angle-double-right"></i> Education</a></li>
                    <li><a href="http://cjtdp.cg.gov.in/" target="_new"><i class="fa fa-angle-double-right"></i> Tribal Development Programme</a> </li>
                    <li><a href="http://jail.cg.gov.in/" target="_new"><i class="fa fa-angle-double-right"></i> Jail Department</a> </li>
                    <li><a href="http://health.cg.gov.in/" target="_new"><i class="fa fa-angle-double-right"></i> Health and Family Welfare</a> </li>
                    <li><a href="http://www.chhattisgarhtourism.net/" target="_new"><i class="fa fa-angle-double-right"></i> Tourism</a> </li>
                    <li><a href="http://cg.nic.in/pgms/index.htm" target="_new"><i class="fa fa-angle-double-right"></i> Public Grievances</a> </li>
                    <li><a href="http://cg.nic.in/schooleducation/" target="_new"><i class="fa fa-angle-double-right"></i> School Education</a> </li>
				</ul>
			</div>
			<div class="col-sm-4">
				<ul class="nav nav-list narrow">					
                    <li><a href="http://cg.nic.in/stateredcross/" target="_new"><i class="fa fa-angle-double-right"></i> Indian Red Cross Society</a></li>
                    <li><a href="http://cexraipur.gov.in/" target="_new"><i class="fa fa-angle-double-right"></i> Central Excise Raipur</a></li>
                    <li><a href="http://shabari.cg.gov.in/" target="_new"><i class="fa fa-angle-double-right"></i> Department of Rural Industries</a></li>
                    <li><a href="http://morraipur.com/" target="_new"><i class="fa fa-angle-double-right"></i> Know India</a></li>
                    <li><a href="http://blc.bih.nic.in/" target="_new"><i class="fa fa-angle-double-right"></i> Bihar Legislative Council</a> </li>
                    <li><a href="http://cmbihar.in/users/home.aspx" target="_new"><i class="fa fa-angle-double-right"></i> Chief Minister's Office</a> </li>
                    <li><a href="http://cms.bih.nic.in/" target="_new"><i class="fa fa-angle-double-right"></i> Hon'ble CM's Secretariat</a> </li>
                    <li><a href="http://dycm.bih.nic.in/" target="_new"><i class="fa fa-angle-double-right"></i> Dy. Chief Minister's Office</a> </li>
                    <li><a href="http://ag.bih.nic.in/" target="_new"><i class="fa fa-angle-double-right"></i> Accountant General, Bihar</a> </li>
                    <li><a href="http://gov.bih.nic.in/" target="_new"><i class="fa fa-angle-double-right"></i> Chief Electoral Officer, Bihar</a> </li>
				</ul>
			</div>
		</div>	
	</div>
</div>
</div>
</div>
<script src="js/eip/citizen/guest-home.js"></script>
