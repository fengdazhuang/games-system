package com.fzz.file.controller;

import com.fzz.api.BaseController;
import com.fzz.api.controller.file.FileUploadControllerApi;
import com.fzz.common.enums.ResponseStatusEnum;
import com.fzz.common.exception.CustomException;
import com.fzz.common.result.ReturnResult;
import com.fzz.common.utils.FileUtil;
import com.fzz.common.utils.JsonUtils;
import com.fzz.file.FileResource;
import com.fzz.file.service.FileUploadService;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang3.StringUtils;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class FileUploadController extends BaseController implements FileUploadControllerApi {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private FileResource fileResource;

    @Autowired
    private GridFSBucket gridFSBucket;


    @Override
    public ReturnResult uploadFiles(MultipartFile[] files) throws IOException {
        List<String> filePathList = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (file != null) {
                    String fileName = file.getOriginalFilename();
                    if (StringUtils.isNotBlank(fileName)) {
                        String fileSplit[] = fileName.split("\\.");
                        String suffix = fileSplit[fileSplit.length - 1];
                        if (!suffix.equalsIgnoreCase("png") && !suffix.equalsIgnoreCase("jpg")
                                && !suffix.equalsIgnoreCase("jpeg")) {
                            continue;
                        }
                        String filePath = fileUploadService.uploadFileToFdfs(file, suffix);
                        if (StringUtils.isNotBlank(filePath)) {
                            filePathList.add(fileResource.getHost() + filePath);
                        }
                    }
                }
            }
        }
        return ReturnResult.ok(filePathList);
    }

    @Override
    public String uploadToGridFS(Map<String,Object> map) throws IOException {
        String id = (String) map.get("id");
        String base64 = (String) map.get("base64");
        byte[] bytes = new BASE64Decoder().decodeBuffer(base64.trim());
        ByteArrayInputStream arrayInputStream=new ByteArrayInputStream(bytes);
        ObjectId objectId = gridFSBucket.uploadFromStream(id+".png",arrayInputStream);
        return objectId.toString();
    }

    @Override
    public ReturnResult readInGridFS(String set) throws Exception {
        Set<String> idSet= JsonUtils.jsonToSet(set,String.class);
        List<String> base64List=new ArrayList<>();
        for(String objectId:idSet){
            File file=readFileInGridFS(objectId);
            String base64 = FileUtil.fileToBase64(file);
            base64List.add(base64);
        }
        return ReturnResult.ok(base64List);
    }


    public File readFileInGridFS(String faceId) throws Exception {
        Bson bson = Filters.eq("_id", new ObjectId(faceId));
        GridFSFindIterable gridFSFiles = gridFSBucket.find(bson);
        GridFSFile gridFSFile = gridFSFiles.first();
        if(gridFSFile==null){
            throw new CustomException(ResponseStatusEnum.SYSTEM_FILE_NOT_FOUND);
        }
        String filename = gridFSFile.getFilename();
        File dir = new File("/workspace/temp_face");
        System.out.println(dir.getAbsolutePath());
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File("/workspace/temp_face/" + filename);
        OutputStream fileOutputStream = new FileOutputStream(file);

        gridFSBucket.downloadToStream(new ObjectId(faceId),fileOutputStream);

        return file;

    }
}