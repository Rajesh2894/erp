--liquibase formatted sql
--changeset Kanchan:V20201001155748__CR_exception_log_dtl_01102020.sql
drop table if exists exception_log_dtl;
--liquibase formatted sql
--changeset Kanchan:V20201001155748__CR_exception_log_dtl_011020201.sql
create table exception_log_dtl
(
        exception_log_id  bigint (15) Primary key,

        exception_class varchar (20)  NULL,

        URL varchar (50) NULL,

       exception_detail varchar(1000) NULL,

        file_name varchar (20) NULL,

        method_name varchar (20) NULL,
		 
		ORGID bigint(12) NOT NULL COMMENT 'Org ID',
 
        CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
 
        CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
 
        UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'User id who update the data',
 
        UPDATED_DATE datetime DEFAULT NULL COMMENT 'Date on which data is going to update',
 
        LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
 
        LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record'

);
