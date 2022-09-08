package surreal.contentcreator.types.parts;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import surreal.contentcreator.types.CTMaterial;
import surreal.contentcreator.util.GeneralUtil;

import java.util.*;

public class PartBlock {
    public static final Map<String, PartBlock> PARTS = Maps.newHashMap();

    public String name;
    public String oreName;

    public List<CTMaterial> materials;

    public PartBlock(String name, String oreName) {
        this.name = name;
        this.oreName = GeneralUtil.toUppercase(CaseFormat.LOWER_UNDERSCORE, CaseFormat.LOWER_CAMEL, oreName);

        this.materials = Lists.newArrayList();

        PARTS.put(name, this);
    }
}
