public class Gc {
	private Object referencedObject;

	public void setReferencedObject(Object obj) {
		this.referencedObject = obj;
	}

	public static void main(String[] args) {
		Gc n1 = new Gc();
		Gc n2 = new Gc();
		Gc n3 = new Gc();

		// n1은 n2를 참조하고 있음
		n1.setReferencedObject(n2);

		// n2는 n3를 참조하고 있음
		n2.setReferencedObject(n3);

		// n3은 아무 객체도 참조하지 않음

		// 가비지 컬렉션을 수행하여 사용되지 않는 객체를 제거
		// 일반적으로는 JVM이 자동으로 가비지 컬렉션을 수행하며, 개발자가
		// 직접 호출 할 필요없습니다.
		// 명시적으로 System.gc() 호출
		System.gc();
	}
}