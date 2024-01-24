--liquibase formatted sql
--changeset PramodPatil:V20231106131913__AL_Tb_Sfac_Equity_Grant_tranche_det_06112023.sql
alter table Tb_Sfac_Equity_Grant_tranche_det
add column EACH_SHARE_VAL Decimal(15,2) null default null,
add column TOTAL_SHARE_VAL Decimal(15,2) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231106131913__AL_Tb_Sfac_Equity_Grant_tranche_det_061120231.sql
alter table Tb_Sfac_Equity_Grant_tranche_det_hist
add column EACH_SHARE_VAL Decimal(15,2) null default null,
add column TOTAL_SHARE_VAL Decimal(15,2) null default null;