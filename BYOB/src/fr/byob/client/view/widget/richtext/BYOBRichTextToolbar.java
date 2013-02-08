package fr.byob.client.view.widget.richtext;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.byob.client.util.Css;
import fr.byob.client.view.widget.ListWidget;

/**
 * Une toolbar à utiliser avec {@link BYOBRichTextArea}. Propose une simple UI pour tous les formats de texte enrichis, affichée dynamiquement pour
 * les fonctionnalités disponibles uniquement.
 */
public class BYOBRichTextToolbar extends Composite {

//    private static final RichTextArea.FontSize[] fontSizesConstants = new RichTextArea.FontSize[] { RichTextArea.FontSize.XX_SMALL,
//        RichTextArea.FontSize.X_SMALL, RichTextArea.FontSize.SMALL, RichTextArea.FontSize.MEDIUM, RichTextArea.FontSize.LARGE,
//        RichTextArea.FontSize.X_LARGE, RichTextArea.FontSize.XX_LARGE };
    
    private Images images = (Images) GWT.create(Images.class);
    private Strings strings = (Strings) GWT.create(Strings.class);

    private RichTextArea.BasicFormatter basic;
    private RichTextArea.ExtendedFormatter extended;
    private BYOBRichTextArea richText;

    private VerticalPanel outer = new VerticalPanel();
    private HorizontalPanel topPanel = new HorizontalPanel();
    private HorizontalPanel bottomPanel = new HorizontalPanel();
    private ToggleButton bold;
    private ToggleButton italic;
    private ToggleButton underline;
    private ToggleButton subscript;
    private ToggleButton superscript;
    private ToggleButton strikethrough;

    private ListBox backColors;
    private ListBox foreColors;
//    private ListBox fonts;
//    private ListBox fontSizes;
    
    private boolean isFullHTML = true;

    /**
     * Creates a new toolbar that drives the given rich text area.
     * 
     * @param richText
     *            the rich text area to be controlled
     */
    public BYOBRichTextToolbar(BYOBRichTextArea richText) {
        Css css = Css.INSTANCE;
        this.richText = richText;
        this.basic = richText.getBasicFormatter();
        this.extended = richText.getExtendedFormatter();
        outer.add(topPanel);
        outer.add(bottomPanel);
        topPanel.setWidth("100%");
        bottomPanel.setWidth("100%");

        initWidget(outer);
        setStyleName(css.BYOBRichTextToolbar());
        richText.addStyleName(css.hasBYOBRichTextToolbar());
        if (basic != null) {
            topPanel.add(bold = createToggleButton(images.bold(), strings.bold(), new BoldListener()));
            topPanel.add(italic = createToggleButton(images.italic(), strings.italic(), new ItalicListener()));
            topPanel.add(underline = createToggleButton(images.underline(), strings.underline(), new UnderlineListener()));
            topPanel.add(subscript = createToggleButton(images.subscript(), strings.subscript(), new SubscriptListener()));
            topPanel.add(superscript = createToggleButton(images.superscript(), strings.superscript(), new SuperscriptListener()));
            topPanel.add(createPushButton(images.justifyLeft(), strings.justifyLeft(), new JustifLeftListener()));
            topPanel.add(createPushButton(images.justifyCenter(), strings.justifyCenter(), new JustifCenterListener()));
            topPanel.add(createPushButton(images.justifyRight(), strings.justifyRight(), new JustifRightListener()));
        }

        if (extended != null) {
            topPanel.add(strikethrough = createToggleButton(images.strikeThrough(), strings.strikeThrough(), new StrikethroughListener()));
            topPanel.add(createPushButton(images.indent(), strings.indent(), new RightIndentListener()));
            topPanel.add(createPushButton(images.outdent(), strings.outdent(), new LeftIndentListener()));
            topPanel.add(createPushButton(images.hr(), strings.hr(), new InsertHRuleListener()));
            topPanel.add(createPushButton(images.ol(), strings.ol(), new InsertOListListener()));
            topPanel.add(createPushButton(images.ul(), strings.ul(), new InsertUListListener()));
            topPanel.add(createPushButton(images.insertImage(), strings.insertImage(), new InserImageListener()));
            topPanel.add(createPushButton(images.createLink(), strings.createLink(), new CreateLinkListener()));
            topPanel.add(createPushButton(images.removeLink(), strings.removeLink(), new RemoveLinkListener()));
            topPanel.add(createPushButton(images.removeFormat(), strings.removeFormat(), new RemoveFormatListener()));
            topPanel.add(createPushButton(images.video(), strings.video(), new InsertVideoListener()));
            topPanel.add(createPushButton(images.breaken(), strings.breaken(), new InsertBreakListener()));
            topPanel.add(createPushButton(images.full(), strings.full(), new SeeFullListener()));
        }

        if (basic != null) {
            bottomPanel.add(backColors = createColorList(strings.background(), new BackColorListener()));
            bottomPanel.add(foreColors = createColorList(strings.foreground(), new ForeColorListener()));
            bottomPanel.setCellHorizontalAlignment(backColors, HasHorizontalAlignment.ALIGN_CENTER);
            bottomPanel.setCellHorizontalAlignment(foreColors, HasHorizontalAlignment.ALIGN_CENTER);
//            bottomPanel.add(fonts = createFontList());
//            bottomPanel.add(fontSizes = createFontSizes());
            
            // Listener pour mettre à jour la barre d'état
            richText.addKeyUpHandler(new RichKeyboardListener());
            richText.addClickHandler(new RichClickListener());
            richText.addClickHandler(new ClickHandler(){

                public void onClick(ClickEvent event) {
                    updateStatus();
                }
                
            });
        }
    }
    
