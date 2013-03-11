package com.ccesun.framework.tools.codegen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class AbstractProcessor implements IProcessor {

	protected List<MaterialDetail> materialDetails = new ArrayList<MaterialDetail>();
	
	protected Log logger = LogFactory.getLog(getClass());
	
	private boolean prepared = false;
	
	private void prepare(CodeGenConfiguation config) throws SQLException {
		
		if (prepared)
			return;
		
		DataSource dataSource = config.getDataSource();
		Connection conn = dataSource.getConnection();
		DatabaseMetaData metaData = conn.getMetaData();
		
		List<Material> materials = getMaterials(conn, metaData, config);
		
		for (Material material : materials) {
			MaterialDetail materialDetail = new MaterialDetail();
			materialDetail.setMaterial(material);
			
			preparePcMappings(conn, metaData, materialDetail);
			preparePkPcMapping(conn, metaData, materialDetail);
			materialDetails.add(materialDetail);
		}
		
		for (MaterialDetail materialDetail : materialDetails) {
			prepareFkPcMappings(conn, metaData, materialDetail);
		}
		
		conn.close();
		prepared = true;
	}
	
	private void prepareFkPcMappings(Connection conn, DatabaseMetaData metaData, MaterialDetail detail) throws SQLException {
		ResultSet rsColumns = metaData.getImportedKeys(null, null, detail.getMaterial().getTableName().toUpperCase());
		List<PropertyColumnMapping> targetPcMappings = detail.getPcMappings();
		while (rsColumns.next()) {
			String fkColumnName = rsColumns.getString("FKCOLUMN_NAME");
			String pkColumnName = rsColumns.getString("PKCOLUMN_NAME");
			String pkTableName = rsColumns.getString("PKTABLE_NAME");
			
			PropertyColumnMapping targetPac = null;
			
			for (PropertyColumnMapping propertyColumnMapping : targetPcMappings) {
				if (propertyColumnMapping.getColumnName().equalsIgnoreCase(fkColumnName)) {
					targetPac = propertyColumnMapping;
					break;
				}
			}
			
			if (targetPac != null) {
				for (MaterialDetail materialDetail : materialDetails) {
	
					if (pkTableName.equals(materialDetail.getMaterial().getTableName())) {
						List<PropertyColumnMapping> pcMappings = materialDetail.getPcMappings();
						for (PropertyColumnMapping propertyColumnMapping : pcMappings) {
							if (pkColumnName.equalsIgnoreCase(propertyColumnMapping.getColumnName())) {
								targetPac.setFk(true);
								String domainName = propertyColumnMapping.getMaterialDetail().getMaterial().getDomainName();
								targetPac.setPropertyName(WordUtils.uncapitalize(domainName));
								targetPac.setPropertyType(domainName);
							}
						}
					}
				}
			}
		}
		
	}

	private void preparePkPcMapping(Connection conn, DatabaseMetaData metaData, MaterialDetail materialDetail) throws SQLException {
		ResultSet rsColumns = metaData.getPrimaryKeys(null, null, materialDetail.getMaterial().getTableName().toUpperCase());
		while (rsColumns.next()) {
			String columnName = rsColumns.getString("COLUMN_NAME");
			List<PropertyColumnMapping> pcMappings = materialDetail.getPcMappings();
			for (PropertyColumnMapping propertyColumnMapping : pcMappings) {
				if (propertyColumnMapping.getColumnName().equalsIgnoreCase(columnName)) {
					propertyColumnMapping.setPk(true);
				}
			}
		}
		
	}

	private void preparePcMappings(Connection conn, DatabaseMetaData metaData, MaterialDetail materialDetail) throws SQLException {
		
		ResultSet rsColumns = metaData.getColumns(null, null, materialDetail.getMaterial().getTableName().toUpperCase(), null);
		while (rsColumns.next()) {
			
			String columnName = rsColumns.getString("COLUMN_NAME");
			String columnRemarks = rsColumns.getString("REMARKS");
			String propertyType = TYPE_MAPPER.get(rsColumns.getInt("DATA_TYPE"));
			String propertyName = getPropertyName(columnName);
			
			PropertyColumnMapping pcMapping = new PropertyColumnMapping();
			
			pcMapping.setColumnName(columnName);
			pcMapping.setPropertyType(propertyType);
			pcMapping.setPropertyName(propertyName);
			pcMapping.setMaterialDetail(materialDetail);
			pcMapping.setColumnRemarks(columnRemarks);
			
			materialDetail.addPcMapping(pcMapping);
		}
	}

	private List<Material> getMaterials(Connection conn, DatabaseMetaData metaData, CodeGenConfiguation config) throws SQLException {
		
		if (!config.isAllArticfacts()) {
			return config.getMaterials();
		}
		BasicDataSource dataSource = (BasicDataSource) config.getDataSource();
		String driverClassName = dataSource.getDriverClassName();
		String userName = dataSource.getUsername();
		String catelog = null;
		String schema = null;
		if (driverClassName.equals("com.mysql.jdbc.Driver")) {
			catelog = null;
			schema = null;
		}
		else if (driverClassName.equals("oracle.jdbc.driver.OracleDriver")) {
			catelog = null;
			schema = userName.toUpperCase();;
		}
		
		ResultSet rsAllTables = metaData.getTables(catelog, schema, null, new String[] {"TABLE"});
		
		List<Material> result = new ArrayList<Material>();
		
		while (rsAllTables.next()) {
			String tableName = rsAllTables.getString("TABLE_NAME");
			String domainName = tableName2BeanName(tableName);
			
			result.add(new Material(domainName, tableName));
		}
		
		config.setMaterials(result);
		return result;
	}

	protected String getPropertyName(String columnName) {
		String[] tmp = columnName.split("_");
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < tmp.length; i++) {
			if (i == 0)
				result.append(WordUtils.uncapitalize(tmp[i].toLowerCase()));
			else
				result.append(WordUtils.capitalize(tmp[i].toLowerCase()));
		}
		return result.toString();
	}
	
	protected String tableName2BeanName(String tableName) {
		String[] tmp = tableName.split("_");
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < tmp.length; i++) {
			result.append(WordUtils.capitalize(tmp[i].toLowerCase()));
		}
		return result.toString();
	}

	@Override
	public <T extends IArtifact> void process(T artifact, CodeGenConfiguation config) {
		try {
			prepare(config);
			doProcess(artifact, materialDetails, config);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	protected abstract <T extends IArtifact> String parseOutputFileName(T artifact, CodeGenConfiguation config, MaterialDetail materialDetail);
	
	protected <T extends IArtifact> void doProcess(T artifact, List<MaterialDetail> materialDetails, CodeGenConfiguation config) throws IOException, TemplateException {
		
		for (MaterialDetail materialDetail : materialDetails) {
			processMaterialDetail(artifact, materialDetail, config);
		}
		
	}

	protected void processMaterialDetail(IArtifact artifact, MaterialDetail materialDetail, CodeGenConfiguation config) throws IOException, TemplateException {
		
		Configuration freemarkerConfig = config.getFreemarkerConfig();
		
		String templateName = artifact.getTemplateName();
		Template template = freemarkerConfig.getTemplate(templateName);
		String outputFileName = parseOutputFileName(artifact, config, materialDetail);
		
		Map<String, Object> root = new HashMap<String, Object>();
		
		root.put("randomNumber", new DecimalFormat("#").format(Math.random() * Integer.MAX_VALUE));
		root.put("config", config);
		root.put("artifact", artifact);
		root.put("materialDetail", materialDetail);
		
		writeAritfact(template, root, outputFileName);
		
	}

	protected void writeAritfact(Template template, Map<String, Object> root, String outputFileName) throws IOException, TemplateException {
		File outputFile = new File(outputFileName);
		File outputFileDir = outputFile.getParentFile();
		if (!outputFileDir.exists())
			outputFileDir.mkdirs();
		
		Writer out = new FileWriter(outputFile);
		template.process(root, out);
		out.flush();
		out.close();
		if (logger.isDebugEnabled())
			logger.debug("CodeGen: " + outputFileName + " has been made successfully.");
	}
	
	
}
