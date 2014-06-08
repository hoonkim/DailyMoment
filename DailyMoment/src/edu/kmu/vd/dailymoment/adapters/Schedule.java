package edu.kmu.vd.dailymoment.adapters;
//@TODO 복구.
public class Schedule
{
  private int mCategoryId;
  private String mEndTime;
  private String mStartTime;
  private String mTitle;

  public Schedule(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    this.mCategoryId = paramInt;
    this.mStartTime = paramString1;
    this.mEndTime = paramString2;
    this.mTitle = paramString3;
  }

  public String getDate()
  {
    return this.mStartTime + " - " + this.mEndTime;
  }

  public int getIcon()
  {
    switch (this.mCategoryId)
    {
    default:
      return 2130837595;
    case 0:
      return 2130837594;
    case 1:
      return 2130837596;
    case 2:
      return 2130837597;
    case 3:
      return 2130837598;
    case 4:
      return 2130837599;
    case 5:
      return 2130837600;
    case 6:
      return 2130837601;
    case 7:
      return 2130837602;
    case 8:
    }
    return 2130837603;
  }

  public String getTitle()
  {
    return this.mTitle;
  }
}