import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class Encryption
{
    public static ArrayList<String> encryptTextFileContents(String filename, SecretKey secretKey) {
        ArrayList<String> encryptedText = new ArrayList<>();
        File inputFile = new File(filename);

        try (Scanner fileScanner = new Scanner(inputFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                encryptedText.add(encryptText(line, secretKey));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + filename);
        } catch (Exception ex) {
            System.out.println("Error while encrypting: " + ex.getMessage());
        }

        return encryptedText;
    }

    public static ArrayList<String> decryptTextFileContents(String filename, SecretKey secretKey) {
        ArrayList<String> decryptedText = new ArrayList<>();
        File inputFile = new File(filename);

        try (Scanner fileScanner = new Scanner(inputFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                decryptedText.add(decryptText(line, secretKey));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + filename);
        } catch (Exception ex) {
            System.out.println("Error while decrypting: " + ex.getMessage());
        }

        return decryptedText;
    }
    //https://stackoverflow.com/questions/18228579/how-to-create-a-secure-random-aes-key-in-java
    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }


    public static String encryptText(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }


    public static String decryptText(String encryptedText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    public static SecretKey loadAESKey(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}
