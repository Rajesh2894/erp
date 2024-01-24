--liquibase formatted sql
--changeset Kanchan:V20210312222804__AL_tb_mtl_inspection_reg_12032021.sql
alter table tb_mtl_inspection_reg modify column IN_NO bigint(12);
