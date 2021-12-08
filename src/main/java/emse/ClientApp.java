package emse;


import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;

import java.io.*;
import java.util.List;

import static java.lang.Thread.sleep;

public class ClientApp {

    public static void main(String[] args) {

        try {
            String nameBucket = "bucket3688";
            String filePathString = "C:\\Users\\caill\\Desktop\\Cours Mines\\Majeure\\cours 2a info\\Cloud\\sales-2021-01-02.csv";
            String nameFile = "sales-2021-01-02.csv";
            String queueURl ="https://sqs.us-west-2.amazonaws.com/528939267914/";
            String pathForCopyObject = "C:\\Users\\caill\\Desktop\\Cours Mines\\Majeure\\cours 2a info\\Cloud\\ResultatEC2";

            // Create a bucket for the Web-Queue-Worker architecture

            S3ControllerCreate.main(new String[]{nameBucket});

            sleep(4000); // We add some delay in order to do not have any error because of the time it takes to create the bucket


            // Check if the csv file exist in the local hard-disk

            System.out.println("\n" + "Checking if the csv file exist in the local hard-disk");

            File file = new File(filePathString);

            if (file.isFile()) {
                System.out.println("\n" + "csv file exist");
            } else {
                // ajouter une erreur
                System.out.println("\n" + "csv file does not exist");
            }

            sleep(2000); // We add some delay in order to do not have any error because of the time it takes to check if the csv file exist

            //Write the file into a bucket in the Amazon S3

            S3ControllerPutObject.main(new String[]{nameBucket, nameFile, filePathString});

            //Send a message to the Inbox queue with the bucket and file names

            SqsClient sqsClient = SqsClient.builder()
                    .build();

            SQSSendMessage.sendMessages(sqsClient, "INBOX", nameBucket, nameFile);

            sleep(2000); // We add some delay in order to do not have any error because of the time it takes to send a message to the Inbox queue with the bucket and file names


            boolean running = true;
            Long lastRecordedTime=System.currentTimeMillis();
            while (running) {
                //we check if we receive a message every minute
                Long timer=System.currentTimeMillis();
                if (timer-lastRecordedTime>60000){
                    // get the message content
                    List<Message> messages = SQSRetrieveMessage.retrieveMessages(sqsClient,queueURl + "INBOX","INBOX");
                    // Delete the message
                    SQSDeleteMessage.deleteMessages(sqsClient, queueURl,messages);

                    S3ControllerGetObject.main( new String[]{ nameBucket, nameFile, pathForCopyObject} ) ;

                }


            }


        } catch (InterruptedException ex) {

            ex.printStackTrace();

        }
    }
}



