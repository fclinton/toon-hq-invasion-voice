/**
 * Created by Foster on 2/20/2017.
 */
// Import required java libraries

import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Extend HttpServlet class
public class servlet extends HttpServlet {

    private String message;

    public void init() throws ServletException
    {
        // Do required initialization

    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        InvasionCollection invasionCollection = new InvasionCollection();
        StringBuffer jb = new StringBuffer();
        String line = null;
        JSONObject jsonObject = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        try {
            jsonObject = new JSONObject(jb.toString());
        } catch (JSONException e) {
            // crash and burn
            throw new IOException("Error parsing JSON request string");
        }
        switch (jsonObject.getJSONObject("result").getString("action")) {
            case "specificinvasion":
                message = invasionCollection.getMessage(jsonObject.getJSONObject("result").getJSONObject("parameters").getString("cog"));
                break;
            default:
                message = invasionCollection.getMessage();
        }
        // Set response content type
        response.setContentType("application/json");
        Matcher matcher = Pattern.compile("[^a-zA-Z\\d\\s,.]").matcher(message);
        message = matcher.replaceAll("");
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
        String outputString = sb.toString();

        out.write(outputString);
        out.close();
    }
    public void destroy()
    {
        // do nothing.
    }
}