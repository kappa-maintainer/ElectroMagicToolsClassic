package weissmoon.electromagictools.api;

import net.minecraft.util.registry.RegistryDefaulted;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Weissmoon on 8/11/19.
 */
public class RegistryDefaultedLocked<K, V> extends RegistryDefaulted<K, V> {

    private static final Logger LOGGER = LogManager.getLogger();
    private Object[] values;

    public RegistryDefaultedLocked(V defaultObjectIn) {
        super(defaultObjectIn);
    }

    /**
     * Register an object on this registry.
     */
    public void putObjectB(K key, V value)
    {
        Validate.notNull(key);
        Validate.notNull(value);
        this.values = null;

        if (this.registryObjects.containsKey(key)) {
            LOGGER.debug("Attempting to add duplicate key '{}' to registry", key);
        }else{
            this.registryObjects.put(key, value);
        }
    }
    public void putObject(K key, V value)
    {
        Validate.notNull(key);
        Validate.notNull(value);

        LOGGER.fatal("Please use pubObjectB(K, V) to register. '{}' not added to registry.", key);
    }
}
