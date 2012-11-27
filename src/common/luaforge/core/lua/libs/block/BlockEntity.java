package luaforge.core.lua.libs.block;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockEntity extends BlockContainer {

    public String className = "luaforge.core.lua.libs.block.BlockTileEntity";
    
    private String textureFile;
    private String visibleName;
    private String hiddenName;
    
    public TileEntity instance;

    public BlockEntity(int id, int iconIndex, Material material, String[] otherArgs) {
        super(id, iconIndex, material);
        this.textureFile = otherArgs[0];
        this.visibleName = otherArgs[1];
        this.setBlockName(otherArgs[2]);
        this.hiddenName = otherArgs[2];
    }
    
    @Override
    public TileEntity createNewTileEntity(World w) {
        if (instance != null) { return instance; }
        try {
            Class<?> c = Class.forName(className);
            return (TileEntity) c.getConstructor(String.class).newInstance(getHiddenName());
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
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
