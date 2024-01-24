--liquibase formatted sql
--changeset Kanchan:V20230110175935__AL_TB_VM_VEREMEN_MAST_10012023.sql
alter table TB_VM_VEREMEN_MAST  add column SAC_HEAD_ID  bigint(20) null default null;