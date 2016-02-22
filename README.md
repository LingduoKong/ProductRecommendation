# readme

This is a product recommendation system based on Walmart Open API.

### How to compile

- Download zip file
- $ unzip ${file name}
- $ cd ${file name}
- $ mvn clean install
- $ mvn exec:java -Dexec.mainClass="com.LingduoKong.app.Main"

### How to test

- $ mvn test

### Work flow

- Get user keyword by command line and search result from API
- From the result, pick up the most matching item, name it A
- Get all recommendation items based on the A
- Choose top 10 items related to A
- Sort these items according to user review

### Over view

- Create a Query class as parent for all http query.
- Use OkHttpClint to handle api query.

