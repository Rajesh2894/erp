<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<script src="assets/libs/indiaMap/underscore-min.js"></script>
<script src="assets/libs/indiaMap/jquery.lazyload.js"
	type="text/javascript" charset="utf-8"></script>
<script src="assets/libs/indiaMap/G.min.js"></script>
<script src="assets/libs/indiaMap/d3.min.js"></script>
<script src="assets/libs/indiaMap/topojson.min.js"></script>

<!-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script> -->
<script type="text/javascript" src="js/common/sfacIndiaMap.js"></script>
<style>

rect.background {
    fill: none;
    pointer-events: all;
}

</style>

<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message text="Dashboard" />
			</h2>

		</div>

		<div class="widget-content padding">
			<form:form id="SfacIndiaMap" action="SfacIndiaMap.html" method="post"
				class="form-horizontal">
				<div class="form-group">
					<label class="col-sm-1 control-label required-control" for="State">
						<spring:message code="sfac.state" text="State" />
					</label>
					<div class="col-sm-3">
						<form:select path="dto.stateId"
							class="form-control chosen-select-no-results" id="stateId"
							onchange="getDistrictList();">
							<form:option value="0">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${command.stateList}" var="lookUp">
								<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-1 control-label required-control"
						for="District"> <spring:message code="sfac.district" text="District" />
					</label>
					<div class="col-sm-3">
						<form:select path="dto.distId" id="distId"
							onchange="getDisData(this);"
							class="form-control mandColorClass distId chosen-select-no-results">
							<form:option value="0">
								<spring:message code='' text="Select" />
							</form:option>
							<c:forEach items="${command.districtList}" var="dist">
								<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-1 control-label required-control"
						for="District"> <spring:message code="sfac.block" text="Block" />
					</label>
					<div class="col-sm-3">
						<form:select path="dto.blockId" id="blockId"
							onchange="getBlockData(this);"
							class="form-control mandColorClass distId chosen-select-no-results">
							<form:option value="0">
								<spring:message code='' text="Select" />
							</form:option>
							<c:forEach items="${command.blockList}" var="dist">
								<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div>&nbsp;</div>

				<div class="">
					<div class="row">
						<div class="col-md-4 col-sm-4 col-lg-4"
							style="padding-right: 0px;">
							<svg viewBox="0 0 430 500" id="map-svg" width="100%"
								height="100%" preserveAspectRatio="xMinYMin meet">
    <rect class="background" width="450" height="500"></rect>
    <g id="map-path" transform="" style="stroke-width: 1.5px;"></g>
    <g id="map-legend"></g>
