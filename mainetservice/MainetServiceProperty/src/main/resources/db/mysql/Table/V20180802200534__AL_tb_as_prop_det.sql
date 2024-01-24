--liquibase formatted sql
--changeset nilima:V20180802200534__AL_tb_as_prop_det1.sql
alter table tb_as_prop_det change column PD_OC_NUMBER PD_OC_NUMBER BIGINT(12) NULL;