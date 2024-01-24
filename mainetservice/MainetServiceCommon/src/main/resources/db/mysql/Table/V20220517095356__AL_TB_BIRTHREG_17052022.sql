--liquibase formatted sql
--changeset Kanchan:V20220517095356__AL_TB_BIRTHREG_17052022.sql
alter table TB_BIRTHREG
add  BND_DW1 bigint(20),
add  BND_DW2 bigint(20),
add  BND_DW3 bigint(20),
add  BND_DW4 bigint(20),
add  BND_DW5 bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20220517095356__AL_TB_BIRTHREG_170520221.sql
alter table TB_BD_BIRTHREG_CORR
add  BND_DW1 bigint(20),
add  BND_DW2 bigint(20),
add  BND_DW3 bigint(20),
add  BND_DW4 bigint(20),
add  BND_DW5 bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20220517095356__AL_TB_BIRTHREG_170520222.sql
alter table TB_BD_BIRTHREG_CORR_HIST
add  BND_DW1 bigint(20),
add  BND_DW2 bigint(20),
add  BND_DW3 bigint(20),
add  BND_DW4 bigint(20),
add  BND_DW5 bigint(20) Null default null;
