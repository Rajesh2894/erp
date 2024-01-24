--liquibase formatted sql
--changeset PramodPatil:V20231127113330__AL_tb_sfac_purchase_info_det_27112023.sql
alter table tb_sfac_purchase_info_det add column MON_YEAR_DATE datetime null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231127113330__AL_tb_sfac_purchase_info_det_271120231.sql
alter table tb_sfac_purchase_info_det_hist add column MON_YEAR_DATE datetime null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231127113330__AL_tb_sfac_purchase_info_det_271120232.sql
alter table Tb_Sfac_Sale_Info_Detail add column MON_YEAR_DATE datetime null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231127113330__AL_tb_sfac_purchase_info_det_271120233.sql
alter table Tb_Sfac_Sale_Info_Detail_Hist add column MON_YEAR_DATE datetime null default null;
