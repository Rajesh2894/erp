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
<script src="assets/js/jquery.marquee.min.js"></script>
<script>

$(function() {
	var curHeight = $('.about1').height();
	var curHeight2 = $('.about2').height();

	  if(curHeight>=115 || curHeight2>=115)
	  {
		  $('.read-more').css('display','block');
	  } else
	         $('.read-more').css('display','none');
	});

</script>
<div class="invisibeHead"></div>
<%-- <div class="sticky-container hidden-xs invisibeHead">
  				 <ul class="sticky-new1"><li><a href="javascript:void(0);" title="Share icon" id="share"><img src="assets/img/newsletter.png" width="32" height="32"></a></li></ul>
                	<ul class="sticky-new">
					 <li><p><a href="javascript:void(0);" onclick="subscribe();"><img src="assets/img/newsletter.png" width="32" height="32"><spring:message code="lable.newsletter" text="Subscribe For Newsletter"/></a></p></li>
                 </ul>
            </div> --%>

<header class="page-heading hidden-md hidden-lg hidden-xs hidden-sm"><div class="container"></div></header>


	
	
<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">  
<div class="container-fluid">
<div class="row">
 <div id="myCarousel" class="carousel slide carousel-fade" data-ride="carousel">
 
 <c:set var="maxfilecount" value="${userSession.getSlidingImgLookUps().size()}" />
 
    <!-- Indicators -->
    <ol class="carousel-indicators">
	    <c:if test="${maxfilecount gt 0 }">
		    <c:forEach begin="0" end="${maxfilecount-1}" varStatus="index" >
		     <c:choose>
		     	<c:when test="${index.index eq 0}">
		     		<li data-target="#myCarousel" data-slide-to="${index.index}" class="active"></li>
		     	</c:when>
		     	<c:otherwise>
		     		    <li data-target="#myCarousel" data-slide-to="${index.index}"></li>
		     	</c:otherwise>
		     </c:choose>
		    </c:forEach>
	    </c:if>
	</ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner"> 
    
    
<c:if test="${userSession.getSlidingImgLookUps().size() gt 0}">
<c:forEach items="${userSession.slidingImgLookUps}" var="slide" varStatus="status">

<c:set var="data" value="${fn:split(slide,'*')}"/>
<c:set var="image" value="${data[0]}"/>
<c:choose>
	<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
		<c:set var="caption" value="${data[1]}"/>
	</c:when>
	<c:otherwise>
		<c:set var="caption" value="${data[2]}"/>
	</c:otherwise>
</c:choose>
<c:if test="${status.index eq 0 }">
	<div class="item active">
		<img src="./${image}" alt="${caption}"/>
		<c:if test="${not empty caption}"><div class="container">
            <div class="carousel-caption animate fadeInLeft">
              <h1>${caption}</h1>
             </div>
          </div></c:if>
	</div>
</c:if>
<c:if test="${status.index ne 0 }">
	<div class="item"><img src="./${image}" alt="${caption}"/>
	<c:if test="${not empty caption}"><div class="container">
            <div class="carousel-caption animate fadeInLeft">
              <h1>${caption}</h1>
             </div>
          </div></c:if>
	</div>
</c:if>
</c:forEach>
</c:if>
</div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span><span class="sr-only">Previous</span></a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span><span class="sr-only">Next</span></a>
  </div>
</div>
</div>	
	
</c:if>	
	
${command.setThemeMap()}



<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}">  


<!--MINISTER START  -->
 ${command.setContactList()}
<c:if test="${command.themeMap['COMMITTEE_MEMBERS'] ne 'I'}">
<div class="about-bg container-fluid clear">

<div class="col-sm-6 col-md-4 col-lg-2 col-xs-12">
<c:if test="${not empty sessionScope.mayorProfile}">
<div class="content-img">
<img src="${sessionScope.mayorprofileImage}" alt="${sessionScope.mayorprofileImage}" title="${sessionScope.mayorName}>" class="img-responsive" >
</div>
<div class="minister-info">
<h3>${sessionScope.mayorName}</h3>
<p class="designation">${sessionScope.mayorProfile}</p> 
<p class="minister-phone"><i class="fa fa-volume-control-phone" aria-hidden="true"></i>${sessionScope.SummaryEng}</p>

</div>

