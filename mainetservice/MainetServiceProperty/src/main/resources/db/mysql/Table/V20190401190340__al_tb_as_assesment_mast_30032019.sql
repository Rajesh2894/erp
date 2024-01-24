--liquibase formatted sql
--changeset nilima:V20190401190340__al_tb_as_assesment_mast_30032019.sql
ALTER TABLE tb_as_assesment_mast
ADD COLUMN MN_ASS_VSRNO VARCHAR(50) NULL AFTER DEED_NO,
CHANGE COLUMN MN_ASS_TAX_COLL_EMP MN_ASS_TAX_COLL_EMP BIGINT(20) NULL COMMENT 'TAX COLLECTOR EMPLOYEE ',
CHANGE COLUMN orgid orgid BIGINT(12) NOT NULL ,
CHANGE COLUMN created_by created_by BIGINT(12) NOT NULL,
CHANGE COLUMN created_date created_date DATETIME NOT NULL,
CHANGE COLUMN updated_by updated_by BIGINT(12),
CHANGE COLUMN updated_date updated_date DATETIME,
CHANGE COLUMN lg_ip_mac lg_ip_mac VARCHAR(100) NOT NULL,
CHANGE COLUMN lg_ip_mac_upd lg_ip_mac_upd VARCHAR(100);


--liquibase formatted sql
--changeset nilima:V20190401190340__al_tb_as_assesment_mast_300320191.sql
ALTER TABLE tb_as_assesment_mast 
CHANGE COLUMN MN_ASS_ward1 MN_ASS_ward1 BIGINT(12) NOT NULL ;