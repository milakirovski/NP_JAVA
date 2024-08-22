package labs.lab8.zad2.XML;

//<tag attribute1="value1" attribute2="value2", ...> value </tag>


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class XMLLeaf implements XMLComponent {

    private String tag;
    private Map<String, String> attributes;
    private String value;


    public XMLLeaf(String tag, String value) {
        this.tag = tag;
        this.attributes = new LinkedHashMap<>();
        this.value = value;
    }

    @Override
    public void addAttribute(String type, String value) {
        attributes.put(type, value);
    }

    public String print(int level) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s<%s", IndentPrinter.printIndent(level) ,tag));

        if(!attributes.isEmpty()) {
            sb.append(" ");
        }

       sb.append(getAttributesAsString(attributes));
        sb.append(">");
        sb.append(value);

        sb.append(String.format("</%s>", tag));

        return sb.toString();
    }

    public static String getAttributesAsString(Map<String, String> attributes) {
        return attributes.entrySet().stream()
                .map(entry -> String.format("%s=\"%s\"", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(" "));
    }
}
