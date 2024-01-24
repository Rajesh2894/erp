--liquibase formatted sql
--changeset shamik:V20180605183042__TB_AST_PURCHASER_HIST1.sql
DROP TABLE IF EXISTS TB_AST_PURCHASER_HIST;


--liquibase formatted sql
--changeset shamik:V20180605183042__TB_AST_PURCHASER_HIST2.sql
CREATE TABLE TB_AST_PURCHASER_HIST (
ASSET_PURCHASER_HIST_ID BIGINT(12),
ASSET_PURCHASER_ID BIGINT(12),
ASSET_ID BIGINT(12)  NOT NULL COMMENT '	X	',
VENDOR BIGINT(20) NOT NULL COMMENT '	X	',
MANUFACTURER VARCHAR(100) NOT NULL COMMENT '	X	',
PURCHASE_ORDER_NO BIGINT(20) NOT NULL COMMENT '	X	',
PURCHASE_DATE DATE NOT NULL COMMENT '	X	',
PURCHASE_COST DECIMAL(15,2) NOT NULL COMMENT '	X	',
BOOK_VALUE DECIMAL(15,2) NOT NULL COMMENT '	X	',
LIFE_IN_YEARS DECIMAL(15,2) NULL DEFAULT NULL 	  COMMENT '	X	',
MODE_OF_PAYMENT BIGINT(20) NULL DEFAULT NULL 	  COMMENT '	X	',
COUNTRY_OF_ORIGIN BIGINT(20) NULL DEFAULT NULL 	  COMMENT '	X	',
CREATED_BY BIGINT(12) NULL DEFAULT NULL 	  COMMENT '	X	',
CREATION_DATE DATETIME NULL DEFAULT NULL 	  COMMENT '	X	',
UPDATED_BY BIGINT(12)  NULL DEFAULT NULL 	  COMMENT '	X	',
UPDATED_DATE DATETIME  NULL DEFAULT NULL 	  COMMENT '	X	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL 	  COMMENT '	X	',
LG_IP_MAC_UPD VARCHAR(100)  NULL DEFAULT NULL 	  COMMENT '	X	',
H_STATUS char(1) NULL DEFAULT NULL 	  COMMENT '	X	');

--liquibase formatted sql
--changeset shamik:V20180605183042__TB_AST_PURCHASER_HIST3.sql
ALTER TABLE TB_AST_PURCHASER_HIST ADD CONSTRAINT PK_TB_AST_PURCHASER_HIST PRIMARY KEY (ASSET_PURCHASER_HIST_ID);

