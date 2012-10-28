package luaforge.core.lua.libs.block;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.StepSound;

public class BlockTemplate extends Block {

     private String textureFile;

     public BlockTemplate (int id, int iconIndex, Material material, String[] otherArgs) {
            super(id, iconIndex, material);
            this.setBlockName(otherArgs[0]);
            this.textureFile = otherArgs[1];
     }
     
     @Override
     public String getTextureFile() {
           return textureFile;
     }
     
     public void setTab(CreativeTabs tab){
            this.setCreativeTab(tab);
     }
     
     public void setSoundOnStep(StepSound sound) {
           this.setStepSound(sound);
     }
     
     public void setHardnessResistance(float hardness, float resistance) {
           this.setHardness(hardness);
           this.setResistance(resistance);
     }
}
