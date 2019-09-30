package com.acefet.blog.convert;

import com.acefet.blog.entity.UserFile;
import com.acefet.blog.form.UserFileForm;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.UserFileVO;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class UserFileConvert {

    public static UserFile fileForm2File(UserFileForm userFileForm){
        UserFile userFile = new UserFile();
        BeanUtils.copyProperties(userFileForm, userFile);
        userFile.setId(Util.getUUID());
        userFile.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return userFile;
    }

    public static UserFile addProperties(UserFile userFile){
        userFile.setId(Util.getUUID());
        userFile.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return userFile;
    }

    public static UserFileVO file2FileVO(UserFile userFile){
        UserFileVO userFileVO = new UserFileVO();
        BeanUtils.copyProperties(userFile, userFileVO);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        userFileVO.setCreateTime(sdf.format(userFile.getCreateTime()));
        return userFileVO;
    }   

}
