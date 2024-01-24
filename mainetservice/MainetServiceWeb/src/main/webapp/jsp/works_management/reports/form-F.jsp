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
	src="js/works_management/reports/form-F.js"></script>

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

			<!---  Report Page Start Here --->
			<div class="report" id="myCanvas">

				<!---  01 Page Start Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h1>
							<b>${userSession.getCurrent().organisation.ONlsOrgname}</b><br>
							<b>(APPENDIX 2.18)<br />FORM F - TENDER FOR A LUMPSUM
								CONTRACT
							</b>
						</h1>
						<p>
							I / We do hereby tender to execute the whole of the work
							described in the<br /> <br /> Drawing Nos:
							______________________________________________________________________<br />
							<br /> and according to the annexed specifications as signed by:
							__________________________________<br /> <br /> and dated:
							________________________________________________________________________<br />
							<br /> for the sum of Rs.: <b>${command.tenderWorksForms.tenderAmount}</b><br />
							<br /> Rupees : <b>(${command.tenderWorksForms.amountInStringFormat})</b><br />
							<br /> and should this tender be accepted . I/We do hereby agree
							and bind myself/ourselves to abide by and fulfill all the
							conditions annexed to the said specification or in default
							thereof to forfeit and pay to the Governor of C. G. the penalties
							of sums of money mentioned in the said conditions, viz:
						</p>
						<br /> <br />
						<p>
							<span class="pull-left">Dated:</span> <span class="pull-right">Tenderer's
								Signature<br />Address
								.......................................................<br />
								....................................................................
							</span>
						</p>
						<p>
							<span class="pull-left"><b>Witness:</b><br /> <br />Address:
							</span>
						</p>
						<br /> <br /> <br /> <br />
						<p>The above tender is hereby accepted by me on behalf of the
							Governor of Chhattisgarh.</p>
						<p>The _________ 200</p>
						<br />
						<p>
							<span class="text-center pull-right">(Designation)<br />
								<b>SIGNATURE OF AUTHORITY BY WHOM<br />THE TENDER IS
									ACCEPTED
							</b></span>
						</p>
						<br /> <br /> <br /> <br /> <br />
						<hr>
						<p>
							<b>To be expressed in words and figures.</b>
						</p>
						<hr>
					</div>
				</div>
				<!---  01 Page Ends Here --->
				<!---  02 Page Start Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>CHAPTER - I<br />CONDITION OF CONTRACT
							</b>
						</h2>
						<p>
							<b>1.1:</b> The person whose tender may be accepted (hereinafter
							called the contractors which expression shall unless excluded by
							or repugnant to the context include his heirs executers,
							administrators representatives and assigns) shall permit
							Government at the time of making any payments to him for the
							value of work done under the contract to deduct the security
							deposit as under.
						</p>
						<p>The Security Deposit to be taken for the due performance of
							the contract under the terms & conditions printed on the tender
							form will be the earnest money plus a deduction of 5 percent from
							the payment made in the running bills, till the two together
							amount to 5 percent of the cost of work put to tender or 5
							percent of the cost of the works executed when the same exceeds
							the cost of work put to tender</p>
						<p>
							<b>1.2:</b> The Contractors is /are to provide everything of
							every sort and kind (with the exception noted in the schedule
							attached) which may be necessary and requisite for the due and
							proper execution of the several works included in the contract
							according to the true intent and meaning of the drawings and
							specifications taken together, which are to be signed by the <span
								class="colour-report"><b>Executive Engineer
									${userSession.getCurrent().organisation.ONlsOrgname}</b></span> herein
							after called the E.E.) and the Contractor (s) whether the same
							may not be particularly described in the specifications or shown
							on the drawings, provided that the same are reasonably and
							obviously to be inferred there from and in case of any
							discrepancy between the drawings and the specifications the E.E.
							is to be decide which shall be follows :
						</p>
						<p>
							<b>1.2A:</b> The Contractor (s) is/are to set out the whole of
							the works in conjunction with an officer, to be deputed by the
							E.E. and during the progress of the works, to amend on the
							requisition of the E.E., any errors which may arise therein and
							provide all the necessary labours, and materials for so doing.
							The Contractor(s) is/are to provide all plant, labour and
							materials (with the exceptions noted in the schedule attached)
							which may be necessary and requisite for the works. All the
							materials and workmanship are to be the best of their respective
							kinds. The Contractor(s) is/are to leave the works in all aspects
							clean and perfect at the completion thereof.
						</p>
						<p>
							<b>1.2B:</b> In respect of all bearings, hinges or similar parts
							intended for use in the superstructure of any bridge, the
							Contractor shall, whenever required, in the course of
							manufacture, arrange and afford all facilities for the purpose of
							inspection and test of all or any of these parts and the material
							use therein to any officer of the Directorate of Inspection of
							the Ministry of works, production and supply of the Governor of
							India and such bearings, hinges or similar parts shall not be
							used in the superstructure of any bridge except on production of
							a certificate of acceptance thereof from the Directorate of
							Inspection. All inspection charges shall be payable by the
							Contractor.
						</p>
						<p>
							<b>1.3:</b> Complete copies of the drawings and specification
							signed by the E.E. are to be furnished by him to the
							Contractor(s) for his/their own use, and the same or copies
							thereof are to be kept on buildings in charge of the
							Contractor(s) agent who is to be constantly kept on the ground by
							the Contractor(s) and to whom the instructions can be given by
							the E.E. The Contractor(s) is/are not to sublet the works or any
							part thereof without the consent in writing of the E.E.
						</p>
						<p>
							<b>1.4:</b> The E.E. is to have at all times access to the works
							which are to be entirely under his control He may require the
							Contractor(s) to dismiss any person in the Contractor (s) employ
							upon the works who may be incompetent or misconduct himself and
							the Contractor (s) is/are forthwith to comply with such
							requirements.
						</p>
						<p>
							<b>1.5:</b> The Contractor (s) is/are not be vary or deviate from
							the drawings or specification or execute any extra work of any
							kind whatsoever unless upon the authority of E.E. to be
							sufficiently shown by any order in writing by any plan or
							drawings expressly given and signed by him as extra or variation
							or by any subsequent written approval signed by him. In cases of
							daily labour all vouchers for the same are to be delivered to the
							E.E. or the officers-in-charge at least during the week following
							that in which the workmen have been done and only such day work
							is to be allowed for as such as may have been authorised by the
							E.E. to be so done unless the work cannot from its character be
							properly measured and valued. The drawings in respect of which
							this contract is drawn up provide for a
						</p>
					</div>
				</div>
				<!---  02 Page Start Here --->
				<!---  03 Page Start Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>minimum depth of foundations for good soil, Any Extra depth
							will be measured as an extra when the foundation trenches have
							been opened up and will be paid for in addition to the sum
							contracted for the completed work.</p>
						<p>
							<b>1.6:</b> Any authority given by the E.E. for any alterations
							or additions in or to work is not to vitiate the contract but all
							additions in or to work is not to vitiate the contract but all
							additions, omissions or variations made in carrying out the works
							are to be measured and valued and certified by the E.E. and added
							to or deducted from the amount of the contract, as the case may
							be, at rates in force in the <span class="colour-report"><b>Engineer-in-Chief
									PHED Chattisgarh Raipur in force from 07.02.2013 for Water
									Supply & Sewerage</b></span> In such cases in which rates do not exist,
							the superintending Engineer will fix the rates to be paid.
						</p>
						<p>
							<b>1.7:</b> All work on materials brought and left upon the
							ground by the contractor(s) on his/their orders for the purpose
							of forming part of the works are to be considered to be the
							property of the <span class="colour-report"><b>Commissioner/CMO
									Chhattisgarh</b></span> and the same are not to be removed or taken any by
							the Contractor's or any other person without the special license
							and consent in writing of the E.E., but the <span
								class="colour-report"><b>Commissioner/CMO
									Chhattisgarh</b></span> is not be in any way answerable for any loss or
							damage which may happen to or in respect of any such work or
							materials either by the same being lost or stolen or injured by
							weather of otherwise.
						</p>
						<p>
							<b>1.8:</b> The E.E. has full power to require the removal from
							the premises of all materials which, in his opinion, are not in
							accordance with the specification and in case of default the E.E.
							is to be at liberty to employ other persons to remove the same
							without being answerable or accountable for any loss or damage
							that may happen or arise to such materials. The E.E. is also to
							have full power to require other proper materials to be
							substituted and in case of default the E.E. may cause the same to
							be supplied and all costs which any attend such removal and
							substitution or to be borne by the Contractor (s).
						</p>
						<p>
							<b>1.9:</b> If in the opinion of the E.E. any of the works, are
							executed with improper materials or defective workmanship, the
							Contractor(s) is/are when required by the E.E. forthwith to
							re-execute the same and to substitute proper materials and
							workmanship and in case of default of the Contractor(s) in so
							doing within a week the E.E. is to have full power to employ
							other person to re-executed the work and the cost thereof shall
							be borne by the Contractor(s).
						</p>
						<p>
							<b>1.10:</b> Any Defect's shrinkage of other faults which, may
							appear within performance period from the completion of the work
							arising out of defective or improper materials or workmanship or
							by any other reason are upon the direction of the E.E. to be
							amended and made good by the Contractor (s) at his / their own
							cost unless the E.E. shall decide that he/they ought to be paid
							for the same and in case of default the Governor of C.G. may
							recover from the Contractor (s) the cost of making good the works
							as per note <b>(13) of</b> additional special conditions
						</p>
						<p>
							<b>1.11:</b> From the Commencement of the work to the completion
							of the same, they are to be under the contractor(s) charge. The
							Contractor (s) is/are to be held responsible for and to make good
							all injuries, damages and repairs occasioned or rendered
							necessary to the same by fire or other causes and they are to
							hold the Governor of C.G. harmless from any claims for injuries
							to persons or for structural damage to property happening from
							any neglect, default, want of proper care of misconduct on the
							part Contractor(s) or of any one in his/their employ during the
							execution of the works.
						</p>
						<p>
							<b>1.12:</b> The E.E. is not have full power to send workmen upon
							the premises to execute fittings and other works not included in
							the Contract for whose operation Contractor (s) is/are to afford
							every reasonable facility during ordinary working hours, provided
							that such operation shall be carried on in such manner as not to
							impede the progress of the work included in the contract but the
							Contractor(s) is/are not to be responsible for any damage which
							may happen to or be occasioned by any such fittings or other
							works.
						</p>
					</div>
				</div>
				<!---  03 Page Ends Here --->
				<!---  04 Page Start Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>1.13:</b> The works comprised in this tender are to be
							commenced immediately upon receipt of order of commencement given
							in writing by the E.E. The whole work, including all such
							addition and variations as aforesaid (but excluding such, if any,
							as may have been postponed by an order from the E.E.) shall be
							completed in every respect within <span class="colour-report"><b><u>08
										months</u></b></span> from the reckoned date (The period will be reckoned
							from the 15 days after the date of Work order in case of
							completion period is up to six months and 30 days in case of
							completion period is more than six months The work shall
							throughout the stipulated period of contract be proceeded with
							all due diligence, keeping in view that time is the essence of
							the contract. The contractor shall be bound in all cases, in
							which the time allowed for any work exceeds one month, to
							complete 1/8th of the whole work before 1/4th of the whole time
							allowed under the contract has elapsed, 3/8th of the work before
							1/2 of such time has elapsed and 3/4th of the work before 3/4th
							of such time has elapsed. In the event of the contractor failing
							to comply with the above conditions, the Executive Engineer shall
							levy on the contractor, as compensation an amount equal to: 0.5%
							(zero point five percent) of the value of work (contract sum) for
							each week of delay, provided that the total amount of
							compensation under the provision of the clause shall be limited
							to 6% (six percent) of the value of work. (Contract sum)
						</p>
						<p>Provided further that if the contractor fails to achieve
							30% (thirty percent) progress in 1/2 (half) of original or
							validly extended period of time the contract shall stand
							terminated after due notice to the contractor and his contract
							finalised</p>
						<p>If the contractor shall desire an extension of time for
							completion of work on the ground of his having been "UNAVOIDABLY"
							hindered in its execution or on any other ground, he must apply
							giving all and complete details of each of such hindrances or
							other causes in writing, to the Executive Engineer positively
							within 15 days of occurrence of such hindrance(s) and seek
							specific extension of time (period from ....................to
							....................). If in the opinion of Executive Engineer,
							such reasonable grounds are shown, the Executive Engineer shall
							himself grant extension of time, if the extension of time sought
							by the contractor is for one month or 10% (ten percent) of the
							stipulated period of completion, whichever is more. If the
							extension of time sought is more than above period mentioned,
							then the Executive Engineer shall refer the case to the
							Superintending Engineer with his recommendation and only after
							his decision in this regard, the Executive Engineer shall
							sanction extension of such time as decided by the Superintending
							Engineer.</p>
						<p>Once the Executive Engineer/Superintending Engineer has
							decided the case of extension of time with reference to the
							particular application of the contractor, it will not be
							competent for them to review/change such a decision later on.
							However, the Superintending Engineer and the Executive Engineer
							shall give the contractor an opportunity to be heard (orally and
							or in writing), before taking any final decision either of
							granting extension of time or permitting the contractor to
							complete the work by the delayed date or before refusing both.</p>
						<p>Provided further where the Executive Engineer has
							recommended grant of extension of particular time of the contract
							or has refused to recommend extension of time but has recommended
							permitting the contractor for delayed completion, the contractor
							shall continue with the work till the final decision by Executive
							Engineer/Superintending Engineer.</p>
						<p>Failure on the part of the contractor for not applying
							extension of time even within 30 days of the cause of such an
							hindrance, it shall be deemed that the contractor does not desire
							extension of time and that he has "Waived" his right if any, to
							claim extension of time for such cause of hindrance.</p>
						<p>Once the Executive Engineer /Superintending Engineer has
							heard (oral and or in writing) the contractor on this subject
							matter of extension of time and if Executive
							Engineer/Superintending Engineer fails to communicate his
							decision within a period of 30 days of such hearing, it shall be
							deemed that the contractor has been granted extension of time for
							the period as applied by him. Provided that the Contractor (s)
							shall not be entitled to any extension of time in respect of the
							extra work involved in the extra depth of foundation mentioned
							clause 1.5.</p>
					</div>
				</div>
				<!---  04 Page Ends Here --->
				<!---  05 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>1.13.1 Compensation Events for consideration of extension
								of time without penalty :- </b><br /> <br />The following mutually
							agreed Compensation Events unless they are caused by the
							contractor would be applicable;
						</p>
						<ul class="text-left">
							<li><b>(a)</b> The Executive Engineer does not give access
								to a part of the site.</li>
							<li><b>(b)</b> The Executive Engineer modifies the schedule
								of other contractor in a way, which affects the work of the
								contractor under the contract.</li>
							<li><b>(c)</b> The Executive Engineer orders a delay or does
								not issue drawings, specification or instructions
								/decisions/approval required for execution of works on time.</li>
							<li><b>(d)</b> The Executive Engineer instructs the
								contractor to uncover or to carry out additional tests upon
								work, which is then found to have no defects.</li>
							<li><b>(e)</b> The Executive Engineer gives an instruction
								for additional work required for safety or other reasons.</li>
							<li><b>(f)</b> The advance payment and or payment of running
								bills (complete in all respect) are delayed.</li>
							<li><b>(g)</b> The Executive Engineer unreasonably delays
								issuing a Certificate of Completion</li>
							<li><b>(h)</b> Other compensation events mentioned in
								contract if any</li>
						</ul>

						<p>
							<del>
								<b>1.13.2 Incentive bonus:</b> Not withstanding the provision
								contained in clause 1.13 above, if the contractor does not
								desire "Extension of Time" AND "WAIVES" his right to claim any
								extension of time on any ground whatsoever and yet - complete
								the contract (Excluding maintenance period if any) before the
								original time allowed for completion (as mentioned in the N.I.T)
								then and then only the contractor shall be entitled to and shall
								be paid "INCENTIVE BONUS". The Incentive Bonus shall be paid to
								the contractor at the rate of 0.25% (zero point two five
								percent) of the contract price per week of early completion
								subject to a maximum of 5% (five percent) of the contract price.
								Part of the week if more than 3 days shall be deemed to be one
								full week.<br /> <b>Note: -</b> The contractor has to give an
								undertaking in writing that he has "WAIVED" all his RIGHT to
								claim/demand extension of time
							</del>
						</p>

						<p>
							<b>1.14: <u>Action when the work is left incomplete
									abandoned or delayed beyond the time limit permitted by the
									Executive Engineer: - </u></b>
						</p>
						<p>
							<b>(i)</b> The Executive Engineer may terminate the contract if
							the contractor causes a fundamental breach of the contract.
						</p>
						<p>
							<b>(ii)</b> Fundamental breach of contract shall include, but not
							be limited to, the following: -
						</p>
						<ul class="text-left">
							<li><b>a)</b> The contractor stops work for four weeks, when
								no stoppage of work is shown on the current programme or the
								stoppage has not been authorised by the Executive Engineer.</li>
							<li><b>b)</b> The Executive Engineer gives notice that
								failure to correct a particular defect is a fundamental breach
								of contract and the contractor fails to correct it within
								reasonable period of time determined by the Executive Engineer
								in the said notice.</li>
							<li><b>c)</b> The contractor has delayed the completion of
								work by the number of weeks [12 (Twelve) weeks] for which the
								maximum amount of compensation of 6% of contract sum is
								exhausted.</li>
							<li><b>d)</b> If the contractor has not completed at least
								thirty percent of the value of construction work required to be
								completed in half of the completion period (Including validly
								extended period if any).</li>
							<li><b>e)</b> If the contractor fails to appoint the
								technical staff and if appointed do not function properly for 4
								weeks even after due written notice by the Executive Engineer.</li>
							<li><b>f)</b> If he violates labour laws.</li>
						</ul>
					</div>
				</div>
				<!---  05 Page Ends Here --->
				<!---  06 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<ul class="text-left">
							<li><b>g)</b> Any other deficiency which goes to the root of
								the contract Performance</li>
						</ul>
						<p>
							<b>(iii)</b> If the contract is terminated, the contractor shall
							stop work immediately, make the site safe and secure and leave
							the site as soon as reasonably possible.
						</p>
						<p>
							<b>(iv)</b> The Executive Engineer shall cause recording and
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
							sole risk and responsibility.
						</p>
						<p>The Executive Engineer shall forfeit the earnest money and
							or security deposit and further recover/deduct/adjust a
							compensation @ 10% (ten percent) of the balance value of work
							left incomplete either from the bill, and or from available
							security/performance guarantee or shall be recovered as "Arrears
							of land revenue"</p>
						<p>
							<b>1.15:</b> The Contractor(s) shall be paid the running payment
							according to the schedule of running payment agreed to at the
							time of award the contract on the completion of each calendar
							commencing from the day of work order a sum of 90% of the total
							value of work done _______ since the last payment according to
							the certificate of the E.E. when the work shall be completed. The
							Contractor (s) is/are to be entitled to receive one moiety of the
							amount remaining due according to the best estimate of the same
							that can be made and the Contractor(s) is/are to be entitled to
							receive the balance of all moneys due or payable to him/them
							under or by virtue of the contract within six month from the
							completion of the works. Provided always that no final or other
							certificate is to cover or relieve the Contractor(s) from
							his/their liability under the provision of clause 1.10 whether or
							not be same be notified by the E.E. at the time or the
							subsequently to the granting of any such certificate.
						</p>
						<p>
							<b>1.16:</b> A certificate of the E.E. or an award of the referee
							hereinafter referred to as the case may be showing the final
							balance due or payable for the Contractor(s) is to be conclusive
							evidence of the works / having been duly completed and that the
							Contractor(s) is/are entitled to receive payment of the final
							balance but without prejudice to the liability of the
							Contractor(s) under provisions of clause 1.10.
						</p>
						<p>
							<b>1.17 ARBITRATION CLAUSE:</b> Except as otherwise provided in
							this contract all question and dispute, relating to the meaning
							of the specifications designs, drawings and instructions herein
							before mentioned and as to thing whatsoever, in any way, arising
							out of or relating to the contract, designs, drawings,
							specifications, estimates, concerning the works, or the execution
							or failure to execute the same, whether arising during the
							progress of the works or after the completion abandonment thereof
							shall be referred to the superintending Engineer shall give his
							written instructions and/or decisions within a period of 60 days
							of such request. This period can be extended by mutual consent of
							the parties.
						</p>
						<p>Upon receipt of written instructions of decisions, the
							parties shall promptly proceed without delay to comply such
							instruction or decision, If the superintending Engineer fails to
							give his instructions or decisions in writing with in a period of
							60 days or mutually agreed time after being requested or if the
							parties may within 60 days refer and appeal to the Chief Engineer
							who shall afford an opportunity to the parties of being heard and
							to offer evidence in support of his appeal. The chief Engineer
							will give his decision within 90 days. If any party is not
							satisfied with the decision of the Chief Engineer, he can, refer
							such dispute for arbitration governed as per "The Chhattishgarh
							Madhyastha Abhikaran Raipur".</p>
						<p>
							<b>1.18.:</b> If at any time before or after the commencement of
							the work, <span class="colour-report"><b>Commissioner/CMO
									Chhattisgarh</b></span> shall for any reason whatsoever: -
						</p>
						<ul class="text-left">
							<li class="padding-top-0"><b>(a)</b> Cause alterations,
								omissions or variations in the drawings and specifications
								involving any curtailment of works as originally contemplated;
								or</li>
						</ul>
					</div>
				</div>
				<!---  06 Page Ends Here --->
				<!---  07 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<ul class="text-left">
							<li><b>(b)</b> Not required the whole of the work as
								specified in the tender to be carried out.<br /> <br />
								<p class="padding-top-0">The Contractor(s) shall have no
									claim to any payment or compensation whatsoever on account of
									any profit or advantage which he/they might have derived from
									the execution of the work in full as specified in the tender
									but which he/they did not derive in consequence of the
									curtailment of the works by reasons of alterations, omissions
									or variations or in consequence of the full amount of the work
									not having been carried out.</p>
								<p>But the Contractor(s) shall be entitled to compensation
									for any loss sustained by him/them by reason of his/their
									having purchased or procured any materials or entered in to any
									engagements or made any advance to labour or taken any other
									preliminary or incidental measures on account of or with a view
									to the execution of the works or the performance of the
									contract.</p></li>
						</ul>
						<p>
							<b>1.19:</b> Death or permanent invalidity of contractor: - if
							the contractor is an individual or a proprietary concern,
							partnership concern, dies during the currency of the contract or
							becomes permanently incapacitated, where the ther surviving
							partners are only minors the contract shall be closed without
							levying any damages/ compensation as provided for in clause 1.14
							of the contract agreement.
						</p>
						<p>However, if competent authority is satisfied about the
							competence of the surviving, then the competent authority shall
							enter into a fresh agreement for the remaining work strictly on
							the same terms and condition, under which the contract was
							awarded.</p>
						<p>
							<mark>1.20: DELETE</mark>
						</p>
					</div>
				</div>
				<!---  07 Page Ends Here --->
				<!---  08 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>CHAPTER - II<br /> <u>DETAILED NOTICE INVITING
									LUMPSUM TENDERS</u><br /> <br />(${userSession.getCurrent().organisation.ONlsOrgname}
							</b>)
						</h2>
						<p>
							<span class="pull-left">NIT No.&emsp;&emsp;/</span> <span
								class="pull-right">Naya Raipur dated
								.........................</span>
						</p>
						<div class="clear"></div>
						<br />
						<h2>
							<b><u>DETAILED NOTICE INVITING TENDER</u></b>
						</h2>
						<p>
							Chief Engineer, Urban Administration & Development Raipur invites
							online Tenders on behalf of Urban Administration & Development
							Department on Form "F" (Lump Sump) From Eligible Class registered
							contractors in "unified Registration system e-Registration" PWD
							Chhattisgarh on Govt. of C.G. E-Procurement System <a
								href="http://eproc.cgstate.gov.in">(http://eproc.cgstate.gov.in)</a>
						</p>
						<br />
						<table class="table table-bordered"
							style="font-size: 10px; margin-left: -46px;">
							<thead>
								<tr>
									<th>S. No</th>
									<th colspan="7">Name of work</th>
									<th>Probable amount of contract(Rs. in Lacs)</th>
									<th>Earnest Money(in Rs.)</th>
									<th>Period of completion</th>
									<th>Class of contractor</th>
									<th>Bid submission fees</th>
									<th>Validity of Tender</th>
								</tr>
								<tr>
									<th>1</th>
									<th colspan="7">2</th>
									<th>3</th>
									<th>4</th>
									<th>5</th>
									<th>6</th>
									<th>7</th>
									<th>8</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td rowspan="6">1</td>
									<td colspan="7" class="text-left">Design, Construction,
										testing & commissioning of RCC Elevated service reservoirs of
										following capacity on different staging & RCC Sump well for
										KONDAGAON WATER SUPPLY SCHEME SANCTIONED UNDER 13th FINANCE</td>
									<td rowspan="6"><b>${command.tenderWorksForms.workEstimateAmt}</b></td>
									<td rowspan="6"><b>${command.tenderWorksForms.tenderSecAmt}</b></td>
									<td rowspan="6"><b>${command.tenderWorksForms.vendorWorkPeriod}(including
											rainy season)</td>
									<td rowspan="6"><b>${command.tenderWorksForms.venderClassDesc}</b>(Unified
										Registration System-E Registration)</td>
									<td rowspan="6"><b>Rs.${command.tenderWorksForms.tenderFeeAmt}</b>
										payable at the time of Bid Preparation and Hash Submission
										through online payment Gateway</td>
									<td rowspan="6"><b>${command.tenderWorksForms.tndValidityDay}</b>
										days from date of opening of financial offer</td>
								</tr>
								<tr>
									<th rowspan="2">Zone</th>
									<th rowspan="2">Location</th>
									<th colspan="3">RCC Elevated Service Reservoirs</th>
									<th rowspan="2">Sump Well Capacity (KL)</th>
									<th rowspan="2">Estimated Cost<br />(Rs. In lakhs)
									</th>
								</tr>
								<tr>
									<th>No</th>
									<th>Capacity (KL)</th>
									<th>Staging (m)</th>
								</tr>
								<tr>
									<td>I & II</td>
									<td>Aadakachhepeda ward</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>III & IV</td>
									<td>Dongri Para ward</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<th colspan="2">Total</th>
									<td></td>
									<td></td>
									<td>-</td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<br />
						<p>
							<b>1.</b> The Bidders intending to participate in this Tender are
							required to get enrolled/ registered on the e-procurement website
							<a href="http://eproc.cgstate.gov.in">(http://eproc.cgstate.gov.in)</a>
							and get Enrolment/ Registration and subsequent empanelment on the
							above mentioned website.
						</p>
						<p>
							<b>2.</b> Validity of offer - <b>${command.tenderWorksForms.tndValidityDay}</b>
							days from date of opening of financial offer.
						</p>
						<p>
							<b>3.</b> The Technical offer shall be opened in presence of the
							Bidders or their authorized representatives, who may choose to be
							present. The date and place of opening of financial offer shall
							be as per key dates given in NIT.
						</p>
						<p>
							<b>4.</b> The bidders are required to submit 'Envelope "<span
								class="colour-report">A</span>" physically as per dates
							Indicated in Key Dates. The Physical Envelope'A' should contain
							the following: - The Earnest Money of <span class="colour-report">Rs.
								<b>${command.tenderWorksForms.tenderSecAmt}/-(Rs.
									${command.tenderWorksForms.tndEarAmntString} )</b>
							</span> in favour of the <span class="colour-report">
								Commissioner/CMO <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							</span> which will be returned to the unsuccessful Bidders. The Earnest
							Money of the successful Bidders will be retained as part of the
							Security Deposit.
						</p>
					</div>
				</div>
				<!---  08 Page Ends Here --->
				<!---  09 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>5.</b> The Key Dates of Tender are :-
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>S. No</th>
									<th>UADD Stage</th>
									<th>Supplier Stage</th>
									<th>Start Date & Time</th>
									<th>Expiry Date & Time</th>
									<th>Envelops</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td class="text-left">Bid- Start date</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>2</td>
									<td class="text-left">Bid- Due Date</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>3</td>
									<td class="text-left">Physical document submission - End
										date</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>4</td>
									<td class="text-left">Bid open date(Scheduled)</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						</p>
						<p>
							Other condition including qualification and details of work can
							be seen in the office of the undersigned during office hours and
							downloaded online directly from the portal Government of
							Chhattisgarh e-procurement portal <a
								href="http://eproc.cgstate.gov.in">http://eproc.cgstate.gov.in</a>
							Shall be submitted online on or before up to <b>${command.tenderWorksForms.tenderIssueToDateDesc}</b>
							up to 17:30 P.M. This NIT shall also form the part of agreement.
						</p>
						<p>
							<span class="colour-report"><b>6.</b> For details on
								tendering procedure through the electronic tendering system,
								please refer to "<b>Instructions for Using the Electronic
									Tendering System</b>"document available along with the tender
								documents. 
						</p>
						</span>
						<p>The Bidders are also invited to get themselves trained on
							the operations of the e-Procurement System. Bidders may get in
							touch with the Service Provider of the e-Procurement System for
							confirming the time and date for their training session.</p>
						<p>
							<b>2.0 SUBMISSION OF TENDERS:</b>
						</p>
						<p>The Tenderer shall fill/upload the Bids online and the Bid
							Hashes of three envelopes shall be digitally signed and submitted
							online as per mentioned key dates. The Bidders shall also have to
							submit Bids online (decrypt the bids using their Digital
							Certificate and encrypt the bids) as per mentioned key dates.
							There shall be two separate Online envelopes as under:-</p>
						<p>
							<b>2.1 ENVELOPE - A</b>
						</p>
						<p>The online envelope A shall contain the details of Earnest
							Money, scanned copy of the Physical Earnest Money and scanned
							copy of documents will be submitted in the online
							prequalification envelope.</p>
						<p>The Physical Earnest Money which is to be submitted
							manually in Physical Envelope -A where it should be clearly
							written on the envelope as under:-</p>
						<p style="text-align: center;">
							<b>ENVELOPE - A</b><br /> <br />EARNEST MONEY<br /> <br />From
							-(Name of Contractor)
						</p>
						<p>
							and should reach <span class="colour-report"><b>Chief
									Engineer, UAD 4th floor, Indrawati Bhwan, Naya Raipur</b></span> as per
							date and time mentioned in the key dates.
						</p>
					</div>
				</div>
				<!---  09 Page Ends Here --->
				<!---  10 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>2.2 ENVELOPE - B</b>
						</p>
						<p>The Second Online envelope shall contain all the technical
							details/specifications of the proposed work and documents. The
							Scanned copy should be submitted online in Envelope "B". The
							details are as under:-</p>
						<ul class="text-left">
							<li><b>I.</b> Registration certificate</li>
							<li><b>II.</b> Pan card</li>
							<li><b>III.</b> Income tax clearance certificate</li>
							<li><b>IV.</b> Valid commercial tax certificate from
								Chhattisgarh</li>
							<li><b>V.</b> Experience certificate</li>
							<li><b>VI.</b> List of Technical Staff detail</li>
							<li><b>VII.</b> Financial capacity certificate as per clause
								2.10</li>
							<li><b>VIII.</b> Affidavit as per Annexure 13</li>
							<li><b>IX.</b> Details of contracts already in hand.</li>
							<li><b>X.</b> Name, residence & place of Business as per
								clause 2.37</li>
							<li><b>XI.</b> List of near relative working in UAD as per
								clause 2.32 as mentioned in Annexure-"J"</li>
							<li><b>XII.</b> Declaration as per clause 2.40 (VII)</li>
						</ul>

						<p>
							<b>2.3 ENVELOPE - C</b>
						</p>
						<p>This Envelope shall contain only the Lump-sum offer. The
							tenderer shall have to duly fill their Lump-sum offer in
							appropriate online form meant for it.</p>
						<p>
							<b>2.4.1</b> Tender will be submitted with the Earnest Money, of
							<span class="colour-report">Rs. <b>${command.tenderWorksForms.tenderSecAmt}/-
									${command.tenderWorksForms.tndEarAmntString} )</b>
							</span> In favour of <span class="colour-report">the
								Commissioner/CMO <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							</span> which will be returned to the unsuccessful tenderer. The Earnest
							Money of the successful tenderer will be retained as part of the
							Security Deposit.
						</p>
						<p>
							<b>2.4.2 The rate of earnest money to be submitted by the
								intending contractors will be as under.</b>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Tender Value From - Up to</th>
									<th>Percent</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="text-left">For tender up to Rs. 1.00 lakh</td>
									<td class="text-left">2 percent</td>
								</tr>
								<tr>
									<td class="text-left">For tender more than Rs. 1.00 lakh
										and up to Rs. 5.00 lakhs</td>
									<td class="text-left">1 percent subject to a minimum of
										Rs. 2000/-</td>
								</tr>
								<tr>
									<td class="text-left">For tenders more than Rs. 5.00 lakh
										and up to Rs. 2.0 crores</td>
									<td class="text-left">0.75 percent subject to a minimum of
										Rs 5,000/-</td>
								</tr>
								<tr>
									<td class="text-left">For tenders more than Rs. 2.00
										crores</td>
									<td class="text-left">0.50 percent subject to a minimum of
										Rs.1.5 lakh and maximum of Rs. 5.00 lakhs</td>
								</tr>
							</tbody>
						</table>
						</p>
						<p>
							<b>2.4.3.:</b> I In the event of withdrawing his/her after before
							the expiry of the period of validity of offer or failing to
							execute the agreement as required by condition No. 2.34 of the
							notice inviting tender (N.I.T.) he/she will not be entitled to
							tender for this work in case of recall of tenders. In addition to
							forfeited of his/her earnest money as per provisions of condition
							No. 2.34 of N.I.T. as may be applicable for the work, the
							registering authority will demote the contractor firm for a
							period of one
						</p>
					</div>
				</div>
				<!---  10 Page Ends Here --->
				<!---  11 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>year. If the tenderer has committed a similar default on
							earlier occasion (s) as well, then such demotion in registration
							will be permanently.</p>
						<p>
							<b>2.5 Form of Earnest Money:</b>
						</p>
						<p>
							<b>2.5.1.:</b> Where the amount of earnest money to be deposited
							is more than Rs. 500/- and the tenderer proposes to pay it in
							cash he shall pay earnest money to be credit of Revenue Deposit
							on behalf of the <span class="colour-report"><b>Commissioner/CMO
									${userSession.getCurrent().organisation.ONlsOrgname}</b></span> in to a
							branch of the State bank of India or Government Treasury or
							Sub-Treasury within the jurisdiction of the <span
								class="colour-report"><b>Commissioner/CMO
									${userSession.getCurrent().organisation.ONlsOrgname}</b></span> mentioned
							above and send/produce the challan to the <span
								class="colour-report"><b>Commissioner/CMO
									${userSession.getCurrent().organisation.ONlsOrgname} </b></span>
							separately and it should not been kept in the cover containing
							tenders. If, however, the tenderer wishes to deposit the earnest
							money in any one of the following forms, he may do so and
							produce/send the same duly hypothecated to the <span
								class="colour-report"><b>Commissioner/CMO
									${userSession.getCurrent().organisation.ONlsOrgname} </b></span>.
						</p>
						<p>
							<b>2.5.1.</b>
						</p>
						<ul class="text-left">
							<li><b>I:</b> Treasury Receipts.</li>
							<li><b>II:</b> National Savings Certificates.</li>
							<li><b>III:</b> Treasury Bonds.</li>
							<li><b>IV:</b> Approval interest bearing security (this
								includes C.G. State development Loans)</li>
							<li><b>V:</b> Government promissory Notes/National plan
								Loans</li>
							<li><b>VI:</b> Post Office Cash Certificates.</li>
							<li><b>VII:</b> 10 Years Treasury Saving Deposit
								Certificates.</li>
							<li><b>VIII:</b> 12 Years National Plan Saving Certificates.</li>
							<li><b>IX:</b> 10 Years Defense Deposit Certificates.</li>
							<li><b>X:</b> National Saving Certificate duly hypothecated
								in the name of <span class="colour-report"><b>Commissioner/CMO
										${userSession.getCurrent().organisation.ONlsOrgname}</b></span>.</li>
							<li><b>XI:</b> All small Savings Securities and post office
								Savings Bank Accounts duly pledged to Government.</li>
							<li><b>XII:</b> Debentures of C.G. Housing Board.</li>
							<li><b>XIII:</b> Bank Drafts of the State Bank of India or
								Scheduled Banks in case of tendered of other States.</li>
							<li><b>XIV:</b> Units of Unit Trust of India.</li>
							<li><b>XV:</b> Bank Drafts issued by big Urban Banks whose
								working capital exceeds Rs. 5.00 crores and by A.B. and C class
								Central/Co. operative Banks / Non- Scheduled States Co-operative
								Banks subject to the condition that the drafts are encashed by
								the accepting authority as soon as they are received and the
								contract are allotted only after the encashment of drafts as per
								M.P.F.D. No. F/3/18/77/8/5 (iv) dated 13/2/1973.</li>
						</ul>

						<p>
							<b>2.5.2:</b> The earnest money in one of the prescribed forms
							should be produced/sent separately and not kept in the cover
							containing the tender and if the earnest money is not in
							accordance with the prescribed mode, the tenders would be
							returned unopened to the tenderer.
						</p>
						<p>
							<b>2.6:</b> The intending tenderers from other States may remit
							the Earnest money in the form of Bank Draft of the State Bank of
							India or any other Scheduled Bank to the <span
								class="colour-report"><b>Commissioner/CMO
									${userSession.getCurrent().organisation.ONlsOrgname}</b></span> concerned.
						</p>
						<p>
							<b>2.7:</b> Earnest money which has been deposited for a
							particular work will not ordinarily be adjusted towards the
							earnest money for another work, but if tender of a contractor for
							a work in the same
						</p>
					</div>
				</div>
				<!---  11 Page Ends Here --->
				<!---  12 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							division has been rejected and the earnest money has not been
							refunded to him to some reason it may be so adjusted by the <span
								class="colour-report"><b>Commissioner/CMO
									${userSession.getCurrent().organisation.ONlsOrgname}</b></span>.
						</p>
						<p>
							<b>2.8:</b> The Security Deposit shall be 5 percent of the amount
							of contracts.
						</p>
						<p>
							<b>2.9:</b> The authority competent to accept a tender reserves
							the right of accepting the tender for the whole work or for
							distinct part of it or of distributing the work between one or
							more tendereres.
						</p>
						<p>
							<b>2.10:</b> A Financial capacity certificate or attested
							photocopy their of from any schedule bank along with the
							application for the tender papers be submitted which should not
							be older than 12 months from the date of application. Amount of
							financial capapcity to be furnished shall be the at least 15
							(fifteen) % of amount put to tender
						</p>
						<p>The financial Capacity certificate shall have to be in the
							following format:</p>
						<p style="text-align: center;">
							CERTIFICATE<br />(On the letter head of the bank)
						</p>
						<p>
							on the basis of transactions/ turn over in the account of
							____________________________________<br />_________________________________________
							(name and address) wer are of the opinion that his financial
							capacity is to the extent of (both figures and words) Rs
							____________ (in words ) ___________________________________ this
							is without any prejudice and responsibility on our part.
						</p>
						<br /> <br />
						<p>
							<span class="pull-left">Place:-<br />Date :-
							</span> <span class="pull-right">Br. Manager.<br />with seal of
								bank
							</span>
						</p>
						<div class="clear"></div>
						<p>
							<b>2.11:</b> The submission of a tender by a contractor implies
							that he has read the notice conditions of tender and contract and
							has made himself aware of the scope and specifications of the
							work to be done and has been the quarries with their approach,
							sites of work etc, and satisfied himself regarding the
							suitability of the materials at the quarries. The responsibility
							of opening of new quarries and construction and maintenance of
							approaches their to shall lie wholly with the contractor.
						</p>
						<p>
							<b>2.12: Subletting of works: -</b> The contract may be rescinded
							and security deposit forfeited, for subletting the work beyond
							permissible limits as mentioned below or if contractor becomes
							insolvent:-
						</p>
						<p>The contract shall not be assigned or sublet without prior
							sanction of the authority who has accepted the tender in writing.
							And if the contractor assign or sublet his contract, for more
							than permissible limits as mentioned below or attempt to do so,
							or become insolvent commence any insolvency proceedings or make
							any composition with his creditors, or attempt to do so or if any
							gratuity, gift, loan, perquisite, reward of and advantage
							pecuniary or otherwise, shall either directly or indirectly be
							given, promised or offered by the contractor, or any of his
							servants or agents or to any public officer or person in the
							employ of Government in any way relating to his office or
							employment, or if any such officer or person shall become in any
							way directly or indirectly interested in the contract, the
							Executive Engineer may there upon by notice in writing rescind
							the contract, and the S.D. of the contractor shall there upon
							stand forfeited and be absolutely at the disposal of Government
							and the same consequences shall ensure as if the contract had
							been rescinded under clause 1.14 thereof, and in addition the
							contractor shall not be entitled to recover or be paid for any
							work thereto for actually performed under the contract. Any such
							assignment/subletting within the limit of 25% by the authority
							who has accepted the tenders OR 50 % by the next higher authority
							accepting the tender or Govt. as the case may be ,shall not
							diminish or dilute the liability/ responsibility of the
							contractor.</p>
						<p>If the contractor gets item / items of work executed on a
							task rate basis without materials, this shall not amount to
							subletting of the contract.</p>
						<p>
							<b>Any subcontracted work, done in Chhattisgarh state with
								prior approval of competent authority, such subcontractor will
								also get the credit for work towards his experience.</b>
						</p>
					</div>
				</div>
				<!---  12Page Ends Here --->
				<!---  13 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b><u>Additional condition issued by Govt.of Chattisgarh
									memo no. 9942/4351/T/11/19/Nivida Raipur dated 21-12-11</u></b>
						</p>
						<ol type="I" class="text-left">
							<li><b>i) The department shall be empowered to terminate
									any contract if the contractor sublets the works to some other
									person on the basis of power of attorney.</b></li>
							<li><b>ii) Subletting of work shall result in reduction
									in experience of the main contractor to the extent of the
									sublet.</b></li>
						</ol>
						<p>
							<b>2.13:</b> All the conditions of the tender notice will be
							binding on the contractor and will form part of the agreement to
							be executed by the contractor in addition to the conditions of
							contract in the prescribed form and special conditions of
							contract and those accepted as common conditions during
							negotiations.
						</p>
						<p>
							<b>2.14:</b> The tenders will be opened at the time and place
							stated in para 2.1 by the <span class="colour-report"><b>Chief
									Engineer</b></span> in the presence of the tenderer or their duly
							authorised agents who may choose to attend. The <span
								class="colour-report"><b>Chief Engineer</b></span> as in para
							2.1 unavoidable circumstance may depute another officer in his
							absence to receive and open tenders on his behalf.
						</p>
						<p>
							<b>2.15:</b> The Executive Engineer does not bind himself to
							accept or recommend for the acceptance to the <span
								class="colour-report"><b>Chief Engineer,
									Commissioner/CMO
									${userSession.getCurrent().organisation.ONlsOrgname}</b></span> or other
							higher authority, the lowest or any tender or to give any reasons
							for his decision.
						</p>
						<p>
							<b>2.16 Taxes Royalty etc.:</b>
						</p>
						<p>
							<b>2.16.1 Taxes:</b> The rate quoted by the Contractor shall be
							deemed to be inclusive of the sales and other levies, duties,
							royalties, cess, toll, taxes of Central and State Governments,
							local bodies and authorities that the Contractor will have to pay
							for the performance of this Contract. The Govt. will perform such
							duties in regard to the deduction of such taxes at source as per
							applicable law. However if "Service Tax" and cess on service tax
							or any other "New Tax" (not increase or decrease in existing tax,
							duties, surcharge, except royalty on minor mineral) is levied on
							the contractor either by Central Govt. or State Govt, then the <span
								class="colour-report"><b><u>Commissioner/CMO
										${userSession.getCurrent().organisation.ONlsOrgname}</u></b></span> shall
							reimburse the "Service Tax" and cess on service tax and or "New
							Tax" amount; on submission of proof of such payments by the
							contractor.
						</p>
						<p>
							<b>2.16.2 Royalty on Minor Minerals</b>
						</p>
						<p>
							The contractor shall pay all quarry, Royalty charges etc. If the
							contractor fails to produce the royalty clearance certificate
							from concerned department then the <span class="colour-report"><b>Commissioner/CMO
									${userSession.getCurrent().organisation.ONlsOrgname}</b></span> shall
							deduct the royalty charges from his bills and keep in deposit
							head, which shall be refunded to the contractor on production of
							royalty clearance certificate from the concerned department. If
							he fails to produce the royalty clearance certificate with in 30
							days of submission of final bill, then royalty charges which was
							keep under deposit head by the <span class="colour-report"><b>Commissioner/CMO
									${userSession.getCurrent().organisation.ONlsOrgname}</b></span> shall be
							deposited to the concerned department and his final bill payment
							shall be released
						</p>
						<p>Any change in the royalty rates of minor minerals notified
							by the state government, after the date of submission of
							financial offer by the bidder/contractor, then this
							increase/decrease in the rates shall be reimbursed/ deducted on
							actual basis.</p>
						<p>
							<b>2.16.3:</b> Income tax at the rate of 2% or such other
							percentage as may be fixed by income tax department from time to
							time from any sum payable to the Contractor shall at the time of
							credit of such sum or at the time of payment to the contractor by
							cash, cheque or draft or any other mode shall be deducted at the
							source from the running, final or any type of payment for this
							contract as per section 194 of income tax Act. 1961.
						</p>
						<p>
							<b>2.16.4:</b> It is open to the contractor or the sub contractor
							as the case may be to make an application to the Income Tax
							officer concerned and obtain from him a certificate authorizing
							the payer to deduct tax at such lower rate or deduct no tax as
							may be appropriate to his case Such certificate will be valid for
							the period specified therein unless it is cancelled by the income
							Tax Officer earlier.
						</p>
					</div>
				</div>
				<!---  13 Page Ends Here --->
				<!---  14 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>2.17 Model Rules for water supply, Sanitation in Labour
								Camps:</b> The contractor will be bound to follow the Chhattisgarh
							model rules relating to layout of water supply and sanitation in
							labour camps (Vide Annexure-A)
						</p>
						<p>
							<b>2.18 Fair wages to Labourers:</b> The Contractor shall pay not
							less than fair wages to labourers engaged by him on the work
							(Copy of rules enclosed vide Annexure-B)
						</p>
						<p>
							<b>2.19 Right to take up work departmentally or to award on
								contract:</b> The Engineer – in –Charge reserves the right to
							take up departmental work or to award works on contract in the
							vicinity without prejudice to the terms of contract.
						</p>
						<p>
							<b>2.20 Issue of Materials by the Department:</b> The following
							Materials will be supplied by the Department :-
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>S.No.</th>
									<th>Name of Article</th>
									<th>Unit</th>
									<th>Rate</th>
									<th>Place of Delivery</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="5">No, Materials shall be supplied by the
										Department.</td>
								</tr>
							</tbody>
						</table>
						</p>
						<p>So for as supply of cement and steel (Mild & H.Y.S.D. Bars)
							and other materials is concerned these or to be arranged by the
							contractor himself at his own cost and the conditions given in
							the annexure - H shall Prevail, The Contractor shall establish a
							laboratory near the site for testing</p>
						<p>
							<b>2.21 Income tax Clearance Certificate:</b> A tenderer applying
							for tender copies for work exceeding Rs. 2.00 lakhs shall have to
							submit as Income Tax clearance Certificate or a Certificate from
							the Income tax authority that the assessment is under
							consideration (as per annexure "D").
						</p>
						<p>
							<b>2.22:</b> The contractor shall execute the work as per
							detailed specifications as incorporated in the tender document
							and in accordance with the approved drawing and special
							conditions incorporated in the tender documents.
						</p>
						<p>
							<b>2.23 Scope of work covered by lump- sum cost:</b> The scope of
							work covered by the lump-sum cost is given in
							<mark class="colour-report">Chapter IV & Chapter V.</mark>
						</p>
						<p>
							<b>2.24 List of works in hand:</b> Tenders must be accompanied by
							a list of contracts already held by the tenderer at the time of
							submitting the tender in
							<mark class="colour-report">Directorate of Administration
								& development
								${userSession.getCurrent().organisation.ONlsOrgname}</mark>
							and elsewhere as per Annexure - K.
						</p>
						<p>
							<b>2.25 Removal of unsuitable or undesirable employees of
								contractor:</b> The Contractor shall on receipt or the requisition
							form the
							<mark class="colour-report">Executive Engineer</mark>
							at once remove any person employed by him on the work who in the
							opinion of E.E. is unsuitable or undesirable.
						</p>
						<p>
							<b>2.26 Recovery of Amount due to Government from contractor:</b>
							Any amount due to Government from the Contractor on any account,
							concerning work may be recovered from him as error of land
							revenue and/or from payment due to him in any of the Govt / Semi
							Government Department.
						</p>
						<p>
							<b>2.27 Transport of materials in contractors responsibility:</b>
							The Contractor shall make his own arrangement for transport of
							all materials The Government is not bound to arrange for
							priorities for Getting wagons or any other materials though all
							possible assistance by way of recommendation will be given, if it
							is found necessary in the opinion of the
							<mark class="colour-report">Engineer -in -Charge</mark>
							if it proves in effective the contractor shall have no claim for
							any compensation on this account.
						</p>
						<p>
							<b>2.28 Arrangement of Tools and Plants:</b> The Contractor shall
							arrange at his own cost tools and plants required for proper
							execution of work.
						</p>
						<p>
							<b>2.29 Increase or Decrease of work specified within lump
								sum:</b> The competent authority reserves the right to increase or
							decrease any work specified within lump sum during the currency
							of the contract and contractor will be bound to comply with the
							order of the competent authority, these variations will be
							Governed by. Clause – 3.29
						</p>
					</div>
				</div>
				<!---  14 Page Ends Here --->
				<!---  15 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>2.30 Execution of work according to time schedule:</b> The
							work shall be done by the Contractor according to the time
							schedule fixed by competent authority.
						</p>
						<p>
							<b>2.31 Canvassing or support or acceptance of tender:</b>
							Canvassing or support in any form for the acceptance of any
							tender is strictly prohibited any tender doing so will render
							himself liable to penalties which may include removal of his name
							from the register of approved contractors.
						</p>
						<p>
							<b>2.32 List of persons employed by contractor:</b> The
							contractor shall not be permitted to tender for works in the
							concerned ULB (responsible for award and execution of contracts)
							in which his near relative is posted as Divisional Accountant or
							as an officer in any capacity between the grades of
							Superintending Engineer and Assistant Engineer (both inclusive) A
							list showing the names of the persons who are working with the
							contractor and are near relatives to any Gazetted officer in the
							<mark>UAD including Directorate</mark>
							at should also be appended to the tender. He should also intimate
							to the E.E. the names of subsequently employed persons who are
							near relatives of any
							<mark>gazetted officer in UAD</mark>
							or Divisional Accountant in concerned divisions. Any breach of
							this condition by the contractor would tender him liable to be
							removed from the approved list of Contractors of this Department.
						</p>
						<del>
							<p>
								<b>2.33 Escalation</b>
							</p>
							<p>Reimbursement /Refund on Variation in Prices of Materials
								/ P. O. L. and Labour Wages</p>
							<p>
								<b>Price Adjustment: -</b>
							</p>
							<ul class="text-left">
								<li><b>(A)</b> Contract price shall be adjusted for
									increase or decrease in rates and price of labour, materials,
									POL, in accordance with the following principles and procedure
									and as per formula given below.<br /> Note: - Price adjustment
									shall be <b>applicable</b> from reckoned date and upto validily
									extended period but shall not apply to the period when, work is
									carried out under penal (compensation) clause.</li>
								<li><b>(B)</b> The price adjustment shall be determined
									during each month from the formula given in the hereunder.</li>
								<li><b>(C)</b> Following expressions and meanings are
									assigned to the work done during each month:<br />To the
									extent that full compensation for any rise or fall in costs to
									the contractor is not covered by the provisions of this or
									other clauses in the contract, the unit rates and prices
									included in the contract shall be deemed to include amounts to
									cover the contingency of such other rise or fall in costs.</li>
							</ul>
							<p>
								<b>The formula (e) for adjustment of prices are: -</b>
							</p>
							<p>R= Total value of work done during the month. It would
								include the amount of secured advance granted, if any, during
								the month, less the amount of secured advance recovered, if any
								during the month. It will exclude value for works executed under
								variations for which price adjustment will be worked separately
								based on the terms mutually agreed.</p>
							<p>
								<b>Adjustment for labour component</b>
							</p>
							<p>
								<b>(i)</b> Price adjustment for increase or decrease in the cost
								due to labour shall be paid in accordance with the following
								formula:
							</p>
							<p>
								V<sub>L</sub> = 0.85 x P<sub>1</sub>/100 x R x (L<sub>i</sub>
								–L<sub>0</sub>)/L<sub>0</sub>
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
								inviting tender.
							</p>
							<p>
								L<sub>i</sub> = The consumer price index for industrial workers
								at the town nearest to the site of work for the month under
								consideration as published by Labour Bureau, Ministry of Labour,
								Government of India.
							</p>
						</del>
					</div>
				</div>
				<!---  15 Page Ends Here --->
				<!---  16 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<del>
							<p>
								P<sub>1</sub> = Percentage of labour component of the work. the
								site or work as published by Labour Bureau, Ministry of Labour,
								Govt. of India. on the date of inviting tender
							</p>
							<p>
								<b>Adjustment for cement component</b>
							</p>
							<p>
								<b>(ii)</b> Price adjustment for increase or decrease in the
								cost of cement procured by the contractor shall be paid in
								accordance with the following formula;
							</p>
							<p>
								V<sub>c</sub> = 0.85 x P<sub>c</sub>/100 x R x (C<sub>i</sub>
								–C<sub>0</sub>)/C<sub>0</sub>
							</p>
							<p>
								V<sub>c</sub> = increase or decrease in the cost of work during
								the month under Consideration due to changes in rates for cement
							</p>
							<p>
								C<sub>0</sub> = The all India wholesale price index for cement
								as published by the Ministry of Industrial Development,
								Government of India, New Delhi. on the date of inviting tender
							</p>
							<p>
								C<sub>i</sub> = The all India average wholesale price index for
								cement for the month Under consideration as published by
								Ministry of Industrial Development, Govt. of India, New Delhi.
							</p>
							<p>
								P<sub>C</sub> = Percentage of cement component of the work.
							</p>
							<p>
								<b>Adjustment for steel component</b>
							</p>
							<p>
								<b>(iii)</b> Price adjustment for increase or decrease in the
								cost of steel procured by the Contractor shall be paid in
								accordance with the following formula;
							</p>
							<p>
								V<sub>s</sub> = 0.85 x P<sub>s</sub>/100 x R x (S<sub>i</sub>
								– S<sub>0</sub>)/S<sub>0</sub>
							</p>
							<p>
								V<sub>s</sub> = increase or decrease in the cost of work during
								the month under Consideration due to changes in the rates for
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
								<b>Note:-</b> <i>for the application of this clause, index
									of Bars and Rods has been to represent steel group.</i>
							</p>
							<p>
								<b>Adjustment of bitumen component</b>
							</p>
							<p>
								<b>(iv)</b> Price adjustment for increase or decrease in the
								cost of bitumen shall be paid in accordance with the following
								formula;
							</p>
							<p>
								V<sub>L</sub> = 0.85 x P<sub>b</sub>/1 00 x R X (B<sub>i</sub>
								–B<sub>0</sub>) /B<sub>0</sub>
							</p>
							<p>
								V<sub>b</sub> = Increase or decrease in the cost of work during
								the month under Consideration due to changes in rates for
								bitumen.
							</p>
							<p>
								B<sub>0</sub> = The official retail price of bitumen at the IOC
								depot at nearest centre on the date of inviting tender
							</p>
							<p>
								B<sub>i</sub> = The official retail price of bitumen of IOC
								depot at nearest center for the 15th day of the month under
								consideration.
							</p>
							<p>
								P<sub>b</sub> = Percentage of bitumen component of the work.
							</p>
							<p>
								<b>Adjustment of POL (fuel and lubricant) component</b>
							</p>
							<p>
								<b>(v)</b> Price adjustment for increase or decrease in cost of
								POL (fuel and lubricant) shall be paid in accordance with the
								following formula;
							</p>
							<p>
								V<sub>f</sub> = 0.85 x P<sub>f</sub>/100 x R x (F<sub>i</sub>
								– F<sub>0</sub>)/F<sub>0</sub>
							</p>
						</del>
					</div>
				</div>
				<!---  16 Page Ends Here --->
				<!---  17 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<del>
							<p>
								V<sub>f</sub> = Increase or decrease in the cost or work during
								the month under consideration due to changes in rates for fuel
								and lubricants.
							</p>
							<p>
								F<sub>0</sub> = The official price of High Speed Diesel (HSD) at
								the existing consumer Diesel pumps out let at nearest center on
								the date of inviting tender
							</p>
							<p>
								F<sub>i</sub> = The official retail price of HSD at the existing
								consumer pumps of IOC at nearest center for the 15th day of
								month under consideration.
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
							<p>
								<b>(vii)</b> Price adjustment for increase or decrease in cost
								of local materials other than Cement, steel, Bitumen and POL
								procured by the contractor shall lay in accordance with the
								following formula;
							</p>
							<p>
								V<sub>m</sub> = 0.85 x P<sub>m</sub> /100 X R x (M<sub>i</sub> -
								M<sub>0</sub>)/M<sub>0</sub>
							</p>
							<p>
								V<sub>m</sub> = Increase or decrease in the cost of work during
								the month under Consideration due to changes in rates for local
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
								than cement, steel, bitumen and POL) of the work.
							</p>
							<p>The following percentages will govern the price adjustment
								for the entire contract:</p>
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>Sl. No.</th>
										<th>Components</th>
										<th>For Road</th>
										<th>For Building</th>
										<th>For Bridge</th>
									</tr>
								</thead>
								</tbody>
								<tr>
									<td>1</td>
									<td>Labour- P<sub>1</sub></td>
									<td>25%</td>
									<td>35%</td>
									<td>30%</td>
								</tr>
								<tr>
									<td>2</td>
									<td>Cement – P<sub>c</sub></td>
									<td>5%</td>
									<td>10%</td>
									<td>25%</td>
								</tr>
								<tr>
									<td>3</td>
									<td>Steel – P<sub>s</sub></td>
									<td>5%</td>
									<td>10%</td>
									<td>25%</td>
								</tr>
								<tr>
									<td>4</td>
									<td>Bitumen – P<sub>b</sub></td>
									<td>10%</td>
									<td>-</td>
									<td>-</td>
								</tr>
								<tr>
									<td>5</td>
									<td>POL – P<sub>f</sub></td>
									<td>10%</td>
									<td>10%</td>
									<td>10%</td>
								</tr>
								<tr>
									<td>6</td>
									<td>Other materials - P<sub>m</sub></td>
									<td>45%</td>
									<td>35%</td>
									<td>10%</td>
								</tr>
								<tr>
									<td></td>
									<th>Total:-</th>
									<th>100%</th>
									<th>100%</th>
									<th>100%</th>
								</tr>
								</tbody>
							</table>
							<p>
								Note :-If in the execution of contract for Road works use of
								certain material(s) is/are not involved (Viz cement, steel,
								Bitumen etc.), then the percentage of other material-Pm shall be
								increased to that extent<br />Example: - Say in a contract of
								roadwork steel is not required (Ps-5%). Pm shall become
								45%+5%=50%
							</p>
							<span class="text-center">Or</span>
							<p>Say cement & steel not required then Pm shall become
								45%+5%+5%=55% and so on</p>
						</del>
					</div>
				</div>
				<!---  17 Page Ends Here --->
				<!---  18 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>2.34 Validity of Offer:</b> Tenders shall remain open up to
							four months from the prescribed date of opening of tenders.
							However, when tenders are invited in 3 cover system and or
							negotiations are held, the modified or fresh offers shall remain
							open up to four months from the prescribed date of opening the
							same. In the event of the tenderer withdrawing the offer before
							the aforesaid dates for any reason whatsoever, Earnest money
							deposited with the tender shall be forfeited to the Govt. by
							<mark>the Commissioner/CMO
								${userSession.getCurrent().organisation.ONlsOrgname}</mark>
							.
						</p>
						<p>In the event of tenderer withdrawing his/her offer before
							the expiry of the period of validity of offer or failing to
							execute the contract agreement he/she not be entitled to tender
							for this work. In the case of recall of tenders, In addition to
							forfeiture of his/her earnest money as may be applicable for
							their work. If the tenderer has committed a similar default on an
							earlier occasion as well, his/her registration in the department
							may be suspended temporarily for a period of 6 months from such
							date as may be ordered by the authority which had registered
							him/her.</p>
						<p>
							<b>2.35 Bank Commission Charges:</b> Bank commission charges in
							all payments by demand drafts outside the State will not be borne
							by the Department but by the Supplier/firms/contractor himself.
						</p>
						<p>
							<b>2.36 Force Majeure:</b> Should failure in performance of any
							part of this contract arise from war, insurrection, restraint
							imposed by Government, act of Legislature or other authority,
							stoppage of hindrance in the supply of raw materials, or fuel,
							explosion, accident, strike, riot, lockout, or other
							disorganization, of labour or transport, breakdown of machine,
							flood, fire act of God, or any inevitable or unforeseen event
							beyond human control directly or indirectly interfering with the
							supply of stores or from any cause which may be a reasonable
							ground or an extension of time, the competent authority will
							allow such additional time as he considers to be justified in the
							circumstances of the case. No compensation will be payable to the
							contractor for any loss incurred by him due to these reasons.
						</p>
						<p>
							<b>2.37:</b> Each tenderer shall supply the name, residence and
							place or business of the person or persons giving the tender and
							shall be signed by the tenderer with his usual signature. When
							tender is given by partnerships the full names of all partners
							shall be furnished. An attested copy of the constitution of the
							firm and the registration number of the firm shall be furnished.
							In such a case the tender must be signed separately by each
							partner thereof or in the event of the absence of any partner it
							must signed on his behalf by a person holding a power of attorney
							authorising him to be so. Tenders by a corporation shall be
							signed with the legal name of the corporation followed by the
							name of the stage of incorporation and by signature and by
							designation of the president, secretary or other persons
							authorised to bind it in the matter.
						</p>
						<p>
							<b>2.38 Technical Knowledge and staff:</b>
						</p>
						<p>
							<b>2.38.1:</b> The tender shall be submitted with the declaration
							that the contractor has successfully carried out large works of
							this nature and has adequate organization, machinery and
							experienced personnel to handle jobs of this type and magnitude.
						</p>
						<p>
							<b>2.38.2 A brief description of large works previously
								executed by tenderer:</b> After the tender has been opened any
							tenderer may be required to submit detailed particulars of such
							works along with manner of their execution and any other
							information that will satisfy the officer receiving the tender
							that the contractor has adequate organisation, Including
							experienced personnel to execute vigorously the work to be
							carried out as per these specifications.
						</p>
						<p>
							<b>2.38.3</b>
						</p>
						<p>
							<b>(a)</b> The contractor shall employ the following Technical
							Staff during the execution of work-
						</p>
						<ul class="text-left">
							<li><b>(i)</b> One graduate engineer when the work to be
								executed is more than Rs. 25 lakhs.</li>
							<li><b>(ii)</b> One diploma engineer when the cost of work
								to be executed is from Rs. 5 lakhs to 25 lakhs.</li>
						</ul>
						<p>
							<b>(b)</b> The Technical Staff should be available at site and
							take instructions from the Engineer-in-Charge or other
							supervisory staff
						</p>
					</div>
				</div>
				<!---  18 Page Ends Here --->
				<!---  19 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>(c)</b> Incase the contractor fails to employ the technical
							staff as afore said, the E.E. shall have the right to take
							suitable remedial measures.
						</p>
						<p>
							<b>(d)</b> The contractor shall give the names and other details
							of the graduate engineer/diploma engineer to whom he intends to
							employ or who is under employment with him , at the time of
							agreement and also give his curriculum vitae.
						</p>
						<p>
							<b>(e)</b> The contractor shall give a certificate to the effect
							that the graduate engineer/diploma engineer is exclusively in his
							employment.
						</p>
						<p>
							<b>(f)</b> A graduate engineer or diploma engineer may look after
							more than one work in the same locality but the total value of
							such works under him shall not exceed Rs. 100 lakhs in the case
							of a graduate engineer and Rs. 50 lakhs in the case of a diploma
							engineer
						</p>
						<p>
							<b>(g)</b> It shall not be necessary for the firm/company whose
							one of the partner is a graduate engineer / diploma engineer to
							employ another graduate engineer / diploma engineer subject to
							the conditions provided under 2.38.3 (a),(b) and (f)
						</p>
						<p>
							<b>(h)</b> The Retired Assistant engineer who is holding a
							diploma may be treated at par with a Graduate for the operation
							of the above clause.
						</p>
						<p>
							<b>Note:-</b> Such Degree or Diploma engineer must be always
							available on works site on day to day basis and actively
							supervise, instruct and guide the contractor’s works force and
							also receive instruction form the Departmental Engineers/Sub
							engineers. In case the contractor fails to employ the above
							technical staff or fails to employ technical staff /personnel as
							submitted by the contractor in Pre qualification documents if
							prequalification is called and or the technical staff/personnel
							so employed are generally not available on work site and or does
							not receive or comply the instructions of the Department
							Engineers, the <span class="colour-report"><b>E.E.</b></span>
							shall recover/deduct from his bills, a sum of Rs. 2500/¬per week
							of such default. If the default continues for more than 4 weeks
							then such default can be treated as "Fundamental Breach of
							Contract" and the contract can be terminated and action shall be
							taken under clause 1.14
						</p>
						<p>
							<b>2.39:</b> The Contractor should also give the following
							information invariably on cover containing the tender.
						</p>
						<p>
							<b>A:</b> Name and address of the Contractor:<br /> <b>B:</b>
							Class in which he is registered:<br /> <b>C:</b> Amount of
							earnest money deposited and No. and date of money receipt etc.
						</p>
						<p>
							<b>2.40:</b> The tender documents have to be completed and
							submitted with all the documents required in the tender notice,
							Following is the summary of the documents required to be
							submitted with the completed tender form.
						</p>
						<p>
							<b>2.40.</b>
						</p>
						<ul class="text-left">
							<li><b>(I):</b> The name, residence and place of business
								etc. of the tenderer vide Clause 2.37 above.</li>
							<li><b>(II):</b> Details of contracts already held by the
								tender vide clause 2.24 above.</li>
							<li><b>(III):</b> Receipt of Earnest money deposited vide
								clause 2.5.1 above or surety bond from bank.</li>
							<li><b>(IV):</b> Income tax clearance certificate vide
								clause 2.21 above.</li>
							<li><b>(V):</b> A list of near relatives of the tenderer
								working in <mark class="colour-report">
									<b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
								</mark> Vide clause 2.32. (See Annexure -J)</li>
							<li><b>(VI):</b> Attested copy of the constitution of firm
								(if required) and power of attorney, as required vide Clause
								2.37.</li>
							<li><b>(VII):</b> A declaration that there has been no
								conviction imprisonment for an offence involving moral
								turpitude.</li>
						</ul>

					</div>
				</div>
				<!---  19 Page Ends Here --->
				<!---  20 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<ul class="text-left">
							<li><b>(VIII):</b> Declaration and description as required
								vide Clause 2.38.1 and 2.38.2.</li>
						</ul>
						<p>
							<b>2.41 Registration with Labour Commissioner:</b> No tender
							shall be accepted and no contract given to any contractor or firm
							who/which is not registered as an Institution with Labour
							Commissioner, C.G. under Contract Labour’s (Regulation and
							Abolition) M.P. Rules, 1983 and the tenderers shall have to
							accompany with a license to this effect.
						</p>
					</div>
				</div>
				<!---  20 Page Ends Here --->
				<!---  21 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>CHAPTER - III</b>
						</h2>
						<p>
							<b>3.1 General:</b> The special conditions are supplementary
							instructions to the tenderers and would forms part of the
							contract.
						</p>
						<p>
							<b>3.2 Drawing:</b> Drawings given, listed and indexed in part
							will form part of the contract.<br />The above drawings show the
							work to be done as definitely and in such details as is possible,
							at the present stage of development of investigation and the
							design. The attached drawings will be supplemented or superseded
							by such additional and detailed drawings as may be necessary or
							desired as the work proceeds. Such additional general and
							detailed drawings will show dimensions and details necessary for
							constructions purposes more completely than are shown on the
							attached drawings. For all features of the work The contractor
							shall be required to perform the work, on these features and in
							accordance with additional general and detailed drawings
							mentioned above at the applicable unit prices tendered in the
							schedule for work or work of similar nature as determined by the
							Engineer-in-charge. The contractor shall check all drawings
							carefully and advise the Engineer-in-charge of any errors or
							omissions discovered. The contractor shall not take advantage of
							errors or omissions as full instruction will be furnished to the
							contractor should any errors or omissions be discovered.
						</p>
						<p>The drawings and specifications are to be considered as
							complementary to each other and should anything appear in one
							that is not described in the other no advantage shall be taken of
							such omission. In case of disagreement between specifications
							shall govern the contract. Should any discrepancies, however,
							appear or should any misunderstanding arise as to the meaning and
							interpretations of the said specifications or drawings or as to
							the dimensions or the quality of the materials for the proper
							execution of the work or as to the measurements or quality and
							valuation of work executed under this contract or extra there
							upon, the same shall be explained by the Engineer-in-charge.</p>
						<p>Figures in dimensioned drawings shall supersede
							measurements by scale and drawings to a large scale shall take
							precedence over those on a small scale. Special directions
							incorporated on the drawings shall be complied with strictly.</p>
						<p>One copy of the drawings and contract documents shall be
							kept at all times at the site of the works by the contractor.</p>
						<p>
							<b>3.3 Data to be furnished by the Contractor:</b> The Contractor
							shall submit the following information to the Engineer-in-charge.
						</p>
						<p>
							<b>3.3.A:</b> Proposed constructions programme and time schedule
							showing sequence of operations with in two weeks of receipt of
							notice to proceed with the work in pursuance of the conditions of
							contract. Along with the above he will also submit programme of
							bringing requisite tools and plants, machinery to be engaged by
							him to the site of work.
						</p>
						<p>
							<b>3.4 Programme of Construction:</b> The Contractor shall submit
							the detailed, year-wise construction programme with in 14 days of
							the date of notice to proceed with the work. This programme may
							be reviewed and revised every year at the beginning of the
							working season.
						</p>
						<p>
							<b>3.5 Action when the progress of any crucial item of work
								is unsatisfactory:</b> If the progress of a crucial item of work,
							which is important for timely completion of work, in
							unsatisfactory the Engineer-in-charge shall, not withstanding
							that the general progress of work is satisfactory. After giving
							the contractor 10 days notice in writing and the contractor will
							have no claim for compensation for any loss sustained by him
							owing to such action.
						</p>
						<p>
							<b>3.6 Inspection and Tests:</b> Except as otherwise provided in
							here of all material and workmanship. If not otherwise designated
							by the specifications shall be subject to inspection. Examination
							and test by the Engineer-in-Charge at any and all times during
							manufacture and/or construction and at any/all places where such
							manufacture or constructions are carried on. The
							Engineer-in-charge shall have the right to reject defective
							materials and workmanship or require its corrections. Rejected
							workmanship shall be satisfactorily replaced with the proper
							material without charge thereof and the contractor shall properly
							segregate and remove the rejected material from the premises.
						</p>
					</div>
				</div>
				<!---  21 Page Ends Here --->
				<!---  22 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>If the contractor fails to proceed at once with the
							replacement of the rejected material and/or the construction of
							defective workmanship the Engineer-in-charge any replace such
							material and/or correct such workmanship and charge the cost
							thereof to the contractor.</p>
						<p>The Contractor shall be liable for replacement of defective
							work up to the time in accordance with clause 1.9 of the
							conditions of contract of all work to be done under the contract.</p>
						<p>The contractor shall furnish promptly without additional
							charge all facilities, labour and material necessary for the safe
							and convenient inspection and tests that may be required by the
							Engineer-in-Charge. All inspections and tests by the departments
							shall be performed in such a manner as not unnecessarily to delay
							the work. Special full size and performance test shall be charged
							with any additional cost of inspection when materials and
							workmanship are not ready by the contractor at the time of
							inspection.</p>
						<p>
							<b>3.7 Removal of temporary work, Plant & Surplus materials:</b>
							Prior to final acceptance of the completed work, but excepting as
							otherwise expressly directed or permitted in writing, the
							contractor shall, at his own expenses remove from the site and
							dispose of all the temporary structures including buildings, pile
							work, crib work, all plant and surplus materials, and all rubbish
							and debris for which he is responsible to the satisfaction of
							Engineer-in-Charge.
						</p>
						<p>
							<b>3.8 Possession prior to completion:</b> The Engineer-in-Charge
							shall have the right to take possession of or use any completed
							part of the work. Such possession or use shall not be deemed as
							an acceptance of any work not completed in accordance with the
							contract.
						</p>
						<p>
							<b>3.9 Damage to works:</b> The works whether fully completed or
							incomplete, all the materials, machinery, plants, tools,
							temporary building and other things connected there with shall
							remain at the risk and in the sole charge of the contractor until
							the completed work has been delivered to the Engineer-in-Charge
							and till completion certificate has been obtained from the
							Engineer-in-charge. Until such delivery of the completed work,
							the contractor shall at his own cost take all precautions
							reasonably to keep all the aforesaid works, materials, machinery,
							plants, temporary buildings and other things connected there with
							free from any loss, damages and in the event of the same or any
							part there of being lost or damaged, he shall forthwith reinstate
							and made good such loss or damages at his own cost.
						</p>
						<p>
							<b>3.10 Examination and tests on completions:</b> On the
							completion of the work and not later than three months there
							after, the Engineer-in-charge shall make such examination and
							tests of the work as may than seem to him to be possible,
							necessary or desirable, and the contractor shall furnish free of
							cost any materials and labour which may be necessary therefore
							and shall facilitate in every way all operations required by the
							Engineer-in-Charge, in making examination and tests.
						</p>
						<p>
							<b>3.11 Climatic Conditions:</b> The Executive Engineer may order
							the contractor to suspend any work that may be subject to the
							damage by climatic conditions and no claims of the contractor
							will be entertained by the department on this account.
						</p>
						<p>
							<b>3.12 Safety regulations:</b> While carrying out this work, the
							contractor will ensure compliance of all safety regulations as
							provided in the Safety Code (Annexure - "H")
						</p>
						<p>
							<b>3.13 Haul roads:</b> A fair weather road of the standard of a
							village cart track is orginarily maintained by the department
							along the canal alignment which is motorable from November to end
							of May, but contractor shall not have any claim on this account
							if one is not provided or maintained. Necessary haul roads to
							work sport borrow areas and water sources shall be satisfactorily
							constructed and maintained by the contractor at his own cost. The
							contractor has to construct and maintain his own approach roads
							from the main haul roads provided by the department. Any new haul
							roads will have also to be constructed and maintained by the
							contractor at his cost.
						</p>
						<p>
							<b>3.14 The Contractor will make his own arrangement:</b> for
							supply of Water, Light & Power for his works and labour camps
							etc.: The contractor will make his own arrangement for supply of
							water light and power for his works and labour camps etc. The
							department will not entertain any claim what soever for any
							failure or break down etc. in supply or electricity to the
							contractor. The Contractor will
						</p>
					</div>
				</div>
				<!---  22 Page Ends Here --->
				<!---  23 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>supply and fix his own tested meter of the approved make
							but the meter will be kept in the custody of the department.</p>
						<p>
							<b>3.15 Interference with other Contractors:</b> The contractor
							must not interfere with other contractors who may be employed
							simultaneously or otherwise by the department. He will at no lime
							engage departmental labour or that of other contractors without
							the written permission of the Engineer-in-Charge.
						</p>
						<p>
							<b>3.16 Regulations and bye laws:</b> The contractor shall
							conform to the regulations, bye-laws any other statutory rules
							made by any local authorities or by the Government and shall
							protect and indemnify Government against any claim or liability
							arising from or based on the violation of any such laws,
							ordinance, regulation, orders, decrees etc.
						</p>
						<p>
							<b>3.17 Order Book:</b> An order book shall be kept in the
							departmental office at the site of the work. As far as possible
							all orders regarding the works are to be entered in this book.
						</p>
						<p>All entries therein shall be signed by the departmental
							officers in direct charge of the work and the contractor or his
							representatives. In the important cases the Executive Engineer or
							the Superintending Engineer will countersign the entries which
							site except with the written permission of the superintending
							Engineer and the Contractors or his representative shall be bound
							to take note of all instructions meant for the contractor as
							entered in the order book without having to be called for
							separately to not them. The Engineer-in-charge shall submit
							periodically copies of the remarks of the order book to the
							Superintending Engineer and Chief Engineer for record and to the
							contractor for compliance and report.</p>
						<p>
							<b>3.18 Conversion of units:</b> Whenever in the contract
							agreement dimensions and units have been expressed in F.P.S.
							system, the same will be converted in to metric system units by
							supplying the standard conversion table of Indian Standard
							Institution so as to derive the corresponding figure
							arithmetically and the contractor will have to accept the figures
							so derived without any claim or compensation whatsoever.
						</p>
						<p>
							<b>3.19 Rights of other contractors and persons:</b> If, during
							the progress of the work covered by this contract, in its
							necessary for other contractors or persons to do work in or about
							the site of work, the contractor shall afford such facilities, as
							the Engineer-in-charge may require.
						</p>
						<p>
							<b>3.20 Employment of technical persons:</b> In accordance with
							the requirement of clause 2.38.3 the contractor will employ or
							produce evidence of having in his employment a qualified
							technical person not below the rank of a Sub-Engineer/Graduate
							Engineer from an Institution recognised by the Government of
							Chhatisgarh and furnish full details to the Engineer-in-charge in
							the following format:
						</p>

						<li><b>(I):</b> Name of the Sub-Engineer/Graduate Engineer
							engaged quoting Diploma or Degree with name of Institutions.</li>
						<li><b>(II):</b> Period for which the Sub-Engineer/Graduate
							Engineer has been engaged with emoluments</li>
						</ul>

						<del>
							<p>
								<b>3.21 ADVANCES TO CONTRACTORS:</b>
							</p>
							<p>
								<b>The provision of (i) Mobilization advance& (ii) Advance
									on plant and machinery) will apply to contract above Rs. one
									crore only</b>
							</p>
							<p>
								<b>3.21.1 Mobilization advance:</b> - Mobilization advance up to
								5 % (Five percent) of the contract value shall be given if
								requested by the contractor within one month of the date of
								order to commence the work. In such a case the contractor shall
								furnish Bank Guarantee from schedule bank for the equal amount
								in favour of the Executive Engineer before sanction and release
								of the advance. This advance shall be Interest free. This 5%
								(Five percent) advance shall be given in the two stages
							</p>
							<p>
								Stage-1: - 2%(Two percent) of the contract value payable after
								signing of the agreement<br /> Stage-2: - 3%(Three percent) of
								the contract value payable on receipt of the certificate from
								the contractor that he has established complete central and
								field testing laboratories and has engaged
							</p>
						</del>
					</div>
				</div>
				<!---  23 Page Ends Here --->

				<!---  24 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<del>
							<p>
								workers/technicians and have brought requisite plants and
								machineries at work site, the work is physically started and
								only after construction programme is submitted by the contractor
								and is duly approved by the Executive Engineer.<br /> Executive
								Engineer shall sanction the mobilization advance
							</p>
							<p>
								<b>3.21.2 Advance on plant and machinery: -</b>
							</p>
							<p>Advance up to 5%(five percent) of the contract value shall
								be given, if requested by the contractor, only for the new plant
								and machineries required for the work and brought to the site by
								the contractor .In such a case the contractor shall furnish Bank
								Guarantee from schedule bank for the equal amount in favour of
								the Executive Engineer before sanction and release of the
								advance. The advance shall be limited to 90% (ninety percent) of
								the price of such new plant and machineries. This advance shall
								be interest free.</p>
							<p>This 5% (Five percent) advance shall be given in the two
								stages</p>
							<p>
								Stage -1: - 2%(Two percent) of the contract value after plant
								and machinery has arrived at the site<br /> Stage-2: - 3%(Three
								percent) of the contract value payable after installation of
								such plant & machinery etc.
							</p>
							<p>This advance shall be made against hypothecation of plants
								and machineries in favour of the Executive Engineer in charge</p>
							<p>Sanctioning authority for the this advance shall be
								Superintending Engineer</p>
							<ul class="text-left">
								<li><b>(a)</b> The contractor shall not remove these plants
									and machineries from the work site without prior written
									permission from the Executive Engineer</li>
								<li><b>(b)</b> The contractor shall submit an affidavit
									along with the application that he has not received or applied
									for advance against plant and machineries for which the advance
									is applied, in any other agreement/office/institution</li>
							</ul>

							<p>
								<b>3.22 Recovery of advances: -</b>
							</p>
							<p>Recovery of above advances (mobilization, plant and
								machineries) will start when 15(fifteen)% of the work is
								executed and recovery of total advance should be completed by
								the time 80(eighty) % of the original contract work is executed
								or when 75% (seventy five percent) of stipulated or validly
								extended period is over; whichever is earlier.</p>
						</del>
						<p>
							<b>3.23 Secured advance:-</b> Advances to contractor are as a
							rule prohibited, and every endeavor should be made to maintain a
							system, under which no payments are made for unmeasured work
							except for work actually done. Exceptions are, however permitted
							in the following cases: -
						</p>
						<p>Cases in which a contractor whose contract is for finished
							work, requires an advance on the security of materials brought to
							site, Executive Engineer may in such cases sanction advances up
							to an amount not exceeding 75% of the value of material and 90%
							in the case of steel (as assessed by the Executive Engineer)
							provided that the rate(s) of allowed in no case is/are more than
							the rate payable for the finished item as stipulated in the
							contract of such materials, provided that they are of
							imperishable nature and that a formal agreement is drawn up with
							the contractor under which Government secures a lien on the
							materials and is safeguarded against losses due to the contractor
							postponing the execution of the work or to the shortage or misuse
							of the materials, and against the expense entitled for their
							proper watch and safe custody.</p>
						<p>Payment of such advances should be made only on the
							certificate of an officer not below the rank of Assistant
							Engineer, that the quantities of materials upon which the
							advances are made have actually been brought to site, that the
							contractor has not previously received any advance on that
							security and that all the materials are required by the
							contractor for use on items of work for which rates for finished
							work have been agreed upon. Recoveries of advances so made should
							not be postponed until the whole of the work entrusted to the
							contractor is completed. They should be made from his bills for
							work done as the materials are used the necessary deductions
							being made whenever the item of work in which they are used; are
							billed for.</p>
					</div>
				</div>
				<!---  24 Page Ends Here --->
				<!---  25 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>Before granting the above-secured advance the contractor
							shall sign the prescribed Indenture Bond in the prescribed form.</p>
						<p>
							<b>3.24 Scope of Lumpsum cost:</b> The lumpsum contract shall
							comprise of the construction, completion and maintenance of the
							works and provision of all labour, materials, constructional
							plants, transport and all works of a temporary or permanent
							nature reguired for such construction, completion and maintenance
							in so far as the necessary for providing the same is specified in
							the contract.
						</p>
						<p>
							<span class="color-blue"><b>3.25 Deleted::</b>
						</p>
						<p>
							<b>3.26 Open foundations:</b> The Contractor's lumpsum should
							include provision for cofferdam, diversion of drain or stream and
							bailing out of water or dewatering foundations and shoring etc.
						</p>
						<del>
							<p>
								<b>3.27 Low water level variation:</b> The low water level given
								in the NIT is for general guidance of the contractor. It is
								liable to vary. No claim due to variation of low water level
								shall be entertained.
							</p>
						</del>
						<p>
							<b>3.28 Schedule of running payment:</b> schedule of running
							payment may be based on the following breakup of the lump sum
							subject to the stipulations of clause 1.15 of conditions of
							contract. No payment shall be made for design work separately.
						</p>
						<p>
							<b>Percentage wise Break Up schedule of Payment for following
								works:-</b>
						</p>
						<table class="text-left">
							<tbody>
								<tr>
									<td>1</td>
									<td width="50%">RCC elevated service reservoirs (2nos)</td>
									<td>-</td>
									<td><span class="colour-report"><b>80%</b></span></td>
								</tr>
								<tr>
									<td>2</td>
									<td>RCC Sump well (2nos)</td>
									<td>-</td>
									<td><span class="colour-report"><b>20%</b></span></td>
								</tr>
								<tr>
									<td colspan="4">-----------------------------------------------------------------------------------------</td>
								</tr>
								<tr>
									<td></td>
									<td>Total</td>
									<td>-</td>
									<td>100%</td>
								</tr>
							</tbody>
						</table>
						<br />
						<p class="text-center">
							STAGE WISE BREAK UP SCHEDULE OF PAYMENT FOR OHT i.e. FOR <span
								class="colour-report"><b>80%</b></span> VALUE
						</p>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>S</th>
									<th>Particulars</th>
									<th>Percentage Payment</th>
									<th>Cumulative percentage</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td class="text-left">After casting of levelling course of
										all 2nos OHT's</td>
									<td>5%</td>
									<td>5%</td>
								</tr>
								<tr>
									<td>2</td>
									<td class="text-left">After foundation including columns
										up to G.L. of all 2nos OHT's</td>
									<td>10%</td>
									<td>15%</td>
								</tr>
								<tr>
									<td>3</td>
									<td class="text-left">After casting of 50% RCC staging of
										all 2nos OHT's</td>
									<td>10%</td>
									<td>25%</td>
								</tr>
								<tr>
									<td>4</td>
									<td class="text-left">After full staging of all 2nos OHT's</td>
									<td>10%</td>
									<td>35%</td>
								</tr>
								<tr>
									<td>5</td>
									<td class="text-left">After ring beam & bottom slab
										casting & supply of pipes and specials of all 2nos OHT's</td>
									<td>25%</td>
									<td>60%</td>
								</tr>
								<tr>
									<td>6</td>
									<td class="text-left">After casting vertical walls of tank
										of all 2nos OHT's</td>
									<td>10%</td>
									<td>70%</td>
								</tr>
								<tr>
									<td>7</td>
									<td class="text-left">After casting stair case and top
										dome/slab<br /> (Including railing, water level indicator,
										lighting arrester, snow cem painting/oil painting/steel ladder
										etc.) of all 2nos OHT's
									</td>
									<td>20%</td>
									<td>90%</td>
								</tr>
								<tr>
									<td>8</td>
									<td class="text-left">After finishing and testing of work
										of all 2nos OHT's</td>
									<td>10%</td>
									<td>100%</td>
								</tr>
							</tbody>
						</table>
						<p class="padding-bottom-0">
							<b><i>Important Note: -</i></b>
						<ul class="text-left padding-top-0">
							<li><b>i. 90% payment of the amount of the SN. 01 to 08
									shall be paid as per clause 1.15 of the agreement for 'F'</b></li>
							<li><b>ii. Payment of each above shown items may be paid
									to the contractor for completion of each OHT and so on.</b></li>
						</ul>
					</div>
				</div>
				<!---  25 Page Ends Here --->
				<!---  26 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p class="text-center">
							STAGE WISE BREAK UP SCHEDULE OF PAYMENT FOR SUMP WELL i.e. FOR <span
								class="colour-report"><b>20%</b></span> VALUE
						</p>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>S</th>
									<th>Particulars</th>
									<th>Percentage Payment</th>
									<th>Cumulative percentage</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td class="text-left">After casting of levelling course of
										all 2nos Sumps</td>
									<td>15%</td>
									<td>15%</td>
								</tr>
								<tr>
									<td>2</td>
									<td class="text-left">After casting base slab & column
										footings if provided of all 2nos Sumps</td>
									<td>15%</td>
									<td>30%</td>
								</tr>
								<tr>
									<td>3</td>
									<td class="text-left">After casting of 50% wall of all
										2nos Sumps</td>
									<td>20%</td>
									<td>50%</td>
								</tr>
								<tr>
									<td>4</td>
									<td class="text-left">After casting of full height of wall
										of all 2nos Sumps</td>
									<td>20%</td>
									<td>70%</td>
								</tr>
								<tr>
									<td>5</td>
									<td class="text-left">After casting of roof slab of all
										2nos Sumps</td>
									<td>10%</td>
									<td>80%</td>
								</tr>
								<tr>
									<td>6</td>
									<td class="text-left">After fixing of pipes, valves,
										specials, Cowels , ladder, manhole frame & cover etc. of all
										2nos Sumps</td>
									<td>10%</td>
									<td>90%</td>
								</tr>
								<tr>
									<td>7</td>
									<td class="text-left">After finishing and testing of work
										of all 2nos Sumps</td>
									<td>10</td>
									<td>100%</td>
								</tr>
							</tbody>
						</table>
						<p class="padding-bottom-0">
							<b><i>Important Note: -</i></b>
						</p>
						<ol type="i" class="text-left padding-top-0">
							<li><span class="colour-report"><b>i- 90% payment
										of the amount of the SN. 01 to 07 shall be paid as per clause
										1.15 of the agreement for 'F'</b></span></li>
							<li><span class="colour-report"><b>ii- Payment of
										each above shown items may be paid to the contractor for
										completion of each OHT and so on.</b></span></li>
						</ol>
						<p>Running payment shall be made as per detailed schedule of
							running payments.</p>
						<del>
							<p>The details schedule shall as prepared within the broad
								break up as indicated above. The detailed schedule shall be
								prepared by mutual agreement between the contractor and the
								Department. Proportional part payment shall be made for
								incomplete items of work as given in the detailed schedule. No
								provision shall be allowed in the detailed schedule for
								arranging materials like H.T. Steel, bearings, centering and
								form work.</p>
							<p>In the event of variation of the span arrangement after
								finalization of the contract, the detailed schedule shall
								revised within the broad break up as approved earlier.</p>
						</del>
						<p>
							<b>3.29 Extra work and rebate:</b> Extra /Rebate work arising out
							of this contract shall be valued at par with SOR for Water supply
							& sewerage work issued by
							<mark class="colour-report">
								<b>Engineer-In-Chief PHED Raipur</b>
							</mark>
							with effect from
							<mark class="colour-report">
								<b>07.02.2013</b>
							</mark>
							and amended up to the Date of issue of NIT ± (Plus, Minus) the
							Percentage which Tender cost bears to the P.A.C. shown in the
							tender document at the time of sanction.
						</p>
						<p>
							<b>For any item of work for which there is no rate in the
								said SOR shall be decided by the SE taking in to consideration
								the expenses incurred by the contractor and its reasonable-ness
								which shall be final and binding.</b>
						</p>
						<p>
							<b>3.30:</b> Any tenderer if choose to quote on his alternative
							drawing, can furnish his alternative drawing in original offer
							only. In case the original offer is quoted on the Departmental
						</p>
					</div>
				</div>
				<!---  26 Page Ends Here --->
				<!---  27 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>CHAPTER-IV<br />SCOPE OF WORK & SPECIFICATION OF WORK
							</b>
						</h2>
						<p>
							<b>4.1 THE WORK INVOLVES :</b>
						</p>
						<p>
							<b>4.1.1</b> DESIGN, CONSTRUCTION, TESTING & COMMISSIONING OF RCC
							ELEVATED SERVICE RESERVOIRS OF FOLLOWING CAPACITY ON DIFFERENT
							STAGING & RCC SUMP WELL FOR KONDAGAON WATER SUPPLY SCHEME
							SANCTIONED UNDER 13TH FINANCE
						</p>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th rowspan="2" width="12%">ZONE</th>
									<th rowspan="2" width="26%">Location</th>
									<th colspan="3">RCC Elevated service reservoirs</th>
									<th rowspan="2" width="20%">Sump well Capacity (KL)</th>
								</tr>
								<tr>
									<th>No</th>
									<th>Capacity (KL)</th>
									<th>Staging (m)</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>I & II</td>
									<td>Aadakachhepeda ward</td>
									<td>1</td>
									<td>350</td>
									<td>17</td>
									<td>105</td>
								</tr>
								<tr>
									<td>III & IV</td>
									<td>Dongri Para ward</td>
									<td>1</td>
									<td>350</td>
									<td>17</td>
									<td>105</td>
								</tr>
								<tr>
									<th colspan="2">Total</th>
									<td>2</td>
									<td>700</td>
									<td>-</td>
									<td>210</td>
								</tr>
							</tbody>
						</table>
						<p>
							including providing and fixing of ISI mark double flanged C.I.
							pipes class ‘B’ Horizontally cast conforming to IS 7181-1974
							<span class="colour-report"><b>OR D.I. double flanged
									pipes class-K9 as per relevant IS specifications</b></span> for Inlet,
							Outlet, scour and over flow delivering up to minimum 5.0 metre
							away from R.C.C. outer columns of reservoir, ISI mark C.I. double
							flanged heavy duty specials and duck foot bends conforming to IS
							1538-1976, and C.I. double flanged ISI marked sluice valves class
							PN 1.6 conforming to IS 14846:2000 of IVC/KIRLOSKAR /VAG<span
								class="colour-report"><b>/IVI/DURGA/UPADHAYAYA</b></span> make
							with dismantling joints etc. R.C.C. valve chambers with R.C.C.
							cover and providing and fixing of all accessories such as
							lightening arrestor, water level indicator, RCC stair case from
							ground level to Balcony and from balcony to roof of the O.H.T.
							and steel ladder from roof to inside floor level of tank,
							ventilating cowls, manhole covers with frame and G.I. pipe
							railing for stairs balcony and at roof, flooring etc. all
							complete as per detailed specifications on lump sum basis.
						</p>
						<p>
							<b>4.1.2</b> The Service Reservoir should be circular in shape
							supported over circular columns. The depth of water in the tank
							shall be less than 4.0 mt. the difference of level between lowest
							supply and full supply level of the tank. The foundation,
							columns, bracings etc. should be so designed, so as to have a
							provision for construction of a single story building between the
							G.L. bracing and first bracing with R.C.C. roof and brick walls.
							The live load of roof slab shall be taken as 150 kg/m2 and the
							dead load of roof slab shall be taken as 360 kg/sqm. The position
							of bottom of bracing shall be so fixed that the head room of
							ground floor if constructed should be approximately 3.5m. The
							construction of such building works is not included in this lump
							sum contract.
						</p>
						<p>
							<b>4.1.3</b> Construction of <span class="colour-report"><b>INTZ</b></span>
							type tank & dome for bottom as well as roof slab shall not be
							allowed in this contract.
						</p>
						<p>
							<b>4.2 GENERAL</b>
						</p>
						<p>
							<b>4.2.1</b> The work of construction of R.C.C. Over Head Service
							Reservoirs involves specialised workmanship, hence requirement of
							higher standard than general concrete work is essential. The
							height of staging will be reckoned from an assumed ground level
							at the site and road level nearest to the site, i.e., the ground
							level at the site or road level which ever is higher shall be
							treated as the base level for determination of staging height.
						</p>
						<p>
							<b>4.2.2</b><span class="colour-report"> Contractor has
								carried out plate load test to ascertain bearing capacity for
								all design purposes.</span>
						</p>
						<p>
							<b>4.2.3</b> The offer shall include provision for 1.2 m wide
							R.C.C. balcony at bottom slab level, railing on balcony and at
							roof level, lightening arrestor, water level indicator, 1.2 meter
							wide RCC stairs from ground level to balcony and 0.70 m wide RCC
							stairs from balcony to roof of the tank. Steel ladder from roof
							of the tank to floor of the tank, all pipes and fitting
							(including puddle collars) up to duck foot bends and from duck
							foot bend up to 5.0 meters out side the supporting structures
							i.e. RCC column including C.I. valves etc. including their
							painting and architectural treatment, protection and drainage
							work.
						</p>
					</div>
				</div>
				<!---  27 Page Ends Here --->
				<!---  28 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>4.3 S.B.C.</b>
						</p>
						<p>
							<b>4.3.1</b> SBC of the soil is to be examined by contractor by <span
								class="colour-report"><b>Engineering
									College/Polytechnic.</b></span>
						</p>
						<p>
							<b>4.3.2 TRIAL PIT</b><br /> Contractors are advised to carry
							out there own trial pit section to get the idea of soil
							condition.
						</p>
						<p>
							<b>4.4 STEEL :</b>
						</p>
						<p>
							<b>4.4.1</b> The contractor shall have to arrange himself the
							entire quantity of steel required for the completion of the work
							under contract, No steel shall be supplied by the department. No
							extension of time will be granted by the department for non
							availability of or non procurement of steel in time or late
							supply of steel or for any other reasons what-so-ever.
						</p>
						<p>
							<b>4.4.2</b> The steel for reinforcement shall be ISI mark thermo
							mechanically treated bars conforming to relevant ISS a test
							certificate shall be required to be furnished to the department
							in support thereof. The stresses in steel for design purposes
							should be taken as specified in IS Code 3370 (Part-II) 1965
							amended up to date.
						</p>
						<p>
							<b>4.4.3</b> In additions, the contractor shall be required to
							get tested the random samples of the Steel brought at site to see
							whether they conform to relevant I.S. specifications. The cost of
							such tests shall be borne by the contractor.
						</p>
						<p>
							<b>4.5</b> The contractor shall have to make his own arrangement
							for requirement of electric power and telephone connections for
							construction work, if they desire these facilities. The
							department shall provide assistance by way of recommendation
							only.
						</p>
						<p>
							<b>4.6</b> For blasting operation, if required in foundation,
							excavation, the contractor will make his own arrangement for
							license, permits and blasting materials from competent authority.
						</p>
						<p>
							<b>4.7</b> The contractor will have make his own arrangement for
							water, required for execution of the works. For testing of tanks,
							the department will provide water at ground level near the tank.
							However all other arrangement for testing of tank shall have to
							be made by the contractor at his own cost.
						</p>
						<p>
							<b>4.8</b> The tank will have a 1.2 m wide balcony all around the
							tank at bottom slab level.
						</p>
						<p>
							<b>4.9</b> The RCC stairs of 1.2 m width from ground level to the
							balcony at bottom slab level should be provided. The width of the
							R.C.C. stairs from balcony to roof of the tank shall be 0.70 m.
						</p>
						<p>
							<b>4.10</b> G.I. Pipe railing of 25mm dia, medium class, in three
							rows shall be provided for RCC stairs from ground level to
							balcony, around the balcony, one side on stairs from balcony to
							roof of the tank and around the roof slab.
						</p>
						<p>
							<b>4.11</b> The Railing post should consists of 125 mm dia RCC
							posts having four nos. T.M.T. bars of 8 mm dia, 1.0 m height @
							1.2 m c/c. These bars shall be welded with the main reinforcement
							of RCC stairs, balcony and roof slab. The RCC post shall be very
							carefully fixed and embedded into concrete.
						</p>
						<p>
							<b>4.12</b> A S.S. steel ladder shall be provided from man hole
							on the top slab to the inside floor of the tank. The steel ladder
							600 mm wide shall comprise of 2 nos. 100 mm x 12 mm S.S. steel
							flat section stringers and 2 nos 20 mm dia S.S. bar footrest at
							400 mm c/c. The bars of 20 mm dia shall be inserted in to the
							flat section by drilling holes in the flat and the bars shall be
							welded with the flat from inside and outside. The ladder shall be
							finished at welded joints so that all sharp edges are removed.
							The ladder shall be painted with chlorine resistant epoxy paints.
						</p>
						<p>
							<b>4.13</b> To avoid any unauthorised person to climb the over
							head tank, the entry at the bottom of the stair case should be
							closed with suitable arrangements like M.S. Gate.
						</p>
						<p>
							<b>4.14</b> Two C.I. Manhole covers and frame of approved quality
							455 mm x 610 mm internal dimension weighing not less than 38 kg
							shall be provided. The weight of cover shall be 23 kg and weight
							of frame 15 kg.
						</p>
						<p>
							<b>4.15</b> Air vents wherever necessary shall be 150 mm dia swan
							neck type, as per design.
						</p>
					</div>
				</div>
				<!---  28 Page Ends Here --->
				<!---  29 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>4.16</b> To Avoid any accident at the time of cleaning or
							maintenance of the tank, the opening of the out let and scour
							pipes in side the tank should be covered with Hemispherical
							stainless steel square bar jali having dimension 10mm x 10mm,
							50mm c/c spacing & embedded in in to slab/dome. The diameter of
							Hemispherie jail shall be 3 times of diameter of pipes.
						</p>
						<p>
							<b>4.17 Lightening conductor :-</b> Alluminium Lightening
							conductor shall be provided & fixed with proper earthing
							arrangements as per relevant IS Specification.
						</p>
						<p>
							<b>4.18 Water level indicator :-</b> Water level indicator shall
							comprise of copper float, guide, pulleys with a pointer on the
							enamel painted indicator plate which shall be calibrated to read
							the depth of water in the tank in meters
						</p>
						<p>
							<b>4.19 M.S. Clamps: -</b> The MS clamps 50mm x 10 mm should be
							provided at 3.0 M C/C for clamping all the vertical pipes, the
							clamps should be fixed in bracing with nut bolts.
						</p>
						<p>
							<b>4.20 Painting: -</b> Painting of OHT shall be done with
							premium smooth emulsion paint as per manufacturers specifications
							to give protective & decorative finish.
						</p>
						<p>
							<b>4.21 WORKMAN SHIP :</b>
						</p>
						<p>
							<b>4.21.1 EXCAVATION :</b><br /> The depth of excavation will
							generally be guided by the underground strata and the safe
							bearing capacity of the foundation soil and as directed by the
							Engineer-in-Charge. The contractor has to carry other tests of
							underground strata/soil at his own cost. No payment will be made
							to the contractor for carrying out test or on account of any
							variation in the soil bearing capacity & design change due to
							strata. No dewatering shall be payable under any circumstances
							whether natural, artificial man made.
						</p>
						<p>
							<b>4.21.2 FILLING FOUNDATION WITH BED CONCRETE (Levelling
								course) :</b><br /> The foundation shall be laid over bed concrete
							(i.e. levelling course) of at least 150 mm thick or more, with at
							least 1:3:6 (M-100) concrete with 40 mm gauge graded metal or the
							prescribed mix as per instruction of Engineer-in-Charge and as
							per relevant I.S. Code.
						</p>
						<p>
							<b>4.21.3 REINFORCED CONCRETE WORK :</b><br /> It shall be
							strictly as per Annexure 'E' special condition. The concrete mix
							and minimum cement concrete specified in Annexure 'E<sub>1</sub>'
							shall be rigidly followed all RCC work shall be carried out as
							per IS 456:2000.
						</p>
						<p>Where the concrete has not fully hardened all laitance
							shall be removed by scrubbing the wet surface with wire or
							bristle brushes, care being taken to avoid dislodgement of the
							particle of aggregate. The surface shall be thoroughly wetted and
							all free water removed. The surface shall then be coated with
							neat cement grout. The first layer of concrete to be placed on
							this surface shall not exceed 15 CM (or 6”) in thickness, and
							shall be rammed against old work, particulars attention being
							paid to corners and close spots. Concrete should be thoroughly
							compacted and fully worked around the reinforcement around
							embedded fixes and into corner of the form work.</p>
						<p>
							<b>4.21.4 MEASURING (Concrete mix proportioning) :</b><br /> The
							quantity of cement shall be determined by weight. The quantities
							of fine and coarse aggregates shall be determined either by
							volume or by weight. The proportion of find and coarse aggregate
							shall be in accordance to para 8 of IS 456-2000.
						</p>
						<p>
							<b>4.21.5 MIXING :</b><br /> Concrete shall be mixed in a
							mechanical mixer. Mixing shall be continued till there is a
							uniform distribution of the ingredients and the mass is uniform
							in colour and consistency but in no case the mixing shall be done
							for less than two minutes.
						</p>
						<p>
							<b>4.21.6 TRANSPORTING :</b><br /> Concrete shall be handled
							from the place of mixing to the place of final deposit as rapidly
							as practicable by methods which will prevent segregation or loss
							of any ingredients and maintaining the required workability.
						</p>
					</div>
				</div>
				<!---  29 Page Ends Here --->
				<!---  30 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>4.21.7 PLACING AND COMPACTING :</b><br /> The concrete shall
							be placed and compacted before setting could commence and should
							not be subsequently disturbed. Methods of placing should be such
							that there is no segregation (Concreting) shall be carried out
							continuously up to construction joints, the position and
							arrangement of which shall be determined by the designer. When
							the work has to be resumed on surface which has hardened, such
							surface shall be roughened. It shall then be swept clean, then
							the roughly wetted and covered with a 12 mm layer of mortar which
							shall be freshly mixed and placed immediately before the placing
							of the concrete.
						</p>
						<p>
							<b>4.21.8 MECHANICAL VIBRATION :</b><br /> When mechanical
							vibrations for compacting concrete are used, reduced water
							content should be adopted. Over vibration or vibration of very
							wet mixed is harmful and should be avoided when-ever vibration
							has to be applied externally the design of form work and the
							disposition of vibrators should receive special consideration to
							ensure efficient compaction and to avoid surface blemishing.
						</p>
						<p>
							<b>4.21.9 CURING :</b> The concrete shall be covered with a layer
							of old gunny bags or canvass or similar absorbent material and
							kept constantly wet for at least twenty eight days from the date
							of placing of concrete.
						</p>
						<p>
							<b>4.21.10 FORM WORK </b><br />
						<p>
							<b>21.10.1</b> The form work shall confirm to the shape lines and
							dimensions as shown on the drawings and so constructed as to
							remain sufficiently rigid during the placing and compacting of
							concrete, and shall be sufficiently tight to prevent loss of
							liquid from concrete. Only well designed and proper steel form
							work shall be used.
						</p>
						<p>
							<b>21.10.2</b> The form work shall be cleared off. All rubbish
							particularly chippings, shaving and saw dust shall be removed
							from the interior of the forms before the concrete is placed and
							the form work in contact with the concrete shall be cleaned and
							thoroughly wetted or treated with an approved composition.
						</p>
						<p>
							<b>4.21.11 STRIPPING OF FORM WORK :</b><br />
						<p>
							<b>22.11.1</b> In no circumstance form work should be struck off
							until the concrete reaches the strength of at least twice the
							stress to which the concrete may be subjected at the time of
							stripping.
						</p>
						<p>
							<b>22.11.2</b> In normal circumstances i.e. temperature above 200
							C form work may be struck after expiry of the following periods
							as per IS 456-1978.
						<ul class="text-left">
							<li class="padding-top-0"><b>(A)</b> Vertical sides of
								slabs, beams and columns 48 hours.</li>
							<li><b>(B)</b> Bottom of slabs under 4.5 M Span : 7 days</li>
							<li><b>(C)</b> Bottoms of slabs over 4.5M Span : 14 days</li>
							<li><b>(D)</b> Bottoms of beam under 6 M Span : 14 days and</li>
							<li class="padding-bottom-0"><b>(E)</b> Bottoms of beam over
								6 M Span : 21 days</li>
						</ul>
						</p>
						<p class="padding-top-0">The form work should be left longer,
							as it would assist the curing. The number of props, their sizes
							and position shall be such as to be able to safely carry the full
							dead load of the slab, beam or arch as the case may be together
							with any live load likely to occur during curing or further
							construction.</p>
						<p>
							<b>4.22 MATERIAL :</b>
						</p>
						<p>
							<b>4.22.1.1 STEEL :</b><br /> All metal for reinforcement shall
							be free from loose mill scale, loose rusts, oil and grease or
							other harmful matter. The steel used for reinforcement shall be
							cleaned immediately before placing the concrete.
						</p>
						<p>
							<b>4.22.1.2 PLACING :</b> All reinforcement shall be placed and
							maintained in position shown in the drawing. It is very difficult
							and costly to alter concrete once placed. It is, therefore very
							important to check the reinforcement and its placing before being
							covered.
						</p>
					</div>
				</div>
				<!---  30 Page Ends Here --->
				<!---  31 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>4.22.1.3 SIZE AND QUALITY OF STEEL BARS :</b><br /> The steel
							bars used for reinforcement shall be strictly as per relevant IS
							Specifications, and the contractor shall have to produce the test
							certificate of the Steel to be used.
						</p>
						<p>
							<b>4.22.2 AGGREGATES :</b><br /> All aggregates shall conform to
							all provisions and test methods of IS 383-1970
						</p>
						<p>
							<b>4.22.3 STORAGE OF MATERIALS :</b><br /> Cement shall be
							stored properly in a dry ventilated buildings.
						</p>
						<p>
							<b>4.23 DESIGN MIX :</b><br />
						<p>
							<b>4.23.1</b> The contractor shall submit mix designs for each
							strength the proposed slump proportional weight of cement
							saturated surface, dry aggregates and water. The mixes shall have
							to be designed as per relevant I.S. Specification.
						</p>
						<p>
							<b>4.23.2</b> The proportion of the concrete shall be such as to
							work readily into forms angles and ground the reinforcement
							without excessive manipulation, segregation of water gain.
						</p>
						<p>
							<b>4.23.3</b> The water content shall not be increased from the
							amount required by the design mix unless cement at required water
							cement ratio added. The Engineer-in-charge may require additional
							cement without extra compensation to the contractor if he
							considers that concrete does not produce the required strength.
						</p>
						<p>
							<b>4.24 TEST :</b><br /> <b>4.24.1</b> All tests as specified in
							the I.S. Specifications codes and required for the execution of
							the work shall be carried out by the contractor at his cost as
							per instruction of Engineer-in-charge.
						</p>
						<p>
							<b>4.24.2 FIELD TESTS :</b><br /> The contractor shall provide
							all arrangements for field test to exercise proper quality
							control over works.
						</p>
						<p>
							<b>4.24.3</b> All double flanged pipes shall be ISI mark (Class B
							conforming to IS 7181-1974) <span class="colour-report"><b>OR
									D.I. double flanged pipes class-K9 as per relevant IS
									specifications</b></span> and D.F. cast iron puddle collars, bell mouths
							and other specials required for inlet, outlet, over flow and
							scour shall be ISI mark conforming to IS 1538:1976 required for
							inlet, outlet, overflow and scour will be supplied and fixed in
							position by the contractor from desired inside level of the tank
							to duck foot bend one metre below G.L. and further providing and
							fixing all double flanged C.I. Pipes and specials from duck foot
							bend on ward up to minimum 5.0 M outside the supporting structure
							of the tank (i.e. RCC column) shall also be supplied and fixed by
							the contractor including testing of the fittings and joints with
							cost of the materials and joints. All the fittings shall be cast
							iron mechanical joint fittings as per I.S. Specifications.
						</p>
						<p>The arrangement for inlet, outlet, overflow and scour pipes
							shall be such that all these pipes and independent of each other
							and each of these shall have bell mouths at their ends. The top
							of bell mouths of inlet and over flow shall be at F.T.L. and top
							of bell mouths of outlet shall be 15 cm above floor of the tank,
							where as the bell mouth of scour pipe shall be flushed with floor
							level. All these vertical pipes shall terminate with flanged duck
							foot bends bottom fixed one meter below ground level. Further
							D.F. pipes should be provided up to minimum 5.0m away from the
							supporting structure of the tank.
						<table class="table table-bordered">
							<thead>
								<tr>
									<th class="colour-report">S</th>
									<th width="12%">ZONE</th>
									<th>Location of RCC Elevated Service Reservoirs</th>
									<th class="colour-report">Inlet pipe</th>
									<th class="colour-report">Outlet pipe</th>
									<th class="colour-report">Over flow</th>
									<th class="colour-report">Scour pipe</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td>I & II</td>
									<td>Aadakachhepeda ward</td>
									<td>150</td>
									<td>200</td>
									<td>150</td>
									<td>100</td>
								</tr>
								<tr>
									<td>2</td>
									<td>III & IV</td>
									<td>Dongri Para ward</td>
									<td>150</td>
									<td>200</td>
									<td>150</td>
									<td>100</td>
								</tr>
							</tbody>
						</table>
						</p>
						<p>
							<b>Note –</b> All the CI/DI DF pipes, valves & specials should
							be duly inspected by DGS&D/ SGS/ RITES.
						</p>
					</div>
				</div>
				<!---  31 Page Ends Here --->
				<!---  32 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>4.24.4</b> Scour pipe of OHT should be connected to the
							nearest natural drain/sewerage line available with pipes and
							other required specials provided by the department but including
							testing by the contractor.
						</p>
						<p>
							<b>4.25</b> The tank will have to be tested for the water
							tightness as per IS 3370 and it will be the responsibility of the
							contractor to make it water tight. Any defects, shrinkage or
							other faults which may appear within <span class="colour-report">twelve</span>
							months from the commissioning of the tank arising out of
							defective or improper materials or workmanship are upon the
							direction of the Executive Engineer to be amended and made good
							by the contractor(s) at his/their own cost unless the Executive
							Engineer shall decide that he/they ought to be paid for the same
							and in case of default the Governor of C.G. may recover from the
							contractor (s) the cost of making good the works. The
							arrangements of water for construction shall be done by the
							contractor at his own cost. For testing purposes, the contractor
							will have to give a test of water tightness of the reservoir to
							the entire satisfaction of the department. The responsibility of
							structural stability shall also rest solely with the contractor.
							The refund of earnest money and security deposit contemplated in
							the agreement clause 15 shall be refunded only after expiry of
							defect liability period after the satisfactory commissioning of
							tank.
						</p>
						<p>
							<b>4.26</b> No charges for the plastering if required for proper
							finishing of the surface of structures shall be paid under any
							circumstances.
						</p>
						<p>
							<b>4.27</b> The contractor shall be required to submit the
							detailed completion drawing in six copies of the work immediately
							on completion of the work.
						<p>
							<b>4.28</b> The work shall be treated as completed when the same
							is completely tested and handed over to the department including
							site clearance.
						</p>
						<p>
							<b>4.29</b> The tenderer should give at time of tendering the
							outline of architectural appearance, design and drawing.
						</p>
						<p>
							<b>4.30</b> The tenderer shall have to produce the complete
							design and working drawing of each component of structure before
							starting of work.
						</p>
						<p>
							<b>4.31</b> Special considerations should be given in the
							preparation of design for seismic forces.
						</p>
						<p>
							<b>4.32</b> The structural design of R.C.C. service reservoir
							shall be done on continuity theory and rotation of joints.
						</p>
						<p>
							<b>4.33</b> Due consideration for wind pressure should be taken
							in the design of structure, as per provision of relevant IS
							specification, the wind pressure may be take as per the wind
							pressure map of India in IS 875 – 1964 with special
							consideration to the previous experience of wind pressure
							experienced in the locality.
						</p>
						<p>
							<b>4.34</b> All duck foot bends shall be fixed and anchored
							properly in concrete blocks to prevent any displacement of pipe
							due to water thrust.
						</p>
						<p>
							<b>4.35</b> The contractor may note that the department may not
							be in a position to acquire or make available sufficient land
							(space) for collection, storage of construction materials and
							equipment’s and working. It shall therefore be contractor’s
							responsibility to make arrangement of land space for this
							purpose.
						</p>
						<p>
							<b>4.36</b> In case there is any delay in land acquisition by the
							department by the concerned competent authority the contractor
							shall only be allowed extension of time on this account. No
							compensation for financial claim shall be accepted.
						</p>
						<p>
							<b>4.37</b> If under unavoidable circumstances or for reasons
							beyond control of the department, the proposed site of
							construction of tank is required to be changed /shifted, the
							contractor shall have to take up construction at alternative
							site, and the contractor shall not have any claim on this
							account.
						</p>
						<p>
							<b>4.38</b> The R.C.C. elevated tank shall be designed, executed
							and tested in accordance to relevant I.S. Specification.
						</p>
					</div>
				</div>
				<!---  32 Page Ends Here --->
				<!---  33 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>4.39 Testing of concrete:</b> All concrete used in the bridge
							structure shall be mixed in power driven mechanical mixers and
							vibrated. The concrete laid under water should not be vibrated.
							The contractor's lumpsum quotation shall include the cost of
							testing of concret cubes. Installation of a Calibrated Testing
							Machine at site by the contractor will be acceptable. The testing
							will however, be done under the supervision of the Engineer-in-
							charge of his authorized representative. The contractor shall
							furnish a test certificate of the concrete test machine, to be
							used by him on the site of works sampling, strength test of
							concret and acceptance criteria shall be in accordance with
							clauses 302.6.4.2 & 302.6.4.3 of IRC: 21:1987
						</p>
						<p>
							<b>4.40 Finish of concrete surface:</b> Good surface of the
							exposed reinforced concrete members must be ensured by the
							contractor by using plane and true to shape form work. On
							striking off the forms, the slight local imperfections may be
							corrected by adequate touching and by rubbing the projections by
							corborandum stone. Plastering shall not be normally permitted on
							RCC surfaces. Corrections of defects must be done as desired by
							the Engineer-in-charge. Tolerance in form work shall be in
							accordance with clause 10.1 of IS: 456.
						</p>
						<p>
							<b>4.41 Rectification:</b> Any heavy obstruction to river course
							or any heavy excavation done by the contractor/firm shall be
							rectified and the river bed brought to its original level on
							completion of work and contractor/firm shall fill up any pits, he
							might have made to the entire satisfaction of the
							Engineer-in-Charge. The structure should be cleared off all the
							debris and dirt and the bridge and site handed over in a clean
							and fit state.
						</p>
						<p>
							<b>4.42 Approaches work and approach slab:</b> Lumpsum cost shall
							include the work of suitably designed filter media as per clause
							2503 / 309.3.2(b) of M.O.S.T. Specifications behind abutment and
							return in full length and height above natural ground level.
							Lumpsum cost shall also include the cost of construction of the
							approach road mentioned in the General Arrangement Drawing. The
							work for earth work & WBM/WMM shall also be carried out as per
							the specifications mentioned in annexure M. The earth work up to
							bottom of dirt wall shall be done before laying last span on
							abutment and adjoining support. RCC approach slab will be done by
							the contractor, the cost of which will be included in the lumpsum
							offer.
						</p>
						<p>
							<b>4.43 Size of Aggregate:</b> Size of aggregate to be used in
							plan concrete, RCC and prestressed concrete structure shall be in
							accordance with clause 1705 of M.O.S.T. specifications. However,
							for sections of structural components of 300 mm thickness and
							less only 20mm and down graded aggregate shall be used.
						</p>
						<p>
							<b>4.44 Applicability of conditions:</b> Conditions 2, 1 to 2.40,
							3.29 and 4.1 to 4.28 shall supersede anything contrary to them
							said in conditions 1.1 to 1.18.
						</p>
					</div>
				</div>
				<!---  33 Page Ends Here --->
				<!---  34 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>CHAPTER - V<br />DESIGN, CONSTRUCTION OF R.C.C. ELEVATED
								SERVICE RESERVOIRS<br />SPECIAL CONDITIONS
							</b>
						</h2>
						<p>
							<b>GENERAL INSTRUCTIONS :</b>
						</p>
						<p>
							<b>1.0</b> Elevated service reservoir should be designed to
							provide architectural appearance and all the related civil
							features should be as per appearance.
						</p>
						<p>
							<b>1.1</b> The RCC columns supporting to the tank should
							necessarily be round shape and not square or rectangular. The top
							i.e. water container may be circular/square or rectangular.
						</p>
						<p>
							<b>1.2</b> On the bottom floor of the water container cast iron
							or stainless steel grating should be provided over the supply
							outlet and scour outlet. This is essential to prevent any
							accident for the labour attending to periodical cleaning of the
							tanks. One such accident occurred at Sehore when two persons in
							succession were sucked in to the scour pipe, the top of which
							could not be seen because of calcium deposits due to regular use
							of bleaching powder. Cast Iron grating 20x20mm or stainless steel
							square 20x20mm can be used with square frames on top of the
							outlet.
						</p>
						<p>
							<b>1.3</b> The over flow pipe should not be connected to the
							distribution system. Connection of over flow pipe to the
							distribution system can result in over filling of the elevated
							service reservoir in case supply valves of the distribution
							system are not open. The over flow pipe should always be kept
							open for draining any excess storage in the tank.
						</p>
						<p>
							<b>1.4</b> It is extremely important to make arrangements for
							supply of sufficient water at the construction site for curing of
							the concrete. Continuous and efficient curing is extremely
							important for development of good compressing strength in any
							concrete structure.
						</p>
						<p>
							<b>1.5</b> It is advisable to use metal derived from igneous rock
							preferably of basaltic of granite origin. The coarse sand should
							be free from soil. This can be checked easily by half filling a
							transparent glass with the sand sample and the other half by
							clean water. Stir the sand vigorously. Silt in the sand can then
							be easily seen in the top water portion.
						</p>
						<p>
							<b>1.6</b> It is extremely essential the contractors undertaking
							the work should have a concrete mixer with them. No hand mixing
							shall be allowed.
						</p>
						<p>
							<b>1.7</b> In no case the concrete should be laid without
							vibration. It is desirable to keep two concrete vibrators at the
							construction site so that in case of a break down the other
							vibrator can be used. It is desirable that the divisions have
							with them at least two concrete vibrators, which is an essential
							T & P for laying concrete.
						</p>
						<p>
							<b>FOLLOWING SPECIFICATIONS SHOULD BE STRICTLY FOLLOWED :</b>
						</p>
						<p>
							<b>1.8</b> "Intz Type" OHT shall not be permitted.
						</p>
						<p>
							<b>2.0 CEMENT AND CONCRETE :</b>
						</p>
						<p>
							<b>2.1</b> Minimum Strength of Concrete :<br />
						<table class="text-left">
							<tbody>
								<tr>
									<td colspan="3">Minimum strength of concrete for
										components of elevated tank will be as below :-</td>
								</tr>
								<tr>
									<td width="50%">Columns staging</td>
									<td width="10%">-</td>
									<td>M25 (250 kg/sqcm)</td>
								</tr>
								<tr>
									<td>Tank including roof</td>
									<td>-</td>
									<td>M30 (300 kg/sqcm)</td>
								</tr>
							</tbody>
						</table>
						</p>
						<p class="padding-top-0">
							<b>2.2</b> Minimum Cement Content<br />
						<table class="text-left">
							<tbody>
								<tr>
									<td colspan="3">From durability considerations minimum
										cement content shall be as below :-</td>
								</tr>
								<tr>
									<td width="35%">Concrete</td>
									<td width="10%">-</td>
									<td>M25 - 350 kg/cum</td>
								</tr>
								<tr>
									<td>Concrete</td>
									<td>-</td>
									<td>M30 - 400 kg/cum</td>
								</tr>
							</tbody>
						</table>
						</p>
						<p class="padding-top-0">
							<b>2.3 Cover of Concrete :</b>The minimum cover shall be 40 mm
							for all the reinforcement. For foundations this cover shall be 60
							mm.
						</p>
					</div>
				</div>
				<!---  34 Page Ends Here --->
				<!---  35 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>2.4 Cement :-</b> The cement shall be ordinary port land
							cement/port land slag cement/concrete special cement conforming
							to ISS.
						</p>
						<p>
							<b>2.5 Water Cement Ratio :</b> Water Cement Ratio shall not be
							more than 0.45 this means 22.5 Litres of water per 50 kg. bag
							cement.
						</p>
						<p>
							<b>2.6 Use of Construction Chemicals :-</b> When the water cement
							ratio is less, the strength and durability of concrete is good.
							It is a advisable to use plasticisers in concrete and reduce
							water cement ration up to 0.4. Plasticisers manufactured by
							reputed companies are recommended.
						</p>
						<p>Proportion of plasticiser to be used shall be as per the
							instruction manual supplied by the manufacturers.</p>
						<p>
							<b>2.8 Construction Joints :</b> Construction joints be treated
							in accordance with IS 456:2000. The surface of already laid
							concrete be cleaned by water jet and cement slurry be applied.
							Cement mortar 10mm thick of the same proportion as in concrete be
							applied and then fresh concrete of the lift be laid. The form
							work must overlay 100 mm on the already laid concrete.
						</p>
						<p>
							<b>2.9 Minimum Dimensions and Shapes :</b>
						<table class="text-left">
							<tbody>
								<tr>
									<td colspan="2">Minimum Dimensions shall be as below :</td>
								</tr>
								<tr>
									<td>Circular columns</td>
									<td>400 mm</td>
								</tr>
								<tr>
									<td>Tank wall</td>
									<td>200mm</td>
								</tr>
								<tr>
									<td>Bottom slab</td>
									<td>150mm</td>
								</tr>
								<tr>
									<td>Top slab</td>
									<td>125 mm</td>
								</tr>
							</tbody>
						</table>
						<p>
							<b>Note -</b><br /> Rectangular/square columns are not allowed
							Circular shafts are also not allowed.<br /> Footing - The depth
							of footing on the face of the column shall not be less than 1/3rd
							of the spread of footing from the face.
						</p>
						<p>
							<b>3.0 STEEL :</b>
						</p>
						<p>
							<b>3.1 Minimum steel :</b> Design requirements as set out in
							relevant codes in respect of steel shall be fully satisfied.
							However, following minimum steel should be provided.
						</p>
						<table class="text-left">
							<tbody>
								<tr>
									<td width="8%">(a)</td>
									<td width="30%">Vertical steel in columns</td>
									<td>0.8% of cross sectional area actually required and
										0.3% where larger section than actually required is provided.
									</td>
								</tr>
								<tr>
									<td>(b)</td>
									<td>Horizontal link in columns</td>
									<td>Not less than 8 mm dia at 200mm c/c or 10 mm dia not
										more than 300 mm c/c.</td>
								</tr>
								<tr>
									<td>(c)</td>
									<td>Exposed RCC surface</td>
									<td>On both faces when thickness is 150 mm or more 2
										kg/sqm in perpendicular direction<br />The above requirement
										is satisfied if 8 mm bars @ 200mm c/c OR 10 mm bars @ 300mm
										c/c are provided.<br />Even if design steel is less than
										above, the above minimum shall be provided.
									</td>
								</tr>
								<tr>
									<td>(d)</td>
									<td>Steel in tank</td>
									<td>As per provision of IS 3370 <span
										class="colour-report">(latest edition)</span> subject to
										minimum as set out in (b) above.
									</td>
								</tr>
							</tbody>
						</table>
						<p>
							<b>3.2 Maximum spacing of reinforcement :</b>Maximum spacing of
							main reinforcement in slab or walls shall not more than 150 mm
							centre to centre. The spacing of secondary bars, such as
							distribution steel or vertical bars in columns.
						</p>
						<p>
							<b>3.3 Type of Steel :</b> The steel for reinforcement shall be
							thermo mechanically treated bars conforming to ISS.
						</p>
						<p>
							Detailing of Steel<br />Before commencing the work, Executive
							Engineer in-charge should study the drawing. It must be
						</p>
					</div>
				</div>
				<!---  35 Page Ends Here --->
				<!---  36 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>insisted that the designer provides details of the shape of
							each bar its diameter, length and numbers of each category in a
							schedule of reinforcement. This must be incorporated in every
							working drawing.</p>
						<p>
							<b>4.0 Protection work Drainage :</b> At the ground level, 100 mm
							thick cement concrete flooring in M10 should be provided for an
							area which is 1.5metre more than the dimensions of tank on all
							sides. This should be laid in a slope of 1:60 from the centre and
							drain be constructed around for outlet of water.
						<p>
							<b>5.0 Approval of Design & Drawing :</b> The contractor will
							submit the detail design & drawings of the R.C.C. elevated
							reservoirs within 30 days from the date of issue of written order
							to commence the work for approval of the competent authority of
							the Department in six copies.
						</p>
					</div>
				</div>
				<!---  36 Page Ends Here --->
				<!---  37 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>ANNEXURE- A - MODEL RULES RELATING TO LABOUR, WATER SUPPLY
								AND SANITATION IN LABOUR CAMP: TND0010160000000000PWDCG84401.doc</b><br />
							<br />ANNEXURE- "A"<br />MODEL RULES RELATING TO LABOUR, WATER
							SUPPLY AND SANITATION IN LABOUR CAMPS
						</h2>
						<p>
							<b>Note:</b> These model rules are intended primarily for labour
							camps which are not of a permanent nature. They lay down the
							minimum desirable standard which should be adhered to Standards
							in Permanent or semipermanent labour camps should not obviously
							be lower than those for temporary camps
						</p>
						<ul class="text-left">
							<li><b>1. Location:</b> The camp should be located in
								elevated and well drained ground in the locality.</li>
							<li><b>2. Labour:</b> Hut to be constructed for one family
								of persons each. The layout to be shown in the prescribed
								sketch.</li>
							<li><b>3. Hutline:</b> The huts to be built of local
								materials. Each hut should provide atleast 20 Sqm. of living
								space.</li>
							<li><b>4. Sanitary facilities:</b> There shall be provided
								latrines and urinals atleast 15 M away from the nearest quarter
								separately, for men and women specially so marked on the
								following scale.</li>
							<li><b>5. Latrines:</b> Pit provided at the rate of 10 users
								or two families per set. Separate Urinals as required as the
								privy can also be used for this purpose.</li>
							<li><b>6. Drinking water:</b> Adequate arrangement shall be
								made for the supply of drinking water. If practicable, filtered
								and chlorinated supply shall be arranged. Where supply is from
								intermittent sources, an overhead covered storage tank shall be
								provided with a capacity of five litres per person per day.
								Where the supply is to be made from a well it shall confirm to
								the sanitary stands. Laid down in the report of the Rural
								Sanitation Committee. The well should be at least 30 metres away
								from any latrine or other sources of pollution. If possible a
								hand pump should be installed for drawing the water from well.
								The well should be effectively disinfected once every month and
								quality of water should be got tested at Public Health
								institution between each work of disinfection.<br /> Washing
								and bathing should be strictly prohibited at places where water
								supply is from a river. The daily supply must be disinfected. In
								the storage reservoir and given at least 3 minutes contact with
								the disinfectant before it is drawn for use.</li>
							<li><b>7. Bathing and Washing:</b> Separate bathing and
								washing place shall be provided for men and women for every 25
								persons in the camp. There shall be a gap and space of 2 Sq.M.
								for washing and bathing. Proper drainage for waste water should
								be provided.</li>
							<li><b>8. Waste disposal:</b> Dustbins shall be provided at
								suitably place in camp and the residents shall be directed to
								throw all rubbish into these dustbins. The dustbins shall be
								provided with covers. The contents shall be removed every day
								and disposed off by trenching.</li>
							<li><b>9. Medical facilities.</b>
								<ul class="text-left">
									<li><b>a)</b> Every camp where 1000 or more persons reside
										shall be provided with whole time, doctor and dispensary. If
										there are women in the camp a whole time nurse shall be
										employed.</li>
									<li><b>b)</b> Every camp where less than 1000 but more
										than 250 persons reside shall be provided with dispensary and
										a part time nurse/midwife shall also be employed.</li>
									<li><b>c)</b> If there are less than 250 persons in any
										camp a first aid kit shall be maintained in- charge of the
										whole time persons.</li>
								</ul></li>
						</ul>
						<p>All the medical facilities mentioned above shall be for all
							residents in the camp, including a dependent of the workers, if
							any, free of cost.</p>
					</div>
				</div>
				<!---  37 Page Ends Here --->
				<!---  38 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">

						<p>
							<b>Sanitary Staff:</b> For each labour camp there should be
							qualified sanitary Inspector & Sweepers should be provided in the
							following scale:
						</p>
						<table class="table table-bordered text-left">
							<tbody>
								<tr>
									<td>1.</td>
									<td>For Camps with strength over 200 but not exceeding 500
										persons.</td>
									<td>One Sweeper for every 75 persons above the first 200
										for which three sweepers should be provided</td>
								</tr>
								<tr>
									<td>2.</td>
									<td>For camps with strength over 500 persons</td>
									<td>One sweeper for every 100 persons above the first 500
										for which six Sweepers should be provided.</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<!---  38 Page Ends Here --->
				<!---  39 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>ANNEXURE - "B"<br /> <br />CONTRACTOR'S LABOUR
								REGULATIONS.
							</b>
						</h2>
						<p>The Contractor shall pay not less than fair wage to
							Labourers engaged by him in the work.</p>
						<p>
							<b>Explanation:</b>
						</p>
						<ul class="text-left">
							<li><b>a)</b> "Fair Wages" means wages whether for time or
								piece work as notified at the time of inviting tenders for the
								works and where such wages have not been so notified the wages
								prescribed by the .................... Department for the
								division in which the work is done.</li>
							<li><b>b)</b> The Contractor shall, notwithstanding the
								provisions of any contract to the contrary, cause to be paid a
								fair wage to labourers indirectly engaged on the work including
								any labour engaged by his sub-contractors in connection with the
								said work as if labourers had been immediately employed by him.</li>
							<li><b>c)</b> In respect of all labour directly or
								indirectly employed on the works on the performance of his
								contract, the contractor shall comply with their cause to be
								complied with the labour act in force.</li>
							<li><b>d)</b> The Executive Engineer/Sub Divisional Officer
								shall have the right to reduce from the money due to the
								contractor any sum required or estimated to be required for
								making good the loss suffered by a worker or workers by reason
								of non-fulfilment of the conditions of the contract for the
								benefit of the workers, non-payment of wages or the deductions
								made from his or their wages, which are not justified by the
								terms of the contract or non-observance of regulations.</li>
							<li><b>e)</b> The contractor shall be primarily liable for
								all payments to be made under and for the observance of the
								regulations aforesaid without prejudice to his right to claim
								indemnity from his sub-contractors.</li>
							<li><b>f)</b> The regulations aforesaid shall be deemed to
								be a part of this contract and any breach thereof shall be
								deemed to be breach of this contract.</li>
							<li><b>g)</b> The contractor shall obtain a valid licence
								under the contract (Regulations and Abolition) Act enforce and
								rules made there under by the competent authority from time to
								time before commencement of work and continue to have a valid
								license until the completion of the work.<br /> Any failure to
								fulfil this requirement shall attract the penal provisions of
								this contract arising out of the resulted non-execution of the
								work assigned to the Contractor.</li>
						</ul>
					</div>
				</div>
				<!---  39 Page Ends Here --->
				<!---  40 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>ANNEXURE D- FORM OF CERTIFICATE OF INCOME TAX<br /> <br />FORM
								OF CERTIFICATE OF INCOME TAX TO BE SUBMITTED BY CONTRACTOR
								TENDERING FOR WORKS CASTING RS. 2.00 LAKHS OR MORE.
							</b>
						</h2>
						<ul class="text-left">
							<li><b>1.</b> Name and Title (of the company/firm HUF) or
								individual) in which the applicant is assessed to Income Tax and
								Address for the purpose of assessment.</li>
							<li><b>2.</b> The Income tax Circle /Ward /District in which
								the applicant is assessed to income tax.</li>
							<li><b>3.</b> Following particulars concerning the last
								Income tax assessment made.
								<ul class="text-left">
									<li><b>a)</b> Reference No. (or GIR No.) of the assessment</li>
									<li><b>b)</b> Assessment year and accounting year.</li>
									<li><b>c)</b> Amount of total income assessed.</li>
									<li><b>d)</b> Amount of tax assessed IT, SI, EPT, BPT,</li>
									<li><b>e)</b> Amount of tax paid IT, ST, EPT, B.P.T.</li>
									<li><b>f)</b> Balance being tax not yet paid and reasons
										for such arrears.</li>
									<li><b>g)</b> Whether any attachment or certificate
										proceedings pending in respect of the arrears.</li>
									<li><b>h)</b> Whether the company or firm or HUF on which
										the assessment was made has been or is being liquidised wound
										up, dissolved, partitioned or being declared insolvent, as the
										case may be.</li>
									<li><b>i)</b> The position about latter assessment namely
										whether returns submitted under Section 22(1) or (2) of the
										Income Tax Act, and whether tax paid under, "Section 18A of
										the Act and the amount of tax so paid or in arrears.</li>
								</ul></li>
							<li><b>4.</b> In case there has been no Income tax
								assessment at all in the past, whether returns submitted under
								section 21(1) or (2) and 18-A(3) and if so, the amount of Income
								Tax returned or tax paid and the Income Tax Circle/
								Ward/District concerned.</li>
							<li><b>5.</b> The Name and address of branch (es) verified
								the Particulars set out above and found correct subject to The
								following remarks.</li>
						</ul>
						<br /> <br />
						</p>
						<span class="pull-left">Dated: ....................</span> <span
							class="pull-right">Signature of I.T.I.<br /> <br />Circle
							/ Ward / District
						</span>
						</p>
					</div>
				</div>
				<!---  40 Page Ends Here --->
				<!---  41 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>Annexure G-I:<br />(Revised from Bank Guarantee Bond)<br />
								<br />(GUARATEE BOND)<br />(In lieu of performance Security
								Deposit)<br />(To be used by approved Scheduled bank))
							</b>
						</h2>
						<p>
							<b>1.</b> In consideration of the Governor of Chhattisgarh (here
							in after called the government having agreed to exempt
							............................................. (Herein after
							called the contractor (s) from the demand under the terms and
							conditions of an agreement dated ......................... made
							between .............................. for the work (Name of
							wrok)
							.............................................................
							(here in after called the said Agreement) of security deposit for
							the due fulfillment by the said contractor (s) of the terms and
							conditions contained in the said agreements on production of a
							bank Guarantee for Rs. ................. Rupees
							............................... Only we.
							(.)...........................................................
							(hereinafter referred to as " the bank (at the request of the
							said contractor (s) do here by undertake to pay the Govt., an
							amount not exceeding Rs. .............. against any loss or
							damage caused to or would be caused to or suffered by the
							Government, by reasons of any breach by the said contractor (s)
							of the terms or conditions contained in the said agreement.
						</p>
						<p>
							<b>2.</b> We (.) ............... do here by undertake to pay the
							amount due and payable under this guarantee without any demur
							merely on demand from the Government stating the amount claimed
							is due by way of loss or damage caused to or would be caused to
							or suffered by the Government by reason of breach by the said
							contractor (s) of any of the terms or conditions contained in the
							said agreements or by reasons of the contractor (s) failure to
							perform the said agreement, Any such demand made on the bank
							shall be conclusive as regards the amount due and payable by the
							bank under this Guarantee, However our liability under this
							Guarantee.shall be restricted to an amount not exceeding ........
						</p>
						<p>
							<b>3.</b> We undertake to pay to the Government any money so
							demanded not with standing any dispute or disputes raised by the
							contractor (s) in any suit or proceedings pending before any
							court or tribunal relating thereto, our liability under this
							present being absolute and unequivocal.
						</p>
						<p>The payment so made by us under this bond shall be a valid
							discharge of our liability for payment there under and the
							contractor (s) shall have no claim against us for making such
							payments.</p>
						<p>
							<b>4.</b> We (.) .......................... further agree that
							the guarantee herein contained shall remain in full force and
							effect during the period that would be taken for the performance
							of said agreement and that it shall continue to be enforce able
							till all the dues o the Government under or by virtue of the said
							agreement have been fully paid and its claims satisfied or
							discharged or till the Executive Engineer P.W.D. certified that
							the terms and conditions of the said agreement have been fully
							and property carried out by the said contractor (s) and terms and
							conditions of the said agreement have been fully and property
							carried out by the said contractor (s) and accordingly discharged
							this guarantee, unless a demand to claim under this Guarantee is
							made on us in writing on or before the (here indicate a date
							which falls 9 months beyond the due date of completion of the
							work)
							..........................................................................................
							we shall be discharged from all liability under the guarantee.
						</p>
						<p>
							<b>5.</b> We (.)
							.............................................................................................................
							further agree with the government that the Govt, shall have the
							fullest liberty without our consent and with out affecting in any
							manner our obligation here under to vary any of the terms and
							conditions of the said agreement or to extend time of performance
							by the said contractor (s) from time to time or to postpone for
							any time or for time to time any of the powers exercisable by the
							Govt. against the said contractor (s) and to for bear or enforce
							any of the terms and conditions relating to the said agreement
							and we shall not be relieved from our liability by reasons of any
							such variations. or extension being granted to the said
							contractor (s) or for barnacle, act or commission on the part of
							the Govt. or any indulgence by the Govt. to the said contractor
							(s) or by any such matter or thing what so ever which under the
							lay relating to sureties would but for this provision have effect
							of so relieving us.
						</p>
					</div>
				</div>
				<!---  41 Page Ends Here --->
				<!---  42 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>6.</b> This guarantee will not be discharged due to the change
							in the constitution of the Bank or the Contractor (s).
						</p>
						<p>
							<b>7.</b> We (.)
							...............................................................................................
							lastly under take not to revoke this guarantee during its
							currency except with the previous consent of the Government in
							writing :-
						</p>
						<p class="padding-left-20">
							Dated the ...................................... day of
							...................................<br />for ( )
							..........................................................................................<br />(>)
							indicate the Name of the Bank<br />
							....................................................................................................
						</p>
					</div>
				</div>
				<!---  42 Page Ends Here --->
				<!---  43 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>Annexure G-II</b>
						</h2>
						<p>
							<span class="pull-left">To,<br />...............................................<br />...............................................<br />...............................................
							</span>
						</p>
						<div class="clear"></div>
						<br />
						<p>Dear Sir,</p>
						<p class="padding-left-20">We enclose our Fixed Deposit
							Receipt/Cash Certificate other similar instrument No.
							.................... for Rs. .................... in favour of
							........................ Designation of the Officer concerned in
							lieu of deposits required from .................... for the due
							fulfillment by him/them of the terms of contractor dated
							.................... for during the period ....................
							commencing from .................... thereof if any.</p>
						<br /> <br />
						<p class="text-center">
							<span class="pull-right">Yours faithfully,<br /> <br />For
								and on behalf.
							</span>
						</p>
						<div class="clear"></div>
						<p>Please specify the nature of the instrument whom instrument
							similar to fixed deposit receipts are tendered and delete item
							not applicable.</p>
					</div>
				</div>
				<!---  43 Page Ends Here --->
				<!---  44 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>ANNEXURE H</b>
						</h2>
						<del>
							<p>
								<b>FOR ROAD AND BRIDGE/CULVERTS:</b> Notwithstanding provisions
								of clause 901.1 of the specifications, if would be optional for
								the contractor to set up his own field laboratory near the site
								of work, if the amount of contract inclusive of tender premium
								does not exceed Rs. 5.00 lakhs.
							</p>
						</del>
						<p>
							<b>FOR BUILDING/OHT/SUMP WORKS:</b>
						</p>
						<ul class="text-left">
							<li><b>i)</b> Rates include the element of testing of
								samples of various materials brought by the contractor for use
								in the work as per list of mandatory tests attached herewith.
								Frequency of such tests to be carried out shall not be less than
								the prescribed frequency. Contractor shall also have to
								establish a field laboratory at site to be approved by the
								Engineer-in-charge. The tests shall have to be conducted by the
								contractor’s material under the supervision of
								Engineer-in-charge or his authorized representative. A record of
								such tests shall be maintained in a duplicate register at site
								of work Duplicate copies of such tests shall be submitted to
								office along with running account bills. The original register
								shall also be submitted along with the final bill. Failure to
								conduct any of the test or not up to the prescribed frequencies
								would invite following consequences.<br /> The
								Engineer-in-charge may reject the work, but if in his opinion
								the work can be accepted despite the aforesaid shortcomings,
								then he may do so subject to a recovery of Rs. 100/- for each
								default and simultaneously inform the Superintending Engineer.
								However, it would be optional for the contractor to set up his
								own laboratory if the amount of contract (inclusive of tender
								premium) does not exceed Rs. 5.00 Lakhs.</li>
							<li><b>ii)</b> As regards steel reinforcement;
								<ul class="text-left">
									<li><b>a)</b> Mild steel and medium tensile steel bars
										shall confirm to IS: 432 (Part-1)
									<li><b>b)</b> Hot rolled deformed bars shall confirm to
										IS: 1139.
									<li><b>c)</b> Cold Twisted bars shall confirm to IS: 1786.












































































































									
									<li><b>d)</b> Hard drawn steel wire fabric shall confirm
										to IS: 1566 and
									<li><b>e)</b> Rolled steel made from structural steel
										shall confirm to IS: 226. All reinforcement shall be free from
										loose mill scales, loose rust and coats of paints, oil, mud or
										other costing which may destroy or reduce bond.<br /> Only
										such steel as is obtained from main producers of steel e.g..
										SAIL, TISCO, TISCO or such steel rolling mills as are having
										license from the B.I.S. to manufacture steel for
										reinforcement, shall be allowed to be used in the work.<br />
										The contractor shall have to produce Test Certificate in the
										proforma prescribed/approved by B.I.S. from the manufacturer
										for every batch of steel brought to site of work.<br />
										Before commencement of use of steel, from any batch, brought
										to site of work by the contractor, the Engineer-in-charge
										shall arrange to get samples tested for nominal mass, tensile
										strength, bend test and rebend test from any Laboratory of his
										choice at the cost of contractor. The selection of test
										specimens and frequency shall be as per relevant. I.S.
										specification of steel to be used.</li>
								</ul></li>
							<li><b>iii)</b> Where, contract provides for cement to be
								arranged by the contractor himself, only I.S.I. marked cement of
								relevant I.S. standard specifications shall be allowed to be
								used in the work subject to the following tests. The arrangement
								for necessary equipment and testing shall have to be made by the
								contractor, himself at a site to be decided by the
								Engineer-in-charge. All expenses shall be borne by the
								contractor. Any lot of cement brought to site by the contractor
								would be permitted to be used in the work. Under the supervision
								of the Engineer-in-charge or his authorities. Representative as
								hereinafter. The record of the tests results shall be maintained
								in the register referred in subsequent para.</li>
						</ul>
					</div>
				</div>
				<!---  44 Page Ends Here --->
				<!---  45 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<table class="table table-bordered text-left">
							<thead>
								<tr>
									<th>Type of Test</th>
									<th>Frequency</th>
									<th>Minimum</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><b>a)</b> Test for initial & final /setting time as
										per IS: 3536 – 1966</td>
									<td>1st Test for 10 tonne or part thereof.</td>
									<td>10 tonne</td>
								</tr>
								<tr>
									<td><b>b)</b>Test for determination of compressive
										strength of cement as per IS: 3536-1966</td>
									<td>1st test for 50 tonne or part thereof.</td>
									<td>50 tonnes</td>
								</tr>
							</tbody>
						</table>
						<p>A Duplicate register as per format hereunder shall be
							maintained at site of work. Extract certified copies of the
							entries for each month shall be submitted to the
							Engineer-in-charge by the contractor. The original register shall
							also be submitted to the Engineer-in-charge on completion of the
							work by the contractor.</p>
						<br />
						<table class="table table-bordered text-left"
							style="font-size: 0.92em; margin-left: -14px;">
							<thead>
								<tr>
									<th>S.No</th>
									<th>Place of receipt of cement</th>
									<th>No. of bags</th>
									<th>Name and address of firm from whom purchased</th>
									<th>Signature of contractor or his authorised
										representative</th>
									<th>Signature of authorised representative of Engineer-
										in-charge.</th>
									<th>Results of test for initial and final setting time</th>
									<th>Result of tests for compressive strength of cement.</th>
									<th>Remark</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th class="text-center">1</th>
									<th class="text-center">2</th>
									<th class="text-center">3</th>
									<th class="text-center">4</th>
									<th class="text-center">5</th>
									<th class="text-center">6</th>
									<th class="text-center">7</th>
									<th class="text-center">8</th>
									<th class="text-center">9</th>
								</tr>
								<tr>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
								</tr>
							</tbody>
						</table>
						<br />
						<p>When the strength of concrete required is up to M-20, then
							O.P.C. conforming to I.S.: 269-1989 or P.P.C. conforming to IS :
							1498-1976 May be used.</p>
						<p>When the strength of concrete required is more than M-20
							but up M-30, the O.P.C. Conforming to IS: 8112-1989 shall be
							used.</p>
						<p>For prestressed concrete works and when the strength of
							concrete required is more than M-30, the OPC conforming to IS:
							12269-1989 shall be used.</p>
						<p>Nominal mix would be adopted for Cement concrete M-7.5 M-10
							M-15 and M-20 Design mix shall have to be adopted for concrete of
							higher strengths.</p>
						<ol type="i">
							<li><b>i)</b> If any item of work is found to be substandard
								by the Engineer-in-charge is to the opinion that the same is
								structurally adequate and can be accepted at a reduced rate,
								then in such cases, the Engineer-in-charge shall have to submit
								proposals for appropriate reduction of rates supported by an
								analysis, in justification thereof, though a D.O. Letter to the
								Superintending Engineer concerned to obtain his approval
								expeditiously (ordinarily with in 15 days) The approved analysis
								along with orders of the Superintending Engineer shall have to
								be appended to the bills of the contractor.</li>
							<li><b>ii)</b> The Contractor shall have to be provided a
								ruled duplicate register at site named "Site Order Book" it
								shall be in the custody of departmental supervisory staff. The
								Engineer-in-charge or his authorized representative may record
								their instruction in this book, which shall be noted by the
								contractor or his authorized representative for compliance.</li>
						</ol>
					</div>
				</div>
				<!---  45 Page Ends Here --->
				<!---  46 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>List of mandatory Tests.</b>
						</p>
						<br />
						<table class="table table-bordered text-left">
							<thead>
								<tr>
									<th>Material</th>
									<th>Test</th>
									<th>Relevant IS code of testing</th>
									<th>Field Laboratory Test</th>
									<th>Minimum Quantity of material work for carrying out
										test.</th>
									<th>Frequency of testing</th>
								</tr>
								<tr>
									<th class="text-center">1</th>
									<th class="text-center">2</th>
									<th class="text-center">3</th>
									<th class="text-center">4</th>
									<th class="text-center">5</th>
									<th class="text-center">6</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Cement concrete or reinforced cement concrete not
										leaner than M-15</td>
									<td>Slump Test</td>
									<td>IS: 1199</td>
									<td>Field</td>
									<td>15 Cum more</td>
									<td>15 Cum or part there frequently by Engineer Incharge</td>
								</tr>
								<tr>
									<td>Reinforced cement concrete</td>
									<td>a)Cube strength</td>
									<td>For Building IS; 456 for Bridges/Culverts IRC: 21-1987</td>
									<td>Field</td>
									<td>15 Cum in slab 5 cum on columns.</td>
									<td>15 Cum</td>
								</tr>
								<tr>
									<td rowspan="2">Steel (if arranged by the contractor)</td>
									<td>a)Tensile strength</td>
									<td>IS: 1608</td>
									<td>Laboratory</td>
									<td>20 tonnes</td>
									<td>Every 20 tonne thereof conform IS: 1786-1985</td>
								</tr>
								<tr>
									<td>b) Bend test</td>
									<td>IS: 1599</td>
									<td>Laboratory</td>
									<td>-do-</td>
									<td>--do--</td>
								</tr>
								<tr>
									<td rowspan="2">Cement (If arranged by the conctractor)</td>
									<td>a) Test for initial & Finalsetting.</td>
									<td>IS: 403</td>
									<td>Field</td>
									<td>10 tonnes</td>
									<td>IS: 4031-1988</td>
								</tr>
								<tr>
									<td>b) Test for determination of compressive strength of
										cement.</td>
									<td>IS: 4031 Part I</td>
									<td>Field</td>
									<td>50 tonnes</td>
									<td>-do-</td>
								</tr>
								<tr>
									<td>Sand</td>
									<td>a) Silt contant.</td>
									<td>IS:2386 Part II</td>
									<td>Field</td>
									<td></td>
									<td>Every 20 cum or part or more frequently as by the
										Engineer-in-charge.</td>
								</tr>
							</tbody>
						</table>

					</div>
				</div>
				<!---  46 Page Ends Here --->
				<!---  47 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<table class="table table-bordered text-left">
							<tbody>
								<tr>
									<td rowspan="2" width="20%"></td>
									<td width="18%">b) Particle size distribution</td>
									<td width="18%">IS: 2386 Part III</td>
									<td width="14%">Field</td>
									<td width="14%"></td>
									<td>Every 20 Cum or part or more frequently as by the
										Engineer-in-charge.</td>
								</tr>
								<tr>
									<td>c) Bulking of sand</td>
									<td>IS: 2386 Part II</td>
									<td>Field</td>
									<td></td>
									<td>-do-</td>
								</tr>
								<tr>
									<td>Stone Aggregate</td>
									<td>a) Percentage of soft or deterious material.</td>
									<td></td>
									<td>Central visual inspection, laboratory test where
										required by the Engr-in-charge or so specified.</td>
									<td>0.00 Cum</td>
									<td>As required Engineer-in-charge.</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<!---  47 Page Ends Here --->
				<!---  48 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>ANNEXURE - J</b>
						</h2>
						<p>
							<b>List showing the name of near relative working in <span
								class="colour-report">UADD</span>. as required vide Clause 2.32
								of Chapter - II
							</b>
						</p>
						<table class="table table-bordered text-left">
							<thead>
								<tr>
									<th>S.No.</th>
									<th>Name of Divisional Accountant and Gazeted Officers
										working in C.G. UAD.</th>
									<th>Relational with self</th>
									<th>Name of Person working with the Contractor who are
										near relative to Gazetted officer mentioned in column(2)</th>
									<th>Relationship</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;
									</td>
								</tr>
							</tbody>
						</table>
						<br /> <br /> <br /> <br />
						<p>
							<span class="pull-left"><b>Date:</b></span> <span
								class="pull-right"><b>Signature of Contractor</b></span>
						</p>
						<div class="clear"></div>
						<br /> <br />
						<h2>
							<b><mark>ANNEXURE - K</mark></b>
						</h2>
						<p>
							<b>List of contracts already held by the Contractor in <span
								class="colour-report">UADD</span> and other Departments at the
								time of Submission of this tender as required vide Clause 2.24
								of the N.I.T.
							</b>
						</p>
						<table class="table table-bordered text-left">
							<thead>
								<tr>
									<th>S.No.</th>
									<th>Name of Work</th>
									<th>Amount of contract Excluding higher / lower percentage
										if any</th>
									<th>Value of work done near excluding percentage</th>
									<th>Value of balance work excluding percentage</th>
									<th>Amount of Solvency at the time of registration</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
									<td>&nbsp;<br />&nbsp;<br />&nbsp;
									</td>
								</tr>
							</tbody>
						</table>
						<br /> <br /> <br /> <br />
						<p>
							<span class="pull-left"><b>Date:</b></span> <span
								class="pull-right"><b>Signature of Contractor</b></span>
						</p>
						<div class="clear"></div>
						<div class="padding-top-220"></div>
						<hr>
						<p>who are near relative to Gazetted officer mentioned in
							column(2)</p>
					</div>
				</div>
				<!---  48 Page Ends Here --->
				<!---  49 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<del>
							<h2>
								<b>ANNEXURE - "O"<br /> <br />ADDITIONAL SPECIAL
									CONDITIONS
								</b>
							</h2>
							<p>
								<b>1. Foundation levels and confirmatory boring:</b> In
								accordance with IS / IRC Code keeping in view the stipulation of
								clause 4.5 & 4.18 of the N.I.T contractors are required to carry
								out confirmatory boring on each pier and abutment locations.
							</p>
							<p>
								<b>2. General arrangement Drawing, approval of design:</b> The
								tender drawing containing general arrangement of structure must
								give one type of structural component out of the acceptable
								types as stipulated in the N.I.T or in these special conditions.
								No option is acceptable. However, change in design at later data
								will examined in accordance with clause 4.16 of N.I.T. Programme
								of submission & approval of detailed design shall be mutually on
								award of the contract.
							</p>
							<p>
								<b>3. Details in General Arrangement Drawing:</b> Type of pier,
								abutment and returns and their foundation levels should be
								clearly shown in the general arrangement drawing of the
								contractor.
							</p>
							<p>
								<b>4. Conditions of Exposure:</b> The condition of exposure
								shall be treated as moderate for this bridge.
							</p>
							<p>
								<b>5. Consultant:</b> A contractor who offers alternative
								designs should declare the name and address of the consultant.
								If the said consultant has not done any work of bridge in
								M.P.P.W.D or Setu Nirmaln Nigam, his qualification and
								experience in design work must be stated.
							</p>
							<p>
								<b>6.</b> R.C.C bearing shall not be allowed.
							</p>
							<p>
								<b>7. Design of box for temperature difference:</b> The
								additional stresses generated by the temperature difference may
								be calculated in accordance with the method contains in a paper
								entitled "Temperature stresses in concrete bridge decks simple
								design method by Dr. V. K. Raina published in Bridge and
								Structural Engineer. If such additional temperature stresses are
								taken into account, the permissible increase of stresses will be
								15% in accordance, with Clause 203 of IRC - 6 of 1986.
							</p>
							<p>
								<b>8. Land for construction Camp:</b> Land for construction camp
								shall be arranged by the contractor.
							</p>
							<p>
								<b>9. Security deposit Clause 1.1 and 2.8 of N. I. T.:</b> Fifty
								percent of the security deposit will be refunded on completion,
								testing and handing over of the bridge, to the department.
								Remaining fifty percent will be refunded six months after
								completion or after one monsoons, whichever is later.
							</p>
							<p>
								<b>10. Supply of detailed drawing:</b> The detailed drawing of
								various component of bridge shall be supplied to the contractor
								in parts as per the progress of the work. In case the
								contractor’s lumpsum offer based on departmental General
								Arrangement Drawing, he will have to submit detailed design &
								drawing of various components for approval as per clause 4.3
							</p>
							<p>
								<b>11. Revision in design:</b> Due to basic data being changed.
								If, on award of work, it is considered necessary to increase the
								length of bridge or vary the foundation and / or formation level
								due to change in the basic hydraulic and sub soil data, the
								contractor shall, submit revised design to suit the change as
								ordered without any extra cost on account of the additional
								design work.
							</p>
							<p class="padding-top-0">But in case there are major changes
								in the data and the contractor is required to redesign the
								bridge, the C.E. may at his discretion allow extra payment for
								design may be commensurate with the extra work involved in the
								design.</p>
							<p>
								<b>(12)</b> If the tenderer, whose tender has been accepted, and
								after signing the agreement, (i) does not start regular actual
								physical items of work within 25% (twenty five percent) of the
								time allowed for completion, or abnormally slowdown the work or
								(iii) abandons the work, or (iv) merely goes on applying for
								extension of time; the Executive Engineer shall serve a "show
								cause" notice with details to the contractor in this regard and
								if the contractor does not reply, or if his reply is considered
								not satisfactory (at the sole discretion of the Executive
								Engineer), his earnest money and the performance security money
								or the Bank Guarantee in this regard shall be forfeited in
								favour of the Govt.If the contractor has committed a similar
								default on earlier occasion (s) in previous three consecutive
								years the contractor shall be debarred from participating in any
								future tender of any P.W.D. Division in the State of
								Chhattisgarh for a period of 2 (two) years from the date of such
								order, by the authority which had registered him/her.
							</p>
						</del>
					</div>
				</div>
				<!---  49 Page Ends Here --->
				<!---  50 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<del>
							<p>Such orders & action shall be final binding and conclusive</p>
							<p>
								<b>(13). performance Guarantee:</b>
							</p>
							<ul class="text-left">
								<li><p>
										<b>(i)</b> The contractor shall also be responsible for
										performance of work carried out by him for a period of 36
										(Thirty six) month beyond the completion of work for which
										performance security has to be furnished by him @ 5%(five
										percent)of amount of contract. For this purpose contractor has
										to submit to the department a Bank Guarantee of 5% amount of
										the value of work done on every running and final bill payable
										to him. If contractor fails to submit bank guarantee of 5%
										amount of the gross bill, then 5% amount of bill shall be
										deducted from his running and final bill payment. However, the
										contractor can get refund of such performance cash security
										amount deducted if he submits appropriate bank guarantee valid
										for the period as stated above or 36 (thirty six) month after
										actual completion.
									</p>
									<p>If require, the Executive Engineer shall ask the
										contractor to extend the validity period of the Bank
										Guarantee(s) for such period which he considers it proper and
										the contractor shall extend the validity period of such Bank
										Guarantee accordingly. If the contractor fails to extend the
										period accordingly, the Executive Engineer shall encash the
										B.G. before the expiry of the validity period.</p></li>
								<li class="padding-top-0"><b>(ii)</b> The contractor shall
									have to carry out all necessary "Rectification" of defects
									noticed, caused due to any reasons at his own cost within such
									reasonable period mentioned in such communication notice from
									the Executive Engineer/Sub Divisional Officers to him.</li>
								<li><b>(iii)</b> Failure of the contractor to rectify the
									defects properly in the given period, it shall be open for the
									Executive Engineer/Sub Divisional Officer to get the defect(s)
									rectified either departmentally or through other agency
									(without calling any tender /quotation) and recover the actual
									cost plus 15 % (fifteen percent) of such cost from the
									contactor from any sum, in any form, and available with the
									department or can be recovered as "Arrears of Land Revenue"</li>
								<li><b>(iv)</b> After two years of completion of
									construction, 50% (fifty percent) of available performance Bank
									guarantee shall be returned to the contractor subject to the
									satisfaction of the Executive Engineer.</li>
								<li><p>
										<b>(v)</b> Remaining performance Bank Guarantee as would be
										remaining (after recovery all cost plus 15% (Fifteen percent)
										for rectification of defects, if done by the department or
										through other agency) shall be returned after 3 years of
										completion.
									</p>
									<p>The performance guarantee will be in addition to the
										normal security to be deducted as per clause 1 of agreement
										for the execution of contract.</p></li>
							</ul>
							<p>
								<b>(14)</b> The tenderer/contractor shall give in advance
								authority letter(s) in favour of the Executive Engineer,
								authorising him to get all Bank’s Fixed Deposit receipts, Bank
								Guarantees (either normal security deposit and or for
								performance security) to get these Bank Receipts and Guarantee
								deeds verified and got confirmed from the concerned Bank. It
								will be only after getting such confirmation that the Executive
								Engineer shall pay any amount accordingly or refund the equal
								amount for which BG submitted has been duly verified and
								confirmed.
							</p>
							<p>
								<b>(15)</b> The contractor shall not remove minor mineral from
								borrow areas, quarries without prior payment of Royalty charges.
							</p>
							<p>
								<b>16.</b>
							</p>
						</del>
					</div>
				</div>
				<!---  50 Page Ends Here --->
				<!---  51 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>Additional Special Condition:</u></b>
						</h2>
						<p>
							<b>a)</b> Cess @ 1% (One percent) shall be deducted at source,
							form every bill of the contractor by the Executive Engineer under
							"Building and Other construction for workers welfare, Cess Act
							1996".
						</p>
						<ul class="text-left">
							<li><b>a)</b> It is mandatory for the construction(s) to get
								himself/themselves registered with "C.G. Building and other
								construction Welfare Board" as soon as the work order is issued
								to him/them for the work amounting to Rs. 10.00 (Ten) lakhs and
								above and submit a copy of the same to the concern Executive
								Engineer, otherwise no payment will be made under the contract.</li>
							<li><b>b)</b> Contractors are advised to go through the
								Notice Inviting Tenders & the tender/P.Q./Bid Capacity document
								thoroughly. Certificates, annexure, enclosures as mentioned in
								the document will have to be submitted by the tenderers strictly
								in the prescribed format, at the time of submission of
								Technical/Financial bid, failing which the contractor shall
								disqualify for the work & his financial offer shall not be
								opened and no representation, appeal or objection what so ever
								in this regard shall be entertained by the department.</li>
							<li><b>c)</b> It is mandatory to submit online by the
								contractor the list of on-going works/works in hand. If any work
								is found delayed beyond one year from the stipulated date of
								completion, the contractor will be disqualified for the reason
								of poor performance.</li>
							<li><b>d)</b> Additional performance security (APS) shall be
								deposited by the successful bidder the time of signing of
								agreement when the bid amount is seriously unbalanced i.e. less
								than estimated cost by more than 10 % in such event the
								successful bidder will deposit the Additional performance
								security (APS) to the extent of difference of 90 % of the PAC
								and bid amount in the shape of <b>B.G.</b> in favour of the <span
								class="colour-report"><b>Commissioner/CMO</b></span> before
								signing the agreement. The same shall be refunded along with
								normal S.D. after completion of the work if the contractor fails
								to complete the work of left the work incomplete, this
								Additional performance security (APS) shall be forfeited by the
								department & the agreement shall be terminated and action shall
								be taken in accordance with clause 1.14 of the agreement. In,
								case the tenderer/contractor refuses to deposit Additional
								performance security (APS) than his bid will be rejected by the
								sanctioning authority and earnest money shall be forfeited.</li>
							<li><b>e)</b></li>
							<li><p>
									<b>f)</b> In the event of withdrawing his/her after before the
									expiry of the period of validity of offer or failing to execute
									the agreement as required to condition No. 2.4.3 and 2.34 of
									the notice inviting tender (N.I.T.) he/she will not be entitled
									to tender for this work in case of recall of tenders. In
									addition to forfeiture of his/her earnest money as per
									provisions of condition No. 2.4.3 and 2.34 of N.I.T. as may be
									applicable for the work, the registering authority will demote
									the contractor/firm for a period of one year. If the tenderer
									has committed a similar default on earlier occasion(s) as well,
									then such demotion in registration will be permanently.
								</p>
								<p>This special condition will supersede anything contrary
									to it in the tender document.</p></li>
						</ul>
					</div>
				</div>
				<!---  51 Page Ends Here --->
				<!---  52 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>ANNEXURE II<br /> <br />SAFETY CODE
							</b>
						</h2>
						<p>
							<b>Scaffolding:</b>
						</p>
						<ul class="text-left">
							<li><b>(i)</b> Suitable scaffold should be provided for
								workman for all works that cannot safely be done from the
								grounds or from solid construction except such short period work
								as can be done safely from ladder is used on extra Mazdoor shall
								be engaged for holding the ladder for carrying materials as well
								suitable foot holes and hand holds shall be provided on the
								ladder and the ladder shall be given an inclination not steeper
								than <sup>1</sup>/<sub>4</sub> to <sup>1</sup>/<sub>4</sub>
								Horizontal and 1 vertical).</li>
							<li><b>(ii)</b> Scaffolding or staging more than 12 M above,
								the ground floor swung or suspended from an overhead support or
								erected with stationer/support shall have a guard rail property
								attached, bolted, braced or otherwise secured at least 1 meter
								high above the floor platforms of such scaffolding or staging
								and extending along the entire length of the outside the ends
								thereof with only such opening as may be necessary for the
								delivery of the materials. Such scaffolding or staging shall be
								fastened as to prevent it from swaying from the building of
								structure.</li>
							<li><b>(iii)</b> Working platform gangways and stairway
								should be so constructed that they should not away unduly or
								unequally and if the height of the platform of the Gangway or
								the stairway is more than 3.54 metres above ground level and or
								floor level they should be closely bearded, should have adequate
								width and should be suitably fenced as described (ii) above.</li>
							<li><b>(iv)</b> Every opening in the floor of a building or
								in a working platform be provided with suitable means to prevent
								the falling of persons or materials by providing suitable
								fencing or railing whose minimum height shall be 1 metre.</li>
							<li><b>(v)</b> Safe means of access shall be provided to all
								working platforms and other working places. Every ladder shall
								be securely fixed. No portable ladder shall be over 9 metre in
								length while the width between side rails inring ladder shall be
								in no case be less than 0.3 metres from ladder upto and
								including 3 meter length. For longer ladders this width should
								be increased at least 2 cm. For each additional meter of length.
								Uniform step spacing shall not exceed 0.3 M adequate precaution
								shall be taken to prevent danger form electrical equipment. No
								material on any of the work site shall be so stacked or placed
								as to cause danger or inconvenience to any person or the public.
								The contractor shall also provide all necessary fencing and
								lights to protect the public from accident and shall be bound to
								bear the expenses of defence of every suit action or other
								precautions of law that may be brought by any person for injury
								sustained owing to neglect of the above and to pay any damages
								and costs which may be awarded in any such suit action or
								proceeding to any such person or which may with consent of the
								contractor be paid to compromise by any such person.</li>
						</ul>
						<p>
							<b>2. Excavation and Trenching:</b> All trenches 1.2 metre or
							more in depth, shall at all times be supplied with at least one
							ladder for each 30 Metre in length of friction thereof. Ladder
							shall be extended from bottom of the trench to atleast 1 metre
							above the surface of the ground. The side of trenches which are
							1.5 metre or more in depth shall be stepped back to give suitable
							slopes or securely held by timber bracing so as to avoid the
							danger of sides to collapse The excavated materials shall not be
							placed within 1.5 metre of the edge of the trench or half of the
							depth of the trench whichever is more. Cutting shall be done from
							top to bottom. Under no circumstances undermining or under
							cutting shall be done.
						</p>
						<p>
							<b>3. Demolition:</b> Before any demolition work is commenced and
							also during the process of the works.
						</p>
						<ul class="text-left">
							<li><b>(a)</b> All roads and open area adjacent to the work
								site shall either be closed or suitably protected.</li>
							<li><b>(b)</b> No electric cable or apparatus which is
								liable to be a source of danger over a cable or apparatus used
								by the operator shall remain electrically charged.</li>

						</ul>
					</div>
				</div>
				<!---  52 Page Ends Here --->
				<!---  53 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<ul class="text-left">
							<li><b>(c)</b> All precautionary steps shall be taken to
								prevent danger to persons employed from risk of fire or
								explosion of flooring. No floor roof or other part of the
								building shall be so overloaded with debris of materials as to
								render it unsafe.
							<li><b>
						</ul>
						<p>
							<b>4. Painting:</b> All necessary personal safety equipment as
							considered adequate by the Engineer-in-charge should be kept
							available for the use of person employed on the site and
							maintained in a condition suitable for immediate use and the
							contractor should take adequate steps to ensure proper use of
							equipment by those concerned.
						</p>
						<ul class="text-left">
							<li><b>a)</b> Workers employed on mixing asphaltic materials
								cement lime mortars shall be provided with protective footwear
								and. protective goggles.</li>
							<li><b>b)</b> Stone brackets shall be provided with
								protective goggles and protective clothing, and seated at
								sufficiently safe intervals.</li>
							<li><b>c)</b> Those engaged in welding works shall be
								provided with welder's protect.</li>
							<li><b>d)</b> When workers are employed in sewers and
								manholes which are in use, the Contractors shall ensure that the
								manhole covers are open and are ventilated atleast for an hour
								before the work shall be coronet off with suitable railing and
								provided with warning signals or boards to prevent accident to
								the public.</li>
							<li><b>e)</b> The Contractor shall not employ men below the
								age of 19 and women on the work of painting with products
								containing lead in any form whenever men above the age of 18 are
								employed on the work of lead painting the following precautions
								should be taken.
								<ul class="text-left">
									<li><b>iii)</b> No paint containing lead or lead shall be
										used except in the from of paste or ready made paint.</li>
									<li><b>iv)</b> Suitable face masks should be supplied for
										use by the workers when paint applied in the from of spray or
										a surface having lead paint dry rubble and scrapped.</li>
									<li><b>v)</b> Overhauled shall be supplied by the
										contractor to the workman and adequate facilities shall be
										provided to enable the working painters to wash during the
										cessations of work.</li>
								</ul></li>
						</ul>
						<p>
							<b>5. Drawing:</b> When the work is done near any place where
							there is risk of drawing all necessary equipment should be
							provided and kept ready for use and all necessary steps taken for
							prompt rescue of any person in danger and adequate provision
							should be made for prompt first aid treatment for all injuries
							likely to be sustained during the course of the work.
						</p>
						<ul class="text-left">
							<li><b>a)</b> Every crane driver or hosing applicants
								operator shall be properly qualified and no personal order an
								age of 21 years should in-charge of any hoisting machine
								including any scaffold which give signals to the operator.</li>
							<li><b>b)</b> In case of every hoisting machine and every
								chain ring lowering or as means of suspensions. The sate working
								load shall be ascertained by adequate means. Every hoisting
								machine and gear referred to above shall be plainly marked with
								the safe working load. In case of hoisting machine having a
								variable safe working load of the conditions under which it is
								applicable shall be clearly indicated. No part of any machine or
								of any gear referred to above in this paragraph shall be loaded
								beyond the safe working load except for load purpose of testing.</li>
							<li><b>c)</b> In case of departmental machine the safe
								working and load shall be notified by the Electrical
								Engineer-in-charge. As regarded contractor’s machine the
								contractor shall notify the safe working load of the machine to
								the Engineer-in-charge, whenever he brings any machinery to site
								of work and get verified by the Electrical Engineer concerned.</li>
							<li><b>d)</b> Motors, gearing transmission, Electric wiring
								and other dangerous part of the hoisting appliance should be
								provided with efficient safe guards and with such means as well
								reduce adequate precautions should be taken to reduce to the
								minimum the risk of any part of a</li>
						</ul>
					</div>
				</div>
				<!---  53 Page Ends Here --->
				<!---  54 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<ul class="text-left">
							<li>suspended load be coming accidentally dispraced When
								workers employed on Electrical installations which are already
								unregistered insulating mats wearing apparel such as gloves
								sleeves and boots as may be necessary should be provided the
								workers should not wear rings, watches and carry keys, or other
								materials which are good conductors of electricity.</li>
						</ul>
						<p>
							<b>7.</b> All scaffolds, ladders and their safety device
							mentioned or described herein shall be maintained in safe
							condition and no scaffold ladder or equipment shall be altered or
							removed while it is in use. Adequate washing facilities shall be
							provided at or near places of work.
						</p>
						<p>
							<b>8.</b> These safety provisions should be brought to the notice
							of all concerned by display on a Notice Board at prominent places
							at the work spot. The persons responsible for compliance of the
							safety code shall be named therein by the contractor.
						</p>
						<p>
							<b>9.</b> To ensure effective endorsement of the rules and
							regulations relating to safety precautions the arrangement made
							by the contractor shall be open to inspection by the Labour
							Officer, Engineer-in-charge, or the Department or their
							representatives.
						</p>
						<p>
							<b>10.</b> Notwithstanding the above clause (1) to (9) there is
							nothing in these to except the contractors to exclude the
							operations of any other act or rule in force in the Republic of
							India.
						</p>
						<p>
							<b>Tender Items</b>
						</p>
						<table class="table table-bordered text-left">
							<thead>
								<tr>
									<th>Sr No</th>
									<th>Group Id</th>
									<th>Item Ref No</th>
									<th>Item Description</th>
								</tr>
							</thead>
							</tbody>
							<tr>
								<td>1</td>
								<td>01</td>
								<td>Const of High level Bridge</td>
								<td>Construction of High Level Bridge across</td>
							</tr>
							</tbody>
						</table>
						<br />
						<p>
							<b>Tender Item Details</b>
						</p>
						<p>
							<b>Group Id:</b>
						</p>
						<br />
						<p>
							<b>Item Ref No:</b>
						</p>
						<p>
							<b>Description:</b>
						</p>
						<p>
							<b>Lenght of Bridge with Returns :</b>
						</p>
						<p>
							<b>Over all width:</b>
						</p>
						<br />
						<p>
							<b>Item Type:</b>
						</p>
						<br />
						<p>
							<b>Estimated Value:</b>
						</p>
						<br />
						<p>
							<b>Unit:</b>
						</p>
						<br />
						<p>
							<b>EMD/FDR Value:</b>
						</p>
						<br />
						<p>
							<b>Time Period:</b>
						</p>
						<p class="strike-line2"></p>
					</div>
				</div>
				<!---  54 Page Ends Here --->
				<!---  55 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>ANNEXURE-13<br /> <br />Affidavit
							</b>
						</h2>
						<p>I ............................................. S/o
							............................................. Aged
							............... years ...............</p>
						<p>resident
							...............................................................
							of
							....................................................................</p>
						<p>(address
							......................................................................................................................................)</p>
						<p>(For and on behalf of
							............................................................), do
							here by and herewith solemnly affirm / state on oath that : -</p>
						<p>
							<b>1.</b> All documents and Informations furnished are correct in
							all respects to the best of my knowledge and belief
						</p>
						<p>
							<b>2.</b> I have not suppressed or omitted any information as is
							required.
						</p>
						<p>
							<b>3. I am/ We are neither black listed nor debarred by
								Govt.of India / Other State Govt. Departments/ Chhattisgarh
								State Govt. Departments/Urban Local Body.</b>
						</p>
						<p>
							<b>4.</b> I hereby authorize Officials to get all the documents
							verified from appropriate source(s).
						</p>
						<br /> <br />
						<p>
							<span class="pull-right text-center">Deponent<br />(..................................)<br />Authorized
								signatory /<br /> for and on behalf of<br />.........................<br />(affix
								seal)
							</span>
						</p>
						<div class="clear"></div>
						<br /> <br />
						<h2>
							<b>Verification</b>
						</h2>
						<p>I .................................................. S/o
							.................................................. do here by
							affirm that the contents stated in Para 1 to 4 above are true to
							the best of my knowledge and believe and are based on my / our
							record.</p>
						<p>Verified that this .................... date of
							.................... 200..... at (Place) ....................</p>
						<br /> <br /> <br />
						<p>
							<span class="pull-left">Seal of attestation by a Public<br />Notary
								with date
							</span> <span class="pull-right text-center">Deponent<br />(..................................)<br />Authorized
								signatory /<br /> for and on behalf of<br />.........................<br />
								<b>(affix seal)</b></span>
						</p>
					</div>
				</div>
				<!---  55 Page Ends Here --->
			</div>

		</form:form>
	</div>
</div>