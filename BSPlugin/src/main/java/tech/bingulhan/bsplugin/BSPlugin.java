package tech.bingulhan.bsplugin;

import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;


//Bungeecord Plugin
public final class BSPlugin extends Plugin implements Listener {

    public static BSPlugin getInstance() {
        return instance;
    }

    private static BSPlugin instance;
    @Override
    public void onEnable() {

        instance = this;

        getProxy().registerChannel(BSPluginConstants.CHANNEL);

        getProxy().getPluginManager().registerListener(this, this);

        getProxy().getPluginManager().registerCommand(this, new CMDKillP());
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {

        if (!event.getTag().equals(BSPluginConstants.CHANNEL)) {
            return;
        }

        byte[] data = event.getData();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        try {
            byte id = dataInputStream.readByte();

            for (IBridgeDataReader reader : IBridgeDataReader.readers) {
                if (reader.id() == id) {
                    reader.read(dataInputStream);
                }
            }

            dataInputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
