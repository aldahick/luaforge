package scriptforge.script;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import scriptforge.Scriptforge;
import scriptforge.api.AnnotationFaker;
import scriptforge.api.IScriptEnvironment;
import scriptforge.utils.InfoParser;
import tiin57.lib.luaj.vm2.Globals;
import tiin57.lib.luaj.vm2.LuaError;
import tiin57.lib.luaj.vm2.LuaValue;
import tiin57.lib.luaj.vm2.compiler.LuaC;
import tiin57.lib.luaj.vm2.lib.Bit32Lib;
import tiin57.lib.luaj.vm2.lib.CoroutineLib;
import tiin57.lib.luaj.vm2.lib.DebugLib;
import tiin57.lib.luaj.vm2.lib.PackageLib;
import tiin57.lib.luaj.vm2.lib.StringLib;
import tiin57.lib.luaj.vm2.lib.TableLib;
import tiin57.lib.luaj.vm2.lib.jse.JseBaseLib;
import tiin57.lib.luaj.vm2.lib.jse.JseIoLib;
import tiin57.lib.luaj.vm2.lib.jse.JseMathLib;
import tiin57.lib.luaj.vm2.lib.jse.JseOsLib;
import tiin57.lib.luaj.vm2.lib.jse.LuajavaLib;
import cpw.mods.fml.common.network.NetworkMod;

public class LuaEnvironment implements IScriptEnvironment {
	
	private LuaValue chunk;
	private File luadir;
	public Globals _G = globals();
	
	/* network.info */
	public NetworkMod networkmod;
	/* * * * */
	
	/* luamod.info */
	public String modid = "";
	public String modname = "";
	public String version = "";
	public String[] authors = new String[0];
	public String credits = "";
	public String description = "";
	public String url = "";
	public String updateurl = "";
	public String logofile = "";
	public boolean isNetworkMod = true;
	public String loadstate = Scriptforge.STATE_PREINIT;
	/* * * * */
	
	public LuaEnvironment(File luadir) {
		this.luadir = luadir;
		File luamodinfo = new File(luadir.getAbsolutePath()+"/luamod.info");
		try {
			HashMap<String, String> p = InfoParser.parseFile(luamodinfo);
			modid = p.get("modid");
			modname = p.get("modname");
			version = p.get("version");
			String a = p.get("authors");
			authors = a.split(",");
			credits = p.get("credits");
			description = p.get("description");
			url = p.get("url");
			updateurl = p.get("updateurl");
			logofile = p.get("logofile");
			loadstate = p.get("loadstate");
			isNetworkMod = p.get("networkmod").equals("true");
			if (isNetworkMod) {
				boolean client = p.get("clientSideRequired").equals("true");
				boolean server = p.get("serverSideRequired").equals("true");
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("clientSideRequired", client);
				map.put("serverSideRequired", server);
				networkmod = AnnotationFaker.fake(NetworkMod.class, map);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}
		_G.get("luaforge").set("modid", modid);
	}
	
	public void callMain() {
		String mainPath = luadir.getPath()+"/main.lua";
		try {
			chunk = _G.loadFile(mainPath);
			chunk.call(LuaValue.valueOf(mainPath));
		} catch (LuaError ex) {
			throw new RuntimeException("Luaforge error:\nIn mod "+modid+"\nError: "+ex.getMessage());
		}
	}
	
	private static Globals globals() {
		Globals g = new Globals();
		
		g.load(new JseBaseLib());
		g.load(new PackageLib());
        g.load(new Bit32Lib());
        g.load(new TableLib());
        g.load(new StringLib());
        g.load(new CoroutineLib());
        g.load(new JseMathLib());
        g.load(new JseIoLib());
        g.load(new JseOsLib());
        g.load(new DebugLib());
        g.load(new LuajavaLib());
        
        LuaC.install();
        g.compiler = LuaC.instance;
		LuaValue luaforge = g.get("require").call("luaforge.lua.lib.luaforge");
		g.set("luaforge", luaforge);
		return g;
	}

	@Override
	public String modid() {
		return modid;
	}

	@Override
	public String modname() {
		return modname;
	}

	@Override
	public String version() {
		return version;
	}

	@Override
	public String[] authors() {
		return authors;
	}

	@Override
	public String credits() {
		return credits;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public String url() {
		return url;
	}

	@Override
	public String updateurl() {
		return updateurl;
	}

	@Override
	public String logofile() {
		return logofile;
	}

	@Override
	public NetworkMod networkmod() {
		return networkmod;
	}

	@Override
	public boolean isNetworkMod() {
		return isNetworkMod;
	}

	@Override
	public String loadstate() {
		return loadstate;
	}
}
