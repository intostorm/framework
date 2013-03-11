package com.ccesun.framework.core.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = -4587628773840915247L;

	private String basePath = "uploads";
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);  
		
		String basePathParam = config.getInitParameter("basePath");
		if (basePathParam != null)
			this.basePath = config.getInitParameter("basePath");
	}

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String path = req.getParameter("p");
		path = path.startsWith("/") ? path : "/".concat(path);
		path = getServletContext().getRealPath(basePath) + path;

		File targetFile = new File(path);
		if (!targetFile.exists()) {
			res.setContentType("text/html;charset=UTF-8");
			res.getWriter().print("指定文件不存在！");
			return;
		}

		int index = path.lastIndexOf("/"); 
		String fileName = path.substring(index + 1);

		ServletOutputStream out = res.getOutputStream();
		res.setHeader("Content-disposition", "attachment;filename="+ fileName);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(path));
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

}