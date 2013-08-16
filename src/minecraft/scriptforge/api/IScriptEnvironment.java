package scriptforge.api;

import cpw.mods.fml.common.network.NetworkMod;

public interface IScriptEnvironment {
	public String modid();
	public String modname();
	public String version();
	public String[] authors();
	public String credits();
	public String description();
	public String url();
	public String updateurl();
	public String logofile();
	public NetworkMod networkmod();
	public boolean isNetworkMod();
	public String loadstate();
	public void callMain();
}
