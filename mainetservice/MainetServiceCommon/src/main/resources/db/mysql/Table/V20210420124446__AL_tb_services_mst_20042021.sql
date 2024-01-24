--liquibase formatted sql
--changeset Kanchan:V20210420124446__AL_tb_services_mst_20042021.sql
alter table tb_services_mst add column SM_EXTERNAL_SERVICE_FLAG varchar(2)  NULL;
