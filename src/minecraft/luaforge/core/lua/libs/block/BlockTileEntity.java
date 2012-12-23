package luaforge.core.lua.libs.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BlockTileEntity extends TileEntity {

    private String blockName;

    public BlockTileEntity() {}
    
    public BlockTileEntity(String blockName) {
        this.blockName = blockName;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        // TODO: Implement per block methods of this; possibly through functions
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        // TODO: Implement per block methods of this; possibly through functions
    }

}
