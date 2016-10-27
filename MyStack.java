import java.util.LinkedList;

public class MyStack<Anytype> {

	private LinkedList<Anytype> list;
	
	public MyStack(){
		list = new LinkedList<Anytype>();
	}

	public void push(Anytype obj){
		list.addFirst(obj);
	}
	
	public Anytype pop(){
		return list.remove();
	}

	public boolean isEmpty(){
		return list.isEmpty();
	}
}

