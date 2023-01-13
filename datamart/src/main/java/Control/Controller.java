package Control;

import SQL.SQLConnect;
import Stations.StationMax;
import Stations.StationMin;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Controller {

    public static void insert(String station, String time, String date,String place, String value) {
        String sql = "INSERT INTO Max (station,time,date,place,value) VALUES(?,?,?,?,?)";
        String dbPath = "C:/Users/leojs/IdeaProjects/aemet/datamart.db";
        SQLConnect connect = new SQLConnect();
        try (Connection conn = connect.connect(dbPath);
             PreparedStatement pstmt =  conn.prepareStatement(sql)) {
            pstmt.setString(1, station);
            pstmt.setString(2, time);
            pstmt.setString(3, date);
            pstmt.setString(4, place);
            pstmt.setString(5, value);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert2(String station, String time, String date,String place, String value) {
        String sql = "INSERT INTO Min (station,time,date,place,value) VALUES(?,?,?,?,?)";
        String dbPath = "C:/Users/leojs/IdeaProjects/aemet/datamart.db";
        SQLConnect connect = new SQLConnect();
        try (Connection conn = connect.connect(dbPath);
             PreparedStatement pstmt =  conn.prepareStatement(sql)) {
            pstmt.setString(1, station);
            pstmt.setString(2, time);
            pstmt.setString(3, date);
            pstmt.setString(4, place);
            pstmt.setString(5, value);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void controller() throws IOException {
        String url = "C:/Users/leojs/IdeaProjects/aemet/datamart.db";
        SQLConnect connect = new SQLConnect();
        try (Connection conn = connect.connect(url)) {
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS Max (" +
                    "station TEXT NOT NULL,"+
                    "time TEXT NOT NULL, " +
                    "date TEXT NOT NULL, " +
                    "place TEXT NOT NULL," +
                    "value TEXT NOT NULL" +
                    ")");
            statement.execute("CREATE TABLE IF NOT EXISTS Min (" +
                    "station TEXT NOT NULL," +
                    "time TEXT NOT NULL, " +
                    "date TEXT NOT NULL, " +
                    "place TEXT NOT NULL," +
                    "value TEXT NOT NULL" +
                    ")");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        File folder = new File("datalakedir");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if(listOfFiles[i].isFile()){
                FileReader fileReader = new FileReader(listOfFiles[i]);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String sCadena = bufferedReader.readLine();
                fileReader.close();
                JsonArray jsonArray = new Gson().fromJson(sCadena,JsonArray.class);

                for (JsonElement jsonElement : jsonArray) {
                    StationMax stationMax = new StationMax();

                    String date = jsonElement.getAsJsonObject().get("fint").getAsString();
                    LocalDateTime localDateMax = LocalDateTime.parse(date);
                    DateTimeFormatter formatterMax1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String date2 = localDateMax.format(formatterMax1);
                    stationMax.setDate(date2);

                    stationMax.setPlace(jsonElement.getAsJsonObject().get("ubi").getAsString());
                    stationMax.setStation(jsonElement.getAsJsonObject().get("idema").getAsString());

                    String timeMax = jsonElement.getAsJsonObject().get("fint").getAsString();
                    LocalDateTime localDateMax2 = LocalDateTime.parse(timeMax);
                    DateTimeFormatter formatterMax2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String time2 = localDateMax2.format(formatterMax2);
                    stationMax.setTime(time2);

                    stationMax.setValue(jsonElement.getAsJsonObject().get("tamax").getAsString());
                    insert(stationMax.getStation(), stationMax.getTime(), stationMax.getDate(), stationMax.getPlace(), stationMax.getValue());

                    StationMin stationMin = new StationMin();

                    String dateMin = jsonElement.getAsJsonObject().get("fint").getAsString();
                    LocalDateTime localDateMin = LocalDateTime.parse(dateMin);
                    DateTimeFormatter formatterMin1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String dateMin2 = localDateMin.format(formatterMin1);
                    stationMin.setDate(dateMin2);

                    stationMin.setPlace(jsonElement.getAsJsonObject().get("ubi").getAsString());
                    stationMin.setStation(jsonElement.getAsJsonObject().get("idema").getAsString());

                    String timeMin = jsonElement.getAsJsonObject().get("fint").getAsString();
                    LocalDateTime localDateMin2 = LocalDateTime.parse(timeMin);
                    DateTimeFormatter formatterMin2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String timeMin2 = localDateMin2.format(formatterMin2);
                    stationMin.setTime(timeMin2);

                    stationMin.setValue(jsonElement.getAsJsonObject().get("tamin").getAsString());
                    insert2(stationMin.getStation(), stationMin.getTime(), stationMin.getDate(), stationMin.getPlace(), stationMin.getValue());
                }
            }
        }
    }
}
