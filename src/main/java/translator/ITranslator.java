package translator;

/**
 * @author: tianshuxin
 * @time: 2021 - 2021/2/5 - 11:16 上午
 */
public interface ITranslator {

    String translate(String source, String sourceLanguage, String targetLanguage);

    boolean support(String language);

    void outputSupportLanguage();

}
