--liquibase formatted sql
--changeset PramodPatil:V20231102163356__AL_tb_transfer_duty_scheduling_hist_02112023.sql
alter table tb_transfer_duty_scheduling_hist modify column REASON_TRANSFER varchar(500) null default null;