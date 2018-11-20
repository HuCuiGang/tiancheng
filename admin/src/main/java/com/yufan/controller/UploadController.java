package com.yufan.controller;

import com.yufan.common.PicUploadResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("/pic")
@Controller
public class UploadController {

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public PicUploadResult upload(@RequestParam  MultipartFile uploadFile) throws IOException {

        String imageUrl="http://image.yufan.com/";
        String path="E:\\image";

        //获取文件的后缀
        String filename=uploadFile.getOriginalFilename();
        String end=filename.substring(filename.lastIndexOf("."));

        //生成新的文件名
        String uuid_name= UUID.randomUUID().toString().replace("-","")+end;

        File d=new File(path);
        if(!d.exists()){
            d.mkdirs();
        }

        File file=new File(path+File.separator+uuid_name);
        //上传图片
        uploadFile.transferTo(file);

        PicUploadResult picUploadResult=new PicUploadResult();

        BufferedImage bufferedImage=ImageIO.read(file);

        if(bufferedImage!=null){
            //上传成功
            picUploadResult.setUrl(imageUrl+uuid_name);
            picUploadResult.setError(0);
            picUploadResult.setWidth(bufferedImage.getWidth()+"");
            picUploadResult.setHeight(bufferedImage.getHeight()+"");
        }else{
            //上传失败
            picUploadResult.setError(1);
            picUploadResult.setMessage("上传失败!");
        }
        return picUploadResult;
    }

}
