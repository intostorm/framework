package com.ccesun.framework.tools.codegen;

import java.util.HashMap;
import java.util.Map;

public interface IProcessor {
	
	static final Map<Integer, String> TYPE_MAPPER = new HashMap<Integer, String>() {

		private static final long serialVersionUID = -7444194454550826615L;

		{
			put(java.sql.Types.CHAR, 		"String");
			put(java.sql.Types.VARCHAR, 	"String");
			put(java.sql.Types.LONGVARCHAR, "String");
			put(java.sql.Types.CLOB, 		"String");
			put(java.sql.Types.CLOB, 		"String");
			put(java.sql.Types.INTEGER, 	"Integer");
			put(java.sql.Types.FLOAT, 		"Float");
			put(java.sql.Types.DECIMAL, 	"Integer");
			put(java.sql.Types.BOOLEAN, 	"boolean");
			put(java.sql.Types.TINYINT, 	"boolean");
			put(java.sql.Types.BIT, 		"boolean");
			put(java.sql.Types.DATE, 		"java.util.Date");
			put(java.sql.Types.TIME, 		"java.util.Date");
			put(java.sql.Types.TIMESTAMP, 	"java.util.Date");
		}
	}; 

	<T extends IArtifact> boolean canHandler(Class<T> cls);
	
	<T extends IArtifact> void process(T artifact, CodeGenConfiguation config);

}
