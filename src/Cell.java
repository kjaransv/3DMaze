import java.awt.Point;
import java.util.*;

public class Cell {
	private boolean FWall_X, FWall_Y, FWall_Z;	
	public int FX, FY, FZ;
	
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
		Cell[][][] result = new Cell[AX][AY][AZ];
		int[][][] trees = new int[AX][AY][AZ];
		
		LinkedList<Wall3D> walls = new LinkedList<Wall3D>();
		
		for (int x=0; x<AX; x++){
			for (int y=0; y<AY; y++){
				for (int z=0; z<AZ; z++){
					result[x][y][z] = new Cell(x, y, z);
					trees[x][y][z] = counter++;
					
					if (x<AX-1) walls.add(new Wall3D(x, y, z, 0));
					if (y<AY-1) walls.add(new Wall3D(x, y, z, 1));
					if (z<AZ-1) walls.add(new Wall3D(x, y, z, 2));
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
		
	public static void combine(int[][] tree, int x1, int y1, int x2, int y2)
	{
		int temp = tree[x2][y2];
		
		tree[x2][y2] = tree[x1][y1];
		
		if(x2 > 0 && tree[x2 - 1][y2] == temp) combine(tree,x2,y2,x2-1,y2);
		if(x2 < tree.length - 1 && tree[x2 + 1][y2] == temp) combine(tree,x2,y2,x2+1,y2);
		if(y2 > 0 && tree[x2][y2 - 1] == temp) combine(tree,x2,y2,x2,y2 - 1);
		if(y2 < tree[0].length-1 && tree[x2][y2 + 1] == temp) combine(tree,x2,y2,x2,y2+1);
	}
	
	public static Cell[][][] ExampleMaze(int AX, int AY, int AZ){
		Cell[][][] result = new Cell[AX][AY][AZ];
		
		for (int x=0; x<AX; x++){
			for (int y=0; y<AY; y++){
				for (int z=0; z<AZ; z++){				
					result[x][y][z] = new Cell(x, y, z);
					result[x][y][z].FWall_X = x==0;
					result[x][y][z].FWall_Y = y==0;
					result[x][y][z].FWall_Z = z==0;
				}
			}
		}
		
		/*result[5][3].FSouthWall = true;
		result[3][5].FEastWall = true;*/
		
		return result;
	}
	
	@Override
	public String toString() {
		return FX+":"+FY+":"+FZ;
	}
}
