--liquibase formatted sql
--changeset Kanchan:V20230207170448__AL_Tb_Sfac_Custom_Hiring_Service_Info_Detail_07022023.sql
Alter table Tb_Sfac_Custom_Hiring_Service_Info_Detail ADD COLUMN RENTED_AMT decimal(15,2) null DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230207170448__AL_Tb_Sfac_Custom_Hiring_Service_Info_Detail_070220231.sql
Alter table Tb_Sfac_Sale_Info_Detail
ADD COLUMN REVN_GEN decimal(15,2) null DEFAULT NULL,
ADD COLUMN MANDI_NAME varchar(200) null DEFAULT NULL,
ADD COLUMN MANDI_ADD varchar(500) null DEFAULT NULL,
ADD COLUMN NAME_IF_TRADER varchar(100) null DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230207170448__AL_Tb_Sfac_Custom_Hiring_Service_Info_Detail_070220232.sql
Alter table Tb_Sfac_Subsidies_Info_Detail
ADD COLUMN SCHEME_AGENCY varchar(200) null DEFAULT NULL,
ADD COLUMN TOTAL_AMT decimal(15,2) null DEFAULT NULL,
ADD COLUMN AMT_PAID_BY_FARMER decimal(15,2) null DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230207170448__AL_Tb_Sfac_Custom_Hiring_Service_Info_Detail_070220233.sql
Alter table Tb_Sfac_PostHarvest_Infra_Info_Detail
ADD COLUMN PROCESSING varchar(500) null DEFAULT NULL,
ADD COLUMN QUALITY_ANALYSIS varchar(500) null DEFAULT NULL;