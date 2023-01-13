
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Feeder {

    public void feeder() throws IOException {
        String url = "https://opendata.aemet.es/opendata/api/observacion/convencional/todas";
        String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZW9qc3RhckBnbWFpbC5jb20iLCJqdGkiOiJhMzY0MjAzMi04YTI0LTQ4NDAtOTU0My02ODI4YWI3ODU0NzEiLCJpc3MiOiJBRU1FVCIsImlhdCI6MTY3MzM1MTIzNiwidXNlcklkIjoiYTM2NDIwMzItOGEyNC00ODQwLTk1NDMtNjgyOGFiNzg1NDcxIiwicm9sZSI6IiJ9.vQpY5VLuTyRJ4AgAIFjepMR1_WmeRvloDqyS8xQBqXw";
        String response = Jsoup.connect(url)
                .ignoreContentType(true)
                .header("accept", "application/json")
                .header("api_key", apiKey)
                .method(Connection.Method.GET)
                .maxBodySize(0).execute().body();

        JsonObject json = new Gson().fromJson(response,JsonObject.class);
        String datosUrl = json.get("datos").getAsString();

        String response2 = Jsoup.connect(datosUrl)
                .ignoreContentType(true)
                .header("accept", "application/json")
                .header("api_key", apiKey)
                .method(Connection.Method.GET)
                .maxBodySize(0).execute().body();

        JsonArray jsonArrays = new Gson().fromJson(response2,JsonArray.class);
        JsonArray finals = new JsonArray();
        for (JsonElement jsonElement : jsonArrays) {
            if(jsonElement.getAsJsonObject().get("lon").getAsDouble() > -16 && jsonElement.getAsJsonObject().get("lon").getAsDouble() < -15){
                if(jsonElement.getAsJsonObject().get("lat").getAsDouble() > 27.5 && jsonElement.getAsJsonObject().get("lat").getAsDouble() < 28.4)
                    finals.add(jsonElement);
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        FileWriter writer = new FileWriter("datalakedir/" + LocalDate.now().format(formatter) + ".events");
        writer.write(finals.toString());
        writer.close();
    }

}
