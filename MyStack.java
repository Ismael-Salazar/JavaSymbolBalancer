import java.util.LinkedList;

//Has the expected features of a stack class.
public class MyStack<Anytype> {

	private LinkedList<Anytype> list;
	
	public MyStack(){
		list = new LinkedList<Anytype>();
	}

	//Push.
	public void push(Anytype obj){
		list.addFirst(obj);
	}
	
	//Pop.
	public Anytype pop(){
		return list.remove();
	}

	//Returns whether the stack is empty.
	public boolean isEmpty(){
		return list.isEmpty();
	}
}

