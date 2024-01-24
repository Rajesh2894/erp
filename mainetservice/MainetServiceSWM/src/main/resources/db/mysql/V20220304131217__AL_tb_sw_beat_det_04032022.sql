--liquibase formatted sql
--changeset Kanchan:V20220304131217__AL_tb_sw_beat_det_04032022.sql
alter table tb_sw_beat_det modify column BEAT_ARE_NAME VARCHAR(500) DEFAULT NULL;
