--liquibase formatted sql
--changeset PramodPatil:V20230929132824__CR_Tb_Sfac_Equity_Grant_shareholding_Det_Hist_29092023.sql
create Table Tb_Sfac_Equity_Grant_shareholding_Det_Hist
(
EGSH_ID_H bigint(20) NOT NULL,
EG_ID_H bigint(20) NOT NULL,
EGSH_ID bigint(20) NOT NULL,
NO_OF_SH bigint(20) NULL DEFAULT NULL,
VALUE_OF_SHARE_ALLOT decimal(15,2) NULL DEFAULT NULL,
TOTAL_AMT_PAID decimal(15,2) NULL DEFAULT NULL,
ORGID bigint(20) NOT NULL,
CREATED_BY bigint(20) NOT NULL,
CREATED_DATE datetime NOT NULL,
UPDATED_BY bigint(20) NULL DEFAULT NULL,
UPDATED_DATE datetime NULL DEFAULT NULL,
LG_IP_MAC varchar(100) NOT NULL,
LG_IP_MAC_UPD varchar(100) NULL DEFAULT NULL,
STATUS char(1) NULL DEFAULT NULL,
H_STATUS char(1)  NULL DEFAULT NULL,
primary key (EGSH_ID_H),
KEY FK_KEY_EG_ID_H3 (EG_ID_H),
CONSTRAINT FK_KEY_EG_ID_H3 FOREIGN KEY (EG_ID_H) REFERENCES tb_sfac_equity_grant_mast_hist (EG_ID_H)
);
