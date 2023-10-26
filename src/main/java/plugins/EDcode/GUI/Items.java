package plugins.EDcode.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import plugins.EDcode.Config.GlobalConfig;
import plugins.EDcode.DataType.Advanced;
import plugins.EDcode.DataType.Titled;
import plugins.EDcode.Util.FileIO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Items {


    public Node item(Button button, String type, String name) {
        HBox hBox = itemSub(type, name);
//        hBox.setPadding(new Insets(10));
        hBox.getChildren().addAll(button);
        return hBox;
    }

    private HBox itemSub(String type, String name) {
        if (GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name) == null ||
                "".equals(GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).getName())) {
            Advanced advanced = new Advanced();
            advanced.setName(name);
            GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().put(name, advanced);
            FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
        }
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    public Node item(Button buttonEn, Button buttonDe, String type, String name) {
        HBox hBox = itemSub(type, name);
//        hBox.setPadding(new Insets(10));
        hBox.getChildren().addAll(buttonEn, buttonDe);
        return hBox;
    }

    public Node item(Node node, Button buttonEn, Button buttonDe, String type, String name) {
        HBox hBox = (HBox) item(buttonEn, buttonDe, type, name);
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(node, hBox);
        return vBox;
    }

    public Node itemComboBox(Map<String, ComboBox> comboBoxs, Node node, Button buttonEn, Button buttonDe, String type, String name) {
        HBox hBox = (HBox) item(buttonEn, buttonDe, type, name);
        VBox vBox = new VBox(10);
        if (GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).getOtherValue() == null) {
            Map<String, Object> map = new HashMap<>();
            comboBoxs.forEach((key, value) -> {
                value.setPrefWidth(190);

                map.put(key, value.getValue());
                GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).setOtherValue(map);
                FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);

                value.setOnAction(event -> {
                    map.put(key, value.getValue());
                    GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).setOtherValue(map);
                    FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
                });
                vBox.getChildren().add(value);
            });
        } else {
            Map<String, Object> map = (Map<String, Object>) GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).getOtherValue();
            comboBoxs.forEach((key, value) -> {
                value.setPrefWidth(190);

                value.setValue(map.get(key));

                value.setOnAction(event -> {
                    map.put(key, value.getValue());
                    GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).setOtherValue(map);
                    FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
                });
                vBox.getChildren().add(value);
            });
        }

        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(node, hBox);
        return vBox;
    }

    /**
     * @param comboBoxs 需要以map的形式传入，可传入多个，但是需要使用 keyName 进行定位
     * @param buttonEn  编码 button
     * @param buttonDe  解码 button
     * @param type      属于基础页面还是进阶页面
     * @param name      高级选项的名字，也就是编码解码的类型，如：base64
     * @return 返回 Node
     */
    public Node itemComboBox(Map<String, ComboBox> comboBoxs, Button buttonEn, Button buttonDe, String type, String name) {
        HBox hBox = (HBox) item(buttonEn, buttonDe, type, name);
        VBox vBox = new VBox(10);
        if (GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).getOtherValue() == null) {
            Map<String, Object> map = new HashMap<>();
            comboBoxs.forEach((key, value) -> {
                value.setPrefWidth(190);

                map.put(key, value.getValue());
                GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).setOtherValue(map);
                FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);

                value.setOnAction(event -> {
                    map.put(key, value.getValue());
                    GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).setOtherValue(map);
                    FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
                });
                vBox.getChildren().add(value);
            });
        } else {
            Map<String, Object> map = (Map<String, Object>) GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).getOtherValue();
            comboBoxs.forEach((key, value) -> {
                value.setPrefWidth(190);

                value.setValue(map.get(key));

                value.setOnAction(event -> {
                    map.put(key, value.getValue());
                    GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).setOtherValue(map);
                    FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
                });
                vBox.getChildren().add(value);
            });
        }

        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBox);
        return vBox;
    }

    public Node titled(String title, Node node, String type, String name) {
        TitledPane titledPane = new TitledPane(title, node);
        titledPane.setCollapsible(true); // 允许崩溃
        titledPane.setExpanded(GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).getStatus());
        titledPane.setOnMouseClicked(event -> {
            GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get(name).setStatus(titledPane.isExpanded());
            FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
        });
        return titledPane;
    }

    public Node add(List<Titled> lists, Node node, String type, String name) {
        Titled titled = new Titled();
        titled.setType(type);
        titled.setName(name);
        titled.setNode(node);
        lists.add(titled);
        return node;
    }

    public Button EDbutton(String name) {
        Button button = new Button(name);
        button.setMinWidth(90);
        return button;
    }
}
