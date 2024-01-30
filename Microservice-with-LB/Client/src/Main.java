import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // Iteracja po portach = Load Balancer
        List<Integer> ports = Arrays.asList(1111, 2222, 3333);
        for (int i = 0; i < ports.size(); i++) {
            Integer currentPort = ports.get(i);
            postEquation(currentPort);
            if (currentPort.equals(ports.get(ports.size() - 1))) {
                i = -1;
            }
        }
    }

    public static void postEquation(Integer port) throws IOException {
        Scanner scanner = new Scanner(System.in);
        // Wprowadzenie danych
        System.out.println("First number:");
        float numberOne = scanner.nextFloat();
        System.out.println("Symbol:");
        String symbol = scanner.next();
        System.out.println("Second number:");
        float numberTwo = scanner.nextFloat();

        System.out.println("Your equation is: " + numberOne + " " + symbol + " " + numberTwo);

        String typeOfEquation = switch (symbol) {
            case "+" -> "plus";
            case "-" -> "minus";
            case "*" -> "multiply";
            case "/" -> "divide";
            default -> throw new IllegalStateException("Unexpected value: " + symbol);
        };
        // Ustanowienie połączenia z serwerem na kolejnym porcie
        URL url = new URL("http://localhost:" + port + "/" + typeOfEquation);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        // Tworzymy obiekt JSON z danymi
        String jsonInputString = "{\"number_one\":" + numberOne + ",\"number_two\":" + numberTwo + "}";

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Odczytujemy odpowiedź
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Response from server (port " + port + "): " + response.toString());
        }
    }
}
