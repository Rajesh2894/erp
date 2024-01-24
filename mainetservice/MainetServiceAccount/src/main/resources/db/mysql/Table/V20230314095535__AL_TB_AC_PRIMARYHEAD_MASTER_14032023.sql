--liquibase formatted sql
--changeset Kanchan:V20230314095535__AL_TB_AC_PRIMARYHEAD_MASTER_14032023.sql
alter table TB_AC_PRIMARYHEAD_MASTER add column sch_code  varchar(20) NULL DEFAULT NULL;