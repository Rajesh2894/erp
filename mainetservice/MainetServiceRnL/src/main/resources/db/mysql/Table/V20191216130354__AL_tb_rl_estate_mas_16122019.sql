--liquibase formatted sql
--changeset Anil:V20191216130354__AL_tb_rl_estate_mas_16122019.sql
ALTER TABLE tb_rl_estate_mas 
ADD COLUMN EAST VARCHAR(200) NULL AFTER ES_LONGITUDE,
ADD COLUMN WEST VARCHAR(200) NULL AFTER EAST,
ADD COLUMN SOUTH VARCHAR(200) NULL AFTER WEST,
ADD COLUMN NORTH VARCHAR(200) NULL AFTER SOUTH;
--liquibase formatted sql
--changeset Anil:V20191216130354__AL_tb_rl_estate_mas_161220191.sql
ALTER TABLE tb_rl_estate_mas_hist
ADD COLUMN EAST VARCHAR(200) NULL AFTER ES_NAME_REG,
ADD COLUMN WEST VARCHAR(200) NULL AFTER EAST,
ADD COLUMN SOUTH VARCHAR(200) NULL AFTER WEST,
ADD COLUMN NORTH VARCHAR(200) NULL AFTER SOUTH;
