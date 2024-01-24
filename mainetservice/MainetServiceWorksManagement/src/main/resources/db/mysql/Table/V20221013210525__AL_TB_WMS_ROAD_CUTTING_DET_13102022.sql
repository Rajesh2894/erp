--liquibase formatted sql
--changeset Kanchan:V20221013210525__AL_TB_WMS_ROAD_CUTTING_DET_13102022.sql
alter table TB_WMS_ROAD_CUTTING_DET add column REF_ID varchar(50) NULL;