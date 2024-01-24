<div id="onlineServices" title="D2K Application" style="display: none" class="leanmodal"></div>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


  <script lang="javascript">

    $(document).ready(function () {

    	 var result=JSON.parse(__doAjaxRequest('CitizenHome.html?superuserchart','post',"",false,'json'));
    	  browserData = [];
    	    for (var i = 0; i < result.length; i++) {
    			browserData.push({
    	            name: result[i].name,
    	            y: result[i].y,
    	         }); 
    		}
        

    	 $('#container').highcharts({
             chart: {
                 plotBackgroundColor: null,
                 plotBorderWidth: null,
                 plotShadow: false,
                 type: 'pie'
             },
             title: {
                 text: getLocalMessage('eip.dept.dashboard.orgwise')
             },
           tooltip: {
             	formatter: function() {
             		return  '<b>'+'<span>'+ this.point.name +'</span><br/>' + getLocalMessage('citizen.dashboard.count') +':</b> '+this.point.y;
                 }
             },
             plotOptions: {
                 pie: {
                     allowPointSelect: true,
                     cursor: 'pointer',
                     dataLabels: {
                         enabled: false
                     },
                     showInLegend: true
                 }
             },
             series: [{
                 type: 'pie',
                 name: '#',
                 data:result
             }]
         });
 		

    	 $('#calendar').fullCalendar({

    			googleCalendarApiKey: 'AIzaSyDcnW6WejpTOCffshGDDb4neIrXVUA1EAE',
    		    events: 'en.indian#holiday@group.v.calendar.google.com',
    			eventClick: function(event) {
    				window.open(event.url, 'gcalevent', 'width=770,height=400');
    				return false;
    			},
    			loading: function(bool) {
    				$('#loading').toggle(bool);
    			}
    			
    		});		
  });
</script>

<div id="wrapper"> 
<div class="wrapper clear"> 

<div class="form-div">
	
  <c:set var="now" value="<%=new java.util.Date()%>" />
  <ul class="breadcrumbs">
       <li><spring:message code="menu.home"/></li>
     <li><spring:message code="dept.link.name"></spring:message></li> 
        <li class="active"><spring:message code="citizen.dashboard.heading"></spring:message></li>
      </ul>
         <h1><spring:message code="citizen.dashboard.heading"/></h1>
      <div id="content" class="dashboard clear">
        <div class="grid txt_center">
         
         <div class="full clear">
         <ul>
            <li>
             <table class="gridtable">
                        
                         <tr>
                           <th><spring:message code="eip.citizen.pbNotice.deptName"/></th>
                            <th><spring:message code="admin.application.completed"/></th>
                            <th><spring:message code="admin.application.pending"/></th>
                             <th><spring:message code="admin.application.rejected"/></th>
                         </tr>
                           <c:if test="${not empty  command.deptCountMap}"> 
                       <c:forEach items="${command.deptCountMap}" var="map" varStatus="var"> 
                                  <tr>
                           		   <td>${map.key}</td> 
                                   <c:set value="${map.value}" var="status"></c:set>
		                          <td>
		                          	<c:choose>
										<c:when test="${status.containsKey('Completed')}">
											${status.get('Completed')}
										</c:when>
										<c:otherwise>
										0
										</c:otherwise>
									</c:choose>
		                          </td>
		                          <td>
		                          	<c:choose>
										<c:when test="${status.containsKey('Pending')}">
											${status.get('Pending')}
										</c:when>
										<c:otherwise>
											0
										</c:otherwise>
									</c:choose>
		                          </td>
		                          <td>
		                          	<c:choose>
										<c:when test="${status.containsKey('Rejected')}">
											${status.get('Rejected')}
										</c:when>
										<c:otherwise>
										0
										</c:otherwise>
									</c:choose>
		                          </td>
		                       
                          		</tr>
                            </c:forEach>
                     </c:if>
                     </table>
                    
            </li>
          </ul>
        </div>
        
        <ul>
       <li class="margin_right_10"><div id="container"></div></li>
       </ul>
         </div>
    </div>

</div>
</div>
</div>
        

  
  



