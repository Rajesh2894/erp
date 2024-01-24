--liquibase formatted sql
--changeset Kanchan:V20201222193639__AL_tb_as_assesment_mast_22122020.sql
alter table tb_as_assesment_mast add column group_prop_no varchar(20),add group_prop_name varchar(500), add parent_prop_no varchar(20), add parent_prop_name varchar(500),add  is_group  varchar(1) null;
--liquibase formatted sql
--changeset Kanchan:V20201222193639__AL_tb_as_assesment_mast_221220201.sql
alter table tb_as_pro_assesment_mast add column group_prop_no varchar(20),add group_prop_name varchar(500), add parent_prop_no varchar(20), add parent_prop_name varchar(500),add is_group  varchar(1)  null;



