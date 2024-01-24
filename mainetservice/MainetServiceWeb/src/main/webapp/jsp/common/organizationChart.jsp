<title>${OrgName}Organization Chart</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/jquery.orgchart.css" media="all" rel="stylesheet"
	type="text/css" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="js/common/jquery.orgchart.js" type="text/javascript"></script>

<style type="text/css">
#orgChart {
	width: auto;
	height: auto;
}

#orgChartContainer {
	width: auto;
	height: auto;
	overflow: auto;
	background: #eeeeee;
}
#orgChartContainer h2 {
	line-height: 1rem;
}
div.orgChart div.node {
	border: 1px solid #3a3a3a;
	border-radius: 0.4rem;
	width: 10rem;
	min-height: 4rem !important;
    max-height: 6rem;
    height: fit-content;
	display: flex;
    margin: 0rem auto;
}
div.orgChart h2 {
	flex-grow: 3;
    margin: auto 0rem;
}
div.orgChart h2:hover {
	background: transparent;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<c:choose>
				<c:when test="${OrgName eq 'ASC'}">
					<h2 style="background-color: EAE7E6;" align="center">ASCL
						Organization Chart</h2>
				</c:when>
				<c:otherwise>
					<h2 style="background-color: EAE7E6;" align="center">${OrgName}
						Organization Chart</h2>
				</c:otherwise>
			</c:choose>

		</div>
		<div class="widget-content padding">
			<div id="orgChartContainer">
				<div id="orgChart"></div>
			</div>

		
	
		<script type="text/javascript">
		/* 
		@Aditya : If we can create a table(tb_ascl_org_mapping) in DB using 3 columns*
		id(incremental) int
		name varchar2
		parent int
		Now there would be parent-child mapping. The child node added will be part of parent id
	
		The output of the table can be passed to data load variable 'asclOrgData' in json string format as below	*/
		$(function() {
			debugger;
			var url = "AsclOrgChart.html?getASCLOrgChartData";
			var returnData = {
	
			};
		var asclOrgData = __doAjaxRequest(url, 'POST', returnData, false, 'json');	
			
	    $(function(){
	        org_chart = $('#orgChart').orgChart({
	            data: asclOrgData,
	            showControls: false,
	            allowEdit: false,
	            onAddNode: function(node){ 
	               // log('Created new node on node '+node.data.id);
	                org_chart.newNode(node.data.id); 
	            },
	            onDeleteNode: function(node){
	               // log('Deleted node '+node.data.id);
	                org_chart.deleteNode(node.data.id); 
	            },
	            onClickNode: function(node){
	               // log('Clicked node '+node.data.id);
	            }
	
	        });
	    });
	
	    // just for example purpose
	    function log(text){
	        $('#consoleOutput').append('<p>'+text+'</p>')
	    }
	    
		});  
	    </script>
	
		<script type="text/javascript">
	
	  var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', 'UA-36251023-1']);
	  _gaq.push(['_setDomainName', 'jqueryscript.net']);
	  _gaq.push(['_trackPageview']);
	
	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })/* (); */
	
	</script>
	<div class="text-center padding-top-10">
		<apptags:backButton url="AdminHome.html"></apptags:backButton>
	</div>
	</div>
</div>
</div>