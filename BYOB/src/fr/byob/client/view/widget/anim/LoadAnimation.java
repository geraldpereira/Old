package fr.byob.client.view.widget.anim;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

public class LoadAnimation extends Animation{
    /**
     * The x-coordinate of the center of the circle.
     */
    private int centerX =100;

    /**
     * The y-coordinate of the center of the circle.
     */
    private int centerY=100 ;
    public LoadAnimation(Widget anim,AbsolutePanel abs,int posx,int posy) {
       this.animate = anim;
       this.absolutePanel = abs;
    }
    private Widget animate = null;
    
    private AbsolutePanel absolutePanel = null;

    /**
     * The radius of the circle.
     */
    private int radius = 20;
    
    @Override
    protected void onUpdate(double progress) {
        double radian = 2 * Math.PI * progress;
        updatePosition(animate, radian, 0);
      }
      /**
       * Update the position of the widget, adding a rotational offset.
       * 
       * @param w the widget to move
       * @param radian the progress in radian
       * @param offset the offset in radian
       */
      private void updatePosition(Widget w, double radian, double offset) {
        radian += offset;
        double x = radius * Math.cos(radian) + centerX;
        double y = radius * Math.sin(radian) + centerY;
        absolutePanel.setWidgetPosition(w, (int) x, (int) y);
      }
      
     public void start(){
         this.onComplete();
         updatePosition(animate, centerX, centerY);
     }
}