</svg>
						</div>

					
							<div class="col-md-8 col-sm-12 col-lg-8">
								<div class="row">
									<div class="col-md-12 col-sm-12 col-lg-12">
										<ul class="nav nav-tabs " id="fpoPMParentTab">
											<li id="home-tab" ><a data-toggle="tab"
												href="#" onclick="window.location.reload(true);" ><strong class="fa fa-home fa-lg"> </strong></a></li>
											<li id="vacant-tab" class="active"><a data-toggle="tab"
												href="#" onclick="loadAllocated('vacant');">Vacant</a></li>


											<li id="allocated-tab"><a data-toggle="tab"
												onclick="loadAllocated('allocated');" href="#">Allocated</a></li>
											<form:input path="viewMode" type="hidden" 
												id="viewMode" />
											<form:input path="totalBlock" type="hidden" 
												id="totalBlock" />
											<form:input path="vacantCount" type="hidden" 
												id="vacantCount" />
											<form:input path="allocatedCount" type="hidden" 
												id="allocatedCount" />			
										</ul>
									</div>
								</div>

									<div id="mainDiv">
								<div class="tab-content">

									<div id="vacant" class="tab-pane fade in active">
										<div class="row">
											<div class="col-md-8 col-sm-12 col-lg-8 ">
												<h4 style="text-align: center; font-size: 15px;">
													<b>Total VACANT Block Summary</b>
												</h4>
												<div class = "table-responsive">
												<table class="table  cleanness_table_data table-bordered table-striped"  id = "vacantTable">
													<thead>
														<tr>
															<th style="width: 1%;">S.No</th>
															<th style="width: 10%;">District</th>
															<th style="width: 20%;">State</th>
															<th style="width: 10%;">Block</th>
															<th style="width: 30%;">Status</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${command.vacantBlockMap}"
															var="mapData1">

															<c:forEach items="${mapData1.value}" var="mapData"
																varStatus="counter">
																<tr class="urlfilter"
																	href="?state=${mapData.state }&range=1-30000&city=${mapData.district}"
																	style="cursor: pointer;">
																	<td style="width: 1%;">${counter.count}</td>
																	<td style="width: 29%;">${mapData.district}</td>
																	<td style="width: 35%;">${mapData.state}</td>
																	<td style="width: 35%;">${mapData.block}</td>
																	<td style="width: 35%;">Vacant</td>

																</tr>
															</c:forEach>
														</c:forEach>
													</tbody>
												</table>
												</div>
											</div>

											<div class="col-md-4 col-sm-12 col-lg-4">

												<h4 style="text-align: center; font-size: 15px;">

													<b>Total VACANT Blocks</b>

												</h4>
												<table class="table cust-tbl">
													<tbody>

														<tr class="tbl-style ">
															<td style="width: 50%;"
																class="donutbar-1"></td>
															<td><div>
																	<div>Total Blocks</div>
																	${command.totalBlock }
																</div>
																<div>
																	<div>Total VACANT Blocks

																<c:forEach items="${command.vacantBlockMap}"
																		var="mapData1">
																${mapData1.key }
																</c:forEach>
																</div></div></td>
														</tr>

													</tbody>
												</table>

											</div>
										</div>
									</div>

									<div id="allocated" class="tab-pane fade ">
										<div class="row">
											<div class="col-md-8 col-sm-12 col-lg-8 ">
												<h4 style="text-align: center; font-size: 15px;">
													<b>Total Allocated Block Summary</b>
												</h4>
												<div class= "table-responsive">
												<table class="table  cleanness_table_data table-bordered table-striped" id = "allocatedTable">
													<thead>
														<tr>
															<th style="width: 1%;">S.No</th>
															<th style="width: 10%;">District</th>
															<th style="width: 20%;">State</th>
															<th style="width: 10%;">Block</th>
															<th style="width: 30%;">Status</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${command.allocatedBlockMap}"
															var="mapData1">
															<c:forEach items="${mapData1.value}" var="mapData"
																varStatus="counter">
																<tr class="urlfilter"
																	href="?state=${mapData.state }&range=1-30000&city=${mapData.district}"
																	style="cursor: pointer;">
																	<td style="width: 1%;">${counter.count}</td>
																	<td style="width: 29%;">${mapData.district}</td>
																	<td style="width: 35%;">${mapData.state}</td>
																	<td style="width: 35%;">${mapData.block}</td>
																	<td style="width: 35%;">Allocated</td>

																</tr>
															</c:forEach>
														</c:forEach>
													</tbody>
												</table>
												</div>
											</div>
											<div class="col-md-4 col-sm-12 col-lg-4">

												<h4 style="text-align: center; font-size: 15px;">

													<b>Total Allocated Blocks</b>

												</h4>
												<table class="table cust-tbl">
													<tbody>

														<tr class="tbl-style ">
															<c:forEach items="${command.allocatedBlockMap}"
																var="mapData1">
																<td style="width: 50%;"
																	class="donutbar-2"></td>
															</c:forEach>
															<td><div>
																	<div>Total Blocks</div>
																	${command.totalBlock }
																</div>
																<div>
																	<div>Total Allocated Blocks
																	<c:forEach items="${command.allocatedBlockMap}"
																		var="mapData1">
																${mapData1.key }
																</c:forEach>
																</div>
																</div></td>
														</tr>

													</tbody>
												</table>

											</div>
										</div>
									</div>

								</div>
							</div>
					

						<div id="blockDiv" style="display: none;">
							
								<div class="row dist-summary">

									<div class="col-md-12 col-sm-12 col-lg-12">
										<h4>
											<span id="nameDis"></span>
										</h4>
									</div>
								</div>

								<div class="col-md-3 col-sm-3 col-lg-3"
									style="background: blueviolet; border: 1px dashed #003; margin-top: 6px; padding: 5px;">
									<div style="text-align: center; font-size: 160%;">
										<c:forEach items="${command.iaList}" var="mapData1">
											<font color="#fff">${mapData1.key }</font>
										</c:forEach>
									</div>
									<h4 class="metric-style">
										<font color="#fff">Total IA</font>
									</h4>
								</div>
								<div class="col-md-3 col-sm-3 col-lg-3"
									style="background: cornflowerblue; border: 1px dashed #003; margin-top: 6px; padding: 5px;">
									<div style="text-align: center; font-size: 160%;">
										<c:forEach items="${command.cbboList}" var="mapData1">
											<font color="#fff">${mapData1.key }</font>
										</c:forEach>
									</div>
									<h4 class="metric-style">
										<font color="#fff">Total CBBO</font>
									</h4>
								</div>
								<div class="col-md-3 col-sm-3 col-lg-3"
									style="background: coral; border: 1px dashed #003; margin-top: 6px; padding: 5px;">
									<div style="text-align: center; font-size: 160%;">
										<c:forEach items="${command.fpoList}" var="mapData1">
											<font color="#fff">${mapData1.key }</font>
										</c:forEach>
									</div>
									<h4 class="metric-style">
										<font color="#fff">Total FPO</font>
									</h4>
								</div>
								<div class="col-md-3 col-sm-3 col-lg-3"
									style="background: yellowgreen; border: 1px dashed #003; margin-top: 6px; padding: 5px;">
									<div style="text-align: center; font-size: 160%;">
										<font color="#fff">0</font>
									</div>
									<h4 class="metric-style">
										<font color="#fff">Total Farmers</font>
									</h4>
								</div>
								<div class="col-md-3 col-sm-3 col-lg-3"
									style="background: darkblue; border: 1px dashed #003; margin-top: 6px; padding: 5px;">
									<div style="text-align: center; font-size: 160%;">
										<font color="#fff">${command.totalBlockDist }</font>
									</div>
									<h4 class="metric-style">
										<font color="#fff">Total Block Covered</font>
									</h4>
								</div>
								<div class="col-md-3 col-sm-3 col-lg-3"
									style="background: green; border: 1px dashed #003; margin-top: 6px; padding: 5px;">
									<div style="text-align: center; font-size: 160%;">
										<c:forEach items="${command.vacantBlockMap}" var="mapData1">
											<font color="#fff">${mapData1.key }</font>
										</c:forEach>
									</div>
									<h4 class="metric-style">
										<font color="#fff">Total Vacant Block</font>
									</h4>
								</div>
								<div class="col-md-3 col-sm-3 col-lg-3"
									style="background: orange; border: 1px dashed #003; margin-top: 6px; padding: 5px;">
									<div style="text-align: center; font-size: 160%;">
										<c:forEach items="${command.allocatedBlockMap}" var="mapData1">
											<font color="#fff">${mapData1.key }</font>
										</c:forEach>

									</div>
									<h4 class="metric-style">
										<font color="#fff">Total Allocated Block</font>
									</h4>
								</div>
								<div style="clear: both;"></div>



								<div style="margin-top: 5px;">
									<div class="row dist-summary">
										<div class="col-md-12 col-sm-12 col-lg-12">
											<h4>IA List Associated with Selected District</h4>
										</div>
									</div>
									<table
										class="table table-bordered table-striped contact-details-table"
										id="iaList">
										<tbody>
											<c:forEach items="${command.iaList}" var="mapData1">
												<c:forEach items="${mapData1.value}" var="mapData2">

													<tr>
														<td>${mapData2}</td>
													</tr>
												</c:forEach>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<div>
									<div class="row dist-summary">
										<div class="col-md-12 col-sm-12 col-lg-12">
											<h4>CBBO List Associated with Selected District</h4>
										</div>
									</div>
									<table
										class="table table-bordered table-striped contact-details-table"
										id="cbboList">
										<tbody>
											<c:forEach items="${command.cbboList}" var="mapData1">
												<c:forEach items="${mapData1.value}" var="mapData2">
													<tr>
														<td>${mapData2}</td>
													</tr>
												</c:forEach>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<div>
									<div class="row dist-summary">
										<div class="col-md-12 col-sm-12 col-lg-12">
											<h4>FPO List Associated with Selected District</h4>
										</div>
									</div>
									<table
										class="table table-bordered table-striped contact-details-table"
										id="fpoList">
										<tbody>

											<c:forEach items="${command.fpoList}" var="mapData1">
												<c:forEach items="${mapData1.value}" var="mapData2">
													<tr>
														<td>${mapData2}</td>
													</tr>
												</c:forEach>
											</c:forEach>

										</tbody>
									</table>
								</div>
								<div>

									<div class="row dist-summary">
										<div class="col-md-12 col-sm-12 col-lg-12">

											<h4>Vacant Block List</h4>
										</div>
									</div>
									<table
										class="table table-bordered table-striped contact-details-table"
										id="bpInfoTable">
										<tbody>
											<c:forEach items="${command.vacantBlockMap}" var="mapData1">
												<c:forEach items="${mapData1.value}" var="mapData2">

													<tr>
														<td>${mapData2.block}</td>
													</tr>
												</c:forEach>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<div>

									<div class="row dist-summary">
										<div class="col-md-12 col-sm-12 col-lg-12">

											<h4>Allocated Block List</h4>
										</div>
									</div>
									<table
										class="table table-bordered table-striped contact-details-table"
										id="alList">
										<tbody>
											<c:forEach items="${command.allocatedBlockMap}"
												var="mapData1">
												<c:forEach items="${mapData1.value}" var="mapData2">

													<tr>
														<td>${mapData2.block}</td>
													</tr>
												</c:forEach>
											</c:forEach>
										</tbody>
									</table>
								</div>

							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	/*  var data_api1 = [{"DISTRICT": "NASHIK", "STATE": "MAHARASHTRA","BLOCK":"TEST", "CATEGORY": "ALLOCATED", "LATITUDE": 16.3492, "LONGITUDE": 73.5594, "RANK": 1, "COLOR": "#3AB54B", "RANGE": "1-30000"}];;;;
	 */

	// var data_api = [{"district": "NASHIK","stateId":10246, "state": "MAHARASHTRA","BLOCK":"TEST", "CATEGORY ":"VACANT", "LATITUDE": "16.3492", "LONGITUDE": "73.5594", "RANK": "1", "COLOR": "#3AB54B", "RANGE": "1-30000"}];
	
	 var data_api = $.parseJSON('${command.vacantJson}');
	var data_api1 = $.parseJSON('${command.alocatedJson}');
	
	var top_four_insights = [ {
		"METRIC" : "Good state of open defecation",
		"VACANT" : "0",
		"ALLOCATED" : "16",
		"CATEGORY" : "Top 4 parameters",
		"RANK" : 1,
		"COLOR" : "#756BB0",
		"SEC_COLOR" : "#BABDDE"
	} ]
	top_four_insights = _.sortBy(top_four_insights, function(o) {
		return o.RANK;
	})
	var deepdive = [ {} ]
	deepdive = _.sortBy(deepdive, function(e) {
		return e.RANK;
	})


	if ('${command.viewMode}' == "VACANT") {
		$('#allocated').tab('hide');
		$('#vacant').tab('show');
	}
	if ('${command.viewMode}' == "ALLOCATED") {
		$('#allocated').tab('show');
		$('#vacant').tab('hide');
	}

	var image_dict = {
		'assets/images/sikar' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/west_kameng' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '1.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/west_sikkim' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/champhai' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '104.png', '84.png',
				'43.png', '106.png', '14.png', '56.png', '73.png', '61.png',
				'50.png', '10.png', '60.png', '82.png', '99.png', '67.png',
				'27.png', '2.png', '46.png', '12.png', '7.png', '93.png',
				'47.png', '80.png', '89.png', '94.png', '86.png', '28.png',
				'64.png', '75.png', '39.png', '101.png', '79.png', '30.png',
				'77.png', '102.png', '34.png', '78.png', '41.png', '87.png',
				'49.png', '32.png', '22.png', '15.png', '38.png', '21.png',
				'9.png', '103.png', '71.png', '72.png', '58.png', '23.png',
				'55.png', '29.png', '16.png', '48.png', '70.png', '1.png',
				'74.png', '88.png', '95.png', '57.png', '5.png', '66.png',
				'81.png', '45.png', '92.png', '65.png', '76.png', '17.png',
				'59.png', '62.png', '100.png', '25.png', '40.png', '37.png',
				'3.png', '6.png', '90.png', '69.png', '105.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/north_sikkim' : [ '68.png', '54.png', '36.png',
				'18.png', '97.png', '26.png', '63.png', '20.png', '44.png',
				'98.png', '51.png', '96.png', '13.png', '11.png', '42.png',
				'35.png', '4.png', '31.png', '91.png', '52.png', '84.png',
				'43.png', '14.png', '56.png', '73.png', '61.png', '50.png',
				'10.png', '60.png', '82.png', '99.png', '67.png', '27.png',
				'2.png', '46.png', '12.png', '7.png', '93.png', '47.png',
				'80.png', '89.png', '94.png', '86.png', '28.png', '64.png',
				'75.png', '39.png', '101.png', '79.png', '30.png', '77.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/west_khasi_VACANT' : [ '68.png', '54.png', '36.png',
				'18.png', '97.png', '26.png', '63.png', '20.png', '44.png',
				'98.png', '51.png', '96.png', '13.png', '11.png', '42.png',
				'35.png', '4.png', '31.png', '91.png', '52.png', '84.png',
				'43.png', '14.png', '56.png', '73.png', '61.png', '50.png',
				'10.png', '60.png', '82.png', '99.png', '67.png', '27.png',
				'2.png', '46.png', '12.png', '7.png', '93.png', '47.png',
				'80.png', '89.png', '94.png', '86.png', '28.png', '64.png',
				'75.png', '39.png', '101.png', '79.png', '30.png', '77.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/tapi' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '1.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/bikaner' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/narmada' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/midnapur_east' : [ '68.png', '54.png', '36.png',
				'18.png', '97.png', '26.png', '63.png', '20.png', '44.png',
				'98.png', '51.png', '96.png', '13.png', '11.png', '42.png',
				'35.png', '4.png', '31.png', '91.png', '52.png', '84.png',
				'43.png', '14.png', '56.png', '73.png', '61.png', '50.png',
				'10.png', '60.png', '82.png', '99.png', '67.png', '27.png',
				'2.png', '46.png', '12.png', '7.png', '93.png', '47.png',
				'80.png', '89.png', '94.png', '86.png', '28.png', '64.png',
				'75.png', '39.png', '101.png', '79.png', '30.png', '77.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/south_sikkim' : [ '68.png', '54.png', '36.png',
				'18.png', '97.png', '26.png', '63.png', '20.png', '44.png',
				'98.png', '51.png', '96.png', '13.png', '11.png', '42.png',
				'35.png', '4.png', '31.png', '91.png', '52.png', '84.png',
				'43.png', '14.png', '56.png', '73.png', '61.png', '50.png',
				'10.png', '60.png', '82.png', '99.png', '67.png', '27.png',
				'2.png', '46.png', '12.png', '7.png', '93.png', '47.png',
				'80.png', '89.png', '94.png', '86.png', '28.png', '64.png',
				'75.png', '39.png', '101.png', '79.png', '30.png', '77.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/debagarh' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/jharsuguda' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/mandya' : [ '68.png', '54.png', '36.png', '18.png',
				'26.png', '63.png', '20.png', '44.png', '51.png', '13.png',
				'11.png', '42.png', '35.png', '4.png', '31.png', '52.png',
				'84.png', '43.png', '14.png', '56.png', '73.png', '61.png',
				'50.png', '10.png', '60.png', '82.png', '67.png', '27.png',
				'2.png', '46.png', '12.png', '7.png', '47.png', '80.png',
				'86.png', '28.png', '64.png', '75.png', '39.png', '79.png',
				'30.png', '77.png', '34.png', '78.png', '41.png', '49.png',
				'32.png', '22.png', '15.png', '38.png', '21.png', '9.png',
				'71.png', '72.png', '58.png', '23.png', '55.png', '29.png',
				'16.png', '48.png', '70.png', '1.png', '74.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '25.png', '40.png', '37.png',
				'3.png', '6.png', '69.png', '24.png', '85.png', '83.png',
				'33.png', '53.png', '8.png', '19.png' ],
		'assets/images/rajnandgaon' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/surat' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/shimla' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/pali' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/ganganagar' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/anand' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/ri_bhoi' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '1.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/coimbatore' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '104.png', '84.png',
				'43.png', '106.png', '14.png', '56.png', '73.png', '61.png',
				'50.png', '10.png', '60.png', '82.png', '99.png', '67.png',
				'27.png', '2.png', '46.png', '12.png', '7.png', '93.png',
				'47.png', '80.png', '89.png', '94.png', '86.png', '28.png',
				'64.png', '75.png', '39.png', '101.png', '79.png', '30.png',
				'77.png', '102.png', '34.png', '78.png', '41.png', '87.png',
				'49.png', '32.png', '22.png', '15.png', '38.png', '21.png',
				'9.png', '103.png', '71.png', '72.png', '58.png', '23.png',
				'55.png', '29.png', '16.png', '48.png', '70.png', '1.png',
				'74.png', '88.png', '95.png', '57.png', '5.png', '66.png',
				'81.png', '45.png', '92.png', '65.png', '76.png', '17.png',
				'59.png', '62.png', '100.png', '25.png', '40.png', '37.png',
				'3.png', '6.png', '90.png', '69.png', '105.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/east_garo_VACANT' : [ '68.png', '54.png', '36.png',
				'18.png', '97.png', '26.png', '63.png', '20.png', '44.png',
				'98.png', '51.png', '96.png', '13.png', '11.png', '42.png',
				'35.png', '4.png', '31.png', '91.png', '52.png', '84.png',
				'43.png', '14.png', '56.png', '73.png', '61.png', '50.png',
				'10.png', '60.png', '82.png', '99.png', '67.png', '27.png',
				'2.png', '46.png', '12.png', '7.png', '93.png', '47.png',
				'80.png', '89.png', '94.png', '86.png', '28.png', '64.png',
				'75.png', '39.png', '101.png', '79.png', '30.png', '77.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/perambaluru' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/kolhapur' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/satara' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/sirsa' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/sindhudurg' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/panchmahal' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/west_siang' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '1.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/thane' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/mahesana' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/nadia' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/hamirpur' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/kheda' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/jaintia_VACANT' : [ '68.png', '54.png', '36.png',
				'18.png', '26.png', '63.png', '20.png', '44.png', '51.png',
				'13.png', '11.png', '42.png', '35.png', '4.png', '31.png',
				'52.png', '43.png', '14.png', '56.png', '73.png', '61.png',
				'50.png', '10.png', '60.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '47.png', '28.png', '64.png',
				'75.png', '39.png', '30.png', '77.png', '34.png', '41.png',
				'49.png', '32.png', '22.png', '15.png', '38.png', '21.png',
				'9.png', '71.png', '72.png', '58.png', '23.png', '55.png',
				'29.png', '16.png', '48.png', '70.png', '1.png', '74.png',
				'57.png', '5.png', '66.png', '45.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '25.png', '40.png', '37.png',
				'3.png', '6.png', '69.png', '24.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/narsinghpur' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/east_khasi_VACANT' : [ '68.png', '54.png', '36.png',
				'18.png', '97.png', '26.png', '63.png', '20.png', '44.png',
				'98.png', '51.png', '96.png', '13.png', '11.png', '42.png',
				'35.png', '4.png', '31.png', '91.png', '52.png', '84.png',
				'43.png', '14.png', '56.png', '73.png', '61.png', '50.png',
				'10.png', '60.png', '82.png', '99.png', '67.png', '27.png',
				'2.png', '46.png', '12.png', '7.png', '93.png', '47.png',
				'80.png', '89.png', '94.png', '86.png', '28.png', '64.png',
				'75.png', '39.png', '101.png', '79.png', '30.png', '77.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/indore' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/udupi' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/gadag' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/hoogly' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/ahmedabad' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/ajmer' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/bharuch' : [ '18.png', '26.png', '20.png', '13.png',
				'11.png', '4.png', '31.png', '14.png', '10.png', '27.png',
				'2.png', '12.png', '7.png', '28.png', '30.png', '34.png',
				'32.png', '22.png', '15.png', '21.png', '9.png', '23.png',
				'29.png', '16.png', '1.png', '5.png', '17.png', '25.png',
				'3.png', '6.png', '24.png', '33.png', '8.png', '19.png' ],
		'assets/images/chamba' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/lahul_&_spiti' : [ '68.png', '54.png', '36.png',
				'18.png', '97.png', '26.png', '63.png', '20.png', '44.png',
				'98.png', '51.png', '96.png', '13.png', '11.png', '42.png',
				'35.png', '4.png', '31.png', '91.png', '52.png', '84.png',
				'43.png', '14.png', '56.png', '73.png', '61.png', '50.png',
				'10.png', '60.png', '82.png', '99.png', '67.png', '27.png',
				'2.png', '46.png', '12.png', '7.png', '93.png', '47.png',
				'80.png', '89.png', '94.png', '86.png', '28.png', '64.png',
				'75.png', '39.png', '101.png', '79.png', '30.png', '77.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/gajapati' : [ '68.png', '54.png', '36.png', '18.png',
				'26.png', '63.png', '20.png', '44.png', '51.png', '13.png',
				'11.png', '42.png', '35.png', '4.png', '31.png', '91.png',
				'52.png', '84.png', '43.png', '14.png', '56.png', '73.png',
				'61.png', '50.png', '10.png', '60.png', '82.png', '67.png',
				'27.png', '2.png', '46.png', '12.png', '7.png', '47.png',
				'80.png', '89.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '79.png', '30.png', '77.png', '34.png', '78.png',
				'41.png', '87.png', '49.png', '32.png', '22.png', '15.png',
				'38.png', '21.png', '9.png', '71.png', '72.png', '58.png',
				'23.png', '55.png', '29.png', '16.png', '48.png', '70.png',
				'1.png', '74.png', '88.png', '57.png', '5.png', '66.png',
				'81.png', '45.png', '65.png', '76.png', '17.png', '59.png',
				'62.png', '25.png', '40.png', '37.png', '3.png', '6.png',
				'90.png', '69.png', '24.png', '85.png', '83.png', '33.png',
				'53.png', '8.png', '19.png' ],
		'assets/images/mandi' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/jhunjhunu' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '1.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/namakkal' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/kanyakumari' : [ '68.png', '54.png', '36.png', '18.png',
				'26.png', '63.png', '20.png', '44.png', '51.png', '13.png',
				'11.png', '42.png', '35.png', '4.png', '31.png', '52.png',
				'43.png', '14.png', '56.png', '73.png', '61.png', '50.png',
				'10.png', '60.png', '67.png', '27.png', '2.png', '46.png',
				'12.png', '7.png', '47.png', '28.png', '64.png', '75.png',
				'39.png', '30.png', '77.png', '34.png', '41.png', '49.png',
				'32.png', '22.png', '15.png', '38.png', '21.png', '9.png',
				'71.png', '72.png', '58.png', '23.png', '55.png', '29.png',
				'16.png', '48.png', '70.png', '1.png', '74.png', '57.png',
				'5.png', '66.png', '45.png', '65.png', '76.png', '17.png',
				'59.png', '62.png', '25.png', '40.png', '37.png', '3.png',
				'6.png', '69.png', '24.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/dungarpur' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '1.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/yamunanagar' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/changlang' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '1.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/east_sikkim' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/bongaigaon' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '104.png', '84.png',
				'43.png', '106.png', '14.png', '56.png', '73.png', '61.png',
				'50.png', '10.png', '60.png', '82.png', '99.png', '67.png',
				'27.png', '2.png', '46.png', '12.png', '7.png', '93.png',
				'47.png', '80.png', '89.png', '94.png', '86.png', '28.png',
				'64.png', '75.png', '39.png', '101.png', '79.png', '30.png',
				'77.png', '102.png', '34.png', '78.png', '41.png', '87.png',
				'49.png', '32.png', '22.png', '15.png', '38.png', '21.png',
				'9.png', '103.png', '71.png', '72.png', '58.png', '23.png',
				'55.png', '29.png', '16.png', '48.png', '70.png', '1.png',
				'74.png', '88.png', '95.png', '57.png', '5.png', '66.png',
				'81.png', '45.png', '92.png', '65.png', '76.png', '17.png',
				'59.png', '62.png', '100.png', '25.png', '40.png', '37.png',
				'3.png', '6.png', '90.png', '69.png', '105.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/navsari' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/dhamtari' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '1.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/kodagu' : [ '68.png', '54.png', '36.png', '18.png',
				'26.png', '63.png', '20.png', '44.png', '51.png', '13.png',
				'11.png', '42.png', '35.png', '4.png', '31.png', '91.png',
				'52.png', '84.png', '43.png', '14.png', '56.png', '73.png',
				'61.png', '50.png', '10.png', '60.png', '82.png', '67.png',
				'27.png', '2.png', '46.png', '12.png', '7.png', '47.png',
				'80.png', '89.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '79.png', '30.png', '77.png', '34.png', '78.png',
				'41.png', '87.png', '49.png', '32.png', '22.png', '15.png',
				'38.png', '21.png', '9.png', '71.png', '72.png', '58.png',
				'23.png', '55.png', '29.png', '16.png', '48.png', '70.png',
				'1.png', '74.png', '88.png', '57.png', '5.png', '66.png',
				'81.png', '45.png', '92.png', '65.png', '76.png', '17.png',
				'59.png', '62.png', '25.png', '40.png', '37.png', '3.png',
				'6.png', '90.png', '69.png', '24.png', '85.png', '83.png',
				'33.png', '53.png', '8.png', '19.png' ],
		'assets/images/churu' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/sehore' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/thoothukudi' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '1.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/gandhinagar' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '102.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '1.png', '74.png', '88.png', '95.png',
				'57.png', '5.png', '66.png', '81.png', '45.png', '92.png',
				'65.png', '76.png', '17.png', '59.png', '62.png', '100.png',
				'25.png', '40.png', '37.png', '3.png', '6.png', '90.png',
				'69.png', '24.png', '85.png', '83.png', '33.png', '53.png',
				'8.png', '19.png' ],
		'assets/images/ramanagara' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '1.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/ratnagiri' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/bengaluru_rural' : [ '68.png', '54.png', '36.png',
				'18.png', '97.png', '26.png', '63.png', '20.png', '44.png',
				'98.png', '51.png', '96.png', '13.png', '11.png', '42.png',
				'35.png', '4.png', '31.png', '91.png', '52.png', '84.png',
				'43.png', '14.png', '56.png', '73.png', '61.png', '50.png',
				'10.png', '60.png', '82.png', '99.png', '67.png', '27.png',
				'2.png', '46.png', '12.png', '7.png', '93.png', '47.png',
				'80.png', '89.png', '94.png', '86.png', '28.png', '64.png',
				'75.png', '39.png', '101.png', '79.png', '30.png', '77.png',
				'34.png', '78.png', '41.png', '87.png', '49.png', '32.png',
				'22.png', '15.png', '38.png', '21.png', '9.png', '71.png',
				'72.png', '58.png', '23.png', '55.png', '29.png', '16.png',
				'48.png', '70.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/papum_pare' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '1.png', '74.png', '88.png', '95.png', '57.png',
				'5.png', '66.png', '81.png', '45.png', '92.png', '65.png',
				'76.png', '17.png', '59.png', '62.png', '100.png', '25.png',
				'40.png', '37.png', '3.png', '6.png', '90.png', '69.png',
				'24.png', '85.png', '83.png', '33.png', '53.png', '8.png',
				'19.png' ],
		'assets/images/north_24_pargana' : [ '68.png', '54.png', '36.png',
				'18.png', '97.png', '26.png', '63.png', '20.png', '44.png',
				'98.png', '51.png', '96.png', '13.png', '11.png', '42.png',
				'35.png', '4.png', '31.png', '91.png', '52.png', '84.png',
				'43.png', '14.png', '56.png', '73.png', '61.png', '50.png',
				'10.png', '60.png', '82.png', '99.png', '67.png', '27.png',
				'2.png', '46.png', '12.png', '7.png', '93.png', '47.png',
				'80.png', '89.png', '94.png', '86.png', '28.png', '64.png',
				'75.png', '39.png', '101.png', '79.png', '30.png', '77.png',
				'102.png', '34.png', '78.png', '41.png', '87.png', '49.png',
				'32.png', '22.png', '15.png', '38.png', '21.png', '9.png',
				'71.png', '72.png', '58.png', '23.png', '55.png', '29.png',
				'16.png', '48.png', '70.png', '1.png', '74.png', '88.png',
				'95.png', '57.png', '5.png', '66.png', '81.png', '45.png',
				'92.png', '65.png', '76.png', '17.png', '59.png', '62.png',
				'100.png', '25.png', '40.png', '37.png', '3.png', '6.png',
				'90.png', '69.png', '24.png', '85.png', '83.png', '33.png',
				'53.png', '8.png', '19.png' ],
		'assets/images/valsad' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/koriya' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/anjaw' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/kullu' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/patan' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ],
		'assets/images/gurgaon' : [ '68.png', '54.png', '36.png', '18.png',
				'97.png', '26.png', '63.png', '20.png', '44.png', '98.png',
				'51.png', '96.png', '13.png', '11.png', '42.png', '35.png',
				'4.png', '31.png', '91.png', '52.png', '84.png', '43.png',
				'14.png', '56.png', '73.png', '61.png', '50.png', '10.png',
				'60.png', '82.png', '99.png', '67.png', '27.png', '2.png',
				'46.png', '12.png', '7.png', '93.png', '47.png', '80.png',
				'89.png', '94.png', '86.png', '28.png', '64.png', '75.png',
				'39.png', '101.png', '79.png', '30.png', '77.png', '34.png',
				'78.png', '41.png', '87.png', '49.png', '32.png', '22.png',
				'15.png', '38.png', '21.png', '9.png', '71.png', '72.png',
				'58.png', '23.png', '55.png', '29.png', '16.png', '48.png',
				'70.png', '74.png', '88.png', '95.png', '57.png', '5.png',
				'66.png', '81.png', '45.png', '92.png', '65.png', '76.png',
				'17.png', '59.png', '62.png', '100.png', '25.png', '40.png',
				'37.png', '3.png', '6.png', '90.png', '69.png', '24.png',
				'85.png', '83.png', '33.png', '53.png', '8.png', '19.png' ]
	};
	
	
	
</script>
<script src="assets/libs/indiaMap/main.js"></script>

