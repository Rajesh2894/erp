--liquibase formatted sql
--changeset Kanchan:V20211029094854__AL_TB_RTI_APPLICATION_29102021.sql
alter table TB_RTI_APPLICATION add column RTI_APPLICANT_TYPYE	bigint(12) not null;
