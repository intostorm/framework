package com.ccesun.framework.plugins.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DynamicReportBuilder {

	private Log log = LogFactory.getLog(this.getClass());
	
	private JasperReportBuilder jrb = report();
	
	public DynamicReportBuilder addColumn(String label, String property) {
		jrb.addColumn(col.column(label, property, String.class));
		return this;
	}
	
	public DynamicReportBuilder addColumn(String label, String property, Class<?> cls) {
		jrb.addColumn(col.column(label, property, cls));
		return this;
	}
	
	public DynamicReportBuilder addColumn(String label, String property, Class<?> cls, int width) {
		jrb.addColumn(col.column(label, property, cls).setWidth(width));
		return this;
	}
	
	public DynamicReportBuilder addParameter(String label, Object value) {
		jrb.addParameter(label, value);
		return this;
	}
	
	/*public DynamicReportBuilder setJasperTemplateDesign(InputStream jasperTemplateDesignInputSteam) {
		try {
			jrb.setTemplateDesign(jasperTemplateDesignInputSteam);
		} catch (DRException e) {
			if (log.isErrorEnabled()) {
				log.error("jasper template design not found!", e);
			}
		}
		return this;
	}
	
	public DynamicReportBuilder setTemplateDesign(File file) {
		try {
			jrb.setTemplateDesign(file);
		} catch (DRException e) {
			if (log.isErrorEnabled()) {
				log.error("jasper template design not found!", e);
			}
		}
		return this;
	}

	public DynamicReportBuilder setTemplateDesign(String fileName){
		try {
			jrb.setTemplateDesign(fileName);
		} catch (DRException e) {
			if (log.isErrorEnabled()) {
				log.error("jasper template design not found!", e);
			}
		}
		return this;
	}

	public DynamicReportBuilder setTemplateDesign(JasperDesign jasperDesign) {
		try {
			jrb.setTemplateDesign(jasperDesign);
		} catch (DRException e) {
			if (log.isErrorEnabled()) {
				log.error("jasper template design not found!", e);
			}
		}
		return this;
	}

	public DynamicReportBuilder setTemplateDesign(URL jasperDesignUrl) {
		try {
			jrb.setTemplateDesign(jasperDesignUrl);
		} catch (DRException e) {
			if (log.isErrorEnabled()) {
				log.error("jasper template design not found!", e);
			}
		}
		return this;
	}*/
	
	private void prepareReport(List<?> objects, String title){

		jrb.setPageFormat(PageType.A4, PageOrientation.PORTRAIT) 
			.setTemplate(Templates.reportTemplate)  
			.title(Templates.createTitleComponent(title))
			.pageFooter(Templates.footerComponent)
			.setDataSource(objects);
	}
	
	public DynamicReportBuilder outputPdf(HttpServletResponse response, List<?> objects, String title, String fileName) {
		
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".pdf");
		
		try {
			return outputPdf(response.getOutputStream(), objects, title, fileName);
		} catch (IOException e) {
			if (log.isErrorEnabled())
				log.error("error on getting response outputStream! ", e);
			return this;
		}
	}
	
	public DynamicReportBuilder outputExcel(HttpServletResponse response, List<?> objects, String title, String fileName) {
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
		
		try {
			return outputExcel(response.getOutputStream(), objects, title, fileName);
		} catch (IOException e) {
			if (log.isErrorEnabled())
				log.error("error on getting response outputStream! ", e);
			return this;
		}
	}
	
	public DynamicReportBuilder outputHtml(HttpServletResponse response, List<?> objects, String title) {
		
		prepareReport(objects, title);
		try {
			jrb.toHtml(response.getOutputStream());
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("Could not output HTML to response", e);
			}
		}
		return this;
	}
	
	public DynamicReportBuilder outputPdf(OutputStream os, List<?> objects, String title, String fileName) {
		
		prepareReport(objects, title);
		try {
			jrb.toPdf(os);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("Could not output PDF to response", e);
			}
		}
		return this;
	}
	
	public DynamicReportBuilder outputExcel(OutputStream os, List<?> objects, String title, String fileName) {
		
		prepareReport(objects, title);
		try {
			jrb.toExcelApiXls(os);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("Could not output EXCEL to response", e);
			}
		}
		return this;
	}
}