    public void setEnabled(boolean isEnabled) {
        bold.setEnabled(isEnabled);
        italic.setEnabled(isEnabled);
        underline.setEnabled(isEnabled);
        subscript.setEnabled(isEnabled);
        superscript.setEnabled(isEnabled);
        strikethrough.setEnabled(isEnabled);
        backColors.setEnabled(isEnabled);
        foreColors.setEnabled(isEnabled);
//        fonts.setEnabled(isEnabled);
//        fontSizes.setEnabled(isEnabled);
    }
    
    //TODO pour évolution en color chooser
    private ListWidget createColorBgList(String caption, ChangeHandler listener) {
        
        ListWidget lb = new ListWidget();
//        lb.addChangeListener(listener);
//        lb.setVisibleItemCount(1);
        
        // Attention a bien mettre les couleurs dans le bon ordre
        lb.addItem(new Label(caption));
        lb.addItem(new Label(strings.white()), "white");
        lb.addItem(new Label(strings.black()), "black");
        HTML red = new HTML();
        red.setHTML("<font style=\"background-color=red\">"+strings.red()+"</font>");
        HTML arial = new HTML();
        arial.setHTML("<font face=Arial>Arial</font>");
        HTML color = new HTML();
        color.setHTML("<font color=green>Vert</font>");
        HTML size = new HTML();
        size.setHTML("<font size=2>Xpetit</font>");
        lb.addItem(red, "red");
        lb.addItem(new Label(strings.green()), "green");
        lb.addItem(new Label(strings.yellow()), "yellow");
        lb.addItem(new Label(strings.blue()), "blue");
        return lb;
    }
    private ListBox createColorList(String caption, ChangeHandler listener) {
        ListBox lb = new ListBox();
        lb.addChangeHandler(listener);
        lb.setVisibleItemCount(1);
        
        // Attention a bien mettre les couleurs dans le bon ordre
        lb.addItem(caption);
        lb.addItem(strings.white(), "white");
        lb.addItem(strings.black(), "black");
        lb.addItem(strings.red(), "red");
        lb.addItem(strings.green(), "green");
        lb.addItem(strings.yellow(), "yellow");
        lb.addItem(strings.blue(), "blue");
        return lb;
    }

//    private ListBox createFontList() {
//        ListBox lb = new ListBox();
//        lb.addChangeListener(new FontsListener());
//        lb.setVisibleItemCount(1);
//
//        lb.addItem(strings.font(), "");
//        
//        lb.addItem("Times New Roman", "Times New Roman");
//        lb.addItem("Arial","Arial");
//        lb.addItem("Courier New", "Courier New");
//        lb.addItem("Georgia", "Georgia");
//        lb.addItem("Verdana", "Verdana");
//        return lb;
//    }
//
//    private ListBox createFontSizes() {
//        ListBox lb = new ListBox();
//        lb.addChangeListener(new FontSizeListener());
//        lb.setVisibleItemCount(1);
//        lb.addItem(strings.size());
//        lb.addItem(strings.small());
//        lb.addItem(strings.normal());
//        lb.addItem(strings.medium());
//        lb.addItem(strings.large());
//        lb.addItem(strings.xlarge());
//        return lb;
//    }

