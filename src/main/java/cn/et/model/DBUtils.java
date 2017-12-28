package cn.et.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DBUtils {
	/**
	 * jdbc封装类
	 */
	static Properties p=new Properties();
	static{
		
		InputStream is=DBUtils.class.getResourceAsStream("/jdbc.properties");
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取连接
	 * @throws Exception 
	 */
	public static Connection getConnection() throws Exception{
		String url=p.getProperty("url");
		String driverClass=p.getProperty("driverClass");
		String uname=p.getProperty("username");
		String password=p.getProperty("password");
		Class.forName(driverClass);
		//登录成功
		Connection conn=DriverManager.getConnection(url, uname, password);
		return conn;
	}
	/**
	 * 行和列结构
	 * id name
	 * 1  zs
	 * 2  ls
	 * 
	 * java结构
	 * 多行 List list=new ArrayList 多个行可以组成一个集合 length=2
	 * 1行 
	 *  Map map=new HashMap()
	 *  map.put("id",1)
	 *  map.put("name",zs)
	 *  
	 *  Map map1=new HashMap()
     *  map1.put("id",2)
     *  map1.put("name",ls)
	 *  
	 *List list=new ArrayList
	 *list.add(map);
	 *list.add(map1)  
	 *
	 * 1行 
	 * class Dept{
	 *   String id;
	 *   String name;
	 * }
	 *Dept d=new Dept();
	 *d.id=1;
	 *d.name='zs''
	 *Dept d1=new Dept();
     *d1.id=2;
     *d1.name='ls''
	 *List list=new ArrayList
     *list.add(d);
     *list.add(d1)  
	 *
	 *
	 *  
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	public static List<Map> query(String sql) throws Exception{
	    Connection conn=getConnection();
	    PreparedStatement pst=conn.prepareStatement(sql);
	    ResultSet rs=pst.executeQuery();
	    ResultSetMetaData rsmd=   rs.getMetaData();
	    List list=new ArrayList();
	    //获取列的个数
	    int columnCount=rsmd.getColumnCount();
	    while(rs.next()){
	        Map map=new HashMap();
	        for(int i=1;i<=columnCount;i++){
	            String colName=rsmd.getColumnName(i);
	            String colValue=rs.getString(i);
	            map.put(colName, colValue);
	        }
	        list.add(map);
	    }
	    rs.close();
	    pst.close();
	    conn.close();
	    return list;
	}
	public static int execute(String sql) throws Exception{
	    Connection conn=getConnection();
        PreparedStatement pst=conn.prepareStatement(sql);
        int i=pst.executeUpdate();
        pst.close();
        conn.close();
        return i;
	}
	/**
    * 2017年10月9日  Administrator
	 * @throws Exception 
    **/
    public static void main(String[] args) throws Exception {
        List<Map> result=query("select * from userinfo");
        System.out.println(result);
        
    }
	
	
	
	
	
	

}
