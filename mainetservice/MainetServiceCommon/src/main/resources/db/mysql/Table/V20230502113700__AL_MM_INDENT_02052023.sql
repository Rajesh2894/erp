--liquibase formatted sql
--changeset Kanchan:V20230502113700__AL_MM_INDENT_02052023.sql
alter table MM_INDENT modify column Indentno varchar(50) not null;