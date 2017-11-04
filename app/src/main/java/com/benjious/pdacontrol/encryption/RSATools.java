package com.benjious.pdacontrol.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;

import javax.crypto.Cipher;



/**
 * RSA�ǶԳƼ����㷨��ʵ���࣬ʵ���˶��ַ������ļ����������ļ��ܣ���Կ���ܣ������ܣ�˽Կ���ܣ�������ǩ����˽Կ���ܣ���ǩ����֤����Կ��֤������
 * �˼����㷨���������ݳ��Ȳ��ܴ���117λ��
 * @author wanna
 *
 */
public class RSATools implements AsymmetryEncrypt
{
	private final static String algorithm = "RSA";           //�ǶԳƼ����㷨RSA

	public static final String SIGNALGORITHM = "SHA1WithRSA"; // ����ǩ���㷨

	private KeyPair rsaKeyPair; 								//RSA�㷨����Կ��  

	private PublicKey pubKey; 									//��Կ 

	private PrivateKey priKey; 								// ˽Կ

	private FileInputStream fis;

	private FileOutputStream fos;

	public RSATools()
	{
	}

	/**
	 * ����
	 * @param cipherText ������Ϣ
	 * @param key        ���ܲ��������˽Կ�����Ϊnull������ִ�н��ܲ���
	 * @return           ���ܺ��������Ϣ
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

		this.priKey = (PrivateKey) key;                   //���ý������õ�˽Կ

		byte[] input = Base64.decode(cipherText);         //��Base64�����Ľ���

		return new String(this.basicDecrypt(input));
	}

	/**
	 * ����
	 * 
	 * @param cipherText ������Ϣ
	 * @param key        ���ܲ��������˽Կ�����Ϊnull������ִ�н��ܲ���
	 * @return           ���ܺ��������Ϣ  �ַ�����ʽ
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

		this.priKey = (PrivateKey) key;

		byte[] input = Base64.decode(cipherText);

		return new String(this.basicDecrypt(input));
	}
	
	/**
	 * ���� ���ļ����н��ܲ������ļ�����չ������Ϊ.fjm�����ܺ�������ļ�����·����������ͬ��
	 * ���� ���� ��F:\1.txt.fjm
	 *      ���ģ� F:\1.txt
	 * @param cipherFile   �����ܵ��ļ�
	 * @param key          �������õ�˽Կ
	 * @return             ���ļ���ʽ�洢��������Ϣ
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

		this.priKey = (PrivateKey) key;

		try
		{
			String strPath = cipherFile.getPath();
			if (!strPath.substring(strPath.length() - 4).toLowerCase().equals(".fjm"))   //�ж��ļ�����չ���Ƿ���fjm������ֹͣ���ܲ���
			{
				return null;
			}

			fis = new FileInputStream(cipherFile);

			byte[] input = this.getByteFromStream(fis);      //��ȡ�ļ����е���������

			byte[] output = this.basicDecrypt(input);        //����

			String outFilePath = strPath.substring(strPath.length() - 4);

			File fileOut = new File(outFilePath);             //�洢������Ϣ���ļ�   

			fos = new FileOutputStream(fileOut);

			for (int i = 0; i < output.length; i++)
			{
				fos.write((int) output[i]);
			}
			System.out.println("���ܳɹ�");
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
	 * ���� ���������е�������Ϣ���н���
	 * @param cipherText  �����������ݵ�������
	 * @param key          ���ܵ�˽Կ�����Ϊnull�����ܽ��н��ܲ�����
	 * @param plainPath    �洢���ܺ��������Ϣ���ļ�·��
	 * @return    ָ��·���£�����������Ϣ���ļ�
	 * @throws Exception
	 */
	public File decrypt(InputStream cipherText, Key key,String plainPath) throws Exception
	{
		if(cipherText==null||key==null)
		{
			return null;
		}
		if(plainPath==null||plainPath.equals(""))
		{
			return null;
		}
		this.priKey= (PrivateKey)key;
		byte[] input = this.getByteFromStream(cipherText);
		byte[] output = this.basicDecrypt(input);
		File outputFile = new File(plainPath);
		fos = new FileOutputStream(outputFile);
		for(int i=0;i<output.length;i++)
		{
			fos.write((int)output[i]);
		}
		System.out.println("���ܳɹ�");
		return outputFile;
	}

	/**
	 * ���� 
	 * @param plainText ����
	 * @param key       �������õĹ�Կ�����Ϊnull���Զ�����һ����Կ�ԣ�ʹ�����еĹ�Կ���м���
	 * @return          ���ܺ�����ģ��ַ�����ʽ
	 * @throws Exception
	 */
	public String encrypt(byte[] plainText, Key key) throws Exception
	{
		if (plainText == null || plainText.length == 0)
		{
			return null;
		}
		this.setKeyPairWhenEncrypt(key);

		return Base64.encode(this.basicEncrypt(plainText));
	}

