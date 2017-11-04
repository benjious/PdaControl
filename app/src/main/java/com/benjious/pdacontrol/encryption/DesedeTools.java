package com.benjious.pdacontrol.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


/**
 * Desede�����㷨��ʵ���˶��ַ������ļ����������ļӽ��ܹ��ܣ�����ʱ����Ϊnull,���Զ�����Des��Կ��������ʹ�ã����ܲ������봫����Կ
 * �����ܽ��н��ܲ���
 * @author wanna
 *
 */
public class DesedeTools implements SymmetryEncrypt
{

	private final static String algorithm = "DESede"; // �ԳƼ����㷨

	private SecretKey desKey; // ��Կ

	private FileInputStream fis; 

	private FileOutputStream fos;

	public DesedeTools()
	{
	}

	/**
	 *����
	 * 
	 * @param plainText
	 *           ���� �ֽ�������ʽ
	 * @param key
	 *            ���ܵ���Կ�����Ϊnull�����Զ�����һ����Կ������ʹ�ã�����getKey����������ô���Կ
	 * @return ����
	 * @throws Exception
	 */
	public String encrypt(byte[] plainText, Key key) throws Exception
	{
		if (plainText == null || plainText.length == 0)
		{
			return null;
		}
		this.setDesKey(key); //���ݴ�����Կ������ü�����Կ

		return Base64.encode(this.basicEncrypt(plainText)); // Base64���б���ת��
	}

	/**
	 * ����
	 * 
	 * @param plainText
	 *            ����
	 * @param key
	 *           ���ܵ���Կ�����Ϊnull�����Զ�����һ����Կ������ʹ�ã�����getKey����������ô���Կ
	 * @return  ����
	 * @throws Exception
	 */
	public String encrypt(String plainText, Key key) throws Exception
	{
		if (plainText == null)
		{
			return null;
		}
		this.setDesKey(key);

		byte[] input = plainText.getBytes();

		return Base64.encode(this.basicEncrypt(input));
	}

	/**
	 * ���� �����ļ����м��ܣ����ܺ�������Ϣ����ڻ�չ��Ϊ.djm����file��ͬĿ¼�µ��ļ��С�
	 * ����  file:		F:\1.txt
	 *     ����  ��     F:\1.txt.djm
	 * 
	 * @param file
	 *           ����
	 * @param key
	 *          �����������Կ�����keyΪnull�����Զ�����һ����Կ���ڼ��ܣ����ܺ��ͨ��getKey����������ô���Կ
	 * @return �����������Ϣ���ļ� 
	 * @throws Exception
	 */
	public File encrypt(File file, Key key) throws Exception
	{
		if (file == null)
		{
			return null;
		}
		if (!file.exists() || file.isDirectory())
		{
			return null;
		}

		try
		{
			this.setDesKey(key);
			fis = new FileInputStream(file);
			byte[] input = this.getByteFromStream(fis); //��ȡ�������е�����

			byte[] output = this.basicEncrypt(input);

			String cipherFilePath = file.getPath() + ".djm";

			File fileOut = new File(cipherFilePath);

			fos = new FileOutputStream(fileOut);

			for (int i = 0; i < output.length; i++)
			{
				fos.write((int) output[i]);               //��������Ϣд���ļ�
			}
			System.out.println("Des�����ļ��ɹ�");
			return fileOut;                              //����
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;

		}
		finally
		{
			this.closeStream();                            //�ر���
		}
	}

	/**
	 * ���ܣ����������е����ݽ��м��ܴ������ܺ󣬽�������Ϣ�洢��ָ���ļ�(cipherPath)��
	 * @param plainText   ������
	 * @param key         ������Կ�����Ϊnull�����Զ�����һ����Կ
	 * @param cipherPath  ���ܺ󣬴��������Ϣ���ļ�·��
	 * @return  ���������Ϣ���ļ�
	 * @throws Exception
	 */
	public File encrypt(InputStream plainText, Key key, String cipherPath) throws Exception
	{
		if (plainText == null)
		{
			return null;
		}
		if (cipherPath == null || cipherPath.equals(""))
		{
			return null;
		}
		try
		{
			this.setDesKey(key);
			
			byte[] input = this.getByteFromStream(plainText);    //��ȡ�������е�������Ϣ
			
			byte[] output = this.basicEncrypt(input);			  //����

			File fileOut = new File(cipherPath);

			fos = new FileOutputStream(fileOut);

			for (int i = 0; i < output.length; i++)
			{
				fos.write((int) output[i]);                      
			}
			System.out.println("�����ܳɹ���");
			return fileOut;                                    
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			this.closeStream();        
		}
	}

