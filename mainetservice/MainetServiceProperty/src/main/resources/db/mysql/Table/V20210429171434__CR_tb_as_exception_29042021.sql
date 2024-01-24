--liquibase formatted sql
--changeset Kanchan:V20210429171434__CR_tb_as_exception_29042021.sql
 create table tb_as_exception(
exceptionId bigint(15) NOT NULL Primary Key,
 prop_no varchar(50) NOT NULL,
excep_reason varchar(1000) NOT NULL,
staus varchar(1) NOT NULL,
bill_type varchar(3) NOT NULL,
ORGID bigint(12) NOT NULL,      
CREATED_BY bigint(12) NOT NULL,      
CREATED_DATE  datetime NOT NULL,      
UPDATED_BY bigint(12) NOT NULL,
UPDATED_DATE datetime NULL,     
LG_IP_MAC varchar(100) NOT NULL,      
LG_IP_MAC_UPD varchar(100) NULL);
