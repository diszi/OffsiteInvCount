package d2.hu.offsiteinvcount.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestAsyncTask extends AsyncTask<HttpCall, String, String> {
    private static final String UTF_8 = "UTF-8";

    private String message;
    private String response;

    @Override
    protected String doInBackground(HttpCall... httpCalls) {
        HttpURLConnection urlConnection = null;
        HttpCall httpCall = httpCalls[0];



        try {
            urlConnection = getHttpConnection(httpCall);
            urlConnection.connect();


            int statusCode = urlConnection.getResponseCode();

            Log.i("------------------>","connection: "+statusCode+"; "+urlConnection.getResponseMessage());
            if (statusCode / 100 != 2) {

                this.message = urlConnection.getResponseMessage();
                this.response = message;

            }


            InputStreamReader streamReader = new
                    InputStreamReader(urlConnection.getInputStream());
            this.response = convertInputStreamToString(streamReader);

            return response;

        } catch (IOException e) {
            e.printStackTrace();
        }




        return null;

    }

    private static String convertInputStreamToString(InputStreamReader inputStreamReader) throws IOException {
        if (inputStreamReader == null) {
            return "";
        }

        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();

        String inputLine;
        String result;

        while ((inputLine = reader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }

        reader.close();
        inputStreamReader.close();
        return stringBuilder.toString();
    }


    private HttpURLConnection getHttpConnection(HttpCall httpCall) throws IOException {

        URL url = new URL(httpCall.getUrl());

        Log.d("------------------>","URL = "+url+"; method="+httpCall.getMethod());


        HttpURLConnection connection = (HttpURLConnection)
                url.openConnection();

        connection.setRequestMethod(httpCall.getMethod().getValue());

        connection.setRequestProperty("Accept", "application/xml");
        connection.setRequestProperty("Content-type", "application/xml");
        connection.setRequestProperty("MAXAUTH","bWF4YWRtaW46SGUxMW9vNzAu");

        connection.setReadTimeout(30000);
        connection.setConnectTimeout(30000);

        if (httpCall.getMethod()== HttpCall.RequestMethod.POST){
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, UTF_8));
            writer.flush();
            writer.close();
            os.close();
        }

        return connection;
    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        onResponse(s);


    }

    public void onResponse(String response){

    }


}