	/**
	 * 
	 * ����
	 * 
	 * @param cipherText ����
	 * @param key        ������Կ
	 * @return           ������Ϣ
	 * @throws Exception
	 */
	public String decrypt(byte[] cipherText, Key key) throws Exception
	{
		if (cipherText == null || cipherText.length == 0)
		{
			return null;
		}
		if (key == null)
		{
			return null;
		}
		else
		{
			this.desKey = (SecretKey) key;
		}
		byte[] input = Base64.decode(cipherText); // ��Base64�Լ��ܺ���ֽ�������н���
		return new String(this.basicDecrypt(input));
	}

	/**
	 * ���� ���������ԿkeyΪnull�����ܽ��н��ܲ���
	 * 
	 * @param cipherText   ����
	 * @param key         �����������ԿKey
	 * @return            ������Ϣ���ַ�����ʽ��  
	 * @throws Exception
	 */
	public String decrypt(String cipherText, Key key) throws Exception
	{
		if (cipherText == null)
		{
			return null;
		}
		if (key == null)
		{
			return null;
		}
		else
		{
			this.desKey = (SecretKey) key;
		}

		byte[] input = Base64.decode(cipherText);

		return new String(this.basicDecrypt(input));
	}

	/**
	 * ���ܣ�����չ��Ϊ.djm������������Ϣ���ļ����н��ܲ��������ܴ�����ļ���Ϣ���ļ���cipherFile��ͬһĿ¼��
	 * ����    cipherFile 			F;\1.txt.djm
	 *   ���ܺ���������Ϣ���ļ�Ϊ 	F:\1.txt   
	 * @param cipherFile   ����������Ϣ���ļ� ��չ��Ϊ.djm
	 * @param key           ������Կ 
	 * @return         �������ļ���Ϣ���ļ�   
	 * @throws Exception
	 */
	public File decrypt(File cipherFile, Key key) throws Exception
	{
		if (cipherFile == null)
		{
			return null;
		}
		if (!cipherFile.exists() || cipherFile.isDirectory())
		{
			return null;
		}

		if (key == null)
		{
			return null;
		}

		this.desKey = (SecretKey) key;                     //���ý�����Կ

		try
		{
			String strPath = cipherFile.getPath();
			if (!strPath.substring(strPath.length() - 4).toLowerCase().equals(".djm"))
			{
				// ֻ����չ��Ϊ.djm���洢������Ϣ���ļ����н��ܴ���
				return null;
			}

			fis = new FileInputStream(cipherFile);

			byte[] input = this.getByteFromStream(fis);

			byte[] output = this.basicDecrypt(input);

			String outFilePath = strPath.substring(strPath.length() - 4);

			File fileOut = new File(outFilePath);           // ���ܺ� �洢������Ϣ���ļ�

			fos = new FileOutputStream(fileOut);

			for (int i = 0; i < output.length; i++)
			{
				fos.write((int) output[i]);
			}
			System.out.println("�ļ����ܳɹ�");
			return fileOut;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.closeStream();
		}

		return null;
	}

