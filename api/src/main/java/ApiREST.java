import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import spark.Spark;

import java.sql.*;

public class ApiREST {

    public void apiRest(){
        String dbPath = "C:/Users/leojs/IdeaProjects/aemet/datamart.db";

        Spark.get("/v1/places/with-max-temperature", (req, res) -> {
            String startDate = req.queryParams("from");
            String endDate = req.queryParams("to");
            Connection con = null;
            JsonArray response = new JsonArray();
            try {
                con = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT place,date,time,station, CAST(value as REAL) as value FROM Max WHERE date BETWEEN '"
                        + startDate + "' AND '" + endDate + "' "+ "ORDER BY value DESC LIMIT 1");
                while(rs.next()){
                    JsonObject obj = new JsonObject();
                    String place = rs.getString("place");
                    String temperature = rs.getString("value");
                    String time = rs.getString("time");
                    String date = rs.getString("date");
                    String station = rs.getString("station");
                    obj.addProperty("place",place);
                    obj.addProperty("station",station);
                    obj.addProperty("temperature",temperature);
                    obj.addProperty("time",time);
                    obj.addProperty("date",date);
                    response.add(obj);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return response;
        });

        Spark.get("/v1/places/with-min-temperature", (req, res) -> {
            String startDate = req.queryParams("from");
            String endDate = req.queryParams("to");
            Connection con = null;
            JsonArray response = new JsonArray();
            try {
                con = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT place,date,time,station, CAST(value as REAL) as value FROM Min WHERE date BETWEEN '"
                        + startDate + "' AND '" + endDate + "' "+ "ORDER BY value ASC LIMIT 1");
                while(rs.next()){
                    JsonObject obj = new JsonObject();
                    String place = rs.getString("place");
                    String temperature = rs.getString("value");
                    String time = rs.getString("time");
                    String date = rs.getString("date");
                    String station = rs.getString("station");
                    obj.addProperty("place",place);
                    obj.addProperty("station",station);
                    obj.addProperty("temperature",temperature);
                    obj.addProperty("time",time);
                    obj.addProperty("date",date);
                    response.add(obj);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return response;
        });
    }
}
