package in.dev.gmsk.util;

import in.dev.gmsk.util.model.Accounts;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class JDBCConnectionTest {

    private final JDBCConnection jdbcConnection = new JDBCConnection(
            "jdbc:mariadb://localhost:3306/pearl", "root", "asus@root"
    );

    @Test
    void getConnection() throws SQLException {
        Connection connection = JDBCConnection.getConnection(jdbcConnection);

        assertTrue(connection.isValid(10));
        connection.close();
        assertTrue(connection.isClosed());
    }

    @Test
    void testQueryExecute() throws SQLException {

        List<Accounts> accountsList = new ArrayList<>();
        String query = "SELECT ah.`locationId`, av.`vouchertype`, ah.`accountsheadingname`, av.`voucherdate`, avh.`amount` FROM `accounts_heading` ah LEFT JOIN `accounts_voucher_heading`  avh ON avh.`headingId` = ah.`id` JOIN `accounts_voucher` av ON av.`id` = `voucherId` ORDER BY ah.`id`";
        Connection connection = JDBCConnection.getConnection(jdbcConnection);
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Accounts accounts = new Accounts();
            accounts.setLocationId(rs.getString("locationId"));
            accounts.setVoucherType(rs.getString("vouchertype"));
            accounts.setAccountsHeadingName(rs.getString("accountsheadingname"));
            accounts.setVoucherDate(LocalDate.parse(rs.getString("voucherdate")));
            accounts.setAmount(rs.getDouble("amount"));
            accountsList.add(accounts);
        }
        rs.close();
        stmt.close();
        connection.close();

        assertTrue(connection.isClosed());
        System.out.println(STR."accountsList = \{accountsList}");
    }
}