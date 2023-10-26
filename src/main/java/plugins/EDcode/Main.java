package plugins.EDcode;

import Plugin.Plugin;
import javafx.scene.Node;
import javafx.stage.Stage;
import plugins.EDcode.GUI.Start;


public class Main implements Plugin {
    @Override
    public String getName() {
        return "EDcode";
    }

    @Override
    public Node getContent(Stage primaryStage) {
        return new Start().show(primaryStage);
    }
}