</c:if>
<c:if test="${not empty sessionScope.deputyMayorProfile}">
<div class="content-img">
<img src="${sessionScope.deputyMayorProfileImage}" alt="${sessionScope.deputyMayorProfileImage}" title="${sessionScope.deputyMayorName}>" class="img-responsive" >
</div>
<div class="minister-info">
<h3>${sessionScope.deputyMayorName}</h3>
<p class="designation">${sessionScope.deputyMayorProfile}</p> 
<p class="minister-phone"><i class="fa fa-volume-control-phone" aria-hidden="true"></i> ${sessionScope.deputyMayorSummaryEng}</p>

 <c:forEach items="${command.organisationContactList }" 	var="principalSeccontact">
	<c:if test="${ principalSeccontact.designationEn eq 'P.S. To Minister'}">
	
		<div class="principal-secretary">
			<c:choose>
				<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
					<h3>${principalSeccontact.contactNameEn} </h3>
					<p class="designation"><small> ${ principalSeccontact.designationEn }</small></p>
					<p class="minister-phone"><i class="fa fa-volume-control-phone" aria-hidden="true"></i>${principalSeccontact.telephoneNo1En}</p>
				</c:when>
				<c:otherwise>
					<h3>${principalSeccontact.contactNameReg} </h3>
					<p class="designation"><small> ${ principalSeccontact.designationReg }</small></p>
					<p class="minister-phone"><i class="fa fa-volume-control-phone" aria-hidden="true"></i>${principalSeccontact.telephoneNo1En}</p>
				</c:otherwise>
			</c:choose>
			
			
		</div>
	
	</c:if>

</c:forEach>

</div>

</c:if>




 </div>

<!--MINISTER END  -->


<!--AUBOUT US START  -->
${command.getAboutUs()}
<c:if test="${not empty command.aboutUsDescFirstPara }">
<div class="col-sm-6 col-md-8 col-lg-10 col-xs-12">
<div class="about-content">
<h1><spring:message code="eip.citizen.aboutUs.title"/></h1>
<p class="about about1">${command.aboutUsDescFirstPara}</p>

<h1><spring:message code="eip.citizen.aboutUs.vision" text="Vision"/></h1>
<p class="about about2">${command.aboutUsDescSecondPara}</p>

</div>
<a href="CitizenAboutUs.html" class="read-more pull-right"><spring:message code="ReadMore" text="read more ...."/> </a>

</div>


</c:if>


<!--AUBOUT US END  -->

</div>

</c:if>





<!--NEWS START  -->




 				 <div class="container-fluid">
				 <div class="row">
<div class="col-md-12 col-lg-6 col-sm-12 col-xs-12 key-contact ">
				 <!--Key Contacts  ------------------------------------------------------------------>
				 <h1><spring:message code="KeyContacts" text="Key Contacts"/></h1>
				
				 
				 
				 
					<div id="list-main">
						  <ul id="list">
						 <c:forEach items="${command.organisationContactList }" 	var="contact">
						 <c:if test="${ contact.designationEn ne 'P.S. To Minister'}">
							 <li style="display: block;">
							 
							 	<c:choose>
								<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
							 		<h3>${contact.contactNameEn}  <small> ${contact.designationEn}</small></h3>
								</c:when>
								<c:otherwise>
									<h3>${contact.contactNameReg} <small> ${contact.designationReg}</small></h3>
								</c:otherwise>
								</c:choose>
								<table width="100%">
								<tbody>
								<tr>
									<td width="8%" align="center"><i class="fa fa-phone" aria-hidden="true"><span class="hide">Phone</span></i></td> 
									<td width="20%"><span class="minister-phone"> ${contact.telephoneNo1En}</span></td>
									<td width="8%"  align="center"><i class="fa fa-mobile" aria-hidden="true"><span class="hide">Mobile</span></i></td>
									<td width="20%"><span class="minister-phone">${contact.telephoneNo2En}</span></td>
									<td width="8%" align="center"><i class="fa fa-envelope" aria-hidden="true"><span class="hide">Email</span></i></td>
									<td width="36%"><span class="minister-phone"><a href="mailto:${contact.email1}" title="${contact.email1}">${contact.email1}</a></span></td>
								</tr>
								</tbody>
								</table>
								
			  				 </li>
		  				 </c:if>
		  				 </c:forEach>
		  				</ul>
		  				
		  			</div>
  			 </div>
					 <!--Key Contacts  ------------------------------------------------------------------>
				 	
				 	
				 	 <!--  News and Events  ------------------------------------------------------------------>
				 
