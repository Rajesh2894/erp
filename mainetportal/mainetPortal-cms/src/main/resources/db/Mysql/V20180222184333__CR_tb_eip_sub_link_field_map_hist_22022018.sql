--liquibase formatted sql
--changeset priya:V20180222184333__CR_tb_eip_sub_link_field_map_hist_22022018.sql
CREATE TABLE tb_eip_sub_link_field_map_hist (
  SUB_LINK_FIELD_ID_H bigint(12) DEFAULT NULL,
  SUB_LINK_FIELD_ID double DEFAULT NULL COMMENT 'Subject link Field Id',
  SUB_LINK_MAS_ID double DEFAULT NULL COMMENT 'Sub Link Master Id',
  FIELD_NAME_EN varchar(100) DEFAULT NULL COMMENT 'Field Name in English',
  FIELD_TYPE_ID double DEFAULT NULL COMMENT 'Field Type Id',
  FIELD_SEQ double DEFAULT NULL COMMENT 'Sequence Of Field',
  FIELD_NAME_MAP varchar(20) DEFAULT NULL COMMENT 'Field Name Map',
  IS_USED char(1) DEFAULT NULL,
  IS_MANDATORY char(1) DEFAULT NULL,
  FIELD_NAME_RG varchar(150) DEFAULT NULL COMMENT 'Regional label name for field',
  ISDELETED char(1) DEFAULT NULL COMMENT 'Flag to identify whether the record is deleted or not. 1 for deleted (inactive) and 0 for not deleted (active) record.',
  ORGID bigint(12) DEFAULT NULL COMMENT 'Organisation ID',
  USER_ID bigint(12) DEFAULT NULL COMMENT 'User Id',
  LANG_ID int(11) DEFAULT NULL COMMENT 'Language Id',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record\n',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record\n',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record\n',
  SUB_SECTION_TYPE bigint(12) DEFAULT NULL,
  H_STATUS varchar(1) DEFAULT NULL COMMENT 'Status of the record'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
