package com.bl.bias.app;

import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BIASRecoveryRateAnalysisConfigController 
{
	private static Preferences prefs;

	private static String trainGroups;
	private static String setANodePairs;
	private static String setALowRecoveryRate;
	private static String setALabel;
	private static String setBNodePairs;
	private static String setBLowRecoveryRate;
	private static String setBLabel;
	private static String setCNodePairs;
	private static String setCLowRecoveryRate;
	private static String setCLabel;
	private static String setDNodePairs;
	private static String setDLowRecoveryRate;
	private static String setDLabel;

	private static Boolean analyzeSetA;
	private static Boolean analyzeSetB;
	private static Boolean analyzeSetC;
	private static Boolean analyzeSetD;

	private static Boolean defaultAnalyzeSetA = true;
	private static Boolean defaultAnalyzeSetB = true;
	private static Boolean defaultAnalyzeSetC = true;
	private static Boolean defaultAnalyzeSetD = true;

	private static Integer setASchedulingImprecisionOffset;
	private static Integer setBSchedulingImprecisionOffset;
	private static Integer setCSchedulingImprecisionOffset;
	private static Integer setDSchedulingImprecisionOffset;
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

	@FXML TextField nodePair1FromTextFieldC;
	@FXML TextField nodePair1ToTextFieldC;
	@FXML TextField nodePair2FromTextFieldC;
	@FXML TextField nodePair2ToTextFieldC;
	@FXML TextField nodePair3FromTextFieldC;
	@FXML TextField nodePair3ToTextFieldC;
	@FXML TextField nodePair4FromTextFieldC;
	@FXML TextField nodePair4ToTextFieldC;
	@FXML TextField nodePair5FromTextFieldC;
	@FXML TextField nodePair5ToTextFieldC;
	@FXML TextField nodePair6FromTextFieldC;
	@FXML TextField nodePair6ToTextFieldC;
	@FXML TextField nodePair7FromTextFieldC;
	@FXML TextField nodePair7ToTextFieldC;
	@FXML TextField nodePair8FromTextFieldC;
	@FXML TextField nodePair8ToTextFieldC;
	@FXML TextField nodePair9FromTextFieldC;
	@FXML TextField nodePair9ToTextFieldC;
	@FXML TextField nodePair10FromTextFieldC;
	@FXML TextField nodePair10ToTextFieldC;
	@FXML TextField nodePair11FromTextFieldC;
	@FXML TextField nodePair11ToTextFieldC;
	@FXML TextField nodePair12FromTextFieldC;
	@FXML TextField nodePair12ToTextFieldC;
	@FXML TextField nodePair13FromTextFieldC;
	@FXML TextField nodePair13ToTextFieldC;
	@FXML TextField nodePair14FromTextFieldC;
	@FXML TextField nodePair14ToTextFieldC;
	@FXML TextField nodePair15FromTextFieldC;
	@FXML TextField nodePair15ToTextFieldC;

	@FXML TextField nodePair1FromTextFieldD;
	@FXML TextField nodePair1ToTextFieldD;
	@FXML TextField nodePair2FromTextFieldD;
	@FXML TextField nodePair2ToTextFieldD;
	@FXML TextField nodePair3FromTextFieldD;
	@FXML TextField nodePair3ToTextFieldD;
	@FXML TextField nodePair4FromTextFieldD;
	@FXML TextField nodePair4ToTextFieldD;
	@FXML TextField nodePair5FromTextFieldD;
	@FXML TextField nodePair5ToTextFieldD;
	@FXML TextField nodePair6FromTextFieldD;
	@FXML TextField nodePair6ToTextFieldD;
	@FXML TextField nodePair7FromTextFieldD;
	@FXML TextField nodePair7ToTextFieldD;
	@FXML TextField nodePair8FromTextFieldD;
	@FXML TextField nodePair8ToTextFieldD;
	@FXML TextField nodePair9FromTextFieldD;
	@FXML TextField nodePair9ToTextFieldD;
	@FXML TextField nodePair10FromTextFieldD;
	@FXML TextField nodePair10ToTextFieldD;
	@FXML TextField nodePair11FromTextFieldD;
	@FXML TextField nodePair11ToTextFieldD;
	@FXML TextField nodePair12FromTextFieldD;
	@FXML TextField nodePair12ToTextFieldD;
	@FXML TextField nodePair13FromTextFieldD;
	@FXML TextField nodePair13ToTextFieldD;
	@FXML TextField nodePair14FromTextFieldD;
	@FXML TextField nodePair14ToTextFieldD;
	@FXML TextField nodePair15FromTextFieldD;
	@FXML TextField nodePair15ToTextFieldD;

	@FXML TextField setALabelTextField;
	@FXML TextField setBLabelTextField;
	@FXML TextField setCLabelTextField;
	@FXML TextField setDLabelTextField;

	@FXML Button updateGroupsButton;
	@FXML Button updateSetANodesButton;
	@FXML Button updateSetBNodesButton;
	@FXML Button updateSetCNodesButton;
	@FXML Button updateSetDNodesButton;
	@FXML Button updateSetALabelButton;
	@FXML Button updateSetBLabelButton;
	@FXML Button updateSetCLabelButton;
	@FXML Button updateSetDLabelButton;

	@FXML ComboBox<String> setALowRecoveryRateComboBox;
	@FXML ComboBox<String> setBLowRecoveryRateComboBox;
	@FXML ComboBox<String> setCLowRecoveryRateComboBox;
	@FXML ComboBox<String> setDLowRecoveryRateComboBox;

	@FXML RadioButton setANoSchedulingImprecisionRadioButton;
	@FXML RadioButton setAThirtySecondImprecisionRadioButton;
	@FXML RadioButton setAOneMinuteImprecisionRadioButton;

	@FXML RadioButton setBNoSchedulingImprecisionRadioButton;
	@FXML RadioButton setBThirtySecondImprecisionRadioButton;
	@FXML RadioButton setBOneMinuteImprecisionRadioButton;

	@FXML RadioButton setCNoSchedulingImprecisionRadioButton;
	@FXML RadioButton setCThirtySecondImprecisionRadioButton;
	@FXML RadioButton setCOneMinuteImprecisionRadioButton;

	@FXML RadioButton setDNoSchedulingImprecisionRadioButton;
	@FXML RadioButton setDThirtySecondImprecisionRadioButton;
	@FXML RadioButton setDOneMinuteImprecisionRadioButton;

	@FXML CheckBox analyzeSetACheckBox;
	@FXML CheckBox analyzeSetBCheckBox;
	@FXML CheckBox analyzeSetCCheckBox;
	@FXML CheckBox analyzeSetDCheckBox;

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

		// See if preferences are stored for Set C node pairs
		if ((prefs.get("rr_setCNodePairs", "") != null) && (prefs.get("rr_setCNodePairs", "") != ""))
		{
			setCNodePairs = prefs.get("rr_setCNodePairs", "");
			String nodePairC[] = setCNodePairs.split(",");

			for (int i = 0; i < nodePairC.length; i++)
			{
				if (i == 0)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair1FromTextFieldC.setText(fromTo[0]);
					nodePair1ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 1)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair2FromTextFieldC.setText(fromTo[0]);
					nodePair2ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 2)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair3FromTextFieldC.setText(fromTo[0]);
					nodePair3ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 3)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair4FromTextFieldC.setText(fromTo[0]);
					nodePair4ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 4)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair5FromTextFieldC.setText(fromTo[0]);
					nodePair5ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 5)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair6FromTextFieldC.setText(fromTo[0]);
					nodePair6ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 6)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair7FromTextFieldC.setText(fromTo[0]);
					nodePair7ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 7)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair8FromTextFieldC.setText(fromTo[0]);
					nodePair8ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 8)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair9FromTextFieldC.setText(fromTo[0]);
					nodePair9ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 9)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair10FromTextFieldC.setText(fromTo[0]);
					nodePair10ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 10)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair11FromTextFieldC.setText(fromTo[0]);
					nodePair11ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 11)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair12FromTextFieldC.setText(fromTo[0]);
					nodePair12ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 12)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair13FromTextFieldC.setText(fromTo[0]);
					nodePair13ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 13)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair14FromTextFieldC.setText(fromTo[0]);
					nodePair14ToTextFieldC.setText(fromTo[1]);
				}
				else if (i == 14)
				{
					String[] fromTo = nodePairC[i].split(":"); 
					nodePair15FromTextFieldC.setText(fromTo[0]);
					nodePair15ToTextFieldC.setText(fromTo[1]);
				}
			}
		}

		// See if preferences are stored for Set D node pairs
		if ((prefs.get("rr_setDNodePairs", "") != null) && (prefs.get("rr_setDNodePairs", "") != ""))
		{
			setDNodePairs = prefs.get("rr_setDNodePairs", "");
			String nodePairD[] = setDNodePairs.split(",");

			for (int i = 0; i < nodePairD.length; i++)
			{
				if (i == 0)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair1FromTextFieldD.setText(fromTo[0]);
					nodePair1ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 1)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair2FromTextFieldD.setText(fromTo[0]);
					nodePair2ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 2)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair3FromTextFieldD.setText(fromTo[0]);
					nodePair3ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 3)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair4FromTextFieldD.setText(fromTo[0]);
					nodePair4ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 4)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair5FromTextFieldD.setText(fromTo[0]);
					nodePair5ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 5)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair6FromTextFieldD.setText(fromTo[0]);
					nodePair6ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 6)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair7FromTextFieldD.setText(fromTo[0]);
					nodePair7ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 7)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair8FromTextFieldD.setText(fromTo[0]);
					nodePair8ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 8)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair9FromTextFieldD.setText(fromTo[0]);
					nodePair9ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 9)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair10FromTextFieldD.setText(fromTo[0]);
					nodePair10ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 10)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair11FromTextFieldD.setText(fromTo[0]);
					nodePair11ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 11)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair12FromTextFieldD.setText(fromTo[0]);
					nodePair12ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 12)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair13FromTextFieldD.setText(fromTo[0]);
					nodePair13ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 13)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair14FromTextFieldD.setText(fromTo[0]);
					nodePair14ToTextFieldD.setText(fromTo[1]);
				}
				else if (i == 14)
				{
					String[] fromTo = nodePairD[i].split(":"); 
					nodePair15FromTextFieldD.setText(fromTo[0]);
					nodePair15ToTextFieldD.setText(fromTo[1]);
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

		// See if preference is stored for scheduling imprecision offset Set C
		if (prefs.getInt("rr_setCSchedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 0)
		{
			setCSchedulingImprecisionOffset = 0;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_setCSchedulingImprecisionOffset", 0);
			setCNoSchedulingImprecisionRadioButton.setSelected(true);
		}
		else if (prefs.getInt("rr_setCSchedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 30)
		{
			setCSchedulingImprecisionOffset = 30;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_setCSchedulingImprecisionOffset", 30);
			setCThirtySecondImprecisionRadioButton.setSelected(true);
		}
		else if (prefs.getInt("rr_setCSchedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 60)
		{
			setCSchedulingImprecisionOffset = 60;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_setCSchedulingImprecisionOffset", 60);
			setCOneMinuteImprecisionRadioButton.setSelected(true);
		}

		// See if preference is stored for scheduling imprecision offset Set D
		if (prefs.getInt("rr_setDSchedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 0)
		{
			setDSchedulingImprecisionOffset = 0;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_setDSchedulingImprecisionOffset", 0);
			setDNoSchedulingImprecisionRadioButton.setSelected(true);
		}
		else if (prefs.getInt("rr_setDSchedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 30)
		{
			setDSchedulingImprecisionOffset = 30;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_setDSchedulingImprecisionOffset", 30);
			setDThirtySecondImprecisionRadioButton.setSelected(true);
		}
		else if (prefs.getInt("rr_setDSchedulingImprecisionOffset", defaultSchedulingImprecisionOffset) == 60)
		{
			setDSchedulingImprecisionOffset = 60;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("rr_setDSchedulingImprecisionOffset", 60);
			setDOneMinuteImprecisionRadioButton.setSelected(true);
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

		// See if preference is stored for low recovery rate Set C
		setCLowRecoveryRateComboBox.setItems(lowRecoveryRates);
		boolean lowRecoveryRateExistsC = prefs.get("rr_setCLowRecoveryRate", null) != null;
		if (lowRecoveryRateExistsC)
		{
			setCLowRecoveryRate = prefs.get("rr_setCLowRecoveryRate", defaultLowRecoveryRate);
			setCLowRecoveryRateComboBox.getSelectionModel().select(setCLowRecoveryRate);
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("rr_setCLowRecoveryRate", defaultLowRecoveryRate);
			}
			setCLowRecoveryRate = prefs.get("rr_setCLowRecoveryRate", defaultLowRecoveryRate);
			setCLowRecoveryRateComboBox.getSelectionModel().select(defaultLowRecoveryRate);
		}

		// See if preference is stored for low recovery rate Set D
		setDLowRecoveryRateComboBox.setItems(lowRecoveryRates);
		boolean lowRecoveryRateExistsD = prefs.get("rr_setDLowRecoveryRate", null) != null;
		if (lowRecoveryRateExistsD)
		{
			setDLowRecoveryRate = prefs.get("rr_setDLowRecoveryRate", defaultLowRecoveryRate);
			setDLowRecoveryRateComboBox.getSelectionModel().select(setDLowRecoveryRate);
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("rr_setDLowRecoveryRate", defaultLowRecoveryRate);
			}
			setDLowRecoveryRate = prefs.get("rr_setDLowRecoveryRate", defaultLowRecoveryRate);
			setDLowRecoveryRateComboBox.getSelectionModel().select(defaultLowRecoveryRate);
		}

		// See if preference is stored for Set A label
		boolean setALabelExists = prefs.get("rr_setALabel", null) != null;
		if (setALabelExists)
		{
			setALabel = prefs.get("rr_setALabel", "");
			setALabelTextField.setText(setALabel);
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("rr_setALabel", "");
			}
			setALabel = prefs.get("rr_setALabel", "");
		}

		// See if preference is stored for Set B label
		boolean setBLabelExists = prefs.get("rr_setBLabel", null) != null;
		if (setBLabelExists)
		{
			setBLabel = prefs.get("rr_setBLabel", "");
			setBLabelTextField.setText(setBLabel);
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("rr_setBLabel", "");
			}
			setBLabel = prefs.get("rr_setBLabel", "");
		}

		// See if preference is stored for Set C label
		boolean setCLabelExists = prefs.get("rr_setCLabel", null) != null;
		if (setCLabelExists)
		{
			setCLabel = prefs.get("rr_setCLabel", "");
			setCLabelTextField.setText(setCLabel);
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("rr_setCLabel", "");
			}
			setCLabel = prefs.get("rr_setCLabel", "");
		}

		// See if preference is stored for Set D label
		boolean setDLabelExists = prefs.get("rr_setDLabel", null) != null;
		if (setDLabelExists)
		{
			setDLabel = prefs.get("rr_setDLabel", "");
			setDLabelTextField.setText(setDLabel);
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("rr_setDLabel", "");
			}
			setDLabel = prefs.get("rr_setDLabel", "");
		}

		// See if preference is stored for analyzing Set A
		if (prefs.getBoolean("rr_analyzeSetA", defaultAnalyzeSetA))
		{
			analyzeSetA = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rr_analyzeSetA", true);
			analyzeSetACheckBox.setSelected(true);
		}
		else
		{
			analyzeSetA = false;
			analyzeSetACheckBox.setSelected(false);
		}

		// See if preference is stored for analyzing Set B
		if (prefs.getBoolean("rr_analyzeSetB", defaultAnalyzeSetB))
		{
			analyzeSetB = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rr_analyzeSetB", true);
			analyzeSetBCheckBox.setSelected(true);
		}
		else
		{
			analyzeSetB = false;
			analyzeSetBCheckBox.setSelected(false);
		}

		// See if preference is stored for analyzing Set C
		if (prefs.getBoolean("rr_analyzeSetC", defaultAnalyzeSetC))
		{
			analyzeSetC = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rr_analyzeSetC", true);
			analyzeSetCCheckBox.setSelected(true);
		}
		else
		{
			analyzeSetC = false;
			analyzeSetCCheckBox.setSelected(false);
		}

		// See if preference is stored for analyzing Set D
		if (prefs.getBoolean("rr_analyzeSetD", defaultAnalyzeSetD))
		{
			analyzeSetD = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rr_analyzeSetD", true);
			analyzeSetDCheckBox.setSelected(true);
		}
		else
		{
			analyzeSetD = false;
			analyzeSetDCheckBox.setSelected(false);
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

	// Node pair # 1 of 15 set C
	@FXML private void handleNodePairFrom1TextFieldC()
	{
		String origText = nodePair1FromTextFieldC.getText().trim();
		nodePair1FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom1TextFieldC()
	{
		nodePair1FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo1TextFieldC()
	{
		String origText = nodePair1ToTextFieldC.getText().trim();
		nodePair1ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo1TextFieldC()
	{
		nodePair1ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 2 of 15 set C
	@FXML private void handleNodePairFrom2TextFieldC()
	{
		String origText = nodePair2FromTextFieldC.getText().trim();
		nodePair2FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom2TextFieldC()
	{
		nodePair2FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo2TextFieldC()
	{
		String origText = nodePair2ToTextFieldC.getText().trim();
		nodePair2ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo2TextFieldC()
	{
		nodePair2ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 3 of 15 set C
	@FXML private void handleNodePairFrom3TextFieldC()
	{
		String origText = nodePair3FromTextFieldC.getText().trim();
		nodePair3FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom3TextFieldC()
	{
		nodePair3FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo3TextFieldC()
	{
		String origText = nodePair3ToTextFieldC.getText().trim();
		nodePair3ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo3TextFieldC()
	{
		nodePair3ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 4 of 15 set C
	@FXML private void handleNodePairFrom4TextFieldC()
	{
		String origText = nodePair4FromTextFieldC.getText().trim();
		nodePair4FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom4TextFieldC()
	{
		nodePair4FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo4TextFieldC()
	{
		String origText = nodePair4ToTextFieldC.getText().trim();
		nodePair4ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo4TextFieldC()
	{
		nodePair4ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}	

	// Node pair # 5 of 15 set C
	@FXML private void handleNodePairFrom5TextFieldC()
	{
		String origText = nodePair5FromTextFieldC.getText().trim();
		nodePair5FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom5TextFieldC()
	{
		nodePair5FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo5TextFieldC()
	{
		String origText = nodePair5ToTextFieldC.getText().trim();
		nodePair5ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo5TextFieldC()
	{
		nodePair5ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 6 of 15 set C
	@FXML private void handleNodePairFrom6TextFieldC()
	{
		String origText = nodePair6FromTextFieldC.getText().trim();
		nodePair6FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom6TextFieldC()
	{
		nodePair6FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo6TextFieldC()
	{
		String origText = nodePair6ToTextFieldC.getText().trim();
		nodePair6ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo6TextFieldC()
	{
		nodePair6ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 7 of 15 set C
	@FXML private void handleNodePairFrom7TextFieldC()
	{
		String origText = nodePair7FromTextFieldC.getText().trim();
		nodePair7FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom7TextFieldC()
	{
		nodePair7FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo7TextFieldC()
	{
		String origText = nodePair7ToTextFieldC.getText().trim();
		nodePair7ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo7TextFieldC()
	{
		nodePair7ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 8 of 15 set C
	@FXML private void handleNodePairFrom8TextFieldC()
	{
		String origText = nodePair8FromTextFieldC.getText().trim();
		nodePair8FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom8TextFieldC()
	{
		nodePair8FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo8TextFieldC()
	{
		String origText = nodePair8ToTextFieldC.getText().trim();
		nodePair8ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo8TextFieldC()
	{
		nodePair8ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 9 of 15 set C
	@FXML private void handleNodePairFrom9TextFieldC()
	{
		String origText = nodePair9FromTextFieldC.getText().trim();
		nodePair9FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom9TextFieldC()
	{
		nodePair9FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo9TextFieldC()
	{
		String origText = nodePair9ToTextFieldC.getText().trim();
		nodePair9ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo9TextFieldC()
	{
		nodePair9ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 10 of 15 set C
	@FXML private void handleNodePairFrom10TextFieldC()
	{
		String origText = nodePair10FromTextFieldC.getText().trim();
		nodePair10FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom10TextFieldC()
	{
		nodePair10FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo10TextFieldC()
	{
		String origText = nodePair10ToTextFieldC.getText().trim();
		nodePair10ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo10TextFieldC()
	{
		nodePair10ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 11 of 15 set C
	@FXML private void handleNodePairFrom11TextFieldC()
	{
		String origText = nodePair11FromTextFieldC.getText().trim();
		nodePair11FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom11TextFieldC()
	{
		nodePair11FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo11TextFieldC()
	{
		String origText = nodePair11ToTextFieldC.getText().trim();
		nodePair11ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo11TextFieldC()
	{
		nodePair11ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 12 of 15 set C
	@FXML private void handleNodePairFrom12TextFieldC()
	{
		String origText = nodePair12FromTextFieldC.getText().trim();
		nodePair12FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom12TextFieldC()
	{
		nodePair12FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo12TextFieldC()
	{
		String origText = nodePair12ToTextFieldC.getText().trim();
		nodePair12ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo12TextFieldC()
	{
		nodePair12ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 13 of 15 set C
	@FXML private void handleNodePairFrom13TextFieldC()
	{
		String origText = nodePair13FromTextFieldC.getText().trim();
		nodePair13FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom13TextFieldC()
	{
		nodePair13FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo13TextFieldC()
	{
		String origText = nodePair13ToTextFieldC.getText().trim();
		nodePair13ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo13TextFieldC()
	{
		nodePair13ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 14 of 15 set C
	@FXML private void handleNodePairFrom14TextFieldC()
	{
		String origText = nodePair14FromTextFieldC.getText().trim();
		nodePair14FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom14TextFieldC()
	{
		nodePair14FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo14TextFieldC()
	{
		String origText = nodePair14ToTextFieldC.getText().trim();
		nodePair14ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo14TextFieldC()
	{
		nodePair14ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 15 of 15 set C
	@FXML private void handleNodePairFrom15TextFieldC()
	{
		String origText = nodePair15FromTextFieldC.getText().trim();
		nodePair15FromTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom15TextFieldC()
	{
		nodePair15FromTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo15TextFieldC()
	{
		String origText = nodePair15ToTextFieldC.getText().trim();
		nodePair15ToTextFieldC.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo15TextFieldC()
	{
		nodePair15ToTextFieldC.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetCNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 1 of 15 set D
	@FXML private void handleNodePairFrom1TextFieldD()
	{
		String origText = nodePair1FromTextFieldD.getText().trim();
		nodePair1FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom1TextFieldD()
	{
		nodePair1FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo1TextFieldD()
	{
		String origText = nodePair1ToTextFieldD.getText().trim();
		nodePair1ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo1TextFieldD()
	{
		nodePair1ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 2 of 15 set D
	@FXML private void handleNodePairFrom2TextFieldD()
	{
		String origText = nodePair2FromTextFieldD.getText().trim();
		nodePair2FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom2TextFieldD()
	{
		nodePair2FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo2TextFieldD()
	{
		String origText = nodePair2ToTextFieldD.getText().trim();
		nodePair2ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo2TextFieldD()
	{
		nodePair2ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 3 of 15 set D
	@FXML private void handleNodePairFrom3TextFieldD()
	{
		String origText = nodePair3FromTextFieldD.getText().trim();
		nodePair3FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom3TextFieldD()
	{
		nodePair3FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo3TextFieldD()
	{
		String origText = nodePair3ToTextFieldD.getText().trim();
		nodePair3ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo3TextFieldD()
	{
		nodePair3ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 4 of 15 set D
	@FXML private void handleNodePairFrom4TextFieldD()
	{
		String origText = nodePair4FromTextFieldD.getText().trim();
		nodePair4FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom4TextFieldD()
	{
		nodePair4FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo4TextFieldD()
	{
		String origText = nodePair4ToTextFieldD.getText().trim();
		nodePair4ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo4TextFieldD()
	{
		nodePair4ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}	

	// Node pair # 5 of 15 set D
	@FXML private void handleNodePairFrom5TextFieldD()
	{
		String origText = nodePair5FromTextFieldD.getText().trim();
		nodePair5FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom5TextFieldD()
	{
		nodePair5FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo5TextFieldD()
	{
		String origText = nodePair5ToTextFieldD.getText().trim();
		nodePair5ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo5TextFieldD()
	{
		nodePair5ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 6 of 15 set D
	@FXML private void handleNodePairFrom6TextFieldD()
	{
		String origText = nodePair6FromTextFieldD.getText().trim();
		nodePair6FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom6TextFieldD()
	{
		nodePair6FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo6TextFieldD()
	{
		String origText = nodePair6ToTextFieldD.getText().trim();
		nodePair6ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo6TextFieldD()
	{
		nodePair6ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 7 of 15 set D
	@FXML private void handleNodePairFrom7TextFieldD()
	{
		String origText = nodePair7FromTextFieldD.getText().trim();
		nodePair7FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom7TextFieldD()
	{
		nodePair7FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo7TextFieldD()
	{
		String origText = nodePair7ToTextFieldD.getText().trim();
		nodePair7ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo7TextFieldD()
	{
		nodePair7ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 8 of 15 set D
	@FXML private void handleNodePairFrom8TextFieldD()
	{
		String origText = nodePair8FromTextFieldD.getText().trim();
		nodePair8FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom8TextFieldD()
	{
		nodePair8FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo8TextFieldD()
	{
		String origText = nodePair8ToTextFieldD.getText().trim();
		nodePair8ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo8TextFieldD()
	{
		nodePair8ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 9 of 15 set D
	@FXML private void handleNodePairFrom9TextFieldD()
	{
		String origText = nodePair9FromTextFieldD.getText().trim();
		nodePair9FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom9TextFieldD()
	{
		nodePair9FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo9TextFieldD()
	{
		String origText = nodePair9ToTextFieldD.getText().trim();
		nodePair9ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo9TextFieldD()
	{
		nodePair9ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 10 of 15 set D
	@FXML private void handleNodePairFrom10TextFieldD()
	{
		String origText = nodePair10FromTextFieldD.getText().trim();
		nodePair10FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom10TextFieldD()
	{
		nodePair10FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo10TextFieldD()
	{
		String origText = nodePair10ToTextFieldD.getText().trim();
		nodePair10ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo10TextFieldD()
	{
		nodePair10ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 11 of 15 set D
	@FXML private void handleNodePairFrom11TextFieldD()
	{
		String origText = nodePair11FromTextFieldD.getText().trim();
		nodePair11FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom11TextFieldD()
	{
		nodePair11FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo11TextFieldD()
	{
		String origText = nodePair11ToTextFieldD.getText().trim();
		nodePair11ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo11TextFieldD()
	{
		nodePair11ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 12 of 15 set D
	@FXML private void handleNodePairFrom12TextFieldD()
	{
		String origText = nodePair12FromTextFieldD.getText().trim();
		nodePair12FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom12TextFieldD()
	{
		nodePair12FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo12TextFieldD()
	{
		String origText = nodePair12ToTextFieldD.getText().trim();
		nodePair12ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo12TextFieldD()
	{
		nodePair12ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 13 of 15 set D
	@FXML private void handleNodePairFrom13TextFieldD()
	{
		String origText = nodePair13FromTextFieldD.getText().trim();
		nodePair13FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom13TextFieldD()
	{
		nodePair13FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo13TextFieldD()
	{
		String origText = nodePair13ToTextFieldD.getText().trim();
		nodePair13ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo13TextFieldD()
	{
		nodePair13ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 14 of 15 set D
	@FXML private void handleNodePairFrom14TextFieldD()
	{
		String origText = nodePair14FromTextFieldD.getText().trim();
		nodePair14FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom14TextFieldD()
	{
		nodePair14FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo14TextFieldD()
	{
		String origText = nodePair14ToTextFieldD.getText().trim();
		nodePair14ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo14TextFieldD()
	{
		nodePair14ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node pair # 15 of 15 set D
	@FXML private void handleNodePairFrom15TextFieldD()
	{
		String origText = nodePair15FromTextFieldD.getText().trim();
		nodePair15FromTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairFrom15TextFieldD()
	{
		nodePair15FromTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNodePairTo15TextFieldD()
	{
		String origText = nodePair15ToTextFieldD.getText().trim();
		nodePair15ToTextFieldD.setText(origText.toUpperCase());	
	}

	@FXML private void handleTextChangedNodePairTo15TextFieldD()
	{
		nodePair15ToTextFieldD.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateSetDNodesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	@FXML private void handleSetCLowRecoveryRateComboBox()
	{
		setCLowRecoveryRate = setCLowRecoveryRateComboBox.getSelectionModel().getSelectedItem().toString();
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_setCLowRecoveryRate", setCLowRecoveryRate);				
	}

	@FXML private void handleSetDLowRecoveryRateComboBox()
	{
		setDLowRecoveryRate = setDLowRecoveryRateComboBox.getSelectionModel().getSelectedItem().toString();
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_setDLowRecoveryRate", setCLowRecoveryRate);				
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

	@FXML private void handleSetCNoSchedulingImprecisionRadioButton()
	{
		setCSchedulingImprecisionOffset = 0;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_setCSchedulingImprecisionOffset", setCSchedulingImprecisionOffset);
	}

	@FXML private void handleSetCThirtySecondImprecisionRadioButton()
	{
		setCSchedulingImprecisionOffset = 30;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_setCSchedulingImprecisionOffset", setCSchedulingImprecisionOffset);
	}

	@FXML private void handleSetCOneMinuteImprecisionRadioButton()
	{
		setCSchedulingImprecisionOffset = 60;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_setCSchedulingImprecisionOffset", setCSchedulingImprecisionOffset);
	}

	@FXML private void handleSetDNoSchedulingImprecisionRadioButton()
	{
		setDSchedulingImprecisionOffset = 0;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_setDSchedulingImprecisionOffset", setDSchedulingImprecisionOffset);
	}

	@FXML private void handleSetDThirtySecondImprecisionRadioButton()
	{
		setDSchedulingImprecisionOffset = 30;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_setDSchedulingImprecisionOffset", setDSchedulingImprecisionOffset);
	}

	@FXML private void handleSetDOneMinuteImprecisionRadioButton()
	{
		setDSchedulingImprecisionOffset = 60;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("rr_setDSchedulingImprecisionOffset", setDSchedulingImprecisionOffset);
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

		// Check for duplicates in set A
		Boolean duplicateFound = false;
		outerLoop:
			for (int i = 0; i < nodePairsToWriteToRegistryA.split(",").length; i++)
			{
				for (int j = 0; j < nodePairsToWriteToRegistryA.split(",").length; j++)
				{
					if (i == j)
						continue;
					else if ((nodePairsToWriteToRegistryA.split(",")[i].split(":")[0].equals(nodePairsToWriteToRegistryA.split(",")[j].split(":")[0]) 
							&& (nodePairsToWriteToRegistryA.split(",")[i].split(":")[1].equals(nodePairsToWriteToRegistryA.split(",")[j].split(":")[1]))))
					{
						duplicateFound = true;

						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText(null);
						alert.setContentText("Duplicate pair of nodes found "+nodePairsToWriteToRegistryA.split(",")[i].split(":")[0]+" to "+nodePairsToWriteToRegistryA.split(",")[i].split(":")[1]+".  Restoring previous values.");
						Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
						stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
						alert.show();

						setANodePairs = prefs.get("rr_setANodePairs", "");
						String nodePairA[] = setANodePairs.split(",");

						for (int k = 0; k < nodePairA.length; k++)
						{
							if (k == 0)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair1FromTextFieldA.setText(fromTo[0]);
								nodePair1ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 1)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair2FromTextFieldA.setText(fromTo[0]);
								nodePair2ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 2)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair3FromTextFieldA.setText(fromTo[0]);
								nodePair3ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 3)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair4FromTextFieldA.setText(fromTo[0]);
								nodePair4ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 4)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair5FromTextFieldA.setText(fromTo[0]);
								nodePair5ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 5)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair6FromTextFieldA.setText(fromTo[0]);
								nodePair6ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 6)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair7FromTextFieldA.setText(fromTo[0]);
								nodePair7ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 7)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair8FromTextFieldA.setText(fromTo[0]);
								nodePair8ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 8)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair9FromTextFieldA.setText(fromTo[0]);
								nodePair9ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 9)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair10FromTextFieldA.setText(fromTo[0]);
								nodePair10ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 10)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair11FromTextFieldA.setText(fromTo[0]);
								nodePair11ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 11)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair12FromTextFieldA.setText(fromTo[0]);
								nodePair12ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 12)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair13FromTextFieldA.setText(fromTo[0]);
								nodePair13ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 13)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair14FromTextFieldA.setText(fromTo[0]);
								nodePair14ToTextFieldA.setText(fromTo[1]);
							}
							else if (k == 14)
							{
								String[] fromTo = nodePairA[k].split(":"); 
								nodePair15FromTextFieldA.setText(fromTo[0]);
								nodePair15ToTextFieldA.setText(fromTo[1]);
							}
						}

						for (int k = nodePairA.length; k < 15; k++)
						{
							if (k == 0)
							{
								nodePair1FromTextFieldA.setText("");
								nodePair1ToTextFieldA.setText("");
							}
							else if (k == 1)
							{
								nodePair2FromTextFieldA.setText("");
								nodePair2ToTextFieldA.setText("");
							}
							else if (k == 2)
							{
								nodePair3FromTextFieldA.setText("");
								nodePair3ToTextFieldA.setText("");
							}
							else if (k == 3)
							{
								nodePair4FromTextFieldA.setText("");
								nodePair4ToTextFieldA.setText("");
							}
							else if (k == 4)
							{
								nodePair5FromTextFieldA.setText("");
								nodePair5ToTextFieldA.setText("");
							}
							else if (k == 5)
							{
								nodePair6FromTextFieldA.setText("");
								nodePair6ToTextFieldA.setText("");
							}
							else if (k == 6)
							{
								nodePair7FromTextFieldA.setText("");
								nodePair7ToTextFieldA.setText("");
							}
							else if (k == 7)
							{
								nodePair8FromTextFieldA.setText("");
								nodePair8ToTextFieldA.setText("");
							}
							else if (k == 8)
							{
								nodePair9FromTextFieldA.setText("");
								nodePair9ToTextFieldA.setText("");
							}
							else if (k == 9)
							{
								nodePair10FromTextFieldA.setText("");
								nodePair10ToTextFieldA.setText("");
							}
							else if (k == 10)
							{
								nodePair11FromTextFieldA.setText("");
								nodePair11ToTextFieldA.setText("");
							}
							else if (k == 11)
							{
								nodePair12FromTextFieldA.setText("");
								nodePair12ToTextFieldA.setText("");
							}
							else if (k == 12)
							{
								nodePair13FromTextFieldA.setText("");
								nodePair13ToTextFieldA.setText("");
							}
							else if (k == 13)
							{
								nodePair14FromTextFieldA.setText("");
								nodePair14ToTextFieldA.setText("");
							}
							else if (k == 14)
							{
								nodePair15FromTextFieldA.setText("");
								nodePair15ToTextFieldA.setText("");
							}
						}
						break outerLoop;
					}
				}
			}

		if (!duplicateFound)
		{
			updateSetANodesButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("rr_setANodePairs", nodePairsToWriteToRegistryA);

			setANodePairs = nodePairsToWriteToRegistryA;
		}
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

		// Check for duplicates in set B
		Boolean duplicateFound = false;
		outerLoop:
			for (int i = 0; i < nodePairsToWriteToRegistryB.split(",").length; i++)
			{
				for (int j = 0; j < nodePairsToWriteToRegistryB.split(",").length; j++)
				{
					if (i == j)
						continue;
					else if ((nodePairsToWriteToRegistryB.split(",")[i].split(":")[0].equals(nodePairsToWriteToRegistryB.split(",")[j].split(":")[0]) 
							&& (nodePairsToWriteToRegistryB.split(",")[i].split(":")[1].equals(nodePairsToWriteToRegistryB.split(",")[j].split(":")[1]))))
					{
						duplicateFound = true;

						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText(null);
						alert.setContentText("Duplicate pair of nodes found "+nodePairsToWriteToRegistryB.split(",")[i].split(":")[0]+" to "+nodePairsToWriteToRegistryB.split(",")[i].split(":")[1]+".  Restoring previous values.");
						Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
						stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
						alert.show();

						setBNodePairs = prefs.get("rr_setBNodePairs", "");
						String nodePairB[] = setBNodePairs.split(",");

						for (int k = 0; k < nodePairB.length; k++)
						{
							if (k == 0)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair1FromTextFieldB.setText(fromTo[0]);
								nodePair1ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 1)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair2FromTextFieldB.setText(fromTo[0]);
								nodePair2ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 2)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair3FromTextFieldB.setText(fromTo[0]);
								nodePair3ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 3)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair4FromTextFieldB.setText(fromTo[0]);
								nodePair4ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 4)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair5FromTextFieldB.setText(fromTo[0]);
								nodePair5ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 5)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair6FromTextFieldB.setText(fromTo[0]);
								nodePair6ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 6)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair7FromTextFieldB.setText(fromTo[0]);
								nodePair7ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 7)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair8FromTextFieldB.setText(fromTo[0]);
								nodePair8ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 8)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair9FromTextFieldB.setText(fromTo[0]);
								nodePair9ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 9)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair10FromTextFieldB.setText(fromTo[0]);
								nodePair10ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 10)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair11FromTextFieldB.setText(fromTo[0]);
								nodePair11ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 11)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair12FromTextFieldB.setText(fromTo[0]);
								nodePair12ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 12)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair13FromTextFieldB.setText(fromTo[0]);
								nodePair13ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 13)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair14FromTextFieldB.setText(fromTo[0]);
								nodePair14ToTextFieldB.setText(fromTo[1]);
							}
							else if (k == 14)
							{
								String[] fromTo = nodePairB[k].split(":"); 
								nodePair15FromTextFieldB.setText(fromTo[0]);
								nodePair15ToTextFieldB.setText(fromTo[1]);
							}
						}

						for (int k = nodePairB.length; k < 15; k++)
						{
							if (k == 0)
							{
								nodePair1FromTextFieldB.setText("");
								nodePair1ToTextFieldB.setText("");
							}
							else if (k == 1)
							{
								nodePair2FromTextFieldB.setText("");
								nodePair2ToTextFieldB.setText("");
							}
							else if (k == 2)
							{
								nodePair3FromTextFieldB.setText("");
								nodePair3ToTextFieldB.setText("");
							}
							else if (k == 3)
							{
								nodePair4FromTextFieldB.setText("");
								nodePair4ToTextFieldB.setText("");
							}
							else if (k == 4)
							{
								nodePair5FromTextFieldB.setText("");
								nodePair5ToTextFieldB.setText("");
							}
							else if (k == 5)
							{
								nodePair6FromTextFieldB.setText("");
								nodePair6ToTextFieldB.setText("");
							}
							else if (k == 6)
							{
								nodePair7FromTextFieldB.setText("");
								nodePair7ToTextFieldB.setText("");
							}
							else if (k == 7)
							{
								nodePair8FromTextFieldB.setText("");
								nodePair8ToTextFieldB.setText("");
							}
							else if (k == 8)
							{
								nodePair9FromTextFieldB.setText("");
								nodePair9ToTextFieldB.setText("");
							}
							else if (k == 9)
							{
								nodePair10FromTextFieldB.setText("");
								nodePair10ToTextFieldB.setText("");
							}
							else if (k == 10)
							{
								nodePair11FromTextFieldB.setText("");
								nodePair11ToTextFieldB.setText("");
							}
							else if (k == 11)
							{
								nodePair12FromTextFieldB.setText("");
								nodePair12ToTextFieldB.setText("");
							}
							else if (k == 12)
							{
								nodePair13FromTextFieldB.setText("");
								nodePair13ToTextFieldB.setText("");
							}
							else if (k == 13)
							{
								nodePair14FromTextFieldB.setText("");
								nodePair14ToTextFieldB.setText("");
							}
							else if (k == 14)
							{
								nodePair15FromTextFieldB.setText("");
								nodePair15ToTextFieldB.setText("");
							}
						}
						break outerLoop;
					}
				}
			}

		if (!duplicateFound)
		{
			updateSetBNodesButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("rr_setBNodePairs", nodePairsToWriteToRegistryB);

			setBNodePairs = nodePairsToWriteToRegistryB;
		}
	}

	@FXML private void handleUpdateSetCNodesButton()
	{
		updateSetCNodesButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		// Eliminate pairs that are missing one node
		if ((nodePair1FromTextFieldC.getText().trim() != null) && (nodePair1FromTextFieldC.getText().trim() != "") && (nodePair1ToTextFieldC.getText().trim() != null) && (nodePair1ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair1FromTextFieldC.getText().trim();
			String origTextTo = nodePair1ToTextFieldC.getText().trim();
			nodePair1FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair1ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair1FromTextFieldC.clear();
			nodePair1ToTextFieldC.clear();
		}

		if ((nodePair2FromTextFieldC.getText().trim() != null) && (nodePair2FromTextFieldC.getText().trim() != "") && (nodePair2ToTextFieldC.getText().trim() != null) && (nodePair2ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair2FromTextFieldC.getText().trim();
			String origTextTo = nodePair2ToTextFieldC.getText().trim();
			nodePair2FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair2ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair2FromTextFieldC.clear();
			nodePair2ToTextFieldC.clear();
		}

		if ((nodePair3FromTextFieldC.getText().trim() != null) && (nodePair3FromTextFieldC.getText().trim() != "") && (nodePair3ToTextFieldC.getText().trim() != null) && (nodePair3ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair3FromTextFieldC.getText().trim();
			String origTextTo = nodePair3ToTextFieldC.getText().trim();
			nodePair3FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair3ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair3FromTextFieldC.clear();
			nodePair3ToTextFieldC.clear();
		}

		if ((nodePair4FromTextFieldC.getText().trim() != null) && (nodePair4FromTextFieldC.getText().trim() != "") && (nodePair4ToTextFieldC.getText().trim() != null) && (nodePair4ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair4FromTextFieldC.getText().trim();
			String origTextTo = nodePair4ToTextFieldC.getText().trim();
			nodePair4FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair4ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair4FromTextFieldC.clear();
			nodePair4ToTextFieldC.clear();
		}

		if ((nodePair5FromTextFieldC.getText().trim() != null) && (nodePair5FromTextFieldC.getText().trim() != "") && (nodePair5ToTextFieldC.getText().trim() != null) && (nodePair5ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair5FromTextFieldC.getText().trim();
			String origTextTo = nodePair5ToTextFieldC.getText().trim();
			nodePair5FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair5ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair5FromTextFieldC.clear();
			nodePair5ToTextFieldC.clear();
		}

		if ((nodePair6FromTextFieldC.getText().trim() != null) && (nodePair6FromTextFieldC.getText().trim() != "") && (nodePair6ToTextFieldC.getText().trim() != null) && (nodePair6ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair6FromTextFieldC.getText().trim();
			String origTextTo = nodePair6ToTextFieldC.getText().trim();
			nodePair6FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair6ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair6FromTextFieldC.clear();
			nodePair6ToTextFieldC.clear();
		}

		if ((nodePair7FromTextFieldC.getText().trim() != null) && (nodePair7FromTextFieldC.getText().trim() != "") && (nodePair7ToTextFieldC.getText().trim() != null) && (nodePair7ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair7FromTextFieldC.getText().trim();
			String origTextTo = nodePair7ToTextFieldC.getText().trim();
			nodePair7FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair7ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair7FromTextFieldC.clear();
			nodePair7ToTextFieldC.clear();
		}

		if ((nodePair8FromTextFieldC.getText().trim() != null) && (nodePair8FromTextFieldC.getText().trim() != "") && (nodePair8ToTextFieldC.getText().trim() != null) && (nodePair8ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair8FromTextFieldC.getText().trim();
			String origTextTo = nodePair8ToTextFieldC.getText().trim();
			nodePair8FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair8ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair8FromTextFieldC.clear();
			nodePair8ToTextFieldC.clear();
		}

		if ((nodePair9FromTextFieldC.getText().trim() != null) && (nodePair9FromTextFieldC.getText().trim() != "") && (nodePair9ToTextFieldC.getText().trim() != null) && (nodePair9ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair9FromTextFieldC.getText().trim();
			String origTextTo = nodePair9ToTextFieldC.getText().trim();
			nodePair9FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair9ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair9FromTextFieldC.clear();
			nodePair9ToTextFieldC.clear();
		}

		if ((nodePair10FromTextFieldC.getText().trim() != null) && (nodePair10FromTextFieldC.getText().trim() != "") && (nodePair10ToTextFieldC.getText().trim() != null) && (nodePair10ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair10FromTextFieldC.getText().trim();
			String origTextTo = nodePair10ToTextFieldC.getText().trim();
			nodePair10FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair10ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair10FromTextFieldC.clear();
			nodePair10ToTextFieldC.clear();
		}

		if ((nodePair11FromTextFieldC.getText().trim() != null) && (nodePair11FromTextFieldC.getText().trim() != "") && (nodePair11ToTextFieldC.getText().trim() != null) && (nodePair11ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair11FromTextFieldC.getText().trim();
			String origTextTo = nodePair11ToTextFieldC.getText().trim();
			nodePair11FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair11ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair11FromTextFieldC.clear();
			nodePair11ToTextFieldC.clear();
		}

		if ((nodePair12FromTextFieldC.getText().trim() != null) && (nodePair12FromTextFieldC.getText().trim() != "") && (nodePair12ToTextFieldC.getText().trim() != null) && (nodePair12ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair12FromTextFieldC.getText().trim();
			String origTextTo = nodePair12ToTextFieldC.getText().trim();
			nodePair12FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair12ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair12FromTextFieldC.clear();
			nodePair12ToTextFieldC.clear();
		}

		if ((nodePair13FromTextFieldC.getText().trim() != null) && (nodePair13FromTextFieldC.getText().trim() != "") && (nodePair13ToTextFieldC.getText().trim() != null) && (nodePair13ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair13FromTextFieldC.getText().trim();
			String origTextTo = nodePair13ToTextFieldC.getText().trim();
			nodePair13FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair13ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair13FromTextFieldC.clear();
			nodePair13ToTextFieldC.clear();
		}

		if ((nodePair14FromTextFieldC.getText().trim() != null) && (nodePair14FromTextFieldC.getText().trim() != "") && (nodePair14ToTextFieldC.getText().trim() != null) && (nodePair14ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair14FromTextFieldC.getText().trim();
			String origTextTo = nodePair14ToTextFieldC.getText().trim();
			nodePair14FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair14ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair14FromTextFieldC.clear();
			nodePair14ToTextFieldC.clear();
		}

		if ((nodePair15FromTextFieldC.getText().trim() != null) && (nodePair15FromTextFieldC.getText().trim() != "") && (nodePair15ToTextFieldC.getText().trim() != null) && (nodePair15ToTextFieldC.getText().trim() != ""))
		{
			String origTextFrom = nodePair15FromTextFieldC.getText().trim();
			String origTextTo = nodePair15ToTextFieldC.getText().trim();
			nodePair15FromTextFieldC.setText(origTextFrom.toUpperCase());
			nodePair15ToTextFieldC.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair15FromTextFieldC.clear();
			nodePair15ToTextFieldC.clear();
		}

		// Write to registry
		String nodePairsToWriteToRegistryC = "";
		if ((nodePair1FromTextFieldC != null) && (nodePair1FromTextFieldC.getText().trim() != "") && (nodePair1ToTextFieldC != null) && (nodePair1ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair1FromTextFieldC.getText() + ":" + nodePair1ToTextFieldC.getText()+"," ;	
			nodePair1FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair1ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair2FromTextFieldC != null) && (nodePair2FromTextFieldC.getText().trim() != "") && (nodePair2ToTextFieldC != null) && (nodePair2ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair2FromTextFieldC.getText() + ":" + nodePair2ToTextFieldC.getText()+"," ;	
			nodePair2FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair2ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair3FromTextFieldC != null) && (nodePair3FromTextFieldC.getText().trim() != "") && (nodePair3ToTextFieldC != null) && (nodePair3ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair3FromTextFieldC.getText() + ":" + nodePair3ToTextFieldC.getText()+"," ;	
			nodePair3FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair3ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair4FromTextFieldC != null) && (nodePair4FromTextFieldC.getText().trim() != "") && (nodePair4ToTextFieldC != null) && (nodePair4ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair4FromTextFieldC.getText() + ":" + nodePair4ToTextFieldC.getText()+"," ;	
			nodePair4FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair4ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair5FromTextFieldC != null) && (nodePair5FromTextFieldC.getText().trim() != "") && (nodePair5ToTextFieldC != null) && (nodePair5ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair5FromTextFieldC.getText() + ":" + nodePair5ToTextFieldC.getText()+"," ;	
			nodePair5FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair5ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair6FromTextFieldC != null) && (nodePair6FromTextFieldC.getText().trim() != "") && (nodePair6ToTextFieldC != null) && (nodePair6ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair6FromTextFieldC.getText() + ":" + nodePair6ToTextFieldC.getText()+"," ;	
			nodePair6FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair6ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair7FromTextFieldC != null) && (nodePair7FromTextFieldC.getText().trim() != "") && (nodePair7ToTextFieldC != null) && (nodePair7ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair7FromTextFieldC.getText() + ":" + nodePair7ToTextFieldC.getText()+"," ;	
			nodePair7FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair7ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair8FromTextFieldC != null) && (nodePair8FromTextFieldC.getText().trim() != "") && (nodePair8ToTextFieldC != null) && (nodePair8ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair8FromTextFieldC.getText() + ":" + nodePair8ToTextFieldC.getText()+"," ;	
			nodePair8FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair8ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair9FromTextFieldC != null) && (nodePair9FromTextFieldC.getText().trim() != "") && (nodePair9ToTextFieldC != null) && (nodePair9ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair9FromTextFieldC.getText() + ":" + nodePair9ToTextFieldC.getText()+"," ;	
			nodePair9FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair9ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair10FromTextFieldC != null) && (nodePair10FromTextFieldC.getText().trim() != "") && (nodePair10ToTextFieldC != null) && (nodePair10ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair10FromTextFieldC.getText() + ":" + nodePair10ToTextFieldC.getText()+"," ;	
			nodePair10FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair10ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair11FromTextFieldC != null) && (nodePair11FromTextFieldC.getText().trim() != "") && (nodePair11ToTextFieldC != null) && (nodePair11ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair11FromTextFieldC.getText() + ":" + nodePair11ToTextFieldC.getText()+"," ;	
			nodePair11FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair11ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair12FromTextFieldC != null) && (nodePair12FromTextFieldC.getText().trim() != "") && (nodePair12ToTextFieldC != null) && (nodePair12ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair12FromTextFieldC.getText() + ":" + nodePair12ToTextFieldC.getText()+"," ;	
			nodePair12FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair12ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair13FromTextFieldC != null) && (nodePair13FromTextFieldC.getText().trim() != "") && (nodePair13ToTextFieldC != null) && (nodePair13ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair13FromTextFieldC.getText() + ":" + nodePair13ToTextFieldC.getText()+"," ;	
			nodePair13FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair13ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair14FromTextFieldC != null) && (nodePair14FromTextFieldC.getText().trim() != "") && (nodePair14ToTextFieldC != null) && (nodePair14ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair14FromTextFieldC.getText() + ":" + nodePair14ToTextFieldC.getText()+"," ;	
			nodePair14FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair14ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair15FromTextFieldC != null) && (nodePair15FromTextFieldC.getText().trim() != "") && (nodePair15ToTextFieldC != null) && (nodePair15ToTextFieldC.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryC += nodePair15FromTextFieldC.getText() + ":" + nodePair15ToTextFieldC.getText()+"," ;	
			nodePair15FromTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair15ToTextFieldC.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		// Check for duplicates in set C
		Boolean duplicateFound = false;
		outerLoop:
			for (int i = 0; i < nodePairsToWriteToRegistryC.split(",").length; i++)
			{
				for (int j = 0; j < nodePairsToWriteToRegistryC.split(",").length; j++)
				{
					if (i == j)
						continue;
					else if ((nodePairsToWriteToRegistryC.split(",")[i].split(":")[0].equals(nodePairsToWriteToRegistryC.split(",")[j].split(":")[0]) 
							&& (nodePairsToWriteToRegistryC.split(",")[i].split(":")[1].equals(nodePairsToWriteToRegistryC.split(",")[j].split(":")[1]))))
					{
						duplicateFound = true;

						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText(null);
						alert.setContentText("Duplicate pair of nodes found "+nodePairsToWriteToRegistryC.split(",")[i].split(":")[0]+" to "+nodePairsToWriteToRegistryC.split(",")[i].split(":")[1]+".  Restoring previous values.");
						Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
						stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
						alert.show();

						setCNodePairs = prefs.get("rr_setCNodePairs", "");
						String nodePairC[] = setCNodePairs.split(",");

						for (int k = 0; k < nodePairC.length; k++)
						{
							if (k == 0)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair1FromTextFieldC.setText(fromTo[0]);
								nodePair1ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 1)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair2FromTextFieldC.setText(fromTo[0]);
								nodePair2ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 2)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair3FromTextFieldC.setText(fromTo[0]);
								nodePair3ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 3)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair4FromTextFieldC.setText(fromTo[0]);
								nodePair4ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 4)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair5FromTextFieldC.setText(fromTo[0]);
								nodePair5ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 5)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair6FromTextFieldC.setText(fromTo[0]);
								nodePair6ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 6)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair7FromTextFieldC.setText(fromTo[0]);
								nodePair7ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 7)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair8FromTextFieldC.setText(fromTo[0]);
								nodePair8ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 8)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair9FromTextFieldC.setText(fromTo[0]);
								nodePair9ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 9)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair10FromTextFieldC.setText(fromTo[0]);
								nodePair10ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 10)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair11FromTextFieldC.setText(fromTo[0]);
								nodePair11ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 11)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair12FromTextFieldC.setText(fromTo[0]);
								nodePair12ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 12)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair13FromTextFieldC.setText(fromTo[0]);
								nodePair13ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 13)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair14FromTextFieldC.setText(fromTo[0]);
								nodePair14ToTextFieldC.setText(fromTo[1]);
							}
							else if (k == 14)
							{
								String[] fromTo = nodePairC[k].split(":"); 
								nodePair15FromTextFieldC.setText(fromTo[0]);
								nodePair15ToTextFieldC.setText(fromTo[1]);
							}
						}

						for (int k = nodePairC.length; k < 15; k++)
						{
							if (k == 0)
							{
								nodePair1FromTextFieldC.setText("");
								nodePair1ToTextFieldC.setText("");
							}
							else if (k == 1)
							{
								nodePair2FromTextFieldC.setText("");
								nodePair2ToTextFieldC.setText("");
							}
							else if (k == 2)
							{
								nodePair3FromTextFieldC.setText("");
								nodePair3ToTextFieldC.setText("");
							}
							else if (k == 3)
							{
								nodePair4FromTextFieldC.setText("");
								nodePair4ToTextFieldC.setText("");
							}
							else if (k == 4)
							{
								nodePair5FromTextFieldC.setText("");
								nodePair5ToTextFieldC.setText("");
							}
							else if (k == 5)
							{
								nodePair6FromTextFieldC.setText("");
								nodePair6ToTextFieldC.setText("");
							}
							else if (k == 6)
							{
								nodePair7FromTextFieldC.setText("");
								nodePair7ToTextFieldC.setText("");
							}
							else if (k == 7)
							{
								nodePair8FromTextFieldC.setText("");
								nodePair8ToTextFieldC.setText("");
							}
							else if (k == 8)
							{
								nodePair9FromTextFieldC.setText("");
								nodePair9ToTextFieldC.setText("");
							}
							else if (k == 9)
							{
								nodePair10FromTextFieldC.setText("");
								nodePair10ToTextFieldC.setText("");
							}
							else if (k == 10)
							{
								nodePair11FromTextFieldC.setText("");
								nodePair11ToTextFieldC.setText("");
							}
							else if (k == 11)
							{
								nodePair12FromTextFieldC.setText("");
								nodePair12ToTextFieldC.setText("");
							}
							else if (k == 12)
							{
								nodePair13FromTextFieldC.setText("");
								nodePair13ToTextFieldC.setText("");
							}
							else if (k == 13)
							{
								nodePair14FromTextFieldC.setText("");
								nodePair14ToTextFieldC.setText("");
							}
							else if (k == 14)
							{
								nodePair15FromTextFieldC.setText("");
								nodePair15ToTextFieldC.setText("");
							}
						}
						break outerLoop;
					}
				}
			}

		if (!duplicateFound)
		{
			updateSetCNodesButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("rr_setCNodePairs", nodePairsToWriteToRegistryC);

			setCNodePairs = nodePairsToWriteToRegistryC;
		}
	}

	@FXML private void handleUpdateSetDNodesButton()
	{
		// Eliminate pairs that are missing one node
		if ((nodePair1FromTextFieldD.getText().trim() != null) && (nodePair1FromTextFieldD.getText().trim() != "") && (nodePair1ToTextFieldD.getText().trim() != null) && (nodePair1ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair1FromTextFieldD.getText().trim();
			String origTextTo = nodePair1ToTextFieldD.getText().trim();
			nodePair1FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair1ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair1FromTextFieldD.clear();
			nodePair1ToTextFieldD.clear();
		}

		if ((nodePair2FromTextFieldD.getText().trim() != null) && (nodePair2FromTextFieldD.getText().trim() != "") && (nodePair2ToTextFieldD.getText().trim() != null) && (nodePair2ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair2FromTextFieldD.getText().trim();
			String origTextTo = nodePair2ToTextFieldD.getText().trim();
			nodePair2FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair2ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair2FromTextFieldD.clear();
			nodePair2ToTextFieldD.clear();
		}

		if ((nodePair3FromTextFieldD.getText().trim() != null) && (nodePair3FromTextFieldD.getText().trim() != "") && (nodePair3ToTextFieldD.getText().trim() != null) && (nodePair3ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair3FromTextFieldD.getText().trim();
			String origTextTo = nodePair3ToTextFieldD.getText().trim();
			nodePair3FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair3ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair3FromTextFieldD.clear();
			nodePair3ToTextFieldD.clear();
		}

		if ((nodePair4FromTextFieldD.getText().trim() != null) && (nodePair4FromTextFieldD.getText().trim() != "") && (nodePair4ToTextFieldD.getText().trim() != null) && (nodePair4ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair4FromTextFieldD.getText().trim();
			String origTextTo = nodePair4ToTextFieldD.getText().trim();
			nodePair4FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair4ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair4FromTextFieldD.clear();
			nodePair4ToTextFieldD.clear();
		}

		if ((nodePair5FromTextFieldD.getText().trim() != null) && (nodePair5FromTextFieldD.getText().trim() != "") && (nodePair5ToTextFieldD.getText().trim() != null) && (nodePair5ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair5FromTextFieldD.getText().trim();
			String origTextTo = nodePair5ToTextFieldD.getText().trim();
			nodePair5FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair5ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair5FromTextFieldD.clear();
			nodePair5ToTextFieldD.clear();
		}

		if ((nodePair6FromTextFieldD.getText().trim() != null) && (nodePair6FromTextFieldD.getText().trim() != "") && (nodePair6ToTextFieldD.getText().trim() != null) && (nodePair6ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair6FromTextFieldD.getText().trim();
			String origTextTo = nodePair6ToTextFieldD.getText().trim();
			nodePair6FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair6ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair6FromTextFieldD.clear();
			nodePair6ToTextFieldD.clear();
		}

		if ((nodePair7FromTextFieldD.getText().trim() != null) && (nodePair7FromTextFieldD.getText().trim() != "") && (nodePair7ToTextFieldD.getText().trim() != null) && (nodePair7ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair7FromTextFieldD.getText().trim();
			String origTextTo = nodePair7ToTextFieldD.getText().trim();
			nodePair7FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair7ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair7FromTextFieldD.clear();
			nodePair7ToTextFieldD.clear();
		}

		if ((nodePair8FromTextFieldD.getText().trim() != null) && (nodePair8FromTextFieldD.getText().trim() != "") && (nodePair8ToTextFieldD.getText().trim() != null) && (nodePair8ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair8FromTextFieldD.getText().trim();
			String origTextTo = nodePair8ToTextFieldD.getText().trim();
			nodePair8FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair8ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair8FromTextFieldD.clear();
			nodePair8ToTextFieldD.clear();
		}

		if ((nodePair9FromTextFieldD.getText().trim() != null) && (nodePair9FromTextFieldD.getText().trim() != "") && (nodePair9ToTextFieldD.getText().trim() != null) && (nodePair9ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair9FromTextFieldD.getText().trim();
			String origTextTo = nodePair9ToTextFieldD.getText().trim();
			nodePair9FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair9ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair9FromTextFieldD.clear();
			nodePair9ToTextFieldD.clear();
		}

		if ((nodePair10FromTextFieldD.getText().trim() != null) && (nodePair10FromTextFieldD.getText().trim() != "") && (nodePair10ToTextFieldD.getText().trim() != null) && (nodePair10ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair10FromTextFieldD.getText().trim();
			String origTextTo = nodePair10ToTextFieldD.getText().trim();
			nodePair10FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair10ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair10FromTextFieldD.clear();
			nodePair10ToTextFieldD.clear();
		}

		if ((nodePair11FromTextFieldD.getText().trim() != null) && (nodePair11FromTextFieldD.getText().trim() != "") && (nodePair11ToTextFieldD.getText().trim() != null) && (nodePair11ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair11FromTextFieldD.getText().trim();
			String origTextTo = nodePair11ToTextFieldD.getText().trim();
			nodePair11FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair11ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair11FromTextFieldD.clear();
			nodePair11ToTextFieldD.clear();
		}

		if ((nodePair12FromTextFieldD.getText().trim() != null) && (nodePair12FromTextFieldD.getText().trim() != "") && (nodePair12ToTextFieldD.getText().trim() != null) && (nodePair12ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair12FromTextFieldD.getText().trim();
			String origTextTo = nodePair12ToTextFieldD.getText().trim();
			nodePair12FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair12ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair12FromTextFieldD.clear();
			nodePair12ToTextFieldD.clear();
		}

		if ((nodePair13FromTextFieldD.getText().trim() != null) && (nodePair13FromTextFieldD.getText().trim() != "") && (nodePair13ToTextFieldD.getText().trim() != null) && (nodePair13ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair13FromTextFieldD.getText().trim();
			String origTextTo = nodePair13ToTextFieldD.getText().trim();
			nodePair13FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair13ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair13FromTextFieldD.clear();
			nodePair13ToTextFieldD.clear();
		}

		if ((nodePair14FromTextFieldD.getText().trim() != null) && (nodePair14FromTextFieldD.getText().trim() != "") && (nodePair14ToTextFieldD.getText().trim() != null) && (nodePair14ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair14FromTextFieldD.getText().trim();
			String origTextTo = nodePair14ToTextFieldD.getText().trim();
			nodePair14FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair14ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair14FromTextFieldD.clear();
			nodePair14ToTextFieldD.clear();
		}

		if ((nodePair15FromTextFieldD.getText().trim() != null) && (nodePair15FromTextFieldD.getText().trim() != "") && (nodePair15ToTextFieldD.getText().trim() != null) && (nodePair15ToTextFieldD.getText().trim() != ""))
		{
			String origTextFrom = nodePair15FromTextFieldD.getText().trim();
			String origTextTo = nodePair15ToTextFieldD.getText().trim();
			nodePair15FromTextFieldD.setText(origTextFrom.toUpperCase());
			nodePair15ToTextFieldD.setText(origTextTo.toUpperCase());
		}
		else
		{
			nodePair15FromTextFieldD.clear();
			nodePair15ToTextFieldD.clear();
		}

		// Write to registry
		String nodePairsToWriteToRegistryD = "";
		if ((nodePair1FromTextFieldD != null) && (nodePair1FromTextFieldD.getText().trim() != "") && (nodePair1ToTextFieldD != null) && (nodePair1ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair1FromTextFieldD.getText() + ":" + nodePair1ToTextFieldD.getText()+"," ;	
			nodePair1FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair1ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair2FromTextFieldD != null) && (nodePair2FromTextFieldD.getText().trim() != "") && (nodePair2ToTextFieldD != null) && (nodePair2ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair2FromTextFieldD.getText() + ":" + nodePair2ToTextFieldD.getText()+"," ;	
			nodePair2FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair2ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair3FromTextFieldD != null) && (nodePair3FromTextFieldD.getText().trim() != "") && (nodePair3ToTextFieldD != null) && (nodePair3ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair3FromTextFieldD.getText() + ":" + nodePair3ToTextFieldD.getText()+"," ;	
			nodePair3FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair3ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair4FromTextFieldD != null) && (nodePair4FromTextFieldD.getText().trim() != "") && (nodePair4ToTextFieldD != null) && (nodePair4ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair4FromTextFieldD.getText() + ":" + nodePair4ToTextFieldD.getText()+"," ;	
			nodePair4FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair4ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair5FromTextFieldD != null) && (nodePair5FromTextFieldD.getText().trim() != "") && (nodePair5ToTextFieldD != null) && (nodePair5ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair5FromTextFieldD.getText() + ":" + nodePair5ToTextFieldD.getText()+"," ;	
			nodePair5FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair5ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair6FromTextFieldD != null) && (nodePair6FromTextFieldD.getText().trim() != "") && (nodePair6ToTextFieldD != null) && (nodePair6ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair6FromTextFieldD.getText() + ":" + nodePair6ToTextFieldD.getText()+"," ;	
			nodePair6FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair6ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair7FromTextFieldD != null) && (nodePair7FromTextFieldD.getText().trim() != "") && (nodePair7ToTextFieldD != null) && (nodePair7ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair7FromTextFieldD.getText() + ":" + nodePair7ToTextFieldD.getText()+"," ;	
			nodePair7FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair7ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair8FromTextFieldD != null) && (nodePair8FromTextFieldD.getText().trim() != "") && (nodePair8ToTextFieldD != null) && (nodePair8ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair8FromTextFieldD.getText() + ":" + nodePair8ToTextFieldD.getText()+"," ;	
			nodePair8FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair8ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair9FromTextFieldD != null) && (nodePair9FromTextFieldD.getText().trim() != "") && (nodePair9ToTextFieldD != null) && (nodePair9ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair9FromTextFieldD.getText() + ":" + nodePair9ToTextFieldD.getText()+"," ;	
			nodePair9FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair9ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair10FromTextFieldD != null) && (nodePair10FromTextFieldD.getText().trim() != "") && (nodePair10ToTextFieldD != null) && (nodePair10ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair10FromTextFieldD.getText() + ":" + nodePair10ToTextFieldD.getText()+"," ;	
			nodePair10FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair10ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair11FromTextFieldD != null) && (nodePair11FromTextFieldD.getText().trim() != "") && (nodePair11ToTextFieldD != null) && (nodePair11ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair11FromTextFieldD.getText() + ":" + nodePair11ToTextFieldD.getText()+"," ;	
			nodePair11FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair11ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair12FromTextFieldD != null) && (nodePair12FromTextFieldD.getText().trim() != "") && (nodePair12ToTextFieldD != null) && (nodePair12ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair12FromTextFieldD.getText() + ":" + nodePair12ToTextFieldD.getText()+"," ;	
			nodePair12FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair12ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair13FromTextFieldD != null) && (nodePair13FromTextFieldD.getText().trim() != "") && (nodePair13ToTextFieldD != null) && (nodePair13ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair13FromTextFieldD.getText() + ":" + nodePair13ToTextFieldD.getText()+"," ;	
			nodePair13FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair13ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair14FromTextFieldD != null) && (nodePair14FromTextFieldD.getText().trim() != "") && (nodePair14ToTextFieldD != null) && (nodePair14ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair14FromTextFieldD.getText() + ":" + nodePair14ToTextFieldD.getText()+"," ;	
			nodePair14FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair14ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		if ((nodePair15FromTextFieldD != null) && (nodePair15FromTextFieldD.getText().trim() != "") && (nodePair15ToTextFieldD != null) && (nodePair15ToTextFieldD.getText().trim() != ""))
		{
			nodePairsToWriteToRegistryD += nodePair15FromTextFieldD.getText() + ":" + nodePair15ToTextFieldD.getText()+"," ;	
			nodePair15FromTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			nodePair15ToTextFieldD.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}

		// Check for duplicates in set D
		Boolean duplicateFound = false;
		outerLoop:
			for (int i = 0; i < nodePairsToWriteToRegistryD.split(",").length; i++)
			{
				for (int j = 0; j < nodePairsToWriteToRegistryD.split(",").length; j++)
				{
					if (i == j)
						continue;
					else if ((nodePairsToWriteToRegistryD.split(",")[i].split(":")[0].equals(nodePairsToWriteToRegistryD.split(",")[j].split(":")[0]) 
							&& (nodePairsToWriteToRegistryD.split(",")[i].split(":")[1].equals(nodePairsToWriteToRegistryD.split(",")[j].split(":")[1]))))
					{
						duplicateFound = true;

						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText(null);
						alert.setContentText("Duplicate pair of nodes found "+nodePairsToWriteToRegistryD.split(",")[i].split(":")[0]+" to "+nodePairsToWriteToRegistryD.split(",")[i].split(":")[1]+".  Restoring previous values.");
						Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
						stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
						alert.show();

						setDNodePairs = prefs.get("rr_setDNodePairs", "");
						String nodePairD[] = setDNodePairs.split(",");

						for (int k = 0; k < nodePairD.length; k++)
						{
							if (k == 0)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair1FromTextFieldD.setText(fromTo[0]);
								nodePair1ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 1)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair2FromTextFieldD.setText(fromTo[0]);
								nodePair2ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 2)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair3FromTextFieldD.setText(fromTo[0]);
								nodePair3ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 3)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair4FromTextFieldD.setText(fromTo[0]);
								nodePair4ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 4)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair5FromTextFieldD.setText(fromTo[0]);
								nodePair5ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 5)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair6FromTextFieldD.setText(fromTo[0]);
								nodePair6ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 6)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair7FromTextFieldD.setText(fromTo[0]);
								nodePair7ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 7)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair8FromTextFieldD.setText(fromTo[0]);
								nodePair8ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 8)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair9FromTextFieldD.setText(fromTo[0]);
								nodePair9ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 9)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair10FromTextFieldD.setText(fromTo[0]);
								nodePair10ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 10)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair11FromTextFieldD.setText(fromTo[0]);
								nodePair11ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 11)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair12FromTextFieldD.setText(fromTo[0]);
								nodePair12ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 12)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair13FromTextFieldD.setText(fromTo[0]);
								nodePair13ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 13)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair14FromTextFieldD.setText(fromTo[0]);
								nodePair14ToTextFieldD.setText(fromTo[1]);
							}
							else if (k == 14)
							{
								String[] fromTo = nodePairD[k].split(":"); 
								nodePair15FromTextFieldD.setText(fromTo[0]);
								nodePair15ToTextFieldD.setText(fromTo[1]);
							}
						}

						for (int k = nodePairD.length; k < 15; k++)
						{
							if (k == 0)
							{
								nodePair1FromTextFieldD.setText("");
								nodePair1ToTextFieldD.setText("");
							}
							else if (k == 1)
							{
								nodePair2FromTextFieldD.setText("");
								nodePair2ToTextFieldD.setText("");
							}
							else if (k == 2)
							{
								nodePair3FromTextFieldD.setText("");
								nodePair3ToTextFieldD.setText("");
							}
							else if (k == 3)
							{
								nodePair4FromTextFieldD.setText("");
								nodePair4ToTextFieldD.setText("");
							}
							else if (k == 4)
							{
								nodePair5FromTextFieldD.setText("");
								nodePair5ToTextFieldD.setText("");
							}
							else if (k == 5)
							{
								nodePair6FromTextFieldD.setText("");
								nodePair6ToTextFieldD.setText("");
							}
							else if (k == 6)
							{
								nodePair7FromTextFieldD.setText("");
								nodePair7ToTextFieldD.setText("");
							}
							else if (k == 7)
							{
								nodePair8FromTextFieldD.setText("");
								nodePair8ToTextFieldD.setText("");
							}
							else if (k == 8)
							{
								nodePair9FromTextFieldD.setText("");
								nodePair9ToTextFieldD.setText("");
							}
							else if (k == 9)
							{
								nodePair10FromTextFieldD.setText("");
								nodePair10ToTextFieldD.setText("");
							}
							else if (k == 10)
							{
								nodePair11FromTextFieldD.setText("");
								nodePair11ToTextFieldD.setText("");
							}
							else if (k == 11)
							{
								nodePair12FromTextFieldD.setText("");
								nodePair12ToTextFieldD.setText("");
							}
							else if (k == 12)
							{
								nodePair13FromTextFieldD.setText("");
								nodePair13ToTextFieldD.setText("");
							}
							else if (k == 13)
							{
								nodePair14FromTextFieldD.setText("");
								nodePair14ToTextFieldD.setText("");
							}
							else if (k == 14)
							{
								nodePair15FromTextFieldD.setText("");
								nodePair15ToTextFieldD.setText("");
							}
						}
						break outerLoop;
					}
				}
			}
		if (!duplicateFound)
		{
			updateSetDNodesButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("rr_setDNodePairs", nodePairsToWriteToRegistryD);

			setDNodePairs = nodePairsToWriteToRegistryD;
		}
	}

	@FXML private void handleUpdateSetALabelButton()
	{
		setALabel = setALabelTextField.getText(0, Math.min(setALabelTextField.getText().length(), 10)).trim();
		setALabelTextField.setText(setALabel);

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_setALabel", setALabel);	
	}

	@FXML private void handleUpdateSetBLabelButton()
	{
		setBLabel = setBLabelTextField.getText(0, Math.min(setBLabelTextField.getText().length(), 10)).trim();
		setBLabelTextField.setText(setBLabel);

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_setBLabel", setBLabel);	
	}

	@FXML private void handleUpdateSetCLabelButton()
	{
		setCLabel = setCLabelTextField.getText(0, Math.min(setCLabelTextField.getText().length(), 10)).trim();
		setCLabelTextField.setText(setCLabel);

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_setCLabel", setCLabel);	
	}

	@FXML private void handleUpdateSetDLabelButton()
	{
		setDLabel = setDLabelTextField.getText(0, Math.min(setDLabelTextField.getText().length(), 10)).trim();
		setDLabelTextField.setText(setCLabel);

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_setDLabel", setDLabel);	
	}

	@FXML private void handleAnalyzeSetACheckBox()
	{
		if (analyzeSetA)
		{
			analyzeSetA = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rr_analyzeSetA", false);
		}
		else
		{
			analyzeSetA = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rr_analyzeSetA", true);
		}
	}

	@FXML private void handleAnalyzeSetBCheckBox()
	{
		if (analyzeSetB)
		{
			analyzeSetB = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rr_analyzeSetB", false);
		}
		else
		{
			analyzeSetB = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rr_analyzeSetB", true);
		}
	}

	@FXML private void handleAnalyzeSetCCheckBox()
	{
		if (analyzeSetC)
		{
			analyzeSetC = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rr_analyzeSetC", false);
		}
		else
		{
			analyzeSetC = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rr_analyzeSetC", true);
		}
	}

	@FXML private void handleAnalyzeSetDCheckBox()
	{
		if (analyzeSetD)
		{
			analyzeSetD = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rr_analyzeSetD", false);
		}
		else
		{
			analyzeSetD = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rr_analyzeSetD", true);
		}
	}

	public static String getSetALowRecoveryRate()
	{
		return setALowRecoveryRate;
	}

	public static String getSetBLowRecoveryRate()
	{
		return setBLowRecoveryRate;
	}

	public static String getSetCLowRecoveryRate()
	{
		return setCLowRecoveryRate;
	}

	public static String getSetDLowRecoveryRate()
	{
		return setDLowRecoveryRate;
	}

	public static Integer getSetAScheduleImprecisionOffsetInSeconds()
	{
		return setASchedulingImprecisionOffset;
	}

	public static Integer getSetBScheduleImprecisionOffsetInSeconds()
	{
		return setBSchedulingImprecisionOffset;
	}

	public static Integer getSetCScheduleImprecisionOffsetInSeconds()
	{
		return setCSchedulingImprecisionOffset;
	}

	public static Integer getSetDScheduleImprecisionOffsetInSeconds()
	{
		return setDSchedulingImprecisionOffset;
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

	public static String getSetCRecoveryRateAnalysisNodePairs()
	{
		return setCNodePairs;
	}

	public static String getSetDRecoveryRateAnalysisNodePairs()
	{
		return setDNodePairs;
	}

	public static String getSetALabel()
	{
		return setALabel;
	}

	public static String getSetBLabel()
	{
		return setBLabel;
	}

	public static String getSetCLabel()
	{
		return setCLabel;
	}

	public static String getSetDLabel()
	{
		return setDLabel;
	}

	public static Boolean getAnalyzeSetA()
	{
		return analyzeSetA;
	}

	public static Boolean getAnalyzeSetB()
	{
		return analyzeSetB;
	}

	public static Boolean getAnalyzeSetC()
	{
		return analyzeSetC;
	}

	public static Boolean getAnalyzeSetD()
	{
		return analyzeSetD;
	}
}