package com.bl.bias.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ParseLocationFormatA 
{
    private SimpleStringProperty parameterName;
    private SimpleStringProperty registryKey;
    private SimpleIntegerProperty typeStartColumn;
    private SimpleIntegerProperty typeEndColumn;
    private SimpleIntegerProperty groupStartColumn;
    private SimpleIntegerProperty groupEndColumn;

    public ParseLocationFormatA(String parameterName, String registryKey, Integer typeStartColumn, Integer typeEndColumn, Integer groupStartColumn, Integer groupEndColumn) 
    {
        this.parameterName = new SimpleStringProperty(parameterName);
        this.registryKey = new SimpleStringProperty(registryKey);
        this.typeStartColumn = new SimpleIntegerProperty(typeStartColumn);
        this.typeEndColumn = new SimpleIntegerProperty(typeEndColumn);
        this.groupStartColumn = new SimpleIntegerProperty(groupStartColumn);
        this.groupEndColumn = new SimpleIntegerProperty(groupEndColumn);
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

    public int getTypeStartColumn() 
    {
        return typeStartColumn.get();
    }

    public void setTypeStartColumn(Integer typeStartColumn) 
    {
        this.typeStartColumn = new SimpleIntegerProperty(typeStartColumn);
    }

    public int getTypeEndColumn() 
    {
        return typeEndColumn.get();
    }

    public void setTypeEndColumn(int typeEndColumn) 
    {
        this.typeEndColumn = new SimpleIntegerProperty(typeEndColumn);
    }
    
    public int getGroupStartColumn() 
    {
        return groupStartColumn.get();
    }

    public void setGroupStartColumn(Integer groupStartColumn) 
    {
        this.groupStartColumn = new SimpleIntegerProperty(groupStartColumn);
    }

    public int getGroupEndColumn() 
    {
        return groupEndColumn.get();
    }

    public void setGroupEndColumn(int groupEndColumn) 
    {
        this.groupEndColumn = new SimpleIntegerProperty(groupEndColumn);
    }
}