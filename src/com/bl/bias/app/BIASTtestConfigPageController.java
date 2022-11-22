package com.bl.bias.app;

import java.util.function.UnaryOperator;
import java.util.prefs.Preferences;

import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BIASTtestConfigPageController
{	
	private static Preferences prefs;
	
	private static Integer defaultAlphaLosIndex = 1;
	
	private static Boolean showSlightAndEqualTstatColumns;
	private static Boolean suppressTypeResultsWhenAssignedToCustomAssignment;
	
	private static ObservableList<String> losValues =  FXCollections.observableArrayList("0.01 (1%)", "0.05 (5%)", "0.10 (10%)");
	
	private static Color[] tTestColors = new Color[8];
	
	private static Integer defaultTTestColumnColorIndex = 7; // Grey
		
	private static Integer tTestColumn1ColorIndex;
	private static Integer tTestColumn2ColorIndex;
	private static Integer tTestColumn3ColorIndex;
	private static Integer tTestColumn4ColorIndex;
	private static Integer tTestColumn5ColorIndex;
	
	@FXML private Label statisticsSectionLabel;
	@FXML private Label losLabel;
	@FXML private Label tTestColumn1Label;
	@FXML private Label tTestColumn2Label;
	@FXML private Label tTestColumn3Label;
	@FXML private Label tTestColumn4Label;
	@FXML private Label tTestColumn5Label;	
	
	@FXML private CheckBox showSlightAndEqualTstatsCheckbox;
	@FXML private CheckBox suppressTypeResultsWhenAssignedToCustomAssignmentsCheckbox;
	
	@FXML private ComboBox<String> losCombobox;
	@FXML private ComboBox<Color> tTestColumn1ColorCombobox;
	@FXML private ComboBox<Color> tTestColumn2ColorCombobox;
	@FXML private ComboBox<Color> tTestColumn3ColorCombobox;
	@FXML private ComboBox<Color> tTestColumn4ColorCombobox;
	@FXML private ComboBox<Color> tTestColumn5ColorCombobox;
		
	@FXML private Button generalNextPageButton;
	@FXML private Button generalPreviousPageButton;
	
	@FXML private GridPane generalConfigGridPanePage1;
	@FXML private GridPane generalConfigGridPanePage2;
	
	@FXML private void initialize() 
	{
		prefs = Preferences.userRoot().node("BIAS");
			
		// See if preference is stored for showing slight and equal t-stat columns
		if (prefs.getBoolean("tt_showSlightAndEqualTstatColumns", true))
		{
			showSlightAndEqualTstatColumns = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("tt_showSlightAndEqualTstatColumns", true);
			tTestColumn3Label.setText("Equal");
			tTestColumn2Label.setVisible(true);
			tTestColumn4Label.setVisible(true);
			tTestColumn2ColorCombobox.setVisible(true);
			tTestColumn4ColorCombobox.setVisible(true);
			showSlightAndEqualTstatsCheckbox.setSelected(true);
		}
		else
		{
			showSlightAndEqualTstatColumns = false;
			tTestColumn3Label.setText("Not Significantly Different");
			tTestColumn2Label.setVisible(false);
			tTestColumn4Label.setVisible(false);
			tTestColumn2ColorCombobox.setVisible(false);
			tTestColumn4ColorCombobox.setVisible(false);
			showSlightAndEqualTstatsCheckbox.setSelected(false);
		}
		
		// See if alpha preference is stored
		losCombobox.setItems(losValues);
		
		boolean alphaExists = prefs.get("tt_alphaLosIndex", null) != null;
		if (alphaExists)
		{
			losCombobox.getSelectionModel().select(prefs.getInt("tt_alphaLosIndex", defaultAlphaLosIndex));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("tt_alphaLosIndex", defaultAlphaLosIndex);
			losCombobox.getSelectionModel().select(getDefaultAlphaIndex());
		}				

		// See if preference is stored for suppressing individual train types when custom assignments are enabled
		if (prefs.getBoolean("tt_suppressTypeResultsWhenAssignedToCustomAssignment", true))
		{
			suppressTypeResultsWhenAssignedToCustomAssignment = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("tt_suppressTypeResultsWhenAssignedToCustomAssignment", true);
			suppressTypeResultsWhenAssignedToCustomAssignmentsCheckbox.setSelected(true);
		}
		else
		{
			suppressTypeResultsWhenAssignedToCustomAssignment = false;
			suppressTypeResultsWhenAssignedToCustomAssignmentsCheckbox.setSelected(false);
		}
		
		// Initialize t-test column colors  -- note that these colors can not be directly used in the WriteTTest classes because they use indexed colors
		tTestColors[0] = Color.RED;  
		tTestColors[1] = Color.CORAL;
		tTestColors[2] = Color.YELLOW;
		tTestColors[3] = Color.LIGHTGREEN;
		tTestColors[4] = Color.GREEN;
		tTestColors[5] = Color.BLUE;
		tTestColors[6] = Color.LIGHTBLUE;
		tTestColors[7] = Color.GRAY;
	    
		tTestColumn1ColorCombobox.getItems().addAll(tTestColors);
		tTestColumn2ColorCombobox.getItems().addAll(tTestColors);
		tTestColumn3ColorCombobox.getItems().addAll(tTestColors);
		tTestColumn4ColorCombobox.getItems().addAll(tTestColors);
		tTestColumn5ColorCombobox.getItems().addAll(tTestColors);
		
		tTestColumn1ColorCombobox.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() 
		{
			@Override public ListCell<Color> call(ListView<Color> p) 
			{
				return new ListCell<Color>() 
				{
					private final Rectangle rectangle;
					{ 
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
						rectangle = new Rectangle(20, 10);
					}

					@Override protected void updateItem(Color item, boolean empty) 
					{
						super.updateItem(item, empty);

						if (item == null || empty) 
						{
							setGraphic(null);
						} 
						else 
						{
							rectangle.setFill(item);
							setGraphic(rectangle);
						}
					}
				};
			}
		});

		tTestColumn1ColorCombobox.setButtonCell(new ListCell<Color>() 
		{
			private final Rectangle rectangle;
			{ 
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
				rectangle = new Rectangle(20, 10);
			}

			@Override protected void updateItem(Color item, boolean empty) 
			{
				super.updateItem(item, empty);

				if (item == null || empty) 
				{
					setGraphic(null);
				} 
				else 
				{
					rectangle.setFill(item);
					setGraphic(rectangle);
				}
			}
		});
		
		tTestColumn2ColorCombobox.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() 
		{
			@Override public ListCell<Color> call(ListView<Color> p) 
			{
				return new ListCell<Color>() 
				{
					private final Rectangle rectangle;
					{ 
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
						rectangle = new Rectangle(20, 10);
					}

					@Override protected void updateItem(Color item, boolean empty) 
					{
						super.updateItem(item, empty);

						if (item == null || empty) 
						{
							setGraphic(null);
						} 
						else 
						{
							rectangle.setFill(item);
							setGraphic(rectangle);
						}
					}
				};
			}
		});

		tTestColumn2ColorCombobox.setButtonCell(new ListCell<Color>() 
		{
			private final Rectangle rectangle;
			{ 
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
				rectangle = new Rectangle(20, 10);
			}

			@Override protected void updateItem(Color item, boolean empty) 
			{
				super.updateItem(item, empty);

				if (item == null || empty) 
				{
					setGraphic(null);
				} 
				else 
				{
					rectangle.setFill(item);
					setGraphic(rectangle);
				}
			}
		});
		
		tTestColumn3ColorCombobox.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() 
		{
			@Override public ListCell<Color> call(ListView<Color> p) 
			{
				return new ListCell<Color>() 
				{
					private final Rectangle rectangle;
					{ 
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
						rectangle = new Rectangle(20, 10);
					}

					@Override protected void updateItem(Color item, boolean empty) 
					{
						super.updateItem(item, empty);

						if (item == null || empty) 
						{
							setGraphic(null);
						} 
						else 
						{
							rectangle.setFill(item);
							setGraphic(rectangle);
						}
					}
				};
			}
		});

		tTestColumn3ColorCombobox.setButtonCell(new ListCell<Color>() 
		{
			private final Rectangle rectangle;
			{ 
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
				rectangle = new Rectangle(20, 10);
			}

			@Override protected void updateItem(Color item, boolean empty) 
			{
				super.updateItem(item, empty);

				if (item == null || empty) 
				{
					setGraphic(null);
				} 
				else 
				{
					rectangle.setFill(item);
					setGraphic(rectangle);
				}
			}
		});
		
		tTestColumn4ColorCombobox.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() 
		{
			@Override public ListCell<Color> call(ListView<Color> p) 
			{
				return new ListCell<Color>() 
				{
					private final Rectangle rectangle;
					{ 
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
						rectangle = new Rectangle(20, 10);
					}

					@Override protected void updateItem(Color item, boolean empty) 
					{
						super.updateItem(item, empty);

						if (item == null || empty) 
						{
							setGraphic(null);
						} 
						else 
						{
							rectangle.setFill(item);
							setGraphic(rectangle);
						}
					}
				};
			}
		});

		tTestColumn4ColorCombobox.setButtonCell(new ListCell<Color>() 
		{
			private final Rectangle rectangle;
			{ 
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
				rectangle = new Rectangle(20, 10);
			}

			@Override protected void updateItem(Color item, boolean empty) 
			{
				super.updateItem(item, empty);

				if (item == null || empty) 
				{
					setGraphic(null);
				} 
				else 
				{
					rectangle.setFill(item);
					setGraphic(rectangle);
				}
			}
		});
		
		tTestColumn5ColorCombobox.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() 
		{
			@Override public ListCell<Color> call(ListView<Color> p) 
			{
				return new ListCell<Color>() 
				{
					private final Rectangle rectangle;
					{ 
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
						rectangle = new Rectangle(20, 10);
					}

					@Override protected void updateItem(Color item, boolean empty) 
					{
						super.updateItem(item, empty);

						if (item == null || empty) 
						{
							setGraphic(null);
						} 
						else 
						{
							rectangle.setFill(item);
							setGraphic(rectangle);
						}
					}
				};
			}
		});

		tTestColumn5ColorCombobox.setButtonCell(new ListCell<Color>() 
		{
			private final Rectangle rectangle;
			{ 
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
				rectangle = new Rectangle(20, 10);
			}

			@Override protected void updateItem(Color item, boolean empty) 
			{
				super.updateItem(item, empty);

				if (item == null || empty) 
				{
					setGraphic(null);
				} 
				else 
				{
					rectangle.setFill(item);
					setGraphic(rectangle);
				}
			}
		});
		
		// See if preference is stored t-test column 1 color
		boolean col1TTestColorExists = prefs.get("tt_tTestColumn1ColorIndex", null) != null;
		
		if (col1TTestColorExists)
		{
			tTestColumn1ColorCombobox.getSelectionModel().select(prefs.getInt("tt_tTestColumn1ColorIndex", getDefaultTTestColumnColorIndex()));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("tt_tTestColumn1ColorIndex", getDefaultTTestColumnColorIndex());
			tTestColumn1ColorCombobox.getSelectionModel().select(getDefaultTTestColumnColorIndex());
		}	
		
		// See if preference is stored t-test column 2 color
		boolean col2TTestColorExists = prefs.get("tt_tTestColumn2ColorIndex", null) != null;

		if (col2TTestColorExists)
		{
			tTestColumn2ColorCombobox.getSelectionModel().select(prefs.getInt("tt_tTestColumn2ColorIndex", getDefaultTTestColumnColorIndex()));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("tt_tTestColumn2ColorIndex", getDefaultTTestColumnColorIndex());
			tTestColumn2ColorCombobox.getSelectionModel().select(getDefaultTTestColumnColorIndex());
		}	

		// See if preference is stored t-test column 3 color
		boolean col3TTestColorExists = prefs.get("tt_tTestColumn3ColorIndex", null) != null;

		if (col3TTestColorExists)
		{
			tTestColumn3ColorCombobox.getSelectionModel().select(prefs.getInt("tt_tTestColumn3ColorIndex", getDefaultTTestColumnColorIndex()));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("tt_tTestColumn3ColorIndex", getDefaultTTestColumnColorIndex());
			tTestColumn3ColorCombobox.getSelectionModel().select(getDefaultTTestColumnColorIndex());
		}	

		// See if preference is stored t-test column 4 color
		boolean col4TTestColorExists = prefs.get("tt_tTestColumn4ColorIndex", null) != null;

		if (col4TTestColorExists)
		{
			tTestColumn4ColorCombobox.getSelectionModel().select(prefs.getInt("tt_tTestColumn4ColorIndex", getDefaultTTestColumnColorIndex()));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("tt_tTestColumn4ColorIndex", getDefaultTTestColumnColorIndex());
			tTestColumn4ColorCombobox.getSelectionModel().select(getDefaultTTestColumnColorIndex());
		}	

		// See if preference is stored t-test column 5 color
		boolean col5TTestColorExists = prefs.get("tt_tTestColumn5ColorIndex", null) != null;

		if (col5TTestColorExists)
		{
			tTestColumn5ColorCombobox.getSelectionModel().select(prefs.getInt("tt_tTestColumn5ColorIndex", getDefaultTTestColumnColorIndex()));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putInt("tt_tTestColumn5ColorIndex", getDefaultTTestColumnColorIndex());
			tTestColumn5ColorCombobox.getSelectionModel().select(getDefaultTTestColumnColorIndex());
		}							
	};
						
	@FXML private void handleShowSlightAndEqualTstatCheckbox()
	{
		if (showSlightAndEqualTstatColumns)
		{
			showSlightAndEqualTstatColumns = false;
			tTestColumn3Label.setText("Not Significantly Different");
			tTestColumn2Label.setVisible(false);
			tTestColumn4Label.setVisible(false);
			tTestColumn2ColorCombobox.setVisible(false);
			tTestColumn4ColorCombobox.setVisible(false);
			showSlightAndEqualTstatsCheckbox.setSelected(false);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("tt_showSlightAndEqualTstatColumns", false);
		}
		else
		{
			showSlightAndEqualTstatColumns = true;
			tTestColumn3Label.setText("Equal");
			tTestColumn2Label.setVisible(true);
			tTestColumn4Label.setVisible(true);
			tTestColumn2ColorCombobox.setVisible(true);
			tTestColumn4ColorCombobox.setVisible(true);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("tt_showSlightAndEqualTstatColumns", true);
		}
	}
	
	public static Boolean getShowSlightAndEqualTstatColumns()
	{
		return showSlightAndEqualTstatColumns;
	}
	
	@FXML private void handleLosCombobox()
	{
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("tt_alphaLosIndex", losCombobox.getSelectionModel().getSelectedIndex());
	}
	
	public static ObservableList<String> getLosValues()
	{
		return losValues;
	}
	
	public static Integer getDefaultAlphaIndex()
	{
		return defaultAlphaLosIndex;
	}
	
	public static Integer getDefaultTTestColumnColorIndex()
	{
		return defaultTTestColumnColorIndex;
	}

	@FXML private void handleSuppressTypeResultsWhenAssignedToCustomAssignmentsCheckbox()
	{
		if (suppressTypeResultsWhenAssignedToCustomAssignment)
		{
			suppressTypeResultsWhenAssignedToCustomAssignment = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("tt_suppressTypeResultsWhenAssignedToCustomAssignment", false);
		}
		else
		{
			suppressTypeResultsWhenAssignedToCustomAssignment = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("tt_suppressTypeResultsWhenAssignedToCustomAssignment", true);
		}
	}
	
	public static Boolean getSuppressTypeResultsWhenAssignedToCustomAssignment()
	{
		return suppressTypeResultsWhenAssignedToCustomAssignment;
	}
	
	@FXML private void handletTestColumn1ColorCombobox(ActionEvent event) 
	{	
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("tt_tTestColumn1ColorIndex", tTestColumn1ColorCombobox.getSelectionModel().getSelectedIndex());
	}
	
	@FXML private void handletTestColumn2ColorCombobox(ActionEvent event) 
	{	
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("tt_tTestColumn2ColorIndex", tTestColumn2ColorCombobox.getSelectionModel().getSelectedIndex());
	}
	
	@FXML private void handletTestColumn3ColorCombobox(ActionEvent event) 
	{	
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("tt_tTestColumn3ColorIndex", tTestColumn3ColorCombobox.getSelectionModel().getSelectedIndex());
	}
	
	@FXML private void handletTestColumn4ColorCombobox(ActionEvent event) 
	{	
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("tt_tTestColumn4ColorIndex", tTestColumn4ColorCombobox.getSelectionModel().getSelectedIndex());
	}
	
	@FXML private void handletTestColumn5ColorCombobox(ActionEvent event) 
	{	
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putInt("tt_tTestColumn5ColorIndex", tTestColumn5ColorCombobox.getSelectionModel().getSelectedIndex());
	}
		
	public static Integer getTTestColumn1ColorIndex()
	{
		return tTestColumn1ColorIndex;
	}
	
	public static Integer getTTestColumn2ColorIndex()
	{
		return tTestColumn2ColorIndex;
	}
	
	public static Integer getTTestColumn3ColorIndex()
	{
		return tTestColumn3ColorIndex;
	}
	
	public static Integer getTTestColumn4ColorIndex()
	{
		return tTestColumn4ColorIndex;
	}
	
	public static Integer getTTestColumn5ColorIndex()
	{
		return tTestColumn5ColorIndex;
	}
	
	public static Color[] getTTestColors()
	{
		return tTestColors;
	}
	UnaryOperator<Change> integerFilter2digits = change -> {
	    String newText = change.getControlNewText();
	    if (newText.matches("([0-9]{0,2})")) { 
	        return change;
	    }
	    return null;
	};
	
	UnaryOperator<Change> integerFilter3digits = change -> {
	    String newText = change.getControlNewText();
	    if (newText.matches("([0-9]{0,3})")) { 
	        return change;
	    }
	    return null;
	};
}