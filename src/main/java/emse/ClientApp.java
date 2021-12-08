package emse;


import java.io.*;

public class ClientApp {

    public static void main(String[] args) {

        String nameBucket = "bucket3688480748";
        String filePathString = "C:\\Users\\caill\\Desktop\\Cours Mines\\Majeure\\cours 2a info\\Cloud\\sales-2021-01-02.csv";


        // Create


        // Check if the csv file exist in the local hard-disk

        System.out.println("\n" + "Checking if the csv file exist in the local hard-disk");

        File file = new File(filePathString);

        if(file.isFile()){
            System.out.println("\n" + "csv file exist");
        }
        else {
            // ajouter une erreur
            System.out.println("\n" + "csv file does not exist");
        }

        //Write the file into a bucket in the Amazon S3




    }




}
