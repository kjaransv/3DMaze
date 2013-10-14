
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
		Cell[][] result = new Cell[10][5];
		
		for (int i=0; i<result.length; i++){
			for (int j=0; j<result[i].length; j++){
				//result[i][j] = new Cell(i==j, i*2==j);
				//result[i][j] = new Cell((int)(Math.random()*2)==1, (int)(Math.random()*2)==1);
				
				// set outer edges
				result[i][j] = new Cell(j==0 || j==result[i].length-1, i==0 || i==result.length-1);
			}
		}
		
		return result;
	}
}
