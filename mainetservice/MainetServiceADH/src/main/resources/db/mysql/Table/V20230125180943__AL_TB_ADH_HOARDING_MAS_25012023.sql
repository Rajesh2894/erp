--liquibase formatted sql
--changeset Kanchan:V20230125180943__AL_TB_ADH_HOARDING_MAS_25012023.sql
Alter table TB_ADH_HOARDING_MAS
modify column HRD_LOCID bigint(20) null default null,
modify column HRD_ZONE1 bigint(20) null default null;