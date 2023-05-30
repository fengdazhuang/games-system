package com.fzz.api;

import com.fzz.common.utils.BaiduFaceUtil;
import com.fzz.common.utils.JsonUtils;
import com.fzz.common.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class BaseController {


    @Autowired
    public RedisUtil redis;

    @Autowired
    private BaiduFaceUtil baiduFaceUtil;

    public static final String REDIS_ADMIN_INFO = "redis_admin_info";
    public static final String MOBILE_SMSCODE = "mobile:smscode";

    public static final String REDIS_MEDICINE_INVENTORY = "redis_medicine_inventory";


    public static final String REDIS_VOLUNTEER_TOKEN="redis_volunteer_token";
    public static final String REDIS_VOLUNTEER_INFO = "redis_volunteer_info";

    public static final String REDIS_COMPETITION_CATEGORYS = "redis_competition_categorys";
    public static final String REDIS_COMPETITION_INFOS = "redis_competition_infos";

    public static final String REDIS_PLAYER_INFO = "redis_player_info";
    public static final String REDIS_PLAYER_MONGO_IDS = "redis_player_mongo_ids";
    public static final String REDIS_JUDGE_INFO = "redis_judge_info";
    public static final String REDIS_JUDGE_MONGO_IDS = "redis_judge_mongo_ids";


    public static final String REDIS_ADMIN_TOKEN = "redis_admin_token";

    public static final String REDIS_FRIEND_LINKS="redis_friend_links";

    public static final String REDIS_ALL_CATEGORY = "redis_all_category";

    public static final String REDIS_WRITER_FANS_COUNTS = "redis_writer_fans_counts";
    public static final String REDIS_MY_FOLLOW_COUNTS = "redis_my_follow_counts";

    public static final String REDIS_NEWS_READ_COUNTS = "redis_news_read_counts";
    public static final String REDIS_ALREADY_READ = "redis_already_read";

    public static final String REDIS_NEWS_COMMENT_COUNTS = "redis_article_comment_counts";

    public static final String REDIS_VOLUNTEER_EMAIL_CODE="redis_volunteer_email_code";
    public static final String REDIS_VOLUNTEER_DIRECTION_INFOS = "redis_volunteer_direction_infos";

    public static final String REDIS_POSITION_VOLUNTEER_COUNTS = "redis_position_volunteer_counts";
    public static final String REDIS_VOLUNTEER_COUNTS="redis_volunteer_counts";
    public static final String REDIS_VOLUNTEER_POSITION_COUNTS="redis_volunteer_position_counts";

    public static final Integer COOKIE_MONTH = 30 * 24 * 60 * 60;
    public static final Integer COOKIE_DELETE = 0;

    public static final Integer COMMON_START_PAGE = 1;
    public static final Integer COMMON_PAGE_SIZE = 10;



    public void setCookie(HttpServletRequest request,
                          HttpServletResponse response,
                          String cookieName,
                          String cookieValue,
                          Integer maxAge) {
        try {
            cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            setCookieValue(request, response, cookieName, cookieValue, maxAge);
//            Cookie cookie = new Cookie(cookieName, cookieValue);
//            cookie.setMaxAge(maxAge);
//            cookie.setDomain("imoocnews.com");
//            cookie.setPath("/");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setCookieValue(HttpServletRequest request,
                          HttpServletResponse response,
                          String cookieName,
                          String cookieValue,
                          Integer maxAge) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setDomain("imoocnews.com");
//        cookie.setDomain(DOMAIN_NAME);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void deleteCookie(HttpServletRequest request,
                             HttpServletResponse response,
                             String cookieName) {
        try {
            String deleteValue = URLEncoder.encode("", "utf-8");
            setCookieValue(request, response, cookieName, deleteValue, COOKIE_DELETE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public Integer getCountsFromRedis(String key) {
        String countsStr = redis.get(key);
        if (StringUtils.isBlank(countsStr)) {
            countsStr = "0";
            redis.set(key,countsStr);
        }
        return Integer.valueOf(countsStr);
    }






    /**
     * 人脸查找
     * @param base64 base64图片
     * @param groupId 被搜索人脸组
     * @return 结果
     */
    public Map<String,Object> faceSearch(String base64,String groupId) {
        String searchImg = base64.split(",")[1];
        String response = baiduFaceUtil.faceSearch(searchImg,groupId);
        Map<String,Object> responseMap = JsonUtils.jsonToPojo(response, Map.class);
        Map<String,Object> result = (Map<String, Object>) responseMap.get("result");
        return result;

    }


    public Map<String,Object> faceSet(String base64,Long id,String groupId,String info) throws IOException {
        String img = base64.split(",")[1];
        String response = baiduFaceUtil.faceSet(img,id,groupId,info);
        Map<String,Object> responseMap = JsonUtils.jsonToPojo(response, Map.class);
        Map<String,Object> result = (Map<String, Object>) responseMap.get("result");
        return result;

    }


    /**
     * 人脸对比
     */
    public Map<String,Object> faceMatch(String source,String target) {
        String sourceImg = source.split(",")[1];
        String targetImg = target.split(",")[1];
        String response = baiduFaceUtil.faceMatch(sourceImg,targetImg);
        Map<String,Object> responseMap = JsonUtils.jsonToPojo(response, Map.class);
        Map<String,Object> result = (Map<String, Object>) responseMap.get("result");
        return result;

    }








}
