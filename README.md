Project: Crypto Wallet

Description:

This project will develop a crypto wallet application that allows users to track their crypto holdings and analyze their wallet performance.

Requirements:

1. Get the Latest Prices:

[ ] Implement functionality to retrieve the latest prices for user tokens from the CoinCap API.
[ ] Make the price retrieval process recurrent with a configurable time interval.
[ ] Utilize multithreading to fetch prices for 3 tokens simultaneously for efficiency.

2. Save the Information:

[ ] Design a database schema to store user wallet information, including token symbol, quantity, and price.
[ ] Develop logic to save the retrieved token prices to the database.

3. Add Asset to the Wallet:

[ ] Implement functionality for users to add new assets (tokens) to their wallet, specifying symbol, price, and quantity.

4. Show Wallet Information:

[ ] Create an API endpoint that returns the user's wallet information in JSON format.
[ ] The response should include details for each token (symbol, price, quantity, value) and the total wallet value in USD.
5. Wallet Evaluation:

[ ] Develop functionality to evaluate the user's wallet performance on a specific date or for the current day.
[ ] The evaluation should accept a list of tokens with symbol, quantity, and average buy price as input (JSON format).
[ ] The program should calculate the following:
Total wallet value in USD.
Best performing asset (symbol and percentage appreciation).
Worst performing asset (symbol and percentage depreciation).
[ ] The evaluation results should be returned as a JSON response.
Project Key Points:

Project Structure:

[ ] Define entities (models) for User and Wallet with their relationships.
[ ] Implement logic for managing user wallets and their associated assets.
Assets Recurrent Update:

[ ] Configure the price update frequency based on user preference.
[ ] Utilize multithreading to retrieve prices for multiple tokens concurrently.
Wallet Evaluation:

[ ] Calculate the total wallet value based on current token prices.
[ ] Compare current prices with average buy prices to determine performance for each token.
[ ] Identify the best and worst performing assets based on percentage change.

General:

[ ] Select and implement an SQL database to store wallet and token information.
[ ] Design API endpoints to handle requests and responses in JSON format.