<div class="col-md-12 col-lg-6 col-sm-12 col-xs-12 news-department ">
	<div class="container-fluid clear">
		<div class="row">
			<div class="col-md-12 col-lg-12"><h1 class="text-center" data-animation-effect="fadeInDown" data-effect-delay="600"><spring:message code="portal.Latest.News" text="Latest News"/></h1></div>
				 <div class="col-md-12 col-lg-12" style="height:600px;">
				 <ul class="news-holder-departments clearfix announcement scrolllistbox-new ">
				 	<c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status">
					<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
						<c:forEach items="${links}" var="download" varStatus="count">
								<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
								<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
								<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}" />
				 
			                   <li class="wow fadeInLeft animated animated" data-wow-offset="10" data-wow-duration="1.5s" style="visibility: visible;-webkit-animation-duration: 1.5s; -moz-animation-duration: 1.5s; animation-duration: 1.5s;">
			                     <div class="news-description">
								 	<div class="col-sm-5"><img src="${lookUp.attachImage }" class="img-responsive" alt="${lookUp.announceDescEng}"></div> 
					                    <div class="col-sm-7">
					                         <h3>
					                         <c:choose>
												<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
					                         <apptags:filedownload filename="EIP" filePath="${download}" actionUrl="CitizenHome.html?Download" eipFileName="${lookUp.announceDescEng}" ></apptags:filedownload>
					                         </c:when>
					                         <c:otherwise>
					                         	 <apptags:filedownload filename="EIP" filePath="${download}" actionUrl="CitizenHome.html?Download" eipFileName="${lookUp.announceDescReg}" ></apptags:filedownload>
					                         </c:otherwise>
					                         </c:choose>
					                         </h3>
					                        <span class="date"><fmt:formatDate type = "date" value="${lookUp.newsDate}"  dateStyle="SHORT" pattern="dd/MM/yyyy"/></span>
					                    </div>
			 					  </div>
			                 </li>
		               </c:forEach>
               </c:forEach>  
                 
        
                  
            </ul>
			
			</div>
			</div>
			</div>
			
			
		 
		  
        </div>
 	 <!--  News and Events  ------------------------------------------------------------------>
        
       
     
</div>
</div>


<!--NEWS END  -->


<!-- SCHEME AND BANNER LINK START -->

<c:if test="${not empty command.publicNotices}">
 <div class="container-fluid highlights-bg">
  
<c:if test="${command.highlighted eq true}">

	<div class="row">
		<div class="col-md-12 col-lg-12"><h1 class="text-center" data-animation-effect="fadeInDown" data-effect-delay="600"><spring:message code="portal.Highlights" text="Highlights"/></h1></div>
		<div class="col-sm-12 col-lg-12 col-lg-12 col-xs-12 text-center">
			
									<ul>
										<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
										
											<c:if test="${lookUp.isHighlighted eq 'Y'}">
													<c:choose>
															<c:when test="${not empty lookUp.profileImgPath }">
															
															
															<c:set var="link" value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />	
																		<li>
																			<c:if test="${lookUp.imagePath ne ' ' }">
																					<c:set var="search" value="\\" />
																					<c:set var="replace" value="\\\\" />
																					<c:set var="path" value="${fn:replace(link,search,replace)}" />
																					<c:if test="${empty lookUp.detailEn}"><a href="javascript:void(0);" onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')" ><img src="${lookUp.imagePath }" class="img-responsive" alt="Loading.."></a></c:if>	
																					<c:if test="${not empty lookUp.detailEn}"><div class="banner-img"><a href="javascript:void(0);" onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')" ><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.detailEn}"></a> </div></c:if>	
																			</c:if>
																			<c:choose>
																				<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																					<c:if test="${lookUp.imagePath eq' ' }"><p class="padding-18"></c:if><apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailEn}"></apptags:filedownload><c:if test="${lookUp.imagePath eq ' '}"></p></c:if>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${lookUp.imagePath eq' ' }"><p class="padding-18"></c:if><apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailReg}"></apptags:filedownload><c:if test="${lookUp.imagePath eq ' '}"></p></c:if>
																				</c:otherwise>
																			</c:choose>
																			
																		</li>
																
															</c:when>
															<c:otherwise>
															
																
																			<li>
																			<c:if test="${lookUp.imagePath ne ' '}">
																				<c:if test="${not empty lookUp.detailEn}"><div class="banner-img"><a class="title" title="${lookUp.detailEn}" href="${lookUp.link}" target="new"><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.detailEn}"></a></div></c:if>
																				<c:if test="${empty lookUp.detailEn}"><a class="title" title="${lookUp.detailEn}" href="${lookUp.link}" target="new"><img src="${lookUp.imagePath }" class="img-responsive" alt="Image Loading.."></a></c:if>
																			</c:if>
													
																			
																			<c:choose>
																				<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																				<c:if test="${lookUp.imagePath eq ' '}"><p class="padding-18"></c:if>	<a class="title" title="${lookUp.detailEn}" href="${lookUp.link}" target="new">${lookUp.detailEn}<span class="hidden">Image-Link</span></a><c:if test="${lookUp.imagePath eq ' ' }"></p></c:if>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${lookUp.imagePath eq ' '}"><p class="padding-18"></c:if><a class="title" title="${lookUp.detailReg}" href="${lookUp.link}" target="new">${lookUp.detailReg}<span class="hidden">Image-Link</span></a><c:if test="${lookUp.imagePath eq ' ' }"></p></c:if>
																				</c:otherwise>
																			</c:choose>
																			</li>
																			
																</c:otherwise>
													</c:choose> 
												</c:if>
									</c:forEach>
							</ul>
				</div>
		</div>
			
