--liquibase formatted sql
--changeset Kanchan:V20210414162035__AL_TB_LGL_CASEPARAWISE_REMARK_14042021.sql
alter table TB_LGL_CASEPARAWISE_REMARK add column  Ref_case_no  varchar(20)  Null;
--liquibase formatted sql
--changeset Kanchan:V20210414162035__AL_TB_LGL_CASEPARAWISE_REMARK_140420211.sql
alter table tb_lgl_caseparawise_remark_hist add column Ref_case_no  varchar(20)  Null;
