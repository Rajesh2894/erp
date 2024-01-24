--liquibase formatted sql
--changeset PramodPatil:V20230714173938__AL_Tb_Sfac_Equity_Grant_shareholding_Det_14072023.sql
Alter table Tb_Sfac_Equity_Grant_shareholding_Det add column STATUS char(1) Null default 'A';

--liquibase formatted sql
--changeset PramodPatil:V20230714173938__AL_Tb_Sfac_Equity_Grant_shareholding_Det_140720231.sql
Alter table Tb_Sfac_Equity_Grant_tranche_det add column STATUS char(1) Null default 'A';

--liquibase formatted sql
--changeset PramodPatil:V20230714173938__AL_Tb_Sfac_Equity_Grant_shareholding_Det_140720232.sql
Alter table Tb_Sfac_Equity_Grant_Det add column STATUS char(1) Null default 'A';

--liquibase formatted sql
--changeset PramodPatil:V20230714173938__AL_Tb_Sfac_Equity_Grant_shareholding_Det_140720233.sql
Alter table Tb_Sfac_Equity_Grant_functional_commt_Det add column STATUS char(1) Null default 'A';