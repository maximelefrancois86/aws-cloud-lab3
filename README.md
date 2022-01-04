<!-- ABOUT THE PROJECT -->
# About The Project

## Project participants:

- Caille Maxence - maxence.caille@etu.emse.fr
- Mathieu Delbos - mathieu.delbos@etu.emse.fr

## Objective:
-  Developping an application based on the Web-Queue-Worker architecture

[The lab](https://gnardin.pages.emse.fr/website/cloud/2021Fall/lab/lab3.html)

## What's in the github repository:

The application is divided in two main components: the Client and the Worker.

### The Client
File : [ClientApp.java](https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/EC2Worker.java)
The Client is responsible for
-	reading the CSV file
- upload it into the cloud
- send a message to the Worker signaling that there is a file ready to be processed
- wait until it receives a message from the Worker that the summarization was completed, and
- download the resulting file.

Other Classes used in the Client component : 
[S3ControllerCreate](https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/S3ControllerCreate.java),
[S3ControllerPutObject](https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/S3ControllerCreate.java),
[SQSSendMessage](https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/SQSSendMessage.java),
[SQSRetrieveMessage](https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/SQSRetrieveMessage.java),
[SQSDeleteMessageClient](https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/SQSDeleteMessageClient.java),
[S3ControllerGetObject](https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/S3ControllerPutObject.java)

### The Worker
File : [EC2Worker.java](https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/EC2Worker.java)
The Worker is responsible for :
-	wait for a message from the Client

-	once the message is received with the name of the file to process, read the file

-	calculate (a) the Total Number of Sales, (b) the Total Amount Sold and (c) the Average Sold per country and per product

-	write a file in the cloud

-	send a message with the name of the file to the Client

-	wait for another message


Other Classes used in the Worker component :
[S3ControllerGetObject](https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/S3ControllerPutObject.java),
[SQSSendMessage](https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/SQSSendMessage.java),
[S3ControllerPutObject](https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/S3ControllerCreate.java),
[CSVParser](https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/CSVParser.java)


## Built With:

* [AWS](https://aws.amazon.com/fr/)
* [Java](https://www.java.com/fr/)











