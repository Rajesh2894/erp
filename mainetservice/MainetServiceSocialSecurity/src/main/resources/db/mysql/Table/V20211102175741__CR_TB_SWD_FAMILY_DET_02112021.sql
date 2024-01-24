--liquibase formatted sql
--changeset Kanchan:V20211102175741__CR_TB_SWD_FAMILY_DET_02112021.sql
create table TB_SWD_FAMILY_DET(
FAM_MEMID bigint(12) primary key  NOT NULL,  
MEM_NAME varchar(200) DEFAULT NULL,        
MEM_RELATION varchar(50) DEFAULT NULL,        
MEM_GEN varchar(10) DEFAULT NULL,    
DOB datetime DEFAULT NULL,    
AGE bigint(5) DEFAULT NULL,
EDUCATION varchar(10) DEFAULT NULL,    
OCCUPATION varchar(10) DEFAULT NULL,    
CONTACT_NO varchar(10) DEFAULT NULL,        
ORGID bigint(12) NOT NULL,     
CREATED_BY bigint(12) NOT NULL,       
CREATED_DATE datetime NOT NULL,       
LG_IP_MAC varchar(100) DEFAULT NULL,        
UPDATED_BY bigint(12) DEFAULT NULL,        
UPDATED_DATE datetime DEFAULT NULL,        
LG_IP_MAC_UPD varchar(100) DEFAULT NULL);  
