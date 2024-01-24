--liquibase formatted sql
--changeset Kanchan:V20210319194415__AL_TB_SCRUTINY_LABELS_19032021.sql
alter table TB_SCRUTINY_LABELS alter TRI_COD1  set default 0;
--liquibase formatted sql
--changeset Kanchan:V20210319194415__AL_TB_SCRUTINY_LABELS_190320211.sql
alter table TB_SCRUTINY_LABELS alter TRI_COD2 set default 0;
