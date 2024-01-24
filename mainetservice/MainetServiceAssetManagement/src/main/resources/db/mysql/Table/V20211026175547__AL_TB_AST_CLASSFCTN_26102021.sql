--liquibase formatted sql
--changeset Kanchan:V20211026175547__AL_TB_AST_CLASSFCTN_26102021.sql
alter table TB_AST_CLASSFCTN add column EAST  varchar(200),add WEST varchar(200),add SOUTH varchar(200),add NORTH varchar(200) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211026175547__AL_TB_AST_CLASSFCTN_261020211.sql
alter table TB_AST_CLASSFCTN_REV add column EAST  varchar(200),add WEST varchar(200),add SOUTH varchar(200),add NORTH varchar(200) NULL DEFAULT NULL;
