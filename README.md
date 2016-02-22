# readme

This is a product recommendation system based on Walmart Open API.

### How to compile

- Download zip file
- $ unzip ${file name}
- cd ${file name}
- mvn clean compile assembly:single
- java -cp target/walmart-recommendation-1.0-SNAPSHOT-jar-with-dependencies.jar com.LingduoKong.app.Main

### Work flow

- Get user keyword by command line and search result from API
- From the result, pick up the most matching item, name it A
- Get all recommendation items based on the A
- Choose top 10 items related to A
- Sort these items according to user review

