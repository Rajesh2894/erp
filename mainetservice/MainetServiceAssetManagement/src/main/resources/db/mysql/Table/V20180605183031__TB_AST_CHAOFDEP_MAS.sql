--liquibase formatted sql
--changeset shamik:V20180605183031__TB_AST_CHAOFDEP_MAS1.sql
DROP TABLE IF EXISTS TB_AST_CHAOFDEP_MAS;

--liquibase formatted sql
--changeset shamik:V20180605183031__TB_AST_CHAOFDEP_MAS2.sql
CREATE TABLE TB_AST_CHAOFDEP_MAS (
GROUP_ID BIGINT(12) COMMENT '	GROUP_ID ',
NAME VARCHAR(100) NOT NULL COMMENT '	NAME OF CHART OF DEPRICIATION ID 	',
ORGID BIGINT(12) NOT NULL COMMENT '	ORGANISATION	',
REMARK VARCHAR(500) NULL COMMENT '	X	',
ACCOUNT_CODE BIGINT(20) NOT NULL COMMENT '	ACCOUNT	CODE',
ASSET_CLASS BIGINT(20) NOT NULL COMMENT '	ASSET CLASS	',
FREQUENCY BIGINT(20) NOT NULL COMMENT '	FREQUENCY	',
RATE DECIMAL(15,2)  NULL COMMENT '	RATE	',
DEPRECIATION_KEY BIGINT(20)   NOT NULL COMMENT '	DEPRECIATION_KEY	',
CREATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT '	X	',
CREATION_DATE DATETIME NULL DEFAULT NULL COMMENT '	X	',
UPDATED_BY BIGINT(12)  NULL DEFAULT NULL COMMENT '	X	',
UPDATED_DATE DATETIME  NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC_UPD VARCHAR(100)  NULL DEFAULT NULL COMMENT '	X	');

--liquibase formatted sql
--changeset shamik:V20180605183031__TB_AST_CHAOFDEP_MAS3.sql
ALTER TABLE TB_AST_CHAOFDEP_MAS ADD CONSTRAINT PK_TB_AST_CHAOFDEP_MAS PRIMARY KEY (GROUP_ID);