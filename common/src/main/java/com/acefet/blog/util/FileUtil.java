package com.acefet.blog.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@PropertySources({@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true)})
public class FileUtil {
	
	public static String PARA_BASEPATH = "basePath";
	public static String PARA_ATTACHEMENTS ="attachments";
	public static String PARA_ATTPATH ="attPath";

	public static String FILE_STATUS_NORMAL = "normal";
	public static String FILE_STATUS_DISABLE = "disable";
	public static String FILE_STATUS_LOSE = "lose";


    private static String basePath;


    private static String attachments;


    private static String attPath;

    @Value("${web.basePath}")
    public void setBasePath(String basePath){
        this.basePath = basePath;
    }

    @Value("${web.attachments}")
    public void setAttachments(String attachments){
        this.attachments = attachments;
    }

    @Value("${web.attPath}")
    public void setAttPath(String attPath){
        this.attPath = attPath;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String readPath = "D:\\HS.html";
//		String writePath = "D:\\upload\\blog\\userFile\\filetest.html";
//		byte[] strs;
//		try {
//			strs = read(readPath);
//			System.out.println(new String(strs,"utf-8"));
//			write(writePath,strs);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


		
	}
	
	public static byte[] read(String filename) throws IOException {
		File file = new File(filename);
		if(!file.exists()){  
			throw new FileNotFoundException(filename);  
	    }
		FileChannel channel = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			channel = fis.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int)channel.size()); 
			while((channel.read(byteBuffer)) > 0){  
                // do nothing  
//              System.out.println("reading");  
            }  
            return byteBuffer.array();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}finally{  
            try{  
                channel.close();  
            }catch (IOException e) {  
                e.printStackTrace();  
            }  
            try{  
                fis.close();  
            }catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
	}
	
	public static byte[] read(String filename,String code) throws IOException {
		File file = new File(filename);
		if(!file.exists()){  
			throw new FileNotFoundException(filename);  
	    }
		FileInputStream fis = null;
		InputStreamReader isr = null;
		byte[] bs = new byte[(int)file.length()];
		try {
			int tempbyte;
			fis = new FileInputStream(file);
			if(code==null) {
				isr = new InputStreamReader(fis);
			}else {
				isr = new InputStreamReader(fis,code);
			}
			
			int x=0;
			while((tempbyte=isr.read())!=-1) {
				bs[x++] = (byte)tempbyte;
			}
			return bs;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}finally{
			try{  
				isr.close();
            }catch (IOException e) {  
                e.printStackTrace();  
            }  
            try{  
            	fis.close(); 
            }catch (IOException e) {  
                e.printStackTrace();  
            }  
		}
	}
	
	public static void write(String filename, byte[] bs) throws IOException {
		File file = new File(filename);
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = null;
		FileChannel channel = null;
		try {
			fos = new FileOutputStream(file);
			channel = fos.getChannel();
			ByteBuffer bytedata = ByteBuffer.allocate(bs.length);
			bytedata.clear();
			bytedata.put(bs);
			bytedata.flip();
			while(bytedata.hasRemaining()) {
				channel.write(bytedata);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}finally {
			try{  
				channel.close();
            }catch (IOException e) {  
                e.printStackTrace();  
            }
			try{  
				fos.close();
            }catch (IOException e) {  
                e.printStackTrace();  
            }
		}
	}
	
	/**
	 * 获取文件配置路径
	 * @return
	 * @throws IOException
	 */
	public static Map getConfigPath() throws IOException {
		basePath = basePath.replace("\\", File.separator);
		attachments = attachments.replace("\\", File.separator);
		attPath = attPath.replace("\\", File.separator);
		
		Map map = new HashMap();
		map.put(PARA_BASEPATH, basePath);
		map.put(PARA_ATTACHEMENTS, attachments);
		map.put(PARA_ATTPATH,attPath);
		return map;
	}
	
	/**
	 * 获取Url访问相对路径
	 * @return
	 * @throws IOException
	 */
	public static String getUrlFilePath() throws IOException {
		Map<String,String> map = getConfigPath();
		String attPath = map.get(PARA_ATTPATH);
		return attPath;
	}
	
	
	/**
	 * 获取真实文件绝对路径
	 * @return
	 * @throws IOException
	 */
	public static String getRealFilePath() throws IOException {
		Map<String,String> map = getConfigPath();
		String basePath = map.get(PARA_BASEPATH);
		String attachments = map.get(PARA_ATTACHEMENTS);
		String filePath = basePath + attachments;
		return filePath;
	}
	
	public static String getFileSubPath() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String _str = sdf.format(new Date());
		return _str +File.separator;
	}
	
	/**
	 * 新文件名
	 * @param filename
	 * @return
	 */
	public static String getNewFileName(String filename) {
		long r = (long)(Math.random()*10000000000l);
		int dotn = filename.lastIndexOf(".");
		String newFileName = "";
		if(dotn==-1) {
			newFileName = filename+"_"+r;
		}else {
			String preName = filename.substring(0, dotn);
			String suffName = filename.substring(dotn);
			//newFileName = preName+"_"+r+suffName;
			newFileName = r+suffName;
		}
		return newFileName;
	}
	
	/**
	 * 上传文件保存
	 * @param bytes
	 * @param newFile
	 * @return
	 * @throws IOException
	 */
	public static File uploadFiles(byte[] bytes,File newFile) throws IOException {
		try {
            // Creating the directory to store userFile
            File dir = newFile.getParentFile();
            if (!dir.exists())
                dir.mkdirs();

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(newFile));
            stream.write(bytes);
            stream.flush();
            stream.close();

            log.info("Save Server File Location="
                    + newFile.getAbsolutePath());

            //return "You successfully uploaded userFile=" + fileName;
        } catch (Exception e) {
            //return "You failed to upload " + userFile.getName() + " => " + e.getMessage();
        	throw e;
        }
		return newFile;
	}

	public static File uploadFiles(byte[] bytes,String filename) throws IOException {
		String filePath = getRealFilePath()+getFileSubPath();
		File file = new File(filePath,filename);
		return uploadFiles(bytes,file);
	}


	public static String uploadFiles( List<MultipartFile> files) throws IOException {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < files.size(); ++i){
			MultipartFile file = files.get(i);
			if(file.isEmpty())continue;
			File _file = FileUtil.uploadFiles(file.getBytes(),FileUtil.getNewFileName(file.getOriginalFilename()));
			buffer.append((buffer.length()>0 ? "," : "")
					+"path:"+File.separator+FileUtil.getFileSubPath()+_file.getName()
					+";filename:"+file.getOriginalFilename());
		}
		return buffer.toString();
	}

	public static File[] getDir(String path) throws IOException {
	    File file = new File(getRealFilePath()+path);
	    if(!file.exists())throw new FileNotFoundException("文件或目录不存在");
        return file.listFiles();
    }

	
	
}
