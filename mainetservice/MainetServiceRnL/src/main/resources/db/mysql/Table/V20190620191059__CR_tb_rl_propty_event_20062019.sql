--liquibase formatted sql
--changeset Anil:V20190620191059__CR_tb_rl_propty_event_20062019.sql
drop TABLE if exists tb_rl_propty_event;
--liquibase formatted sql
--changeset Anil:V20190620191059__CR_tb_rl_propty_event_200620191.sql
CREATE TABLE tb_rl_propty_event (
  PROP_EVID bigint(12) NOT NULL COMMENT ' Primary Key',
  PROP_ID bigint(12) NOT NULL COMMENT ' FK',
  PROP_EVENT bigint(12) NOT NULL COMMENT ' Prefix->EVT',
  PROP_ANLLOW char(1) NOT NULL COMMENT ' A->Allowed  N->Not Allowed',
  PROP_EV_STATUS char(1) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT ' organization id',
  LANGID bigint(8) NOT NULL COMMENT ' language id 1- english,2-regional',
  CREATED_BY bigint(12) NOT NULL COMMENT ' user id who created the record',
  CREATED_DATE date NOT NULL COMMENT ' record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT ' user id who updated the record',
  UPDATED_DATE date DEFAULT NULL COMMENT ' user id who updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT ' machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT ' machine ip address from where user has updated the record',
  PRIMARY KEY (PROP_EVID),
  KEY FK_PROP_ID_EV (PROP_ID),
  CONSTRAINT FK_PROP_ID_EV FOREIGN KEY (PROP_ID) REFERENCES tb_rl_property_mas (PROP_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;