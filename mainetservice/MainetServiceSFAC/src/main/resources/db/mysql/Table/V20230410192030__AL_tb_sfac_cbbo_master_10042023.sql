--liquibase formatted sql
--changeset Kanchan:V20230410192030__AL_tb_sfac_cbbo_master_10042023.sql
Alter table tb_sfac_cbbo_master change column APPROVED AC_IN_STATUS char(1) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230410192030__AL_tb_sfac_cbbo_master_100420231.sql
Alter table Tb_SFAC_Fpo_Master change column APPROVED_BY_CBBO AC_IN_STATUS char(1) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230410192030__AL_tb_sfac_cbbo_master_100420232.sql
Alter table TB_SFAC_CBBO_MAST_HIST change column APPROVED AC_IN_STATUS char(1) Null default null;