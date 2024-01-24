--liquibase formatted sql
--changeset Anil:V20190717170719__AL_tb_newsletter_scubscription_det_17072019.sql
alter table tb_newsletter_scubscription_det add column SUB_VIEW_DATE date ;
