Usage
=====

.. _installation:

Installation
------------

To use the project, first build the project:

.. code-block:: console

   $ mvn package

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



