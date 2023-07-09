import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static final String PERSON_URL = "person.txt";
    public static final String SECRET_URL = "secret.txt";

    public static void main(String[] args) {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String userName = scanner.nextLine();

        Thread userInfoThread = new Thread(() -> {
            String userInfo = main.getUserInfo();
            main.saveUserInfoToFile(userName, userInfo, PERSON_URL);
        });

        Thread secretInfoThread = new Thread(() -> {
            System.out.println("Enter secret information: ");
            String secretInfo = scanner.nextLine();
            main.saveSecretInfoToFile(userName, secretInfo, SECRET_URL);
        });

        try {
            userInfoThread.start();
            userInfoThread.join();
            secretInfoThread.start();
            secretInfoThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getUserInfo() {
        Scanner scanner = new Scanner(System.in);
        String password, email;

        System.out.println("Enter password: ");
        password = scanner.nextLine();

        System.out.println("Enter email: ");
        email = scanner.nextLine();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Password: ").append(password).append(System.lineSeparator());
        stringBuilder.append("Email: ").append(email).append(System.lineSeparator());

        return stringBuilder.toString();
    }

    public void saveUserInfoToFile(String userName, String userInfo, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("Username: " + userName);
            writer.newLine();
            writer.write(userInfo);
            writer.write("~~~~~~~~~~~~~~~~~~~~");
            writer.newLine();
            writer.flush();
            System.out.println("User information saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void saveSecretInfoToFile(String userName, String secretInfo, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.newLine();
            writer.write("Secret Information for User: " + userName);
            writer.newLine();
            writer.write(secretInfo);
            writer.newLine();
            writer.write("~~~~~~~~~~~~~~~~~~~~");
            writer.flush();
            System.out.println("Secret information saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}