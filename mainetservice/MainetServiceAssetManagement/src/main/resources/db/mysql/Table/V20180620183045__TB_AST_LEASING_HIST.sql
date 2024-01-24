--liquibase formatted sql
--changeset shamik:V20180620183045__TB_AST_LEASING_HIST1.sql
DROP TABLE IF EXISTS TB_AST_LEASING_HIST;

--liquibase formatted sql
--changeset shamik:V20180620183045__TB_AST_LEASING_HIST2.sql
CREATE TABLE TB_AST_LEASING_HIST (
ASSET_LEASING_HIST_ID BIGINT(12),
ASSET_LEASING_ID BIGINT(12),
ASSET_ID BIGINT(12) NOT NULL COMMENT '	X	',
CONTRACT_AGREEMENT_NO BIGINT(20) NULL DEFAULT NULL COMMENT '	X	',
AGREEMENT_DATE DATE NULL DEFAULT NULL COMMENT '	X	',
NOTICE_DATE DATE NULL DEFAULT NULL COMMENT '	X	',
LEASE_START_DATE DATE NULL DEFAULT NULL COMMENT '	X	',
LEASE_END_DATE DATE NULL DEFAULT NULL COMMENT '	X	',
LEASE_TYPE BIGINT(20) NULL DEFAULT NULL COMMENT '	X	',
PURCHASE_PRIZE DECIMAL(15,2) NULL DEFAULT NULL COMMENT '	X	',
NO_OF_INSTALLMENT BIGINT(20) NULL DEFAULT NULL COMMENT '	X	',
PAYMENT_FREQUENCY BIGINT(20) NULL DEFAULT NULL COMMENT '	X	',
ADVANCE_PAYMENT DECIMAL(15,2) NULL DEFAULT NULL COMMENT '	X	',
CREATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT '	X	',
CREATION_DATE DATETIME NULL DEFAULT NULL COMMENT '	X	',
UPDATED_BY BIGINT(12)  NULL DEFAULT NULL COMMENT '	X	',
UPDATED_DATE DATETIME  NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC_UPD VARCHAR(100)  NULL DEFAULT NULL COMMENT '	X	',
H_STATUS char(1)  NULL DEFAULT NULL COMMENT '	X	');


--liquibase formatted sql
--changeset shamik:V20180620183045__TB_AST_LEASING_HIST3.sql
ALTER TABLE TB_AST_LEASING_HIST ADD CONSTRAINT PK_TB_AST_LEASING_HIST PRIMARY KEY (ASSET_LEASING_HIST_ID);


