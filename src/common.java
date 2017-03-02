import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Foster on 3/1/2017.
 */
public class common {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
    public static String getMessage(){
        try {
            JSONObject jsonObject = readJsonFromUrl("http://toonhq.org/api/v1/invasion/");
            JSONArray invasions = jsonObject.getJSONArray("invasions");
            StringBuilder sb = new StringBuilder();
            if(invasions.length()==0){
                sb.append("There are no invasons");
                return "There are no invasions";
            }else {
                sb.append("There are ");
            }
            for (int i = 0; i < invasions.length(); i++) {
                JSONObject thisInvasion = invasions.getJSONObject(i);
                sb.append(thisInvasion.getString("cog"));
                sb.append("s in ");
                sb.append(thisInvasion.getString("district"));
                if(i+1==invasions.length()){
                    sb.append(".");
                }else if(i+2==invasions.length()){
                    sb.append(", and ");
                }else{
                    sb.append(", ");
                }

            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "There are no invasions";
    }
}
