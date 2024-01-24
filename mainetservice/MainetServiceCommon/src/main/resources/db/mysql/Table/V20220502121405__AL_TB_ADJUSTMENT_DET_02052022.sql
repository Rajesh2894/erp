--liquibase formatted sql
--changeset Kanchan:V20220502121405__AL_TB_ADJUSTMENT_DET_02052022.sql
alter table TB_ADJUSTMENT_DET Add column BD_BILLDETID bigint(12) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220502121405__AL_TB_ADJUSTMENT_DET_020520221.sql
alter table TB_ADJUSTMENT_DET Add column BM_IDNO bigint(12) null default null;