</c:if>			
	<c:if test="${command.scheme eq true}">
			
		<div class="row">
			<div class="col-md-12 col-lg-12"><h1 class="text-center" data-animation-effect="fadeInDown" data-effect-delay="600"><spring:message code="portal.Scheme" text="Schemes"/></h1></div>
			<div class="col-sm-12 col-lg-12 col-lg-12 col-xs-12 text-center">
			<c:set var="schemeCount" value="0" scope="page"/>
				<ul class="scheme">
					<c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
											<c:if test="${lookUp.isHighlighted ne 'Y' && lookUp.isUsefullLink ne 'Y'}">
											<c:set var="schemeCount" value="${schemeCount + 1}" scope="page"/>
											<c:if test="${schemeCount le 8}">
													<c:choose>
															<c:when test="${not empty lookUp.profileImgPath }">
															
															
															<c:set var="link" value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />	
																		<li>
																			<c:if test="${lookUp.imagePath ne ' ' }">
																					<c:set var="search" value="\\" />
																					<c:set var="replace" value="\\\\" />
																					<c:set var="path" value="${fn:replace(link,search,replace)}" />
																					<c:if test="${empty lookUp.detailEn}"><a href="javascript:void(0);" onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')" ><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.imagePath }"></a></c:if>	
																					<c:if test="${not empty lookUp.detailEn}"><div class="banner-img"><a href="javascript:void(0);" onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')" ><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.detailEn }"></a> </div></c:if>	
																			</c:if>
																			<c:choose>
																				<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																					<c:if test="${lookUp.imagePath eq' ' }"><p class="padding-18"></c:if><apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailEn}"></apptags:filedownload><c:if test="${lookUp.imagePath eq ' '}"></p></c:if>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${lookUp.imagePath eq' ' }"><p class="padding-18"></c:if><apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailReg}"></apptags:filedownload><c:if test="${lookUp.imagePath eq ' '}"></p></c:if>
																				</c:otherwise>
																			</c:choose>
																			
																		</li>
																
															</c:when>
															<c:otherwise>
															
																
																			<li>
																			<c:if test="${lookUp.imagePath ne ' '}">
																				<c:if test="${not empty lookUp.detailEn}"><div class="banner-img"><a class="title" title="${lookUp.detailEn}" href="${lookUp.link}" target="new"><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.detailEn }"></a></div></c:if>
																				<c:if test="${empty lookUp.detailEn}"><a class="title" title="${lookUp.detailEn}" href="${lookUp.link}" target="new"><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.imagePath }"></a></c:if>
																			</c:if>
													
																			
																			<c:choose>
																				<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																					<c:if test="${lookUp.imagePath eq ' '}"><p class="padding-18"></c:if>	<a class="title" title="${lookUp.detailEn}" href="${lookUp.link}" target="new">${lookUp.detailEn}</a><c:if test="${lookUp.imagePath eq ' ' }"></p></c:if>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${lookUp.imagePath eq ' '}"><p class="padding-18"></c:if>	<a class="title" title="${lookUp.detailReg}" href="${lookUp.link}" target="new">${lookUp.detailReg}</a><c:if test="${lookUp.imagePath eq ' ' }"></p></c:if>
																				</c:otherwise>
																			</c:choose>
																			</li>
																			
																</c:otherwise>
													</c:choose> 
													</c:if>
												</c:if>
									</c:forEach>
				</ul>
				
				<c:if test="${schemeCount gt 8}">
					<div class="news-bttn-area text-center">				
	           			<a href="CitizenHome.html?schemes" class="bttn" title="View More"><spring:message code="portal.link.viewmore" text="VIEW MORE"/></a>
	            	</div>
				</c:if>
			</div>
		</div>
			
			
	</c:if>		
