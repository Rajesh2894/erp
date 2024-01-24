--liquibase formatted sql
--changeset PramodPatil:V20230816185524__AL_Tb_SFAC_Cbbo_Master_16082023.sql
Alter table Tb_SFAC_Cbbo_Master modify column SDB1 bigint(20) null default null;
Alter table Tb_SFAC_Cbbo_Master add column SDB2 bigint(20) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20230816185524__AL_Tb_SFAC_Cbbo_Master_160820231.sql
Alter table TB_SFAC_CBBO_MAST_HIST modify column SDB1 bigint(20) null default null;
Alter table TB_SFAC_CBBO_MAST_HIST add column SDB2 bigint(20) null default null;