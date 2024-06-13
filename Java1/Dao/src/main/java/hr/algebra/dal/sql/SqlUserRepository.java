/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.UserRepository;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Optional;
import javax.sql.DataSource;
import org.mindrot.jbcrypt.BCrypt;

public class SqlUserRepository implements UserRepository {

    private static final String ID_USER = "IDUser";
    private static final String USERNAME = "Username";
    private static final String HASHED_PASSWORD = "HashedPassword";
    private static final String ROLE_ID = "RoleID";
    private static final String USER_EXISTS = "{ CALL userExists (?,?) }";
    private static final String CREATE_USER = "{ CALL createUser (?,?,?,?) }";
    private static final String SELECT_USER = "{ CALL selectUser (?) }";

    @Override
    public int userExists(String userName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(USER_EXISTS)) {
            stmt.setString(1, userName);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(2);
        }
    }

    @Override
    public int register(String username, String password, int role) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setInt(3, role);
            stmt.registerOutParameter(4, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(4);
        }
    }

    @Override
    public Optional<User> login(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_USER)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString(HASHED_PASSWORD);
                    if (BCrypt.checkpw(password, hashedPassword)) {
                        return Optional.of(createUserFromResultSet(rs));
                    }
                }
            }
        }
        return Optional.empty();
    }

    private static User createUserFromResultSet(ResultSet rs) throws Exception {
        return new User(
                rs.getInt(ID_USER),
                rs.getString(USERNAME),
                rs.getString(HASHED_PASSWORD),
                rs.getInt(ROLE_ID));
    }

}
