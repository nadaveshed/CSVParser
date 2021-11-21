package com.example.csvparser;

import com.opencsv.CSVReader;
import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public List<Player> listAll() {
        return repo.findAll();
    }

    public List<String[]> fileList() throws IOException {
        String fileName = "src/main/java/com/example/csvparser/players.csv";
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            Timer timer = new Timer();
            timer.schedule(new CheckForUpdates(), 0, 900000);

            List<String[]> r = reader.readAll();
            JSONArray playerData = new JSONArray();
            String[][] listToArray = new String[r.size()][2];
            r.toArray(listToArray);
            Player p = new Player();
            for (int i = 1; i<r.size(); i++){
                p.setPlayerId(Integer.valueOf(listToArray[i][0]));
                p.setNickname(listToArray[i][1]);
                repo.save(p);
                playerData.put(fetchData(Integer.valueOf(listToArray[i][0]), listToArray[i][1]));
            }

            String csv = CDL.toString(playerData);
            File file = new File("full_players_data.csv");
            FileUtils.writeStringToFile(file, csv, "UTF-8");
            reader.close();
            return r;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    private JSONObject fetchData(int id, String nickname) {
        try{
            String url=" https://www.balldontlie.io/api/v1/players/" + id;
            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject json = new JSONObject(jsonText);
                json.put("nickname", nickname);
                return json;
            } finally {
                is.close();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}

class CheckForUpdates extends TimerTask {
    public void run() {
        System.out.println("Check for updates");
    }
}