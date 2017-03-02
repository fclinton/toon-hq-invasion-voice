/**
 * Created by Foster on 2/20/2017.
 */
// Import required java libraries

import com.amazon.speech.speechlet.authentication.SpeechletRequestSignatureVerifier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        InputStream is = request.getInputStream();
        byte[] buffer=new byte[request.getContentLength()];
        is.read(buffer);
        SpeechletRequestSignatureVerifier.checkRequestSignature(buffer,request.getHeader("Signature"),request.getHeader("SignatureCertChainUrl"));
        message=common.getMessage();
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