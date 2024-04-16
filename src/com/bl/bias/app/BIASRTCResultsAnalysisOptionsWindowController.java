package com.bl.bias.app;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.controlsfx.control.CheckComboBox;

import com.bl.bias.exception.ErrorShutdown;

public final class BIASRTCResultsAnalysisOptionsWindowController
{
	private static Boolean otp = true;
	private static Boolean velocity = true;
	private static Boolean trainMiles = true;
	private static Boolean delayMinutesPer100TrainMiles = true;
	private static Boolean trainCount = true;
	private static Boolean entireNetwork = true;
	private static Boolean elapsedRunTime = true;
	private static Boolean elapsedRunTimePerTrain = true;
	private static Boolean idealRunTime = true;
	private static Boolean trueDelay = true;
	private static Boolean byGroup = true;
	private static Boolean byType = true;
	private static Boolean delayMinutesPerTrain = true;
	private static Boolean selectedLines = false;
	private static Boolean selectedSubdivisions = false;
	private static Boolean considerAllFiles = true;
	private static Boolean considerFirstXFiles = false;
	
	private static SimpleBooleanProperty customAssignments = new SimpleBooleanProperty();
		
	private static BooleanBinding disableReturnToMainButton1;
	private static BooleanBinding disableReturnToMainButton2;
	private static BooleanBinding disableReturnToMainButton3;
	private static BooleanBinding disableReturnToMainButton4;
		
	private static List<String> listOfLines = new ArrayList<String>();
	private static List<String> listOfSubdivisions= new ArrayList<String>();
	
	private static Integer filesToConsiderCount;
	
	private static Stage customAssignmentWindow;
	
	@FXML private Button applyAndReturnToMainButton;
	@FXML private Button cancelAndReturnToMainButton;

	@FXML private Spinner<Integer> filesToProcessSpinner;

	@FXML private CheckBox otpCheckbox;
	@FXML private CheckBox velocityCheckbox;
	@FXML private CheckBox trainMilesCheckbox;
	@FXML private CheckBox delayMinutesPer100TrainMilesCheckbox;
	@FXML private CheckBox trainCountCheckbox;
	@FXML private CheckBox entireNetworkCheckbox;
	@FXML private CheckBox elapsedRunTimeCheckbox;
	@FXML private CheckBox elapsedRunTimePerTrainCheckbox;
	@FXML private CheckBox idealRunTimeCheckbox;
	@FXML private CheckBox trueDelayCheckbox;
	@FXML private CheckBox trainGroupCheckbox;
	@FXML private CheckBox trainTypeCheckbox;
	@FXML private CheckBox delayMinutesPerTrainCheckbox;
	@FXML private CheckBox selectedLinesCheckbox;
	@FXML private CheckBox selectedSubdivisionsCheckbox;
	@FXML private CheckBox customAssignmentCheckbox;
	
	@FXML private RadioButton considerAllFilesRadioButton;
	@FXML private RadioButton considerFirstXFilesRadioButton;

	@FXML private CheckComboBox<String> linesCheckComboBox;
	@FXML private CheckComboBox<String> subdivisionsCheckComboBox;
	
	@FXML private Label folderNameLabel;
	@FXML private Label messageLabel;
	@FXML private Label fromFilesLabel;
	@FXML private Label customAssignmentLabel;
	
