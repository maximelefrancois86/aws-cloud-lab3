package emse;


import java.io.*;

import static java.lang.Thread.sleep;

public class ClientApp {

    public static void main(String[] args) {

        try {
            String nameBucket = "bucket3688";
            String filePathString = "C:\\Users\\caill\\Desktop\\Cours Mines\\Majeure\\cours 2a info\\Cloud\\sales-2021-01-02.csv";
            String nameFile = "sales-2021-01-02.csv";

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
        }

        catch (InterruptedException ex) {

            ex.printStackTrace();

        }
    }
}



