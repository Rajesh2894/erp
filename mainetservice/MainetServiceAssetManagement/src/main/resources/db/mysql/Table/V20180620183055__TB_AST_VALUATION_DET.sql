--liquibase formatted sql
--changeset shamik:V20180620183024__TB_AST_CLASSFCTN1.sql
DROP TABLE if exists TB_AST_VALUATION_DET;

--liquibase formatted sql
--changeset shamik:V20180620183024__TB_AST_CLASSFCTN2.sql
CREATE TABLE TB_AST_VALUATION_DET(
VALUATION_DET_ID bigint(12),
VALUATION_DET_REF_ID bigint(12),
ASSET_ID bigint(12),							
BOOK_VALUE DECIMAL(15,2),
BOOK_DATE DATETIME,
BOOK_FINYEAR bigint(12),	
BOOK_END_DATE DATETIME,
BOOK_END_VALUE DECIMAL(15,2),
CHANGE_TYPE	VARCHAR(15),
ADDITIONAL_COST DECIMAL(15,2),
EXPENDITURE_COST DECIMAL(15,2),
ACCUM_DEPR_VALUE DECIMAL(15,2),
DEPR_VALUE DECIMAL(15,2),	
GROUP_ID BIGINT(12),	
STATE CHAR(1),
ORGID BIGINT(12),
CREATED_BY bigint(12) NULL DEFAULT NULL COMMENT '	CREATED_BY		',
CREATION_DATE DATETIME NULL DEFAULT NULL COMMENT '	CREATION_DATE		',
UPDATED_BY bigint(12) NULL DEFAULT NULL COMMENT '	UPDATED_BY		',
UPDATED_DATE DATETIME NULL DEFAULT NULL COMMENT '	UPDATED_DATE		',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL COMMENT '	LG_IP_MAC		',
LG_IP_MAC_UPD VARCHAR(100) NULL DEFAULT NULL COMMENT '	LG_IP_MAC_UPD	');

--liquibase formatted sql
--changeset shamik:V20180620183024__TB_AST_CLASSFCTN3.sql
ALTER TABLE TB_AST_VALUATION_DET ADD CONSTRAINT PK_TB_AST_VALUATION_DET PRIMARY KEY (VALUATION_DET_ID);

