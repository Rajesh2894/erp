<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="date" class="java.util.Date" />
<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
 <c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">  
 <div id="footer">
    
        <div class="container-fluid departments">
		<h1 class="text-center"><spring:message code="department.name" text="Departments"/></h1>
 		<div class="container-fluid ">
		<div class="col-sm-4">
            <h2><spring:message code="department.administration" text="Administration"/></h2>
            <div class="col-sm-6">
			
               <ul class="nav nav-list narrow">
              <li> <a href="ULBHome.html?resetULB&orgId=2"  title="<spring:message code="department.parliament.affairs" text="Parliament Affairs"/>"><spring:message code="department.parliament.affairs" text="Parliament Affairs"/></a> </li>
              <li> <a href="ULBHome.html?resetULB&orgId=3"  title="<spring:message code="department.cabinet" text="Cabinet"/>"><spring:message code="department.cabinet" text="Cabinet"/></a> </li>
			  <li> <a href="ULBHome.html?resetULB&orgId=4"  title="<spring:message code="department.GAD-full" text="GAD"/>"><spring:message code="department.GAD" text="GAD"/></a> </li>
              <li> <a href="ULBHome.html?resetULB&orgId=5"  title="<spring:message code="department.home" text="Home"/>"><spring:message code="department.home" text="Home"/></a> </li>
              <li> <a href="ULBHome.html?resetULB&orgId=6"  title="<spring:message code="department.law" text="Law"/>"><spring:message code="department.law" text="Law"/></a> </li>
             
             </ul>
			 </div>
			  <div class="col-sm-6">
			 <ul class="nav nav-list narrow">
              <li> <a href="ULBHome.html?resetULB&orgId=7"  title="<spring:message code="department.disaster" text="Disaster"/>"><spring:message code="department.disaster" text="Disaster"/></a> </li>
 			  <li> <a href="ULBHome.html?resetULB&orgId=8"  title="<spring:message code="department.planning" text="Planning"/>"><spring:message code="department.planning" text="Planning"/></a> </li>
 		      <li> <a href="ULBHome.html?resetULB&orgId=9"  title="<spring:message code="department.vigilance" text="Vigilance"/>"><spring:message code="department.vigilance" text="Vigilance"/> </a> </li>
              <li> <a href="ULBHome.html?resetULB&orgId=10"  title="<spring:message code="department.IPRD-full" text="IPRD"/>"><spring:message code="department.IPRD" text="IPRD"/></a> </li>
             </ul>
           </div>
 		   </div>
		   
		   <div class="col-sm-4">
		   			<h2><spring:message code="department.finance" text="Finance"/></h2>
            <div class="col-sm-6">
               <ul class="nav nav-list narrow">
             <li><a href="ULBHome.html?resetULB&orgId=11"  title="<spring:message code="department.revenue.land.reform" text="Revenue & Land Reform"/>"><spring:message code="department.revenue.land.reform" text="Revenue & Land Reform"/></a></li>
                    <li><a href="ULBHome.html?resetULB&orgId=12"  title="<spring:message code="department.finance.dept" text="Finance"/>"><spring:message code="department.finance.dept" text="Finance"/></a></li>
                    <li><a href="ULBHome.html?resetULB&orgId=13"  title="<spring:message code="department.commercial.taxes" text="Commercial Taxes"/>"><spring:message code="department.commercial.taxes" text="Commercial Taxes"/></a></li>
                   <li><a href="ULBHome.html?resetULB&orgId=49"  title="<spring:message code="department.registration.excise.prohibition" text="Registration Excise & Prohibition"/>"><spring:message code="department.registration.excise.prohibition" text="Registration Excise & Prohibition"/></a></li>
             
             </ul>
			 </div>
			  <div class="col-sm-6">
 			 <ul class="nav nav-list narrow">
              <li><a href="ULBHome.html?resetULB&orgId=15"  title="<spring:message code="department.excise.prohibition" text="Excise & Prohibition Department"/>"><spring:message code="department.excise.prohibition" text="Excise & Prohibition Department"/></a></li>
              <li><a href="ULBHome.html?resetULB&orgId=16"  title="<spring:message code="department.transport" text="Transport"/>"><spring:message code="department.transport" text="Transport"/></a></li>
              <li><a href="ULBHome.html?resetULB&orgId=17"  title="<spring:message code="department.mines.geology" text="Mines & Geology"/>"><spring:message code="department.mines.geology" text="Mines & Geology"/></a></li>
             </ul>
           </div>
 		   </div>
		   
		   
		   <div class="col-sm-4">
 				 <h2><spring:message code="department.human.resource" text="Human Resource" /></h2>
                  <ul class="nav nav-list narrow">
                   <li><a href="ULBHome.html?resetULB&orgId=18"  title="<spring:message code="department.education" text="Education"/>"><spring:message code="department.education" text="Education"/></a></li>
                   <li><a href="ULBHome.html?resetULB&orgId=19"  title="<spring:message code="department.health" text="Health"/>"><spring:message code="department.health" text="Health"/></a></li>
                  </ul>
                </div>
                  
                
 		   
		   
				 </div>
 				
				<div class="container-fluid">
				
				
				
				<div class="col-sm-4">
							<h2><spring:message code="department.infrastructure" text="Infrastructure"/></h2>

            <div class="col-sm-6">
               <ul class="nav nav-list narrow">
             <li><a href="ULBHome.html?resetULB&orgId=20"  title="<spring:message code="department.building.construction" text="Building Construction"/>"><spring:message code="department.building.construction" text="Building Construction"/></a></li>
					<li><a href="ULBHome.html?resetULB&orgId=21"  title="<spring:message code="department.road.construction" text="Road Construction"/>"><spring:message code="department.road.construction" text="Road Construction"/></a></li>
                    <li><a href="ULBHome.html?resetULB&orgId=22"  title="<spring:message code="department.energy" text="Energy"/>"><spring:message code="department.energy" text="Energy"/></a></li>
                    <li><a href="ULBHome.html?resetULB&orgId=23"  title="<spring:message code="department.rural.works" text="Rural Works"/>"><spring:message code="department.rural.works" text="Rural Works"/></a></li>
                    <li><a href="ULBHome.html?resetULB&orgId=24"  title="<spring:message code="department.PHED-full" text="PHED"/>"><spring:message code="department.PHED" text="PHED"/></a></li>
             
             </ul>
			 </div>
			  <div class="col-sm-6">
 			 <ul class="nav nav-list narrow">
              <li><a href="ULBHome.html?resetULB&orgId=25"  title="<spring:message code="department.UDHD-full" text="UDHD"/>"><spring:message code="department.UDHD" text="UDHD"/></a></li>
                    <li><a href="ULBHome.html?resetULB&orgId=26"  title="<spring:message code="department.industry" text="Industry"/>"><spring:message code="department.industry" text="Industry"/></a></li>
				    <li><a href="ULBHome.html?resetULB&orgId=27"  title="<spring:message code="department.information.technology" text="Information Technology"/>"><spring:message code="department.information.technology" text="Information Technology"/></a></li>
					<li><a href="ULBHome.html?resetULB&orgId=28"  title="<spring:message code="department.science.technology" text="Science & Technology"/>"><spring:message code="department.science.technology" text="Science & Technology"/></a></li>
             </ul>
           </div>
 		   </div>
		   <div class="col-sm-4">
		   <h2><spring:message code="department.agriculture.allied" text="Agriculture & Allied"/></h2>
            <div class="col-sm-6">
			
               <ul class="nav nav-list narrow">
             <li><a href="ULBHome.html?resetULB&orgId=29"  title="<spring:message code="department.agriculture" text="Agriculture"/>"><spring:message code="department.agriculture" text="Agriculture"/></a></li>
					<li><a href="ULBHome.html?resetULB&orgId=30"  title="<spring:message code="department.animal.fish.resource" text="Animal & Fish Resource"/>"><spring:message code="department.animal.fish.resource" text="Animal & Fish Resource"/></a></li>
                    <li><a href="ULBHome.html?resetULB&orgId=31"  title="<spring:message code="department.water.resource" text="Water Resource"/>"><spring:message code="department.water.resource" text="Water Resource"/></a></li>
                    <li><a href="ULBHome.html?resetULB&orgId=32"  title="<spring:message code="department.rural.development" text="Rural Development"/>"><spring:message code="department.rural.development" text="Rural Development"/></a></li>
                    <li><a href="ULBHome.html?resetULB&orgId=33"  title="<spring:message code="department.panchayati.raj" text="Panchayati Raj"/>"><spring:message code="department.panchayati.raj" text="Panchayati Raj"/></a></li>
					
					
             
             </ul>
			 </div>
			  <div class="col-sm-6">
 			 <ul class="nav nav-list narrow">
			 <li><a href="ULBHome.html?resetULB&orgId=34"  title="<spring:message code="department.food.consumer.protection" text="Food Consumer Protection"/>"><spring:message code="department.food.consumer.protection" text="Food Consumer Protection"/></a></li>
              <li><a href="ULBHome.html?resetULB&orgId=35"  title="<spring:message code="department.cooperative" text="Co-Operative"/>"><spring:message code="department.cooperative" text="Co-Operative"/></a></li>
                    <li><a href="ULBHome.html?resetULB&orgId=36"  title="<spring:message code="department.environmente.forest" text="Environment & Forest"/>"><spring:message code="department.environmente.forest" text="Environment & Forest"/></a></li>
				    <li><a href="ULBHome.html?resetULB&orgId=37"  title="<spring:message code="department.minor.water.resource" text="Minor Water Resource"/>"><spring:message code="department.minor.water.resource" text="Minor Water Resource"/></a></li>
					<li><a href="ULBHome.html?resetULB&orgId=38"  title="<spring:message code="department.sugarcane" text="Sugarcane"/>"><spring:message code="department.sugarcane" text="Sugarcane"/></a></li>
             </ul>
           </div>
 		   </div>
		   
		   <div class="col-sm-4">
 		  <div class="col-sm-6">
		   <h2><spring:message code="department.social.welfare" text="Social Welfare"/></h2>
                  <ul class="nav nav-list narrow">
                  <li><a href="ULBHome.html?resetULB&orgId=39"  title="<spring:message code="department.social.welfare" text="Social Welfare"/>"><spring:message code="department.social.welfare" text="Social Welfare"/></a></li>
                  <li><a href="ULBHome.html?resetULB&orgId=40"  title="<spring:message code="department.labour.resources" text="Labour Resources"/>"><spring:message code="department.labour.resources" text="Labour Resources"/></a></li>
                  <li><a href="ULBHome.html?resetULB&orgId=41"  title="<spring:message code="department.BC.EBC" text="BC & EBC"/>"><spring:message code="department.BC.EBC" text="BC & EBC"/></a></li>
				   <li><a href="ULBHome.html?resetULB&orgId=42"  title="<spring:message code="department.minority.welfare" text="Minority Welfare"/>"><spring:message code="department.minority.welfare" text="Minority Welfare"/></a></li>
                  <li><a href="ULBHome.html?resetULB&orgId=43"  title="<spring:message code="department.SC.ST.Welfare" text="SC & ST Welfare"/>"><spring:message code="department.SC.ST.Welfare" text="SC & ST Welfare"/></a></li>
                  
               </ul>
			   </div>
			   		   <div class="col-sm-6">
 		   				<h2><spring:message code="department.art.culture.tourism" text="Art Culture & Tourism"/></h2>
                  <ul class="nav nav-list narrow">
                  <li><a href="ULBHome.html?resetULB&orgId=44"  title="<spring:message code="department.tourism" text="Tourism"/>"><spring:message code="department.tourism" text="Tourism"/></a></li>
                  <li><a href="ULBHome.html?resetULB&orgId=45"  title="<spring:message code="department.youth.art.culture" text="Youth & Art Culture"/>"><spring:message code="department.youth.art.culture" text="Youth & Art Culture"/></a></li>
               </ul>
			   </div>
           </div>
		   <div class="col-sm-4">
		    <ul class="nav nav-list narrow pull-right">
            <li><a href="Content.html?links&page=more_sites"  title="<spring:message code="department.more.sites" text="More Sites..."/>"><spring:message code="department.more.sites" text="More Sites..."/></a></li>
            </ul>
		   </div>
              </div>
           </div>
          
           </div>
 </c:if>


