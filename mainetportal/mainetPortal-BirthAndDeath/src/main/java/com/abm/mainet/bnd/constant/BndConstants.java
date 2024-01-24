package com.abm.mainet.bnd.constant;

public interface BndConstants {
        String BND_RATE_MASTER = "BNDRateMaster";

//      String BND_RATE_MASTER_URL ="http://192.168.100.33:8090/mainetbpmservice/rest/bnd/getBNDCharge";       // local url
       // String BND_RATE_MASTER_URL ="http://103.209.145.36:8089/mainetbpmservice/rest/bnd/getBNDCharge";

        //String BND_RATE_MASTER_URL ="http://192.168.100.33:8090/mainetbpmservice/rest/bnd/getBNDCharge";
        String BND_RATE_MASTER_URL ="http://103.209.145.36:8089/mainetbpmservice/rest/bnd/getBNDCharge";
        String CHECKLISTMODEL = "ChecklistModel";
        String IBC = "IBC"; //Issuance of birth certificates
        String IDC = "IDC";  //Issuance of Death certificate
        String 	DRC="DRC";   //Death Registration correction
        String INC="INC" ;//inclusion of child 
        String BR="BRG";  //birth Registration
        String DR ="DR"; //death registration
        String STATUS="O";
        String BRI="BRI"; //
        String BRC="BRC";
        String RBC = "ABC";
        String RDC = "ADC";
        String BNDCHARGES="certificateCharge";
        String CAA = "CAA";
        String BIRTH_DEATH = "BND";
        String BIRT_REPORT_URL="http://192.168.100.33:8090/birt/frameset?__report";
        String CHARGES_AMOUNT="0.0";
        String CHARGES_AMOUNT_FLG="N";
        String 	ROLE_CODE="BND_APPROVER";
        String  DESIGN_PRIFIX="DLO";
        String REJECTED="REJECTED";
        String APPROVED="APPROVED";
        String PENDING="PENDING";
        String CLOSED="CLOSED";
        String OPEN="OPEN";
        String BIRTH_REG_URL_FOR_WORLFLOW_INIT="BirthRegistrationForm.html";
        String ISSUANCE_BIRTH_URL_FOR_WORLFLOW_INIT="IssuanceBirthCertificate.html";
        String ISSUANCE_DEATH_URL_FOR_WORLFLOW_INIT="IssuanceDeathCertificate.html";
        String DEATH_REG_URL_FOR_WORLFLOW_INIT="DeathRegistration.html";
        String BIRTH_STATUS_APPROVED="A";
        String DEATH_STATUS_APPROVED="Y";
        String CHARGES_APPLY_STATUS="CA";
        String STRART_DATE="01/01/";
        String TO_DATE="31/12/";
        String FLAT_RATE="flatRate";
        String DRN_ACK_PAGE = "bndAcknowldgeMentStatusForm";
        String CHECKLISTAPPLICABLE = "A";
        
        public enum ServiceDesc {
        	DEATH_REG("Death Registration Service"),
        	DEATH_CORR("Death Correction Service"),
        	BIRTH_REG("Birth Registration Service"),
        	BIRTH_CORR("Birth Correction Service"),
            BIRTH_ISSUE("Issuence of Birth Certificate"),
            DEATH_ISSUE("Issuence of Death Certificate"),
            CHILD_INCL("Child Inclusion Service");
        	
        	private String serviceDesc;

        	ServiceDesc(String serviceDesc) {
                this.serviceDesc = serviceDesc;
            }

            public String getServiceDesc() {
                return serviceDesc;
            }
        }
        

        
}
