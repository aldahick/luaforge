package luaforge.core.lua.libs.item;

import net.minecraft.src.Item;

public class ItemTemplate extends Item {
    private String visibleName;
    private String hiddenName;
    private String textureFile;

    public ItemTemplate(int id, int iconIndex, Object[] otherArgs) {
        super(id);
        maxStackSize = (Integer) otherArgs[0];
        textureFile = otherArgs[1].toString();
        visibleName = otherArgs[2].toString();
        hiddenName = otherArgs[3].toString();
        this.setIconIndex(iconIndex);
        this.setItemName(hiddenName);
    }
    
    @Override
    public String getTextureFile() {
        return textureFile;
    }
    
    public String getVisibleName() {
        return visibleName;
    }
    
    public String getHiddenName() {
        return hiddenName;
    }
}
