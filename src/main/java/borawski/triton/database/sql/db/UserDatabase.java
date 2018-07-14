package borawski.triton.database.sql.db;

import borawski.triton.database.sql.SQLConnection;
import borawski.triton.database.sql.SQLDatabase;

import java.util.Map;

/**
 * Created by Ethan on 5/10/2018.
 */
public class UserDatabase<User> implements SQLDatabase {
    @Override
    public String getHost() {
        return null;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public String getUser() {
        return null;
    }

    @Override
    public String getPass() {
        return null;
    }

    @Override
    public Map getDataCache() {
        return null;
    }

    @Override
    public int getRefresh() {
        return 0;
    }

    @Override
    public void update(String key, Object value) {

    }

    @Override
    public Object query(String key) {
        return null;
    }

    @Override
    public SQLConnection getConnection() {
        return null;
    }
}
