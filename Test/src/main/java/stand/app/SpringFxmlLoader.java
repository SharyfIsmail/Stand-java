package stand.app;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SpringFxmlLoader {

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public Parent load(URL url) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(url);
			// setLocation необходим для корректной загрузки включенных шаблонов, таких как
			// productTable.fxml,
			// без этого получим исключение javafx.fxml.LoadException: Base location is
			// undefined.
//			loader.setLocation(SpringFxmlLoader.class.getResource("/view/fxml/main.fxml"));
			// setLocation необходим для корректной того чтобы loader видел наши кастомные
			// котнролы
			loader.setClassLoader(SpringFxmlLoader.class.getClassLoader());
			loader.setControllerFactory(applicationContext::getBean);
			return loader.load();
		} catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}
}