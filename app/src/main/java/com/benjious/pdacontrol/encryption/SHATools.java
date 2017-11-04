package com.benjious.pdacontrol.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


/**
 * SHAժҪ�㷨��ʵ����
 * @author Wanna
 *
 */
public class SHATools implements MessageDigester
{
	private static final String algorithm = "SHA"; // ����ժҪ�㷨

	private MessageDigest SHAmes;

	public MessageDigest getSHAmes()
	{
		return SHAmes;
	}

	public void setSHAmes(MessageDigest ames)
	{
		SHAmes = ames;
	}

	/**
	 * ���췽��������MessageDigest���󣬹���ϢժҪʹ��
	 * 
	 */
	public SHATools()
	{
		try
		{
			SHAmes = MessageDigest.getInstance(algorithm);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ��ϢժҪ�����ֽ����������������Կkey������null����,
	 */
	public String encrypt(byte[] plainText, Key key) throws Exception
	{
		if (plainText == null || plainText.length == 0)
		{
			return null;
		}
		if (key != null)
		{
			return null; // ��ϢժҪ����Ҫ������Կ
		}

		return this.byte2hex(this.basicDigester(plainText));
		// return new String(this.DigestePlain(plainText));

	}

	/**
	 * ��ϢժҪ�����ַ���������������Կkey������null����,
	 */
	public String encrypt(String plainText, Key key) throws Exception
	{
		if (plainText == null)
		{
			return null;
		}
		if (key != null)
		{
			return null;
		}

		byte[] input = plainText.getBytes();

		return this.byte2hex(this.basicDigester(input));
		// return new String(this.DigestePlain(input));
	}

	/**
	 * ��ϢժҪ�����ļ�������������Կkey������null����,
	 */
	public String encrypt(File file, Key key) throws Exception
	{
		if (file == null)
		{
			return null;
		}
		if (!file.exists()||file.isDirectory())
		{
			return null;
		}
		if (key != null)
		{
			return null;
		}

		byte[] input = new byte[(int) file.length()];
		FileInputStream fis = null;

		try
		{
			fis = new FileInputStream(file);
			for (int i = 0; i < file.length(); i++)
			{
				input[i] = (byte) fis.read();
			}
			// return new String(this.DigestePlain(input));
			return this.byte2hex(this.basicDigester(input));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			fis.close();
		}
	}

	/**
	 * ��ϢժҪ�����������е����ݲ�����������Կkey������null����,
	 */
	public String encrypt(InputStream plainText, Key key) throws Exception
	{
		if (plainText == null)
		{
			return null;
		}
		if (key != null)
		{
			return null;
		}
		int length = -1;
		ArrayList temp = new ArrayList();
		try
		{
			while ((length = plainText.read()) != -1)
			{
				temp.add((byte) length);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		byte[] input = new byte[temp.size()];

		for (int i = 0; i < temp.size(); i++)
		{
			Byte bt = (Byte) temp.get(i);
			input[i] = bt.byteValue();
		}
		// return new String(this.DigestePlain(input));
		return this.byte2hex(this.basicDigester(input));
	}

	/**
	 * ��ϢժҪ�Ļ����������������������á�
	 * 
	 * @param plain   Ҫ����ժҪ������ֽ�����
	 * @return ժҪ����������
	 */
	private byte[] basicDigester(byte[] input)
	{
		MessageDigest md = this.getSHAmes();

		md.update(input);

		return md.digest();
	}

	/**
	 * ���ֽ�ת�����ַ���
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b)
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++)
		{
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
			{
				hs = hs + "0" + stmp;
			}
			else
			{
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
}
