--liquibase formatted sql
--changeset Kanchan:V20230118182027__CR_tb_sfac_fpo_mgmt_cost_mast_18012023.sql
create table tb_sfac_fpo_mgmt_cost_mast
(
FMC_ID bigint(20) NOT NULL,
FPO_ID bigint(20) NOT NULL,
CBBO_ID bigint(20) NOT NULL,
IA_ID bigint(20) NOT NULL,
APP_NO Varchar(1000) NOT NULL,
FINANCIAL_YEAR bigint(20) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(FMC_ID),
KEY FK_KEY_FPO_ID1(FPO_ID),
KEY FK_KEY_CBBO_ID1(CBBO_ID),
KEY FK_KEY_IA_ID1(IA_ID),
CONSTRAINT FK_KEY_FPO_ID_1 FOREIGN KEY (FPO_ID) REFERENCES Tb_SFAC_Fpo_Master (FPO_ID),
CONSTRAINT FK_KEY_CBBO_ID_1 FOREIGN KEY (CBBO_ID) REFERENCES Tb_SFAC_Cbbo_Master (CBBO_ID),
CONSTRAINT FK_KEY_IA_ID_1 FOREIGN KEY (IA_ID) REFERENCES TB_SFAC_IA_Master (IA_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230118182027__CR_tb_sfac_fpo_mgmt_cost_mast_180120231.sql
create table tb_sfac_fpo_mgmt_cost_mast_det
(
FMCC_ID bigint(20) NOT NULL,
FMC_ID bigint(20) NOT NULL,
PARTICULARS bigint(20) NULL DEFAULT NULL,
MGMT_COST_EXPECTED Decimal(15,2) NULL DEFAULT NULL,
MGMT_COST_INCURRED Decimal(15,2) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(FMCC_ID),
KEY FK_KEY_FMC_ID1(FMC_ID),
CONSTRAINT FK_KEY_FMC_ID_1 FOREIGN KEY (FMC_ID) REFERENCES tb_sfac_fpo_mgmt_cost_mast (FMC_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230118182027__CR_tb_sfac_fpo_mgmt_cost_mast_180120232.sql
create table tb_sfac_fpo_mgmt_cost_doc_det
(
DOC_ID bigint(20) NOT NULL,
FMC_ID bigint(20) NOT NULL,
DOC_DESC Varchar(300) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(DOC_ID),
KEY FK_KEY_FMC_ID2(FMC_ID),
CONSTRAINT FK_KEY_FMC_ID_2 FOREIGN KEY (FMC_ID) REFERENCES tb_sfac_fpo_mgmt_cost_mast (FMC_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230118182027__CR_tb_sfac_fpo_mgmt_cost_mast_180120233.sql
create table Tb_Sfac_Audited_Balance_Sheet_Info_Detail
(
ABS_ID bigint(20) NOT NULL,
FPM_ID bigint(20) NOT NULL,
FINANCIAL_YEAR bigint(20) NULL DEFAULT NULL,
Doc_Name Varchar(200) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(ABS_ID),
KEY FK_KEY_FPM_ID2(FPM_ID),
CONSTRAINT FK_KEY_FPM_ID_2 FOREIGN KEY (FPM_ID) REFERENCES Tb_Sfac_Fpo_Profile_Mgmt_Mast (FPM_ID)
);