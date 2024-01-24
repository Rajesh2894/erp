<div id="onlineServices" title="D2K Application" style="display: none" class="leanmodal"></div>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="js/mainet/dashboard/highcharts.js"></script>
<script src="js/mainet/dashboard/exporting.js"></script>

<ol class="breadcrumb margin-top-10">
	<li><i class="fa fa-home"></i> <spring:message code="menu.home" /></li>
</ol>
<script>
setTimeout(function(){
	$('.tab-content .active').not(':first').removeClass('active'); 
	},100) 
	document.onscroll = function() {
    if (window.innerHeight + window.scrollY+300 > document.body.clientHeight) {
       $('.mbr-arrow').css('display','none');
    }
    else{
    	$('.mbr-arrow').css('display','block');
    }
}
	$(document).ready(function() {
	    $(".mbr-arrow").click(function(event){
	        $('html, body').animate({scrollTop: '+=630px'}, 800);
	    });	    
	});
</script>

<div class="content">
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<input type="hidden" value='${EIPMenuManager.userType}' id="userType">
	<div class="animated slideInDown">
		
		
		<div class="clearfix"></div>
			<!-- Start Added by ABM2144 -->
			<c:if test="${!empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }" var="user">		
			<!-- <div class="container">			
			<div class="row"> -->
			<div class="col-lg-4 col-lg-offset-8 col-md-5 col-md-offset-7 col-sm-6 col-sm-offset-6 col-xs-11 col-xs-offset-1" id="jumpTask">						
			<select id="selectedTask" data-content="" class="form-control chosen-select-no-results" aria-label="Search Task">
				<option value="0">Search Task</option>
				<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
					<c:forEach items="${menuRoleEntitlement.childList}" var="data">
					<c:if test="${masters.entitle.smfid eq  data.parentId}">
					<c:set var="action0" value="${data.entitle.smfaction}" />					
					<optgroup label="${masters.entitle.smfname}">
			    	<option value="${data.entitle.smfid}">${data.entitle.smfname}</option>
			    	<c:forEach items="${menuRoleEntitlement.childList}"	var="data1">
			    	<c:if test="${data.entitle.smfid eq  data1.parentId}">
			    	<c:set var="action1" value="${data1.entitle.smfaction}" />
			    	<option value="${data1.entitle.smfid}">${data1.entitle.smfname}</option>
			    	<c:forEach items="${menuRoleEntitlement.childList}" var="data2">
					<c:if test="${data1.entitle.smfid eq  data2.parentId}">																	
					<c:set var="action2" value="${data2.entitle.smfaction}" />
					<option value="${data2.entitle.smfid}">${data2.entitle.smfname}</option>
					<c:forEach items="${menuRoleEntitlement.childList}"	var="data3">
					<c:if test="${data2.entitle.smfid eq  data3.parentId}">	
					<c:set var="action3" value="${data3.entitle.smfaction}" />
					<option value="${data3.entitle.smfid}">${data3.entitle.smfname}</option>
					</c:if></c:forEach>
					</c:if></c:forEach>
			    	</c:if></c:forEach>
			    	</optgroup> 
			    	</c:if></c:forEach>
				</c:forEach>
			</select>
			</div>
			<!-- </div></div> -->				
    		</c:if>
    		<div class="clear"></div>
    		<!-- End Added by ABM2144-->
    		
			<h2 id="dashmenu"><span class="hidden"><spring:message code="CitizenServices" text="CitizenServices"></spring:message></span></h2> 
			<div class="col-lg-12 padding-0">
			<div id="sidebar-menu">
				<ul id="nav" class="has_sub">
						<li id="0" style="display:none"><a>Search Task</a></li>
						<c:forEach var="masters" items="${menuRoleEntitlement.parentList}">
						<li class="has_sub" id="${masters.entitle.smfid}">
						<a role="link" title="${masters.entitle.smfname}" id="go${masters.entitle.smfid}" name="go${masters.entitle.smfid}"  href="${masters.entitle.smfaction}" onclick="dodajAktywne(this);breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}',${masters.entitle.smfid});"><span>${masters.entitle.smfname}</span> </a>
							
							<ul class="scroll-menu">
								<c:forEach items="${menuRoleEntitlement.childList}" var="data">
								   <c:if test="${masters.entitle.smfid eq  data.parentId}">
										 <c:set var="action0" value="${data.entitle.smfaction}" />
										
										<li class="navigation" id="${data.entitle.smfid}">
										<a role="link" title="${data.entitle.smfname}"  <c:choose> <c:when test="${fn:containsIgnoreCase(action0 , '.html')}">onclick="openRelatedForm('${action0}','this','${data.entitle.smfdescription}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise> href="${action0}" </c:otherwise></c:choose> > ${data.entitle.smfname} </a> 
											<ul class="scroll-menu1">
												<c:forEach items="${menuRoleEntitlement.childList}"	var="data1">
													
													<c:if test="${data.entitle.smfid eq  data1.parentId}">
														
														
														
												<c:set var="action1" value="${data1.entitle.smfaction}" />
												
												
												
												<li class="folder " id="${data1.entitle.smfid}"><a title="${data1.entitle.smfname}"  <c:choose><c:when test="${fn:containsIgnoreCase(action1 , '.html')}">onclick="openRelatedForm('${action1}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise>href="${action1}"</c:otherwise></c:choose>>${data1.entitle.smfname}</a>
															<ul>
																<c:forEach items="${menuRoleEntitlement.childList}" var="data2">
																	<c:if test="${data1.entitle.smfid eq  data2.parentId}">
																	
																		<c:set var="action2" value="${data2.entitle.smfaction}" />
																		<li class="folder" id="${data2.entitle.smfid}"><a role="link" title="${data2.entitle.smfname}"  <c:choose><c:when test="${fn:containsIgnoreCase(action2 , '.html')}">onclick="openRelatedForm('${action2}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise>href="${action2}"</c:otherwise></c:choose>>${data2.entitle.smfname}</a>
																			<ul>
																				<c:forEach items="${menuRoleEntitlement.childList}"	var="data3">
																					<c:if test="${data2.entitle.smfid eq  data3.parentId}">	
																							<c:set var="action3" value="${data3.entitle.smfaction}" />
																						<li class="folder" id="${data3.entitle.smfid}"><a role="link" title="${data3.entitle.smfname}" <c:choose><c:when test="${fn:containsIgnoreCase(action3 , '.html')}">onclick="openRelatedForm('${action3}');breadcrumb('${masters.entitle.smfname}+${data.entitle.smfname}+${data1.entitle.smfname}+${data2.entitle.smfname}+${data3.entitle.smfname}',${masters.entitle.smfid});" href="#"</c:when><c:otherwise>href="${action3}"</c:otherwise></c:choose>>${data3.entitle.smfname}</a></li>
																					</c:if>
																				</c:forEach>
																			</ul></li>
																	</c:if>
																</c:forEach>
															</ul></li>
													</c:if>
												</c:forEach>
											</ul></li>
									</c:if>
								</c:forEach>
							</ul>
							</li>
					</c:forEach>
			</ul>
			</div>
			
		</div>
		<div class="clearfix"></div>
	</div>
