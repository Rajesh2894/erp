--liquibase formatted sql
--changeset Kanchan:V20210129093439__AL_tb_eip_feedback_29012021.sql
alter table tb_eip_feedback add column fd_subject varchar(4000) default null;  
--liquibase formatted sql
--changeset Kanchan: V20210129093439__AL_tb_eip_feedback_290120211.sql        
alter table tb_eip_feedback_hist add column  fd_subject varchar(4000) default null; 
