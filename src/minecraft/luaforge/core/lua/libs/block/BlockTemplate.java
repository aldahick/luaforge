package luaforge.core.lua.libs.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTemplate extends Block {

    private String textureFile;
    private String visibleName;
    private String hiddenName;

    public BlockTemplate(int id, int iconIndex, Material material, String[] otherArgs) {
        super(id, iconIndex, material);
        this.textureFile = otherArgs[0];
        this.visibleName = otherArgs[1];
        this.setBlockName(otherArgs[2]);
        this.hiddenName = otherArgs[2];
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