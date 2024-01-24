--liquibase formatted sql
--changeset Kanchan:V20230613184435__AL_tb_sfac_fpo_administrative_det_13062023.sql
alter Table tb_sfac_fpo_administrative_det
add Column GENDER_ID BigInt (20) null default null after TITLE_ID ;