--liquibase formatted sql
--changeset Kanchan:V20230508194714__AL_TB_ADT_POSTADT_MAS_08052023.sql
alter table TB_ADT_POSTADT_MAS
add column SUB_UNIT_COMP_DONE varchar(500) null default null,
add column SUB_UNIT_COMP_PENDING varchar(500) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230508194714__AL_TB_ADT_POSTADT_MAS_080520231.sql
alter table TB_ADT_POSTADT_MAS_HIST
add column SUB_UNIT_COMP_DONE varchar(500) null default null,
add column SUB_UNIT_COMP_PENDING varchar(500) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230508194714__AL_TB_ADT_POSTADT_MAS_080520232.sql
alter table mm_expired_det modify column transactionid bigint(12) null default null ;