</div>			

  
</c:if>




				
				



<!-- SCHEME AND BANNER LINK END -->
<c:if test="${command.usefull eq true}">

<div class="container-fluid important-bg">
<div class="row">
<div class="col-md-12 col-lg-12"><h1 class="text-center" data-animation-effect="fadeInDown" data-effect-delay="600"><spring:message code="ImportantLinks" text="Important Links"/></h1></div>
<div class="col-sm-12 col-lg-12 col-lg-12 col-xs-12 text-center">
<ul>



 <c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
										
											<c:if test="${lookUp.isUsefullLink eq 'Y'}">
													<c:choose>
															<c:when test="${not empty lookUp.profileImgPath }">
															
															
															<c:set var="link" value="${stringUtility.getStringAfterChar('/',lookUp.profileImgPath)}" />	
																		<li>
																			<c:if test="${lookUp.imagePath ne ' ' }">
																					<c:set var="search" value="\\" />
																					<c:set var="replace" value="\\\\" />
																					<c:set var="path" value="${fn:replace(link,search,replace)}" />
																					<c:if test="${empty lookUp.detailEn}"><a href="javascript:void(0);" onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')" ><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.imagePath }" ></a></c:if>	
																					<c:if test="${not empty lookUp.detailEn}"><div class="banner-img"><a href="javascript:void(0);" onclick="downloadFile('${path}','CitizenPublicNotices.html?Download')" ><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.detailEn }"></a> </div></c:if>	
																			</c:if>
																			<c:choose>
																				<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																					<c:if test="${lookUp.imagePath eq' ' }"><p class="padding-18"></c:if><apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailEn}"></apptags:filedownload><c:if test="${lookUp.imagePath eq ' '}"></p></c:if>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${lookUp.imagePath eq' ' }"><p class="padding-18"></c:if><apptags:filedownload filename="EIP" filePath="${link}" actionUrl="CitizenPublicNotices.html?Download" eipFileName="${lookUp.detailReg}"></apptags:filedownload><c:if test="${lookUp.imagePath eq ' '}"></p></c:if>
																				</c:otherwise>
																			</c:choose>
																			
																		</li>
																
															</c:when>
															<c:otherwise>
															
																
																			<li>
																			<c:if test="${lookUp.imagePath ne ' '}">
																				<c:if test="${not empty lookUp.detailEn}"><div class="banner-img"><a class="title" title="${lookUp.detailEn}" href="${lookUp.link}" target="new"><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.detailEn}"></a></div></c:if>
																				<c:if test="${empty lookUp.detailEn}"><a class="title" title="${lookUp.detailEn}" href="${lookUp.link}" target="new"><img src="${lookUp.imagePath }" class="img-responsive" alt="${lookUp.imagePath }"></a></c:if>
																			</c:if>
													
																			
																			<c:choose>
																				<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																					<c:if test="${lookUp.imagePath eq ' '}"><p class="padding-18"></c:if>	<a class="title" title="${lookUp.detailEn}" href="${lookUp.link}" target="new">${lookUp.detailEn}</a><c:if test="${lookUp.imagePath eq ' ' }"></p></c:if>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${lookUp.imagePath eq ' '}"><p class="padding-18"></c:if><a class="title" title="${lookUp.detailReg}" href="${lookUp.link}" target="new">${lookUp.detailReg}</a><c:if test="${lookUp.imagePath eq ' ' }"></p></c:if>
																				</c:otherwise>
																			</c:choose>
																			</li>
																			
																</c:otherwise>
													</c:choose> 
												</c:if>
									</c:forEach>
  
  
  </ul>
</div>
</div>
</div>
</c:if>
<!--  IMPORTANT LINK-->


</c:if>


<c:if test="${command.themeMap['PROFILE'] ne 'I'}">
<div class="bg-green-texture">
	 <c:forEach items="${command.getAllhtml('PROFILE')}" var="Allhtml">
		${Allhtml}
	</c:forEach> 
</div>
 </c:if>		 
 
 
 

<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">  
 
 
<c:if test="${command.themeMap['NEWS'] ne 'I'}">

