package luaforge.core.lua.libs.item;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumAction;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.World;

public class ItemTemplate extends Item {

    private String visibleName;
    private String hiddenName;
    private String textureFile;
    
    public EnumAction action = EnumAction.none;
    
    /** Number of ticks to run while 'EnumAction'ing until result. */
    public final int itemUseDuration = 32; // TODO: Make itemUseDuration configurable

    /** The amount this food item heals the player. */
    private final int healAmount = 0; // TODO: Make healAmount configurable
    private final float saturationModifier = 0; // TODO: Make saturationModifier configurable

    /**
     * If this field is true, the food can be consumed even if the player don't need to eat.
     */
    private boolean alwaysEdible; // TODO: Make alwaysEdible configurable

    /**
     * represents the potion effect that will occurr upon eating this food. Set by setPotionEffect
     */
    private int potionId;

    /** set by setPotionEffect */
    private int potionDuration;

    /** set by setPotionEffect */
    private int potionAmplifier;

    /** probably of the set potion effect occurring */
    private float potionEffectProbability;

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

    @Override
    public EnumAction getItemUseAction(ItemStack is) {
        return action;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World w, EntityPlayer player) {
        if(this.action == EnumAction.eat) {
            if (player.canEat(this.alwaysEdible)) {
                player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
            }
        }
        return itemStack;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return this.itemUseDuration;
    }

    @Override
    public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        --par1ItemStack.stackSize;
        par3EntityPlayer.getFoodStats().addStats(this.healAmount, this.saturationModifier);
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        this.func_77849_c(par1ItemStack, par2World, par3EntityPlayer);
        return par1ItemStack;
    }
    
    protected void func_77849_c(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par2World.isRemote && this.potionId > 0 && par2World.rand.nextFloat() < this.potionEffectProbability)
        {
            par3EntityPlayer.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
        }
    }

    public String getVisibleName() {
        return visibleName;
    }

    public String getHiddenName() {
        return hiddenName;
    }
}
