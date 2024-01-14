package com.bl.bias.app;

import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class BIASRecoveryRateAnalysisConfigController 
{
	private static Preferences prefs;

	private static String trainGroups;
	private static String setANodePairs;
	private static String setALowRecoveryRate;
	private static String setBNodePairs;
	private static String setBLowRecoveryRate;

	private static Integer setASchedulingImprecisionOffset;
	private static Integer setBSchedulingImprecisionOffset;
	private static Integer defaultSchedulingImprecisionOffset = 0;

	private static ObservableList<String> lowRecoveryRates =  FXCollections.observableArrayList("0%", "5%", "10%", "15%", "20%");
	private static String defaultLowRecoveryRate = "15%";

	@FXML TextField group1TextField;
	@FXML TextField group2TextField;
	@FXML TextField group3TextField;
	@FXML TextField group4TextField;
	@FXML TextField group5TextField;

	@FXML TextField nodePair1FromTextFieldA;
	@FXML TextField nodePair1ToTextFieldA;
	@FXML TextField nodePair2FromTextFieldA;
	@FXML TextField nodePair2ToTextFieldA;
	@FXML TextField nodePair3FromTextFieldA;
	@FXML TextField nodePair3ToTextFieldA;
	@FXML TextField nodePair4FromTextFieldA;
	@FXML TextField nodePair4ToTextFieldA;
	@FXML TextField nodePair5FromTextFieldA;
	@FXML TextField nodePair5ToTextFieldA;
	@FXML TextField nodePair6FromTextFieldA;
	@FXML TextField nodePair6ToTextFieldA;
	@FXML TextField nodePair7FromTextFieldA;
	@FXML TextField nodePair7ToTextFieldA;
	@FXML TextField nodePair8FromTextFieldA;
	@FXML TextField nodePair8ToTextFieldA;
	@FXML TextField nodePair9FromTextFieldA;
	@FXML TextField nodePair9ToTextFieldA;
	@FXML TextField nodePair10FromTextFieldA;
	@FXML TextField nodePair10ToTextFieldA;
	@FXML TextField nodePair11FromTextFieldA;
	@FXML TextField nodePair11ToTextFieldA;
	@FXML TextField nodePair12FromTextFieldA;
	@FXML TextField nodePair12ToTextFieldA;
	@FXML TextField nodePair13FromTextFieldA;
	@FXML TextField nodePair13ToTextFieldA;
	@FXML TextField nodePair14FromTextFieldA;
	@FXML TextField nodePair14ToTextFieldA;
	@FXML TextField nodePair15FromTextFieldA;
	@FXML TextField nodePair15ToTextFieldA;

	@FXML TextField nodePair1FromTextFieldB;
	@FXML TextField nodePair1ToTextFieldB;
	@FXML TextField nodePair2FromTextFieldB;
	@FXML TextField nodePair2ToTextFieldB;
	@FXML TextField nodePair3FromTextFieldB;
	@FXML TextField nodePair3ToTextFieldB;
	@FXML TextField nodePair4FromTextFieldB;
	@FXML TextField nodePair4ToTextFieldB;
	@FXML TextField nodePair5FromTextFieldB;
	@FXML TextField nodePair5ToTextFieldB;
	@FXML TextField nodePair6FromTextFieldB;
	@FXML TextField nodePair6ToTextFieldB;
	@FXML TextField nodePair7FromTextFieldB;
	@FXML TextField nodePair7ToTextFieldB;
	@FXML TextField nodePair8FromTextFieldB;
	@FXML TextField nodePair8ToTextFieldB;
	@FXML TextField nodePair9FromTextFieldB;
	@FXML TextField nodePair9ToTextFieldB;
	@FXML TextField nodePair10FromTextFieldB;
	@FXML TextField nodePair10ToTextFieldB;
	@FXML TextField nodePair11FromTextFieldB;
	@FXML TextField nodePair11ToTextFieldB;
	@FXML TextField nodePair12FromTextFieldB;
	@FXML TextField nodePair12ToTextFieldB;
	@FXML TextField nodePair13FromTextFieldB;
	@FXML TextField nodePair13ToTextFieldB;
	@FXML TextField nodePair14FromTextFieldB;
	@FXML TextField nodePair14ToTextFieldB;
	@FXML TextField nodePair15FromTextFieldB;
	@FXML TextField nodePair15ToTextFieldB;

	@FXML Button updateGroupsButton;
	@FXML Button updateSetANodesButton;
	@FXML Button updateSetBNodesButton;

	@FXML ComboBox<String> setALowRecoveryRateComboBox;
	@FXML ComboBox<String> setBLowRecoveryRateComboBox;

	@FXML RadioButton setANoSchedulingImprecisionRadioButton;
	@FXML RadioButton setAThirtySecondImprecisionRadioButton;
	@FXML RadioButton setAOneMinuteImprecisionRadioButton;

	@FXML RadioButton setBNoSchedulingImprecisionRadioButton;
	@FXML RadioButton setBThirtySecondImprecisionRadioButton;
	@FXML RadioButton setBOneMinuteImprecisionRadioButton;

	@FXML private void initialize()
	{
		// Set up prefs
		prefs = Preferences.userRoot().node("BIAS");

		// See if preferences are stored for groups
		if ((prefs.get("rr_trainGroups", "") != null) && (prefs.get("rr_trainGroups", "") != ""))
		{
			trainGroups = prefs.get("rr_trainGroups", "");
			String trainGroup[] = trainGroups.split(",");

			for (int i = 0; i < trainGroup.length; i++)
			{
				if (i == 0)
					group1TextField.setText(trainGroup[0]);
				else if (i == 1)
					group2TextField.setText(trainGroup[1]);
				else if (i == 2)
					group3TextField.setText(trainGroup[2]);
				else if (i == 3)
					group4TextField.setText(trainGroup[3]);
				else if (i == 4)
					group5TextField.setText(trainGroup[4]);
			}
		}

		// See if preferences are stored for Set A node pairs
		if ((prefs.get("rr_setANodePairs", "") != null) && (prefs.get("rr_setANodePairs", "") != ""))
		{
			setANodePairs = prefs.get("rr_setANodePairs", "");
			String nodePairA[] = setANodePairs.split(",");

			for (int i = 0; i < nodePairA.length; i++)
			{
				if (i == 0)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair1FromTextFieldA.setText(fromTo[0]);
					nodePair1ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 1)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair2FromTextFieldA.setText(fromTo[0]);
					nodePair2ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 2)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair3FromTextFieldA.setText(fromTo[0]);
					nodePair3ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 3)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair4FromTextFieldA.setText(fromTo[0]);
					nodePair4ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 4)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair5FromTextFieldA.setText(fromTo[0]);
					nodePair5ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 5)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair6FromTextFieldA.setText(fromTo[0]);
					nodePair6ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 6)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair7FromTextFieldA.setText(fromTo[0]);
					nodePair7ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 7)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair8FromTextFieldA.setText(fromTo[0]);
					nodePair8ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 8)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair9FromTextFieldA.setText(fromTo[0]);
					nodePair9ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 9)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair10FromTextFieldA.setText(fromTo[0]);
					nodePair10ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 10)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair11FromTextFieldA.setText(fromTo[0]);
					nodePair11ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 11)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair12FromTextFieldA.setText(fromTo[0]);
					nodePair12ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 12)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair13FromTextFieldA.setText(fromTo[0]);
					nodePair13ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 13)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair14FromTextFieldA.setText(fromTo[0]);
					nodePair14ToTextFieldA.setText(fromTo[1]);
				}
				else if (i == 14)
				{
					String[] fromTo = nodePairA[i].split(":"); 
					nodePair15FromTextFieldA.setText(fromTo[0]);
					nodePair15ToTextFieldA.setText(fromTo[1]);
				}
			}
		}

		// See if preferences are stored for Set B node pairs
		if ((prefs.get("rr_setBNodePairs", "") != null) && (prefs.get("rr_setBNodePairs", "") != ""))
		{
			setBNodePairs = prefs.get("rr_setBNodePairs", "");
			String nodePairB[] = setBNodePairs.split(",");

			for (int i = 0; i < nodePairB.length; i++)
			{
				if (i == 0)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair1FromTextFieldB.setText(fromTo[0]);
					nodePair1ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 1)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair2FromTextFieldB.setText(fromTo[0]);
					nodePair2ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 2)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair3FromTextFieldB.setText(fromTo[0]);
					nodePair3ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 3)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair4FromTextFieldB.setText(fromTo[0]);
					nodePair4ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 4)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair5FromTextFieldB.setText(fromTo[0]);
					nodePair5ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 5)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair6FromTextFieldB.setText(fromTo[0]);
					nodePair6ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 6)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair7FromTextFieldB.setText(fromTo[0]);
					nodePair7ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 7)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair8FromTextFieldB.setText(fromTo[0]);
					nodePair8ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 8)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair9FromTextFieldB.setText(fromTo[0]);
					nodePair9ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 9)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair10FromTextFieldB.setText(fromTo[0]);
					nodePair10ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 10)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair11FromTextFieldB.setText(fromTo[0]);
					nodePair11ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 11)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair12FromTextFieldB.setText(fromTo[0]);
					nodePair12ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 12)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair13FromTextFieldB.setText(fromTo[0]);
					nodePair13ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 13)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair14FromTextFieldB.setText(fromTo[0]);
					nodePair14ToTextFieldB.setText(fromTo[1]);
				}
				else if (i == 14)
				{
					String[] fromTo = nodePairB[i].split(":"); 
					nodePair15FromTextFieldB.setText(fromTo[0]);
					nodePair15ToTextFieldB.setText(fromTo[1]);
				}
			}
		}

		// See if preference is stored for scheduling imprecision offset Set A
		if (prefs.getInt("rr_setASchedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 0)
		{
			setASchedulingImprecisionOffset = 0;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_setASchedulingImprecisionOffset", 0);
			setANoSchedulingImprecisionRadioButton.setSelected(true);
		}
		else if (prefs.getInt("rr_setASchedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 30)
		{
			setASchedulingImprecisionOffset = 30;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_setASchedulingImprecisionOffset", 30);
			setAThirtySecondImprecisionRadioButton.setSelected(true);
		}
		else if (prefs.getInt("rr_setASchedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 60)
		{
			setASchedulingImprecisionOffset = 60;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_setASchedulingImprecisionOffset", 60);
			setAOneMinuteImprecisionRadioButton.setSelected(true);
		}

		// See if preference is stored for scheduling imprecision offset Set B
		if (prefs.getInt("rr_setBSchedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 0)
		{
			setBSchedulingImprecisionOffset = 0;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_setBSchedulingImprecisionOffset", 0);
			setBNoSchedulingImprecisionRadioButton.setSelected(true);
		}
		else if (prefs.getInt("rr_setBSchedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 30)
		{
			setBSchedulingImprecisionOffset = 30;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_setBSchedulingImprecisionOffset", 30);
			setBThirtySecondImprecisionRadioButton.setSelected(true);
		}
		else if (prefs.getInt("rr_setBSchedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 60)
		{
			setBSchedulingImprecisionOffset = 60;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_setBSchedulingImprecisionOffset", 60);
			setBOneMinuteImprecisionRadioButton.setSelected(true);
		}

		// See if preference is stored for low recovery rate Set A
		setALowRecoveryRateComboBox.setItems(lowRecoveryRates);
		boolean lowRecoveryRateExistsA = prefs.get("rr_setALowRecoveryRate", null) != null;
		if (lowRecoveryRateExistsA)
		{
			setALowRecoveryRate = prefs.get("rr_setALowRecoveryRate", defaultLowRecoveryRate);
			setALowRecoveryRateComboBox.getSelectionModel().select(setALowRecoveryRate);
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("rr_setALowRecoveryRate", defaultLowRecoveryRate);
			}
			setALowRecoveryRate = prefs.get("rr_setALowRecoveryRate", defaultLowRecoveryRate);
			setALowRecoveryRateComboBox.getSelectionModel().select(defaultLowRecoveryRate);
		}	

		// See if preference is stored for low recovery rate Set B
		setBLowRecoveryRateComboBox.setItems(lowRecoveryRates);
		boolean lowRecoveryRateExistsB = prefs.get("rr_setBLowRecoveryRate", null) != null;
		if (lowRecoveryRateExistsB)
		{
			setBLowRecoveryRate = prefs.get("rr_setBLowRecoveryRate", defaultLowRecoveryRate);
			setBLowRecoveryRateComboBox.getSelectionModel().select(setBLowRecoveryRate);
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("rr_setBLowRecoveryRate", defaultLowRecoveryRate);
			}
			setBLowRecoveryRate = prefs.get("rr_setBLowRecoveryRate", defaultLowRecoveryRate);
			setBLowRecoveryRateComboBox.getSelectionModel().select(defaultLowRecoveryRate);
		}	
	}

	@FXML private void handleGroup1TextField()
	{
		if (group1TextField.getText().trim().length() == 3)
		{
			String origText = group1TextField.getText().trim();
			group1TextField.setText(origText.toUpperCase());
		}
		else
		{
			group1TextField.setText("");
		}			
	}

	@FXML private void handleTextChangedGroup1TextField()
	{
		group1TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateGroupsButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleGroup2TextField()
	{
		if (group2TextField.getText().trim().length() == 3)
		{
			String origText = group2TextField.getText().trim();
			group2TextField.setText(origText.toUpperCase());
		}
		else
		{
			group2TextField.setText("");
		}			
	}

	@FXML private void handleTextChangedGroup2TextField()
	{
		group2TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateGroupsButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleGroup3TextField()
	{
		if (group3TextField.getText().trim().length() == 3)
		{
			String origText = group3TextField.getText().trim();
			group3TextField.setText(origText.toUpperCase());
		}
		else
		{
			group3TextField.setText("");
		}			
	}

	@FXML private void handleTextChangedGroup3TextField()
	{
		group3TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateGroupsButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleGroup4TextField()
	{
		if (group4TextField.getText().trim().length() == 3)
		{
			String origText = group4TextField.getText().trim();
			group4TextField.setText(origText.toUpperCase());
		}
		else
		{
			group4TextField.setText("");
		}			
	}

	@FXML private void handleTextChangedGroup4TextField()
	{
		group4TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateGroupsButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleGroup5TextField()
	{
		if (group5TextField.getText().trim().length() == 3)
		{
			String origText = group5TextField.getText().trim();
			group5TextField.setText(origText.toUpperCase());
		}
		else
		{
			group5TextField.setText("");
		}			
	}

	@FXML private void handleTextChangedGroup5TextField()
	{
		group5TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateGroupsButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 1 of 15 set A
	@FXML private void handleNodePairFrom1TextFieldA()
	{
		String origText = nodePair1FromTextFieldA.getText().trim();
		nodePair1FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom1TextFieldA()
	{
		nodePair1FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo1TextFieldA()
	{
		String origText = nodePair1ToTextFieldA.getText().trim();
		nodePair1ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo1TextFieldA()
	{
		nodePair1ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 2 of 15 set A
	@FXML private void handleNodePairFrom2TextFieldA()
	{
		String origText = nodePair2FromTextFieldA.getText().trim();
		nodePair2FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom2TextFieldA()
	{
		nodePair2FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo2TextFieldA()
	{
		String origText = nodePair2ToTextFieldA.getText().trim();
		nodePair2ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo2TextFieldA()
	{
		nodePair2ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 3 of 15 set A
	@FXML private void handleNodePairFrom3TextFieldA()
	{
		String origText = nodePair3FromTextFieldA.getText().trim();
		nodePair3FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom3TextFieldA()
	{
		nodePair3FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo3TextFieldA()
	{
		String origText = nodePair3ToTextFieldA.getText().trim();
		nodePair3ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo3TextFieldA()
	{
		nodePair3ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 4 of 15 set A
	@FXML private void handleNodePairFrom4TextFieldA()
	{
		String origText = nodePair4FromTextFieldA.getText().trim();
		nodePair4FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom4TextFieldA()
	{
		nodePair4FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo4TextFieldA()
	{
		String origText = nodePair4ToTextFieldA.getText().trim();
		nodePair4ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo4TextFieldA()
	{
		nodePair4ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 5 of 15 set A
	@FXML private void handleNodePairFrom5TextFieldA()
	{
		String origText = nodePair5FromTextFieldA.getText().trim();
		nodePair5FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom5TextFieldA()
	{
		nodePair5FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo5TextFieldA()
	{
		String origText = nodePair5ToTextFieldA.getText().trim();
		nodePair5ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo5TextFieldA()
	{
		nodePair5ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 6 of 15 set A
	@FXML private void handleNodePairFrom6TextFieldA()
	{
		String origText = nodePair6FromTextFieldA.getText().trim();
		nodePair6FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom6TextFieldA()
	{
		nodePair6FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo6TextFieldA()
	{
		String origText = nodePair6ToTextFieldA.getText().trim();
		nodePair6ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo6TextFieldA()
	{
		nodePair6ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 7 of 15 set A
	@FXML private void handleNodePairFrom7TextFieldA()
	{
		String origText = nodePair7FromTextFieldA.getText().trim();
		nodePair7FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom7TextFieldA()
	{
		nodePair7FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo7TextFieldA()
	{
		String origText = nodePair7ToTextFieldA.getText().trim();
		nodePair7ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo7TextFieldA()
	{
		nodePair7ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 8 of 15 set A
	@FXML private void handleNodePairFrom8TextFieldA()
	{
		String origText = nodePair8FromTextFieldA.getText().trim();
		nodePair8FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom8TextFieldA()
	{
		nodePair8FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo8TextFieldA()
	{
		String origText = nodePair8ToTextFieldA.getText().trim();
		nodePair8ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo8TextFieldA()
	{
		nodePair8ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 9 of 15 set A
	@FXML private void handleNodePairFrom9TextFieldA()
	{
		String origText = nodePair9FromTextFieldA.getText().trim();
		nodePair9FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom9TextFieldA()
	{
		nodePair9FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo9TextFieldA()
	{
		String origText = nodePair9ToTextFieldA.getText().trim();
		nodePair9ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo9TextFieldA()
	{
		nodePair9ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 10 of 15 set A
	@FXML private void handleNodePairFrom10TextFieldA()
	{
		String origText = nodePair10FromTextFieldA.getText().trim();
		nodePair10FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom10TextFieldA()
	{
		nodePair10FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo10TextFieldA()
	{
		String origText = nodePair10ToTextFieldA.getText().trim();
		nodePair10ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo10TextFieldA()
	{
		nodePair10ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 11 of 15 set A
	@FXML private void handleNodePairFrom11TextFieldA()
	{
		String origText = nodePair11FromTextFieldA.getText().trim();
		nodePair11FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom11TextFieldA()
	{
		nodePair11FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo11TextFieldA()
	{
		String origText = nodePair11ToTextFieldA.getText().trim();
		nodePair11ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo11TextFieldA()
	{
		nodePair11ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 12 of 15 set A
	@FXML private void handleNodePairFrom12TextFieldA()
	{
		String origText = nodePair12FromTextFieldA.getText().trim();
		nodePair12FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom12TextFieldA()
	{
		nodePair12FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo12TextFieldA()
	{
		String origText = nodePair11ToTextFieldA.getText().trim();
		nodePair12ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo12TextFieldA()
	{
		nodePair12ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 13 of 15 set A
	@FXML private void handleNodePairFrom13TextFieldA()
	{
		String origText = nodePair13FromTextFieldA.getText().trim();
		nodePair13FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom13TextFieldA()
	{
		nodePair13FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo13TextFieldA()
	{
		String origText = nodePair13ToTextFieldA.getText().trim();
		nodePair13ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo13TextFieldA()
	{
		nodePair13ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 14 of 15 set A
	@FXML private void handleNodePairFrom14TextFieldA()
	{
		String origText = nodePair14FromTextFieldA.getText().trim();
		nodePair14FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom14TextFieldA()
	{
		nodePair14FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo14TextFieldA()
	{
		String origText = nodePair14ToTextFieldA.getText().trim();
		nodePair14ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo14TextFieldA()
	{
		nodePair14ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 15 of 15 set A
	@FXML private void handleNodePairFrom15TextFieldA()
	{
		String origText = nodePair15FromTextFieldA.getText().trim();
		nodePair15FromTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom15TextFieldA()
	{
		nodePair15FromTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo15TextFieldA()
	{
		String origText = nodePair15ToTextFieldA.getText().trim();
		nodePair15ToTextFieldA.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo15TextFieldA()
	{
		nodePair15ToTextFieldA.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetANodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 1 of 15 set B
	@FXML private void handleNodePairFrom1TextFieldB()
	{
		String origText = nodePair1FromTextFieldB.getText().trim();
		nodePair1FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom1TextFieldB()
	{
		nodePair1FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo1TextFieldB()
	{
		String origText = nodePair1ToTextFieldB.getText().trim();
		nodePair1ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo1TextFieldB()
	{
		nodePair1ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 2 of 15 set B
	@FXML private void handleNodePairFrom2TextFieldB()
	{
		String origText = nodePair2FromTextFieldB.getText().trim();
		nodePair2FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom2TextFieldB()
	{
		nodePair2FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo2TextFieldB()
	{
		String origText = nodePair2ToTextFieldB.getText().trim();
		nodePair2ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo2TextFieldB()
	{
		nodePair2ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 3 of 15 set B
	@FXML private void handleNodePairFrom3TextFieldB()
	{
		String origText = nodePair3FromTextFieldB.getText().trim();
		nodePair3FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom3TextFieldB()
	{
		nodePair3FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo3TextFieldB()
	{
		String origText = nodePair3ToTextFieldB.getText().trim();
		nodePair3ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo3TextFieldB()
	{
		nodePair3ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 4 of 15 set B
	@FXML private void handleNodePairFrom4TextFieldB()
	{
		String origText = nodePair4FromTextFieldB.getText().trim();
		nodePair4FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom4TextFieldB()
	{
		nodePair4FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo4TextFieldB()
	{
		String origText = nodePair4ToTextFieldB.getText().trim();
		nodePair4ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo4TextFieldB()
	{
		nodePair4ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}	

	// Node pair # 5 of 15 set B
	@FXML private void handleNodePairFrom5TextFieldB()
	{
		String origText = nodePair5FromTextFieldB.getText().trim();
		nodePair5FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom5TextFieldB()
	{
		nodePair5FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo5TextFieldB()
	{
		String origText = nodePair5ToTextFieldB.getText().trim();
		nodePair5ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo5TextFieldB()
	{
		nodePair5ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 6 of 15 set B
	@FXML private void handleNodePairFrom6TextFieldB()
	{
		String origText = nodePair6FromTextFieldB.getText().trim();
		nodePair6FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom6TextFieldB()
	{
		nodePair6FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo6TextFieldB()
	{
		String origText = nodePair6ToTextFieldB.getText().trim();
		nodePair6ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo6TextFieldB()
	{
		nodePair6ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 7 of 15 set B
	@FXML private void handleNodePairFrom7TextFieldB()
	{
		String origText = nodePair7FromTextFieldB.getText().trim();
		nodePair7FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom7TextFieldB()
	{
		nodePair7FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo7TextFieldB()
	{
		String origText = nodePair7ToTextFieldB.getText().trim();
		nodePair7ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo7TextFieldB()
	{
		nodePair7ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 8 of 15 set B
	@FXML private void handleNodePairFrom8TextFieldB()
	{
		String origText = nodePair8FromTextFieldB.getText().trim();
		nodePair8FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom8TextFieldB()
	{
		nodePair8FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo8TextFieldB()
	{
		String origText = nodePair8ToTextFieldB.getText().trim();
		nodePair8ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo8TextFieldB()
	{
		nodePair8ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 9 of 15 set B
	@FXML private void handleNodePairFrom9TextFieldB()
	{
		String origText = nodePair9FromTextFieldB.getText().trim();
		nodePair9FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom9TextFieldB()
	{
		nodePair9FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo9TextFieldB()
	{
		String origText = nodePair9ToTextFieldB.getText().trim();
		nodePair9ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo9TextFieldB()
	{
		nodePair9ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 10 of 15 set B
	@FXML private void handleNodePairFrom10TextFieldB()
	{
		String origText = nodePair10FromTextFieldB.getText().trim();
		nodePair10FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom10TextFieldB()
	{
		nodePair10FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo10TextFieldB()
	{
		String origText = nodePair10ToTextFieldB.getText().trim();
		nodePair10ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo10TextFieldB()
	{
		nodePair10ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 11 of 15 set B
	@FXML private void handleNodePairFrom11TextFieldB()
	{
		String origText = nodePair11FromTextFieldB.getText().trim();
		nodePair11FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom11TextFieldB()
	{
		nodePair11FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo11TextFieldB()
	{
		String origText = nodePair11ToTextFieldB.getText().trim();
		nodePair11ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo11TextFieldB()
	{
		nodePair11ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 12 of 15 set B
	@FXML private void handleNodePairFrom12TextFieldB()
	{
		String origText = nodePair12FromTextFieldB.getText().trim();
		nodePair12FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom12TextFieldB()
	{
		nodePair12FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo12TextFieldB()
	{
		String origText = nodePair12ToTextFieldB.getText().trim();
		nodePair12ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo12TextFieldB()
	{
		nodePair12ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 13 of 15 set B
	@FXML private void handleNodePairFrom13TextFieldB()
	{
		String origText = nodePair13FromTextFieldB.getText().trim();
		nodePair13FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom13TextFieldB()
	{
		nodePair13FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo13TextFieldB()
	{
		String origText = nodePair13ToTextFieldB.getText().trim();
		nodePair13ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo13TextFieldB()
	{
		nodePair13ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 14 of 15 set B
	@FXML private void handleNodePairFrom14TextFieldB()
	{
		String origText = nodePair14FromTextFieldB.getText().trim();
		nodePair14FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom14TextFieldB()
	{
		nodePair14FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo14TextFieldB()
	{
		String origText = nodePair14ToTextFieldB.getText().trim();
		nodePair14ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo14TextFieldB()
	{
		nodePair14ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 15 of 15 set B
	@FXML private void handleNodePairFrom15TextFieldB()
	{
		String origText = nodePair15FromTextFieldB.getText().trim();
		nodePair15FromTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom15TextFieldB()
	{
		nodePair15FromTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo15TextFieldB()
	{
		String origText = nodePair15ToTextFieldB.getText().trim();
		nodePair15ToTextFieldB.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo15TextFieldB()
	{
		nodePair15ToTextFieldB.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetBNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleSetALowRecoveryRateComboBox()
	{
		setALowRecoveryRate = setALowRecoveryRateComboBox.getSelectionModel().getSelectedItem().toString();
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_setALowRecoveryRate", setALowRecoveryRate);				
	}
	
	@FXML private void handleSetBLowRecoveryRateComboBox()
	{
		setBLowRecoveryRate = setBLowRecoveryRateComboBox.getSelectionModel().getSelectedItem().toString();
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_setBLowRecoveryRate", setBLowRecoveryRate);				
	}

	@FXML private void handleSetANoSchedulingImprecisionRadioButton()
	{
		setASchedulingImprecisionOffset = 0;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_setASchedulingImprecisionOffset", setASchedulingImprecisionOffset);
	}

	@FXML private void handleSetAThirtySecondImprecisionRadioButton()
	{
		setASchedulingImprecisionOffset = 30;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_setASchedulingImprecisionOffset", setASchedulingImprecisionOffset);
	}

	@FXML private void handleSetAOneMinuteImprecisionRadioButton()
	{
		setASchedulingImprecisionOffset = 60;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_setASchedulingImprecisionOffset", setASchedulingImprecisionOffset);
	}
	
	@FXML private void handleSetBNoSchedulingImprecisionRadioButton()
	{
		setBSchedulingImprecisionOffset = 0;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_setBSchedulingImprecisionOffset", setBSchedulingImprecisionOffset);
	}

	@FXML private void handleSetBThirtySecondImprecisionRadioButton()
	{
		setBSchedulingImprecisionOffset = 30;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_setBSchedulingImprecisionOffset", setBSchedulingImprecisionOffset);
	}

	@FXML private void handleSetBOneMinuteImprecisionRadioButton()
	{
		setBSchedulingImprecisionOffset = 60;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_setBSchedulingImprecisionOffset", setBSchedulingImprecisionOffset);
	}

	@FXML private void handleUpdateGroupsButton()
	{
		if (group1TextField.getText().trim().length() == 3)
		{
			String origText = group1TextField.getText().trim();
			group1TextField.setText(origText.toUpperCase());
			group1TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
		{
			group1TextField.setText("");
		}

		if (group2TextField.getText().trim().length() == 3)
		{
			String origText = group2TextField.getText().trim();
			group2TextField.setText(origText.toUpperCase());
			group2TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
		{
			group2TextField.setText("");
		}

		if (group3TextField.getText().trim().length() == 3)
		{
			String origText = group3TextField.getText().trim();
			group3TextField.setText(origText.toUpperCase());
			group3TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
		{
			group3TextField.setText("");
		}

		if (group4TextField.getText().trim().length() == 3)
		{
			String origText = group4TextField.getText().trim();
			group4TextField.setText(origText.toUpperCase());
			group4TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
		{
			group4TextField.setText("");
		}

		if (group5TextField.getText().trim().length() == 3)
		{
			String origText = group5TextField.getText().trim();
			group5TextField.setText(origText.toUpperCase());
			group5TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
		{
			group5TextField.setText("");
		}

		updateGroupsButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		// Write to registry
		String groupsToWriteToRegistry = "";
		if ((group1TextField != null) && (group1TextField.getText().trim() != ""))
			groupsToWriteToRegistry += group1TextField.getText() + ",";
		if ((group2TextField != null) && (group2TextField.getText().trim() != ""))
			groupsToWriteToRegistry += group2TextField.getText() + ",";
		if ((group3TextField != null) && (group3TextField.getText().trim() != ""))
			groupsToWriteToRegistry += group3TextField.getText() + ",";
		if ((group4TextField != null) && (group4TextField.getText().trim() != ""))
			groupsToWriteToRegistry += group4TextField.getText() + ",";
		if ((group5TextField != null) && (group5TextField.getText().trim() != ""))
			groupsToWriteToRegistry += group5TextField.getText() + ",";

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_trainGroups", groupsToWriteToRegistry);

		trainGroups = groupsToWriteToRegistry;
	}

	@FXML private void handleUpdateSetANodesButton()
	{
		updateSetANodesButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		// Eliminate pairs that are missing one node
		if ((nodePair1FromTextFieldA.getText().trim() != null) && (nodePair1FromTextFieldA.getText().trim() != "") && (nodePair1ToTextFieldA.getText().trim() != null) && (nodePair1ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair1FromTextFieldA.getText().trim();
			String origTextTo = nodePair1ToTextFieldA.getText().trim();
			nodePair1FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair1ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair1FromTextFieldA.clear();
			nodePair1ToTextFieldA.clear();
		}

		if ((nodePair2FromTextFieldA.getText().trim() != null) && (nodePair2FromTextFieldA.getText().trim() != "") && (nodePair2ToTextFieldA.getText().trim() != null) && (nodePair2ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair2FromTextFieldA.getText().trim();
			String origTextTo = nodePair2ToTextFieldA.getText().trim();
			nodePair2FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair2ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair2FromTextFieldA.clear();
			nodePair2ToTextFieldA.clear();
		}

		if ((nodePair3FromTextFieldA.getText().trim() != null) && (nodePair3FromTextFieldA.getText().trim() != "") && (nodePair3ToTextFieldA.getText().trim() != null) && (nodePair3ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair3FromTextFieldA.getText().trim();
			String origTextTo = nodePair3ToTextFieldA.getText().trim();
			nodePair3FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair3ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair3FromTextFieldA.clear();
			nodePair3ToTextFieldA.clear();
		}

		if ((nodePair4FromTextFieldA.getText().trim() != null) && (nodePair4FromTextFieldA.getText().trim() != "") && (nodePair4ToTextFieldA.getText().trim() != null) && (nodePair4ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair4FromTextFieldA.getText().trim();
			String origTextTo = nodePair4ToTextFieldA.getText().trim();
			nodePair4FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair4ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair4FromTextFieldA.clear();
			nodePair4ToTextFieldA.clear();
		}

		if ((nodePair5FromTextFieldA.getText().trim() != null) && (nodePair5FromTextFieldA.getText().trim() != "") && (nodePair5ToTextFieldA.getText().trim() != null) && (nodePair5ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair5FromTextFieldA.getText().trim();
			String origTextTo = nodePair5ToTextFieldA.getText().trim();
			nodePair5FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair5ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair5FromTextFieldA.clear();
			nodePair5ToTextFieldA.clear();
		}

		if ((nodePair6FromTextFieldA.getText().trim() != null) && (nodePair6FromTextFieldA.getText().trim() != "") && (nodePair6ToTextFieldA.getText().trim() != null) && (nodePair6ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair6FromTextFieldA.getText().trim();
			String origTextTo = nodePair6ToTextFieldA.getText().trim();
			nodePair6FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair6ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair6FromTextFieldA.clear();
			nodePair6ToTextFieldA.clear();
		}

		if ((nodePair7FromTextFieldA.getText().trim() != null) && (nodePair7FromTextFieldA.getText().trim() != "") && (nodePair7ToTextFieldA.getText().trim() != null) && (nodePair7ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair7FromTextFieldA.getText().trim();
			String origTextTo = nodePair7ToTextFieldA.getText().trim();
			nodePair7FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair7ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair7FromTextFieldA.clear();
			nodePair7ToTextFieldA.clear();
		}

		if ((nodePair8FromTextFieldA.getText().trim() != null) && (nodePair8FromTextFieldA.getText().trim() != "") && (nodePair8ToTextFieldA.getText().trim() != null) && (nodePair8ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair8FromTextFieldA.getText().trim();
			String origTextTo = nodePair8ToTextFieldA.getText().trim();
			nodePair8FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair8ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair8FromTextFieldA.clear();
			nodePair8ToTextFieldA.clear();
		}

		if ((nodePair9FromTextFieldA.getText().trim() != null) && (nodePair9FromTextFieldA.getText().trim() != "") && (nodePair9ToTextFieldA.getText().trim() != null) && (nodePair9ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair9FromTextFieldA.getText().trim();
			String origTextTo = nodePair9ToTextFieldA.getText().trim();
			nodePair9FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair9ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair9FromTextFieldA.clear();
			nodePair9ToTextFieldA.clear();
		}

		if ((nodePair10FromTextFieldA.getText().trim() != null) && (nodePair10FromTextFieldA.getText().trim() != "") && (nodePair10ToTextFieldA.getText().trim() != null) && (nodePair10ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair10FromTextFieldA.getText().trim();
			String origTextTo = nodePair10ToTextFieldA.getText().trim();
			nodePair10FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair10ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair10FromTextFieldA.clear();
			nodePair10ToTextFieldA.clear();
		}

		if ((nodePair11FromTextFieldA.getText().trim() != null) && (nodePair11FromTextFieldA.getText().trim() != "") && (nodePair11ToTextFieldA.getText().trim() != null) && (nodePair11ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair11FromTextFieldA.getText().trim();
			String origTextTo = nodePair11ToTextFieldA.getText().trim();
			nodePair11FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair11ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair11FromTextFieldA.clear();
			nodePair11ToTextFieldA.clear();
		}

		if ((nodePair12FromTextFieldA.getText().trim() != null) && (nodePair12FromTextFieldA.getText().trim() != "") && (nodePair12ToTextFieldA.getText().trim() != null) && (nodePair12ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair12FromTextFieldA.getText().trim();
			String origTextTo = nodePair12ToTextFieldA.getText().trim();
			nodePair12FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair12ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair12FromTextFieldA.clear();
			nodePair12ToTextFieldA.clear();
		}

		if ((nodePair13FromTextFieldA.getText().trim() != null) && (nodePair13FromTextFieldA.getText().trim() != "") && (nodePair13ToTextFieldA.getText().trim() != null) && (nodePair13ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair13FromTextFieldA.getText().trim();
			String origTextTo = nodePair13ToTextFieldA.getText().trim();
			nodePair13FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair13ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair13FromTextFieldA.clear();
			nodePair13ToTextFieldA.clear();
		}

		if ((nodePair14FromTextFieldA.getText().trim() != null) && (nodePair14FromTextFieldA.getText().trim() != "") && (nodePair14ToTextFieldA.getText().trim() != null) && (nodePair14ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair14FromTextFieldA.getText().trim();
			String origTextTo = nodePair14ToTextFieldA.getText().trim();
			nodePair14FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair14ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair14FromTextFieldA.clear();
			nodePair14ToTextFieldA.clear();
		}

		if ((nodePair15FromTextFieldA.getText().trim() != null) && (nodePair15FromTextFieldA.getText().trim() != "") && (nodePair15ToTextFieldA.getText().trim() != null) && (nodePair15ToTextFieldA.getText().trim() != ""))
		{
			String origTextFrom = nodePair15FromTextFieldA.getText().trim();
			String origTextTo = nodePair15ToTextFieldA.getText().trim();
			nodePair15FromTextFieldA.setText(origTextFrom.toUpperCase());
			nodePair15ToTextFieldA.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair15FromTextFieldA.clear();
			nodePair15ToTextFieldA.clear();
		}

		// Write to registry
		String nodePairsToWriteToRegistryA = "";
		if ((nodePair1FromTextFieldA != null) && (nodePair1FromTextFieldA.getText().trim() != "") && (nodePair1ToTextFieldA != null) && (nodePair1ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair1FromTextFieldA.getText() + ":" + nodePair1ToTextFieldA.getText()+"," ;	
			nodePair1FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair1ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair2FromTextFieldA != null) && (nodePair2FromTextFieldA.getText().trim() != "") && (nodePair2ToTextFieldA != null) && (nodePair2ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair2FromTextFieldA.getText() + ":" + nodePair2ToTextFieldA.getText()+"," ;	
			nodePair2FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair2ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair3FromTextFieldA != null) && (nodePair3FromTextFieldA.getText().trim() != "") && (nodePair3ToTextFieldA != null) && (nodePair3ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair3FromTextFieldA.getText() + ":" + nodePair3ToTextFieldA.getText()+"," ;	
			nodePair3FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair3ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair4FromTextFieldA != null) && (nodePair4FromTextFieldA.getText().trim() != "") && (nodePair4ToTextFieldA != null) && (nodePair4ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair4FromTextFieldA.getText() + ":" + nodePair4ToTextFieldA.getText()+"," ;	
			nodePair4FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair4ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair5FromTextFieldA != null) && (nodePair5FromTextFieldA.getText().trim() != "") && (nodePair5ToTextFieldA != null) && (nodePair5ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair5FromTextFieldA.getText() + ":" + nodePair5ToTextFieldA.getText()+"," ;	
			nodePair5FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair5ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair6FromTextFieldA != null) && (nodePair6FromTextFieldA.getText().trim() != "") && (nodePair6ToTextFieldA != null) && (nodePair6ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair6FromTextFieldA.getText() + ":" + nodePair6ToTextFieldA.getText()+"," ;	
			nodePair6FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair6ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair7FromTextFieldA != null) && (nodePair7FromTextFieldA.getText().trim() != "") && (nodePair7ToTextFieldA != null) && (nodePair7ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair7FromTextFieldA.getText() + ":" + nodePair7ToTextFieldA.getText()+"," ;	
			nodePair7FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair7ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair8FromTextFieldA != null) && (nodePair8FromTextFieldA.getText().trim() != "") && (nodePair8ToTextFieldA != null) && (nodePair8ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair8FromTextFieldA.getText() + ":" + nodePair8ToTextFieldA.getText()+"," ;	
			nodePair8FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair8ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair9FromTextFieldA != null) && (nodePair9FromTextFieldA.getText().trim() != "") && (nodePair9ToTextFieldA != null) && (nodePair9ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair9FromTextFieldA.getText() + ":" + nodePair9ToTextFieldA.getText()+"," ;	
			nodePair9FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair9ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair10FromTextFieldA != null) && (nodePair10FromTextFieldA.getText().trim() != "") && (nodePair10ToTextFieldA != null) && (nodePair10ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair10FromTextFieldA.getText() + ":" + nodePair10ToTextFieldA.getText()+"," ;	
			nodePair10FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair10ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair11FromTextFieldA != null) && (nodePair11FromTextFieldA.getText().trim() != "") && (nodePair11ToTextFieldA != null) && (nodePair11ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair11FromTextFieldA.getText() + ":" + nodePair11ToTextFieldA.getText()+"," ;	
			nodePair11FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair11ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair12FromTextFieldA != null) && (nodePair12FromTextFieldA.getText().trim() != "") && (nodePair12ToTextFieldA != null) && (nodePair12ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair12FromTextFieldA.getText() + ":" + nodePair12ToTextFieldA.getText()+"," ;	
			nodePair12FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair12ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair13FromTextFieldA != null) && (nodePair13FromTextFieldA.getText().trim() != "") && (nodePair13ToTextFieldA != null) && (nodePair13ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair13FromTextFieldA.getText() + ":" + nodePair13ToTextFieldA.getText()+"," ;	
			nodePair13FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair13ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair14FromTextFieldA != null) && (nodePair14FromTextFieldA.getText().trim() != "") && (nodePair14ToTextFieldA != null) && (nodePair14ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair14FromTextFieldA.getText() + ":" + nodePair14ToTextFieldA.getText()+"," ;	
			nodePair14FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair14ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair15FromTextFieldA != null) && (nodePair15FromTextFieldA.getText().trim() != "") && (nodePair15ToTextFieldA != null) && (nodePair15ToTextFieldA.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryA += nodePair15FromTextFieldA.getText() + ":" + nodePair15ToTextFieldA.getText()+"," ;	
			nodePair15FromTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair15ToTextFieldA.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_setANodePairs", nodePairsToWriteToRegistryA);

		setANodePairs = nodePairsToWriteToRegistryA;
	}

	@FXML private void handleUpdateSetBNodesButton()
	{
		updateSetBNodesButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		// Eliminate pairs that are missing one node
		if ((nodePair1FromTextFieldB.getText().trim() != null) && (nodePair1FromTextFieldB.getText().trim() != "") && (nodePair1ToTextFieldB.getText().trim() != null) && (nodePair1ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair1FromTextFieldB.getText().trim();
			String origTextTo = nodePair1ToTextFieldB.getText().trim();
			nodePair1FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair1ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair1FromTextFieldB.clear();
			nodePair1ToTextFieldB.clear();
		}

		if ((nodePair2FromTextFieldB.getText().trim() != null) && (nodePair2FromTextFieldB.getText().trim() != "") && (nodePair2ToTextFieldB.getText().trim() != null) && (nodePair2ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair2FromTextFieldB.getText().trim();
			String origTextTo = nodePair2ToTextFieldB.getText().trim();
			nodePair2FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair2ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair2FromTextFieldB.clear();
			nodePair2ToTextFieldB.clear();
		}

		if ((nodePair3FromTextFieldB.getText().trim() != null) && (nodePair3FromTextFieldB.getText().trim() != "") && (nodePair3ToTextFieldB.getText().trim() != null) && (nodePair3ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair3FromTextFieldB.getText().trim();
			String origTextTo = nodePair3ToTextFieldB.getText().trim();
			nodePair3FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair3ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair3FromTextFieldB.clear();
			nodePair3ToTextFieldB.clear();
		}

		if ((nodePair4FromTextFieldB.getText().trim() != null) && (nodePair4FromTextFieldB.getText().trim() != "") && (nodePair4ToTextFieldB.getText().trim() != null) && (nodePair4ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair4FromTextFieldB.getText().trim();
			String origTextTo = nodePair4ToTextFieldB.getText().trim();
			nodePair4FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair4ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair4FromTextFieldB.clear();
			nodePair4ToTextFieldB.clear();
		}

		if ((nodePair5FromTextFieldB.getText().trim() != null) && (nodePair5FromTextFieldB.getText().trim() != "") && (nodePair5ToTextFieldB.getText().trim() != null) && (nodePair5ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair5FromTextFieldB.getText().trim();
			String origTextTo = nodePair5ToTextFieldB.getText().trim();
			nodePair5FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair5ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair5FromTextFieldB.clear();
			nodePair5ToTextFieldB.clear();
		}

		if ((nodePair6FromTextFieldB.getText().trim() != null) && (nodePair6FromTextFieldB.getText().trim() != "") && (nodePair6ToTextFieldB.getText().trim() != null) && (nodePair6ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair6FromTextFieldB.getText().trim();
			String origTextTo = nodePair6ToTextFieldB.getText().trim();
			nodePair6FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair6ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair6FromTextFieldB.clear();
			nodePair6ToTextFieldB.clear();
		}

		if ((nodePair7FromTextFieldB.getText().trim() != null) && (nodePair7FromTextFieldB.getText().trim() != "") && (nodePair7ToTextFieldB.getText().trim() != null) && (nodePair7ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair7FromTextFieldB.getText().trim();
			String origTextTo = nodePair7ToTextFieldB.getText().trim();
			nodePair7FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair7ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair7FromTextFieldB.clear();
			nodePair7ToTextFieldB.clear();
		}

		if ((nodePair8FromTextFieldB.getText().trim() != null) && (nodePair8FromTextFieldB.getText().trim() != "") && (nodePair8ToTextFieldB.getText().trim() != null) && (nodePair8ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair8FromTextFieldB.getText().trim();
			String origTextTo = nodePair8ToTextFieldB.getText().trim();
			nodePair8FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair8ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair8FromTextFieldB.clear();
			nodePair8ToTextFieldB.clear();
		}

		if ((nodePair9FromTextFieldB.getText().trim() != null) && (nodePair9FromTextFieldB.getText().trim() != "") && (nodePair9ToTextFieldB.getText().trim() != null) && (nodePair9ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair9FromTextFieldB.getText().trim();
			String origTextTo = nodePair9ToTextFieldB.getText().trim();
			nodePair9FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair9ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair9FromTextFieldB.clear();
			nodePair9ToTextFieldB.clear();
		}

		if ((nodePair10FromTextFieldB.getText().trim() != null) && (nodePair10FromTextFieldB.getText().trim() != "") && (nodePair10ToTextFieldB.getText().trim() != null) && (nodePair10ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair10FromTextFieldB.getText().trim();
			String origTextTo = nodePair10ToTextFieldB.getText().trim();
			nodePair10FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair10ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair10FromTextFieldB.clear();
			nodePair10ToTextFieldB.clear();
		}

		if ((nodePair11FromTextFieldB.getText().trim() != null) && (nodePair11FromTextFieldB.getText().trim() != "") && (nodePair11ToTextFieldB.getText().trim() != null) && (nodePair11ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair11FromTextFieldB.getText().trim();
			String origTextTo = nodePair11ToTextFieldB.getText().trim();
			nodePair11FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair11ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair11FromTextFieldB.clear();
			nodePair11ToTextFieldB.clear();
		}

		if ((nodePair12FromTextFieldB.getText().trim() != null) && (nodePair12FromTextFieldB.getText().trim() != "") && (nodePair12ToTextFieldB.getText().trim() != null) && (nodePair12ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair12FromTextFieldB.getText().trim();
			String origTextTo = nodePair12ToTextFieldB.getText().trim();
			nodePair12FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair12ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair12FromTextFieldB.clear();
			nodePair12ToTextFieldB.clear();
		}

		if ((nodePair13FromTextFieldB.getText().trim() != null) && (nodePair13FromTextFieldB.getText().trim() != "") && (nodePair13ToTextFieldB.getText().trim() != null) && (nodePair13ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair13FromTextFieldB.getText().trim();
			String origTextTo = nodePair13ToTextFieldB.getText().trim();
			nodePair13FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair13ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair13FromTextFieldB.clear();
			nodePair13ToTextFieldB.clear();
		}

		if ((nodePair14FromTextFieldB.getText().trim() != null) && (nodePair14FromTextFieldB.getText().trim() != "") && (nodePair14ToTextFieldB.getText().trim() != null) && (nodePair14ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair14FromTextFieldB.getText().trim();
			String origTextTo = nodePair14ToTextFieldB.getText().trim();
			nodePair14FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair14ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair14FromTextFieldB.clear();
			nodePair14ToTextFieldB.clear();
		}

		if ((nodePair15FromTextFieldB.getText().trim() != null) && (nodePair15FromTextFieldB.getText().trim() != "") && (nodePair15ToTextFieldB.getText().trim() != null) && (nodePair15ToTextFieldB.getText().trim() != ""))
		{
			String origTextFrom = nodePair15FromTextFieldB.getText().trim();
			String origTextTo = nodePair15ToTextFieldB.getText().trim();
			nodePair15FromTextFieldB.setText(origTextFrom.toUpperCase());
			nodePair15ToTextFieldB.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair15FromTextFieldB.clear();
			nodePair15ToTextFieldB.clear();
		}

		// Write to registry
		String nodePairsToWriteToRegistryB = "";
		if ((nodePair1FromTextFieldB != null) && (nodePair1FromTextFieldB.getText().trim() != "") && (nodePair1ToTextFieldB != null) && (nodePair1ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair1FromTextFieldB.getText() + ":" + nodePair1ToTextFieldB.getText()+"," ;	
			nodePair1FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair1ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair2FromTextFieldB != null) && (nodePair2FromTextFieldB.getText().trim() != "") && (nodePair2ToTextFieldB != null) && (nodePair2ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair2FromTextFieldB.getText() + ":" + nodePair2ToTextFieldB.getText()+"," ;	
			nodePair2FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair2ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair3FromTextFieldB != null) && (nodePair3FromTextFieldB.getText().trim() != "") && (nodePair3ToTextFieldB != null) && (nodePair3ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair3FromTextFieldB.getText() + ":" + nodePair3ToTextFieldB.getText()+"," ;	
			nodePair3FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair3ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair4FromTextFieldB != null) && (nodePair4FromTextFieldB.getText().trim() != "") && (nodePair4ToTextFieldB != null) && (nodePair4ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair4FromTextFieldB.getText() + ":" + nodePair4ToTextFieldB.getText()+"," ;	
			nodePair4FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair4ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair5FromTextFieldB != null) && (nodePair5FromTextFieldB.getText().trim() != "") && (nodePair5ToTextFieldB != null) && (nodePair5ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair5FromTextFieldB.getText() + ":" + nodePair5ToTextFieldB.getText()+"," ;	
			nodePair5FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair5ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair6FromTextFieldB != null) && (nodePair6FromTextFieldB.getText().trim() != "") && (nodePair6ToTextFieldB != null) && (nodePair6ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair6FromTextFieldB.getText() + ":" + nodePair6ToTextFieldB.getText()+"," ;	
			nodePair6FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair6ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair7FromTextFieldB != null) && (nodePair7FromTextFieldB.getText().trim() != "") && (nodePair7ToTextFieldB != null) && (nodePair7ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair7FromTextFieldB.getText() + ":" + nodePair7ToTextFieldB.getText()+"," ;	
			nodePair7FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair7ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair8FromTextFieldB != null) && (nodePair8FromTextFieldB.getText().trim() != "") && (nodePair8ToTextFieldB != null) && (nodePair8ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair8FromTextFieldB.getText() + ":" + nodePair8ToTextFieldB.getText()+"," ;	
			nodePair8FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair8ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair9FromTextFieldB != null) && (nodePair9FromTextFieldB.getText().trim() != "") && (nodePair9ToTextFieldB != null) && (nodePair9ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair9FromTextFieldB.getText() + ":" + nodePair9ToTextFieldB.getText()+"," ;	
			nodePair9FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair9ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair10FromTextFieldB != null) && (nodePair10FromTextFieldB.getText().trim() != "") && (nodePair10ToTextFieldB != null) && (nodePair10ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair10FromTextFieldB.getText() + ":" + nodePair10ToTextFieldB.getText()+"," ;	
			nodePair10FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair10ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair11FromTextFieldB != null) && (nodePair11FromTextFieldB.getText().trim() != "") && (nodePair11ToTextFieldB != null) && (nodePair11ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair11FromTextFieldB.getText() + ":" + nodePair11ToTextFieldB.getText()+"," ;	
			nodePair11FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair11ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair12FromTextFieldB != null) && (nodePair12FromTextFieldB.getText().trim() != "") && (nodePair12ToTextFieldB != null) && (nodePair12ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair12FromTextFieldB.getText() + ":" + nodePair12ToTextFieldB.getText()+"," ;	
			nodePair12FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair12ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair13FromTextFieldB != null) && (nodePair13FromTextFieldB.getText().trim() != "") && (nodePair13ToTextFieldB != null) && (nodePair13ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair13FromTextFieldB.getText() + ":" + nodePair13ToTextFieldB.getText()+"," ;	
			nodePair13FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair13ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair14FromTextFieldB != null) && (nodePair14FromTextFieldB.getText().trim() != "") && (nodePair14ToTextFieldB != null) && (nodePair14ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair14FromTextFieldB.getText() + ":" + nodePair14ToTextFieldB.getText()+"," ;	
			nodePair14FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair14ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair15FromTextFieldB != null) && (nodePair15FromTextFieldB.getText().trim() != "") && (nodePair15ToTextFieldB != null) && (nodePair15ToTextFieldB.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryB += nodePair15FromTextFieldB.getText() + ":" + nodePair15ToTextFieldB.getText()+"," ;	
			nodePair15FromTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair15ToTextFieldB.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_setBNodePairs", nodePairsToWriteToRegistryB);

		setBNodePairs = nodePairsToWriteToRegistryB;
	}

	public static String getSetALowRecoveryRate()
	{
		return setALowRecoveryRate;
	}
	
	public static String getSetBLowRecoveryRate()
	{
		return setBLowRecoveryRate;
	}

	public static Integer getSetAScheduleImprecisionOffsetInSeconds()
	{
		return setASchedulingImprecisionOffset;
	}
	
	public static Integer getSetBScheduleImprecisionOffsetInSeconds()
	{
		return setBSchedulingImprecisionOffset;
	}

	public static String getRecoveryRateAnalysisTrainGroups()
	{
		return trainGroups;
	}

	public static String getSetARecoveryRateAnalysisNodePairs()
	{
		return setANodePairs;
	}
	
	public static String getSetBRecoveryRateAnalysisNodePairs()
	{
		return setBNodePairs;
	}
}