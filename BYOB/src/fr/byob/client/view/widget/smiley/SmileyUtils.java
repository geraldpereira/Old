package fr.byob.client.view.widget.smiley;


public class SmileyUtils {
    public static void main(String[] args) {
        System.out.println(getTextWithSmiley(":p Le weekend a en ;) fait <3 commencé :-o deux :D jours :( avant :'( le B-) départ:-/. Et x-( ouiB-), avec:p le;) stress:-| de savoir si j’allais  ..."));
    }
    
    public static String getTextWithSmiley(String text){
        if(text != null){
            text = replaceAngry(text); //OK 2
            text = replaceAngry2(text); //OK 2
            text = replaceCool(text); //OK 1
            text = replaceCrying(text); //OK 3
            text = replaceFrown(text); //OK 4
            text = replaceGrin(text); //OK 5
            text = replaceHeart(text); //OK 6
            text = replaceNosesmile(text); //OK 7
            text = replaceShocked(text); //OK
//            text = replaceSkeptical(text); //OK
            text = replaceStickingtongueout(text);
            text = replaceKiss(text);
            text = replaceGene(text);
            text = replaceTimide(text);
//            text = replaceStraightface(text);
            text = replaceWink(text);
        }
        return text;
//        return text.replaceAll(":p", "titi et gros minet");
    }
    private static String replaceKiss(String text){
        for(String smil : SmileyConstants.kiss()){
            text = text.replaceAll(smil, "<img src=\""+SmileyKiss.INSTANCE.kiss().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceAngry(String text){
        for(String smil : SmileyConstants.angry()){
            text = text.replaceAll(smil, "<img src=\""+SmileyAngry.INSTANCE.angry().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceCool(String text){
        for(String smil : SmileyConstants.cool()){
            text = text.replaceAll(smil, "<img src=\""+SmileyCool.INSTANCE.cool().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceCrying(String text){
        for(String smil : SmileyConstants.crying()){
            text = text.replaceAll(smil, "<img src=\""+SmileyCrying.INSTANCE.crying().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceFrown(String text){
        for(String smil : SmileyConstants.frown()){
            text = text.replaceAll(smil, "<img src=\""+SmileyFrown.INSTANCE.frown().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceGrin(String text){
        for(String smil : SmileyConstants.grin()){
            text = text.replaceAll(smil, "<img src=\""+SmileyGrin.INSTANCE.grin().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceNosesmile(String text){
        for(String smil : SmileyConstants.nosesmile()){
            text = text.replaceAll(smil, "<img src=\""+SmileyNoseSmile.INSTANCE.nosesmile().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceShocked(String text){
        for(String smil : SmileyConstants.shocked()){
            text = text.replaceAll(smil, "<img src=\""+SmileyShocked.INSTANCE.shocked().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceSkeptical(String text){
        for(String smil : SmileyConstants.skeptical()){
            text = text.replaceAll(smil, "<img src=\""+SmileySkeptical.INSTANCE.skeptical().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceStickingtongueout(String text){
        for(String smil : SmileyConstants.stickingtongueout()){
            text = text.replaceAll(smil, "<img src=\""+SmileyStickingTongueOut.INSTANCE.stickingtongueout().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceStraightface(String text){
        for(String smil : SmileyConstants.straightface()){
            text = text.replaceAll(smil, "<img src=\""+SmileyStraightFace.INSTANCE.straightface().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceWink(String text){
        for(String smil : SmileyConstants.wink()){
            text = text.replaceAll(smil, "<img src=\""+SmileyWink.INSTANCE.wink().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceHeart(String text){
        for(String smil : SmileyConstants.heart()){
            text = text.replaceAll(smil, "<img src=\""+SmileyHeart.INSTANCE.heart().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceGene(String text){
        for(String smil : SmileyConstants.gene()){
            text = text.replaceAll(smil, "<img src=\""+SmileyGene.INSTANCE.gene().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceTimide(String text){
        for(String smil : SmileyConstants.timide()){
            text = text.replaceAll(smil, "<img src=\""+SmileyTimide.INSTANCE.timide().createImage().getUrl()+"\" />");
        }
        return text;
    }
    private static String replaceAngry2(String text){
        for(String smil : SmileyConstants.angry2()){
            text = text.replaceAll(smil, "<img src=\""+SmileyAngry2.INSTANCE.angry2().createImage().getUrl()+"\" />");
        }
        return text;
    }
}
