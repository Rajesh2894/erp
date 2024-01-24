--liquibase formatted sql
--changeset Kanchan:V20201222191358__AL_TB_LGL_COMNT_REVW_DTL_22122020.sql
alter table TB_LGL_COMNT_REVW_DTL add column  HR_ID BIGINT(12) not null;
--liquibase formatted sql
--changeset Kanchan:V20201222191358__AL_TB_LGL_COMNT_REVW_DTL_221220201.sql
alter table TB_LGL_COMNT_REVW_DTL_HIST add column  HR_ID BIGINT(12) not null;
--liquibase formatted sql
--changeset Kanchan:V20201222191358__AL_TB_LGL_COMNT_REVW_DTL_221220202.sql
alter table TB_LGL_COMNT_REVW_DTL add column HR_DATE DATE  not null;
--liquibase formatted sql
--changeset Kanchan:V20201222191358__AL_TB_LGL_COMNT_REVW_DTL_221220203.sql
alter table TB_LGL_COMNT_REVW_DTL_HIST add column HR_DATE DATE not null;
--liquibase formatted sql
--changeset Kanchan:V20201222191358__AL_TB_LGL_COMNT_REVW_DTL_221220204.sql
alter table TB_LGL_COMNT_REVW_DTL add column ACTIVE_STATUS CHAR(1) null;
 














