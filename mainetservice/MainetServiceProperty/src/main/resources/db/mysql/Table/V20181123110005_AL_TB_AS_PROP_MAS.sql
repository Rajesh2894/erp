--liquibase formatted sql
--changeset nilima:V20181123110005_AL_TB_AS_PROP_MAS1.sql
ALTER TABLE TB_AS_PROP_MAS CHANGE COLUMN `PM_PLOT_NO_CS` `PM_PLOT_NO_CS` VARCHAR(50) NULL;
--liquibase formatted sql
--changeset nilima:V20181123110005_AL_TB_AS_PROP_MAS2.sql
ALTER TABLE TB_AS_PROP_MAS CHANGE COLUMN `PM_PLOT_NO` `PM_PLOT_NO` VARCHAR(50) NULL;

