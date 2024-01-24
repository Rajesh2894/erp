--liquibase formatted sql
--changeset Kanchan:V20220328230501__AL_TB_WMS_WORKDEFINATION_28032022.sql
alter table TB_WMS_WORKDEFINATION
add column LATITUDE varchar(100),add 
 LONGITUDE varchar(100) NULL DEFAULT NULL;
