--liquibase formatted sql
--changeset PramodPatil: V20230720195203__AL_TB_SFAC_MILESTONE_COMP_FPO_DET_20072023.sql
Alter table TB_SFAC_MILESTONE_COMP_FPO_DET add column STATUS varchar(10) Null Default 'A';

--liquibase formatted sql
--changeset PramodPatil: V20230720195203__AL_TB_SFAC_MILESTONE_COMP_FPO_DET_200720231.sql
Alter table TB_SFAC_MILESTONE_DELIVERABLES_DET add column STATUS varchar(10) Null Default 'A';

--liquibase formatted sql
--changeset PramodPatil: V20230720195203__AL_TB_SFAC_MILESTONE_COMP_FPO_DET_200720232.sql
Alter table TB_SFAC_MILESTONE_CBBO_DET add column STATUS varchar(10) Null Default 'A';