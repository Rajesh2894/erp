--liquibase formatted sql
--changeset Kanchan:V20201026150919__AL_tb_wms_bid_master_hist_26102020.sql
alter table tb_wms_bid_master_hist add column TND_ID  bigInt(12)not null,add key(TND_ID);
--liquibase formatted sql
--changeset Kanchan:V20201026150919__AL_tb_wms_bid_master_hist_261020201.sql
alter table tb_wms_bid_master add column TND_ID  bigInt(12) not null,add key(TND_ID);
