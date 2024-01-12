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
	private static String nodePairs;
	private static String lowRecoveryRate;

	private static Integer schedulingImprecisionOffset;
	private static Integer defaultSchedulingImprecisionOffset = 0;

	private static ObservableList<String> lowRecoveryRates =  FXCollections.observableArrayList("0%", "5%", "10%", "15%");
	private static String defaultLowRecoveryRate = "15%";

	@FXML TextField group1TextField;
	@FXML TextField group2TextField;
	@FXML TextField group3TextField;
	@FXML TextField group4TextField;
	@FXML TextField group5TextField;

	@FXML TextField nodePair1FromTextField;
	@FXML TextField nodePair1ToTextField;
	@FXML TextField nodePair2FromTextField;
	@FXML TextField nodePair2ToTextField;
	@FXML TextField nodePair3FromTextField;
	@FXML TextField nodePair3ToTextField;
	@FXML TextField nodePair4FromTextField;
	@FXML TextField nodePair4ToTextField;
	@FXML TextField nodePair5FromTextField;
	@FXML TextField nodePair5ToTextField;
	@FXML TextField nodePair6FromTextField;
	@FXML TextField nodePair6ToTextField;
	@FXML TextField nodePair7FromTextField;
	@FXML TextField nodePair7ToTextField;
	@FXML TextField nodePair8FromTextField;
	@FXML TextField nodePair8ToTextField;
	@FXML TextField nodePair9FromTextField;
	@FXML TextField nodePair9ToTextField;
	@FXML TextField nodePair10FromTextField;
	@FXML TextField nodePair10ToTextField;
	@FXML TextField nodePair11FromTextField;
	@FXML TextField nodePair11ToTextField;
	@FXML TextField nodePair12FromTextField;
	@FXML TextField nodePair12ToTextField;
	@FXML TextField nodePair13FromTextField;
	@FXML TextField nodePair13ToTextField;
	@FXML TextField nodePair14FromTextField;
	@FXML TextField nodePair14ToTextField;

	@FXML Button updateGroupsButton;
	@FXML Button updateNodesButton;

	@FXML ComboBox<String> lowRecoveryRateComboBox;

	@FXML RadioButton noSchedulingImprecisionRadioButton;
	@FXML RadioButton thirtySecondImprecisionRadioButton;
	@FXML RadioButton oneMinuteImprecisionRadioButton;

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

		// See if preferences are stored for node pairs
		if ((prefs.get("rr_nodePairs", "") != null) && (prefs.get("rr_nodePairs", "") != ""))
		{
			nodePairs = prefs.get("rr_nodePairs", "");
			String nodePair[] = nodePairs.split(",");

			for (int i = 0; i < nodePair.length; i++)
			{
				if (i == 0)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair1FromTextField.setText(fromTo[0]);
					nodePair1ToTextField.setText(fromTo[1]);
				}
				else if (i == 1)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair2FromTextField.setText(fromTo[0]);
					nodePair2ToTextField.setText(fromTo[1]);
				}
				else if (i == 2)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair3FromTextField.setText(fromTo[0]);
					nodePair3ToTextField.setText(fromTo[1]);
				}
				else if (i == 3)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair4FromTextField.setText(fromTo[0]);
					nodePair4ToTextField.setText(fromTo[1]);
				}
				else if (i == 4)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair5FromTextField.setText(fromTo[0]);
					nodePair5ToTextField.setText(fromTo[1]);
				}
				else if (i == 5)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair6FromTextField.setText(fromTo[0]);
					nodePair6ToTextField.setText(fromTo[1]);
				}
				else if (i == 6)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair7FromTextField.setText(fromTo[0]);
					nodePair7ToTextField.setText(fromTo[1]);
				}
				else if (i == 7)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair8FromTextField.setText(fromTo[0]);
					nodePair8ToTextField.setText(fromTo[1]);
				}
				else if (i == 8)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair9FromTextField.setText(fromTo[0]);
					nodePair9ToTextField.setText(fromTo[1]);
				}
				else if (i == 9)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair10FromTextField.setText(fromTo[0]);
					nodePair10ToTextField.setText(fromTo[1]);
				}
				else if (i == 10)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair11FromTextField.setText(fromTo[0]);
					nodePair11ToTextField.setText(fromTo[1]);
				}
				else if (i == 11)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair12FromTextField.setText(fromTo[0]);
					nodePair12ToTextField.setText(fromTo[1]);
				}
				else if (i == 12)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair13FromTextField.setText(fromTo[0]);
					nodePair13ToTextField.setText(fromTo[1]);
				}
				else if (i == 13)
				{
					String[] fromTo = nodePair[i].split(":"); 
					nodePair14FromTextField.setText(fromTo[0]);
					nodePair14ToTextField.setText(fromTo[1]);
				}
			}
		}

		// See if preference is stored for scheduling imprecision offset
		if (prefs.getInt("rr_schedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 0)
		{
			schedulingImprecisionOffset = 0;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_schedulingImprecisionOffset", 0);
			noSchedulingImprecisionRadioButton.setSelected(true);
		}
		else if (prefs.getInt("rr_schedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 30)
		{
			schedulingImprecisionOffset = 30;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_schedulingImprecisionOffset", 30);
			thirtySecondImprecisionRadioButton.setSelected(true);
		}
		else if (prefs.getInt("rr_schedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 60)
		{
			schedulingImprecisionOffset = 60;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_schedulingImprecisionOffset", 60);
			oneMinuteImprecisionRadioButton.setSelected(true);
		}
		
		// See if preference is stored for low recovery rate
		lowRecoveryRateComboBox.setItems(lowRecoveryRates);
		boolean lowRecoveryRateExists = prefs.get("rr_lowRecoveryRate", null) != null;
		if (lowRecoveryRateExists)
		{
			lowRecoveryRate = prefs.get("rr_lowRecoveryRate", defaultLowRecoveryRate);
			lowRecoveryRateComboBox.getSelectionModel().select(lowRecoveryRate);
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("rr_lowRecoveryRate", defaultLowRecoveryRate);
			}
			lowRecoveryRate = prefs.get("rr_lowRecoveryRate", defaultLowRecoveryRate);
			lowRecoveryRateComboBox.getSelectionModel().select(defaultLowRecoveryRate);
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

	// Node pair # 1 of 14
	@FXML private void handleNodePairFrom1TextField()
	{
		String origText = nodePair1FromTextField.getText().trim();
		nodePair1FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom1TextField()
	{
		nodePair1FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo1TextField()
	{
		String origText = nodePair1ToTextField.getText().trim();
		nodePair1ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo1TextField()
	{
		nodePair1ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 2 of 14
	@FXML private void handleNodePairFrom2TextField()
	{
		String origText = nodePair2FromTextField.getText().trim();
		nodePair2FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom2TextField()
	{
		nodePair2FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo2TextField()
	{
		String origText = nodePair2ToTextField.getText().trim();
		nodePair2ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo2TextField()
	{
		nodePair2ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 3 of 14
	@FXML private void handleNodePairFrom3TextField()
	{
		String origText = nodePair3FromTextField.getText().trim();
		nodePair3FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom3TextField()
	{
		nodePair3FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo3TextField()
	{
		String origText = nodePair3ToTextField.getText().trim();
		nodePair3ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo3TextField()
	{
		nodePair3ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 4 of 14
	@FXML private void handleNodePairFrom4TextField()
	{
		String origText = nodePair4FromTextField.getText().trim();
		nodePair4FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom4TextField()
	{
		nodePair4FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo4TextField()
	{
		String origText = nodePair4ToTextField.getText().trim();
		nodePair4ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo4TextField()
	{
		nodePair4ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 5 of 14
	@FXML private void handleNodePairFrom5TextField()
	{
		String origText = nodePair5FromTextField.getText().trim();
		nodePair5FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom5TextField()
	{
		nodePair5FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo5TextField()
	{
		String origText = nodePair5ToTextField.getText().trim();
		nodePair5ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo5TextField()
	{
		nodePair5ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 6 of 14
	@FXML private void handleNodePairFrom6TextField()
	{
		String origText = nodePair6FromTextField.getText().trim();
		nodePair6FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom6TextField()
	{
		nodePair6FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo6TextField()
	{
		String origText = nodePair6ToTextField.getText().trim();
		nodePair6ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo6TextField()
	{
		nodePair6ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 7 of 14
	@FXML private void handleNodePairFrom7TextField()
	{
		String origText = nodePair7FromTextField.getText().trim();
		nodePair7FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom7TextField()
	{
		nodePair7FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo7TextField()
	{
		String origText = nodePair7ToTextField.getText().trim();
		nodePair7ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo7TextField()
	{
		nodePair7ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 8 of 14
	@FXML private void handleNodePairFrom8TextField()
	{
		String origText = nodePair8FromTextField.getText().trim();
		nodePair8FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom8TextField()
	{
		nodePair8FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo8TextField()
	{
		String origText = nodePair8ToTextField.getText().trim();
		nodePair8ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo8TextField()
	{
		nodePair8ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 9 of 14
	@FXML private void handleNodePairFrom9TextField()
	{
		String origText = nodePair9FromTextField.getText().trim();
		nodePair9FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom9TextField()
	{
		nodePair9FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo9TextField()
	{
		String origText = nodePair9ToTextField.getText().trim();
		nodePair9ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo9TextField()
	{
		nodePair9ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 10 of 14
	@FXML private void handleNodePairFrom10TextField()
	{
		String origText = nodePair10FromTextField.getText().trim();
		nodePair10FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom10TextField()
	{
		nodePair10FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo10TextField()
	{
		String origText = nodePair10ToTextField.getText().trim();
		nodePair10ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo10TextField()
	{
		nodePair10ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 11 of 14
	@FXML private void handleNodePairFrom11TextField()
	{
		String origText = nodePair11FromTextField.getText().trim();
		nodePair11FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom11TextField()
	{
		nodePair11FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo11TextField()
	{
		String origText = nodePair11ToTextField.getText().trim();
		nodePair11ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo11TextField()
	{
		nodePair11ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 12 of 14
	@FXML private void handleNodePairFrom12TextField()
	{
		String origText = nodePair12FromTextField.getText().trim();
		nodePair12FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom12TextField()
	{
		nodePair12FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo12TextField()
	{
		String origText = nodePair11ToTextField.getText().trim();
		nodePair12ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo12TextField()
	{
		nodePair12ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 13 of 14
	@FXML private void handleNodePairFrom13TextField()
	{
		String origText = nodePair13FromTextField.getText().trim();
		nodePair13FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom13TextField()
	{
		nodePair13FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo13TextField()
	{
		String origText = nodePair13ToTextField.getText().trim();
		nodePair13ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo13TextField()
	{
		nodePair13ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 14 of 14
	@FXML private void handleNodePairFrom14TextField()
	{
		String origText = nodePair14FromTextField.getText().trim();
		nodePair14FromTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom14TextField()
	{
		nodePair14FromTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo14TextField()
	{
		String origText = nodePair14ToTextField.getText().trim();
		nodePair14ToTextField.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo14TextField()
	{
		nodePair14ToTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleLowRecoveryRateComboBox()
	{
		lowRecoveryRate = lowRecoveryRateComboBox.getSelectionModel().getSelectedItem().toString();
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_lowRecoveryRate", lowRecoveryRate);				
	}

	@FXML private void handleNoSchedulingImprecisionRadioButton()
	{
		schedulingImprecisionOffset = 0;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_schedulingImprecisionOffset", schedulingImprecisionOffset);
	}
	
	@FXML private void handleThirtySecondImprecisionRadioButton()
	{
		schedulingImprecisionOffset = 30;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_schedulingImprecisionOffset", schedulingImprecisionOffset);
	}
	
	@FXML private void handleOneMinuteImprecisionRadioButton()
	{
		schedulingImprecisionOffset = 60;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_schedulingImprecisionOffset", schedulingImprecisionOffset);
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
	}

	@FXML private void handleUpdateNodesButton()
	{
		updateNodesButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		// Eliminate pairs that are missing one node
		if ((nodePair1FromTextField.getText().trim() != null) && (nodePair1FromTextField.getText().trim() != "") && (nodePair1ToTextField.getText().trim() != null) && (nodePair1ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair1FromTextField.getText().trim();
			String origTextTo = nodePair1ToTextField.getText().trim();
			nodePair1FromTextField.setText(origTextFrom.toUpperCase());
			nodePair1ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair1FromTextField.clear();
			nodePair1ToTextField.clear();
		}

		if ((nodePair2FromTextField.getText().trim() != null) && (nodePair2FromTextField.getText().trim() != "") && (nodePair2ToTextField.getText().trim() != null) && (nodePair2ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair2FromTextField.getText().trim();
			String origTextTo = nodePair2ToTextField.getText().trim();
			nodePair2FromTextField.setText(origTextFrom.toUpperCase());
			nodePair2ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair2FromTextField.clear();
			nodePair2ToTextField.clear();
		}

		if ((nodePair3FromTextField.getText().trim() != null) && (nodePair3FromTextField.getText().trim() != "") && (nodePair3ToTextField.getText().trim() != null) && (nodePair3ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair3FromTextField.getText().trim();
			String origTextTo = nodePair3ToTextField.getText().trim();
			nodePair3FromTextField.setText(origTextFrom.toUpperCase());
			nodePair3ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair3FromTextField.clear();
			nodePair3ToTextField.clear();
		}

		if ((nodePair4FromTextField.getText().trim() != null) && (nodePair4FromTextField.getText().trim() != "") && (nodePair4ToTextField.getText().trim() != null) && (nodePair4ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair4FromTextField.getText().trim();
			String origTextTo = nodePair4ToTextField.getText().trim();
			nodePair4FromTextField.setText(origTextFrom.toUpperCase());
			nodePair4ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair4FromTextField.clear();
			nodePair4ToTextField.clear();
		}

		if ((nodePair5FromTextField.getText().trim() != null) && (nodePair5FromTextField.getText().trim() != "") && (nodePair5ToTextField.getText().trim() != null) && (nodePair5ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair5FromTextField.getText().trim();
			String origTextTo = nodePair5ToTextField.getText().trim();
			nodePair5FromTextField.setText(origTextFrom.toUpperCase());
			nodePair5ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair5FromTextField.clear();
			nodePair5ToTextField.clear();
		}

		if ((nodePair6FromTextField.getText().trim() != null) && (nodePair6FromTextField.getText().trim() != "") && (nodePair6ToTextField.getText().trim() != null) && (nodePair6ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair6FromTextField.getText().trim();
			String origTextTo = nodePair6ToTextField.getText().trim();
			nodePair6FromTextField.setText(origTextFrom.toUpperCase());
			nodePair6ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair6FromTextField.clear();
			nodePair6ToTextField.clear();
		}

		if ((nodePair7FromTextField.getText().trim() != null) && (nodePair7FromTextField.getText().trim() != "") && (nodePair7ToTextField.getText().trim() != null) && (nodePair7ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair7FromTextField.getText().trim();
			String origTextTo = nodePair7ToTextField.getText().trim();
			nodePair7FromTextField.setText(origTextFrom.toUpperCase());
			nodePair7ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair7FromTextField.clear();
			nodePair7ToTextField.clear();
		}

		if ((nodePair8FromTextField.getText().trim() != null) && (nodePair8FromTextField.getText().trim() != "") && (nodePair8ToTextField.getText().trim() != null) && (nodePair8ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair8FromTextField.getText().trim();
			String origTextTo = nodePair8ToTextField.getText().trim();
			nodePair8FromTextField.setText(origTextFrom.toUpperCase());
			nodePair8ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair8FromTextField.clear();
			nodePair8ToTextField.clear();
		}

		if ((nodePair9FromTextField.getText().trim() != null) && (nodePair9FromTextField.getText().trim() != "") && (nodePair9ToTextField.getText().trim() != null) && (nodePair9ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair9FromTextField.getText().trim();
			String origTextTo = nodePair9ToTextField.getText().trim();
			nodePair9FromTextField.setText(origTextFrom.toUpperCase());
			nodePair9ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair9FromTextField.clear();
			nodePair9ToTextField.clear();
		}

		if ((nodePair10FromTextField.getText().trim() != null) && (nodePair10FromTextField.getText().trim() != "") && (nodePair10ToTextField.getText().trim() != null) && (nodePair10ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair10FromTextField.getText().trim();
			String origTextTo = nodePair10ToTextField.getText().trim();
			nodePair10FromTextField.setText(origTextFrom.toUpperCase());
			nodePair10ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair10FromTextField.clear();
			nodePair10ToTextField.clear();
		}

		if ((nodePair11FromTextField.getText().trim() != null) && (nodePair11FromTextField.getText().trim() != "") && (nodePair11ToTextField.getText().trim() != null) && (nodePair11ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair11FromTextField.getText().trim();
			String origTextTo = nodePair11ToTextField.getText().trim();
			nodePair11FromTextField.setText(origTextFrom.toUpperCase());
			nodePair11ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair11FromTextField.clear();
			nodePair11ToTextField.clear();
		}

		if ((nodePair12FromTextField.getText().trim() != null) && (nodePair12FromTextField.getText().trim() != "") && (nodePair12ToTextField.getText().trim() != null) && (nodePair12ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair12FromTextField.getText().trim();
			String origTextTo = nodePair12ToTextField.getText().trim();
			nodePair12FromTextField.setText(origTextFrom.toUpperCase());
			nodePair12ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair12FromTextField.clear();
			nodePair12ToTextField.clear();
		}

		if ((nodePair13FromTextField.getText().trim() != null) && (nodePair13FromTextField.getText().trim() != "") && (nodePair13ToTextField.getText().trim() != null) && (nodePair13ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair13FromTextField.getText().trim();
			String origTextTo = nodePair13ToTextField.getText().trim();
			nodePair13FromTextField.setText(origTextFrom.toUpperCase());
			nodePair13ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair13FromTextField.clear();
			nodePair13ToTextField.clear();
		}

		if ((nodePair14FromTextField.getText().trim() != null) && (nodePair14FromTextField.getText().trim() != "") && (nodePair14ToTextField.getText().trim() != null) && (nodePair14ToTextField.getText().trim() != ""))
		{
			String origTextFrom = nodePair14FromTextField.getText().trim();
			String origTextTo = nodePair14ToTextField.getText().trim();
			nodePair14FromTextField.setText(origTextFrom.toUpperCase());
			nodePair14ToTextField.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair14FromTextField.clear();
			nodePair14ToTextField.clear();
		}

		// Write to registry
		String nodePairsToWriteToRegistry = "";
		if ((nodePair1FromTextField != null) && (nodePair1FromTextField.getText().trim() != "") && (nodePair1ToTextField != null) && (nodePair1ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair1FromTextField.getText() + ":" + nodePair1ToTextField.getText()+"," ;	
			nodePair1FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair1ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair2FromTextField != null) && (nodePair2FromTextField.getText().trim() != "") && (nodePair2ToTextField != null) && (nodePair2ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair2FromTextField.getText() + ":" + nodePair2ToTextField.getText()+"," ;	
			nodePair2FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair2ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair3FromTextField != null) && (nodePair3FromTextField.getText().trim() != "") && (nodePair3ToTextField != null) && (nodePair3ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair3FromTextField.getText() + ":" + nodePair3ToTextField.getText()+"," ;	
			nodePair3FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair3ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair4FromTextField != null) && (nodePair4FromTextField.getText().trim() != "") && (nodePair4ToTextField != null) && (nodePair4ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair4FromTextField.getText() + ":" + nodePair4ToTextField.getText()+"," ;	
			nodePair4FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair4ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair5FromTextField != null) && (nodePair5FromTextField.getText().trim() != "") && (nodePair5ToTextField != null) && (nodePair5ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair5FromTextField.getText() + ":" + nodePair5ToTextField.getText()+"," ;	
			nodePair5FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair5ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair6FromTextField != null) && (nodePair6FromTextField.getText().trim() != "") && (nodePair6ToTextField != null) && (nodePair6ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair6FromTextField.getText() + ":" + nodePair6ToTextField.getText()+"," ;	
			nodePair6FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair6ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair7FromTextField != null) && (nodePair7FromTextField.getText().trim() != "") && (nodePair7ToTextField != null) && (nodePair7ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair7FromTextField.getText() + ":" + nodePair7ToTextField.getText()+"," ;	
			nodePair7FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair7ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair8FromTextField != null) && (nodePair8FromTextField.getText().trim() != "") && (nodePair8ToTextField != null) && (nodePair8ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair8FromTextField.getText() + ":" + nodePair8ToTextField.getText()+"," ;	
			nodePair8FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair8ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair9FromTextField != null) && (nodePair9FromTextField.getText().trim() != "") && (nodePair9ToTextField != null) && (nodePair9ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair9FromTextField.getText() + ":" + nodePair9ToTextField.getText()+"," ;	
			nodePair9FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair9ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair10FromTextField != null) && (nodePair10FromTextField.getText().trim() != "") && (nodePair10ToTextField != null) && (nodePair10ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair10FromTextField.getText() + ":" + nodePair10ToTextField.getText()+"," ;	
			nodePair10FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair10ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair11FromTextField != null) && (nodePair11FromTextField.getText().trim() != "") && (nodePair11ToTextField != null) && (nodePair11ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair11FromTextField.getText() + ":" + nodePair11ToTextField.getText()+"," ;	
			nodePair11FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair11ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair12FromTextField != null) && (nodePair12FromTextField.getText().trim() != "") && (nodePair12ToTextField != null) && (nodePair12ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair12FromTextField.getText() + ":" + nodePair12ToTextField.getText()+"," ;	
			nodePair12FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair12ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair13FromTextField != null) && (nodePair13FromTextField.getText().trim() != "") && (nodePair13ToTextField != null) && (nodePair13ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair13FromTextField.getText() + ":" + nodePair13ToTextField.getText()+"," ;	
			nodePair13FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair13ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair14FromTextField != null) && (nodePair14FromTextField.getText().trim() != "") && (nodePair14ToTextField != null) && (nodePair14ToTextField.getText().trim() != ""))
		{
			nodePairsToWriteToRegistry += nodePair14FromTextField.getText() + ":" + nodePair14ToTextField.getText()+"," ;	
			nodePair14FromTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair14ToTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_nodePairs", nodePairsToWriteToRegistry);
	}

	public static String getLowRecoveryRate()
	{
		return lowRecoveryRate;
	}
	
	public static Integer getScheduleImprecisionOffsetInSeconds()
	{
		return schedulingImprecisionOffset;
	}

	public static String getRecoveryRateAnalysisTrainGroups()
	{
		return trainGroups;
	}

	public static String getRecoveryRateAnalysisNodePairs()
	{
		return nodePairs;
	}
}