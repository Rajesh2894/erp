--liquibase formatted sql
--changeset shamik:V20180620183047__TB_AST_LINEAR_HIST1.sql
DROP TABLE IF EXISTS TB_AST_LINEAR_HIST;

--liquibase formatted sql
--changeset shamik:V20180620183047__TB_AST_LINEAR_HIST2.sql
CREATE TABLE TB_AST_LINEAR_HIST (
ASSET_LINEAR_HIST_ID BIGINT(12),
ASSET_LINEAR_ID BIGINT(12),
ASSET_ID BIGINT(12)  NOT NULL COMMENT '	X	',
START_POINT DECIMAL(15,2),
END_POINT DECIMAL(15,2),
AST_LENGTH DECIMAL(15,2),
LENGTH_UNIT BIGINT(20),
TYPE_OF_OFFSET1 VARCHAR(100),
OFFSET1 DECIMAL(15,2),
OFFSET1_UNIT BIGINT(20),
TYPE_OF_OFFSET2 VARCHAR(100),
OFFSET2 DECIMAL(15,2),
OFFSET2_UNIT BIGINT(20),
MARKER_CODE VARCHAR(100),
MARKER_DESCRIPTION VARCHAR(100),
MARKER_TYPE BIGINT(20),
GRID_START_POINT DECIMAL(15,2),
GRID_END_POINT DECIMAL(15,2),
UOM BIGINT(20),
CREATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT '	X	',
CREATION_DATE DATETIME NULL DEFAULT NULL COMMENT '	X	',
UPDATED_BY BIGINT(12)  NULL DEFAULT NULL COMMENT '	X	',
UPDATED_DATE DATETIME  NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC_UPD VARCHAR(100)  NULL DEFAULT NULL COMMENT '	X	',
H_STATUS char(1)  NULL DEFAULT NULL COMMENT '	X	');


--liquibase formatted sql
--changeset shamik:V20180620183047__TB_AST_LINEAR_HIST3.sql
ALTER TABLE TB_AST_LINEAR_HIST ADD CONSTRAINT PK_TB_AST_LINEAR_HIST PRIMARY KEY (ASSET_LINEAR_HIST_ID);



