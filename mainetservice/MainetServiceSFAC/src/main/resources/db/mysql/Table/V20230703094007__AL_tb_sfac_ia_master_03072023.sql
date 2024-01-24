--liquibase formatted sql
--changeset Kanchan:V20230703094007__AL_tb_sfac_ia_master_03072023.sql
Alter table tb_sfac_ia_master modify column AC_IN_STATUS char(1) null default 'A';
--liquibase formatted sql
--changeset Kanchan:V20230703094007__AL_tb_sfac_ia_master_030720231.sql
Alter table tb_sfac_fpo_master modify column AC_IN_STATUS char(1) null default 'A';
--liquibase formatted sql
--changeset Kanchan:V20230703094007__AL_tb_sfac_ia_master_030720232.sql
Alter table Tb_SFAC_Cbbo_Master modify column AC_IN_STATUS char(1) null default 'A';
--liquibase formatted sql
--changeset Kanchan:V20230703094007__AL_tb_sfac_ia_master_030720233.sql
Alter Table Tb_Sfac_Equity_Grant_tranche_det change column DMC_APPROVAL_DATE DATE_OF_SANCTIONED datetime Null default null;