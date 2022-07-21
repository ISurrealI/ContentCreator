package surreal.contentcreator.types.parts;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import surreal.contentcreator.types.CTMaterial;
import surreal.contentcreator.util.GeneralUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class PartItem {
    public static final Map<String, PartItem> PARTS = Maps.newHashMap();

    public String name;
    public String oreName;

    public Set<CTMaterial> materials;

    public PartItem(String name, String oreName) {
        this.name = name;
        this.oreName = GeneralUtil.toUppercase(CaseFormat.LOWER_UNDERSCORE, CaseFormat.LOWER_CAMEL, oreName);

        this.materials = Sets.newHashSet();

        PARTS.put(name, this);
    }

    public Collection<CTMaterial> getMaterials() {
        return this.materials;
    }
}
