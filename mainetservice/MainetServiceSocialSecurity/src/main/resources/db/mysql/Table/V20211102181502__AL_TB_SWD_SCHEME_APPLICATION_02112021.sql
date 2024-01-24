--liquibase formatted sql
--changeset Kanchan:V20211102181502__AL_TB_SWD_SCHEME_APPLICATION_02112021.sql
alter table  TB_SWD_SCHEME_APPLICATION
add column SWD_WARD1  bigint(12),
add SWD_WARD2  bigint(12),
add SWD_WARD3  bigint(12),
add SWD_WARD4  bigint(12),
add SWD_WARD5  bigint(12),
add DISABILITY_CERT_NO varchar(50) DEFAULT NULL;
