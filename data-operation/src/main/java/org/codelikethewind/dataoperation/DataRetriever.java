package org.codelikethewind.dataoperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DataRetriever {
	
	// DB
	public static final String DB_URL = System.getProperty("db.url", "jdbc:postgresql://localhost:5432/bpm");
	public static final String DB_USER = System.getProperty("db.user", "tolarewa");
	public static final String DB_PASSWORD = System.getProperty("db.password", "");
	
	public static final String ACTIVE_PROCESS_QUERY = "select PROCESSINSTANCEID, PROCESSID, EXTERNALID from "
			+ "PROCESSINSTANCELOG where STATUS=1; ";
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to load Postgres Driver");
		}
	}
	
	public static List<DataOperationSpec> findAllActiveProcesses() throws SQLException {
		
		List<DataOperationSpec> result = new ArrayList<>();
		List<DataOperationSpec> exclueded = new ArrayList<>();
		
		try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
			PreparedStatement stmt = connection.prepareStatement(ACTIVE_PROCESS_QUERY);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				long pid = rs.getLong(1);
				String deploymentID = rs.getString(3);

				DataOperationSpec spec = new DataOperationSpec(deploymentID, pid);
				result.add(spec);
			}
			System.out.println("[Query Result] Total: " + (result.size() + exclueded.size()+", Excluded: " + exclueded.size()+", Count: " + result.size()));
			return result;
		}
	}
	
	public static void main(String [] arg) {
		try {
			List<DataOperationSpec> l = findAllActiveProcesses();

			System.out.println("\nTotal: " + l.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
