--liquibase formatted sql
--changeset Kanchan:V20221207132020__AL_tb_fm_complain_closure_07122022.sql
alter table tb_fm_complain_closure
add column PROPERTY_SAVED decimal(12,4) NULL DEFAULT NULL,
add column PROPERTY_LOST decimal(12,4) NULL DEFAULT NULL;