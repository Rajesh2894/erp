--liquibase formatted sql
--changeset Kanchan:V20211102112914__AL_tb_fm_complain_register_02112021.sql
alter table tb_fm_complain_register add column call_attended_by varchar(200) NULL DEFAULT NULL; 
--liquibase formatted sql
--changeset Kanchan:V20211102112914__AL_tb_fm_complain_register_021120211.sql
alter  table tb_fm_complain_register add column recorded_by varchar(200) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211102112914__AL_tb_fm_complain_register_021120212.sql
alter  table tb_fm_complain_register add column call_forwarded varchar(200)  NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211102112914__AL_tb_fm_complain_register_021120213.sql
alter  table tb_fm_complain_register_hist add column call_attended_by varchar(200) NULL DEFAULT NULL; 
--liquibase formatted sql
--changeset Kanchan:V20211102112914__AL_tb_fm_complain_register_021120214.sql
alter  table tb_fm_complain_register_hist add column recorded_by varchar(200)   NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211102112914__AL_tb_fm_complain_register_021120215.sql
alter  table tb_fm_complain_register_hist add column call_forwarded varchar(200)  NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211102112914__AL_tb_fm_complain_register_021120216.sql
alter  table tb_fm_complain_register  modify column incident_desc varchar(600) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211102112914__AL_tb_fm_complain_register_021120217.sql
alter  table tb_fm_complain_register_hist  modify column incident_desc varchar(600) NULL DEFAULT NULL;
