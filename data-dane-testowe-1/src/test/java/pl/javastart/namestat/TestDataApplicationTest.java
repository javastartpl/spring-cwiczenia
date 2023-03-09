package pl.javastart.namestat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootTest
class TestDataApplicationTest {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select count(*) from name_stats");
        resultSet.next();
        int count = resultSet.getInt(1);
        Assertions.assertEquals(1017, count);
    }

}
