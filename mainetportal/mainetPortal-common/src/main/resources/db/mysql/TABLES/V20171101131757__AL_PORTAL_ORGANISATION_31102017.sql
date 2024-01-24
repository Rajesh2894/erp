--liquibase formatted sql
--changeset nilima:V20171101131757__AL_PORTAL_ORGANISATION_31102017.sql
ALTER TABLE tb_organisation
CHANGE COLUMN APP_START_DATE TRAN_START_DATE DATETIME NULL DEFAULT NULL COMMENT 'Transaction Start Date' ;

--liquibase formatted sql
--changeset nilima:V20171101131757__AL_PORTAL_ORGANISATION_311020171.sql
update tb_organisation set TRAN_START_DATE=ESDT_DATE;
COMMIT;

--liquibase formatted sql
--changeset nilima:V20171101131757__AL_PORTAL_ORGANISATION_311020172.sql
update tb_organisation set ESDT_DATE=NULL;
COMMIT;