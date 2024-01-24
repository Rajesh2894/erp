--liquibase formatted sql
--changeset Kanchan:V20220221122118__AL_tb_noc_for_building_permission_21022022.sql
alter table tb_noc_for_building_permission
add column MALABA_CHARGE bigint(5) NULL DEFAULT NULL,
add column PRO_DESC varchar(100) NULL DEFAULT NULL,
add column SALE_DEED_DATE DATE NULL DEFAULT NULL;
