package luaforge.core.lua.libs.block;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.StepSound;

public class BlockTemplate extends Block {

    private String textureFile;
    private String visibleName;

    public BlockTemplate(int id, int iconIndex, Material material, String[] otherArgs) {
        super(id, iconIndex, material);
        this.textureFile = otherArgs[0];
        this.visibleName = otherArgs[1];
        this.setBlockName(otherArgs[2]);
        this.setTab(CreativeTabs.tabMisc); // DEBUG
    }

    @Override
    public String getTextureFile() {
        return textureFile;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public void setTab(CreativeTabs tab) {
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
