package structures;

public class AuxGenerator {

	private String name;
	private int counter;
	
	
	public AuxGenerator(String name, int counter) {
		this.name = name;
		this.counter = counter;
	}
	
	public String getName() {
		int counter=this.counter;
		this.counter++;
		System.out.println("====AUXGENERATOR===="+name+counter);
		return name+counter;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}



}
