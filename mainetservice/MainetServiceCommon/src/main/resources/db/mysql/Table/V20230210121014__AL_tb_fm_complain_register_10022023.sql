--liquibase formatted sql
--changeset Kanchan:V20230210121014__AL_tb_fm_complain_register_10022023.sql
alter table tb_fm_complain_register modify time time NULL DEFAULT NULL;