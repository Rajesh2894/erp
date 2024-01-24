--liquibase formatted sql
--changeset Kanchan:V20210409180620__AL_TB_CARE_REQUEST_09042021.sql
alter table TB_CARE_REQUEST add column DISTRICT BIGINT (12) NULL ;
