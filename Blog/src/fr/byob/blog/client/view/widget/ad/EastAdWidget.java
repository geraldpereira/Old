package fr.byob.blog.client.view.widget.ad;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.view.widget.anim.EastShowPanel;
import fr.byob.client.util.Css;
import fr.byob.client.view.widget.ad.AdSenseWidget;

public class EastAdWidget extends Composite implements IAdWidget {

    private final static String[] messages = new String[] { "<br/>C<br/>O<br/>N<br/>S<br/>O<br/>M<br/>M<br/>E", "<br/><br/>P<br/>R<br/>O<br/>D<br/>U<br/>I<br/>S",
            "<br/><br/><br/>O<br/>B<br/>E<br/>I<br/>S", "<br/><br/><br/>D<br/>O<br/>R<br/>S", "<br/><br/>P<br/>R<br/>O<br/>C<br/>R<br/>E<br/>E", "<br/><br/>O<br/>U<br/>B<br/>L<br/>I<br/>E",
            "<br/><br/>R<br/>E<br/>N<br/>O<br/>N<br/>C<br/>E", "<br/><br/><br/>V<br/>O<br/>T<br/>E", "<br/><br/><br/>J<br/>U<br/>G<br/>E", "<br/><br/>D<br/>E<br/>N<br/>O<br/>N<br/>C<br/>E",
            "<br/><br/>A<br/>C<br/>H<br/>E<br/>T<br/>E", };

    private final static int width = 120;
    private final static int height = 600;

    private EastShowPanel panel;
    private final String adId;

    public EastAdWidget(String adId) {
        this.adId = adId;
        panel = new EastShowPanel(new AdSenseWidget(adId), subliminalFactory(), width);
        initWidget(panel);
        scheduleRun();
    }

    public void refresh() {
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

    protected Widget subliminalFactory() {
        double d = Math.random();
        int index = (int) (d * messages.length);
        HTML subliminal = new HTML(messages[index]);
        subliminal.setStyleName(Css.INSTANCE.subliminalEast());
        subliminal.setSize(width - 10 + "px", height - 10 + "px");
        return subliminal;
    }
}
