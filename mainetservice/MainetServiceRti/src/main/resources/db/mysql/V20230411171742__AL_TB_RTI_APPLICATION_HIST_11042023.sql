--liquibase formatted sql
--changeset Kanchan:V20230411171742__AL_TB_RTI_APPLICATION_HIST_11042023.sql
alter table TB_RTI_APPLICATION_HIST add column 
NON_JUDCL_NO varchar(50) null default null,  
add CHALLAN_NO varchar(50) null default null, 
add NON_JUDCL_DATE date null default null, 
add CHALLAN_DATE date null default null; 
--liquibase formatted sql
--changeset Kanchan:V20230411171742__AL_TB_RTI_APPLICATION_HIST_110420231.sql
alter table TB_RTI_APPLICATION add column 
NON_JUDCL_NO varchar(50) null default null,  
add CHALLAN_NO varchar(50) null default null, 
add NON_JUDCL_DATE date null default null, 
add CHALLAN_DATE date null default null; 