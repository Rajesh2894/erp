<%	response.setContentType("text/html; charset=utf-8"); %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
.nav>li>a {
    position: relative;
    display: block;
    padding: 10px 27px;
}
</style>

<ol class="breadcrumb">
<li class="breadcrumb-item"><a href="CitizenHome.html" title="Home"><i class="fa fa-home" aria-hidden="true"></i> Home</a></li>
<li class="breadcrumb-item"><a href="DashBoard.html" title="Dashboard">Dashboard</a></li>
<li class="breadcrumb-item active">Aarthik Hal, Yuvaon ko Bal</li>

</ol>

<div class="container-fluid dashboard-page">
    <div class="container-fluid">
     <div class="col-sm-12" id="nischay">

  
 
<div class="dashboard-items">
<ul class="nav nav-pills" role="tablist">
  <li role="presentation" class="active"><a href="DashBoard.html" class="red-nischay" title="7  Nischay">7  Nischay </a></li>
  <li role="presentation"><a href="Administration.html" title="Administration"> Administration and Finance </a></li>
  <li role="presentation"><a href="HumanResource.html" title="Human Resource Department">HRD</a></li>
  <li role="presentation"><a href="Infrastructure.html" title="Infrastructure"> Infrastructure</a></li>
  <li role="presentation"><a href="Agriculture.html" title="Agriculture &amp; Allied"> Agriculture &amp; Allied </a></li>
  <li role="presentation"><a href="Welfare.html" title="Welfare">Welfare</a></li>
  <li role="presentation"><a href="ArtCulture.html" title="Art Culture and Tourism"> Art Culture and Tourism</a></li>
 </ul>
</div>
                   
                  
             
               
							  
							  
							  <div id="schemes">
							  
							  <h3 class="text-center">Aarthik Hal, Yuvaon ko Bal</h3>
							  
							  <div class="container">
                   
 				  <div class="row">
 				  
				  <div class="col-sm-3">
				   <div class="item">
                      <a href="DashBoard.html?report&reportname=R_ST_CREDIT_DASH.rptdesign"><div class="box student">
 					 <div align="center"> <i class="fa fa-credit-card" aria-hidden="true"></i></div>
					   <div class="header"><h1><spring:message code="dashboard.scheme1.student" text="Student Credit Card" /></h1></div>
					   <div class="progressbar">
					   
					   
  					   <div class="row">
					   <div class="col-sm-6">
					   <span>73 %</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">30</span>
					   <h1>Financial</h1></div>
					   </div>
					   </div>
					   
						</div></a>
                      </div>
				  </div>
				  
				   <div class="col-sm-3">
				   <div class="item">
                      <a href="drcc.html"><div class="box student">
 					 <div align="center"> <i class="fa fa-university" aria-hidden="true"></i></div>
					   <div class="header"><h1 style="line-height:17px;"><spring:message code="dashboard.scheme1.jilla" text="Jillan Nibandhan abam Paramarsh Kendra" /></h1></div>
					   <div class="progressbar">
					   
					   
  					   <div class="row">
					   <div class="col-sm-6">
					   <span data-count="10%">10</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">30</span>
					   <h1>Financial</h1></div>
					   </div>
					   </div>
					   
						</div></a>
                      </div>
				  </div>
					
					 <div class="col-sm-3">
				   <div class="item">
                      <a href="DashBoard.html?report&reportname=R_SH_ALLOWANCE_DASH.rptdesign"><div class="box student">
 					 <div align="center"> <i class="fa fa-money" aria-hidden="true"></i></div>
					   <div class="header"><h1><spring:message code="dashboard.scheme1.sahayata" text="Swayang Shahayata Batta"/></h1></div>
					   <div class="progressbar">
					   
					   
  					   <div class="row">
					   <div class="col-sm-6">
					   <span data-count="10%">10</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">30</span>
					   <h1>Financial</h1></div>
					   </div>
					   </div>
					   
						</div></a>
                      </div>
				  </div>
					  
					  
 				<div class="col-sm-3">
				   <div class="item">
                      <a href="DashBoard.html?report&reportname=R_YOUTH_DASH.rptdesign"><div class="box student">
 					 <div align="center"> <i class="fa fa-male" aria-hidden="true"></i></div>
					   <div class="header"><h1><spring:message code="dashboard.scheme1.kushal" text="Kushal Yuva Karyakram" /></h1></div>
					   <div class="progressbar">
					   
					   
  					   <div class="row">
					   <div class="col-sm-6">
					   <span data-count="10%">10</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">30</span>
					   <h1>Financial</h1></div>
					   </div>
					   </div>
					   
						</div></a>
                      </div>
				  </div>
					 
					
				<div class="col-sm-3">
				   <div class="item">
                      <a href="wifi.html"><div class="box student">
 					 <div align="center">  <i class="fa fa-wifi" aria-hidden="true"></i></div>
					   <div class="header"><h1><spring:message code="dashboard.scheme1.wifi" text="Wi-Fi" /></h1></div>
					   <div class="progressbar">
					   
					   
  					   <div class="row">
					   <div class="col-sm-6">
					   <span data-count="10%">10</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">30</span>
					   <h1>Financial</h1></div>
					   </div>
					   </div>
					   
						</div></a>
                      </div>
				  </div>
					
				<div class="col-sm-3">
				   <div class="item">
                      <a href="startup.html"><div class="box student">
 					 <div align="center"> <i class="fa fa-thumbs-up" aria-hidden="true"></i></div>
					   <div class="header"><h1><spring:message code="dashboard.scheme1.startup" text="Start Up" /></h1></div>
					   <div class="progressbar">
					   
					   
  					   <div class="row">
					   <div class="col-sm-6">
					   <span data-count="10%">10</span>
					   <h1>Physical</h1></div>
					   <div class="col-sm-6">
					   <span data-count="30%">30</span>
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