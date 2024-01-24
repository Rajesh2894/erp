--liquibase formatted sql
--changeset nilima:V20190509200505_AL_TB_LGL_CASE_MAS1.sql
alter table TB_LGL_CASE_MAS add column CSE_OFFICE_INCHARGE integer;
--liquibase formatted sql
--changeset nilima:V20190509200505_AL_TB_LGL_CASE_MAS2.sql
alter table TB_LGL_CASE_MAS add column CSE_OIC_APPOINT_DATE date;
--liquibase formatted sql
--changeset nilima:V20190509200505_AL_TB_LGL_CASE_MAS3.sql
alter table TB_LGL_CASE_MAS_HIST add column CSE_OFFICE_INCHARGE integer;
--liquibase formatted sql
--changeset nilima:V20190509200505_AL_TB_LGL_CASE_MAS4.sql
alter table TB_LGL_CASE_MAS_HIST add column CSE_OIC_APPOINT_DATE date;
--liquibase formatted sql
--changeset nilima:V20190509200505_AL_TB_LGL_CASE_MAS5.sql
alter table TB_LGL_CASE_PDDETAILS_HIST add column CSED_PARTY_TYPE integer;
--liquibase formatted sql
--changeset nilima:V20190509200505_AL_TB_LGL_CASE_MAS6.sql
alter table TB_LGL_CASE_PDDETAILS add column CSED_PARTY_TYPE integer;
