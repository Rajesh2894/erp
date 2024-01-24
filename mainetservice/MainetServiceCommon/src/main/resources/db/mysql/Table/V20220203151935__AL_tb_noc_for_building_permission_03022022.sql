--liquibase formatted sql
--changeset Kanchan:V20220203151935__AL_tb_noc_for_building_permission_03022022.sql
alter table tb_noc_for_building_permission add column P_NAME  varchar (500),
add P_ADDR  varchar (500),
add S_NAME varchar (500),
add S_ADDR  varchar (500),
add EAST        varchar (500),
add WEST      varchar (500),
add SOUTH     varchar (500),
add NORTH     varchar (500) DEFAULT NULL;
