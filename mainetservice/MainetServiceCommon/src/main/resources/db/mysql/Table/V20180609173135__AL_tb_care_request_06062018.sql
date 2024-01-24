--liquibase formatted sql
--changeset nilima:V20180609173135__AL_tb_care_request_06062018.sql
ALTER TABLE tb_care_request
ADD COLUMN LANDMARK VARCHAR(200) NULL AFTER `LAST_DATE_OF_ACTION`;



