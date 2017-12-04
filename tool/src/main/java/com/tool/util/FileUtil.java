package com.tool.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 文件工具类
 * 
 */
public class FileUtil {
	private static String CHARSET_GBK = "GBK";
	private static String CHARSET_UTF_8 = "UTF-8";
	private static String CHARSET_UTF_16LE = "UTF-16LE";
	private static String CHARSET_UTF_16BE = "UTF-16BE";
	public final static int FILTER_TYPE_DELECT =1;
	public final static int FILTER_TYPE_SELECT =2;
	public final static int FILTER_TYPE_NOFILTER =-1;
	public final static int FILTER_TYPE_NOFILTER_ONELINE =-2;
	
	/**
	 * 创建文件
	 */
	public static String createDataFile(String path,String fileName) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String dataFileName = path + "/" + fileName;
		File dataFile = new File(dataFileName);
		try {
			dataFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			dataFileName  = null;
		}
		return dataFileName;
	} 
	public static byte[] readFileByBytes(String fileName) {
/*        File file = new File(fileName);
        byte[] temp = null;
        InputStream in = null;
        try {
            // 一次读一个字节
            in = new FileInputStream(file);
            temp = new byte[in.available()];
            
            in.read(temp);
           
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            temp= null;
        }finally{
            try{
            	if(in !=null)in.close();
            }catch(Exception e2){
            	in =null;
            }
        }
        return temp;*/
		 File file = new File(fileName);
	        byte[] temp = null;
	        InputStream in = null;
	        ByteArrayOutputStream  out =null;
	        try {
	        	byte []  buffer =new byte[10240];

	        	int len = -1;
	            in = new FileInputStream(file);
	            out = new ByteArrayOutputStream();
	            while((len=in.read(buffer)) !=-1){
	            	out.write(buffer,0,len);
	            }
	            temp = out.toByteArray();
	            out.close();
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	            temp = null;
	        }finally{
	            try{
	            	if(out !=null)out.close();
	            }catch(Exception e2){
	            	out =null;
	            }
	            try{
	            	if(in !=null)in.close();
	            }catch(Exception e2){
	            	in =null;
	            }
	        }
	        return temp;
    }
	
	public static byte[] readFileByBytes(String fileName,int maxSize) {
        File file = new File(fileName);
        byte[] temp = null;
        InputStream in = null;
        ByteArrayOutputStream  out =null;
        int totalLen =0;
        try {
        	byte [] buffer =null;
        	if(maxSize >0){
        		buffer =new byte[maxSize];
        	}else{
        		 buffer =new byte[1024];
        	}
        	int len = -1;
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            while((len=in.read(buffer)) !=-1){
            	out.write(buffer,0,len);
            	totalLen +=len;
            	if(totalLen >=maxSize)break;
            }
            temp = out.toByteArray();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            temp = null;
        }finally{
            try{
            	if(out !=null)out.close();
            }catch(Exception e2){
            	out =null;
            }
            try{
            	if(in !=null)in.close();
            }catch(Exception e2){
            	in =null;
            }
        }
        return temp;
    }
	
	public static String read(String fileName ,String filter,int filterType) {
		String fileContent = "";
		InputStreamReader in = null;
		BufferedReader buffer = null;
		try {
			File f = new File(fileName);
			if (!f.exists() || !f.isFile()) {
				return null;
			}

			String fileCharset = getCharsetByFile(f);
			in = new InputStreamReader(new FileInputStream(
					fileName), fileCharset);
			buffer = new BufferedReader(in);
			StringBuffer sb = new StringBuffer("");
			String line = null;
			//带bom的utf8文件去bom
			if(CHARSET_UTF_8.equals(fileCharset)){
				byte[] firstline = buffer.readLine().getBytes()  ;
				if(firstline[0] == (byte) 0xEF
						&& firstline[1] == (byte) 0xBB
						&& firstline[2] == (byte) 0xBF){
					//去bom
					byte[] noBomByte=new  byte[firstline.length-3];
					for(int k=3;k<firstline.length;k++){
						noBomByte[k-3]=firstline[k];
					}
					sb.append(new String(noBomByte));
				}else{
					sb.append(new String(firstline));
				}
				if(filterType !=FILTER_TYPE_NOFILTER_ONELINE)sb.append("\r\n");
			}
			while ((line = buffer.readLine()) != null) {
				switch(filterType){
				case FILTER_TYPE_NOFILTER: 
					sb.append(line).append("\r\n");
					break;
				case FILTER_TYPE_NOFILTER_ONELINE: 
					sb.append(line);
					break;
				case FILTER_TYPE_DELECT: 
					if(! line.toLowerCase().contains(filter.toLowerCase())){	//if(! line.toLowerCase().matches(filter)){					
						sb.append(line).append("\r\n");
					}
					break;
				case FILTER_TYPE_SELECT: 
					if(line.toLowerCase().contains(filter.toLowerCase())){	//if(! line.toLowerCase().matches(filter)){
						sb.append(line).append("\r\n");
					}
					break;
				}

			}

			fileContent = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
				try {
					if(buffer !=null)buffer.close();
					if(in !=null )in.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
		}
		return fileContent;
	}
    /**
     * 显示输入流中还剩的字节数
     */
    private static void showAvailableBytes(InputStream in) {
        try {
            System.out.println("当前字节输入流中的字节数为:" + in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 写文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param data
	 *            数据
	 * @throws IOException
	 */
	public static void writeFile(String filePath, String data)
			throws IOException {
		File file = new File(filePath);

		File dir = file.getParentFile();

		if (!dir.exists())
			dir.mkdirs();
		if (file.exists())
			file.delete();
		file.createNewFile();

		FileOutputStream fos = new FileOutputStream(file);

		fos.write(data.getBytes());
		fos.flush();
		fos.close();
	}


	public static void writeFile(String filePath, String data,String charset)
			throws IOException {
		File file = new File(filePath);

		File dir = file.getParentFile();

		if (!dir.exists())
			dir.mkdirs();
		if (file.exists())
			file.delete();
		file.createNewFile();

		FileOutputStream fos = new FileOutputStream(file);
        fos.write(data.getBytes(charset)); 

		fos.flush();
		fos.close();
	}

	public static boolean writeFile(String filePath, String data ,String charset, boolean append)
			throws IOException {
		File file = new File(filePath);
		File dir = file.getParentFile();

		if (!dir.exists() ||!file.exists() ) return false;

		FileOutputStream fos = new FileOutputStream(file,append);
        fos.write(data.getBytes(charset)); 
		fos.flush();
		fos.close();
		return true;
	}
	

	public static boolean writeFile(String filePath, String data , boolean append)
			throws IOException {
		File file = new File(filePath);
		File dir = file.getParentFile();

		if (!dir.exists() ||!file.exists() ) return false;

		FileOutputStream fos = new FileOutputStream(file,append);
        fos.write(data.getBytes()); 
		fos.flush();
		fos.close();
		return true;
	}
	
	public static boolean writeFile(String filePath, byte[] data , boolean append)
			throws IOException {
		File file = new File(filePath);
		File dir = file.getParentFile();
		
		if (!dir.exists() ||!file.exists() ) return false;
		
		FileOutputStream fos = new FileOutputStream(file,append);
		fos.write(data); 
		fos.flush();
		fos.close();
		return true;
	}

	public static void writeFile(String filePath, byte[] data)
			throws IOException {
		File file = new File(filePath);

		File dir = file.getParentFile();

		if (!dir.exists())
			dir.mkdirs();
		if (file.exists())
			file.delete();
		file.createNewFile();

		FileOutputStream fos = new FileOutputStream(file);
		fos.write(data);
		fos.flush();
		fos.close();
	}
	
	/**
	 * 写文件
	 * @param filePath 文件路径
	 * @param is 输入流
	 * @throws IOException
	 */
	public static void writeFile(String filePath, InputStream is)
			throws IOException {
		File file = new File(filePath);

		File dir = file.getParentFile();

		if (!dir.exists())
			dir.mkdirs();
		if (file.exists())
			file.delete();
		file.createNewFile();

		FileOutputStream fos = new FileOutputStream(file);
		byte[] data = new byte[10240*3];
		int l = 0;
		int size=0;
		while ((l = is.read(data)) != -1) {
			fos.write(data, 0, l);
			size+=l;
		}
		fos.close();
	}

	/**
	 * 删除文件　可删除不为空目录
	 * 
	 * @param file
	 *            　要删除的File
	 * @return 删除是否成功
	 */
	public static boolean fileKiller(File file) {
		// Log.i("fileName", file.getPath());
		if (file.isDirectory()) {// 若为目录，则先删除目录下所有文件
			File[] subFiles = file.listFiles();
			for (int i = 0; i < subFiles.length; i++)
				if (!fileKiller(subFiles[i]))// 递归删除所有目录与文件
					return false;
		}
		return file.delete();
	}

	/**
	 * 复制文件夹
	 * 
	 * @param dir
	 *            　待复制文件夹
	 * @param folderPath
	 *            　路径
	 * @param includeDir
	 *            　是否包含本文件夹
	 * @return
	 */
	public static boolean dirCopy(File dir, String folderPath,
			boolean includeDir ,boolean isDelExists) {
		File newFile = null;
		if (includeDir)// 复制文件夹
			newFile = new File(folderPath + File.separator + dir.getName());
		else
			newFile = new File(folderPath);
		
		if(isDelExists){
			if(newFile.exists())fileKiller(newFile);
			if (!newFile.mkdirs()) {// 不存在，创建目录
				return false;
			}
		}else{
			if (!newFile.exists() && !newFile.mkdirs()) {// 不存在，创建目录
				return false;
			}
		}

		File[] subFiles = dir.listFiles();

		boolean creatSuccess = false;
		for (int i = 0; i < subFiles.length; i++) {
			if (subFiles[i].isDirectory())
				creatSuccess = dirCopy(subFiles[i], newFile.getPath(), true,isDelExists);
			else
				creatSuccess = fileCopy(subFiles[i], newFile.getPath(),true);

			if (!creatSuccess) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 文件复制
	 * 
	 * @param file
	 *            　源文件
	 * @param folderPath
	 *            　目标目录
	 * @return　复制是否成功
	 */
	public static boolean fileCopy(File file, String folderPath ,boolean isDelExist) {
		File newFile = new File(folderPath + File.separator + file.getName());
		try {
			if(newFile.exists() && isDelExist)newFile.delete();
			
			newFile.createNewFile();// 创建文件

			OutputStream os = new FileOutputStream(newFile);
			InputStream is = new FileInputStream(file);
			byte buffer[] = new byte[10240];
			int realLength;
			while ((realLength = is.read(buffer)) > 0) {
				os.write(buffer, 0, realLength);
			}
			is.close();
			os.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public static boolean fileCopy(File file, String folderPath ,String charset,boolean isDelExist) {
		File newFile = new File(folderPath + File.separator + file.getName());
		try {
			if(newFile.exists() && isDelExist)newFile.delete();
			
			newFile.createNewFile();// 创建文件

			OutputStream os = new FileOutputStream(newFile);
			InputStream is = new FileInputStream(file);
			byte buffer[] = new byte[10240];
			int realLength;
			while ((realLength = is.read(buffer)) > 0) {
				os.write(buffer, 0, realLength);
			}
			is.close();
			os.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
	/**
	 * 文件名修正
	 * 
	 * @param fileName
	 * @return
	 */
	public static String modifyFileName(String fileName) {
		if (fileName == null)
			return null;
		String s = "\\/:*?\"<>|";
		StringBuffer sb = new StringBuffer();
		char[] chars = fileName.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (s.indexOf(chars[i]) == -1) {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}

	public static String getCharsetByFile(File file) {
		/*
		 *  ANSI：无格式定义；
		 *  Unicode：前两个字节为FFFE； 
		 *  Unicode big endian：前两字节为FEFF；　 
		 *  UTF-8：前两字节为EFBB；　
		 */
		String charset = CHARSET_GBK;
		byte[] first3Bytes = new byte[3];
		BufferedInputStream bis =null;
		try {
			boolean checked = false;
			bis = new BufferedInputStream(
					new FileInputStream(file));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1){
				if(bis !=null)bis.close();
				return charset;
			}
			if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = CHARSET_UTF_16LE;
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE
					&& first3Bytes[1] == (byte) 0xFF) {
				charset = CHARSET_UTF_16BE;
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF
					&& first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = CHARSET_UTF_8;
				checked = true;
			}
			//bis.reset();
			if (!checked) {
				// int len = 0;
				int loc = 0;

				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
															// (0x80
															// - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = CHARSET_UTF_8;
								break;
							} else
								break;
						} else
							break;
					}
				}
			}

			if(bis !=null)bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charset;
	}
}
