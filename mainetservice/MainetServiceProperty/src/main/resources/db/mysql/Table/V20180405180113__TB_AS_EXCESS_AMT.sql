--liquibase formatted sql
--changeset shamik:V20180405180113__TB_AS_EXCESS_AMT1.sql
DROP TABLE IF EXISTS TB_AS_EXCESS_AMT;

--liquibase formatted sql
--changeset shamik:V20180405180113__TB_AS_EXCESS_AMT2.sql
CREATE TABLE TB_AS_EXCESS_AMT (
EXCESS_ID BIGINT(12) NOT NULL DEFAULT '0' COMMENT 'PRIMARY KEY',
PROP_NO VARCHAR(20)	 NOT NULL	  COMMENT '	ASSESMENT NO OF TB_AS_ASSESSMENT_MAST	',
RM_RCPTID BIGINT(12) DEFAULT NULL COMMENT 'RECEPT ID OF TB_RECEIPT_MAS',
EXC_AMT DECIMAL(12,2) DEFAULT NULL COMMENT 'EXCESS AMOUNT',
ADJ_AMT DECIMAL(12,2) DEFAULT NULL COMMENT 'ADDJUSTED AMOUNT',
EXCADJ_FLAG CHAR(1) DEFAULT NULL COMMENT 'EXCESS OR ADJUSTMENT FLAG N(NON ADJUSTED)/Y ( ADJUSTED)',
EXCESS_DIS VARCHAR(1) DEFAULT NULL COMMENT 'EXCESS DISHONOUR FLAG',
TAX_ID BIGINT(12) DEFAULT NULL COMMENT 'TAX_ID',
ORGID BIGINT(12) NOT NULL COMMENT 'ORGANISATION ID',
CREATED_BY BIGINT(12)	 NOT NULL	  COMMENT '	USER ID WHO CREATED THE RECORD	',
CREATED_DATE DATETIME	 NOT NULL	  COMMENT '	RECORD CREATION DATE	',
UPDATED_BY BIGINT(12) DEFAULT NULL COMMENT 'USER ID WHO UPDATE THE DATA',
UPDATED_DATE DATETIME DEFAULT NULL COMMENT 'DATE ON WHICH DATA IS GOING TO UPDATE',
LG_IP_MAC VARCHAR(100) DEFAULT NULL COMMENT 'CLIENT MACHINE?S LOGIN NAME | IP ADDRESS | PHYSICAL ADDRESS',
LG_IP_MAC_UPD VARCHAR(100) DEFAULT NULL COMMENT 'UPDATED CLIENT MACHINE?S LOGIN NAME | IP ADDRESS | PHYSICAL ADDRESS',
EXTRA_COL1 VARCHAR(100) DEFAULT NULL COMMENT 'X',
EXTRA_COL2 VARCHAR(100) DEFAULT NULL COMMENT 'X',
EXTRA_COL3 VARCHAR(100) DEFAULT NULL COMMENT 'X'
 );


--liquibase formatted sql
--changeset shamik:V20180405180113__TB_AS_EXCESS_AMT3.sql
ALTER TABLE TB_AS_EXCESS_AMT ADD CONSTRAINT PK_TB_AS_EXCESS_AMT PRIMARY KEY (EXCESS_ID);

--liquibase formatted sql
--changeset shamik:V20180405180113__TB_AS_EXCESS_AMT4.sql
ALTER TABLE TB_AS_EXCESS_AMT ADD CONSTRAINT FK_TB_AS_EXCESS_AMT FOREIGN KEY (RM_RCPTID)
REFERENCES  TB_RECEIPT_MAS (RM_RCPTID);