    private PushButton createPushButton(AbstractImagePrototype img, String tip, ClickHandler listener) {
        PushButton pb = new PushButton(img.createImage());
        pb.setWidth("12px");
        pb.addClickHandler(listener);
        pb.setTitle(tip);
        return pb;
    }

    private ToggleButton createToggleButton(AbstractImagePrototype img, String tip, ClickHandler listener) {
        ToggleButton tb = new ToggleButton(img.createImage());
        tb.setWidth("12px");
        tb.addClickHandler(listener);
        tb.setTitle(tip);
        return tb;
    }

    /**
     * Met à jour la toolbar
     */
    private void updateStatus() {
        if (basic != null) {
            bold.setDown(basic.isBold());
            italic.setDown(basic.isItalic());
            underline.setDown(basic.isUnderlined());
            subscript.setDown(basic.isSubscript());
            superscript.setDown(basic.isSuperscript());
        }

        if (extended != null) {
            strikethrough.setDown(extended.isStrikethrough());
        }
    }
    
    private class BackColorListener implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            basic.setBackColor(backColors.getValue(backColors.getSelectedIndex()));
            backColors.setSelectedIndex(0);
        }
    }

    private class ForeColorListener implements ChangeHandler {
        public void onChange(ChangeEvent event) {
            basic.setForeColor(foreColors.getValue(foreColors.getSelectedIndex()));
            foreColors.setSelectedIndex(0);
        }
    }

