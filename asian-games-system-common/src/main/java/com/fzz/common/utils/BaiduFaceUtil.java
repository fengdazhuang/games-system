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

//    static final String access_token=getAuth();

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
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
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
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
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public String faceSearch(String base64) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            Map<String, Object> map = new HashMap<>();
            byte[] readFileByBytes = FileUtil.readFileByBytes("C:\\Users\\冯大壮\\workspace\\asian-games-system\\asian-games-system-service-personnel\\src\\main\\resources\\312109010821.jpg");
            String encode = Base64Util.encode(readFileByBytes);

            map.put("image", base64);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "player,judge");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = JsonUtils.objectToJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
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
        // 请求url
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

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
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
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", source);
            map1.put("face_type","IDCARD");
            map1.put("liveness_control", "NORMAL");
            map1.put("image_type", "BASE64");
            map1.put("quality_control", "LOW");

            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", target);
            map2.put("face_type","IDCARD");
            map2.put("liveness_control", "NORMAL");
            map2.put("image_type", "BASE64");
            map2.put("quality_control", "LOW");

            List<Map<String,Object>> list=new ArrayList<>();
            list.add(map1);
            list.add(map2);
            String param = JsonUtils.objectToJson(list);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "[调用鉴权接口获取的token]";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

}
