--liquibase formatted sql
--changeset Kanchan:V20230131181540__AL_TB_ADT_POSTADT_MAS_31012023.sql
Alter table TB_ADT_POSTADT_MAS add column SUB_UNIT_CLOSED INT(2) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230131181540__AL_TB_ADT_POSTADT_MAS_310120231.sql
Alter table TB_ADT_POSTADT_MAS add column SUB_UNIT INT(2) null default null;