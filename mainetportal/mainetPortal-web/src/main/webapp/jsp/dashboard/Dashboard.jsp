<%	response.setContentType("text/html; charset=utf-8"); %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<ol class="breadcrumb">
<li class="breadcrumb-item"><a href="CitizenHome.html" title="Home"><i class="fa fa-home" aria-hidden="true"></i> Home</a></li>
<li class="breadcrumb-item active">Dashboard</li>
</ol>
  

 <div class="container-fluid dashboard-page">
    <div class="container-fluid">
     <div class="col-sm-12" id="nischay">

  
 

<div class="dashboard-items">
<ul class="nav nav-pills" role="tablist">
  <li role="presentation" class="active"><a href="DashBoard.html" class="red-nischay" title="7 Nischay">7 Nischay </a></li>
  <li role="presentation"><a href="Administration.html" title="Administration"> Administration and Finance </a></li>
  <li role="presentation"><a href="HumanResource.html" title="Human Resource Department">HRD</a></li>
  <li role="presentation"><a href="Infrastructure.html" title="Infrastructure"> Infrastructure</a></li>
  <li role="presentation"><a href="Agriculture.html" title="Agriculture &amp; Allied"> Agriculture &amp; Allied </a></li>
  <li role="presentation"><a href="Welfare.html" title="Welfare">Welfare</a></li>
  <li role="presentation"><a href="ArtCulture.html" title="Art Culture and Tourism"> Art Culture and Tourism</a></li>
 </ul>
</div>
 				 
                   
                  
             
               
							  
	  
	  <div id="schemes">
	  <div class="container">
                   
 				  <div class="row ">
 				  
				  <div class="col-sm-3">
				   <div class="item">
                      <a href="DashBoard.html?page&scheme=Aarthik"><div class="box darkblue-nischay">
 					 <div align="center"> <img src="assets/img/Aarthik.png" class="img-responsive" alt="Aarthik"></div>
					   <div class="header"><h1><spring:message code="dashboard.scheme1" text="Arthik Hal Youbayon ko Bal"/></h1></div>
					   <div class="progressbar">
					   
					   
  					   <div class="row">
					   <div class="col-sm-6">
					   <span data-count="10%">10 %</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">9 %</span>
					   <h1>Financial</h1></div>
					   </div>
					   </div>
					   
						</div></a>
                      </div>
				  </div>
				  
				  <div class="col-sm-3">
                    <div class="item">
                    
                     <a href="DashBoard.html?report&reportname=R_WOMEN_EMPL_DASH.rptdesign"> <div class="box red-nischay">
					 <div align="center"> <img src="assets/img/Mahilaon.png" class="img-responsive" alt="Mahilaon"></div>
                        <div class="header"><h1 style="line-height:17px;"><spring:message code="dashboard.scheme2" text="Rojgar Mahilayon ka AdhiKar"/></h1></div>
						<div class="progressbar">
					   
					   
  					   <div class="row">
					   <div class="col-sm-6">
					   <span data-count="10%">10 %</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">9 %</span>
					   <h1>Financial</h1></div>
					   </div>
					   </div>
						
						</div></a>
                      </div>
					</div>
					
					<div class="col-sm-3">
                <div class="item">
                     <a href="DashBoard.html?report&reportname=R_HAR_GHAR_BIJLI.rptdesign"> 
					 <div class="box darkred-nischay">
					 <div align="center"> <img src="assets/img/Bijli.png" class="img-responsive" alt="Bijli"></div>
                         <div class="header"><h1><spring:message code="dashboard.scheme3" text="Har Ghar Bijli"/></h1></div>
						 <div class="progressbar">
					   
					   
  					   <div class="row">
					   <div class="col-sm-6">
					   <span data-count="10%">10 %</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">9 %</span>
					   <h1>Financial</h1></div>
					   </div>
					   </div>
						 </div></a>
                      </div>
					  </div>
					  
					  
 					<div class="col-sm-3">
					<div class="item">
                      <a href="javascript:void(0);"><div class="box lightblue-nischay">
					 <div align="center"> <img src="assets/img/Nal.png" class="img-responsive" alt="Nal"></div>
                        <div class="header"><h1><spring:message code="dashboard.scheme4" text="Har Ghar Nal Ka Jal"/></h1></div>
						<div class="progressbar">
					   
					   
  					   <div class="row">
					   <div class="col-sm-6">
					   <span data-count="10%">10 %</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">9 %</span>
					   <h1>Financial</h1></div>
					   </div>
					   </div>
						</div></a>
                      </div>
 					</div>
					</div>
					 <div class="row ">
					
					<div class="col-sm-3">
					<div class="item">
                      <a href="javascript:void(0);"><div class="box darkgreen-nischay">
					 <div align="center"> <img src="assets/img/Gali.png" class="img-responsive" alt="Gali"></div>
                        <div class="header"><h1><spring:message code="dashboard.scheme5" text="Ghar Tak Pakki Nali Gali"/></h1></div>
						<div class="progressbar">
					   
					   
  					   <div class="row">
					   <div class="col-sm-6">
					   <span data-count="10%">10 %</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">9 %</span>
					   <h1>Financial</h1></div>
					   </div>
					   </div>
						</div></a>
                      </div>
					  </div>
					<div class="col-sm-3">
                   <div class="item">
                      <a href="sauchalay-nirman-ghar-ka-samman.html"><div class="box lightyellow-nischay">
					 <div align="center"> <img src="assets/img/Shauchalay.png" class="img-responsive" alt="Shauchalay"></div>
                        <div class="header"><h1 style="line-height:17px;"><spring:message code="dashboard.scheme6" text="Sochalya Nirman Ghar ka Samman"/></h1></div>
						<div class="progressbar">
					   
					   
  					   <div class="row">
					   <div class="col-sm-6">
					   <span data-count="10%">10 %</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">9 %</span>
					   <h1>Financial</h1></div>
					   </div>
					   </div>
						 
						
						</div></a>
                      </div>
                     </div>
                     
                     <div class="col-sm-3">
					  <div class="item">
                      <a href="DashBoard.html?page&scheme=Awsar"><div class="box brinjal-nischay">
					 <div align="center"> <img src="assets/img/Avsar.png" class="img-responsive" alt="Avsar"></div>
                       <div class="header"><h1><spring:message code="dashboard.scheme7" text="Absar Badhen Age Padhen"/></h1></div>
 					   <div class="progressbar">
  					   <div class="row">
					   <div class="col-sm-6">
					   <span data-count="10%">10 %</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">9 %</span>
					   <h1>Financial</h1></div>
					   </div>
					   </div>
					   </div></a>
                      </div>
 					 </div>
					 </div>
					  </div>
                </div>
				     </div>
 
    
  </div>
  </div>
 