--liquibase formatted sql
--changeset Anil:V20190708151425__AL_tb_newsletter_scubscription_det_08072019.sql
ALTER TABLE tb_newsletter_scubscription_det 
ADD COLUMN SUB_ID BIGINT(12) NOT NULL FIRST,
ADD PRIMARY KEY (SUB_ID);
