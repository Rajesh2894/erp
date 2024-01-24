--liquibase formatted sql
--changeset Kanchan:V20210607104828__AL_tb_as_excess_amt_07062021.sql
alter table tb_as_excess_amt add column pd_flatno varchar(12) NULL;
