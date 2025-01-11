CREATE TABLE GLOBAL_PARAMETER
(
    `gp_key`     VARCHAR(30) PRIMARY KEY,
    `gp_value`   VARCHAR(100)                                                   NOT NULL,
    `gp_comment` VARCHAR(100)                                                   NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4;

INSERT INTO GLOBAL_PARAMETER (gp_key, gp_value, gp_comment)
VALUES ('COIN_CAP_BASE_URL', 'https://api.coincap.io/v2/', 'Base URL to access CoinCap API.'),
       ('COIN_CAP_ASSET_TIME_INTERVAL', '1', 'Number to follow COIN_CAP_ASSET_PERIOD for CoinCap search.'),
       ('COIN_CAP_ASSET_PERIOD', 'm', 'Default value for get asset history. m - minutes; h - hours; d - days'),
       ('ASSET_UPDATE_PERIOD', '5', 'Frequency, in minutes, which the prices will be checked.')
;
