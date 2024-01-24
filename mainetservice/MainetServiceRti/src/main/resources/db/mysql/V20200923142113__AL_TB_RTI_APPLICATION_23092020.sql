--liquibase formatted sql
--changeset Kanchan:V20200923142113__AL_TB_RTI_APPLICATION_23092020.sql
alter table TB_RTI_APPLICATION add column RTI_DISPATCH_NO VARCHAR(50) NULL;
--liquibase formatted sql
--changeset Kanchan:V20200923142113__AL_TB_RTI_APPLICATION_230920201.sql
alter table TB_RTI_APPLICATION add column RTI_DISPATCH_DATE DATETIME NULL;