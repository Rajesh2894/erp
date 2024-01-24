--liquibase formatted sql
--changeset nilima:V20181206180428__CR_TB_SW_UCHC_03122018.sql
CREATE TABLE TB_SW_UCHC
(UCHC_ID bigint(12) comment 'primary key',
UCHC_MONTH varchar(50) comment 'Consumer_charges_information_for_month',	
UCHC_YEAR varchar(50) comment 'Consumer_charges_information_for_year',	
UCHC_DATE Date comment 'Collection_date',	
UCHC_CNAME varchar(200) comment 'Consumer_name',	
UCHC_CADD varchar(500) comment 'Consumer_address',	
UCHC_CMOBILENO varchar(100) comment 'Consumer_mobile_no',	
UCHC_CWARDNO varchar(100) comment 'Ward_No',	
UCHC_CCHARG decimal(20,2) comment 'Consumer_charge',	
UCHC_CRBOOKNO varchar(100) comment 'Reciept_book_No',	
UCHC_CRRECEIPTNO varchar(100) comment 'Reciept_No',	
UCHC_CTOAMT decimal(20,2) comment 'total_Amount',
ORGID bigint(12) ,
CREATED_BY bigint(12) ,
CREATED_DATE datetime ,
UPDATED_BY bigint(12) ,
UPDATED_DATE datetime ,
LG_IP_MAC varchar(100) ,
LG_IP_MAC_UPD varchar(100) ,
PRIMARY KEY (UCHC_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;