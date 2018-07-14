package me.borawski.perseus.utils.instance;

import com.google.gson.JsonObject;
import me.borawski.perseus.Perseus;
import me.borawski.perseus.instance.Instance;
import me.borawski.perseus.instance.ServerType;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Ethan on 3/6/2017.
 */
public class InstanceUtil {

    public static String getServers() {
        JsonObject object = new JsonObject();
        for(final ServerType type : Perseus.getInstanceManager().getServerTypes()) {
            final JsonObject typeObject = new JsonObject();
            Perseus.getInstanceManager().getInstances().stream().filter(new Predicate<Instance>() {
                @Override
                public boolean test(Instance instance) {
                    return instance.getServerType().equals(type);
                }
            }).forEach(new Consumer<Instance>() {
                @Override
                public void accept(Instance instance) {
                    JsonObject properties = new JsonObject();
                    properties.addProperty("name", Perseus.getInstanceManager().getAssignmentID(instance.getPort()));
                    properties.addProperty("status", instance.getStatus());
                    properties.addProperty("players", instance.getPlayers());
                    typeObject.add(instance.getName(), properties);
                }
            });
            object.add(type.getName(), typeObject);
        }
        return object.toString();
    }

}
