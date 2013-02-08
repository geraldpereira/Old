package fr.byob.blog.client.view.widget.ad;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.view.widget.anim.NorthShowPanel;
import fr.byob.client.util.Css;
import fr.byob.client.view.widget.ad.AdSenseWidget;

public class NorthAdWidget extends Composite implements IAdWidget {
    
    private final static String [] messages = new String []{
        "REGARDE LA T.V.",
        "NE REFLECHIS PAS",
        "CONFORME TOI",
        "SOUMETS TOI",
        "ACHETE",
        "NE REMETS PAS L'AUTORITE EN CAUSE",
        "TRAVAILLE 8 HEURES",
        "DORS 8 HEURES",
        "JOUE 8 HEURES",
        "CONSOMME",
        "PRODUIS",
        "OBEIS",
        "DORS",
        "PROCREE",
        "OUBLIE",
        "RENONCE",
        "VOTE",
        "JUGE",
        "DENONCE"
    };
    private final static int width = 468;
    private final static int height = 15;
    
    private final NorthShowPanel panel;
    private final String adId;
    public NorthAdWidget(String adId){
        this.adId = adId;
        panel = new NorthShowPanel(new AdSenseWidget(adId),subliminalFactory(),height);
        initWidget(panel);
        scheduleRun();
    }

    public void refresh (){
        panel.setToShow(new AdSenseWidget(adId));
        panel.setToHide(subliminalFactory());
        scheduleRun();
    }
    
    private void scheduleRun(){
        Timer t = new Timer() {
            public void run() {
                panel.show(1200);
            }
        };
        t.schedule(500);
    }
    
    protected Widget subliminalFactory(){
        double d = Math.random();
        int index = (int) (d * messages.length);
        HTML subliminal = new HTML(messages[index]);
        subliminal.setStyleName(Css.INSTANCE.subliminalNorth());
        subliminal.setSize(width+"px", height-4+"px");
        return subliminal;                
    }
    
    
}
