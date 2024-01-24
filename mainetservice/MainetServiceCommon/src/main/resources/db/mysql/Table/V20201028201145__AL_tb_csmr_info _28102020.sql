--liquibase formatted sql
--changeset Kanchan:V20201028201145__AL_tb_csmr_info _28102020.sql
alter table tb_csmr_info add  ARV decimal(15,2) NULL,add PTIN varchar(40) NULL;
