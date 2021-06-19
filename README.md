Steps:

1. run `docker-compose up -d` to start kafka cluster 
    
    Control center : `http://localhost:9021/`
   
    Schema Registry: `http://localhost:8081/subjects`


2. Create Avro schema registry
    ```
    curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \ 
        --data '{"schema": " { \"type\" : \"record\", \"namespace\" : \"com.avro.model\", \"name\" : \"Customer\", \"version\" : \"1\", \"fields\" : [ {\"name\" : \"first_name\", \"type\":\"string\", \"doc\" :\"first name\"}, {\"name\" : \"last_name\", \"type\":\"string\", \"doc\" :\"last name\"}, {\"name\" : \"age\", \"type\":\"int\", \"doc\" :\"age\"} ] } "}' \
        http://localhost:8081/subjects/customer/versions
    ```
   
    Schema registry details:
   
        1. http://localhost:8081/subjects/customer/versions
        2. http://localhost:8081/subjects/customer/versions/1
   
3. To create model class using avro schema file
         
        Download Link :: https://archive.apache.org/dist/avro/avro-1.7.7/java/

        java -jar avro-tools-1.7.7.jar compile schema customer.avsc .

4.  Producer and Consumer commands
    
        bin/kafka-avro-console-producer --broker-list localhost:9092 --topic avro_topic --property value.schema=' { "type" : "record", "namespace" : "com", "name" : "Customer", "version" : "1", "fields" : [ {"name" : "first_name", "type":"string", "doc" :"first name"}, {"name" : "last_name", "type":"string", "doc" :"last name"} {"name" : "age", "type":"int", "doc" :"age"} ] } '

        bin/kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic avro-topic --from-beginning --property schema.registry.url=http://localhost:8081

5. API's
        
        http://localhost:8080/api/health
        
        Producer: http://localhost:8080/api/send?name=sunil

        Consumer : http://localhost:8080/api/consume
        