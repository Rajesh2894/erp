--liquibase formatted sql
--changeset nilima:V20171005172741__AL_SER_TB_ORGANISATION_04102017.sql
ALTER TABLE tb_organisation
CHANGE COLUMN APP_START_DATE TRAN_START_DATE DATETIME NULL DEFAULT NULL COMMENT 'Transaction Start Date' ;

--liquibase formatted sql
--changeset nilima:V20171005172741__AL_SER_TB_ORGANISATION_041020171.sql
update tb_organisation set TRAN_START_DATE=ESDT_DATE;
COMMIT;

--liquibase formatted sql
--changeset nilima:V20171005172741__AL_SER_TB_ORGANISATION_041020172.sql
update tb_organisation set ESDT_DATE=NULL;
COMMIT;
