--liquibase formatted sql
--changeset shamik:V20180720200521_TB_AST_ERROR1.sql
DROP TABLE IF EXISTS TB_AST_ERROR;

--liquibase formatted sql
--changeset shamik:V20180720200521_TB_AST_ERROR2.sql
CREATE TABLE TB_AST_ERROR(
ERR_ID BIGINT(12),
ERR_DATA VARCHAR(500) NULL DEFAULT NULL COMMENT '	X	',
ERR_DESCRIPTION VARCHAR(500) NOT NULL COMMENT '	X	',
FILE_NAME VARCHAR(500) NULL DEFAULT NULL COMMENT '	X	',
AST_TYPE VARCHAR(500) NULL DEFAULT NULL COMMENT '	X	',
ORGID BIGINT(12) NOT NULL COMMENT '	X	',
CREATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT '	X	',
CREATION_DATE DATETIME NULL DEFAULT NULL COMMENT '	X	',
UPDATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT '	X	',
UPDATED_DATE DATETIME NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	',
LG_IP_MAC_UPD VARCHAR(100) NULL DEFAULT NULL COMMENT '	X	'
);


--liquibase formatted sql
--changeset shamik:V20180720200521_TB_AST_ERROR3.sql
ALTER TABLE TB_AST_ERROR ADD CONSTRAINT PK_TB_AST_ERROR PRIMARY KEY (ERR_ID);
