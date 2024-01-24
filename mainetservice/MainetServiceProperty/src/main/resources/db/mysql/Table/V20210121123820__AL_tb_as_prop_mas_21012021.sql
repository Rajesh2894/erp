--liquibase formatted sql
--changeset Kanchan:V20210121123820__AL_tb_as_prop_mas_21012021.sql
alter table tb_as_prop_mas add column
group_prop_no  varchar(20), 
add group_prop_name  varchar(500),
add parent_prop_no  varchar(20),
add parent_prop_name  varchar(500),
add is_group  varchar(1) null;
