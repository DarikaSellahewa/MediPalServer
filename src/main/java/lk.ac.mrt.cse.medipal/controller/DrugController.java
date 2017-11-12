package lk.ac.mrt.cse.medipal.controller;

import lk.ac.mrt.cse.medipal.Database.DB_Connection;
import lk.ac.mrt.cse.medipal.model.Drug;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrugController {
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static Connection connection;
    public static Logger LOGGER = org.apache.log4j.Logger.getLogger(DrugController.class);

    public Drug getDrugDetails(String drugID){
        try {
            connection = DB_Connection.getDBConnection().getConnection();
            String SQL = "SELECT * FROM  `drug` WHERE `drug_id` = ?";
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, drugID);
            resultSet = preparedStatement.executeQuery();
            Drug drug = new Drug();
            if (resultSet.next()){
                drug.setDrug_id(resultSet.getString("drug_id"));
                drug.setDrug_name(resultSet.getString("drug_name"));
                drug.setCategory_id(resultSet.getString("category_id"));
                return drug;
            }
        } catch (SQLException | IOException | PropertyVetoException ex) {
            LOGGER.error("Error getting drug details", ex);
        } finally {
            try {
                DbUtils.closeQuietly(resultSet);
                DbUtils.closeQuietly(preparedStatement);
                DbUtils.close(connection);
            } catch (SQLException ex) {
                LOGGER.error("Error closing sql connection", ex);
            }
        }
        return null;
    }

    public ArrayList<Drug> getAllDrugs(){
//        try {
//            connection = DB_Connection.getDBConnection().getConnection();
//            String SQL = "SELECT * FROM  `drug`";
//            preparedStatement = connection.prepareStatement(SQL);
//            resultSet = preparedStatement.executeQuery();
//            ArrayList<Drug> drugList = new ArrayList<Drug>();
//            while (resultSet.next()){
//                Drug drug = new Drug();
//                drug.setDrug_id(resultSet.getString("drug_id"));
//                drug.setDrug_name(resultSet.getString("drug_name"));
//                drug.setCategory_id(resultSet.getString("category_id"));
//                drugList.add(drug);
//            }
//            return drugList;
//        } catch (SQLException | IOException | PropertyVetoException ex) {
//            LOGGER.error("Error getting drug details", ex);
//        } finally {
//            try {
//                DbUtils.closeQuietly(resultSet);
//                DbUtils.closeQuietly(preparedStatement);
//                DbUtils.close(connection);
//            } catch (SQLException ex) {
//                LOGGER.error("Error closing sql connection", ex);
//            }
//        }
        return null;
    }

    public ArrayList<Drug> getDrugsByDiease(String diseaseID){
        try {
            connection = DB_Connection.getDBConnection().getConnection();
            String SQL = "SELECT * FROM  `drug` WHERE `disease_id`= ?";
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, diseaseID);
            resultSet = preparedStatement.executeQuery();
            ArrayList<Drug> drugList = new ArrayList<Drug>();
            while (resultSet.next()){
                Drug drug = new Drug();
                drug.setDrug_id(resultSet.getString("drug_id"));
                drug.setDrug_name(resultSet.getString("drug_name"));
                drug.setCategory_id(resultSet.getString("category_id"));
                drugList.add(drug);
            }
            return drugList;
        } catch (SQLException | IOException | PropertyVetoException ex) {
            LOGGER.error("Error getting drugs for disease details", ex);
        } finally {
            try {
                DbUtils.closeQuietly(resultSet);
                DbUtils.closeQuietly(preparedStatement);
                DbUtils.close(connection);
            } catch (SQLException ex) {
                LOGGER.error("Error closing sql connection", ex);
            }
        }
        return null;
    }

    public ArrayList<String> getDrugListByCategoryList(int diseaseID, ArrayList<String> categoryList){
        try {
            connection = DB_Connection.getDBConnection().getConnection();
            String SQL = "SELECT drug.drug_id, drug.drug_name, category.CATEGORY_NAME, drug_disease.Disease " +
                    "FROM drug INNER JOIN category ON drug.category_id=category.CATEGORY_ID " +
                    "INNER JOIN drug_disease ON drug_disease.Drug = drug.drug_id " +
                    "AND drug_disease.Disease= ?;";
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, diseaseID);
            resultSet = preparedStatement.executeQuery();
            ArrayList<String> drugList = new ArrayList<String>();
            while (resultSet.next()){
                if(categoryList.contains(resultSet.getString("CATEGORY_NAME")));
                    drugList.add(resultSet.getString("drug_name"));
            }
            return drugList;
        } catch (SQLException | IOException | PropertyVetoException ex) {
            LOGGER.error("Error getting drug list", ex);
        } finally {
            try {
                DbUtils.closeQuietly(resultSet);
                DbUtils.closeQuietly(preparedStatement);
                DbUtils.close(connection);
            } catch (SQLException ex) {
                LOGGER.error("Error closing sql connection", ex);
            }
        }
        return null;
    }

    public ArrayList<String> getCategoryListByDrugList(int diseaseID, ArrayList<String> drugList){
        try {
            connection = DB_Connection.getDBConnection().getConnection();
            String SQL = "SELECT drug.drug_id, drug.drug_name, category.CATEGORY_NAME, drug_disease.Disease FROM drug " +
                    "INNER JOIN category ON drug.category_id=category.CATEGORY_ID " +
                    "INNER JOIN drug_disease ON drug_disease.Drug = drug.drug_id " +
                    "AND drug_disease.Disease= ?;";
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, diseaseID);
            resultSet = preparedStatement.executeQuery();
            ArrayList<String> categoryList = new ArrayList<String>();
            while (resultSet.next()){
                if(drugList.contains(resultSet.getString("drug_name")));
                    drugList.add(resultSet.getString("CATEGORY_NAME"));
            }
            return drugList;
        } catch (SQLException | IOException | PropertyVetoException ex) {
            LOGGER.error("Error getting category list", ex);
        } finally {
            try {
                DbUtils.closeQuietly(resultSet);
                DbUtils.closeQuietly(preparedStatement);
                DbUtils.close(connection);
            } catch (SQLException ex) {
                LOGGER.error("Error closing sql connection", ex);
            }
        }
        return null;
    }

    public String getDrugNameByID(int id){

        String drug_name = null;
        try{
                connection = DB_Connection.getDBConnection().getConnection();
                String SQL = "SELECT drug_name from drug where drug_id = ?";
                preparedStatement = connection.prepareStatement(SQL);
                preparedStatement.setInt(1,id);
                resultSet = preparedStatement.executeQuery();
                drug_name = resultSet.getString("drug_name");

        }catch(SQLException | IOException | PropertyVetoException ex) {
                LOGGER.error("Error getting category list", ex);
        } finally {
            try {
                    DbUtils.closeQuietly(resultSet);
                    DbUtils.closeQuietly(preparedStatement);
                    DbUtils.close(connection);
            } catch (SQLException ex) {
                    LOGGER.error("Error closing sql connection", ex);
            }
            }

            return drug_name;

        }

    //Inserting the d-d interactions to the hash map
    public HashMap<String, List<Integer>> getSeverityValueFromDBForDrugToDruginteraction(HashMap<String, List<Integer>> scoreValue){

        String id = null;
        String drug_name1 = null;
        String drug_name2 = null;

        try{
            connection = DB_Connection.getDBConnection().getConnection();
            String SQL_1 = "SELECT drug1_id, drug2_id, severity from d_d_interaction";
            preparedStatement = connection.prepareStatement(SQL_1);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                drug_name1 = getDrugNameByID(resultSet.getInt("drug1_id"));
                drug_name2 = getDrugNameByID(resultSet.getInt("drug2_id"));
                id = drug_name1 +"_"+ drug_name2;
                if(scoreValue.containsKey(id)){
                    scoreValue.get(id).add(resultSet.getInt("severity"));
                } else {
                    scoreValue.put(id, new ArrayList<Integer>());

                }


            }

        }catch(SQLException | IOException | PropertyVetoException ex) {
            LOGGER.error("Error getting category list", ex);
        } finally {
            try {
                DbUtils.closeQuietly(resultSet);
                DbUtils.closeQuietly(preparedStatement);
                DbUtils.close(connection);
            } catch (SQLException ex) {
                LOGGER.error("Error closing sql connection", ex);
            }
        }

        return scoreValue;
    }


    //Inserting the d-disease interactions to the hash map
    public HashMap<String, List<Integer>> getSeverityValueFromDBForDrugToDiseaseinteraction(HashMap<String, List<Integer>> scoreValue){

        String id = null;
        String drug_name = null;
        String disease_name = null;

        try{
            connection = DB_Connection.getDBConnection().getConnection();
            String SQL_1 = "SELECT drug_id, disease_id, severity from d_di_interaction";
            preparedStatement = connection.prepareStatement(SQL_1);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                drug_name = getDrugNameByID(resultSet.getInt("drug_id"));

                if (resultSet.getInt("disease_id") == 1)
                    disease_name = "Diabetes";
                else if (resultSet.getInt("disease_id") == 2)
                    disease_name = "Hypertension";
                else
                    disease_name = "COPD";


                id = drug_name +"_"+ disease_name;
                if(scoreValue.containsKey(id)){
                    scoreValue.get(id).add(resultSet.getInt("severity"));
                } else {
                    scoreValue.put(id, new ArrayList<Integer>());

                }


            }

        }catch(SQLException | IOException | PropertyVetoException ex) {
            LOGGER.error("Error getting category list", ex);
        } finally {
            try {
                DbUtils.closeQuietly(resultSet);
                DbUtils.closeQuietly(preparedStatement);
                DbUtils.close(connection);
            } catch (SQLException ex) {
                LOGGER.error("Error closing sql connection", ex);
            }
        }

        return scoreValue;
    }



}
