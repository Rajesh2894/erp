
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="css/report.css" rel="stylesheet" type="text/css">
<link href="css/kendo.common-material.min.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="css/kendo.material.min.css" type="text/css" />
<script type="text/javascript" src="js/mainet/ui/kendo.all.min.js"></script>
<script type="text/javascript" src="js/mainet/ui/jszip.min.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/works_management/tenderinitiation.js"></script>
<script type="text/javascript"
	src="js/works_management/reports/form-B.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">

	<!-- Start Widget Content -->
	<div class="widget-content padding">
		<header align="center">
			<button class="button" onclick="ExportPdf()">Download as
				.pdf</button>
			<button class="button" onclick="exportHTML();">Download as
				.doc</button>
			<button class="button" onclick="backTenderPage();">
				<i class="fa fa-chevron-circle-left padding-right-5"></i>
				<spring:message code="works.management.back" text="" />
			</button>
		</header>
		<!-- Scroll to Top button -->
		<!--<button onclick="topFunction()" id="myBtn" title="Go to top">Top</button> -->
		<!-- Start Form -->
		<form:form action="TenderInitiation.html" class="form-horizontal"
			name="TenderInitiation" id="TenderInitiation"
			modelAttribute="command">

			<!-- Start Validation include tag -->
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div
				class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDiv" style="display: none;"></div>

			<!-- End Validation include tag -->


			<div class="loader"></div>
			<div class="report" id="myCanvas">

				<!---  01 Page Start Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h1>
							<b>FORM 'B'<br /> <b>${userSession.getCurrent().organisation.ONlsOrgname}</b><br />PUBLIC
								WORKS DEPARTMENT<br />ITEM RATE TENDER AND CONTRACT FOR WORKS
							</b>
						</h1>
						<p>Issued to Shri/M/s.
							......................................................................................................................</p>
						<p>Class of Contractor. ..................E-Registration
							No................................Date...............................</p>
						<p>
							Name of Work : <b>${command.tenderWorksForms.workName}</b>
						</p>
						<p>
							Amount of Contract : <b>Rs.
								${command.tenderWorksForms.workEstimateAmt}</b>
						</p>
						<p>
							Amount of EMD/FDR Rs. <b>Rs.
								${command.tenderWorksForms.tenderSecAmt} </b>
						</p>
						<p>
							Cost of Tender Form <b>Rs.
								${command.tenderWorksForms.tenderFeeAmt} </b>
						</p>
						<p>Vide M.R. No. & Date
							...................................................................................................................</p>
						<p>
							Time allowed for Completion <b>${command.tenderWorksForms.vendorWorkPeriod}</b>
							Months from the reckoned date including/Excluding <br />&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;rainy
							season (16th June to 15th October)
						</p>
						<p>Date of opening Tender
							................................................................................................................</p>
						<p>
							<b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							.........................................................
						</p>
						<p>
						<h2>
							<b>General Rules and Directions for the Guidance of
								Contractors</b>
						</h2>
						</p>
						<p>1. All works proposed for execution by contract will be
							notified in a form of invitation to tender posted in public
							places and signed by the authority inviting the tenders. This
							form will state the work to be carried out as well as the date
							for submitting and opening tenders and the time allowed for
							carrying out the work, also the amount of the earnest money to be
							deposited with the tender and the amount of security deposit to
							be deposited by the successful tenderer and the percentage if
							any. to be deducted from bills. Copies of specifications,
							drawings and a Schedule of quantities and rates of the various
							descriptions of work and any other documents required in
							connection with the work, signed for the purpose of
							identification by the authority competent to approve the tender
							shall also be open for inspection by the contractor at the office
							of the authority inviting the tenders. during office hours.</p>
						<p>2. In the event of the tender being submitted by a firm, it
							must be signed separately by each member thereof, or in the event
							of the absence of any partner it must be signed on his behalf by
							a person holding a power of attorney authorizing him to do so.
							Such power of attorney should be produced with the tender and it
							must disclose whether the firm is duly registered under the
							Indian Partnership Act.</p>
						<p>3. Any person who submits a tender shall fill up the usual
							printed form stating at what rate he is willing to under take
							each item of work. Tenders which propose any alteration in the
							work specified in the said form of invitation to tender or fn the
							time allowed for carrying out the work or which contain any other
							conditions of any sort will be liable to rejection, unless there
							is specific provision in the conditions of the Notice Inviting
							Tenders e.g in three cover system.</p>

					</div>
				</div>
				<!---  01 Page Ends Here --->

				<!---  02 Page Start Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>No single tender shall include more than one work but
							contractors who wish to tender for two or more works shall submit
							a separate tender for each. Tender shall have the name and number
							of the work to which they refer, written outside the envelope.</p>
						<p>4. The authority receiving tenders or his duly authorised
							assistant, will open tenders in the presence of any attending
							contractors or his authorized representative, who may be present
							at the time and will enter the amounts of the several tenders in
							a comparative statement in a suitable form. Receipts for earnest
							money will be given to all tenderers except those tenders which
							are rejected and whose earnest money is refunded on the day the
							tenders are opened.</p>
						<p>5. The Authority competent to decide of the- tenders shall
							have the right of rejecting all or any of the tenders. With out
							assigning any reason thereof.</p>
						<p>6. The receipt of a clerk for any money paid by the
							contractor will not be considered as any acknowledgement of
							payment to the Commissioner/CMO authority and the contractor
							shall be responsible for seeing that he procures a receipt signed
							by that authority or any other person duly authorised by him.</p>
						<p>
							7. The memorandum of work tendered for, and the schedule of
							materials to be supplied by the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							and their issue rates shall be filled in and completed before the
							tender form is issued. If a form is issued to an intending
							tenderer without having been so filled in and completed, he shall
							request the office to have this done before he completes and
							delivers his tender.
						</p>
						<p>
						<h2>
							<b>TENDER FOR WORK</b>
						</h2>
						</p>
						<p>
							I/We hereby tender for the execution to the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							of the works specified by in the under written memorandum within
							the time specified in such memorandum at the rates specified
							therein and in accordance in all respect with the specifications,
							designs, drawings, and instructions in writing referred to in
							rule I hereof and in clause 12 of the annexed conditions, and
							with such materials as are provided for by and in all other
							respects in accordance with such conditions so for applicable.
						</p>
						<p>
						<h2>
							<b>MEMORANDUM</b>
						</h2>
						<p>
						<ul>
							<li>(a) Name of work :- <b>${command.tenderWorksForms.workName}</b></li>
							<li>(b) Probable amount of Contract Rs. <b>${command.tenderWorksForms.workEstimateAmt}</b></li>
							<li>(c) Earnest money <b>Rs.${command.tenderWorksForms.tenderSecAmt}</b></li>
							<li>(d) Security deposit
								...................................................................................................................</li>
							<li>(e) Percentage if any to be deducted from
								bills(Performance guarantee) .............................</li>
							<li>(f ) Time allowed for the work .......................
								from the reckoned date including / excluding rainy season <br />&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;(
								from 16th June to 15th October) (deleted which ever is not
								necessary).
							</li>
						</ul>
					</div>
				</div>
				<!---  02 Page Start Here --->
				<!---  03 Page Start Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
						<h2>
							<b>=SCHEDULE OF ITEMS =</b>
						</h2>
						</p>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Sl.No.</th>
									<th>Reference to Item No. of S.O.R. (If any)</th>
									<th>Description of Item of work</th>
									<th>Quantity</th>
									<th>Unit</th>
									<th>Rate in figure per unit</th>
									<th>Rate in words per unit</th>
									<th>Rate in words per unit</th>
									<th>Remarks</th>
								</tr>
								<tr>
									<th>1</th>
									<th>2</th>
									<th>3</th>
									<th>4</th>
									<th>5</th>
									<th>6</th>
									<th>7</th>
									<th>8</th>
									<th>9</th>
								</tr>
							<thead>
								<tr>
									<td>1</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>2</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>3 etc.</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td colspan="8" style="">Grand Total of column No. (8) =
										Rs. ................................................ <br />(in
										figure and in words)
										..................................................................
									</td>
								</tr>
						</table>
						<p>
						<h2>
							<b>(SEE DETAILS IN "SCHEDULE OF ITEMS" - ANNEXURE "E"
								enclosed</b>
						</h2>
						</p>
						<p>
							Should this tender be accepted I/we hereby agree to abide by and
							fulfill all terms and provisions of the said conditions of the
							contract annexed hereto as far as applicable or in default,
							thereof to forfeit & pay to the Commissioner/CMO of <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							or his successors in office the sums of money mentioned in the
							said condition. A separate sealed cover duly super scribed
							containing the sum of Rs. ........................ as earnest
							money the full value of which is to be absolutely forfeited to
							the said Commissioner/CMO of <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							or his successors in office without prejudice to any other rights
							or remedies of the said Commissioner/CMO of <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							or his successors in office should I/we fail to commence the work
							specified in the above memorandum or should I/we not deposit the
							full amount of security deposit specified in the above
							memorandum, in accordance with clause 1 of the said conditions of
							the contract, otherwise the said sum of Rs.
							.......................... shall be retained by <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							on account of such security deposit as aforesaid or the full
							value of which shall be retained by <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							on account of the security deposit specified in clause 1 of the
							said conditions of the contract.
						</p>
						<br />
						<p>
						<table>
							<tr>
								<td>Signature of witness to Contractor's Signature <br />
									<br /> <br />Dated the
									&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;day of <br /> <br />............................................20
								</td>&emsp;
								<td></td>
								<td>&emsp;</td>
								<td>&emsp;</td>
								<td>&emsp;</td>
								<td>Signature of the Contractor before <br />submission of
									tender <br /> <br />Dated the
									&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; day of <br /> <br />............................................20
								</td>
							</tr>
						</table>
						</p>
						<p>Address of the witness:
							............................................................................................................................</p>
						<p>Occupation of the witness:
							.......................................................................................................................</p>
					</div>
				</div>
				<!---  03 Page Ends Here --->
				<!---  04 Page Start Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							The above tender is hereby accepted by me for and on behalf of
							the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
						</p>
						<p>
							Dated the ..................................
							&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
							20 &emsp;&emsp; day of .............................. 20 <br />&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;...........................................................
						</p>
						<p>(If several sub works are included, they should be detailed
							in a separate list.)</p>
						<br /> <br />
						<p class="pull-right" style="text-align: center;">
							Signature of the Officer by whom accepted <br />(Designation
							with seal of office)
						</p>
						<br /> <br /> <br />
						<h2>
							<b>Conditions of Contract<br /> <br /> Definition
							</b>
						</h2>
						<br />
						<p>
							1. The contract means the documents, forming the notice inviting
							tenders and tender documents submitted by the tenderer and the
							acceptance thereof including the formal agreement executed
							between the Commissioner/CMO on behalf of <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							and the contractor.
						</p>
						<p>2. In the contract the following expressions shall, unless
							otherwise required by the context, have the meanings hereby
							respectively assigned to them: -</p>
						<p>(a) The expression "works" or "work" shall, unless thereby
							mean something either in the subject or context repugnant to such
							construction, be construed and taken to mean the works or by
							virtue of the contract contracted to be executed whether
							temporary or permanent and whether original, altered, substituted
							or additional.</p>
						<p>(b) The "site" shall mean the land and/or other places on,
							into or through which work is to be executed under the contract
							or any adjacent land path or street through which work is to be
							executed under the contract (or any adjacent land, path, or
							street which maybe allotted or used for the purpose of carrying
							out the contract- subject to the condition that such adjacent
							land, path or street are available and specially permitted to to
							be used to the contractor)</p>
						<p>
							(c) The "Commissioner/CMO" means Commissioner/Chief Municipal
							officer of The <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							and his successors in Office.
						</p>
						<p>(d) The "Commissioner/CMO/Engineer as the case may be who
							shall sign the contract, Supervise and be in charge of the work.</p>
						<p>(e) "Competent Authority mean Commissioner/CMO, MIC/PIC,
							General Body/Parishad as the case may be.</p>
						<p>
							<b>Note: -</b> "Words" importing the singular number include
							plural number and vice-versa,
						</p>
						<p>
							<b>Clause 1 - SECURITY DEPOSIT-:</b> The person whose tender may
							be accepted (hereinafter called the contractor which expression
							shall unless excluded by or repugnant to the context include his
							heirs executers, administrators representatives and assigns)
							shall permit <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							at the time of making any payments to him for the value of work
							done under the contract to deduct the security deposit as under.
							The Security Deposit to be taken for the due performance of the
							contract under the terms & conditions printed on the tender form
							will be the earnest money plus a deduction of 5 percent from the
							payment made in the running bills.
						</p>
					</div>
				</div>
				<!---  04 Page Ends Here --->
				<!---  05 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>Clause 2 - COMPENSATION FOR DELAY -:</b>
						</p>
						<p>The time allowed for carrying out the work, as entered in
							the tender form, shall be strictly observed by the contractor and
							shall be deemed to be the essence of the contract and shall be
							reckoned from the fifteenth day after the date on which the order
							to commence the work is issued to the contractor, for a work
							where completion is up to 6 months</p>
						<p>
							<b>For works, for which the completion period is beyond six
								months: -</b>
						</p>
						<p>The period will be reckoned from the thirtieth day after
							the date on which the order to commence the work is issued to
							contractor .The work shall throughout the stipulated period of
							contract be proceeded with all due diligence, keeping in view
							that time is the essence of the contract.</p>
						<p>The contractor shall be bound in all cases, in which the
							time allowed for any work exceeds one month, to complete 1/8th of
							the whole work before 1/4th of the whole time allowed under the
							contract has elapsed, 3/8th of the work before 1/2 of such time
							has elapsed and 3/4th of the work before 3/4th of such time has
							elapsed. In the event of the contractor failing to comply with
							the above conditions, the Commissionor/CMO shall levy on the
							contractor, as compensation an amount equal to: 0.5% (zero point
							five percent) of the value of work (contract sum) for each week
							of delay, provided that the total amount of compensation under
							this provision of the clause shall be limited to 6% (six percent)
							of the value of work. (Contract sum)</p>
						<p>Provided further that if the contractor fails to achieve
							30% (thirty percent) progress in half (1/2) of original or
							validly extended period of time (reference clause 5 below) the
							contract shall stand terminated after due notice to the
							contractor and his contract finallised, with earnest money and or
							security deposit forfeited and levy of further compensation at
							the rate of 10% of the balance amount of contract left
							incomplete, either from the bill, and or from available
							security/performance guarantee or shall be recovered as "Arrears
							of land revenue".</p>
						<p>The decision of the Competent Authority in the matter of
							grant of extension of time only (reference clause 5 below) shall
							be final, binding and conclusive. But he has no right to change
							either the rate of compensation or reduce and or condone the
							period of delay- once such an order is passed by him (on each
							extension application of the contractor). It shall not be open
							for a revision.</p>
						<p>
							Where the Commissioner/CMO or Competent Authority or
							Commissioner/CMO as the case may decides that the contractor is
							liable to pay compensation for not giving proportionate progress
							under this clause and the compensation is recommended during the
							intermediate period, such compensation shall be kept in deposit
							and shall be refunded if the contractor subsequently makes up the
							progress for the lost time, within the period of contract
							including extension granted, if any. failing which the
							compensation amount shall be forfeited in favour of the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
						</p>
						<p>
							<b>Clause 3 -: Action when the work is left incomplete
								abandoned or delayed beyond the time limit permitted by the
								Commissioner/CMO or Competent Authority: -</b>
						</p>
						<p>(i) The Competent Authority may terminate the contract if
							the contractor causes a fundamental breach of the contract.</p>
						<p>(ii) Fundamental breach of contract shall include, but not
							be limited to, the following: -</p>
						<ul>
							<li>(a) The contractor stops work for four weeks, when no
								stoppage of work is shown on the current programme or the
								stoppage has not been authorised by the Commissioner/CMO.</li>
							<li>(b) The Commissioner/CMO gives notice that failure to
								correct a particular defect is a fundamental breach of contract
								and the contractor fails to correct it within reasonable period
								of time determined by the Commissioner/CMO in the said notice.</li>
							<li>(c) The contractor has delayed the completion of work by
								the number of weeks [12 (Twelve) weeks] for which the maximum
								amount of compensation of 6% of contract sum is exhausted.</li>
						</ul>
					</div>
				</div>
				<!---  05 Page Ends Here --->
				<!---  06 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<ul>
							<li>(d) If the contractor has not completed at least thirty
								percent of the value of construction work required to be
								completed in half of the completion period (Including validly
								extended period if any).</li>
							<li>(e) If the contractor fails to appoint the technical
								staff and if appointed do not function properly for 4 weeks even
								after due written notice by the Commissioner/CMO/Engineer in
								Charge as the case may be.</li>
							<li>(f) If he violates labour laws.</li>
							<li>(g) If the contractor fails to setup field laboratory
								with appropriate equipments with in 30 days from the reckoned
								date (*applicable for each contract valued more than rupees 3
								crores.)</li>
							<li>(h) Any other deficiency which goes to the root of the
								contract Performance</li>
						</ul>
						<p>(iii) If the contract is terminated, the contractor shall
							stop work immediately, make the site safe and secure and leave
							the site as soon as reasonably possible.</p>
						<p>(iv) The Commissioner/CMO shall cause recording and
							checking of measurements of all items of work done (taking in to
							account quality and quantity of items actually executed) and
							prepare the final bill after adjusting all pervious outstanding
							dues. Such recording of measurements shall be done after due
							notice regarding time and date of recording measurement and
							directing the contractor to either remain present himself or his
							authorised representative so as to satisfy himself that the
							recording of measurement is just and proper. Failure on his parts
							either to attend and or refusing to acknowledge the measurement
							so recorded in the department measurement book, shall be at his
							sole risk and responsibility.</p>
						<p>(v) In addition to the provision contained in clause 2
							above the Competent Authority/ Commissioner/CMO shall forfeit the
							earnest money and or security deposit and recover/deduct/adjust a
							compensation of 10% (ten percent) of the balance value of work
							left in complete either from the bill, and or from available
							security/performance guarantee or shall be recovered as "Arrears
							of land revenue"</p>
						<br />
						<h2>
							<b>Power to take possession of or require removal of
								Materials<br /> Tools and Plants or sale of Contractor's Plants
								etc.: -
							</b>
						</h2>
						<br />
						<p>
							<b>Clause 4:</b> In any case in which any of the powers,
							conferred upon the Commissioner/CMO by clause - 3 hereof shall
							have become exercisable and the same shall not be exercised, the
							nonexercise thereof shall not constitute a waiver of any of the
							conditions hereof and such powers shall notwithstanding be
							exercisable in the event of any future case of default by the
							contractor for which by any clause or clauses hereof he is
							declared liable to pay compensation shall remain unaffected. In
							the event of the Commissioner/CMO putting in force either of the
							power clause 3 vested in him under the preceding clause he may,
							if he so desires, take possession of all or any tools, plant
							materials, and stores in or upon the works, or the site thereof
							or belonging to the contractor or procured by him and intended to
							be used for the execution of the work or any part thereof paying
							or allowing for the same in account at the contract rates, or in
							case of these not being applicable, at current market rates to be
							certified by Commissioner, whose certificate thereof shall be
							final; otherwise the Commissioner may by notice in writing to the
							contractor or his clerk of the works foreman or authorised agent
							require him to remove such tools plant, materials or stores from
							the premises (within a time to be specified in such notice) and
							in the event of the contractor failing to comply with any such
							requisition, the CMO may remove them at the contractors expense
							sell them by auction or private sale on account of the contractor
							& at his risk in all respects and the certificate of the
							Commissioner/CMO as to the expense of any such removal and the
							amount of the proceeds and expense of any such sale shall be
							final and conclusive against the contractor.
						</p>
					</div>
				</div>
				<!---  06 Page Ends Here --->
				<!---  07 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>EXTENSION OF TIME:</b>
						</h2>
						<p>
							<b>Clause 5</b>
						</p>
						<p>5.1 - If the contractor shall desire an extension of time
							for completion of work on the ground of his having been
							"UNAVOIDABLY" hindered or on compensation events or on any other
							ground(s), he must apply giving all and complete details of such
							hindrance(s) or Compensation Events and or other cause(s) in
							writing, to the Commissioner/CMO positively within 15 days of
							occurrence of such hindrance(s) Compensation events other causes
							and seek specific extension of time (period from
							...................... to ..................... ).</p>
						<p>
							<b>&emsp;&emsp;<mark>In case the grounds shown by the
									contactor are reasonable, the Competent
									Authority/Commissioner/CMO shall be competent to grant the
									extension himself as under :-</mark></b>
						</p>
						<p>Once the Commissioner/CMO/Competent Authority has decided
							the case of extension of time with reference to the particular
							application of the contractor, it will not be competent for them
							to review/change such a decision later on. However, the Competent
							Authority and the Commissioner/CMO shall give the contractor an
							opportunity to be heard (orally and or in writing), before taking
							any final decision either of granting extension of time or
							permitting the contractor to complete the work by the delayed
							date (under clause 2 of the contact) or before refusing both.</p>
						<p>Provided further where the
							Commissioner/CMO/Commissioner/CMO has recommended grant of
							extension of particular time under clause 5.1 of the contract or
							has refused to recommend extension of time but has recommended
							permitting the contractor for delayed completion, (clause 2) the
							contractor shall continue with the work till the final decision
							by Commissioner/CMO/ Competent Authority.</p>
						<p>Failure on the part of the contractor for not applying
							extension of time even within 30 days of the cause of such an
							hindrance, it shall be deemed that the contractor does not desire
							extension of time and that he has "Waived" his right if any, to
							claim extension of time.</p>
						<p>
							Once the Commissioner/CMO/ Competent Authority has heard (oral
							and or in writing) the contractor on this subject matter of
							extension of time and if Commissioner/CMO/ Competent Authority
							fails to communicate his decision within a period of 30 days of
							such hearing, it shall be <b><u>deemed</u></b> that the
							contractor has been granted extension of time for the period as
							applied by him.
						</p>
						<p>
							5.2 <b>Compensation Events:-</b>
						</p>
						<p>The following mutually agreed Compensation Events unless
							they are caused by the contractor would be applicable;</p>
						<ul>
							<li>(a) The Commissioner/CMO does not give access to a part
								of the site</li>
							<li>(b) The Commissioner/CMO modifies the schedule of other
								contractor in a way, which affects the work of the contractor
								under the contract.</li>
							<li>(c) The Commissioner/CMO orders a delay or does not
								issue drawings, specification or instructions
								/decisions/approval required for execution of works on time.</li>
							<li>(d) The Commissioner/CMO instructs the contractor to
								uncover or to carry out additional tests upon work, which is
								then found to have no defects.</li>
							<li>(e) The Commissioner/CMO gives an instruction for
								additional work required for safety or other reasons.</li>
							<li>(f) The advance payment and or payment of running bills
								(complete in all respect) are delayed.</li>
							<li>(g) The Commissioner/CMO unreasonably delays issuing a
								Certificate of Completion</li>
							<li>(h) Other compensation events mentioned in contract if
								any</li>
						</ul>
					</div>
				</div>
				<!---  07 Page Ends Here --->
				<!---  08 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>FINAL CERTIFICATE</b>
						</p>
						<p>
							<b>Clause 6 -</b> On completion of the work the contractor shall
							be furnished with a certificate by the Commissioner/CMO
							(hereinafter called the Commissioner/CMO ) of such completion in
							the form appended at the end, but no such certificate shall be
							given, nor shall the work be considered to be complete until the
							contractor shall have removed from the premises on which the
							works shall be executed, all scaffolding surplus materials and
							rubbish, and cleaned off the dirt from all wood-work, doors
							windows walls, floors or other parts of any building in upon or
							about which the work is to be executed or of which he may have
							had possession for the purpose of the execution there of nor
							until the work; shall have been measured by the Commissioner/CMO
							whose measurements shall be binding and conclusive against the
							contractor. If the contractor shall fail to comply with the
							requirements of this clause as to removal of scaffolding surplus
							materials and rubbish and cleaning of dirt on or before the date
							fixed for the completion of the work, the Commissioner/CMO may,
							at the expense of the contractor remove such scaffolding, surplus
							materials and rubbish and dispose of the same as he thinks fit
							and clean off such dirt as aforesaid and the contractor shall
							forthwith pay the amount of all expenses so incurred, and shall
							have no claim in respect of any such scaffolding or surplus
							materials as aforesaid, except for any sum actually realised by
							the sale thereof.
						</p>
						<p>
							<b>Clause 7 - PAYMENT ON INTERMEDIATE CERTIFICATE TO BE
								REGARDED AS ADVANCES:</b> <br />No payments shall ordinarily be
							made for work estimated to cost less then Rs. 1,000/- (Rs. One
							Thousand) till after the whole of the works shall have been
							completed and certificate of completion given but if intermediate
							payment during the course of execution of works is considered
							desirable in the interest of works, the contractor may be paid at
							the discretion of the Officer-in-charge But in the case of works
							estimated to cost more then rupees one thousand, the contractor
							shall on submitting the bill therefore be entitled to receive a
							monthly payment proportionate to the part thereof then approved
							and passed by the Officer-in-charge whose certificate of such
							approval and passing of the sum so payable shall be final and
							conclusive against the contractor. But all such intermediate
							payments shall be regarded as payments by way of advance against
							the final payment for works actually done and completed and shall
							not preclude the requiring of bad unsound and imperfect or
							unskillful work to be removed and taken away and reconstructed or
							erected or be considered as an admission of the due performance
							of the contract or any such part thereof, in any respect, or the
							accruing of any claim, nor shall it conclude determine, or affect
							in any way the powers of the Officer - in-charge under these
							conditions or any of them as to the final settlement and
							adjustment of the accounts or otherwise or in any other way vary
							or affect the contract. The final bill shall be submitted by the
							contractor within one month of the date fixed for completion of
							the work, otherwise the Officerin- charge’s certificate of the
							measurement and of the total amount payable for work accordingly
							shall be final and binding on all parties.
						</p>
						<p>
							<b>Clause 8 - Bills to be submitted monthly:</b> <br />"A bill
							shall be submitted by the contractor by 15th day of each month
							for all works executed by him till the end of previous month less
							the gross amount received by him till the last previous month.
							This bill must be supported by records of detail measurement of
							quantities of all executed items of work along with true copies
							of record and result of all tests conducted in the previous month
							(date wise). The Commissioner/CMO shall take or cause to be taken
							the requisite measurement for purpose of having the same
							verified/checked by the sub Engineer/Assistant
							Engineer/Commissioner/CMO concern for quantity, quality and
							specification and examining all the "test results" and record the
							same in the Departmental measurement, book. Based on above record
							measurement bill shall be corrected /prepared afresh. The
							contractor shall sign the measurement and the bill. by 25 th day
							of the month subject to availability of the funds If the
							contractor fails to submit, the bill on or before the day
							prescribed, the Commissioner/CMO after waiting for another 15
							days shall depute a subordinate to measure the said work in the
							presence of contractor and or his authorized
							Engineer/Representative, whose counter signature to the
							measurement recorded with quantity and quality remark will be
							sufficient proof for acceptance of the same and shall be binding
							on the contractor. <br />All such running bill payments are by
							way of "Advances" and shall be subject to final adjustment.
						</p>
					</div>
				</div>
				<!---  08 Page Ends Here --->
				<!---  09 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>Clause 9 - BILLS TO BE ON PRINTED FORMS:</b> <br />The
							contractor shall submit all bills on printed forms to be had on
							application at the office of the Commissioner or CMO, and the
							charges in the bills shall always be entered at the rates
							specified in the tender or in the case of any extra work ordered
							in pursuance of these conditions, and not mentioned or provided
							for in the tender at the rates hereinafter provided for such
							work. The deduction or addition as the case may be of the
							percentage will be calculated on the amount of the bill for the
							work done, after deducting the cost of materials supplied
							departmentally at rates specified in the agreement.
						</p>
						<p>
							<b>Clause 10 - RECEIPTS TO BE SIGNED BY PARTNERS OF PERSONS
								HAVING AUTHORITY TO DO SO:</b> <br />Receipts for payments made on
							account of a work when executed by a firm must also be signed by
							the several partners, except where the contractors are described
							in their tender as a firm in which case the receipt must be
							signed in the name of-the firm by one of the partners, or by some
							other person having authority to give effectual receipt for the
							firm.
						</p>
						<p>
							<b>CLAUSE 11</b> Reimbursement/Refund on Variation in Prices of
							Materials / P. O. L. and Labour Wages, only for the works
							contract value more than Rs.3 Cr.
						</p>
						<p>
							<b>Price Adjustment: -</b>
						</p>
						<ul>
							<li>(A) Contract price shall be adjusted for increase or
								decrease in rates and price of labour, materials, POL, in
								accordance with the following principles and procedure and as
								per formula given below. <br /> <br />Note: - Price adjustment
								shall be applicable from reckoned date and upto validly extended
								period under clause 5.1 above but shall not apply to the period
								when, work is carried out under clause 2 above
							<li>
							<li>(B) The price adjustment shall be determined during each
								month from the formula given in the hereunder.</li>
							<li>(C) Following expressions and meanings are assigned to
								the work done during each month: <br />&emsp;&emsp;To the
								extent that full compensation for any rise or fall in costs to
								the contractor is not covered by the provisions of this or other
								clauses in the contract, the unit rates and prices included in
								the contract shall be deemed to include amounts to cover the
								contingency of such other rise or fall in costs.
							</li>
						</ul>
						<h2>
							<b>The formula (e) for adjustment of prices are: -</b>
						</h2>
						<p>R= Total value of work done during the month. It would
							include the amount of secured advance granted, if any, during the
							month, less the amount of secured advance recovered, if any
							during the month. It will exclude value for works executed under
							variations for which price adjustment will be worked separately
							based on the terms mutually agreed.</p>
						<p>
							<b>Adjustment for labour component</b>
						</p>
						<p>(i) Price adjustment for increase or decrease in the cost
							due to labour shall be paid in ccordance with the following
							formula:</p>
						<p>
							V<sub>L</sub> = 0.85 x P<sub>1</sub>/100 x R x (L<sub>i</sub> -L<sub>0</sub>)/L<sub>0</sub>
						</p>
						<p>
							V<sub>L</sub> = increase or decrease in the cost of work during
							the month under consideration due to changes in rates for local
							labour.
						</p>
						<p>
							L<sub>0</sub> = the consumer price index for industrial workers
							at the town nearest to the site or work as published by Labour
							Bureau, Ministry of Labour, Govt. of India. on the date of
							inviting tender
						</p>
						<p>
							L<sub>i</sub> = The consumer price index for industrial workers
							at the town nearest to the site of work for the month under
							consideration as published by Labour Bureau, Ministry of Labour,
							Government of India.
						</p>
						<p>
							P<sub>1</sub> = Percentage of labour component of the work.
						</p>
					</div>
				</div>
				<!---  09 Page Ends Here --->
				<!---  10 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>Adjustment for cement component</b>
						</p>
						<p>(ii) Price adjustment for increase or decrease in the cost
							of cement procured by the contractor shall be paid in accordance
							with the following formula;</p>
						<p>
							V<sub>c</sub> = 0.85 x P<sub>c</sub>/100 x R x (C<sub>i</sub> -C<sub>0</sub>)/C<sub>0</sub>
						</p>
						<p>
							V<sub>c</sub> = increase or decrease in the cost of work during
							the month under consideration due to changes in rates for cement
						</p>
						<p>
							C<sub>0</sub> = The all India wholesale price index for cement as
							published by the Ministry of Industrial Development, Government
							of India, New Delhi. <br />on the date of inviting tender
						</p>
						<p>
							C<sub>i</sub> = The all India average wholesale price index for
							cement for the month under consideration as published by Ministry
							of Industrial Development, Govt. of India, New Delhi.
						</p>
						<p>
							P<sub>C</sub> = Percentage of cement component of the work.
						</p>
						<p>
							<b>Adjustment for steel component</b>
						</p>
						<p>(iii) Price adjustment for increase or decrease in the cost
							of steel procured by the Contractor shall be paid in accordance
							with the following formula;</p>
						<p>
							V<sub>s</sub> = 0.85 x P<sub>s</sub>/100 x R x (S<sub>i</sub> - S<sub>0</sub>)/S<sub>0</sub>
						</p>
						<p>
							V<sub>s</sub> = increase or decrease in the cost of work during
							the month under consideration due to changes in the rates for
							steel.
						</p>
						<p>
							S<sub>0</sub> = The all India wholesale price index for steel
							(Bar and Rods) as published by the Ministry of Industrial
							Development, Government of India, New Delhi. on the date of
							inviting tender
						</p>
						<p>
							S<sub>i</sub> = The all India average wholesale price index for
							steel (Bar and Roads) for the month under consideration as
							published by Ministry of Industrial Development, New Delhi.
						</p>
						<p>
							P<sub>s</sub> = Percentage of steel component of the work.
						</p>
						<p>
							<b>Note:-</b> <i>for the application of this clause, index of
								Bars and Rods has been to represent steel group.</i>
						</p>
						<p>
							<b>Adjustment of bitumen component</b>
						</p>
						<p>(iv) Price adjustment for increase or decrease in the cost
							of bitumen shall be paid in accordance with the following
							formula;</p>
						<p>
							V<sub>L</sub> = 0.85 x P<sub>b</sub>/1 00 x R X (B<sub>i</sub> -B<sub>0</sub>)
							/B<sub>0</sub>
						</p>
						<p>
							V<sub>b</sub> = Increase or decrease in the cost of work during
							the month under Consideration due to changes in rates for bitumen
						</p>
						<p>
							B<sub>0</sub> = The official retail price of bitumen at the IOC
							depot at nearest centre on the date of inviting tender.
						</p>
						<p>
							B<sub>i</sub> = The official retail price of bitumen of IOC depot
							at nearest center for the 15th day of the month under
							consideration.
						</p>
						<p>
							P<sub>b</sub> = Percentage of bitumen component of the work.
						</p>
						<p>
							<b>Adjustment of POL (fuel and lubricant) component</b>
						</p>
						<p>(v) Price adjustment for increase or decrease in cost of
							POL (fuel and lubricant) shall be paid in accordance with the
							following formula;</p>
						<p>
							V<sub>f</sub> = 0.85 x P<sub>f</sub> /100 x R x (F<sub>i</sub> -
							F<sub>0</sub>)/F<sub>0</sub>
						</p>
						<p>
							V<sub>f</sub> = Increase or decrease in the cost or work during
							the month under consideration due to changes in rates for fuel
							and lubricants.
						</p>
					</div>
				</div>
				<!---  10 Page Ends Here --->
				<!---  11 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							F<sub>0</sub> = The official price of High Speed Diesel (HSD) at
							the existing consumer Diesel pumps out let at nearest center on
							the date of inviting tender
						</p>
						<p>
							F<sub>i</sub> = The official retail price of HSD at the existing
							consumer pumps of IOC at nearest center for the 15th day of month
							under consideration.
						</p>
						<p>
							P<sub>f</sub> = Percentage of fuel and lubricants component of
							the work.
						</p>
						<p>
							<b>Note: -</b> For the application of this clause, the price of
							High speed Diesel Oil has been chosen to represent fuel and
							lubricants group.
						</p>
						<p>
							<b>Adjustment of Other Materials Component</b>
						</p>
						<p>(vii) Price adjustment for increase or decrease in cost of
							local materials other than Cement, steel, Bitumen and POL
							procured by the contractor shall lay in accordance with the
							following formula;</p>
						<p>
							V<sub>m</sub> = 0.85 x P<sub>m</sub> /100 X R x (M<sub>i</sub> -
							M<sub>0</sub>)/M<sub>0</sub>
						</p>
						<p>
							V<sub>m</sub> = Increase or decrease in the cost of work during
							the month under consideration due to changes in rates for local
							materials other than cement, steel, bitumen and POL.
						</p>
						<p>
							M<sub>0</sub> = The all India wholesale price index (all
							commodities) as published by the Ministry of Industrial
							Development, Govt. of India, New Delhi. on the date of inviting
							tender
						</p>
						<p>
							M<sub>i</sub> = The all India Wholesale price index (all
							commodities) for the month under consideration as published by
							Ministry of Industrial Development, Govt. of India, New Delhi.
						</p>
						<p>
							P<sub>m</sub> = Percentage of local material component (Other
							than cement, steel, Bitumen and POL) of the work.
						</p>
						<p>
							<b>The following percentages will govern the price adjustment
								for the entire contract:</b>
						</p>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Sl. No.</th>
									<th>Components</th>
									<th>For road</th>
									<th>For Building</th>
									<th>For bridge</th>
								</tr>
							</thead>
							<tr>
								<td>1</td>
								<td>Lbour- P<sub>1</sub>
								</td>
								<td>25%</td>
								<td>35%</td>
								<td>30%</td>
							</tr>
							<tr>
								<td>2</td>
								<td>Cement - P<sub>c</sub>
								</td>
								<td>5%</td>
								<td>10%</td>
								<td>25%</td>
							</tr>
							<tr>
								<td>3</td>
								<td>Steel - P<sub>s</sub>
								</td>
								<td>5%</td>
								<td>10%</td>
								<td>25%</td>
							</tr>
							<tr>
								<td>4</td>
								<td>Bitumen - P<sub>b</sub>
								</td>
								<td>10%</td>
								<td>-</td>
								<td>-</td>
							</tr>
							<tr>
								<td>5</td>
								<td>POL - P<sub>f</sub>
								</td>
								<td>10%</td>
								<td>10%</td>
								<td>10%</td>
							</tr>
							<tr>
								<td>6</td>
								<td>Other materials - P<sub>m</sub>
								</td>
								<td>45%</td>
								<td>35%</td>
								<td>10%</td>
							</tr>
							<td></td>
							<td><b>Total: -</b></td>
							<td><b> 100%</b></td>
							<td><b> 100%</b></td>
							<td><b> 100%</b></td>
						</table>
						<br />
						<p>Note :- If in the execution of contract for Road works use
							of certain material(s) is/are not involved (Viz cement, steel,
							Bitumen etc.), then the percentage of other material-Pm shall be
							increased to that extent</p>
						<p>Example:- Say in a contract of roadwork steel is not
							required (Ps-5%). Pm shall become 45%+5%=50%</p>
						<p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Or</p>
						<p>Say cement & steel not required then Pm shall become
							45%+5%+5%=55% and so on</p>
					</div>
				</div>
				<!---  11 Page Ends Here --->
				<!---  12 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>Clause 12: Work to be executed in Accordance with
								Specification, Drawing, Order, etc.:</b>
						</p>
						<p>The contractor shall execute the whole and every part of
							work in the most substantial and workman like manner, and both as
							regards materials and otherwise in every respect in strict
							accordance with the specifications. The contractor shall also
							conform exactly fully and faithfully to the designs, drawings and
							instructions in writing relating to the work signed by the
							Commissioner/CMO and lodged in his office and to which the
							contractor shall be entitled to have access at such office or on
							the site of the work for the purpose of inspection during office
							hours and the contractor shall if he so requires be entitled at
							his own expense to take or cause to be made copies of the
							specifications, and of all such designs, drawings and
							instructions as aforesaid.</p>
						<p>MORTH/IRC specifications for road and bridges,
							specifications for rural roads and other I.R.C. publications and
							their manual, latest CPWD specifications/I.S.I. codes for
							buildings or special specifications whenever enclosed separately
							shall apply in the case of any variance the following order of
							precedence shall prevail: -</p>
						<ul>
							<li>1. Specifications as per NIT.</li>
							<li>2. Specifications as per S.O.R.</li>
							<li>3 MORTH/IRC specifications for road and bridges,
								specifications for rural roads and other I.R.C. Publications and
								their manual, latest CPWD specifications/I.S.I. codes for
								buildings or special specifications whenever enclosed separately</li>
							<li>4. Mode of measurements for building shall be as
								provided in the S.O.R. applicable to the contract. Where such
								mode of measurement is not specified in the S.O.R. it shall be
								done as per l.S.l. Code of building measurement. However if any
								mode of measurement is specifically mentioned in the N.l.T. the
								same will get precedence over all the above.</li>
						</ul>
						<p>
							<b>Clause 12 - A:</b> In respect of all bearings, hinges or
							similar part intended for use in the superstructure of any
							bridge, the contractor shall, whenever required, in the course of
							manufacture, arrange and afford all facilities for the purpose of
							inspection and test of all or any of the part and the material
							used therein to any officer of the Directorate of inspection of
							the Ministry of works production and supply of the Government of
							India and such bearings, hinges or similar parts shall not be
							used in the superstructure of any bridge except on production of
							a certificate of acceptance thereof from the Directorate of
							inspection. All inspection charges will be payable by the
							contractors. (This clause may be struck off if the tender is not
							for bridgework).
						</p>
						<h2>
							<b><u>Variations</u></b>
						</h2>
						<p>
							<b>Clause 13 - Additions, Alterations in Specifications and
								Designs.</b>
						</p>
						<p>
							The Commissioner/CMO shall have power to make any alterations in,
							omissions from, additions to, or substitutions for, the original
							specifications, drawings, designs and instruction, that may
							appear to him to be necessary or advisable during the progress of
							the work, and the contractor shall be bound to carry out the work
							in accordance with any instruction which may be given to him in
							writing, signed by the Commissioner/CMO and such alterations,
							omissions, additions or substitution shall not invalidate the
							contract and any altered, additional or substituted work, which
							the contractor may be directed to do in the manner above
							specified as part of the work; shall be carried out by the
							contractor on the same conditions in all respects on which he
							agreed to do the main work and at the same rates as are specified
							in the tender for the main work, provided the total value of all
							such increased or altered or substituted work does not exceed 25%
							of the amount put to tender inclusive of contractor percentage.
							if such value exceeds 25% it shall be open to the contractor
							either to determine the contract or apply for extension. <br />
							<b>Note: -</b> Such additions, alterations, substitution, shall
							have to be within the Scope of work tendered for
						</p>
					</div>
				</div>
				<!---  12 Page Ends Here --->
				<!---  13 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>Rates for works not in schedule of rates</u></b>
						</h2>
						<p>If during the course of execution, where it is found
							necessary that certain item/items of work not provided for in the
							S.O.R. required to be carried out then the Engineer-in-Charge
							shall identify such item / items including approximate quantity
							of the contract and ask the contractor to submit his rates in
							writing supported by the requisite data within a period of 7
							days. The Engineer-in-Charge shall obtain approval/ modification
							of the proposed rate from the Competent Authority after
							recommendation of Commissioner/CMO and communicate the same
							within a period of 4 weeks to the contractor, in case the
							contractor agrees to the above rates as fixed by the Competent
							Authority then they shall form a part of supplementary schedule
							of the contract agreement .lf the contractor does not agree to
							the rate of the Competent Authority then it shall be open for the
							Commissioner/CMO to get the work executed through any other
							agency. The contractor will not however be entitled to any
							compensation due to delay or hindrance or loss of profit accruing
							on account of this extra work executed by alternative agency.</p>
						<p>If the contractor commences non-schedule work or incur
							expenditure in regard thereto before the rates shall have been
							determined by the Competent Authority, then he shall be entitled
							for payment for the work done as decided by the Competent
							Authority. the decision of the Competent Authority shall be
							final. Such a decision shall be given by the Competent Authority.
							Within a period of 30 (Thirty) days and it shall be open to the
							contractor not to continue that item further. In such an event
							that item shall be got executed by other agency at such an
							approved rate by Competent Authority Contractor may either
							determine his contract if variations exceeds 10 (Ten) % of the
							Administrative approval, or may apply for extension.</p>
						<h2>
							<b><u>Extension of time in consequence of variations</u></b>
						</h2>
						<p>The time for the completion of work shall be extended in
							proportion of the variation of the work bear to the original
							contract work and certificate of Commissioner/CMO shall be
							conclusive as to such proportion.</p>
						<h2>
							<b>NO CLAIM TO ANY PAYMENT OR COMPENSATION FOR<br />ALTERATION
								IN OR RESTRICTION OF WORKS:
							</b>
						</h2>
						<p>
							<b>Clause 14 -</b> If at any time after the execution of the
							contract documents, the Commissioner/CMO shall for any reason
							whatsoever require the whole or any part of the work as specified
							in the tender to be stopped for any period or shall not require
							the whole or part of the work to be carried out at all or to be
							carried out by the contractor he shall give notice in writing of
							the fact to the contractor who shall there upon suspend or stop
							the work totally or partially, as the case may be.
						</p>
						<p>If any such case, except as provided hereunder, the
							contractor shall have no claim to any payment or compensation
							what so ever on account of any profit or advantage which he might
							have derived from the execution of the work in full, but which he
							did not so derive in consequence of the full amount of the work
							not having been carried out, or on account of any loss that he
							may be put to on account of materials purchased or for
							unemployment of labour recruited by him. He shall not also have
							any claim for compensation by reason of any alteration having
							been made in the original specifications, drawing, designs and
							instructions, which may involve any curtailment of the work as
							originally contemplated. Where, however, materials have already
							been purchased or agreed to be purchased by the contractor shall
							be paid for such materials at the rates determined by the
							Commissioner/CMO, provided they are not in excess of requirement
							and of approved quality and / or shall be compensated for the
							loss, if any that he may be put to, in respect of materials
							agreed to be purchased by him, the amount of such compensation to
							be determined by the Commissioner/CMO whose decision shall be
							final. If the contractor suffers any loss on account of his
							having to pay labour charges during the period during which the
							stoppage of work has been ordered under this clause, the
							contractor shall, on application be entitled to such compensation
							on account of labour charges as the Commissioner/CMO, whose
							decision shall be final, may consider reasonable provided that
							the contractor shall not be entitled to any compensation on
							account of labour charges,</p>
					</div>
				</div>
				<!---  13 Page Ends Here --->
				<!---  14 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>if in the opinion of the Commissioner/CMO, the labour could
							have been employed by the contractor elsewhere for the whole or
							part of the period during which the stoppage of the work has been
							ordered as aforesaid. If the total duration of suspension of the
							work is more than the six months, then this suspension of the
							work will be considered as permanent stoppage of the work, and
							the contractor can determine the contract, if he so desires.</p>
						<p>
							<b>Clause 15 <u>ACTION AND COMPENSATION PAYABLE IN CASE
									OF BAD WORK:</u></b>
						</p>
						<p>If at any time before the security deposit is refunded to
							the contractor, it shall appear to the Commissioner/CMO or his
							subordinate in charge of the work, that any work has been
							executed with unsound, imperfect or unskilful workmanship or with
							material of inferior quality or that any materials or articles
							provided by him for the execution of the work are unsound, or of
							a quality inferior to that contracted for, or are otherwise not
							in accordance with the contract, it shall be lawful for the
							Commissioner/CMO to intimate this fact in writing to the
							contractor and then notwithstanding the fact that the work,
							materials or articles complained of may have been Inadvertently
							passed, certified and paid for contractor shall be bound
							forthwith to rectify, or remove and reconstruct the work so
							specified in whole or in part, as the case may require, or if so
							required, shall remove the materials or articles so specified and
							provide other proper and suitable materials or articles at his
							own proper charge and cost, and in the event of his failing to do
							so with in a period to be specified by the Commissioner/CMO in
							the written intimation aforesaid ,the contractor shall be liable
							to pay compensation at the rate of one percent on the amount of
							contract put to tender every day not exceeding ten days, during
							which the failure so, continues and in the case of any such
							failure the Commissioner/CMO may rectify or remove and,
							re-execute the work or remove and replace the materials or
							articles complained of as the case may be at the risk and expense
							in all respects of the contractor. Should the Commissioner/CMO
							consider that any such inferior work or materials as described
							above may be accepted or made use of it shall be within his
							discretion to accept to the same at such reduced rates as he may
							fix therefore</p>
						<h2>
							<b><u>WORK TO BE OPEN FOR INSPECTION-CONTRACTOR OR
									RESPONSIBLEAGENT TO BE PRESENT:</u></b>
						</h2>
						<p>
							<b>Clause 16-</b> All work under or in course of execution or
							executed in pursuance of the contract shall at all time be open
							to the inspection and supervision of the Commissioner/CMO and his
							subordinates and the contractor shall at all time during the
							usual working hours, and at all other times at which reasonable
							notice of the intention of the Commissioner/CMO or his
							subordinate to visit the work shall have been given to the
							contractor, either himself be present to receive orders and
							instruction or have a responsible agent duly accredited in
							writing present for that purpose. Orders given to the
							contractor’s agent shall be considered to have the same force
							as if they had been given to the contractor himself.
						</p>
						<p>
							<b>Clause 17 - NOTICE TO BE GIVEN BEFORE WORK IS COVERED UP:</b>
							<br />The contractor shall give not less than five days notice
							in writing to the Commissioner/CMO or his subordinate in charge
							of the work before covering tip or otherwise placing beyond the
							reach of measurement any work in order that the same may be
							measured, and correct dimensions thereof be taken before the same
							is so covered up or placed beyond the reach of measurement, any
							work without the consent in writing of the Commissioner/CMO or
							his subordinate in charge of the work and if any work shall be
							covered up or placed beyond the reach of measurement without such
							notice having been given or consent obtained, the same shall be
							uncovered at the contractors expenses, or in default thereof, no
							payment or allowance shall be made for such work or the materials
							with which the same was executed.
						</p>
						<p>
							<b>Clause 18- CONTRACTOR LIABLE FOR DAMAGE DONE AND FOR
								IMPERFECTIONS AFTER CERTIFICATE OF COMPLETION</b>
						</p>
						<p>If the contractor or his work people or servants shall
							break, deface injure or destroy any part of building in which
							they may be working or any building, road, road curbs, fences,
							enclosures, water pipes, cables drains, electric or telephone
							posts or Wires trees grass or grassland or cultivated ground</p>
					</div>
				</div>
				<!---  14 Page Ends Here --->
				<!---  15 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>continuous to the premises on which the work or any part of
							it is being executed, or if any damage shall happen to the work
							while in progress, from any cause whatever, or any imperfections
							become apparent, the contractor shall make good the same at his
							own expense or in default, the Commissioner/CMO may cause the
							same to be made good by other workmen and deduct the expense of
							which certificate of the Commissioner/CMO shall be final) from
							any sums that may be then or at any time thereafter, may become
							due to the contractor or from his security deposits, or the
							proceeds of sale thereof or of a sufficient portion thereof.</p>
						<p>
							<b>The security deposit</b> of the contractor to the extent of
							50% shall be refunded on his getting the completion certificate,
							provided that all the recoveries outstanding against him are
							realised. Balance 50% of the amount shall be refunded after four
							months of completion of work or final bill paid whichever is
							earlier
						</p>
						<p>
							<b>Clause 19 - CONTRACTOR TO SUPPLY PLANT, LADDERS,
								SCAFFOLDING, ETC.:</b>
						</p>
						<p>
							The contractor shall supply at his own cost materials (except
							such special materials if any, as may in accordance with the
							contractor be supplied from the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							Stores) plants, tool, appliances, implements, ladders, cordage,
							tackle, Scaffolding and temporary work requisite for the proper
							execution the work whether original, or altered or substituted,
							and whether included in the specification or other documents
							forming part of the contractor referred to in these condition or
							not or which may be necessary for the purpose of satisfying or
							complying with the requirement of the Commissioner/CMO as to any
							matter as to which under these conditions he is entitled to be
							satisfied, or which he is entitled to require together with
							carriage there for to and from the work . The contractor shall
							also supply without charge requisite number of persons with the
							means and materials necessary for the purpose of setting out
							works, and counting, weighing& assisting in the measurement or
							examination at any time and from time to time of the work, or
							materials. Failing his so doing the same may be provided by the
							Commissioner/CMO at the expenses of the contractor and the
							expenses may be deducted from any money due to the contractor
							under the contract, or from his security deposit or the proceeds
							of sale thereof, or of a sufficient portion thereof.
						</p>
						<p>
							<b>Contractor is liable for damages arising from
								non-provision of lights fencing etc.</b> The contractor shall also
							provide at his own cost except when the contract specifically
							provides otherwise and except for payments due under clause all
							necessary fencing and lights required to protect the public from
							accident and shall be bound to bear the expenses of defence of
							every suit, action or proceedings at law that may be brought by
							any person for injury sustained owing to neglect of the above
							precautions & to pay any damage and costs which may be awarded in
							any such suit, action or proceedings to any such person or which
							may with the consent of the contractor be paid to compromise any
							claim by any such person.
						</p>
						<p>
							<b>COMPENSATION UNDER SECTION 12 SUB-SECTION (1) OF THE
								WORKMAN'S COMPENSATION ACT 1923:</b>
						</p>
						<p>
							<b>Clause 20 -</b> ln every case in which by virtue of the
							provisions of section 12 sub-section (1) of the workman's
							compensation Act 1923 Government is obliged to pay compensation
							to a workman employed by the contractor in execution of the
							works, Government will recover from the contractor the amount of
							compensation so paid and without prejudice to the rights of
							Government under section (1) sub-section (2) of the said Act.
							Government shall be at liberty to recover the amount or any part
							thereof by deducting it from the security deposit or from any sum
							due by Government to the contractor whether under this contract
							or otherwise. Government may not be bound to contest any claim
							made against them under section - 12 sub-section (1) of the said
							Act except on the written request of the contractor and upon his
							giving to Government full security for all cases for which
							Government might become liable in consequence contesting such
							claim.
						</p>
						<p>
							<b>LABOUR:</b>
						</p>
						<p>
							<b>Clause 21 -</b> The contractor should get himself registered
							under contract - labour regulations and abolition Act 1970
							including its amendments after getting a certificate from the
							principal employer, who will be the Commissioner/CMO
						</p>

					</div>
				</div>
				<!---  15 Page Ends Here --->
				<!---  16 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>Clause 22 -</b> Labour below the age of 14 years - No labour
							below the age of 14 years shall be employed on the work.
						</p>
						<p>
							<b>FAIR WAGE:</b>
						</p>
						<p>
							<b>Clause 23 -</b> The contractor shall pay not less than fair
							wage to labour engaged by him on the work.
						</p>
						<ul>
							<li><b>Explanation - (a):</b> Fair wage' means wage(s)
								whether for time or piece work notified during the period of
								execution of contract for the work and where such wages have not
								been so notified, the wages prescribed by the Works Department
								SOR for that period</li>
							<li><b>(b)</b> The contractor shall, notwithstanding the
								provisions of any contract to the contrary cause to be paid a
								fair wage to labourers indirectly engaged on the work including
								any labour engaged by his sub-contractors in connection with the
								said work, as if the labourers had been immediately employed by
								him.</li>
							<li><b>(c)</b> In respect of labour directly or indirectly
								employed on the work for the performance of the contractors part
								of this agreement the contractor shall comply with or cause to
								be complied with the Labour Act in force.</li>
							<li><b>(d</b>) The Commissioner/CMO shall have the right to
								deduct, from the moneys due to the contractor, any sum required
								or estimated to be required for making good the loss suffered by
								a worker or workers by reasons of non-fulfilment to the
								conditions of the contract for the benefit of the workers
								non-payment of wages or deductions made from his or their wages,
								which are not justified by the terms of the contract or non
								observance of the regulations.</li>
							<li><b>(e)</b> The contractor shall be primarily liable for
								all payments to be made under and for observance of the
								regulations afore said without prejudice to his right to claim
								indemnity from his sub-contractors.</li>
							<li><b>(f)</b> The regulations aforesaid shall be deemed to
								be a part of this contract and any breach thereof shall be
								deemed to be a breach of this contract.</li>
						</ul>
						<h2>
							<b>Subletting of works</b>
						</h2>
						<p>
							<b>Clause 24 :-</b> The contract may be rescinded and security
							deposit forfeited, for subletting the work beyond permissible
							limits as per clause 7.1 of appendix 2.10 or if contractor
							becomes insolvent: -
						</p>
						<p>
							<b>"Note : Such subletting/assignment shall not be made to
								any other Contractor registered in Class AI to A5 Category in
								the Public Works Department of Chhattisgarh or in similar
								Category in other Deptt. of the State or in other organization
								or Agency (Class with about similar financial capacity) by
								whatever name these are called."</b>
						</p>
						<p>
							<b>24.1</b> The contract shall not be assigned or sublet without
							prior sanction of the authority who has accepted the tender in
							writing. And if the contractor assign or sublet his contract, for
							more than permissible limits as per clause 7.1 of appendix 2.10
							or attempt to do so, or become insolvent commence any insolvency
							proceedings or make any composition with his creditors, or
							attempt to do so or if any gratuity, gift, loan, perquisite,
							reward of and advantage pecuniary or otherwise, shall either
							directly or indirectly be given, promised or offered by the
							contractor, or any of his servants or agents or to any public
							officer or person in the employ of Government in any way relating
							to his office or employment, or if any such officer or person
							shall become in any way directly or indirectly interested in the
							contract, the Commissioner/CMO may there upon by notice in
							writing rescind the contract, and the S.D. of the contractor
							shall there upon stand forfeited and be absolutely at the
							disposal of <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							and the same consequences shall ensure as if the contract had
							been rescinded under clause 3 thereof, and in addition the
							contractor shall not be entitled to recover or be paid for any
							work thereto for actually performed under the contract. Any such
							assignment/subletting within the limit of 25% by the authority
							who has accepted the tenders OR 50 % by the next higher authority
							accepting the tender or competent/Govt authority as the case may
							be ,shall not diminish or dilute the liability/ responsibility of
							the contractor.
						</p>
					</div>
				</div>
				<!---  16 Page Ends Here --->
				<!---  17 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>If the contractor gets item / items of work executed on a
							task rate basis without materials, this shall not amount to
							subletting of the contract.</p>
						<p>
							<b>Any subcontracted work, done in Chhattisgarh state with
								prior approval of competent authority, such subcontractor will
								also get the credit for work towards his experience.</b>
						</p>
						<p>
							<b>24.2 The Commissioner/CMO shall be empowered to terminate
								any contract if the contractor sublets the works to some other
								person on the basis of power of attorney.<br /> Subletting of
								work shall result in reduction in experience of the main
								contractor to the extent of the sublet.
							</b>
						</p>
						<p>
							<b>Clause 25: Sum payable by way of Compensation to be
								considered as Reasonable Compensation Without Reference to
								Actual Loss:</b>
						</p>
						<p>
							All sums payable by way of compensation under any of these
							condition shall be considered as reasonable compensation to be
							applied to the use of <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							without reference to the actual loss or damage sustained, and
							whether or not any damage shall have been sustained.
						</p>
						<p>
							<b>Clause 26 - CHANGE IN THE CONSTITUTION OF FIRM:</b>
						</p>
						<p>In the case of tender by partners any change in the
							constitution of the firm shall be forthwith notified by the
							contractor to the Commissioner/CMO for his information, and
							contractor shall initiate steps for fresh & new registration
							which shall be assessed & decided by the competent authority for
							fresh registration</p>
						<h2>
							<b>WORK TO BE UNDER DIRECTION OF Commissioner/CMO or
								Authorized ENGINEER/Officers</b>
						</h2>
						<p>
							<b>Clause 27 -</b> All works to be executed under the contract
							shall be executed under the direction and subject to the approval
							in all respect of the Commissioner/CMO of the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							for the time being who shall be entitled to direct at what point
							or points and in what manner they are to commenced and from time
							to time carried on.
						</p>
						<h2>
							<b>ARBITRATION CLAUSE:</b>
						</h2>
						<p>
							<b>Clause 28</b> Except as otherwise provided in this contract
							all question and dispute relating to the meaning of the
							specification, designs, drawings and instruction herein before
							mentioned as to thing whatsoever in any way arising out of or
							relating to the contract designs, drawings, specification,
							estimate, concerning the works, or the execution or failure to
							execute the same, whether arising during the progress of the
							work, or a after the abandonment there of shall be referred to
							the Commissioner/CMO for his decision, within a period of 30
							(thirty) days of such an occurrence (s). There upon the
							Commissioner/CMO shall give his written instructions and/or
							decisions, after hearing the contractor and Engineer-in-Charge
							within a period of 15 (fifteen) days of such request. This period
							can be extended by mutual consent of parties.
						</p>
						<p>Upon receipt of written instructions or decisions, of
							Commissioner/CMO the parties shall promptly proceed without delay
							to comply such instructions or decisions. If the Commissioner/CMO
							fails to give his instruction or decisions in writing within a
							period of 15 (fifteen) days or mutually agreed time after being
							requested and/or, if the party (es) is/are aggrieved against the
							decision of the Commissioner/CMO , the aggrieved party may within
							30 days prefer an appeal to the Competent Authority, who shall
							afford an opportunity to the parties of being heard and to offer
							evidence in support of his appeal. The, Competent Authority will
							give his decision within 30 (thirty) days, or such, mutually
							agreed period.</p>
						<p>If any party is not satisfied with the decision of the
							Competent Authority he can file a petition for resolving the
							dispute through arbitration in the arbitration tribunal A
							reference to Arbitration Tribunal shall be no ground for not
							continuing the work on the part of the Contractor.</p>
					</div>
				</div>
				<!---  17 Page Ends Here --->
				<!---  18 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>Payment as per original terms and condition of the
							agreement shall be continued by the Commissioner/CMO in
							accordance with clause 8 above.</p>
						<h2>
							<b>LUMP SUM IN ESTIMATE:</b>
						</h2>
						<p>
							<b>Clause 29 -</b> When the estimate on which a tender is made
							includes lump sums in respect of part of the works, the
							contractor shall be entitled to payment in respect of the items
							of work involved or the part of the work in the question at the
							same rates as are payable under this contract for such items, or
							if the part of the work in question is not, in the opinion of the
							Commissioner/CMO, capable of measurement, the Commissioner/CMO
							may at the his discretion pay the lump sum amount entered in the
							estimates , and the certificate in writing of the
							Commissioner/CMO shall be final and conclusive against the
							contractor with regard to any sum or sums payable to him under
							the provisions of this clause.
						</p>
						<p>
							<b>Action where no specification:</b>
						</p>
						<p>
							<b>Clause 30 -</b> In the case of any class of work for which
							there is no specification as is mentioned in Rule such work shall
							be carried out in accordance with the specification approved by
							Competent Authority for application to.
						</p>
						<p>
							<b>Contractor's Percentage whether Applied to Net or Gross
								Amounts of Bills:</b>
						</p>
						<p>
							<b>Clause 31 -</b> The percentage referred to at Para 7 of the
							tender will be deducted from/added to the gross amount of the
							bills for work done after deduction of the cost of materials
							supplied by the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>.
						</p>
						<p>
							<b>Claim for Quantities Entered in the Tender or Estimate:</b>
						</p>
						<p>
							<b>Clause 32 -</b> Quantities shown in the tender are approximate
							and no claim shall be entertained for quantities of work executed
							being either more or less than those entered in the tender of
							estimate. This is subject to the limitations as provided for in
							clause 13 and 14 above
						</p>
						<p>
							<b>Claim for Compensation for Delay In Starting the Work:</b>
						</p>
						<p>
							<b>Clause 33</b> No compensation shall be allowed for any delay
							caused, except as provided under clause 5.3, in starting of the
							work on any other ground or reasons whatsoever.
						</p>
						<h2>
							<b><u>EMPLOYMENT OF SCARCITY LABOUR:</u></b>
						</h2>
						<p>
							<b>Clause 34-</b> If Government declare a state of Scarcity or
							famine to exist in any village situated within sixteen kilometres
							of the work the contractor, shall employ upon such parts of the
							work as are suitable for unskilled labour, any person certified
							to him by the Competent Authority or by any person to whom the
							Competent Authority may have delegated this duty in writing to be
							in need of relief and shall be bound to pay to such persons wages
							not below the minimum which Government may have fixed in this
							behalf. Any dispute, which may arise in connection with the
							implementation of this clause, shall be decided by the Competent
							Authority whose decision shall be final and binding on the
							contractor.
						</p>
						<p>
							<b>Clause 35: - Royalty on Minor Minerals</b>
						</p>
						<p>The contractor shall pay all quarry, Royalty charges etc.
							If the contractor fails to produce the royalty clearance
							certificate from concerned department then the Commissioner/CMO
							shall deduct the royalty charges from his bills and keep in
							deposit head, which shall be refunded to the contractor on
							production of royalty clearance certificate from the concerned
							department. If he fails to produce the royalty clearance
							certificate with in 30 days of submission of final bill, then
							royalty charges which was keep under deposit head by the
							Commissioner/CMO shall be deposited to the concerned department
							and his final bill payment shall be released</p>

					</div>
				</div>
				<!---  18 Page Ends Here --->
				<!---  19 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">

						<p>Any change in the royalty rates of minor minerals notified
							by the state government, after the date of submission of
							financial offer by the bidder/contractor, then this
							increase/decrease in the rates shall be reimbursed/deducted on
							actual basis.</p>

						<p>
							<b>Clause 36 - TECHNICAL EXAMINATION:</b>
						</p>
						<p>
							The <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							shall have the right to cause Audit and Technical Examination of
							the works and the final bills of the contractor including all
							supporting vouchers, abstracts etc. to be made as per payments of
							the final bills and if as a result of such Audit & Technical
							Examination the sum is found to have been overpaid in respect of
							any work done by the contractor under the contract or any work
							claimed by him to hays been done under contract and found not to
							have been executed, the contractor shall be liable to refund the
							amount of over payment and it shall be lawful for the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							to recover the same from the security deposit of the contractor
							or from any dues payable to the contractor from the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							account if it is found that the contractor was paid lesser than
							what was due to him under the contract in respect any work
							executed by him under it, the amount of such under payment shall
							be duly paid by the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							to the contractor.
						</p>
						<p>In the case of any audit examination and recovery
							consequent on the same the contractor shall be given an
							opportunity to explain his case and decision of the
							Commissioner/CMO shall be final.</p>
						<p>In the case of Technical Audit, consequent on which there
							is a recovery from the contractor, no recovery, should be made
							without orders of the Competent Authority whose decision shall be
							final. AII action under this clause should be initiated and
							intimated to the contractor within a period of Twenty four months
							form the date of completion of work</p>
						<p>
							<b>Clause 37 - DEATH OF PERMANENT INVALIDITY OF CONTRACTOR:</b>
						</p>
						<p>If the contractor is an individual or a proprietary
							concern, partnership concern, dies during the currency of the
							contract or becomes permanently incapacitated, where the
							surviving partners are only minors the contract shall be closed
							without levying any damages/compensation as provided for in
							clause 3 of the contract agreement. However, if competent
							authority is satisfied about the competence of the surviving,
							then the competent authority shall enter into a fresh agreement
							for the remaining work strictly on the same terms and conditions,
							under which the contract was awarded.</p>
						<p>
							<b>Clause 38 - PENALTY FOR BREACH OF CONTRACT:</b>
						</p>
						<p>
							On the breach of any term or condition of this contract by the
							contractor the said <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							shall be entitled to forfeit the Security deposit or the balance
							thereof that may at the time be remaining, and to realise and
							retain the same as damages and compensation for the said breach
							but without prejudice to the right of the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							to recover further sums as damages from any sums due or which may
							become due to the contractor by <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							or otherwise howsoever.
						</p>
					</div>
				</div>
				<!---  19 Page Ends Here --->
				<!---  20 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>ANNEXURE - "E"<br />SECHUDLE OF ITEMS (BILLS OF QUANTITY)
							</b>
						</h2>
						<br />
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Sr. No</th>
									<th>Reference to item No. of S.O.R. (in any)</th>
									<th>Description of item</th>
									<th>Unit</th>
									<th>Quantity</th>
									<th>Rate in figure per unit (Rs.. .)</th>
									<th>Rate in words per unit(Rs….)</th>
									<th>Amount in (figure)</th>
									<th>Remark</th>
								</tr>
								<tr>
									<th>1</th>
									<th>2</th>
									<th>3</th>
									<th>4</th>
									<th>5</th>
									<th>6</th>
									<th>7</th>
									<th>8</th>
									<th>9</th>
								</tr>
							</thead>
							<tr>
								<td>1</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>2</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>3</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>4</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>5</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>etc</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>etc</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
						<br /> <br /> <br /> <br />
						<p class="pull-right" style="text-align: center;">
							<b>Contractor (Signature with name and<br /> seal of
								Authorised signatory of the<br /> contractor
							</b>
						</p>
						<br> <br /> <br> <br />
						<p>1) The value of total tendered cost is for Rs.
							........................................ and</p>
						<p>2) The total value of all S.O.R. items (excluding non
							S.O.R. items) as per sanctioned estimate is Rs.
							............................ (in figure) (rupees
							.........................................................................................
							in words)</p>
					</div>
				</div>
				<!---  20 Page Ends Here --->

			</div>
			<!---  Report Page Ends Here --->
		</form:form>
	</div>
	<!-- End Widget Content -->
</div>
<!-- End Content Here -->