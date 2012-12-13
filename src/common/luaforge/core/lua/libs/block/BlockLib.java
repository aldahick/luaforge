package luaforge.core.lua.libs.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.io.File;
import java.util.HashMap;
import luaforge.core.Log;
import luaforge.core.api.LuaMethod;
import luaforge.core.lua.LuaEnvironment;
import luaforge.luaj.vm2.LuaError;
import luaforge.luaj.vm2.LuaTable;
import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.Varargs;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.StepSound;

// TODO: Document that the format changed to OOP-style
public class BlockLib {

    public static HashMap<String, BlockTemplate> regularBlocks = new HashMap<String, BlockTemplate>();
    public static HashMap<String, BlockEntity> tileEntityBlocks = new HashMap<String, BlockEntity>();
    
    public static void createBlockOrBlockEntity(boolean isBlockContainer, Varargs args, LuaEnvironment env) {
        int id = args.arg1().checkint();
        int iconIndex = args.arg(2).checkint();
        String argMaterial = args.arg(3).checkjstring();
        String givenTexturePath = args.arg(4).checkjstring();
        String textureFile = "/" + (new File(env.getModPath()).getName()) + ((givenTexturePath.startsWith("/")) ? "" : "/") + givenTexturePath;
        String visibleName = args.arg(5).checkjstring();
        String blockName = args.arg(6).checkjstring();

        Material material = getMaterial(argMaterial);
        if (material == null) {
            throw new LuaError("Invalid material specified in block " + blockName);
        }
        
        if (isBlockContainer) {
            BlockEntity be = new BlockEntity(id, iconIndex, material, new String[] {textureFile, visibleName, blockName});
            tileEntityBlocks.put(blockName, be);
            GameRegistry.registerBlock(be);
            GameRegistry.registerTileEntity(BlockTileEntity.class, be.getHiddenName());
            LanguageRegistry.addName(be, be.getVisibleName());
        } else {
            BlockTemplate bt = new BlockTemplate(id, iconIndex, material, new String[] {textureFile, visibleName, blockName});
            regularBlocks.put(blockName, bt);
            GameRegistry.registerBlock(bt);
            LanguageRegistry.addName(bt, bt.getVisibleName());
        }
        
    }
    
    @LuaMethod(name = "block")
    public static Varargs createBlockTileEntity(Varargs args, LuaEnvironment env) { // TODO: document createBlockTileEntity on the wiki
        // TODO: Setup TileEntity methods to actually make this useful
        createBlockOrBlockEntity(true, args, env);
        LuaTable t = LuaValue.tableOf();
        t.setmetatable(env._G.get("block"));
        t.set(LuaValue.valueOf("name"), LuaValue.valueOf(args.arg(6).checkjstring()));
        return t;
    }
    
    @LuaMethod(name = "block")
    public static Varargs createBlock(Varargs args, LuaEnvironment env) {
        createBlockOrBlockEntity(false, args, env);
        LuaTable t = LuaValue.tableOf();
        t.setmetatable(env._G.get("block"));
        t.set(LuaValue.valueOf("name"), LuaValue.valueOf(args.arg(6).checkjstring()));
        return t;
    }
    
    @LuaMethod(name = "block")
    public static Varargs setCreativeTab(Varargs args, LuaEnvironment env) {
        String blockName = args.arg1().checktable().get(LuaValue.valueOf("name")).checkjstring();
        String tabName = args.arg(2).tojstring();
        BlockTemplate bt;
        BlockEntity be;
        CreativeTabs t = getCreativeTab(tabName);
        if (t == null) {
            Log.warning(env.getModName() + " contains an invalid tab name: " + tabName);
        }
        if ((bt = regularBlocks.get(blockName)) != null) {
            if(bt.getHiddenName().equals(blockName)) {
                bt.setCreativeTab(getCreativeTab(tabName));
            }
        } else if ((be = tileEntityBlocks.get(blockName)) != null) {
            if(be.getHiddenName().equals(blockName)) {
                be.setCreativeTab(getCreativeTab(tabName));
            }
        } else {
            throw new LuaError(env.getModName() + " contains a reference to a block that doesn't exist: " + blockName);
        }
        return LuaValue.NONE;
    }
    
