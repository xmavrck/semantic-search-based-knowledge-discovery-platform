# Python package requirements #
- sudo apt-get install python3-pip
- pip3 install nltk
- pip3 install flask
- pip3 install scikit-learn
- pip3 install scipy
- pip3 install pandas  
Then open python3 and install the packages using below commands in terminal.  
python3  
>>> import nltk  
>>> nltk.download('all')  



# Environment Variables Path #


We have 4 projects located at   
https://git.xenonstack.com/consulting/knowledge-based-big-data-discovery-using-semantic-analysis-ontology/tree/master  



Config file is located at src/main/resources/config.properties for every project.   
We don’t need config file for python-mlib-api,because corpus data path is set at classpath.  

Note: Environment variable should point to the config file path of particular project.  
# Web-Oauth-Server  
Variable Name: SEMANTIC_ANALYSIS_OAUTH_CONFIG  

Variables need to change in config file   

###mongo related:
- mongo.hostname
- mongo.port

###mail server config
- semanticanalysis.oauth.emailId
- semanticanalysis.oauth.password
- semanticanalysis.oauth.emailHostName
- semanticanalysis.oauth.emailPortNo


### oauth server config
- semanticanalysis.oauth.hostname=http://localhost:8091

### Webclient Config
- semanticanalysis.hostname=http://localhost:8092


# Web Crawler
Variable Name: SARAH_WEB_CRAWLER_CONFIG  

Variables need to change in config file :  
###mongo config
- mongo.hostname
- mongo.port

### Hadoop Config
- hadoop.username=root
- hdfs.uri=hdfs://172.16.0.12:9000
- hdfs.rootDir=/usr/sarah

### Kafka Config
- kafka.broker.address=localhost:9092
- kafka.topic=semantic_analysis_events
- linger.ms=10
- kafka.producer.config= /home/dev/workspace/semantic-analysis/web-crawler/src/main/resources/producer.properties

### zips files temp path
- zip.files.tempath=/home/dev/Misc/zip-file-temp-path

### fuseki config
- fuseki.host=
- fuseki.port=
- fuseki.dataset=
- fuseki.username=
- fuseki.password=

### python api configs (used to build sparql query)
- python.host=
- python.port=

# Web Client #
- Variable Name: SEMANTIC_ANALYSIS_WEB_CLIENT_CONFIG

###DATABASE RELATED
- mongo.hostname=localhost
- mongo.port=27017

###APP RELATED(address of web client)
- semantic.hostname=http://localhost:8092

###LOGIN COOKIE path
- cookieDomainPath=.xenonstack.com

###oauth configs
- oAuth.rest.baseUrl=http://knowledge-discovery-oauth.staging.xenonstack.com
- oAuth.baseUrl=knowledge-discovery-oauth.staging.xenonstack.com/oauth
- oAuth.callbackUrl=http://knowledge-discovery-client.staging.xenonstack.com/dashboard

###webclient hostname(web client address)
- semanticanalysis.hostname=http://knowledge-discovery-client.staging.xenonstack.com/dashboard

### OAUth Consumer Key And Secret

- consumerKey=201700606FRQIYMsR4UWhgpkzXBUTWzx8tGZlVbh9QYU8ly3TGE5BgB7mM1488355061925f6cd3c0b80c2465397087b3c2ca2fb0d

- consumerSecret=dSDkA8HZV5ZT4KQ8c28DvGlBTrD8q25IbJLhhayd4gI5qGEw0c14883550619261164fdc7d85e411bb5658dc7c07aaee7  

Update the path of web crawler host in js config
- web-client/src/main/resources/static/js/config.js  

var BASE_URL="http://knowledge-discovery-crawler.staging.xenonstack.com/";  

Change this variable name to web crawler domain name  


# Real time Data-processor  
Variable Name: SARAH_SPARK_STREAMING_CONFIG  

### kafka properties
- kafka.topic=semantic_analysis_events
- metadata.broker.list=localhost:9092

###hdfs properties
- hdfs.username=root

###python machine learning api's
- classification.mlib.host=localhost
- classification.mlib.port=5000

###ruby
- ruby.temppath=/home/dev/workspace/ruby-script

### fuseki config
- fuseki.host=localhost
- fuseki.port=3030
- fuseki.dataset=semantic-analysis
- fuseki.username=admin
- fuseki.password=pw

### nlp api
nlp.api.host=localhost
nlp.api.port=8093
