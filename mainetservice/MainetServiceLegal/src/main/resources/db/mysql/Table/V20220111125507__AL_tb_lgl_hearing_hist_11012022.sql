--liquibase formatted sql
--changeset Kanchan:V20220111125507__AL_tb_lgl_hearing_hist_11012022.sql
alter table tb_lgl_hearing_hist modify column hr_preparation varchar(1000) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220111125507__AL_tb_lgl_hearing_hist_110120221.sql
alter table TB_LGL_HEARING modify column hr_preparation varchar(1000) DEFAULT NULL;
