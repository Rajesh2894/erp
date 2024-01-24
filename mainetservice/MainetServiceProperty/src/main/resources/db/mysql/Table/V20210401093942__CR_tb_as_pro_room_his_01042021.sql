--liquibase formatted sql
--changeset Kanchan:V20210401093942__CR_tb_as_pro_room_his_01042021.sql
create table tb_as_pro_room_his (
PRO_HIS_ROOMID bigint(12)  NOT NULL,
pro_assd_id bigint(12) NOT NULL,
PRO_ROOM_NO bigint(12) DEFAULT NULL,
IS_ACTIVE  varchar(10) DEFAULT NULL,
CPD_RMTYPEID bigint(12) DEFAULT NULL,
PRO_RMLENGTH decimal(20,2) DEFAULT NULL,
PRO_RMWIDTH decimal(20,2) DEFAULT NULL,
PRO_RMAREA decimal(20,2) DEFAULT NULL,
ORGID bigint(12) DEFAULT NULL,
USER_ID bigint(12) DEFAULT NULL,
LANG_ID bigint(12) DEFAULT NULL,
LMODDATE datetime DEFAULT NULL,
H_STATUS varchar(10) DEFAULT NULL,
primary key (PRO_HIS_ROOMID));

