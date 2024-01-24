--liquibase formatted sql
--changeset Kanchan:V20210302122119__AL_TB_ADH_LICENSE_VALIDATITY_MASTER_02032021.sql
alter table TB_ADH_LICENSE_VALIDATITY_MASTER add column TRI_COD1 bigint(12),add TRI_COD2 bigint(12) null;
