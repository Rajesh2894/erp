--liquibase formatted sql
--changeset Kanchan:V20210512120842__AL_TB_RTI_APPLICATION_12052021.sql
ALTER TABLE TB_RTI_APPLICATION  ADD COLUMN RTI_RELATED_DEPT int(11) NULL
