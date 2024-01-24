--liquibase formatted sql
--changeset PramodPatil:V20240102121954__AL_TB_BPMS_LAND_SCH_02012024.sql
ALTER TABLE  TB_BPMS_LAND_SCH change column `SP_Acq_status_rel_CWP/SLP`  SP_Acq_status_rel_CWP_SLP varchar(45) null default null;
