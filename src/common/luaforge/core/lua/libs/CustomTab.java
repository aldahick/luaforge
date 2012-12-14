package luaforge.core.lua.libs;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;

public class CustomTab extends CreativeTabs {
    
    private ItemStack icon;
    
    public CustomTab(String label, ItemStack icon) {
        super(label);
        this.icon = icon;
    }
    
    @Override
    public ItemStack getIconItemStack() {
        return icon;
    }
}
