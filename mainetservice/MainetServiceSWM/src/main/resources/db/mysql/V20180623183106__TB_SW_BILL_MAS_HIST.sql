--liquibase formatted sql
--changeset shamik:V20180623183106__TB_SW_BILL_MAS_HIST1.sql
DROP TABLE IF EXISTS TB_SW_BILL_MAS_HIST;

--liquibase formatted sql
--changeset shamik:V20180623183106__TB_SW_BILL_MAS_HIST2.sql
CREATE TABLE TB_SW_BILL_MAS_HIST(
SW_BM_IDNO_HIST_ID BIGINT(12),
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
BILL_PAID_FLG CHAR(1),
ORGID BIGINT(12),
CREATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT '	X	',
CREATION_DATE DATETIME NULL DEFAULT NULL COMMENT '	X	',
UPDATED_BY BIGINT(12)  NULL DEFAULT NULL COMMENT '	X	',
UPDATED_DATE DATETIME  NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC_UPD VARCHAR(100)  NULL DEFAULT NULL COMMENT '	X	',
H_STATUS char(1) NULL DEFAULT NULL COMMENT '	X '	);




--liquibase formatted sql
--changeset shamik:V20180623183106__TB_SW_BILL_MAS_HIST3.sql
ALTER TABLE TB_SW_BILL_MAS_HIST ADD CONSTRAINT PK_TB_SW_BILL_MAS_HIST PRIMARY KEY (SW_BM_IDNO_HIST_ID);


