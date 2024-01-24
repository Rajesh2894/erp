--liquibase formatted sql
--changeset shamik:V20180316183046__TB_AST_CHART_OF_DEPRETN_HIST1.sql
DROP TABLE IF EXISTS TB_AST_CHART_OF_DEPRETN_HIST;



--liquibase formatted sql
--changeset shamik:V20180316183046__TB_AST_CHART_OF_DEPRETN_HIST2.sql
CREATE TABLE TB_AST_CHART_OF_DEPRETN_HIST (
ASSET_DEPRETN_HIST_ID BIGINT(12),
ASSET_DEPRETN_ID BIGINT(12),
ASSET_ID BIGINT(12)  NOT NULL COMMENT '	X	',
SALVAGE_VALUE BIGINT(20),
CHART_OF_DEPRETN VARCHAR(10) NOT NULL COMMENT '	X	',
ORIGINAL_USEFUL_LIFE VARCHAR(100),
REMARK VARCHAR(500),
CREATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT '	X	',
CREATION_DATE DATETIME NULL DEFAULT NULL COMMENT '	X	',
UPDATED_BY BIGINT(12)  NULL DEFAULT NULL COMMENT '	X	',
UPDATED_DATE DATETIME  NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC_UPD VARCHAR(100)  NULL DEFAULT NULL COMMENT '	X	',
H_STATUS char(1)  NULL DEFAULT NULL COMMENT '	X	');



--liquibase formatted sql
--changeset shamik:V20180316183046__TB_AST_CHART_OF_DEPRETN_HIST3.sql
ALTER TABLE TB_AST_CHART_OF_DEPRETN_HIST ADD CONSTRAINT PK_TB_AST_CHART_OF_DEPRETN_HIST PRIMARY KEY (ASSET_DEPRETN_HIST_ID);





