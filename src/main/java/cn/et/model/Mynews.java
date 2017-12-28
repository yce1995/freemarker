package cn.et.model;

import java.util.List;
import java.util.Map;

public class Mynews{
	public void insertNews(String title,String content,String path,String date) throws Exception{
		String url = "insert into news(title,content,path,date) values('"+title+"','"+content+"','"+path+"','"+date+"')";
		DBUtils.execute(url);
	}
	public List<Map> queryNews() throws Exception{
		String url = "select * from news";
		return DBUtils.query(url);
	}
}
