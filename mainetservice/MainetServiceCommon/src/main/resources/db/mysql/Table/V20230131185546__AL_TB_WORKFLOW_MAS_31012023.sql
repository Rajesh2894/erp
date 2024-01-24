--liquibase formatted sql
--changeset Kanchan:V20230131185546__AL_TB_WORKFLOW_MAS_31012023.sql
Alter table TB_WORKFLOW_MAS add column MAS_ID bigint(20) Null default null;