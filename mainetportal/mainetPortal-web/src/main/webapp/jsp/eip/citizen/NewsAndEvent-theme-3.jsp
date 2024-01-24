<%@page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<link href="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
 
<script>
	$(document).ready(function() {
		$('.popular-box #news-carousel').owlCarousel({
			loop : true,
			margin : 10,
			animateOut : 'slideOutDown',
			animateIn : 'flipInX',
			 autoPlay:true,
			    autoPlayTimeout:4000,
			    autoplayHoverPause:true,

			items : 1,
			nav : true,
			responsive : {
				0 : {
					items : 1
				},
				600 : {
					items : 1
				},
				1000 : {
					items : 1
				}
			}
		})
	});
	
	$(document).ready(function(){
	   $(".press-release").click(function(){
		    var imageId = $(this).data('id');
		    var imageList=imageId.split(",");
		    $('div#press-modal-body-div > div').remove();	
		    $('div#press-modal-body-div > img').remove();
			for(i=0;i<imageList.length;i++){
				if(i==0){
					$("<div>", {
					    'class': "item active press-release-item"
					}).appendTo('.press-modal-body');					
				}
				else{
					$("<div>", {
					    'class': "item press-release-item"
					}).appendTo('.press-modal-body');
				}
	
				var img = $('<img alt="Image" class="img-responsive" />').attr({
		            'id': 'myImage'+i,
		            'src': './cache/'+imageList[i]
		        }).appendTo('.press-release-item');
		    }
		    
	     $('#pressreleasediv').modal('show');
	   });
	});
	
</script>

<ol class="breadcrumb">
      <li  class="breadcrumb-item"><a href="CitizenHome.html"><strong class="fa fa-home margin-right-5"></strong><spring:message code="home" text="Home"/></a></li>
      <li class="breadcrumb-item active"><spring:message code="theme3.portal.press.release" text="Press Release"/></li>
</ol>
<!-- <div class="widget-content padding"> -->
<div class="container-fluid">
	<div class="col-sm-12" id="nischay">
		<div class="widget">
			<div class="widget-header">
				<h2><spring:message code="theme3.portal.press.release" text="Press Release"/></h2>
            </div>
			<div class="widget-content padding">
				
				<div class="press-release-content">
					<table class="table table-bordered table-striped dataTableNormal">
						<thead>
							<tr>
								<th><spring:message code="theme3.portal.details.info" text="Details / Information"/></th>
								<th class="pr-date"><spring:message code="theme3.portal.date" text="Date"/></th>							
								<th><spring:message code="theme3.portal.images" text="Images"/></th>
								<th><spring:message code="theme3.portal.download" text="Download"/></th>
							</tr>
						</thead>
						<tbody>
								<c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="counter">
									<tr>
										<td >
 										<c:choose>
										<c:when
											test="${userSession.getCurrent().getLanguageId() eq 1}">										
											${lookUp.announceDescEng}
										</c:when>
										<c:otherwise>
											${lookUp.announceDescReg}
										</c:otherwise>
										</c:choose>
										</td>
										<td class="pr-date text-center">
											<span class="date">
												<fmt:formatDate type="date" value="${lookUp.newsDate}" dateStyle="SHORT" pattern="dd-MM-yyyy"/>
											</span>
										</td>
										<td  class="text-center">
										<c:if test="${not empty lookUp.attachImage}">
											<a class="press-release" data-toggle="modal" data-id="${lookUp.attachImage}"  ><i class="fa fa-picture-o " ></i></a>
										</c:if>	
										</td>
										<td class="text-center">
										<c:if test="${not empty lookUp.attach}">
										<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
										<c:forEach items="${links}" var="download" varStatus="count">
											<a href="./cache/${download}" target="_blank" class="">&nbsp;<i class="fa fa-download" ></i>&nbsp;</a>
										</c:forEach>	
										</c:if>
										</td>				
									</tr>
								</c:forEach>
						</tbody>
					</table>
					
					<!-- Modal for Press Release Events starts -->
					<div id="pressreleasediv" class="modal fade press-modal press-release-modal-class" role="dialog">
						<div class="modal-dialog modal-lg">
							<!-- Modal content-->
							<div class="modal-content">
								<button type="button" class="close" data-dismiss="modal">
									<i class="fa fa-times" aria-hidden="true"></i>
								</button>
								<div class="modal-body ">
									<div id="myCarousel-1" class="carousel slide carousel-fade" data-ride="carousel">
										<!-- Wrapper for slides -->
										<div class="carousel-inner press-modal-body" id="press-modal-body-div">
	
										</div>
				
										<!-- Left and right controls -->
										<a class="left carousel-control hidden-xs" href="#myCarousel-1"	data-slide="prev">
											<i class="fa fa-angle-left" aria-hidden="true"></i>
											<span class="sr-only">Previous</span>
										</a>
										<a class="right carousel-control hidden-xs" href="#myCarousel-1" data-slide="next">
											<i class="fa fa-angle-right" aria-hidden="true"></i>
											<span class="sr-only">Next</span>
										</a>
									</div>
									
								</div>
							</div>
	
						</div>
					</div>
					<!-- Modal for Press Release Events ends -->
					
				</div>
				<div class="clear"></div>

			</div>
		</div>
	</div>
</div>
<!-- </div> -->