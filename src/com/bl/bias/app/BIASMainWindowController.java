package com.bl.bias.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class BIASMainWindowController 
{
	@FXML private GridPane mainGridPane;

	@FXML private Menu subMenuModuleConfig;

	@FXML private MenuItem menuItemRTCResultsAnalysis;
	@FXML private MenuItem menuItemTtest;
	@FXML private MenuItem menuItemBridgeClosureAnalysis;
	@FXML private MenuItem menuItemMaintenanceWindowAnalysis;
	@FXML private MenuItem menuItemRadixxResSsimConversionIATAExcel;
	@FXML private MenuItem menuItemRadixxResSsimConversionS3Excel;
	@FXML private MenuItem menuItemRadixxResSsimComparison;
	@FXML private MenuItem menuItemGradeXingSpeeds;
	@FXML private MenuItem menuItemUscgBridgeCompliance;
	@FXML private MenuItem menuItemRecoveryRate;
	@FXML private MenuItem menuItemJuaCompliance;
	@FXML private MenuItem menuItemExit; 
	@FXML private MenuItem menuItemRTCResultsAnalysisConfig;
	@FXML private MenuItem menuItemTTestConfig;
	@FXML private MenuItem menuItemBridgeClosureAnalysisConfig;
	@FXML private MenuItem menuItemMaintenanceWindowAnalysisConfig;
	@FXML private MenuItem menuItemRadixxResSsimConversionConfigIATAExcel;
	@FXML private MenuItem menuItemRadixxResSsimComparisonConfig;
	@FXML private MenuItem menuItemGradeXingSpeedsConfig;
	@FXML private MenuItem menuItemUscgBridgeComplianceConfig;
	@FXML private MenuItem menuItemRecoveryRateConfig;
	@FXML private MenuItem menuItemJuaComplianceConfig;
	@FXML private MenuItem menuItemGeneralConfig;
	@FXML private MenuItem menuItemParseConfig;
	@FXML private MenuItem menuItemVersioningAndPermissions;

	@FXML private Node nodeRTCResultsAnalysis;
	@FXML private Node nodeTTest;
	@FXML private Node nodeGeneralConfig;
	@FXML private Node nodeParseConfig; 
	@FXML private Node nodeTTestConfig;
	@FXML private Node nodeRTCResultsAnalysisConfig;
	@FXML private Node nodeVersioningAndPermissions;
	@FXML private Node nodeMaintenanceWindowAnalysis;
	@FXML private Node nodeMaintenanceWindowAnalysisConfig;
	@FXML private Node nodeBridgeClosureAnalysis;
	@FXML private Node nodeBridgeClosureAnalysisConfig;
	@FXML private Node nodeRadixxResSsimConversionIATAExcel;
	@FXML private Node nodeRadixxResSsimConversionS3Excel;
	@FXML private Node nodeRadixxResSsimComparison;
	@FXML private Node nodeRadixxResSsimConversionIATAExcelConfig;
	@FXML private Node nodeRadixxResSsimComparisonConfig;
	@FXML private Node nodeGradeXingSpeeds;
	@FXML private Node nodeGradeXingSpeedsConfig;
	@FXML private Node nodeUscgBridgeCompliance;
	@FXML private Node nodeUscgBridgeComplianceConfig;
	@FXML private Node nodeRecoveryRate;
	@FXML private Node nodeRecoveryRateConfig;
	@FXML private Node nodeJuaCompliance;
	@FXML private Node nodeJuaComplianceConfig;

	@FXML private ImageView headerBackground; 

	@FXML private Label IASLabel;

	@FXML private void initialize() throws IOException
	{
		// Load header background which will vary based on user location
		Image image = new Image(getClass().getResourceAsStream(BIASLaunch.getHeaderFile()));
		headerBackground.setImage(image);

		// Load all modules' FXML and Controllers
		nodeRTCResultsAnalysis = FXMLLoader.load(getClass().getResource("BIASRTCResultsAnalysisPage.fxml"));
		nodeTTest = FXMLLoader.load(getClass().getResource("BIASTtestPage.fxml"));
		nodeVersioningAndPermissions = FXMLLoader.load(getClass().getResource("BIASVersioningAndPermissionsPage.fxml"));
		nodeGeneralConfig = FXMLLoader.load(getClass().getResource("BIASGeneralConfigPage.fxml"));
		nodeTTestConfig = FXMLLoader.load(getClass().getResource("BIASTtestConfigPage.fxml"));
		nodeRTCResultsAnalysisConfig = FXMLLoader.load(getClass().getResource("BIASRTCResultsAnalysisConfigPage.fxml"));
		nodeMaintenanceWindowAnalysisConfig = FXMLLoader.load(getClass().getResource("BIASMaintenanceWindowAnalysisConfigPage.fxml"));
		nodeBridgeClosureAnalysis = FXMLLoader.load(getClass().getResource("BIASBridgeClosureAnalysisPage.fxml"));
		nodeBridgeClosureAnalysisConfig = FXMLLoader.load(getClass().getResource("BIASBridgeClosureAnalysisConfigPage.fxml"));
		nodeRadixxResSsimConversionIATAExcel = FXMLLoader.load(getClass().getResource("BIASRadixxResSsimConversionPageIATAExcel.fxml"));
		nodeRadixxResSsimConversionS3Excel = FXMLLoader.load(getClass().getResource("BIASRadixxResSsimConversionPageS3Excel.fxml"));
		nodeRadixxResSsimComparison = FXMLLoader.load(getClass().getResource("BIASRadixxResSsimComparisonPage.fxml"));
		nodeRadixxResSsimConversionIATAExcelConfig = FXMLLoader.load(getClass().getResource("BIASRadixxResSsimConversionConfigPageIATAExcel.fxml"));
		nodeRadixxResSsimComparisonConfig = FXMLLoader.load(getClass().getResource("BIASRadixxResSsimComparisonConfigPage.fxml"));
		nodeGradeXingSpeeds = FXMLLoader.load(getClass().getResource("BIASGradeXingSpeedsPage.fxml"));
		nodeGradeXingSpeedsConfig = FXMLLoader.load(getClass().getResource("BIASGradeXingSpeedsConfigPage.fxml"));
		nodeUscgBridgeCompliance = FXMLLoader.load(getClass().getResource("BIASUscgBridgeComplianceAnalysisPage.fxml"));
		nodeUscgBridgeComplianceConfig = FXMLLoader.load(getClass().getResource("BIASUscgBridgeComplianceAnalysisConfigPage.fxml"));
		nodeRecoveryRate = FXMLLoader.load(getClass().getResource("BIASRecoveryRateAnalysisPage.fxml"));
		nodeRecoveryRateConfig = FXMLLoader.load(getClass().getResource("BIASRecoveryRateAnalysisConfigPage.fxml"));
		nodeJuaCompliance = FXMLLoader.load(getClass().getResource("BIASJuaCompliancePage.fxml"));
		nodeJuaComplianceConfig = FXMLLoader.load(getClass().getResource("BIASJuaComplianceConfigPage.fxml"));

		// LOADING BELOW nodes will throw exception if trying to launch as JAR
		nodeMaintenanceWindowAnalysis = FXMLLoader.load(getClass().getResource("BIASMaintenanceWindowAnalysisPage.fxml"));
		nodeParseConfig = FXMLLoader.load(getClass().getResource("BIASParseConfigPage.fxml"));

		// Determine set of modules that user may access
		Object[] permittedModules = BIASProcessPermissions.getVerifiedUserModules().toArray();

		// Only show menu items that user has access to.  BIASProcessPermissions is given to both the module and the config settings for the module.
		for (int i=0; i < permittedModules.length; i++)
		{
			if (permittedModules[i].toString().contains("RTC Results"))
			{
				menuItemRTCResultsAnalysis.setVisible(true);
				subMenuModuleConfig.setVisible(true);
				menuItemRTCResultsAnalysisConfig.setVisible(true);

				if (i == 0)
				{
					handleMenuItemRTCResultsAnalysis(null);
				}
			}
			else if (permittedModules[i].toString().contains("T-test"))
			{
				menuItemTtest.setVisible(true);
				subMenuModuleConfig.setVisible(true);
				menuItemTTestConfig.setVisible(true);

				if (i == 0)
				{
					handleMenuItemTtest(null);
				}
			}
			else if (permittedModules[i].toString().contains("General"))
			{
				menuItemGeneralConfig.setVisible(true);

				if (i == 0)
				{
					handleMenuItemGeneralConfig(null);
				}
			}
			else if (permittedModules[i].toString().contains("Parse"))
			{
				menuItemParseConfig.setVisible(true);

				if (i == 0)
				{
					handleMenuItemParseConfig(null);
				}
			}
			else if (permittedModules[i].toString().contains("Maintenance"))
			{
				menuItemMaintenanceWindowAnalysis.setVisible(true);
				subMenuModuleConfig.setVisible(true);
				menuItemMaintenanceWindowAnalysisConfig.setVisible(true);

				if (i == 0)
				{
					handleMenuItemMaintenanceWindowAnalysis(null);
				}
			}
			else if (permittedModules[i].toString().contains("Bridge Closure"))
			{
				menuItemBridgeClosureAnalysis.setVisible(true);
				subMenuModuleConfig.setVisible(true);
				menuItemBridgeClosureAnalysisConfig.setVisible(true);

				if (i == 0)
				{
					handleMenuItemBridgeClosureAnalysis(null);
				}
			}
			else if (permittedModules[i].toString().contains("SSIM Conversion"))
			{
				menuItemRadixxResSsimConversionIATAExcel.setVisible(true);
				menuItemRadixxResSsimConversionS3Excel.setVisible(true);
				subMenuModuleConfig.setVisible(true);
				menuItemRadixxResSsimConversionConfigIATAExcel.setVisible(true);

				if (i == 0)
				{
					handleMenuItemRadixxResSsimConversionIATAExcel(null);
				}
			}
			else if (permittedModules[i].toString().contains("SSIM Comparison"))
			{
				menuItemRadixxResSsimComparison.setVisible(true);
				subMenuModuleConfig.setVisible(true);
				menuItemRadixxResSsimComparisonConfig.setVisible(true);
				if (i == 0)
				{
					handleMenuItemRadixxResSsimComparison(null);
				}
			}
			else if (permittedModules[i].toString().contains("Grade"))
			{
				menuItemGradeXingSpeeds.setVisible(true);
				subMenuModuleConfig.setVisible(true);
				menuItemGradeXingSpeedsConfig.setVisible(true);

				if (i == 0)
				{
					handleMenuItemGradeXingSpeeds(null);
				}
			}
			else if (permittedModules[i].toString().contains("USCG Bridge Compliance"))
			{
				menuItemUscgBridgeCompliance.setVisible(true);
				subMenuModuleConfig.setVisible(true);
				menuItemUscgBridgeComplianceConfig.setVisible(true);

				if (i == 0)
				{
					handleMenuItemUscgBridgeCompliance(null);
				}
			}
			else if (permittedModules[i].toString().contains("Recovery Rate"))
			{
				menuItemRecoveryRate.setVisible(true);
				subMenuModuleConfig.setVisible(true);
				menuItemRecoveryRateConfig.setVisible(true);

				if (i == 0)
				{
					handleMenuItemRecoveryRate(null);
				}
			}
			else if (permittedModules[i].toString().contains("JUA Compliance"))
			{
				menuItemJuaCompliance.setVisible(true);
				subMenuModuleConfig.setVisible(true);
				menuItemJuaComplianceConfig.setVisible(true);

				if (i == 0)
				{
					handleMenuItemJuaCompliance(null);
				}
			}
		}
		menuItemVersioningAndPermissions.setVisible(true);
	}

	@FXML private void handleMenuItemRTCResultsAnalysis(ActionEvent event) throws IOException 
	{
		showModuleOnMainGridPane(nodeRTCResultsAnalysis);
	}

	@FXML private void handleMenuItemTtest(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeTTest);
	}

	@FXML private void handleMenuItemBridgeClosureAnalysis(ActionEvent event)
	{
		showModuleOnMainGridPane(nodeBridgeClosureAnalysis);
	}

	@FXML private void handleMenuItemMaintenanceWindowAnalysis(ActionEvent event)
	{
		showModuleOnMainGridPane(nodeMaintenanceWindowAnalysis);
	}

	@FXML private void handleMenuItemGeneralConfig(ActionEvent event)
	{
		showModuleOnMainGridPane(nodeGeneralConfig);
	}

	@FXML private void handleMenuItemTTestConfig(ActionEvent event)
	{
		showModuleOnMainGridPane(nodeTTest);
	}

	@FXML private void handleMenuItemRTCResultsAnalysisConfig(ActionEvent event)
	{
		showModuleOnMainGridPane(nodeRTCResultsAnalysisConfig);
	}

	@FXML private void handleMenuItemMaintenanceWindowAnalysisConfig(ActionEvent event)
	{
		showModuleOnMainGridPane(nodeMaintenanceWindowAnalysisConfig);
	}

	@FXML private void handleMenuItemParseConfig(ActionEvent event) 
	{
		showModuleOnMainGridPane(nodeParseConfig);
	}

	@FXML private void handleMenuItemVersioningAndPermissions(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeVersioningAndPermissions);
	}

	@FXML private void handleMenuItemBridgeClosureAnalysisConfig(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeBridgeClosureAnalysisConfig);
	}

	@FXML private void handleMenuItemRadixxResSsimConversionIATAExcel(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeRadixxResSsimConversionIATAExcel);
	}

	@FXML private void handleMenuItemRadixxResSsimConversionS3Excel(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeRadixxResSsimConversionS3Excel);
	}    

	@FXML private void handleMenuItemRadixxResSsimComparison(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeRadixxResSsimComparison);
	}

	@FXML private void handleMenuItemGradeXingSpeeds(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeGradeXingSpeeds);
	}

	@FXML private void handleMenuItemGradeXingSpeedsConfig(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeGradeXingSpeedsConfig);
	}

	@FXML private void handleMenuItemRadixxResSsimConversionConfigIATAExcel(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeRadixxResSsimConversionIATAExcelConfig);
	}

	@FXML private void handleMenuItemRadixxResSsimComparisonConfig(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeRadixxResSsimComparisonConfig))
		{	
			showModuleOnMainGridPane(nodeRadixxResSsimComparisonConfig);
		}
	}

	@FXML private void handleMenuItemUscgBridgeCompliance(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeRadixxResSsimComparisonConfig))
		{	
			showModuleOnMainGridPane(nodeUscgBridgeCompliance);
		}
	}
	
	@FXML private void handleMenuItemUscgBridgeComplianceConfig(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeUscgBridgeComplianceConfig);
	}
	
	@FXML private void handleMenuItemRecoveryRate(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeRecoveryRate);
	}
	
	@FXML private void handleMenuItemRecoveryRateConfig(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeRecoveryRateConfig))
		{	
			showModuleOnMainGridPane(nodeRecoveryRateConfig);
		}
	}
	
	@FXML private void handleMenuItemJuaCompliance(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeJuaCompliance);
	}
	
	@FXML private void handleMenuItemJuaComplianceConfig(ActionEvent event) throws IOException
	{
		showModuleOnMainGridPane(nodeJuaComplianceConfig);
	}

	void showModuleOnMainGridPane(Node nodeToDisplay)
	{
		// Remove all nodes except the desired one
		if (mainGridPane.getChildren().contains(nodeRTCResultsAnalysis))
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);

		if (mainGridPane.getChildren().contains(nodeTTest))
			mainGridPane.getChildren().remove(nodeTTest);

		if (mainGridPane.getChildren().contains(nodeGeneralConfig))
			mainGridPane.getChildren().remove(nodeGeneralConfig);

		if (mainGridPane.getChildren().contains(nodeParseConfig))
			mainGridPane.getChildren().remove(nodeParseConfig);

		if (mainGridPane.getChildren().contains(nodeRadixxResSsimConversionIATAExcelConfig))
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);

		if (mainGridPane.getChildren().contains(nodeRadixxResSsimConversionS3Excel))
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);

		if (mainGridPane.getChildren().contains(nodeRadixxResSsimComparison))
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);

		if (mainGridPane.getChildren().contains(nodeRadixxResSsimComparisonConfig))
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);

		if (mainGridPane.getChildren().contains(nodeVersioningAndPermissions))
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);

		if (mainGridPane.getChildren().contains(nodeTTestConfig))
			mainGridPane.getChildren().remove(nodeTTestConfig);

		if (mainGridPane.getChildren().contains(nodeRTCResultsAnalysisConfig))
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);

		if (mainGridPane.getChildren().contains(nodeMaintenanceWindowAnalysis))
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);

		if (mainGridPane.getChildren().contains(nodeMaintenanceWindowAnalysisConfig))
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);

		if (mainGridPane.getChildren().contains(nodeBridgeClosureAnalysis))
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);

		if (mainGridPane.getChildren().contains(nodeBridgeClosureAnalysisConfig))
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);

		if (mainGridPane.getChildren().contains(nodeGradeXingSpeeds))
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);

		if (mainGridPane.getChildren().contains(nodeGradeXingSpeedsConfig))
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);

		if (mainGridPane.getChildren().contains(nodeUscgBridgeCompliance))
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);

		if (mainGridPane.getChildren().contains(nodeUscgBridgeComplianceConfig))
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);

		if (mainGridPane.getChildren().contains(nodeRecoveryRate))
			mainGridPane.getChildren().remove(nodeRecoveryRate);

		if (mainGridPane.getChildren().contains(nodeRecoveryRateConfig))
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

		if (mainGridPane.getChildren().contains(nodeJuaCompliance))
			mainGridPane.getChildren().remove(nodeJuaCompliance);
		
		if (mainGridPane.getChildren().contains(nodeJuaComplianceConfig))
			mainGridPane.getChildren().remove(nodeJuaComplianceConfig);

		// Add the desired Pane
		mainGridPane.add(nodeToDisplay, 0, 2);    	
	}

	@FXML private void handleMenuItemExit(ActionEvent event) 
	{
		System.exit(0);
	}
}