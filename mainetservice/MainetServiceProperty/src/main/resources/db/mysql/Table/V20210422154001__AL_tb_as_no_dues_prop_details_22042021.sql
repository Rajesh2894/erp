--liquibase formatted sql
--changeset Kanchan:V20210422154001__AL_tb_as_no_dues_prop_details_22042021.sql
alter table tb_as_no_dues_prop_details add column FY_ID BIGINT (20) NULL DEFAULT NULL AFTER FLAT_NO;
