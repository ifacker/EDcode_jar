package plugins.EDcode.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import plugins.EDcode.DataType.Titled;
import plugins.EDcode.Util.ED.*;
import plugins.EDcode.Util.MsgBox;
import plugins.EDcode.Util.OAC;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedPage {
    BorderPane root;
    HBox hBoxAll;
    VBox vBoxTextArea;
    VBox vBoxButtons;
    VBox vBoxButton;
    ScrollPane scrollPane;
    TextArea textAreaIn;
    TextArea textAreaOut;
    TextArea textAreaKey;
    Button buttonOpen;
    Button buttonClose;
    Button buttonReverse;
    Button buttonAesEn;
    Button buttonAesDe;
    Button buttonAesCreateKey;
    Button buttonAesCreateIV;
    ComboBox comboBoxAesMode;
    ComboBox comboBoxAesPadding;
    ComboBox comboBoxAesOutputType;
    ComboBox comboBoxAesKeySize;
    TextArea textAreaAesIV;
    VBox vBoxAes;
    Button buttonDesEn;
    Button buttonDesDe;
    Button buttonDesCreateKey;
    Button buttonDesCreateIV;
    ComboBox comboBoxDesMode;
    ComboBox comboBoxDesPadding;
    ComboBox comboBoxDesOutputType;
    TextArea textAreaDesIV;
    VBox vBoxDes;
    Button buttonDesedeEn;
    Button buttonDesedeDe;
    Button buttonDesedeCreateKey;
    Button buttonDesedeCreateIV;
    ComboBox comboBoxDesedeMode;
    ComboBox comboBoxDesedePadding;
    ComboBox comboBoxDesedeOutputType;
    ComboBox comboBoxDesedeKeySize;
    TextArea textAreaDesedeIV;
    VBox vBoxDesede;
    Button buttonRsaEn;
    Button buttonRsaDe;
    Button buttonRsaCreateKeys;
    Button buttonRsaReverse;
    ComboBox comboBoxRsaKeySize;
    TextArea textAreaRsaKey;
    VBox vBoxRsa;
    Button buttonJasyptEn;
    Button buttonJasyptDe;
    Button buttonBCryptEn;
    Button buttonRailFenceEn;
    Button buttonRailFenceDe;
    Button buttonRailFenceNotSpace;
    Button buttonVigenereEn;
    Button buttonVigenereDe;
    Separator separator;
    List<Titled> listsTitled;
    private String type = "adva";

    public Node show() {
        textAreaIn = new TextArea();
        textAreaIn.setPromptText("输入：");
        textAreaIn.setWrapText(true);
        textAreaIn.setPrefHeight(500);
        textAreaKey = new TextArea();
        textAreaKey.setPrefHeight(40);
        textAreaKey.setWrapText(true);
        textAreaKey.setPromptText("Key：");
        textAreaOut = new TextArea();
//        textAreaOut.setEditable(false);
        textAreaOut.setPromptText("输出：");
        textAreaOut.setWrapText(true);
        textAreaOut.setPrefHeight(500);

        // textarea
        vBoxTextArea = new VBox(10);
        vBoxTextArea.setPadding(new Insets(10, 0, 10, 10));
        vBoxTextArea.setPrefWidth(400);
        vBoxTextArea.getChildren().addAll(textAreaIn, textAreaKey, textAreaOut);

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

        // Aes
        comboBoxAesMode = new ComboBox();
        comboBoxAesMode.getItems().addAll("ECB", "CBC", "CTR", "OFB", "CFB");
        comboBoxAesMode.setValue("ECB");
        comboBoxAesPadding = new ComboBox();
        comboBoxAesPadding.getItems().addAll("PKCS5Padding", "PKCS7Padding", "ISO10126Padding", "NoPadding"); //"ZeroPadding", "ISO9797ALG3Padding", "ANSI_X923Padding"
        comboBoxAesPadding.setValue("PKCS5Padding");
        comboBoxAesOutputType = new ComboBox();
        comboBoxAesOutputType.getItems().addAll("Base64", "Hex");
        comboBoxAesOutputType.setValue("Base64");
        comboBoxAesKeySize = new ComboBox();
        comboBoxAesKeySize.getItems().addAll("生成 24 位(128) key", "生成 32 位(192) key", "生成 44 位(256) key");
        comboBoxAesKeySize.setValue("生成 24 位(128) key");
        Map<String, ComboBox> aesBianMa = new HashMap<>();
        aesBianMa.put("mode", comboBoxAesMode);
        aesBianMa.put("padding", comboBoxAesPadding);
        aesBianMa.put("outputType", comboBoxAesOutputType);
        aesBianMa.put("keySize", comboBoxAesKeySize);
        buttonAesCreateKey = new Button("生成 Key");
        buttonAesCreateKey.setPrefWidth(190);
        buttonAesCreateKey.setOnAction(event -> {
            try {
                int keySize = 128;
                switch ((String) comboBoxAesKeySize.getValue()) {
                    case "生成 24 位(128) key":
                        keySize = 128;
                        break;
                    case "生成 32 位(192) key":
                        keySize = 192;
                        break;
                    case "生成 44 位(256) key":
                        keySize = 256;
                        break;
                }
                textAreaKey.setText(Aes2.keyToString(Aes2.generateAESKey(keySize)));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
        buttonAesCreateIV = new Button("生成偏移量 IV");
        buttonAesCreateIV.setPrefWidth(190);
        buttonAesCreateIV.setOnAction(event -> {
            byte[] tmp = Aes2.generateRandomIV(16);
            String iv = "";
            for (byte b : tmp) {
                iv += b + " ";
            }
            textAreaAesIV.setText(iv.trim());
        });
        textAreaAesIV = new TextArea();
        textAreaAesIV.setPromptText("偏移量 IV（ECB不需要偏移量，所以设置无效；其他都需要偏移量参数）");
        textAreaAesIV.setPrefWidth(190);
        textAreaAesIV.setPrefHeight(65);
        textAreaAesIV.setWrapText(true);

        buttonAesEn = new Items().EDbutton("编码");
        buttonAesEn.setOnAction(event -> {
            if ("".equals(textAreaKey.getText())) {
                MsgBox.sendSystemInfo("提示", "请输入 Key");
                return;
            }
            try {
                if ("".equals(textAreaAesIV.getText()) || "ECB".equals(comboBoxAesMode.getValue())) {
                    textAreaOut.setText(Aes2.encode(textAreaIn.getText(), textAreaKey.getText(), (String) comboBoxAesMode.getValue(), (String) comboBoxAesPadding.getValue(), (String) comboBoxAesOutputType.getValue()));
                } else {
                    textAreaOut.setText(Aes2.encode(textAreaIn.getText(), textAreaKey.getText(), (String) comboBoxAesMode.getValue(), (String) comboBoxAesPadding.getValue(), (String) comboBoxAesOutputType.getValue(), textAreaAesIV.getText()));
                }
            } catch (Exception e) {
                switch ((String) comboBoxAesPadding.getValue()) {
                    case "NoPadding":
                        MsgBox.sendSystemInfo("错误", "NoPadding编码需要明文的字符长度是16的倍数");
                        return;
                }
                MsgBox.sendSystemInfo("错误", "编码失败 " + e.getMessage());
//                throw new RuntimeException(e);
            }
        });
        buttonAesDe = new Items().EDbutton("解码");
        buttonAesDe.setOnAction(event -> {
            if ("".equals(textAreaKey.getText())) {
                MsgBox.sendSystemInfo("提示", "请输入 Key");
                return;
            }
            try {
                if ("".equals(textAreaAesIV.getText()) || "ECB".equals(comboBoxAesMode.getValue())) {
                    textAreaOut.setText(Aes2.decode(textAreaIn.getText(), textAreaKey.getText(), (String) comboBoxAesMode.getValue(), (String) comboBoxAesPadding.getValue(), (String) comboBoxAesOutputType.getValue()));
                } else {
                    textAreaOut.setText(Aes2.decode(textAreaIn.getText(), textAreaKey.getText(), (String) comboBoxAesMode.getValue(), (String) comboBoxAesPadding.getValue(), (String) comboBoxAesOutputType.getValue(), textAreaAesIV.getText()));
                }
            } catch (Exception e) {
                MsgBox.sendSystemInfo("错误", "解码失败 " + e.getMessage());
            }
        });
        vBoxAes = new VBox(10);
        vBoxAes.getChildren().addAll(buttonAesCreateKey, buttonAesCreateIV, textAreaAesIV);

        // Des
        comboBoxDesMode = new ComboBox();
        comboBoxDesMode.getItems().addAll("ECB", "CBC", "CTR", "OFB", "CFB");
        comboBoxDesMode.setValue("ECB");
        comboBoxDesPadding = new ComboBox();
        comboBoxDesPadding.getItems().addAll("PKCS5Padding", "PKCS7Padding", "ISO10126Padding", "NoPadding"); //"ZeroPadding", "ISO9797ALG3Padding", "ANSI_X923Padding"
        comboBoxDesPadding.setValue("PKCS5Padding");
        comboBoxDesOutputType = new ComboBox();
        comboBoxDesOutputType.getItems().addAll("Base64", "Hex");
        comboBoxDesOutputType.setValue("Base64");
        Map<String, ComboBox> desBianMa = new HashMap<>();
        desBianMa.put("mode", comboBoxDesMode);
        desBianMa.put("padding", comboBoxDesPadding);
        desBianMa.put("outputType", comboBoxDesOutputType);
        buttonDesCreateKey = new Button("生成 Key");
        buttonDesCreateKey.setPrefWidth(190);
        buttonDesCreateKey.setOnAction(event -> {
            textAreaKey.setText(Des.getRandomKey());
        });
        buttonDesCreateIV = new Button("生成偏移量 IV");
        buttonDesCreateIV.setPrefWidth(190);
        buttonDesCreateIV.setOnAction(event -> {
            String iv = "";
            for (byte b : Des.getRandomIV()) {
                iv += b + " ";
            }
            textAreaDesIV.setText(iv.trim());
        });
        textAreaDesIV = new TextArea();
        textAreaDesIV.setPromptText("偏移量 IV（ECB不需要偏移量，所以设置无效；其他都需要偏移量参数）");
        textAreaDesIV.setPrefWidth(190);
        textAreaDesIV.setPrefHeight(65);
        textAreaDesIV.setWrapText(true);

        buttonDesEn = new Items().EDbutton("编码");
        buttonDesEn.setOnAction(event -> {
            if ("".equals(textAreaKey.getText())) {
                MsgBox.sendSystemInfo("提示", "请输入 Key");
                return;
            }
            try {
                if ("".equals(textAreaDesIV.getText()) || "ECB".equals(comboBoxDesMode.getValue())) {
                    textAreaOut.setText(Des.encode(textAreaIn.getText(), textAreaKey.getText(), (String) comboBoxDesMode.getValue(), (String) comboBoxDesPadding.getValue(), (String) comboBoxDesOutputType.getValue()));
                } else {
                    textAreaOut.setText(Des.encode(textAreaIn.getText(), textAreaKey.getText(), (String) comboBoxDesMode.getValue(), (String) comboBoxDesPadding.getValue(), (String) comboBoxDesOutputType.getValue(), textAreaDesIV.getText()));
                }
            } catch (Exception e) {
//                switch ((String) comboBoxDesPadding.getValue()) {
//                    case "NoPadding":
//                        MsgBox.sendSystemInfo("错误", "NoPadding编码需要明文的字符长度是16的倍数");
//                        return;
//                }
                MsgBox.sendSystemInfo("错误", "编码失败 " + e.getMessage());
//                throw new RuntimeException(e);
            }
        });
        buttonDesDe = new Items().EDbutton("解码");
        buttonDesDe.setOnAction(event -> {
            if ("".equals(textAreaKey.getText())) {
                MsgBox.sendSystemInfo("提示", "请输入 Key");
                return;
            }
            try {
                if ("".equals(textAreaDesIV.getText()) || "ECB".equals(comboBoxDesMode.getValue())) {
                    textAreaOut.setText(Des.decode(textAreaIn.getText(), textAreaKey.getText(), (String) comboBoxDesMode.getValue(), (String) comboBoxDesPadding.getValue(), (String) comboBoxDesOutputType.getValue()));
                } else {
                    textAreaOut.setText(Des.decode(textAreaIn.getText(), textAreaKey.getText(), (String) comboBoxDesMode.getValue(), (String) comboBoxDesPadding.getValue(), (String) comboBoxDesOutputType.getValue(), textAreaDesIV.getText()));
                }
            } catch (Exception e) {
                MsgBox.sendSystemInfo("错误", "解码失败 " + e.getMessage());
            }
        });
        vBoxDes = new VBox(10);
        vBoxDes.getChildren().addAll(buttonDesCreateKey, buttonDesCreateIV, textAreaDesIV);

        // 3Des
        comboBoxDesedeMode = new ComboBox();
        comboBoxDesedeMode.getItems().addAll("ECB", "CBC", "CTR", "OFB", "CFB");
        comboBoxDesedeMode.setValue("ECB");
        comboBoxDesedePadding = new ComboBox();
        comboBoxDesedePadding.getItems().addAll("PKCS5Padding", "PKCS7Padding", "ISO10126Padding", "NoPadding"); //"ZeroPadding", "ISO9797ALG3Padding", "ANSI_X923Padding"
        comboBoxDesedePadding.setValue("PKCS5Padding");
        comboBoxDesedeOutputType = new ComboBox();
        comboBoxDesedeOutputType.getItems().addAll("Base64", "Hex");
        comboBoxDesedeOutputType.setValue("Base64");
        comboBoxDesedeKeySize = new ComboBox();
        comboBoxDesedeKeySize.getItems().addAll("生成 16 位(80) key", "生成 24 位(128) key");
        comboBoxDesedeKeySize.setValue("生成 24 位(128) key");
        Map<String, ComboBox> desedeBianMa = new HashMap<>();
        desedeBianMa.put("mode", comboBoxDesedeMode);
        desedeBianMa.put("padding", comboBoxDesedePadding);
        desedeBianMa.put("outputType", comboBoxDesedeOutputType);
        desedeBianMa.put("keySize", comboBoxDesedeKeySize);
        buttonDesedeCreateKey = new Button("生成 Key");
        buttonDesedeCreateKey.setPrefWidth(190);
        buttonDesedeCreateKey.setOnAction(event -> {
            int keySize = 128;
            switch ((String) comboBoxDesedeKeySize.getValue()) {
                case "生成 16 位(80) key":
                    MsgBox.sendSystemInfo("提示", "16 位的 key 只有 PKCS7Padding 支持");
                    keySize = 80;
                    break;
                case "生成 24 位(128) key":
                    keySize = 128;
                    break;
            }
            textAreaKey.setText(Desede.getRandomKey(keySize));
        });
        buttonDesedeCreateIV = new Button("生成偏移量 IV");
        buttonDesedeCreateIV.setPrefWidth(190);
        buttonDesedeCreateIV.setOnAction(event -> {
            String iv = "";
            for (byte b : Desede.getRandomIV()) {
                iv += b + " ";
            }
            textAreaDesedeIV.setText(iv.trim());
        });
        textAreaDesedeIV = new TextArea();
        textAreaDesedeIV.setPromptText("偏移量 IV（ECB不需要偏移量，所以设置无效；其他都需要偏移量参数）");
        textAreaDesedeIV.setPrefWidth(190);
        textAreaDesedeIV.setPrefHeight(65);
        textAreaDesedeIV.setWrapText(true);

        buttonDesedeEn = new Items().EDbutton("编码");
        buttonDesedeEn.setOnAction(event -> {
            if ("".equals(textAreaKey.getText())) {
                MsgBox.sendSystemInfo("提示", "请输入 Key");
                return;
            }
            try {
                if ("".equals(textAreaDesedeIV.getText()) || "ECB".equals(comboBoxDesedeMode.getValue())) {
                    textAreaOut.setText(Desede.encode(textAreaIn.getText(), textAreaKey.getText(), (String) comboBoxDesedeMode.getValue(), (String) comboBoxDesedePadding.getValue(), (String) comboBoxDesedeOutputType.getValue()));
                } else {
                    textAreaOut.setText(Desede.encode(textAreaIn.getText(), textAreaKey.getText(), (String) comboBoxDesedeMode.getValue(), (String) comboBoxDesedePadding.getValue(), (String) comboBoxDesedeOutputType.getValue(), textAreaDesedeIV.getText()));
                }
            } catch (Exception e) {
//                switch ((String) comboBoxDesedePadding.getValue()) {
//                    case "NoPadding":
//                        MsgBox.sendSystemInfo("错误", "NoPadding编码需要明文的字符长度是16的倍数");
//                        return;
//                }
                MsgBox.sendSystemInfo("错误", "编码失败 " + e.getMessage());
//                throw new RuntimeException(e);
            }
        });
        buttonDesedeDe = new Items().EDbutton("编码");
        buttonDesedeDe.setOnAction(event -> {
            if ("".equals(textAreaKey.getText())) {
                MsgBox.sendSystemInfo("提示", "请输入 Key");
                return;
            }
            try {
                if ("".equals(textAreaDesedeIV.getText()) || "ECB".equals(comboBoxDesedeMode.getValue())) {
                    textAreaOut.setText(Desede.decode(textAreaIn.getText(), textAreaKey.getText(), (String) comboBoxDesedeMode.getValue(), (String) comboBoxDesedePadding.getValue(), (String) comboBoxDesedeOutputType.getValue()));
                } else {
                    textAreaOut.setText(Desede.decode(textAreaIn.getText(), textAreaKey.getText(), (String) comboBoxDesedeMode.getValue(), (String) comboBoxDesedePadding.getValue(), (String) comboBoxDesedeOutputType.getValue(), textAreaDesedeIV.getText()));
                }
            } catch (Exception e) {
                MsgBox.sendSystemInfo("错误", "解码失败 " + e.getMessage());
            }
        });
        vBoxDesede = new VBox(10);
        vBoxDesede.getChildren().addAll(buttonDesedeCreateKey, buttonDesedeCreateIV, textAreaDesedeIV);

        // RSA
        comboBoxRsaKeySize = new ComboBox();
        comboBoxRsaKeySize.getItems().addAll("key-512位(bit)", "key-1024位(bit)", "key-2048位(bit)", "key-3072位(bit)", "key-4096位(bit)");
        comboBoxRsaKeySize.setValue("key-2048位(bit)");
        Map<String, ComboBox> rsaBianMa = new HashMap<>();
        rsaBianMa.put("keySize", comboBoxRsaKeySize);
        buttonRsaCreateKeys = new Button("生成 Keys");
        buttonRsaCreateKeys.setPrefWidth(190);
        buttonRsaCreateKeys.setOnAction(event -> {
            try {
                int keySize = 2048;
                switch ((String) comboBoxRsaKeySize.getValue()) {
                    case "key-512位(bit)":
                        keySize = 512;
                        break;
                    case "key-1024位(bit)":
                        keySize = 1024;
                        break;
                    case "key-2048位(bit)":
                        keySize = 2048;
                        break;
                    case "key-3072位(bit)":
                        keySize = 3072;
                        break;
                    case "key-4096位(bit)":
                        keySize = 4096;
                        break;
                }
                KeyPair keyPair = Rsa2.generateKeyPair(keySize);
                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();
                textAreaKey.setText(String.format("%s\n%s\n%s", Rsa2.publicKeyHome, Rsa2.publicKeyToString(publicKey), Rsa2.publicKeyEnd));
                textAreaRsaKey.setText(String.format("%s\n%s\n%s", Rsa2.privateKeyHome, Rsa2.privateKeyToString(privateKey), Rsa2.privateKeyEnd));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        buttonRsaReverse = new Button("公私互换");
        buttonRsaReverse.setPrefWidth(190);
        buttonRsaReverse.setOnAction(event -> {
            String tmp = textAreaRsaKey.getText();
            textAreaRsaKey.setText(textAreaKey.getText());
            textAreaKey.setText(tmp);
        });
        textAreaRsaKey = new TextArea();
        textAreaRsaKey.setPromptText("Key2：");
        textAreaRsaKey.setPrefWidth(190);
        textAreaRsaKey.setPrefHeight(90);
        textAreaRsaKey.setWrapText(true);
        vBoxRsa = new VBox(10);
        vBoxRsa.getChildren().addAll(buttonRsaCreateKeys, textAreaRsaKey, buttonRsaReverse);
        buttonRsaEn = new Items().EDbutton("编码");
        buttonRsaEn.setOnAction(event -> {
            try {
                if (textAreaKey.getText().contains(Rsa2.publicKeyHome)) {
                    textAreaOut.setText(Rsa2.encryptWithPublicKey(textAreaIn.getText(), Rsa2.stringToPublicKey(Rsa2.fuckHomeEnd(textAreaKey.getText()))));
                } else if (textAreaKey.getText().contains(Rsa2.privateKeyHome)) {
                    textAreaOut.setText(Rsa2.encryptWithPrivateKey(textAreaIn.getText(), Rsa2.stringToPrivateKey(Rsa2.fuckHomeEnd(textAreaKey.getText()))));
                } else {
                    MsgBox.sendSystemInfo("提示", String.format("请输入合规的 key，需要包含\"%s\"...", Rsa2.publicKeyHome));
                }
            } catch (Exception e) {
                MsgBox.sendSystemInfo("错误", "编码错误 " + e.getMessage());
//                e.printStackTrace();
            }
        });
        buttonRsaDe = new Items().EDbutton("解码");
        buttonRsaDe.setOnAction(event -> {
            try {
                if (textAreaKey.getText().contains(Rsa2.publicKeyHome)) {
                    textAreaOut.setText(Rsa2.decryptWithPublicKey(textAreaIn.getText(), Rsa2.stringToPublicKey(Rsa2.fuckHomeEnd(textAreaKey.getText()))));
                } else if (textAreaKey.getText().contains(Rsa2.privateKeyHome)) {
                    textAreaOut.setText(Rsa2.decryptWithPrivateKey(textAreaIn.getText(), Rsa2.stringToPrivateKey(Rsa2.fuckHomeEnd(textAreaKey.getText()))));
                } else {
                    MsgBox.sendSystemInfo("提示", String.format("请输入合规的 key，需要包含\"%s\"...", Rsa2.publicKeyHome));
                }
            } catch (Exception e) {
                MsgBox.sendSystemInfo("错误", "解码错误 " + e.getMessage());
//                e.printStackTrace();
            }
        });

        // Jasypt
        buttonJasyptEn = new Items().EDbutton("编码");
        buttonJasyptEn.setOnAction(event -> {
            if ("".equals(textAreaKey.getText())) {
                MsgBox.sendSystemInfo("提示", "请输入 Key");
                return;
            }
            textAreaOut.setText(Jasypt.encode(textAreaIn.getText(), textAreaKey.getText()));
        });
        buttonJasyptDe = new Items().EDbutton("解码");
        buttonJasyptDe.setOnAction(event -> {
            if ("".equals(textAreaKey.getText())) {
                MsgBox.sendSystemInfo("提示", "请输入 Key");
                return;
            }
            try {
                textAreaOut.setText(Jasypt.decode(textAreaIn.getText(), textAreaKey.getText()));
            } catch (Exception e) {
                MsgBox.sendSystemInfo("提示", "解码失败");
            }
        });

        // BCrypt
        buttonBCryptEn = new Items().EDbutton("编码");
        buttonBCryptEn.setOnAction(event -> {
            int num = 10;

            if ("".equals(textAreaKey.getText())) {
                MsgBox.sendSystemInfo("提示", "默认工作因子为：10（4-30）");
            } else {
                try {
                    num = Integer.parseInt(textAreaKey.getText());
                } catch (Exception e) {
                    MsgBox.sendSystemInfo("提示", "请输入数字（4-30）");
                    return;
                }
            }

            if (num > 15) {
                MsgBox.showConfirmation("警告", "你现在的参数已经超过了15（4-30），运算将会非常缓慢，是否继续？", r -> {
                    if (r == ButtonType.OK) {
                        try {
                            textAreaOut.setText(BCrypt.encode(textAreaIn.getText(), textAreaKey.getText()));
                        } catch (Exception e) {
                            MsgBox.sendSystemInfo("提示", "编码失败");
                        }
                    }
                });
            } else {
                if (num < 4) {
                    MsgBox.sendSystemInfo("提示", "默认工作因子太小了，请输入 >= 4（4-30）");
                    return;
                }

                try {
                    textAreaOut.setText(BCrypt.encode(textAreaIn.getText(), textAreaKey.getText()));
                } catch (Exception e) {
                    MsgBox.sendSystemInfo("提示", "编码失败");
                }
            }

        });

        // Rail Fence（栅栏密码）
        buttonRailFenceEn = new Items().EDbutton("编码");
        buttonRailFenceEn.setOnAction(event -> {
            try {
                if (textAreaIn.getText().trim().contains(" ")) {
                    MsgBox.sendSystemInfo("提示", "栅栏密码中，明文中不能存在空格");
                    return;
                }
                textAreaOut.setText(RailFence.encode(textAreaIn.getText(), Integer.parseInt(textAreaKey.getText())));
            } catch (Exception e) {
                MsgBox.sendSystemInfo("提示", "编码失败，注意：Key 为大于1的数字");
            }
        });
        buttonRailFenceDe = new Items().EDbutton("解码");
        buttonRailFenceDe.setOnAction(event -> {
            try {
                if (textAreaIn.getText().trim().contains(" ")) {
                    MsgBox.sendSystemInfo("提示", "栅栏密码中，明文中不能存在空格");
                    return;
                }
                textAreaOut.setText(RailFence.decode(textAreaIn.getText(), Integer.parseInt(textAreaKey.getText())));
            } catch (Exception e) {
                MsgBox.sendSystemInfo("提示", "解码失败，注意：Key 为大于1的数字");
            }
        });
        buttonRailFenceNotSpace = new Button("一键去空格");
        buttonRailFenceNotSpace.setPrefWidth(190);
        buttonRailFenceNotSpace.setOnAction(event -> {
            textAreaIn.setText(textAreaIn.getText().replaceAll(" ", ""));
        });

        // Vigenere（维吉尼亚密码）
        buttonVigenereEn = new Items().EDbutton("编码");
        buttonVigenereEn.setOnAction(event -> {
            try {
                textAreaOut.setText(Vigenere.encode(textAreaIn.getText(), textAreaKey.getText()));
            } catch (Exception e) {
                MsgBox.sendSystemInfo("提示", "编码失败，看看 Key 有没有写");
            }
        });
        buttonVigenereDe = new Items().EDbutton("解码");
        buttonVigenereDe.setOnAction(event -> {
            try {
                textAreaOut.setText(Vigenere.decode(textAreaIn.getText(), textAreaKey.getText()));
            } catch (Exception e) {
                MsgBox.sendSystemInfo("提示", "解码失败，看看 Key 有没有写");
            }
        });

        // 分割线
        separator = new Separator();

        // func
        listsTitled = new ArrayList<>();
        vBoxButton = new VBox(10);
        vBoxButton.setPadding(new Insets(10, 20, 10, 10));
        vBoxButton.getChildren().addAll(
                new Items().item(buttonOpen, buttonClose, type, "root"), buttonReverse, separator,
                OAC.addTitled(aesBianMa, vBoxAes, buttonAesEn, buttonAesDe, "AES", type, "aes", listsTitled),
                OAC.addTitled(desBianMa, vBoxDes, buttonDesEn, buttonDesDe, "DES", type, "des", listsTitled),
                OAC.addTitled(desedeBianMa, vBoxDesede, buttonDesedeEn, buttonDesedeDe, "3DES", type, "3des", listsTitled),
                OAC.addTitled(rsaBianMa, vBoxRsa, buttonRsaEn, buttonRsaDe, "RSA", type, "rsa", listsTitled),
                OAC.addTitled(buttonJasyptEn, buttonJasyptDe, "Jasypt", type, "jasypt", listsTitled),
                OAC.addTitled(buttonBCryptEn, "BCrypt", type, "bcrypt", listsTitled),
                OAC.addTitled(buttonRailFenceNotSpace, buttonRailFenceEn, buttonRailFenceDe, "Rail Fence（栅栏密码）", type, "railFence", listsTitled),
                OAC.addTitled(buttonVigenereEn, buttonVigenereDe, "Vigenere（维吉尼亚密码）", type, "vigenere", listsTitled)
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
