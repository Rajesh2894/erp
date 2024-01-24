--liquibase formatted sql
--changeset PramodPatil:V20240108175709__AL_TB_WMS_ROAD_CUTTING_DET_08012024.sql
alter table TB_WMS_ROAD_CUTTING_DET add column RCD_ENDLATITUDE varchar(100) null default null;
