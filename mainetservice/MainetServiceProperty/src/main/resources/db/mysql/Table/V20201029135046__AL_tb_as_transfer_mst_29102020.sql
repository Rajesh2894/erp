--liquibase formatted sql
--changeset Kanchan:V20201029135046__AL_tb_as_transfer_mst_29102020.sql
alter table tb_as_transfer_mst add CERTIFICATE_NO varchar(100);
