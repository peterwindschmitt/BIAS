package com.bl.bias.tools;

public class ConvertNumberDatatypes 
{
	public static double round (double value, int precision) 
	{
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}	
}
