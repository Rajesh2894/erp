--liquibase formatted sql
--changeset Kanchan:V20211230164236__AL_TB_CFC_APPLICATION_30122021.sql
alter table TB_CFC_APPLICATION_ADDRESS modify column  APA_CITY_NAME varchar(200) NULL DEFAULT NULL;
