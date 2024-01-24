--liquibase formatted sql
--changeset Kanchan:V20210112160804__AL_TB_LGL_COMNT_REVW_DTL_HIST_12012021.sql
alter table TB_LGL_COMNT_REVW_DTL_HIST		   
add column CREATED_BY bigint(12) not null,
add CREATED_DATE datetime not null,
add UPDATED_BY bigint(12) null,
add UPDATED_DATE datetime null,
add LG_IP_MAC varchar(100) not null,
add LG_IP_MAC_UPD varchar(100) null,
add HR_ID bigint(12) not null,
add HR_DATE date not null;
