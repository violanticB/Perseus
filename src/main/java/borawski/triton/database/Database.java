package borawski.triton.database;

import java.util.Map;

/**
 * Created by Ethan on 5/10/2018.
 */
public interface Database<T> {

    String getHost();
    int getPort();
    String getUser();
    String getPass();

    Map<String, T> getDataCache();
    int getRefresh();
    void update(String key, T value);
    T query(String key);

}
