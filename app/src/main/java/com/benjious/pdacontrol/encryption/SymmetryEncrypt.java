package com.benjious.pdacontrol.encryption;

import java.io.File;
import java.io.InputStream;
import java.security.Key;


/**
 * �ԳƼ����㷨�Ĺ��ýӿڣ�ʵ�ֶԳƼ����㷨�ļӽ��ܹ���
 * @author Wanna
 *
 */
public interface SymmetryEncrypt extends Encryption
{
	/**
	 * ���ļ����м��ܴ���
	 * @param file  Ҫ���ܵ��ļ�
	 * @param key   ���ܲ�������Կ
	 * @return     ����������Ϣ���ļ� ��file·������չ��Ϊ.djm���ļ�
	 * 				��file    F:\1.txt
	 *               ����     F:\1.txt.djm
	 * @throws Exception
	 */
	public File encrypt(File file, Key key) throws Exception;
	
	/**
	 * ���������е����ݽ��м���
	 * @param plainText ���м������ݵ�������
	 * @param key       ������Կ
	 * @param cipherPath ���ܺ�ģ����������Ϣ���ļ�����·��
	 * @return  ָ��·���µģ�����������Ϣ���ļ�
	 * @throws Exception
	 */
	public File encrypt(InputStream plainText,Key key,String cipherPath) throws Exception;
	
	
	/**
	 * ����
	 * @param cipherText   ����
	 * @param key         ������Կ
	 * @return     ���ַ�����ʽ����������Ϣ
	 * @throws Exception
	 */
	public String decrypt(byte[] cipherText, Key key) throws Exception;
	
	/**
	 * ����
	 * @param cipherText  ������Ϣ
	 * @param key         ������Կ
	 * @return        ���ַ�����ʽ����������Ϣ
	 * @throws Exception
	 */
	public String decrypt(String cipherText, Key key) throws Exception;
	
	/**
	 * ���ܣ�����չ��Ϊ.djm�������ļ����н��ܲ���
	 * @param cipherFile  ����
	 * @param key         ������Կ
	 * @return     ����������Ϣ���ļ�
	 * ����  �����ļ�   F:\1.txt.djm
	 *       ���أ�     F:\1.txt 
	 * @throws Exception
	 */
	public File decrypt(File cipherFile, Key key) throws Exception;

	/**
	 * ����
	 * @param cipherText  �������ļ���Ϣ��������
	 * @param key         ������Կ
	 * @param plainPath   ���ܺ󣬽�������Ϣ����ڴ�·����
	 * @return    ����������Ϣ���ļ�  
	 * @throws Exception
	 */
	public File decrypt(InputStream cipherText, Key key,String plainPath) throws Exception;

	/**
	 * ��ü���/���ܲ�����ʹ�õ���ԿKey
	 * @return  ��/������ԿKey
	 * @throws Exception
	 */
	public Key getKey() throws Exception;
}
