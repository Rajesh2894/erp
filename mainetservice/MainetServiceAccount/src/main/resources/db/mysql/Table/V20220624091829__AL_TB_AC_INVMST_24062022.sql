--liquibase formatted sql
--changeset Kanchan:V20220624091829__AL_TB_AC_INVMST_24062022.sql
alter table TB_AC_INVMST add column ACCOUNT_NUMBER bigint(20) NULL default null;