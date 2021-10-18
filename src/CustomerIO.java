import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerIO {
	public final static File USER_FILE = new File("userData.txt");

	// save
	public static void save(List<Customer> customers) {

//		List<Customer> list = new ArrayList<>();  //main에 있어

		// ObjectOutputStream : 객체를 출력하는거
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
			oos.writeObject(customers);
			oos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// load
	// load()안에는 받는얘, load()앞에는 주는얘타입(int,string,void(빈거) 등등_return필요):리턴해서 앞에타입으로 준다
	public static List<Customer> load() {
		List<Customer> a = null; // 저장한 리스트랑 다른거임,****저장한걸 들고와서 여기에 다시 저장하는거야
//		List<Integer> read = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_FILE))) {
			// ****a = (타임)으로 저장한걸읽은것(ois.readObject())
			a = (List<Customer>) ois.readObject(); // (List<Customer>) 타입!!
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return a; // a(List<Customer> : 타입)로 리턴
	}

}

//Student student = new Student("asdf", 10, 3.3);
//Student student1 = new Student("asdf1", 10, 3.3);
//Student student2 = new Student("asdf2", 10, 3.3);
//List<Student> list = new ArrayList<Student>();
//list.add(student);
//list.add(student1);
//list.add(student2);
//
//try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("my-obj.ser"))) {
//	oos.writeObject(list);
//	oos.flush();
//} catch (FileNotFoundException e) {
//	e.printStackTrace();
//} catch (IOException e) {
//	e.printStackTrace();
//}
//
//List<Student> read = null;
//try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("my-obj.ser"))) {
//	read = (List<Student>) ois.readObject();
//} catch (FileNotFoundException e) {
//	e.printStackTrace();
//} catch (IOException e) {
//	e.printStackTrace();
//} catch (ClassNotFoundException e) {
//	e.printStackTrace();
//}
//for (Student z : read) {
//	System.out.println(z.getName());
//}
//System.out.println(read);