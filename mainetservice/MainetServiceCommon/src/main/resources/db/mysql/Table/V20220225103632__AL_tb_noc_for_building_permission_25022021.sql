--liquibase formatted sql
--changeset Kanchan:V20220225103632__AL_tb_noc_for_building_permission_25022021.sql
alter table tb_noc_for_building_permission modify column PRO_DESC varchar(1000) NULL DEFAULT NULL;
