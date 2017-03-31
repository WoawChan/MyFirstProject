package com.my.maven.utils;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
/**
 * �ʼ�������
 * @author WosawChan
 * 2016-12-25
 */
public class MailUtils {
	/**
	 * ������
	 */
	private static String fromAddr = "15917395354@163.com";
	/**
	 * ��������Ȩ��
	 */
	private static String pwd = "wyyxsqm2009";
	/**
	 * ����Э��
	 */
	private static String protocol = "smtp";
	/**
	 * ������
	 */
	private static String host = "smtp.163.com";
	/**
	 * �����˿�
	 */
	private static String port = "25";
	/**
	 * �Ƿ���Ҫ�����֤
	 */
	private static String auth = "true";
	/**
	 * �Ƿ���debug����
	 */
	private static String debug = "true";
	/**
	 * �����ʼ����������ӻỰ
	 * @return session
	 */
	private static Session createSession(){
		//���û�����Ϣ
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", protocol);
		properties.setProperty("mail.smtp.host", host); 
		properties.setProperty("mail.smtp.port", port); 
		properties.setProperty("mail.smtp.auth", auth);	
		properties.setProperty("mail.debug", debug); 
		//�����Ự
		Session session = Session.getInstance(properties);
		return session;
	}
	
	/**
	 * �����ʼ��������Ϣ
	 * @throws Exception 
	 * @return message
	 */
	private static Message createMessage() throws Exception{
		//�����ʼ��Ự
		Session session = createSession(); 
		//�����ʼ�����
		Message message = new MimeMessage(session); 
		
		//������
		message.setFrom(new InternetAddress(fromAddr));
		
		//����ռ���
		String toAddr = "977827013@qq.com,1425917228@qq.com";
		String[] addrArray = toAddr.split(",");
		int size = addrArray.length;
		Address[] addr = null;
		if(size >0 ){
			addr = new InternetAddress[size];
			for(int i=0;i<size;i++){
				addr[i] = new InternetAddress(addrArray[i]);
			}
		} 
		message.setRecipients(Message.RecipientType.TO, addr); 
		
		//����
		String subject = "�ҵ�һ������ʼ�";
		message.setSubject(subject);
		
		//��Ϣ���ݶ������Ҫ���͵�bodyPart
		Multipart mp = new MimeMultipart();
		
		//��������
		BodyPart contentPart = new MimeBodyPart();
		contentPart.setContent("Hello World!!!!", "text/html;charset=utf-8");
		mp.addBodyPart(contentPart);
		
		//�������
		String filePath = "C:/work/test/������.xls,C:/work/test/mailTest.xls"; //�ļ���ַ
		String[] pathArray = filePath.split(",");
		int fileSize = pathArray.length;
		for(int i=0;i<fileSize;i++){
			BodyPart filePart = new MimeBodyPart();
			DataSource dataSource = new FileDataSource(pathArray[i]);
			filePart.setDataHandler(new DataHandler(dataSource));
			filePart.setFileName(MimeUtility.encodeText(dataSource.getName(),"utf-8",null)); //�������������������
			mp.addBodyPart(filePart);
		}
		
		message.setContent(mp); //���Ҫ���͵���Ϣ����
		message.saveChanges(); //���沢�������յ��ʼ�����
		
		return message;
	}
	
	/**
	 * �����ʼ�
	 * @param session
	 * @param message
	 */
	private static void sendMail(Session session, Message message){
		Transport transport = null;
		try {
			transport = session.getTransport();
			transport.connect(fromAddr,pwd); //���ӷ�������
			transport.sendMessage(message, message.getAllRecipients()); //�����ʼ�
			System.out.println("**********�ʼ����ͳɹ�**************");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�ļ�����ʧ�ܣ�"+e);
		} finally {
			try {
				if(transport != null){
					transport.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * �������
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Session session = createSession();
			Message message = createMessage();
			sendMail(session, message);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�ļ�����ʧ�ܣ�"+e);
		}
	}
}
