--liquibase formatted sql
--changeset Kanchan:V20220210130037__AL_tb_vehicle_mast_10022022.sql
Alter table  tb_vehicle_mast modify column 
vehicle_purpose  varchar(255)  DEFAULT NULL;
