ALTER TABLE TB_LGL_COURT_MAST_HIST
CHANGE COLUMN CRT_START_TIME CRT_START_TIME VARCHAR(20) NOT NULL COMMENT 'Court Start Time' ,
CHANGE COLUMN CRT_END_TIME CRT_END_TIME VARCHAR(20) NOT NULL COMMENT 'Court End Time' ;