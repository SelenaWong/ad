package com.app.ad.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import android.util.Log;

public class TCPClient implements INetworkTransmission {
	private String TAG = "TCPClient";
	private String ip;
	private int port;
	private Socket socket;
	private BufferedInputStream inputStream;
	private BufferedOutputStream outputStream;
	private TCPClientListener listener;
	private byte[] buffer;

	public TCPClient() {
		this.buffer = new byte[1024];
	}

	public TCPClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.buffer = new byte[1024];
	}

	public void setParameters(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public TCPClientListener getListener() {
		return this.listener;
	}

	public void setListener(TCPClientListener listener) {
		this.listener = listener;
	}

	public synchronized boolean open() {
		this.socket = new Socket();
		new Thread(new Runnable() {
			public void run() {
				try {
					TCPClient.this.socket.setSoTimeout(15 * 1000);// 读写超时
					TCPClient.this.socket.connect(new InetSocketAddress(
							TCPClient.this.ip, TCPClient.this.port), 10 * 1000);// 连接超时
					TCPClient.this.inputStream = new BufferedInputStream(
							TCPClient.this.socket.getInputStream());
					TCPClient.this.outputStream = new BufferedOutputStream(
							TCPClient.this.socket.getOutputStream());
					if (TCPClient.this.listener != null) {
						TCPClient.this.listener.onConnect(true);
					}
					for (;;) {
						try {
							int length = TCPClient.this.inputStream
									.read(TCPClient.this.buffer);
							Log.i(TAG, "recv data");
							if (length > 0) {
								TCPClient.this.onReceive(TCPClient.this.buffer,
										length);
								TCPClient.this.close();
								break;
							}
							try {
								Thread.sleep(10L);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}
				} catch (SocketException ex) {
					Log.i("TCPClient", "SocketException");
					ex.printStackTrace();
					TCPClient.this.listener.onConnect(false);
				} catch (SocketTimeoutException e) {
					Log.i("TCPClient", "SocketTimeOutException");
					e.printStackTrace();
					TCPClient.this.listener.onConnect(false);
				} catch (IOException e1) {
					e1.printStackTrace();
					TCPClient.this.listener.onConnect(false);
				}
			}
		}).start();
		return true;
	}

	public synchronized void close() {
		Log.i(TAG, "close");
		if (this.socket != null) {
			try {
				if (!this.socket.isClosed()) {
					this.socket.close();
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean send(String text) {
		if (this.outputStream != null) {
			Log.i(TAG, "TCPClient send:" + text);
			try {
				this.outputStream.write(text.getBytes("utf-8"));// this.outputStream.write(text.getBytes("GBK"));
				this.outputStream.flush();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public void onReceive(byte[] buffer, int length) {
		if (this.listener != null) {
			this.listener.onReceive(buffer, length);
		}
	}

	public static abstract interface TCPClientListener {
		public abstract void onConnect(boolean paramBoolean);

		public abstract void onReceive(byte[] paramArrayOfByte, int paramInt);
	}
}
