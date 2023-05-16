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