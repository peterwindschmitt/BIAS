package com.bl.bias.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ParseLocationFormatB 
{
    private SimpleStringProperty parameterName;
    private SimpleStringProperty registryKey;
    private SimpleIntegerProperty startColumn;
    private SimpleIntegerProperty endColumn;
   
    public ParseLocationFormatB(String parameterName, String registryKey, Integer startColumn, Integer endColumn) 
    {
        this.parameterName = new SimpleStringProperty(parameterName);
        this.registryKey = new SimpleStringProperty(registryKey);
        this.startColumn = new SimpleIntegerProperty(startColumn);
        this.endColumn = new SimpleIntegerProperty(endColumn);
    }

    public String getParameterName() 
    {
        return parameterName.get();
    }

    public void setParameterName(String parameterName) 
    {
        this.parameterName = new SimpleStringProperty(parameterName);
    }
    
    public String getRegistryKey() 
    {
        return registryKey.get();
    }

    public void setRegistryKey(String registryKey) 
    {
        this.registryKey = new SimpleStringProperty(registryKey);
    }

    public int getStartColumn() 
    {
        return startColumn.get();
    }

    public void setStartColumn(Integer startColumn) 
    {
        this.startColumn = new SimpleIntegerProperty(startColumn);
    }

    public int getEndColumn() 
    {
        return endColumn.get();
    }

    public void setEndColumn(int endColumn) 
    {
        this.endColumn = new SimpleIntegerProperty(endColumn);
    }
}