<c:if test="${empty userSession.getEmployee().getEmploginname() || userSession.getEmployee().getEmploginname() eq 'NOUSER'}">
<div class="footer-logos">
<div class="container clear">
		<div class="row">
		<div class="col-md-12 col-lg-12">
		<div id="carosel">
		<div class="owl-carousel owl-theme">
		 <div class="item"><a href="https://cm.bihar.gov.in/users/home.aspx" target="_blank" title="CM Bihar"><img src="assets/img/cmbihar.jpg" class="img-responsive" alt="CM Bihar" title="CM Bihar"></a></div>
		  <div class="item"><a href="https://www.india.gov.in/" target="_blank"><img src="assets/img/india-gov-iprd.png" class="img-responsive" alt="India.gov.in" title="India.gov.in" ></a></div>
		<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">
		<div class="item"><a href="http://csd.bih.nic.in/Scripts/Asset/Asset.asp" target="_blank" title="Asset Declaration"><img src="assets/img/asse-dec.png" class="img-responsive" alt="Asset Declaration" title="Asset Declaration"></a></div>
		</c:if>
		<div class="item"><a href="http://cmrf.bih.nic.in/users/home.aspx" target="_blank" title="Chief Minister Relief Fund"><img src="assets/img/cmrf.png" class="img-responsive" alt="Chief Minister Relief Fund" title="Chief Minister Relief Fund"></a></div>
		<div class="item"><a href="http://www.dashboard.bihar.gov.in" target="_blank" title="Compliance Dashboard"><img src="assets/img/e-dash.png" class="img-responsive" alt="Compliance Dashboard"></a></div>
		<div class="item"><a href="http://www.biharonline.gov.in/rti/Index.aspx?AspxAutoDetectCookieSupport=1" target="_blank" title="Jankari"><img src="assets/img/jankari.png" class="img-responsive" alt="Jankari" title="Jankari"></a></div>
		<div class="item"><a href="http://loksamvad.bihar.gov.in/" target="_blank" title="Lok Samvad"><img src="assets/img/loksamvad.png" class="img-responsive" alt="Lok Samvad" title="Lok Samvad"></a></div>
		<div class="item"><a href="cache/1/Asset/Jigyasa-Helpline.pdf" target="_blank" title="Jigyasa-Helpline Number"><img src="assets/img/helpline.png" class="img-responsive" alt="Jigyasa-Helpline Number" title="Jigyasa-Helpline Number"></a></div>
		 <div class="item"><a href="https://cm.bihar.gov.in/users/home.aspx" target="_blank" title="CM Bihar"><img src="assets/img/cmbihar.jpg" class="img-responsive" alt="CM Bihar" title="CM Bihar"></a></div>
		<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">
		<div class="item"><a href="http://csd.bih.nic.in/Scripts/Asset/Asset.asp" target="_blank" title="Asset Declaration"><img src="assets/img/asse-dec.png" class="img-responsive" alt="Asset Declaration" title="Asset Declaration"></a></div>
		</c:if>
		 <div class="item"><a href="https://www.india.gov.in/" target="_blank"><img src="assets/img/india-gov-iprd.png" class="img-responsive" alt="India.gov.in" title="India.gov.in" ></a></div>
		<div class="item"><a href="http://cmrf.bih.nic.in/users/home.aspx" target="_blank" title="Chief Minister Relief Fund"><img src="assets/img/cmrf.png" class="img-responsive" alt="Chief Minister Relief Fund" title="Chief Minister Relief Fund"></a></div>
		<div class="item"><a href="http://www.dashboard.bihar.gov.in" target="_blank" title="Compliance Dashboard"><img src="assets/img/e-dash.png" class="img-responsive" alt="Compliance Dashboard"></a></div>
		<div class="item"><a href="http://www.biharonline.gov.in/rti/Index.aspx?AspxAutoDetectCookieSupport=1" target="_blank" title="Jankari"><img src="assets/img/jankari.png" class="img-responsive" alt="Jankari" title="Jankari"></a></div>
		<div class="item"><a href="http://loksamvad.bihar.gov.in/" target="_blank" title="Lok Samvad"><img src="assets/img/loksamvad.png" class="img-responsive" alt="Lok Samvad" title="Lok Samvad"></a></div>
 		<div class="item"><a href="cache/1/Asset/Jigyasa-Helpline.pdf" target="_blank" title="Jigyasa-Helpline Number"><img src="assets/img/helpline.png" class="img-responsive" alt="Jigyasa-Helpline Number" title="Jigyasa-Helpline Number"></a></div>
 		
 		</div>
		</div>
		</div>
		</div>