<div class="white news-bg container-fluid clear">
		<div class="row">
				<div class="col-md-12 col-lg-12"><h1 class="text-center" data-animation-effect="fadeInDown" data-effect-delay="600"><spring:message code="portal.Latest.News" text="Latest News"/></h1></div>
				 
				 <div class="col-md-12 col-lg-12">
				 <ul class="news-holder clearfix">
                 <c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="status" begin="0" end="5">
                 
				
		<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
		<c:forEach items="${links}" var="download" varStatus="count">
		<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
		<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
		<c:set var="link" value="${stringUtility.getStringAfterChar('/',download)}" />
		
                 <li class="col-sm-6 col-md-6 col-lg-4 col-xs-12 wow fadeInLeft animated animated" data-wow-offset="10" data-wow-duration="1.5s" style="visibility: visible;-webkit-animation-duration: 1.5s; -moz-animation-duration: 1.5s; animation-duration: 1.5s;">
                 
                
                 
        <div class="news-description">
        <c:choose>
	        <c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
	        
				<div class="col-xs-5">
					
						<c:if test="${ lookUp.attachImage ne ''}"><img src="${lookUp.attachImage }" class="img-responsive" style="height:130px;" alt="${lookUp.announceDescEng}"></c:if>
						<c:if test="${ lookUp.attachImage eq ''}"><img src="\images\news.svg" class="img-responsive" style="height:130px;" alt=""></c:if>
				</div> 
		        <div class="col-xs-7">
		        <c:choose>
			        <c:when test="${isDMS}">
			        	<div class="content-news"><h3><a href="javascript:void(0);" onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescEng}</a></h3></div>
			        </c:when>
		       
			 		<c:otherwise>
						<div class="content-news"><h3><apptags:filedownload filename="EIP" filePath="${download}" actionUrl="CitizenHome.html?Download" eipFileName="${lookUp.announceDescEng}" ></apptags:filedownload></h3></div>
		 			</c:otherwise> 
				</c:choose>
				<span class="date"><fmt:formatDate type = "date" value="${lookUp.newsDate}"  dateStyle="SHORT" pattern="dd/MM/yyyy"/></span>
				</div>
			</c:when>   
		<c:otherwise>
			<div class="col-xs-5">
				 <c:if test="${ lookUp.attachImage ne ' '}"><img src="${lookUp.attachImage }" class="img-responsive" style="height:130px;"  alt="${lookUp.announceDescReg}"></c:if>
				 <c:if test="${ lookUp.attachImage eq ' '}"><img src="\images\news.svg" class="img-responsive" alt="Minor sample post image"></c:if>
			</div>
			<div class="col-xs-7">
			<div class="content-news">
				<c:choose>
	                <c:when test="${isDMS}">						                    
	                  <a href="javascript:void(0);" onClick="downloadDmsFile('${lookUp.docId}','${lookUp.docName}','CitizenPublicNotices.html?DownloadFile' )">${lookUp.announceDescReg}</a>
                	</c:when>
                	
				     <c:otherwise>
				      	<h3><apptags:filedownload filename="EIP" filePath="${download}" actionUrl="CitizenHome.html?Download" eipFileName="${lookUp.announceDescReg}"></apptags:filedownload></h3>
				     </c:otherwise>
			     </c:choose>
	     
			</div>
			 <span class="date"><fmt:formatDate type = "date" value="${lookUp.newsDate}"  dateStyle="SHORT" pattern="dd/MM/yyyy"/></span>
			</div>
		</c:otherwise>
	</c:choose>
	</div>
	</li>	
	
	
    </c:forEach>
	</c:forEach>
    	</ul>
 		  <div class="news-bttn-area text-center">				
           <a href="CitizenHome.html?newsAndEvent" class="bttn" title="View More"><spring:message code="portal.link.viewmore" text="VIEW MORE"/></a>
            </div>
       			</div> 

		 
							
					</div>							
				</div>
    
