package com.acefet.blog.service.impl;

import com.acefet.blog.convert.UserFileConvert;
import com.acefet.blog.entity.UserFile;
import com.acefet.blog.enums.ResultEnum;
import com.acefet.blog.exception.SystemException;
import com.acefet.blog.form.UserFileForm;
import com.acefet.blog.repository.UserFileRepository;
import com.acefet.blog.service.UserFileService;
import com.acefet.blog.util.FileUtil;
import com.acefet.blog.util.Util;
import com.acefet.blog.vo.UserFileVO;
import com.acefet.blog.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    private UserFileRepository userFileRepository;

    @Override
		public UserFileVO view(String id) {
        UserFile userFile = userFileRepository.getFileById(id);
        if(userFile ==null){
            log.error("【查找file】数据不存在，id={}",id);
            throw new SystemException(ResultEnum.CUSTOM_NOT_EXIST);
        }
        UserFileVO userFileVO = UserFileConvert.file2FileVO(userFile);
        return userFileVO;
    }

    @Override
    public Page<UserFileVO> query(String searchValue, String orderType, String orderName,
                                  String pageNum, String pageSize) {
        int size = com.acefet.blog.util.Page.PAGESIZE_DEFAULT, page = 0;
        if(StringUtils.isEmpty(pageNum))pageNum = "1";
        if(pageSize!=null && Util.isNumeric(pageSize))size = Integer.parseInt(pageSize);
        if(!Util.isNumeric(pageNum)){
            log.error("【查找file】参数不正确，pageNum={}",pageNum);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        page = Integer.parseInt(pageNum)-1;
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        if(StringUtils.isEmpty(orderName)){
            orderList.add(Sort.Order.desc("createTime"));
        }else{
            if("asc".equalsIgnoreCase(orderType)){
                orderList.add(Sort.Order.asc(orderName));
            }else{
                orderList.add(Sort.Order.desc(orderName));
            }
        }
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderList));
        Page<UserFile> filePage = null;

        if(StringUtils.isEmpty(searchValue)){
            filePage = userFileRepository.findAll(pageable);
        }else{
            filePage = userFileRepository.findAllByFileNameLikeOrFileDescriptionLike("%"+searchValue+"%","%"+searchValue+"%",pageable);
        }


        Page<UserFileVO> fileVOPage = null;
        if(filePage!=null){
            fileVOPage = filePage.map(this::convertToFileDto);
        }
        
        return fileVOPage;
    }

    private UserFileVO convertToFileDto(UserFile userFile){
        UserFileVO userFileVO = null;
        userFileVO = UserFileConvert.file2FileVO(userFile);
        
        return userFileVO;
    }

    public UserFileVO create(UserFileForm userFileForm, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            log.error("【创建file】校验失败，userFileForm={}", userFileForm);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        UserFile userFile = UserFileConvert.fileForm2File(userFileForm);
        userFileRepository.save(userFile);
        UserFileVO userFileVO = UserFileConvert.file2FileVO(userFile);
        return userFileVO;
    }

    public UserFileVO update(UserFileForm userFileForm, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            log.error("【更新file】校验失败，userFileForm={}", userFileForm);
            throw new SystemException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        UserFile userFile = userFileRepository.getFileById(userFileForm.getId());
        if(userFile ==null){
            log.error("【更新file】数据不存在,id={}", userFileForm.getId());
            throw new SystemException(ResultEnum.CUSTOM_NOT_EXIST);
        }
        BeanUtils.copyProperties(userFileForm, userFile);
        userFileRepository.save(userFile);
        UserFileVO userFileVO = UserFileConvert.file2FileVO(userFile);
        return userFileVO;
    }

    @Override
    public UserFileVO save(UserFileForm userFileForm, BindingResult bindingResult) throws IOException {
        UserFileVO userFileVO = null;
        if(StringUtils.isEmpty(userFileForm.getId())){
            userFileVO = create(userFileForm,bindingResult);
        }else{
            userFileVO = update(userFileForm,bindingResult);
        }
        return userFileVO;
    }

    @Override
    public UserFileVO save(MultipartFile multipartFile, Map<String,String> map) throws IOException {

        String filePath = map.get("filePath");
        String subPath = map.get("subPath");
        String groupId = map.get("groupId");
        String actionId = map.get("actionId");
        String userId = map.get("userId");
        String userName = map.get("userName");

        if(userId==null)userId = "";
        if(userName==null)userName = "";

        if(multipartFile.isEmpty())return null;
        String newFileName = FileUtil.getNewFileName(multipartFile.getOriginalFilename());
        File newfile = new File(filePath+subPath+newFileName);
        newfile = FileUtil.uploadFiles(multipartFile.getBytes(), newfile);

        UserFile userfile = new UserFile();
        userfile.setActionId(actionId);
        userfile.setGroupId(groupId);
        userfile.setFileName(newfile.getName());
        userfile.setFilePath(subPath+newFileName);
        userfile.setFileSize(newfile.length()+"");
        userfile.setRemark("");
        userfile.setStatus(FileUtil.FILE_STATUS_NORMAL);
        userfile.setType(multipartFile.getContentType());
        userfile.setUserId(userId);
        userfile.setUserName(userName);
        userfile.setFileDescription(multipartFile.getOriginalFilename());

        if(multipartFile.getContentType().startsWith("image")){
            //保存图片记录
            BufferedImage bimg = ImageIO.read(newfile);
            int width          = bimg.getWidth();
            int height         = bimg.getHeight();

            userfile.setImgSize(width+"x"+height);
        }

        userfile = UserFileConvert.addProperties(userfile);
        userFileRepository.save(userfile);
        UserFileVO userFileVO = UserFileConvert.file2FileVO(userfile);
        return userFileVO;
    }

    @Override
    public void delete(String[] ids,String realPath) {
        if(ids==null){
            log.error("【删除file】参数不正确，ids={}",ids);
            throw new SystemException(ResultEnum.PARAM_ERROR);
        }
        List<UserFile> list = userFileRepository.findAllByIdIn(ids);
        for (UserFile userFile: list) {
            File file = new File(realPath+userFile.getFilePath());
            if(file.exists())file.delete();
        }
        userFileRepository.deleteInBatch(list);
    }

}
