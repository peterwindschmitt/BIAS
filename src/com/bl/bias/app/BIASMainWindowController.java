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
		}
		menuItemVersioningAndPermissions.setVisible(true);
	}

	@FXML private void handleMenuItemRTCResultsAnalysis(ActionEvent event) throws IOException 
	{
		if (!mainGridPane.getChildren().contains(nodeRTCResultsAnalysis))
		{
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeRTCResultsAnalysis, 0, 2);    	
		}
	}

	@FXML private void handleMenuItemTtest(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeTTest))
		{
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeTTest, 0, 2);
		}
	}

	@FXML private void handleMenuItemBridgeClosureAnalysis(ActionEvent event)
	{
		if (!mainGridPane.getChildren().contains(nodeBridgeClosureAnalysis))
		{
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeBridgeClosureAnalysis, 0, 2);
		}
	}

	@FXML private void handleMenuItemMaintenanceWindowAnalysis(ActionEvent event)
	{
		if (!mainGridPane.getChildren().contains(nodeMaintenanceWindowAnalysis))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeMaintenanceWindowAnalysis, 0, 2);
		}
	}

	@FXML private void handleMenuItemGeneralConfig(ActionEvent event)
	{
		if (!mainGridPane.getChildren().contains(nodeGeneralConfig))
		{
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeGeneralConfig, 0, 2);
		}
	}

	@FXML private void handleMenuItemTTestConfig(ActionEvent event)
	{
		if (!mainGridPane.getChildren().contains(nodeTTestConfig))
		{
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeTTestConfig, 0, 2);
		}
	}

	@FXML private void handleMenuItemRTCResultsAnalysisConfig(ActionEvent event)
	{
		if (!mainGridPane.getChildren().contains(nodeRTCResultsAnalysisConfig))
		{
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeRTCResultsAnalysisConfig, 0, 2);
		}
	}

	@FXML private void handleMenuItemMaintenanceWindowAnalysisConfig(ActionEvent event)
	{
		if (!mainGridPane.getChildren().contains(nodeMaintenanceWindowAnalysisConfig))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeMaintenanceWindowAnalysisConfig, 0, 2);
		}
	}

	@FXML private void handleMenuItemParseConfig(ActionEvent event) 
	{
		if (!mainGridPane.getChildren().contains(nodeParseConfig))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeParseConfig, 0, 2);
		}
	}

	@FXML private void handleMenuItemVersioningAndPermissions(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeVersioningAndPermissions))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeVersioningAndPermissions, 0, 2);
		}
	}

	@FXML private void handleMenuItemBridgeClosureAnalysisConfig(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeBridgeClosureAnalysisConfig))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeBridgeClosureAnalysisConfig, 0, 2);
		}
	}

	@FXML private void handleMenuItemRadixxResSsimConversionIATAExcel(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeRadixxResSsimConversionIATAExcel))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeRadixxResSsimConversionIATAExcel, 0, 2);
		}
	}

	@FXML private void handleMenuItemRadixxResSsimConversionS3Excel(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeRadixxResSsimConversionS3Excel))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeRadixxResSsimConversionS3Excel, 0, 2);
		}
	}    

	@FXML private void handleMenuItemRadixxResSsimComparison(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeRadixxResSsimComparison))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeRadixxResSsimComparison, 0, 2);
		}
	}

	@FXML private void handleMenuItemGradeXingSpeeds(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeGradeXingSpeeds))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeGradeXingSpeeds, 0, 2);
		}
	}

	@FXML private void handleMenuItemGradeXingSpeedsConfig(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeGradeXingSpeedsConfig))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeGradeXingSpeedsConfig, 0, 2);
		}
	}

	@FXML private void handleMenuItemRadixxResSsimConversionConfigIATAExcel(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeRadixxResSsimConversionIATAExcelConfig))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeRadixxResSsimConversionIATAExcelConfig, 0, 2);
		}
	}

	@FXML private void handleMenuItemRadixxResSsimComparisonConfig(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeRadixxResSsimComparisonConfig))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeRadixxResSsimComparisonConfig, 0, 2);
		}
	}

	@FXML private void handleMenuItemUscgBridgeCompliance(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeUscgBridgeCompliance))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeUscgBridgeCompliance, 0, 2);
		}
	}

	@FXML private void handleMenuItemUscgBridgeComplianceConfig(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeUscgBridgeComplianceConfig))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeRecoveryRate);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeUscgBridgeComplianceConfig, 0, 2);
		}
	}
	
	@FXML private void handleMenuItemRecoveryRate(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeRecoveryRate))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRateConfig);

			mainGridPane.add(nodeRecoveryRate, 0, 2);
		}
	}
	
	@FXML private void handleMenuItemRecoveryRateConfig(ActionEvent event) throws IOException
	{
		if (!mainGridPane.getChildren().contains(nodeRecoveryRateConfig))
		{	
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
			mainGridPane.getChildren().remove(nodeTTest);
			mainGridPane.getChildren().remove(nodeGeneralConfig);
			mainGridPane.getChildren().remove(nodeParseConfig);
			mainGridPane.getChildren().remove(nodeTTestConfig);
			mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
			mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
			mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
			mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionIATAExcelConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimConversionS3Excel);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparison);
			mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
			mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
			mainGridPane.getChildren().remove(nodeRadixxResSsimComparisonConfig);
			mainGridPane.getChildren().remove(nodeUscgBridgeCompliance);
			mainGridPane.getChildren().remove(nodeUscgBridgeComplianceConfig);
			mainGridPane.getChildren().remove(nodeRecoveryRate);

			mainGridPane.add(nodeRecoveryRateConfig, 0, 2);
		}
	}

	@FXML private void handleMenuItemExit(ActionEvent event) 
	{
		System.exit(0);
	}
}