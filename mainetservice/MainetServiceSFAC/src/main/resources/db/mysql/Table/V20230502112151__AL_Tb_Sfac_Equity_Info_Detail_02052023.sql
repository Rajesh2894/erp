--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_02052023.sql
Alter table Tb_Sfac_Equity_Info_Detail ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_020520231.sql
Alter table TB_SFAC_FPO_PROFILE_FIG_DET ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_020520232.sql
Alter table TB_SFAC_FPO_PROFILE_AGGR_DEMAND_DET ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_020520233.sql
Alter table Tb_Sfac_Management_Cost_Info_Detail ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_020520234.sql
Alter table Tb_Sfac_PostHarvest_Infra_Info_Detail ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_020520235.sql
Alter table Tb_Sfac_License_Info_Detail ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_020520236.sql
Alter table Tb_Sfac_Audited_Balance_Sheet_Info_Detail ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_020520237.sql
Alter table Tb_Sfac_Production_Info_Detail
ADD COLUMN AVL_ITEM_QTY decimal(10,2) NULL DEFAULT NULL,
 ADD COLUMN AREA_OF_PROD bigint(20) NULL DEFAULT NULL,
 ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_020520238.sql
Alter table Tb_Sfac_Sale_Info_Detail
ADD COLUMN DATE_OF_SALE date NULL DEFAULT NULL,
 ADD COLUMN TRADER_PIN bigint(20) NULL DEFAULT NULL,
 ADD COLUMN TRADER_GST varchar(20) NULL DEFAULT NULL,
 ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_020520239.sql
Alter table Tb_Sfac_Storage_Info_Detail
ADD COLUMN Storage_type varchar(100) NULL DEFAULT NULL,
ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_0205202310.sql
Alter table Tb_Sfac_Transport_Vehicle_Info_Detail
ADD COLUMN AMC decimal(15,2) NULL DEFAULT NULL,
ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_0205202311.sql
Alter table Tb_Sfac_Subsidies_Info_Detail
ADD COLUMN EST_AMT_OF_BENIFIT decimal(15,2) NULL DEFAULT NULL,
 ADD COLUMN BENIFIT_TYPE bigint(20) NULL DEFAULT NULL,
 ADD COLUMN DATE_ON_WHICH_AVAILED date NULL DEFAULT NULL,
 ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_0205202312.sql
Alter table Tb_Sfac_Market_Linkage_Info_Detail
ADD COLUMN FROM_DATE date NULL DEFAULT NULL,
 ADD COLUMN TO_DATE date NULL DEFAULT NULL,
 ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_0205202313.sql
Alter table Tb_Sfac_Equipment_Info_Detail
ADD COLUMN EQIP_TYPE bigint(20) NULL DEFAULT NULL,
ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_0205202314.sql
Alter table Tb_Sfac_Custom_Hiring_Service_Info_Detail
ADD COLUMN EQIP_TYPE bigint(20) NULL DEFAULT NULL,
 ADD COLUMN EQIP_NAME varchar(100) NULL DEFAULT NULL,
 ADD COLUMN NO_OF_EQIP bigint(20) NULL DEFAULT NULL,
 ADD COLUMN RENTED_TO varchar(100) NULL DEFAULT NULL,
 ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_0205202315.sql
Alter table Tb_Sfac_Credit_Info_Detail
ADD COLUMN TYPE_OF_LOAN bigint(20) NULL DEFAULT NULL,
 ADD COLUMN FROM_DATE date NULL DEFAULT NULL,
 ADD COLUMN TO_DATE date NULL DEFAULT NULL,
 ADD COLUMN INTEREST_RATE decimal(5,2) NULL DEFAULT NULL,
 ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL,
 ADD COLUMN DOC_NAME varchar(50) NULL DEFAULT NULL,
 ADD COLUMN DOC_STATUS varchar(10) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_0205202316.sql
Alter table Tb_Sfac_Credit_Grant_Info_Detail
ADD COLUMN TYPE_OF_LOAN bigint(20) NULL DEFAULT NULL,
ADD COLUMN LENDING_INST varchar(100) NULL DEFAULT NULL,
 ADD COLUMN STATUS varchar(50) NULL DEFAULT NULL,
 ADD COLUMN DOC_NAME varchar(50) NULL DEFAULT NULL,
 ADD COLUMN DOC_STATUS varchar(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230502112151__AL_Tb_Sfac_Equity_Info_Detail_0205202317.sql
Alter table Tb_Sfac_FPOProfile_Training_Info_Detail
ADD COLUMN TRAINING_TOPIC varchar(1000) NULL DEFAULT NULL,
 ADD COLUMN TRAINEE_ORG varchar(200) NULL DEFAULT NULL,
 ADD COLUMN TRAINER_NAME varchar(50) NULL DEFAULT NULL,
 ADD COLUMN VENUE varchar(200) NULL DEFAULT NULL,
 ADD COLUMN PINCODE bigint(20) NULL DEFAULT NULL,
 ADD COLUMN STATUS varchar(20) NULL DEFAULT NULL;