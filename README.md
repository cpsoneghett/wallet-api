# Wallet-API

**Author:** Christiano Soneghett

## Project Stack:

- **Java 21**
- **Spring Boot 3.4.1**
- **MySQL 8.x**
- **Docker**

## How to Run The Application:

### 1. With Docker:

At the root directory, run:

```bash
docker-compose up --build
```

Then, the server might start at port 8081 as exposed in the docker-compose.yml. If not, after MySQL starts, you can run
the application directly from the project root with tests:

```bash
mvn spring-boot:run
```

## About the Features:

### Pre-Configurations:

Some configurable features will have a database global parameter (GLOBAL_PARAMETER table), which stores key-value pairs
that the application uses to dynamically adjust behavior when updated.

### 1) Feed the Token Table:

For all purposes, I'll refer to the cryptos individually as TOKENS and the cryptos in the user's wallets as ASSETS. Upon
startup, the Wallet-API will retrieve all tokens from the CoinCap API (https://api.coincap.io/v2/assets) and populate
the TOKEN table, if available.

### 2) Get The Last Prices (Scheduled):

The API will fetch the history from the CoinCap
API (https://api.coincap.io/v2/assets/bitcoin/history?interval=<dynamic_interval>) where <dynamic_interval> is, by
default, m1 (prices with a 1-minute interval). This value can be configured through an endpoint, which will update the
value in the GLOBAL_PARAMETER table.

To change the interval, you can send a request to the following URL:

```
POST http://localhost:8081/api/v1/scheduler/update
```

Payload:

```
20
```

About the concurrency, it was created an AsyncConfig.java and a SchedulerConfig.java to work with a DynamicScheduler.java class that
will configure the fixedRate to get the prices from the CoinCapAPI.

DynamicScheduler.java
```java
    @PostConstruct
    public void startScheduler() {
        long initialRate = getAssetUpdatePeriod();
        logger.info("Starting scheduler with initial rate: {} ms", initialRate);
        scheduleTask(initialRate);
    }

    public void scheduleTask(long fixedRate) {
        if (scheduledTask != null) {
            logger.info("Cancelling existing scheduled task before scheduling a new one.");
            scheduledTask.cancel(false);
        }


        logger.info("Scheduling task with a fixed rate of {} ms", fixedRate);
        scheduledTask = taskScheduler.schedule(tokenService::updateTokensHistoryList, new FixedRateTrigger(fixedRate));
        logger.info("Task scheduled successfully.");
    }
```

After the project starts, the scheduler starts and add the UpdateTokenHistory to be monitored constantly at a fixedRate (initially with 5min).
The tokens found to update will be added to a list of List<CompletableFuture<Void>> to be processed asynchronously.

### 3) Save the information:

The initial schemas for MySQL (excluding for tests) are being imported by Flyway scripts.

All the assets are being saved in a table ASSET that are associated to one WALLET.

### 4) Show Wallet Information:

Some wallet's info will be presented by the following URL:

```
GET http://localhost:8081/api/v1/wallet/<private_key>
```

To create a wallet, the only request needed:

```
POST http://localhost:8081/api/v1/wallet/create
```

Payload:

```
{
    "email": test@test.com
}
```

Response:

```
{
    "email": "test@test.com",
    "publicAddress": "mn2BvxoqFgJnHtd67qsw9SDXZLgAyxY2Ya",
    "privateKey": "cQLadJ7g9smorV5EdQHaBTyRG8i9W7ws4sJDf2cLeozyv1YdtAwZ",
    "createdAt": "2025-01-14T12:06:16.9631971"
}
```

### 5) Wallet evaluation:

The wallet evaluation will be presented by the following request:

```
POST http://localhost:8081/api/v1/wallet/evaluate
```

Payload:

```
BTC, 0.12345, 37870.5058
ETH, 4.89532, 2004.9774
```

Response:
```
{
    "total": 27646.002225875406871828029,
    "best_asset": "BTC",
    "best_performance": 154.72,
    "worst_asset": "ETH",
    "worst_performance": 60.34
}
```

## Possible improvements:

1) To implement API Security for endpoints;
2) Check more carefully API's performance and then maybe check the need to a load balancer;
3) Review the updates frequency and the accuracy of the values that will be updated in each wallet to avoid any
   incorrect value calculated or presented;
4) Better exception handling with ActionListeners;
5) Better responses in the Controllers, with a user message and a dev message, in case of some exception;
6) More unit and integrated tests;
