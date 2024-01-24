<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%-- <jsp:useBean id="stringUtility" class="com.abm.mainet.common.utility.StringUtility"/> --%>
<script src="js/eip/citizen/landingPage.js"></script>
<!-- ============================================================== --> 
    <!-- Start Content here --> 
    <!-- ============================================================== -->
    <div class="content"> 
      <!-- Start info box -->
      <div class="row top-summary">
        <div class="col-lg-3 col-md-6">
          <div class="widget green-1 animated fadeInDown">
            <div class="widget-content padding">
              <div class="widget-icon"> <i class="icon-globe-inv"></i> </div>
              <div class="text-box">
                <p class="maindata">Total <b>Visitors</b></p>
                <h2><span class="animate-number" data-value="25153" data-duration="3000">0</span></h2>
                <div class="clearfix"></div>
              </div>
            </div>
            <div class="widget-footer">
              <div class="row">
                <div class="col-sm-12"> <i class="fa fa-caret-up rel-change"></i> <b>39%</b> increase in traffic </div>
              </div>
              <div class="clearfix"></div>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-md-6">
          <div class="widget darkblue-2 animated fadeInDown">
            <div class="widget-content padding">
              <div class="widget-icon"> <i class="fa fa-users"></i> </div>
              <div class="text-box">
                <p class="maindata">Register <b>Users</b></p>
                <h2><span class="animate-number" data-value="6399" data-duration="3000">0</span></h2>
                <div class="clearfix"></div>
              </div>
            </div>
            <div class="widget-footer">
              <div class="row">
                <div class="col-sm-12"> <i class="fa fa-caret-up rel-change"></i> <b>5%</b> increase Registration </div>
              </div>
              <div class="clearfix"></div>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-md-6">
          <div class="widget pink-1 animated fadeInDown">
            <div class="widget-content padding">
              <div class="widget-icon"> <i class="fa fa-building-o"></i> </div>
              <div class="text-box">
                <p class="maindata">Active <b>ULB</b></p>
                <h2><span class="animate-number" data-value="11" data-duration="3000">0</span></h2>
                <div class="clearfix"></div>
              </div>
            </div>
            <div class="widget-footer">
              <div class="row">
                <div class="col-sm-12"> <i class="fa fa-caret-up rel-change"></i> <b>7</b> Added </div>
              </div>
              <div class="clearfix"></div>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-md-6">
          <div class="widget lightblue-1 animated fadeInDown">
            <div class="widget-content padding">
              <div class="widget-icon"> <i class="fa fa-user"></i> </div>
              <div class="text-box">
                <p class="maindata">Loged In<b> Users</b></p>
                <h2><span class="animate-number" data-value="148" data-duration="3000">0</span></h2>
                <div class="clearfix"></div>
              </div>
            </div>
            <div class="widget-footer">
              <div class="row">
                <div class="col-sm-12"> <i class="fa fa-caret-up rel-change"></i> <b>6%</b> increase in users </div>
              </div>
              <div class="clearfix"></div>
            </div>
          </div>
        </div>
      </div>
      <!-- End of info box -->
      
      <div class="row">
        <div class="col-lg-8 portlets">
          <div class="widget">
            <div class="widget-content padding">
               <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
  <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
  </ol>

  <!-- Wrapper for slides -->
  <div class="heightfix-slider carousel-inner" role="listbox">
    <div class="item active">
      <img src="images/slider/1.jpg" alt="">
    </div>
    <div class="item">
      <img src="images/slider/2.jpg" alt="">
    </div>
   <div class="item">
      <img src="images/slider/3.jpg" alt="">
    </div>
  </div>

  <!-- Controls -->
  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>
            </div>
          </div>
        </div>
        <div class="col-lg-4 portlets">
          <div class="widget">
              <div class="widget-header transparent">
                <h2>Select Your<strong> Municipality</strong></h2>
                <div class="additional-btn">
                  <a href="#" class="widget-toggle"><i class="icon-down-open-2"></i></a>
                </div>
              </div>
              <div class="widget-content padding">
                <form:form method="post" name="selectMunicipalForm" id="selectMunicipalForm" action="Home.html" class="list"> 	
		         <div id="contentright">
		  		   <spring:hasBindErrors name="command">
					<script type="text/javascript">
						function closeErrBox()
						{
							$('.error-div').remove();
						}
					</script>
				    <div class="error-div" align="left">
					   	<div class="closeme">
							<img alt="Close" width="25" title="Close" src="css/images/close.png" onclick="closeErrBox()"/>
						</div>
					   	<ul>
					   		<form:errors path="*" element="li" cssClass="error-msg"/>		   		  		
					   	</ul>
				    </div>
				</spring:hasBindErrors></div>	
                  <div class="form-group">
                  <label><spring:message code="eip.landingpage.selectdistrict.msg" text="Select District"/></label>
                  <select class="form-control" name="selectedDistrict" id="selectedDistrict" onchange="onDistrictChange(this);" data-content="Select District">
                    <option value="-1"><spring:message code="eip.landingpage.select" /></option>
	                   <c:forEach items="${command.districtList}" var="district">
	          	    <option value="${district.lookUpId}">${district.lookUpDesc}</option>
	                   </c:forEach>
	             </select>
                  </div>
                  <div class="form-group">
                  <label><spring:message code="eip.landingpage.urbanlocal.msg" text="Select Urban Local Body"/></label>
                    <div id="mainselection">
                           <select name="selectedMunicipal" id="selectedMunicipal" data-content="Select Municipal" class="form-control">
	                           <option value="-1"><spring:message code="eip.landingpage.select" /></option>
 	                       </select>
 	                   </div> 
                  </div>
                    <input type="submit" class="btn btn-primary btn-lg btn-block" value="<spring:message code="rti.submit" />" id="submit"/>
               </form:form>
              </div>
            </div>
        </div>
      </div>
      <div class="row">
        <div class="col-lg-8 portlets">
          <div class="widget">
            <div class="widget-header ">
              <h2><strong>e - Municipality</strong> Project</h2>
              <div class="additional-btn"> <a href="#" class="widget-toggle"><i class="icon-down-open-2"></i></a> </div>
            </div>
            <div class="widget-content padding">
                <p> ${command.aboutOrgDescDetails}</p>
               <a href="javascript:void(0);"  onclick="readMore('${command.aboutOrgDescDetails}');" class="read_more"><spring:message
							code="eip.citizen.home.readMore" /></a>
            </div>
          </div>

          <div class="widget">
            <div class="widget-header transparent">
              <h2>Our <strong>Services</strong></h2>
              <div class="additional-btn">  <a href="#" class="widget-toggle"><i class="icon-down-open-2"></i></a> </div>
            </div>
            <div class="widget-content padding">
              <div class="boxes">
                  <div class="services marginfix-services">
                    <ul>
                        <li>
                        <img alt="" src="images/services/1-img.png">
                        <p> <spring:message code="eip.langingpage.rti" 
                        /></p>
                        </li><li>
                        <img alt="" src="images/services/2-img.png">
                        <p><spring:message code="eip.langingpage.birthanddeath" /></p>
                        </li><li>
                        <img alt="" src="images/services/3-img.png">
                        <p><spring:message code="eip.langingpage.townplanning" /></p>
                        </li><li>
                        <img alt="" src="images/services/4-img.png">
                        <p> <spring:message code="eip.langingpage.property" /></p>
                        </li><li>
                        <img alt="" src="images/services/5-img.png">
                        <p><spring:message code="eip.langingpage.advertismentandhoarding" /></p>
                        </li><li>
                        <img alt="" src="images/services/6-img.png">
                        <p> <spring:message code="eip.langingpage.rent" /></p>
                        </li><li>
                        <img alt="" src="images/services/7-img.png">
                        <p><spring:message code="eip.langingpage.lease"/></p>
                        </li>
                    </ul>
                  </div>
                  <div class="rec_nav"> 
                    <a href="javascript:void(0)" class="prev_ser"></a> 
                    <a href="javascript:void(0)" class="next_ser"></a> 
                  </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-4 portlets">
          <div class="widget">
            <div class="widget-header transparent">
              <h2><strong>Recent </strong>Announcements</h2>
              <div class="additional-btn"> 
                <a href="#" class="widget-toggle"><i class="icon-down-open-2"></i></a> </div>
            </div>
            <div class="widget-content padding">
              <div class="boxes about-fix-div">
                <div class="recent_announcement heightfix-announcement">
                  <ul>
	                <c:forEach items="${command.eipAnnouncementLanding}" var="lookUp" varStatus="status">
					<c:set var="links" value="${fn:split(lookUp.attachment,',')}"/>
									   <li>
										<c:forEach items="${links}" var="download"> 
											 <c:choose>
											<c:when
												test="${userSession.getCurrent().getLanguageId() eq 1}">
												<apptags:filedownload filename="EIP" filePath="${download}" actionUrl="ApplicationForm.html?Download" eipFileName="${status.count}.${lookUp.announceDescEng}"></apptags:filedownload>
												</c:when>
											<c:otherwise>
												<apptags:filedownload filename="EIP" filePath="${download}" actionUrl="ApplicationForm.html?Download" eipFileName="${status.count}.${lookUp.announceDescReg}"></apptags:filedownload>
													</c:otherwise>
										      </c:choose>
											</c:forEach>
										</li>
						 </c:forEach>
						 </ul>
                </div>
                <div class="rec_nav"> <a href="javascript:void(0)" class="rec_prev"></a> <a href="javascript:void(0)" class="rec_next"></a> </div>
              </div>
            </div>
          </div>
        </div>
      </div>
     
          

    </div>
    <!-- ============================================================== --> 
    <!-- End content here --> 
    <!-- ============================================================== --> 