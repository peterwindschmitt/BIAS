package com.bl.bias.objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MarineAccessPeriod 
{
    private SimpleDoubleProperty marinePeriodStartDouble;
    private SimpleDoubleProperty marinePeriodEndDouble;
    private SimpleBooleanProperty mo;
    private SimpleBooleanProperty tu;
    private SimpleBooleanProperty we;
    private SimpleBooleanProperty th;
    private SimpleBooleanProperty fr;
    private SimpleBooleanProperty sa;
    private SimpleBooleanProperty su;

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

    public String getMarinePeriodStartTime() 
    {
        return null;
    }
    
    public void setMarinePeriodStartDouble(Double marinePeriodStartDouble) 
    {
        this.marinePeriodStartDouble = new SimpleDoubleProperty(marinePeriodStartDouble);
    }
    
    public String getMarinePeriodEndTime() 
    {
        return null;
    }
    
    public Double getMarinePeriodEndDouble() 
    {
        return marinePeriodEndDouble.get();
    }

    public void setMarinePeriodEndDouble(Double marinePeriodEndDouble) 
    {
        this.marinePeriodEndDouble = new SimpleDoubleProperty(marinePeriodEndDouble);
    }

    public boolean getMo() 
    {
        return mo.get();
    }

    public void setMo(Boolean mo) 
    {
        this.mo = new SimpleBooleanProperty(mo);
    }
    
    public boolean getTu() 
    {
        return tu.get();
    }

    public void setTu(Boolean tu) 
    {
        this.tu = new SimpleBooleanProperty(tu);
    }
    
    public boolean getWe() 
    {
        return we.get();
    }

    public void setWe(Boolean we) 
    {
        this.we = new SimpleBooleanProperty(we);
    }
    
    public boolean getTh() 
    {
        return th.get();
    }

    public void setTh(Boolean th) 
    {
        this.th = new SimpleBooleanProperty(th);
    }
    
    public boolean getFr() 
    {
        return fr.get();
    }

    public void setFr(Boolean fr) 
    {
        this.fr = new SimpleBooleanProperty(fr);
    }
    
    public boolean getSa() 
    {
        return sa.get();
    }

    public void setSa(Boolean sa) 
    {
        this.sa = new SimpleBooleanProperty(sa);
    }
    
    public boolean getSu() 
    {
        return su.get();
    }

    public void setSu(Boolean su) 
    {
        this.su = new SimpleBooleanProperty(su);
    }
}