	@FXML private void initialize() 
	{
		// Initially set checkboxes disabled
		selectedLinesCheckbox.setDisable(true);
        selectedSubdivisionsCheckbox.setDisable(true);
         
        // Check files to determine if checkComboBoxes should be enabled
     	BIASPreprocessLinesAndSubsForRTCResultsAnalysis getPrelimData = new BIASPreprocessLinesAndSubsForRTCResultsAnalysis(BIASRTCResultsAnalysisPageController.getFiles());
        int fileCount = getPrelimData.returnTotalFiles();
             
        // Set up custom assignment of types
        customAssignmentLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) 
                {
                   try 
                   {
                	   workWithCustomAssignments();
                   } 
                   catch (IOException e1) 
                   {
                	   ErrorShutdown.displayError(e1, this.getClass().getCanonicalName());
                   }
                }
            }
        });
        
        // Set up checkComboBoxes
        if (getPrelimData.returnAvailableLines().size() > 0)
        {
        	linesCheckComboBox.getItems().addAll((getPrelimData.returnAvailableLines()));
        }
        else
        {
        	linesCheckComboBox.setDisable(true);
        }
        
        if (getPrelimData.returnAvailableSubdivisions().size() > 0)
        {
        	subdivisionsCheckComboBox.getItems().addAll((getPrelimData.returnAvailableSubdivisions()));
        }
        else
        {
        	subdivisionsCheckComboBox.setDisable(true);
        }
        
        linesCheckComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) 
            {
            	listOfLines = linesCheckComboBox.getCheckModel().getCheckedItems();
            	if (listOfLines.size() == 0)
            	{
            		selectedLinesCheckbox.setDisable(true);
            		selectedLinesCheckbox.setSelected(false);
            		selectedLines = false;
            	}
            	else
            	{
            		selectedLinesCheckbox.setDisable(false);
            	}
            }
        });
       
        subdivisionsCheckComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) 
            {
            	listOfSubdivisions = subdivisionsCheckComboBox.getCheckModel().getCheckedItems();
            	if (listOfSubdivisions.size() == 0)
            	{
            		selectedSubdivisionsCheckbox.setDisable(true);
            		selectedSubdivisionsCheckbox.setSelected(false);
            		selectedSubdivisions = false;
            	}
            	else
            	{
            		selectedSubdivisionsCheckbox.setDisable(false);
            	}
            }
        });
                
        // Set up spinner for file count
        if (fileCount > 1)
        {
	        filesToConsiderCount = fileCount - 1;
	        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, fileCount - 1, fileCount - 1);
	        filesToProcessSpinner.setValueFactory(valueFactory);
	        filesToProcessSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
	        	@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					filesToConsiderCount = newValue;	
				}
			});
        }
        else
        {	
        	filesToProcessSpinner.setDisable(true);
        	considerFirstXFilesRadioButton.setDisable(true);
        	fromFilesLabel.setDisable(true);
        }
        
        // Set up Boolean Bindings
        disableReturnToMainButton1 = (trueDelayCheckbox.selectedProperty().not().and(idealRunTimeCheckbox.selectedProperty().not().and(trainMilesCheckbox.selectedProperty().not().and(trainCountCheckbox.selectedProperty().not().and(elapsedRunTimeCheckbox.selectedProperty().not().and(elapsedRunTimePerTrainCheckbox.selectedProperty().not().and(velocityCheckbox.selectedProperty().not().and(otpCheckbox.selectedProperty().not().and(delayMinutesPer100TrainMilesCheckbox.selectedProperty().not().and(delayMinutesPerTrainCheckbox.selectedProperty().not())))))))))); 
		disableReturnToMainButton2 = (entireNetworkCheckbox.selectedProperty().not().and(selectedLinesCheckbox.selectedProperty().not().and((selectedSubdivisionsCheckbox.selectedProperty().not()))));
		disableReturnToMainButton3 = (trainTypeCheckbox.selectedProperty().not().and(trainGroupCheckbox.selectedProperty().not()));
		disableReturnToMainButton4 = (customAssignmentCheckbox.selectedProperty().and(trainTypeCheckbox.selectedProperty().not()));
		
		applyAndReturnToMainButton.disableProperty().bind(disableReturnToMainButton1.or(disableReturnToMainButton2).or(disableReturnToMainButton3).or(disableReturnToMainButton4));
		
		// Set checkbox selected
		if (getOtp())
			otpCheckbox.setSelected(true);
		else
			otpCheckbox.setSelected(false);

		if (getVelocity())
			velocityCheckbox.setSelected(true);
		else
			velocityCheckbox.setSelected(false);

		if (getTrainMiles())
			trainMilesCheckbox.setSelected(true);
		else
			trainMilesCheckbox.setSelected(false);

		if (getDelayMinutesPer100TrainMiles())
			delayMinutesPer100TrainMilesCheckbox.setSelected(true);
		else
			delayMinutesPer100TrainMilesCheckbox.setSelected(false);

		if (getTrainCount())
			trainCountCheckbox.setSelected(true);
		else
			trainCountCheckbox.setSelected(false);

		if (getEntireNetwork())
			entireNetworkCheckbox.setSelected(true);
		else
			entireNetworkCheckbox.setSelected(false);

		if (getElapsedRunTime())
			elapsedRunTimeCheckbox.setSelected(true);
		else
			elapsedRunTimeCheckbox.setSelected(false);

		if (getElapsedRunTimePerTrain())
			elapsedRunTimePerTrainCheckbox.setSelected(true);
		else
			elapsedRunTimePerTrainCheckbox.setSelected(false);
		
		if (getIdealRunTime())
			idealRunTimeCheckbox.setSelected(true);
		else
			idealRunTimeCheckbox.setSelected(false);

		if (getTrueDelay())
			trueDelayCheckbox.setSelected(true);
		else
			trueDelayCheckbox.setSelected(false);

		if (getTrainGroup())
			trainGroupCheckbox.setSelected(true);
		else
			trainGroupCheckbox.setSelected(false);

		if (getTrainType())
			trainTypeCheckbox.setSelected(true);
		else
			trainTypeCheckbox.setSelected(false);

		if (getDelayMinutesPerTrain())
			delayMinutesPerTrainCheckbox.setSelected(true);
		else
			delayMinutesPerTrainCheckbox.setSelected(false);

		if (getSelectedLines())
			selectedLinesCheckbox.setSelected(true);
		else
			selectedLinesCheckbox.setSelected(false);
		
		if (getSelectedSubdivisions())
			selectedSubdivisionsCheckbox.setSelected(true);
		else
			selectedSubdivisionsCheckbox.setSelected(false);
		
		if (getCustomAssignments().getValue())
			customAssignmentCheckbox.setSelected(true);
		else
			customAssignmentCheckbox.setSelected(false);
		
		// Set custom assignments enabled if there are valid assignments
		if ((BIASCustomAssignmentsWindowController.returnCustomCategoryTypes1().size() > 0)  || (BIASCustomAssignmentsWindowController.returnCustomCategoryTypes2().size() > 0))
		{
			setCustomAssignmentCheckboxEnabled();
		}
		
		// Set Radio Button Selected
		if (getConsiderAllFiles())
			considerAllFilesRadioButton.setSelected(true);
		else
			considerFirstXFilesRadioButton.setSelected(true);
		
		// Set list and subdivision checkboxes
		if (linesCheckComboBox.getItems().size() == 0)
        	selectedLinesCheckbox.setDisable(true);
        
        if (subdivisionsCheckComboBox.getItems().size() == 0)
        	selectedSubdivisionsCheckbox.setDisable(true);
        
        // Set CheckComboBox Entries
		if (listOfLines.size() > 0)
		{
			// For each entry is listOfLines
			Iterator<String> itr1 = listOfLines.iterator();
			while (itr1.hasNext())
			{
				String currentListItem = itr1.next();
				if (linesCheckComboBox.getItems().contains(currentListItem))
				{
					linesCheckComboBox.getCheckModel().check(currentListItem);
					selectedLinesCheckbox.setDisable(false);
				}
			}
		}
		
		if (listOfSubdivisions.size() > 0)
		{
			// For each entry is listOfSubdivisions
			Iterator<String> itr1 = listOfSubdivisions.iterator();
			while (itr1.hasNext())
			{
				String currentListItem = itr1.next();
				if (subdivisionsCheckComboBox.getItems().contains(currentListItem))
				{
					subdivisionsCheckComboBox.getCheckModel().check(currentListItem);
					selectedSubdivisionsCheckbox.setDisable(false);
				}
			}
		}   
		
		// Set spinner value
		if (getFilesToConsiderCount() != null)
			filesToProcessSpinner.getValueFactory().setValue(getFilesToConsiderCount());     
	}

	@FXML private void handleApplyAndReturnToMainButton(ActionEvent event) 
	{
		BIASRTCResultsAnalysisPageController.closeTheExtractConfigWindow();
	}

	@FXML private void handleOtpCheckbox(ActionEvent event) 
	{
		if (otp == true)
			otp = false;
		else
			otp = true;
	}

	@FXML private void handleVelocityCheckbox(ActionEvent event) 
	{
		if (velocity == true)
			velocity = false;
		else
			velocity = true;
	}

	@FXML private void handleTrainMilesCheckbox(ActionEvent event) 
	{
		if (trainMiles == true)
			trainMiles = false;
		else
			trainMiles = true;
	}

	@FXML private void handleDelayMinutesPer100TrainMilesCheckbox(ActionEvent event) 
	{
		if (delayMinutesPer100TrainMiles == true)
			delayMinutesPer100TrainMiles = false;
		else
			delayMinutesPer100TrainMiles = true;
	}

	@FXML private void handleTrainCountCheckbox(ActionEvent event) 
	{
		if (trainCount == true)
			trainCount = false;
		else
			trainCount = true;
	}

	@FXML private void handleEntireNetworkCheckbox(ActionEvent event) 
	{
		if (entireNetwork == true)
			entireNetwork = false;
		else
			entireNetwork = true;
	}

	@FXML private void handleElapsedRunTimeCheckbox(ActionEvent event) 
	{
		if (elapsedRunTime == true)
			elapsedRunTime = false;
		else
			elapsedRunTime = true;
	}
	
	@FXML private void handleElapsedRunTimePerTrainCheckbox(ActionEvent event) 
	{
		if (elapsedRunTimePerTrain == true)
			elapsedRunTimePerTrain = false;
		else
			elapsedRunTimePerTrain = true;
	}
	
	@FXML private void handleIdealRunTimeCheckbox(ActionEvent event) 
	{
		if (idealRunTime == true)
			idealRunTime = false;
		else
			idealRunTime = true;
	}

	@FXML private void handleTrueDelayCheckbox(ActionEvent event) 
	{
		if (trueDelay == true)
			trueDelay = false;
		else
			trueDelay = true;
	}

	@FXML private void handleTrainGroupCheckbox(ActionEvent event) 
	{
		if (byGroup == true)
			byGroup = false;
		else
			byGroup = true;
	}

	@FXML private void handleTrainTypeCheckbox(ActionEvent event) 
	{
		if (byType == true)
			byType = false;
		else
			byType = true;
	}

	@FXML private void handleCustomAssignmentCheckbox(ActionEvent event) 
	{
		if (customAssignments.get() == true)
			customAssignments.set(false);
		else
			customAssignments.set(true);
	}

	@FXML private void handleDelayMinutesPerTrainCheckbox(ActionEvent event) 
	{
		if (delayMinutesPerTrain == true)
			delayMinutesPerTrain = false;
		else
			delayMinutesPerTrain = true;
	}

	@FXML private void handleSelectedLinesCheckbox(ActionEvent event)  
	{
		if (selectedLines == true)
			selectedLines = false;
		else
			selectedLines = true;
	}
	
	@FXML private void handleSelectedSubdivisionsCheckbox(ActionEvent event)  
	{
		if (selectedSubdivisions == true)
			selectedSubdivisions = false;
		else
			selectedSubdivisions = true;
	}

	@FXML private void handleConsiderAllFilesRadioButton(ActionEvent event) 
	{
		considerAllFiles = true;
		considerFirstXFiles = false;
	}

	@FXML private void handleConsiderFirstXFilesRadioButton(ActionEvent event) 
	{
		considerAllFiles = false;
		considerFirstXFiles = true;
	}

	@FXML private void setCustomAssignmentCheckboxEnabled()
	{
		customAssignmentCheckbox.setDisable(false);
	}
	
	@FXML private void setCustomAssignmentCheckboxDisabled()
	{
		customAssignmentCheckbox.setDisable(true);
	}
	
	public static Boolean getOtp()
	{
		return otp;
	}

	public static Boolean getVelocity()
	{
		return velocity;
	}

	public static Boolean getTrainMiles()
	{
		return trainMiles;
	}

	public static Boolean getDelayMinutesPer100TrainMiles()
	{
		return delayMinutesPer100TrainMiles;
	}

	public static Boolean getTrainCount()
	{
		return trainCount;
	}

	public static Boolean getEntireNetwork()
	{
		return entireNetwork;
	}

	public static Boolean getElapsedRunTime()
	{
		return elapsedRunTime;
	}

	public static Boolean getElapsedRunTimePerTrain()
	{
		return elapsedRunTimePerTrain;
	}

	public static Boolean getIdealRunTime()
	{
		return idealRunTime;
	}

	public static Boolean getTrueDelay() 
	{
		return trueDelay;
	}

	public static Boolean getTrainGroup()
	{
		return byGroup;
	}

	public static Boolean getTrainType()
	{
		return byType;
	}

	public static Boolean getDelayMinutesPerTrain() 
	{
		return delayMinutesPerTrain;
	}
	
	public static Boolean getSelectedLines() 
	{
		return selectedLines;
	}
	
	public static Boolean getSelectedSubdivisions() 
	{
		return selectedSubdivisions;
	}
	
	public static SimpleBooleanProperty getCustomAssignments() 
	{
		return customAssignments;
	}
	
	public static void setGetCustomAssignmentsToFalse() 
	{
		customAssignments.set(false);
	}
	
	public static List<String> getListOfLines()
	{
		return listOfLines;
	}
	
	public static List<String> getListOfSubdivisions()
	{
		return listOfSubdivisions;
	}
	
	public static Boolean getConsiderAllFiles()
	{
		return considerAllFiles;
	}
	
	public static Boolean getConsiderFirstXFiles()
	{
		return considerFirstXFiles;
	}
	
	public static Integer getFilesToConsiderCount()
	{
		return filesToConsiderCount;
	}
	
	// Reset parameters for new file loaded
	public static void resetParametersForNewFile()
	{
		filesToConsiderCount = null;
		selectedSubdivisions = false;
		selectedLines = false;
		considerAllFiles = true;
		considerFirstXFiles = false;
		otp = true;
		velocity = true;
		trainMiles = true;
		delayMinutesPer100TrainMiles = true;
		trainCount = true;
		entireNetwork = true;
		elapsedRunTime = true;
		elapsedRunTimePerTrain = true;
		idealRunTime = true;
		trueDelay = true;
		byGroup = true;
		byType = true;
		delayMinutesPerTrain = true;
		selectedLines = false;
		selectedSubdivisions = false;
		considerAllFiles = true;
		considerFirstXFiles = false;
		customAssignments.set(false);
		listOfLines = new ArrayList<String>();
		listOfSubdivisions = new ArrayList<String>();
	}
		
	private void workWithCustomAssignments() throws IOException
	{

		Parent root2 = FXMLLoader.load(getClass().getResource("BIASCustomAssignmentsWindow.fxml"));
		Scene secondScene = new Scene(root2, 520, 320);
		customAssignmentWindow = new Stage();
		customAssignmentWindow.setResizable(false);
		customAssignmentWindow.setTitle("Select Custom Assignments");
		customAssignmentWindow.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
		customAssignmentWindow.setScene(secondScene);
		customAssignmentWindow.initModality(Modality.APPLICATION_MODAL);
		
		customAssignmentWindow.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if (KeyCode.ESCAPE == event.getCode())
			{
				closeWindow();
			}
		});
		
		customAssignmentWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent e) {
		    	closeWindow();
		    }
		});
		
		customAssignmentWindow.setOnHiding(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent e) {
		    	closeWindow();
		    }
		});
		        				
		customAssignmentWindow.show();
	}
	
	public void closeWindow()
	{
		customAssignmentWindow.close();
		
		if ((BIASCustomAssignmentsWindowController.returnCustomCategoryTypes1().size() > 0)  || (BIASCustomAssignmentsWindowController.returnCustomCategoryTypes2().size() > 0))
		{
			setCustomAssignmentCheckboxEnabled();
			customAssignmentCheckbox.setSelected(true);
			customAssignments.set(true);
		}
		else
		{
			setCustomAssignmentCheckboxDisabled();
			customAssignmentCheckbox.setSelected(false);
			customAssignments.set(false);
		}
	}
}