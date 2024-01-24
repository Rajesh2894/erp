--liquibase formatted sql
--changeset nilima:V20181129210057__CR_TB_SW_VECHILELOGF_29112018.sql
CREATE TABLE TB_SW_VECHILELOGF (
VEL_ID        bigint(12) NOT NULL,
VEL_SLRMCNAME varchar(500) COMMENT 'SRLM_Centre_Name',
VEL_SLRMCNO   varchar(100) COMMENT 'SLRM_Centre_No',
VEL_ADDRESS   varchar(500) COMMENT 'Address',
VE_VETYPE     varchar(100) COMMENT 'Type_of_Vehicle',
VE_NO         varchar(15)  COMMENT 'Vehicle_No',
VEL_NOWARD    bigint(3)    COMMENT 'Total_No_of_Wards',
VEL_WARDNO    varchar(100) COMMENT 'WardNo (Comma Seperated)',
VEL_NOHOUSE   bigint(3)    COMMENT 'Total_no_of_Houses',
VEL_NOCOMMA   bigint(3)    COMMENT 'Total_No_of_commercial_area',
VEL_NOESTABLI bigint(3)    COMMENT 'Total_no_of_establishments',
VEL_NOBEAT    bigint(3)    COMMENT 'Total_beats',
VEL_MONTH     varchar(3)   COMMENT 'MONTH',
VEL_EMPNAME1  varchar(500)   COMMENT 'anitation_Staff__Name_1',
VEL_EMPID1     varchar(100)   COMMENT 'anitation_Staff__Name_1',
VEL_EMPNAME2  varchar(500)   COMMENT 'anitation_Staff__Name_2',
VEL_EMPID2     varchar(100)   COMMENT 'anitation_Staff__Name_2',
VEL_EMPNAME3  varchar(500)   COMMENT 'anitation_Staff__Name_3',
VEL_EMPID3    varchar(100)   COMMENT 'anitation_Staff__Name_3',
ORGID bigint(12) ,
CREATED_BY bigint(12) ,
CREATED_DATE datetime ,
UPDATED_BY bigint(12) ,
UPDATED_DATE datetime ,
LG_IP_MAC varchar(100) ,
LG_IP_MAC_UPD varchar(100) ,
PRIMARY KEY (VEL_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

	
	



	
	

	

