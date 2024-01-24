--liquibase formatted sql
--changeset Kanchan:V20210422105932__AL_tb_as_pro_bill_mas_22042021.sql
alter table tb_as_pro_bill_mas add column PD_FLATNO varchar(48) NULL;
