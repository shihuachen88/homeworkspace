package gov.doge.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    public void createCustomerTable() {
//        String sql = """
//            CREATE TABLE IF NOT EXISTS customers (
//                id SERIAL PRIMARY KEY,
//                first_name VARCHAR(255),
//                last_name VARCHAR(255)
//            );
//        """.trimIndent();
//        jdbcTemplate.execute(sql);
//        System.out.println("Customers table ensured to exist.");
//    }
//
//    public void insertCustomer(String firstName, String lastName) {
//        String sql = "INSERT INTO customers (first_name, last_name) VALUES (?, ?)";
//        jdbcTemplate.update(sql, firstName, lastName);
//        System.out.println("Inserted customer: " + firstName + " " + lastName);
//    }
//
//    public List<Map<String, Object>> findAllCustomers() {
//        String sql = "SELECT id, first_name, last_name FROM customers";
//        return jdbcTemplate.queryForList(sql);
//    }
}

