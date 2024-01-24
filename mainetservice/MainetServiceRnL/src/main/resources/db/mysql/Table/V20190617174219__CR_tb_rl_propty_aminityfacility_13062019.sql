--liquibase formatted sql
--changeset Anil:V20190617174219__CR_tb_rl_propty_aminityfacility_13062019.sql
drop table if exists tb_rl_propty_aminityfacility;
--liquibase formatted sql
--changeset Anil:V20190617174219__CR_tb_rl_propty_aminityfacility_130620191.sql
CREATE TABLE tb_rl_propty_aminityfacility (
  PROP_AMID bigint(12) NOT NULL COMMENT ' primary key',
  PROP_ID bigint(12) NOT NULL COMMENT ' FK',
  PROP_AMTFACT bigint(12) NOT NULL COMMENT ' Prefix->AMT,FAC',
  PROP_QUANTITY decimal(15,2) DEFAULT NULL COMMENT ' ',
  PROP_TYPE char(1) NOT NULL COMMENT ' A->Aminity F->Facility',
  PROP_AMIFAC_STATUS char(2) NOT NULL,
  ORGID bigint(12) NOT NULL COMMENT ' organization id',
  LANGID bigint(8) NOT NULL COMMENT ' language id 1- english,2-regional',
  CREATED_BY bigint(12) NOT NULL COMMENT ' user id who created the record',
  CREATED_DATE date NOT NULL COMMENT ' record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT ' user id who updated the record',
  UPDATED_DATE date DEFAULT NULL COMMENT ' user id who updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT ' machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT ' machine ip address from where user has updated the record',
  PRIMARY KEY (PROP_AMID),
  KEY FK_PROP_ID (PROP_ID),
  CONSTRAINT FK_PROP_ID FOREIGN KEY (PROP_ID) REFERENCES tb_rl_property_mas (PROP_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


