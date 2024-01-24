--liquibase formatted sql
--changeset Kanchan:V20210315181834__AL_tb_cfc_hospital_info_15032021.sql
alter table tb_cfc_hospital_info add column
reg_branch_nm	Varchar(50),
add medical_prof_type	Varchar(50),	
add business_years	decimal(5,2),	
add business_strt_dt	datetime NOT NULL;	
