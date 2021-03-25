package resultwriter;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author: tianshuxin
 * @time: 2021 - 2021/3/25 - 3:11 下午
 */
public interface ResultWriter {

    void write(Document result, String targetLanguage, String sourcePath);

}
