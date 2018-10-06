/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Class that cares about all Screen functions
 *
 * @author bruno
 */
public class ScreenUtil {

	/**
	 * Method that opens a new screen
	 *
	 * @param oldStage   is the old window, that will be closed
	 * @param sSceneName is the scene's name, that will be loaded
	 * @param isModal    True or false about the modal property
	 */
	public void openNewWindow(Stage oldStage, String sSceneName, boolean isModal) {

		Stage newStage = new Stage();
		Parent root = loadScene(sSceneName);

		Scene scene = new Scene(root);
		scene.getStylesheets().add("/style/StyleApp.css");
		newStage.setScene(scene);
		if (isModal) {
			newStage.initStyle(StageStyle.UTILITY);
			newStage.setTitle("Configuração");
			newStage.initOwner(oldStage);
			newStage.initModality(Modality.APPLICATION_MODAL);
		}
		newStage.showAndWait();
		if (!isModal) {
			closeOldStage(oldStage, newStage);
		}
	}

	/**
	 * Method that opens a new screen, thanks to 'hashMapValues' we can now pass
	 * objects from one screen to another
	 *
	 * @param oldStage      is the old window, that will be closed
	 * @param sSceneName    is the scene's name, that will be loaded
	 * @param isModal       True or false about the modal property
	 * @param hashMapValues Its filled with the objects that we need to pass to the
	 *                      new window
	 */
	public void openNewWindow(Stage oldStage, String sSceneName, boolean isModal,
			HashMap<String, Object> hashMapValues) {
		Stage newStage = new Stage();

		Parent root = loadScene(sSceneName);
		Scene scene = new Scene(root);
		scene.getStylesheets().add("/style/StyleApp.css");
		newStage.setScene(scene);
		if (isModal) {
			newStage.initStyle(StageStyle.UTILITY);
			newStage.setTitle("Configuração");
			newStage.initOwner(oldStage);
			newStage.initModality(Modality.APPLICATION_MODAL);
		}
		notifyAllListeners(sSceneName, hashMapValues);
		newStage.showAndWait();
		if (!isModal) {
			closeOldStage(oldStage, newStage);

		}
	}

	private Parent loadScene(String sSceneName) {
		Parent root = null;

		try {
			System.out.println("Scene>>>> " + sSceneName);
			URL url = new File(getClass().getResource("/fxml/" + sSceneName + ".fxml").getPath()).toURI().toURL();
			root = FXMLLoader.load(url);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return root;
	}

	private void closeOldStage(Stage oldStage, Stage newStage) {
		if (oldStage != null) {
			oldStage.close();
		}
	}

	public static void numericField(final TextField textField) {
		textField.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					char ch = textField.getText().charAt(oldValue.intValue());
					if (!(ch >= '0' && ch <= '9')) {
						textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
					}
				}
			}
		});
	}

	public static void addTextLimiter(final TextField tf, final int maxLength) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
				if (tf.getText().length() > maxLength) {
					String s = tf.getText().substring(0, maxLength);
					tf.setText(s);
				}
			}
		});
	}

	private static ArrayList<OnChangeScreen> listeners = new ArrayList<OnChangeScreen>();

	public interface OnChangeScreen {
		void onScreenChanged(String newScreen, HashMap<String, Object> hmap);
	}

	public static void addOnChangeScreenListener(OnChangeScreen newListener) {
		listeners.add(newListener);
	}

	private void notifyAllListeners(String newScreen, HashMap<String, Object> hmap) {
		for (OnChangeScreen l : listeners) {
			l.onScreenChanged(newScreen, hmap);
		}
	}
}
