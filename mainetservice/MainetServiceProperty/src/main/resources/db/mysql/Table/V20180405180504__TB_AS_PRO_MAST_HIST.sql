--liquibase formatted sql
--changeset nilima:V20180405180504__TB_AS_PRO_MAST_HIST1.sql
drop table IF EXISTS TB_AS_PRO_MAST_HIST;

--liquibase formatted sql
--changeset nilima:V20180405180504__TB_AS_PRO_MAST_HIST2.sql
CREATE TABLE TB_AS_PRO_MAST_HIST (
`PRO_ASS_HIST_ID` BIGINT(12),
`PRO_ASS_ID`	BIGINT(12)	NOT NULL	  COMMENT '	PRIMARY KEY	',
`PRO_ASS_NO`	VARCHAR(20)	NOT NULL	  COMMENT '	ASSESMENT NUMBER	',
`TPP_APPROVAL_NO`	VARCHAR(200)	NULL	  COMMENT '	BUILDING APPROVAL NO	',
`PRO_ASS_OLDPROPNO`	VARCHAR(20)	NOT NULL	  COMMENT '	OLD PROPERTY NUMBER	',
`TPP_PLOT_NO_CS`	VARCHAR(50)	NOT NULL	  COMMENT '	CSN_KHASARA NO	',
`TPP_SURVEY_NUMBER`	VARCHAR(25)	NOT NULL	  COMMENT '	SURVEY NUMBER	',
`TPP_KHATA_NO`	VARCHAR(50)	NOT NULL	  COMMENT '	KHATA NUMBER	',
`TPP_TOJI_NO`	VARCHAR(50)	NOT NULL	  COMMENT '	TOJI NUMBER	',
`TPP_PLOT_NO`	VARCHAR(50)	NOT NULL	  COMMENT '	PLOT NUMBER	',
`PRO_ASS_STREET_NO`	VARCHAR(500)	NOT NULL	  COMMENT '	STREET NUMBER_NAME	',
`TPP_VILLAGE_MAUJA`	VARCHAR(50)	NOT NULL	  COMMENT '	VILLAGE_MAUJA NAME	',
`PRO_ASS_ADDRESS`	VARCHAR(1000)	NOT NULL	  COMMENT '	PROPERTY ADDRESS	',
`LOC_ID`	BIGINT(12)	NOT NULL	  COMMENT '	LOCATION  FOREGIN KEY(TB_LOCATION_MAS)	',
`PRO_ASS_PINCODE`	INT(11)	NOT NULL	  COMMENT '	PINCODE	',
`PRO_ASS_CORR_ADDRESS`	VARCHAR(1000)	NOT NULL	  COMMENT '	CORRESPONDENCE ADDRESS	',
`PRO_ASS_CORR_PINCODE`	INT(11)	NOT NULL	  COMMENT '	CORRESPONDENCE ADDRESS PINCODE	',
`PRO_ASS_CORR_EMAIL`	VARCHAR(25)	NULL	  COMMENT '	CORRESPONDENCE EMAIL	',
`PRO_ASS_LP_RECEIPT_NO`	VARCHAR(25)	 NULL DEFAULT NULL 	  COMMENT '	LAST PAYMENT RECEIPT NO.	',
`PRO_ASS_LP_RECEIPT_AMT`	DECIMAL(15,2)	 NULL DEFAULT NULL 	  COMMENT '	LAST PAYMENT RECEIPT AMOUNT	',
`PRO_ASS_LP_RECEIPT_DATE`	DATETIME	 NULL DEFAULT NULL 	  COMMENT '	LAST PAYMENT RECEIPT DATE	',
`PRO_ASS_LP_YEAR`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	LAST PAYMENT BILLING CYCLE	',
`PRO_ASS_LP_BILL_CYCLE`	BIGINT(20)	 NULL DEFAULT NULL 	  COMMENT '	BILL CYCLE	',
`BILL_AMOUNT`	DECIMAL(15,2)	 NULL DEFAULT NULL 	  COMMENT '	BILL AMOUNT	',
`OUTSTANDING_AMOUNT`	DECIMAL(15,2)	 NULL DEFAULT NULL 	  COMMENT '	OUTSTANDING AMOUNT	',
`PRO_ASS_ACQ_DATE`	DATETIME	NOT NULL	  COMMENT '	ACQUISITION DATE	',
`PRO_ASS_PROP_TYPE1`	BIGINT(12)	NOT NULL	  COMMENT '	PROPERTY TYPE	',
`PRO_ASS_PROP_TYPE2`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	PROPERTY SUBTYPE	',
`PRO_ASS_PROP_TYPE3`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	PROPERTY SUBTYPE	',
`PRO_ASS_PROP_TYPE4`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	PROPERTY SUBTYPE	',
`PRO_ASS_PROP_TYPE5`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	PROPERTY SUBTYPE	',
`PRO_ASS_PLOT_AREA`	DECIMAL(15,2)	NOT NULL	  COMMENT '	TOTAL PLOT AREA	',
`PRO_ASS_BUIT_AREA_GR`	DECIMAL(15,2)	NOT NULL	  COMMENT '	BUILD UP/CONSTRUCTED AREA ON THE GROUND FLOOR	',
`PRO_ASS_OWNER_TYPE`	BIGINT(12)	NOT NULL	  COMMENT '	OWNER TYPE (PREFIX OWT)	',
`PRO_ASS_WARD1`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	WARD1	',
`PRO_ASS_WARD2`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	WARD2	',
`PRO_ASS_WARD3`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	WARD3	',
`PRO_ASS_WARD4`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	WARD4	',
`PRO_ASS_WARD5`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	WARD5	',
`PRO_ASS_GIS_ID`	VARCHAR(20)	 NULL DEFAULT NULL 	  COMMENT '	GIS ID	',
`PRO_ASS_ACTIVE`	CHAR(1)	NOT NULL	  COMMENT '	FLAG TO IDENTIFY WHETHER THE RECORD IS DELETED OR NOT. ''Y''  FOR NOT DELETED (ACTIVE) RECORD AND ''N'' FOR DELETED (INACTIVE) .	',
`PRO_ASS_STATUS`	CHAR(2)	NOT NULL	  COMMENT '	ASSESMENT FLOW STATUS	',
`PRO_ASS_AUT_STATUS`	CHAR(2)	 NULL DEFAULT NULL 	  COMMENT '	AUTHORISATION STATUS	',
`PRO_ASS_AUT_BY`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	AUTHORISATION BY (EMPID)	',
`PRO_ASS_AUT_DATE`	DATETIME	 NULL DEFAULT NULL 	  COMMENT '	AUTHORISATION DATE	',
PRO_ASS_TASK_ID BIGINT(12) NULL DEFAULT NULL COMMENT '	AUTHORISATION TASK ID',
`ORGID`	BIGINT(12)	NOT NULL	  COMMENT '	ORGNISATION ID	',
`CREATED_BY`	BIGINT(12)	NOT NULL	  COMMENT '	USER ID WHO CREATED THE RECORD	',
`CREATED_DATE`	DATETIME	NOT NULL	  COMMENT '	RECORD CREATION DATE	',
`UPDATED_BY`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	USER ID WHO UPDATE THE DATA	',
`UPDATED_DATE`	DATETIME	 NULL DEFAULT NULL 	  COMMENT '	DATE ON WHICH DATA IS GOING TO UPDATE	',
`LG_IP_MAC`	VARCHAR(100)	 NULL DEFAULT NULL 	  COMMENT '	CLIENT MACHINE?S LOGIN NAME | IP ADDRESS | PHYSICAL ADDRESS	',
`LG_IP_MAC_UPD`	VARCHAR(100)	 NULL DEFAULT NULL 	  COMMENT '	CLIENT MACHINE?S LOGIN NAME | IP ADDRESS | PHYSICAL ADDRESS UPDATED	',
`APM_APPLICATION_ID`	BIGINT(16)	 NULL DEFAULT NULL 	  COMMENT '	APPLICATION ID	',
`PRO_ASS_EMAIL`	VARCHAR(25)	 NULL DEFAULT NULL 	  COMMENT '	PROPERTY ADDRESS : EMAIL ID	',
`SM_SERVICE_ID`	BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	SERVICE ID NUMBER	',
`PRO_INDFY_REASON`	VARCHAR(25)	 NULL DEFAULT NULL 	  COMMENT '	REASON TO FOR CREATATION OF PROPERTY LIKE MUTATION - M, NEW - N, BIFERCATION - B OR AMALGAMATION - A	' 			,
`PRO_CHILD_PROP`	BIGINT(20)	 NULL DEFAULT NULL 	  COMMENT '	PROPERTY ID OF CHILD PROPERTY IN CASE OF NEW PROPERTY CREATED DUE TO AMALGAMATION	' 			,
`PRO_PARENT_PROP`	BIGINT(20)	 NULL DEFAULT NULL 	  COMMENT '	PROPERTY ID OF PARENT PROPERTY IN CASE OF NEW PROPERTY CREATED DUE TO MUTATION OR BIFERCATION	',
`FA_YEARID` BIGINT(12)	 NULL DEFAULT NULL 	  COMMENT '	FINANCIAL ID',
`H_STATUS` char(1)  NULL DEFAULT NULL 	  COMMENT '	X	');

--liquibase formatted sql
--changeset nilima:V20180405180504__TB_AS_PRO_MAST_HIST3.sql
alter table TB_AS_PRO_MAST_HIST add constraint PK_PRO_ASS_HIST_ID primary key (PRO_ASS_HIST_ID);