    @LuaMethod(name = "block")
    public static Varargs setLightLevel(Varargs args) {
        String blockName = args.arg1().checktable().get(LuaValue.valueOf("name")).checkjstring();
        args.arg(2).checkint();
        float value = args.arg(2).tofloat();
        if((value > 1.0) || (value < 0)) {
            throw new LuaError("Value cannot be less than 0 and no greater than 1, got " + value);
        }
        if(regularBlocks.get(blockName) != null) {
            regularBlocks.get(blockName).setLightValue(value);
        } else if (tileEntityBlocks.get(blockName) != null) {
            tileEntityBlocks.get(blockName).setLightValue(value);
        } else {
            throw new LuaError("Block does not exist: " + blockName);
        }
        
        return LuaValue.NONE;
    }
    
    @LuaMethod(name = "block")
    public static Varargs setHardness(Varargs args) {
        args.arg(2).checkint();
        BlockTemplate bt = regularBlocks.get(args.arg1().checktable().get(LuaValue.valueOf("name")).checkjstring());
        BlockEntity be = tileEntityBlocks.get(args.arg1().checktable().get(LuaValue.valueOf("name")).checkjstring());
        if (bt != null) {
            bt.setHardness(args.arg(2).tofloat());
        } else if (be != null) {
            be.setHardness(args.arg(2).tofloat());
        } else {
            throw new LuaError("Block does not exist: " + args.arg1().checkjstring());
        }
        
        return LuaValue.NONE;
    }
    
    @LuaMethod(name = "block")
    public static Varargs setUnbreakable(Varargs args) { 
        
        BlockTemplate bt = regularBlocks.get(args.arg1().checktable().get(LuaValue.valueOf("name")).checkjstring());
        BlockEntity be = tileEntityBlocks.get(args.arg1().checktable().get(LuaValue.valueOf("name")).checkjstring());
        if (bt != null) {
            bt.setBlockUnbreakable();
        } else if (be != null) {
            be.setBlockUnbreakable();
        } else {
            throw new LuaError("Block does not exist: " + args.arg1().checkjstring());
        }
        
        return LuaValue.NONE;
    }
    
    @LuaMethod(name = "block")
    public static Varargs setSound(Varargs args) {
        BlockTemplate bt = regularBlocks.get(args.arg1().checktable().get(LuaValue.valueOf("name")).checkjstring());
        BlockEntity be = tileEntityBlocks.get(args.arg1().checktable().get(LuaValue.valueOf("name")).checkjstring());
        String soundName = args.arg(2).checkjstring();
        StepSound ss = getStepSound(args.arg(2).checkjstring());
        if (ss == null) {
            args.arg(3).checkint();
            args.arg(4).checkint();
            ss = new StepSound(soundName, args.arg(3).tofloat(), args.arg(4).tofloat());
        }
        if (bt != null) {
            bt.setStepSound(ss);
        } else if (be != null) {
            be.setStepSound(ss);
        } else {
            throw new LuaError("Block does not exist: " + args.arg1().checkjstring());
        }
        return LuaValue.NONE;
    }
    
