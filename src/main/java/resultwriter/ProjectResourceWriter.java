package resultwriter;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;

/**
 * @author: tianshuxin
 * @time: 2021 - 2021/3/25 - 3:30 下午
 */
public class ProjectResourceWriter implements ResultWriter {
    private final String projectResourceDir;
    private final SAXReader reader = new SAXReader();

    public ProjectResourceWriter(String projectResourceDir) {
        this.projectResourceDir = projectResourceDir;
    }

    @Override
    public void write(Document result, String targetLanguage, String sourcePath) {
        String targetDirName = "values-" + targetLanguage;
        String targetPath = projectResourceDir + File.separator + targetDirName;
        // 一般情况下直接values-$targetLanguage是存在的，如果不存在的话可能是类似CN-TW这种的，对于这种暂时都写入前缀的翻译
        if (!new File(targetPath).exists()) {
            File dir = new File(projectResourceDir);
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().startsWith(targetDirName)) {
                        appendToFile(file.getAbsolutePath(), result);
                    }
                }
            }
        } else {
            appendToFile(targetPath, result);
        }
    }

    private void appendToFile(String dirPath, Document translatedDoc) {
        System.out.println("write translated data to " + dirPath);
        File dir = new File(dirPath);
        if (dir.exists()) {
            File targetFile = new File(dir, "strings.xml");
            System.out.println("write translated data to " + targetFile.getAbsolutePath());
            if (targetFile.exists()) {
                try {
                    Document targetDoc = reader.read(targetFile);
                    Element targetRootEle = targetDoc.getRootElement();
                    Iterator<Element> elements = translatedDoc.getRootElement().elementIterator();
                    while (elements.hasNext()) {
                        Element e = elements.next();
                        targetRootEle.add(e.createCopy());
                    }
                    FileWriter writer = new FileWriter(targetFile);
                    OutputFormat format = OutputFormat.createPrettyPrint();
                    format.setIndent("    ");
                    XMLWriter xmlWriter = new XMLWriter(writer, format);
                    xmlWriter.write(targetDoc);
                    xmlWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("\\u001B[31m" + "try write data to " + dirPath + File.separator + "strings.xml failed!!" + "\\u001B[0m");
            }
        }
    }
}
