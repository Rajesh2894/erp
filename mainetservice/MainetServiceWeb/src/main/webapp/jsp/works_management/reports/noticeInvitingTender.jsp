<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="css/report.css" rel="stylesheet" type="text/css">
<link href="css/kendo.common-material.min.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="css/kendo.material.min.css" type="text/css" />
<script type="text/javascript" src="js/mainet/ui/kendo.all.min.js"></script>
<script type="text/javascript" src="js/mainet/ui/jszip.min.js"></script>
<script type="text/javascript"
	src="js/works_management/tenderinitiation.js"></script>

<script type="text/javascript"
	src="js/works_management/reports/noticeInvitingTender.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<button onclick="topFunction()" id="myBtn" title="Go to top">Top</button>
	<header class="fixed" align="center">
		<button class="button" onclick="ExportPdf()">Download as .pdf</button>
		<button class="button" onclick="exportHTML();">Download as
			.doc</button>

		<c:choose>
			<c:when test="${command.mode eq 'CAP'}">
				<button class="button"
					onclick="window.location.href='ContractAgreementPrint.html'">
					<i class="fa fa-chevron-circle-left padding-right-5"></i>
					<spring:message code="works.management.back" text="" />
				</button>

			</c:when>

			<c:otherwise>
				<button class="button" onclick="backTenderPage();">
					<i class="fa fa-chevron-circle-left padding-right-5"></i>
					<spring:message code="works.management.back" text="" />
				</button>
			</c:otherwise>
		</c:choose>

	</header>
	<div class="report" id="myCanvas">
		<div class="widget-content padding">
			<form:form action="TenderInitiation.html" method="POST"
				class="form-horizontal" id="tenderInitiationNotice">
				<!-- Start Validation include tag -->

				<!---  Report Page Start Here --->
				<!---  01 Page Start Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>${userSession.getCurrent().organisation.ONlsOrgname} </b><br />
							<br> DETAILED NOTICE INVITING TENDER
						</h2>
						<p>
							<span class="pull-left"><b>N.I.T. No :-
									....................</b></span><span class="pull-right"><b>DATE
									<fmt:formatDate value="<%=new java.util.Date()%>"
										pattern="dd/MM/yyyy" />
							</b></span>
						</p>
						<p>
							<b>1. Introduction</b>
						</p>
						<p>
							1.1 Tenders are invited in Form "A" with bid capacity online from
							<b>${command.printNoticeInvintingTender.venderClassDesc}</b>
							class contractors of registered in E-Registration (Single window
							registration applicable ) under Chhattisgarh Government for
							similar work on CG e-Procurement System for the following work as
							per schedule of rates CGPWD SOR building in force from
							01.01.2015, Road in force from 01.01.2015, Electrification in
							force from 01.01.2015 and amendments applicable up to date of
							issue of NIT. The tender documents can be purchased from the
							UADD. Website <a href="http://uadd.cgprocurement.gov.in">http://uadd.cgprocurement.gov.in</a>
							directly through online of the cost of tender form on or before
							date up to 5:30-P.M. <br /> Cost of tender form: - Rs. <b>${command.printNoticeInvintingTender.tenderFeeAmt}</b>(For
							tenders online)
						</p>
						<p>
							<b><u>For On line tenders:-</u></b> The bid seals (hash) of the
							online bids required to be submitted by the bidders have to be
							generated and submitted after signing them with Digital
							Signatures on the system up to 5:30 P.M. on date <b>${command.printNoticeInvintingTender.technicalOpenDateDesc}</b>
							then only the On line tenders of those contractors will be
							received on the Next Tender . Website from ........ -P.M. on date
							........ up to ........ P.M. on date ..........
						</p>
						<br> <br>
						<p>
							Contractors have to submit <b> Registration Certificate,
								Earnest Money Deposit/Fix Deposit Receipt, Demand Draft of
								Processing Fees & Affidavit</b> in original in a separate Envelope
							and the same should reach the concerned office of the
							Commissioner / Chief Municipal Officer <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							before date <b>${command.printNoticeInvintingTender.tenderIssueToDateDesc}</b>
							up to ............. P.M. by registered A.D./Speed post .
						</p>
						<%-- <p>
							<b>Contractors have to submit the following Documents
								online:-</b>
						</p>
						<p>
							(i) Tender document fee with details. (ii) The earnest money
							deposit (EMD/FDR) with details. (iii) Affidavit in original (Non
							Judicial stamp in Rs. 100 as per annexure-13 in PQ) (iv) Self
							attested copy of Valid Registration Certificate. CG PWD and
							(Partnership Deed. Registration amendment certificate as the case
							may be) (v) Attested copy of PAN card issued by I.T. Department.
							(vi) Attested copy of GST Number. (vii) Valid Bank Solvency
							certificate in Bank Letter Head. (Not older than 12months from
							the bid due date amended if any) Solvency certificate must has
							bank Dispatch No. or verifiable unique No. & Date. <br />portal
							before date <b>${command.printNoticeInvintingTender.technicalOpenDateDesc}</b>
							up to 15.30 P.M. Through Online Only.
						</p> --%>
						<p>As the bids of the contractors have to be digitally signed
							by the contractor before submitting the bids Online, the bidders
							are advised to obtain Digital Certificates in order to bid for
							the work</p>
						<p>
							<b>Note:</b> - For online purchase of tender document application
							letter is not required
						</p>
						<p>
							(i). Name of the Work: - <b>${command.printNoticeInvintingTender.workName}</b>
						</p>
						<p>
							(ii). Probable amount of contract :- Rs. <b>${command.printNoticeInvintingTender.workEstimateAmt}</b>
						</p>
						<p>
							(iii). Amount of earnest money :- Rs.<b>${command.printNoticeInvintingTender.tenderSecAmt}</b>

						</p>
						<p>
							(iv) Time allowed for completion <b>${command.printNoticeInvintingTender.vendorWorkPeriod}</b>
							months including/excluding rainy season (from 16th June to 15th
							October)from the date of written order to commence the work <small>(
								Delete whichever is not applicable)</small>.
						</p>
						<p>
							<b>1.2 The electrical work shall be executed by civil
								contractor by engaging the person(s) only who possess proper
								valid electric license issued by the competent authority of the
								state Government. He shall also attach a copy of the license
								before starting electrical items of work.</b>
						</p>
						<p>1.3: Not more than one tender shall be submitted by any
							contractor or by a firm of contractors.</p>
						<p>1.4: No two or more concerns in which an individual is
							interested, as a proprietor and/ or partner shall tender for the
							execution of the same Work. If they do so all such tenders shall
							be liable to be rejected.</p>
						<p>1.5: The authority competent to accept the tenders shall be
							as per Govt. of Chhattisgarh Urban Administration & Development
							Department, Raipur order dated 05-12-2014.</p>
					</div>
				</div>
				<!---  01 Page Ends Here --->
				<!---  02 Page Start Here --->
				<div class="page">
					<div class="subpage">
						<p>1.6: Tender document consisting of plans, specifications,
							schedule(s) of quantities of the various items of work to be
							done, the conditions of contract and other necessary documents,
							together with addressed envelopes to be used for return of forms
							and other documents will be open for inspection and issued/sold
							on payment of Rs. .................. up to close of office hours
							of ..........</p>
						<p>1.7: The copies of others drawings and documents pertaining
							to the work signed for the purpose of identification by the
							accepting office or his accredited representative and samples of
							materials to be arranged by the contractor will be open for
							inspection by tenderers at the offices of ..................
							during working hours between up to the date mentioned in clause
							1.1 & 1.6 above.</p>
						<p>1.8: Tenders shall not be received by any other means like
							ordinary post or personal delivery.</p>
						<%-- <p>
							1.9. On line <b>tenders shall be opened</b> on date <b>${command.printNoticeInvintingTender.tenderIssueFromDateDesc}
								to ${command.printNoticeInvintingTender.tenderIssueToDateDesc}</b>
							at ---<b>17.30P.M.</b> at the office of the Director/Chief
							Engineer, Urban Administration & Development, Raipur(CG) pin-
							492002 before the contractors or his authorized representative
							intending to be present
						</p> --%>

						<p>1.9. Any manual tender received through registered post
							(AD.) Speed post after close of office hours of the prescribed
							dead line for receipt of tenders shall not be received from the
							postman and or if received shall be returned back to the tenderer
							unopened. All other tenders received before the prescribed
							deadline for receipt of tenders shall be in kept in safe custody
							with the Clerk of the office of Commissioner/ Chief Municipal
							Officer/Executive Engineer/Engineer incharg (as the case may be)
							till the prescribed time for opening of tenders. On line and /or
							manual tenders shall be opened on date ............... at
							---11.30-A.M./P.M. at the office of the Commissioner/ Chief
							Municipal Officer/ Executive Engineer / Engineer in charge before
							the contractors or his authorized representative intending to be
							present.</p>

						<p>1.10 All manual tenders received after the prescribed
							deadline shall be returned back unopened after subscribing the
							following remarks with dated initials by Municipal
							Corporation/Chief Municipal Officer. "Received late on date
							.............. at ............. AM./P.M. hence not entertained
							and returned"</p>

						<p>
							1.11 NOTES FOR GUIDANCE OF THE DEPARTMENTAL OFFICERS ONLY <br />Note
							(I): Fill in the blanks and strike out whichever is not
							applicable carefully before issue of N.I.T. for publication and
							display on the notice board as well as before sale / issue to
							intending tenderers.
						</p>
						<p>Note (II): Unless the tender forms with complete documents
							are fully prepared and ready for delivery to intending tenderers;
							the notice shall not be displayed on the notice board nor sent
							for publication in the press.</p>
						<p>Note (III): All tenders received after the deadline shall
							be noted in the register to be maintained in the concerned office
							& shall be counter signed by the head of office in the following
							format.</p>
						<p>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>S.No.</th>
									<th>Name of work</th>
									<th>Time & date of receipt</th>
									<th>Initial of person who returned the tender.</th>
									<th>Date initials of the head office.</th>
								</tr>
							</thead>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						</table>
						</p>
						<p>Note (IV): The names of tenderer(s) or their authorized
							representative present at the time of opening of tender(s) shall
							be noted in a register and their signature obtained in token of
							their presence. Rates and conditions, if any, offered by the
							tenderer(s) shall be read aloud by the officer opening the
							tender(s), so that the same can be noted by the tenderers or
							their representatives, if they so desire. The rates as well as
							conditions contained in the forwarding letter or separately
							attached to the tender(s) be read out, may also be got signed by
							all the tenderers or their representatives, who might be present.
							If they so desire. Comparative statements when ready (this should
							be as far as possible be got ready on the day after opening
							tenders) should also be exhibited publicly to the tenderers or
							their representatives.</p>
						<p>Note (V): The officer opening the tender(s) should mark
							every tender under his dated initials by the fraction, the
							denominator of each will show the order in which the tenders are
							opened and the numerator will show the total number of tenders
							received. This should be done invariably on the first</p>
					</div>
				</div>
				<!---  02 Page Ends Here --->


				<!---  03 Page Starts Here --->
				<div class="page">
					<div class="subpage">
						<p>page of the tender form and on page (2) where the tenderers
							are to sign as well as pages of schedule of items. (Annexure-E)
							attested and number the corrections and overwriting on each in
							tender with dated initials. If some words or figure is/are
							corrected more than once, then all of such corrections must be
							initialed with date.</p>
						<p>
							<b>1.12 INSTRUCTIONS FOR GUIDANCE OF TENDERERS</b>
						</p>
						<p>The tender will be liable to be rejected out-right, if
							while submitting it:-</p>

						<ul>
							<li>I) The tenderer proposes any alterations in the work
								specified, in the time allowed for carrying out the work or any
								conditions thereof - &emsp; or</li>
							<li>II) Any of the pages of the tenders removed or replaced
								- &emsp; or</li>
							<li>III) In the case of item rate tenders, rates are not
								entered in figures, and in words and the total of the each item
								and grand totals are not written by the tenderers in the last
								column of the schedule of items (Annexure-E under his signature
								- &emsp; or</li>
							<li>IV) If erasures without attestation are made by him in
								the tender - &emsp; or</li>
							<li>V) If all corrections and conditions and pasted slips
								are not initialed & dated by the tenderer - <br />&emsp;&emsp;&emsp;or
							</li>
							<li>VI) If the tenderer or in the case, each partner or any
								parteners so authorised thereof, does not sign or
								signature/signatures is/are not attested by a witness on page 2
								of the tender in the space provided for the purpose - &emsp; or</li>
							<li>VII) If documents are not filled in ink or by ball pen.</li>
						</ul>
						<p>
							<b>2 .RATES:</b>
						</p>
						<p>2.1 The schedule of items: The schedule of all items of
							work to be executed is enclosed as Annexure - E</p>
						<p>2.2 Percentage rate tender in form "A" or "C"</p>
						<p>
							2.2.1 <b><u>In respect of percentage rate tenders:</u></b>-
							contractor should quote his separate tender percentage rate above
							or below or at par the following schedule of rates.
						<ul>
							<li>(a) Building Works :
								......................................................</li>
							<li>(b) Electrical Works :
								......................................................</li>
							<li>(c) Road Works :
								.......................................................</li>
							<li>(d) Bridge Works :
								.......................................................</li>
						</ul>
						<p>2.2.2: The percentage of tender above I below or at par
							with the relevant schedule of rates inclusive of all amendments
							issued up to the date of the issue of notice inviting tenders
							should be expressed on the tender form itself, both in words and
							figures in such a way that interpolation is not possible and all
							over writings should be neatly scored out and rewritten and the
							corrections should be duly attested and dated prior to the
							submission of tender. Tenders not specifying percentage in words
							will summarily be rejected. In the case of variation between the
							rates stated in figure and words, the lesser of the two shall be
							deemed to be valid. Any amendments to the schedule or rates after
							the date of issue of this tender notice or the date of issue of
							any amendments to the N.I.T. specifically notifying the said
							amendments to the current schedule of, rates, shall not apply to
							this tender.</p>
						<p>2.2.3: The percentage tendered by the contractor will apply
							to those rates which find place in the Schedule of rates
							mentioned in clause 2.2.1 or have been derived from the said
							Schedule of rates and not to other items of work.</p>
					</div>
				</div>
				<!---  03 Page Ends Here --->

				<!---  04 Page Starts Here --->
				<div class="page">
					<div class="subpage">
						<p>2.2.4: The percentage quoted by the contractor shall not be
							altered by the contractor during the term of contract. The
							deduction or addition, as the case may be of the percentage will
							be calculated on the amount of bill for the work done, after
							deducting the cost of materials supplied departmentally, if any,
							at rates specified in the agreement</p>
						<p>2.2.5: If the work involves more than one S.O.R. even then
							the contractor shall quote only single rate, applicable to the
							concerned S.O.R. (for example- Building S.O.R. and Electrical
							S.O.R.)</p>

						<p>
							2.3 <b>Item Rates tenders in forms 'B': -</b> <br>
						</p>
						<p>2.3.1: Item Rate Tenders in form-"B". In respect of item
							rate tenders, contractor should quoted his rates for the items
							mentioned in the schedule of item in Annexture-F of this N.I.T.
							Only rates quoted shall be considered. The rates should be
							expressed in figures as well as words and the unit should be
							rates by the department. The contractor will not have the freedom
							to change the unit. No percentage above or below the schedule be
							quoted.</p>
						<p>2.3.2: The rates quoted in the tender for the various items
							of work will not be altered by the contractor during the item of
							contract.</p>
						<p>
							2.4 <b>Lead and lift of water:</b> No lead and lift for carting
							of water will be paid.
						</p>
						<p>
							2.5 <b>Lead and lift of materials:</b> No lead and lift for
							carting of material shall be payable to the contractor except in
							case of such items for which specific lead and or lift are
							provided in the Schedule of rates mentioned in clause 2.2.1 of
							the N.I.T. or in the schedule of items in respect of item rate
							tender.
						</p>
						<p>
							2.6 <b>Addition alteration and Non-Schedule items of works</b>:-
							During the execution of the work there is likelihood of addition
							alteration in the items of work and also of such items of work,
							which do not find place in the Schedule of rates, referred to
							above in respect of percentage rate contracts (Form "A"),
							<!-- or such
							items which are not given in the schedule of items in respect of
							item rate contracts (Form 'B'), -->
							for which contractor has not quoted his rates. <br />Contractor
							will have to carry out these items of work <br />
						</p>
						<p>(i) for percentage rate tender (form A) - as provided in
							clause 13 the conditions of contract</p>
						<p>
						<p>(ii) for item rate tender (form B)- as provided in clause
							13 of the conditions of contract</p>

						<p>

							<br> <br>
						<p>
							<b>3. Submission of Tender :-</b>
						</p>
						<p>3.1: Earnest money: -No tender will be considered without
							the dep osit of the specified earnest money which will be
							returned to the unsuccessful tenderers on the rejection of their
							tenders, or earlier as may be decided by the competent authority
							and on production of a certificate of
							................................that all tender documents have
							been returned, and will be retained from the successful tenders
							as part of the security deposit.</p>
						<p>
							<b>3.2 <u>Forms of earnest money:</u></b>
						</p>
						<!-- <p>3.2.1: The amount of earnest money shall be deposited
							online in the portal.</p> -->
						<p>3.2.1: The amount of earnest money shall be accepted only
							in the shape of Bank drafts or in other interest bearing shapes
							mentioned in W.D. Manual Para. 2.079 in favour of the
							Commissioner/ Chief Municipal Officer of concerned ULB. valid for
							a period of -----------months at least and further subject to
							appropriate verification by the Commissioner/Chief Municipal
							Officer of concerned ULB.</p>

						<p>3.2.2: The intending tenderers from other state may remit
							EMD/FDR in the form of the bank draft of any schedule bank
							payable at par at</p>

						<!-- <p>3.3 Earnest Money should be submitted through Online only.
							if the earnest money is not found in Accordance with the
							prescribed mode the tender will be unopened.</p> -->
						<p>
							<b>3.3: Earnest Money in separate covers: </b>The earnest money
							in one of the prescribed forms should be produced / sent
							separately and not kept in the covers containing the tender and
							if the earnest money is not found in accordance with the
							prescribed mode the tender will be returned unopened to the
							tenderer in case of tenders to be submitted by registered .A.D.
							or speed post.
						</p>
					</div>
				</div>
				<!---  04 Page Ends Here --->

				<!---  05 Page Starts Here --->
				<div class="page">
					<div class="subpage">
						<p>In case of on line tenders earnest money submitted shall be
							verified & if found in accordance with the prescribed mode, then
							only on line financial offer shall be opened</p>
						<p>
							<b>3.4 Adjustment of the earnest money: -</b> Earnest money,
							which has been deposited for a particular work, will not be
							adjusted towards the earnest money for another work but can be
							adjusted (if available) for the same work if tender is recalled
							and if requested in writing by the tenderer.
						</p>
						<p>
							3.4.1. <b><u>Refund of earnest money:</u></b>
						</p>
						<p>3.4.1. (i): If it is decided on the same day to reject all
							the tenders, the earnest money of all tenderers shall be refunded
							immediately after taking decision by the competent authority.</p>
						<p>3.4.1.(ii) 3.4.1:(ii) The earnest money of tenderers whose
							tenders are rejected shall be refunded .Also in case of the
							tenderer whose tender is accepted, and /or conveyed after expiry
							of the validity period ,Earnest money shall be refundable unless
							validity period extended by the tenderer.</p>
						<p>
							3.5 <b><u>Security Deposit:</u></b>
						</p>
						<p>(a) The Security Deposit shall be recovered from the
							Running Bills and final bill @ 5%(five) Percent as per clause - I
							of the agreement read with Para 3.5 of the N.I.T.</p>
						<p>(b) The amount of the EMD/FDR shall not be adjusted when
							value of work done reaches the limit of the amount of Contract or
							exceeds the probable amount of the contract.</p>
						<p>(c) For unbalanced tender rate additional security Deposit
							shall be deposited as per respective clause of "Special Condition
							of NIT in percentage rate/item rate tenders.</p>
						<p>
							3.6 <b><u>Implication of submission of tender:</u></b> Tenderers
							are advised to visit site sufficiently in advance of the date
							fixed for submission of the tenders. A tenderer shall be deemed
							to have full knowledge of the relevant documents, samples site
							etc. whether he inspects them or not.
						</p>

						<p>3.7: The submission of a tender by a contractor implies
							that he has read the notice, conditions of tender and all other
							contract documents and made himself aware of the standard and
							procedure, in this respect, laid down in MORTH / I.R.C.
							Specification / CPWD Specification / ISI Code for building and
							electrical works to be done, has thoroughly inspected the
							quarries with their approaches, site of work, etc., and satisfied
							himself regarding the suitability and availability of site of
							work, etc. and satisfied himself regarding the suitability and
							availability of the materials at the quarries. The responsibility
							of opening new quarries and construction and maintenance of
							approaches there to shall lie wholly with the contractor.</p>
						<p>
							3.8 <b><u>Income Tax Certificate-</u></b> A tenderer purchasing
							tender documents for works exceeding Rs. 10 lacs shall submit
							either
						</p>
						<p>(a)Income Tax clearance certificate issued with in 12
							months from the date of receipt of tender. OR</p>
						<p>(b)His Income Tax return for the preceding 3 years and
							where law requires shall submit the audited balance sheet of

							Profit and Loss Account Statements with auditor's report for the
							preceding 3 years.</p>
						<p>
							<b>3.8.1:</b> A financial capacity certificate or attested
							photocopy their - of, from any schedule bank along with the
							application for the tender papers be submitted which should not
							be older than 12 months from the date of application. Amount of
							financial capacity to be furnished shall be at least 15(fifteen)
							% of amount put to tender
						</p>
						<p>The financial capacity certificate shall have to be in the
							following format:</p>
						<br />
					</div>
				</div>
				<!---  05 Page Ends Here --->


				<!---  06 Page Starts Here --->
				<div class="page">
					<div class="subpage">

						<h2>
							<b>CERTIFICATE</b> <br /> <small><b>(on the letter
									head of the Bank)</b></small>
						</h2>
						<p>On the basis of transactions/turn over in the account of
							____________________________________</p>
						<p>__________________________________________________________________________________</p>
						<p>&emsp;&emsp;&emsp;(Name and Address)</p>
						<p>We are of the opinion that his financial capacity is to the
							extent of (both figures & words) Rs.</p>
						<p>....................................................................................................................................</p>
						<p>This is without any prejudice and responsibility on our
							part.</p>
						<span class="pull-right">Br Manager <br />With seal of
							Bank
						</span>
						<p>
							Place: <br />Date :
						</p>
						<p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;_____________00___________</p>
						<p>In case of Online tender, financial offer shall not be
							opened & In case of manual tenders, tender copies would not be
							issued/sold in the absence of these documents mentioned in clause
							3.8 and 3.9</p>
						<p>3.9. List of works In Progress: Tenderer must furnish a
							list of contracts already held by him at the time of submitting
							the tender, in the Department and elsewhere showing therein.</p>
						<p>3.9.(1): The amount of each contract and total period of
							completion with information of original stipulated date of
							completion and actual date of completion.</p>
						<p>3.9.(2): Balance of works remaining to be done, and the
							remaining time allowed as per contract.</p>
						<p>3.9.(3): The amount of solvency certificate produced by him
							at the time of enrolment in the department.</p>
						<p>3.9.(4): Details of works where he withdraw his offer or
							did not-execute the agreement or where his contracts were
							rescinded in any department/organization (by whatever name these
							are called) of the Govt. of Chhattisgarh.</p>

						<p>3.9(5): Tender submitted and wherever his offer is the
							lowest with details of work, contract sum& period mentioned for
							completion there in.</p>
						<p>3.9(6): Other required documents</p>
						<p>
							3.10 <b><u>Relationship:</u></b> The contractor shall not be
							permitted to tender for works in the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							(responsible for award and Execution of contracts) in which his
							near relative is posted as Account Officer. He shall intimate the
							name of his near relative working in the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>,
							He shall also intimate the name of persons who are working with
							him in any capacity or subsequently employed by <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>.
							Any breach of this condition by the contractor would render
							himself liable to be removed from the approved list of
							contractors of the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>.
						</p>
						<p>
							Note: <i>By the term near relative is meant wife, husband,
								parents and son, grandson, brothers, sisters, brother-in-law,
								father-in-law and mother-in-law.</i>
						</p>
						<p>3.11 Signature of the tenderer for the works shall be
							witnessed by another person and signature affixed with his name
							designation and address in the space provided in the Tender
							document. Failure to observe this condition can render the tender
							of the contractor liable to rejection.</p>

					</div>
				</div>
				<!---  06 Page Ends Here --->

				<!---  07 Page Starts Here --->
				<div class="page">
					<div class="subpage">

						<p>
							4. <b>Opening and acceptance of tender:</b>
						</p>
						<p>
							4.1: <b><u>Place and time of opening</u></b> : The tenders shall
							be opened at 11.30 AM or as suitable on the day subsequent to the
							dead line prescribed for receipt for tenders as per Para 1.1
							above by the concerned <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							in the presence of the tenderer or their duly authorised agents
							who may choose to attend. The officer authorised to open the
							tender may depute another officer to open the tender under
							unavoidable circumstances. If that day happens to be a holiday,
							then it shall be opened on the immediate next working day at the
							same time and place.
						</p>
						<p>
							4.2 <b><u>Powers of Officer, receiving tenders</u></b> : The
							officer who opens the tender and for which he is not competent to
							accept , shall forward the tender to the competent authority
							through proper administrative channel; with his definite
							recommendation and enclosing therein all the tenders so received
							along with a complete set of approved NIT.
						</p>
						<p>4.3:Conditional tender are liable to be rejected</p>
						<p>
							4.4 . <b><u>Canvassing:</u></b> - Canvassing for support in any
							from for the acceptance of any tender is strictly prohibited. Any
							tenderer doing so will render himself liable to penalties which
							may include removal to of his name from the register of approved
							contractors or penal action under section-8 of the C.G .
							vinirdishtta Bhrasta Acharan Nivaran vidheyak , 1982.
						</p>
						<p>4.5 Unsealed tenders : The tenders can be rejected if not
							properly sealed by wax or by sticking tape, in the case of manual
							tenders</p>
						<p>4.6.:The authority competent to accept a tender reserves
							the right to accept the tender for the whole work or for a
							distinct part thereof or by distributing the work between one or
							more tenderers,or reject the tender as a whole without assigning
							any reason. Such decision shall not be open to challenge in any
							forum or court of law.</p>
						<p>
							4.7 <b><u>Validity of offer:</u></b> Tender shall remain open up
							to 120 (one hundred twenty ) days from the specified deadlines of
							receipt of tender(s) and in the event of the tenderer withdrawing
							his offer before the aforesaid date, for any reason whatsoever,
							earnest money deposited shall be forfeited.
						</p>
						<p>4.7.1: In the event of withdrawing his/her offer before the
							expiry of the period of validity of offer or failing to execute
							the agreement as required by condition NO.8.1.1. of the notice
							inviting tender (N.I.T.) he/she will not be entitled to tender
							for this work. In case of recall of tenders, in addition to
							forfeiture of his/her earnest money as per provisions of
							condition nos. 4.7 & 1.1. of the N.I.T. as may be applicable for
							the work, If the tenderer has committed a similar default on
							earlier occasion (s)as well then his/her registration in the
							department may be suspended temporarily for a period of 2 (Two)
							years, from such date as may be ordered by the authority who had
							registered him/her.</p>

						<p>
							5. <b><u>Specifications -</u></b>
						</p>
						<p>
							5.1 Brief Specifications: - A brief note on construction and
							specification of all the major items of the work is enclosed in <b>Annexure-
								D</b>
						</p>
						<p>
							5.2 <b><u>Material of construction:</u></b> - The materials of
							construction to be used in the work shall be governed by the
							MORTH /IRC specifications for Rural roads /other IRC publications
							and their manual/ latest CPWD specifications/ISI codes for
							buildings and the relevant Indian standard specification with
							amendments and revisions issued up to the date of tender notice.
							Where ever any material has I.S.I. mark such material alone has
							to be used
						</p>
						<p>
							5.3 <b><u>Workmanship</u></b>:- The work shall be carried out
							according to the specification referred to hereinafter and
							according to sound engineering practice. The decision of the
							Executive Engineer, in respect of workmanship will be final.
						</p>
						<p>
							5.4 <b><u>Specification for Building Work:-</u> </b>(Including
							water supply and sanitary fittings.)
						</p>
						<p>
							<b>5.4.1</b> The contractor shall execute the work in conformity
							with the standards and procedure laid down latest CPWD
							specifications/ISI codes for buildings or special specification
							when ever enclosed separately and in accordance with the approved
							drawings
						</p>
					</div>
				</div>
				<!---  07 Page Ends Here --->

				<!---  08 Page Starts Here --->
				<div class="page">
					<div class="subpage">

						<p>
							<b>5.4.2</b> Concrete. All concrete shall be Mixed in concrete
							mixer and compacted by mechanical vibrators. Slump test shall be
							carried out during concreting and sample test cubes prepared and
							tested for strength in accordance with the code. The Department
							will carry out the testing at the cost of contractor.
						</p>
						<p>The results of the tests shall conform with the required
							standard and if the Engineer-in-charge considers that a
							structural test is necessary, the same shall be carried out as
							instructed by the Engineer-in-charge at the contractor's expense
							and should the result of this be unsatisfactory the contractor
							will be bound to take down and reconstruct the particular portion
							of which has given unsatisfactory test results.</p>

						<p>
							<b>5.4.3 Bricks.:- </b>The contractor should use the bricks
							manufactured on the metric system, as for as possible.
						</p>
						<p>
							<b>5.4.4</b> All timber used in the wood work for works must be
							properly seasoned. In case of important buildings mechanical
							seasoning should be done in good seasoning plant.
						</p>
						<p>In case the contractor does not procure good seasoned wood,
							he may be asked to get it seasoned in plant at his own expense</p>

						<br>
						<p>5.4.5: Maintenance of roofs. Subject to the provision in
							the agreements, it will be the responsibility of the contract to
							see that the roof does not leak, during the period of the first
							rainy season in respect of tile and sheet roofing and two
							consecutive rainy seasons in respect of lime concrete and cement
							concrete terraced roof, after its completion. He will make good
							and replace all the defective work on this account at his own
							cost.</p>
						<p>5.5 Specification of Electrical works.</p>
						<p>
							<b>5.5.1</b> The work will be carried out as per the approved
							drawing and as directed by the ........... the work will be
							governed by " General specifications " for the Electrical works
							in Government building in Chhattisgarh in forces from 1972. All
							electrical materials must bear "ISI mark.
						</p>
						<p>
							<b>5.5.2</b> All samples of electrical accessories should be got
							approved from the Engineer-in charge prior the their us in work.
							Contractor will have to arrange and afford all facilities for
							their inspection and rectify the defects pointed out by them.
							Item involved in the Electrical work in enclosed in Annexure D.
						</p>
						<p>
							<b>5.5.3</b> The period of testing and refund of deposit will be
							6 months after completion of work.
						</p>
						<p>
							<b>5.5.4</b> In case of supply of ceiling fan, table fan, exhaust
							fan, cabin fan tube light fixtures will be made by the department
							as mentioned in the SOR as such labour rates only as per SOR will
							be paid for fitting of such items in position as per SOR.
						</p>
						<p>
							<b>5.5.5</b> The contractor should submit "as build" detailed
							wiring diagram on tracing cloth showing the point position of
							switch length of point, position of D.B. and main switch circuit
							No. in which points fall at time of final bill. Otherwise
							deduction of 1/2 percent (Half percent) will be made from the
							contract sum of all electrical items.
						</p>
						<p>
							<b>5.6.</b> Specifications for road/bridge/culvert works.
						</p>
						<br>
						<p>The road / bridge / culvert works shall be carried out
							according to MORST&H specifications for road & bridge works/
							Specifications for Rural roads ,its manual / specification in
							force' and or special specification or the relevant
							specifications published by the Indian Road congress.</p>
						<p>
							<b>5.7</b> Contradictions or amendments: In the event of
							contradiction between the stipulations of the Schedule of rates
							(schedule of rate relevant to this NIT) and aforesaid
							specification (vide Para5.1 to 5.6 above) the stipulations of the
							schedule of rates shall gain precedence. In the event of
							contradictions, if any, between different specifications and or
							codes of practice, referred to above the decision of Chief
							Engineer shall be final.
						</p>

					</div>
				</div>
				<!---  08 Page Ends Here --->

				<!---  09 Page Starts Here --->
				<div class="page">
					<div class="subpage">

						<p>
							<b>6. Supply of Materials:</b> The following materials will be
							supplied by the department
						</p>
						<table style="width: 100%;">
							<tr>
								<td>Name of Materials</td>
								<td style="text-align: center;">Rate.</td>
								<td>Place of delivery</td>
							</tr>
							<tr>
								<td>1.</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>2.</td>
								<td>-----------Nil------------</td>
								<td></td>
							</tr>
							<tr>
								<td>3.</td>
								<td></td>
								<td></td>
							</tr>
						</table>

						<p>6.1 : Delay in supply :- If the materials are not supplied
							in time the contractor will not be allowed any claim for any
							loss, which may be caused to him but only extension of time will
							be given at the discretion of the Commissioner/Chief Municipal
							Officer and .................................... if applied for
							by the contractor with in 15 days of its proposed utilization and
							as detailed in the latest construction program. Request of such
							material by the contractor shall be sent with in one month in
							advance.</p>
						<p>7. Miscellaneous Conditions</p>
						<p>1.:The tenderer or supplier should have a place of business
							in the State of Chhattisgarh from where the goods would be
							supplied to various destination in the state and also hold a
							registration certificate as per rules.</p>
						<p>2.:The tenderer or supplier shall also submit the clearance
							certificate as provided under section 36 of Chhattisgarh Vanijyak
							Kar Adhiniyam.</p>
						<p>7.1 Subletting: The contractor shall not without the prior
							approval of the authority who has accepted the tender in writing,
							sublet or assign to any other party or parties, any portion of
							the work under the contract. Where such approval is granted, the
							contractor shall not be relieved of any obligation or duty or
							responsibility, which he undertakes under the contract. However
							such subletting in no case be more than 25 % of contract value.
							But if required can be increased up to 50(fifty) % with the prior
							permission of the next higher authority accepting the tender or
							the Government as the case may be</p>
						<p>7.2 Taxes: The rate quoted by the Contractor shall be
							deemed to be inclusive of the sales and other levies, duties,
							royalties, cess, toll, taxes of Central and State Governments,
							local bodies and authorities that the Contractor will have to pay
							for the performance of this Contract. The Govt. will perform such
							duties in regard to the deduction of such taxes at source as per
							applicable law.</p>
						<p>
							<b>However if "Service Tax" and cess on service tax or any
								other "New Tax" (not increase or decrease in existing tax,
								duties, surcharge, except royalty on minor mineral) is levied on
								the contractor either by Central Govt. or State Govt, then the
								Commissioner/Chief Municipal Officer shall reimburse the
								"Service Tax" and cess on service tax and or "New Tax" amount;
								on submission of proof of such payments by the contractor.</b>
						</p>
						<p>7.3 Minerals extracted for works carried out on behalf of
							the Government of India, from the quarries in possession of and
							controlled by the state Government is subject to payment of
							Royalty by the contractor to whom it shall not be refundable. The
							Executive Engineer shall not also issue any certificate in
							respect of such materials extracted for Government of India work
							(Applicable to Government of India works only)</p>
						<p>7.4 Rules of Labour Camps: The contractor will be bound to
							follow the Chhattisgarh Model Rules relating to layout, water
							supply and sanitation on labour camps (vide Annexure-A) and the
							provision of the National Building Code of India work in regard
							to constructions and safety.</p>
						<p>7.5 Fair Wages: The contractor shall pay not less than fair
							wages to labourers engaged by him during the contract period of
							the works (rules enclosed vide Annexure-B).</p>
						<p>7.6 Work in the Vicinity: The Commissioner/Chief Municipal
							Officer reserves the right to take up departmental work or to
							award work on contract in the vicinity without prejudice to the
							terms of contract.</p>
						<p>7.7 Best quality of construction materials. Materials of
							the best quality will be used as approved by the Executive
							Engineer/Chief Engineer . Where ever any material bears I.S.I.
							stamp(mark), this shall have first preference on other available
							accepted material(s)</p>

					</div>
				</div>
				<!---  09 Page Ends Here --->

				<!---  10 Page Starts Here --->
				<div class="page">
					<div class="subpage">

						<p>7.8 Removal of undesired persons: The contractor shall on
							receipt of the requisition from the Executive Engineer/Chief
							Engineer at once remove any person(s) employed by him on the work
							who in the opinion of the Executive Engineer/Engineer in Charge
							is/are unsuitable or undesirable.</p>
						<p>
							7.9 Amount due from contractor: Any amount due to the Government
							of Chhattisgarh/<b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							from the contractor on any account concerning work may be
							recovered from him as arrears of land revenue.
						</p>
						<p>7.10 Tools and Plants: - The contractor shall arrange at
							his own cost tools and plant required for the proper execution of
							the work. Certain plants may however be issued at the sole
							discretion of the Executive Engineer/Engineer in Charge and at
							the approved rate to the contractor as a special case.</p>
						<p>7.11 Right to Increase or decrease work: The
							Engineer-in-charge reserves the right to increase or decrease
							with- in the scope of work any item of the work during the
							currency of the contract as per Provision given in clause (13) of
							the conditions of contract.</p>

						<p>7.12 Time Schedule: The work shall be done by the
							contractor according to time schedule approved by the Engineer in
							charge.</p>
						<p>7.13 Time of Contract:- Time allowed for carrying out the
							work as entered in the N.I.T. shall be strictly observed by the
							contractor and shall be reckoned from the date of work order to
							commence the work after taking in to account the prescribed 15/30
							days of prepatory period</p>
						<p>7.14 Payment by Cheque: The payment will be made by
							cheques/e-payment only. No bank commission charges on realising
							such payments will be born by the Department</p>
						<p>7.15 Transport of materials: The contractor shall make his
							own arrangements for transport of all materials. The Executive
							Engineer/Chief Engineer is not bound to arrange for priority in
							getting wagons or any other materials though all possible
							assistance by way of recommendation will be given if it is found
							necessary in his opinion, if the recommendation proves to be
							in-effective, the contractor shall have no claim for any
							compensation on that account.</p>
						<p>
							7.16 The methodology and equipment, material, labour, transport
							to be used on the project shall be furnished by the contractor to
							the Engineer-in-charge well in advance of commencement of work
							and approval of the Engineer-in-charge obtained prior to its
							adoption and use. <br />The contractor shall give a trial run of
							the equipment for establishing its capability to achieve the laid
							down specifications and tolerance to the satisfaction of the
							Chief Engineer before commencement of work, if so desired by the
							Chief Engineer. <br />All equipment provided shall be of proven
							efficiency and shall be operated and maintained at all time in a
							manner acceptable to the Chief Engineer <br />No equipment or
							personnel will be removed from site without permission of the
							Chief Engineer.
						</p>
						<p>7.17 Work Programme and methodology of construction: The
							contractor shall furnish his programme of construction for
							execution of the work within the stipulated time and obtain the
							approval of the Engineer-in-Charge prior to actual commencement
							of work. For works costing more than 10 crores The contractor
							shall furnish his programme of construction for execution of the
							work within the stipulated time including the time and quantity
							schedule of material, transport, equipment, labour etc. The
							contractor shall also submit a statement of "Cash Flow" (as per
							the format enclosed) Together with methodology construction of
							each item of work and obtain the approval of the
							Engineer-in-Charge prior to actual commencement of work.</p>
						<p>7.18 Revised programme of work in case of slippage: In case
							of slippage from the approved work programme at any stage, the
							contractor shall furnish revised programme to make up the
							slippage within the stipulated time schedule and obtain the
							approval of the Engineer-in-Charge to the revised programme. Such
							progress report shall be submitted monthly (by 5th of each month)
							in the prescribed format in the tender documents.</p>

					</div>
				</div>
				<!---  10 Page Ends Here --->

				<!---  11 Page Starts Here --->
				<div class="page">
					<div class="subpage">

						<p>7.19 Documentation: The contractor will prepare drawing(s)
							of the work as constructed and will supply original with three
							copies to the Engineer-in-Charge who will verify and certify
							these drawings, finally Constructed drawing(s) shall then be
							prepared by the contractor and supplied in triplicate along with
							a microfilm in case of minor and major bridges and on tracing
							cloth in all other cases to the Engineer-in-Charge for record and
							reference purpose.</p>
						<p>7.20: The contractor shall have to provide a ruled
							duplicate register at site named "Site order book". It shall be
							in the custody of departmental supervisory staff. The Engineer-in
							Charge or his authorized representative shall record their
							instructions in this book, which shall be noted by the contractor
							or his authorized representative for compliance.</p>
						<p>7.21: If any item of work is found to be substandard but
							the Engineer-in-Charge is of the opinion that the same is
							structurally adequate and can be accepted at the reduce rate,
							then in such cases, the Engineer-in-Charge shall have to submit
							proposals for appropriate reduction of rates supported by an
							analysis, in justification thereof, through a letter to the
							Commissioner/Chief Municipal Officer concerned and obtain his
							approval expeditiously (ordinarily within 15 days). The approved
							analysis along with orders of the Commissioner/Chief Municipal
							Officer shall have to be appended IN the bills of the contractor.
						</p>

						<p>
							<b>8. <u>SPECIAL CONDITIONS:</u></b>
						</p>
						<p>(i) To be inserted in the N.I.T of a particular work if
							found necessary in the interest of the work.(Note:- Any such
							special condition cannot over rule or be on contravention of the
							prescribed clauses and conditions)</p>
						<p>8.1 Agreement: -</p>
						<p>8.1.1 Execution of agreement: The tenderer whose tender has
							been accepted (here in after referred to as the contractor,)
							shall produce an appropriate solvency certificate, if so required
							by the Commissioner/Chief Municipal Officer and will execute the
							agreement In the prescribed form, within a fortnight of the date
							of communication of the acceptance of his tender by the
							department. Failure to be so will result in the earnest money
							being forfeited to the
							${userSession.getCurrent().organisation.ONlsOrgname} and tender
							being cancelled.</p>
						<p>8.1.2</p>
						<ul>
							<li>(a)The contractor shall employ the following Technical
								Staff during the execution of work- <!-- <b>As per the
									requirement of work and as prescribed in Annexure- 11 of
									Pre-Qualification documents</b> -->
								<ul>
									<li>(i) One graduate engineer when the work to be executed
										is more than Rs. 25 lakhs.</li>
									<li>(ii) One diploma engineer when the cost of work to be
										executed is from Rs. 5 lakhs to 25 lakhs.</li>
								</ul>
							</li>
							<li>(b) The Technical Staff should be available at site and
								take instructions from the Engineer-in-Charge or other
								supervisory staff</li>
							<li>(c) Incase the contractor fails to employ the technical
								staff as aforesaid, the Executive Engineer shall have the right
								to take suitable remedial measures.</li>
							<li>(d) The contractor shall give the names and other
								details of the graduate engineer/diploma engineer to whom he
								intends to employ or who is under employment with him , at the
								time of agreement and also give his curriculum vitee.</li>
							<li>(e) The contractor shall give a certificate to the
								effect that the graduate engineer/diploma engineer is
								exclusively in his employment.</li>
							<li>(f) A graduate engineer or diploma engineer may look
								after more than one work in the same locality but the total
								value of such works under him shall not exceed Rs. 100 lakhs in
								the case of a graduate engineer and Rs. 50 lakhs in the case of
								a diploma engineer</li>
							<li>(g) It shall not be necessary for the firm/company whose
								one of the partner is a graduate engineer/diploma engineer to
								employ another graduate engineer / diploma engineer subject to
								the conditions provided under 8.1.2 (a),(b) and (f)</li>
						</ul>
					</div>
				</div>
				<!---  11 Page Ends Here --->

				<!---  12 Page Starts Here --->
				<div class="page">
					<div class="subpage">
						<ul>
							<li>(h) The Retired Assistant engineer who is holding a
								diploma may be treated at par with a Graduate for the operation
								of the above clause.</li>
						</ul>
						<p>
							<b>Note:- Such Degree or Diploma engineer must be always
								available on works site on day to day basis and actively
								supervise, instruct and guide the contractor’s works force and
								also receive instruction form the Departmental Engineers/Sub
								engineers.</b>
						</p>
						<p>&emsp;&emsp;In case the contractor fails to employ the
							above technical staff or fails to employ technical staff
							/personnel as submitted by the contractor in Pre qualification
							documents if prequalification is called and or the technical
							staff/personnel so employed are generally not available on work
							site and or does not receive or comply the instructions of the
							Departmental Engineers. The Executive Engineer shall
							recover/deduct from his bills, a sum of Rs. 2500/-per week of
							such default. If the default continues for more than 4 weeks then
							such default can be treated as "Fundamental Breach of Contract"
							and the contract can be terminated and action shall be taken
							under clause 3</p>

						<p>8.2 Conditions applicable for contract:-</p>
						<p>All the conditions of the tender notice will be binding on
							the contractors in addition to the conditions of the contract in
							the prescribed form :-</p>

						<p>Following documents annexed with this N.I.T shall form an
							integral part of the contract document.</p>
						<p>Annexure- "A" : Model Rules relating to labour water supply
							etc.</p>
						<p>Annexure-"B" : Contractor's labour regulations.</p>
						<p>
							Annexure-"C": <br />(a) Drawing (for buildings and Bridges)
						<ul>
							<li>(i) Site plan/location</li>
							<li>(ii) Plan, Cross section and elevation structural
								drawing, bar bending schedule etc.</li>
							<li>(iii) Circuit wiring and plumbing drawing (for Buildings
								only)</li>
							<li>(iv) Founding and formation levels, for C.D. Works</li>
						</ul>
						</p>
						<p>
							(b) For road work :- Index plan and locations of <br />&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;C.D.
							Works with type of C.D. <br />&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;(H.P.
							box culvert, flush/raised, <br />&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Causeway,
							slab culvert/ Bridge)
					</div>
				</div>
				<!---  12 Page Ends Here --->

				<!---  13 Page Starts Here --->
				<div class="page">
					<div class="subpage">

						<h2>
							<b>ANNEXURE - "A"</b>
						</h2>
						<p style="text-align: center;">
							MODEL RULES RELATING TO LABOUR, WATER SUPPLY AND SANITATION IN <br />LABOUR
							CAMPS
						</p>
						<p>Notes: These model rules are intended primarily for labour
							camps, which are not of a permanent nature. They lay down the
							minimum desirable standard, which should be adhered to standards
							in permanent or semi-permanent labour camps should not be
							obviously be lower than those for temporary camps.</p>
						<p>
							<b>1.</b> Location-: The camp should be located in elevated and
							well drained ground in the locality.
						</p>
						<p>
							<b>2.</b> Labour huts to be constructed for one family of 5
							persons each. The layout to be shown in the prescribed sketch.
						</p>
						<p>
							<b>3.</b> Hutting: The huts to be built of local material. Each
							hut should provide at least 20 sqm. of living space.
						</p>
						<p>
							<b>4.</b> Sanitary facilities: Latrines and urinals shall be
							provided at least 15 meters away from the nearest quarters
							separately for man and women specially so marked on the following
							scale.
						</p>
						<p>
							<b>5.</b> Latrines - Pit provided at the rate of 10 users or two
							families per scat, separate urinals as required as the privy can
							also be used for this purpose.
						</p>
						<p>
							<b>6.</b> Drinking Water - Adequate arrangement shall be made for
							the supply of drinking water. If practicable filtered and
							chlorinated supplies shall be arranged when supplies are from
							intermittent sources overhead storage tank shall be provided with
							capacity of five liters a person per day. Where the supply is to
							be made from a well, it shall conform to the sanitary standard
							laid down in the report of the Rural Sanitation Committee. The
							well should be at least 30 meters away from any latrine or other
							source of population. If possible, hand pump should be installed
							for drinking water from well. The well should be effectively
							disinfected once every month and the quality of water should be
							got tested at the public Health Institution between each work of
							disinfecting.
						</p>
						<p>
							<b>7.</b> Bathing and Washing - Separate bathing and washing
							place shall be provided for men and women for every 25 persons in
							the camp. There shall be one gap and space of 2 sqm. for washing
							and bathing. Proper drainage for the wastewater should be
							provided.
						</p>
						<p>
							<b>8.</b> Waste Disposal - (A) Dustbin shall be provided at
							suitable places in camp and the residents shall be directed to
							throw all rubbish into these dustbin. The dustbins shall be
							provided with cover. The contents shall be removed every day and
							disposed off by trenching.
						</p>
						<p>
							<b>9.</b> Medical facilities
						</p>
						<p>(A) Every camp where 1000 or more persons reside shall be
							provided with whole time Doctor and Dispensary. If there are
							women in the camp, a whole time nurse Shall be employed.</p>
						<p>(B) Every camp where less than 1000 but more than 250
							persons reside shall be provided with Dispensary and a part time
							Nurse/Midwife.</p>
						<p>(C) If there are less than 250 persons in any camp a First
							Aid Kit shall be maintained in-charge of whole time persons,
							trained in First Aid.</p>
						<p>&emsp;&emsp;All the medical facilities mentioned above
							shall be for all residents in the camp including a dependent of
							the worker, if any free of cost.</p>
						<p>Sanitary Staff - For each labour camp, there shall be
							qualified Sanitary Inspector and Sweeper should be provided in
							the following scales:</p>
						<p>(1) For camps with strength over 200 but not exceeding 500
							persons. One sweeper for every 75 persons above the first 200 for
							which 3 sweepers shall be provided.</p>

					</div>
				</div>
				<!---  13 Page Ends Here --->


				<!---  14 Page Starts Here --->
				<div class="page">
					<div class="subpage">

						<p>(2) For camps with strength over 500 persons one sweeper
							for every 100 persons above 1 st 500 for which 6 sweepers should
							be provided.</p>

						<h2>
							<b>ANNEXURE - "B"<br />CONTRACTOR'S LABOUR REGULATIONS
							</b>
						</h2>
						<p>The Contractor shall pay not less than fair wages to
							labourers engaged by him in the work.</p>
						<p>Explanation</p>
						<ul>
							<li>(a) "Fair Wages" means wages whether for time or piece
								works as notified during the period of execution of the works
								and where such wages have not been so notified the wages
								prescribed by the department in which the work is done.</li>
							<li>(b) The contractor shall not with standing the provision
								of any contract to the contrary, cause to be paid a fair wage to
								labourers indirectly engaged on the work i/e any labour engaged
								by his subcontractors in connection with the said work as if
								labourers had been immediately employed by him.</li>
							<li>(c) In respect of all labour directly or indirectly
								employed on the works or the performance of his contract, the
								contractor shall comply with or cause to be complied with the
								Labour Act, in force.</li>
							<li>(d) The Executive Engineer shall have the right to
								deduct from the money due to the contractor any sum required or
								estimated to be required for making good the less suffered by a
								worker or workers by reason of non-fulfillment of the conditions
								of the contract for the benefit of the workers nonpayment of
								wages or of deductions mode from his or their wages which are
								not justified by their terms of contract on non observance of
								regulations.</li>
							<li>(e) The contractor shall be primarily liable for all
								payments to be made under and for the observance of the
								regulations aforesaid without prejudices to his right to claim
								indemnity from his sub-contract.</li>
							<li>(f) The regulations aforesaid shall be deemed to be a
								part of this contract and any breach there of shall be deemed to
								be a breach of this contract.</li>
							<li>(g) The contractor shall obtain a valid license under
								the Contract (Regulation and Abolition) Act, 1970 and rules made
								there under by component authority from time to time before
								commencement of work, and continue to have a valid license until
								the completion of the work.</li>
						</ul>
						<p>Any failure to fulfill this requirement shall attract the
							penal provisions of this contract arising out of the recalled
							non-execution of the work assigned to the contractor.</p>

						<h2>
							<b><u>ANNEXURE - C</u></b>
						</h2>
						<p>(a) Drawing (for buildings and Bridges)</p>
						<ul>
							<li>(v) Site plan/location</li>
							<li>(vi) Plan, Cross section and elevation, structural
								drawing, bar bending schedule etc.</li>
							<li>(vii) Circuit wiring and plumbing drawing (for Buildings
								only)</li>
							<li>(viii) Founding and formation levels, also for C.D.
								Works</li>
						</ul>
						<p>
							(b) For road work :- Index plan and locations of <br />&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;C.D.
							Works with type of C.D. <br />&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;(H.P.
							box culvert, flush/raised, <br />&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;causeway,
							slab culvert/ Bridge with bench mark all levels and <br />&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;details
							of each
						</p>
						<p style="text-align: center;">
							<b><u>"Attach Prints"</u> N.A.</b>
						</p>
						<br />

					</div>
				</div>
				<!---  14 Page Ends Here --->

				<!---  15 Page Starts Here --->
				<div class="page">
					<div class="subpage">

						<h2>
							<b><u>Annexure-"D"</u></b>
						</h2>
						<p style="text-align: center;">
							Brief Specifications for major items of the work of construction
							of ------------ <br /> <small>(Mention the Items
								involved with details )</small>
						</p>
						<p style="text-align: center;">
							<b>Annexure - "D" (For percent rate tenders)</b>
						</p>
						<table Class="table table-bordered">
							<thead>
								<tr>
									<th colspan="5">Schedule of Items.</th>
								</tr>
								<tr>
									<th>S.No.</th>
									<th>S.O.R. Item No.</th>
									<th>Description of Item</th>
									<th style="width: 150px">Unit</th>
									<th style="width: 150px">Qty.</th>
								</tr>
							</thead>
							<tr>
								<td>1</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>2</td>
								<td colspan="4">Attached</td>
							</tr>
							<tr>
								<td>3 <br />etc.
								</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
						<ul>
							<li><b>Note:-</b> In case of any discrepancy in this table
								vis-à-vis the applicable S.O.R. the provisions contained in the
								applicable S.O.R. Shall prevail.</li>
						</ul>

						<br> <br>
						<p style="text-align: center;">
							<b>Annexure - "E" (For Item rate tenders)</b>
						</p>
						<br> <br>
						<table Class="table table-bordered">

							<thead>
								<tr align="center">
									<th colspan="9">Schedule of Items.</th>
								</tr>
								<tr>
									<th>S.No.</th>
									<th>S.O.R. Item No. (reference in any)</th>
									<th>Descripti on of Item</th>
									<th style="width: 150px">Unit</th>
									<th style="width: 150px">Qty.</th>
									<th style="width: 150px">Rate in Figure</th>
									<th style="width: 150px">Rate in Words</th>
									<th style="width: 150px">Rate in Words</th>
									<th style="width: 150px">Amount in figure</th>
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
							<tfoot>
								<tr>
									<th colspan="9">Grand Total Rs. ................ (in
										Figure)</th>
								</tr>
								<tr>
									<th colspan="9">And .......................(in words)</th>
								</tr>

							</tfoot>
						</table>
					</div>
				</div>
				<!---  15 Page Ends Here --->

				<!---  16 Page Starts Here --->
				<div class="page">
					<div class="subpage">
						<p></p>

						<p style="text-align: center;">
							<b>ANNEXURE F</b> <br /> <b>(Revised from Bank Guarantee
								Bond) <br /> (GUARATEE BOND)<br /> (In lieu of performance
								Security Deposit)<br /> (To be used by approved Scheduled bank)
								)
							</b>
						</p>
						<p>
							<b>1.</b> In consideration of the <b><u>${userSession.getCurrent().organisation.ONlsOrgname}</u></b>
							having agreed to exempt ............ (Herein after called the
							contractor (s) from the demand under the terms and conditions of
							an agreement dated ............ made between
							...................... for the work (Name of work) ............
							(here in after called the said Agreement) of security deposit for
							the due fulfilment by the said contractor (s) of the terms and
							conditions contained in the said agreements on production of a
							bank Guarantee for Rs. ............... Rupees .............. Only
							we. (.) .............. (hereinafter referred to as " the bank (at
							the request of the said contractor (s) do here by undertake to
							pay the <b><u>${userSession.getCurrent().organisation.ONlsOrgname}</u></b>
							an amount not exceeding Rs. .................... against any loss
							or damage caused to or would be caused to or suffered by the <b><u>${userSession.getCurrent().organisation.ONlsOrgname},</u></b>
							by reasons of any breach by the said contractor (s) of the terms
							or conditions contained in the said agreement.
						</p>
						<p>
							<b>2.</b> We (.) ..................... do here by undertake to
							pay the amount due and payable under this guarantee without any
							demur merely on demand from the <b><u>${userSession.getCurrent().organisation.ONlsOrgname}</u></b>
							stating the amount claimed is due by way of loss or damage caused
							to or would be caused to or suffered by the <b>${userSession.getCurrent().organisation.ONlsOrgname},</u></b>
							by reason of breach by the said contractor (s) of any of the
							terms or conditions contained in the said aggrements or by
							reasons of the contractor (s) failure to perform the said
							agreement, Any such demand made on the bank shall be conclusive
							as regards the amount due and payable by the bank under this
							Guarantee, Howere our liability under this Guarantee. shall be
							restricted to an amount not exceeding
							...............................
						</p>
						<p>
							<b>3.</b> We undertake to pay to the <b><u>${userSession.getCurrent().organisation.ONlsOrgname}</u></b>
							any money so demanded not with standing any dispute or disputes
							raised by the contractor (s) in any suit or proceedings pending
							before any court or tribunal relating thereto, our liability
							under this present being absolute and unequivocal. <br />&emsp;&emsp;The
							payament so made by us under this bond shall be a valid discharge
							of our liability for payment there under and the contractor (s)
							shall have no claim against us for making such payments.
						</p>
						<p>
							<b>4.</b> We (.) ............... further agree that the guarantee
							herein contained shall remain in full force and effect during the
							period that would be taken for the performance of said agreement
							and that it shall continue to be enforce able till all the dues o
							the <b><u>${userSession.getCurrent().organisation.ONlsOrgname}</u></b>
							under or by virtue of the said agreement have been fully paid and
							its claims satisfied or discharged or till the <b><u>${userSession.getCurrent().organisation.ONlsOrgname}.</u></b>
							certified that the terms and conditions of the said agreement
							have been fully and property carried out by the said contractor
							(s) and terms and conditions of the said agreement have been
							fully and property carried out by the said contractor (s) and
							accordingly discharged this guarantee, unless a demand to claim
							under this Guarantee is made on us in writing on or before the
							(here indicate a date which falls 9 months beyond the due date of
							completion of the work) ............................ we shall be
							discharged from all liability under the guarantee.
						</p>
						<p>
							<b>5.</b> We (.) ............................ further agree with
							the <b><u>${userSession.getCurrent().organisation.ONlsOrgname}</u></b>that
							the <b><u>${userSession.getCurrent().organisation.ONlsOrgname}
							</u></b>shall have the fullest liberty without our consent and with out
							affecting in any manner our obligation here under to vary any of
							the terms and conditions of the said agreement or to extend time
							of performance by the said contractor (s) from time to time or to
							postpone for any time or for time to time any of the powers
							excerciseable by <b><u>${userSession.getCurrent().organisation.ONlsOrgname},</u></b>
							against the said contractor (s) and to for bear or enforce any of
							the terms and conditions relating to the said agreement and we
							shall not be relieved from our liability by reasons of any such
							variations. or extension being granted to the said contractor (s)
							or for barnacle, act or commission on the part of the <b><u>${userSession.getCurrent().organisation.ONlsOrgname},</u></b>
							or any indulgence by the <b><u>${userSession.getCurrent().organisation.ONlsOrgname},</u></b>
							to the said contractor (s) or by any such matter or thing what so
							ever which under the lay relating to surities would but for this
							provision have effect of so relieving us.
						</p>

						<p>
							<b>6.</b> This guarantee will not be discharged due to the change
							in the constitution of the Bank or the Contractor (s).
						</p>

					</div>
				</div>
				<!---  16 Page Ends Here --->

				<!---  17 Page Starts Here --->
				<div class="page">
					<div class="subpage">

						<p>
							<b>7.</b> We (.) ............. lastly under take not to revoke
							this gurarantee during its currency except with the previous
							consent of the <b><u>${userSession.getCurrent().organisation.ONlsOrgname}</u></b>
							writing :-\Dated the ............................... day of
							......................... for ()
							................................. <br />(>) indicate the Name of
							the Bank ...................................
						</p>
						<br />
						<p style="text-align: center;">
							<b>Annexure-G<br /> SPECIAL CONDITIONS OF N.I.T.<br /> <u>(Reference
									Clause 8 of NIT)</u></b>
						</p>
						<p>
							<b>(1)</b> Additional performance security (APS) shall be
							deposited by the successful bidder at the time if signing of
							agreement when the bid amount is seriously unbalanced i.e. less
							than the estimated cost by more than 10%. In such an event the
							successful bidder will deposit the additional performance
							security (APS) to the extent of difference of 90% of the PAC and
							bid amount in the shape of FDR , in favour of the
							Commissioner/Chief Municipal Officer before singing the
							agreement.
						</p>
						<p>The same shall be refunded along with the normal S.D. after
							completion of the work. If the contractor fails to complete the
							work or left the work incomplete, this additional performance
							security (APS) , shall be forfeited by the department , & the
							agreement shall be terminated and the action shall be taken
							accordance with clause 3 of the agreement . In case the tenderer
							/ Contractor refuse to deposit Additional performance security
							(APS) then his bid will be rejected by the sanctioning authority
							and earnest money shall be forfeited.</p>
						<p>
							<b>(2)</b> (2) If the tenderer, whose tender has been accepted,
							and after signing the agreement, (i) does not start regular
							actual physical items of work within 25% (twenty five percent) of
							the time allowed for completion, or abnormally slowdown the work
							or (iii) abandons the work, or (iv) merely goes on applying for
							extension of time; the Commissioner/Chief Municipal
							Officer/Engineer in Charge shall serve a "show cause" notice with
							details to the contractor in this regard and if the contractor
							does not reply, or if his reply is considered not satisfactory
							(at the sole discretion of the Commissioner/Chief Municipal
							Officer/Engineer incharge), his earnest money and the performance
							security money or the Bank Guarantee in this regard shall be
							forfeited in favour of the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							.If the contractor has committed a similar default on earlier
							occasion (s) in previous three consecutive years the contractor
							shall be debarred from participating in any future tender of any
							working in <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>t
							for a period of 2 (two) years from the date of such order, by the
							authority which had registered him/her.
						</p>
						<p>Such orders & action shall be final binding and conclusive

						</p>
						<p>
							<b>(3) <u>Detailed programme of Construction:</u></b>
						</p>
						<p>(i) Within 15 days of issue of order to start work, the
							contractor shall submit in the prescribed proforma a detailed
							construction programme month wise mentioning start and completion
							of each item/event involved in the due performance of the
							contract For contract more than 10 Crores Contractor shall also
							submit detailed programme monthwise for</p>
						<ul>
							<li>(a) Materials procurement</li>
							<li>(b) Their transport arrangement to worksite with details
								of No. of truck/tippers</li>
							<li>(c) Detailing of construction plants & equipments</li>
							<li>(d) Cash flow/revised Cash flow</li>
						</ul>

						<p>
							(ii) The contractor shall submit in the first week of each month
							a statement of "<b>target vis-a-vis actual performance</b>" of
							each item/event with slippage, if any; mentioning reasons of
							slippage and proposal for revised construction programme to
							complete the same in targeted date or validly extended date.
							Failure to submit this monthly statement for 4(four) months can
							be treated as "fundamental Breach of Contract" and can result in
							invoking clause 3 of the conditions of contract.
						</p>

					</div>
				</div>
				<!---  17 Page Ends Here --->
				<!---  18 Page Starts Here --->
				<div class="page">
					<div class="subpage">

						<p>
							<b>(4). Performance Guarantee:</b>
						</p>
						<p>
							(i) (i) The contractor shall also be responsible for performance
							of work carried out by him for a period of 12 (Twelve) month
							beyond the completion of work for which performance security has
							to be furnished by him @ 5%(five percent)of amount of contract.
							For this purpose contractor has to submit to the department a
							Bank Guarantee of 5% amount of the value of work done on every
							running and final bill payable to him. If contractor fails to
							submit bank guarantee of 5% amount of the gross bill, then 5%
							amount of bill shall be deducted from his running and final bill
							payment. However, the contractor can get refund of such
							performance cash security amount deducted if he submits
							appropriate bank guarantee valid for the period as stated above
							or 12 (Twelve) month after actual completion. <br />&emsp;&emsp;If
							require, the Commissioner/ Chief Municipal Officer shall ask the
							contractor to extend the validity period of the Bank Guarantee(s)
							for such period which he considers it proper and the contractor
							shall extend the validity period of such Bank Guarantee
							accordingly. If the contractor fails to extend the period
							accordingly, the Commissioner/ Chief Municipal Officer shall
							encash the B.G. before the expiry of the validity period.
						</p>
						<p>(ii) The contractor shall have to carry out all necessary
							"Rectification" of defects noticed, caused due to any reasons at
							his own cost within such reasonable period mentioned in such
							communication notice from the Commissioner/Chief Municipal
							Officer/ Engineer in Charge to him.</p>
						<p>(iii) Failure of the contractor to rectify the defects
							properly in the given period, it shall be open for the
							Commissioner/Chief Municipal Officer/Engineer in Charge to get
							the defect(s) rectified either departmentally or through other
							agency (without calling any tender /quotation) and recover the
							actual cost plus 15 % (fifteen percent) of such cost from the
							contractor from any sum, in any form, and available with the
							department or can be recovered as "Arrears of Land Revenue"</p>
						<p>(iv) After two years of completion of construction, 50%
							(fifty percent) of available performance Bank guarantee shall be
							returned to the contractor subject to the satisfaction of the
							Executive Engineer.</p>
						<p>
							(v) Remaining <b>performance Bank</b> Guarantee as would be
							remaining (after recovery all cost plus 15% (Fifteen percent) for
							rectification of defects, if done by the department or through
							other agency) shall be returned <b>after 3 years</b> of
							completion. <br />The performance guarantee will be in addition
							to the normal security to be deducted as per clause 1 of
							agreement for the execution of contract.
						</p>
						<p>
							<b>(5)</b> T(5) The tenderer/contractor shall give in advance
							authority letter(s) in favour of the Commissioner/ Chief
							Municipal Officer, authorizing him to get all Bank's Fixed
							Deposit receipts, Bank Guarantees (either normal security deposit
							and or for performance security) to get these Bank Receipts and
							Guarantee deeds verified and got confirmed from the concerned
							Bank. It will be only after getting such confirmation that the
							Commissioner/ Chief Municipal Officer shall pay any amount
							accordingly or refund the equal amount for which BG submitted has
							been duly verified and confirmed.

						</p>
						<p>
							<b>(6)</b> The contractor shall not remove minor mineral from
							borrow areas, quarries without prior payment of Royalty charges.
						</p>
						<p>
							<b>(7) For Bituminous Road Works</b>
						</p>
						<ul>
							<li>(a) Bitumen of required penetration grade or emulsion
								shall be procured by the contractor directly from any or all of
								the Govt. Oil Company viz. Indian Oil Company (IOCL), Hindustan
								Petroleum (HPCL) and Bharat Petroleum Company (BPCL)</li>
						</ul>

						<ul>
							<li>(b) It shall be obligatory on the contractor to submit
								within one week of receipt of Bitumen, original first copy of
								Bitumen invoice(s) (meant for the Buyer/Customer) (not other
								copies meant for Storage, Transporter etc. etc.) to the
								concerned Division or Divisions and get the invoice duly stamped
								and see that suitable entry is recorded by the Division of its
								use with grade, quantity proposed to be used in particular
								-contract agreements (s)/Division. On the original invoice the
								contractor shall have to write "...... MT quantity or Bitumen of
								this invoices is proposed to be used in agreement No.
								.............................. of .................... of
								..................... Division and ...............</li>
						</ul>
					</div>
				</div>
				<!---  18 Page Ends Here --->
				<!---  19 Page Starts Here --->
				<div class="page">
					<div class="subpage">
						<ul>
							<p>
								quantity in agreement No. ............................ of
								................ of this or (other named) division in that
								divisions agreement No. ................ of
								......................... and sign the same. Contractor(s) shall
								also furnish a certificate that "This is to certify that I/We
								have submitted the original and true bill(s) & I am responsible
								for its veracity" <br />The sub divisional officer there after
								shall countersign the same and submit the invoice(s) to the
								Executive Engineer who shall cause the same to be photocopied
								and return the photocopy duly certified to the contractor. All
								originals invoices shall be retained by the Executive Engineer
								till the payments final bill. There after the contractor shall
								return the attested photocopies of all invoices and take back
								the original invoices of Bitumen.
							</p>
							<li>(c) The contractor shall have to install hot mix plant
								as per clause 504.3.4 of MORTH specification 15 days before
								starting of bituminous work nearer to the site of work, so as to
								maintain the temperature of hot mixed materials at work site as
								per MORTH norms. In case of failure in installing the hot mix
								plant, it shall be treated as breach of contract and penalty
								shall be imposed under agreement clause 38</li>
							<!-- <li>(d) For probable amount of contract upto Rs. Five Crore,
								Contractor shall submit the certificate of availability with him
								(owned or leased or by procurement against mobilization
								advances) regarding computeraized hot mix plant. Sensor
								Paver/mechanical paver Vibratory roller, {for 50mm or more
								thickness of B.M./D.B.M. (with M.S.S./S.D.B.C. & B.C. } and
								other plants and machineries duly certified by Executive
								Engineer or Equivalent Officer (Certificate shall not be older
								than 24 months), other wise tender will be disqualified while
								opening . <br />&emsp;&emsp;For probable amount of contract
								more than Rs. Five Crore the conditions of prequalification
								document shall be followed .
							</li>
							<li>(e) Test for binder content <br />&emsp;&emsp;It will
								be mandatory and binding to the contractor to get checked every
								alternate load/lot of mix material at hot mix plant site in the
								presence of department’s authorized personnel.
							</li>
							<li>(f) Bituminous work after sunset shall be allowed only
								with the specific written permission of the Engineer-in-Charge
								who shall then be fully responsible for the strict quality
								control of the work.</li> -->
							<li>(d) "Contractor shall submit the certificate of
								availability with him (Owned or leased or by procurement against
								mobilization advances) regarding computerized hot mix plant,
								Sensor paver/mechanical paver, Vibratory Roller [ for 50 mm or
								more thickness of B.M./D.B.M. (with M.S.S./S.D.B.C. & B.C.)] and
								other plants and machineries duly certified by Executive
								Engineer or Equivalent officer, along with the EMD/FDR envelope,
								otherwise tender will be disqualified while opening.</li>
						</ul>
						<!-- <p>
							<b>(8)</b> Incentive bonus clause 5.3 of appendix 2.13, appendix
							2.14 and the clause 4(performance gaurantee) of (Annexure G)
							special condition of NIT are not applicable for ordinary repairs,
							supply of materials, survey and envestigation of road/bridge/
							building works.
						</p> -->
						<p>
							<b>(8)</b> The contractor has to fix reflecting information
							board, size 120 cms X 90 cms. One at starting point of the road
							and another from end point of the road describing the details of
							work as instructed by E. E. at his costs.
						</p>
						<!-- <p>
							<b>(10)</b> Cess @ 1% (One Percent) shall be deducted at source,
							from every bill of contractor by the Executive Engineer
							under"Building and other construction for Workers Welfare, Cess
							Act 1996".
						</p>
						<p>
							<b>(11)</b> It is mandatory for the construction(s) to get
							himself/themselves registered with"C.G. Building and other
							construction Welfare Board"as soon as the work order is issued to
							him/them for the work amounting to Rs.10.00 (Ten) lakhs and above
							and submit a copy of the same to the concern Executive Engineer,
							otherwise no payment will be made under the contract.
						</p>

						<p>
							<b>(12)</b> Contractors are advised to go through the Notice
							Inviting tenders & the tender/PQ document thoroughly.
							Certificates, annexures, enclosures as mentioned in the document
							will have to be submitted by the tenders strictly in the
							prescribed format, at the time of submission of
							Technical/Financial bid, failing which the contractor shall
							disqualify for the work & his financial offer shall not be opened
							and no representation, appeal or objection, what so ever in this
							regard shall be entertained by the department.
						</p>
						<p>
							<b>(13)</b> It is mandatory to submit online by the contractor
							the list of on-going works/works in hand. If any work is found
							delayed beyond one year from the stipulated date of completion
							due to contractor's fault, the contractor will be disqualified
							for the reason of poor performance.
						</p>
						<p>
							<b>(14)</b> jkT; ljdkj ds fdlh Hkh foHkkx esa dkyhlwph ;k fMckj
							fufonkdkjksa dks foHkkx ds fdlh Hkh fufonk esa Hkkx ysus dk
							vf/kdkj ugha gksxk A
						</p> -->
						<p>
							<b>(9)</b> In case of conflict between "General condition of
							contract and the special condition" the terms of special
							conditions shall prevail.
						</p>
						<!-- <p>
							<b>(16)</b> For Road Construction Work within a radius of <b>300
								km</b> from any Thermal Power Plant, the use of fly ash shall
							invariably be done in embankment construction in confirmation to
							guidelines given in IRC: SP-58-2001. <b>(Amendment issued
								vide Deputy Secretary Govt. of Chhattisgarh Public Works
								Departments, Raipur Memo. NO. 3298/2286/2016/19 /Tech-1,Dates
								15.08.2016)</b>
						</p>
						<p>
							<b>(17)</b> In this document Pre-Contract Integrity Pact is added
							as Annexure H, as per the guidelines issued by Government vide <b>Letter
								no- 243@fo@fu@pkj@2013 u;k jk;iqj] fnukad 06 tqykbZ] 2013<br />(Amendment
								issued vide Govt. of Chhattisgarh Public Works Departments,
								Raipur Order No. F 21-5/T/19/12/Tender New Raipur Dates
								06.08.2016)
							</b>
						</p>
						<p>
							<b>(18)</b> "Apart from taking other safety measures the
							contractor shall observe and ensure the following safety
							regulations:
						</p>
						<ul>
							<li>a. He shall provide safety belts, helmets, flouroscent
								jackets to all the workers deployed on the site. He shall
								arrange the same for all the officials/persons also visiting the
								site.</li>
							<li>b. He shall provide strong and stout scaffolding,
								centering and shuttering at site-the design of which shall be
								got approved from the engineer in charge. The Contractor shall
								be held fully responsible for any accident if it occurs due to
								not following the above.</li>
							<li>c. The complete site shall be fully lighted, watched,
								barricated and guarded untill completion of work and taking over
								so as to forbid entry of any tresspasser.</li>
							<li>d. All the excavated trenches (for foundations/septic
								tanks), bore holes shall be kept properly guarded/fenced, all
								the time, to avoid falling of any persons their in.</li>
							<li>e. The periphery area of building inside and outside
								shall be provided with temporary shed structures, so as to safe
								guard the workers/officials passing by it from anything falling
								from the work going on in the upper stones.</li>
							<li>f. In case of road works the widening portion when
								excavated shall be provided with all safety measures like safety
								ribbons, guard stones, cautionary sign boards (reflective)</li>
							<li>g. The contractor shall arrange good strong ladders etc
								to facilitate the departmental officials in supervision.</li>
							<li>h. The contractor shall get his workers fully insured
								their life against accidents.</li>
							<li>i. The contractor shall work in such a manner that
								neither, the public & nor any property (govt./public/private) is
								put to risk.</li>
							<li>j. On the roads under contructions, the contractor shall
								ensure that no construction material is kept/stacked on the road
								carriage way/shoulder.''</li> -->
						</ul>
					</div>
				</div>
				<!---  19 Page Ends Here --->

				<!---  20 Page Starts Here --->
				<div class="page">
					<div class="subpage">
						<!-- <ul>
							<li>k. Comply with all applicable safety regulations.</li>
							<li>l. Take care for the safety of all persons entitled to
								be on the site.</li>
							<li>m. Use reasonable efforts to keep the site and works
								clear of unnecessary obstruction so as to avoid danger to these
								persons.</li>
							<li>n. Provide any temporary works (including roadways,
								footways, guards and fences) which may be necessary, because of
								the execution of the works, for the use and protection of the
								public and of owners and occupiers of adjacent land.</li>
							<li>o. The contractor shall be fully responsible for the
								adequate safety of all site operations and method of
								constructions.</li>
							<li>p. The contractor shall submit to the Engineer a
								detailed proposal covering safety measures proposed to be
								adopted at site.</li>
							<li>q. Breach of safety provision by the Contractor and his
								employees shall constitute a sufficient cause for action.</li>
							<li>r. Adequate precautions shall be taken to prevent
								accidents from electric cables, while digging operation is
								underway.</li>
							<li>s. Workers employed on bituminous works, stone crushersm
								concrete batching plants etc. shall be provided with protective
								goggles, gloves, gumboots etc.</li>
							<li>t. Those engaged in welding work shall be provided with
								welder protective sheilds.</li>
							<li>u. All display boards shall be retro reflective material
								and of sizes mentioned in the drawing.</li>
							<li>v. All vehicles will have reverse horns.</li>
							<li>w. In addition, if directions are given by the
								“Engineer” to augment the safety measures, the Contractor has to
								abide by his directions.</li>
							<li>x. A safety officer shall be nominated by the Contractor
								to prepare safety programme and after getting the approval from
								the Engineer, oversee the safety arrangement at site.</li>
						</ul>
						<p>
							<b>These special conditions will supersede any thing contrary
								to it in the tender document</b>
						</p> -->

						<h2>
							<b>SPECIAL CONDITION</b>
						</h2>
						<br> <br>
						<p>1. In the event of withdrawing his/her after before the
							expiry of the period of validity of offer of failing to execute
							the agreement as required by condition No. 8.1.1 of the notice
							inviting tender (N.I.T) he/ she will not be entitled to tender
							for this work in case of recall of tenders. In addition to
							forfeiture of his/her earnest money as per provisions of
							condition No. 4.7 and 8.1.1 of N.I.T as may be applicable for the
							work, the registering authority will demote the contractor/ firm
							for a period of one year. If the tenderer has committed a similar
							default of earlier occasion (s) as will, then such demotion in
							registration will be permanently.</p>
						<p>This special condition will supersede anything contrary to
							it in the tender document.</p>

						<p>2. Cess @ 1% (One percent) shall be deducted at source.
							From every bill of contractor by the Commissioner/Chief Municipal
							Officer under "Building and other construction for Workers
							Welfare, Cess Act 1996 "</p>
						<p>3. It is mandatoryfor the contractor (s) to get
							himself/themselves registered with “C.G. Building and other
							construction Welfare Board" as soon as the work order is issued
							to him/ them for the work amounting to Rs. 10.00 (Ten) Lakhs and
							above and submit a copy of the same to the same to the concern
							Engineer in Charge, otherwise no payment will be made under the
							contract.</p>

						<p>4. Contractors are advised to go through the Notice
							Inviting tenders & the tender/PQ/Bid Capacity document
							thoroughly. Certificates, annexures, enclosures as mentioned in
							the document will have to be submitted by the tenders strictly in
							the prescribed format at the time of submission of Technical/
							Financial bid, failing which the contractor shall disqualify for
							the work & his financial offer shall not be opened and no
							representation appeal or objection, what so ever in this regard
							shall be entertained by the department.</p>
					</div>

				</div>
				<!---  20 Page Ends Here --->
				<!---  21 Page Starts Here --->
				<div class="page">
					<div class="subpage">
						<h2>
							<b>Name of work</b>
							</p>
							<table
								style="font-size: 0.92em; font-weight: 500; text-align: left;">
								<tr>
									<td width="400">NAME OF DIVISION</td>
									<td width="400">NAME OF CONTRACTOR</td>
									<td width="400">AGR. No.</td>
								</tr>
								<tr>
									<td>....................................................<br />&nbsp;
									</td>
									<td>....................................................<br />&nbsp;
									</td>
									<td>..........................................<br />&nbsp;
									</td>
								</tr>
								<tr>
									<td>DATE OR WORK ORDER</td>
									<td>DUE DATE OF COMPLETION</td>
									<td>EXTENSIONS GRANTED/APPLIED UP TO</td>
								</tr>
								<tr>
									<td>....................................................</td>
									<td>....................................................</td>
									<td></td>
								</tr>
							</table>
							<br />

							<p style="text-align: center;">DETAILD WORK PROGRAMME -
								ORIGINAL/1ST Revision/2nd Revision/......... Revision)</p>

							<h2>WORK ITEMS</h2>
							<table>
								<thead>
									<tr>
										<th rowspan="2"></th>
										<th rowspan="2"></th>
										<th rowspan="2"></th>
										<th colspan="11"></th>
										<th rowspan="3"></th>
									</tr>
									<tr>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
									</tr>
								</thead>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
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
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
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
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
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
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
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
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
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
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
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
							<!---  21 Page Ends Here --->
					</div>
				</div>
				<%-- <div class="text-center clear padding-10">
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backTenderHomePage();" id="button-Cancel"><i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div> --%>
			</form:form>
		</div>
	</div>
</div>