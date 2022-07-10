package surreal.contentcreator.types;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class CTPart {
    public static final Map<String, CTPart> PARTS = Maps.newHashMap();

    public String name;
    public String texName;

    public Set<CTMaterial> materials;

    public CTPart(String name, String texName) {
        this.name = name;
        this.texName = texName;

        this.materials = Sets.newHashSet();

        PARTS.put(name, this);
    }
}
