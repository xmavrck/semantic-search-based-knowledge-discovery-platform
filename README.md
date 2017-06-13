# Introduction #

This project is a platform where we automate the processing of scrapping websites and then applying semantic analysis on that and provide easy ontology and feature based search for users.  

# Basic Requirements #
- MongoDB 3.2.9
- JDK 1.8 (oracle)
- Maven 3.3.9
- Hadoop (version :- 2.6 )
- Spark 1.6.0
- Apache Solr 5.1.0( we are not using this because it support only nt format , it doesn’t support rdf + ontology).
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

There are siz modules of this project.  
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
