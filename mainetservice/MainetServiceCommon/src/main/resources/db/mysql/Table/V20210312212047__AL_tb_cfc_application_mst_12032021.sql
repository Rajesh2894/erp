--liquibase formatted sql
--changeset Kanchan:V20210312212047__AL_tb_cfc_application_mst_12032021.sql
alter table tb_cfc_application_mst modify column APM_APPLICATION_ID bigint(20);
--liquibase formatted sql
--changeset Kanchan:V20210312212047__AL_tb_cfc_application_mst_120320211.sql
alter table tb_workflow_request modify column APM_APPLICATION_ID bigint(20);
--liquibase formatted sql
--changeset Kanchan:V20210312212047__AL_tb_cfc_application_mst_120320212.sql
alter table tb_workflow_action modify column APM_APPLICATION_ID bigint(20);
--liquibase formatted sql
--changeset Kanchan:V20210312212047__AL_tb_cfc_application_mst_120320213.sql
alter table tb_workflow_task modify column APM_APPLICATION_ID bigint(20);

