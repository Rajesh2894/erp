CREATE OR REPLACE FUNCTION nos_words(input NUMBER) RETURN VARCHAR2 IS
    v_words    VARCHAR2(32767); --To return the whole number.
    n_len      NUMBER(3); --For length of the number.
    v_hdd      VARCHAR2(3000); --For storing hundreds into words.
    v_thsd     VARCHAR2(3000); --For storing thousands into words.
    v_lacs     VARCHAR2(3000); --For storing lacs into words.
    v_crores   VARCHAR2(3000); --For storing crores into words.
    v_million  VARCHAR2(3000); --For storing million into words.
    v_billion  VARCHAR2(3000); --For storing billion into words.
    v_trillion VARCHAR2(3000); --For storing trillion into words.
    n_place    NUMBER(2); --For storing the place of the
    --decimal point.
    n_input        NUMBER; --For storing the precision of the                 --number.
    v_paise        VARCHAR2(3000); --For storing the paise into words.
    n_roundedinput NUMBER; --For storing the rounded input.
  BEGIN
    n_roundedinput := ROUND(input, 2); --Rounding it off to two decimals.
    n_len          := LENGTH(input); --Store the length of the passed
    --number.
    n_input := input; --Store the passed number.
    n_place := INSTR(input, '.'); --Find the units place of decimal                  --point.

    --Store the precision into n_input and its length into n_len,provided
    --the number is in decimals.
    IF (n_place > 0) THEN
      n_input := TO_NUMBER(SUBSTR(input, 1, n_place - 1));
      n_len   := LENGTH(n_input);
    END IF;

    --Provided the number is in hundreds, or more then convert it into words in the specified way.
    IF (n_len >= 3) THEN
      --Provided that the last three numbers are zeros, convert the three figures into words.
      IF (SUBSTR(n_input, -3) != 0) THEN
        SELECT TO_CHAR(TO_DATE(SUBSTR(n_input, -3), 'j'), 'Jsp')
          INTO v_hdd
          FROM DUAL;
      END IF;

      --Provided that the length of the number is more than 3 and both the last two numbers are not zeros,then
      --convert those two into words. This makes up a thousand figure.
      IF (n_len > 3) THEN
        IF (SUBSTR(SUBSTR(n_input, 1, (n_len - LENGTH(SUBSTR(n_input, -3)))),
                   -2) != 0) THEN
          SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                               1,
                                               (n_len -
                                               LENGTH(SUBSTR(n_input, -3)))),
                                        -2),
                                 'j'),
                         'Jsp') || ' Thousand '
            INTO v_thsd
            FROM DUAL;
        END IF;

        --Provided that instead of two numbers for e.g. 2143, i.e. only 2 is left,
        --then convert it into words.
        IF (n_len = 4) THEN
          IF (SUBSTR(SUBSTR(n_input,
                            1,
                            (n_len - LENGTH(SUBSTR(n_input, -3)))),
                     1,
                     2) != 0) THEN
            SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                 1,
                                                 (n_len -
                                                 LENGTH(SUBSTR(n_input, -3)))),
                                          1,
                                          2),
                                   'j'),
                           'Jsp') || ' Thousand '
              INTO v_thsd
              FROM DUAL;
          END IF;
        END IF;

        --Provided that the length of the number is more than 5 and both the last two numbers are not zeros,then
        --convert those two into words. This makes up a figure of lac.
        IF (n_len > 5) THEN
          IF (SUBSTR(SUBSTR(n_input,
                            1,
                            (n_len - LENGTH(SUBSTR(n_input, -5)))),
                     -2) != 0) THEN
            SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                 1,
                                                 (n_len -
                                                 LENGTH(SUBSTR(n_input, -5)))),
                                          -2),
                                   'j'),
                           'Jsp') || ' Lacs '
              INTO v_lacs
              FROM DUAL;
          END IF;

          --Provided that instead of two numbers for e.g. 132143, i.e. only  1 is left,
          --then convert it into words.
          IF (n_len = 6) THEN
            IF (SUBSTR(SUBSTR(n_input,
                              1,
                              (n_len - LENGTH(SUBSTR(n_input, -5)))),
                       1,
                       2) != 0) THEN
              SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                   1,
                                                   (n_len -
                                                   LENGTH(SUBSTR(n_input,
                                                                  -5)))),
                                            1,
                                            2),
                                     'j'),
                             'Jsp') || ' Lacs '
                INTO v_lacs
                FROM DUAL;
            END IF;
          END IF;

          --Provided that the length of the number is more than 7 and both the last two numbers are not zeros,then
          --convert those two into words. This makes up a figure of crore.
          IF (n_len > 7) THEN
            IF (SUBSTR(SUBSTR(n_input,
                              1,
                              (n_len - LENGTH(SUBSTR(n_input, -7)))),
                       -2) != 0) THEN
              SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                   1,
                                                   (n_len -
                                                   LENGTH(SUBSTR(n_input,
                                                                  -7)))),
                                            -2),
                                     'j'),
                             'Jsp') || ' Crores '
                INTO v_crores
                FROM DUAL;
            END IF;

            --Provided that instead of two numbers for e.g. 32132143, i.e. only  2 is left,
            --then convert it into words.
            IF (n_len = 8) THEN
              IF (SUBSTR(SUBSTR(n_input,
                                1,
                                (n_len - LENGTH(SUBSTR(n_input, -7)))),
                         1,
                         2) != 0) THEN
                SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                     1,
                                                     (n_len -
                                                     LENGTH(SUBSTR(n_input,
                                                                    -7)))),
                                              1,
                                              2),
                                       'j'),
                               'Jsp') || ' Crores '
                  INTO v_crores
                  FROM DUAL;
              END IF;
            END IF;

            --Provided that the length of the number is more than 9 and both the last two numbers are not zeros,then
            --convert those two into words. This makes up a figure of million.
            IF (n_len > 9) THEN
              IF (SUBSTR(SUBSTR(n_input,
                                1,
                                (n_len - LENGTH(SUBSTR(n_input, -9)))),
                         -2) != 0) THEN
                SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                     1,
                                                     (n_len -
                                                     LENGTH(SUBSTR(n_input,
                                                                    -9)))),
                                              -2),
                                       'j'),
                               'Jsp') || ' Million '
                  INTO v_million
                  FROM DUAL;
              END IF;

              --Provided that instead of two numbers for e.g. 2902132143, i.e. only  2 is left,
              --then convert it into words.
              IF (n_len = 10) THEN
                IF (SUBSTR(SUBSTR(n_input,
                                  1,
                                  (n_len - LENGTH(SUBSTR(n_input, -9)))),
                           1,
                           2) != 0) THEN
                  SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                       1,
                                                       (n_len -
                                                       LENGTH(SUBSTR(n_input,
                                                                      -9)))),
                                                1,
                                                2),
                                         'j'),
                                 'Jsp') || ' Million '
                    INTO v_million
                    FROM DUAL;
                END IF;
              END IF;

              --Provided that the length of the number is more than 11 and both the last two numbers are not zeros,then
              --convert those two into words. This makes up a figure of billion.
              IF (n_len > 11) THEN
                IF (SUBSTR(SUBSTR(n_input,
                                  1,
                                  (n_len - LENGTH(SUBSTR(n_input, -11)))),
                           -2) != 0) THEN
                  SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                       1,
                                                       (n_len -
                                                       LENGTH(SUBSTR(n_input,
                                                                      -11)))),
                                                -2),
                                         'j'),
                                 'Jsp') || ' Billion '
                    INTO v_billion
                    FROM DUAL;
                END IF;

                --Provided that instead of two numbers for e.g. 342902132143, i.e. only  3 is left,
                --then convert it into words.
                IF (n_len = 12) THEN
                  IF (SUBSTR(SUBSTR(n_input,
                                    1,
                                    (n_len - LENGTH(SUBSTR(n_input, -11)))),
                             1,
                             2) != 0) THEN
                    SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                         1,
                                                         (n_len -
                                                         LENGTH(SUBSTR(n_input,
                                                                        -11)))),
                                                  1,
                                                  2),
                                           'j'),
                                   'Jsp') || ' Billion '
                      INTO v_billion
                      FROM DUAL;
                  END IF;
                END IF;

                --Provided that the length of the number is more than 13 and both the last two numbers are not zeros,then
                --convert those two into words. This makes up a figure of trillion.
                IF (n_len > 13) THEN
                  IF (SUBSTR(SUBSTR(n_input,
                                    1,
                                    (n_len - LENGTH(SUBSTR(n_input, -13)))),
                             -2) != 0) THEN
                    SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                         1,
                                                         (n_len -
                                                         LENGTH(SUBSTR(n_input,
                                                                        -13)))),
                                                  -2),
                                           'j'),
                                   'Jsp') || ' Trillion '
                      INTO v_trillion
                      FROM DUAL;
                  END IF;

                  --Provided that instead of two numbers for e.g. 76342902132143, i.e. only  7 is left,
                  --then convert it into words.
                  IF (n_len = 14) THEN
                    IF (SUBSTR(SUBSTR(n_input,
                                      1,
                                      (n_len - LENGTH(SUBSTR(n_input, -13)))),
                               1,
                               2) != 0) THEN
                      SELECT TO_CHAR(TO_DATE(SUBSTR(SUBSTR(n_input,
                                                           1,
                                                           (n_len -
                                                           LENGTH(SUBSTR(n_input,
                                                                          -13)))),
                                                    1,
                                                    2),
                                             'j'),
                                     'Jsp') || ' Trillion '
                        INTO v_trillion
                        FROM DUAL;
                    END IF;
                  END IF;
                END IF;
              END IF;
            END IF;
          END IF;
        END IF;
      END IF;

      --Provided the figure is in trillions, make sure that the converted trillion is stored first.
      IF (v_trillion IS NOT NULL) THEN
        v_words := v_trillion || ' ';
      END IF;

      --Provided the figure is in billions, make sure that the converted billion is stored first or after previous one.
      IF (v_billion IS NOT NULL) THEN
        v_words := v_words || v_billion || ' ';
      END IF;

      --Provided the figure is in millions, make sure that the converted million is stored first or after previous one.
      IF (v_million IS NOT NULL) THEN
        v_words := v_words || v_million || ' ';
      END IF;

      --Provided the figure is in crores, make sure that the converted crores is stored first or after previous one.
      IF (v_crores IS NOT NULL) THEN
        v_words := v_words || v_crores || ' ';
      END IF;

      --Provided the figure is in lacs, make sure that the converted lacs is stored first or after previous one.
      IF (v_lacs IS NOT NULL) THEN
        v_words := v_words || v_lacs || ' ';
      END IF;

      --Provided the figure is in thousands, make sure that the converted thousands is stored first or after previous one.
      IF (v_thsd IS NOT NULL) THEN
        v_words := v_words || v_thsd || ' ';
      END IF;

      --Provided the figure is in hundreds, make sure that the converted hundreds is stored first or after previous one.
      IF (v_hdd IS NOT NULL) THEN
        v_words := v_words || v_hdd;
      END IF;
      --Provided that the number is less than hundred but the number is non-zero, convert the number into words
    ELSIF (n_len < 3 AND n_input != 0) THEN
      SELECT TO_CHAR(TO_DATE(n_input, 'j'), 'Jsp') INTO v_words FROM DUAL;
    END IF;

    --Provided that the input is zero,then hardcoded value "Zero" is passed.
    IF n_input = 0 THEN
      v_words := 'Zero';
    END IF;

    --For Paise, the place of decimal point should be present and the input number is not an whole number, round the figure to 2 decimal points and convert it into words and
    --add it to the already existing value in v_words,variable.
    --For paise
    IF (n_place > 0 AND input != 0) THEN
      SELECT TO_CHAR(TO_DATE(RPAD(SUBSTR(n_roundedinput,
                                         n_place + 1,
                                         LENGTH(n_roundedinput) - n_place),
                                  2,
                                  0),
                             'j'),
                     'Jsp') || ' Paise '
        INTO v_paise
        FROM DUAL;

      v_words := v_words || ' And ' || v_paise;
    END IF;

    --Add a string 'Only' to the value in the variable,v_words, which is to be passed out of the function.
    RETURN v_words || ' Only';
  END;
/
