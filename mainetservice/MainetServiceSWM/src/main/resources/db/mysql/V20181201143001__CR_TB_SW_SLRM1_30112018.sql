--liquibase formatted sql
--changeset nilima:V20181201143001__CR_TB_SW_SLRM1_30112018.sql
CREATE TABLE TB_SW_SLRM1 (
SLRM1_ID bigint(12) NOT NULL,
SLRM1_Month varchar(50),
SLRM1_Year	 varchar(50) ,
SLRM1_Ncentre varchar(200),
SLRM1_NCNO varchar(200),
CD_WardNo varchar(50),
CD_NWard varchar(200),
ORGID bigint(12) ,
CREATED_BY bigint(12) ,
CREATED_DATE datetime ,
UPDATED_BY bigint(12) ,
UPDATED_DATE datetime ,
LG_IP_MAC varchar(100) ,
LG_IP_MAC_UPD varchar(100) ,
PRIMARY KEY (SLRM1_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


