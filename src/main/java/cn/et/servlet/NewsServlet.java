package cn.et.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.et.model.Mynews;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class NewsServlet
 */
public class NewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public static final String HTML_DIR = "E:\\html";
    Mynews mynews = new Mynews();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Date time = new Date();
		String sdfTime = sdf.format(time);
		String uuid =UUID.randomUUID().toString();
		
		//生成html
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File("src/main/resources"));
			cfg.setObjectWrapper(new DefaultObjectWrapper()); 
			
			Map root = new HashMap();
			root.put("title", title);
			root.put("content", content);
			root.put("date", sdfTime);			
			Template temp = cfg.getTemplate("newsdetali.ftl");
			String newsPath = HTML_DIR+"/"+uuid+".html";
			Writer out = new OutputStreamWriter(new FileOutputStream(newsPath));
			temp.process(root, out);
			out.flush(); 
			out.close();
			mynews.insertNews(title, content, uuid+".html", sdfTime);
			response.getWriter().println("发布完成");
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