</div>

<script>

	 $(window).load(function() { 
		 var color=[];
		 var faq =$('#gridAdminFAQ >> tr').length-1;
		 var quicklink =$('#gridAdminQuickLinkForm >> tr').length-1;
		 var sublink =$('#gridSectionEntryForm >> tr').length-1;
		 var section =$('#gridSectionDetails >> tr').length-1;
		 var mayor =$('#gridAdminMayorForm >> tr').length-1;
		 var deputyMayor =$('#gridAdminDeputyMayorForm >> tr').length-1;
		 var commissioner =$('#gridAdminCommissionerForm >> tr').length-1;
		 var deputyCommissioner =$('#gridAdminDeputyCommissionerForm >> tr').length-1;
		 var notice =$('#gridAdminPublicNoticesForm >> tr').length-1;
		 var contact =$('#gridAdminContactUs >> tr').length-1;
		 var images =$('#gridEIPHomeImages >> tr').length-1;
		 var logo =$('#gridEIPLogoImages >> tr').length-1;
		 var news =$('#gridEipAnnouncementForm >> tr').length-1;
		 var browserdata=[];
						if (faq > 0) {
							browserdata.push({
								name : $("#faq").val(),
								y : faq,
							});
							color.push('#FE2D00');
						}
						if (quicklink > 0) {
							browserdata.push({
								name : $("#quicklink").val(),
								y : quicklink,
							});
							color.push('#3FAF00');
						}
						if (sublink > 0) {
							browserdata.push({
								name : $("#sublink").val(),
								y : sublink,
							});
							color.push('#0094AF');
						}
						if (section > 0) {
							browserdata.push({
								name : $("#sections").val(),
								y : section,
							});
							color.push('#2D65C2');
						}
						if (mayor > 0) {
							browserdata.push({
								name : $("#committee1").val(),
								y : mayor,
							});
							color.push('#B200FF');
						}
						if (deputyMayor > 0) {
							browserdata.push({
								name : $("#committee2").val(),
								y : deputyMayor,

							});
							color.push('#981046');
						}
						if (commissioner > 0) {
							browserdata.push({
								name : $("#committee3").val(),
								y : commissioner,
							});
							color.push('#0000FF');
						}
						if (deputyCommissioner > 0) {
							browserdata.push({
								name : $("#committee4").val(),
								y : deputyCommissioner,

							});
							color.push('#006400');
						}
						if (notice > 0) {
							browserdata.push({
								name : $("#notice").val(),
								y : notice,
							});
							color.push('#CFC800');
						}
						if (contact > 0) {
							browserdata.push({
								name : $("#contact").val(),
								y : contact,
							});
							color.push('#74FF00');
						}
						if (images > 0) {
							browserdata.push({
								name : $("#slider").val(),
								y : images,
							});
							color.push('#00E0FF');
						}
						if (logo > 0) {
							browserdata.push({
								name : $("#logo").val(),
								y : logo,
							});
							color.push('#C70039');
						}
						if (news > 0) {
							browserdata.push({
								name : $("#news").val(),
								y : news,
							});
							color.push('#4E0081');
						}
						Highcharts.setOptions({
							colors : color
						});

						$('#container').highcharts(
								{
									chart : {
										plotBackgroundColor : null,
										plotBorderWidth : null,
										plotShadow : false,
										type : 'pie'
									},
									title : {
										text : $("#dashHeading").val()
									},
									exporting : {
										enabled : false
									},
									tooltip : {
										useHTML : true,
										formatter : function() {
											return '<b>' + '<span>'
													+ this.point.name
													+ '</span> :</b> '
													+ this.point.y;
										}
									},
									noData : {
										attr : undefined,
										position : {
											"x" : 0,
											"y" : 0,
											"align" : "center",
											"verticalAlign" : "middle"
										},
										style : {
											"fontSize" : "15px",
											"fontWeight" : "bold",
											"color" : "#000000"
										},
										useHTML : false,
									},
									plotOptions : {
										pie : {
											allowPointSelect : true,
											cursor : 'pointer',
											dataLabels : {
												enabled : true,
												color : '#000000',
												connectorColor : '#000000',
												distance : 15,
												useHTML : true,
												formatter : function() {
													return '<b>'+this.point.name+' : '+this.point.y+'</b>';
												}
											},
											showInLegend : true,
											point : {
												events : {
													click : function(event) {
													},
												}
											}
										}
									},
									series : [ {
										type : 'pie',
										name : '#',                                                                              
										data : browserdata
									} ]
								});
					});
</script>

<!-- Down Arrow Start -->
	<div class="mbr-arrow hidden-sm-down">
		<a> <i class="fa fa-angle-down" aria-hidden="true"></i>
			<span><spring:message code="theme3.portal.scroll" text="Scroll"/></span>
		</a>
	</div>
<!-- Down Arrow End -->

<script>
$(window).load(function () {
   $("#selectedTask").chosen();
   $("#selectedTask_chosen").removeAttr("style");
   $("#selectedTask").on("change",function(){
	   var search = $("#selectedTask option:selected").val().trim();
	   $($("li[id="+search+"]").parentsUntil("ul#nav")).each(function() {if($(this).is("ul")){$(this).css("display","block")}});
 	   $('html, body').animate({
 		   scrollTop: $("li[id="+search+"] > a").addClass("flashi").offset().top-50}, 'slow'); 	   
   }) 
  });
</script>