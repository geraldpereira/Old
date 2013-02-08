package fr.byob.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface Css  extends Constants {
	
	public final static Css INSTANCE = (Css) GWT
	.create(Css.class);
	
	String bleuet();
	String fontText();
	String settings();
	String console();
	String loginSettings();
	String cbgRPLoginSettings();
	String LinkSettings();
	String dateSettings();
	String backgroundColorBlue();
	String backgroundColorGrey();
	String backgroundColorGris();
	String backgroundColorWhite();
	String blanc();
	String gris();
	String fonce();
    String ghgrn();
    String red();
    String italic();
    String redBorder();
    String rnsecs();
    String sec();
    String secln();
    String seclnd();
    String seclnlt();
    String seclnltd();
    String padall1();
    String mrgall1();
    String padall2();
    String mrgall2();
    String padall3();
    String mrgall3();
    String padall4();
    String mrgall4();
    String padall5();
    String mrgall5();
    String padall6();
    String mrgall6();
    String padall7();
    String mrgall7();
    String padall8();
    String mrgall8();
    String padall9();
    String mrgall9();
    String padall10();
    String mrgall10();
    String padt1ex();
    String padt0();
    String mrgt0();
    String padt1();
    String mrgt1();
    String padt2();
    String mrgt2();
    String padt3();
    String mrgt3();
    String padt4();
    String mrgt4();
    String padt5();
    String mrgt5();
    String padt6();
    String mrgt6();
    String padt7();
    String mrgt7();
    String padt8();
    String mrgt8();
    String padt9();
    String mrgt9();
    String padt10();
    String mrgt10();
    String padb1ex();
    String padb0();
    String mrgb0();
    String padb1();
    String mrgb1();
    String padb2();
    String mrgb2();
    String padb3();
    String mrgb3();
    String padb4();
    String mrgb4();
    String padb5();
    String mrgb5();
    String padb6();
    String mrgb6();
    String padb7();
    String mrgb7();
    String padb8();
    String mrgb8();
    String padb9();
    String mrgb9();
    String padb10();
    String mrgb10();
    String padl1ex();
    String padl0();
    String mrgl0();
    String padl1();
    String mrgl1();
    String padl2();
    String mrgl2();
    String padl3();
    String mrgl3();
    String padl4();
    String mrgl4();
    String padl5();
    String mrgl5();
    String padl6();
    String mrgl6();
    String padl7();
    String mrgl7();
    String padl8();
    String mrgl8();
    String padl9();
    String mrgl9();
    String padl10();
    String mrgl10();
    String padr1ex();
    String padr0();
    String mrgr0();
    String padr1();
    String mrgr1();
    String padr2();
    String mrgr2();
    String padr3();
    String mrgr3();
    String padr4();
    String mrgr4();
    String padr5();
    String mrgr5();
    String padr6();
    String mrgr6();
    String padr7();
    String mrgr7();
    String padr8();
    String mrgr8();
    String padr9();
    String mrgr9();
    String padr10();
    String mrgr10();
    String pad10();
    String mrg10();
    String mrg20();
    String erreur();
    String conn();
    String connEach();
    String ajoutNews();
    String ajoutInfo();
    String ajoutTitle();
    String roundTitle();
    String cbgRP();
    String cbgRPMenuGlob();
    String cbgRPmenu();
    String deco();
    String pubTitle();
    String titleMenu();
    String titleMenuB();
    String menuSelectText();
    String pub();
    String msgdate();
    String msghelp();
    String msgconsole();
    String msgcat();
    String msgtitle();
    String msglabel();
    String ajoutTitleArt();
    String gwtHorizontalSplitPanel();
    String cbgRPSplit();
    String split();
    String cbgRPBorderTitleArt();
    String userConnected();
    String ajoutTitleArtCat();
    String ajoutMenu();
    String aTitle();
    String menuSelect();
    String cbgRPGrid();
    String cbgRPBorderTitle();
    String cbgRPToolbarTop();
    String cbgRPToolbarBottom();
    String toolbarTop();
    String toolbarBottom();
    String cbgRPFormTop();
    String cbgRPFormBottom();
    String cbgRPForm();
    String formtitle();
    String formlabel();
    String form();
    String listWidget();
    String listWidgetRow();
    String selectedList();
    String opaque();
    String titleListWidget();
    String rowselected();
    String lastrow();
    String rownoselected();
    String gridheader();
    String gridcell();
    String gridborder();
    String gridPadding();
    String gridBorderTotal();
    String borderTitle();
    String cbgRPBorderSite();
    String borderSite();
    String borderMenuSelect();
    String cbgRPBorderMenuSelect();
    String CalendarWidget();
    String navbar();
    String imageButton();
    String table();
    String weekheader();
    String days();
    String cell();
    String gwtHTML();
    String span();
    String today();
    String day();
    String sshdr();
    String sshdrt();
    String sshdrtxt();
    String sshdrtxtsm();
    String ssabln();
    String ssabhln();
    String ssibln();
    String BYOBRichTextArea();
    String hasBYOBRichTextToolbar();
    String BYOBRichTextToolbar();
    String BYOBRichTextToolbarBorder();
    String gwtPushButtonup();
    String gwtPushButtonuphovering();
    String gwtPushButtondown();
    String gwtPushButtondownhovering();
    String gwtToggleButtonup();
    String gwtToggleButtonuphovering();
    String gwtToggleButtondown();
    String gwtToggleButtondownhovering();
    String BYOBRichText();
    String HorizontalDisclosurePanel();
    String HorizontalDisclosurePanelMouseOver();
    String cursor();
    String cursorFleche();
    String cursorHelp();
    String msgdateComm();
    String msglabelComm();
    String msgdateLink();
    String msglabelLink();
    String msgcatLink();
    String msgtitleLink();
    String RoundedCalendarWidget();
    String overMouseTable();
    
    // Chat css
    String tabBarTitle (); // TabBarTitle
    String tabBarTitleReceived (); // TabBarTitle-received
    String tabBarTitleSelected();
    
    String conversationPanel (); // ConversationPanel
    String sent (); // sent
    String received (); // received
    String tabContent (); // TabContent
    String tabRound (); // TabRound

    String contactMouseOver (); // Contact-MouseOver
    String contactRoundHeader (); // ContactRoundHeader
    String contactRound (); // ContactRound
    String headerContact (); // headerContact
    String contact (); // Contact
    String entryName (); // entry-name
    String entryStatus (); // entry-status
    String contactSelf (); // Contact-Self
    
    // Général
    String backgroundColorMain();
    String fontColorMain();
	String hideOverflow();
	String subliminalEast();
	String subliminalNorth();
	String centered();
    String textboxfocus();
    
    
}
