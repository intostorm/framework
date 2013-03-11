package com.ccesun.framework.plugins.report;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperReportUtils {
	
	private static JRDataSource getDataSource(List<?> dataList) {
		if (dataList == null || dataList.isEmpty())
			return new JREmptyDataSource();
		return new JRBeanCollectionDataSource(dataList);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void exportHTML(HttpServletResponse response, String jasperReportFilePath, Map parameters, List<?> dataList)
			throws Exception {
		JasperReport jasperReport = (JasperReport)JRLoader.loadObjectFromFile(jasperReportFilePath);
		JRDataSource dataSource = getDataSource(dataList);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		JRExporter exporter = new JRHtmlExporter();
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
		exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
		exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "images/");
		exporter.exportReport();
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void exportPdf(HttpServletResponse response, String jasperReportFilePath, Map parameters, List<?> dataList, String pdfName) throws Exception {
		JasperReport jasperReport = (JasperReport)JRLoader.loadObjectFromFile(jasperReportFilePath);
		JRDataSource dataSource = getDataSource(dataList);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		JRExporter exporter = new JRPdfExporter();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.exportReport();
		
		byte[] bytes = baos.toByteArray();
		if (bytes != null && bytes.length > 0) {
			response.reset();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=" + pdfName + ".pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void exportExcel(HttpServletResponse response, String jasperReportFilePath, Map parameters, List<?> dataList, String excelName) throws Exception {
		JasperReport jasperReport = (JasperReport)JRLoader.loadObjectFromFile(jasperReportFilePath);
		JRDataSource dataSource = getDataSource(dataList);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		JRExporter exporter = new JRXlsExporter();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
		exporter.exportReport();
		
		byte[] bytes = baos.toByteArray();
		if (bytes != null && bytes.length > 0) {
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=" + excelName + ".xls");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void exportPdf(OutputStream os, String jasperReportFilePath, Map parameters, List<?> dataList, String pdfName) throws Exception {
		JasperReport jasperReport = (JasperReport)JRLoader.loadObjectFromFile(jasperReportFilePath);
		JRDataSource dataSource = getDataSource(dataList);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		JRExporter exporter = new JRPdfExporter();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.exportReport();
		
		byte[] bytes = baos.toByteArray();
		if (bytes != null && bytes.length > 0) {
			os.write(bytes, 0, bytes.length);
			os.flush();
			os.close();
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void exportExcel(OutputStream os, String jasperReportFilePath, Map parameters, List<?> dataList, String excelName) throws Exception {
		JasperReport jasperReport = (JasperReport)JRLoader.loadObjectFromFile(jasperReportFilePath);
		JRDataSource dataSource = getDataSource(dataList);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		JRExporter exporter = new JRXlsExporter();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
		exporter.exportReport();
		
		byte[] bytes = baos.toByteArray();
		if (bytes != null && bytes.length > 0) {
			os.write(bytes, 0, bytes.length);
			os.flush();
			os.close();
		}
		
	}
	
}
