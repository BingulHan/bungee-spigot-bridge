package tech.bingulhan.bsplugin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class CMDKillP extends Command {

    public CMDKillP() {
        super("killp");
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length>0) {
            String pn = strings[0];
            boolean isPlayerOnline = BSPlugin.getInstance().getProxy().getPlayers().stream().anyMatch(p->p.getName().equals(pn));
            if (isPlayerOnline) {
                ProxiedPlayer player =  BSPlugin.getInstance().getProxy().getPlayers().stream().filter(p->p.getName().equals(pn)).findAny().get();

                try {

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    dataOutputStream.writeUTF(pn);

                    player.getServer().sendData(BSPluginConstants.CHANNEL, byteArrayOutputStream.toByteArray());
                    byteArrayOutputStream.close();
                    dataOutputStream.close();

                    commandSender.sendMessage("veri gitti!");

                }catch (Exception exception){
                    exception.printStackTrace();
                }

            }


        }
    }
}