</div>
</div>

<!-- VISITOR COUNT-->

<c:if test="${ not empty totalRegisUser}">
	<div class="white container-fluid padding-10 visitors-counter">
		<div class="container">
			<div class="col-sm-3 col-md-6 col-lg-3 col-xs-12"
				style="border-right: 1px #fff dotted;">
				<ul>
					<li><i class="fa fa-pie-chart text-primary"></i> <spring:message
							code="TotalVisitors" text="Total Visitors" /> <span
						class="animate-number badge pull-right"
						data-value="<%=request.getSession().getAttribute("countuser")%>"
						id="countuser2" data-duration="3000">0</span></li>
				</ul>
			</div>

			<div class="col-sm-3 col-md-6 col-lg-3 col-xs-12"
				style="border-right: 1px #fff dotted;">
				<ul>
					<li><c:set var="orgId"
							value="${userSession.organisation.orgid}" /> <img
						src="<spring:message code="hitcounter.URL" arguments="${orgId}"/>"
						title="<spring:message code="left.TotalVisitors"/>"
						Alt="<spring:message code="left.TotalVisitors"/>" height="20"
						width="129" border="0" class="hide" /> <i
						class="fa fa-calendar-check-o text-danger"></i><spring:message code="TodayVisitors" text="Today Visitors" /> <span
						class="animate-number badge pull-right"
						data-value="<%=request.getSession().getAttribute("loggedInUser")%>"
						data-duration="3000" id="loggedInUser">0</span></li>
				</ul>
			</div>


			<div class="col-sm-3 col-md-6 col-lg-3 col-xs-12"
				style="border-right: 1px #fff dotted;">
				<ul>
					<li><i class="fa fa-user text-success"></i> <spring:message
							code="ActiveUsers" text="Active Users" /> <span
						class="animate-number badge pull-right"
						data-value="<%=request.getSession().getAttribute("activeuser")%>"
						data-duration="3000" id="activeuser">0</span></li>
				</ul>
			</div>


			<div class="col-sm-3 col-md-6 col-lg-3 col-xs-12">
				<ul>
					<li class="facebook-color"><i class="fa fa-users text-info"></i>
						<spring:message code="RegisterUsers" text="Register Users" /> <span
						class="animate-number badge pull-right"
						data-value="<%=request.getSession().getAttribute("totalRegisUser")%>"
						data-duration="3000" id="totalRegisUser">0</span></li>
				</ul>
			</div>
		</div>
	</div>
