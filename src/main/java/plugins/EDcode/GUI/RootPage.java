package plugins.EDcode.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import plugins.EDcode.Config.GlobalConfig;
import plugins.EDcode.DataType.Titled;
import plugins.EDcode.Util.ED.*;
import plugins.EDcode.Util.FileIO;
import plugins.EDcode.Util.MsgBox;
import plugins.EDcode.Util.OAC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootPage {
    BorderPane root;
    HBox hBoxAll;
    VBox vBoxTextArea;
    VBox vBoxButtons;
    VBox vBoxButton;
    ScrollPane scrollPane;
    TextArea textAreaIn;
    TextArea textAreaOut;
    Button buttonOpen;
    Button buttonClose;
    Button buttonReverse;
    Button buttonBase64En;
    Button buttonBase64De;
    ComboBox comboBoxURL;
    Button buttonURLEn;
    Button buttonURLDe;
    Button buttonMd5En;
    ComboBox comboBoxHtml;
    Button buttonHtmlEn;
    Button buttonHtmlDe;
    Button buttonAsciiEn;
    Button buttonAsciiDe;
    ComboBox comboBoxAsciiJinZhi;   // 进制
    ComboBox comboBoxAsciiJianGe;   // 间隔
    Button buttonUnicodeEn;
    Button buttonUnicodeDe;
    Button buttonMorseEn;
    Button buttonMorseDe;
    Separator separator;
    List<Titled> listsTitled;
    private String type = "base";

    public Node show() {
        textAreaIn = new TextArea();
        textAreaIn.setPromptText("输入：");
        textAreaIn.setWrapText(true);
        textAreaIn.setPrefHeight(500);
        textAreaOut = new TextArea();
//        textAreaOut.setEditable(false);
        textAreaOut.setPromptText("输出：");
        textAreaOut.setWrapText(true);
        textAreaOut.setPrefHeight(500);

        // textarea
        vBoxTextArea = new VBox(10);
        vBoxTextArea.setPadding(new Insets(10, 0, 10, 10));
        vBoxTextArea.setPrefWidth(400);
        vBoxTextArea.getChildren().addAll(textAreaIn, textAreaOut);

        // button
        buttonOpen = new Button("展开");
        buttonOpen.setPrefWidth(100);
        buttonOpen.setOnAction(event -> {
            OAC.openAndClose(true, listsTitled);
        });
        buttonClose = new Button("收起");
        buttonClose.setPrefWidth(100);
        buttonClose.setOnAction(event -> {
            OAC.openAndClose(false, listsTitled);
        });
        buttonReverse = new Button("两极反转");
        buttonReverse.setOnAction(event -> {
            String tmp = textAreaOut.getText();
            textAreaOut.setText(textAreaIn.getText());
            textAreaIn.setText(tmp);
        });
        buttonReverse.setPrefWidth(210);

        // Base64
        buttonBase64En = new Items().EDbutton("编码");
        buttonBase64En.setOnAction(event -> {
            textAreaOut.setText(Base64.encode(textAreaIn.getText()));
        });
        buttonBase64De = new Items().EDbutton("解码");
        buttonBase64De.setOnAction(event -> {
            try {
                textAreaOut.setText(Base64.decode(textAreaIn.getText()));
            } catch (Exception e) {
                MsgBox.sendSystemInfo("提示", "解码失败");
            }
        });

        // URL
        comboBoxURL = new ComboBox();
        comboBoxURL.getItems().addAll("全编码", "半编码", "空格用 + 代替");
        comboBoxURL.setValue("半编码");
        Map<String, ComboBox> urlBianMa = new HashMap<>();
        urlBianMa.put("bianMa", comboBoxURL);
        buttonURLEn = new Items().EDbutton("编码");
        buttonURLEn.setOnAction(event -> {
            Map<String, Object> map = (Map<String, Object>) GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get("url").getOtherValue();
            String bianMa = (String) map.get("bianMa");
            if (bianMa.equals("半编码")) {
                textAreaOut.setText(URL.encode(textAreaIn.getText()));
            } else if (bianMa.equals("全编码")) {
                textAreaOut.setText(URL.encodeAll(textAreaIn.getText()));
            } else if (bianMa.equals("空格用 + 代替")) {
                textAreaOut.setText(URL.encodeSpace(textAreaIn.getText()));
            }
        });
        buttonURLDe = new Items().EDbutton("解码");
        buttonURLDe.setOnAction(event -> {
            try {
                textAreaOut.setText(URL.decode(textAreaIn.getText()));
            } catch (Exception e) {
                MsgBox.sendSystemInfo("提示", "解码失败");
            }
        });

        // md5
        buttonMd5En = new Items().EDbutton("编码");
        buttonMd5En.setOnAction(event -> {
            textAreaOut.setText(Md5.encode(textAreaIn.getText()));
        });

        // html
        comboBoxHtml = new ComboBox();
        comboBoxHtml.getItems().addAll("全编码", "半编码");
        comboBoxHtml.setValue("全编码");
        Map<String, ComboBox> htmlBianMa = new HashMap<>();
        htmlBianMa.put("bianMa", comboBoxHtml);
        buttonHtmlEn = new Items().EDbutton("编码");
        buttonHtmlEn.setOnAction(event -> {
            Map<String, Object> map = (Map<String, Object>) GlobalConfig.CONFIG.getAdvanceds().get(type).getAdvanceds().get("html").getOtherValue();
            String bianMa = (String) map.get("bianMa");
            if (bianMa.equals("半编码")) {
                textAreaOut.setText(Html.encode(textAreaIn.getText()));
            } else if (bianMa.equals("全编码")) {
                textAreaOut.setText(Html.encodeAll(textAreaIn.getText()));
            }
        });
        buttonHtmlDe = new Items().EDbutton("解码");
        buttonHtmlDe.setOnAction(event -> {
            try {
                textAreaOut.setText(Html.decode(textAreaIn.getText()));
            } catch (Exception e) {
                MsgBox.sendSystemInfo("提示", "解码失败");
            }
        });

        // ASCII
        comboBoxAsciiJianGe = new ComboBox();
        comboBoxAsciiJianGe.getItems().addAll("无", "空格", "换行");
        comboBoxAsciiJianGe.setValue("无");
        comboBoxAsciiJinZhi = new ComboBox();
        comboBoxAsciiJinZhi.getItems().addAll("十进制", "十六进制");
        comboBoxAsciiJinZhi.setValue("十进制");
        Map<String, ComboBox> asciiBianMa = new HashMap<>();
        asciiBianMa.put("jianGe", comboBoxAsciiJianGe);
        asciiBianMa.put("jinZhi", comboBoxAsciiJinZhi);
        buttonAsciiEn = new Items().EDbutton("编码");
        buttonAsciiEn.setOnAction(event -> {
            switch (comboBoxAsciiJinZhi.getValue().toString()) {
                case "十进制":
                    switch (comboBoxAsciiJianGe.getValue().toString()) {
                        case "无":
                            textAreaOut.setText(Ascii.encode(textAreaIn.getText(), ""));
                            break;
                        case "空格":
                            textAreaOut.setText(Ascii.encode(textAreaIn.getText(), " "));
                            break;
                        case "换行":
                            textAreaOut.setText(Ascii.encode(textAreaIn.getText(), "\n"));
                            break;
                    }
                    break;
                case "十六进制":
                    switch (comboBoxAsciiJianGe.getValue().toString()) {
                        case "无":
                            textAreaOut.setText(Ascii.encodeHex(textAreaIn.getText(), ""));
                            break;
                        case "空格":
                            textAreaOut.setText(Ascii.encodeHex(textAreaIn.getText(), " "));
                            break;
                        case "换行":
                            textAreaOut.setText(Ascii.encodeHex(textAreaIn.getText(), "\n"));
                            break;
                    }
                    break;
            }
        });
        buttonAsciiDe = new Items().EDbutton("解码");
        buttonAsciiDe.setOnAction(event -> {
            switch (comboBoxAsciiJinZhi.getValue().toString()) {
                case "十进制":
                    try {
                        textAreaOut.setText(Ascii.decode(textAreaIn.getText()));
                    } catch (Exception e) {
                        MsgBox.sendSystemInfo("提示", "解码失败");
                    }
                    break;
                case "十六进制":
                    try {
                        textAreaOut.setText(Ascii.decodeHex(textAreaIn.getText()));
                    } catch (Exception e) {
                        MsgBox.sendSystemInfo("提示", "解码失败");
                    }
                    break;
            }
        });

        // Unicode
        buttonUnicodeEn = new Items().EDbutton("编码");
        buttonUnicodeEn.setOnAction(event -> {
            textAreaOut.setText(Unicode.encode(textAreaIn.getText(), true));
        });
        buttonUnicodeDe = new Items().EDbutton("解码");
        buttonUnicodeDe.setOnAction(event -> {
            textAreaOut.setText(Unicode.decode(textAreaIn.getText()));
        });

        // Morse code
        buttonMorseEn = new Items().EDbutton("编码");
        buttonMorseEn.setOnAction(event -> {
            textAreaOut.setText(Morse.encode(textAreaIn.getText()));
        });
        buttonMorseDe = new Items().EDbutton("解码");
        buttonMorseDe.setOnAction(event -> {
            textAreaOut.setText(Morse.decode(textAreaIn.getText()));
        });

        // 分割线
        separator = new Separator();

        // func
        listsTitled = new ArrayList<>();
        vBoxButton = new VBox(10);
        vBoxButton.setPadding(new Insets(10, 20, 10, 10));
        vBoxButton.getChildren().addAll(
                new Items().item(buttonOpen, buttonClose, type, "root"), buttonReverse, separator,
                OAC.addTitled(buttonBase64En, buttonBase64De, "Base64", type, "base64", listsTitled),
                OAC.addTitled(urlBianMa, buttonURLEn, buttonURLDe, "URL", type, "url", listsTitled),
                OAC.addTitled(buttonMd5En, "MD5", type, "md5", listsTitled),
                OAC.addTitled(htmlBianMa, buttonHtmlEn, buttonHtmlDe, "HTML", type, "html", listsTitled),
                OAC.addTitled(asciiBianMa, buttonAsciiEn, buttonAsciiDe, "ASCII", type, "ascii", listsTitled),
                OAC.addTitled(buttonUnicodeEn, buttonUnicodeDe, "Unicode", type, "unicode", listsTitled),
                OAC.addTitled(buttonMorseEn, buttonMorseDe, "Morse（莫斯密码）", type, "morse", listsTitled)
        );
        scrollPane = new ScrollPane(vBoxButton);
        scrollPane.setFitToWidth(true);
        vBoxButtons = new VBox(10);
        vBoxButtons.setPadding(new Insets(10, 10, 10, 0));
        vBoxButtons.getChildren().addAll(scrollPane);

        hBoxAll = new HBox(10);
        hBoxAll.setPadding(new Insets(10));
        hBoxAll.setAlignment(Pos.CENTER);
        hBoxAll.getChildren().addAll(vBoxTextArea, vBoxButtons);

        root = new BorderPane();
        root.setCenter(hBoxAll);
        return root;
    }
}
