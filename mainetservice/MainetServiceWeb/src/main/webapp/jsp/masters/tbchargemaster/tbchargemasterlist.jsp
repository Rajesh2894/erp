<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/mainet/themes/humanity/jquery.ui.all.css" />
<script src="js/mainet/ui/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/mainet/jquery.jqGrid.min.js" type="text/javascript"></script>

<script src="js/masters/chargemaster.js"></script>
<script src="js/mainet/script-library.js"></script>
<script type="text/javascript">


</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				Charge <strong>Master</strong>
			</h2>
		</div>

		<div class="widget-content padding">
			<div class="text-right padding-bottom-10">
				<input type="button" value="Create Data" class="btn btn-success createData" >
			</div>
			
			
			<table id="grid"></table>
			<div id="pagered"></div>
		</div>
	</div>
</div>
