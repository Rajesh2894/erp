--liquibase formatted sql
--changeset Kanchan:V20220825131636__AL_tb_transfer_duty_scheduling_25082022.sql
Alter table tb_transfer_duty_scheduling modify column REASON_TRANSFER varchar(500) null default null,
modify column REMARKS varchar(500) null default null;