--liquibase formatted sql
--changeset PramodPatil:V20230908130003__AL_tb_vendormaster_08092023.sql
alter table tb_vendormaster add column VM_VENDORNAME_REG varchar(400) null default null;