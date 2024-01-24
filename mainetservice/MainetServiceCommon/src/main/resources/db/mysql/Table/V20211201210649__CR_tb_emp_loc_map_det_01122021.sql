--liquibase formatted sql
--changeset Kanchan:V20211201210649__CR_tb_emp_loc_map_det_01122021.sql
create table tb_emp_loc_map_det(
emp_loc_id bigint(12) primary key  NOT NULL,
emp_id bigint(12) DEFAULT NULL,
ward1 bigint(12) DEFAULT NULL,
ward2 bigint(12) DEFAULT NULL,
ward3 bigint(12) DEFAULT NULL,
ward4 bigint(12) DEFAULT NULL,
ward5 bigint(12) DEFAULT NULL,
orgid bigint(12) DEFAULT NULL,
created_date datetime DEFAULT NULL,
created_by bigint(12) DEFAULT NULL,
LG_IP_MAC varchar(100) DEFAULT NULL,
updated_date datetime DEFAULT NULL,
updated_by bigint(12) DEFAULT NULL,
LG_IP_MAC_UPD varchar(100) DEFAULT NULL);

