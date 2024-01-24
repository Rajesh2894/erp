--liquibase formatted sql
--changeset Anil:V20200831125843__AL_tb_as_assesment_mast_31082020.sql
ALTER TABLE tb_as_assesment_mast ADD COLUMN editable_date DATE NULL;
--liquibase formatted sql
--changeset Anil:V20200831125843__AL_tb_as_assesment_mast_310820201.sql
ALTER TABLE tb_as_pro_assesment_mast ADD COLUMN editable_date DATE NULL;
--liquibase formatted sql
--changeset Anil:V20200831125843__AL_tb_as_assesment_mast_310820202.sql
ALTER TABLE tb_as_pro_mast_hist ADD COLUMN editable_date DATE NULL;

