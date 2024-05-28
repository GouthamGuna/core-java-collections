package in.dev.gmsk.service;

import in.dev.gmsk.util.JDBCConnection;
import in.dev.gmsk.util.model.Accounts;
import in.dev.gmsk.util.model.FeeModel;

import java.util.stream.Stream;

public interface AccountService {

    Stream<Accounts> findByAll(JDBCConnection connection);

    Stream<FeeModel> getDefaulterFeeList (FeeModel model, JDBCConnection connection);
}
