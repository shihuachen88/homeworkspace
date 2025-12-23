package gov.doge.homework;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/ecfr")
public class EcfrController {

	private static JdbcTemplate jdbcTemplate = new JdbcTemplate();
    @GetMapping("/wordcount")
    public List<WordCount> wordCount() {
 
    	return jdbcTemplate.query(
    	"SELECT * FROM word_count_per_agency()", new BeanPropertyRowMapper<>(WordCount.class)
        );
    }

    @GetMapping("/volatility")
    public List<Volatility> volatility() {
        return jdbcTemplate.query(
            "SELECT * FROM regulatory_volatility()", new BeanPropertyRowMapper<>(Volatility.class)
        );
    }
}
