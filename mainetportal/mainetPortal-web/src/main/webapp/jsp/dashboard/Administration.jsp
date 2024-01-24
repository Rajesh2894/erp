<%	response.setContentType("text/html; charset=utf-8"); %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<ol class="breadcrumb">
	<li class="breadcrumb-item"><a href="CitizenHome.html" title="Home"><i
			class="fa fa-home" aria-hidden="true"></i> Home</a></li>
	<li class="breadcrumb-item"><a href="dashboard.html"
		title="Dashboard">Dashboard</a></li>
	<li class="breadcrumb-item active">Administration</li>

</ol>



<div class="container-fluid dashboard-page">
	<div class="container-fluid">
		<div class="col-sm-12" id="nischay">



			
<div class="dashboard-items">
<ul class="nav nav-pills" role="tablist">
  <li role="presentation"><a href="DashBoard.html" class="red-nischay" title="7  Nischay">7  Nischay </a></li>
  <li role="presentation" class="active"><a href="Administration.html" title="Administration"> Administration and Finance </a></li>
  <li role="presentation"><a href="HumanResource.html" title="Human Resource Department">HRD</a></li>
  <li role="presentation"><a href="Infrastructure.html" title="Infrastructure"> Infrastructure</a></li>
  <li role="presentation"><a href="Agriculture.html" title="Agriculture &amp; Allied"> Agriculture &amp; Allied </a></li>
  <li role="presentation"><a href="Welfare.html" title="Welfare">Welfare</a></li>
  <li role="presentation"><a href="ArtCulture.html" title="Art Culture and Tourism"> Art Culture and Tourism</a></li>
 </ul>
</div>







			<div id="schemes">
				<div class="container">

					<div class="row ">




						<div class="col-sm-3">
							<div class="item">
								<a href="javascript:void(0);">
									<div class="box lightblue-nischay">
										<div align="center">
											<i class="fa fa-university" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Parliament Affairs</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>


						<div class="col-sm-3">
							<div class="item">
								<a href="javascript:void(0);">
									<div class="box lightyellow-nischay">
										<div align="center">
											<i class="fa fa-building" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Cabinet</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="item">
								<a href="javascript:void(0);">
									<div class="box darkblue-nischay">
										<div align="center">
											<i class="fa fa-user-secret" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>General Administration</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="item">
								<a href="Administration.html?page&scheme=HomeDepartment">
									<div class="box yellow-nischay">
										<div align="center">
											<i class="fa fa-home" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Home</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>



					</div>
					<div class="row ">


						<div class="col-sm-3">
							<div class="item">
								<a href="javascript:void(0);">
									<div class="box gray-nischay">
										<div align="center">
											<i class="fa fa-balance-scale" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Law</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="item">
								<a href="Administration.html?page&scheme=DisasterManagement">
									<div class="box red-nischay">
										<div align="center">
											<i class="fa fa-fire" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Disaster</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="item">
								<a href="Administration.html?page&scheme=PlanningDepartment">
									<div class="box orange-nischay">
										<div align="center">
											<i class="fa fa-cubes" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Planning</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>


						<div class="col-sm-3">
							<div class="item">
								<a href="javascript:void(0);">
									<div class="box brinjal-nischay">
										<div align="center">
											<i class="fa fa-eye" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Vigilance</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>
					</div>

					<div class="row ">
						<div class="col-sm-3">
							<div class="item">
								<a href="javascript:void(0);">
									<div class="box lightblue-nischay">
										<div align="center">
											<i class="fa fa-users" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Information &amp; Public Relation</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="item">
								<a href="Administration.html?page&scheme=RevenueDepartment">
									<div class="box brown-nischay">
										<div align="center">
											<i class="fa fa-money" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Revenue & Land Reform</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>


						<div class="col-sm-3">
							<div class="item">
								<a href="javascript:void(0);">
									<div class="box darkblue-nischay">
										<div align="center">
											<i class="fa fa-inr" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Finance</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="item">
								<a href="javascript:void(0);">
									<div class="box lightyellow-nischay">
										<div align="center">
											<i class="fa fa-file-text" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Commercial Taxes</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>
					</div>
					<div class="row ">
						<div class="col-sm-3">
							<div class="item">
								<a href="javascript:void(0);">
									<div class="box red-nischay">
										<div align="center">
											<i class="fa fa-registered" aria-hidden="true"></i>
										</div>

										<div class="header">
											<h1>Registration Excise & Prohibition</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="item">
								<a href="javascript:void(0);">
									<div class="box darkred-nischay">
										<div align="center">
											<i class="fa fa-bar-chart" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Excise & Prohibition</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="item">
								<a href="javascript:void(0);">
									<div class="box gray-nischay">
										<div align="center">
											<i class="fa fa-truck" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Transport</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="item">
								<a href="javascript:void(0);">
									<div class="box brown-nischay">
										<div align="center">
											<i class="fa fa-globe" aria-hidden="true"></i>
										</div>
										<div class="header">
											<h1>Mines & Geology</h1>
										</div>
										<div class="progressbar">


											<div class="row">
												<div class="col-sm-6">
													<span data-count="10%">10 %</span>
													<h1>Physical</h1>
												</div>
												<div class="col-sm-6">
													<span data-count="30%">9 %</span>
													<h1>Financial</h1>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</div>


					</div>




				</div>
			</div>
		</div>


	</div>
</div>

