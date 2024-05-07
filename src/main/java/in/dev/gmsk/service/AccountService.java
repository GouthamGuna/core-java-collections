package in.dev.gmsk.service;

import in.dev.gmsk.util.JDBCConnection;
import in.dev.gmsk.util.model.Accounts;

import java.util.stream.Stream;

public interface AccountService {

    Stream<Accounts> findByAll(JDBCConnection connection);
}
