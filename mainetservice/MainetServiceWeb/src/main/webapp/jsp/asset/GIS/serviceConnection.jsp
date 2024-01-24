<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<link rel="stylesheet"
	href="https://js.arcgis.com/4.10/esri/css/main.css">
<link rel="stylesheet" href="../../../assets/libs/GIS/css/custom.css">
<link rel="stylesheet"	href="../../../assets/libs/bootstrap/css/bootstrap.min.css">
<script src="https://js.arcgis.com/4.10/"></script>
<script src="https://code.jquery.com/jquery-latest.min.js"
	type="text/javascript"></script>
<script src="../../../assets/libs/GIS/js/Service_Connection.js"
	type="text/javascript"></script>

<div class="panel-container">
<script type="text/javascript">
onloadgetdefaultmap('<%= request.getParameter("ids") %>');
</script>

	<div class="panel-side">
		<h2>Service Connection By Facility Identifier</h2>
		<ul id="ABMI_Graphics">
			<li>Loading&hellip;</li>
		</ul>
	</div>
	<div class="border-1 padding-5" style="height: 900px; width: 100%;"
		id="sceneDiv"></div>
</div>


