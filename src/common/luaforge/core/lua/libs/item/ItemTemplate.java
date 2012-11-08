package luaforge.core.lua.libs.item;

import net.minecraft.src.Item;

public class ItemTemplate extends Item {
    private String visibleName;
    private String hiddenName;
    private String textureFile;
    /*
     * otherArgs[0] = visibleName (String)
     * otherArgs[1] = hiddenName  (String)
     * otherArgs[2] = textureFile (String)
     * otherArgs[3] = maxStackSize(int)
     */
    public ItemTemplate(int id, int iconIndex, Object[] otherArgs) {
        super(id);
        this.setIconIndex(iconIndex);
        maxStackSize = (Integer) otherArgs[3];
        visibleName = otherArgs[0].toString();
        hiddenName = otherArgs[1].toString();
        this.setItemName(hiddenName);
        textureFile = otherArgs[2].toString();
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
