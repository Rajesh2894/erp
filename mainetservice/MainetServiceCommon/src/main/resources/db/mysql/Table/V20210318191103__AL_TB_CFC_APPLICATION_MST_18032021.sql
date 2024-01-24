--liquibase formatted sql
--changeset Kanchan:V20210318191103__AL_TB_CFC_APPLICATION_MST_18032021.sql
ALTER Table  TB_CFC_APPLICATION_MST add column ISSUED_BY  BIGINT(12),
add ISSUED_DATE datetime NULL;

--liquibase formatted sql
--changeset Kanchan:V20210318191103__AL_TB_CFC_APPLICATION_MST_180320211.sql
ALTER Table TB_DEP_COMPLAINT_SUBTYPE add column
 OTP_VALID_REQ varchar(5) NULL;
