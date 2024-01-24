--liquibase formatted sql
--changeset nilima:V20170426124714__AL_TB_WT_EXCEPTION_MGAP_MGAP_REMARK.sql
alter table TB_WT_EXCEPTION_MGAP add mgap_remark NVARCHAR2(500);
--changeset nilima:V20170426124714__AL_TB_WT_EXCEPTION_MGAP_MGAP_REMARK1.sql
comment on column TB_WT_EXCEPTION_MGAP.mgap_remark
  is 'Remark for Exception';
