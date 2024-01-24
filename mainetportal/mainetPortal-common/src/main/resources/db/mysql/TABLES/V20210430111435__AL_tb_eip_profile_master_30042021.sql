--liquibase formatted sql
--changeset Kanchan:V20210430111435__AL_tb_eip_profile_master_30042021.sql
alter table tb_eip_profile_master add dt_of_join datetime;
--liquibase formatted sql
--changeset Kanchan:V20210430111435__AL_tb_eip_profile_master_300420211.sql
alter table tb_eip_profile_master_hist add dt_of_join datetime;

