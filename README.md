
# 가비지 컬렉션 (Garbage Collection)

### 목차

- GC란?
- GC 필요성
- GC 매커니즘
- GC 동작 코드
- GC 메모리 leak 발생

---

## GC란?

![1](https://github.com/minjoon0524/Garbage-Collection/assets/118280086/9dbc3150-234a-4434-a128-5043661cdc87)

출처 - [https://coding-factory.tistory.com/829](https://coding-factory.tistory.com/829)

- GC는 Garbage Collection의 약자로, 프로그래밍 언어에서 사용되는 메모리 자동 관리 기법입니다. 앞으로 사용되지 않는 객체의 메모리를 Garbage라고 합니다.
- 프로그램이 더 이상 필요하지 않은 메모리를 자동으로 회수하는 기능을 말합니다. 즉, Garbage를 정해진 스케줄에 의해 정리해주는 것 입니다.
- Stop the World
    - GC를 수행하기 위해 JVM이 멈추는 현상을 의미합니다.
    - GC가 작동하는 동안 GC관련 Thread를 제외한 모든 Thread는 멈춥니다.
    - 일반적으로 튜닝이라는 것은 이 시간을 최소화 하는 것을 의미합니다.

![2](https://github.com/minjoon0524/Garbage-Collection/assets/118280086/bb436ed3-d477-4890-93cf-0810f545e2eb)

### **Young 영역(Young Generation)**

- 새롭게 생성된 객체가 할당(Allocation)되는 영역
- 대부분의 객체가 금방 Unreachable 상태가 되기 때문에, 많은 객체가 Young 영역에 생성되었다가 사라집니다.
- Young 영역에 대한 가비지 컬렉션(Garbage Collection)을 Minor GC라고 부릅니다.

### **Old 영역(Old Generation)**

- Young영역에서 Reachable 상태를 유지하여 살아남은 객체가 복사되는 영역
- Young 영역보다 크게 할당되며, 영역의 크기가 큰 만큼 가비지는 적게 발생합니다.
- Old 영역에 대한 가비지 컬렉션(Garbage Collection)을 Major GC 또는 Full GC라고 부릅니다.
    
    ![3](https://github.com/minjoon0524/Garbage-Collection/assets/118280086/dacea0ce-fad4-43ce-9695-d8784b9e70ef)
    

### **Eden**

- new를 통해 새로 생성된 객체가 위치.
- 정기적인 쓰레기 수집 후 살아남은 객체들은 Survivor 영역으로 보냅니다.

### **Survivor 0 / Survivor 1**

- 최소 1번의 GC 이상 살아남은 객체가 존재하는 영역
- Survivor 영역에는 특별한 규칙이 있습니다. Survivor 0 또는 Survivor 1 둘 중 하나에는 꼭 비어 있어야 하는 것입니다.

출처 -  [https://inpa.tistory.com/entry/JAVA-☕-가비지-컬렉션GC-동작-원리-알고리즘-💯-총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%80%EB%B9%84%EC%A7%80-%EC%BB%AC%EB%A0%89%EC%85%98GC-%EB%8F%99%EC%9E%91-%EC%9B%90%EB%A6%AC-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC)

---

## GC의 필요성

- GC의 필요성은 메모리 관리의 번거로움과 오류 가능성을 해결하기 위해서입니다.
- 수동으로 메모리를 관리하는 것은 실수를 유발할 수 있고 시간이 많이 소요됩니다.
- 메모리를 제대로 해제하지 않으면 메모리 누수와 비효율적인 메모리 사용을 초래할 수 있습니다.
- GC는 이러한 문제를 해결하여 프로그래머가 명시적으로 메모리 관리를 하지 않아도 되도록 도와줍니다.

---

### GC의 작동 메커니즘

![4](https://github.com/minjoon0524/Garbage-Collection/assets/118280086/8adac1b7-9b84-404f-8521-a7381edefb25)

- 선언한 값들은 Stack에 쌓이고 객체는 Heap영역에 참조됩니다.

![5](https://github.com/minjoon0524/Garbage-Collection/assets/118280086/400cdfec-8bfc-448e-9fdb-98219f6b9da9)

- 실행이 끝나고 Heap영역에 객체가 남게되는데 이 객체가 GC의 대상이 됩니다.

```java
public class Test{
	public static void main(String[] args){
		int num1=10;
		int num2=5;
		int sum =num1 + num2;
		String name="던";
		
		System.out.println(sum);
		System.out.println(name);
	}
}
```

### GC과정

1. GC가 Stack의 모든 변수를 스캔하면서 각각 객체를 참조하고 있는지 찾아서 마킹합니다. 
2. Reachacble Object가 참조하고 있는 객체도 찾아서 마킹합니다. 
3. 마킹되지 않은 객체를 Heap에서 제거합니다. 

출처 - [https://www.youtube.com/watch?v=vZRmCbl871I](https://www.youtube.com/watch?v=vZRmCbl871I)

![6](https://github.com/minjoon0524/Garbage-Collection/assets/118280086/56822049-b2ae-407c-ae27-27fc625cbf97)

- GC 메커니즘은 몇 단계로 구성됩니다:
    - **마킹(Marking)**: 루트 객체(전역 변수나 스택에 있는 객체 등)로부터의 참조를 따라가면서 아직 사용 중인 객체를 식별하고 표시합니다.
    - **스윕(Sweep):** 전체 메모리 힙을 스캔하면서 표시되지 않은 객체는 가비지로 간주되고 해당 메모리가 해제됩니다.
    - **압축(선택적인 단계)**: 메모리 조각화를 최소화하기 위해 객체를 서로 가까이 옮기며 더 큰 연속된 빈 메모리 블록을 만들 수 있습니다.
    
    ---
    

## GC 동작코드

```java
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
```

```java
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
```

---

### GC 메모리 leak 발생

- GC 메모리 누수는 일반적으로 사용 중인 객체가 아닌데도 메모리가 계속 차지되는 경우입니다.
- 이는 객체가 더 이상 필요하지 않음에도 참조가 남아있어 GC가 해당 메모리를 회수하지 못하는 상황에서 발생할 수 있습니다.
- 이러한 상황에서는 프로그램이 메모리를 비효율적으로 사용하게 되고, 장기적으로는 메모리 부족으로 이어질 수 있습니다.

```java
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class GC_Leak {
    private List<WeakReference<String>> list = new ArrayList<>();
    
    public void addToMyList(String str) {
        list.add(new WeakReference<>(str));
    }
    
    public static void main(String[] args) {
        GC_Leak gc = new GC_Leak();
        for (int i = 0; i < 1000; i++) {
            gc.addToMyList("string" + i);
        }
    }
}
//list 필드를 List<WeakReference<String>> 타입으로 변경하고, 
//addToMyList 메서드에서 문자열을 WeakReference로 감싸서 list에 추가합니다.
// 이렇게 하여String 객체는 더 이상 강한 참조를 가지지 않게 되므로, 가비지 컬렉터가 필요 없어진 객체를 수거할 수 있습니다
```

※ 유튜브 강의 및 블로그 검색 ChatGPT를 통해 GC에 대해서 이해했습니다.
