package com.mnm.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootTest
public class DatabaseConnectionTest {

    @Test
    public void testDatabaseConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://database-1.cxkaquq6qa78.ap-northeast-2.rds.amazonaws.com:3306", "root", "sklookie1234");
            if (connection != null) {
                System.out.println("연결 성공!");
            }
        } catch (SQLException e) {
            System.out.println("연결 실패: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
