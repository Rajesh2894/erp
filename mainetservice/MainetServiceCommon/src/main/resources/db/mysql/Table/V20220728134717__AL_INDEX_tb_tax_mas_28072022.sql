--liquibase formatted sql
--changeset Kanchan:V20220728134717__AL_INDEX_tb_tax_mas_28072022.sql
create index idx_tb_tax_mas_TAX_DESC on tb_tax_mas (TAX_DESC);
--liquibase formatted sql
--changeset Kanchan:V20220728134717__AL_INDEX_tb_tax_mas_280720221.sql
create index idx_tb_tax_mas_TAX_CODE on tb_tax_mas (TAX_CODE);
--liquibase formatted sql
--changeset Kanchan:V20220728134717__AL_INDEX_tb_tax_mas_280720222.sql
create index idx_tb_tax_mas_TAX_DISPLAY_SEQ on tb_tax_mas (TAX_DISPLAY_SEQ);