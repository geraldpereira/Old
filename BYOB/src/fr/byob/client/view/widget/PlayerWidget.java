package fr.byob.client.view.widget;

import com.bramosystems.gwt.player.client.AbstractMediaPlayer;
import com.bramosystems.gwt.player.client.LoadException;
import com.bramosystems.gwt.player.client.PluginNotFoundException;
import com.bramosystems.gwt.player.client.PluginVersionException;
import com.bramosystems.gwt.player.client.ui.FlashMP3Player;
import com.bramosystems.gwt.player.client.ui.QuickTimePlayer;
import com.bramosystems.gwt.player.client.ui.WinMediaPlayer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

public class PlayerWidget extends Composite{
    private AbstractMediaPlayer player;
    public PlayerWidget(String url) {
        SimplePanel panel = new SimplePanel();   // create panel to hold the player

        player = null;
        try {
            // create the player, specifing URL of media
            player = new WinMediaPlayer(url,true,"450px", "550px");
            player.showLogger(true);
            panel.setWidget(player); // add player to panel.
        } catch(LoadException e) {
            try{
                player = new QuickTimePlayer(url,true,"450px", "550px");
                panel.setWidget(player); // add player to panel.
                // catch loading exception and alert user
                Window.alert("An error occured while loading");
            } catch(LoadException ep) {
                try{
                    player = new FlashMP3Player(url,true,"450px", "550px");
                    panel.setWidget(player); // add player to panel.
                    // catch loading exception and alert user
                    Window.alert("An error occured while loading");
                } catch(LoadException exp) {
                    // catch loading exception and alert user
                    Window.alert("An error occured while loading");
                } catch(PluginVersionException exp) {
                    // required plugin version is not available, alert user possibly providing a link
                    // to the plugin download page.
                    panel.setWidget(new HTML(".. some nice message telling the user to download plugin first .."));
                } catch(PluginNotFoundException exp) {
                    // catch PluginNotFoundException and tell user to download plugin, possibly providing
                    // a link to the plugin download page.
                    panel.setWidget(new HTML(".. another nice message telling the user to download plugin.."));
                }
            } catch(PluginVersionException ep) {
                // required plugin version is not available, alert user possibly providing a link
                // to the plugin download page.
                panel.setWidget(new HTML(".. some nice message telling the user to download plugin first .."));
            } catch(PluginNotFoundException ep) {
                // catch PluginNotFoundException and tell user to download plugin, possibly providing
                // a link to the plugin download page.
                panel.setWidget(new HTML(".. another nice message telling the user to download plugin.."));
            }

        } catch(PluginVersionException e) {
            // required plugin version is not available, alert user possibly providing a link
            // to the plugin download page.
            panel.setWidget(new HTML(".. some nice message telling the user to download plugin first .."));
        } catch(PluginNotFoundException e) {
            // catch PluginNotFoundException and tell user to download plugin, possibly providing
            // a link to the plugin download page.
            panel.setWidget(new HTML(".. another nice message telling the user to download plugin.."));
        }
        initWidget(panel);
    }

    public void setVideo(String url){
        try {
            player.loadMedia(url);
        } catch (LoadException e) {
            Window.alert("An error occured while loading");
        }
    }
}
