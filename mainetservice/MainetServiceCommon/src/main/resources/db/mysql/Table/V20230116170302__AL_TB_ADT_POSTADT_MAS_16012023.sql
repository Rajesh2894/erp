--liquibase formatted sql
--changeset Kanchan:V20230116170302__AL_TB_ADT_POSTADT_MAS_16012023.sql
alter table TB_ADT_POSTADT_MAS modify column AUDIT_PARA_DESC varchar(5000) NULL default null;