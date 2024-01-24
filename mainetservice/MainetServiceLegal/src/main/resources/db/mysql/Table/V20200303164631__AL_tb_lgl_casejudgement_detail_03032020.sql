--liquibase formatted sql
--changeset Anil:V20200303164631__AL_tb_lgl_casejudgement_detail_03032020.sql
ALTER TABLE tb_lgl_casejudgement_detail CHANGE COLUMN cjd_actiontaken cjd_actiontaken VARCHAR(1000) NULL DEFAULT NULL COMMENT 'judegement Implementation action' ;
--liquibase formatted sql
--changeset Anil:V20200303164631__AL_tb_lgl_casejudgement_detail_030320201.sql
ALTER TABLE tb_lgl_casejudgement_detail_hist CHANGE COLUMN cjd_actiontaken cjd_actiontaken VARCHAR(1000) NULL DEFAULT NULL COMMENT 'judegement Implementation action' ;
