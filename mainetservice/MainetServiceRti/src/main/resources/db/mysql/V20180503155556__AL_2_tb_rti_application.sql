--liquibase formatted sql
--changeset harshit:V20180503155556__AL_2_tb_rti_application.sql
ALTER TABLE tb_rti_application ADD COLUMN DELIVERYREFERENCENUMBER bigint(16);