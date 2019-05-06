package com.community.shetuanbao.utils;

import java.util.ArrayList;
import java.util.List;

public class StrListChange
{

	public static List<String[]> StrToList(String info)
	{
		List<String[]> list=new ArrayList<String[]>();
		String[] s=info.split("\\|");
		int num=0;
		for(String ss:s)
		{
			num=0;
			String[] temp=ss.split("<#>");
			String[] midd=new String[temp.length];
			for(String a:temp)
			{
				midd[num++]=a;	
			}
			list.add(midd);
		}
		return list;
	}
	

	public static String[] StrToArray(String info)
	{
		int num=0;
		String[] first=info.split("\\|");
		for(int i=0;i<first.length;i++)
		{
			String[] temp1=first[i].split("<#>");
			num+=temp1.length;
		}
		String[] temp2=new String[num];
		num=0;
		for(String second:first)
		{
			String[] temp3=second.split("<#>");
			for(String third:temp3)
			{
				temp2[num]=third;
				num++;
			}
		}
		return temp2;
	}
	
	//��Listת�����ַ�
	public static String ListToStr(List<String[]> list)
	{ 
		String mess="";
		List<String[]> ls=new ArrayList<String[]>();
		ls=list;                                          
		for(int i=0;i<ls.size();i++)
		{
			String[] ss=ls.get(i);
			for(String s:ss)
			{
				mess+=s+"<#>";
		    }
			mess+="|";
		}
		return mess;
	}
	

	public static String ListToStrAndroid(List<String[]> list)
	{
		if(list.isEmpty())
		{
			return "";
		}
		String mess="";
		List<String[]> ls=new ArrayList<String[]>();
		ls=list;
		for(int i=0;i<ls.size();i++)
		{
			String[] ss=ls.get(i);
			for(String s:ss)
			{
				mess+=s+"<#>";
		    }
			mess=mess.substring(0,mess.length()-3);
			mess+="|";
		}
		return mess.substring(0,mess.length()-1);
	}
	
	//�ų���ͬ������ �����ַ�
	public static String Streamline(String mess)
	{
		String[] str=mess.split("<#>");
		String info="";
		for(int i=0;i<str.length-1;i++)
		{
			String temp=str[i];
			String temp2=str[i+1];
			if(!temp.equals(temp2))
			{
				info+=temp+"<#>";
			}
			else
			{
				continue;
			}
		}
		return info+str[str.length-1];
	}
	

	public static String ArrayToStrAndroid(String[] arry)
	{
		String str="";
		for(String ss:arry)
		{
			if(ss!=null)
			{
				str+=ss+"|";
			}
		}
		return str;
	}
}
