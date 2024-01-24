--liquibase formatted sql
--changeset Kanchan:V20230602165958__AL_TB_RTI_APPLICATION_02062023.sql
alter table TB_RTI_APPLICATION add column RTI_DISPATCH_EMAIL varchar(100) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230602165958__AL_TB_RTI_APPLICATION_020620231.sql
alter table TB_RTI_APPLICATION_HIST add column RTI_DISPATCH_EMAIL varchar(100) null default null;