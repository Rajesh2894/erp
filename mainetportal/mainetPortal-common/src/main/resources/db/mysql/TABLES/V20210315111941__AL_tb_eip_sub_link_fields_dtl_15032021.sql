--liquibase formatted sql
--changeset Kanchan:V20210315111941__AL_tb_eip_sub_link_fields_dtl_15032021.sql
alter table tb_eip_sub_link_fields_dtl add column  issue_date datetime ;
--liquibase formatted sql
--changeset Kanchan:V20210315111941__AL_tb_eip_sub_link_fields_dtl_150320211.sql
alter table tb_eip_sub_link_fields_dtl_hist add column issue_date datetime ; 
--liquibase formatted sql
--changeset Kanchan:V20210315111941__AL_tb_eip_sub_link_fields_dtl_150320212.sql
alter table tb_eip_sub_link_fields_dtl add column link_01 varchar(1000);
--liquibase formatted sql
--changeset Kanchan:V20210315111941__AL_tb_eip_sub_link_fields_dtl_150320213.sql
alter table tb_eip_sub_link_fields_dtl add column link_02 varchar(1000);
--liquibase formatted sql
--changeset Kanchan:V20210315111941__AL_tb_eip_sub_link_fields_dtl_150320214.sql
alter table tb_eip_sub_link_fields_dtl_hist add column link_01 varchar(1000);
--liquibase formatted sql
--changeset Kanchan:V20210315111941__AL_tb_eip_sub_link_fields_dtl_150320215.sql
alter table tb_eip_sub_link_fields_dtl_hist add column link_02 varchar(1000);
--liquibase formatted sql
--changeset Kanchan:V20210315111941__AL_tb_eip_sub_link_fields_dtl_150320216.sql
alter table tb_eip_sub_link_fields_dtl modify column validity_date datetime ;
--liquibase formatted sql
--changeset Kanchan:V20210315111941__AL_tb_eip_sub_link_fields_dtl_150320217.sql
alter table tb_eip_sub_link_fields_dtl_hist modify column validity_date datetime ;
