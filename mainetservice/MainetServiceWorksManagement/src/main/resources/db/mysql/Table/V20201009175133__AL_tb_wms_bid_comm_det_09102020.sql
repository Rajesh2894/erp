--liquibase formatted sql
--changeset Kanchan:V20201009175133__AL_tb_wms_bid_comm_det_09102020.sql
ALTER TABLE tb_wms_bid_comm_det CHANGE COLUMN weigtage weigtage DECIMAL(5,2) NOT NULL ;
--liquibase formatted sql
--changeset Kanchan:V20201009175133__AL_tb_wms_bid_comm_det_091020201.sql
ALTER TABLE tb_wms_bid_tech_det CHANGE COLUMN weightage weightage DECIMAL(5,2) NOT NULL ;
--liquibase formatted sql
--changeset Kanchan:V20201009175133__AL_tb_wms_bid_comm_det_091020202.sql
ALTER TABLE tb_wms_bid_comm_det_hist CHANGE COLUMN weigtage weigtage DECIMAL(5,2) NOT NULL ;
--liquibase formatted sql
--changeset Kanchan:V20201009175133__AL_tb_wms_bid_comm_det_091020203.sql
ALTER TABLE tb_wms_bid_tech_det_hist CHANGE COLUMN weightage weightage DECIMAL(5,2) NOT NULL ;