</c:if>
<c:if test="${command.themeMap['PHOTO_GALLERY'] ne 'I'}">

  <div class="container-fluid photo-box">
  
  <div class="row">
  
				 <div class="col-md-12 col-lg-12">
						 <h1 class="text-center " data-animation-effect="fadeInDown" data-effect-delay="600"><spring:message code="portal.Media.Gallery" text="Media Gallery"/></h1>
				</div>
    
   <div class="col-md-12 col-lg-12">
   <div class="col-sm-3">
    <h3><spring:message code="PhotoGallery" text="Photo Gallery"/></h3>
   <div class="gallery-box"><a href="Content.html?links&page=Photo Gallery" title="<spring:message code="PhotoGallery" text="Photo Gallery"/>"><img src="assets/img/photos1.png" class="img-responsive" alt="Photo Gallery"></a>
   
     <a href="Content.html?links&page=Photo Gallery" class="view-more"  title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message code="portal.link.viewmore" text="View More"/> <i class="fa fa-arrow-right" aria-hidden="true"></i></a>

   </div>
   
   </div>
     <div class="col-sm-3">
	     <h3><spring:message code="VideoGallery" text="Video Gallery"/></h3>
 	 <div class="gallery-box"><a href="Content.html?links&page=Video Gallery" title="<spring:message code="VideoGallery" text="Video Gallery"/>"><img src="assets/img/videos1.png" class="img-responsive" alt="Video Gallery"></a>
  <a href="Content.html?links&page=Video Gallery" class="view-more"  title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message code="portal.link.viewmore" text="View More"/> <i class="fa fa-arrow-right" aria-hidden="true"></i> </a>
  
   
     </div></div>
   <div class="col-sm-3">
    <h3><spring:message code="portal.publications" text="Publications"/></h3>

   <div class="gallery-box"><a href="Content.html?links&page=Publications" title="Publications"><img src="assets/img/publications1.png" class="text-center img-responsive" alt="Publications"></a>
     <a href="Content.html?links&page=Publications" class="view-more" title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message code="portal.link.viewmore" text="View More"/> <i class="fa fa-arrow-right" aria-hidden="true"></i> </a>

   </div></div>
   <div class="col-sm-3">
    <h3><spring:message code="Feedback" text="Feedback"/></h3>

   <div class="gallery-box"><a onclick="openPopup('CitizenFeedBack.html?publishFeedBack')" href='#' title="Feedback"><img src="assets/img/feedback1.png" class="text-center img-responsive" alt="Feedback"></a>
     <a onclick="openPopup('CitizenFeedBack.html?publishFeedBack')" href='#' class="view-more" title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message code="portal.link.viewmore" text="View More"/> <i class="fa fa-arrow-right" aria-hidden="true"></i> </a>

   </div></div>
  </div>
  </div>
  </div>
 </c:if>  
<c:if test="${command.themeMap['FACTS'] ne 'I'}">
<div class="container-fluid facts clear">

<div class="col-md-12 col-lg-12">
	<h1 class="text-center" data-animation-effect="fadeInDown" data-effect-delay="600"><spring:message code="portal.Facts.Speak" text="Facts Speak"/></h1>
	</div>
	
	<div class="container-fluid">
	
	<div class="clear" id="schemes-new">
	<c:forEach items="${command.getAllhtml('FACTS')}" var="Allhtml"> ${Allhtml} </c:forEach> 
	</div>
	
	</div>
	</div>
 </c:if>	
 
</c:if>




<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}">


<c:if test="${command.themeMap['FACTS'] ne 'I'}">
<div class="container-fluid facts clear">

<div class="col-md-12 col-lg-12">
	<h1 class="text-center" data-animation-effect="fadeInDown" data-effect-delay="600"><spring:message code="portal.Facts.Speak" text="Facts Speak"/></h1>
	</div>
	
	<div class="container-fluid">
	
	<div class="clear" id="schemes-new">
	<c:forEach items="${command.getAllhtml('FACTS')}" var="Allhtml"> ${Allhtml} </c:forEach> 
	</div>
	
	</div>
	</div>
 </c:if>  