//    private class FontsListener implements ChangeListener {
//        public void onChange(Widget sender) {
//            basic.setFontName(fonts.getValue(fonts.getSelectedIndex()));
//            fonts.setSelectedIndex(0);
//        }
//    }
//
//    private class FontSizeListener implements ChangeListener {
//        public void onChange(Widget sender) {
//            basic.setFontSize(fontSizesConstants[fontSizes.getSelectedIndex() - 1]);
//            fontSizes.setSelectedIndex(0);
//        }
//    }

    private class RichClickListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            // On utilise le RichTextArea's onKeyUp event pour mettre à jour le status de la toolbar.
            // Catch tous les cas ou l'utilisateur déplace le curseur via le clavier ou via les raccourcis navigateur.
            updateStatus();
        }
    }

    private class RichKeyboardListener implements KeyUpHandler {
        public void onKeyUp(KeyUpEvent event) {
         // On utilise le RichTextArea's onKeyUp event pour mettre à jour le status de la toolbar.
            // Catch tous les cas ou l'utilisateur déplace le curseur via le clavier ou via les raccourcis navigateur.
            updateStatus();
        }
    }

    private class RemoveFormatListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            extended.removeFormat();
        }
    }

    private class InsertUListListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            extended.insertUnorderedList();
        }
    }

    private class InsertOListListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            extended.insertOrderedList();
        }
    }

    private class InsertHRuleListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            extended.insertHorizontalRule();
        }
    }

    private class RemoveLinkListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            extended.removeLink();
        }
    }

    private class CreateLinkListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            String url = Window.prompt("Enter a link URL:", "http://");
            if (url != null) {
                extended.createLink(url);
            }
        }
    }

    private class InserImageListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            String url = Window.prompt("Enter an image URL:", "http://");
            if (url != null) {
                extended.insertImage(url);
            }
        }
    }
    private class InsertVideoListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            String url = Window.prompt("Enter an video URL:", "http://");
            if (url != null) {
                richText.addBalise("<video>"+url+"</video>");
            }
        }
    }
    private class InsertBreakListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            richText.addBalise(":break:");
        }
    }
    private class SeeFullListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            richText.setFullHTML(isFullHTML);
            if(isFullHTML){
                richText.setText(richText.getHTML());
                isFullHTML = false;
            }else{
                richText.setHTML(richText.getText());
                isFullHTML = true;
            }
        }
    }
    private class JustifRightListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            basic.setJustification(RichTextArea.Justification.RIGHT);
        }
    }

    private class JustifCenterListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            basic.setJustification(RichTextArea.Justification.CENTER);
        }
    }

    private class JustifLeftListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            basic.setJustification(RichTextArea.Justification.LEFT);
        }
    }

    private class LeftIndentListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            extended.leftIndent();
        }
    }

    private class RightIndentListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            extended.rightIndent();
        }
    }

    private class StrikethroughListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            extended.toggleStrikethrough();
        }
    }

    private class SuperscriptListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            basic.toggleSuperscript();
        }
    }

    private class SubscriptListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            basic.toggleSubscript();
        }
    }

    private class UnderlineListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            basic.toggleUnderline();
        }
    }

    private class ItalicListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            basic.toggleItalic();
        }
    }

    private class BoldListener implements ClickHandler {
        public void onClick(ClickEvent event) {
            basic.toggleBold();
        }
    }
    
    /**
     * Cet {@link ImageBundle} est utilisé pour les icones de boutons
     */
    public interface Images extends ImageBundle {

        AbstractImagePrototype bold();

        AbstractImagePrototype breaken();

        AbstractImagePrototype createLink();
        
        AbstractImagePrototype full();

        AbstractImagePrototype hr();

        AbstractImagePrototype indent();

        AbstractImagePrototype insertImage();

        AbstractImagePrototype italic();

        AbstractImagePrototype justifyCenter();

        AbstractImagePrototype justifyLeft();

        AbstractImagePrototype justifyRight();

        AbstractImagePrototype ol();

        AbstractImagePrototype outdent();

        AbstractImagePrototype removeFormat();

        AbstractImagePrototype removeLink();

        AbstractImagePrototype strikeThrough();

        AbstractImagePrototype subscript();

        AbstractImagePrototype superscript();

        AbstractImagePrototype ul();

        AbstractImagePrototype underline();
        
        AbstractImagePrototype video();
    }

    /**
     * Cette {@link Constants} interface est utilisée pour l'internationnalisation
     */
    public interface Strings extends Constants {

        String black();

        String blue();
        
        String breaken();

        String bold();

        String color();

        String createLink();

        String font();
        
        String full();

        String green();

        String hr();

        String indent();

        String insertImage();

        String italic();

        String justifyCenter();

        String justifyLeft();

        String justifyRight();

        String large();

        String medium();

        String normal();

        String ol();

        String outdent();

        String red();

        String removeFormat();

        String removeLink();

        String size();

        String small();

        String strikeThrough();

        String subscript();

        String superscript();

        String ul();

        String underline();
        
        String video();

        String white();

        String xlarge();

        String xsmall();

        String xxlarge();

        String xxsmall();

        String yellow();

        String background();

        String foreground();
    }

}