</c:if>

</c:if>
 <footer class="clearfix">
 <div class="container-fluid">
	<!--Scroll To Top--> 
    <a class="tothetop" href="javascript:void(0);"><strong class="fa fa-angle-up"></strong><span><spring:message code="Top" text="Top"/></span></a> 
	<div class="col-sm-4">
	
	 <div class="pull-left ">
	    <ul>
	      <li><img src="assets/img/css.jpg" class="holder_img" alt="Loading please wait" title="W3C CSS standards"></li>
	      <li><img src="assets/img/html.jpg" class="holder_img" alt="Loading please wait" title="W3C XHTML standards"></li>
	      <li><img src="assets/img/uc.jpg" class="holder_img" alt="Loading please wait" title="GIGW standards"></li>
	      <li><img src="assets/img/w3c.png" class="holder_img" alt="Loading please wait" title="W3C WAI-AA WCAG standards"></li>
	    </ul>
	  </div>
	</div>
	
	<div class="footer-links col-sm-8 text-center">

	<ul>
	 <li> <i class="fa fa-info-circle" aria-hidden="true"></i><a href="#" title="Content managed by IPRD" target="_blank"><spring:message code="content.managed" text="Content managed by IPRD"/></a> </li>
 	<li><i class="fa fa-info-circle" aria-hidden="true"></i><a href="http://abmindia.com/" title="Website designed, developed by ABM" target="_blank"><spring:message code="website.design" text="Website designed, developed by ABM"/></a> </li>
	</ul>

	</div>
	
	
	

  </div>
