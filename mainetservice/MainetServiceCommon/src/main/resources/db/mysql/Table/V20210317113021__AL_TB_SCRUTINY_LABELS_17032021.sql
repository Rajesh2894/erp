--liquibase formatted sql
--changeset Kanchan:V20210317113021__AL_TB_SCRUTINY_LABELS_17032021.sql
alter table TB_SCRUTINY_LABELS add column TRI_COD1 bigint(12),add TRI_COD2 bigint(12) NULL;
