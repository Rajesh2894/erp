--liquibase formatted sql
--changeset Kanchan:V20230113100323__CR_Tb_Sfac_Storage_Info_Detail_13012023.sql
create table Tb_Sfac_Storage_Info_Detail			
(
STORAGE_ID bigint(20) NOT NULL,
FPM_ID Bigint(20) NOT NULL,
Commodity_Name Varchar(100) NULL DEFAULT NULL,
Storage_Name Varchar(100) NULL DEFAULT NULL,
Storage_Address Varchar(1000) NULL DEFAULT NULL,
Storage_Admin_Name Varchar(100) NULL DEFAULT NULL,
Contact_No Bigint(20) NULL DEFAULT NULL,
email Varchar(100) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(STORAGE_ID),
KEY FK_KEY_121(FPM_ID),
CONSTRAINT FK_KEY_FPM_ID_121 FOREIGN KEY (FPM_ID) REFERENCES Tb_Sfac_Fpo_Profile_Mgmt_Mast (FPM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230113100323__CR_Tb_Sfac_Storage_Info_Detail_130120231.sql
create table Tb_Sfac_Custom_Hiring_Center_Info_Detail  
(
CENTER_ID bigint(20) NOT NULL,
FPM_ID bigint(20) NOT NULL,
Owner_Name Varchar(100) NULL DEFAULT NULL,
Center_Address Varchar(1000) NULL DEFAULT NULL,
Email Varchar(100) NULL DEFAULT NULL,
Phone_No bigint(20) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(CENTER_ID),
KEY FK_KEY_122(FPM_ID),
CONSTRAINT FK_KEY_FPM_ID_122 FOREIGN KEY (FPM_ID) REFERENCES Tb_Sfac_Fpo_Profile_Mgmt_Mast (FPM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230113100323__CR_Tb_Sfac_Storage_Info_Detail_130120232.sql
create table Tb_Sfac_Custom_Hiring_Service_Info_Detail  
(
SERVICE_ID bigint(20) NOT NULL,
FPM_ID bigint(20) NOT NULL,
Rented_Item_Name Varchar(300) NULL DEFAULT NULL,
Item_Quantity bigint(20) NULL DEFAULT NULL,
Rented_From_Date datetime NULL DEFAULT NULL,
Rented_To_Date datetime NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(SERVICE_ID),
KEY FK_KEY_123(FPM_ID),
CONSTRAINT FK_KEY_FPM_ID_123 FOREIGN KEY (FPM_ID) REFERENCES Tb_Sfac_Fpo_Profile_Mgmt_Mast (FPM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230113100323__CR_Tb_Sfac_Storage_Info_Detail_130120233.sql
create table Tb_Sfac_Production_Info_Detail  
(
PRODUCTION_ID bigint(20) NOT NULL,
FPM_ID bigint(20) NOT NULL,
Commodity_Name Varchar(100) NULL DEFAULT NULL,
Item_Quantity bigint(20) NULL DEFAULT NULL,
Unit bigint(20) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(PRODUCTION_ID),
KEY FK_KEY_124(FPM_ID),
CONSTRAINT FK_KEY_FPM_ID_124 FOREIGN KEY (FPM_ID) REFERENCES Tb_Sfac_Fpo_Profile_Mgmt_Mast (FPM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230113100323__CR_Tb_Sfac_Storage_Info_Detail_130120234.sql
create table Tb_Sfac_Sale_Info_Detail  
(
SALE_ID bigint(20) NOT NULL,
FPM_ID bigint(20) NOT NULL,
Commodity_Name Varchar(200) NULL DEFAULT NULL,
Commodity_Quantity bigint(20) NULL DEFAULT NULL,
Unit bigint(20) NULL DEFAULT NULL,
Commodity_Rate Decimal(15,2) NULL DEFAULT NULL,
Commodity_Sold_Price Decimal(15,2) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(SALE_ID),
KEY FK_KEY_125(FPM_ID),
CONSTRAINT FK_KEY_FPM_ID_125 FOREIGN KEY (FPM_ID) REFERENCES Tb_Sfac_Fpo_Profile_Mgmt_Mast (FPM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230113100323__CR_Tb_Sfac_Storage_Info_Detail_130120235.sql
create table Tb_Sfac_Subsidies_Info_Detail  
(
SUBSIDIES_ID bigint(20) NOT NULL,
FPM_ID bigint(20) NOT NULL,
Scheme_Name Varchar(500) NULL DEFAULT NULL,
Subsidies_Amount Decimal(15,2) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(SUBSIDIES_ID),
KEY FK_KEY_126(FPM_ID),
CONSTRAINT FK_KEY_FPM_ID_126 FOREIGN KEY (FPM_ID) REFERENCES Tb_Sfac_Fpo_Profile_Mgmt_Mast (FPM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230113100323__CR_Tb_Sfac_Storage_Info_Detail_130120236.sql
create table Tb_Sfac_PreHarvesh_Infra_Info_Detail  
(
PHI_ID bigint(20) NOT NULL,
FPM_ID bigint(20) NOT NULL,
Storage_Type Varchar(100) NULL DEFAULT NULL,
Storage_Capicity bigint(20) NULL DEFAULT NULL,
PHP_DESC Varchar(1000) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(PHI_ID),
KEY FK_KEY_127(FPM_ID),
CONSTRAINT FK_KEY_FPM_ID_127 FOREIGN KEY (FPM_ID) REFERENCES Tb_Sfac_Fpo_Profile_Mgmt_Mast (FPM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230113100323__CR_Tb_Sfac_Storage_Info_Detail_130120237.sql
create table Tb_Sfac_PostHarvest_Infra_Info_Detail  
(
POHI_ID bigint(20) NOT NULL,
FPM_ID bigint(20) NOT NULL,
Storage_Type Varchar(100) NULL DEFAULT NULL,
Storage_Capicity bigint(20) NULL DEFAULT NULL,
PHP_DESC Varchar(1000) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(POHI_ID),
KEY FK_KEY_128(FPM_ID),
CONSTRAINT FK_KEY_FPM_ID_128 FOREIGN KEY (FPM_ID) REFERENCES Tb_Sfac_Fpo_Profile_Mgmt_Mast (FPM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230113100323__CR_Tb_Sfac_Storage_Info_Detail_130120238.sql
create table Tb_Sfac_Transport_Vehicle_Info_Detail  
(
TV_ID bigint(20) NOT NULL,
FPM_ID bigint(20) NOT NULL,
Vehicle_Type Varchar(100) NULL DEFAULT NULL,
Vehicle_Number Varchar(50) NULL DEFAULT NULL,
No_Of_Vehicle bigint(20) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(TV_ID),
KEY FK_KEY_129(FPM_ID),
CONSTRAINT FK_KEY_FPM_ID_129 FOREIGN KEY (FPM_ID) REFERENCES Tb_Sfac_Fpo_Profile_Mgmt_Mast (FPM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230113100323__CR_Tb_Sfac_Storage_Info_Detail_130120239.sql
create table Tb_Sfac_Market_Linkage_Info_Detail  
(
ML_ID bigint(20) NOT NULL,
FPM_ID bigint(20) NOT NULL,
Commodity_Name Varchar(100) NULL DEFAULT NULL,
Market_Place Varchar(200) NULL DEFAULT NULL,
Commodity_Rate Decimal(15,2) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(ML_ID),
KEY FK_KEY_120(FPM_ID),
CONSTRAINT FK_KEY_FPM_ID_120 FOREIGN KEY (FPM_ID) REFERENCES Tb_Sfac_Fpo_Profile_Mgmt_Mast (FPM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230113100323__CR_Tb_Sfac_Storage_Info_Detail_1301202310.sql
create table Tb_Sfac_Business_Plan_Info_Detail
(
BP_ID bigint(20) NOT NULL,
FPM_ID bigint(20) NOT NULL,
Doc_Desc Varchar(500) NULL DEFAULT NULL,
Doc_Name varchar(100) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(BP_ID),
KEY FK_KEY_1201(FPM_ID),
CONSTRAINT FK_KEY_FPM_ID_1201 FOREIGN KEY (FPM_ID) REFERENCES Tb_Sfac_Fpo_Profile_Mgmt_Mast (FPM_ID)
);
