--liquibase formatted sql
--changeset Kanchan:V20220127113219__AL_TB_DOCUMENT_GROUP_27012022.sql
alter table TB_DOCUMENT_GROUP modify column doc_name varchar(500) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220127113219__AL_TB_DOCUMENT_GROUP_270120221.sql
alter table TB_DOCUMENT_GROUP modify column doc_name_reg varchar(500) NULL DEFAULT NULL;
