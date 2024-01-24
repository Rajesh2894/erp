--liquibase formatted sql
--changeset Kanchan:V20230405124318__AL_Tb_SFAC_Fpo_Master_05042023.sql
Alter table Tb_SFAC_Fpo_Master add column ACK_NUMBER bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230405124318__AL_Tb_SFAC_Fpo_Master_050420231.sql
Alter table TB_SFAC_FPO_MASTER_HIST Add column AC_IN_STATUS char(1) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230405124318__AL_Tb_SFAC_Fpo_Master_050420232.sql
Alter table TB_SFAC_IA_Master add column AC_IN_STATUS char(1) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230405124318__AL_Tb_SFAC_Fpo_Master_050420233.sql
Alter table TB_SFAC_IA_MAST_HIST add column AC_IN_STATUS char(1) Null default null;