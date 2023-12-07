package com.bl.bias.objects;

import com.bl.bias.tools.ConvertDateTime;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;

public class MarineAccessPeriod 
{
    private SimpleDoubleProperty marinePeriodStartDouble;
    private SimpleDoubleProperty marinePeriodEndDouble;
    private ObservableValue<Boolean> mo;
    private ObservableValue<Boolean> tu;
    private ObservableValue<Boolean> we;
    private ObservableValue<Boolean> th;
    private ObservableValue<Boolean> fr;
    private ObservableValue<Boolean> sa;
    private ObservableValue<Boolean> su;

    public MarineAccessPeriod(Double marinePeriodStartDouble, Double marinePeriodEndDouble, Boolean mo, Boolean tu, Boolean we, Boolean th, Boolean fr, Boolean sa, Boolean su) 
    {
        this.marinePeriodStartDouble = new SimpleDoubleProperty(marinePeriodStartDouble);
        this.marinePeriodEndDouble = new SimpleDoubleProperty(marinePeriodEndDouble);
        this.mo = new SimpleBooleanProperty(mo);
        this.tu = new SimpleBooleanProperty(tu);
        this.we = new SimpleBooleanProperty(we);
        this.th = new SimpleBooleanProperty(th);
        this.fr = new SimpleBooleanProperty(fr);
        this.sa = new SimpleBooleanProperty(sa);
        this.su = new SimpleBooleanProperty(su);
    }

    public Double getMarinePeriodStartDouble() 
    {
        return marinePeriodStartDouble.get();
    }

    public ObservableValue<String> getMarinePeriodStartTime() 
    {
        String timeAsString = ConvertDateTime.convertSerialToHHMMString(marinePeriodStartDouble.doubleValue());
        ObservableValue<String> timeAsObservableString = new ReadOnlyObjectWrapper<String>(timeAsString);
    	return timeAsObservableString;
    }
    
    public void setMarinePeriodStartDouble(Double marinePeriodStartDouble) 
    {
        this.marinePeriodStartDouble = new SimpleDoubleProperty(marinePeriodStartDouble);
    }
    
    public ObservableValue<String> getMarinePeriodEndTime() 
    {
    	String timeAsString = ConvertDateTime.convertSerialToHHMMString(marinePeriodEndDouble.doubleValue());
        ObservableValue<String> timeAsObservableString = new ReadOnlyObjectWrapper<String>(timeAsString);
    	return timeAsObservableString;
    }
    
    public Double getMarinePeriodEndDouble() 
    {
        return marinePeriodEndDouble.get();
    }

    public void setMarinePeriodEndDouble(Double marinePeriodEndDouble) 
    {
        this.marinePeriodEndDouble = new SimpleDoubleProperty(marinePeriodEndDouble);
    }

    public ObservableValue<Boolean> getMo() 
    {
        return mo;
    }

    public void setMo(Boolean mo) 
    {
        this.mo = new SimpleBooleanProperty(mo);
    }
    
    public ObservableValue<Boolean> getTu() 
    {
        return tu;
    }

    public void setTu(Boolean tu) 
    {
        this.tu = new SimpleBooleanProperty(tu);
    }
    
    public ObservableValue<Boolean> getWe() 
    {
        return we;
    }

    public void setWe(Boolean we) 
    {
        this.we = new SimpleBooleanProperty(we);
    }
    
    public ObservableValue<Boolean> getTh() 
    {
        return th;
    }

    public void setTh(Boolean th) 
    {
        this.th = new SimpleBooleanProperty(th);
    }
    
    public ObservableValue<Boolean> getFr() 
    {
        return fr;
    }

    public void setFr(Boolean fr) 
    {
        this.fr = new SimpleBooleanProperty(fr);
    }
    
    public ObservableValue<Boolean> getSa() 
    {
        return sa;
    }

    public void setSa(Boolean sa) 
    {
        this.sa = new SimpleBooleanProperty(sa);
    }
    
    public ObservableValue<Boolean> getSu() 
    {
        return su;
    }

    public void setSu(Boolean su) 
    {
        this.su = new SimpleBooleanProperty(su);
    }
}