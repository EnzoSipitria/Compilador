package structures;

public class TercetoLabel extends Terceto {

	
	public TercetoLabel(Object first, Object Position) {
		// TODO Auto-generated constructor stub
		this.operator = "label";
		this.first = first;
		this.position = (Integer) Position;//-1 para la posicion xq viene con un +2
	}
	
	
	@Override
	public String getAssembler() {
		
		return "labelGENERADO"+this.first+": ";
	}

	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return null;
	}

}
