--liquibase formatted sql
--changeset Kanchan:V20210817094102__AL_TB_AS_NO_DUES_PROP_DETAILS_17082021.sql
ALTER TABLE `TB_AS_NO_DUES_PROP_DETAILS`  ADD COLUMN `OUTWARD_NO` VARCHAR(50) NULL default NULL;
