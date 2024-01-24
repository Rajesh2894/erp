--liquibase formatted sql
--changeset Kanchan:V20210810124146__AL_tb_cfc_sonography_detail_10082021.sql
alter table tb_cfc_sonography_detail  modify column  FACILITIES_AT_CENTER bigint(12), modify  FACILITIES_FOR_TEST bigint(12) NULL;
