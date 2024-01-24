--liquibase formatted sql
--changeset Kanchan:V20220304151426__AL_tb_hearing_mas_04032022.sql
Alter table  tb_hearing_mas  add  column  DSGID bigint(19) DEFAULT NULL;
