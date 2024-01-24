--liquibase formatted sql
--changeset Kanchan:V20220726160130__AL_TB_WMS_RABILL_26072022.sql
alter table TB_WMS_RABILL add column RA_BILLTYPE char(1) null default null;