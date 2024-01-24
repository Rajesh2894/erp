--liquibase formatted sql
--changeset Kanchan:V20221114100808__AL_tb_as_bill_det_14112022.sql
alter table tb_as_bill_det add COLUMN APM_APPLICATION_ID bigint(16) DEFAULT null;