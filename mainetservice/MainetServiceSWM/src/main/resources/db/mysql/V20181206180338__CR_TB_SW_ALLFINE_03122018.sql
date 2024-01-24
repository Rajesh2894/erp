--liquibase formatted sql
--changeset nilima:V20181206180338__CR_TB_SW_ALLFINE_03122018.sql
CREATE TABLE TB_SW_ALLFINE
(ALLF_ID bigint(12) comment 'primary key',
 ALLF_MONTH varchar(50) comment 'Fine_information_for_month',	
 ALLF_YEAR  varchar(50) comment 'Fine_information_for_year',		
 ALLF_Date  date comment 'Fine_collection_Date	',			
 ALLF_APPNAME varchar(600) comment 'Name',
 ALLF_APPADDRESS varchar(1000) comment 'Address',
 ALLF_APPMOBNO varchar(10) comment 'Mobile_No',
 ALLF_WardNo varchar(50) comment 'Ward_No',
 ALLF_FTYPE varchar(50) comment 'Fine_type',
 ALLF_FSTYPE varchar(50) comment 'Fine_subtype',
 ALLF_FRBOOKNO varchar(100) comment 'Reciept_book_No',	
 ALLF_FTOAMT decimal(20,2) comment 'total_Amount',
 ALLF_FRESFIBE varchar(1000) comment 'Reason_for_fine',		
ORGID bigint(12) ,
CREATED_BY bigint(12) ,
CREATED_DATE datetime ,
UPDATED_BY bigint(12) ,
UPDATED_DATE datetime ,
LG_IP_MAC varchar(100) ,
LG_IP_MAC_UPD varchar(100) ,
PRIMARY KEY (ALLF_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	
	
	




