--liquibase formatted sql
--changeset Kanchan:V20211126184630__AL_TB_SWD_SCHEME_MAST_26112021.sql
alter table TB_SWD_SCHEME_MAST add column LIFE_CERTIFICATE_REQ varchar(10) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211126184630__AL_TB_SWD_SCHEME_MAST_261120211.sql
alter table TB_SWD_SCHEME_MAST_HIST add column LIFE_CERTIFICATE_REQ varchar(10) NULL DEFAULT NULL;

