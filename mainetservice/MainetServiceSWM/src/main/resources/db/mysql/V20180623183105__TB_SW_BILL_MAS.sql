--liquibase formatted sql
--changeset shamik:V20180623183105__TB_SW_NEW_BILL_MAS1.sql
DROP TABLE IF EXISTS TB_SW_BILL_MAS;

--liquibase formatted sql
--changeset shamik:V20180623183105__TB_SW_BILL_MAS2.sql
CREATE TABLE TB_SW_BILL_MAS(
SW_BM_IDNO BIGINT(12),
SW_BM_BILLNO BIGINT(12),
REGISTRATION_ID BIGINT(12),
SW_NEW_CUST_ID VARCHAR(100),
LAST_PAYMENT_UPTO DATETIME,
MANUAL_RECEIPT_NO VARCHAR(20),
RECEIPT_UPLOAD_INFO VARCHAR(500),
MONTHLY_CHARGES DECIMAL(15,2),
FIN_YEARID BIGINT(12),
BILL_AMOUNT DECIMAL(15,2),
OUTST_AMOUNT DECIMAL(15,2),
ADVANCE_AMOUNT DECIMAL(15,2),
BILL_FROM_DATE DATETIME, 
BILL_TO_DATE DATETIME, 
BILL_PAID_FLG CHAR(1) NULL DEFAULT 'N' COMMENT '	X	',
ORGID BIGINT(12),
CREATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT '	X	',
CREATION_DATE DATETIME NULL DEFAULT NULL COMMENT '	X	',
UPDATED_BY BIGINT(12)  NULL DEFAULT NULL COMMENT '	X	',
UPDATED_DATE DATETIME  NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC_UPD VARCHAR(100)  NULL DEFAULT NULL COMMENT '	X	');




--liquibase formatted sql
--changeset shamik:V20180623183105__TB_SW_BILL_MAS3.sql
ALTER TABLE TB_SW_BILL_MAS ADD CONSTRAINT PK_TB_SW_BILL_MAS PRIMARY KEY (SW_BM_IDNO);


--liquibase formatted sql
--changeset shamik:V20180623183105__TB_SW_NEW_BILL_MAS4.sql
ALTER TABLE TB_SW_BILL_MAS ADD CONSTRAINT FK_TB_SW_BILL_MAS FOREIGN KEY (REGISTRATION_ID)
  REFERENCES TB_SW_REGISTRATION (REGISTRATION_ID);

