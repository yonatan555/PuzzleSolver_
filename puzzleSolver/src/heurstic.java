public class heurstic {
	// sum of Manhattan distances between tiles and goal
	int manhattan_algorithmD(int[][]arr , int[][] goal) {
			int sum = 0;
			for (int i = 0; i < goal.length; i++) {
				for (int j = 0; j < goal[0].length; j++) {
					for (int i2 = 0; i2 < arr.length; i2 ++) {
						for (int j2 = 0; j2 < arr[0].length; j2++) {
							if (arr[i2 ][j2] == goal[i][j] && arr[i2 ][j2] != 0 ) {
								sum += Math.abs(i-i2 )+Math.abs(j-j2);
							}
						}
					}
				}
			}
			return sum*3;
		}
}


