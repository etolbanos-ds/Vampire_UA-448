package unixCommands;
//package com.discovery.scenario;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


public class Trt 
{

	/**
	 * @param args
	 */
	
	public static void cxvcxv() 
	{
		// TODO Auto-generated method stub

		String[] cmdArray = {"C:\\Users\\Eduardo\\Downloads\\putty.exe", "qa-017.dp.discovery.com", "etolbanos-ds"};
		Runtime rt = Runtime.getRuntime();
		try 
		
		{
			Process pr = rt.exec(cmdArray);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	public static void main(String[] args) 
	{
		Session session = null;
		Channel channel = null;
		
		// Commands to be executed, separated by semi colons
		String command = "cd /; ls -l";
		//String hosttoConnect = "QA ENVIRONMENT";
		//String userid = "yourLogin";
		//String password ="Yourpassword";
		String hosttoConnect = "qa-017.dp.discovery.com";
		int port =22;
		String userid = "etolbanos-ds";
		String password ="BZU9KRpd";
		StringBuilder outputBuffer = new StringBuilder();
 
		try 
		{
 
			JSch jsch = new JSch();
			session = jsch.getSession(userid,hosttoConnect, port);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(password);
			session.connect();
 
			// exec 'scp -f rfile' remotely
			channel = session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);
			channel.connect();
			OutputStream out = channel.getOutputStream();
			InputStream commandOutput  = channel.getInputStream();
			int readByte = commandOutput.read();
			while(readByte != 0xffffffff)
			{
				outputBuffer.append((char)readByte);
				readByte = commandOutput.read();
			}
			System.out.println(outputBuffer.toString());
			channel.disconnect();
			session.disconnect();
 
		} 
		catch (JSchException jsche) 
		{
			System.err.println(jsche.getLocalizedMessage());
		} 
		catch (IOException ioe) 
		{
			System.err.println(ioe.getLocalizedMessage());
		} 
		finally 
		{
			channel.disconnect();
			session.disconnect();
		}
 
	}

}