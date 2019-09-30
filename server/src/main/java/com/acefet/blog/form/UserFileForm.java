package com.acefet.blog.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserFileForm {

    /**id**/
		private String id;
    /**文件名**/
    @Length(min = 1, max = 255, message = "文件名在1-255之间")
		private String fileName;
    /**文件描述**/
		private String fileDescription;
    /**文件路径**/
    @Length(min = 1, max = 255, message = "文件路径在1-255之间")
		private String filePath;
    /**文件类型**/
		private String type;
    /**文件大小**/
		private String fileSize;
    /**图像尺寸**/
		private String imgSize;
    /**文件状态**/
		private String status;
    /**文件标志**/
		private String flag;
    /**所属组**/
		private String groupId;
    /**所属编号**/
		private String actionId;
    /**上传人编号**/
		private String userId;
    /**上传人名称**/
		private String userName;
    /**上传时间**/
		private String createTime;
    /**备注**/
		private String remark;
}
