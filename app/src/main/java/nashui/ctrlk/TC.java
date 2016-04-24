package nashui.ctrlk;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class TC {

    public static String getAccessToken(String username, String password, String client_id, String client_secret, String userid, String kid, String Key) {

        String access_token = null;
        String code = null;
        String h = null;
        try {
            Connection conn = null;
            String url = "http://kk.bigk2.com:8080/KOAuthDemeter/authorize?response_type=code&client_id=" + client_id + "&redirect_uri=http://52us.sinaapp.com/test";
            conn = Jsoup.connect(url);
            Document doc = conn.get();
            List<FormElement> formList = doc.getAllElements().forms();
            if (formList.size() > 0) {
                FormElement form = formList.get(0);
                Elements usernameInput = form.select("input[name=username]");
                Elements passwordInput = form.select("input[name=password]");
                usernameInput.attr("value", username);
                passwordInput.attr("value", password);
                conn = form.submit();
                doc = conn.post();
            }

            code = doc.baseUri().split("=")[1];
            String tokenUrl = "http://kk.bigk2.com:8080/KOAuthDemeter/accessToken";
            conn = Jsoup.connect(tokenUrl);
            conn.data("grant_type", "authorization_code");
            conn.data("client_id", client_id);
            conn.data("client_secret", client_secret);
            conn.data("redirect_uri", "http://www.baidu.com");
            conn.data("code", code);
            doc = conn.post();
            String tokenResp = doc.body().text();
            JSONObject jo = new JSONObject(tokenResp);
            access_token = jo.getString("access_token");
            JSONObject ClientKey = new JSONObject();
            ClientKey.put("userid", URLEncoder.encode(userid, "UTF-8"));
            ClientKey.put("kid", URLEncoder.encode(kid, "UTF-8"));
            ClientKey.put("key", URLEncoder.encode(Key, "UTF-8"));
            String SwUrl = "http://kk.bigk2.com:8080/KOAuthDemeter/KControl/doSwitchK";
            h = post(SwUrl, ClientKey, access_token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "token:\n" + access_token + "\n\n" + h;
    }

    public static String post(String URL, JSONObject json, String to) {

        HttpURLConnection connection = null;
        String s = null;
        try {
            URL uri = new URL(URL);
            connection = (HttpURLConnection) uri.openConnection();
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.addRequestProperty("Authorization", "Bearer " + to);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            String jsonstr = json.toString();
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(jsonstr);
            bufferedWriter.flush();
            outputStream.close();
            bufferedWriter.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                String result;

                while ((result = bufferedReader.readLine()) != null) {
                    builder.append(result);
                }
                s = builder.toString();
                System.out.println(s);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return s;
    }

}

