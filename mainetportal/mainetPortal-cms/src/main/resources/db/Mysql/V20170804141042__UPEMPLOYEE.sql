CREATE EVENT UPEMPLOYEE
ON SCHEDULE EVERY 1 HOUR
DO
  update EMPLOYEE 
  set LOCK_UNLOCK=null,
      LOCK_DATE=null,
      LOGGED_IN_ATTEMPT=null
  where upper(trim(LOCK_UNLOCK))='L' and round((NOW() - LOCK_DATE) * 24 * 60)>=60 ;

