package structures;

public class AuxGenerator {

	private String name;
	private int counter;
	
	
	public AuxGenerator() {
		this.name = "Aux";
		this.counter = 1;
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
	
	public void setCounter() {
		this.counter++;
	}

	public void initCounter() {
		this.counter=1;
		// TODO Auto-generated method stub
		
	}





}
