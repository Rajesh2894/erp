--liquibase formatted sql
--changeset Kanchan:V20210427191057__AL_tb_as_transfer_owner_dtl_27042021.sql
alter table tb_as_transfer_owner_dtl add column owner_name_reg varchar(500)default null;
