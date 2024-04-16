package com.bl.bias.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.controlsfx.control.CheckComboBox;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public final class BIASCustomAssignmentsWindowController 
{
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	@FXML private CheckComboBox<String> customCategoryCheckComboBox1;
	@FXML private CheckComboBox<String> customCategoryCheckComboBox2;
	@FXML private TextField customCategoryTextField1;
	@FXML private TextField customCategoryTextField2;

	private static String customCategory1Name;
	private static String customCategory1NameQ;
	private static String customCategory2Name;
	private static String customCategory2NameQ;

	private static List<String> customCategory1TypesQ = new ArrayList<String>();
	private static List<String> customCategory2TypesQ = new ArrayList<String>();
	private static List<String> customCategory1Types = new ArrayList<String>();
	private static List<String> customCategory2Types = new ArrayList<String>();
	
	@FXML private void initialize() 
	{
		BIASPreprocessTrainCategoriesForRTCResultsAnalysis getPrelimData = new BIASPreprocessTrainCategoriesForRTCResultsAnalysis(BIASRTCResultsAnalysisPageController.getFiles());

		// User defined category 1
		if (customCategory1Name == null)
		{
			customCategory1Name = customCategoryTextField1.getText();
			customCategory1NameQ = customCategoryTextField1.getText();
		}

		// User defined category 2
		if (customCategory2Name == null)
		{
			customCategory2Name = customCategoryTextField2.getText();
			customCategory2NameQ = customCategoryTextField2.getText();
		}

		customCategoryTextField1.setText(customCategory1Name);
		customCategoryTextField2.setText(customCategory2Name);

		if (getPrelimData.returnAvailableTypes().size() > 0)
        {
        	customCategoryCheckComboBox1.getItems().addAll((getPrelimData.returnAvailableTypes()));
        	customCategoryCheckComboBox2.getItems().addAll((getPrelimData.returnAvailableTypes()));

        	// For each entry in custom assignments 1
        	Iterator<String> itr1 = customCategory1Types.iterator();
        	while (itr1.hasNext())
        	{
        		String currentListItem = itr1.next();
        		if (customCategoryCheckComboBox1.getItems().contains(currentListItem))
        		{
        			customCategoryCheckComboBox1.getCheckModel().check(currentListItem);
        		}
        	}

        	// For each entry in custom assignments 2
        	Iterator<String> itr2 = customCategory2Types.iterator();
        	while (itr2.hasNext())
        	{
        		String currentListItem = itr2.next();
        		if (customCategoryCheckComboBox2.getItems().contains(currentListItem))
        		{
        			customCategoryCheckComboBox2.getCheckModel().check(currentListItem);
        		}
        	}
        }
        else
        {
        	customCategoryCheckComboBox1.setDisable(true);
        	customCategoryCheckComboBox2.setDisable(true);
        }


		customCategoryCheckComboBox1.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			public void onChanged(ListChangeListener.Change<? extends String> c) 
			{
				customCategory1TypesQ = customCategoryCheckComboBox1.getCheckModel().getCheckedItems();
			}
		});

		customCategoryCheckComboBox2.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			public void onChanged(ListChangeListener.Change<? extends String> c) 
			{
				customCategory2TypesQ = customCategoryCheckComboBox2.getCheckModel().getCheckedItems();
			}
		});

		customCategoryTextField1.textProperty().addListener((obs, oldText, newText) -> {
			customCategory1NameQ = newText;
		});

		customCategoryTextField2.textProperty().addListener((obs, oldText, newText) -> {
			customCategory2NameQ = newText;
		});
	}

	@FXML private void handleOkButton(ActionEvent event) 
	{
		if ((customCategory1NameQ.trim().contentEquals("")) || (customCategory1NameQ == null) || (customCategory2NameQ.trim().contentEquals("")) || (customCategory2NameQ == null))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Valid category name(s) needed!");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(BIASLaunch.getFrameIconFile()).toString()));
			alert.show();
		}
		else if (customCategory1NameQ.contentEquals(customCategory2NameQ))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Category names are the same.  Rename at least one.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(BIASLaunch.getFrameIconFile()).toString()));
			alert.show();
		}
		else
		{
			customCategory1Name = customCategory1NameQ.trim();
			customCategory2Name = customCategory2NameQ.trim();
			customCategory1Name = customCategory1NameQ.trim();
			customCategory2Name = customCategory2NameQ.trim();
			customCategory1Types = customCategory1TypesQ;
			customCategory2Types = customCategory2TypesQ;

			Stage stage = (Stage) okButton.getScene().getWindow();
			stage.close();
		}
	}

	@FXML private void handleCancelButton(ActionEvent event) 
	{
		customCategory1NameQ = customCategory1Name;
		customCategory2NameQ = customCategory2Name;
		customCategory1TypesQ = customCategory1Types;
		customCategory2TypesQ = customCategory2Types;

		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public static String returnCustomCategory1()
	{
		if (customCategory1Name == null)
			return "";
		else
			return customCategory1Name;
	}

	public static String returnCustomCategory2()
	{
		if (customCategory2Name == null)
			return "";
		else
			return customCategory2Name;
	}

	public static List<String> returnCustomCategoryTypes1()
	{
		return customCategory1Types;
	}

	public static List<String> returnCustomCategoryTypes2()
	{
		return customCategory2Types;
	}

	public static void resetCustomAssignments()
	{
		customCategory1Name = null;
		customCategory1NameQ = null;
		customCategory2Name = null;
		customCategory2NameQ = null;

		customCategory1Types = new ArrayList<String>();
		customCategory1TypesQ = new ArrayList<String>();
		customCategory2Types = new ArrayList<String>();
		customCategory2TypesQ = new ArrayList<String>();
	}
}