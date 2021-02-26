import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author: tianshuxin
 * @time: 2021 - 2021/2/5 - 11:16 上午
 */
public class Main {
    private static final String[] targetLanguages = new String[]{"ar", "bn", "de", "es", "fr", "in", "ja", "pt", "ro", "ru"};

    public static void main(String[] args) {
//        System.out.println(new GoogleTranslator().translate("你好", "ZH", "in"));
        translateAndroidXmlString("src/main/resources/strings.xml", new GoogleTranslator());
    }

    private static void translateAndroidXmlString(String sourcePath, ITranslator translate) {
        translateAndroidXmlString(sourcePath, translate, "EN");
    }

    private static void translateAndroidXmlString(String sourcePath, ITranslator translate, String sourceLanguage) {
        if (!translate.support(sourceLanguage)) {
            System.out.println("This translator " + translate.getClass() + " do not support language " + sourceLanguage);
            return;
        }
        SAXReader reader = new SAXReader();
        int successCount = 0;
        int unSupportCount = 0;
        try {
            for (String targetLanguage : targetLanguages) {
                if (!translate.support(targetLanguage)) {
                    System.out.println("This translator " + translate.getClass() + " do not support language " + targetLanguage + ", CHECK!!!!");
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
                    element.setText(translate.translate(value, sourceLanguage, targetLanguage));
                }
                System.out.println("Translate " + targetLanguage + " finish!");
                System.out.println(document.asXML());
                FileWriter writer = new FileWriter(getPathWithoutLastSegment(sourcePath) + "result-" + targetLanguage + ".xml");
                document.write(writer);
                writer.flush();
                successCount++;
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Finished %d/%d, %d need check!", successCount, targetLanguages.length, unSupportCount);
    }

    public static String getPathWithoutLastSegment(String input) {
        int index = input.lastIndexOf("/");
        if (index != -1) {
            String[] split = input.split("/");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < split.length - 1; i++) {
                sb.append(split[i]).append("/");
            }
            return sb.toString();
        } else {
            return input;
        }
    }

}
