--liquibase formatted sql
--changeset priya:V20180119114727__AL_AC_Script_04122017.sql
ALTER TABLE tb_tax_mas
ADD COLUMN Tax_Active CHAR(1) NULL AFTER `tax_desc_id`;

--liquibase formatted sql
--changeset priya:V20180119114727__AL_AC_Script_041220171.sql
update tb_tax_mas set tax_active='Y' where tax_active is null;
commit;