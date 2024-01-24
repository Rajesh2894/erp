--liquibase formatted sql
--changeset shamik:V20180620183058__TB_AST_PURCHASER_REV1.sql
DROP TABLE IF EXISTS TB_AST_PURCHASER_REV;


--liquibase formatted sql
--changeset shamik:V20180620183058__TB_AST_PURCHASER_REV2.sql
CREATE TABLE TB_AST_PURCHASER_REV (
ASSET_PURCHASER_REV_ID BIGINT(12)   NOT NULL COMMENT '	Primary key	',
ASSET_PURCHASER_ID BIGINT(12)   NOT NULL COMMENT '	Primary key	',
ASSET_ID BIGINT(12)  NOT NULL COMMENT '	REFERENCES  Key of TB_AST_INFO_MST ',
VENDOR BIGINT(20) NULL COMMENT '	VENDOR 	info	',
MANUFACTURER VARCHAR(100) NOT NULL COMMENT '	MANUFACTURER 	info	',
PURCHASE_ORDER_NO BIGINT(20) NULL COMMENT '	PURCHASE_ORDER_NO	',
PURCHASE_DATE DATE NOT NULL COMMENT '	PURCHASE DATE	',
PURCHASE_COST DECIMAL(15,2) NOT NULL COMMENT '	PURCHASE COST	',
BOOK_VALUE DECIMAL(15,2) NOT NULL COMMENT '	BOOK VALUE	',
LIFE_IN_YEARS DECIMAL(15,2) NULL DEFAULT NULL 	  COMMENT '	Total LIFE in Years	',
MODE_OF_PAYMENT BIGINT(20) NULL DEFAULT NULL 	  COMMENT '	MODE OF PAYMENT	',
COUNTRY_OF_ORIGIN BIGINT(20) NULL DEFAULT NULL 	  COMMENT '	COUNTRY OF ORIGIN	',
PURCHASE_ORDER_DATE DATETIME NULL DEFAULT NULL COMMENT '	X	',
CREATED_BY BIGINT(12) NULL DEFAULT NULL 	  COMMENT '	CREATED_BY			',
CREATION_DATE DATETIME NULL DEFAULT NULL 	  COMMENT '	CREATION_DATE			',
UPDATED_BY BIGINT(12)  NULL DEFAULT NULL 	  COMMENT '	UPDATED_BY			',
UPDATED_DATE DATETIME  NULL DEFAULT NULL 	  COMMENT '	UPDATED_DATE	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL 	  COMMENT '	LG_IP_MAC	',
LG_IP_MAC_UPD VARCHAR(100)  NULL DEFAULT NULL 	  COMMENT '	LG_IP_MAC_UPD	');

--liquibase formatted sql
--changeset shamik:V20180620183058__TB_AST_PURCHASER_REV3.sql
ALTER TABLE TB_AST_PURCHASER_REV ADD CONSTRAINT PK_TB_AST_PURCHASER_REV PRIMARY KEY (ASSET_PURCHASER_REV_ID);


