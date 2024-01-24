--liquibase formatted sql
--changeset Kanchan:V20230203174324__AL_tb_workflow_mas_03022023.sql
alter table tb_workflow_mas change WF_VEH_MAIN_BY WF_EXT_IDENTIFIER bigint(20) DEFAULT '0';