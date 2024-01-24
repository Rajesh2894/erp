--liquibase formatted sql
--changeset nilima:V20181129210146__CR_TB_SW_VECHILELOG1_29112018.sql
CREATE TABLE TB_SW_VECHILELOG1 (
VEL1_ID         bigint(12) NOT NULL,
VEL1_TBEATPOPU  bigint(12)    COMMENT 'Total_Population_on_beat',
VEL1_THOUSEEST  decimal(15,2) COMMENT 'Total_no_of_house_or_establishment_on_beat',
VEL1_TANICOUNT  decimal(15,2) COMMENT 'Total_animal_count_on_beat',
VEL1_THOUSEESTC decimal(15,2) COMMENT 'Total_no_of_houses_or_establishment_preparing_compost',
VEL1_MWCDRY     decimal(15,2) COMMENT 'Max_waste_capacity_of_dry_waste',
VEL1_MWCWET     decimal(15,2) COMMENT 'Max_waste_capacity_of_wet_waste',
VEL1_MWCHWZ     decimal(15,2) COMMENT 'Max_waste_capacity_of_Hazardous_waste',
ORGID bigint(12) ,
CREATED_BY bigint(12) ,
CREATED_DATE datetime ,
UPDATED_BY bigint(12) ,
UPDATED_DATE datetime ,
LG_IP_MAC varchar(100) ,
LG_IP_MAC_UPD varchar(100) ,
PRIMARY KEY (VEL1_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




	

	
	
	
	

