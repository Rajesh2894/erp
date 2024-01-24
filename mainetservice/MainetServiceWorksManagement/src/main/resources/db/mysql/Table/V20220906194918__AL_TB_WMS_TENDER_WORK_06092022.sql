--liquibase formatted sql
--changeset Kanchan:V20220906194918__AL_TB_WMS_TENDER_WORK_06092022.sql
alter table TB_WMS_TENDER_WORK add PR_ID bigint(12);