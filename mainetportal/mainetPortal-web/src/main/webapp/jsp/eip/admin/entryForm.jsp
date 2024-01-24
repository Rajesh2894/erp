<%@ taglib tagdir="/WEB-INF/tags" prefix="apptags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="clearfix" id="home_content">
	<div class="col-xs-12">

		<div class="row">

				<div class="form-div">
				
					<form action="javascript:void(0);" method="post"> 
					
						<div class="form-elements">
							
							<div class="element">	
								<label for="field_name">Field Name :</label>
								<span><input type="text" name="field_name" id="field_name"/></span>
							</div>
							<div class="element">		
								<label for="field_type">Field Type :</label>
								<span>
									<select id="field_type" name="field_type">
										<option>Select Field Type</option>
										<option value="TF">TextField</option>
										<option value="TA">Text Area</option>
										<option value="DB">DopDown Box</option>
									</select>
								</span>
							</div>
						
						</div>
						
						<div class="form-elements" align="center">
							
							<div class="element">	
								<input type="button" value="Save"/>
								<input type="reset" value="Reset"/>
							</div>
							
						</div>
					
					</form>
				
				</div>
				
		</div>
		
	</div>
	
</div>	