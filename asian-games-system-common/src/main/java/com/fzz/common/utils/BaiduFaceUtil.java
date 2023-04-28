package com.fzz.common.utils;


import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BaiduFaceUtil {

    private static final String CLIENT_ID = "eoSb4aexFMClCX0ljvuF4WZj";
    private static final String CLIENT_SECRET = "A2mOIPnFynqezt9wKMxbbIWB5MhKMYhr";

    /**
     * 获取API访问token
     */
    public static String getAuth() {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + CLIENT_ID
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + CLIENT_SECRET;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }


    /**
     * 重要提示代码中所需工具类
     * 下载
     */
    public String faceSearch(String base64,String groupId) {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", base64);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", groupId);
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");
            String param = JsonUtils.objectToJson(map);

            String accessToken = getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String faceSet(String base64,Long id,String groupId,String info) throws IOException {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", base64);
            map.put("group_id", groupId);
            map.put("user_id", id);
            map.put("user_info", info);
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = JsonUtils.objectToJson(map);
            String accessToken = getAuth();
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String faceMatch(String source,String target) {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", source);
            map1.put("face_type", "IDCARD");
            map1.put("liveness_control", "NORMAL");
            map1.put("image_type", "BASE64");
            map1.put("quality_control", "LOW");

            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", target);
            map2.put("face_type", "IDCARD");
            map2.put("liveness_control", "NORMAL");
            map2.put("image_type", "BASE64");
            map2.put("quality_control", "LOW");

            List<Map<String, Object>> list = new ArrayList<>();
            list.add(map1);
            list.add(map2);
            String param = JsonUtils.objectToJson(list);
            String accessToken = getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
