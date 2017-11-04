package com.benjious.pdacontrol.encryption;

import java.security.Key;

/**
 * ������������ϲ�ӿڣ��ṩ����������ļ��ܷ���
 * @author wanna
 *
 */
public interface Encryption
{
	/**
	 * ����
	 * @param plainText ���ģ��ֽ�������ʽ
	 * @param key       ���ܲ����������Կ
	 * @return          ���ܺ�Ľ��
	 * @throws Exception
	 */
	public String encrypt(byte[] plainText,Key key) throws Exception;
	
	/**
	 * ����
	 * @param plainText ���ģ��ַ�����ʽ
	 * @param key       ����������Կ
	 * @return          ���ܽ�������ģ�
	 * @throws Exception
	 */
	public String encrypt(String plainText, Key key) throws Exception;
	
}
