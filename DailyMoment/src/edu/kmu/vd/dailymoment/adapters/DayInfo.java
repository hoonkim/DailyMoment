package edu.kmu.vd.dailymoment.adapters;
public class DayInfo
{
  private String day;
  private boolean inMonth;

  public String getDay()
  {
    return this.day;
  }

  public boolean isInMonth()
  {
    return this.inMonth;
  }

  public void setDay(String paramString)
  {
    this.day = paramString;
  }

  public void setInMonth(boolean paramBoolean)
  {
    this.inMonth = paramBoolean;
  }
}