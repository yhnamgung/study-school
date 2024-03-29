import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class F_CopyList {

	public static void main(String[] args) {
		List<String> src = Arrays.asList("Box","Apple","Toy","Robot");
		//복사본을 만들어서 사용)
		List<String> dest = new ArrayList<>(src);
		
		//정렬하여 결과 출력
		Collections.sort(dest);
		System.out.println(dest);
		
		//src를 dest로 복사
		Collections.copy(dest, src);
		System.out.println(dest);

	}

}
