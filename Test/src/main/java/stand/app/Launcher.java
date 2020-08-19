package stand.app;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import stand.app.config.StandConfig;
import stand.util.DataSender;

public class Launcher extends Application {

	private ApplicationContext applicationContext = new AnnotationConfigApplicationContext(StandConfig.class);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		SpringFxmlLoader loader = new SpringFxmlLoader();
		loader.setApplicationContext(applicationContext);
		Parent root = loader.load(getClass().getResource("/view/fxml/main.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Semicron Control");
		primaryStage.setMinHeight(755);
		primaryStage.setMinWidth(883);
		primaryStage.setMaxHeight(755);
		primaryStage.setMaxWidth(883);
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				DataSender dataSender = (DataSender) applicationContext.getBean("dataSender");
				try {
					dataSender.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
		primaryStage.show();
	}
}