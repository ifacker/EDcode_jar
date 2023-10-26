package plugins.EDcode.GUI;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import plugins.EDcode.Config.GlobalConfig;
import plugins.EDcode.DataType.Advanced;
import plugins.EDcode.DataType.Config;
import plugins.EDcode.Util.FileIO;

import java.io.File;

public class Start {
    VBox root;
    TabPane tabPane;
    Tab tabRoot;
    Tab tabAdvanced;
    Tab tabAbout;

    public Node show(Stage primaryStage) {
        initConfig();

        tabRoot = new Tab("基础");
        tabRoot.setClosable(false);
        tabRoot.setContent(new RootPage().show());

        tabAdvanced = new Tab("进阶");
        tabAdvanced.setClosable(false);
        tabAdvanced.setContent(new AdvancedPage().show());

        tabAbout = new Tab("关于");
        tabAbout.setClosable(false);
        tabAbout.setContent(new AboutPage().show());

        tabPane = new TabPane();
        tabPane.setTabMinWidth(50);
        tabPane.getTabs().addAll(tabRoot, tabAdvanced, tabAbout);

        root = new VBox(10);
        root.getChildren().addAll(tabPane);
        return root;
    }

    private void initConfig(){
        GlobalConfig.CONFIG = new Config();
        if (new File(GlobalConfig.CONFIG_PATH).exists()) {
            GlobalConfig.CONFIG = (Config) FileIO.readObject(GlobalConfig.CONFIG_PATH, Config.class);
        } else {
            Advanced base = new Advanced();
            base.setStatus(false);
            GlobalConfig.CONFIG.getAdvanceds().put("base", base);
            Advanced adva = new Advanced();
            adva.setStatus(false);
            GlobalConfig.CONFIG.getAdvanceds().put("adva", adva);
            FileIO.saveObject(GlobalConfig.CONFIG_PATH, GlobalConfig.CONFIG);
        }
    }
}
