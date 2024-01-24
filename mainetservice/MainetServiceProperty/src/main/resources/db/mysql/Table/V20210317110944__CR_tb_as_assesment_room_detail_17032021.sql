--liquibase formatted sql
--changeset Kanchan:V20210317110944__CR_tb_as_assesment_room_detail_17032021.sql
CREATE TABLE tb_as_assesment_room_detail (
  MN_AS_ROOMID bigint(12) NOT NULL,
  MN_assd_id bigint(12) DEFAULT NULL,
  CPD_RMTYPEID bigint(12) DEFAULT NULL,
  PR_RMLENGTH decimal(12,2) DEFAULT NULL,
  PR_RMWIDTH decimal(12,2) DEFAULT NULL,
  PR_RMAREA decimal(12,2) DEFAULT NULL,
  ORGID bigint(12) DEFAULT NULL,
  USER_ID bigint(12) DEFAULT NULL,
  LANG_ID bigint(12) DEFAULT NULL,
  LMODDATE datetime DEFAULT NULL,
  MN_ROOM_NO bigint(12) DEFAULT NULL,
  IS_ACTIVE varchar(20) DEFAULT NULL,
  PRIMARY KEY (MN_AS_ROOMID),
  KEY FK_DETAIL_MN_ASSD_ID (MN_assd_id),
  CONSTRAINT FK_DETAIL_MN_ASSD_ID FOREIGN KEY (MN_assd_id) REFERENCES tb_as_assesment_detail (MN_ASSD_ID)
) ;
