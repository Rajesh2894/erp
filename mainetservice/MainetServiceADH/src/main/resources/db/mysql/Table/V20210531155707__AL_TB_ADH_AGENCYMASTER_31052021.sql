--liquibase formatted sql
--changeset Kanchan:V20210531155707__AL_TB_ADH_AGENCYMASTER_31052021.sql
alter table TB_ADH_AGENCYMASTER add column TRD_FTYPE bigint(12) NULL;
