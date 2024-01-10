package com.bl.bias.app;

import java.util.prefs.Preferences;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class BIASRecoveryRateAnalysisConfigController 
{
	private static Preferences prefs;

	private static String trainGroups;
	private static String nodePairs;

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

	@FXML Button updateGroupsButton;
	@FXML Button updateNodesButton;

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
			}
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

	// Node pair # 1 of 8
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

	// Node pair # 2 of 8
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

	// Node pair # 3 of 8
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

	// Node pair # 4 of 8
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

	// Node pair # 5 of 8
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

	// Node pair # 6 of 8
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

	// Node pair # 7 of 8
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

	// Node pair # 8 of 8
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

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("rr_nodePairs", nodePairsToWriteToRegistry);
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