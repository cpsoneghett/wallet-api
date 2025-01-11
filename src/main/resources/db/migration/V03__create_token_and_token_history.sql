CREATE TABLE TOKEN
(
    `ID`                 INT PRIMARY KEY AUTO_INCREMENT,
    `ID_NAME`            VARCHAR(50)                                                    NOT NULL,
    `NR_RANK`            INT                                                            NOT NULL,
    `SYMBOL`             VARCHAR(10)                                                    NOT NULL,
    `NAME`               VARCHAR(50)                                                    NOT NULL,
    `SUPPLY`             DECIMAL(40, 20)                                                NULL,
    `MAX_SUPPLY`         DECIMAL(40, 20)                                                NULL,
    `MARKET_CAP_USD`     DECIMAL(40, 20)                                                NULL,
    `VOLUME_USD_24H`     DECIMAL(40, 20)                                                NULL,
    `PRICE_USD`          DECIMAL(40, 20)                                                NULL,
    `CHANGE_PERCENT_24H` DECIMAL(40, 20)                                                NULL,
    `VWAP_24H`           DECIMAL(40, 20)                                                NULL,
    `DT_CREATED`         DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    `DT_UPDATED`         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

CREATE INDEX IDX_TOKEN_ID ON TOKEN (ID);


CREATE TABLE TOKEN_HISTORY
(
    `ID`                   INT PRIMARY KEY AUTO_INCREMENT,
    `ID_TOKEN`             INT                                NOT NULL,
    `PRICE`                DECIMAL(40, 20)                    NULL,
    `DT_CREATED`           DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `DT_COINCAP_TIMESTAMP` TIMESTAMP                          NOT NULL,
    FOREIGN KEY (`ID_TOKEN`) REFERENCES TOKEN (`ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

CREATE INDEX IDX_TOKEN_HISTORY_ID ON TOKEN_HISTORY (ID);