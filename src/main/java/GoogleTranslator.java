import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author: tianshuxin
 * @time: 2021 - 2021/2/5 - 11:17 上午
 */
public class GoogleTranslator implements ITranslator {
    private final Translate translate;
    private final List<Language> supportLanguage;

    public GoogleTranslator() {
        System.setProperty("GOOGLE_API_KEY", Constants.GOOGLE_API_KEY);
        translate = TranslateOptions.getDefaultInstance().getService();
        supportLanguage = translate.listSupportedLanguages();
    }

    @Override
    public String translate(String source, String sourceLanguage, String targetLanguage) {
        try {
            Translation translation = translate.translate(source, Translate.TranslateOption.sourceLanguage(sourceLanguage),
                    Translate.TranslateOption.targetLanguage(targetLanguage));
            return translation.getTranslatedText();
        } catch (Exception e) {
            e.printStackTrace();
            return "error!";
        }
    }

    @Override
    public boolean support(String language) {
        return language != null && supportLanguage.stream().anyMatch(new Predicate<Language>() {
            @Override
            public boolean test(Language l) {
                return language.equalsIgnoreCase(l.getCode());
            }
        });
    }

    @Override
    public void outputSupportLanguage() {
        System.out.println("Supported languages:");
        for (Language language : supportLanguage) {
            System.out.printf("Name: %s, Code: %s\n", language.getName(), language.getCode());
        }
    }
}
