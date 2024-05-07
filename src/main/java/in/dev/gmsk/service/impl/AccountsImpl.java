package in.dev.gmsk.service.impl;

import in.dev.gmsk.service.AccountService;
import in.dev.gmsk.util.JDBCConnection;
import in.dev.gmsk.util.model.Accounts;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AccountsImpl implements AccountService {

    @Override
    public Stream<Accounts> findByAll(JDBCConnection jdbcConnection) {
        try {
            List<Accounts> accountsList = new ArrayList<>();
            String query = "SELECT av.`locationId`, av.`vouchertype`, ah.`accountsheadingname`, av.`voucherdate`, avh.`amount` FROM `accounts_heading` ah LEFT JOIN `accounts_voucher_heading`  avh ON avh.`headingId` = ah.`id` JOIN `accounts_voucher` av ON av.`id` = `voucherId` ORDER BY ah.`id`";
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

            return accountsList.parallelStream().distinct();
        } catch (Exception e) {
            throw new RuntimeException(STR."Error Occer \{e}");
        }
    }
}
