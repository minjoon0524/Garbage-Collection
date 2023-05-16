// 가비지 컬렉션을 테스트하기 위한 클래스 정의
class GCTest {
	// 객체가 생성될 때 메시지 출력
	public GCTest() {
		System.out.println("객체 생성");
	}

	// 객체가 소멸될 때 메시지 출력
	@Override
	protected void finalize() throws Throwable {
		System.out.println("객체 소멸");
		super.finalize();
	}

// 메인 메소드
	public static void main(String[] args) {
		// GCTest 객체 생성
		GCTest gc1 = new GCTest();
		GCTest gc2 = new GCTest();

		// gc1 참조 변수에 null 할당
		gc1 = null;

		// 가비지 컬렉션 실행 요청
		System.gc();

		// gc2 참조 변수에 null 할당
		gc2 = null;

		// 가비지 컬렉션 실행 요청
		System.gc();
	}
}