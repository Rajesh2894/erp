--liquibase formatted sql
--changeset Kanchan:V20220808144136__AL_tb_as_bill_det_hist_08082022.sql
alter table tb_as_bill_det_hist add column BM_IDNO_HIST_ID bigint(12) null default null;