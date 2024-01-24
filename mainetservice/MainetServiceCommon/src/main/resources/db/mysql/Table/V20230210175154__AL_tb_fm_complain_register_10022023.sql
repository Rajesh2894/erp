--liquibase formatted sql
--changeset Kanchan:V20230210175154__AL_tb_fm_complain_register_10022023.sql
Alter table tb_fm_complain_register modify column assign_vehicle varchar(200) Null default null;