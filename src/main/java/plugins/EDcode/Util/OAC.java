package plugins.EDcode.Util;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import plugins.EDcode.Config.GlobalConfig;
import plugins.EDcode.DataType.Titled;
import plugins.EDcode.GUI.Items;

import java.util.List;
import java.util.Map;

public class OAC {
    public static void openAndClose(Boolean status, List<Titled> listsTitled) {
        for (Titled titled : listsTitled) {
            TitledPane titledPane = (TitledPane) titled.getNode();
            titledPane.setExpanded(status);
            GlobalConfig.CONFIG.getAdvanceds().get(titled.getType()).getAdvanceds().get(titled.getName()).setStatus(status);
        }
        FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
    }

    public static Node addTitled(Button button, String title, String type, String name, List<Titled> listsTitled) {
        return new Items().add(listsTitled, new Items().titled(title, new Items().item(button, type, name), type, name), type, name);
    }

    public static Node addTitled(Button buttonEn, Button buttonDe, String title, String type, String name, List<Titled> listsTitled) {
        return new Items().add(listsTitled, new Items().titled(title, new Items().item(buttonEn, buttonDe, type, name), type, name), type, name);
    }

    public static Node addTitled(Map<String, ComboBox> comboBoxMap, Button buttonEn, Button buttonDe, String title, String type, String name, List<Titled> listsTitled) {
        return new Items().add(listsTitled, new Items().titled(title, new Items().itemComboBox(comboBoxMap, buttonEn, buttonDe, type, name), type, name), type, name);
    }

    public static Node addTitled(Map<String, ComboBox> comboBoxMap,Node node, Button buttonEn, Button buttonDe, String title, String type, String name, List<Titled> listsTitled) {
        return new Items().add(listsTitled, new Items().titled(title, new Items().itemComboBox(comboBoxMap, node, buttonEn, buttonDe, type, name), type, name), type, name);
    }

    public static Node addTitled(Node node, Button buttonEn, Button buttonDe, String title, String type, String name, List<Titled> listsTitled) {
        return new Items().add(listsTitled, new Items().titled(title, new Items().item(node, buttonEn, buttonDe, type, name), type, name), type, name);
    }
}
