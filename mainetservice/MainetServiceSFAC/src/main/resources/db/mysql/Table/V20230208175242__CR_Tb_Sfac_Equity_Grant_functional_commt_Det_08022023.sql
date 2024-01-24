--liquibase formatted sql
--changeset Kanchan:V20230208175242__CR_Tb_Sfac_Equity_Grant_functional_commt_Det_08022023.sql
create table Tb_Sfac_Equity_Grant_functional_commt_Det
(
EGFC_ID bigint(20) NOT NULL,
EG_ID Bigint(20) NOT NULL,
FC_NAME Varchar(100) NULL DEFAULT NULL,
MAJOR_ACTIVITY Varchar(100) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(EGFC_ID),
KEY FK_EG_ID_idx_17 (EG_ID),
CONSTRAINT FK_EG_ID_17 FOREIGN KEY (EG_ID) REFERENCES Tb_Sfac_Equity_Grant_Mast (EG_ID)
);
--liquibase formatted sql
--changeset Kanchan:V20230208175242__CR_Tb_Sfac_Equity_Grant_functional_commt_Det_080220231.sql
create table Tb_Sfac_Equity_Grant_shareholding_Det
(
EGSH_ID bigint(20) NOT NULL,
EG_ID bigint(20) NOT NULL,
NO_OF_SH bigint(20) NULL DEFAULT NULL,
VALUE_OF_SHARE_ALLOT Decimal(15,2) NULL DEFAULT NULL,
TOTAL_AMT_PAID Decimal(15,2) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
primary key(EGSH_ID),
KEY FK_EG_ID_idx_37 (EG_ID),
CONSTRAINT FK_EG_ID_37 FOREIGN KEY (EG_ID) REFERENCES Tb_Sfac_Equity_Grant_Mast (EG_ID)
);