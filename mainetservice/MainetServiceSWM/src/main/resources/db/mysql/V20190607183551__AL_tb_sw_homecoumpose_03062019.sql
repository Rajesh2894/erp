--liquibase formatted sql
--changeset nilima:V20190607183551__AL_tb_sw_homecoumpose_03062019.sql
ALTER TABLE tb_sw_homecoumpose 
ADD COLUMN SW_HCOM_DATE DATE NULL AFTER `SW_HCOM_PREPARED`;

--liquibase formatted sql
--changeset nilima:V20190607183551__AL_tb_sw_homecoumpose_030620191.sql
ALTER TABLE tb_sw_homecoumpose
ADD COLUMN REGISTRATION_ID BIGINT(12) NULL AFTER SW_HCOM_DATE;

