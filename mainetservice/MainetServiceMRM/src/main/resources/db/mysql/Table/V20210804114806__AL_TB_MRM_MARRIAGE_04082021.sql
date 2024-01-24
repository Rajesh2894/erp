--liquibase formatted sql
--changeset Kanchan:V20210804114806__AL_TB_MRM_MARRIAGE_04082021.sql
alter table TB_MRM_MARRIAGE add column REG_DATE date;
--liquibase formatted sql
--changeset Kanchan:V20210804114806__AL_TB_MRM_MARRIAGE_040820211.sql
alter table TB_MRM_MARRIAGE_HIST add column REG_DATE date;
