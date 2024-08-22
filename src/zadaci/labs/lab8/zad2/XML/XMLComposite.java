package labs.lab8.zad2.XML;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XMLComposite implements XMLComponent {

    private String tag;
    private List<XMLComponent> values;
    private Map<String, String> attributes;

    public XMLComposite(String tag) {
        this.tag = tag;
        this.values = new ArrayList<XMLComponent>();
        this.attributes = new LinkedHashMap<String, String>();
    }


    public void addComponent(XMLComponent element) {
        values.add(element);
    }

    public void addAttribute(String type, String value) {
        attributes.put(type, value);
    }

    public String print(int level){
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s<%s", IndentPrinter.printIndent(level) ,tag));

        if(!attributes.isEmpty()){
            sb.append(" ");
        }
        sb.append(XMLLeaf.getAttributesAsString(attributes));
        sb.append(">\n");

        String valuesStr = values.stream()
                .map(i -> i.print(level + 1))
                .collect(Collectors.joining("\n"));

        sb.append(valuesStr).append("\n");

        sb.append(String.format("%s</%s>", IndentPrinter.printIndent(level),tag));

       return sb.toString();
    }
}
