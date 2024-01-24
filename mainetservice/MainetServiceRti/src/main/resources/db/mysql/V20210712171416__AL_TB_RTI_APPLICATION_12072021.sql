--liquibase formatted sql
--changeset Kanchan:V20210712171416__AL_TB_RTI_APPLICATION_12072021.sql
alter table TB_RTI_APPLICATION add column district_id bigint(20) null;
