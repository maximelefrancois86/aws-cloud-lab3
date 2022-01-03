package emse;

import com.opencsv.exceptions.CsvException;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.io.IOException;
import java.util.List;


public class EC2Worker {

    public static String createQueue(SqsClient sqsClient, String queueName) {

        try {
            System.out.println("\nCreating Queue");

            CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                    .queueName(queueName)
                    .build();

            sqsClient.createQueue(createQueueRequest);

            System.out.println("\nGetting queue url");

            GetQueueUrlResponse getQueueUrlResponse =
                    sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build());
            String queueUrl = getQueueUrlResponse.queueUrl();
            return queueUrl;

        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "";
    }

    public static List<Message> receiveMessages(SqsClient sqsClient, String queueUrl) {

        System.out.println("Checking for messages ...");

        try {
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(5)
                    .build();
            List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
            return messages;
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return null;
    }

    public static void deleteMessages(SqsClient sqsClient, String queueUrl, List<Message> messages) {

        System.out.println("\nDeleting the messages");

        try {
            for (Message message : messages) {
                DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(message.receiptHandle())
                        .build();
                sqsClient.deleteMessage(deleteMessageRequest);

                System.out.println("\n Done");
            }


        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static void main(String args[]) {
        SqsClient sqsClient = SqsClient.builder()
                .build();
        String inbox = createQueue(sqsClient, "INBOX");
        System.out.println("INBOX queue created");
        String outbox = createQueue(sqsClient, "OUTBOX");
        System.out.println("OUTBOX queue created");


        Long lastRecordedTime = System.currentTimeMillis();

        while (true) {
            Long timer = System.currentTimeMillis();
            //we cjeck for messages every 60 seconds
            if (timer - lastRecordedTime > 60000) {
                List<Message> messages = receiveMessages(sqsClient, inbox);


                lastRecordedTime = timer;
                if (messages.size() == 2) {
                    System.out.println("a message has been received");
                    try {
                        //extracting the bucket name
                        System.out.println("number of messages :" + messages.size());
                        String bucket = messages.get(0).body();
                        System.out.println("Bucket name : " + bucket);

                        //extracting the file name
                        String fileName = messages.get(1).body();
                        System.out.println("File name : " + fileName);
                        //Getting the cvs file
                        S3ControllerGetObject.main(new String[]{bucket, fileName, "ec2sales.csv"});
                        //Anaylizing the csv file
                        //S3ControllerAnalyseData.main(new String[]{"ec2sales.csv"});
                        try {
                            CSVParser.main(new String[]{"ec2sales.csv"});
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (CsvException e) {
                            e.printStackTrace();
                        }
                        deleteMessages(sqsClient, inbox, messages);
                        System.out.println("\n" + "Writing the file into a bucket in the Amazon S3");
                        S3ControllerPutObject.main(new String[]{bucket, "data.txt", "data.txt"});
                        System.out.println("\n" + "Sending a message to the Inbox queue with the bucket and file names");
                        String queueURl = "https://sqs.us-west-2.amazonaws.com/528939267914/";
                        SQSSendMessage.sendMessages(sqsClient, queueURl + "OUTBOX", bucket, "data.txt");

                    } catch (SqsException e) {
                        System.err.println(e.awsErrorDetails().errorMessage());
                        System.exit(1);
                    }

                }
            }
        }
    }
}
