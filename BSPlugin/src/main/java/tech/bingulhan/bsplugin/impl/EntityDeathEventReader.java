package tech.bingulhan.bsplugin.impl;

import net.md_5.bungee.api.ProxyServer;
import tech.bingulhan.bsplugin.IBridgeDataReader;

import java.io.DataInputStream;
import java.io.IOException;

public class EntityDeathEventReader implements IBridgeDataReader {


    @Override
    public void read(DataInputStream inputStream) {
        try {
            String type = inputStream.readUTF();
            boolean isKillerPlayer = inputStream.readBoolean();

            String playerName = "";
            if (!isKillerPlayer) {
                playerName = inputStream.readUTF();
                ProxyServer.getInstance().getLogger().info("EntityDeath Event => "+type+" PlayerName "+playerName);
            } else{
                ProxyServer.getInstance().getLogger().info("EntityDeath Event => "+type);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public byte id() {
        return 0;
    }
}
