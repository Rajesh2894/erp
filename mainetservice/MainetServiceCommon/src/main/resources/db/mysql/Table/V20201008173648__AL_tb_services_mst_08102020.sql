--liquibase formatted sql
--changeset Kanchan:V20201008173648__AL_tb_services_mst_08102020.sql
alter table tb_services_mst  add  column  SM_CHECKLIST_REQ  char(1)  DEFAULT  'Y' COMMENT 'Checklist verification required';
--liquibase formatted sql
--changeset Kanchan:V20201008173648__AL_tb_services_mst_081020201.sql
alter table tb_services_mst_hist  add  column  SM_CHECKLIST_REQ  char(1)  DEFAULT  'Y' COMMENT 'Checklist verification required';
