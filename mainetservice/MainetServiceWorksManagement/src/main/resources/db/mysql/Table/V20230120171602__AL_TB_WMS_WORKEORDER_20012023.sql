--liquibase formatted sql
--changeset Kanchan:V20230120171602__AL_TB_WMS_WORKEORDER_20012023.sql
alter table TB_WMS_WORKEORDER add column COMPLETION_DATE datetime NULL DEFAULT NULL;