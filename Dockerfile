From openjdk:8
copy ./target/transactionprocessing-0.0.1-SNAPSHOT.jar transactionprocessing-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","transactionprocessing-0.0.1-SNAPSHOT.jar"]
