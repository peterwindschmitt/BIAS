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
    @FXML private MenuItem menuItemRadixxResSsimConversion;
    @FXML private MenuItem menuItemGradeXingSpeeds;
    @FXML private MenuItem menuItemExit; 
    @FXML private MenuItem menuItemRTCResultsAnalysisConfig;
    @FXML private MenuItem menuItemTTestConfig;
    @FXML private MenuItem menuItemBridgeClosureAnalysisConfig;
    @FXML private MenuItem menuItemMaintenanceWindowAnalysisConfig;
    @FXML private MenuItem menuItemRadixxResSsimConversionConfig;
    @FXML private MenuItem menuItemGradeXingSpeedsConfig;
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
    @FXML private Node nodeRadixxResSsimConversion;
    @FXML private Node nodeRadixxResSsimConversionConfig;
    @FXML private Node nodeGradeXingSpeeds;
    @FXML private Node nodeGradeXingSpeedsConfig;
       
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
        nodeRadixxResSsimConversion = FXMLLoader.load(getClass().getResource("BIASRadixxResSsimConversionPage.fxml"));
        nodeRadixxResSsimConversionConfig = FXMLLoader.load(getClass().getResource("BIASRadixxResSsimConversionConfigPage.fxml"));
        nodeGradeXingSpeeds = FXMLLoader.load(getClass().getResource("BIASGradeXingSpeedsPage.fxml"));
        nodeGradeXingSpeedsConfig = FXMLLoader.load(getClass().getResource("BIASGradeXingSpeedsConfigPage.fxml"));
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
	    	else if (permittedModules[i].toString().contains("Bridge"))
	    	{
	    		menuItemBridgeClosureAnalysis.setVisible(true);
	    		subMenuModuleConfig.setVisible(true);
	    		menuItemBridgeClosureAnalysisConfig.setVisible(true);
	    		
	    		if (i == 0)
	    		{
	    			handleMenuItemBridgeClosureAnalysis(null);
	    		}
	    	}
	    	else if (permittedModules[i].toString().contains("Radixx"))
	    	{
	    		menuItemRadixxResSsimConversion.setVisible(true);
	    		subMenuModuleConfig.setVisible(true);
	    		menuItemRadixxResSsimConversionConfig.setVisible(true);
	    		
	    		if (i == 0)
	    		{
	    			handleMenuItemRadixxResSsimConversion(null);
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
	    	mainGridPane.getChildren().remove(nodeTTestConfig);
	    	mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    	
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
    		mainGridPane.getChildren().remove(nodeTTestConfig);
    		mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
    		mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
    		mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
    		mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
    		mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
    		mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
    		mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    		    	    	
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeParseConfig);
	    	mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
	    	mainGridPane.getChildren().remove(nodeTTestConfig);
	    	mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    		    	
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeParseConfig);
	    	mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
	    	mainGridPane.getChildren().remove(nodeTTestConfig);
	    	mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    		    	    	
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
	    	mainGridPane.getChildren().remove(nodeTTestConfig);
	    	mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    		    	
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeParseConfig);
           	mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
           	mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
           	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
           	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
           	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
           	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
           	mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
           	mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    		    	
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeParseConfig);
	    	mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
	    	mainGridPane.getChildren().remove(nodeTTestConfig);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    		    	
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
	    	mainGridPane.getChildren().remove(nodeTTestConfig);
	    	mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
	    	mainGridPane.getChildren().remove(nodeParseConfig);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    		    	
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
	    	mainGridPane.getChildren().remove(nodeTTestConfig);
	    	mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    		    	
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeParseConfig);
		    mainGridPane.getChildren().remove(nodeTTestConfig);
		    mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    		    	
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeParseConfig);
		    mainGridPane.getChildren().remove(nodeTTestConfig);
		    mainGridPane.getChildren().remove(nodeRTCResultsAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysis);
	    	mainGridPane.getChildren().remove(nodeMaintenanceWindowAnalysisConfig);
	    	mainGridPane.getChildren().remove(nodeBridgeClosureAnalysis);
	    	mainGridPane.getChildren().remove(nodeVersioningAndPermissions);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    		    	
		    mainGridPane.add(nodeBridgeClosureAnalysisConfig, 0, 2);
	    }
    }
    
    @FXML private void handleMenuItemRadixxResSsimConversion(ActionEvent event) throws IOException
    {
    	if (!mainGridPane.getChildren().contains(nodeRadixxResSsimConversion))
    	{	
	    	mainGridPane.getChildren().remove(nodeRTCResultsAnalysis);
		    mainGridPane.getChildren().remove(nodeTTest);
		    mainGridPane.getChildren().remove(nodeGeneralConfig);
		    mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
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
	    		    	
		    mainGridPane.add(nodeRadixxResSsimConversion, 0, 2);
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    		    	
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversionConfig);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
	    		    	
		    mainGridPane.add(nodeGradeXingSpeedsConfig, 0, 2);
	    }
    }
    
    @FXML private void handleMenuItemRadixxResSsimConversionConfig(ActionEvent event) throws IOException
    {
    	if (!mainGridPane.getChildren().contains(nodeRadixxResSsimConversionConfig))
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
	    	mainGridPane.getChildren().remove(nodeRadixxResSsimConversion);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeeds);
	    	mainGridPane.getChildren().remove(nodeGradeXingSpeedsConfig);
	    		    	
		    mainGridPane.add(nodeRadixxResSsimConversionConfig, 0, 2);
	    }
    }
    
    @FXML private void handleMenuItemExit(ActionEvent event) 
    {
        System.exit(0);
    }
}