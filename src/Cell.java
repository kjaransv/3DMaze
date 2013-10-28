import java.util.*;

public class Cell {
	private boolean FWall_X, FWall_Y, FWall_Z;	
	public int FX, FY, FZ;
	public boolean FVisited;
	
	public Cell(int AX, int AY, int AZ){
		FWall_X = true;
		FWall_Y = true;
		FWall_Z = true;
		
		FX = AX;
		FY = AY;
		FZ = AZ;
	}
	
	public boolean Wall_X(){
		return FWall_X;
	}
	
	public boolean Wall_Y(){
		return FWall_Y;
	}
	
	public boolean Wall_Z(){
		return FWall_Z;
	}
	
	public boolean EatMe(Cell[][][] ACells){
		if (FVisited) return true;
		
		int count = 0;
		
		// 6 directions
		// either wall between or out of bounds or the cell is visited
		
		// the border check not needed? borders always have their walls!
		if (Wall_X() || FX==ACells.length-1 || ACells[FX+1][FY][FZ].FVisited) count++;
		if (Wall_Y() || FY==ACells[0].length-1 || ACells[FX][FY+1][FZ].FVisited) count++;
		if (Wall_Z() || FZ==ACells[0][0].length-1 || ACells[FX][FY][FZ+1].FVisited) count++;
		
		if (FX==0 || ACells[FX-1][FY][FZ].Wall_X() || ACells[FX-1][FY][FZ].FVisited) count++;
		if (FY==0 || ACells[FX][FY-1][FZ].Wall_Y() || ACells[FX][FY-1][FZ].FVisited) count++;
		if (FZ==0 || ACells[FX][FY][FZ-1].Wall_Z() || ACells[FX][FY][FZ-1].FVisited) count++;
		
		// if at least 5 are valid then mark as visited
		return count>4;
	}
	
	private static void ReZone(int[][][] AZone, int AOld, int ANew, int x, int y, int z){
		// boundaries
		if (
				x>=0 && x<AZone.length && 
				y>=0 && y<AZone[0].length && 
				z>=0 && z<AZone[0][0].length){
			// zone
			if (AZone[x][y][z] == AOld){
				AZone[x][y][z] = ANew;
				
				ReZone(AZone, AOld, ANew, x+1, y, z);
				ReZone(AZone, AOld, ANew, x-1, y, z);
				ReZone(AZone, AOld, ANew, x, y+1, z);
				ReZone(AZone, AOld, ANew, x, y-1, z);
				ReZone(AZone, AOld, ANew, x, y, z+1);
				ReZone(AZone, AOld, ANew, x, y, z-1);
			}
		}
	}
	
	public static boolean combine(int[][][] AZone, Cell A1, Cell A2){
		boolean result = AZone[A1.FX][A1.FY][A1.FZ] != AZone[A2.FX][A2.FY][A2.FZ];
		
		if (result){
			ReZone(AZone, AZone[A2.FX][A2.FY][A2.FZ], AZone[A1.FX][A1.FY][A1.FZ], A2.FX, A2.FY, A2.FZ);
		}
		
		return result;
	}
	
	public static Cell[][][] kruskalMaze3D(int AX, int AY, int AZ)
	{
		int counter = 0;
		Cell[][][] result = new Cell[AX+2][AY+2][AZ+2];
		int[][][] trees = new int[AX+2][AY+2][AZ+2];
		
		LinkedList<Wall3D> walls = new LinkedList<Wall3D>();
		
		for (int x=0; x<AX+2; x++){
			for (int y=0; y<AY+2; y++){
				for (int z=0; z<AZ+2; z++){
					result[x][y][z] = new Cell(x, y, z);
					trees[x][y][z] = counter++;
					
					if (x>0 && x<AX) walls.add(new Wall3D(x, y, z, 0));
					if (y>0 && y<AY) walls.add(new Wall3D(x, y, z, 1));
					if (z>0 && z<AZ) walls.add(new Wall3D(x, y, z, 2));
				}
			}
		}
		
		while(!walls.isEmpty())
		{
			int randomWall = (int)(Math.random() * walls.size());
			
			Wall3D wall = walls.remove(randomWall);
			
			Cell source = result[wall.x][wall.y][wall.z];			
			switch (wall.direction){
				case 0:{// x
					if (combine(trees, source, result[wall.x+1][wall.y][wall.z])){
						source.FWall_X = false;
					}
					break;
				}
				case 1:{// y
					if (combine(trees, source, result[wall.x][wall.y+1][wall.z])){
						source.FWall_Y = false;
					}
					break;
				}
				case 2:{// z
					if (combine(trees, source, result[wall.x][wall.y][wall.z+1])){
						source.FWall_Z = false;
					}
					break;
				}
			}	
		}	
		
		return result;
	}
	
	@Override
	public String toString() {
		return FX+":"+FY+":"+FZ;
	}
}
