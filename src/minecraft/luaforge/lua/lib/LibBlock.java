package luaforge.lua.lib;

import java.util.HashMap;

import luaforge.api.LuaLib;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import tiin57.lib.luaj.vm2.LuaTable;
import tiin57.lib.luaj.vm2.LuaValue;
import tiin57.lib.luaj.vm2.lib.OneArgFunction;
import tiin57.lib.luaj.vm2.lib.TwoArgFunction;
import tiin57.lib.luaj.vm2.lib.jse.CoerceJavaToLua;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@LuaLib(name="block")
public class LibBlock extends LuaTable {
	final HashMap<Integer, IconRegister> registers = new HashMap<Integer, IconRegister>();
	public LibBlock() {
		set("new", new TwoArgFunction() {
			public LuaValue call(LuaValue id, LuaValue material) {
				if (!(id.isint() && material.isuserdata())) { return LuaValue.NIL; }
				if (!(material.checkuserdata() instanceof Material)) { return LuaValue.NIL; }
				Block b = new Block(id.checkint(), (Material)material.checkuserdata()) {
					@SideOnly(Side.CLIENT)
					public void registerIcons(IconRegister ir) {
						registers.put(this.blockID, ir);
					}
				};
				LuaValue luab = CoerceJavaToLua.coerce(b);
				return luab;
			}
		});
		set("setCreativeTab", new TwoArgFunction() {
			public LuaValue call(LuaValue block, LuaValue tab) {
				if (block.isuserdata() && tab.isuserdata()) {
					Object b = block.checkuserdata();
					Object t = tab.checkuserdata();
					if (b instanceof Block && t instanceof CreativeTabs) {
						Block blo = (Block)b;
						blo.setCreativeTab((CreativeTabs)t);
						return CoerceJavaToLua.coerce(blo);
					}
				}
				return LuaValue.NIL;
			}
		});
		set("setUnlocalizedName", new TwoArgFunction() {
			public LuaValue call(LuaValue block, LuaValue name) {
				if (block.isuserdata() && name.isstring()) {
					Object b = block.checkuserdata();
					String n = name.checkjstring();
					if (b instanceof Block) {
						Block blo = (Block)b;
						blo.setUnlocalizedName(n);
						return CoerceJavaToLua.coerce(blo);
					}
				}
				return LuaValue.NIL;
			}
		});
		set("setIngameName", new TwoArgFunction() {
			public LuaValue call(LuaValue block, LuaValue name) {
				if (block.isuserdata() && name.isstring()) {
					Object b = block.checkuserdata();
					String n = name.checkjstring();
					if (b instanceof Block) {
						Block blo = (Block)b;
						LanguageRegistry.addName(blo, n);
					}
				}
				return LuaValue.NIL;
			}
		});
		set("registerIcon", new TwoArgFunction() {
			public LuaValue call(LuaValue block, LuaValue icon) {
				if (block.isuserdata() && icon.isstring()) {
					Object b = block.checkuserdata();
					String ic = icon.checkjstring();
					if (b instanceof Block) {
						Block blo = (Block)b;
						Object i = registers.get(blo.blockID);
						if ((i != null) && (i instanceof IconRegister)) {
							IconRegister ir = (IconRegister)i;
							ir.registerIcon(ic);
						}
					}
				}
				return LuaValue.NIL;
			}
		});
	}
}
