CREATE TABLE WALLET
(
    `ID`             BIGINT(10) PRIMARY KEY AUTO_INCREMENT,
    `PUBLIC_ADDRESS` VARCHAR(50)                                                    NOT NULL,
    `PRIVATE_KEY`    VARCHAR(50)                                                    NOT NULL,
    `TOTAL`          DECIMAL(40, 20)                                                NOT NULL,
    `DT_CREATED`     DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    `DT_UPDATED`     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE ASSET
(
    `ID`         BIGINT(10) PRIMARY KEY AUTO_INCREMENT,
    `ID_ASSET`   BINARY(16) DEFAULT (UUID_TO_BIN(UUID())),
    `SYMBOL`     VARCHAR(5)                                                       NOT NULL,
    `QUANTITY`   DECIMAL(40, 20)                                                  NOT NULL,
    `PRICE`      DECIMAL(40, 20)                                                  NOT NULL,
    `VALUE`      DECIMAL(40, 20)                                                  NOT NULL,
    `ID_WALLET`  BIGINT(10)                                                       NOT NULL,
    `DT_CREATED` DATETIME   DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    `DT_UPDATED` DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (`ID_WALLET`) REFERENCES WALLET (`ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

CREATE INDEX IDX_ASSET_ID ON ASSET (ID);
CREATE INDEX IDX_ASSET_WALLET_ID ON ASSET (ID_WALLET);