    private static Material getMaterial(String m) {
        if(m.equalsIgnoreCase("air")) { return Material.air; }
        else if(m.equalsIgnoreCase("cactus")) { return Material.cactus; }
        else if(m.equalsIgnoreCase("cake")) { return Material.cake; }
        else if(m.equalsIgnoreCase("circuits")) { return Material.circuits; }
        else if(m.equalsIgnoreCase("clay")) { return Material.clay; }
        else if(m.equalsIgnoreCase("cloth")) { return Material.cloth; }
        else if(m.equalsIgnoreCase("craftedSnow")) { return Material.craftedSnow; }
        else if(m.equalsIgnoreCase("dragonEgg")) { return Material.dragonEgg; }
        else if(m.equalsIgnoreCase("fire")) { return Material.fire; }
        else if(m.equalsIgnoreCase("glass")) { return Material.glass; }
        else if(m.equalsIgnoreCase("grass")) { return Material.grass; }
        else if(m.equalsIgnoreCase("ground")) { return Material.ground; }
        else if(m.equalsIgnoreCase("ice")) { return Material.ice; }
        else if(m.equalsIgnoreCase("iron")) { return Material.iron; }
        else if(m.equalsIgnoreCase("lava")) { return Material.lava; }
        else if(m.equalsIgnoreCase("leaves")) { return Material.leaves; }
        else if(m.equalsIgnoreCase("piston")) { return Material.piston; }
        else if(m.equalsIgnoreCase("plants")) { return Material.plants; }
        else if(m.equalsIgnoreCase("portal")) { return Material.portal; }
        else if(m.equalsIgnoreCase("pumpkin")) { return Material.pumpkin; }
        else if(m.equalsIgnoreCase("redstoneLight")) { return Material.redstoneLight; }
        else if(m.equalsIgnoreCase("rock")) { return Material.rock; }
        else if(m.equalsIgnoreCase("sand")) { return Material.sand; }
        else if(m.equalsIgnoreCase("snow")) { return Material.snow; }
        else if(m.equalsIgnoreCase("sponge")) { return Material.sponge; }
        else if(m.equalsIgnoreCase("tnt")) { return Material.tnt; }
        else if(m.equalsIgnoreCase("vine")) { return Material.vine; }
        else if(m.equalsIgnoreCase("water")) { return Material.water; }
        else if(m.equalsIgnoreCase("web")) { return Material.web; }
        else if(m.equalsIgnoreCase("wood")) { return Material.wood; }
        else { return null; }
    }
    
    private static CreativeTabs getCreativeTab(String tabName) {
        if(tabName.equalsIgnoreCase("tabAllSearch")) { return CreativeTabs.tabAllSearch; }
                else if(tabName.equalsIgnoreCase("tabBlock")) { return CreativeTabs.tabBlock; }
                else if(tabName.equalsIgnoreCase("tabBrewing")) { return CreativeTabs.tabBrewing; }
                else if(tabName.equalsIgnoreCase("tabCombat")) { return CreativeTabs.tabCombat; }
                else if(tabName.equalsIgnoreCase("tabDecorations")) { return CreativeTabs.tabDecorations; }
                else if(tabName.equalsIgnoreCase("tabFood")) { return CreativeTabs.tabFood; }
                else if(tabName.equalsIgnoreCase("tabInventory")) { return CreativeTabs.tabInventory; }
                else if(tabName.equalsIgnoreCase("tabMaterials")) { return CreativeTabs.tabMaterials; }
                else if(tabName.equalsIgnoreCase("tabMisc")) { return CreativeTabs.tabMisc; }
                else if(tabName.equalsIgnoreCase("tabRedstone")) { return CreativeTabs.tabRedstone; }
                else if(tabName.equalsIgnoreCase("tabTools")) { return CreativeTabs.tabTools; }
                else if(tabName.equalsIgnoreCase("tabTransport")) { return CreativeTabs.tabTransport; }
                else { return null; }
    }
    
    private static StepSound getStepSound(String soundName) {
        if(soundName.equalsIgnoreCase("soundPowderFootstep")) { return Block.soundPowderFootstep; }
        else if (soundName.equalsIgnoreCase("soundWoodFootstep")) { return Block.soundWoodFootstep; }
        else if (soundName.equalsIgnoreCase("soundGravelFootstep")) { return Block.soundGravelFootstep; }
        else if (soundName.equalsIgnoreCase("soundGrassFootstep")) { return Block.soundGrassFootstep; }
        else if (soundName.equalsIgnoreCase("soundStoneFootstep")) { return Block.soundStoneFootstep; }
        else if (soundName.equalsIgnoreCase("soundMetalFootstep")) { return Block.soundMetalFootstep; }
        else if (soundName.equalsIgnoreCase("soundGlassFootstep")) { return Block.soundGlassFootstep; }
        else if (soundName.equalsIgnoreCase("soundClothFootstep")) { return Block.soundClothFootstep; }
        else if (soundName.equalsIgnoreCase("soundSandFootstep")) { return Block.soundSandFootstep; }
        else if (soundName.equalsIgnoreCase("soundSnowFootstep")) { return Block.soundSnowFootstep; }
        else if (soundName.equalsIgnoreCase("soundLadderFootstep")) { return Block.soundLadderFootstep; }
        else if (soundName.equalsIgnoreCase("soundAnvilFootstep")) { return Block.soundAnvilFootstep; }
        else { return null; }
    }
    
}