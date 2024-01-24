--liquibase formatted sql
--changeset Kanchan:V20210303205954__AL_TB_RTS_DRN_CON_03032021.sql
alter table TB_RTS_DRN_CON add column NO_OF_FLAT int(20);
