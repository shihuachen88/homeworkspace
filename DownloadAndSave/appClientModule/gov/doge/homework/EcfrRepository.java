package gov.doge.homework;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EcfrRepository {

	  private static final String INSERT_SQL =
		        """
		        INSERT INTO ecfr_documents
		        (title, agency, effective_date, content, checksum)
		        VALUES (?, ?, ?, ?, ?)
		        """;

		    public static void save(
		            int title,
		            String agency,
		            Date effectiveDate,
		            String content,
		            String checksum) throws SQLException {

		        try (Connection conn = DriverManager.getConnection(
		                "jdbc:postgresql://localhost:5432/ecfr", "postgre", "admin123");
		             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

		            ps.setInt(1, title);
		            ps.setString(2, agency);
		            ps.setDate(3, effectiveDate);
		            ps.setString(4, content);
		            ps.setString(5, checksum);
		            ps.executeUpdate();
		        }
		    }
}
