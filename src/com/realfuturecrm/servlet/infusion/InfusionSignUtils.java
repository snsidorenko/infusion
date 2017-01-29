package com.realfuturecrm.servlet.infusion;

import com.realfuture.model.InfusionSetting;
//import org.apache.ws.commons.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InfusionSignUtils {
    private static final String integrationKey = "infusion.integration.key";
    private static final String integrationSecret = "infusion.integration.secret";
    private static final String accountURL = "infusion.account.url";
    private static final String callbackUrl = "infusion.callback.url";
    private static final String baseUrl = "infusion.base.url";


    public static String redirectURL(){
        return "https://localhost:80/app/infusion";
    }

    public static String clientId(){
        return "5wzra6sh7suktdzsw33ek94c";
//        return Config.getInstance().getParam(integrationKey, "ba0af651-7cc7-49cb-a9a9-98a5fa4e9891");
    }
    protected static String secret(){
        return "GMw9RGvj2T";
    }

    public static Map<String, Object> paramsMap(boolean refresh){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Content-Type", "application/x-www-form-urlencoded");
        if(refresh) {
            map.put("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId() + ":" + secret()).getBytes()));
//            map.put("Authorization", "Basic " + Base64.encode((clientId() + ":" + secret()).getBytes()));
        }
        return map;
    }

    public static String accountUrl(){
        return "https://api.infusionsoft.com/token";
//        return Config.getInstance().getParam(accountURL, "https://account-d.docusign.com") + s;
    }

//    public static String returnURL(String base, int mode){
//        return base + "/app/infusion/result";
//    }

    public static String oauthUrl(String abId){
        return "https://signin.infusionsoft.com/app/oauth/authorize?response_type=code&scope=full" +
                "&client_id=" + clientId() +
                "&redirect_uri=" + redirectURL() +
                "&state="+abId;
    }

    public static String authToken(){
        return authToken(getSetting());
    }
    public static String authToken(InfusionSetting setting){
        if(setting != null){
            if(setting.isExpired()){
                JSONObject json = null;
                try {
                    json = WS.POST(paramsMap(true), accountUrl(), "grant_type=refresh_token&refresh_token=" + setting.getRefreshToken() + "&redirect_uri=" + redirectURL()).getJSONObject();
                    storeSetting(json);
                    return getSetting().getToken();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                return setting.getToken();
        }
        return null;
    }

    public static void storeSetting(JSONObject json){
        if(!json.containsKey("error")) {
            json.put("date", new Date().getTime());
            try {
                FileWriter fw = new FileWriter("c://temp/token.json");
                fw.write(json.toJSONString());
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static InfusionSetting getSetting(){
        try {
            JSONObject json = (JSONObject)new JSONParser().parse(new FileReader("c://temp/token.json"));
            return new InfusionSetting(json.get("access_token").toString(), json.get("refresh_token").toString(), Long.valueOf(json.get("date").toString()), Integer.valueOf(json.get("expires_in").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
