import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//Github Link
//https://github.com/rogueinc7/comp-sec-CA2.git

public class Main
{
    public static void main(String[] args)
    {

        Scanner keyboard = new Scanner(System.in);
        String[] menu =
        {
                "1. Encrypt a File",
                "2. Decrypt a File",
                "3. Exit"
        };

        int menuChoice = 0;
        do
        {
            {
                Menu.displayMenu(menu, "Encrypt/Decrypt Data Application");
                try {
                    menuChoice = Menu.getMenuChoice(menu.length);
                    switch (menuChoice) {
                        case 1:
                            System.out.println("Enter the filename to encrypt:");
                            String plainTextFilename = keyboard.next();
                            plainTextFilename = Validation.validateFileName(plainTextFilename);

                            var secretKey = Encryption.generateAESKey();
                            String encodedKey = java.util.Base64.getEncoder().encodeToString(secretKey.getEncoded());
                            var encryptedText = Encryption.encryptTextFileContents(plainTextFilename, secretKey);

                            //https://www.w3schools.com/java/java_files_create.asp
                            try (FileWriter fileWriter = new FileWriter("ciphertext.txt")) {
                                for (String line : encryptedText) {
                                    fileWriter.write(line + System.lineSeparator());
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("The File encrypted successfully.");
                            System.out.println("The AES key (base64-encoded) is: " + encodedKey);
                            System.out.println("The encrypted content has been saved to: ciphertext.txt");
                            break;
                        case 2:
                            System.out.println("Enter the filename to decrypt:");
                            String encryptedFilename = keyboard.next();
                            encryptedFilename = Validation.validateFileName(encryptedFilename);

                            System.out.println("Enter the AES key (base64-encoded):");
                            String base64Key = keyboard.next();

                            try {
                                var secretKey1 = Encryption.loadAESKey(base64Key);
                                var decryptedText = Encryption.decryptTextFileContents(encryptedFilename, secretKey1);


                                try (FileWriter fileWriter = new FileWriter("plaintext.txt")) {
                                    for (String line : decryptedText) {
                                        fileWriter.write(line + System.lineSeparator());
                                    }
                                }
                                catch (IOException e) {
                                    throw new RuntimeException("Error writing to plaintext.txt: " + e.getMessage());
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid AES key. Please ensure the key is base64-encoded.");
                            } catch (Exception e) {
                                System.out.println("An error occurred during decryption: " + e.getMessage());
                            }
                            System.out.println("The file was decrypted successfully.");
                            System.out.println("The decrypted content has been saved to: plaintext.txt");
                            break;
                        case 3:
                            System.out.println("This program has now been closed, Bye!");
                            System.exit(0);
                            break;
                        default:
                            break;
                    }
                }
                catch(Exception e)
                {
                    System.out.println("Invalid - Please enter a valid option");
                }
            }
        }
        while (menuChoice != 0);
    }
}