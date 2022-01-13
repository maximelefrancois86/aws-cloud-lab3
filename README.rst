.. raw:: html

   <!-- ABOUT THE PROJECT -->

About The Project
=================

`Lab
instructions <https://gnardin.pages.emse.fr/website/cloud/2021Fall/lab/lab3.html>`__

`Lab’s
answers <https://github.com/maxant38/aws-cloud-lab3/blob/master/questions.md>`__

This project is licenced under the terms of the MIT licence

Project participants:
---------------------

-  Caille Maxence - maxence.caille@etu.emse.fr
-  Mathieu Delbos - mathieu.delbos@etu.emse.fr

Objective:
----------

-  Developping an application based on the Web-Queue-Worker architecture
   to summarize sale transactions from a retailer in a Cloud
   environment.

Use the project
---------------

Build the project with Maven :

.. code:: bash

   mvn package

Programs to execute : - EC2Worker.java - EC2CLient.java

What’s in the github repository:
--------------------------------

The application is divided in two main components: the Client and the
Worker.

The Client
~~~~~~~~~~

File :
`ClientApp.java <https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/EC2Worker.java>`__

The Client is responsible for:

-  reading the CSV file

-  upload it into the cloud

-  send a message to the Worker signaling that there is a file ready to
   be processed

-  wait until it receives a message from the Worker that the
   summarization was completed, and download the resulting file.

Other Classes used in the Client component :
`S3ControllerCreate <https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/S3ControllerCreate.java>`__,
`S3ControllerPutObject <https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/S3ControllerCreate.java>`__,
`SQSSendMessage <https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/SQSSendMessage.java>`__,
`SQSRetrieveMessage <https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/SQSRetrieveMessage.java>`__,
`SQSDeleteMessageClient <https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/SQSDeleteMessageClient.java>`__,
`S3ControllerGetObject <https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/S3ControllerPutObject.java>`__

The Worker
~~~~~~~~~~

File :
`EC2Worker.java <https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/EC2Worker.java>`__

The Worker is responsible for :

-  wait for a message from the Client

-  once the message is received with the name of the file to process,
   read the file

-  calculate (a) the Total Number of Sales, (b) the Total Amount Sold
   and (c) the Average Sold per country and per product

-  write a file in the cloud

-  send a message with the name of the file to the Client

-  wait for another message

Other Classes used in the Worker component :
`S3ControllerGetObject <https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/S3ControllerPutObject.java>`__,
`SQSSendMessage <https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/SQSSendMessage.java>`__,
`S3ControllerPutObject <https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/S3ControllerCreate.java>`__,
`CSVParser <https://github.com/maxant38/aws-cloud-lab3/blob/master/src/main/java/emse/CSVParser.java>`__

Built With:
-----------

-  `AWS <https://aws.amazon.com/fr/>`__
-  `Java <https://www.java.com/fr/>`__