	/**
	 * ���� ���������е��������ݽ��ܣ����ܺ�����Ĵ洢��ָ���ļ��У�plainPath��
	 * @param cipherText  ����������Ϣ��������
	 * @param key         �����������Կ
	 * @param plainPath   ���ܺ���������ŵ��ļ�·��
	 * @return ���ܺ󣬴���������Ϣ���ļ�
	 * @throws Exception
	 */
	public File decrypt(InputStream cipherText, Key key, String plainPath) throws Exception
	{
		if (cipherText == null || key == null)
		{
			return null;
		}
		if (plainPath == null || plainPath.equals(""))
		{
			return null;
		}
		try
		{
			this.desKey = (SecretKey) key;

			byte[] input = this.getByteFromStream(cipherText);   //��ȡ�������е�����

			byte[] output = this.basicDecrypt(input);            //����

			File outputFile = new File(plainPath);
			fos = new FileOutputStream(outputFile);

			for (int i = 0; i < output.length; i++)
			{
				fos.write((int) output[i]);
			}
			System.out.println("�����ܳɹ�");
			return outputFile;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			this.closeStream();
		}
	}

	/**
	 * ��ü���/���ܲ�������Կ
	 * @return  ����/���ܲ������õ���ԿKey
	 * @throws Exception
	 */
	public Key getKey() throws Exception
	{
		return desKey;
	}

	/**
	 * ���ܴ������������ݣ��ֽ�������ʽ��
	 * @param input   Ҫ���м��ܵ��ֽ�����
	 * @return      ���ܴ������������ݣ��ֽ�������ʽ��
	 * @throws Exception
	 */
	private byte[] basicEncrypt(byte[] input) throws Exception
	{
		Cipher cipher = null;
		try
		{
			cipher = Cipher.getInstance(algorithm);        //创建加密�?�?的Cipher�?

			cipher.init(Cipher.ENCRYPT_MODE, this.desKey); // 用密钥初始化�? cipher�?

			return cipher.doFinal(input);                  //完成加密运算
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 *ʵ�ֻ����Ľ��ܹ��ܣ���������������
	 * 
	 * @param input Ҫ���н��ܵ��ֽ�����
	 * @return    ���ܴ������������ݣ��ֽ�������ʽ��
	 * @throws Exception
	 */
	private byte[] basicDecrypt(byte[] input) throws Exception
	{
		Cipher cipher = null;
		try
		{
			cipher = Cipher.getInstance(algorithm);

			cipher.init(Cipher.DECRYPT_MODE, this.desKey);   //用密钥初始化�? cipher�?

			return cipher.doFinal(input);                   //

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * ���ü�����Կ�����keyΪnull�������generateDesKey������������Des��ԿKey������ʹ��
	 *              �����null,��ʹ�ô������Կ���м��ܲ���
	 * 
	 * @param key 
	 * @throws Exception
	 */
	private void setDesKey(Key key) throws Exception
	{
		if (key == null)
		{
			desKey = (SecretKey) this.generateDesKey();
		}
		else
		{
			desKey = (SecretKey) key;
		}
	}

	/**
	 * ����Desede�㷨����Կ
	 * 
	 * @return
	 */
	private Key generateDesKey() throws Exception
	{

		// KeyGenerator�?
		KeyGenerator keyGen = null;
		SecretKey sekey = null;
		try
		{
			keyGen = KeyGenerator.getInstance(algorithm);   //���ݼ����㷨���KeyGenerator������Կ������
			keyGen.init(168);                               //��ʼ����Կ����
			sekey = keyGen.generateKey();                   //���� ��Կ
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			throw e;
		}

		return sekey;
	}

	/**
	 * ��ȡ�������е����ݣ����ֽ�������ʽ����
	 * 
	 * @param is   ������
	 * @return     ���ֽ�������ʽ�����������е�����
	 * @throws Exception
	 */
	private byte[] getByteFromStream(InputStream is) throws Exception
	{
		int length = -1;
		ArrayList temp = new ArrayList();

		while ((length = is.read()) != -1)
		{
			temp.add((byte) length);
		}

		byte[] out = new byte[temp.size()];
		for (int i = 0; i < temp.size(); i++)
		{
			Byte byt = (Byte) temp.get(i);
			out[i] = byt.byteValue();
		}
		return out;
	}
	
	/**
	 * �ر���
	 * @throws Exception
	 */
	private void closeStream() throws Exception
	{
		if (fis != null)
		{
			fis.close();
		}
		if (fos != null)
		{
			fos.close();
		}
	}

}
