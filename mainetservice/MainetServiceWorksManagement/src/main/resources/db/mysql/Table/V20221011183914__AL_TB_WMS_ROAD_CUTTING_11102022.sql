--liquibase formatted sql
--changeset Kanchan:V20221011183914__AL_TB_WMS_ROAD_CUTTING_11102022.sql
alter table TB_WMS_ROAD_CUTTING add column PURPOSE_ID BIGINT(10) NULL;
--liquibase formatted sql
--changeset Kanchan:V20221011183914__AL_TB_WMS_ROAD_CUTTING_111020221.sql
alter table TB_WMS_ROAD_CUTTING add column PURPOSE_VALUE varchar(2) NULL;