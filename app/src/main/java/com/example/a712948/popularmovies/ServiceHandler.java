package com.example.a712948.popularmovies;

/**
 * @author Hannah Paulson
 * @since 8/14/15.
 */
public class ServiceHandler  {

//    public String LOG = "LOG";
//    protected Void doInBackground(Void... params) {
//        // Start of getting data back
//        HttpURLConnection connection = null;
//        BufferedReader reader = null;
//
//        String movieString = null;
//
//        try {
//            URL url = new URL("http://api.themoviedb.org/3/discover/movie?sorted_by=popularity.asc&api_key=fc47e47a86969055486f846572f8bf83");
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
//            InputStream inputStream = connection.getInputStream();
//            StringBuffer buffer = new StringBuffer();
//
//            if (inputStream == null) {
//                // Nothing to do.
//                return null;
//            }
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                // But it does make debugging a *lot* easier if you print out the completed
//                // buffer for debugging.
//                buffer.append(line + "\n");
//            }
//
//            if (buffer.length() == 0) {
//                // Stream was empty.  No point in parsing.
//                return null;
//            }
//            movieString = buffer.toString();
//            Log.e(LOG, "movieString" + movieString);
//        } catch (IOException e) {
//            Log.e(LOG, "Error ", e);
//            // If the code didn't successfully get the weather data, there's no point in attemping
//            // to parse it.
//            return null;
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (final IOException e) {
//                    Log.e(LOG, "Error closing stream", e);
//                }
//            }
//        }
//        return null;
//
//    }
}
