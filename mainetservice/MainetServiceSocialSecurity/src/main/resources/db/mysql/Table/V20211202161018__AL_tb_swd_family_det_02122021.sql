--liquibase formatted sql
--changeset Kanchan:V20211202161018__AL_tb_swd_family_det_02122021.sql
alter table tb_swd_family_det change column ORGID  SAPI_ID bigint(12) NOT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211202161018__AL_tb_swd_family_det_021220211.sql
alter table tb_swd_family_det add column ORGID bigint(12) NOT NULL;
