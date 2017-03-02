/**
 * Created by Foster on 2/20/2017.
 */
// Import required java libraries

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.*;
import javax.servlet.http.*;
// Extend HttpServlet class
public class servlet extends HttpServlet {
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
    private String message;

    public void init() throws ServletException
    {
        // Do required initialization

    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        try {
            JSONObject jsonObject = readJsonFromUrl("http://toonhq.org/api/v1/invasion/");
            JSONArray invasions = jsonObject.getJSONArray("invasions");
            StringBuilder sb = new StringBuilder();
            if(invasions.length()==0){
                sb.append("There are no invasons");
                return;
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
            message=sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Set response content type
        response.setContentType("application/json");
        Matcher matcher = Pattern.compile("[^a-zA-Z\\d\\s,.]").matcher(message);
        message=matcher.replaceAll("");
        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"speech\": \"");
        sb.append(message);
        sb.append("\",");
        sb.append("\"source\": \"foster-api\",");
        sb.append("\"displayText\": \"");
        sb.append(message);
        sb.append("\"");
        sb.append(",\n" +
                "  \"data\":{\n" +
                "  \"google\":{\n" +
                "  \"expect_user_response\": false\n" +
                "  }\n" +
                "  }");
        sb.append("}");
        String outputString=sb.toString();

        out.write(outputString);
        out.close();
    }

    public void destroy()
    {
        // do nothing.
    }
}