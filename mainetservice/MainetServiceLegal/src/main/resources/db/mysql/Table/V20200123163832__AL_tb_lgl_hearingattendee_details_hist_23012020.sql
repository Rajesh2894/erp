--liquibase formatted sql
--changeset Anil:V20200123163832__AL_tb_lgl_hearingattendee_details_hist_23012020.sql
ALTER TABLE tb_lgl_hearingattendee_details_hist DROP PRIMARY KEY,
ADD PRIMARY KEY (`HRA_ID_HIS`);
