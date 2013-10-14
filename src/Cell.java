
public class Cell {
	private boolean FWestWall;
	private boolean FSouthWall;
	
	public int FX, FY;
	
	public Cell(boolean AWestWall, boolean ASouthWall, int AX, int AY){
		FWestWall = AWestWall;
		FSouthWall = ASouthWall;
		
		FX = AX;
		FY = AY;
	}
	
	public boolean WestWall(){
		return FWestWall;
	}
	
	public boolean SouthWall(){
		return FSouthWall;
	}
	
	public static Cell[][] ExampleWalls(){
		Cell[][] result = new Cell[1000][500];
		
		for (int i=0; i<result.length; i++){
			for (int j=0; j<result[i].length; j++){
				boolean w = j==0 || j==result[i].length-1;
				boolean s = i==0 || i==result.length-1;
				//result[i][j] = new Cell(i==j, i*2==j);
				//result[i][j] = new Cell((int)(Math.random()*2)==1, (int)(Math.random()*2)==1);
				
				// set outer edges
				//w = false;
				//s = false;
				result[i][j] = new Cell(w, s, i, j);
				//result[i][j] = new Cell(w || (int)(Math.random()*2)==1, s || (int)(Math.random()*2)==1);
			}
		}
		
		result[5][2].FSouthWall = true;
		result[5][2].FWestWall = true;
		result[4][2].FWestWall = true;
		
		return result;
	}
}
