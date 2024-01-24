--liquibase formatted sql
--changeset Kanchan:V20230127185308__AL_tb_workflow_action_27012023.sql
alter table tb_workflow_action modify column COMMENTS varchar(6000);
--liquibase formatted sql
--changeset Kanchan:V20230127185308__AL_tb_workflow_action_270120231.sql
alter table tb_care_request modify column COMPLAINT_DESC varchar(6000);