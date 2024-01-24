--liquibase formatted sql
--changeset shamik:V20180316183025__TB_AST_PURCHASER1.sql
DROP TABLE IF EXISTS TB_AST_PURCHASER;


--liquibase formatted sql
--changeset shamik:V20180316183025__TB_AST_PURCHASER2.sql
CREATE TABLE TB_AST_PURCHASER (
ASSET_PURCHASER_ID BIGINT(12)   NOT NULL COMMENT '	Primary key	',
ASSET_ID BIGINT(12)  NOT NULL COMMENT '	REFERENCES  Key of TB_AST_INFO_MST ',
VENDOR VARCHAR(100) NOT NULL COMMENT '	VENDOR 	info	',
MANUFACTURER VARCHAR(100) NOT NULL COMMENT '	MANUFACTURER 	info	',
PURCHASE_ORDER_NO BIGINT(20) NOT NULL COMMENT '	PURCHASE_ORDER_NO	',
PURCHASE_DATE DATE NOT NULL COMMENT '	PURCHASE DATE	',
PURCHASE_COST BIGINT(20) NOT NULL COMMENT '	PURCHASE COST	',
BOOK_VALUE BIGINT(20) NOT NULL COMMENT '	BOOK VALUE	',
LIFE_IN_YEARS VARCHAR(100) NULL DEFAULT NULL 	  COMMENT '	Total LIFE in Years	',
MODE_OF_PAYMENT VARCHAR(100) NULL DEFAULT NULL 	  COMMENT '	MODE OF PAYMENT	',
COUNTRY_OF_ORIGIN VARCHAR(100) NULL DEFAULT NULL 	  COMMENT '	COUNTRY OF ORIGIN	',
CREATED_BY BIGINT(12) NULL DEFAULT NULL 	  COMMENT '	CREATED_BY			',
CREATION_DATE DATETIME NULL DEFAULT NULL 	  COMMENT '	CREATION_DATE			',
UPDATED_BY BIGINT(12)  NULL DEFAULT NULL 	  COMMENT '	UPDATED_BY			',
UPDATED_DATE DATETIME  NULL DEFAULT NULL 	  COMMENT '	UPDATED_DATE	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL 	  COMMENT '	LG_IP_MAC	',
LG_IP_MAC_UPD VARCHAR(100)  NULL DEFAULT NULL 	  COMMENT '	LG_IP_MAC_UPD	');

--liquibase formatted sql
--changeset shamik:V20180316183025__TB_AST_PURCHASER3.sql
ALTER TABLE TB_AST_PURCHASER ADD CONSTRAINT PK_TB_AST_PURCHASER PRIMARY KEY (ASSET_PURCHASER_ID);

--liquibase formatted sql
--changeset shamik:V20180316183025__TB_AST_PURCHASER4.sql
ALTER TABLE TB_AST_PURCHASER   ADD CONSTRAINT FK_TB_AST_PURCHASER FOREIGN KEY (ASSET_ID)
  REFERENCES TB_AST_INFO_MST (ASSET_ID);

