--liquibase formatted sql
--changeset Kanchan:V20210531105245__AL_TB_RTI_APPLICATION_31052021.sql
alter table TB_RTI_APPLICATION  add column
 
TRD_WARD1   bigint(12),        
add TRD_WARD2  bigint(12),    
add TRD_WARD3  bigint(12),     
add TRD_WARD4  bigint(12),     
add TRD_WARD5   bigint(12) NULL;
