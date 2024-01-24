--liquibase formatted sql
--changeset Kanchan:V20201210200043__tb_cmt_council_meeting_mast_hist_10122020.sql
alter table tb_cmt_council_meeting_mast_hist add column reason  varchar(250)null;
