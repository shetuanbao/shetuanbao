package com.stb.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.springframework.util.ResourceUtils;

public class Imageutil {
	public static byte[] getImage(String name) {
		 byte[] data=null;
	     try
			{
	     	File file = ResourceUtils.getFile("classpath:img/"+name);
				//根据路径创建输入流
				BufferedInputStream in=new BufferedInputStream(new FileInputStream(file));
				//创建一个新的缓冲输出流，指定缓冲区大小为1024Byte
				ByteArrayOutputStream out=new ByteArrayOutputStream(1024);
				byte[] temp=new byte[1024];		                      //创建大小为1024的比特数组
				int size=0;						                      //定义大小常量
				while((size=in.read(temp))!=-1)                       //若有内容读出，写入比特数组
				{	
					out.write(temp,0,size);				              //写入比特数组
				}
				data=out.toByteArray();			                      //将图片信息以比特数组形式读出并赋值给图片比特数组
				out.close();                                          //关闭输出流
				in.close();                                           //关闭输入流
			}
			catch(Exception e)
			{
				e.printStackTrace();                                  //打印异常信息
			}
	     return data;
	}
}
