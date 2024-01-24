--liquibase formatted sql
--changeset nilima:V20181201142929__CR_TB_SW_CD_WCM_30112018.sql
CREATE TABLE TB_SW_CD_WCM (
CD_ID bigint(12) NOT NULL,
CD_Month varchar(50),
CD_Year	 varchar(50) ,
CD_Ncentre varchar(200),
CD_WardNo varchar(50),
CD_NWard varchar(200),
CD_Date DATE ,
CD_NSource varchar(200) ,
CD_Tvehicle varchar(50) ,
CD_VNo varchar(50),
CD_MCapacity decimal(15,2) ,
CD_TAW	 decimal(15,2) ,
CD_AP char(1) ,
CD_NRN	varchar(200),
CD_BPSN	varchar(200),
CD_NCAO	varchar(200),
CD_NWC	varchar(200),
ORGID bigint(12) ,
CREATED_BY bigint(12) ,
CREATED_DATE datetime ,
UPDATED_BY bigint(12) ,
UPDATED_DATE datetime ,
LG_IP_MAC varchar(100) ,
LG_IP_MAC_UPD varchar(100) ,
PRIMARY KEY (CD_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




