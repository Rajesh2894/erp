--liquibase formatted sql
--changeset Kanchan:V20211020170101__AL_TB_AST_CLASSFCTN_20102021.sql
alter table TB_AST_CLASSFCTN add column ADDRESS varchar(500) NULL; 
--liquibase formatted sql
--changeset Kanchan:V20211020170101__AL_TB_AST_CLASSFCTN_201020211.sql
alter table TB_AST_CLASSFCTN_REV add column ADDRESS varchar(500) NULL; 
