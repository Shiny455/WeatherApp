import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class WeatherApp {

    private static final String API_KEY = "b533a603bfdb82c52fe212646fb4c24f";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();

        try {
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" +
                                city + "&units=metric&appid=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                System.out.println("Error: Unable to fetch weather data");
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }

                reader.close();

                JSONObject obj = new JSONObject(jsonBuilder.toString());

                String cityName = obj.getString("name");
                JSONObject main = obj.getJSONObject("main");
                double temp = main.getDouble("temp");
                int humidity = main.getInt("humidity");

                JSONObject weather = obj.getJSONArray("weather").getJSONObject(0);
                String description = weather.getString("description");

                System.out.println("\nWeather in " + cityName + ":");
                System.out.println("Temperature: " + temp + "°C");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("Condition: " + description);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
}
}
