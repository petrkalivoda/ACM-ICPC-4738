import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * @author Petr Kalivoda
 */
public class Main {

    public static final double PI = 3.141;

    public static void main(String[] args) throws IOException {
        double T;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int nMissiles, nCase = 0;
        double[][] missiles, towers;

        while ((line = br.readLine()) instanceof String) {
            if (line.trim().equals("")) {
                continue; //ignore blank lines
            }

            nMissiles = Integer.parseInt(line);
            if (nMissiles == 0) {
                break; //end
            }

            line = br.readLine();
            StringTokenizer st = new StringTokenizer(line);

            towers = new double[][]{
                {
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken()),
                },
                {
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken()),
                },
            };

            T = Double.parseDouble(st.nextToken());

            missiles = new double[nMissiles][];

            for (int i = 0; i < missiles.length; i++) {
                line = br.readLine();
                st = new StringTokenizer(line);

                double[] tmp_missile = new double[] {
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken()),
                };
                
                missiles[i] = new double[]{
                    tmp_missile[0],
                    tmp_missile[1],
                    distanceSQ(tmp_missile, towers[0]),
                    distanceSQ(tmp_missile, towers[1]),
                };
            }

            System.out.printf("%d. %d\n", ++nCase, doTestCase(T, towers, missiles));

        }
    }

    public static int doTestCase(double T, double[][] towers, double[][] missiles) {
        int covered = 0;
        double RTower1, RTower2;

        for (double[] missile : missiles) {
            //#1. - RTower1 given by i-th missile, RTower2 computed
            RTower1 = missile[2];
            RTower2 = T/PI - RTower1;
            if(RTower2 >= 0) {
                //otherwise missile can not be covered by tower 1
                covered = Math.max(covered, countCovered(missiles, towers, RTower1, RTower2));
            }
            //#2. - RTower2 given by i-th missile, RTower1 computed
            RTower2 = missile[3];
            RTower1 = T/PI - RTower2;
            if(RTower1 >= 0) {
                //otherwise missile can not be covered by tower 2
                covered = Math.max(covered, countCovered(missiles, towers, RTower1, RTower2));
            }
            
            if(covered == missiles.length) {
                //no point in computing anymore
                return 0;
            }
        }

        return missiles.length - covered;
    }
    
    /**
     * Counts missiles covered by the shield
     * 
     * @param missiles
     * @param towers
     * @param RTower1 squared radius of tower 1
     * @param RTower2 squared radius of tower 2
     * @return 
     */
    public static int countCovered(double[][] missiles, double[][] towers, double RTower1, double RTower2) {
        int nCovered = 0;
        
        for (double[] missile : missiles) {
            if(isCovered(missile, RTower1, RTower2)) {
                nCovered++;
            }
        }

        return nCovered;
    }

    /**
     * Determines, wether or not is a missile covered by the shield
     *
     * @param missile
     * @param RTower1 squared radius of tower 1
     * @param RTower2 squared radius of tower 2
     * @return
     */
    public static boolean isCovered(double[] missile, double RTower1, double RTower2) {
        return missile[2] <= RTower1 || missile[3] <= RTower2;
    }

    /**
     * Calculates squared distance between two points
     *
     * @param pointA
     * @param pointB
     * @return
     */
    public static double distanceSQ(double[] pointA, double[] pointB) {
        return Math.pow(pointA[0] - pointB[0], 2) + Math.pow(pointA[1] - pointB[1], 2);
    }
}