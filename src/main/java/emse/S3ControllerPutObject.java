package emse;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class S3ControllerPutObject {

        public static void main(String[] args) {

            System.out.println("\n Start uploading the object to the bucket");

            final String USAGE = "\n" +
                    "Usage:\n" +
                    "  <bucketName> <objectKey> <objectPath> \n\n" +
                    "Where:\n" +
                    "  bucketName - the Amazon S3 bucket to upload an object into.\n" +
                    "  objectKey - the object to upload (for example, book.pdf).\n" +
                    "  objectPath - the path where the file is located (for example, C:/AWS/book2.pdf). \n\n" ;

            if (args.length != 3) {
                System.out.println(USAGE);
                System.exit(1);
            }

            String bucketName =args[0];
            String objectKey = args[1];
            String objectPath = args[2];
            System.out.println("Putting object " + objectKey +" into bucket "+bucketName);
            System.out.println("  in bucket: " + bucketName);

            S3Client s3 = S3Client.builder()
                    .build();

            String result = putS3Object(s3, bucketName, objectKey, objectPath);
            System.out.println("Tag information: "+result);
            s3.close();

            System.out.println("\n Done ");
        }


        public static String putS3Object(S3Client s3,
                                         String bucketName,
                                         String objectKey,
                                         String objectPath) {

            try {

                Map<String, String> metadata = new HashMap<>();
                metadata.put("fichier-csv-lab3", "lab3");

                PutObjectRequest putOb = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .metadata(metadata)
                        .build();

                PutObjectResponse response = s3.putObject(putOb,
                        RequestBody.fromBytes(getObjectFile(objectPath)));

                return response.eTag();

            } catch (S3Exception e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
            return "";
        }

        // Return a byte array
        private static byte[] getObjectFile(String filePath) {

            FileInputStream fileInputStream = null;
            byte[] bytesArray = null;

            try {
                File file = new File(filePath);
                bytesArray = new byte[(int) file.length()];
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(bytesArray);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return bytesArray;
        }

}

