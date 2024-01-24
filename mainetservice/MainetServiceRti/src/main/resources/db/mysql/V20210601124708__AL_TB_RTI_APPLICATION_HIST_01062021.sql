--liquibase formatted sql
--changeset Kanchan:V20210601124708__AL_TB_RTI_APPLICATION_HIST_01062021.sql
alter table TB_RTI_APPLICATION_HIST  add column
 
TRD_WARD1   bigint(12),        
add TRD_WARD2  bigint(12),    
add TRD_WARD3  bigint(12),     
add TRD_WARD4  bigint(12),     
add TRD_WARD5   bigint(12) NULL;

