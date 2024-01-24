--liquibase formatted sql
--changeset Kanchan:V20220517164759__AL_TB_BIRTHREG_HIST_17052022.sql
alter table TB_BIRTHREG_HIST
add  BND_DW1 bigint(20),
add  BND_DW2 bigint(20),
add  BND_DW3 bigint(20),
add  BND_DW4 bigint(20),
add  BND_DW5 bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20220517164759__AL_TB_BIRTHREG_HIST_170520221.sql
alter table  tb_deathreg
add  BND_DW1 bigint(20),
add  BND_DW2 bigint(20),
add  BND_DW3 bigint(20),
add  BND_DW4 bigint(20),
add  BND_DW5 bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20220517164759__AL_TB_BIRTHREG_HIST_170520222.sql
alter table tb_deathreg_history
add  BND_DW1 bigint(20),
add  BND_DW2 bigint(20),
add  BND_DW3 bigint(20),
add  BND_DW4 bigint(20),
add  BND_DW5 bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20220517164759__AL_TB_BIRTHREG_HIST_170520223.sql
alter table tb_bd_deathreg_corr
add  BND_DW1 bigint(20),
add  BND_DW2 bigint(20),
add  BND_DW3 bigint(20),
add  BND_DW4 bigint(20),
add  BND_DW5 bigint(20) Null default null;


