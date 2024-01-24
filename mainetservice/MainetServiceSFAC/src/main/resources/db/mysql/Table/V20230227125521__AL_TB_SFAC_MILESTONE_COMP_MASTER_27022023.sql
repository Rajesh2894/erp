--liquibase formatted sql
--changeset Kanchan:V20230227125521__AL_TB_SFAC_MILESTONE_COMP_MASTER_27022023.sql
Alter table TB_SFAC_MILESTONE_COMP_MASTER ADD Column CBBO_ID bigint(20) not null ;
--liquibase formatted sql
--changeset Kanchan:V20230227125521__AL_TB_SFAC_MILESTONE_COMP_MASTER_270220231.sql
Alter table TB_SFAC_MILESTONE_COMP_MASTER ADD Column INVOICE_AMT decimal(15,2) null default null;