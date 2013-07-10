package luaforge.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import luaforge.Luaforge;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;

public class ConnectionHandler implements IConnectionHandler {
	// SERVER SIDE
	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		
	}
	// SERVER SIDE
	// Return non-empty string to stop connection
	@Override
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
		return "";
	}
	// Remote connection CLIENT SIDE
	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
		Packet250CustomPayload packet = new Packet250CustomPayload();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(Luaforge.NET_TYPE_IDENTIFYREQUEST);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		packet.channel="luaforge";
		packet.data = baos.toByteArray();
		packet.length = packet.data.length;
		netClientHandler.handleVanilla250Packet(packet);
	}
	// Local connection CLIENT SIDE
	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
		
	}
	// ALL SIDES
	@Override
	public void connectionClosed(INetworkManager manager) {
		// Totally useless.
	}
	// CLIENTSIDE
	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
		
	}

}
