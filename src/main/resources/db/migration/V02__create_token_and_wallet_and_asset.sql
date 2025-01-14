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

CREATE TABLE WALLET
(
    `ID`             BIGINT(10) PRIMARY KEY AUTO_INCREMENT,
    `EMAIL`          VARCHAR(50) UNIQUE                                             NOT NULL,
    `PUBLIC_ADDRESS` VARCHAR(200) UNIQUE                                            NOT NULL,
    `PRIVATE_KEY`    VARCHAR(200) UNIQUE                                            NOT NULL,
    `TOTAL`          DECIMAL(40, 20)                                                NOT NULL,
    `DT_CREATED`     DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    `DT_UPDATED`     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE ASSET
(
    `ID`         BIGINT(10) PRIMARY KEY AUTO_INCREMENT,
    `QUANTITY`   DECIMAL(40, 20)                                                NOT NULL,
    `PRICE`      DECIMAL(40, 20)                                                NOT NULL,
    `VALUE`      DECIMAL(40, 20)                                                NOT NULL,
    `ID_WALLET`  BIGINT(10)                                                     NOT NULL,
    `ID_TOKEN`   INT                                                            NOT NULL,
    `DT_CREATED` DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    `DT_UPDATED` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (`ID_WALLET`) REFERENCES WALLET (`ID`),
    FOREIGN KEY (`ID_TOKEN`) REFERENCES TOKEN (`ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

CREATE INDEX IDX_ASSET_ID ON ASSET (ID);
CREATE INDEX IDX_ASSET_WALLET_ID ON ASSET (ID_WALLET);