--liquibase formatted sql
--changeset shamik:V20180316183028__TB_AST_LEASING1.sql
DROP TABLE IF EXISTS TB_AST_LEASING;

--liquibase formatted sql
--changeset shamik:V20180316183028__TB_AST_LEASING2.sql
CREATE TABLE TB_AST_LEASING (
ASSET_LEASING_ID BIGINT(12),
ASSET_ID BIGINT(12) NOT NULL COMMENT '	X	',
CONTRACT_AGREEMENT_NO BIGINT(20) NULL DEFAULT NULL COMMENT '	X	',
AGREEMENT_DATE DATE NULL DEFAULT NULL COMMENT '	X	',
NOTICE_DATE DATE NULL DEFAULT NULL COMMENT '	X	',
LEASE_START_DATE DATE NULL DEFAULT NULL COMMENT '	X	',
LEASE_END_DATE DATE NULL DEFAULT NULL COMMENT '	X	',
LEASE_TYPE VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
PURCHASE_PRIZE BIGINT(20) NULL DEFAULT NULL COMMENT '	X	',
NO_OF_INSTALLMENT BIGINT(20) NULL DEFAULT NULL COMMENT '	X	',
PAYMENT_FREQUENCY VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
ADVANCE_PAYMENT VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
CREATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT '	X	',
CREATION_DATE DATETIME NULL DEFAULT NULL COMMENT '	X	',
UPDATED_BY BIGINT(12)  NULL DEFAULT NULL COMMENT '	X	',
UPDATED_DATE DATETIME  NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC_UPD VARCHAR(100)  NULL DEFAULT NULL COMMENT '	X	');


--liquibase formatted sql
--changeset shamik:V20180316183028__TB_AST_LEASING3.sql
ALTER TABLE TB_AST_LEASING ADD CONSTRAINT PK_TB_AST_LEASING PRIMARY KEY (ASSET_LEASING_ID);

--liquibase formatted sql
--changeset shamik:V20180316183028__TB_AST_LEASING4.sql
ALTER TABLE TB_AST_LEASING   ADD CONSTRAINT FK_TB_AST_LEASING FOREIGN KEY (ASSET_ID)
  REFERENCES TB_AST_INFO_MST (ASSET_ID);

