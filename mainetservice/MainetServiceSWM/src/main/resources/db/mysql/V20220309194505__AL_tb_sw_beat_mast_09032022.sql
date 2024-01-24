--liquibase formatted sql
--changeset Kanchan:V20220309194505__AL_tb_sw_beat_mast_09032022.sql
Alter table  tb_sw_beat_mast modify column  BEAT_NAME VARCHAR(500) DEFAULT NULL;
