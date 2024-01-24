--liquibase formatted sql
--changeset Kanchan:V20230203163507__AL_tb_workflow_mas_03022023.sql
alter table tb_workflow_mas add column WF_VEH_MAIN_BY bigint(20) DEFAULT '0';