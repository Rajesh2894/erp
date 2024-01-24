--liquibase formatted sql
--changeset Kanchan:V20210312135320__AL_tb_as_pro_assesment_mast_12032021.sql
ALTER TABLE tb_as_pro_assesment_mast add column LOGICAL_PROP_NO varchar(50);
