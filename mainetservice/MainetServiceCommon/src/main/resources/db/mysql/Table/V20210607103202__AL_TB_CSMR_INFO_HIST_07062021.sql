--liquibase formatted sql
--changeset Kanchan:V20210607103202__AL_TB_CSMR_INFO_HIST_07062021.sql
alter table TB_CSMR_INFO_HIST add column CS_RECEIPT_NUMBER  varchar(50);
