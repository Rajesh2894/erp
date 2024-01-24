--liquibase formatted sql
--changeset shamik:V20180620183049__TB_AST_FUNC_LOCATION_MAS_HIST1.sql
DROP TABLE IF EXISTS TB_AST_FUNC_LOCATION_MAS_HIST;


--liquibase formatted sql
--changeset shamik:V20180620183049__TB_AST_FUNC_LOCATION_MAS_HIST2.sql
CREATE TABLE TB_AST_FUNC_LOCATION_MAS_HIST (
FUNC_LOCATION_CODE_HIST_ID BIGINT(12),
FUNC_LOCATION_ID BIGINT(12),
FUNC_LOCATION_CODE VARCHAR(500),
ORGID BIGINT(12) NOT NULL COMMENT '	X	',
DESCRIPTION VARCHAR(100),
FUNC_LOC_PARENT_ID BIGINT(20),
START_POINT VARCHAR(100),
END_POINT VARCHAR(100),
UNIT BIGINT(20),
CREATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT '	X	',
CREATION_DATE DATETIME NULL DEFAULT NULL COMMENT '	X	',
UPDATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT '	X	',
UPDATED_DATE DATETIME NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC_UPD VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
H_STATUS char(1) NULL DEFAULT NULL COMMENT '	X	'
);


--liquibase formatted sql
--changeset shamik:V20180620183049__TB_AST_FUNC_LOCATION_MAS_HIST3.sql
ALTER TABLE TB_AST_FUNC_LOCATION_MAS_HIST ADD CONSTRAINT PK_TB_AST_FUNC_LOCATION_MAS_HIST PRIMARY KEY (FUNC_LOCATION_CODE_HIST_ID);

