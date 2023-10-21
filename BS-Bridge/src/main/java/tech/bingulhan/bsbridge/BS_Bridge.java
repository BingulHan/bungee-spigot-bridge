package tech.bingulhan.bsbridge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.util.Objects;


//Spigot plugin
public final class BS_Bridge extends JavaPlugin implements Listener, PluginMessageListener {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getMessenger().registerOutgoingPluginChannel(this, BSPluginConstants.CHANNEL);
        getServer().getMessenger().registerIncomingPluginChannel(this, BSPluginConstants.CHANNEL, this);

        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        String type = event.getEntity().getType().toString();

        String playerName = "";
        boolean isPlayerKiller = Objects.isNull(event.getEntity().getKiller());
        if (!isPlayerKiller) {
            playerName = event.getEntity().getKiller().getName();
        }

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(outputStream);
            dos.writeByte(0);

            dos.writeUTF(type);
            dos.writeBoolean(isPlayerKiller);
            if (!isPlayerKiller) {
                dos.writeUTF(playerName);

                event.getEntity().getKiller().sendPluginMessage(this, BSPluginConstants.CHANNEL, outputStream.toByteArray());

            }
            dos.close();
            outputStream.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data) {
        if (!channel.equals(BSPluginConstants.CHANNEL)) {
            return;
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        try {

            String victimPlayer = dataInputStream.readUTF();
            OfflinePlayer p = Bukkit.getOfflinePlayer(victimPlayer);
            if (p!=null&&p.isOnline()) {
                p.getPlayer().damage(1000);
                p.getPlayer().sendMessage(ChatColor.RED +"Bungecord üzerinden gelen emir ile sen öldün!");
            }

            dataInputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
