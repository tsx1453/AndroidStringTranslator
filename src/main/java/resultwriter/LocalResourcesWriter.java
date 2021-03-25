package resultwriter;

import org.dom4j.Document;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: tianshuxin
 * @time: 2021 - 2021/3/25 - 3:13 下午
 */
public class LocalResourcesWriter implements ResultWriter {
    @Override
    public void write(Document result, String targetLanguage, String sourcePath) {
        try {
            FileWriter writer = new FileWriter(getPathWithoutLastSegment(sourcePath) + "result-" + targetLanguage + ".xml");
            result.write(writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPathWithoutLastSegment(String input) {
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
