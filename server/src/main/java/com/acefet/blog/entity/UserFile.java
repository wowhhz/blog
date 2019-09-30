package com.acefet.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class UserFile {
	/**id**/
	@Id
	private String id;
	/**文件名**/
	private String fileName;
	/**文件描述**/
	private String fileDescription;
	/**文件路径**/
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
	private Timestamp createTime;
	/**备注**/
	private String remark;
}
