package com.acefet.blog.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TestForm {

    /**id**/
    private String id;
    /**pid**/
    private String pid;
    /**名称**/
    @NotNull(message = "名称不能为空")
    @Length(min = 1, max = 20, message = "名称在20位以内")
    private String name;
    /**密码**/
    @Length(min = 3,max = 18, message = "密码在3-18位之间")
    @NotNull(message = "密码不能为空")
    private String password;
    /**Email**/
    @NotNull(message = "Email不能为空")
    @Email(message = "必须提供正确的Email格式")
    private String email;
    /**日期**/
    @NotNull(message = "日期不能为空")
    private String dater;
    /**时间**/
    @NotNull(message = "时间不能为空")
    private String timer;
    /**下拉项**/
    @NotNull(message = "下拉项不能为空")
    private String selectr;
    /**单选项**/
    @NotNull(message = "单选项不能为空")
    private String radio;
    /**多选项**/
    @NotNull(message = "多选项不能为空")
    private String checkbox;
    /**大文本**/
    @NotNull(message = "大文本不能为空")
    private String textarea;
    /**文件**/
    @NotNull(message = "文件不能为空")
    private List<MultipartFile> file;
    /**内容**/
    @NotNull(message = "内容不能为空")
    private String content;

}
