--liquibase formatted sql
--changeset Kanchan:V20230524125211__CR_TB_SFAC_FARMER_LAND_DETAILS_24052023.sql
create table TB_SFAC_FARMER_LAND_DETAILS
(
LAND_ID bigint(20) NOT NULL,
FRM_ID bigint(20) NOT NULL,
LAND_TYPE Bigint(20) NULL DEFAULT NULL,
LAND_PROPORTION bigint(20) NULL DEFAULT NULL,
FRM_TYPE bigint(20) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key (LAND_ID),
KEY FK_KEY_FRM_ID_11 (FRM_ID),
CONSTRAINT FK_KEY_FRM_ID_11 FOREIGN KEY (FRM_ID) REFERENCES Tb_SFAC_Farmer_Mast (FRM_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230524125211__CR_TB_SFAC_FARMER_LAND_DETAILS_240520231.sql
create table TB_SFAC_FARMER_ON_OFF_DET
(
FRM_ON_OFF_ID bigint(20) NOT NULL,
FRM_ID bigint(20) NOT NULL,
ACTIVITY_TYPE bigint(20) NULL DEFAULT NULL,
LIVE_STOCK bigint(20) NULL DEFAULT NULL,
LIVE_STOCK_QUNTITY bigint(20) NULL DEFAULT NULL,
NO_OF_FARMERS bigint(20) NULL DEFAULT NULL,
ANNUAL_REVENUE Decimal(15,2) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key (FRM_ON_OFF_ID),
KEY FK_KEY_FRM_ID_12 (FRM_ID),
CONSTRAINT FK_KEY_FRM_ID_12 FOREIGN KEY (FRM_ID) REFERENCES Tb_SFAC_Farmer_Mast (FRM_ID)  
);
--liquibase formatted sql
--changeset Kanchan:V20230524125211__CR_TB_SFAC_FARMER_LAND_DETAILS_240520232.sql
create table TB_SFAC_FARMER_BANK_DETAILS
(
FRM_BANK_ID bigint(20) NOT NULL,
FRM_ID bigint(20) NOT NULL,
BANK_ID bigint(20) NULL DEFAULT NULL,
BANK_NAME Varchar(300) NULL DEFAULT NULL,
ACCOUNT_NO varchar(16) NULL DEFAULT NULL,
IFSC_CODE varchar(22) NULL DEFAULT NULL,
BRANCH varchar(400) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key (FRM_BANK_ID),
KEY FK_KEY_FRM_ID_13 (FRM_ID),
CONSTRAINT FK_KEY_FRM_ID_13 FOREIGN KEY (FRM_ID) REFERENCES Tb_SFAC_Farmer_Mast (FRM_ID)
);