--liquibase formatted sql
--changeset Kanchan:V20221209094239__AL_TB_SFAC_MEETING_MASTER_09122022.sql
Alter table TB_SFAC_MEETING_MASTER
add column MEETING_INVITATION_MSG Varchar(250) Null default null,
add column CONVENER_OF_MEETING bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20221209094239__AL_TB_SFAC_MEETING_MASTER_091220221.sql
Alter table TB_SFAC_MEETING_MOM
add column ACTION_OWNER bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20221209094239__AL_TB_SFAC_MEETING_MASTER_091220222.sql
Alter table TB_SFAC_MEETING_MASTER
add column TABLE_AGENDA Varchar(250) Null default null;