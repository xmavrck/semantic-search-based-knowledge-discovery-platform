package com.sarah.web_crawler.dto;

public class Head
{
    private String[] vars;

    public String[] getVars ()
    {
        return vars;
    }

    public void setVars (String[] vars)
    {
        this.vars = vars;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [vars = "+vars+"]";
    }
}
		
