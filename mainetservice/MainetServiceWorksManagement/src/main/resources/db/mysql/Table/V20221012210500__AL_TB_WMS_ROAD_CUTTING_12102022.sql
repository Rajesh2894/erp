--liquibase formatted sql
--changeset Kanchan:V20221012210500__AL_TB_WMS_ROAD_CUTTING_12102022.sql
alter table TB_WMS_ROAD_CUTTING add column REF_ID varchar(50) NULL;