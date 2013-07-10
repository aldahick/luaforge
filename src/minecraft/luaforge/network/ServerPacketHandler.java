package luaforge.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import luaforge.Luaforge;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler implements IPacketHandler {
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player _player) {
		if (packet.channel.equals("luaforge") && _player != null && _player instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)_player;
			ByteArrayDataInput badi = ByteStreams.newDataInput(packet.data);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			int type = badi.readInt();
			try {
				if (type==Luaforge.NET_TYPE_IDENTIFYREQUEST) {
					dos.writeInt(Luaforge.NET_TYPE_MODLISTREQUESTSTART);
					dos.writeInt(Luaforge.containers.size());
					for (String modid : Luaforge.containers.keySet()) {
						dos.writeChars(modid);
					}
					dos.writeInt(Luaforge.NET_TYPE_MODLISTREQUESTCLOSE);
					sendPacket(baos, _player);
				} else if (type==Luaforge.NET_TYPE_MODIDENTIFY) {
					boolean bool = badi.readBoolean();
					String modid = badi.readLine();
					String version = badi.readLine();
					if (!bool) {
						player.playerNetServerHandler.kickPlayerFromServer("You do not have the required Luaforge mod "+modid+", version "+version);
					}
				} else if (type==Luaforge.NET_TYPE_MODLISTEND) {
					dos.writeInt(Luaforge.NET_TYPE_MODLISTENDCONFIRM);
					sendPacket(baos, _player);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void sendPacket(ByteArrayOutputStream baos, Player player) {
		Packet250CustomPayload packet = new Packet250CustomPayload();
		byte[] bytes = baos.toByteArray();
		packet.channel = "luaforge";
		packet.data = bytes;
		packet.length = packet.data.length;
		PacketDispatcher.sendPacketToPlayer(packet, player);
	}

}
