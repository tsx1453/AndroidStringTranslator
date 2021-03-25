import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import resultwriter.ResultWriter;
import translator.ITranslator;

import java.io.File;
import java.util.Iterator;

/**
 * @author: tianshuxin
 * @time: 2021 - 2021/3/25 - 3:25 下午
 */
public class Translation {

    private final ITranslator translator;
    private final ResultWriter writer;

    public Translation(ITranslator translator, ResultWriter writer) {
        this.translator = translator;
        this.writer = writer;
    }

    public void translateAndroidXmlString(String sourcePath, String sourceLanguage, String[] targetLanguages) {
        if (!translator.support(sourceLanguage)) {
            System.out.println("This translator " + translator.getClass() + " do not support language " + sourceLanguage);
            return;
        }
        SAXReader reader = new SAXReader();
        int successCount = 0;
        int unSupportCount = 0;
        try {
            for (String targetLanguage : targetLanguages) {
                if (!translator.support(targetLanguage)) {
                    System.out.println("This translator " + translator.getClass() + " do not support language " + targetLanguage + ", CHECK!!!!");
                    unSupportCount++;
                }
                System.out.println("Translate " + targetLanguage + " start!");
                Document document = reader.read(new File(sourcePath));
                Element rootElement = document.getRootElement();
                Iterator<Element> it = rootElement.elementIterator();
                while (it.hasNext()) {
                    Element element = it.next();
                    String name = element.attribute("name").getValue();
                    String value = element.getStringValue();
                    System.out.println("Translate " + name + "->" + value);
                    element.setText(translator.translate(value, sourceLanguage, targetLanguage));
                }
                System.out.println("Translate " + targetLanguage + " finish!");
                System.out.println(document.asXML());
                writer.write(document, targetLanguage, sourcePath);
                successCount++;
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        System.out.printf("Finished %d/%d, %d need check!", successCount, targetLanguages.length, unSupportCount);
    }

}
