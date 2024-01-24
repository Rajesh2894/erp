--liquibase formatted sql
--changeset nilima:V20171220193102__al_tb_location_mas_hist_20122017.sql
ALTER TABLE tb_location_mas_hist
ADD COLUMN LATTIUDE VARCHAR(100) NULL COMMENT 'Lattiude' AFTER H_STATUS,
ADD COLUMN LONGITUDE VARCHAR(100) NULL COMMENT 'Longitude' AFTER LATTIUDE;
