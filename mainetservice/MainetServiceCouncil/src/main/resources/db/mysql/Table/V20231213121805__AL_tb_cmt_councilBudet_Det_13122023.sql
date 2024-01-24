--liquibase formatted sql
--changeset PramodPatil:V20231213121805__AL_tb_cmt_councilBudet_Det_13122023.sql
alter table tb_cmt_councilBudet_Det add column FILED_ID bigint(12) null default null;
