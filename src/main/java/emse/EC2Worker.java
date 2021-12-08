package emse;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

public class EC2Worker {

    public static String createQueue(SqsClient sqsClient, String queueName ) {

        try {
            System.out.println("\nCreate Queue");

            CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                    .queueName(queueName)
                    .build();

            sqsClient.createQueue(createQueueRequest);

            System.out.println("\nGet queue url");

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

        System.out.println("\nReceive messages");

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

    public static void main (String args []){
        SqsClient sqsClient = SqsClient.builder()
                .build();
        String inbox=createQueue(sqsClient, "INBOX");
        String outbox=createQueue(sqsClient, "OUTBOX");

        Long lastRecordedTime=System.currentTimeMillis();

        while(true){
            Long timer=System.currentTimeMillis();
            //we receive messages every minute
            if (timer-lastRecordedTime>60000){
                List<Message> messages=receiveMessages(sqsClient,inbox);
                lastRecordedTime=timer;
                if ( messages!=null){
                    try {
                        //extracting the bucket name
                        String bucket=messages.get(0).body();
                        //extracting the file name
                        String fileName=messages.get(1).body();
                        System.out.println("Bucket name"+bucket);
                        System.out.println("File name"+fileName);
                        //Getting the cvs file
                        S3ControllerGetObject.main(new String[]{bucket,fileName,""});
                        //Anaylizing the csv file
                        S3ControllerAnalyseData.main(new String[]{fileName});
                        deleteMessages(sqsClient,inbox,messages);
                    } catch (SqsException e) {
                        System.err.println(e.awsErrorDetails().errorMessage());
                        System.exit(1);
                    }
                }
            }
        }
    }
}