<c:if test="${command.themeMap['PHOTO_GALLERY'] ne 'I'}">

  <div class="container-fluid photo-box">
  
  <div class="row">
  
				 <div class="col-md-12 col-lg-12">
						 <h1 class="text-center " data-animation-effect="fadeInDown" data-effect-delay="600"><spring:message code="portal.Media.Gallery" text="Media Gallery"/></h1>
				</div>
    
   <div class="col-md-12 col-lg-12">
   <div class="col-sm-3">
    <h3><spring:message code="PhotoGallery" text="Photo Gallery"/></h3>
   <div class="gallery-box"><a href="Content.html?links&page=Photo Gallery" title="<spring:message code="PhotoGallery" text="Photo Gallery"/>"><img src="assets/img/photos1.png" class="img-responsive" alt="Photo Gallery"></a>
   
     <a href="Content.html?links&page=Photo Gallery" class="view-more"  title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message code="portal.link.viewmore" text="View More"/> <i class="fa fa-arrow-right" aria-hidden="true"></i></a>

   </div>
   
   </div>
     <div class="col-sm-3">
	     <h3><spring:message code="VideoGallery" text="Video Gallery"/></h3>
 	 <div class="gallery-box"><a href="Content.html?links&page=Video Gallery" title="<spring:message code="VideoGallery" text="Video Gallery"/>"><img src="assets/img/videos1.png" class="img-responsive" alt="Video Gallery"></a>
  <a href="Content.html?links&page=Video Gallery" class="view-more"  title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message code="portal.link.viewmore" text="View More"/> <i class="fa fa-arrow-right" aria-hidden="true"></i> </a>
  
   
     </div></div>
   <div class="col-sm-3">
    <h3><spring:message code="portal.publications" text="Publications"/></h3>

   <div class="gallery-box"><a href="Content.html?links&page=Publications" title="Publications"><img src="assets/img/publications1.png" class="text-center img-responsive" alt="Publications"></a>
     <a href="Content.html?links&page=Publications" class="view-more" title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message code="portal.link.viewmore" text="View More"/> <i class="fa fa-arrow-right" aria-hidden="true"></i> </a>

   </div></div>
   
   <div class="col-sm-3">
    <h3><spring:message code="Feedback" text="Feedback"/></h3>

   <div class="gallery-box"><a onclick="openPopup('CitizenFeedBack.html?publishFeedBack')" href='#' title="Feedback"><img src="assets/img/feedback1.png" class="text-center img-responsive" alt="Feedback"></a>
     <a onclick="openPopup('CitizenFeedBack.html?publishFeedBack')" href='#' class="view-more" title="<spring:message code="portal.link.viewmore" text="View More"/>"><spring:message code="portal.link.viewmore" text="View More"/> <i class="fa fa-arrow-right" aria-hidden="true"></i> </a>

   </div></div>
  </div>
  </div>
  </div>
 </c:if>  


</c:if>
<%-- <div class="intro-header"> 
<div class="container news-container"  align="center">

<div class="tab-content custom-tab-content" align="center">
<div class="subscribe-panel">
<h1>Newsletter!!!</h1>
<p>Subscribe to our weekly Newsletter and stay tuned.</p>
                
                <form action="" method="post">
                    	
							
							<div class="col-md-4 col-md-offset-3">
								<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-envelope" aria-hidden="true"></i></span>
									<input type="text" class="form-control input-lg" name="email" id="email"  placeholder="Enter your Email"/>
								</div>
							</div>
					<div class="col-sm-2">
                    <button class="btn btn-blue-2 btn-lg">Subscribe Now!</button>
                   </div>
              </form>

</div>
</div>
</div>
</div> --%>


<c:if test="${command.themeMap['RTI'] ne 'I'}">
	 

		</c:if>




 
<script src="js/eip/citizen/guest-home.js"></script>

 <script>
$(document).ready(function() {

    var show_per_page = 5;
    var number_of_items = $('#list').children('li').size();
    var number_of_pages = Math.ceil(number_of_items / show_per_page);

    $('#list-main').append('<ul class="pagination"></ul><input id=current_page type=hidden><input id=show_per_page type=hidden>');
    $('#current_page').val(0);
    $('#show_per_page').val(show_per_page);

    var navigation_html = '';
    var current_link = 0;
    while (number_of_pages > current_link) {
        navigation_html += '<li><a class="page" onclick="go_to_page(' + current_link + ')" longdesc="' + current_link + '">' + (current_link + 1) + '</a></li>';
        current_link++;
    }
    navigation_html += '';

    $('.pagination').html(navigation_html);
    $('.pagination li a.page:first').addClass('active');

    $('#list').children().css('display', 'none');
    $('#list').children().slice(0, show_per_page).css('display', 'block');

});



function go_to_page(page_num) {
	
    var show_per_page = parseInt($('#show_per_page').val(), 0);
    start_from = page_num * show_per_page;
    end_on = start_from + show_per_page;
    $('#list').children().css('display', 'none').slice(start_from, end_on).css('display', 'block');
  
    $('ul li a.active').removeClass('active');
    $('.pagination li a.page[longdesc=' + page_num + ']').addClass('active');
    $('#current_page').val(page_num);
}

$(function(){
	  $('ul.scrolllistbox-new').marquee(); 
});


/* 
$(document).ready(function(){
    var maxLength = 500;
    $(".about").each(function(){
        var myStr = $(this).text();
        if($.trim(myStr).length > maxLength){
            var newStr = myStr.substring(0, maxLength);
            var removedStr = myStr.substring(maxLength, $.trim(myStr).length);
            $(this).empty().html(newStr);
            $(this).append(' <a href="CitizenAboutUs.html" class="read-more" style="right:0px;">read more...</a>');
            $(this).append('<span class="more-text">' + removedStr + '</span>');
        }
    });
    $(".read-more").click(function(){
        $(this).siblings(".more-text").contents().unwrap();
        $(this).remove();
    });
}); */
 </script>