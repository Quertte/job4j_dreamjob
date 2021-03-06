package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDbStore {

    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> opt = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "insert into users(name, email, password) values(?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                    opt = Optional.of(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return opt;
    }

    public Optional<User> findUserByEmailAndPwd(String email, String pwd) {
        Optional<User> user = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "select * from users where email = ? and password = ?")
        ) {
            ps.setString(1, email);
            ps.setString(2, pwd);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    user = Optional.of(
                            new User(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getString("email"),
                                    it.getString("password")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
