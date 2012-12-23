package luaforge.core.lua.libs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

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
