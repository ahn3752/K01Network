package chat5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MultiClient {
	
public static void main(String[] args) {
	
	//클라이언트의 접속자명을 입력한다.
	System.out.println("이름을 입력하세요:");
	Scanner scanner = new Scanner(System.in);
	String s_name = scanner.nextLine();
	
	//PrintWriter out = null;
	//서버의 메세지를 읽어오는 기능을 Receiver클래스로 옮김
	//BufferedReader in = null;
	
	try {
		String ServerIP = "localhost";
		//실행시 매개변수로 IP주소를 전달한다면 해당 주소로 설정한다.
		if(args.length>0) {
			ServerIP = args[0];
		}
		//IP주소와 포트를 기반으로 Socket객체를 생성하여 서버에 접속요청을 한다.
		Socket socket = new Socket(ServerIP, 9999);
		System.out.println("서버와 연결되었습니다..");
		
		//서버에서 보내는 메세지를 읽어올 Receiver쓰레드 객체생성 및 시작
		Thread receiver = new Receiver(socket);
		//setDaemon(true) 선언이 없으므로 독립쓰레드로 생성된다.
		receiver.start();
		//서버-> 클라이언트 측으로 메세지를 전송하기 위한 스트림 생성.
		Thread sender = new Sender(socket, s_name);
		//setDaemon(true) 선언이 없으므로 독립쓰레드로 생성된다.
		sender.start();
		
		}
	catch (Exception e) {
		System.out.println("예외발생[MultiClient]"+e);
	}
	}
}
