<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/account/depositeAndAdvncHeadMappingMaster.js"></script>

<spring:hasBindErrors
	name="accountDepositeAndAdvnHeadsMappingEntryMasterBean">
</spring:hasBindErrors>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="deposite.advn.head.label"
					text="Deposite And Advance Mapping Labels" />
			</h2>
		<apptags:helpDoc url="AccountDepositeAndAdvnHeadsMappingEntryMaster.html" helpDocRefURL="AccountDepositeAndAdvnHeadsMappingEntryMaster.html"></apptags:helpDoc>		
		</div>




		<div class="widget-content padding">

			<form:form method="post" action="" class="form-horizontal"
				modelAttribute="accountDepositeAndAdvnHeadsMappingEntryMasterBean">



				<div class="warning-div alert alert-danger alert-dismissible hide "
					id="errorDiv">
					<button type="button" class="close" aria-label="Close"
						onclick="closeErrBox()">
						<span aria-hidden="true">&times;</span>
					</button>

					<ul>
						<li><i class='fa fa-exclamation-circle'></i>&nbsp;<form:errors
								path="*" /></li>
					</ul>
					<script>
					$(".warning-div ul").each(function () {
					    var lines = $(this).html().split("<br>");
					    $(this).html('<li>' + lines.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;") + '</li>');						
					});
		  			$('html,body').animate({ scrollTop: 0 }, 'slow');
		  		</script>
				</div>


				<div class="form-group">
					<label class="label-control col-sm-2 required-control"><spring:message
							code="" text="Mapping Type" /> </label>
					<div class="col-sm-4">
						<form:select path="mappingType" class="form-control"
							id="mappintType" onchange="populateFileds()">
							<form:option value="0" id="">
								<spring:message code="accounts.master.selLevel" />
							</form:option>
							<c:forEach items="${getLevels}" varStatus="status" var="level">
								<form:option value="${level.lookUpId}"
									code="${level.lookUpCode}">${level.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>


					<div class="label-control" id="advancedHead">
						<label class="label-control col-sm-2 required-control"><spring:message
								code="" text="Advanced Type" /> </label>
						<div class="col-sm-4">
							<form:select path="advancedType" class="form-control"
								id="advancedType" onchange="populateFileds()">
								<form:option value="0" id="">
									<spring:message code="accounts.master.selLevel" />
								</form:option>
								<c:forEach items="${advancedTypeLevel}" varStatus="status"
									var="level">
									<form:option value="${level.lookUpId}"
										code="${level.lookUpCode}">${level.descLangFirst}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</div>

				<ul id="depositeHead">
					<li>
						<div class="form-group">

							<label class="label-control col-sm-2 required-control"><spring:message
									code="" text="Deposite Type" /> </label>
							<div class="col-sm-4">
								<form:select path="depositeType" class="form-control"
									id="depositeType">
									<form:option value="0" id="">
										<spring:message code="accounts.master.selLevel" />
									</form:option>
									<c:forEach items="${depositTypeLevel}" varStatus="status"
										var="level">
										<form:option value="${level.lookUpId}"
											code="${level.lookUpCode}">${level.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>


							<label class="label-control col-sm-2 required-control"><spring:message
									code="" text="Department" /></label>
							<div class="col-sm-4">
								<form:select path="deptId" class="form-control" id="deptId">
									<form:option value="0" id="">
										<spring:message code="accounts.master.selLevel" />
									</form:option>
									<c:forEach items="${departmentlist}" var="departMstData">
										<option value="${departMstData.dpDeptid}">${departMstData.dpDeptdesc}</option>
									</c:forEach>
								</form:select>
							</div>


						</div>
					</li>
				</ul>


				<div class="text-center padding-bottom-10">
					<a href="javascript:void(0);" onclick="searchForData(this)"
						class="btn btn-success" id="btnsearch"><i class="fa fa-search"></i>&nbsp;<spring:message
							code="" text="Search" /></a>
					<spring:url var="cancelButtonURL"
						value="AccountDepositeAndAdvnHeadsMappingEntryMaster.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="account.bankmaster.reset" text="Reset" /></a>
					<button type="button" class="btn btn-blue-2 createData"
						onclick="openEntryForm()">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.create" text="Create" />
					</button>
					<a href="javascript:void(0);" onclick="editData(this)"
						class="btn btn-success" id="btnsearch"><i
						class="fa fa-plus-circle"></i>&nbsp;<spring:message code=""
							text="Edit" /></a>
				</div>

				<table id="grid"></table>
				<div id="pagered"></div>

			</form:form>
		</div>
	</div>
</div>