</footer>
<div class="footer-link1">
<div class="container-fluid">

	<div class="footer-link1 col-sm-12 text-center">
	<ul>
 		<li> <a href="ScreenReader.html" title="Screen-Reader" ><spring:message code="ScreenReader" text="Screen-Reader"/></a>    </li>
 		    
 			<li> <a href="Websitepolicy.html" title="Website Policies" ><spring:message code="website.policies" text="Website Policies"/></a></li> 
 			<li> <a href="help.html" title="Help" ><spring:message code="help.footer" text="Help"/></a> 
 			<li><a href="webInfo.html" title="Web Information Manager" ><spring:message code="web.information" text="Web Information Manager"/></a> </li>
 
			<li> <a href="http://abmindia.com/" title="Copyright &copy; ABM ${currentYear}" target="_blank" id="cpy"><spring:message code="Copyright" text="Copyright &copy; ABM ${currentYear}"/></a> </li>
 	        <c:set var="lastUpdated" value="${userSession.lastUpdated}"></c:set>
 	        <fmt:formatDate type = "date" value="${lastUpdated}"  dateStyle="SHORT" pattern="dd/MM/yyyy" var="formatedDt"/>
 	      <li> <a href="javascript:void(0)" title="Last Updated on ${formatedDt}" ><spring:message code="last.updated.on" text="Last Updated on ${formatedDt}"/>&nbsp; ${formatedDt}</a> </li>
	  </ul>
	  </div>


</div>
</div>

