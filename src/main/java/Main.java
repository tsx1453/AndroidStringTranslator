import resultwriter.ProjectResourceWriter;
import translator.GoogleTranslator;

/**
 * @author: tianshuxin
 * @time: 2021 - 2021/2/5 - 11:16 上午
 */
public class Main {
    //    private static final String[] targetLanguages = new String[]{"ar", "bn", "de", "es", "fr", "in", "ja", "pt", "ro", "ru"};
    private static final String[] targetLanguages = new String[]{"af", "ar", "cs", "de", "de", "es", "fi", "fr", "it", "ja", "ko", "pl", "pt", "ru", "th", "tl"};
//    private static final String[] targetLanguages = new String[]{"en"};

    public static void main(String[] args) {
        Translation translation = new Translation(new GoogleTranslator(), new ProjectResourceWriter("/Users/tianshuxin/Workspace/Android/AlphaCleaner/app/src/main/res"));
        translation.translateAndroidXmlString("src/main/resources/strings.xml", "EN", targetLanguages);
    }

}
