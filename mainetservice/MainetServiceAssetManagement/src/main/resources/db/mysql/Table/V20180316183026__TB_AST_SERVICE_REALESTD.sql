--liquibase formatted sql
--changeset shamik:V20180316183026__TB_AST_SERVICE_REALESTD1.sql
DROP TABLE IF EXISTS TB_AST_SERVICE_REALESTD;

--liquibase formatted sql
--changeset shamik:V20180316183026__TB_AST_SERVICE_REALESTD2.sql
CREATE TABLE TB_AST_SERVICE_REALESTD (
ASSET_SERVICE_ID BIGINT(12)  NOT NULL COMMENT '	Primary Key	',
ASSET_ID BIGINT(12) NOT NULL COMMENT '	REFERENCES key 	TB_AST_INFO_MST ',
SERVICE_NUMBER BIGINT(20) NULL COMMENT '	X	',
SERVICE_PROVIDER VARCHAR(100) NULL COMMENT '	X	',
SERVICE_START_DATE DATE NULL COMMENT '	X	',
SERVICE_EXPIRY_DATE DATE NULL COMMENT '	X	',
SERVICE_AMOUNT BIGINT(20) NULL COMMENT '	X	',
WARRANTY BIGINT(20) NULL COMMENT '	X	',
COST_CENTER DECIMAL(15,2) NULL COMMENT '	X	',
SERVICE_CONTENT VARCHAR(100) NULL COMMENT '	X	',
SERVICE_DESCRIPTION VARCHAR(100) NULL DEFAULT NULL 	  COMMENT '	X	',
ASSESSMENT_NO BIGINT(20) NULL DEFAULT NULL 	  COMMENT '	X	',
PROPERTY_REGISTRATION_NO BIGINT(20) NULL DEFAULT NULL 	  COMMENT '	X	',
TAX_CODE BIGINT(20) NULL DEFAULT NULL 	  COMMENT '	X	',
REAL_ESTATE_AMOUNT BIGINT(20) NULL DEFAULT NULL 	  COMMENT '	X	',
TAX_ZONE_LOCATION VARCHAR(100) NULL DEFAULT NULL 	  COMMENT '	X	',
MUNICIPALITY_NAME VARCHAR(100) NULL DEFAULT NULL 	  COMMENT '	X	',
CREATED_BY BIGINT(12) NULL DEFAULT NULL 	  COMMENT '	X	',
CREATION_DATE DATETIME NULL DEFAULT NULL 	  COMMENT '	X	',
UPDATED_BY BIGINT(12)  NULL DEFAULT NULL 	  COMMENT '	X	',
UPDATED_DATE DATETIME  NULL DEFAULT NULL 	  COMMENT '	X	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL 	  COMMENT '	X	',
LG_IP_MAC_UPD VARCHAR(100)  NULL DEFAULT NULL 	  COMMENT '	X	');

--liquibase formatted sql
--changeset shamik:V20180316183026__TB_AST_SERVICE_REALESTD3.sql
ALTER TABLE TB_AST_SERVICE_REALESTD ADD CONSTRAINT PK_TB_AST_SERVICE_REALESTD PRIMARY KEY (ASSET_SERVICE_ID);


--liquibase formatted sql
--changeset shamik:V20180316183026__TB_AST_SERVICE_REALESTD4.sql
ALTER TABLE TB_AST_SERVICE_REALESTD   ADD CONSTRAINT FK_TB_AST_SERVICE_REALESTD FOREIGN KEY (ASSET_ID)
  REFERENCES TB_AST_INFO_MST (ASSET_ID);


