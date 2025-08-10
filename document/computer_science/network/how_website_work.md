# How Website Works

## When We Enter google.com

브라우저를 통해, google.com 도메인으로 들어가게 될 경우, 해당 IP 주소를 알아야한다. 

### 요청할 서버의 IP 를 얻는 과정
DNS 는 도메인 명을 IP 주소로 변환시켜주는 시스템이다. 

클라이언트는 먼저 자신의 캐시에 (브라우저, OS) 질의하려는 도메인에 해당하는 IP 에 대한 정보가 있는지 먼저 찾게된다.  
정보가 없다면 Local DNS Sever 에게 도메인에 해당하는 IP 주소를 질의한다.

1. Local DNS 서버는 Root DNS 서버에게 .com 도메인을 관리하는 DNS 서버가 어디있는지 질의한다.
2. 해당 서버를 TLD(Top-Level Domain) 서버라고 한다. 
3. TLD 서버에게 google.com 도메인을 관리하는 서버가 어디있는지 질의한다.
4. google.com을 관리하는 권한 있는 네임 서버(Authoritative Name Server)의 주소를 반환한다.
5. 로컬 DNS 서버는 권한 있는 네임 서버에 " www.google.com의 IP 주소는 뭐야?"라고 질의하여 최종적으로 IP 주소를 얻게 된다.

### 요청할 서버와 통신 하는 과정

1. http2 프로토콜을 이용할 경우 
- 3way handshake 를 통해 클라이언트 - 서버간 연결을 생성한다. (syn - ack & syn - ack)
- 연결된 커넥션을 통해 데이터를 주고 받는다. (HTTP 규약에 맞게 통신한다)
- 멀티플렉싱을 통해 서버와 클라이언트는 하나의 커넥션으로 여러 스트림을 생성할 수 있고, 병렬처리를 통해 효율적으로 통신한다.
- 4way handshake 를 통해 클라이언트 - 서버간 연결을 제거한다. (fin - ack - fin - ack)

### 브라우저 렌더링 

- 응답받은 HTML, CSS 파일을 읽어 브라우저가 화면을 구성하여 사용자에게 보여준다. 
