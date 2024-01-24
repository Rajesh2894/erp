--liquibase formatted sql
--changeset shamik:V20180903191507_AL_TB_AST_PURCHASER1.sql
alter table TB_AST_PURCHASER change column MANUFACTURER MANUFACTURER varchar(100) DEFAULT NULL COMMENT '	MANUFACTURER 	info	';
--liquibase formatted sql
--changeset shamik:V20180903191507_AL_TB_AST_PURCHASER2.sql
alter table TB_AST_PURCHASER_HIST change column MANUFACTURER MANUFACTURER varchar(100) DEFAULT NULL COMMENT '	MANUFACTURER 	info	';
--liquibase formatted sql
--changeset shamik:V20180903191507_AL_TB_AST_PURCHASER3.sql
alter table TB_AST_PURCHASER_REV change column MANUFACTURER MANUFACTURER varchar(100) DEFAULT NULL COMMENT '	MANUFACTURER 	info	';
