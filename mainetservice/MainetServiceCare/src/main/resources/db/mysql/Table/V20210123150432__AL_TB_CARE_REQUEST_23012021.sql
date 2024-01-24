--liquibase formatted sql
--changeset Kanchan:V20210123150432__AL_TB_CARE_REQUEST_23012021.sql
alter table  TB_CARE_REQUEST add column RESIDENT_ID     varchar(20)    NULL;
