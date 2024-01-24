--liquibase formatted sql
--changeset PramodPatil: V20230724121321__AL_tb_vm_OEM_Warranty_24072023.sql 
ALTER TABLE tb_vm_OEM_Warranty MODIFY REF_NO varchar(100) null default null;
