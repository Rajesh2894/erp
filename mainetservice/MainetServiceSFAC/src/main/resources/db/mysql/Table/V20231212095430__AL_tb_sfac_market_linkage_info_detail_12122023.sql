--liquibase formatted sql
--changeset PramodPatil:V20231212095430__AL_tb_sfac_market_linkage_info_detail_12122023.sql
alter table tb_sfac_market_linkage_info_detail modify MR_REG_NO varchar(1000) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231212095430__AL_tb_sfac_market_linkage_info_detail_121220231.sql
alter table tb_sfac_market_linkage_info_detail_hist modify MR_REG_NO varchar(1000) null default null;