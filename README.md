# Introduction #

This project is a platform where we automate the processing of scrapping websites and then applying semantic analysis on that and provide easy ontology and feature based search for users.User initiate his Web Scrappy Jobs by entering universities urls and then submits it to our web crawler service which uses java.util.concurrent packages to scrap data from university websites concurrently and save it to HDSF and generate Kafka event to Kafka Topic.So Spark Streaming is subscribed to Kafka Topic and it reads the event as soon as Kafka writes the events(publish-subscribe model) and then it cleans the webpage data and then running classificaton algorithms to identify that whether the extracted page is faculty cv or not using supervised learning and  and extracting important feature like name,email,university,location etc from the extracted page using CoreNLP techniques and in last converting all data into RDF(Resource Descriptor Framework) and store it in our JENA triple store.  
Then User searches for any research area in any particular university, so we used concept of Semantic Search instead of keyword based search using Ontology to expand the query and give best results to the user. 

# Basic Requirements #
- MongoDB 3.2.9
- JDK 1.8 (oracle)
- Maven 3.3.9
- Hadoop (version :- 2.6 )
- Spark 1.6.0
- Python 3.4.3
- Apache Kafka 0.9.0.1-scala-2.10 
- Zookeeper
- ruby 2.2.3p173 (2015-08-18 revision 51636) [x86_64-linux]  
https://www.digitalocean.com/community/tutorials/how-to-install-ruby-on-rails-with-rbenv-on-ubuntu-14-04

- Anystyle -parser (Follow steps from here)  
https://github.com/inukshuk/anystyle-parser

- Apache Jena Fuseki Server 2.5.0 ( To store RDF Triples ).

# stanford nlp package

We need to install stanforf-srparser models into our local maven repository.This jar is located in jars folder  
and then use the command given below.
```
mvn install:install-file -Dfile=location-to-this-path/stanford-srparser-2014-10-23-models.jar -DgroupId=edu.stanford.nlp -DartifactId=stanford-srparser -Dversion=3.5.2 -Dpackaging=jar
```

# Installation #

* Please set these environment variables in your bashrc or /etc/environment file  
- SARAH_SPARK_STREAMING_CONFIG=path/to/spark-config  
- SEMANTIC_ANALYSIS_WEB_CLIENT_CONFIG=path/to/web-client  
- SARAH_WEB_CRAWLER_CONFIG=path/to/web-crawler-config  
- SEMANTIC_ANALYSIS_OAUTH_CONFIG=path/to/oauth-config  


There are six modules of this project.  
* Web Crawler (REST API to scrap websites and store pages in HDFS)  
```
mvn clean install
```
```
java  -Xmx2g -jar target/web-crawler-1.0.0-SNAPSHOT.jar
```

* Web OAuth ( Our OAuth 1.0a Server )
```
mvn clean install
```
```
java -jar target/web-oauth-server-1.0.0-SNAPSHOT.jar
```

* Web Client ( Web Client for user,employee and admin )
```
mvn clean install
```
```
java -jar target/web-client-1.0.0-SNAPSHOT.jar
```

* Stanford NLP API ( Used to extract from the Document )
```
mvn clean install
```
```
java -Xmx8g -jar target/stanford-nlp-api-1.0.0-SNAPSHOT.jar
```


* Data Processor ( real time spark streaming processing engine to extract knowledge from scrapped pages by data cleaning,classification techniques )
```
mvn clean install
```
Then go to SPARK_HOME(spark installation directory).
```
./bin/spark-submit --driver-memory 8g --num-executors 4 --class com.sarah.realtime.spark.data_processor.App -your-base-directory/semantic-analysis/real-time-data-processor/target/data-processor-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```   
For run in cluster mode
```
 /home/spark/spark/bin/spark-submit --class com.sarah.realtime.spark.data_processor.App --master spark://172.31.10.28:6066/  --deploy-mode cluster  hdfs://172.31.12.216:9000/usr/sarah/data-processor-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```
* Python Mlib Api( These are python machine learning algo api services )
```
python3 mlib_api.py
```
