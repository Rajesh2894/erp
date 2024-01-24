--liquibase formatted sql
--changeset PramodPatil:V20231108114426__AL_TB_SFAC_MILESTONE_COMP_MASTER_08112023.sql
alter table TB_SFAC_MILESTONE_COMP_MASTER add column MILESTONE_NAM varchar(100) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231108114426__AL_TB_SFAC_MILESTONE_COMP_MASTER_081120231.sql
alter table TB_SFAC_MILESTONE_COMP_MASTER modify column MS_ID bigint(20) null default null;