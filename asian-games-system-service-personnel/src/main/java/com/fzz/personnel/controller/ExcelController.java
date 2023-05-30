package com.fzz.personnel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fzz.api.controller.personnal.ExcelControllerApi;
import com.fzz.common.result.ReturnResult;
import com.fzz.common.utils.JsonUtils;
import com.fzz.model.entity.excel.ExcelPlayer;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class ExcelController implements ExcelControllerApi {

    @Override
    public ReturnResult excelExport(Map<String,Object> map) {
        String filename= System.currentTimeMillis()+".xlsx";
        List<Map<String,Object>> mapList = (List<Map<String, Object>>) map.get("playerList");

        /*List<ExcelPlayer> playerList = JSON.parseArray(JSON.toJSONString(mapList), ExcelPlayer.class);;*/
        List<ExcelPlayer> playerList = JsonUtils.jsonToList(JsonUtils.objectToJson(mapList), ExcelPlayer.class);;
        EasyExcel.write(filename, ExcelPlayer.class).sheet("运动员").doWrite(playerList);
        return ReturnResult.ok();
    }

    @Override
    public ReturnResult excelImport(MultipartFile multipartFile) {
        return null;
    }
}
