/**
 * Created by fclinton foster626@gmail.com on 2/20/2017.
 */
// Import required java libraries

import com.amazon.speech.speechlet.authentication.SpeechletRequestSignatureVerifier;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Extend HttpServlet class
public class alexaservlet extends HttpServlet {
    private String message;

    public void init() throws ServletException
    {
        // Do required initialization

    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        String line = null;
        JSONObject jsonObject = null;
        StringBuffer jb = new StringBuffer();
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
        byte[] buffer=new byte[request.getContentLength()];
        buffer=jb.toString().getBytes();
        SpeechletRequestSignatureVerifier.checkRequestSignature(buffer,request.getHeader("Signature"),request.getHeader("SignatureCertChainUrl"));
        InvasionCollection invasionCollection = new InvasionCollection();



        switch (jsonObject.getJSONObject("request").getJSONObject("intent").getString("name")) {
            case "Specificcog":
                message = invasionCollection.getMessage(jsonObject.getJSONObject("request")
                        .getJSONObject("intent").getJSONObject("slots").getJSONObject("cogslot")
                        .getString("value"));
                break;
            default:
                message = invasionCollection.getMessage();
        }
        // Set response content type
        response.setContentType("application/json");
        Matcher matcher = Pattern.compile("[^a-zA-Z\\d\\s,.]").matcher(message);
        message=matcher.replaceAll("");
        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();

       sb.append("{\n" +
               "  \"version\": \"string\",");
        sb.append("\"response\": {\n" + "    \"outputSpeech\": {\n" + "      \"type\": \"PlainText\",\n" + "      \"text\": \"").append(message).append("\"\n").append("    },");
        sb.append("\n" +
                "\"shouldEndSession\": true" +
                "}}");



        String outputString=sb.toString();

        out.write(outputString);
        out.close();
    }

    public void destroy()
    {
        // do nothing.
    }
}