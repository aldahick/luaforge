package luaforge.core.lua.libs.block;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class BlockTileEntity extends TileEntity {

    private String blockName;

    public BlockTileEntity() {}
    
    public BlockTileEntity(String blockName) {
        System.out.println("CUSTOM CONSTRUCTOR CALLED!");
        this.blockName = blockName;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        // TODO: Implement per block methods of this
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        // TODO: Implement per block methods of this
    }

}
