--liquibase formatted sql
--changeset Anil:V20200304122102__AL_tb_res_configmaster_04032020.sql
ALTER TABLE tb_res_configmaster CHANGE COLUMN page_id page_id VARCHAR(50) NOT NULL COMMENT 'page id' ;
--liquibase formatted sql
--changeset Anil:V20200304122102__AL_tb_res_configmaster_040320201.sql
ALTER TABLE tb_res_configdet CHANGE COLUMN field_id field_id VARCHAR(50) NOT NULL ;
