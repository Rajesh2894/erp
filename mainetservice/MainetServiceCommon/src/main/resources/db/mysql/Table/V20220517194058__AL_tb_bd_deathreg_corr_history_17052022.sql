--liquibase formatted sql
--changeset Kanchan:V20220517194058__AL_tb_bd_deathreg_corr_history_17052022.sql
alter table tb_bd_deathreg_corr_history

add column BND_DW1 bigint(20),

add column BND_DW2 bigint(20),

add column BND_DW3 bigint(20),

add column BND_DW4 bigint(20),

add column BND_DW5 bigint(20) Null default null;
