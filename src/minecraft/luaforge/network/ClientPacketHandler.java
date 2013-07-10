package luaforge.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import luaforge.Luaforge;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler {
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("luaforge")) {
			ByteArrayDataInput badi = ByteStreams.newDataInput(packet.data);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			int type = badi.readInt();
			try {
				if (type==Luaforge.NET_TYPE_MODLISTREQUESTSTART) {
					int size = badi.readInt();
					int i=0;
					List<String> serverMods = new ArrayList<String>();
					while (i<size) {
						serverMods.add(badi.readLine());
					}
					int end = badi.readInt();
					if (serverMods.isEmpty()) {
						dos.writeInt(Luaforge.NET_TYPE_MODLISTEND);
						sendPacket(baos);
						return;
					}
					for (String modid : Luaforge.containers.keySet()) {
						dos.writeInt(Luaforge.NET_TYPE_MODIDENTIFY);
						dos.writeBoolean(serverMods.contains(modid));
						dos.writeChars(modid);
						dos.writeChars(Luaforge.containers.get(modid).getVersion());
						sendPacket(baos);
						baos = new ByteArrayOutputStream();
						dos = new DataOutputStream(baos);
					}
					dos.writeInt(Luaforge.NET_TYPE_MODLISTEND);
					sendPacket(baos);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void sendPacket(ByteArrayOutputStream baos) {
		Packet250CustomPayload packet = new Packet250CustomPayload();
		byte[] bytes = baos.toByteArray();
		packet.channel = "luaforge";
		packet.data = bytes;
		packet.length = packet.data.length;
		PacketDispatcher.sendPacketToServer(packet);
	}
}
