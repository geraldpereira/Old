package fr.byob.client.view.widget.anim;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AnimView extends Composite{
    private AbsolutePanel absolutePanel = null;
    private Timer timer = null;

    private static AnimView anim = new AnimView();
    
    private AnimView() {
        absolutePanel = new AbsolutePanel();
        absolutePanel.setSize("250px", "250px");

        // Add a widget that we will animate
        Widget animate = AnimImages.INSTANCE.load().createImage();
        Widget animate45 = AnimImages.INSTANCE.load45().createImage();
        Widget animate90 = AnimImages.INSTANCE.load90().createImage();
        Widget animate135 = AnimImages.INSTANCE.load135().createImage();
        Widget animate180 = AnimImages.INSTANCE.load180().createImage();
        Widget animate225 = AnimImages.INSTANCE.load225().createImage();
        Widget animate270 = AnimImages.INSTANCE.load270().createImage();
        Widget animate315 = AnimImages.INSTANCE.load315().createImage();
        Label loading = new Label(AnimStrings.INSTANCE.loading());
        loading.setStyleName("loading");
        absolutePanel.add(loading);
        if(loading.getText().equals("Loading")){
            absolutePanel.setWidgetPosition(loading, 80, 60);
        }else if(loading.getText().equals("Chargement")){
            absolutePanel.setWidgetPosition(loading, 70, 60);
        }
        absolutePanel.add(animate);
        absolutePanel.add(animate315);
        absolutePanel.add(animate270);
        absolutePanel.add(animate225);
        absolutePanel.add(animate180);
        absolutePanel.add(animate135);
        absolutePanel.add(animate90);
        absolutePanel.add(animate45);

        // Add the components to a panel and return it
        HorizontalPanel mainLayout = new HorizontalPanel();
        mainLayout.setSpacing(10);

        mainLayout.add(absolutePanel);

        // Create the custom animation
        final LoadAnimation animation = new LoadAnimation(animate,absolutePanel,120,100);
        final LoadAnimation animation45 = new LoadAnimation(animate45,absolutePanel,135,105);
        final LoadAnimation animation90 = new LoadAnimation(animate90,absolutePanel,140,120);
        final LoadAnimation animation135 = new LoadAnimation(animate135,absolutePanel,135,135);
        final LoadAnimation animation180 = new LoadAnimation(animate180,absolutePanel,120,140);
        final LoadAnimation animation225 = new LoadAnimation(animate225,absolutePanel,105,135);
        final LoadAnimation animation270 = new LoadAnimation(animate270,absolutePanel,100,120);
        final LoadAnimation animation315 = new LoadAnimation(animate315,absolutePanel,105,105);
        timer= new Timer(){

            @Override
            public void run() {
                animation45.run(350);
                animation90.run(500);
                animation135.run(750);
                animation180.run(1000);
                animation225.run(1250);
                animation270.run(1500);
                animation315.run(1750);
                animation.run(2000);
            }

        };
        timer.scheduleRepeating(2000);
        mainLayout.setCellHorizontalAlignment(absolutePanel, HasHorizontalAlignment.ALIGN_CENTER);
        mainLayout.setCellVerticalAlignment(absolutePanel, HasVerticalAlignment.ALIGN_MIDDLE);
        mainLayout.setSize("100%", "100%");
        initWidget(mainLayout);
    }
    
    public static AnimView getInstance(){
        return anim;
    }

    public void startTimer(){
        timer.scheduleRepeating(2000);
        timer.run();
    }
    public void stopTimer(){
        timer.cancel();
    }
}
