--liquibase formatted sql
--changeset PramodPatil:V20230922175714__AL_tb_workflow_mas_22092023.sql
alter table tb_workflow_mas drop column WF_VEH_MAIN_BY; 

--liquibase formatted sql
--changeset PramodPatil:V20230922175714__AL_tb_workflow_mas_220920231.sql
update tb_workflow_mas set WF_EXT_IDENTIFIER=0 where WF_EXT_IDENTIFIER is null;