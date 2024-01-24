--liquibase formatted sql
--changeset PramodPatil:V20240102122017__AL_tb_cmt_councilBudet_Det_02012024.sql
alter table tb_cmt_councilBudet_Det add column PROPOSAL_DEP_ID bigint(12)  null default null;
