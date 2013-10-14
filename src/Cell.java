
public class Cell {
	private boolean FWestWall;
	private boolean FSouthWall;
	
	public Cell(boolean AWestWall, boolean ASouthWall){
		FWestWall = AWestWall;
		FSouthWall = ASouthWall;
	}
	
	public boolean WestWall(){
		return FWestWall;
	}
	
	public boolean SouthWall(){
		return FSouthWall;
	}
	
	public static Cell[][] ExampleWalls(){
		Cell[][] result = new Cell[50][50];
		
		for (int i=0; i<result.length; i++){
			for (int j=0; j<result[i].length; j++){
				result[i][j] = new Cell(i==j, i*2==j);
			}
		}
		
		return result;
	}
}
