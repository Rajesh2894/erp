--liquibase formatted sql
--changeset Anil:V20191106121710__CR_tb_adh_contract_mapping_06112019.sql
drop table if exists tb_adh_contract_mapping;
--liquibase formatted sql
--changeset Anil:V20191106121710__CR_tb_adh_contract_mapping_061120191.sql
CREATE TABLE tb_adh_contract_mapping(
cont_hrd_mapid bigint(12) NOT NULL,
cont_id bigint(12) NOT NULL,
hrd_id bigint(12) NOT NULL,
cont_map_autby bigint(12) DEFAULT NULL,
cont_map_autdate date DEFAULT NULL,
cont_map_active char(1) NOT NULL,
orgid bigint(12) NOT NULL,
created_by bigint(12) NOT NULL,
created_date datetime NOT NULL,
lg_ip_mac varchar(100) NOT NULL,
updated_by bigint(12) DEFAULT NULL,
updated_date datetime DEFAULT NULL,
lg_ip_mac_upd varchar(100) DEFAULT NULL,
PRIMARY KEY (cont_hrd_mapid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
