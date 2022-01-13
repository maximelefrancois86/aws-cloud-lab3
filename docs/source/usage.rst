Usage
=====

.. _installation:

Installation
------------

To use the project, first build the project:

.. code-block:: console

   $ mvn package
   
 
.. note::

   The Client and Worker apps need to be launch separately on different machines. However it is possible to launch the Worker on an EC2 instance


Using the Worker
----------------

EC2 Worker: a Java application (with one class that includes main method) that runs on an EC2 instance. It does the following tasks:

-  Create an AWS SQS queue named Inbox to receive messages from clients with ``createQueue()`` function

-  Create an AWS SQS queue named Outbox to send messages to clients with ``createQueue()`` function

-  Check for a message in the Inbox queue every 1 minute with ``receiveMessages`` function

-  If there is a message, retrieve and delete the message from the queue with ``deleteMessages(sqsClient, inbox, messages)`` function

-  Retrieve the file from the Amazon S3 whose bucket and name are indicated in the message body with ``S3ControllerGetObject`` class

-  Calculate (a) the **Total Number of Sales**, (b) the **Total Amount Sold** and (c) the **Average Sold** per country and per product with ``CSVParser`` class

-  Write the results in a file in the Amazon S3 with  ``S3ControllerPutObject`` class


-  Send a response message in the Outbox queue to the client with the name of the incoming file and the output file containing the result with ``sendMessages()`` from ``SQSSendMEssages`` Class


Using the Client
----------------


**Client App** : a Java application (with one class that includes main method) that is hosted on your local machine (not on the cloud). 
The Client App does the following steps:

-  Read the hourly sales file from the local hard-disk (For this project, the file sales-2021-01-02.csv)

-  Write the file into a bucket in the Amazon S3 with ``S3ControllerCreate`` class for creating the bucket and ``S3ControllerPutObject`` for writing the file.

-  Send a message to the Inbox queue with the bucket and file names with ``sendMessages()`` function from ``SQSSndMessage`` class

-  Wait until a response message is received with the results in the Outbox queue. The Client has to check the queue every 1 minute for messages with ``retrieveMessage()`` function from ``SQSRetrieveMessage`` class

-  Once the message is received, retrieve the message content and delete the message from the Outbox queue with  ``deleteMessageClient()`` function from ``SQSDeleteMEssageClient`` class

-  Read the file with the results from the Amazon S3 with ``S3ControllerGetObject`` class 







 
