	/**
	 * ���� 
	 * @param plainText  ����
	 * @param key        �������õĹ�Կ�����Ϊnull���Զ�����һ����Կ�ԣ�ʹ�����еĹ�Կ���м���
	 * @return           ���ܺ�����ģ��ַ�����ʽ
	 * @throws Exception
	 */
	public String encrypt(String plainText, Key key) throws Exception
	{
		if (plainText == null)
		{
			return null;
		}
		this.setKeyPairWhenEncrypt(key);

		byte[] input = plainText.getBytes();

		return Base64.encode(this.basicEncrypt(input));
	}

	/**
	 * �����ļ������ܺ���file·��������һ��չ��Ϊ���ļ������ڴ洢������Ϣ
	 * ���� ���ģ�      F:\1.txt
	 *      �����ļ�Ϊ��F:\1.txt.fjm
	 * @param file  �����ܵ��ļ�
	 * @param key   �������õĹ�Կ�����Ϊnull���Զ�����һ����Կ�ԣ�ʹ�����еĹ�Կ���м���
	 * @return      ��չ��Ϊ.fjm������������Ϣ���ļ�
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
			this.setKeyPairWhenEncrypt(key);

			fis = new FileInputStream(file);

			byte[] input = this.getByteFromStream(fis);

			byte[] output = this.basicEncrypt(input);

			String cipherFilePath = file.getPath() + ".fjm";       //�� file·���´���һ��չ��Ϊ.fjm���ļ�

			File fileOut = new File(cipherFilePath);

			fos = new FileOutputStream(fileOut);

			for (int i = 0; i < output.length; i++)
			{
				fos.write((int) output[i]);
			}
			System.out.println("���ܳɹ�");
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
	 * ����
	 * @param plainText  ����������Ϣ��������
	 * @param key        �������蹫ԿKey�����null�����Զ�����һ����Կ�ԣ������еĹ�Կ���м���
	 * @param cipherPath ���ܺ󣬴��������Ϣ���ļ�·��
	 * @return   �������ļ���Ϣ���ļ�
	 * @throws Exception
	 */
	public File encrypt(InputStream plainText, Key key,String cipherPath) throws Exception
	{
		if(plainText==null)
		{
			return null;
		}
		if(cipherPath==null||cipherPath.equals(""))
		{
			return null;
		}
		try
		{
		this.setKeyPairWhenEncrypt(key);
		
		byte[] input = this.getByteFromStream(plainText);
		byte[] output = this.basicEncrypt(input);
		
		File outputFile = new File(cipherPath);
		fos = new FileOutputStream(outputFile);
		for(int i = 0;i<output.length;i++)
		{
			fos.write((int)output[i]);
		}
		System.out.println("���ܳɹ�");
		return outputFile;
		}catch(Exception e)
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
	 * ����ǩ��
	 * @param plainText  ����
	 * @param key     ����ǩ������˽Կ�����keyΪnull,���Զ�Ϊ������һ����Կ�ԣ�����˽Կ����ǩ��
	 * @return  �ַ�����ʽ��ǩ����Ϣ
	 * @throws Exception
	 */
	public String digitalSignature(String plainText, Key key) throws Exception
	{
		try
		{
			if (plainText == null)
			{
				return null;
			}

			this.setKeyPairWhenSignature(key);

			byte[] input = plainText.getBytes();

			return Base64.encode(this.basicSignature(input));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * ��֤ǩ��
	 * @param plainText  �����ַ���
	 * @param signMessage ǩ����Ϣ
	 * @param key         ��֤ǩ������Ĺ�Կ��Ϊnull���ܽ�����֤
	 * @return     ��֤���
	 *               true ǩ����ȷ
	 *               falseǩ������
	 * @throws Exception
	 */
	public boolean validateSignature(String plainText, String signMessage, Key key) throws Exception
	{
		try
		{
			if (plainText == null || signMessage == null)
			{
				return false;
			}
			if (key == null)
			{
				return false;
			}
			else
			{
				this.pubKey = (PublicKey) key;
			}
			byte[] plain = plainText.getBytes();
			byte[] input = Base64.decode(signMessage);
			return this.basicValidateSign(plain, input);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * ���ļ���������ǩ��������ǩ����Ϣ���ļ���չ��Ϊ.sig����file����ͬĿ¼��
	 * ��:   file :			F:\1.txt
	 *       ǩ����Ϣ�ļ���  F:\1.txt.sig
	 * @param file ��ǩ�����ļ�
	 * @param key  ����ǩ�����蹫Կ������null�����Զ�����һ����Կ�ԣ�����˽Կ����ǩ������
	 * @return   ����ǩ����Ϣ���ļ�
	 * @throws Exception
	 */
	public File digitalSignature(File file, Key key) throws Exception
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

			this.setKeyPairWhenSignature(key);
			fis = new FileInputStream(file);

			byte[] input = this.getByteFromStream(fis);

			byte[] output = this.basicSignature(input);

			//System.out.println("����ǩ����Ϣ" + Base64.encode(output));

			String cipherFilePath = file.getPath() + ".sig";     //�洢ǩ����Ϣ���ļ�·��

			File fileOut = new File(cipherFilePath);

			fos = new FileOutputStream(fileOut);

			for (int i = 0; i < output.length; i++)
			{
				fos.write((int) output[i]);
			}
			System.out.println("����ǩ���ɹ�");
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
	 * ��֤ǩ����Ϣ����.sig����ǩ����Ϣ���ļ�������֤
	 * @param plainText ����
	 * @param signMessage ǩ����Ϣ�ļ�����չ��Ϊ.sig
	 * @param key   ��֤ǩ�����蹫Կkey
	 * @return      ��֤���
	 *               true ǩ����ȷ
	 *               falseǩ������
	 * @throws Exception
	 */
	public boolean validateSignature(File plainText, File signMessage, Key key) throws Exception
	{
		if (plainText == null)
		{
			return false;
		}
		if (!plainText.exists() || plainText.isDirectory())
		{
			return false;
		}
		if (key == null)
		{
			return false;
		}

		this.pubKey = (PublicKey) key;

		FileInputStream fis2 = null;
		try
		{
			String signPath = signMessage.getPath();
			if (!signPath.substring(signPath.length() - 4).toLowerCase().equals(".sig"))//�ж�ǩ����Ϣ�ļ���չ���Ƿ�Ϊ.sig
			{
				return false;
			}
			fis = new FileInputStream(plainText);

			byte[] plain = this.getByteFromStream(fis);

			fis2 = new FileInputStream(signMessage);

			byte[] input = this.getByteFromStream(fis2);

			return this.basicValidateSign(plain, input);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (fis2 != null)
			{
				fis.close();
			}
			this.closeStream();
		}
		return false;
	}
	
	/**
	 * ����ǩ�� 
	 * @param plainStream   ����������Ϣ��������
	 * @param key           ����ǩ�������˽Կ
	 * @param signPath      ���ǩ����Ϣ���ļ�·��
	 * @return   ����ǩ����Ϣ���ļ�
	 * @throws Exception
	 */
	public File digitalSignature(InputStream plainStream, Key key,String signPath) throws Exception
	{
		if (plainStream == null)
		{
			return null;
		}

		this.setKeyPairWhenSignature(key);

		try
		{
			byte[] input = this.getByteFromStream(plainStream);

			byte[] output = this.basicSignature(input);

			File outSignMessage = new File(signPath);  // ����ǩ����Ϣ���ļ�
			
			fos = new FileOutputStream(outSignMessage);
			for (int i = 0; i < output.length; i++)
			{
				fos.write((int) output[i]);
			}
			
			System.out.println("ǩ���ɹ�");
			return outSignMessage;
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
	 * ��֤ǩ����Ϣ
	 * @param plainStream ����������Ϣ��������
	 * @param signStream  ����ǩ����Ϣ��������
	 * @param key         ��֤ǩ������Ĺ�Կkey
	 * @return            ��֤���
	 *                    true ǩ����ȷ
	 *                    falseǩ������
	 * @throws Exception
	 */
	public boolean validateSignature(InputStream plainStream, InputStream signStream, Key key) throws Exception
	{
		if (plainStream == null || signStream == null || key == null)
		{
			return false;
		}
		this.pubKey = (PublicKey) key;

		byte[] plain = this.getByteFromStream(plainStream);
		byte[] input = this.getByteFromStream(signStream);

		System.out.println("��֤����");
		return this.basicValidateSign(plain, input);
	}

	/**
	 * ������Կ��
	 */
	public KeyPair getKeyPair() throws Exception
	{
		return this.rsaKeyPair;
	}

	/**
	 * ����˽Կ
	 */
	public Key getPrivateKey() throws Exception
	{
		return this.priKey;
	}
	
	/**
	 * ���ع�Կ
	 */
	public Key getPublicKey() throws Exception
	{
		return this.pubKey;
	}

	/**
	 * ����RSA��Կ��
	 * @return ��Կ��KeyPair
	 * @throws Exception
	 */
	private KeyPair generateKeyPair() throws Exception
	{
		KeyPair kPair = null;
		try
		{
			// ���ݼ����㷨���KeyPairGenerator���� 
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
			// ������Կ����
			keyGen.initialize(1024);
			kPair = keyGen.generateKeyPair();

		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			throw e;
		}
		return kPair;
	}

	/**
	 * ���ܲ���ʱ�����ü��ܹ�Կ
	 * ���key Ϊnull�����Զ�����һ����Կ�ԣ������еĹ�Կ���м���
	 * �����Ϊnull������Ϊ��Կ���м��ܲ���
	 * @param key   ��Կ
	 * @return     �������蹫Կ�Ƿ����óɹ�
	 * @throws Exception
	 */
	private Boolean setKeyPairWhenEncrypt(Key key) throws Exception
	{

		try
		{
			if (key == null)
			{
				this.rsaKeyPair = this.generateKeyPair();
				this.pubKey = this.rsaKeyPair.getPublic();
				this.priKey = this.rsaKeyPair.getPrivate();
			}
			else
			{
				this.pubKey = (PublicKey) key;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return true;
	}
	
	/**
	 * ����ǩ��ʱ������ǩ������������Կ
	 * �����Կkey��Ϊ�գ�����Ϊ˽Կ�洢
	 * ���Ϊnull,���Զ�����һ����Կ�ԣ������е�˽Կ����ǩ������
	 * @param key  ��Կ
	 * @return  ˽Կ�Ƿ����óɹ�
	 * @throws Exception
	 */
	private Boolean setKeyPairWhenSignature(Key key) throws Exception
	{

		try
		{
			if (key == null)
			{
				this.rsaKeyPair = this.generateKeyPair();
				this.pubKey = this.rsaKeyPair.getPublic();
				this.priKey = this.rsaKeyPair.getPrivate();
			}
			else
			{
				this.priKey = (PrivateKey) key;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return true;
	}
	
	/**
	 * ʵ�ֻ����ļ��ܹ��ܣ���������������
	 * @param input Ҫ���м��ܵ��ֽ�����
	 * @return      ���ܴ������������ݣ��ֽ�������ʽ�� 
	 * @throws Exception
	 */
	private byte[] basicEncrypt(byte[] input) throws Exception
	{
		Cipher cipher = null;
		try
		{
			cipher = Cipher.getInstance(algorithm);

			cipher.init(Cipher.ENCRYPT_MODE, this.pubKey); // �ù�ԿpubKey��ʼ����Cipher

			return cipher.doFinal(input);                 //����
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

	}
	
	/**
	 * �����Ľ��ܷ�������������������
	 * @param input   Ҫ���н��ܵ��ֽ�����
	 * @return   ���ܴ������������ݣ��ֽ�������ʽ��
	 * @throws Exception
	 */
	private byte[] basicDecrypt(byte[] input) throws Exception
	{
		Cipher cipher = null;
		try
		{
			cipher = Cipher.getInstance(algorithm);

			cipher.init(Cipher.DECRYPT_MODE, this.priKey); // ��˽ԿpriKey��ʼ����cipher

			return cipher.doFinal(input);                  //����
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * ʵ�ֻ���������ǩ�����ܣ���������������
	 * @param input
	 *           Ҫ����ǩ�����ֽ�����
	 * @return  �ֽ�������ʽ��ǩ����Ϣ
	 * @throws Exception
	 */
	private byte[] basicSignature(byte[] input) throws Exception
	{
		try
		{
			Signature sig = Signature.getInstance(SIGNALGORITHM);

			sig.initSign(this.priKey);  //�ô�˽Կ��ʼ����ǩ������Signature

			sig.update(input);          

			return sig.sign();          //ǩ��
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * ʵ�ֻ�����ǩ����֤����,��������������
	 * @param plain
	 *            ���ĵ��ֽ�����
	 * @param input
	 *            ǩ����Ϣ
	 * @return   ��֤���
	 * 			true ǩ����ȷ  falseǩ������
	 * @throws Exception
	 */
	private boolean basicValidateSign(byte[] plain, byte[] input) throws Exception
	{
		try
		{
			Signature sig = Signature.getInstance(SIGNALGORITHM);

			sig.initVerify(this.pubKey);  // �ù�ԿpubKey��ʼ��������Signature����

			sig.update(plain);           

			return sig.verify(input);     //ǩ����֤ 
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
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
		if(fis!=null)
		{
			fis.close();
		}
		if(fos!=null)
		{
			fos.close();
		}
	}
	
}
