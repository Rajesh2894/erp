--liquibase formatted sql
--changeset Kanchan:V20230621200842__AL_tb_adh_mas_21062023.sql
ALTER TABLE tb_adh_mas ADD ULB_STATUS CHAR(1) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230621200842__AL_tb_adh_mas_210620231.sql
ALTER TABLE tb_adh_mas_hist ADD ULB_STATUS CHAR(1) null default null;