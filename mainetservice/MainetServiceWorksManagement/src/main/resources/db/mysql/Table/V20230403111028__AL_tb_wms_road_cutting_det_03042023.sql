--liquibase formatted sql
--changeset Kanchan:V20230403111028__AL_tb_wms_road_cutting_det_03042023.sql
alter table tb_wms_road_cutting_det add RCD_AREANAME varchar(500) null default null;