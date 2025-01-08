CREATE TABLE GLOBAL_PARAMETER
(
    `gp_key`     VARCHAR(30) PRIMARY KEY,
    `gp_value`   VARCHAR(100) NOT NULL,
    `gp_comment` VARCHAR(100) NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO GLOBAL_PARAMETER (gp_key, gp_value, gp_comment)
VALUES ('COIN_CAP_BASE_URL', 'https://api.coincap.io/v2/', 'Base URL to access CoinCap API.'),
       ('ASSET_UPDATE_PERIOD', '10', 'Frequency, in seconds, which the prices will be checked.');
