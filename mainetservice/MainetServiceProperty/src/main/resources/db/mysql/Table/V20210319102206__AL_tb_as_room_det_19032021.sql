--liquibase formatted sql
--changeset Kanchan:V20210319102206__AL_tb_as_room_det_19032021.sql
alter table tb_as_room_det change column LANG_I  LANG_ID bigint(15)  DEFAULT  NULL;
