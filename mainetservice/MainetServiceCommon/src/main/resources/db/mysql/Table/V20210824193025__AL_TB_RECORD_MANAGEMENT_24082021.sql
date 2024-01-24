--liquibase formatted sql
--changeset Kanchan:V20210824193025__AL_TB_RECORD_MANAGEMENT_24082021.sql
alter table TB_RECORD_MANAGEMENT  add column DEL_DOC_ACTION_DT DATETIME NULL;
--liquibase formatted sql
--changeset Kanchan:V20210824193025__AL_TB_RECORD_MANAGEMENT_240820211.sql
alter table TB_RECORD_MANAGEMENT  add column RET_DOC_ACTION_DT DATETIME NULL;
