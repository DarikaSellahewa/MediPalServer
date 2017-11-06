package lk.ac.mrt.cse.medipal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScoreCalculationController {

    public int[] matrixMul(String[] historicalMedicines, String[][] prescribedMedicines){

        HashMap<String, List<Integer>> scoreValue = new HashMap<>();
        String[] comparingStrings = new String[2];
        int[] conflictArr = new int[prescribedMedicines.length];
        int conflictValue = 0;

        for(int i=0; i< historicalMedicines.length;i++){

            conflictValue = 0;
            for (int j=0; j<historicalMedicines.length ;j++){

                    comparingStrings[0] = historicalMedicines[i];
                    comparingStrings[1] = prescribedMedicines[i][j];
                    conflictValue += getConflictValue(scoreValue,comparingStrings);//pass the hash map parameter

            }

            conflictArr[i] = conflictValue;



        }

        return conflictArr;

    }

    /*
    public int conflictValDerivation(String[] comparingStrings){

        int conflictVal = 0;

        if(comparingStrings[0] == null || comparingStrings[1] == null)
            conflictVal = 0;
        else{
            String id = comparingStrings[0]+"_"+comparingStrings[1];
            conflictVal = valueFromRAM(id);

        }


        return conflictVal;

    }
    */

    /*
    public HashMap<String, List<Integer>> conflictDataToHM(String[] indices){

        HashMap<String, List<Integer>> scoreValue = new HashMap<>();
        int val = -1000;//db value from db for two indices: method call
        //indices = get indices from db for each drug from the db
        String id = indices[0]+"_"+indices[1];

        if(scoreValue.containsKey(id)){
            scoreValue.get(id).add(val);
        } else {
            scoreValue.put(id, new ArrayList<Integer>());

        }

        return scoreValue;

    }*/

    public int getConflictValue(HashMap<String, List<Integer>> scoreValue, String[] comparingStrings){

        String id = comparingStrings[0]+"_"+comparingStrings[1];
        int conflictVal = 0;
        conflictVal = scoreValue.get(id).get(0);//converting the Integer value to int

        return conflictVal;


    }

}
