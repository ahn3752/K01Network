package chat2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MultiClient {
	public static void main(String[] args) {
		
		System.out.println("이름을 입력하세요:");
		Scanner scanner = new Scanner(System.in);
		String s_name = scanner.nextLine();
		
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			//클라이언트 실행시 별도의 접속IP가 없으면 localhost로 고정된다.
			String ServerIP = "localhost";
			//실행시 매개변수로 IP주소를 전달한다면 해당 주소로 설정한다.
			if(args.length>0) {
				ServerIP = args[0];
			}
			//IP주소와 포트를 기반으로 Socket객체를 생성하여 서버에 접속요청을 한다.
			Socket socket = new Socket(ServerIP, 9999);
			System.out.println("서버와 연결되었습니다..");
			
			/*
			 InputStreamReader / OutputStreamReader는 바이트 스트림과
			 문자 스트림의 상호변환을 제공하는 입출력 스트림이다.
			 바이트를 읽어서 지정된 문자인코딩에 따라 문자로 변환하는데 사용된다.
			 */
			//서버-> 클라이언트 측으로 메세지를 전송하기 위한 스트림 생성.
			out = new PrintWriter(socket.getOutputStream(), true);
			//클라이언트로 부터 메세지를 받기위한 스트림 생성
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//서버로 입력된 이름을 출력 스트림을 통해 전송한다.
			out.println(s_name);//접속자의 "대화명"을 서버측으로 전송한다.
			
			/*
			 소켓이 close되기전이라면 클라이언트는 지속적으로 서버로 메세지를 보낼 수 있다.
			 */
			
			while(out!=null) {
				try {
					//서버가 echo해준 내용을 라인단위로 읽어와서 콘솔에 출력
					if(in!=null) {
						System.out.println("Receive : "+in.readLine());
					}
					
					//클라이언트는 내용을 입력한 후 서버로 전송한다.
					String s2 = scanner.nextLine();
					if(s2.equals("q") || s2.equals("Q")) {
						//만약 입력값이 q(Q)라면 while루프를 탈출한다.
						break;
					}
					else {
						//q가 아니라면 서버로 입력한 내용을 전송한다.
						out.println(s2);
					}
				}
				catch(Exception e) {
					System.out.println("예외 :"+e);
				}
			}
			//클라이언트가 q를 입력하면 소켓과 스트림을 모두 자원해제한다.
			in.close();
			out.close();
			socket.close();
		}
		catch (Exception e) {
			System.out.println("예외발생[MultiClient]"+e);
		}
	}
}
