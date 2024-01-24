--liquibase formatted sql
--changeset Kanchan:V20210317111400__CR_tb_as_room_det_17032021.sql
CREATE TABLE tb_as_room_det(
  PR_ROOMID bigint(12) NOT NULL,
  PD_ID bigint(12) DEFAULT NULL,
  CPD_RMTYPEID bigint(12) DEFAULT NULL,
  PR_RMLENGTH decimal(15,2) DEFAULT NULL,
  PR_RMWIDTH decimal(15,2) DEFAULT NULL,
  PR_RMAREA decimal(15,2) DEFAULT NULL,
  ORGID bigint(15) NOT NULL,
  USER_ID bigint(15) NOT NULL,
  LANG_I bigint(15) DEFAULT NULL,
  LMODDATE datetime NOT NULL,
  PR_ROOM_NO bigint(12) DEFAULT NULL,
  IS_ACTIVE varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (PR_ROOMID)
) ;
