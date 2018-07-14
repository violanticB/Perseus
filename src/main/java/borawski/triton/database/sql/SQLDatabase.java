package borawski.triton.database.sql;

import borawski.triton.database.Database;

/**
 * Created by Ethan on 5/10/2018.
 */
public interface SQLDatabase extends Database {

    SQLConnection getConnection();

}
