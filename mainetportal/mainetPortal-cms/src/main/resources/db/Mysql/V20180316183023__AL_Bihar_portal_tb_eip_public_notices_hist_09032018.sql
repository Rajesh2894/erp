--liquibase formatted sql
--changeset shamik:V20180316183023__AL_Bihar_portal_tb_eip_public_notices_hist_090320181.sql
ALTER TABLE tb_eip_public_notices_hist 
DROP COLUMN LANG_ID,
CHANGE COLUMN USER_ID CREATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT 'User Id' ,
ADD COLUMN NOTICE_TITLE VARCHAR(45) NULL DEFAULT NULL COMMENT '' AFTER PN_ID,
ADD COLUMN LINKTYPE CHAR(1) NULL DEFAULT NULL COMMENT '' AFTER DETAIL_REG,
ADD COLUMN LINK VARCHAR(500) NULL DEFAULT NULL COMMENT '' AFTER LINKTYPE,
ADD COLUMN IMAGEPATH VARCHAR(500) NULL DEFAULT NULL COMMENT '' AFTER ISSUE_DATE;

--liquibase formatted sql
--changeset shamik:V20180316183023__AL_Bihar_portal_tb_eip_public_notices_hist_090320182.sql
ALTER TABLE tb_eip_public_notices_hist 
ADD COLUMN ISHIGHLIGHTED CHAR(1) NULL DEFAULT 'N' COMMENT '' AFTER H_STATUS;
