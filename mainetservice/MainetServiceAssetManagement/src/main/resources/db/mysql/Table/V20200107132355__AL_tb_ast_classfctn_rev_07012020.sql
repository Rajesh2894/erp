--liquibase formatted sql
--changeset Anil:V20200107132355__AL_tb_ast_classfctn_rev_07012020.sql
ALTER TABLE tb_ast_classfctn_rev ADD COLUMN SURVEY_NO VARCHAR(200) NULL AFTER LG_IP_MAC_UPD;
