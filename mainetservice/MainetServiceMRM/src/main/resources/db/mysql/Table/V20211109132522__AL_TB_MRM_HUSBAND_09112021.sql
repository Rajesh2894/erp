--liquibase formatted sql
--changeset Kanchan:V20211109132522__AL_TB_MRM_HUSBAND_09112021.sql
alter table TB_MRM_HUSBAND add column NRI varchar(5) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211109132522__AL_TB_MRM_HUSBAND_091120211.sql
alter table TB_MRM_HUSBAND_HIST add column NRI varchar(5) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211109132522__AL_TB_MRM_HUSBAND_091120212.sql
alter table TB_MRM_WIFE add column NRI varchar(5) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211109132522__AL_TB_MRM_HUSBAND_091120213.sql
alter table TB_MRM_WIFE_HIST add column NRI varchar(5) DEFAULT NULL; 
--liquibase formatted sql
--changeset Kanchan:V20211109132522__AL_TB_MRM_HUSBAND_091120214.sql
alter table TB_MRM_WITNESS_DET add column OTHER_REL varchar (100) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211109132522__AL_TB_MRM_HUSBAND_091120215.sql
alter table TB_MRM_WITNESS_DET_HIST add column OTHER_REL varchar (100) DEFAULT NULL;
