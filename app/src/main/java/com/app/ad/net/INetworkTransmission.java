package com.app.ad.net;

public abstract interface INetworkTransmission
{
  public abstract void setParameters(String paramString, int paramInt);
  
  public abstract boolean open();
  
  public abstract void close();
  
  public abstract boolean send(String paramString);
  
  public abstract void onReceive(byte[] paramArrayOfByte, int paramInt);
}
