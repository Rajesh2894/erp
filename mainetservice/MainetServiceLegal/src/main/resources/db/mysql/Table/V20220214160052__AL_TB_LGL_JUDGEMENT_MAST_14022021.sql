--liquibase formatted sql
--changeset Kanchan:V20220214160052__AL_TB_LGL_JUDGEMENT_MAST_14022021.sql
alter table TB_LGL_JUDGEMENT_MAST modify column JUD_BENCH_NAME varchar(500) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220214160052__AL_TB_LGL_JUDGEMENT_MAST_140220211.sql
alter table  TB_LGL_JUDGEMENT_MAST_HIST modify column JUD_BENCH_NAME varchar(500) NULL DEFAULT NULL;
