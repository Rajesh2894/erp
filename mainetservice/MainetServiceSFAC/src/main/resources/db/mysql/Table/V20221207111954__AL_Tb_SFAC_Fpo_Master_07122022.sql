--liquibase formatted sql
--changeset Kanchan:V20221207111954__AL_Tb_SFAC_Fpo_Master_07122022.sql
alter table Tb_SFAC_Fpo_Master
change column IA_STATE SDB1 bigint(20) NOT NULL,
change column IA_DIST SDB2 bigint(20) NOT NULL,
change column IA_BLOCK SDB3 bigint(20) NOT NULL;